package com.zpmc.ztos.infra.base.common.utils.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class UnitUtils {
    private static final Logger LOGGER = Logger.getLogger(UnitUtils.class);

    private UnitUtils() {
    }

    public static void validateUfvObCv(Unit inUnit) throws BizViolation {
        Serializable itinGkey;
        UnitCategoryEnum category = inUnit.getUnitCategory();
        Routing routing = inUnit.getUnitRouting();
        RoutingPoint pod = routing.getRtgPOD1();
        RoutingPoint currentPoint = ContextHelper.getThreadFacility() != null ? ContextHelper.getThreadFacility().getFcyRoutingPoint() : null;
        UnitFacilityVisit ufv = inUnit.getUnitActiveUfvNowActive();
        if (ufv == null) {
            return;
        }
        CarrierVisit cv = routing.getRtgDeclaredCv();
        VisitDetails cvd = cv == null ? null : cv.getCvCvd();
        CarrierItinerary itin = null;
        IQueryResult qr = null;
        if (cvd != null && (qr = UnitUtils.getItinSrvcGkeys(cvd.getCvdGkey())).getTotalResultCount() != 0 && (itinGkey = (Serializable)qr.getValue(0, 0)) != null) {
            itin = (CarrierItinerary) HibernateApi.getInstance().load(CarrierItinerary.class, itinGkey);
        }
        BizViolation bv = null;
        if (itin != null && pod != null) {
            if (UnitCategoryEnum.IMPORT.equals((Object)category)) {
                if (!(cv.getCvFacility().isFcyNonOperational().booleanValue() || ObjectUtils.equals((Object)pod, (Object)currentPoint) || itin.isPointInItinerary(pod))) {
                    String unitId = inUnit.getUnitId();
                    String curPointId = currentPoint == null ? "" : currentPoint.getPointId();
                    CarrierService service = null;
                    Serializable srvcGkey = (Serializable)qr.getValue(0, 1);
                    if (srvcGkey != null) {
                        service = (CarrierService)HibernateApi.getInstance().load(CarrierService.class, srvcGkey);
                    }
                    String svcId = service == null ? null : service.getSrvcId();
                    bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.ROUTING_ERROR5, (Throwable)null, (BizViolation)bv, null, (Object[])new String[]{unitId, pod.getPointId(), curPointId, svcId});
                }
            } else if (!itin.isPointInItinerary(pod)) {
                Facility fcy = ufv.getUfvFacility();
                Facility nextFcy = ufv.getUfvIntendedObCv() != null ? ufv.getUfvIntendedObCv().getCvNextFacility() : null;
                CarrierVisit finalCv = ufv.getUfvUnit().getUnitRouting().getRtgDeclaredCv();
                if (nextFcy == null || finalCv == null) {
                    bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.ROUTING__POINT_NOT_IN_ITINERARY, bv, (IMetafieldId) UnitField.RTG_P_O_D1, (Object)pod.getPointId(), (Object)cv);
                } else if (!DrayStatusEnum.TRANSFER.equals((Object)inUnit.getUnitDrayStatus())) {
//                    if (fcy.findFacilityRelayTo(nextFcy) != null) {
//                        if (!finalCv.isPointInItinerary(pod)) {
//                            bv = BizViolation.createFieldViolation((IPropertyKey)IInventoryPropertyKeys.ROUTING__POINT_NOT_IN_ITINERARY, (BizViolation)bv, (IMetafieldId)UnitField.RTG_P_O_D1, (Object)pod.getPointId(), (Object)finalCv);
//                        }
//                    } else {
//                        bv = BizViolation.createFieldViolation((IPropertyKey)IInventoryPropertyKeys.ROUTING__POINT_NOT_IN_ITINERARY, (BizViolation)bv, (IMetafieldId)UnitField.RTG_P_O_D1, (Object)pod.getPointId(), (Object)cv);
//                    }
                }
            }
        }
        if (bv != null) {
            throw bv;
        }
    }

    @Nullable
    public static Date getLastReeferDisconnectTime(Unit inUnit) {
        EventManager em;
        Event event;
        EventType eventType = EventType.findEventType((String) EventEnum.UNIT_POWER_DISCONNECT.getId());
        if (eventType == null) {
            LOGGER.error((Object)("getLastReeferDisconnectTime: Event Type " + EventEnum.UNIT_POWER_DISCONNECT.getId() + " could not be found"));
            return null;
        }
//        if (inUnit.isReefer() && (event = (em = (EventManager) Roastery.getBean((String)"eventManager")).getMostRecentEventByTypeFasterVersion(eventType, (IServiceable)inUnit)) != null) {
//            return event.getEventTime();
//        }
        return null;
    }

    public static Date disallowNull(Date inDate) {
        return inDate != null ? inDate : new Date(System.currentTimeMillis() + 1728000000L);
    }

    public static int getUnitVisitStateValue(UnitVisitStateEnum inUnitVisitState) {
        if (UnitVisitStateEnum.DEPARTED.equals((Object)inUnitVisitState)) {
            return 0;
        }
        if (UnitVisitStateEnum.ACTIVE.equals((Object)inUnitVisitState)) {
            return 2;
        }
        if (UnitVisitStateEnum.ADVISED.equals((Object)inUnitVisitState)) {
            return 3;
        }
        return -1;
    }

    @Nullable
    public static Double verifyGrossWt(Double inGrossWtKg) throws BizViolation {
        Double grossWt = null;
        if (inGrossWtKg != null && inGrossWtKg.intValue() != 0) {
            if (inGrossWtKg.intValue() > 0) {
                grossWt = inGrossWtKg;
            } else {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__GROSSWT_NEGATIVE, null, (Object)inGrossWtKg);
            }
        }
        return grossWt;
    }

    public static void validateUnitGrossWeight(Unit inUnit, Double inGoodsAndCtrWtKg, boolean inIsHumanUser) throws BizViolation {
        UnitUtils.validateUnitWeight(inUnit, inGoodsAndCtrWtKg, inIsHumanUser, UnitField.UNIT_GOODS_AND_CTR_WT_KG, "Gross Weight");
    }

    public static void validateUnitGrossWeight(Unit inUnit, Double inGoodsAndCtrWtKg) throws BizViolation {
        UnitUtils.validateUnitGrossWeight(inUnit, inGoodsAndCtrWtKg, ContextHelper.isUpdateFromHumanUser());
    }

    public static void validateUnitVgmWeight(Unit inUnit, Double inGoodsAndCtrWtKgVerfiedGross, boolean inIsHumanUser) throws BizViolation {
        UnitUtils.validateUnitWeight(inUnit, inGoodsAndCtrWtKgVerfiedGross, inIsHumanUser, UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS, "VGM Weight");
    }

    public static void validateUnitVgmWeight(Unit inUnit, Double inGoodsAndCtrWtKgVerfiedGross) throws BizViolation {
        UnitUtils.validateUnitWeight(inUnit, inGoodsAndCtrWtKgVerfiedGross, ContextHelper.isUpdateFromHumanUser(), UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS, "VGM Weight");
    }

    public static void validateUnitVgmWeight(Unit inUnit, Double inGoodsAndCtrWtKg, boolean inIsHumanUser, IMetafieldId inFieldId, String inFieldName) throws BizViolation {
        boolean validate = inIsHumanUser || DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource());
        UnitUtils.validateUnitWeight(inUnit, inGoodsAndCtrWtKg, validate, inFieldId, inFieldName);
    }

    public static void validateUnitWeight(Unit inUnit, Double inGoodsAndCtrWtKg, boolean inIsHumanUser, IMetafieldId inFieldId, String inFieldName) throws BizViolation {
        Double goodsAndCtrWtKg = UnitUtils.verifyGrossWt(inGoodsAndCtrWtKg);
        if (inIsHumanUser) {
            UnitUtils.validateWeight(inUnit, goodsAndCtrWtKg, inFieldId, inFieldName);
        }
    }

    public static boolean getUnitRepairedDamageStatus(UnitEquipment inUnitEquipment) {
        return !EqDamageSeverityEnum.NONE.equals((Object)inUnitEquipment.getUeDamageSeverity()) && !EqDamageSeverityEnum.REPAIRED.equals((Object)inUnitEquipment.getUeDamageSeverity());
    }

    public static Object getRequiredField(IMetafieldId inFieldId, FieldChanges inChanges) throws BizViolation {
        FieldChange fieldChange = inChanges.getFieldChange(inFieldId);
        if (fieldChange == null || fieldChange.getNewValue() == null) {
            throw BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.MISSING_REQD_PARM, null, (IMetafieldId)inFieldId);
        }
        return fieldChange.getNewValue();
    }

    @Nullable
    public static Object getOptionalField(IMetafieldId inFieldId, FieldChanges inChanges) {
        FieldChange fieldChange = inChanges.getFieldChange(inFieldId);
        if (fieldChange == null) {
            return null;
        }
        return fieldChange.getNewValue();
    }

    @Nullable
    public static String getEquipmentFullId(String inDigits, boolean inDoEqCheck) {
        if (!inDigits.isEmpty()) {
            if (inDoEqCheck && inDigits.length() >= 4 && inDigits.substring(0, 4).matches("[a-zA-Z]{4}")) {
                Equipment equipment = Equipment.findEquipment((String)inDigits);
                if (equipment != null) {
                    String fullCtrId = equipment.getEqIdFull();
                    return fullCtrId;
                }
                return inDigits + "%";
            }
            if (!Character.isDigit(inDigits.charAt(0))) {
                return inDigits + "%";
            }
        }
        return null;
    }

    public static String getEquipmentIdNbrOnly(String inDigits) {
        if (!inDigits.isEmpty()) {
            if (inDigits.matches("\\d{7}") || inDigits.matches("\\d{8}")) {
                return inDigits;
            }
            inDigits = "%" + inDigits + "%";
        }
        return inDigits;
    }

    public static boolean sealsChanged(FieldChanges inChanges) {
        return inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR1) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR2) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR3) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR4) || inChanges.hasFieldChange(IInventoryField.UNIT_IS_CTR_SEALED);
    }

    public static boolean overDimensionsChanged(FieldChanges inChanges) {
        return inChanges.hasFieldChange(IInventoryField.UNIT_OOG_BACK_CM) || inChanges.hasFieldChange(IInventoryField.UNIT_OOG_FRONT_CM) || inChanges.hasFieldChange(IInventoryField.UNIT_OOG_LEFT_CM) || inChanges.hasFieldChange(IInventoryField.UNIT_OOG_RIGHT_CM) || inChanges.hasFieldChange(IInventoryField.UNIT_OOG_TOP_CM);
    }

    @Nullable
    static String getMetaFieldLabel(IMetafieldId inMetafieldId) {
        IMetafieldDictionary dictionary = Roastery.getMetafieldDictionary();
        IMetafield metafield = dictionary.findMetafield(inMetafieldId);
        ITranslationContext translator = TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext());
        return translator.getMessageTranslator().getMessage(metafield.getShortLabelKey());
    }

    public static void addImpediments(Collection inImpediments, Set inImpedimentIds) {
        for (Object impediment : inImpediments) {
            String uiValue = ((IImpediment)impediment).getTableUiValue();
            if (uiValue == null) continue;
            inImpedimentIds.add(uiValue);
        }
    }

    public static String getImpedimentsDetails(IImpediment inImpediment) {
//        IVeto veto = UnitUtils.getVetoForEntity(inImpediment.getAffectedEntity().getLogicalEntityType(), inImpediment.getVetoes());
//        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean((String)"messageTranslatorProvider");
//        IMessageTranslator translator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
//        StringBuilder sb = new StringBuilder();
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.FLAG_TYPE_ID));
//        sb.append(":");
//        sb.append(inImpediment.getFlagType().getId());
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.HP_VIEW_ID));
//        sb.append(":");
//        sb.append(inImpediment.getHpvId());
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.IMPEDIMENT_STATUS));
//        sb.append(":");
//        sb.append(translator.getMessage(inImpediment.getStatus().getDescriptionPropertyKey()));
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.IMPEDIMENT_APPLIED_BY));
//        sb.append(":");
//        sb.append(inImpediment.getAppliedBy());
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.IMPEDIMENT_DATE));
//        sb.append(":");
//        sb.append(ArgoUtils.convertDateToLocalTime((Date)inImpediment.getApplyDate()));
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.VETO_BY));
//        sb.append(":");
//        if (veto != null) {
//            sb.append(veto.getVetoedBy());
//        } else {
//            sb.append("null");
//        }
//        sb.append(",");
//        sb.append(UnitUtils.getMetaFieldLabel(IArgoBizMetafield.VETO_DATE));
//        sb.append(":");
//        if (veto != null) {
//            sb.append(ArgoUtils.convertDateToLocalTime((Date)veto.getDate()));
//        } else {
//            sb.append("null");
//        }
        ArrayList<StringBuilder> impList = new ArrayList<StringBuilder>();
