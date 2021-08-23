package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.GrossWeightSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrder;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.EquipmentState;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;

public abstract class UnitBase extends UnitEquipmentDeprecated{

    private static final Logger LOGGER = Logger.getLogger(Unit.class);

    public void setFieldValue(IMetafieldId inMetaFieldId, Object inFieldValue) {
        IMetafieldId qualifyingMetafieldId = inMetaFieldId.getQualifyingMetafieldId();
        if (UnitField.UNIT_REEFER_RECORD_SET.equals((Object)qualifyingMetafieldId)) {
            if (inFieldValue != null) {
                IDomainQuery dq = QueryUtils.createDomainQuery((String)"ReeferRecord").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.RFREC_CREATION_REF_ID, (Object)this.getReeferCreationRefId())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.RFREC_UNIT, (Object)this.getUnitGkey()));
                List records = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
                ReeferRecord record = null;
                if (!records.isEmpty()) {
                    record = (ReeferRecord)records.remove(0);
                }
                if (record == null) {
                    record = ReeferRecord.createReeferRecord((Unit)this);
                    HibernateApi.getInstance().save((Object)record);
                }
                record.setFieldValue(inMetaFieldId.getQualifiedMetafieldId(), inFieldValue);
            }
        } else if (!IInventoryField.UNIT_IS_OOG.equals((Object)inMetaFieldId)) {
            super.setFieldValue(inMetaFieldId, inFieldValue);
        }
    }

    public Object getFieldValue(IMetafieldId inMetaFieldId) {
        IMetafieldId qualifyingMetafieldId = inMetaFieldId.getQualifyingMetafieldId();
        if (UnitField.UNIT_REEFER_RECORD_SET.equals((Object)qualifyingMetafieldId)) {
            return null;
        }
        return super.getFieldValue(inMetaFieldId);
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        if (inOutMoreChanges.hasFieldChange(IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS_VALUES) && this.getUnitGoodsAndCtrWtKgVerfiedGross() != null) {
            this.setSelfAndFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG, this.getUnitGoodsAndCtrWtKgVerfiedGross(), inOutMoreChanges);
            if (!ContextHelper.getThreadDataSource().getKey().startsWith("EDI") && !inOutMoreChanges.hasFieldChange(UnitField.UNIT_GROSS_WEIGHT_SOURCE)) {
                this.setSelfAndFieldChange(UnitField.UNIT_GROSS_WEIGHT_SOURCE, (Object) GrossWeightSourceEnum.USER, inOutMoreChanges);
            }
        }
        this.setSelfAndFieldChange(IInventoryField.UNIT_IS_OOG, this.hasOverdimensions(), inOutMoreChanges);
        if (ExamEnum.OFFSITERETURN.equals((Object)this.getUnitExam())) {
            this.setSelfAndFieldChange(IInventoryField.UNIT_DRAY_STATUS, (Object)DrayStatusEnum.OFFSITE, inOutMoreChanges);
        }
    }

    private boolean hasOverdimensions() {
        long oog = this.getUnitOogBackCm() == null ? 0L : this.getUnitOogBackCm();
        oog += this.getUnitOogFrontCm() == null ? 0L : this.getUnitOogFrontCm();
        oog += this.getUnitOogLeftCm() == null ? 0L : this.getUnitOogLeftCm();
        oog += this.getUnitOogRightCm() == null ? 0L : this.getUnitOogRightCm();
        return (oog += this.getUnitOogTopCm() == null ? 0L : this.getUnitOogTopCm()) > 0L;
    }

    public String getUnitAppliedHoldOrPermName() {
        IServicesManager sm = (IServicesManager) Roastery.getBean((String)"servicesManager");
        Set impediments = sm.getActiveFlagIds(this.getGuardianArray(), (ILogicalEntity)this);
        return ArgoUtils.createStringFromSetValues((Collection)impediments, (String)",");
    }

    public String getUnitAppliedHoldOrPermView() {
        HashSet<String> hpvSet = new HashSet<String>();
        String impediments = this.getUnitAppliedHoldOrPermName();
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        String[] impedimentArray = StringUtils.split((String)impediments, (String)",");
        if (impedimentArray != null && impedimentArray.length > 0) {
            for (String impdId : impedimentArray) {
                IFlagType flagType = sm.getFlagTypeById(impdId);
                hpvSet.add(flagType.getHpvId());
            }
        }
        return ArgoUtils.createStringFromSetValues(hpvSet, (String)",");
    }

    public static IPredicate formUnitAppliedHoldOrPermNamePredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predCtr = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.EQ, UnitField.UFV_PRIMARY_EQS_GKEY);
        IPredicate predChs = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.EQ, UnitField.UFV_CARRIAGE_EQS_GKEY);
        IPredicate predUnit = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.UNIT, UnitField.UFV_UNIT_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.not((IPredicate)predCtr)).add(PredicateFactory.not((IPredicate)predUnit)).add(PredicateFactory.not((IPredicate)predChs));
        }
        return PredicateFactory.disjunction().add(predCtr).add(predUnit).add(predChs);
    }

    public String getHumanReadableKey() {
        return this.getUnitId();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public void purge() {
//        UnitManager unitManager = (UnitManager)Roastery.getBean((String)"unitManager");
//        unitManager.purgeUnit((Unit)this);
    }

    public LogicalEntityEnum getLogicalEntityType() {
        return LogicalEntityEnum.UNIT;
    }

    public String getLogEntityId() {
        return this.getUnitId();
    }

    public String getLogEntityParentId() {
        return this.getLogEntityId();
    }

    public void calculateFlags() {
        this.updateDenormalizedFields();
    }

    protected abstract void updateDenormalizedFields();

    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        if (DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource())) {
            return null;
        }
        UnitVisitStateEnum visitState = this.getUnitVisitState();
        if (UnitVisitStateEnum.DEPARTED.equals((Object)visitState) || UnitVisitStateEnum.RETIRED.equals((Object)visitState)) {
            return BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITEQUIPMENT_DOESNT_ALLOW_APPLY_FLAG_TYPE, null, (Object)this.getUnitId(), (Object)((Object)visitState));
        }
        return null;
    }

    public Complex getLogEntityComplex() {
        return this.getUnitComplex();
    }

    public List getGuardians() {
        MetafieldIdList pathsToGuardians = UnitBase.getPathsToGuardians(this.getLogicalEntityType());
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

    public static MetafieldIdList getPathsToGuardians(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(UnitField.UNIT_DECLARED_IB_CVD);
        fields.add(UnitField.UNIT_DECLARED_OB_CVD);
        fields.add(UnitField.UNIT_DECLARED_OB_VES);
        fields.add(UnitField.UNIT_DEPARTURE_ORDER);
        fields.add(UnitField.UNIT_ARRIVAL_ORDER);
        fields.add(UnitBase.getUnitGdsBlMfId());
        fields.add(IInventoryBizMetafield.UNIT_SERVICE_ORDERS);
        fields.add(IInventoryBizMetafield.UNIT_EQUIPMENT_STATES);
        fields.add(IInventoryBizMetafield.UNIT_ACC_EQUIPMENT_STATES);
        fields.add(IInventoryBizMetafield.UNIT_CTR_EQUIPMENT_STATES);
        fields.add(IInventoryBizMetafield.UNIT_CHS_EQUIPMENT_STATES);
        return fields;
    }

    public IGuardian[] getGuardianArray() {
        EquipmentState eqs;
        ArrayList<EquipmentState> guardians = new ArrayList<EquipmentState>();
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue != null && (eqs = ue.getUeEquipmentState()) != null) {
            guardians.add(eqs);
        }
        if ((ue = this.getUnitCarriageUe()) != null && (eqs = ue.getUeEquipmentState()) != null) {
            guardians.add(eqs);
        }
        IGuardian[] guardianArr = new IGuardian[guardians.size()];
        for (int i = 0; i < guardianArr.length; ++i) {
            guardianArr[i] = (IGuardian)guardians.get(i);
        }
        return guardianArr;
    }

    @Nullable
    private static IMetafieldId getUnitGdsBlMfId() {
        return UnitField.getQualifiedField(IArgoBizMetafield.GDSBL_BILLS_OF_LADING, "Unit");
    }

    public String getLovKeyNameForPathToGuardian(String inMetaFieldId) {
        if (inMetaFieldId != null) {
            IMetafieldId mfid = MetafieldIdFactory.valueOf((String)inMetaFieldId);
            if (mfid.equals((Object)UnitField.UNIT_DECLARED_IB_CVD) || mfid.equals((Object)UnitField.UNIT_DECLARED_OB_CVD)) {
                return "argoLov.activeVesselAndTrainVisitDetails";
            }
            if (mfid.equals((Object)UnitField.UNIT_DEPARTURE_ORDER)) {
                return "inventoryLov.equipOrder";
            }
            if (mfid.equals((Object)UnitField.UNIT_DECLARED_OB_VES)) {
                return "vesselLov.vessel";
            }
            if (inMetaFieldId.equals(UnitBase.getUnitGdsBlMfId().getQualifiedId())) {
                return "cargoLov.blsForServices";
            }
            if (inMetaFieldId.equals(IInventoryBizMetafield.UNIT_SERVICE_ORDERS.getQualifiedId())) {
                return "ordersLov.srvOrdrNbr";
            }
            if (inMetaFieldId.equals(IInventoryBizMetafield.UNIT_EQUIPMENT_STATES.getQualifiedId()) || inMetaFieldId.equals(IInventoryBizMetafield.UNIT_ACC_EQUIPMENT_STATES.getQualifiedId()) || inMetaFieldId.equals(IInventoryBizMetafield.UNIT_CHS_EQUIPMENT_STATES.getQualifiedId()) || inMetaFieldId.equals(IInventoryBizMetafield.UNIT_CTR_EQUIPMENT_STATES.getQualifiedId())) {
                return "inventoryLov.equipmentStates";
            }
        }
        return null;
    }

    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        if (inLogicalEntityType == null) {
            return null;
        }
        if (LogicalEntityEnum.VV.equals((Object)inLogicalEntityType) || LogicalEntityEnum.RV.equals((Object)inLogicalEntityType) || LogicalEntityEnum.TV.equals((Object)inLogicalEntityType)) {
            String unitDeclIBCvd = UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV_DETAIL, "Unit").getQualifiedId();
            return this.getLovKeyNameForPathToGuardian(unitDeclIBCvd);
        }
        if (LogicalEntityEnum.BKG.equals((Object)inLogicalEntityType) || LogicalEntityEnum.DO.equals((Object)inLogicalEntityType)) {
            String ueDepartureOrder = UnitField.getQualifiedField(UnitField.UE_DEPARTURE_ORDER, "Unit").getQualifiedId();
            return this.getLovKeyNameForPathToGuardian(ueDepartureOrder);
        }
        if (LogicalEntityEnum.VES.equals((Object)inLogicalEntityType)) {
            return this.getLovKeyNameForPathToGuardian(UnitField.UNIT_DECLARED_OB_VES.getQualifiedId());
        }
        if (LogicalEntityEnum.BL.equals((Object)inLogicalEntityType)) {
            String blMfIdString = UnitBase.getUnitGdsBlMfId().getQualifiedId();
            return this.getLovKeyNameForPathToGuardian(blMfIdString);
        }
        if (LogicalEntityEnum.SRVO.equals((Object)inLogicalEntityType)) {
            return this.getLovKeyNameForPathToGuardian(IInventoryBizMetafield.UNIT_SERVICE_ORDERS.getQualifiedId());
        }
        if (LogicalEntityEnum.EQ.equals((Object)inLogicalEntityType)) {
            return this.getLovKeyNameForPathToGuardian(IInventoryBizMetafield.UNIT_EQUIPMENT_STATES.getQualifiedId());
        }
        throw BizFailure.create((String)("The entered IGuardian Type " + (Object)inLogicalEntityType + " is currently not supported by the system " + "(method getLovKeyNameForLogicalEntityType)."));
    }

    public Boolean skipEventRecording() {
        EquipEventsTypeToRecordEnum recordEvent;
        if (this.getLogicalEntityType() == null || this.getLogicalEntityType() != LogicalEntityEnum.UNIT) {
            return false;
        }
        if (this.getUnitEquipment() != null && (recordEvent = this.getUnitEquipment().getEqEquipType().getEqtypeEventsTypeToRecordEnum()) != null && recordEvent == EquipEventsTypeToRecordEnum.NONE) {
            return true;
        }
        return false;
    }

    public static MetafieldIdList getPathsToBizUnits(LogicalEntityEnum inEntity) {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(UnitField.getQualifiedField(UnitField.EQS_EQ_OPERATOR, "Unit"));
        fields.add(UnitField.getQualifiedField(UnitField.EQS_EQ_OWNER, "Unit"));
        fields.add(UnitField.UNIT_LINE_OPERATOR);
        fields.add(UnitField.getQualifiedField(UnitField.GDS_CONSIGNEE_BZU, "Unit"));
        fields.add(UnitField.getQualifiedField(UnitField.GDS_SHIPPER_BZU, "Unit"));
        fields.add(UnitField.UNIT_RTG_TRUCKING_COMPANY);
        return fields;
    }

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList predicateFields = new MetafieldIdList();
        predicateFields.add(UnitField.UNIT_CATEGORY);
        predicateFields.add(UnitField.UNIT_FREIGHT_KIND);
        predicateFields.add(UnitField.UNIT_DRAY_STATUS);
        predicateFields.add(UnitField.UNIT_SPECIAL_STOW);
        predicateFields.add(UnitField.UNIT_SPECIAL_STOW2);
        predicateFields.add(UnitField.UNIT_SPECIAL_STOW3);
        predicateFields.add(UnitField.UNIT_DECK_RQMNT);
        predicateFields.add(UnitField.UNIT_REQUIRES_POWER);
        predicateFields.add(UnitField.UNIT_IS_POWERED);
        predicateFields.add(UnitField.UNIT_IS_OOG);
        predicateFields.add(UnitField.UNIT_LINE_OPERATOR);
        predicateFields.add(UnitField.UNIT_RTG_TRUCKING_COMPANY);
        predicateFields.add(UnitField.UNIT_GOODS_AND_CTR_WT_KG);
        predicateFields.add(UnitField.UNIT_SEAL_NBR1);
        predicateFields.add(UnitField.UNIT_SEAL_NBR2);
        predicateFields.add(UnitField.UNIT_SEAL_NBR3);
        predicateFields.add(UnitField.UNIT_SEAL_NBR4);
        predicateFields.add(UnitField.UNIT_REMARK);
        predicateFields.add(IInventoryBizMetafield.UNIT_IMPEDIMENTS);
        predicateFields.add(UnitField.UNIT_FLEX_STRING01);
        predicateFields.add(UnitField.UNIT_FLEX_STRING02);
        predicateFields.add(UnitField.UNIT_FLEX_STRING03);
        predicateFields.add(UnitField.UNIT_FLEX_STRING04);
        predicateFields.add(UnitField.UNIT_FLEX_STRING05);
        predicateFields.add(UnitField.UNIT_FLEX_STRING06);
        predicateFields.add(UnitField.UNIT_FLEX_STRING07);
        predicateFields.add(UnitField.UNIT_FLEX_STRING08);
        predicateFields.add(UnitField.UNIT_FLEX_STRING09);
        predicateFields.add(UnitField.UNIT_FLEX_STRING10);
        predicateFields.add(UnitField.UNIT_FLEX_STRING11);
        predicateFields.add(UnitField.UNIT_FLEX_STRING12);
        predicateFields.add(UnitField.UNIT_FLEX_STRING13);
        predicateFields.add(UnitField.UNIT_FLEX_STRING14);
        predicateFields.add(UnitField.UNIT_FLEX_STRING15);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING01);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING02);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING03);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING04);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING05);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING06);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING07);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING08);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING09);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_STRING10);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE01);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE02);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE03);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE04);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE05);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE06);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE07);
        predicateFields.add(UnitField.UNIT_UFV_FLEX_DATE08);
        predicateFields.add(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING01);
        predicateFields.add(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING02);
        predicateFields.add(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING03);
        predicateFields.add(UnitField.UNIT_UFV_IS_CALLING_CSI_COUNTRY);
        predicateFields.add(UnitField.UNIT_PRIMARY_EQ_DAMAGE_SEVERITY);
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_EQ_GRADE_ID, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_DEPARTURE_ORDER_NBR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_ARRIVAL_ORDER_NBR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_EQ_OPERATOR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_EQ_OWNER, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IArgoRefField.EQ_EQUIP_TYPE, "Unit"));
        predicateFields.add(UnitField.UNIT_PRIMARY_RFR_TYPE);
        predicateFields.add(UnitField.UNIT_PRIMARY_EQ_RFR_TYPE);
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_ID), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IArgoRefField.EQ_ISO_GROUP, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IArgoRefField.EQ_MATERIAL, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IArgoRefField.EQ_CLASS, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.EQS_OFFHIRE_LOCATION, "Unit"));
        predicateFields.add(UnitField.UNIT_RTG_DECLARED_OB_CV);
        predicateFields.add(UnitField.UNIT_RTG_DECLARED_OB_CV_MODE);
        predicateFields.add(UnitField.UNIT_RTG_DECLARED_OB_CV_VEHICLE_TYPE);
        predicateFields.add(UnitField.UNIT_RTG_DECLARED_OB_CV_CLASSIFICATION);
        predicateFields.add(UnitField.UNIT_RTG_OPL);
        predicateFields.add(UnitField.UNIT_RTG_OPL_CNTRY);
        predicateFields.add(UnitField.UNIT_RTG_POL);
        predicateFields.add(UnitField.UNIT_RTG_POL_CNTRY);
        predicateFields.add(UnitField.UNIT_RTG_POD1);
        predicateFields.add(UnitField.UNIT_RTG_POD1_CNTRY);
        predicateFields.add(UnitField.UNIT_RTG_POD2);
        predicateFields.add(UnitField.UNIT_RTG_POD2_CNTRY);
        predicateFields.add(UnitField.UNIT_RTG_GROUP);
        predicateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UFV_DWELL_DAYS, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_IB_CV, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_IB_CARRIER_MODE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASS_TYPE), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASSIFICATION), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_INTENDED_OB_CV, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_INTENDED_OB_CARRIER_MODE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_INTENDED_OB_OPERATOR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_DECLARED_OB_OPERATOR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_INTENDED_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASS_TYPE), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_INTENDED_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASSIFICATION), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_OB_CV, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_IB_CVD_SERVICE, "Unit"));
        predicateFields.add(UnitField.UNIT_DECLARED_OB_CV_SERVICE);
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_OB_CVD_SERVICE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_INTENDED_CVD_SERVICE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_ACTUAL_OB_CARRIER_MODE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASS_TYPE), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UnitField.UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASSIFICATION), "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_TIME_IN, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_TIME_OUT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_TIME_OF_LAST_MOVE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_LAST_FREE_DAY, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_TRANSIT_STATE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_POS_NAME, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_VERIFIED_YARD_POSITION, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_FACILITY, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_RESTOW_TYPE, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_CONSIGNEE_BZU, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_SHIPPER_BZU, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_SHIPPER_NAME, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_SHIPPER_CREDIT_STATUS, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_CONSIGNEE_NAME, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_CONSIGNEE_CREDIT_STATUS, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_COMMODITY, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_ORIGIN, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_DESTINATION, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_SET_POINT_C, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_LIMIT_MAX_C, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_LIMIT_MIN_C, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_VENT_REQUIRED, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_VENT_UNIT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_HUMIDITY_PCT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_O2_PCT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_CO2_PCT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_LATEST_ON_POWER_TIME, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_REQUESTED_OFF_POWER_TIME, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.GDS_BL_NBR, "Unit"));
        predicateFields.add(UnitField.UNIT_GDS_IS_HAZARDOUS);
        predicateFields.add(UnitField.getQualifiedField(UnitField.UNIT_WAY_BILL_NBR, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UNIT_WAY_BILL_DATE, "Unit"));
        predicateFields.add(IInventoryBizMetafield.UNIT_IS_STORAGE_PAID);
        predicateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UNIT_IS_IMPORT_DELIVERY_ORDER_VALID, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UNIT_IS_BOOKING_TEMP_DIFFERENT, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.IS_BOOKING_HOLD_PARTIAL_QTY_RECEIVED, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UNIT_WEIGHT_DISCREPANCY, "Unit"));
        predicateFields.add(IInventoryBizMetafield.UNIT_IS_LINE_STORAGE_PAID);
        predicateFields.add(UnitField.getQualifiedField(UnitField.UFV_LINE_LAST_FREE_DAY, "Unit"));
        predicateFields.add(UnitField.UNIT_ID);
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_EQ_ID_PREFIX, "Unit"));
        predicateFields.add(UnitField.getQualifiedField(UnitField.UE_EQ_ID_NBR_ONLY, "Unit"));
        predicateFields.add(UnitField.UNIT_ARE_PLACARDS_MISMATCHED);
        predicateFields.add(UnitField.UNIT_EXAM);
        predicateFields.add(UnitField.UNIT_INBOND);
        predicateFields.add(UnitField.UNIT_RTG_BOND_DESTINATION);
        predicateFields.add(UnitField.UNIT_RTG_BOND_TRUCKING_COMPANY);
        predicateFields.add(UnitField.UNIT_AGENT1);
        predicateFields.add(UnitField.UNIT_AGENT2);
        predicateFields.add(UnitField.UNIT_AGENT1_MASTER);
        predicateFields.add(UnitField.UNIT_AGENT2_MASTER);
        predicateFields.add(UnitField.UNIT_AGENT1_CREDIT_STATUS);
        predicateFields.add(UnitField.UNIT_AGENT2_CREDIT_STATUS);
        predicateFields.add(UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS);
        predicateFields.add(UnitField.UNIT_VGM_ENTITY);
        predicateFields.add(UnitField.UNIT_GROSS_WEIGHT_SOURCE);
        predicateFields.add(UnitField.UNIT_MNR_STATUS);
        predicateFields.add(UnitField.UNIT_CONDITION_I_D);
        return predicateFields;
    }

    public static MetafieldIdList getUpdateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList updateFields = new MetafieldIdList();
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_DECLARED_IB_CV, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_CATEGORY, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FREIGHT_KIND, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_DRAY_STATUS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SPECIAL_STOW3, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_DECK_RQMNT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REQUIRES_POWER, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_IS_POWERED, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_BACK_CM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_FRONT_CM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_LEFT_CM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_RIGHT_CM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_OOG_TOP_CM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_LINE_OPERATOR, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_GOODS_AND_CTR_WT_KG, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR1, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR3, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_SEAL_NBR4, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REMARK, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING01, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING02, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING03, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING04, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING05, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING06, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING07, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING08, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING09, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING10, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING11, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING12, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING13, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING14, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_FLEX_STRING15, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING01, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING02, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING03, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING04, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING05, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING06, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING07, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING08, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING09, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_STRING10, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE01, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE02, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE03, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE04, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE05, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE06, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE07, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_UFV_FLEX_DATE08, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING01, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING02, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_EQS_FLEX_STRING03, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UFV_VERIFIED_FOR_LOAD, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UFV_VERIFIED_YARD_POSITION, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_PRIMARY_UE_GRADE, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.EQS_OFFHIRE_LOCATION, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_DECLARED_OB_CV, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POL, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POD1, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_POD2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT1, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_OPT3, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_GROUP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_RTG_TRUCKING_COMPANY, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_GOODS_AND_CTR_WT_KG, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_GROSS_WEIGHT_SOURCE, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_CONSIGNEE_BZU, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_SHIPPER_BZU, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_COMMODITY, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_ORIGIN, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_DESTINATION, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_SET_POINT_C, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_LIMIT_MAX_C, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TEMP_LIMIT_MIN_C, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_VENT_REQUIRED, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_VENT_UNIT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_HUMIDITY_PCT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_O2_PCT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_CO2_PCT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_LATEST_ON_POWER_TIME, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_REQUESTED_OFF_POWER_TIME, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_BL_NBR, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TIME_MONITOR1, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TIME_MONITOR2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TIME_MONITOR3, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.GDS_RFREQ_TIME_MONITOR4, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_TIME, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_RETURN_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_VENT_SETTING, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_VENT_UNIT, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_HMDTY, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_O2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_CO2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_IS_POWERED, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_IS_BULB_ON, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DEFROST_INTERVAL_HOURS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DRAINS_OPEN, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_FAN_SETTING, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_MIN_MONITORED_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_MAX_MONITORED_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_DEFROST_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_SET_POINT_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_SUPPLY_TMP, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_FUEL_LEVEL, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_REFRECORD_REMARK, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT1, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT2, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT1_MASTER, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT2_MASTER, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT1_CREDIT_STATUS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_AGENT2_CREDIT_STATUS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_EXAM, "Unit"));
        updateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS_VALUES, "Unit"));
        updateFields.add(UnitField.getQualifiedField(IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VGM_VALUES, "Unit"));
        updateFields.add(UnitField.getQualifiedField(IInventoryField.UNIT_VGM_ENTITY, "Unit"));
        updateFields.add(UnitField.getQualifiedField(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_ADVISED, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_MNR_STATUS, "Unit"));
        updateFields.add(UnitField.getQualifiedField(UnitField.UNIT_CONDITION_I_D, "Unit"));
        updateFields.add(UnitField.UNIT_RTG_BOND_TRUCKING_COMPANY);
        return updateFields;
    }

    public String getUnitGrantedHoldsPermissions() {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Set impediments = sm.getVetoedFlagIds(this.getGuardianArray(), (ILogicalEntity)this);
        return ArgoUtils.createStringFromSetValues((Collection)impediments, (String)",");
    }

    public static IPredicate formUnitGrantedHoldsPermissionsPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predCtr = sm.createVetoedFlagPredicate(inValue, LogicalEntityEnum.EQ, UnitField.UFV_PRIMARY_EQS_GKEY);
        IPredicate predChs = sm.createVetoedFlagPredicate(inValue, LogicalEntityEnum.EQ, UnitField.UFV_CARRIAGE_EQS_GKEY);
        IPredicate predUnit = sm.createVetoedFlagPredicate(inValue, LogicalEntityEnum.UNIT, UnitField.UFV_UNIT_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.not((IPredicate)predCtr)).add(PredicateFactory.not((IPredicate)predUnit)).add(PredicateFactory.not((IPredicate)predChs));
        }
        return PredicateFactory.disjunction().add(predCtr).add(predUnit).add(predChs);
    }

    public static IPredicate formUnitHoldsPermissionsAppliedOnPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqField(IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_APPLIED_TO_CLASS, (Object)"Unit"));
        IPredicate intf = PredicateFactory.createPredicate((UserContext)UserContextUtils.getSystemUserContext(), (IMetafieldId)IServicesField.FLAG_APPLIED_DATE, (PredicateVerbEnum)inVerb, (Object)inValue);
        dq.addDqPredicate(intf);
        return PredicateFactory.subQueryIn((IDomainQuery)dq, (IMetafieldId)UnitField.UFV_UNIT_GKEY);
    }

    public static IPredicate formUnitHoldsPermissionsUpdatedOnPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IMetafieldId vetoAppDate = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesField.FLAG_VETOS, (IMetafieldId)IServicesField.VETO_APPLIED_DATE);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqField(IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.FLAG_APPLIED_TO_CLASS, (Object)"Unit"));
        if (PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            IDomainQuery subDq = QueryUtils.createDomainQuery((String)"Veto").addDqField(IServicesField.VETO_BLOCKED_FLAG).addDqPredicate(PredicateFactory.eqProperty((IMetafieldId)IServicesField.VETO_BLOCKED_FLAG, (IMetafieldId) OuterQueryMetafieldId.valueOf((IMetafieldId)IServicesField.FLAG_GKEY)));
            dq.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.subQueryExists((IDomainQuery)subDq)));
        } else {
            IPredicate intf = PredicateFactory.createPredicate((UserContext) UserContextUtils.getSystemUserContext(), (IMetafieldId)vetoAppDate, (PredicateVerbEnum)inVerb, (Object)inValue);
            dq.addDqPredicate(intf);
        }
        return PredicateFactory.subQueryIn((IDomainQuery)dq, (IMetafieldId)UnitField.UFV_UNIT_GKEY);
    }

    public static IPredicate formUnitAppliedHoldOrPermViewPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predCtr = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.EQ, UnitField.UFV_PRIMARY_EQS_GKEY);
        IPredicate predChs = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.EQ, UnitField.UFV_CARRIAGE_EQS_GKEY);
        IPredicate predUnit = sm.createActiveFlagPredicateForHpv(inValue, LogicalEntityEnum.UNIT, UnitField.UFV_UNIT_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.not((IPredicate)predCtr)).add(PredicateFactory.not((IPredicate)predUnit)).add(PredicateFactory.not((IPredicate)predChs));
        }
        return PredicateFactory.disjunction().add(predCtr).add(predUnit).add(predChs);
    }

    @Nullable
    public EqBaseOrder getDepartureOrder() {
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue == null) {
            return null;
        }
        EqBaseOrderItem eqoi = ue.getUeDepartureOrderItem();
        return eqoi == null ? null : eqoi.getEqboiOrder();
    }

    @Nullable
    public EqBaseOrder getArrivalOrder() {
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue == null) {
            return null;
        }
        EqBaseOrderItem eqoi = ue.getUeArrivalOrderItem();
        return eqoi == null ? null : eqoi.getEqboiOrder();
    }

    public boolean isAssignedForBooking() {
        if (this.getUnitPrimaryUe().getUeDepartureOrderItem() != null && (UnitCategoryEnum.EXPORT.equals((Object)this.getUnitCategory()) || UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory()))) {
            LocPosition recentPosition;
            boolean hasDepartedFcy;
            UnitFacilityVisit ufv = this.getUnitActiveUfv();
            boolean bl = hasDepartedFcy = UnitVisitStateEnum.DEPARTED.equals((Object)this.getUnitVisitState()) || ufv != null && UfvTransitStateEnum.S70_DEPARTED.equals((Object)ufv.getUfvTransitState());
            return !hasDepartedFcy || !DrayStatusEnum.RETURN.equals((Object)this.getUnitDrayStatus()) || (recentPosition = this.findMostRecentPosition()) == null || !LocTypeEnum.TRUCK.equals((Object)recentPosition.getPosLocType());
        }
        return false;
    }

    @Nullable
    public LocPosition findMostRecentPosition() {
        UnitFacilityVisit ufv = this.findMostRecentHistoryUfv();
        if (ufv != null) {
            return ufv.getUfvLastKnownPosition();
        }
        return null;
    }

    @Nullable
    public UnitFacilityVisit findMostRecentHistoryUfv() {
        Date lastTime = new Date(0L);
        UnitFacilityVisit mostRecentUfv = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object ufv : ufvSet) {
                if (!((UnitFacilityVisit)ufv).isComplete()) continue;
                Date ufvTimeComplete = ((UnitFacilityVisit)ufv).getUfvTimeComplete();
                if (ufvTimeComplete == null) {
                    LOGGER.error((Object)("getUfvTimeComplete: Unit has a completed UFV with a null timestamp: " + this));
                } else if (lastTime.before(ufvTimeComplete)) {
                    lastTime = ufvTimeComplete;
                }
                mostRecentUfv = ((UnitFacilityVisit)ufv);
            }
        }
        return mostRecentUfv;
    }

    public void updateAgent1(ScopedBizUnit inAgent) {
        this.setUnitAgent1(inAgent);
    }

    public void updateAgent2(ScopedBizUnit inAgent) {
        this.setUnitAgent2(inAgent);
    }

    @Nullable
    public Date getUnitComplexInTime() {
        Date complexInTime = null;
        Date complexEcInTime = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitFacilityVisit").addDqField(IInventoryField.UFV_TIME_IN).addDqField(IInventoryField.UFV_TIME_EC_IN).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.UFV_UNIT, (Object)this.getPrimaryKey())).addDqPredicate((IPredicate)PredicateFactory.disjunction().add(PredicateFactory.isNotNull((IMetafieldId)IInventoryField.UFV_TIME_IN)).add(PredicateFactory.isNotNull((IMetafieldId)IInventoryField.UFV_TIME_EC_IN)));
        dq.addDqOrdering(Ordering.asc((IMetafieldId)IInventoryField.UFV_TIME_IN));
        dq.setScopingEnabled(Boolean.FALSE.booleanValue());
        List ufvs = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (!ufvs.isEmpty()) {
            UnitFacilityVisit ufv = (UnitFacilityVisit)ufvs.get(0);
            complexInTime = ufv.getUfvTimeIn();
            complexEcInTime = ufv.getUfvTimeEcIn();
        }
        if (complexEcInTime != null) {
            return complexEcInTime;
        }
        return complexInTime;
    }

    @Nullable
    public Date getUnitComplexOutTime() {
        Date complexOutTime = null;
        Date complexEcOutTime = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitFacilityVisit").addDqField(IInventoryField.UFV_TIME_OUT).addDqField(IInventoryField.UFV_TIME_EC_OUT).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.UFV_UNIT, (Object)this.getPrimaryKey())).addDqOrdering(Ordering.desc((IMetafieldId)IInventoryField.UFV_TIME_OUT));
        dq.setScopingEnabled(Boolean.FALSE.booleanValue());
        dq.setDqMaxResults(1);
        List ufvList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (ufvList == null || ufvList.isEmpty()) {
            return null;
        }
        UnitFacilityVisit ufv = (UnitFacilityVisit)ufvList.get(0);
        complexOutTime = ufv.getUfvTimeOut();
        complexEcOutTime = ufv.getUfvTimeEcOut();
        if (complexOutTime != null) {
            return complexOutTime;
        }
        return complexEcOutTime;
    }

    public void updateInbond(InbondEnum inInbondStatus) {
        this.setUnitInbond(inInbondStatus);
    }

    public void updateBondedTrkc(ScopedBizUnit inBlBondTrkc) {
        Routing routing = this.getUnitRouting();
        if (routing != null) {
            routing.setRtgBondTruckingCompany(inBlBondTrkc);
        }
    }

    public void updateBondedDestination(String inBlBondDest) {
        Routing routing = this.getUnitRouting();
        if (routing != null) {
            routing.setRtgBondedDestination(inBlBondDest);
        }
    }

    public void updateExam(ExamEnum inExamStatus) {
        this.setUnitExam(inExamStatus);
    }

    public void updateUnitGoodsAndCtrWtKgGateMeasured(Double inUnitGdsAndCtrWtKgGateMeasured) {
        this.setUnitGoodsAndCtrWtKgGateMeasured(inUnitGdsAndCtrWtKgGateMeasured);
    }

    public void updateUnitGoodsAndCtrWtKgYardMeasured(Double inUnitGoodsAndCtrWtKgYardMeasured) {
        this.setUnitGoodsAndCtrWtKgYardMeasured(inUnitGoodsAndCtrWtKgYardMeasured);
    }

    public String getReeferCreationRefId() {
        RequestUuid uniqueRequestId = TransactionParms.getBoundParms().getUniqueId();
        return uniqueRequestId.toString() + this.getUnitId();
    }

    @Nullable
    public Unit getCarriageUnit() {
        if (this.getUnitRelatedUnit() == null) {
            return null;
        }
        return EqUnitRoleEnum.CARRIAGE.equals((Object)this.getUnitRelatedUnit().getUnitEqRole()) ? this.getUnitRelatedUnit() : null;
    }

    @Nullable
    public UnitFacilityVisit getUnitActiveUfvNowActive() {
        UnitFacilityVisit ufv = super.getUnitActiveUfv();
        return ufv != null && UnitVisitStateEnum.ACTIVE.equals((Object)ufv.getUfvVisitState()) ? ufv : null;
    }

    public LocPosition findCurrentPosition() {
        UnitFacilityVisit ufv;
        LocPosition pos = null;
        if (this.isActive()) {
            UnitFacilityVisit ufv2 = this.getUnitActiveUfvNowActive();
            pos = ufv2 != null ? ufv2.getUfvLastKnownPosition() : this.findMostRecentPosition();
        } else if (this.getUnitVisitState() == UnitVisitStateEnum.DEPARTED) {
            pos = this.findMostRecentPosition();
        } else if (this.getUnitVisitState() == UnitVisitStateEnum.ADVISED && (ufv = this.findProbableInitialUfv()) != null) {
            pos = ufv.getUfvArrivePosition();
        }
        return pos != null ? pos : LocPosition.getUnknownPosition();
    }

    @Nullable
    public UnitFacilityVisit findProbableInitialUfv() {
        UnitFacilityVisit mostLikelyUfv = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object ufv : ufvSet) {
                CarrierVisit cv;
                if (!((UnitFacilityVisit)ufv).isFuture() || (cv = ((UnitFacilityVisit)ufv).getInboundCarrierVisit()) == null) continue;
                if (cv.getCvCarrierMode() == LocTypeEnum.VESSEL) {
                    return ((UnitFacilityVisit)ufv);
                }
                if (cv.getCvCarrierMode() == LocTypeEnum.TRUCK) {
                    if (mostLikelyUfv != null) continue;
                    mostLikelyUfv = ((UnitFacilityVisit)ufv);
                    continue;
                }
                mostLikelyUfv = ((UnitFacilityVisit)ufv);
            }
        }
        return mostLikelyUfv;
    }

    @Nullable
    public final UnitFacilityVisit getUfvForFacilityNewest(Facility inFacility) {
        UnitFacilityVisit ufv = this.getUfvForFacilityLiveOnly(inFacility);
        if (ufv == null) {
            ufv = this.getUfvForFacilityCompletedOnly(inFacility);
        }
        return ufv;
    }

    @Nullable
    public final UnitFacilityVisit getUfvForFacilityLiveOnly(Facility inFacility) {
        UnitFacilityVisit bestUfv = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object thisUfv : ufvSet) {
                if (!((UnitFacilityVisit)thisUfv).isForFacility(inFacility) || ((UnitFacilityVisit)thisUfv).isComplete()) continue;
                if (bestUfv == null) {
                    bestUfv = ((UnitFacilityVisit)thisUfv);
                    continue;
                }
                LOGGER.error((Object)("getUfvForFacilityLiveOnly: more than one active/advised UFV: " + thisUfv));
            }
        }
        return bestUfv;
    }

    @Nullable
    public final UnitFacilityVisit getUfvForFacilityCompletedOnly(Facility inFacility) {
        UnitFacilityVisit bestUfv = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object thisUfv : ufvSet) {
                if (!((UnitFacilityVisit)thisUfv).isForFacility(inFacility) || !((UnitFacilityVisit)thisUfv).isComplete()) continue;
                if (bestUfv == null) {
                    bestUfv = ((UnitFacilityVisit)thisUfv);
                    continue;
                }
                Date tBest = bestUfv.getUfvTimeComplete();
                Date tThis = ((UnitFacilityVisit)thisUfv).getUfvTimeComplete();
                if (tBest == null || tThis == null) {
                    LOGGER.error((Object)("getUfvForFacilityCompletedOnly: Unit has a completed UFV with a null timestamp: " + this));
                    if (tBest != null) continue;
                    bestUfv = ((UnitFacilityVisit)thisUfv);
                    continue;
                }
                if (!tThis.after(tBest)) continue;
                bestUfv = ((UnitFacilityVisit)thisUfv);
            }
        }
        return bestUfv;
    }

    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote) {
        return this.recordUnitEvent(inUnitEventType, inAlreadyUpdatedFields, inEventNote, null);
    }

    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime) {
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IEvent evnt = null;
        try {
            evnt = srvcMgr.recordEvent(inUnitEventType, inEventNote, null, null, (IServiceable)this, inAlreadyUpdatedFields, inEventTime);
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inUnitEventType.getId() + " on " + this + " : " + e + " (ignored)"));
        }
        return evnt;
    }

    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, @Nullable FieldChanges inAlreadyUpdatedFields, String inEventNote, @Nullable Date inEventTime, IEntity inRelatedEntity) {
        return this.recordUnitEvent(inUnitEventType, inAlreadyUpdatedFields, inEventNote, inEventTime, inRelatedEntity, null);
    }

    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime, IEntity inRelatedEntity, FieldChanges inOutMoreChanges) {
        if (inRelatedEntity.getPrimaryKey() == null) {
            HibernateApi.getInstance().save((Object)inRelatedEntity);
        }
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IEvent evnt = null;
        try {
            evnt = srvcMgr.recordEvent(inUnitEventType, inEventNote, null, null, (IServiceable)this, inAlreadyUpdatedFields, inEventTime, inRelatedEntity, inOutMoreChanges);
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inUnitEventType.getId() + " on " + this + " : " + e + " (ignored)"));
        }
        return evnt;
    }

    public boolean isActive() {
        return UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState());
    }

    public boolean isLiveUnit() {
        return this.getUnitVisitState() == UnitVisitStateEnum.ADVISED || this.getUnitVisitState() == UnitVisitStateEnum.ACTIVE;
    }

    public final void applyUpdateOnUnitCombo(IUnitUpdate inUnitUpdate) throws BizViolation {
        if (inUnitUpdate.applyOnAll(this.thisUnit())) {
            Collection<Unit> unitsInCombo = this.ensureUnitSet();
            if (unitsInCombo.isEmpty()) {
                inUnitUpdate.apply(this.thisUnit());
                return;
            }
            inUnitUpdate.apply(this.thisUnit());
            for (Unit unit : unitsInCombo) {
                if (unit.equals(this) || !inUnitUpdate.allowUpdate(unit)) continue;
                inUnitUpdate.apply(unit);
            }
        } else {
            inUnitUpdate.apply(this.thisUnit());
        }
    }

    void applyMasterRoutingOnPayloadUnits() throws BizViolation {
        if (this.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) {
            return;
        }
        if (this.hasNoneInPayloadRole()) {
            return;
        }
        this.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) {
                inUnit.copyRoutingFromMaster();
            }

            @Override
            public boolean allowUpdate(Unit inUnit) {
                return inUnit.getUnitEqRole() == EqUnitRoleEnum.PAYLOAD;
            }
        });
    }

    void copyRoutingFromMaster() {
        Unit masterUnit = this.getUnitRelatedUnit();
        if (masterUnit == null) {
            return;
        }
        if (this.getUnitEqRole() != EqUnitRoleEnum.PAYLOAD) {
            return;
        }
        if (this.getUnitCategory() != UnitCategoryEnum.EXPORT && this.getUnitCategory() != UnitCategoryEnum.TRANSSHIP) {
            return;
        }
        IMetafieldId[] rtgFields = new IMetafieldId[]{UnitField.UNIT_RTG_POD1, UnitField.UNIT_RTG_POD2, UnitField.UNIT_DESTIN, UnitField.UNIT_RTG_DECLARED_OB_CV};
        FieldChanges fcs = new FieldChanges();
        for (IMetafieldId rtgField : rtgFields) {
            Object oldValue = this.getFieldValue(rtgField);
            Object newValue = masterUnit.getFieldValue(rtgField);
            if (oldValue == null && newValue == null) continue;
            this.setFieldValue(rtgField, newValue);
            fcs.setFieldChange(rtgField, oldValue, newValue);
        }
        this.recordUnitEvent((IEventType)EventEnum.UNIT_REROUTE, fcs, null);
    }

    public boolean hasOneUeInPayloadRole() {
        return this.getAttachedPayloadCount() == 1;
    }

    public boolean hasNoneInPayloadRole() {
        return this.getAttachedPayloadCount() == 0;
    }

    public int getAttachedPayloadCount() {
        Set ueSet = this.getUnitUeSet();
        int i = 0;
        if (ueSet != null) {
            for (Object ue : ueSet) {
                if (!EqUnitRoleEnum.PAYLOAD.equals((Object)((UnitEquipment)ue).getUeEqRole())) continue;
                ++i;
            }
        }
        return i;
    }

    @Nullable
    public EquipGrade getEqsGradeID() {
        EquipmentState eqs = this.getUnitEquipmentState();
        return eqs == null ? null : eqs.getEqsGradeID();
    }

    @Override
    @Nullable
    public EquipGrade getUnitGradeID() {
        EquipGrade grade = super.getUnitGradeID();
        if (grade != null) {
            return grade;
        }
        grade = this.getEqsGradeID();
        return grade != null && grade.getGradeIDPersisted() ? grade : null;
    }

    public void postEventCreation(IEvent inEvent) {
        Event event = (Event)inEvent;
        if (!UnitEventMap.containsRoleEvent((IEventType)EventEnum.getEnum((String)event.getEvntEventType().getId()))) {
            return;
        }
        TransactionParms params = TransactionParms.getBoundParms();
        if (this.getUnitEqRole() == EqUnitRoleEnum.PRIMARY) {
            params.putApplicationParm((Object)UnitEventMap.getEventKey(this.getUnitGkey(), event.getEventTypeId()), (Object)event);
        } else {
            IEventType primaryEventType;
            Long primaryUnitGkey = null;
            UnitCombo uc = this.getUnitCombo();
            if (uc == null) {
                return;
            }
            Collection<Unit> unitSet = this.ensureUnitSet();
            for (Unit ucUnit : unitSet) {
                if (ucUnit.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) continue;
                primaryUnitGkey = ucUnit.getUnitGkey();
                break;
            }
            if (primaryUnitGkey == null) {
                LOGGER.error((Object)("Cannot populate primary event as no primary unit found for " + this));
                return;
            }
            if (event.getEvntRelatedEntityGkey() == null) {
                Unit relatedUnit = Unit.hydrate(primaryUnitGkey);
                event.updateRelatedEntity((IEntity)relatedUnit);
            }
            if ((primaryEventType = UnitEventMap.getPrimaryEventType((IEventType)EventEnum.getEnum((String)event.getEvntEventType().getId()))) == null) {
                LOGGER.error((Object)("No primary event type found for " + event.getEvntEventType().getId()));
                return;
            }
            Event primaryEvent = (Event)params.getApplicationParm((Object)UnitEventMap.getEventKey(primaryUnitGkey, primaryEventType.getId()));
            if (primaryEvent != null) {
                event.updateRelatedEvent(primaryEvent);
            }
        }
    }

    protected static IUnitFinder getFndr() {
        return (IUnitFinder)Roastery.getBean((String)"unitFinder");
    }

}
