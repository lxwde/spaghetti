package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.HazardItemDO;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.HazardsNumberTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Placard;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class HazardItem extends HazardItemDO {
    private UnitYardVisit _uyvForXpm;
    private static final Logger LOGGER = Logger.getLogger(HazardItem.class);

    public HazardItem() {
        this.setHzrdiLtdQty(Boolean.FALSE);
        this.setHzrdiMarinePollutants(Boolean.FALSE);
    }

    @Nullable
    public String getDescription() {
        String result = null;
        IMessageTranslator messageTranslator = ArgoUtils.getUserMessageTranslator();
        if (messageTranslator == null) {
            messageTranslator = ArgoUtils.getEnglishMessageTranslator();
        }
        ImdgClassEnum imdgClass = this.getHzrdiImdgClass();
        if (messageTranslator != null && imdgClass != null) {
            result = messageTranslator.getMessage(imdgClass.getDescriptionPropertyKey());
        }
        return result;
    }

    public Serializable[] getHzrdiPlacardArray() {
        Collection<HazardItemPlacard> items = this.ensureItems();
        Iterator<HazardItemPlacard> iterator = items.iterator();
        Serializable[] result = new Serializable[items.size()];
        int i = 0;
        while (iterator.hasNext()) {
            HazardItemPlacard hazardItemPlacard = iterator.next();
            Placard placard = hazardItemPlacard.getHzrdipPlacard();
            result[i++] = placard.getPlacardGkey();
        }
        return result;
    }

    public String getHzrdiPlacardsString() {
        StringBuffer stringBuffer = new StringBuffer();
        Collection<HazardItemPlacard> items = this.ensureItems();
        Iterator<HazardItemPlacard> iterator = items.iterator();
        boolean i = false;
        while (iterator.hasNext()) {
            HazardItemPlacard hazardItemPlacard = iterator.next();
            Placard placard = hazardItemPlacard.getHzrdipPlacard();
            stringBuffer.append(placard.getPlacardText());
            if (!iterator.hasNext()) continue;
            stringBuffer.append(", ");
        }
        return stringBuffer.toString();
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (inFieldId.equals((Object)IInventoryBizMetafield.HZRDI_PLACARD_ARRAY)) {
            Serializable[] gKeys = (Serializable[])inFieldValue;
            for (int i = 0; i < gKeys.length; ++i) {
                Placard placard = (Placard)HibernateApi.getInstance().load(Placard.class, gKeys[i]);
                if (placard == null) continue;
                HazardItemPlacard hazardItemPlacard = new HazardItemPlacard();
                hazardItemPlacard.setFieldValue(IInventoryField.HZRDIP_PLACARD, gKeys[i]);
                this.addPlacard(hazardItemPlacard);
            }
        } else {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    public HazardItemPlacard addPlacard(HazardItemPlacard inPlacard) {
        Collection<HazardItemPlacard> items = this.ensureItems();
        inPlacard.setHzrdipHazardItem(this);
        items.add(inPlacard);
        return inPlacard;
    }

    private List<Placard> getPlacardsForRequirement(boolean inIncludeOptional) {
        ArrayList<Placard> placards;
        block5: {
            placards = new ArrayList<Placard>();
            if (this.getHzrdiPlacardSet() == null) break block5;
            HazardousGoods hzgds = HazardousGoods.findHazardousGoods(this.getHzrdiUNnum());
            if (inIncludeOptional || hzgds == null) {
                for (Object hzip : this.getHzrdiPlacardSet()) {
                    placards.add(((HazardItemPlacard)hzip).getHzrdipPlacard());
                }
            } else {
                HashSet<Placard> refReqPlacards = new HashSet<Placard>();
                HashSet<Placard> refAllPlacards = new HashSet<Placard>();
                refAllPlacards.add(hzgds.getHzgoodsPlacard());
                if (!hzgds.getHzgoodsIsPlacardOptional().booleanValue()) {
                    refReqPlacards.add(hzgds.getHzgoodsPlacard());
                }
//                refAllPlacards.addAll(SubsidiaryRisk.getPlacards(SubsidiaryRisk.findSubsidiaryRisk(this.getHzrdiUNnum()), true));
//                refReqPlacards.addAll(SubsidiaryRisk.getPlacards(SubsidiaryRisk.findSubsidiaryRisk(this.getHzrdiUNnum()), false));
                for (Object hzip : this.getHzrdiPlacardSet()) {
                    if (!refReqPlacards.contains(((HazardItemPlacard)hzip).getHzrdipPlacard()) && refAllPlacards.contains(((HazardItemPlacard)hzip).getHzrdipPlacard())) continue;
                    placards.add(((HazardItemPlacard)hzip).getHzrdipPlacard());
                }
            }
        }
        return placards;
    }

    public List<Placard> getRequiredPlacards() {
        return this.getPlacardsForRequirement(false);
    }

    public List<Placard> getAllPlacards() {
        return this.getPlacardsForRequirement(true);
    }

    public static HazardItem createHazardItemEntity(Hazards inOwnerHazards, ImdgClassEnum inImdgClass, String inUnNbr) {
        return HazardItem.createHazardItemEntity(inOwnerHazards, inImdgClass, inUnNbr, HazardsNumberTypeEnum.UN);
    }

    public static HazardItem createHazardItemEntity(Hazards inOwnerHazards, ImdgClassEnum inImdgClass, String inUnNbr, HazardsNumberTypeEnum inUnNbrType) {
        return inOwnerHazards.addHazardItem(inImdgClass, inUnNbr, inUnNbrType);
    }

    protected HazardItem(Hazards inOwnerHazards, ImdgClassEnum inImdgClass, String inUnNbr) {
        this();
        this.setHzrdiHazards(inOwnerHazards);
        this.setHzrdiImdgClass(inImdgClass);
        this.setHzrdiUNnum(inUnNbr);
    }

    protected HazardItem(Hazards inOwnerHazards, ImdgClassEnum inImdgClass, String inUnNbr, HazardsNumberTypeEnum inHazNbrType) {
        this();
        this.setHzrdiHazards(inOwnerHazards);
        this.setHzrdiImdgClass(inImdgClass);
        this.setHzrdiUNnum(inUnNbr);
        this.setHzrdiNbrType(inHazNbrType);
    }

    @Override
    protected void setHzrdiImdgClass(ImdgClassEnum inImdgClass) {
        super.setHzrdiImdgClass(inImdgClass);
        ImdgClassEnum uberImdgClass = inImdgClass;
        if (uberImdgClass == null) {
            throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.VALIDATION_REQUIRED_FIELD, null, (Object)"Imdg Class");
        }
        String imdgId = inImdgClass.getKey();
        if (imdgId.length() == 4) {
            uberImdgClass = ImdgClassEnum.getEnum(imdgId.substring(0, 3));
            this.setHzrdiExplosiveClass(imdgId.substring(3, 4));
        }
        this.setHzrdiImdgCode(uberImdgClass);
    }

    public void updateHzrdiExplosiveClass(String inHzrdiExplosiveClass) {
        this.setHzrdiExplosiveClass(inHzrdiExplosiveClass);
    }

    public Object getUnitHazardItemPlacardTableKey() {
        return this.getHzrdiGkey();
    }

    private Collection<HazardItemPlacard> ensureItems() {
        HashSet items = (HashSet) this.getHzrdiPlacardSet();
        if (items == null) {
            items = new HashSet();
            this.setHzrdiPlacardSet(items);
        }
        return items;
    }

    public void preProcessInsert(FieldChanges inFieldChanges) {
        HazardFireCode fireCode = HazardFireCode.findByImdgClassAndUnNbr(this.getHzrdiImdgClass(), this.getHzrdiUNnum());
        if (fireCode != null) {
            this.setHzrdiFireCode(fireCode);
            this.getHzrdiHazards().computeWorstFireCode();
        }
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        this.updateHzrdiSeqIfNull();
        this.setSelfAndFieldChange(IInventoryField.HZRDI_SEQ, this.getHzrdiSeq(), inOutMoreChanges);
        this.setSelfAndFieldChange(IInventoryField.HZRDI_FIRE_CODE, this.getHzrdiFireCode(), inOutMoreChanges);
    }

    private void updateHzrdiSeqIfNull() {
        if (this.getHzrdiSeq() == null) {
            HazardItem lastItem = this.getHzrdiHazards().getLastItem();
            if (lastItem == null || lastItem.getHzrdiSeq() == null) {
                this.getHzrdiHazards().initializeSeqAllItems();
            } else {
                this.setHzrdiSeq(lastItem.getHzrdiSeq() + 1L);
            }
        }
    }

    public void preProcessUpdate(FieldChanges inFieldChanges, FieldChanges inOutMoreChanges) {
        if (inFieldChanges.hasFieldChange(IInventoryField.HZRDI_IMDG_CLASS) || inFieldChanges.hasFieldChange(IInventoryField.HZRDI_U_NNUM) || inFieldChanges.hasFieldChange(IInventoryField.HZRDI_WEIGHT)) {
            if (!inFieldChanges.hasFieldChange(IInventoryField.HZRDI_FIRE_CODE)) {
                HazardFireCode fireCode = HazardFireCode.findByImdgClassAndUnNbr(this.getHzrdiImdgClass(), this.getHzrdiUNnum());
                this.setSelfAndFieldChange(IInventoryField.HZRDI_FIRE_CODE, fireCode, inOutMoreChanges);
            }
            this.getHzrdiHazards().computeWorstFireCode();
        }
    }

    public void preProcessDelete(FieldChanges inFieldChanges) {
        this.getHzrdiHazards().computeWorstFireCode();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (this.getHzrdiQuantity() != null && this.getHzrdiPackageType() == null) {
            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.HAZITEM_QUANTITY_REQUIRES_PACKAGE_TYPE, (BizViolation)bv);
        }
        return bv;
    }

    public int compareTo(Object inHazardItem) {
        if (!(inHazardItem instanceof HazardItem)) {
            return 1;
        }
        String thisHazardKey = this.getHzrdiImdgCode().getKey();
        String thisExplosiveClass = this.getHzrdiExplosiveClass();
        if (thisExplosiveClass != null) {
            thisHazardKey = thisHazardKey + thisExplosiveClass;
        }
        String inHazardKey = ((HazardItem)inHazardItem).getHzrdiImdgCode().getKey();
        String inHazardExplosiveClass = ((HazardItem)inHazardItem).getHzrdiExplosiveClass();
        if (thisExplosiveClass != null) {
            inHazardKey = inHazardKey + inHazardExplosiveClass;
        }
        return thisHazardKey.compareTo(inHazardKey);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    protected Object clone() throws CloneNotSupportedException {
        HazardItem copyHzrdi = (HazardItem)super.clone();
        copyHzrdi.setPrimaryKey(null);
        Set origHzrdiPlacardSet = this.getHzrdiPlacardSet();
        if (origHzrdiPlacardSet != null) {
            HashSet<HazardItemPlacard> copyHzrdiPlacardSet = new HashSet<HazardItemPlacard>();
            for (Object hzrdiplacard : origHzrdiPlacardSet) {
                HazardItemPlacard cloneItemPlacard = ((HazardItemPlacard)hzrdiplacard).deepClone();
                cloneItemPlacard.updateParentHazardItem(copyHzrdi);
                copyHzrdiPlacardSet.add(cloneItemPlacard);
            }
            copyHzrdi.setHzrdiPlacardSet(copyHzrdiPlacardSet);
        }
        return copyHzrdi;
    }

    public HazardItem deepClone() {
        try {
            return (HazardItem)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    public void updateParentHazard(Hazards inHazard) {
        if (inHazard == null) {
            throw BizFailure.create((String)"Update HazardItem ParentOrder is  null .This is not a valid entry!");
        }
        this.setHzrdiHazards(inHazard);
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        Hazards hazards = this.getHzrdiHazards();
        if (hazards != null) {
            List<HazardItem> hazardsItems = hazards.ensureItems();
            if (!hazardsItems.contains(this)) {
                hazardsItems.add(this);
            }
            hazards.updateOwnerDenormalizedFields();
        }
    }

    public void onDelete() {
        Hazards hazards = this.getHzrdiHazards();
        if (hazards != null) {
            List<HazardItem> hazardsItems = hazards.ensureItems();
            if (hazardsItems.contains(this)) {
                hazardsItems.remove(this);
            }
            hazards.updateOwnerDenormalizedFields();
        }
    }

    @Nullable
    public String getHzrdiFirecodeCode() {
        String code = null;
        if (this.getHzrdiFireCode() != null) {
            code = this.getHzrdiFireCode().getFirecodeFireCode();
        }
        return code;
    }

    @Nullable
    public String getHzrdiFirecodeClass() {
        String code = null;
        if (this.getHzrdiFireCode() != null) {
            code = this.getHzrdiFireCode().getFirecodeFireCodeClass();
        }
        return code;
    }

    public void updateHzrdiUNnum(String inUNnum) {
        this.setHzrdiUNnum(inUNnum);
    }

    public void setHzrdiUyvForXpm(UnitYardVisit inUyv) {
        this._uyvForXpm = inUyv;
    }

    public UnitYardVisit getHzrdiUyvForXpm() {
        if (this._uyvForXpm == null) {
            this._uyvForXpm = this.getHzrdiHazards().findUyvFromHazards();
        }
        return this._uyvForXpm;
    }

    public UnitYardVisit getHzrdiUyvForYard(Serializable inComplexGkey, Serializable inFacilityGKey, Serializable inYardGkey) {
        return this.getHzrdiHazards().findUyvFromHazardsForYard(inComplexGkey, inFacilityGKey, inYardGkey);
    }

    public void update(IValueHolder inHzrdVao) {
        block10: {
            IValueHolder[] hzrdplacard;
            HazardItemPlacard hzrditemPlacard;
            String desc;
            Long placardkey;
            Placard plcrd;
            String plcrdtxt;
            block9: {
                if (inHzrdVao == null) {
                    return;
                }
                this.ensureItems().clear();
                for (IMetafieldId field : inHzrdVao.getFields()) {
                    if (HiberCache.getFieldType((String)field.getFieldId()) == null) continue;
                    this.setFieldValue(field, inHzrdVao.getFieldValue(field));
                }
                Object placardArrayValue = inHzrdVao.getFieldValue(IInventoryBizMetafield.HZRDI_PLACARD_ARRAY);
                if (placardArrayValue == null && ContextHelper.isUpdateFromHumanUser()) {
                    return;
                }
                if (!(placardArrayValue instanceof Serializable[])) break block9;
                Serializable[] placardArray = (Serializable[])placardArrayValue;
                for (int i = 0; i < placardArray.length; ++i) {
                    Placard placard = (Placard)HibernateApi.getInstance().load(Placard.class, placardArray[i]);
                    HazardItemPlacard hzrditemPlacard2 = new HazardItemPlacard();
                    hzrditemPlacard2.setFieldValue(IInventoryField.HZRDIP_PLACARD, placardArray[i]);
                    this.addPlacard(hzrditemPlacard2);
                }
                break block10;
            }
            Object placardVaos = inHzrdVao.getFieldValue(IInventoryBizMetafield.UNIT_HAZARD_ITEM_PLACARD_TABLE_KEY);
            if (placardVaos instanceof List) {
                List placardvaolst = (List)placardVaos;
                for (Object placardVao : placardvaolst) {
                    plcrdtxt = (String)((ValueObject)placardVao).getFieldValue(IInventoryField.HZRDIP_PLACARD);
                    plcrd = Placard.findPlacard(plcrdtxt);
                    placardkey = null;
                    if (plcrd != null) {
                        placardkey = plcrd.getPlacardGkey();
                    }
                    desc = (String)((ValueObject)placardVao).getFieldValue(IInventoryField.HZRDIP_DESCRIPTION);
                    hzrditemPlacard = new HazardItemPlacard();
                    hzrditemPlacard.setFieldValue(IInventoryField.HZRDIP_PLACARD, placardkey);
                    hzrditemPlacard.setFieldValue(IInventoryField.HZRDIP_DESCRIPTION, desc);
                    this.addPlacard(hzrditemPlacard);
                }
            }
            if (!(placardVaos instanceof IValueHolder[]) || (hzrdplacard = (IValueHolder[])inHzrdVao.getFieldValue(IInventoryBizMetafield.UNIT_HAZARD_ITEM_PLACARD_TABLE_KEY)) == null) break block10;
            for (int j = 0; j < hzrdplacard.length; ++j) {
                IValueHolder hzrdplacardVao = hzrdplacard[j];
                plcrdtxt = (String)hzrdplacardVao.getFieldValue(IInventoryField.HZRDIP_PLACARD);
                plcrd = Placard.findPlacard(plcrdtxt);
                placardkey = null;
                if (plcrd != null) {
                    placardkey = plcrd.getPlacardGkey();
                }
                desc = (String)hzrdplacardVao.getFieldValue(IInventoryField.HZRDIP_DESCRIPTION);
                hzrditemPlacard = new HazardItemPlacard();
                hzrditemPlacard.setFieldValue(IInventoryField.HZRDIP_PLACARD, placardkey);
                hzrditemPlacard.setFieldValue(IInventoryField.HZRDIP_DESCRIPTION, desc);
                this.addPlacard(hzrditemPlacard);
            }
        }
    }

    public void recordInsertEvent(IServiceable inServiceable, FieldChanges inOutEdiTracedChanges, String inNotes) {
        FieldChanges fieldChanges = new FieldChanges();
        fieldChanges.setFieldChange(IInventoryField.HZRDI_IMDG_CLASS, null, (Object)this.getHzrdiImdgClass());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_U_NNUM, null, (Object)this.getHzrdiUNnum());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_QUANTITY, null, (Object)this.getHzrdiQuantity());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_WEIGHT, null, (Object)this.getHzrdiWeight());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_FLASH_POINT, null, (Object)this.getHzrdiFlashPoint());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_FIRE_CODE, null, (Object)this.getHzrdiFirecodeCode());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_PACKING_GROUP, null, (Object)this.getHzrdiPackingGroup());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_MARINE_POLLUTANTS, null, (Object)this.getHzrdiMarinePollutants());
        fieldChanges.setFieldChange(IInventoryField.HZRDI_LTD_QTY, null, (Object)this.getHzrdiLtdQty());
        fieldChanges.setFieldChange(UnitField.HZRDIP_PLACRD_TEXT, null, (Object)this.getHzrdiPlacardsString());
        fieldChanges.setFieldChange(UnitField.HZRDI_PROPER_NAME, null, (Object)this.getHzrdiProperName());
        fieldChanges.setFieldChange(UnitField.HZRDI_TECH_NAME, null, (Object)this.getHzrdiTechName());
        fieldChanges.setFieldChange(UnitField.HZRDI_PACKAGE_TYPE, null, (Object)this.getHzrdiPackageType());
        if (inOutEdiTracedChanges != null) {
            inOutEdiTracedChanges.addFieldChanges(fieldChanges);
        }
        String notes = "Insert " + (Object)((Object)this.getHzrdiImdgClass()) + " with UN Number " + this.getHzrdiUNnum();
        String string = notes = inNotes != null ? notes + ", " + inNotes : notes;
        if (inServiceable != null && LogicalEntityEnum.BKG.equals((Object)inServiceable.getLogicalEntityType())) {
            this.recordEvent(inServiceable, fieldChanges, EventEnum.BOOKING_HAZARDS_INSERT, notes);
        } else {
            this.recordEvent(inServiceable, fieldChanges, EventEnum.UNIT_HAZARDS_INSERT, notes);
        }
    }

    public void recordDeleteEvent(IServiceable inServiceable, FieldChanges inOutEdiTracedChanges, String inNotes) {
        FieldChanges fieldChanges = new FieldChanges();
        fieldChanges.setFieldChange(IInventoryField.HZRDI_IMDG_CLASS, (Object)this.getHzrdiImdgClass(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_U_NNUM, (Object)this.getHzrdiUNnum(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_QUANTITY, (Object)this.getHzrdiQuantity(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_WEIGHT, (Object)this.getHzrdiWeight(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_FLASH_POINT, (Object)this.getHzrdiFlashPoint(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_FIRE_CODE, (Object)this.getHzrdiFirecodeCode(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_PACKING_GROUP, (Object)this.getHzrdiPackingGroup(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_MARINE_POLLUTANTS, (Object)this.getHzrdiMarinePollutants(), null);
        fieldChanges.setFieldChange(IInventoryField.HZRDI_LTD_QTY, (Object)this.getHzrdiLtdQty(), null);
        fieldChanges.setFieldChange(UnitField.HZRDIP_PLACRD_TEXT, (Object)this.getHzrdiPlacardsString(), null);
        if (inOutEdiTracedChanges != null) {
            inOutEdiTracedChanges.addFieldChanges(fieldChanges);
        }
        String notes = "Delete " + (Object)((Object)this.getHzrdiImdgClass()) + " with UN Number " + this.getHzrdiUNnum();
        String string = notes = inNotes != null ? notes + ", " + inNotes : notes;
        if (inServiceable != null && LogicalEntityEnum.BKG.equals((Object)inServiceable.getLogicalEntityType())) {
            this.recordEvent(inServiceable, fieldChanges, EventEnum.BOOKING_HAZARDS_DELETE, notes);
        } else {
            this.recordEvent(inServiceable, fieldChanges, EventEnum.UNIT_HAZARDS_DELETE, notes);
        }
    }

    public void recordUpdateEvent(IServiceable inServiceable, HazardItem inOldHazardItem, FieldChanges inOutEdiTracedChanges, String inNotes) {
        FieldChanges fieldChanges = new FieldChanges();
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_IMDG_CLASS, (Object)inOldHazardItem.getHzrdiImdgClass(), (Object)this.getHzrdiImdgClass());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_U_NNUM, inOldHazardItem.getHzrdiUNnum(), this.getHzrdiUNnum());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_QUANTITY, inOldHazardItem.getHzrdiQuantity(), this.getHzrdiQuantity());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_WEIGHT, inOldHazardItem.getHzrdiWeight(), this.getHzrdiWeight());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_FLASH_POINT, inOldHazardItem.getHzrdiFlashPoint(), this.getHzrdiFlashPoint());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_FIRE_CODE, inOldHazardItem.getHzrdiFireCode(), this.getHzrdiFireCode());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_PACKING_GROUP, (Object)inOldHazardItem.getHzrdiPackingGroup(), (Object)this.getHzrdiPackingGroup());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_MARINE_POLLUTANTS, inOldHazardItem.getHzrdiMarinePollutants(), this.getHzrdiMarinePollutants());
        this.addFieldChange(fieldChanges, IInventoryField.HZRDI_LTD_QTY, inOldHazardItem.getHzrdiLtdQty(), this.getHzrdiLtdQty());
        this.addFieldChange(fieldChanges, UnitField.HZRDIP_PLACRD_TEXT, inOldHazardItem.getHzrdiPlacardsString(), this.getHzrdiPlacardsString());
        this.addFieldChange(fieldChanges, UnitField.HZRDI_PROPER_NAME, inOldHazardItem.getHzrdiProperName(), this.getHzrdiProperName());
        this.addFieldChange(fieldChanges, UnitField.HZRDI_TECH_NAME, inOldHazardItem.getHzrdiTechName(), this.getHzrdiTechName());
        this.addFieldChange(fieldChanges, UnitField.HZRDI_PACKAGE_TYPE, inOldHazardItem.getHzrdiPackageType(), this.getHzrdiPackageType());
        if (fieldChanges.getFieldChangeCount() != 0) {
            if (inOutEdiTracedChanges != null) {
                inOutEdiTracedChanges.addFieldChanges(fieldChanges);
            }
            String notes = "Update " + (Object)((Object)this.getHzrdiImdgClass()) + " with UN Number " + this.getHzrdiUNnum();
            String string = notes = inNotes != null ? notes + ", " + inNotes : notes;
            if (inServiceable != null && LogicalEntityEnum.BKG.equals((Object)inServiceable.getLogicalEntityType())) {
                this.recordEvent(inServiceable, fieldChanges, EventEnum.BOOKING_HAZARDS_UPDATE, notes);
            } else {
                this.recordEvent(inServiceable, fieldChanges, EventEnum.UNIT_HAZARDS_UPDATE, notes);
            }
        }
    }

    private void addFieldChange(FieldChanges inOutFieldChanges, IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        if (inOldValue != null && inNewValue == null) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        } else if (inOldValue == null && inNewValue != null) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        } else if (inOldValue != null && !inOldValue.equals(inNewValue)) {
            inOutFieldChanges.setFieldChange(inFieldId, inOldValue, inNewValue);
        }
    }

    @Nullable
    private IEvent recordEvent(IServiceable inServiceable, FieldChanges inFieldChanges, EventEnum inEventEnum, String inNotes) {
        IServicesManager srvcMgr = (IServicesManager) Roastery.getBean((String)"servicesManager");
        IEvent event = null;
        try {
            event = srvcMgr.recordEvent((IEventType)inEventEnum, inNotes, null, null, inServiceable, inFieldChanges, null);
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inEventEnum.getId() + " on " + this + " : " + e + " (ignored)"));
        }
        return event;
    }

    public Class getArchiveClass()
    {
    //    return ArchiveHazardItem.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    public static HazardItem hydrate(Serializable inPrimaryKey) {
        return (HazardItem) HibernateApi.getInstance().load(HazardItem.class, inPrimaryKey);
    }
}
