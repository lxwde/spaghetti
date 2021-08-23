package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.GoodsBaseDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class GoodsBase extends GoodsBaseDO implements IGoods {
    private static final Logger LOGGER = Logger.getLogger(GoodsBase.class);

    public String getGoodsThreeMainHazardCodes() {
        String allImdgs = this.getGdsImdgTypes();
        return allImdgs == null ? "" : allImdgs;
    }

    public String getGoodsThreeMainHazardUNNumbers() {
        String allHazds = this.getGdsThreeMainHazardUNNumbers();
        return allHazds == null ? "" : allHazds;
    }

    @Nullable
    public String getGoodsMostSevereHazardClass() {
        String hazCode = null;
        Hazards hazards = this.getGdsHazards();
        if (hazards != null) {
            Iterator iterator = hazards.getHazardItemsIteratorOrderedBySeverity();
            for (int i = 0; i < 1 && iterator.hasNext(); ++i) {
                HazardItem hazardIt = (HazardItem)iterator.next();
                hazCode = hazardIt.getHzrdiImdgClass().getName();
            }
        }
        return hazCode;
    }

    public boolean isTemperatureControlled() {
        Commodity cmdy = this.getGdsCommodity();
        return cmdy != null && cmdy.isTemperatureControlled();
    }

    @Override
    public void updateShipper(Object inObject) {
        if (inObject == null) {
            this.setGdsShipperBzu(null);
        } else if (inObject instanceof ScopedBizUnit) {
            this.setGdsShipperBzu((ScopedBizUnit)inObject);
        } else if (inObject instanceof String) {
            String id = StringUtils.trim((String)((String)inObject));
//            Shipper bzu = StringUtils.isEmpty((String)id) ? null : Shipper.findOrCreateShipperByIdOrName((String)id);
//            this.setGdsShipperBzu((ScopedBizUnit)bzu);
        } else {
            throw BizFailure.create((String)("invalid input: " + inObject));
        }
    }

    @Override
    public void updateConsignee(Object inObject) {
        if (inObject == null) {
            this.setGdsConsigneeBzu(null);
        } else if (inObject instanceof ScopedBizUnit) {
            this.setGdsConsigneeBzu((ScopedBizUnit)inObject);
        } else if (inObject instanceof String) {
            String id = StringUtils.trim((String)((String)inObject));
//            Shipper bzu = StringUtils.isEmpty((String)id) ? null : Shipper.findOrCreateShipperByIdOrName((String)id);
//            this.setGdsConsigneeBzu((ScopedBizUnit)bzu);
        } else {
            throw BizFailure.create((String)("invalid input: " + inObject));
        }
    }

    public void updateCommodity(Commodity inCommodity) {
        this.setGdsCommodity(inCommodity);
    }

    @Nullable
    public String getShipperAsString() {
        if (ArgoConfig.SHIPPER_CONSIGNEE_USING_NAME.isOn(ContextHelper.getThreadUserContext())) {
            return this.getGdsShipperBzu() == null ? null : this.getGdsShipperBzu().getBzuName();
        }
        return this.getGdsShipperBzu() == null ? null : this.getGdsShipperBzu().getBzuId();
    }

    @Nullable
    public String getConsigneeAsString() {
        if (ArgoConfig.SHIPPER_CONSIGNEE_USING_NAME.isOn(ContextHelper.getThreadUserContext())) {
            return this.getGdsConsigneeBzu() == null ? null : this.getGdsConsigneeBzu().getBzuName();
        }
        return this.getGdsConsigneeBzu() == null ? null : this.getGdsConsigneeBzu().getBzuId();
    }

    @Override
    public void setOrigin(String inGdsOrigin) {
        super.setGdsOrigin(inGdsOrigin);
    }

    @Override
    public void setDestination(String inGdsDestination) {
        super.setGdsDestination(inGdsDestination);
    }

    @Override
    public void attachHazards(Hazards inNewHazards) {
        this.attachHazards(this.getGdsHazards(), inNewHazards);
    }

    public void attachHazards(Hazards inNewHazards, FieldChanges inOutEdiTracedChanges) {
        this.attachHazards(this.getGdsHazards(), inNewHazards, inOutEdiTracedChanges);
    }

    public void attachHazards(Hazards inOldHazards, Hazards inNewHazards) {
        this.attachHazards(inOldHazards, inNewHazards, null);
    }

    private void attachHazards(IValueHolder[] inNewHazards) {
        Hazards hazards = this.ensureGdsHazards();
        if (hazards.seqNeedsInitialization()) {
            hazards.initializeSeqAllItems();
        }
        Unit unit = this.getGdsUnit();
        ArrayList oldHazards = new ArrayList(hazards.getHzrdItems());
        for (IValueHolder vh : inNewHazards) {
            Long gkey = (Long)vh.getFieldValue(IInventoryField.HZRDI_GKEY);
            HazardItem item = null;
            if (gkey == null) {
                item = hazards.addHazardItem(vh);
                item.recordInsertEvent((IServiceable) unit, null, null);
                continue;
            }
            item = this.findHazardItem(oldHazards, gkey);
            if (item != null) {
                HazardItem oldHazardItem = item.deepClone();
                item.update(vh);
                oldHazards.remove(item);
                item.recordUpdateEvent((IServiceable) unit, oldHazardItem, null, null);
                continue;
            }
            hazards.addHazardItem(vh);
        }
        for (Object item : oldHazards) {
            hazards.deleteHazardItem((HazardItem)item);
            ((HazardItem)item).recordDeleteEvent((IServiceable) unit, null, null);
        }
        if (hazards.getHzrdItems().isEmpty()) {
            this.setGdsHazards(null);
        }
        this.validateObservedPlacards();
        this.calculateDenormalizedHazardFields();
        this.calculateDenormalizedHazardUNFields();
    }

    private HazardItem findHazardItem(List inHazardItems, Long inGkey) {
        for (Object hazardItem : inHazardItems) {
            if (!inGkey.equals(((HazardItem)hazardItem).getHzrdiGkey())) continue;
            return (HazardItem) hazardItem;
        }
        return null;
    }

    public void attachHazards(Hazards inOldHazards, Hazards inNewHazards, FieldChanges inOutEdiTracedChanges) {
        List items;
        List list = items = inNewHazards == null ? null : inNewHazards.getHzrdItems();
        if (items == null || items.isEmpty()) {
            this.setGdsHazards(null);
        } else {
            Long gdsGkey = this.getGdsGkey();
            if (gdsGkey == null) {
                LOGGER.error((Object)("attachHazards: attach hazards to Goods, but Goods not yet persisted: " + inNewHazards));
            } else {
                inNewHazards.attachHazardToEntity("GoodsBase", gdsGkey);
            }
            this.setGdsHazards(inNewHazards);
        }
        if (inOldHazards != null && !inOldHazards.equals(inNewHazards)) {
            HibernateApi.getInstance().delete((Object)inOldHazards);
        }
        this.validateObservedPlacards();
        DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
//        if (!DataSourceEnum.DATA_IMPORT.equals((Object)dataSource) && !DataSourceEnum.SPARCS_IMPORT.equals((Object)dataSource)) {
//            if (ArgoConfig.HAZARD_TRACKING.isOn(ContextHelper.getThreadUserContext())) {
//                Hazards.recordDetailedTracking(this.getGdsUnit(), inOldHazards, inNewHazards, inOutEdiTracedChanges, null);
//            } else if ((inOldHazards != null || inNewHazards != null) && this.getGdsUnit() != null) {
//                this.getGdsUnit().recordUnitEvent((IEventType) EventEnum.UNIT_HAZARDS_UPDATE, null, "Hazards Updated");
//            }
//        }
        String oldImdgValue = this.getGdsImdgTypes();
        this.calculateDenormalizedHazardFields();
        if (inOutEdiTracedChanges != null) {
            inOutEdiTracedChanges.setFieldChange(new FieldChange(IInventoryField.GDS_IMDG_TYPES, (Object)oldImdgValue, (Object)this.getGdsImdgTypes()));
        }
        String oldHzUNValue = this.getGdsThreeMainHazardUNNumbers();
        this.calculateDenormalizedHazardUNFields();
        if (inOutEdiTracedChanges != null) {
            inOutEdiTracedChanges.setFieldChange(new FieldChange(IInventoryField.GDS_THREE_MAIN_HAZARD_U_N_NUMBERS, (Object)oldHzUNValue, (Object)this.getGdsThreeMainHazardUNNumbers()));
        }
    }

    private void validateObservedPlacards() {
        HashSet<Placard> obsPlacardSet = new HashSet<Placard>();
        boolean arePlacardsMismatched = false;
        Unit unit = this.getGdsUnit();
        Hazards unitHazards = this.getGdsHazards();
//        UnitManager unitMngr = (UnitManager)Roastery.getBean((String)"unitManager");
//        if (unit == null) {
//            return;
//        }
//        HashSet currUnitPlacardSet = (HashSet) unit.getUnitObservedPlacards();
//        if (currUnitPlacardSet == null || currUnitPlacardSet.isEmpty()) {
//            currUnitPlacardSet = new HashSet();
//        }
//        for (ObservedPlacard op : currUnitPlacardSet) {
//            obsPlacardSet.add(op.getObsplacardPlacard());
//        }
//        arePlacardsMismatched = unitMngr.computeUnitPlacardMismatch(unitHazards, obsPlacardSet);
//        unit.setUnitArePlacardsMismatched(arePlacardsMismatched);
    }

    public void calculateDenormalizedHazardFields() {
        Collection<String> imdgTypes = this.getImdgTypes(false);
        if (imdgTypes.isEmpty()) {
            this.setGdsIsHazardous(Boolean.FALSE);
            this.setGdsImdgTypes(null);
        } else {
            this.setGdsIsHazardous(Boolean.TRUE);
            this.setGdsImdgTypes(StringUtils.join(imdgTypes, (char)','));
        }
    }

    public Collection<String> getImdgTypes(boolean inCheckLimitedQty) {
        Hazards hazards = this.getGdsHazards();
        if (hazards == null || hazards.getHzrdItems() == null || hazards.getHzrdItems().isEmpty()) {
            return Collections.emptyList();
        }
        LinkedHashSet<String> imdgTypes = new LinkedHashSet<String>();
        Iterator<HazardItem> iterator = hazards.getHazardItemsIterator();
        while (iterator.hasNext()) {
            HazardItem hazardIt = iterator.next();
            ImdgClassEnum code = hazardIt.getHzrdiImdgCode();
            boolean includeImdgType = code != null;
            if (!includeImdgType) continue;
            if (inCheckLimitedQty) {
                boolean bl = includeImdgType = hazardIt.getHzrdiLtdQty() == null || hazardIt.getHzrdiLtdQty() == false;
            }
            if (!includeImdgType) continue;
            String explosiveClass = hazardIt.getHzrdiExplosiveClass();
            if (explosiveClass == null) {
                imdgTypes.add(code.getKey());
                continue;
            }
            imdgTypes.add(code.getKey() + explosiveClass);
        }
        return imdgTypes;
    }

    public void calculateDenormalizedHazardUNFields() {
        Hazards hazards = this.getGdsHazards();
        if (hazards == null || hazards.getHzrdItems() == null || hazards.getHzrdItems().isEmpty()) {
            this.setGdsThreeMainHazardUNNumbers(null);
        } else {
            StringBuilder hazUNNums = new StringBuilder();
            Iterator iterator = hazards.getHazardItemsIteratorOrderedBySeverity();
            for (int i = 0; iterator.hasNext() && i < 3; ++i) {
                HazardItem hazardIt = (HazardItem)iterator.next();
                String num = hazardIt.getHzrdiUNnum();
                if (num == null) continue;
                if (hazUNNums == null || hazUNNums.length() == 0) {
                    hazUNNums.append(num);
                    continue;
                }
                if (hazUNNums.length() <= 0 || hazUNNums.indexOf(num) != -1) continue;
                hazUNNums.append(',');
                hazUNNums.append(num);
            }
            this.setGdsThreeMainHazardUNNumbers(hazUNNums.toString());
        }
    }

    @Override
    public void setCommodity(Commodity inCmdy) {
        super.setGdsCommodity(inCmdy);
    }

    @Override
    public void setGdsReeferRqmnts(ReeferRqmnts inGdsReeferRqmnts) {
        super.setGdsReeferRqmnts(inGdsReeferRqmnts);
    }

    public void assignUnitBl(String inGdsBlNbr) throws BizViolation {
        CarrierVisit cv;
        Unit unit;
        ScopedBizUnit line;
//        InventoryCargoManager cargoManager;
//        IBillOfLading billOfLading;
//        if (Roastery.containsBean((String)"inventoryCargoManager") && (billOfLading = (cargoManager = (InventoryCargoManager)Roastery.getBean((String)"inventoryCargoManager")).findBillOfLading(inGdsBlNbr, line = (unit = this.getGdsUnit()).getUnitLineOperator(), cv = unit.getUnitDeclaredIbCv())) != null) {
//            cargoManager.assignUnitBillOfLading(this.getGdsUnit(), billOfLading.getGkey());
//        }
        super.setGdsBlNbr(inGdsBlNbr);
    }

    protected void removeUnitBillOfLading(String inGdsBlNbr) throws BizViolation {
        CarrierVisit cv;
        Unit unit;
        ScopedBizUnit line;
//        InventoryCargoManager cargoManager;
//        IBillOfLading billOfLading;
//        if (Roastery.containsBean((String)"inventoryCargoManager") && (billOfLading = (cargoManager = (InventoryCargoManager)Roastery.getBean((String)"inventoryCargoManager")).findBillOfLading(inGdsBlNbr, line = (unit = this.getGdsUnit()).getUnitLineOperator(), cv = unit.getUnitDeclaredIbCv())) != null) {
//            cargoManager.removeUnitBillOfLading(this.getGdsUnit(), billOfLading);
//        }
        super.setGdsBlNbr(null);
    }

    public ReeferRqmnts ensureGdsReeferRqmnts() {
        ReeferRqmnts requirements = super.getGdsReeferRqmnts();
        if (requirements == null) {
            requirements = new ReeferRqmnts();
            this.setGdsReeferRqmnts(requirements);
        }
        return requirements;
    }

    public Hazards ensureGdsHazards() {
        Hazards hazards = this.getGdsHazards();
        if (hazards == null) {
            hazards = new Hazards();
            HibernateApi.getInstance().save((Object)hazards);
            this.setGdsHazards(hazards);
            hazards.attachHazardToEntity("GoodsBase", (Long)this.getPrimaryKey());
        }
        return hazards;
    }

    public void setFieldValue(IMetafieldId inMetaFieldId, Object inFieldValue) {
        if (IInventoryBizMetafield.GDS_SHIPPER_AS_STRING.equals((Object)inMetaFieldId)) {
            this.updateShipper(inFieldValue);
        } else if (IInventoryBizMetafield.GDS_CONSIGNEE_AS_STRING.equals((Object)inMetaFieldId)) {
            this.updateConsignee(inFieldValue);
        } else {
            super.setFieldValue(inMetaFieldId, inFieldValue);
        }
    }

    @Nullable
    public Object getFieldValue(IMetafieldId inMetaFieldId) {
        IMetafieldId qualifyingMetafieldId = inMetaFieldId.getQualifyingMetafieldId();
        if (UnitField.GDS_UNIT.equals((Object)qualifyingMetafieldId)) {
            Unit unit = this.getGdsUnit();
            return unit.getFieldValue(inMetaFieldId.getQualifiedMetafieldId());
        }
        return super.getFieldValue(inMetaFieldId);
    }

    public static IMetafieldId getGoodsDeclaredIbCarrierMf() {
        return UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV_DETAIL, "GoodsBase");
    }

    @Nullable
    public LogicalEntityEnum getLogicalEntityType() {
        return null;
    }

    public String getLogEntityId() {
        return this.getGdsUnit().getUnitId();
    }

    public String getLogEntityParentId() {
        return this.getLogEntityId();
    }

    public void calculateFlags() {
        Unit unit = this.getGdsUnit();
        unit.updateDenormalizedFields();
    }

    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        Unit unit = this.getGdsUnit();
        UnitVisitStateEnum visitState = unit.getUnitVisitState();
        if (UnitVisitStateEnum.DEPARTED.equals((Object)visitState) || UnitVisitStateEnum.RETIRED.equals((Object)visitState)) {
            return BizViolation.create((IPropertyKey) IInventoryPropertyKeys.GOODSBASE_DOESNT_ALLOW_APPLY_FLAG_TYPE, null, (Object)unit.getUnitId(), (Object)((Object)visitState));
        }
        return null;
    }

    public Complex getLogEntityComplex() {
        return this.getGdsUnit().getUnitComplex();
    }

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList unitQueryableFields = new MetafieldIdList();
        unitQueryableFields.add(UnitField.GDS_COMMODITY);
        unitQueryableFields.add(UnitField.GDS_ORIGIN);
        unitQueryableFields.add(UnitField.GDS_DESTINATION);
        unitQueryableFields.add(UnitField.GDS_CONSIGNEE_BZU);
        unitQueryableFields.add(UnitField.GDS_SHIPPER_BZU);
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_C_O2_PCT));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_HUMIDITY_PCT));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_O2_PCT));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_LIMIT_MAX_C));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_LIMIT_MIN_C));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_REQUIRED_C));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_VENT_REQUIRED));
        unitQueryableFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_VENT_UNIT));
        unitQueryableFields.add(UnitField.GDS_IS_HAZARDOUS);
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_CATEGORY, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_FREIGHT_KIND, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_DRAY_STATUS, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW2, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW3, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_DECK_RQMNT, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_REQUIRES_POWER, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_IS_POWERED, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_IS_OOG, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_LINE_OPERATOR, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_GOODS_AND_CTR_WT_KG, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR1, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR2, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR3, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR4, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_UE_GRADE_ID, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_UE_EQ_OPERATOR_ID, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQTYPE, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQUIP_ISO_GROUP, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQUIP_MATERIAL, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQ_DAMAGE_SEVERITY, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_DECLARED_OB_CV, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_DECLARED_OB_CV_MODE, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POL, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POD1, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_GROUP, "GoodsBase"));
        unitQueryableFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UFV_DWELL_DAYS, "GoodsBase"));
        return unitQueryableFields;
    }

    public static MetafieldIdList getPathsToGuardians(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(GoodsBase.getGoodsDeclaredIbCarrierMf());
        fields.add(UnitField.getQualifiedField(UnitField.UFV_INTENDED_OB_CVD, "GoodsBase"));
        fields.add(UnitField.getQualifiedField(UnitField.UE_DECLARED_OB_VES, "GoodsBase"));
        fields.add(UnitField.getQualifiedField(IArgoBizMetafield.GDSBL_BILLS_OF_LADING, "GoodsBase"));
        return fields;
    }

    public static MetafieldIdList getUpdateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_CATEGORY, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_FREIGHT_KIND, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_DRAY_STATUS, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW3, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_DECK_RQMNT, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REQUIRES_POWER, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_IS_POWERED, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_BACK_CM, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_FRONT_CM, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_LEFT_CM, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_RIGHT_CM, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_TOP_CM, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_LINE_OPERATOR, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_GOODS_AND_CTR_WT_KG, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR1, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR3, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR4, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_UE_GRADE_ID, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_DECLARED_OB_CV, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POL, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POD1, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POD2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT1, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT3, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_GROUP, "GoodsBase"));
        ids.add(UnitField.GDS_CONSIGNEE_BZU);
        ids.add(UnitField.GDS_SHIPPER_BZU);
        ids.add(UnitField.GDS_COMMODITY);
        ids.add(UnitField.GDS_ORIGIN);
        ids.add(UnitField.GDS_DESTINATION);
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_C_O2_PCT));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_HUMIDITY_PCT));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_O2_PCT));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_LIMIT_MAX_C));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_LIMIT_MIN_C));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_TEMP_REQUIRED_C));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_VENT_REQUIRED));
        ids.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.GDS_REEFER_RQMNTS, (IMetafieldId) UnitField.RFREQ_VENT_UNIT));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_TIME, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_RETURN_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_VENT_SETTING, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_VENT_UNIT, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_HMDTY, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_O2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_CO2, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_IS_POWERED, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_IS_BULB_ON, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DEFROST_INTERVAL_HOURS, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DRAINS_OPEN, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_FAN_SETTING, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_MIN_MONITORED_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_MAX_MONITORED_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DEFROST_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_SET_POINT_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_SUPPLY_TMP, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_FUEL_LEVEL, "GoodsBase"));
        ids.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_REMARK, "GoodsBase"));
        return ids;
    }

    public List getGuardians() {
        MetafieldIdList pathsToGuardians = GoodsBase.getPathsToGuardians(this.getLogicalEntityType());
        ArrayList<Object> guardians = new ArrayList<Object>();
        for (int i = 0; i < pathsToGuardians.getSize(); ++i) {
            IMetafieldId id = pathsToGuardians.get(i);
            Object relatedObject = this.getField(id);
            if (relatedObject == null) continue;
            if (IGuardian.class.isAssignableFrom(relatedObject.getClass())) {
                guardians.add(relatedObject);
                continue;
            }
            if (relatedObject instanceof Collection) {
                for (Object guardianOfColl : (Collection)relatedObject) {
                    if (IGuardian.class.isAssignableFrom(guardianOfColl.getClass())) {
                        guardians.add(guardianOfColl);
                        continue;
                    }
                    LOGGER.error((Object)("verifyFlag: calculated guardian is collection - but an entry is of wrong class, class=" + guardianOfColl.getClass() + ", value=" + guardianOfColl));
                }
                continue;
            }
            LOGGER.error((Object)("getGuardians: calculated guardian is of wrong class, class=" + relatedObject.getClass() + ", value=" + relatedObject));
        }
        return guardians;
    }

    @Nullable
    public String getLovKeyNameForPathToGuardian(String inMetaFieldId) {
        if (inMetaFieldId != null) {
            String unitDeclIBCvd = UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV_DETAIL, "GoodsBase").getQualifiedId();
            String ufvIntendedOBCvd = UnitField.getQualifiedField(UnitField.UFV_INTENDED_OB_CVD, "GoodsBase").getQualifiedId();
            String vessel = UnitField.getQualifiedField(UnitField.UE_DECLARED_OB_VES, "GoodsBase").getQualifiedId();
            if (inMetaFieldId.equals(unitDeclIBCvd) || inMetaFieldId.equals(ufvIntendedOBCvd)) {
                return "argoLov.activeVesselAndTrainVisitDetails";
            }
            if (inMetaFieldId.equals(vessel)) {
                return "vesselLov.vessel";
            }
            if (inMetaFieldId.equals(IArgoBizMetafield.GDSBL_BILLS_OF_LADING.getQualifiedId())) {
                return "cargoLov.blsForServices";
            }
        }
        return null;
    }

    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        if (inLogicalEntityType != null) {
            if (LogicalEntityEnum.VV.equals((Object)inLogicalEntityType)) {
                String unitDeclIBCvd = UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV_DETAIL, "GoodsBase").getQualifiedId();
                return this.getLovKeyNameForPathToGuardian(unitDeclIBCvd);
            }
            if (LogicalEntityEnum.VES.equals((Object)inLogicalEntityType)) {
                String vessel = UnitField.getQualifiedField(UnitField.UE_DECLARED_OB_VES, "GoodsBase").getQualifiedId();
                return this.getLovKeyNameForPathToGuardian(vessel);
            }
            if (LogicalEntityEnum.BL.equals((Object)inLogicalEntityType)) {
                String blMfIdString = IArgoBizMetafield.GDSBL_BILLS_OF_LADING.getQualifiedId();
                return this.getLovKeyNameForPathToGuardian(blMfIdString);
            }
        }
        return null;
    }

    @Nullable
    public IValueHolder[] getGoodsHazardVao() {
        Hazards hazards = this.getGdsHazards();
        if (hazards == null) {
            return null;
        }
        return hazards.getHazardsHazItemsVao();
    }

    public void setGoodsHazardVao(Object inValue) {
        if (inValue == null) {
            this.attachHazards((Hazards)null);
        } else if (inValue instanceof IValueHolder[]) {
            this.attachHazards((IValueHolder[])inValue);
        } else {
            throw BizFailure.create((String)("Wrong class sent as input to setGoodsHazardVao. class = " + inValue.getClass()));
        }
    }

    public HazardItem attachHazard(String inImdgCode, String inUnNumber) throws BizViolation {
        Hazards hazards = this.getGdsHazards();
        if (hazards == null) {
            hazards = Hazards.createHazardsEntity();
        }
        HazardItem item = hazards.addHazardItem(inImdgCode, inUnNumber);
        this.attachHazards(hazards);
        return item;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(UnitField.GDS_COMMODITY)) {
            try {
                this.validateCommodity();
            }
            catch (BizViolation inBv) {
                bv = inBv.appendToChain(bv);
            }
        }
        if (inChanges.hasFieldChange(UnitField.GDS_REEFER_RQMNTS)) {
            try {
                this.validateReeferComponent();
            }
            catch (BizViolation inBv) {
                bv = inBv.appendToChain(bv);
            }
        }
        return bv;
    }

    private void validateCommodity() throws BizViolation {
        Commodity gdsCommodity = this.getGdsCommodity();
        if (gdsCommodity != null && ContextHelper.isUpdateFromHumanUser() && gdsCommodity.isTemperatureControlled() && !this.isEqtypeTempControlled()) {
            String eqId = this.nullSafeGetEquipId();
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.GOODS__CMDY_NEEDS_TEMP_CONTROL_EQTYP_IS_NON_REEFER, null, (Object)eqId);
        }
    }

    @Nullable
    private String nullSafeGetEquipId() {
        Equipment primaryEq = this.getGdsUnit().getPrimaryEq();
        EquipType eqType = primaryEq == null ? null : primaryEq.getEqEquipType();
        String eqId = eqType == null ? null : eqType.getEqtypId();
        return eqId;
    }

    private void validateReeferComponent() throws BizViolation {
        this.validateReeferMonitorTimes();
        boolean reeferRqPresent = this.isReeferRequirementPresent();
        if (!reeferRqPresent) {
            return;
        }
        if (!this.isEqtypeTempControlled() && ContextHelper.isUpdateFromHumanUser()) {
            String eqId = this.nullSafeGetEquipId();
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.GOODS__REEFER_RQMNTS_NOT_REQUIRED, null, (Object)eqId);
        }
        if (ContextHelper.isUpdateFromHumanUser()) {
            this.validateTemperature();
        }
    }

    private void validateReeferMonitorTimes() {
        ReeferRqmnts reeferRq = this.getGdsReeferRqmnts();
        if (reeferRq == null) {
            return;
        }
        if (!this.isMonitorTimesInOrder(reeferRq)) {
            MessageCollectorUtils.getMessageCollector().appendMessage(MessageLevelEnum.SEVERE, IArgoPropertyKeys.MONITOR_TIMES_NOT_CHRONOLOGICAL_ORDER, null, null);
        }
    }

    private boolean isMonitorTimesInOrder(ReeferRqmnts inReeferRq) {
        Date monitor1 = inReeferRq.getRfreqTimeMonitor1();
        Date monitor2 = inReeferRq.getRfreqTimeMonitor2();
        Date monitor3 = inReeferRq.getRfreqTimeMonitor3();
        Date monitor4 = inReeferRq.getRfreqTimeMonitor4();
        Date tempDate = null;
        if (monitor1 != null) {
            tempDate = monitor1;
        }
        if (monitor2 != null) {
            if (tempDate != null && tempDate.after(monitor2)) {
                return false;
            }
            tempDate = monitor2;
        }
        if (monitor3 != null) {
            if (tempDate != null && tempDate.after(monitor3)) {
                return false;
            }
            tempDate = monitor3;
        }
        if (monitor4 != null) {
            if (tempDate != null && tempDate.after(monitor4)) {
                return false;
            }
            tempDate = monitor4;
        }
        return true;
    }

    private void validateTemperature() throws BizViolation {
        this.validateTempFromCmdyPerspective();
        this.validateTempOutOfRange();
    }

    private void validateTempFromCmdyPerspective() throws BizViolation {
        ReeferRqmnts reeferRq = this.getGdsReeferRqmnts();
        Commodity cmdy = this.getGdsCommodity();
        if (cmdy != null) {
            if (reeferRq.getRfreqTempRequiredC() != null) {
                cmdy.validateTempRequired(reeferRq.getRfreqTempRequiredC());
            }
            if (reeferRq.getRfreqTempLimitMinC() != null) {
                cmdy.validateTempOutOfRange(reeferRq.getRfreqTempLimitMinC());
            }
            if (reeferRq.getRfreqTempLimitMaxC() != null) {
                cmdy.validateTempOutOfRange(reeferRq.getRfreqTempLimitMaxC());
            }
        }
    }

    private void validateTempOutOfRange() throws BizViolation {
        ReeferRqmnts reeferRq = this.getGdsReeferRqmnts();
        Double minTemp = reeferRq.getRfreqTempLimitMinC();
        Double maxTemp = reeferRq.getRfreqTempLimitMaxC();
        Double temp = reeferRq.getRfreqTempRequiredC();
        if (temp != null) {
            if (minTemp != null && temp < minTemp) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.GOODS__TEMP_TOO_LOW_FOR_RFR_RQMNT_MIN_TEMP, null, (Object)temp, (Object)minTemp);
            }
            if (maxTemp != null && temp > maxTemp) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.GOODS__TEMP_TOO_HIGH_FOR_RFR_RQMNT_MAX_TEMP, null, (Object)temp, (Object)maxTemp);
            }
        }
    }

    private boolean isEqtypeTempControlled() {
        Equipment primaryEq;
        Equipment equipment = primaryEq = this.getGdsUnit() == null ? null : this.getGdsUnit().getPrimaryEq();
        if (primaryEq == null) {
            return false;
        }
        EquipType eqtyp = primaryEq.getEqEquipType();
        return eqtyp.isTemperatureControlled();
    }

    private boolean isReeferRequirementPresent() {
        boolean isReeferRqPresent = false;
        ReeferRqmnts reeferRq = this.getGdsReeferRqmnts();
        if (reeferRq != null && (reeferRq.getRfreqCO2Pct() != null || reeferRq.getRfreqHumidityPct() != null || reeferRq.getRfreqLatestOnPowerTime() != null || reeferRq.getRfreqRequestedOffPowerTime() != null || reeferRq.getRfreqO2Pct() != null || reeferRq.getRfreqTempLimitMaxC() != null || reeferRq.getRfreqTempLimitMinC() != null || reeferRq.getRfreqTempRequiredC() != null || reeferRq.getRfreqVentRequired() != null)) {
            isReeferRqPresent = true;
        }
        return isReeferRqPresent;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public void setGoodsOrigin(String inOrigin) {
        this.setGdsOrigin(inOrigin);
    }

    public void setGoodsDestination(String inDestination) {
        this.setGdsDestination(inDestination);
    }

    public void refreshUnit(Unit inUnit) {
        if (this.getGdsUnit() != null && this.getGdsUnit().getPrimaryKey().equals(inUnit.getPrimaryKey())) {
            this.setGdsUnit(inUnit);
        }
    }

    public boolean isManifested() {
        return false;
    }

    public void clearHazardsRef() {
        this.setGdsHazards(null);
    }

    public static GoodsBase hydrate(Serializable inPrimaryKey) {
        return (GoodsBase)HibernateApi.getInstance().load(GoodsBase.class, inPrimaryKey);
    }

    public void updateFromGoods(GoodsBase inOldGoods) {
        if (inOldGoods != null && inOldGoods.getGdsHazards() != null) {
            HibernateApi.getInstance().save((Object)this);
            inOldGoods.getGdsHazards().replaceParent("GoodsBase", this.getGdsGkey());
        }
        this.setOrigin(inOldGoods.getGdsOrigin());
        this.setDestination(inOldGoods.getGdsDestination());
        this.updateShipper(inOldGoods.getShipperAsString());
        this.updateConsignee(inOldGoods.getConsigneeAsString());
        this.setGdsReeferRqmnts(inOldGoods.getGdsReeferRqmnts());
        this.setCommodity(inOldGoods.getGdsCommodity());
        this.setGoodsHazardVao(inOldGoods.getGoodsHazardVao());
    }

    @Nullable
    public IValueHolder[] getGdsDeclarationVao() {
        Set gdsDeclarationSet = this.getGdsDeclaration();
        if (gdsDeclarationSet == null || gdsDeclarationSet.isEmpty()) {
            return null;
        }
        IValueHolder[] gdsDecVho = new IValueHolder[gdsDeclarationSet.size()];
        Iterator gdsDecIter = gdsDeclarationSet.iterator();
        int i = 0;
        while (gdsDecIter.hasNext()) {
            GoodsDeclaration goodsDeclaration = (GoodsDeclaration)gdsDecIter.next();
            ValueObject vao = goodsDeclaration.getValueObject();
            vao.setFieldValue(IInventoryField.GDSDECL_GKEY, (Object)goodsDeclaration.getGdsdeclGkey());
            vao.setFieldValue(IInventoryField.GDSDECL_SEQ, (Object)goodsDeclaration.getGdsdeclSeq());
            vao.setFieldValue(IInventoryField.GDSDECL_DESCRIPTION, (Object)goodsDeclaration.getGdsdeclDescription());
            vao.setFieldValue(IInventoryField.GDSDECL_QUANTITY_TYPE, (Object)goodsDeclaration.getGdsdeclQuantityType());
            gdsDecVho[i] = vao;
            ++i;
        }
        return gdsDecVho;
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
        if (!DataSourceEnum.USER_DBA.equals((Object)dataSource)) {
            this.getGdsUnit().validateIftChangeAllowed();
        }
    }

    public Class getArchiveClass() {
//        return ArchiveGoodsBase.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    public void propagateLineReeferRqments() {
        ScopedBizUnit lineOp;
        ReeferRqmnts reeferRqmnts = this.getGdsReeferRqmnts();
        if (reeferRqmnts == null) {
            reeferRqmnts = new ReeferRqmnts();
            this.setGdsReeferRqmnts(reeferRqmnts);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("propagateLineReeferRqments: Reefer details are created since unit [" + this.getGdsUnit().getUnitId() + "] " + "has no reefer details already"));
            }
        }
        if ((lineOp = this.getGdsUnit().getUnitLineOperator()) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("propagateLineReeferRqments: Unit[" + this.getGdsUnit().getUnitId() + "] LineOperator is null so propagate is not required"));
            }
            return;
        }
        LineOperator lineOperator = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)this.getGdsUnit().getUnitLineOperator());
        Date monitor1 = lineOperator.getLineopReeferTimeMonitor1();
        Date monitor2 = lineOperator.getLineopReeferTimeMonitor2();
        Date monitor3 = lineOperator.getLineopReeferTimeMonitor3();
        Date monitor4 = lineOperator.getLineopReeferTimeMonitor4();
        Integer offPowerTime = lineOperator.getLineopReeferOffPowerTimeLimit();
        reeferRqmnts.updateReeferMonitors(monitor1, monitor2, monitor3, monitor4, offPowerTime);
    }

    public void beforePropertySourcePurge() {
        this.clearHazardsRef();
    }

    public void propogateCommodityTemperature(FieldChanges inOutMoreChanges) {
        Commodity cmdy = this.getGdsCommodity();
        Double cmdyMinTemp = cmdy != null ? cmdy.getCmdyTempMin() : null;
        Double cmdyMaxTemp = cmdy != null ? cmdy.getCmdyTempMax() : null;
        ReeferRqmnts reeferRqmnts = this.getGdsReeferRqmnts();
        if (cmdyMinTemp == null && cmdyMaxTemp == null && reeferRqmnts == null) {
            return;
        }
        if (reeferRqmnts == null) {
            reeferRqmnts = new ReeferRqmnts();
            this.setGdsReeferRqmnts(reeferRqmnts);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("propogateCommodityTemperature: Reefer details are created since unit [" + this.getGdsUnit().getUnitId() + "] " + "has no reefer details already"));
            }
        }
        Double rfreqMinTemp = reeferRqmnts.getRfreqTempLimitMinC();
        Double rfreqMaxTemp = reeferRqmnts.getRfreqTempLimitMaxC();
        if (!this.isIdenticalValue(rfreqMinTemp, cmdyMinTemp)) {
            reeferRqmnts.setRfreqTempLimitMinC(cmdyMinTemp);
            inOutMoreChanges.setFieldChange(new FieldChange(UnitField.GDS_RFREQ_TEMP_LIMIT_MIN_C, (Object)rfreqMinTemp, (Object)cmdyMinTemp));
        }
        if (!this.isIdenticalValue(rfreqMaxTemp, cmdyMaxTemp)) {
            reeferRqmnts.setRfreqTempLimitMaxC(cmdyMaxTemp);
            inOutMoreChanges.setFieldChange(new FieldChange(UnitField.GDS_RFREQ_TEMP_LIMIT_MAX_C, (Object)rfreqMaxTemp, (Object)cmdyMaxTemp));
        }
    }

    private boolean isIdenticalValue(Double inOldValue, Double inNewValue) {
        return inOldValue == null && inNewValue == null || inOldValue != null && inOldValue.equals(inNewValue) || inNewValue != null && inNewValue.equals(inOldValue);
    }

}
