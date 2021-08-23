package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.TbdUnitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.CarrierIncompatibilityReasonEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class TbdUnit extends TbdUnitDO {
    public static final String BENTO_REQUEST_TBDUNIT_VALIDATE = "tdbunit-validate-request";
    public static final String BENTO_REQUEST_TBDUNIT_SUBSTITUTE = "tdbunit-substitute-request";
    public static final String BENTO_RESPONSE_TBDUNIT = "tdbunit-response";
    public static final String BENTO_TBDUNIT_GKEY = "tdbunit-gkey";
    public static final String BENTO_UYV_GKEY = "uyv-gkey";
    public static final String BENTO_UNIT_ID = "unit-id";
    public static final String TBDUNIT_SUBSTITUTION_GROOVY_CLASS = "TbdUnitSubstitutionGroovyImpl";
    private static final Logger LOGGER = Logger.getLogger(TbdUnit.class);
    public static final String TBDUNIT_VALIDATION_GROOVY_CLASS = "TbdUnitValidationGroovyImpl";

    public TbdUnit() {
        this.setTbduCreateTime(new Date(System.currentTimeMillis()));
        this.setTbduCategory(UnitCategoryEnum.STORAGE);
        this.setTbduFreightKind(FreightKindEnum.MTY);
        this.setTbduVisitState(UnitVisitStateEnum.ACTIVE);
        this.setTbduRestowType(RestowTypeEnum.NONE);
        this.setTbduClass(EquipClassEnum.CONTAINER);
        this.setTbduDamageSeverity(EqDamageSeverityEnum.NONE);
        this.setTbduDrayStatus(DrayStatusEnum.FORWARD);
        this.setTbduRequiresPower(Boolean.FALSE);
        this.setTbduIsOog(Boolean.FALSE);
        this.setTbduIsHazardous(Boolean.FALSE);
        this.setTbduIsPendingDelete(Boolean.FALSE);
    }

    public static TbdUnit createTbdEmptyForDelivery(Facility inFacility, CarrierVisit inTruckVisit, EquipType inEqType, ScopedBizUnit inLineOperator) {
        return TbdUnit.createTbdEmpty(inFacility, inTruckVisit, inEqType, inLineOperator, UnitCategoryEnum.STORAGE);
    }

    public static TbdUnit createTbdEmpty(Facility inFacility, CarrierVisit inCarrierVisit, EquipType inEqType, ScopedBizUnit inLineOperator, UnitCategoryEnum inTbdUnitCategory) {
        return TbdUnit.createTbdEmpty(inFacility, inCarrierVisit, inEqType, inLineOperator, inTbdUnitCategory, inFacility.getFcyRoutingPoint());
    }

    public static TbdUnit createTbdEmpty(Facility inFacility, CarrierVisit inCarrierVisit, EquipType inEqType, ScopedBizUnit inLineOperator, UnitCategoryEnum inTbdUnitCategory, RoutingPoint inPointOfDischarge) {
        return TbdUnit.createTbdEmpty(inFacility, inCarrierVisit, inEqType, inLineOperator, inTbdUnitCategory, inFacility.getFcyRoutingPoint(), RestowTypeEnum.NONE);
    }

    public static TbdUnit createTbdEmpty(Facility inFacility, CarrierVisit inCarrierVisit, EquipType inEqType, ScopedBizUnit inLineOperator, UnitCategoryEnum inTbdUnitCategory, RoutingPoint inPointOfDischarge, RestowTypeEnum inRestowTypeEnum) {
        Double tareWt;
        Complex complex = inFacility.getFcyComplex();
        if (inPointOfDischarge == null) {
            inPointOfDischarge = inFacility.getFcyRoutingPoint();
        }
        TbdUnit tbdu = new TbdUnit();
        tbdu.setTbduFacility(inFacility);
        tbdu.setTbduIbCv(CarrierVisit.getGenericCarrierVisit((Complex)complex));
        tbdu.setTbduObCv(inCarrierVisit);
        tbdu.setTbduEquipType(inEqType);
        tbdu.setTbduLineOperator(inLineOperator);
        tbdu.setTbduPOD1(inPointOfDischarge);
        if (inTbdUnitCategory != null) {
            tbdu.setTbduCategory(inTbdUnitCategory);
        }
        if (inRestowTypeEnum != null) {
            tbdu.setTbduRestowType(inRestowTypeEnum);
        }
        if (inEqType != null && (tareWt = inEqType.getEqtypTareWeightKg()) != null) {
            tbdu.setTbduGoodsAndCtrWtKg(tareWt);
        }
        HibernateApi.getInstance().save((Object)tbdu);
        return tbdu;
    }

    public static List<WorkInstruction> checkIfTbdUnitCanBeDeleted(Serializable inGkey) {
        if (inGkey == null) {
            throw BizFailure.create((String)"inGkey cannot be null");
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_TBD_UNIT, (Object)inGkey));
        List wiList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return wiList;
    }

    public boolean hasPlannedWi(WiMoveKindEnum[] inMoveKinds) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) IMovesField.WI_TBD_UNIT, (Object)this.getTbduGkey())).addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object[])inMoveKinds)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.PLANNED));
        return HibernateApi.getInstance().findCountByDomainQuery(dq) > 0;
    }

    @Nullable
    public static TbdUnit findByTruckTransaction(Serializable inTranGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"TbdUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.TBDU_TRUCK_TRANSACTION_GKEY, (Object)inTranGkey));
        return (TbdUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static void purgeTbdUnitsByRemark(String inRemark) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"TbdUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.TBDU_REMARK, (Object)inRemark));
        TbdUnit.deleteTbdUnits(HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq), 100L);
    }

    public static IMessageCollector purgeTbdUnitPendingDeletion(int inMaxbatchsize) {
        return TbdUnit.deleteTbdUnits(TbdUnit.findTbdUnitsPendingDeletion(), inMaxbatchsize);
    }

    public static Serializable[] findTbdUnitsPendingDeletion() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"TbdUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.TBDU_IS_PENDING_DELETE, (Object) Boolean.TRUE));
        return HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
    }

    public String getContainerargoRemarks() {
        return super.getTbduRemark();
    }

    private void addMnfDateToBuffer(StringBuffer inRemark, Date inMnfDate) {
        if (inMnfDate != null) {
            String dateStr = inMnfDate.toString();
            int indx = dateStr.lastIndexOf(32);
            if (indx > 0) {
                inRemark.append(dateStr.substring(0, indx));
            } else {
                inRemark.append(dateStr);
            }
        } else {
            inRemark.append("n/a");
        }
    }

    @Nullable
    private String material2Str(EquipMaterialEnum inMat) {
        if (inMat == null) {
            return "U";
        }
        if (EquipMaterialEnum.ALUMINUM.equals((Object)inMat)) {
            return "A";
        }
        if (EquipMaterialEnum.STEEL.equals((Object)inMat)) {
            return "S";
        }
        return null;
    }

    public static Message handleBentoRequest(@NotNull Message inMessage) {
//        Locale locale;
//        block20: {
//            locale = null;
//            try {
//                String unitId;
//                long uyvGkey;
//                long tbduGkey;
//                BentoNode requestNode;
//                try {
//                    requestNode = new BentoNode((DataInput)inMessage);
//                }
//                catch (Exception ex) {
//                    LOGGER.error((Object)"Unexpected error reading Message into Bento format", (Throwable)ex);
//                    throw BizFailure.wrap((Throwable)ex);
//                }
//                try {
//                    tbduGkey = requestNode.getLong(BENTO_TBDUNIT_GKEY);
//                    uyvGkey = requestNode.getLong(BENTO_UYV_GKEY);
//                    unitId = requestNode.getString(BENTO_UNIT_ID);
//                    locale = BentoServerJobHelper.readLocale((BentoNode)requestNode);
//                    if (locale == null) {
//                        locale = ContextHelper.getThreadUserContext().getUserLocale();
//                    }
//                }
//                catch (Exception ex) {
//                    HashMap<IPropertyKey, Object> errorMessages = new HashMap<IPropertyKey, Object>();
//                    errorMessages.put(IArgoPropertyKeys.BENTO_UNEXPECTED_ERROR, null);
//                    Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), errorMessages, (String)ex.getMessage(), (Locale)locale, (boolean)false);
//                    return messageResponse;
//                }
//                TbdUnit tbdUnit = TbdUnit.hydrate(Long.valueOf(tbduGkey));
//                if (tbdUnit == null) {
//                    HashMap<IPropertyKey, Object[]> errorMessages = new HashMap<IPropertyKey, Object[]>();
//                    errorMessages.put(IInventoryPropertyKeys.BENTO_TBD_UNIT_NOT_FOUND, new Object[]{tbduGkey});
//                    Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), errorMessages, null, (Locale)locale, (boolean)false);
//                    return messageResponse;
//                }
//                UnitYardVisit uyv = UnitYardVisit.hydrate(Long.valueOf(uyvGkey));
//                if (uyv == null) {
//                    HashMap<IPropertyKey, Object[]> errorMessages = new HashMap<IPropertyKey, Object[]>();
//                    errorMessages.put(IInventoryPropertyKeys.BENTO_UNIT_YARD_VISIT_NOT_FOUND, new Object[]{uyvGkey, unitId});
//                    Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), errorMessages, null, (Locale)locale, (boolean)false);
//                    return messageResponse;
//                }
//                String requestId = requestNode.getName();
//                final TbdUnitDeliveryValidator validator = new TbdUnitDeliveryValidator(tbdUnit, uyv.getUyvUfv());
//                if (BENTO_REQUEST_TBDUNIT_VALIDATE.equals(requestId)) {
//                    String groovyScript = TbdUnit.getGroovyScript(TBDUNIT_VALIDATION_GROOVY_CLASS);
//                    if (groovyScript == null) {
//                        if (LOGGER.isDebugEnabled()) {
//                            LOGGER.debug((Object)"no groovy code found doing standard validations");
//                        }
//                        validator.validateAll(WiMoveKindEnum.VeslDisch);
//                    } else {
//                        if (LOGGER.isDebugEnabled()) {
//                            LOGGER.debug((Object)" groovy code found doing  custom validations");
//                        }
//                        CustomGroovyInvoker.invokeCustomGroovy((String)groovyScript, (Class[])new Class[]{Map.class}, (Object[])new Object[]{new HashMap(){
//                            {
//                                this.put("VALIDATOR", validator);
//                            }
//                        }}, (String)TBDUNIT_VALIDATION_GROOVY_CLASS, (String)"execute");
//                    }
//                    break block20;
//                }
//                if (BENTO_REQUEST_TBDUNIT_SUBSTITUTE.equals(requestId)) {
//                    String groovyScript = TbdUnit.getGroovyScript(TBDUNIT_SUBSTITUTION_GROOVY_CLASS);
//                    if (groovyScript != null) {
//                        CustomGroovyInvoker.invokeCustomGroovy((String)groovyScript, (Class[])new Class[]{Map.class}, (Object[])new Object[]{new HashMap(){
//                            {
//                                this.put("VALIDATOR", validator);
//                            }
//                        }}, (String)TBDUNIT_SUBSTITUTION_GROOVY_CLASS, (String)"execute");
//                        if (LOGGER.isDebugEnabled()) {
//                            LOGGER.debug((Object)" groovy code found invoking it before substituion is done");
//                        }
//                    }
//                    tbdUnit.replaceWithUfv(uyv.getUyvUfv());
//                    break block20;
//                }
//                throw BizFailure.create((String)("invalid bento request: " + requestId));
//            }
//            catch (BizViolation fault) {
//                BizViolation bv;
//                ArrayList<String> errorMessages = new ArrayList<String>();
//                for (bv = fault; bv != null; bv = bv.getNextViolation()) {
//                    String msg = bv.getLocalizedMessage();
//                    errorMessages.add(msg);
//                }
//                String longMsg = "";
//                if (bv != null && bv.getMessage() != null) {
//                    longMsg = bv.getMessage();
//                }
//                Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), errorMessages, (String)longMsg, (boolean)false);
//                return messageResponse;
//            }
//            catch (Throwable t) {
//                LOGGER.error((Object)"Unexpected error", t);
//                HashMap<IPropertyKey, Object> errorMessages = new HashMap<IPropertyKey, Object>();
//                errorMessages.put(IArgoPropertyKeys.BENTO_UNEXPECTED_ERROR, null);
//                Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), errorMessages, (String)t.getMessage(), (Locale)locale, (boolean)false);
//                return messageResponse;
//            }
//        }
//        HashMap<IPropertyKey, Object[]> successMessages = new HashMap<IPropertyKey, Object[]>();
//        successMessages.put(IArgoPropertyKeys.BENTO_MESSAGE_SUCCESSFULLY_PROCESSED, new Object[]{inMessage.getMessageID()});
//        Message messageResponse = BentoServerJobHelper.handleResponse((String)BENTO_RESPONSE_TBDUNIT, (int)inMessage.getMessageID(), successMessages, null, (Locale)locale, (boolean)true);
//        return messageResponse;
        return null;
    }

    public TbdUnit cloneTbdUnit() {
        try {
            TbdUnit newTbdUnit = (TbdUnit)this.clone();
            newTbdUnit.setTbduGkey(null);
            HibernateApi.getInstance().save((Object)newTbdUnit);
            return newTbdUnit;
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    public boolean isEqIdWithinRange(Equipment inEquipment) {
        String rangesAsString = this.getTbduSerialRanges();
        if (rangesAsString == null) {
            return true;
        }
//        SerialRangeSet srs = new SerialRangeSet(rangesAsString);
//        return srs.isEqIdWithinRangeSet(inEquipment.getEqIdFull());
        return false;
    }

    public void validateUfvMatch(UnitFacilityVisit inUfv) throws BizViolation {
        EqBaseOrderItem eqOrderItem;
        Unit unit = inUfv.getUfvUnit();
        UnitEquipment ue = unit.getUnitPrimaryUe();
        if (ue == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_WITHOUT_EQUIPEMENT, null);
        }
        BizViolation bv = null;
        Equipment equipment = ue.getUeEquipment();
        if (!this.isEqIdWithinRange(equipment)) {
            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_MISMATCH_SER_NUMBER, bv, (Object)equipment.getEqIdFull());
        }
        if (!ObjectUtils.equals((Object)unit.getUnitFreightKind(), (Object)this.getTbduFreightKind())) {
            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_MISMATCH_FREIGHT_KIND, (BizViolation)bv, (Object)unit.getUnitFreightKind(), (Object)this.getTbduFreightKind());
        }
        if (!ObjectUtils.equals((Object)unit.getUnitLineOperator(), (Object)this.getTbduLineOperator())) {
            String tbduLineId = this.getTbduLineOperator() == null ? null : this.getTbduLineOperator().getBzuId();
            String unitLineId = unit.getUnitLineOperator() == null ? null : unit.getUnitLineOperator().getBzuId();
            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_MISMATCH_LINE_OP, (BizViolation)bv, (Object)unitLineId, (Object)tbduLineId);
        }
        if ((eqOrderItem = this.getTbduDepartureOrderItem()) != null) {
            eqOrderItem.validateUnitMatchesForLoadOut(inUfv);
        }
        if (bv != null) {
            throw bv;
        }
    }

    public void copyTBDPropertiesToUfv(UnitFacilityVisit inUfv) throws BizViolation {
        UnitCategoryEnum tbdCat;
        RoutingPoint tbdPol;
        Facility tbduFcy;
        Unit unit = inUfv.getUfvUnit();
        unit.startTrackingFieldChanges();
        UnitEquipment ue = unit.getUnitPrimaryUe();
        if (ue == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_WITHOUT_EQUIPEMENT, null);
        }
        inUfv.updateObCv(this.getTbduObCv());
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        unit.assignToOrder(eqOrderItem, ue.getUeEquipment());
        RoutingPoint rp1 = this.getTbduPOD1();
        RoutingPoint rp2 = this.getTbduPOD2();
        if (rp1 != null) {
            unit.getUnitRouting().setRtgPOD1(rp1);
        }
        unit.getUnitRouting().setRtgPOD2(rp2);
        if (inUfv.getUfvActualObCv() != null && LocTypeEnum.VESSEL.equals((Object)inUfv.getUfvActualObCv().getCvCarrierMode()) && (tbduFcy = inUfv.getUfvActualObCv().getCvFacility()) != null && (tbdPol = tbduFcy.getFcyRoutingPoint()) != null) {
            unit.getUnitRouting().setRtgPOL(tbdPol);
        }
        if ((tbdCat = this.getTbduCategory()) != null) {
            unit.setUnitCategory(tbdCat);
        }
        FieldChanges fcs = unit.endTrackingFieldChanges();
        unit.recordUnitEvent((IEventType)EventEnum.UNIT_TBD_UNIT_MERGE, fcs, "TBD Unit Merge", null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void replaceWithUfv(UnitFacilityVisit inUfv) throws BizViolation {
        this.copyTBDPropertiesToUfv(inUfv);
        Long myGkey = this.getTbduGkey();
        if (myGkey != null) {
            try {
                BizViolation bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.TBD_UNIT_FAILED_TO_DELETE, null, (Object)myGkey);
                int times = 250;
                int count = 0;
                boolean found = false;
                while (count < 250) {
                    try {
                        List<WorkInstruction> wis = TbdUnit.checkIfTbdUnitCanBeDeleted(myGkey);
                        if (wis == null || wis.isEmpty()) {
                            HibernateApi.getInstance().delete((Object)this);
                            found = true;
                            break;
                        }
                        LOGGER.info((Object)("attempting to make sure tbdu WI is deleted: " + myGkey));
                        Thread.currentThread();
                        Thread.sleep(20L);
                    }
                    catch (InterruptedException e) {
                        LOGGER.debug((Object)"Ignoring interrupted exception", (Throwable)e);
                    }
                    finally {
                        ++count;
                    }
                }
                if (!found) {
                    LOGGER.warn((Object)bv.getMessage());
                }
            }
            catch (Exception e) {
                LOGGER.warn((Object)("tbdunit: error deleting tbdunit during substitution transaction: " + myGkey));
            }
        }
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getTbduFacility() == null) {
            this.setSelfAndFieldChange(IInventoryField.TBDU_FACILITY, (Object)ContextHelper.getThreadFacility(), inOutMoreChanges);
        }
        if (this.getTbduIbCv() == null) {
            CarrierVisit genericCarrier = CarrierVisit.getGenericCarrierVisit((Complex)ContextHelper.getThreadComplex());
            this.setSelfAndFieldChange(IInventoryField.TBDU_IB_CV, (Object)genericCarrier, inOutMoreChanges);
        }
    }

    public void updateTruckTransactionGkey(Long inTruckTransactionGkey) {
        super.setTbduTruckTransactionGkey(inTruckTransactionGkey);
    }

    public void updateSerialRanges(String inSerialRanges) {
        super.setTbduSerialRanges(inSerialRanges);
    }

    public void updatePOD1(RoutingPoint inPOD1) {
        super.setTbduPOD1(inPOD1);
    }

    public void updateYardPosition(String inYardPosition) {
        super.setTbduYardPosition(inYardPosition);
    }

    public void updateDepartureOrderItem(EqBaseOrderItem inDepartureOrderItem) {
        super.setTbduDepartureOrderItem(inDepartureOrderItem);
    }

    public void updateRemark(String inRemark) {
        super.setTbduRemark(inRemark);
    }

    public void updateRestowTypeEnum(RestowTypeEnum inRestowTypeEnum) {
        super.setTbduRestowType(inRestowTypeEnum);
    }

    public void updateIsPendingDelete(Boolean inBoolean) {
        super.setTbduIsPendingDelete(inBoolean);
    }

    public Long getContainerargoGkey() {
        return this.getTbduGkey();
    }

    @Nullable
    public Long getContainerargoPkey() {
        return null;
    }

    @Nullable
    public Long getContainerargoBundleKey() {
        return null;
    }

    @Nullable
    public Date getContainerargoUpdateTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoLastMoveTime() {
        return null;
    }

    public Boolean getContainerargoPreferCelsiusTemp() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoDesiredOnPowerState() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoSentReeferRequest() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoOD() {
        return this.getTbduIsOog();
    }

    public Boolean getContainerargoTynes() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoLiveReefer() {
        return this.getTbduRequiresPower();
    }

    public Boolean getContainerargoDamage() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoHold1() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoHold2() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoHold3() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoChanged() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoFolded() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoCOU() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoUnVerified() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoMultHazard() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoOnPower() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoWasDelivered() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoAddSlaveHeights() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoTransferableRelease() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoValidateReleaseGroup() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoWheeledCare() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoMustPlanToWheels() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoSpecialBox() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoAddSlaveWeights() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoVerifiedLoadList() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoPlugProjection() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoDirectHandling() {
        return Boolean.FALSE;
    }

    public Boolean getContainerargoRailRASPriorityContainer() {
        return Boolean.FALSE;
    }

    public Long getContainerargoReservedForCheId() {
        return -1L;
    }

    public Boolean getContainerargoStop() {
        return Boolean.FALSE;
    }

    @Nullable
    public Long getContainerargoEquipmentHeight() {
        EquipType et = this.getTbduEquipType();
        return et == null ? null : et.getEqtypHeightMm();
    }

    @Nullable
    public Long getContainerargoWeight() {
        return null;
    }

    @Nullable
    public Long getContainerargoWeightAdvised() {
        return null;
    }

    @Nullable
    public Long getContainerargoWeightGateMeasured() {
        return null;
    }

    @Nullable
    public Long getContainerargoWeightYardMeasured() {
        return null;
    }

    @Nullable
    public Float getContainerargoReeferTemp() {
        return null;
    }

    @Nullable
    public Float getContainerargoUpperReeferTemp() {
        return null;
    }

    @Nullable
    public Float getContainerargoLowerReeferTemp() {
        return null;
    }

    @Nullable
    public Long getContainerargoOverdimensionTop() {
        return null;
    }

    @Nullable
    public Long getContainerargoOverdimensionRight() {
        return null;
    }

    @Nullable
    public Long getContainerargoOverdimensionLeft() {
        return null;
    }

    @Nullable
    public Long getContainerargoOverdimensionFront() {
        return null;
    }

    @Nullable
    public Long getContainerargoOverdimensionBack() {
        return null;
    }

    @Nullable
    public String getContainerargoArriveVisit() {
        return null;
    }

    @Nullable
    public String getContainerargoDepartVisit() {
        return null;
    }

    @Nullable
    public Character getContainerargoQArrive() {
        return null;
    }

    @Nullable
    public Character getContainerargoQDepart() {
        return null;
    }

    @Nullable
    public Character getContainerargoQCategory() {
        return null;
    }

    @Nullable
    public Character getContainerargoQStatus() {
        return null;
    }

    @Nullable
    public Character getContainerargoQReeferType() {
        return null;
    }

    @Nullable
    public Character getContainerargoQRehandle() {
        return null;
    }

    @Nullable
    public Character getContainerargoQHazStowReq() {
        return null;
    }

    @Nullable
    public Character getContainerargoQEquipmentType() {
        return null;
    }

    @Nullable
    public Character getContainerargoQCheckDigit() {
        return null;
    }

    @Nullable
    public Character getContainerargoQFtxQualifier() {
        return null;
    }

    public String getContainerargoEquipmentId() {
        StringBuilder buf = new StringBuilder("TBD_");
        buf.append("000000");
        String digits = this.getTbduGkey().toString();
        buf.append(digits);
        buf.delete(4, 3 + digits.length());
        return buf.toString();
    }

    @Nullable
    public String getContainerargoBookingNumber() {
        EqBaseOrderItem item = this.getTbduDepartureOrderItem();
        return item == null ? null : item.getEqboiOrder().getEqboNbr();
    }

    @Nullable
    public String getContainerargoService() {
        return null;
    }

    @Nullable
    public String getContainerargoEquipmentType() {
        return null;
    }

    @Nullable
    public String getContainerargoLoadPort() {
        return null;
    }

    @Nullable
    public String getContainerargoDischargePort() {
        return null;
    }

    @Nullable
    public String getContainerargoProjDischPort() {
        return null;
    }

    public String getContainerargoArchCommodity() {
        return null;
    }

    @Nullable
    public String getContainerargoDestination() {
        return null;
    }

    @Nullable
    public String getContainerargoOrigin() {
        return null;
    }

    @Nullable
    public String getContainerargoEquipmentFeature() {
        return null;
    }

    @Nullable
    public String getContainerargoLineOperator() {
        return null;
    }

    @Nullable
    public String getContainerargoGroup() {
        return null;
    }

    @Nullable
    public String getContainerargoStow1() {
        return null;
    }

    @Nullable
    public String getContainerargoStow2() {
        return null;
    }

    @Nullable
    public String getContainerargoStow3() {
        return null;
    }

    @Nullable
    public String getContainerargoCommodity() {
        return null;
    }

    public Long getContainerargoUnitCombo() {
        return null;
    }

    public Long getContainerargoEqUnitRole() {
        return null;
    }

    public Long getContainerargoRailConeStatus() {
        return null;
    }

    public String[] getContainerargoHazards() {
        return new String[0];
    }

    @Nullable
    public String getContainerargoUserValidated1() {
        return null;
    }

    @Nullable
    public String getContainerargoUserValidated2() {
        return null;
    }

    @Nullable
    public String getContainerargoUserValidated3() {
        return null;
    }

    @Nullable
    public String getContainerargoUserValidated4() {
        return null;
    }

    @Nullable
    public String getContainerargoUserValidated5() {
        return null;
    }

    public String getContainerargoMnRStatus() {
        return null;
    }

    @Nullable
    public String getContainerargoUser1() {
        return null;
    }

    @Nullable
    public String getContainerargoUser2() {
        return null;
    }

    @Nullable
    public String getContainerargoUser3() {
        return null;
    }

    @Nullable
    public String getContainerargoUser4() {
        return null;
    }

    @Nullable
    public String getContainerargoUser5() {
        return null;
    }

    @Nullable
    public String getContainerargoUser6() {
        return null;
    }

    @Nullable
    public String getContainerargoUser7() {
        return null;
    }

    @Nullable
    public String getContainerargoUser8() {
        return null;
    }

    @Nullable
    public String getContainerargoUser9() {
        return null;
    }

    @Nullable
    public String getContainerargoUser10() {
        return null;
    }

    @Nullable
    public String getContainerargoUserA() {
        return null;
    }

    @Nullable
    public String getContainerargoUserB() {
        return null;
    }

    @Nullable
    public String getContainerargoUserC() {
        return null;
    }

    @Nullable
    public String getContainerargoUserD() {
        return null;
    }

    @Nullable
    public String getContainerargoUserE() {
        return null;
    }

    @Nullable
    public String getContainerargoUserF() {
        return null;
    }

    @Nullable
    public String getContainerargoUserG() {
        return null;
    }

    @Nullable
    public String getContainerargoUserH() {
        return null;
    }

    @Nullable
    public String getContainerargoUserI() {
        return null;
    }

    @Nullable
    public String getContainerargoUserJ() {
        return null;
    }

    @Nullable
    public String getContainerargoUserK() {
        return null;
    }

    @Nullable
    public String getContainerargoUserL() {
        return null;
    }

    @Nullable
    public String getContainerargoUserM() {
        return null;
    }

    @Nullable
    public String[] getContainerargoFlexStringsCntr() {
        return null;
    }

    @Nullable
    public String getContainerargoUserN() {
        return null;
    }

    @Nullable
    public String getContainerargoUserO() {
        return null;
    }

    @Nullable
    public String getContainerargoHostKey() {
        return null;
    }

    @Nullable
    public String getContainerargoChassis() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate1() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate2() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate3() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate4() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate5() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate6() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate7() {
        return null;
    }

    @Nullable
    public Date getContainerargoUfvFlexDate8() {
        return null;
    }

    @Nullable
    public Date getContainerargoRnDTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoYardInTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoYardOutTime() {
        return null;
    }

    public Date getContainerargoEmptySelectionTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoAppointmentTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoUserTime() {
        return null;
    }

    @Nullable
    public Date getContainerargoLastReeferTime() {
        return null;
    }

    @Nullable
    public Float getContainerargoLastReeferTemp() {
        return null;
    }

    @Nullable
    public Date getContainerargoInventoryTime() {
        return null;
    }

    @Nullable
    public String getContainerargoBundledOn() {
        return null;
    }

    @Nullable
    public Long getContainerargoTareWeight() {
        Long tareWeight = null;
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        Double temp = eqOrderItem.getEqoiTareWeightMax();
        if (temp != null) {
            tareWeight = temp.longValue();
        }
        return tareWeight;
    }

    @Nullable
    public String getContainerargoCSC() {
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        String csc = eqOrderItem.getEqoiCscExpiration();
        return csc;
    }

    @Nullable
    public String getContainerargoMaterial() {
        String material = null;
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        if (eqOrderItem.getEqoiEqMaterial() != null) {
            material = this.material2Str(eqOrderItem.getEqoiEqMaterial());
        }
        return material;
    }

    @Nullable
    public Date getContainerargoBuildDateMin() {
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        Date mnfDateMin = eqOrderItem.getEqoiManufactureDateMin();
        return mnfDateMin;
    }

    @Nullable
    public Date getContainerargoBuildDateMax() {
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        Date mnfDateMax = eqOrderItem.getEqoiManufactureDateMax();
        return mnfDateMax;
    }

    @Nullable
    public Long getContainerargoPayload() {
        return null;
    }

    @Nullable
    public String getContainerargoEquipRequestId() {
        return null;
    }

    @Nullable
    public String getContainerargoNote() {
        return null;
    }

    public Collection getContainerargoBundleSlaves() {
        return Collections.emptyList();
    }

    @Nullable
    public String getContainerargoDamageCode() {
        return null;
    }

    @Nullable
    public Long getContainerargoVisitState() {
        return null;
    }

    @Nullable
    public Long getContainerargoTransitState() {
        return null;
    }

    @Nullable
    public String getContainerargoFireCode() {
        return null;
    }

    @Nullable
    public Long getContainerargoDrayStatus() {
        return null;
    }

    @Nullable
    public String getContainerargoSeal1() {
        return null;
    }

    @Nullable
    public String getContainerargoSeal2() {
        return null;
    }

    @Nullable
    public String getContainerargoSeal3() {
        return null;
    }

    @Nullable
    public String getContainerargoSeal4() {
        return null;
    }

    @Nullable
    public String getContainerargoPositionOnTruck() {
        return null;
    }

    @Nullable
    public String getContainerargoTruckLicense() {
        String licNbr;
        CarrierVisit cv = this.getTbduIbCv();
        String licNbr1 = null;
        if (cv != null && LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode())) {
            licNbr1 = cv.getCarrierVehicleId();
        }
        if ((licNbr = licNbr1) == null) {
            CarrierVisit cv1 = this.getTbduObCv();
            String licNbr2 = null;
            if (cv1 != null && LocTypeEnum.TRUCK.equals((Object)cv1.getCvCarrierMode())) {
                licNbr2 = cv1.getCarrierVehicleId();
            }
            licNbr = licNbr2;
        }
        return licNbr;
    }

    @Nullable
    public String getContainerargoTransId() {
        String batNbr;
        VisitDetails cvd;
        CarrierVisit cv = this.getTbduIbCv();
        String batNbr1 = null;
        if (cv != null && LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode()) && (cvd = cv.getCvCvd()) != null) {
            batNbr1 = cvd.getSecondNaturalKey();
        }
        if ((batNbr = batNbr1) == null) {
            VisitDetails cvd2;
            CarrierVisit cv2 = this.getTbduObCv();
            String batNbr2 = null;
            if (cv2 != null && LocTypeEnum.TRUCK.equals((Object)cv2.getCvCarrierMode()) && (cvd2 = cv2.getCvCvd()) != null) {
                batNbr2 = cvd2.getSecondNaturalKey();
            }
            batNbr = batNbr2;
        }
        return batNbr;
    }

    @Nullable
    public String getContainerargoTruckCompany() {
        String company;
        CarrierVisit cv = this.getTbduIbCv();
        String company1 = null;
        if (cv != null && LocTypeEnum.TRUCK.equals((Object)cv.getCvCarrierMode())) {
            company1 = cv.getCarrierOperatorName();
        }
        if ((company = company1) == null) {
            CarrierVisit cv1 = this.getTbduObCv();
            String company2 = null;
            if (cv1 != null && LocTypeEnum.TRUCK.equals((Object)cv1.getCvCarrierMode())) {
                company2 = cv1.getCarrierOperatorName();
            }
            company = company2;
        }
        return company;
    }

    @Nullable
    public String getContainerargoTruckDriver() {
        return null;
    }

    @Nullable
    public String getContainerargoCustomsAgent() {
        return null;
    }

    @Nullable
    public String getContainerargoShipper() {
        return null;
    }

    @Nullable
    public String getContainerargoConsignee() {
        return null;
    }

    @Nullable
    public String getContainerargoExchangeLane() {
        return null;
    }

    @Nullable
    public String getContainerargoContainerGrade() {
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        EquipGrade equipGrade = eqOrderItem.getEqoiEqGrade();
        if (equipGrade == null) {
            return null;
        }
        return equipGrade.getEqgrdId();
    }

    @Nullable
    public String getContainerargoContainerCondition() {
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return null;
        }
        EquipCondition equipCondition = eqOrderItem.getEqoiEqCondition();
        if (equipCondition == null) {
            return null;
        }
        return eqOrderItem.getEqoiEqCondition().getEqcondId();
    }

    @Nullable
    public String getContainerargoFacilityId() {
        return null;
    }

    public Long getContainerargoLocOrientation() {
        return null;
    }

    @Nullable
    public String getContainerargoImportDelivGroup() {
        return null;
    }

    @Nullable
    public String getContainerargoECn() {
        return null;
    }

    @Nullable
    public String getContainerargoReeferComment() {
        return null;
    }

    @Nullable
    public String getContainerargoDeliveryTask() {
        return null;
    }

    @Nullable
    public String getContainerargoOpdPort1() {
        return null;
    }

    @Nullable
    public String getContainerargoOpdPort2() {
        return null;
    }

    @Nullable
    public String getContainerargoOpdPort3() {
        return null;
    }

    public String getContainerargoBillOfLading() {
        return this.getTbduBlNbr();
    }

    @Nullable
    public String getContainerargoBoxStrength() {
        return null;
    }

    @Nullable
    public String getContainerargoLowBillOfLading() {
        return null;
    }

    @Nullable
    public String getContainerargoBookingRelease() {
        return null;
    }

    public String getContainerargoHousekeepingCurrentSlot() {
        return null;
    }

    public String getContainerargoHousekeepingFutureSlot() {
        return null;
    }

    public Long getContainerargoHousekeepingCurrentScore() {
        return null;
    }

    public Long getContainerargoHousekeepingFutureScore() {
        return null;
    }

    public Date getContainerargoHousekeepingTimeStamp() {
        return null;
    }

    @Nullable
    public String getTbduCategoryString() {
        UnitCategoryEnum theEnum = this.getTbduCategory();
        if (theEnum == null) {
            return null;
        }
        return theEnum.getKey();
    }

    @Nullable
    public String getTbduFreightKindString() {
        FreightKindEnum theEnum = this.getTbduFreightKind();
        if (theEnum == null) {
            return null;
        }
        return theEnum.getKey();
    }

    @Nullable
    public String getTbduSpecialStowString() {
        SpecialStow theValue = this.getTbduSpecialStow();
        if (theValue == null) {
            return null;
        }
        return theValue.getStwId();
    }

    @Nullable
    public String getTbduObCvtring() {
        CarrierVisit theValue = this.getTbduObCv();
        if (theValue == null) {
            return null;
        }
        return theValue.getCvId();
    }

    @Nullable
    public String getTbduDepartureOrderItemString() {
        EqBaseOrderItem theValue = this.getTbduDepartureOrderItem();
        if (theValue == null) {
            return null;
        }
        String val = theValue.getPrimaryKey() == null ? null : theValue.getPrimaryKey().toString();
        return val;
    }

    private String getReservedUnitsList() {
        StringBuilder result = new StringBuilder("");
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return result.toString();
        }
        try {
            Collection<Unit> reservedUnits = this.getOrderItemReservedUnits(eqOrderItem);
            if (reservedUnits != null && !reservedUnits.isEmpty()) {
                for (Unit aC : reservedUnits) {
                    Unit unit = aC;
                    if (unit.isUnitInYard()) {
                        result.append(unit.getUnitId());
                        result.append(" ");
                        continue;
                    }
                    if (!LOGGER.isDebugEnabled()) continue;
                    LOGGER.debug((Object)("TBDUnit Remark Calc: ignoring the unit as it is not in the yard: " + unit.getUnitId()));
                }
            }
        }
        catch (Exception e) {
            LOGGER.warn((Object)"TbdUnit: Ignoring error during computation of reserved units list", (Throwable)e);
        }
        return result.toString();
    }

    public Collection<Unit> getOrderItemReservedUnits(EqBaseOrderItem inEqOrderItem) {
        return this.getUnitFinder().findUnitsReservedForOrderItem(inEqOrderItem);
    }

    private IUnitFinder getUnitFinder() {
        return (IUnitFinder) Roastery.getBean((String)"unitFinder");
    }

    private String getOrderItemRemarks() {
        String result = "";
        EqBaseOrderItem eqOrderItem = this.getTbduDepartureOrderItem();
        if (eqOrderItem == null) {
            return result;
        }
        if (eqOrderItem.getEqoiRemarks() != null && !eqOrderItem.getEqoiRemarks().isEmpty()) {
            result = eqOrderItem.getEqoiRemarks();
        }
        return result;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public static TbdUnit hydrate(Serializable inPrimaryKey) {
        return (TbdUnit)HibernateApi.getInstance().load(TbdUnit.class, inPrimaryKey);
    }

    @Override
    public void setTbduPkey(Long inPkey) {
        super.setTbduPkey(inPkey);
    }

    @Nullable
    private static String getGroovyScript(String inString) {
        if (!ArgoConfig.DISABLE_GROOVY_EXECUTION.isOn(ContextHelper.getThreadUserContext())) {
   //         return DigitalAsset.findGroovyClassCode((String)inString);
        }
        return null;
    }

    @Nullable
    public Point3D getContainerargoLocPdsCoordinates() {
        return null;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(IInventoryField.TBDU_OB_CV) && (CarrierVisitPhaseEnum.DEPARTED.equals((Object)this.getTbduObCv().getCvVisitPhase()) || CarrierVisitPhaseEnum.COMPLETE.equals((Object)this.getTbduObCv().getCvVisitPhase()) || CarrierVisitPhaseEnum.CANCELED.equals((Object)this.getTbduObCv().getCvVisitPhase()))) {
            if (LocTypeEnum.VESSEL.equals((Object)this.getTbduObCv().getCvCarrierMode())) {
                return BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_OUTBOUND_VESSEL_VISIT, (BizViolation)bv, (Object)this.getTbduObCvtring());
            }
            if (LocTypeEnum.TRAIN.equals((Object)this.getTbduObCv().getCvCarrierMode())) {
                return BizViolation.create((IPropertyKey) IArgoPropertyKeys.INVALID_OUTBOUND_TRAIN_VISIT, (BizViolation)bv, (Object)this.getTbduObCvtring());
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.TBDU_YARD_POSITION)) {
            Yard yrd;
            String yardPos = null;
            FieldChange fc = inChanges.getFieldChange(IInventoryField.TBDU_YARD_POSITION);
            if (fc != null && !StringUtils.isEmpty((String)(yardPos = (String)fc.getNewValue())) && (yrd = ContextHelper.getThreadYard()) != null) {
                AbstractBin yrdBinModel = yrd.getYrdBinModel();
                if (yrdBinModel == null) {
                    return BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_YARD_MODEL_FOR_YBY_YARD, (BizViolation)bv, (Object)yrd.getId());
                }
                BinContext binContext = BinContext.findBinContext((String)"STOWAGE_CONTAINERS");
                if (binContext == null) {
                    throw BizFailure.createProgrammingFailure((String)("Failed to verify that" + yardPos + " is a valid yard position"));
                }
                AbstractBin aBin = yrdBinModel.findDescendantBinFromInternalSlotString(yardPos, binContext);
                if (aBin == null) {
                    return BizViolation.create((IPropertyKey)IInventoryPropertyKeys.POSITION_DOES_NOT_EXIST_IN_YARD, (BizViolation)bv, (Object)yardPos, (Object)yrd.getId());
                }
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.TBDU_P_O_D1) || inChanges.hasFieldChange(IInventoryField.TBDU_OB_CV)) {
            FieldChange fc;
            RoutingPoint rtgPoint = this.getTbduPOD1();
            CarrierVisit cv = this.getTbduObCv();
            if (inChanges.hasFieldChange(IInventoryField.TBDU_P_O_D1)) {
                fc = inChanges.getFieldChange(IInventoryField.TBDU_P_O_D1);
                RoutingPoint routingPoint = rtgPoint = fc != null ? (RoutingPoint)fc.getNewValue() : rtgPoint;
            }
            if (inChanges.hasFieldChange(IInventoryField.TBDU_OB_CV)) {
                fc = inChanges.getFieldChange(IInventoryField.TBDU_OB_CV);
                CarrierVisit carrierVisit = cv = fc != null ? (CarrierVisit)fc.getNewValue() : cv;
            }
            if (cv != null && !cv.isGenericCv() && (cv.getCvCarrierMode().equals((Object)LocTypeEnum.VESSEL) || cv.getCvCarrierMode().equals((Object)LocTypeEnum.TRAIN)) && rtgPoint != null && !cv.isPointInItinerary(rtgPoint)) {
                return BizViolation.create((IPropertyKey)IInventoryPropertyKeys.ROUTING__POINT_NOT_IN_ITINERARY, null, (Object)IInventoryField.TBDU_P_O_D1, (Object)rtgPoint.getPointId(), (Object)cv);
            }
        }
        return bv;
    }

    @Nullable
    public String getContainerargoChassisType() {
        return null;
    }

    @Nullable
    public String getContainerargoChassisLineOp() {
        return null;
    }

    @Nullable
    public String getContainerargoN4StowFactor() {
        return this.getTbduStowFactor();
    }

    @Nullable
    public String getContainerargoN4StackingFactor() {
        return null;
    }

    @Nullable
    public String getContainerargoN4SectionFactor() {
        return null;
    }

    @Nullable
    public String getContainerargoN4SegregationFactor() {
        return null;
    }

    @Nullable
    public Long getContainerargoTankRailType() {
        return null;
    }

    public String getContainerargoN4OptimalRailTZSlot() {
        return null;
    }

    @Override
    protected void setTbduStowFactor(String inTbduStowFactor) {
        super.setTbduStowFactor(inTbduStowFactor != null ? inTbduStowFactor : "");
    }

    @Override
    public String getTbduStowFactor() {
        String plannedUfvStowFactor = super.getTbduStowFactor() != null ? super.getTbduStowFactor() : "";
        return plannedUfvStowFactor;
    }

    public Long getContainerargoCarrierIncompatibilityReason() {
        return null;
    }

    public LocPosition getFinalPlannedPosition() {
        List<WorkInstruction> wiList = this.getCurrentWiList();
        if (wiList.isEmpty()) {
            return null;
        }
        WorkInstruction wi = wiList.get(0);
        return wi.getWiPosition();
    }

    public List<WorkInstruction> getCurrentWiList() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_TBD_UNIT, (Object)this.getTbduGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE)).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        List wiList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return wiList;
    }

    public CarrierIncompatibilityReasonEnum checkIncompatibilityForLoad(LocPosition inCurPosition) {
        IArgoRailManager railManager = (IArgoRailManager)Roastery.getBean((String)"argoRailManager");
        EquipNominalLengthEnum eqtypNominalLength = this.getTbduEquipType().getEqtypNominalLength();
        LocPosition finalPlannedPositionForDisch = this.getFinalPlannedPosition();
        if (finalPlannedPositionForDisch != null) {
            inCurPosition = finalPlannedPositionForDisch;
        }
        CarrierIncompatibilityReasonEnum reasonEnum = CarrierIncompatibilityReasonEnum.NONE;
        try {
            FieldChanges fieldChanges = new FieldChanges();
            HashMap<Long, FieldChanges> tbdChanges = new HashMap<Long, FieldChanges>();
            boolean isCompatibile = true;
            if (inCurPosition != null && LocTypeEnum.RAILCAR.equals((Object)inCurPosition.getPosLocType())) {
                isCompatibile = railManager.isCarrierIncompatibleForLoad(inCurPosition, eqtypNominalLength);
            }
            if (!isCompatibile) {
                reasonEnum = CarrierIncompatibilityReasonEnum.PIN_MISMATCHED;
            }
            fieldChanges.setFieldChange(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON, (Object)reasonEnum);
            tbdChanges.put(this.getTbduGkey(), fieldChanges);
            TbdUnit.updateCarrierIncompatibilityFlag(tbdChanges);
        }
        catch (BizViolation inBizViolation) {
            String posSlot = finalPlannedPositionForDisch == null ? "" : finalPlannedPositionForDisch.getPosSlot();
            LOGGER.error((Object)("Unable to compute incompatibility reason for WI planned for Railcar slot " + posSlot));
            return reasonEnum;
        }
        return reasonEnum;
    }

    public static void updateCarrierIncompatibilityFlag(final Map<Long, FieldChanges> inTbduChanges) {
        final UserContext systemContext = ContextHelper.getSystemUserContextForScope((IScopeEnum)ScopeEnum.YARD, (Serializable)ContextHelper.getThreadYardKey());
        PersistenceTemplate pt = new PersistenceTemplate(systemContext);
        UserContext currentUC = TransactionParms.getBoundParms().getUserContext();
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    TransactionParms.getBoundParms().setUserContext(systemContext);
                    if (inTbduChanges != null && !inTbduChanges.isEmpty()) {
                        for (Long aLong : inTbduChanges.keySet()) {
                            FieldChanges changes;
                            Long entityGkey = aLong;
                            if (entityGkey == null || (changes = (FieldChanges)inTbduChanges.get(entityGkey)) == null) continue;
                            TbdUnit tbdUnit = (TbdUnit)HibernateApi.getInstance().load(TbdUnit.class, (Serializable)entityGkey);
                            if (tbdUnit == null) {
                                LOGGER.warn((Object)("Failed to load an entity of type: " + TbdUnit.class.getName() + "with gkey: " + entityGkey));
                                continue;
                            }
                            CarrierIncompatibilityReasonEnum newValue = null;
                            if (changes.getFieldChange(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON).getNewValue() != null) {
                                newValue = (CarrierIncompatibilityReasonEnum)((Object)changes.getFieldChange(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON).getNewValue());
                            }
                            tbdUnit.setFieldValue(IInventoryField.TBDU_CARRIER_INCOMPATIBLE_REASON, (Object)newValue);
                        }
                    }
                }
                catch (Exception ex) {
                    LOGGER.error((Object)"Unable to set pin mismatch compatibility for Units");
                }
            }
        });
        TransactionParms.getBoundParms().setUserContext(currentUC);
    }

    public static IMessageCollector deleteTbdUnits(Serializable[] inPrimaryKeys, long inMaxDeleteBatchSize) {
        IMessageCollector mc = MessageCollectorUtils.getMessageCollector();
        if (inPrimaryKeys == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"Returning from deleteTbdUnits without deletion as the list is null ");
            }
            return mc;
        }
