package com.zpmc.ztos.infra.base.business.model;

import com.alibaba.fastjson.support.geo.Geometry;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.AbstractBinDO;
import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.spatial.BinNameTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.Disjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.HibernatingObjRef;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public abstract class AbstractBin extends AbstractBinDO implements IBinCopyable,
        IObsoleteable {
//    private transient FieldHandler _fieldHandler;
    public static final long TOP_BIN_LEVEL = 1L;
    private static final boolean IS_COPY_OPERATION = true;
    public static final boolean EXCLUDE_BIN_ANCESTORS = true;
    public static final boolean INCLUDE_ACTIVE_FILTER = true;
    private static final Logger LOGGER = Logger.getLogger(AbstractBin.class);

    public AbstractBin() {
        this.setAbnLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setAbnLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getAbnLifeCycleState();
    }

    public void doBeforeMergeProcessing(AbstractBin inToBin) {
    }

    public void reviewDuplicateChildren(IMessageCollector inOutMessageCollector) {
    }

    public static void deleteBinAndOwnedObjects(Serializable inBinGkey, boolean inDatabaseDelete) {
        AbstractBin.deleteBinAndOwnedObjects(inBinGkey, MessageCollectorUtils.getMessageCollector(), inDatabaseDelete);
    }

    public static void deleteBinAndOwnedObjects(final Serializable inBinGkey, IMessageCollector inMessageCollector, final boolean inDatabaseDelete) {
        final HibernateApi hibernateApi = HibernateApi.getInstance();
        final ArrayList keysForObjectsToDelete = new ArrayList();
        PersistenceTemplate pt = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
        IMessageCollector msg = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                AbstractBin binToDelete = (AbstractBin)hibernateApi.get(AbstractBin.class, inBinGkey);
                if (binToDelete != null) {
                    binToDelete.addOwnedObjectKeysToListInDeleteOrder(keysForObjectsToDelete, false);
                    binToDelete.clearFKRefsBeforeDelete();
                }
            }
        });
        MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        int batchSize = 100;
        final Counter prevI = new Counter(0);
        while (prevI._index < keysForObjectsToDelete.size()) {
            msg = pt.invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    for (int i = prevI._index; i < Math.min(prevI._index + 100, keysForObjectsToDelete.size()); ++i) {
                        Serializable objGkey = ((HibernatingObjRef)keysForObjectsToDelete.get(i)).getObjGkey();
                        Class objClass = ((HibernatingObjRef)keysForObjectsToDelete.get(i)).getEntityClass();
                        DatabaseEntity objToDelete = (DatabaseEntity)hibernateApi.get(objClass, objGkey);
                        if (objToDelete == null) continue;
                        hibernateApi.delete((Object)objToDelete, inDatabaseDelete);
                    }
                    prevI._index += 100;
                }
            });
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
            if (!msg.containsMessage()) continue;
            break;
        }
    }

    public static void generateBinAncestorsForAllDescendants(final Serializable inBinGkey, IMessageCollector inMessageCollector) {
        final ArrayList keysForBinsNeedingBan = new ArrayList();
        PersistenceTemplate pt = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
        IMessageCollector msg = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                AbstractBin topBin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, inBinGkey);
                if (topBin != null) {
                    Boolean orderBinsByTypeDesc = Boolean.TRUE;
                    topBin.addOwnedBinKeysToListOrderedByLevelAndName(keysForBinsNeedingBan, orderBinsByTypeDesc);
                }
            }
        });
        MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        int batchSize = 100;
        final Counter prevJ = new Counter(0);
        while (prevJ._index < keysForBinsNeedingBan.size()) {
            pt = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
            msg = pt.invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    for (int i = prevJ._index; i < Math.min(prevJ._index + 100, keysForBinsNeedingBan.size()); ++i) {
                        AbstractBin bin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, (Serializable)keysForBinsNeedingBan.get(i));
                        if (bin == null) continue;
                        bin.generateBinAncestors();
                    }
                    prevJ._index += 100;
                }
            });
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
            if (!msg.containsMessage()) continue;
            break;
        }
    }

    public static void createBinAncestorsForAllDescendants(UserContext inUserContext, final Serializable inBinGkey, IMessageCollector inMessageCollector) {
        final ArrayList keysForBinsNeedingBan = new ArrayList();
        PersistenceTemplate pt = new PersistenceTemplate(inUserContext);
        IMessageCollector msg = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                AbstractBin topBin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, inBinGkey);
                if (topBin != null) {
                    Boolean orderBinsByTypeDesc = Boolean.TRUE;
                    topBin.addOwnedBinKeysToListOrderedByLevelAndName(keysForBinsNeedingBan, orderBinsByTypeDesc);
                }
            }
        });
        MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        int batchSize = 100;
        final Counter prevJ = new Counter(0);
        while (prevJ._index < keysForBinsNeedingBan.size()) {
            pt = new PersistenceTemplate(inUserContext);
            msg = pt.invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    for (int i = prevJ._index; i < Math.min(prevJ._index + 100, keysForBinsNeedingBan.size()); ++i) {
                        AbstractBin bin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, (Serializable)keysForBinsNeedingBan.get(i));
                        if (bin == null) continue;
                        bin.createBinAncestors();
                    }
                    prevJ._index += 100;
                }
            });
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
            if (!msg.containsMessage()) continue;
            break;
        }
    }

    public static void updateCachedBinNamesForDescendants(UserContext inUserContext, final Serializable inBinGkey, IMessageCollector inMessageCollector) {
        final ArrayList keysForBinsNeedingName = new ArrayList();
        PersistenceTemplate pt = new PersistenceTemplate(inUserContext);
        IMessageCollector msg = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                AbstractBin topBin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, inBinGkey);
                if (topBin != null) {
                    Boolean orderBinsByTypeDesc = Boolean.FALSE;
                    topBin.addOwnedBinKeysToListOrderedByLevelAndName(keysForBinsNeedingName, orderBinsByTypeDesc);
                }
            }
        });
        if (inMessageCollector.getMessageCount() < 10000) {
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        }
        int batchSize = 100;
        final Counter prevJ = new Counter(0);
        while (prevJ._index < keysForBinsNeedingName.size()) {
            pt = new PersistenceTemplate(inUserContext);
            pt.setRollbackOnSevereErrors(false);
            msg = pt.invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    for (int i = prevJ._index; i < Math.min(prevJ._index + 100, keysForBinsNeedingName.size()); ++i) {
                        AbstractBin bin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, (Serializable)keysForBinsNeedingName.get(i));
                        if (bin == null || inBinGkey.equals(bin.getPrimaryKey())) continue;
                        bin.updateCachedBinName();
                    }
                    prevJ._index += 100;
                }
            });
            if (inMessageCollector.getMessageCount() >= 10000) continue;
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        }
    }

    public static void deleteBinsWithDupNames(final Serializable inBinGkey, IMessageCollector inMessageCollector) {
        final ArrayList keysForBinsNeedingDupRvw = new ArrayList();
        PersistenceTemplate pt = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
        IMessageCollector msg = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                AbstractBin topBin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, inBinGkey);
                if (topBin != null) {
                    Boolean orderBinsByTypeDesc = Boolean.TRUE;
                    topBin.addOwnedBinKeysToListOrderedByLevelAndName(keysForBinsNeedingDupRvw, orderBinsByTypeDesc);
                }
            }
        });
        MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
        final ArrayList keysOfBinsToDelete = new ArrayList();
        int batchSize = 100;
        final Counter prevI = new Counter(0);
        final PrevBinInfo prevBinInfo = new PrevBinInfo();
        while (prevI._index < keysForBinsNeedingDupRvw.size()) {
            msg = pt.invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    for (int i = prevI._index; i < Math.min(prevI._index + 100, keysForBinsNeedingDupRvw.size()); ++i) {
                        Serializable objGkey = (Serializable)keysForBinsNeedingDupRvw.get(i);
                        AbstractBin bin = (AbstractBin)HibernateApi.getInstance().get(AbstractBin.class, objGkey);
                        if (bin == null) continue;
                        String currentBinName = bin.getAbnName();
                        if (prevBinInfo._prevBinTypeGkey != null && bin.getAbnBinType().getBtpGkey().equals(prevBinInfo._prevBinTypeGkey) && prevBinInfo._prevBinName != null && prevBinInfo._prevBinName.equals(currentBinName)) {
                            LOGGER.warn((Object)("Found duplicate bin name " + currentBinName + "for bin with gkey = " + bin.getAbnGkey() + ". Deleting."));
                            keysOfBinsToDelete.add(bin.getAbnGkey());
                        } else if (currentBinName == null) {
                            LOGGER.warn((Object)("Found nameless bin with gkey = " + bin.getAbnGkey() + ". Deleting."));
                            keysOfBinsToDelete.add(bin.getAbnGkey());
                        } else {
                            prevBinInfo._prevBinName = currentBinName;
                        }
                        prevBinInfo._prevBinTypeGkey = bin.getAbnBinType().getBtpGkey();
                    }
                    prevI._index += 100;
                }
            });
            MessageCollectorUtils.appendMessages((IMessageCollector)inMessageCollector, (IMessageCollector)msg);
            if (!msg.containsMessage()) continue;
        }
        for (Object binGkey : keysOfBinsToDelete) {
            AbstractBin.deleteBinAndOwnedObjects((Serializable)binGkey, inMessageCollector, true);
        }
    }

    @Nullable
    public AbstractBin findAncestorBinAtLevel(Long inAncestorLevel) {
        AbstractBin ancestorBin;
        if (this.getBinLevel() == inAncestorLevel.longValue()) {
            return this;
        }
        for (ancestorBin = this.getAbnParentBin(); ancestorBin != null && inAncestorLevel < ancestorBin.getBinLevel(); ancestorBin = ancestorBin.getAbnParentBin()) {
        }
        return ancestorBin;
    }

    public List<AbstractBin> findOwnedBins(BinContext inBinContext) {
        return this.findOwnedBins(inBinContext, true);
    }

    public List<AbstractBin> findOwnedBins(BinContext inBinContext, boolean inIncludeActiveFilter) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.ABN_PARENT_BIN, (Object)this.getAbnGkey()));
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        if (inIncludeActiveFilter) {
   //         dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public List<AbstractBin> findDescendantsOfBinType(BinType inBinType) {
        if (inBinType == null) {
            return new ArrayList<AbstractBin>();
        }
        IDomainQuery dq = this.getAllDescendantBinsDq(null, true);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.ABN_BIN_TYPE, (Object)inBinType.getBtpGkey()));
        List bins = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        int maxReasonableResults = 3000;
        if (bins.size() > 3000) {
            LOGGER.warn((Object)"findDescendantsOfBinType: more than 3000 returned. 3000");
        }
        return bins;
    }

    public boolean descendantsOfBinTypeExist(BinType inBinType) {
        IDomainQuery dq = this.getAllDescendantBinsDq(null, false);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.ABN_BIN_TYPE, (Object)inBinType.getBtpGkey()));
        return Roastery.getHibernateApi().existsByDomainQuery(dq);
    }

    public List<AbstractBin> findDescendantsIntersectingPolygon(Geometry inPolygon, BinContext inBinContext) {
        IDomainQuery dq = this.getDescendantsIntersectingPolygonDQ(inPolygon, inBinContext, true);
        List bins = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        int maxReasonableResults = 1000;
        if (bins.size() > 1000) {
            LOGGER.warn((Object)"findDescendantsIntersectingPolygon: more than 1000 returned.");
        }
        return bins;
    }

    public IDomainQuery getDescendantsIntersectingPolygonDQ(Geometry inPolygon, BinContext inBinContext, boolean inHydratingEntities) {
        IDomainQuery dq = this.getIntersectingBinsPolygonDQ(inPolygon, inBinContext, inHydratingEntities);
        dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IBinField.ABN_GKEY, (Object)this.getAbnGkey()));
        return dq;
    }

    public IDomainQuery getIntersectingBinsPolygonDQ(Geometry inPolygon, BinContext inBinContext, boolean inHydratingEntities) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqField(IBinField.ABN_GKEY).addDqPredicate(PredicateFactory.geoIntersects((IMetafieldId)IBinField.ABN_POLYGON, (Object)inPolygon));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        this.addAncestorBinJoin(dq, inHydratingEntities);
        return dq;
    }

    public IDomainQuery getDescendantsWithinPolygonDQ(@NotNull Geometry inPolygon, @NotNull BinContext inBinContext, boolean inHydratingEntities) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqField(IBinField.ABN_GKEY).addDqPredicate(PredicateFactory.geoWithin((IMetafieldId)IBinField.ABN_POLYGON, (Object)inPolygon));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        this.addAncestorBinJoin(dq, inHydratingEntities);
        dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IBinField.ABN_GKEY, (Object)this.getAbnGkey()));
        return dq;
    }

    public List<AbstractBin> findDescendantsContainingPoint(Point inPoint, BinContext inBinContext) {
        IDomainQuery dq = this.getAllDescendantBinsDq(inBinContext, true);
        dq.addDqPredicate(PredicateFactory.geoContains((IMetafieldId)IBinField.ABN_POLYGON, (Object)inPoint));
        List bins = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        int maxReasonableResults = 1000;
        if (bins.size() > 1000) {
            LOGGER.warn((Object)"findDescendantsContainingPoint: more than 1000 returned.");
        }
        return bins;
    }

    @Nullable
    public AbstractBin findDescendantBinFromInternalSlotString(@Nullable String inInternalSlotString, BinContext inBinContext) {
        AbstractBin bin = null;
        List<AbstractBin> bins = this.findDescendantBinsFromInternalSlotString(inInternalSlotString, inBinContext);
        if (bins != null && !bins.isEmpty()) {
            Collections.sort(bins, new BinLevelComparatorDesc());
            bin = bins.get(0);
        }
        return bin;
    }

    public Map<String, Serializable> findDescendantBinFromPPos(@Nullable String inInternalSlotString, BinContext inBinContext) {
        AbstractBin bin = null;
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        List<AbstractBin> bins = this.findDescendantBinsFromInternalSlotString(inInternalSlotString, inBinContext);
        if (bins != null && !bins.isEmpty()) {
            Collections.sort(bins, new BinLevelComparatorDesc());
            bin = bins.get(0);
        }
        if (bin != null) {
            long binLevel = bin.getBinLevel();
            if (binLevel == 2L) {
                map.put("ABM_BLOCK", bin.getAbnGkey());
            } else if (binLevel == 3L) {
                map.put("ABM_SECTION", bin.getAbnGkey());
                map.put("ABM_BLOCK", bin.getAbnParentBin().getAbnGkey());
            } else if (binLevel == 4L) {
                map.put("ABM_STACK", bin.getAbnGkey());
                map.put("ABM_SECTION", bin.getAbnParentBin().getAbnGkey());
                map.put("ABM_BLOCK", bin.getAbnParentBin().getAbnParentBin().getAbnGkey());
            }
        }
        return map;
    }

    public List<AbstractBin> findDescendantBinsFromInternalSlotString(String inInternalSlotString, BinContext inBinContext) {
        List<AbstractBin> resultBins = null;
        if (inInternalSlotString != null && !inInternalSlotString.isEmpty()) {
            String binName = this.computeBinNameFromInternalSlotString(inInternalSlotString);
            resultBins = this.findDescendantBinsFromBinName(inBinContext, binName, IBinField.ABN_NAME);
            if (resultBins.isEmpty()) {
                resultBins = this.findDescendantBinsFromBinName(inBinContext, binName, IBinField.ABN_NAME_ALT);
            }
        } else {
            resultBins = new ArrayList<AbstractBin>();
        }
        return resultBins;
    }

    @Nullable
    public AbstractBin findDescendantBinByNameAndType(String inInternalSlotString, BinContext inBinContext, BinType inBinType) {
        AbstractBin resultBin = null;
        List<AbstractBin> bins = this.findDescendantBinsFromInternalSlotString(inInternalSlotString, inBinContext);
        if (bins != null && !bins.isEmpty()) {
            for (AbstractBin bin : bins) {
                if (!inBinType.equals(bin.getAbnBinType())) continue;
                resultBin = bin;
                break;
            }
        }
        return resultBin;
    }

    public List<Serializable> findAllDescendantBinGkeys(BinContext inBinContext) {
        IDomainQuery dq = this.getAllDescendantBinsDq(inBinContext, false);
        return Arrays.asList(Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq));
    }

    public Long getTierIndexFromInternalSlotString(String inSlotName) {
        Long tierIndex = 0L;
        String tierString = this.getTierStringFromInternalSlotString(inSlotName);
        if (tierString != null && !tierString.isEmpty()) {
            try {
                tierIndex = Long.parseLong(tierString);
            }
            catch (NumberFormatException e) {
                tierIndex = 0L;
            }
        }
        return tierIndex;
    }

    public String computeInternalBinName(BinNameTypeEnum inBinNameType) {
        if (BinNameTypeEnum.STANDARD.equals((Object)inBinNameType)) {
            return this.getAbnName();
        }
        return this.getAbnNameAlt();
    }

    public abstract long getBinLevel();

    public void setParentBin(AbstractBin inParentBin) {
        this.setAbnParentBin(inParentBin);
    }

    public void createBinAncestors() {
        AbstractBin parentBin = this.getAbnParentBin();
        if (parentBin != null) {
            while (parentBin != null) {
                BinAncestor.createBinAncestor(this, parentBin);
                parentBin = parentBin.getAbnParentBin();
            }
            BinAncestor.createBinAncestor(this, this);
        }
    }

    public void generateBinAncestors() {
        AbstractBin parentBin = this.getAbnParentBin();
        if (parentBin != null) {
            while (parentBin != null) {
                BinAncestor.updateOrCreateBinAncestor(this, parentBin);
                parentBin = parentBin.getAbnParentBin();
            }
            BinAncestor.updateOrCreateBinAncestor(this, this);
        }
    }

    public String getTierName(Long inTier) {
        return inTier.toString();
    }

    public void updateName(String inBinName) {
        this.setAbnName(inBinName);
    }

    public void updateNameAlt(String inBinNameAlt) {
        this.setAbnNameAlt(inBinNameAlt);
    }

    public void updateBinPolygon(Geometry inPolygon) {
        this.setAbnPolygon(inPolygon);
    }

    public void updateGeodeticAnchorLatitude(Double inLatitude) {
        this.setAbnGeodeticAnchorLatitude(inLatitude);
    }

    public void updateGeodeticAnchorLongitude(Double inLongitude) {
        this.setAbnGeodeticAnchorLongitude(inLongitude);
    }

    public void updateGeodeticAnchorOrientation(Double inOrientation) {
        this.setAbnGeodeticAnchorOrientation(inOrientation);
    }

    public static boolean isBinInstanceOf(AbstractBin inAbstractBin, Class inClassToCompare) {
        IBinClassesMetaData md = (IBinClassesMetaData)Roastery.getBean((String)"binClassesMetaData");
        return md.isBinAnInstanceOf(inAbstractBin, inClassToCompare);
    }

    public static boolean isBinSubTypeInstanceOf(String inBinSubType, Class inClassToCompare) {
        IBinClassesMetaData md = (IBinClassesMetaData)Roastery.getBean((String)"binClassesMetaData");
        return md.isBinSubTypeAnInstanceOf(inBinSubType, inClassToCompare);
    }

    @Override
    public void addOwnedObjectKeysToListInCopyOrder(List<HibernatingObjRef> inOutKeysToCopy, IBinOwnedEntitiesFilter inOwnedEntitiesFilter) {
        IBinClassesMetaData md = (IBinClassesMetaData)Roastery.getBean((String)"binClassesMetaData");
        inOutKeysToCopy.add(new HibernatingObjRef(md.getClassForBin(this), this.getAbnGkey()));
        if (inOwnedEntitiesFilter.isRequired("BinNameTable")) {
            List<BinNameTable> bnts = BinNameTable.findBinNameTablesForOwningBin(this);
            for (BinNameTable bnt : bnts) {
                bnt.addOwnedObjectKeysToListInCopyOrder(inOutKeysToCopy, inOwnedEntitiesFilter);
                HibernateApi.getInstance().evict((Object)bnt);
            }
        }
        List<AbstractBin> ownedBins = this.findOwnedBins(null);
        for (AbstractBin ownedBin : ownedBins) {
            ownedBin.addOwnedObjectKeysToListInCopyOrder(inOutKeysToCopy, inOwnedEntitiesFilter);
            HibernateApi.getInstance().evict((Object)ownedBin);
        }
    }

    @Override
    public void shallowCopy(Class inClass, Serializable inToTopBinGkey, long inTopBinLevel, IBinCopyable inObjToCopyTo) {
 //       BinCopyableUtils.shallowCopy(inClass, this, inToTopBinGkey, inObjToCopyTo, inTopBinLevel);
        ((AbstractBin)inObjToCopyTo).setAbnBinType(this.getAbnBinType());
    }

    @Override
    public List<IMetafieldId> getKeyFields() {
        ArrayList<IMetafieldId> naturalKeyFields = new ArrayList<IMetafieldId>();
        naturalKeyFields.add(IBinField.ABN_BIN_TYPE);
        naturalKeyFields.add(IBinField.ABN_NAME);
        return naturalKeyFields;
    }

    @Override
    public IMetafieldId getTopBinField(long inTopBinLevel) {
        IMetafieldId topBinGkeyField;
        long binLevel = this.getBinLevel();
        if (binLevel <= inTopBinLevel) {
            topBinGkeyField = IBinField.ABN_GKEY;
        } else {
            topBinGkeyField = IBinField.ABN_PARENT_BIN;
            for (long currentLevel = inTopBinLevel + 1L; currentLevel < binLevel; ++currentLevel) {
                topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)topBinGkeyField, (IMetafieldId)IBinField.ABN_PARENT_BIN);
            }
            topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)topBinGkeyField, (IMetafieldId)IBinField.ABN_GKEY);
        }
        return topBinGkeyField;
    }

    @Override
    public boolean hasNamingScheme() {
        return false;
    }

    public String getUiFullPositionWithTier(BinNameTypeEnum inBinNameType, String inTierName) {
        if (inBinNameType.equals((Object)BinNameTypeEnum.STANDARD)) {
            return this.getAbnName() + "." + inTierName;
        }
        return this.getAbnNameAlt() + "." + inTierName;
    }

    public String getBinNameWithTier(BinNameTypeEnum inBinNameType, String inTierName) {
        if (inBinNameType.equals((Object)BinNameTypeEnum.STANDARD)) {
            return this.getAbnName() + "." + inTierName;
        }
        return this.getAbnNameAlt() + "." + inTierName;
    }

    public boolean isValidTierName(String inTierName) {
        return true;
    }

    public void clearFKRefsBeforeDelete() {
    }

    protected Integer getBinNameMaxLength() {
        return null;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        String name;
        BizViolation violationChain = super.validateChanges(inChanges);
        Integer binNameMaxLen = this.getBinNameMaxLength();
        if (inChanges.hasFieldChange(IBinField.ABN_NAME) && binNameMaxLen != null && (name = (String)inChanges.getFieldChange(IBinField.ABN_NAME).getNewValue()).length() > binNameMaxLen) {
            violationChain = BizViolation.create((IPropertyKey)ISpatialPropertyKeys.BIN_NAME_EXCEEDS_MAX_LENGTH, (BizViolation)violationChain, (Object)name, (Object)binNameMaxLen);
        }
        return violationChain;
    }

    public void addOwnedObjectKeysToListInDeleteOrder(List<HibernatingObjRef> inOutKeysToDelete, boolean inExcludeBinAncestors) {
        this.addOwnedObjectKeysToListInDeleteOrder(inOutKeysToDelete, inExcludeBinAncestors, true);
    }

    public void addOwnedObjectKeysToListInDeleteOrder(List<HibernatingObjRef> inOutKeysToDelete, boolean inExcludeBinAncestors, boolean inIncludeActiveFilter) {
        HibernateApi hibernateApi = HibernateApi.getInstance();
        List<AbstractBin> ownedBins = this.findOwnedBins(null, inIncludeActiveFilter);
        for (AbstractBin ownedBin : ownedBins) {
            ownedBin.addOwnedObjectKeysToListInDeleteOrder(inOutKeysToDelete, inExcludeBinAncestors, inIncludeActiveFilter);
            hibernateApi.evict((Object)ownedBin);
        }
        if (!inExcludeBinAncestors) {
            List<BinAncestor> banResults = BinAncestor.findBinAncestorsForBin(this, inIncludeActiveFilter);
            for (BinAncestor ban : banResults) {
                inOutKeysToDelete.add(new HibernatingObjRef(BinAncestor.class, ban.getBanGkey()));
                hibernateApi.evict((Object)ban);
            }
        }
        List<BinNameTable> bnts = BinNameTable.findBinNameTablesForOwningBin(this, inIncludeActiveFilter);
        for (BinNameTable bnt : bnts) {
            List<BinName> bns = bnt.getBinNames();
            for (BinName bn : bns) {
                inOutKeysToDelete.add(new HibernatingObjRef(BinName.class, bn.getBnmGkey()));
                hibernateApi.evict((Object)bn);
            }
            inOutKeysToDelete.add(new HibernatingObjRef(BinNameTable.class, bnt.getBntGkey()));
            hibernateApi.evict((Object)bnt);
        }
        inOutKeysToDelete.add(new HibernatingObjRef(AbstractBin.class, this.getAbnGkey()));
    }

    @Nullable
    protected String getTierStringFromInternalSlotString(String inSlotName) {
        String delim = ".";
        int invalidIndex = -1;
        int stackTierDelim = inSlotName.lastIndexOf(".");
        return stackTierDelim != -1 ? inSlotName.substring(stackTierDelim + 1, inSlotName.length()) : null;
    }

    protected String computeBinNameFromInternalSlotString(String inInternalSlotString) {
        String binName = inInternalSlotString;
        int lastDot = inInternalSlotString.lastIndexOf(46);
        if (lastDot >= 0) {
            binName = inInternalSlotString.substring(0, lastDot);
        }
        return binName;
    }

    public void addOwnedBinKeysToListOrderedByLevelAndName(List<Serializable> inKeysForBinsNeedingBan, Boolean inOrderByBinTypeDesc) {
        Long thisKey = this.getAbnGkey();
        Long maxLevel = this.getGlobalMaxBinLevel();
        if (maxLevel != null && maxLevel - this.getBinLevel() > 0L) {
            Ordering binTypeOrder = inOrderByBinTypeDesc != false ? Ordering.desc((IMetafieldId) BinCompoundField.ABN_BIN_TYPE_LEVEL) : Ordering.asc((IMetafieldId) BinCompoundField.ABN_BIN_TYPE_LEVEL);
            Disjunction disjunction = PredicateFactory.disjunction();
            IMetafieldId currentMFI = IBinField.ABN_PARENT_BIN;
            for (long level = this.getBinLevel() + 1L; level <= maxLevel; ++level) {
                disjunction.add(PredicateFactory.eq((IMetafieldId)currentMFI, (Object)thisKey));
                currentMFI = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)currentMFI, (IMetafieldId)IBinField.ABN_PARENT_BIN);
            }
            IDomainQuery abnDq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqField(IBinField.ABN_GKEY).addDqPredicate((IPredicate)disjunction).addDqOrdering(binTypeOrder).addDqOrdering(Ordering.asc((IMetafieldId)IBinField.ABN_NAME)).addDqOrdering(Ordering.asc((IMetafieldId)IBinField.ABN_GKEY));
            abnDq.setFullLeftOuterJoin(true);
            abnDq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
            IQueryResult binKeys = Roastery.getHibernateApi().findValuesByDomainQuery(abnDq);
            for (int i = 0; i < binKeys.getTotalResultCount(); ++i) {
                Long key = (Long)binKeys.getValue(i, IBinField.ABN_GKEY);
                if (key == null) continue;
                inKeysForBinsNeedingBan.add(key);
            }
        }
        inKeysForBinsNeedingBan.add(thisKey);
    }

    private long getGlobalMaxBinLevel() {
        IDomainQuery maxLevelDq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqAggregateField(AggregateFunctionType.MAX, IBinField.ABN_BIN_LEVEL);
        maxLevelDq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult maxLevelResult = Roastery.getHibernateApi().findValuesByDomainQuery(maxLevelDq);
        Long maxLevel = 0L;
        if (maxLevelResult.getTotalResultCount() > 0) {
            maxLevel = (Long)maxLevelResult.getValue(0, 0);
        }
        return maxLevel;
    }

    public void updateCachedBinName() {
        String binNameStd = this.computeInternalBinName(BinNameTypeEnum.STANDARD);
        String binNameAlt = this.computeInternalBinName(BinNameTypeEnum.ALTERNATE);
        this.updateName(binNameStd);
        this.updateNameAlt(binNameAlt);
    }

    @Nullable
    private Point getXYPointFromGeoPointR(GeoPoint inGeoPoint) {
        Double geodeticAnchorLatitude = this.getAbnGeodeticAnchorLatitude();
        Double geodeticAnchorLongitude = this.getAbnGeodeticAnchorLongitude();
        if (geodeticAnchorLatitude != null && geodeticAnchorLongitude != null) {
            GeoPoint geoAnchor = new GeoPoint(geodeticAnchorLatitude, geodeticAnchorLongitude, inGeoPoint.getAltitudeMeters());
            double distance = geoAnchor.computeDistanceToCm(inGeoPoint);
            double azimuthDegrees = geoAnchor.computeAzimuthToDegrees(inGeoPoint);
            double azimuthRadians = Math.toRadians(azimuthDegrees);
            double geoOrientationRadians = Math.toRadians(this.getAbnGeodeticAnchorOrientation());
            double yardVectorRadians = (geoOrientationRadians + 1.5707963267948966 - azimuthRadians) % (Math.PI * 2);
            double modelY = distance * Math.sin(yardVectorRadians);
            double modelX = distance * Math.cos(yardVectorRadians);
  //          return new GeometryFactory().createPoint(new Coordinate(modelX, modelY, inGeoPoint.getAltitudeMeters()));
        }
        return null;
    }

    @Nullable
    public Point getXYPointFromGeoPoint(GeoPoint inGeoPoint) {
        if (inGeoPoint != null) {
            Point point = this.getXYPointFromGeoPointR(inGeoPoint);
            if (point == null) {
                AbstractBin parent = this.getAbnParentBin();
                if (parent != null) {
                    return parent.getXYPointFromGeoPoint(inGeoPoint);
                }
            } else {
                return point;
            }
        }
        return null;
    }

    @Nullable
    public Point getXYPoint() {
        Geometry binPolygon = this.getAbnPolygon();
        if (binPolygon != null) {
 //           return binPolygon.getCentroid();
        }
        return null;
    }

    @Nullable
    private GeoPoint getGeoPointFromXYPointR(Point inPoint) {
        Double anchorLatInCompassDegrees = this.getAbnGeodeticAnchorLatitude();
        Double anchorLongInCompassDegrees = this.getAbnGeodeticAnchorLongitude();
        Double anchorOrientationInCompassDegrees = this.getAbnGeodeticAnchorOrientation();
        if (anchorLatInCompassDegrees != null && anchorLongInCompassDegrees != null && anchorOrientationInCompassDegrees != null) {
//            double lat = Math.toRadians(anchorLatInCompassDegrees);
//            double lon = Math.toRadians(anchorLongInCompassDegrees);
//            double rotInCompassRadians = Math.toRadians(anchorOrientationInCompassDegrees);
//            double rotInCartessianDegrees = GeoPoint.getCartesianDegreesFromCompassDegrees(anchorOrientationInCompassDegrees);
//            double rotInCartessianRadians = Math.toRadians(rotInCartessianDegrees);
//            double dx = inPoint.getX();
//            double dy = inPoint.getY();
//            double d = Math.sqrt(dx * dx + dy * dy);
//            double angleInCartesianRadians = Math.atan2(dy, dx);
//            double angleInCartesianDegrees = Math.toDegrees(angleInCartesianRadians);
//            double angleInCompassDegrees = GeoPoint.getCompassDegreesFromCartesianDegrees(angleInCartesianDegrees);
//            double theta = Math.toRadians(angleInCompassDegrees);
//            double yardRotationInPureRadians = Math.toRadians(anchorOrientationInCompassDegrees);
//            double r = 6.371009E8;
//            double f = d / 6.371009E8;
//            double latInCompassRadians = Math.asin(Math.sin(lat) * Math.cos(f) + Math.cos(lat) * Math.sin(f) * Math.cos(theta += yardRotationInPureRadians));
//            double longInCompassRadians = lon + Math.atan2(Math.sin(theta) * Math.sin(f) * Math.cos(lat), Math.cos(f) - Math.sin(lat) * Math.sin(lat));
//            double latInCompassDegrees = Math.toDegrees(latInCompassRadians);
//            double longInCompassDegrees = Math.toDegrees(longInCompassRadians);
//            return new GeoPoint(latInCompassDegrees, longInCompassDegrees, 0.0);
        }
        return null;
    }

    @Nullable
    public GeoPoint getGeoPointFromXYPoint(Point inPoint) {
        if (inPoint != null) {
            GeoPoint geoPoint = this.getGeoPointFromXYPointR(inPoint);
            if (geoPoint == null) {
                AbstractBin parent = this.getAbnParentBin();
                if (parent != null) {
                    return parent.getGeoPointFromXYPoint(inPoint);
                }
            } else {
                return geoPoint;
            }
        }
        return null;
    }

    @Nullable
    public GeoPoint getGeoPoint() {
        Point centroid;
        Geometry binPolygon = this.getAbnPolygon();
//        if (binPolygon != null && (centroid = binPolygon.getCentroid()) != null) {
//            return this.getGeoPointFromXYPoint(centroid);
//        }
        return null;
    }

    @Nullable
    public Double getHeadingInCompassDegrees() {
        int numCoordinates;
        Geometry centerline = this.getAbnCenterline();
//        if (centerline != null && (numCoordinates = centerline.getNumPoints()) >= 2) {
//            Coordinate[] coordinates = centerline.getCoordinates();
//            Coordinate start = coordinates[0];
//            Coordinate end = coordinates[numCoordinates - 1];
//            double dx = end.x - start.x;
//            double dy = end.y - start.y;
//            double cartesianAngleInRadians = Math.atan2(dy, dx);
//            double cartesianAngleInDegrees = Math.toDegrees(cartesianAngleInRadians += Math.toRadians(this.getOrientation()));
//            return GeoPoint.getCompassDegreesFromCartesianDegrees(cartesianAngleInDegrees);
//        }
        return null;
    }

    public void updateZIndexMin(Long inMinTier) {
        this.setAbnZIndexMin(inMinTier);
    }

    public void updateZIndexMax(Long inMaxTier) {
        this.setAbnZIndexMax(inMaxTier);
    }

    public IDomainQuery getThisAndDescendantBinsDq(BinContext inBinContext, boolean inHydratingEntities) {
        return this.getDescendentBinsJoinQueryDq(inBinContext, inHydratingEntities, false);
    }

    public IDomainQuery getAllDescendantBinsDq(BinContext inBinContext, boolean inHydratingEntities) {
        return this.getDescendentBinsJoinQueryDq(inBinContext, inHydratingEntities, true);
    }

    private IDomainQuery getDescendentBinsJoinQueryDq(BinContext inBinContext, boolean inHydratingEntities, boolean inExcludeThisBin) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqField(IBinField.ABN_GKEY);
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        if (inExcludeThisBin) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IBinField.ABN_GKEY, (Object)this.getAbnGkey()));
        }
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        this.addAncestorBinJoin(dq, inHydratingEntities);
        return dq;
    }

    private IDomainQuery getDescendentBinsSubQueryDq(BinContext inBinContext) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqPredicate(PredicateFactory.ne((IMetafieldId)IBinField.ABN_GKEY, (Object)this.getAbnGkey())).addDqPredicate(this.isAncestorBinPredicate());
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        return dq;
    }

    public IDomainQuery addAncestorBinJoin(@NotNull IDomainQuery inDomainQuery, boolean inHydratingEntities) {
        String abnBinAncestorSetAlias = "ban";
        IEntityId banEntityId = EntityIdFactory.valueOf((String)"BinAncestor", (String)abnBinAncestorSetAlias);
        IMetafieldId eaBanAncestorBin = MetafieldIdFactory.getEntityAwareMetafieldId((IEntityId)banEntityId, (IMetafieldId)IBinField.BAN_ANCESTOR_BIN);
        IMetafieldId eaBanLifeCycleState = MetafieldIdFactory.getEntityAwareMetafieldId((IEntityId)banEntityId, (IMetafieldId)IBinField.BAN_LIFE_CYCLE_STATE);
        JoinTypeEnum joinType = inHydratingEntities ? JoinTypeEnum.LEFT_OUTER_JOIN_FETCH : JoinTypeEnum.INNER_JOIN;
        inDomainQuery.addDqJoin(PredicateFactory.createJoin((JoinTypeEnum)joinType, (IMetafieldId)IBinField.ABN_BIN_ANCESTOR_SET, (String)abnBinAncestorSetAlias));
        inDomainQuery.addDqPredicate(PredicateFactory.eq((IMetafieldId)eaBanAncestorBin, (Object)this.getAbnGkey()));
        inDomainQuery.addDqPredicate(PredicateFactory.eq((IMetafieldId)eaBanLifeCycleState, (Object)LifeCycleStateEnum.ACTIVE));
        return inDomainQuery;
    }

    public IPredicate isAncestorBinPredicate() {
        IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"BinAncestor").addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_ANCESTOR_BIN, (Object)this.getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_ANCESTOR_LEVEL, (Object)this.getAbnBinLevel())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_BIN, (Object)OuterQueryMetafieldId.valueOf((IMetafieldId)IBinField.ABN_GKEY)));
        return PredicateFactory.subQueryExists((IDomainQuery)subQuery);
    }

    public static IPredicate buildAncestorBinPredicate(@NotNull List<Serializable> inAncestorBinGkeys, @NotNull IMetafieldId inBinGkeyMetafield) {
        IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"BinAncestor").addDqPredicate(PredicateFactory.in((IMetafieldId)IBinField.BAN_ANCESTOR_BIN, inAncestorBinGkeys)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.BAN_BIN, (Object)OuterQueryMetafieldId.valueOf((IMetafieldId)inBinGkeyMetafield)));
        return PredicateFactory.subQueryExists((IDomainQuery)subQuery);
    }

    private List<AbstractBin> findDescendantBinsFromBinName(BinContext inBinContext, String inBinName, IMetafieldId inNameField) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqPredicate(PredicateFactory.eq((IMetafieldId)inNameField, (Object)inBinName)).addDqPredicate(this.isAncestorBinPredicate());
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        if (inBinContext != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_CONTEXT, (Object)inBinContext.getBcxGkey()));
        }
        Serializable[] results = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        ArrayList<AbstractBin> resultBinsLocal = new ArrayList<AbstractBin>();
        for (Serializable result : results) {
            AbstractBin bin = (AbstractBin)HibernateApi.getInstance().load(AbstractBin.class, result);
            resultBinsLocal.add(bin);
        }
        return resultBinsLocal;
    }

    public List<AbstractBin> findDescendantBinsWNullPolygon(BinType inBinType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBin").addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.ABN_BIN_TYPE, (Object)inBinType.getBtpGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IBinField.ABN_PARENT_BIN, (Object)this.getAbnGkey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId)IBinField.ABN_POLYGON));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    private Double getOrientation() {
        Double orientation = this.getAbnGeodeticAnchorOrientation();
        if (orientation == null) {
            return this.getAbnParentBin().getOrientation();
        }
        return null;
    }

    public String toString() {
        return this.getEntityName() + "[" + this.getPrimaryKey() + ":" + this.getAbnName() + "]";
    }

