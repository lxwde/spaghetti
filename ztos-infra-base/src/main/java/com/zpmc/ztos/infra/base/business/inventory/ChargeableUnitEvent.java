package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.ChargeableUnitEventDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Guarantee;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.DateUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ChargeableUnitEvent extends ChargeableUnitEventDO implements IServiceExtract{
    static final String STORAGE = "STORAGE";
    static final String LINE_STORAGE = "LINE_STORAGE";
    static final String REEFER = "REEFER";

    public Class getArchiveClass() {
     //   return ArchiveChargeableUnitEvent.class;
        return null;
    }

    public boolean doArchive() {
        return ArgoConfig.ARCHIVE_CHG_UNIT_EVENTS_PRIOR_TO_PURGE.isOn(ContextHelper.getThreadUserContext());
    }

    public ChargeableUnitEvent() {
        this.setBexuIsHazardous(Boolean.FALSE);
        this.setBexuIsLocked(Boolean.FALSE);
        this.setBexuIsRefrigerated(Boolean.FALSE);
        this.setBexuIsOog(Boolean.FALSE);
        this.setBexuIsOverrideValue(Boolean.FALSE);
        this.setBexuIsBundle(Boolean.FALSE);
    }

    public static ChargeableUnitEvent create(Long inUfvGkey, Operator inOperator) {
        ChargeableUnitEvent event = new ChargeableUnitEvent();
        event.setBexuUfvGkey(inUfvGkey);
        event.setBexuTerminalOperator(inOperator);
        HibernateApi.getInstance().save((Object)event);
        return event;
    }

    @Override
    public String getServiceId() {
        return this.getBexuEventType();
    }

    @Override
    public Serializable getServiceExtractGkey() {
        return this.getBexuGkey();
    }

    @Override
    public Double getServiceExtractQuantity() {
        return this.getBexuQuantity();
    }

    @Override
    public Date getServiceGuaranteeThruDay() {
        return this.getBexuGuaranteeThruDay();
    }

    @Override
    public String getServiceExtractQuantityUnit() {
        return this.getBexuQuantityUnit();
    }

    @Override
    public double getNullSafeQuantity() {
        Double qty = this.getBexuQuantity();
        return qty == null ? 1.0 : qty;
    }

    @Override
    public boolean isInventoryStorageService() {
        return STORAGE.equals(this.getBexuEventType());
    }

    @Override
    public boolean isInventoryLineStorageService() {
        return LINE_STORAGE.equals(this.getBexuEventType());
    }

    @Override
    public boolean isInventoryReeferService() {
        return REEFER.equals(this.getBexuEventType());
    }

    @Override
    public String getServiceEntityId() {
        return this.getBexuEqId();
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        EquipType equipType;
        Object value;
        if (inChanges.hasFieldChange(IArgoExtractField.BEXU_ISO_CODE) && (value = inChanges.getFieldChange(IArgoExtractField.BEXU_ISO_CODE).getNewValue()) instanceof String && (equipType = EquipType.findEquipType((String)value)) != null) {
            String height = equipType.getEqtypNominalHeight().getKey();
            String isoGroup = equipType.getEqtypIsoGroup().getKey();
            String subClass = equipType.getEqtypClass().getKey();
            String length = equipType.getEqtypNominalLength().getKey();
            inOutMoreChanges.setFieldChange(IArgoExtractField.BEXU_ISO_GROUP, (Object)isoGroup);
            inOutMoreChanges.setFieldChange(IArgoExtractField.BEXU_ISO_HEIGHT, (Object)height);
            inOutMoreChanges.setFieldChange(IArgoExtractField.BEXU_ISO_LENGTH, (Object)length);
            inOutMoreChanges.setFieldChange(IArgoExtractField.BEXU_EQ_SUB_CLASS, (Object)subClass);
        }
    }

    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        Object value = inFieldValue;
        if (inFieldValue instanceof AtomizedEnum) {
            value = ((AtomizedEnum)inFieldValue).getKey();
        }
        super.setFieldValue(inFieldId, value);
    }

    public boolean isLocked() {
        return false;
    }

    public static MetafieldIdList getPredicateFields() {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(IArgoExtractField.BEXU_BATCH_ID);
        ids.add(IArgoExtractField.BEXU_BL_NBR);
        ids.add(IArgoExtractField.BEXU_BOOKING_NBR);
        ids.add(IArgoExtractField.BEXU_BUNDLE_UNIT_ID);
        ids.add(IArgoExtractField.BEXU_CATEGORY);
        ids.add(IArgoExtractField.BEXU_COMMODITY_ID);
        ids.add(IArgoExtractField.BEXU_COMPLEX_ID);
        ids.add(IArgoExtractField.BEXU_CONSIGNEE_ID);
        ids.add(IArgoExtractField.BEXU_DRAY_STATUS);
        ids.add(IArgoExtractField.BEXU_EQ_ID);
        ids.add(IArgoExtractField.BEXU_EQ_SUB_CLASS);
        ids.add(IArgoExtractField.BEXU_EVENT_END_TIME);
        ids.add(IArgoExtractField.BEXU_EVENT_START_TIME);
        ids.add(IArgoExtractField.BEXU_EVENT_TYPE);
        ids.add(IArgoExtractField.BEXU_FACILITY_ID);
        ids.add(IArgoExtractField.BEXU_FIRST_AVAILABILITY);
        ids.add(IArgoExtractField.BEXU_FINAL_DESTINATION);
        ids.add(IArgoExtractField.BEXU_FIRE_CODE);
        ids.add(IArgoExtractField.BEXU_FIRE_CODE_CLASS);
        ids.add(IArgoExtractField.BEXU_FLEX_DATE01);
        ids.add(IArgoExtractField.BEXU_FLEX_DATE02);
        ids.add(IArgoExtractField.BEXU_FLEX_DATE03);
        ids.add(IArgoExtractField.BEXU_FLEX_DATE04);
        ids.add(IArgoExtractField.BEXU_FLEX_DATE05);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG01);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG02);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG03);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG04);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG05);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING01);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING02);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING03);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING04);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING05);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING06);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING07);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING08);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING09);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING10);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING11);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING12);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING13);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING14);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING15);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING16);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING17);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING18);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING19);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING20);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING21);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING22);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING23);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING24);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING25);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING26);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING27);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING28);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING29);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING30);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING31);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING32);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING33);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING34);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING35);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING36);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING37);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING38);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING39);
        ids.add(IArgoExtractField.BEXU_FLEX_STRING40);
        ids.add(IArgoExtractField.BEXU_FM_POS_LOC_ID);
        ids.add(IArgoExtractField.BEXU_FM_POS_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_FM_POSITION_NAME);
        ids.add(IArgoExtractField.BEXU_FM_POSITION_SLOT);
        ids.add(IArgoExtractField.BEXU_FREIGHT_KIND);
        ids.add(IArgoExtractField.BEXU_GUARANTEE_PARTY);
        ids.add(IArgoExtractField.BEXU_GUARANTEE_THRU_DAY);
        ids.add(IArgoExtractField.BEXU_IS_OVERRIDE_VALUE);
        ids.add(IArgoExtractField.BEXU_OVERRIDE_VALUE_TYPE);
        ids.add(IArgoExtractField.BEXU_OVERRIDE_VALUE);
        ids.add(IArgoExtractField.BEXU_GUARANTEE_ID);
        ids.add(IArgoExtractField.BEXU_GUARANTEE_GKEY);
        ids.add(IArgoExtractField.BEXU_GKEY);
        ids.add(IArgoExtractField.BEXU_UFV_GKEY);
        ids.add(IArgoExtractField.BEXU_UNIT_GKEY);
        ids.add(IArgoExtractField.BEXU_IB_CALL_NBR);
        ids.add(IArgoExtractField.BEXU_IB_CARRIER_A_T_A);
        ids.add(IArgoExtractField.BEXU_IB_CARRIER_A_T_D);
        ids.add(IArgoExtractField.BEXU_IB_CARRIER_E_T_A);
        ids.add(IArgoExtractField.BEXU_IB_CARRIER_LINE_ID);
        ids.add(MetafieldIdFactory.valueOf((String)"bexuLineOperatorName"));
        ids.add(IArgoExtractField.BEXU_IB_CARRIER_NAME);
        ids.add(IArgoExtractField.BEXU_IB_ID);
        ids.add(IArgoExtractField.BEXU_IB_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_IB_SERVICE_ID);
        ids.add(IArgoExtractField.BEXU_IB_VESSEL_TYPE);
        ids.add(IArgoExtractField.BEXU_IB_VISIT_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CALL_NBR);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CARRIER_A_T_A);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CARRIER_A_T_D);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CARRIER_E_T_A);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CARRIER_LINE_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_CARRIER_NAME);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_SERVICE_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_VISIT_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_VESSEL_CLASS_ID);
        ids.add(IArgoExtractField.BEXU_IBINTENDED_VESSEL_TYPE);
        ids.add(IArgoExtractField.BEXU_IMDG_CLASS);
        ids.add(IArgoExtractField.BEXU_IS_HAZARDOUS);
        ids.add(IArgoExtractField.BEXU_IS_LOCKED);
        ids.add(IArgoExtractField.BEXU_IS_OOG);
        ids.add(IArgoExtractField.BEXU_IS_REFRIGERATED);
        ids.add(IArgoExtractField.BEXU_IS_BUNDLE);
        ids.add(IArgoExtractField.BEXU_ISO_CODE);
        ids.add(IArgoExtractField.BEXU_ISO_GROUP);
        ids.add(IArgoExtractField.BEXU_ISO_HEIGHT);
        ids.add(IArgoExtractField.BEXU_ISO_LENGTH);
        ids.add(IArgoExtractField.BEXU_LINE_OPERATOR_ID);
        ids.add(IArgoExtractField.BEXU_OB_CALL_NBR);
        ids.add(IArgoExtractField.BEXU_OB_CARRIER_A_T_A);
        ids.add(IArgoExtractField.BEXU_OB_CARRIER_A_T_D);
        ids.add(IArgoExtractField.BEXU_OB_CARRIER_E_T_A);
        ids.add(IArgoExtractField.BEXU_OB_CARRIER_LINE_ID);
        ids.add(IArgoExtractField.BEXU_OB_CARRIER_NAME);
        ids.add(IArgoExtractField.BEXU_OB_ID);
        ids.add(IArgoExtractField.BEXU_OB_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_OB_SERVICE_ID);
        ids.add(IArgoExtractField.BEXU_OB_VESSEL_TYPE);
        ids.add(IArgoExtractField.BEXU_OB_VESSEL_CLASS_ID);
        ids.add(IArgoExtractField.BEXU_OB_VISIT_ID);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CALL_NBR);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CARRIER_A_T_A);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CARRIER_A_T_D);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CARRIER_E_T_A);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CARRIER_LINE_ID);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_CARRIER_NAME);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_ID);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_SERVICE_ID);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_VESSEL_CLASS_ID);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_VESSEL_TYPE);
        ids.add(IArgoExtractField.BEXU_OBINTENDED_VISIT_ID);
        ids.add(IArgoExtractField.BEXU_PAYEE_CUSTOMER_ID);
        ids.add(IArgoExtractField.BEXU_PAYEE_ROLE);
        ids.add(IArgoExtractField.BEXU_PAID_THRU_DAY);
        ids.add(IArgoExtractField.BEXU_POD1);
        ids.add(IArgoExtractField.BEXU_POL1);
        ids.add(IArgoExtractField.BEXU_QUAY_CHE_ID);
        ids.add(IArgoExtractField.BEXU_QUANTITY);
        ids.add(IArgoExtractField.BEXU_QUANTITY_UNIT);
        ids.add(IArgoExtractField.BEXU_REHANDLE_COUNT);
        ids.add(IArgoExtractField.BEXU_RESTOW_ACCOUNT);
        ids.add(IArgoExtractField.BEXU_RESTOW_REASON);
        ids.add(IArgoExtractField.BEXU_RESTOW_TYPE);
        ids.add(IArgoExtractField.BEXU_RULE_START_DAY);
        ids.add(IArgoExtractField.BEXU_RULE_END_DAY);
        ids.add(IArgoExtractField.BEXU_SERVICE_ORDER);
        ids.add(IArgoExtractField.BEXU_SHIPPER_ID);
        ids.add(IArgoExtractField.BEXU_SPECIAL_STOW);
        ids.add(IArgoExtractField.BEXU_STATUS);
        ids.add(IArgoExtractField.BEXU_TERMINAL_OPERATOR);
        ids.add(IArgoExtractField.BEXU_TIME_DISCHARGE_COMPLETE);
        ids.add(IArgoExtractField.BEXU_TIME_FIRST_FREE_DAY);
        ids.add(IArgoExtractField.BEXU_TIME_OF_LOADING);
        ids.add(IArgoExtractField.BEXU_UFV_TIME_IN);
        ids.add(IArgoExtractField.BEXU_UFV_TIME_OUT);
        ids.add(IArgoExtractField.BEXU_TO_POS_LOC_ID);
        ids.add(IArgoExtractField.BEXU_TO_POS_LOC_TYPE);
        ids.add(IArgoExtractField.BEXU_TO_POSITION_SLOT);
        ids.add(IArgoExtractField.BEXU_TO_POSITION_NAME);
        ids.add(IArgoBizMetafield.PERFORMED_DAY_OF_WEEK_UNIT);
        ids.add(IArgoBizMetafield.PERFORMED_TIME_OF_DAY_UNIT);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE01);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE02);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE03);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE04);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE05);
        ids.add(IArgoExtractField.BEXU_CARGO_QUANTITY);
        ids.add(IArgoExtractField.BEXU_CARGO_QUANTITY_UNIT);
        ids.add(IArgoExtractField.BEXU_EQ_OWNER_ID);
        ids.add(IArgoExtractField.BEXU_IB_VESSEL_LLOYDS_ID);
        ids.add(IArgoExtractField.BEXU_OB_VESSEL_LLOYDS_ID);
        ids.add(IArgoExtractField.BEXU_EQ_ROLE);
        ids.add(IArgoExtractField.BEXU_VERIFIED_GROSS_MASS);
        ids.add(IArgoExtractField.BEXU_VGM_VERIFIER_ENTITY);
        return ids;
    }

    public static MetafieldIdList getNumericPredicateFields() {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(IArgoExtractField.BEXU_QUANTITY);
        ids.add(IArgoExtractField.BEXU_QUANTITY_UNIT);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG01);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG02);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG03);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG04);
        ids.add(IArgoExtractField.BEXU_FLEX_LONG05);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE01);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE02);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE03);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE04);
        ids.add(IArgoExtractField.BEXU_FLEX_DOUBLE05);
        ids.add(IArgoExtractField.BEXU_CARGO_QUANTITY);
        return ids;
    }

    @Override
    public IMetafieldId getStatusFieldId() {
        IMetafieldId statusId = IArgoExtractField.BEXU_STATUS;
        return statusId;
    }

