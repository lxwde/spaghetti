package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.BinNameTableDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernatingObjRef;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BinNameTable extends BinNameTableDO implements IBinCopyable {
    
    private static Map<Long, CachedBinNameTable> _cachedBinNameTables = new ConcurrentHashMap<Long, CachedBinNameTable>();
    private static int MAX_CACHED_TABLES = 6;

    public BinNameTable() {
        this.setBntLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBntLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBntLifeCycleState();
    }

    public static BinNameTable findOrCreateBinNameTable(long inTableIndex, String inTableName, AbstractBin inOwningBin) {
        BinNameTable binNameTable = BinNameTable.findBinNameTableFromTableIndex(inTableIndex, inOwningBin);
        boolean createdBinNameTable = false;
        boolean changedName = false;
        if (binNameTable == null) {
            binNameTable = BinNameTable.createBinNameTable(inTableIndex, inTableName, inOwningBin);
            createdBinNameTable = true;
        } else if (inTableName != null) {
            binNameTable.setBntTableName(inTableName);
            changedName = true;
        }
        if (createdBinNameTable || changedName) {
            Roastery.getHibernateApi().save((Object)binNameTable);
            Roastery.getHibernateApi().flush();
        }
        return binNameTable;
    }

    public static BinNameTable createBinNameTable(long inTableIndex, String inTableName, AbstractBin inOwningBin) {
        BinNameTable binNameTable = new BinNameTable();
        binNameTable.setBntOwningBin(inOwningBin);
        binNameTable.setBntTableIndex(inTableIndex);
        binNameTable.setBntTableName(inTableName);
        return binNameTable;
    }

    @Nullable
    public static BinNameTable findBinNameTableFromTableIndex(long inTableIndex, AbstractBin inOwningBin) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinNameTable").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_TABLE_INDEX, (Object)inTableIndex)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_OWNING_BIN, (Object)inOwningBin.getAbnGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (BinNameTable)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);

    }

    @Nullable
    public static BinNameTable findBinNameTableFromUserName(String inTableName, AbstractBin inOwningBin) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinNameTable").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_TABLE_NAME, (Object)inTableName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_OWNING_BIN, (Object)inOwningBin.getAbnGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (BinNameTable) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);

    }

    public static List<BinNameTable> findBinNameTablesForOwningBin(AbstractBin inOwningBin) {
        return BinNameTable.findBinNameTablesForOwningBin(inOwningBin, true);
    }

    public static List<BinNameTable> findBinNameTablesForOwningBin(AbstractBin inOwningBin, boolean inIncludeActiveFilter) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinNameTable").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_OWNING_BIN, (Object)inOwningBin.getAbnGkey()));
        if (inIncludeActiveFilter) {
            dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);

    }

    public static Serializable[] findBinNameTablesWithNoTableName(Serializable[] inOwningBinGkeys) {
//        Junction owningBinReferences = PredicateFactory.disjunction().add(PredicateFactory.in((IMetafieldId)IBinField.BNT_OWNING_BIN, (Object[])inOwningBinGkeys));
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinNameTable").addDqPredicate((IPredicate)owningBinReferences).addDqPredicate(PredicateFactory.isNull((IMetafieldId)IBinField.BNT_TABLE_NAME));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        return Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        return null;
    }

    public static Long findMaxTableIndexForYardModel(Serializable inYardModelGkey) {
        Long maxTableIndex = 0L;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinNameTable").addDqField(IBinField.BNT_TABLE_INDEX).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNT_OWNING_BIN, (Object)inYardModelGkey)).addDqOrdering(Ordering.desc((IMetafieldId) IBinField.BNT_TABLE_INDEX));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult qr = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
        if (qr != null && qr.getTotalResultCount() > 0) {
            maxTableIndex = (Long)qr.getValue(0, IBinField.BNT_TABLE_INDEX);
        }
        return maxTableIndex;
    }

    public static void clearBinNameCache() {
        _cachedBinNameTables.clear();
    }

    public List<BinName> getBinNames() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)this.getBntGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return null;
    }

    public void deleteOldNames() {
        this.deleteAllBinNames();
    }

    @Nullable
    public BinName findBinNameFromLogicalPosition(long inLogicalPosition) {
        CachedBinNameTable cbnt = _cachedBinNameTables.get(this.getBntGkey());
        if (cbnt == null && LifeCycleStateEnum.ACTIVE.equals((Object)this.getLifeCycleState())) {
            if (_cachedBinNameTables.size() > MAX_CACHED_TABLES) {
                CachedBinNameTable oldestCbnt = null;
                for (CachedBinNameTable cbntsEntry : _cachedBinNameTables.values()) {
                    if (oldestCbnt != null && !oldestCbnt.getLastRef().after(cbntsEntry.getLastRef())) continue;
                    oldestCbnt = cbntsEntry;
                }
                if (oldestCbnt != null) {
                    _cachedBinNameTables.remove(oldestCbnt.getTableGkey());
                }
            }
            cbnt = new CachedBinNameTable(this);
            _cachedBinNameTables.put(this.getBntGkey(), cbnt);
        }
        return cbnt != null ? cbnt.getBinNameFromLogicalPosition(inLogicalPosition) : null;
    }

    @Nullable
    public BinName findBinNameFromUserName(String inUserName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)this.getBntGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_USER_NAME, (Object)inUserName));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        return (BinName)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
        return null;
    }

    public void deleteAllBinNames() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)this.getBntGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        Roastery.getHibernateApi().deleteByDomainQuery(dq);
