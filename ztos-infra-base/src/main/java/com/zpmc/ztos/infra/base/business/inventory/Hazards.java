package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.HazardsDO;
import com.zpmc.ztos.infra.base.business.enums.inventory.HazardsNumberTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.GoodsBase;
import com.zpmc.ztos.infra.base.business.model.Placard;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class Hazards extends HazardsDO {
    private final SeqComparator _sortByDescSeq = new SeqComparator();
    private static final Logger LOGGER = Logger.getLogger((String) Hazards.class.getName());

    public Hazards() {
        this.ensureItems();
    }

    public static Hazards createHazardsEntity() {
        Hazards hazards = new Hazards();
        HibernateApi.getInstance().save((Object)hazards);
        return hazards;
    }

    public void attachHazardToEntity(String inEntityName, Long inEntityGkey) {
        if (this.getHzrdOwnerEntityGkey() != null) {
            if (inEntityGkey.equals(this.getHzrdOwnerEntityGkey()) && inEntityName.equals(this.getHzrdOwnerEntityName())) {
                return;
            }
            throw BizFailure.create((String)("Attempt to attach Hazards to an entity of class <" + inEntityName + "> with gkey <" + inEntityGkey + "> that is already attached to an entity of class <" + this.getHzrdOwnerEntityName() + "> with gkey <" + this.getHzrdOwnerEntityGkey() + ">"));
        }
        this.setHzrdOwnerEntityName(inEntityName);
        this.setHzrdOwnerEntityGkey(inEntityGkey);
    }

    public void replaceParent(String inEntityName, Long inEntityGkey) {
        this.setHzrdOwnerEntityName(inEntityName);
        this.setHzrdOwnerEntityGkey(inEntityGkey);
    }

    public HazardItem addHazardItem(ImdgClassEnum inImdgClass, String inUnNbr) {
        return this.addHazardItem(inImdgClass, inUnNbr, HazardsNumberTypeEnum.UN);
    }

    public HazardItem addHazardItem(String inImdgClassId, String inUnNbr) throws BizViolation {
        return this.addHazardItem(inImdgClassId, inUnNbr, HazardsNumberTypeEnum.UN);
    }

    public HazardItem addHazardItem(String inImdgClassId, String inUnNbr, HazardsNumberTypeEnum inHazNbrType) throws BizViolation {
        ImdgClassEnum imdgClass = ImdgClassEnum.getEnum(inImdgClassId);
        if (imdgClass == null) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.IMDG__UNKNOWN_IMDG_CLASS, null, (Object)inImdgClassId);
        }
        return this.addHazardItem(imdgClass, inUnNbr, inHazNbrType);
    }

    public HazardItem addHazardItem(ImdgClassEnum inImdgClass, String inUnNbr, HazardsNumberTypeEnum inHazNbrType) {
        HazardItem item = new HazardItem(this, inImdgClass, inUnNbr, inHazNbrType);
        List<HazardItem> items = this.ensureItems();
        items.add(item);
        if (this.getHzrdGkey() != null) {
            HibernateApi.getInstance().save((Object)item);
        }
        LOGGER.info((Object)("addHazardItem: added a hazard for <" + (Object)((Object)inImdgClass) + ">,<" + inUnNbr + ">,<" + (Object)((Object)inHazNbrType) + ">"));
        return item;
    }

    public HazardItem addHazardItem(HazardItem inItem) {
        List<HazardItem> items = this.ensureItems();
        items.add(inItem);
        inItem.updateParentHazard(this);
        HibernateApi.getInstance().saveOrUpdate((Object)inItem);
        return inItem;
    }

    public HazardItem findHazardItem(String inUnNumber) {
        Iterator<HazardItem> hazardItemIterator = this.getHazardItemsIterator();
        while (hazardItemIterator.hasNext()) {
            HazardItem testItem = hazardItemIterator.next();
            if (!StringUtils.equals((String)inUnNumber, (String)testItem.getHzrdiUNnum())) continue;
            return testItem;
        }
        return null;
    }

    public HazardItem findHazardItem(ImdgClassEnum inImdgClass, String inUnNumber) {
        Iterator<HazardItem> hazardItemIterator = this.getHazardItemsIterator();
        while (hazardItemIterator.hasNext()) {
            HazardItem testItem = hazardItemIterator.next();
            if (!StringUtils.equals((String)inUnNumber, (String)testItem.getHzrdiUNnum()) || inImdgClass != testItem.getHzrdiImdgClass()) continue;
            return testItem;
        }
        return null;
    }

    @Nullable
    public HazardItem findHazardItemIfUnique(ImdgClassEnum inImdgClass) {
        int count = 0;
        HazardItem item = null;
        Iterator<HazardItem> hazardItemIterator = this.getHazardItemsIterator();
        while (hazardItemIterator.hasNext()) {
            HazardItem testItem = hazardItemIterator.next();
            if (!testItem.getHzrdiImdgClass().equals((Object)inImdgClass)) continue;
            ++count;
            item = testItem;
        }
        return count == 1 ? item : null;
    }

    @Nullable
    public HazardItem findHazardItem(ImdgClassEnum inImdgClass) {
        Iterator<HazardItem> hazardItemIterator = this.getHazardItemsIterator();
        while (hazardItemIterator.hasNext()) {
            HazardItem testItem = hazardItemIterator.next();
            if (!testItem.getHzrdiImdgClass().equals((Object)inImdgClass)) continue;
            return testItem;
        }
        return null;
    }

    public void deleteHazardItem(HazardItem inItem) {
        List<HazardItem> items = this.ensureItems();
        items.remove(inItem);
    }

    public void deleteAllHazardItems() {
        List items = this.getHzrdItems();
        if (items != null) {
            items.clear();
        }
    }

    public Iterator getHazardItemsIteratorOrderedBySeverity() {
        this.ensureItems();
        List items = this.getHzrdItems();
        Collections.sort(items);
        return items.iterator();
    }

    public Iterator<HazardItem> getHazardItemsIterator() {
        List<HazardItem> items = this.ensureItems();
        return items.iterator();
    }

    protected List<HazardItem> ensureItems() {
        ArrayList items = (ArrayList) this.getHzrdItems();
        if (items == null) {
            items = new ArrayList();
            this.setHzrdItems(items);
        }
        return items;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int hazCount = 0;
        Iterator<HazardItem> hazardItemIterator = this.getHazardItemsIterator();
        while (hazardItemIterator.hasNext()) {
            HazardItem testItem = hazardItemIterator.next();
            if (hazCount++ > 0) {
                sb.append(", ");
            }
            sb.append(testItem.getHzrdiImdgClass().getKey());
            String hzrdiUNnum = testItem.getHzrdiUNnum();
            if (hzrdiUNnum == null) continue;
            sb.append('/');
            sb.append(hzrdiUNnum);
        }
        return sb.toString();
    }

    public String getWorstHazardClass() {
        String worstClassDesc = null;
        Iterator iterator = this.getHazardItemsIteratorOrderedBySeverity();
        if (iterator.hasNext()) {
            HazardItem worstHzdIt = (HazardItem)iterator.next();
            worstClassDesc = worstHzdIt.getHzrdiImdgClass().getName();
        }
        return worstClassDesc;
    }

    public IValueHolder[] getHazardsHazItemsVao() {
        List<HazardItem> items = this.ensureItems();
        IValueHolder[] result = new IValueHolder[items.size()];
        int i = 0;
        for (HazardItem hazardItem : items) {
            ValueObject vao = hazardItem.getValueObject();
            if (hazardItem.getHzrdiFireCode() != null) {
                vao.setFieldIfNotPresent(IInventoryField.HZRDI_FIRE_CODE, (Object)hazardItem.getHzrdiFireCode().getFirecodeGkey());
            }
            ArrayList<ValueObject> placardvaolst = new ArrayList<ValueObject>();
            if (hazardItem.getHzrdiPlacardSet() != null) {
                for (Object placard : hazardItem.getHzrdiPlacardSet()) {
                    ValueObject placardvao = ((HazardItemPlacard)placard).getValueObject();
                    placardvao.setFieldIfNotPresent(IInventoryField.HZRDIP_PLACARD, (Object)((HazardItemPlacard)placard).getHzrdipPlacard().getPlacardText());
                    placardvao.setFieldIfNotPresent(IInventoryField.PLACARD_GKEY, (Object)((HazardItemPlacard)placard).getHzrdipPlacard().getPlacardGkey());
                    placardvaolst.add(placardvao);
                }
                vao.setFieldIfNotPresent(IInventoryBizMetafield.HZRDI_PLACARD_ARRAY, (Object)hazardItem.getHzrdiPlacardArray());
                vao.setFieldIfNotPresent(IInventoryBizMetafield.UNIT_HAZARD_ITEM_PLACARD_TABLE_KEY, placardvaolst);
            }
            result[i++] = vao;
        }
        return result;
    }

    public void setHazardsHazItemsVao(IValueHolder[] inHzrdVaos) {
        List existingHazardItems = this.getHzrdItems();
        if (existingHazardItems != null) {
            existingHazardItems.clear();
        }
        for (int i = 0; i < inHzrdVaos.length; ++i) {
            IValueHolder hzrdVao = inHzrdVaos[i];
            this.addHazardItem(hzrdVao);
        }
    }

    public void addHazardsHazItemsVao(IValueHolder[] inHzrdVaos) {
        for (int i = 0; i < inHzrdVaos.length; ++i) {
            String nbr;
            boolean hazardExists;
            IValueHolder hzrdVao = inHzrdVaos[i];
            if (hzrdVao == null) continue;
            ImdgClassEnum imdg = (ImdgClassEnum)((Object)hzrdVao.getFieldValue(IInventoryField.HZRDI_IMDG_CLASS));
            boolean bl = hazardExists = this.findHazardItem(imdg, nbr = (String)hzrdVao.getFieldValue(IInventoryField.HZRDI_U_NNUM)) != null;
            if (hazardExists) continue;
            this.addHazardItem(hzrdVao);
        }
    }

    public HazardItem addHazardItem(IValueHolder inHzrdVao) {
        if (inHzrdVao == null) {
            return null;
        }
        ImdgClassEnum imdg = (ImdgClassEnum)((Object)inHzrdVao.getFieldValue(IInventoryField.HZRDI_IMDG_CLASS));
        String nbr = (String)inHzrdVao.getFieldValue(IInventoryField.HZRDI_U_NNUM);
        HazardItem hzrditem = this.addHazardItem(imdg, nbr);
        hzrditem.update(inHzrdVao);
        return hzrditem;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        Hazards hazards;
        IEntity entity = inAuditEvent.getEntity();
        if (entity instanceof Hazards && "GoodsBase".equals((hazards = (Hazards)entity).getHzrdOwnerEntityName())) {
            return inAuditEvent;
        }
        return null;
    }

    protected Object clone() throws CloneNotSupportedException {
        Hazards copyHzrd = (Hazards)super.clone();
        copyHzrd.setPrimaryKey(null);
        List origHzrdiSet = this.getHzrdItems();
        if (origHzrdiSet != null) {
            ArrayList<HazardItem> copyHzrdiSet = new ArrayList<HazardItem>();
            for (Object hzrdi : origHzrdiSet) {
                HazardItem cloneItem = ((HazardItem)hzrdi).deepClone();
                cloneItem.updateParentHazard(copyHzrd);
                copyHzrdiSet.add(cloneItem);
            }
            copyHzrd.setHzrdItems(copyHzrdiSet);
        }
        return copyHzrd;
    }

    @Nullable
    public static Hazards cloneHazards(Hazards inHazards) {
        return inHazards != null ? inHazards.cloneHazards() : null;
    }

    public Hazards cloneHazards() {
        Hazards hazards = Hazards.createHazardsEntity();
        Iterator<HazardItem> itr = this.getHazardItemsIterator();
        while (itr.hasNext()) {
            HazardItem tranHazardItem = itr.next();
            HazardItem clonedItem = hazards.addHazardItem(tranHazardItem.getHzrdiImdgCode(), tranHazardItem.getHzrdiUNnum());
            clonedItem.setHzrdiDcLgRef(tranHazardItem.getHzrdiDcLgRef());
            clonedItem.setHzrdiDeckRestrictions(tranHazardItem.getHzrdiDeckRestrictions());
            clonedItem.setHzrdiEmergencyTelephone(tranHazardItem.getHzrdiEmergencyTelephone());
            clonedItem.setHzrdiEMSNumber(tranHazardItem.getHzrdiEMSNumber());
            clonedItem.setHzrdiERGNumber(tranHazardItem.getHzrdiERGNumber());
            clonedItem.setHzrdiExplosiveClass(tranHazardItem.getHzrdiExplosiveClass());
            clonedItem.setHzrdiFireCode(tranHazardItem.getHzrdiFireCode());
            clonedItem.setHzrdiFlashPoint(tranHazardItem.getHzrdiFlashPoint());
            clonedItem.setHzrdiHazIdUpper(tranHazardItem.getHzrdiHazIdUpper());
            clonedItem.setHzrdiImdgClass(tranHazardItem.getHzrdiImdgClass());
            clonedItem.setHzrdiInhalationZone(tranHazardItem.getHzrdiInhalationZone());
            clonedItem.setHzrdiLtdQty(tranHazardItem.getHzrdiLtdQty());
            clonedItem.setHzrdiMarinePollutants(tranHazardItem.getHzrdiMarinePollutants());
            clonedItem.setHzrdiMFAG(tranHazardItem.getHzrdiMFAG());
            clonedItem.setHzrdiPackageType(tranHazardItem.getHzrdiPackageType());
            clonedItem.setHzrdiPackingGroup(tranHazardItem.getHzrdiPackingGroup());
            clonedItem.setHzrdiPageNumber(tranHazardItem.getHzrdiPageNumber());
            clonedItem.setHzrdiProperName(tranHazardItem.getHzrdiProperName());
            clonedItem.setHzrdiQuantity(tranHazardItem.getHzrdiQuantity());
            clonedItem.setHzrdiSecondaryIMO1(tranHazardItem.getHzrdiSecondaryIMO1());
            clonedItem.setHzrdiSecondaryIMO2(tranHazardItem.getHzrdiSecondaryIMO2());
            clonedItem.setHzrdiSubstanceLower(tranHazardItem.getHzrdiSubstanceLower());
            clonedItem.setHzrdiTechName(tranHazardItem.getHzrdiTechName());
            clonedItem.setHzrdiWeight(tranHazardItem.getHzrdiWeight());
            clonedItem.setHzrdiMoveMethod(tranHazardItem.getHzrdiMoveMethod());
            clonedItem.setHzrdiNbrType(tranHazardItem.getHzrdiNbrType());
            clonedItem.setHzrdiPlannerRef(tranHazardItem.getHzrdiPlannerRef());
            clonedItem.setHzrdiNotes(tranHazardItem.getHzrdiNotes());
            clonedItem.setHzrdiSeq(tranHazardItem.getHzrdiSeq());
            Roastery.getHibernateApi().save((Object)clonedItem);
            if (tranHazardItem.getHzrdiPlacardSet() == null) continue;
            LinkedHashSet<HazardItemPlacard> clonedPlacardSet = new LinkedHashSet<HazardItemPlacard>();
            clonedItem.setHzrdiPlacardSet(clonedPlacardSet);
            for (Object tranHazardItemPlacard : tranHazardItem.getHzrdiPlacardSet()) {
                HazardItemPlacard clonedPlacard = HazardItemPlacard.createHazardItemPlacardEntity(clonedItem);
                clonedPlacard.setHzrdipPlacard(((HazardItemPlacard)tranHazardItemPlacard).getHzrdipPlacard());
                clonedPlacard.setHzrdipDescription(((HazardItemPlacard)tranHazardItemPlacard).getHzrdipDescription());
                clonedPlacardSet.add(clonedPlacard);
            }
        }
        return hazards;
    }

    public Hazards deepClone() {
        try {
            return (Hazards)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    private GoodsBase getOwnerGoods() {
        GoodsBase ownerGoods = null;
        if ("GoodsBase".equals(this.getHzrdOwnerEntityName())) {
            Long goodsGkey = this.getHzrdOwnerEntityGkey();
            ownerGoods = (GoodsBase)HibernateApi.getInstance().get(GoodsBase.class, (Serializable)goodsGkey);
        }
        return ownerGoods;
    }

    public void updateOwnerDenormalizedFields() {
        GoodsBase ownerGoods = this.getOwnerGoods();
        if (ownerGoods != null) {
            ownerGoods.calculateDenormalizedHazardFields();
            ownerGoods.calculateDenormalizedHazardUNFields();
        }
    }

    public void onDelete() {
        List items = this.getHzrdItems();
        if (items != null && !items.isEmpty()) {
            throw BizFailure.create((String)"Please delete each Hazard item before deleting");
        }
        GoodsBase ownerGoods = this.getOwnerGoods();
        if (ownerGoods != null) {
            ownerGoods.clearHazardsRef();
            ownerGoods.calculateDenormalizedHazardFields();
            ownerGoods.calculateDenormalizedHazardUNFields();
        }
    }

    protected void computeWorstFireCode() {
        List items = this.getHzrdItems();
        HazardFireCode worstFireCode = null;
        HazardItem worstItem = null;
        for (Object item : items) {
            HazardFireCode itemFireCode = ((HazardItem)item).getHzrdiFireCode();
            if (itemFireCode != null) {
                String fireCodeClass = itemFireCode.getFirecodeFireCodeClass();
                if (worstFireCode == null) {
                    worstFireCode = itemFireCode;
                    worstItem = ((HazardItem)item);
                } else if (worstFireCode.getFirecodeFireCodeClass().compareToIgnoreCase(fireCodeClass) < 0) {
                    worstFireCode = itemFireCode;
                    worstItem = ((HazardItem)item);
                } else if (worstFireCode.getFirecodeFireCodeClass().compareToIgnoreCase(fireCodeClass) == 0) {
                    Double worstItemWeight;
                    Double itemWeight = ((HazardItem)item).getHzrdiWeight() == null ? new Double(0.0) : ((HazardItem)item).getHzrdiWeight();
                    Double d = worstItemWeight = worstItem.getHzrdiWeight() == null ? new Double(0.0) : worstItem.getHzrdiWeight();
                    if (itemWeight > worstItemWeight) {
                        worstFireCode = itemFireCode;
                        worstItem = ((HazardItem)item);
                    }
                }
            }
     //       this.setHzrdWorstFireCode(worstFireCode);
        }
    }

    public static void recordDetailedTracking(Unit inServiceable, Hazards inOldHazards, Hazards inNewHazards, FieldChanges inOutEdiTracedChanges, String inNotes) {
        block11: {
            HazardItem oldItem;
            block12: {
                block10: {
                    if (inServiceable == null || inServiceable.getPrimaryKey() == null) {
                        return;
                    }
                    if (inOldHazards != null || inNewHazards == null) break block10;
                    Iterator<HazardItem> it = inNewHazards.getHazardItemsIterator();
                    while (it.hasNext()) {
                        HazardItem item = it.next();
                        item.recordInsertEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
                    }
                    break block11;
                }
                if (inOldHazards == null || inNewHazards != null) break block12;
                Iterator<HazardItem> it = inOldHazards.getHazardItemsIterator();
                while (it.hasNext()) {
                    HazardItem item = it.next();
                    item.recordDeleteEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
                }
                break block11;
            }
            if (inOldHazards == null) break block11;
            if (inOldHazards == inNewHazards) {
                LOGGER.error((Object)("Hazard Tracking skipped tracking for " + inServiceable.getEntityName() + " because the Old and New Hazards were same!"));
                LOGGER.error((Object)"This is an anti-pattern. Please fix it.");
                return;
            }
            Iterator<HazardItem> itOld = inOldHazards.getHazardItemsIterator();
            while (itOld.hasNext()) {
                HazardItem oldItem2 = itOld.next();
                HazardItem newItem = inNewHazards.findHazardItem(oldItem2.getHzrdiImdgClass());
                if (newItem != null) continue;
                oldItem2.recordDeleteEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
                itOld.remove();
            }
            ArrayList<HazardItem> remainingNewItems = new ArrayList<HazardItem>();
            Iterator<HazardItem> itNew = inNewHazards.getHazardItemsIterator();
            while (itNew.hasNext()) {
                HazardItem newItem = itNew.next();
                oldItem = inOldHazards.findHazardItem(newItem.getHzrdiImdgClass());
                if (oldItem == null) {
                    newItem.recordInsertEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
                    continue;
                }
                remainingNewItems.add(newItem);
            }
            Iterator<HazardItem> itRamainingOld = inOldHazards.getHazardItemsIterator();
            while (itRamainingOld.hasNext()) {
                HazardItem uniqueNewItem;
                oldItem = itRamainingOld.next();
                HazardItem uniqueOldItem = inOldHazards.findHazardItemIfUnique(oldItem.getHzrdiImdgClass());
                if (uniqueOldItem == null || (uniqueNewItem = inNewHazards.findHazardItemIfUnique(oldItem.getHzrdiImdgClass())) == null) continue;
                uniqueNewItem.recordUpdateEvent((IServiceable) inServiceable, uniqueOldItem, inOutEdiTracedChanges, inNotes);
                itRamainingOld.remove();
                remainingNewItems.remove(uniqueNewItem);
            }
            Iterator<HazardItem> itReplacedOld = inOldHazards.getHazardItemsIterator();
            while (itReplacedOld.hasNext()) {
                HazardItem oldItem3 = itReplacedOld.next();
                oldItem3.recordDeleteEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
            }
            for (HazardItem newItem : remainingNewItems) {
                newItem.recordInsertEvent((IServiceable) inServiceable, inOutEdiTracedChanges, inNotes);
            }
        }
    }

    public boolean isIMDGClassUnknown() {
        Iterator<HazardItem> itr = this.getHazardItemsIterator();
        while (itr.hasNext()) {
            HazardItem tranHzrditem = itr.next();
            if (ImdgClassEnum.IMDG_X.equals((Object)tranHzrditem.getHzrdiImdgClass())) continue;
            return false;
        }
        return true;
    }

    @Nullable
    public String getHzrdItemImdgCode() {
        List<HazardItem> hazardItemList = this.ensureItems();
        if (hazardItemList.isEmpty()) {
            return null;
        }
        StringBuilder ids = new StringBuilder();
        for (HazardItem hazardItem : hazardItemList) {
            ImdgClassEnum imdgCode = hazardItem.getHzrdiImdgCode();
            if (imdgCode == null) continue;
            String message = this.getTranslator().getMessage(imdgCode.getDescriptionPropertyKey());
            ids.append(message);
            ids.append(';');
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    private IMessageTranslator getTranslator() {
        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
        IMessageTranslator translator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
        return translator;
    }

    private Set<Placard> getRequiredPlacards() {
        HashSet<Placard> requiredPlacards = new HashSet<Placard>();
        if (this.getHzrdItems() != null) {
            for (Object hzrdi : this.getHzrdItems()) {
                requiredPlacards.addAll(((HazardItem)hzrdi).getRequiredPlacards());
            }
        }
        return requiredPlacards;
    }

    private Set<String> getRequiredPlacardIds() {
        HashSet<String> requiredPlacardIds = new HashSet<String>();
        for (Placard placard : this.getRequiredPlacards()) {
            requiredPlacardIds.add(placard.getPlacardText());
        }
        return requiredPlacardIds;
    }

    private Set<Placard> getAllPlacards() {
        HashSet<Placard> allPlacards = new HashSet<Placard>();
        if (this.getHzrdItems() != null) {
            for (Object hzrdi : this.getHzrdItems()) {
                allPlacards.addAll(((HazardItem)hzrdi).getAllPlacards());
            }
        }
        return allPlacards;
    }

    public Set<String> getAllPlacardIds() {
        HashSet<String> allPlacardIds = new HashSet<String>();
        for (Placard placard : this.getAllPlacards()) {
            allPlacardIds.add(placard.getPlacardText());
        }
        return allPlacardIds;
    }

    public static Set<String> getPlacardIdsNotPermitted(Hazards inHazards, Collection<Placard> inObservedPlacards) {
        HashSet permittedPlacardIds;
        HashSet<String> notPermitted = new HashSet<String>();
        HashSet hashSet = permittedPlacardIds = inHazards != null ? (HashSet) inHazards.getAllPlacardIds() : new HashSet();
        if (inObservedPlacards != null) {
            for (Placard observedPlacard : inObservedPlacards) {
                String observedPlacardId = observedPlacard.getPlacardText();
                if (permittedPlacardIds.contains(observedPlacardId)) continue;
                notPermitted.add(observedPlacardId);
            }
        }
        return notPermitted;
    }

    public static Set<String> getPlacardIdsMissing(Hazards inHazards, Collection<Placard> inObservedPlacards) {
        HashSet<String> missing = new HashSet<String>();
        if (inHazards != null) {
            HashSet<String> observedPlacardIds = new HashSet<String>();
            if (inObservedPlacards != null) {
                for (Placard observedPlacard : inObservedPlacards) {
                    observedPlacardIds.add(observedPlacard.getPlacardText());
                }
            }
            for (String requiredPlacardId : inHazards.getRequiredPlacardIds()) {
                if (observedPlacardIds.contains(requiredPlacardId)) continue;
                missing.add(requiredPlacardId);
            }
        }
        return missing;
    }

    public Class getArchiveClass() {
      //  return ArchiveHazards.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    public static Hazards hydrate(Serializable inPrimaryKey) {
        return (Hazards)HibernateApi.getInstance().load(Hazards.class, inPrimaryKey);
    }

    public boolean seqNeedsInitialization() {
        HazardItem lastItem = this.getLastItem();
        return lastItem != null && lastItem.getHzrdiSeq() == null;
    }

    @Nullable
    public HazardItem getLastItem() {
        List<HazardItem> items = this.ensureItems();
        ArrayList<HazardItem> itemsList = new ArrayList<HazardItem>(items);
        Collections.sort(itemsList, this._sortByDescSeq);
        return itemsList.isEmpty() ? null : (HazardItem)itemsList.get(0);
    }

    public void initializeSeqAllItems() {
        List<HazardItem> items = this.ensureItems();
        Collections.sort(items, this._sortByDescSeq);
        for (int i = 1; i <= items.size(); ++i) {
            HazardItem item = items.get(i - 1);
            if (item.getHzrdiSeq() != null) continue;
            item.setHzrdiSeq(Long.valueOf(i));
        }
    }

    @Nullable
    public UnitYardVisit findUyvFromHazards() {
        UnitYardVisit uyv = null;
        Unit unit = this.findUnitFromHazards();
        if (unit != null) {
            UnitFacilityVisit ufv = unit.getUnitActiveUfvNowActive();
            if (ufv != null) {
                try {
                    Yard yard = ufv.getUfvFacility().getActiveYard();
                    uyv = ufv.getUyvForYard(yard);
                }
                catch (BizViolation inBizViolation) {
                    LOGGER.error((Object)"No active yard found!", (Throwable)inBizViolation);
                }
            } else {
                uyv = Unit.getUyvForUnit(ContextHelper.getThreadComplexKey(), ContextHelper.getThreadFacilityKey(), ContextHelper.getThreadYardKey(), unit);
            }
        }
        return uyv;
    }

    @Nullable
    public UnitYardVisit findUyvFromHazardsForYard(Serializable inComplexGkey, Serializable inFacilityGKey, Serializable inYardGkey) {
        UnitYardVisit uyv = null;
        Unit unit = this.findUnitFromHazards();
        if (unit != null) {
            uyv = Unit.getUyvForUnit(inComplexGkey, inFacilityGKey, inYardGkey, unit);
        }
        return uyv;
    }

    private Unit findUnitFromHazards() {
        GoodsBase gds;
        Long gkey;
        Unit result = null;
        if ("GoodsBase".equals(this.getHzrdOwnerEntityName()) && (gkey = this.getHzrdOwnerEntityGkey()) != null && (gds = (GoodsBase)HibernateApi.getInstance().get(GoodsBase.class, (Serializable)gkey)) != null) {
            result = gds.getGdsUnit();
        }
        return result;
    }

    private static class SeqComparator
            implements Comparator<HazardItem> {
        private SeqComparator() {
        }

        @Override
        public int compare(HazardItem inItem1, HazardItem inItem2) {
            if (inItem2.getHzrdiSeq() == null) {
                return -1;
            }
            if (inItem1.getHzrdiSeq() == null) {
                return 1;
            }
            if (inItem1.getHzrdiSeq().equals(inItem2.getHzrdiSeq())) {
                return 0;
            }
            if (inItem1.getHzrdiSeq() < inItem2.getHzrdiSeq()) {
                return 1;
            }
            return -1;
        }
    }

}