//        BatchKeys br = new BatchKeys((Object[])inPrimaryKeys, (int)inMaxDeleteBatchSize);
//        UserContext uc = ContextHelper.getThreadUserContext();
//        BatchKeys itr = br.getIterator();
//        while (itr.hasNext()) {
//            final Serializable[] tbdunitGkey = (Serializable[])itr.next();
//            IMessageCollector msgCollector = new PersistenceTemplate(uc).invoke(new CarinaPersistenceCallback(){
//
//                protected void doInTransaction() {
//                    for (Serializable unitGkey : tbdunitGkey) {
//                        TbdUnit tbdu = TbdUnit.hydrate(unitGkey);
//                        if (tbdu == null) {
//                            LOGGER.error((Object)("No tbd unit found for gkey " + unitGkey));
//                            continue;
//                        }
//                        try {
//                            List<WorkInstruction> wis = TbdUnit.checkIfTbdUnitCanBeDeleted(unitGkey);
//                            if (wis == null || wis.isEmpty()) {
//                                HibernateApi.getInstance().delete((Object)tbdu);
//                                continue;
//                            }
//                            LOGGER.error((Object)("It seems work instructions are pointing to the TBDUnit =  " + wis));
//                        }
//                        catch (Exception e) {
//                            Long ser = tbdu.getTbduGkey();
//                            LOGGER.error((Object)("failed to delete TBDUnit with gkey =  " + ser), (Throwable)e);
//                        }
//                    }
//                }
//            });
//            MessageCollectorUtils.appendMessages((IMessageCollector)mc, (IMessageCollector)msgCollector);
//        }
//        if (!mc.hasError()) {
//            if (LOGGER.isInfoEnabled()) {
//                LOGGER.info((Object)"TBDUnits were deleted succesfully in the database");
//            }
//            Object[] parms = new Object[]{(long)inPrimaryKeys.length};
//            mc.appendMessage(MessageLevelEnum.INFO, IInventoryPropertyKeys.UNIT_DELETE_SUCCESS, "", parms);
//        }
        return mc;
    }
}