//    @Override
//    public TieredCalculation getTieredCalculation(IMetafieldId inNumericFieldId) {
//        Double qtyOwed;
//        Object fieldValue = this.getFieldValue(inNumericFieldId);
//        if (fieldValue instanceof Float) {
//            qtyOwed = new Double(((Float)fieldValue).doubleValue());
//        } else if (fieldValue instanceof Double) {
//            qtyOwed = (Double)fieldValue;
//        } else if (fieldValue instanceof Long) {
//            qtyOwed = new Double(((Long)fieldValue).longValue());
//        } else if (fieldValue instanceof Integer) {
//            qtyOwed = new Double(((Integer)fieldValue).longValue());
//        } else {
//            throw BizFailure.create((IPropertyKey)IArgoPropertyKeys.EXTRACT_FIELD_NOT_NUMERIC, null, this.getClass(), (Object)inNumericFieldId);
//        }
//        return new TieredCalculation(0.0, qtyOwed, null, null, null, null, null);
//    }

    @Override
    public Date getPerformedDay() {
        return this.getBexuEventStartTime();
    }

    @Override
    public Date getServicePaidThruDay() {
        return this.getBexuPaidThruDay();
    }

    @Override
    @Nullable
    public WeekdayEnum getPerformedDayOfWeekUnit() {
        WeekdayEnum dayOfWeek = null;
        Date performed = this.getBexuEventStartTime();
        if (performed != null) {
            dayOfWeek = ArgoUtils.getDayOfWeek(ContextHelper.getThreadUserTimezone(), performed);
        }
        return dayOfWeek;
    }

    @Override
    @Nullable
    public WeekdayEnum getPerformedDayOfWeekMarine() {
        WeekdayEnum dayOfWeek = null;
        return dayOfWeek;
    }

    @Override
    public Long getPerformedTimeOfDayMarine() {
        return 0L;
    }

    @Override
    public Long getPerformedTimeOfDayUnit() {
        Calendar calendar = Calendar.getInstance(ContextHelper.getThreadUserTimezone());
        calendar.setTime(this.getBexuEventStartTime());
        int hours = calendar.get(11);
        int minutes = calendar.get(12);
//        Long retValue = hours * 100 + minutes;
//        return retValue;
        return null;
    }

    @Nullable
    public static IPredicate formPerformedDayOfWeekUnitPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
            return PredicateFactory.eq((IMetafieldId)inMetafieldId, (Object)((Object)WeekdayEnum.getEnum((String)inValue)));
        }
        if (PredicateVerbEnum.NE.equals((Object)inVerb)) {
            return PredicateFactory.ne((IMetafieldId)inMetafieldId, (Object)((Object)WeekdayEnum.getEnum((String)inValue)));
        }
        if (PredicateVerbEnum.IN == inVerb) {
            int valCount = ((Object[])inValue).length;
            WeekdayEnum[] valArray = new WeekdayEnum[valCount];
            for (int i = 0; i < valCount; ++i) {
                String itemValue = ((Object[])inValue)[i].toString();
                valArray[i] = WeekdayEnum.getEnum(itemValue);
            }
            return PredicateFactory.in((IMetafieldId)inMetafieldId, (Object[])valArray);
        }
        return null;
    }

    @Nullable
    public static IPredicate formPerformedTimeOfDayUnitPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        if (PredicateVerbEnum.EQ.equals((Object)inVerb)) {
            return PredicateFactory.eq((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.GT.equals((Object)inVerb)) {
            return PredicateFactory.gt((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.GE.equals((Object)inVerb)) {
            return PredicateFactory.ge((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.LT.equals((Object)inVerb)) {
            return PredicateFactory.lt((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        if (PredicateVerbEnum.LE.equals((Object)inVerb)) {
            return PredicateFactory.le((IMetafieldId)inMetafieldId, (Object)inValue);
        }
        return null;
    }

    @Override
    public void setStatusInvoicedOrPartial() {
        String eventTypeStr = this.getBexuEventType();
        if (!this.isTimeBasedEvent()) {
            this.setBexuStatus("INVOICED");
        }
        this.setStatusInvoicedOrPartialForStorageReefer(eventTypeStr);
    }

    public void setStatusInvoicedOrPartialForStorageReefer(String inEventTypeStr) {
        if (!"QUEUED".equals(this.getBexuStatus())) {
            if (this.getBexuEventEndTime() == null) {
                this.setBexuStatus("PARTIAL");
            } else if (this.getBexuPaidThruDay() != null) {
                Date dateTobeCompared;
                Date date = dateTobeCompared = this.getBexuRuleEndDay() != null ? this.getBexuRuleEndDay() : this.getBexuEventEndTime();
                if (DateUtils.isSameDay((Date)this.getBexuPaidThruDay(), (Date)dateTobeCompared)) {
                    this.setBexuStatus("INVOICED");
                } else if (this.getBexuPaidThruDay().before(dateTobeCompared)) {
                    this.setBexuStatus("PARTIAL");
                } else {
                    this.setBexuStatus("INVOICED");
                }
            }
        }
    }

    @Override
    public IMetafieldId getDraftInvNbrField() {
        return IArgoExtractField.BEXU_LAST_DRAFT_INV_NBR;
    }

    @Override
    public void setLastDraftInvNbr(String inLastDraftInvNbr) {
        this.setBexuLastDraftInvNbr(inLastDraftInvNbr);
    }

    @Override
    public void setStatusQueued() {
        if (this.isTimeBasedEvent() && this.getBexuEventEndTime() != null && this.getBexuPaidThruDay() != null && this.getBexuPaidThruDay().after(this.getBexuEventStartTime())) {
            this.setBexuStatus("PARTIAL");
        } else if (this.getBexuGuaranteeGkey() != null) {
            this.setBexuStatus("GUARANTEED");
        } else {
            this.setBexuStatus("QUEUED");
        }
        this.setBexuLastDraftInvNbr(null);
    }

    @Nullable
    public static ChargeableUnitEvent getCUErecordForGuarantee(Guarantee inGnte) {
        ChargeableUnitEvent cue = null;
        Long extractGkey = inGnte.getGnteAppliedToPrimaryKey();
        BillingExtractEntityEnum appliedToClass = inGnte.getGnteAppliedToClass();
        if (null != extractGkey && BillingExtractEntityEnum.INV.equals((Object)appliedToClass)) {
            cue = (ChargeableUnitEvent) Roastery.getHibernateApi().get(ChargeableUnitEvent.class, (Serializable)extractGkey);
        }
        return cue;
    }

    @Override
    public void setStatusDraft() {
        this.setBexuStatus("DRAFT");
    }

    @Override
    public void setStatusCancelled() {
        this.setBexuStatus("CANCELLED");
    }

    @Override
    public String getStatus() {
        return this.getBexuStatus();
    }

    @Override
    public Long getEventGkey() {
        return this.getBexuSourceGkey();
    }

    @Override
    public String getEventType() {
        return this.getBexuEventType();
    }

    @Override
    public String getPrimaryCustomerId() {
        return this.getBexuLineOperatorId();
    }

    @Override
    public Date getServiceExtractEventEndTime() {
        return this.getBexuEventEndTime();
    }

    @Override
    public String getFacilityId() {
        return this.getBexuFacilityId();
    }

    @Override
    public String getComplexId() {
        return this.getBexuCommodityId();
    }

    @Override
    public void setFacility(Facility inFacility) {
        this.setBexuFacility(inFacility);
    }

    @Override
    public Facility getFacility() {
        return this.getBexuFacility();
    }

    @Override
    public Date getEventEndTime() {
        return this.getBexuEventEndTime();
    }

    @Override
    public Date getRuleStartDate() {
        return this.getBexuRuleStartDay();
    }

    @Override
    public Date getRuleEndDate() {
        return this.getBexuRuleEndDay();
    }

    @Override
    public Date getTimeDischargeComplete() {
        return this.getBexuTimeDischargeComplete();
    }

    @Override
    public Date getCarrierETA() {
        return this.getBexuIbCarrierETA();
    }

    @Override
    public Date getOBCarrierETA() {
        return this.getBexuObCarrierETA();
    }

    @Override
    public Date getFirstAvailability() {
        return this.getBexuFirstAvailability();
    }

    @Override
    public Date getTimeFirstFreeDay() {
        return this.getBexuTimeFirstFreeDay();
    }

    @Override
    public boolean isOverrideValue() {
        return this.getBexuIsOverrideValue() != null ? this.getBexuIsOverrideValue() : false;
    }

    @Override
    public String getOverrideValueType() {
        return this.getBexuOverrideValueType();
    }

    @Override
    public Double getOverrideValue() {
        return this.getBexuOverrideValue();
    }

    @Override
    public String getGuaranteeId() {
        return this.getBexuGuaranteeId();
    }

    @Override
    @Nullable
    public String getFixedPriceWaiverId() {
        Object waiverIdStr = null;
        if (this.isOverrideValue() && GuaranteeOverrideTypeEnum.FIXED_PRICE.getKey().equals(this.getOverrideValueType())) {
            waiverIdStr = null;
        }
        return (String) waiverIdStr;
    }

    @Override
    public String getGnteInvProcessingStatus() {
        return this.getBexuGnteInvProcessingStatus();
    }

    @Override
    public String getLastDraftInvNbr() {
        return this.getBexuLastDraftInvNbr();
    }

    @Override
    public String getSecondaryCustomerId() {
        if (UnitCategoryEnum.IMPORT.getKey().equals(this.getBexuCategory())) {
            return this.getBexuConsigneeId();
        }
        if (UnitCategoryEnum.EXPORT.getKey().equals(this.getBexuCategory())) {
            return this.getBexuShipperId();
        }
        if (UnitCategoryEnum.DOMESTIC.getKey().equals(this.getBexuCategory())) {
            return this.getBexuConsigneeId();
        }
        return this.getBexuLineOperatorId();
    }

    public static IPredicate formBexuLineOperatorNamePredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"LineOperator").addDqField(IArgoRefField.BZU_ID);
        IPredicate intf = PredicateFactory.createPredicate((UserContext) UserContextUtils.getSystemUserContext(), (IMetafieldId) IArgoRefField.BZU_NAME, (PredicateVerbEnum)inVerb, (Object)inValue);
        dq.addDqPredicate(intf);
        return PredicateFactory.subQueryIn((IDomainQuery)dq, (IMetafieldId)IArgoExtractField.BEXU_LINE_OPERATOR_ID);
    }

    public static Boolean isCUEExistsByStatus(Serializable inEntityGkey, String inEvntTypeId, String[] inAllowedStates) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoExtractField.BEXU_SOURCE_GKEY, (Object)inEntityGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEvntTypeId)).addDqPredicate(PredicateFactory.in((IMetafieldId)IArgoExtractField.BEXU_STATUS, (Object[])inAllowedStates)).addDqOrdering(Ordering.desc((IMetafieldId)IArgoExtractField.BEXU_CREATED));
        return HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public static ChargeableUnitEvent hydrate(Serializable inPrimaryKey) {
        return (ChargeableUnitEvent)HibernateApi.getInstance().load(ChargeableUnitEvent.class, inPrimaryKey);
    }

    @Override
    public boolean isTimeBasedEvent() {
        return ChargeableUnitEventTypeEnum.STORAGE.getKey().equals(this.getEventType()) || ChargeableUnitEventTypeEnum.LINE_STORAGE.getKey().equals(this.getEventType()) || ChargeableUnitEventTypeEnum.REEFER.getKey().equals(this.getEventType());
    }

    @Override
    public Date getIBCarrierATA() {
        return this.getBexuIbCarrierATA();
    }

    @Override
    public Date getOBCarrierATA() {
        return this.getBexuObCarrierATA();
    }

    @Override
    public Date getIBCarrierATD() {
        return this.getBexuIbCarrierATD();
    }

    @Override
    public Date getOBCarrierATD() {
        return this.getBexuObCarrierATD();
    }

    @Override
    public Date getVesselATA() {
        return null;
    }

    @Override
    public Date getVesselATD() {
        return null;
    }

    @Override
    public String getNotes() {
        return this.getBexuNotes();
    }

    @Override
    public String toString() {
        StringBuilder cue = new StringBuilder("ChargeableUnitEvent[");
        cue.append(" Batch Id:").append(this.getBexuBatchId()).append(" Event Type Id;").append(this.getBexuEventType()).append(" Equipment Id:").append(this.getBexuEqId()).append(" Category:").append(this.getBexuCategory()).append(" Start Time:").append(this.getBexuEventStartTime() != null ? this.getBexuEventStartTime().toString() : "null").append(" End Time:").append(this.getBexuEventEndTime() != null ? this.getBexuEventEndTime().toString() : "null").append(" IB Visit Id:").append(this.getBexuIbVisitId()).append(" OB Visit Id:").append(this.getBexuObVisitId()).append(" Iso Code:").append(this.getBexuIsoCode()).append(']');
        return cue.toString();
    }
}