//        HibernateApi.getInstance().flush();
    }

    public List<BinName> findBinNamesInLogicalOrder() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)this.getBntGkey())).addDqOrdering(Ordering.asc((IMetafieldId) IBinField.BNM_LOGICAL_POSITION));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return null;
    }

    @Override
    public void shallowCopy(Class inClass, Serializable inToTopBinGkey, long inTopBinLevel, IBinCopyable inObjToCopyTo) {
 //       BinCopyableUtils.shallowCopy(inClass, this, inToTopBinGkey, inObjToCopyTo, inTopBinLevel);
    }

    @Override
    public List<IMetafieldId> getKeyFields() {
        ArrayList<IMetafieldId> naturalKeyFields = new ArrayList<IMetafieldId>();
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNT_OWNING_BIN, (IMetafieldId) IBinField.ABN_NAME));
        naturalKeyFields.add(IBinField.BNT_TABLE_NAME);
        naturalKeyFields.add(IBinField.BNT_TABLE_INDEX);
        return naturalKeyFields;
    }

    @Override
    public void addOwnedObjectKeysToListInCopyOrder(List<HibernatingObjRef> inOutKeysToCopy, IBinOwnedEntitiesFilter inOwnedEntitiesFilter) {
        inOutKeysToCopy.add(new HibernatingObjRef(BinNameTable.class, this.getBntGkey()));
        if (inOwnedEntitiesFilter.isRequired("BinName")) {
            List<BinName> bns = this.getBinNames();
            for (BinName bn : bns) {
                bn.addOwnedObjectKeysToListInCopyOrder(inOutKeysToCopy, inOwnedEntitiesFilter);
            }
        }
    }

    @Override
    @Nullable
    public IMetafieldId getTopBinField(long inTopBinLevel) {
        AbstractBin owningBin = this.getBntOwningBin();
//        Long owningBinLevel = owningBin.getBinLevel();
//        if (owningBin != null && owningBinLevel != null && owningBinLevel >= inTopBinLevel) {
//            IMetafieldId topBinGkeyField = owningBin.getTopBinField(inTopBinLevel);
//            topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BNT_OWNING_BIN, (IMetafieldId)topBinGkeyField);
//            return topBinGkeyField;
//        }
        return null;
    }

    @Override
    public boolean hasNamingScheme() {
        return false;
    }

    public void removeFromCache(long inBinNameLogicalPosition) {
        CachedBinNameTable cbnt = _cachedBinNameTables.get(this.getBntGkey());
        if (cbnt != null) {
            cbnt.removeBinName(inBinNameLogicalPosition);
        }
    }

    public void preDelete() {
        this.deleteBinNameTableFromCache();
   //     super.preDelete();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        if (inChanges.hasFieldChange(IBinField.BNT_LIFE_CYCLE_STATE) && LifeCycleStateEnum.OBSOLETE.equals(inChanges.getFieldChange(IBinField.BNT_LIFE_CYCLE_STATE).getNewValue())) {
            this.deleteBinNameTableFromCache();
        }
        return super.validateChanges(inChanges);
    }

    private void deleteBinNameTableFromCache() {
        CachedBinNameTable cbnt = _cachedBinNameTables.get(this.getBntGkey());
        if (cbnt != null) {
            _cachedBinNameTables.remove(cbnt.getTableGkey());
        }
    }

    private static class CachedBinNameTable {
        private Date _lastRef = new Date();
        private long _tableGkey;
        private HashMap<Long, Long> _logicalPosMap = new HashMap();

        CachedBinNameTable(BinNameTable inBinNameTable) {
            this._tableGkey = inBinNameTable.getBntGkey();
            for (BinName binName : inBinNameTable.getBinNames()) {
                if (!LifeCycleStateEnum.ACTIVE.equals((Object)binName.getLifeCycleState())) continue;
                this._logicalPosMap.put(binName.getBnmLogicalPosition(), binName.getBnmGkey());
            }
        }

        @Nullable
        BinName getBinNameFromLogicalPosition(long inLogicalPosition) {
            BinName result = null;
            this._lastRef = new Date();
//            if (this._logicalPosMap.containsKey(inLogicalPosition)) {
//                long bnGkey = this._logicalPosMap.get(inLogicalPosition);
//                result = (BinName)HibernateApi.getInstance().load(BinName.class, (Serializable)Long.valueOf(bnGkey));
//            } else {
//                BinNameTable binNameTable = (BinNameTable)HibernateApi.getInstance().load(BinNameTable.class, (Serializable)Long.valueOf(this._tableGkey));
//                BinName binName = BinName.findBinNameFromTableAndPosition(binNameTable, inLogicalPosition);
//                if (binName != null) {
//                    this._logicalPosMap.put(inLogicalPosition, binName.getBnmGkey());
//                    result = binName;
//                }
//            }
           return result;
        }

        void removeBinName(Long inLogicalPosition) {
            if (this._logicalPosMap.containsKey(inLogicalPosition)) {
                this._logicalPosMap.remove(inLogicalPosition);
            }
        }

        public Date getLastRef() {
            return this._lastRef;
        }

        public long getTableGkey() {
            return this._tableGkey;
        }
    }
}