//    public FieldHandler getFieldHandler() {
//        return this._fieldHandler;
//    }
//
//    public void setFieldHandler(FieldHandler inFieldHandler) {
//        this._fieldHandler = inFieldHandler;
//    }

    private static class BinLevelComparatorDesc
            implements Comparator<AbstractBin> {
        private BinLevelComparatorDesc() {
        }

        @Override
        public int compare(AbstractBin inBin1, AbstractBin inBin2) {
            Long bin1Level = inBin1.getBinLevel();
            Long bin2Level = inBin2.getBinLevel();
            return bin2Level.compareTo(bin1Level);
        }
    }

    private static class BinNameAndTypeComparator
            implements Comparator<AbstractBin> {
        private BinNameAndTypeComparator() {
        }

        @Override
        public int compare(AbstractBin inBin1, AbstractBin inBin2) {
            String abn1String = inBin1.getAbnBinType().getBtpGkey() + inBin1.getAbnName();
            String abn2String = inBin2.getAbnBinType().getBtpGkey() + inBin2.getAbnName();
            return abn1String.compareTo(abn2String);
        }
    }

    private static class PrevBinInfo {
        public Serializable _prevBinTypeGkey;
        public String _prevBinName;

        private PrevBinInfo() {
        }
    }

    private static class Counter {
        public int _index;

        public Counter(int inIndex) {
            this._index = inIndex;
        }
    }
}