//        impList.add(sb);
        return StringUtils.join(impList, (String)"\n");
    }

//    @Nullable
//    private static IVeto getVetoForEntity(LogicalEntityEnum inEntityType, Collection inVetoes) {
//        if (inVetoes == null || inVetoes.size() <= 0) {
//            return null;
//        }
//        Iterator it = inVetoes.iterator();
//        if (inVetoes.size() == 1) {
//            return (IVeto)it.next();
//        }
//        while (it.hasNext()) {
//            IVeto currentVeto = (IVeto)it.next();
//            LogicalEntityEnum vetoedEntityClass = currentVeto.getVetoedEntityClass();
//            if (vetoedEntityClass.equals((Object)inEntityType)) continue;
//            return currentVeto;
//        }
//        return null;
//    }

    private static IQueryResult getItinSrvcGkeys(Serializable inCvdGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"VisitDetails").addDqField(IArgoField.CVD_ITINERARY).addDqField(IArgoField.CVD_SERVICE).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CVD_GKEY, (Object)inCvdGkey));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        return qr;
    }

    private static void validateWeight(Unit inUnit, Double inGoodsAndCtrWtKg, IMetafieldId inFieldId, String inFieldName) throws BizViolation {
        if (FreightKindEnum.BBK.equals((Object)inUnit.getUnitFreightKind())) {
            return;
        }
        Equipment primaryEq = inUnit.getPrimaryEq();
        Double tareWt = primaryEq.getEqTareWeightKg();
        Double safeWt = primaryEq.getEqSafeWeightKg();
        long tareWtL = 0L;
        long safeWtL = 0L;
        long grossWtL = 0L;
        if (tareWt != null) {
            tareWtL = tareWt.longValue();
        }
        if (safeWt != null) {
            safeWtL = safeWt.longValue();
        }
        if (inGoodsAndCtrWtKg != null) {
            grossWtL = inGoodsAndCtrWtKg.longValue();
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("Category: <" + (Object)inUnit.getUnitFreightKind() + ">, Gross Wt: <" + grossWtL + ">, Tare Wt: <" + tareWtL + ">, Safe Wt: <" + safeWtL + ">"));
        }
        BizViolation bv = null;
        if (inUnit.getUnitFreightKind() == FreightKindEnum.MTY) {
            long delta = new Double(0.1 * (double)tareWtL).longValue();
            if (tareWtL != 0L && (grossWtL > tareWtL + delta || grossWtL < tareWtL - delta)) {
                bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.UNITS__CTR_MTY_GROSSWT_MUST_EQUAL_TAREWT, (BizViolation)bv, (IMetafieldId)inFieldId, (Object)inFieldName, (Object)tareWt);
            }
        } else {
            if (safeWtL != 0L && grossWtL > safeWtL) {
                bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.UNITS__GROSSWT_EXCEEDS_SAFEWT, bv, (IMetafieldId)inFieldId, (Object)inFieldName, (Object)safeWt);
            }
            if (tareWtL != 0L && grossWtL < tareWtL) {
                bv = BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.UNITS__GROSSWT_CANT_BE_LESS_OR_EQUAL_TO_TAREWT, (BizViolation)bv, (IMetafieldId)inFieldId, (Object)inFieldName, (Object)inUnit.getUnitFreightKind(), (Object)tareWt);
            }
        }
        if (bv != null) {
            throw bv;
        }
    }
}
