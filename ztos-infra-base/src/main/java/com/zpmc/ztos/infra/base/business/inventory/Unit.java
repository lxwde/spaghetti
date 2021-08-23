package com.zpmc.ztos.infra.base.business.inventory;

//import com.google.common.base.Strings;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.equipments.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.CodedConfig;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.configs.StringConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.systems.ServiceRule;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.common.utils.inventory.UnitUtils;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.*;

public class Unit extends UnitEquipment implements ILocation,
       // IEventExtractor,
        IPropertySourceEntity
        {
    public static final UnitVisitStateEnum[] LIVE_STATES = new UnitVisitStateEnum[]{UnitVisitStateEnum.ACTIVE, UnitVisitStateEnum.ADVISED};
    public static final UnitVisitStateEnum[] HISTORY_STATES = new UnitVisitStateEnum[]{UnitVisitStateEnum.DEPARTED, UnitVisitStateEnum.RETIRED};
    private static final Logger LOGGER = Logger.getLogger(Unit.class);
    private static final Set<UnitVisitStateEnum> TRANSITIONS_ADVISED = new HashSet<UnitVisitStateEnum>();
    private static final Set<UnitVisitStateEnum> TRANSITIONS_ACTIVE = new HashSet<UnitVisitStateEnum>();
    private static final Set<UnitVisitStateEnum> TRANSITIONS_DEPARTED = new HashSet<UnitVisitStateEnum>();
    private static final Set<UnitVisitStateEnum> TRANSITIONS_RETIRED = new HashSet<UnitVisitStateEnum>();
    private FieldChangeTracker _tracker;
    public static final MetafieldIdList UNIT_PROTECTED_FIELDS;
    public static final String GEN_YARD = "GEN_YARD";
    private UnitNode _unitNode = new UnitNode(this);
    private boolean _isNewlyCreatedUnit;
    private String _notes = "VGM Changes";
    private static final int HASH_FACTOR = 32;

    public LocTypeEnum getLocType() {
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        LocTypeEnum currentPositionType = null;
        if (ufv != null) {
            currentPositionType = ufv.getUfvLastKnownPosition().getPosLocType();
        }
        return currentPositionType;
    }

    public Long getLocGkey() {
        return this.getUnitGkey();
    }

    public String getLocId() {
        return this.getUnitId();
    }

    public String getCarrierVehicleId() {
        return this.getUnitId();
    }

    public Facility getLocFacility() {
        UnitFacilityVisit activeUfv = this.getUnitActiveUfv();
        if (activeUfv != null) {
            return activeUfv.getUfvFacility();
        }
        return null;
    }

    @Nullable
    public CarrierVisit getInboundCv() {
        UnitFacilityVisit activeUfv = this.getUnitActiveUfv();
        if (activeUfv != null) {
            return activeUfv.getUfvActualIbCv();
        }
        return null;
    }

    @Nullable
    public CarrierVisit getOutboundCv() {
        UnitFacilityVisit activeUfv = this.getUnitActiveUfv();
        if (activeUfv != null) {
            return activeUfv.getUfvActualObCv();
        }
        return null;
    }

    public void verifyMoveFromAllowed(IEntity inMovingEntity, String inSlot) throws BizViolation {
    }

    public void verifyMoveToAllowed(IEntity inMovingEntity, String inSlot) {
    }

    public Unit() {
        this.setUnitNeedsReview(Boolean.FALSE);
        this.setUnitRequiresPower(Boolean.FALSE);
        this.setUnitIsPowered(Boolean.FALSE);
        this.setUnitWantPowered(Boolean.FALSE);
        this.setUnitValidateReleaseGroup(Boolean.FALSE);
        this.setUnitIsTransferableRelease(Boolean.FALSE);
        this.setUnitIgnorePayloadWeights(Boolean.FALSE);
        this.setUnitIgnorePayloadHeights(Boolean.FALSE);
        this.setUnitIsOog(Boolean.FALSE);
        this.setUnitTimeDenormalizeCalc(new Date(0L));
        this.setUnitStoppedVessel(Boolean.FALSE);
        this.setUnitStoppedRail(Boolean.FALSE);
        this.setUnitStoppedRoad(Boolean.FALSE);
        this.setUnitCreateTime(ArgoUtils.timeNow());
        this.setUnitTimeLastStateChange(ArgoUtils.timeNow());
        this.setUnitIsStowplanPosted(Boolean.FALSE);
        this.setUnitEqRole(EqUnitRoleEnum.PRIMARY);
        this.setUnitIsFolded(Boolean.FALSE);
        this.setUnitIsReserved(Boolean.FALSE);
        this.setUnitDamageSeverity(EqDamageSeverityEnum.NONE);
    }

    protected static Unit createContainerizedUnit(String inCreateNote, Complex inComplex, CarrierVisit inDeclaredIbCv, CarrierVisit inDeclaredObCv, Equipment inPrimaryEq, ScopedBizUnit inLineOperator) {
        return Unit.createContainerizedUnit(inCreateNote, inComplex, inDeclaredIbCv, inDeclaredObCv, inPrimaryEq, inLineOperator, null, null);
    }

    protected static Unit createContainerizedUnit(String inCreateNote, Complex inComplex, CarrierVisit inDeclaredIbCv, CarrierVisit inDeclaredObCv, Equipment inPrimaryEq, ScopedBizUnit inLineOperator, ScopedBizUnit inShipper, ScopedBizUnit inTruckingCompany) {
        return Unit.createContainerizedUnit(inCreateNote, inComplex, inDeclaredIbCv, inDeclaredObCv, inPrimaryEq, inLineOperator, inShipper, inTruckingCompany, null);
    }

    protected static Unit createContainerizedUnit(String inCreateNote, Complex inComplex, CarrierVisit inDeclaredIbCv, CarrierVisit inDeclaredObCv, Equipment inPrimaryEq, ScopedBizUnit inLineOperator, ScopedBizUnit inShipper, ScopedBizUnit inTruckingCompany, ScopedBizUnit inAgent) {
        boolean isPersisted;
        Unit unit = new Unit();
        unit.setUnitComplex(inComplex);
        unit.setUnitVisitState(UnitVisitStateEnum.ADVISED);
        unit.setUnitCategory(UnitCategoryEnum.STORAGE);
        unit.setUnitDeclaredIbCv(inDeclaredIbCv);
        unit.setUnitLineOperator(inLineOperator);
        Routing routing = new Routing();
        routing.setRtgDeclaredCv(inDeclaredObCv);
        routing.setRtgTruckingCompany(inTruckingCompany);
        unit.setUnitRouting(routing);
        boolean isPinGenerated = false;
        if (inLineOperator != null) {
            isPinGenerated = Unit.generatePin(routing, inLineOperator);
        }
        unit.setUnitFreightKind(FreightKindEnum.FCL);
        unit.setUnitDeckRqmnt(VslDeckRqmntEnum.EITHER);
        unit.setUnitAgent1(inAgent);
        unit.setUnitId(inPrimaryEq.getEqIdFull());
        unit.setUnitEquipment(inPrimaryEq);
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentState(inPrimaryEq, unit.getUnitComplex().getCpxOperator(), inLineOperator);
        unit.setUnitEquipmentState(eqs);
        if (unit.getEqsGradeID() != null && (isPersisted = unit.getEqsGradeID().getGradeIDPersisted())) {
            unit.setUnitGradeID(unit.getEqsGradeID());
        }
        if (!ContextHelper.getThreadIsFromUpgrade()) {
            EquipCondition eqCond;
            Boolean propogatePrevUseDamages = false;
            EquipClassEnum eqClass = inPrimaryEq.getEqClass();
            if (EquipClassEnum.CONTAINER.equals((Object)eqClass)) {
                propogatePrevUseDamages = !InventoryConfig.CLEAR_CTR_DAMAGES.isOn(ContextHelper.getThreadUserContext());
            } else if (EquipClassEnum.CHASSIS.equals((Object)eqClass)) {
                propogatePrevUseDamages = !InventoryConfig.CLEAR_CHS_DAMAGES.isOn(ContextHelper.getThreadUserContext());
            } else if (EquipClassEnum.ACCESSORY.equals((Object)eqClass)) {
                propogatePrevUseDamages = !InventoryConfig.CLEAR_ACC_DAMAGES.isOn(ContextHelper.getThreadUserContext());
            }
            IUnitFinder uf = (IUnitFinder)Roastery.getBean((String)"unitFinder");
            Unit prevUse = uf.findAttachedUnit(unit.getUnitComplex(), inPrimaryEq);
            if (propogatePrevUseDamages.booleanValue() && prevUse != null) {
                unit.setUnitDamageSeverity(prevUse.getUnitDamageSeverity());
                unit.setUnitSparcsDamageCode(prevUse.getUnitSparcsDamageCode());
                UnitEquipDamages formerDamages = prevUse.getUnitDamages();
                if (formerDamages != null) {
                    unit.setUnitDamages(formerDamages.createPersistentCopy());
                }
            }
//            if (prevUse != null && (eqCond = prevUse.getUnitConditionID()) != null && eqCond.isPersist()) {
//                unit.setUnitConditionID(eqCond);
//            }
        }
        unit.attachNewBaseGoods();
        if (unit.getGoods() != null) {
            unit.getGoods().updateShipper((Object)inShipper);
        }
        unit.setUnitGoodsAndCtrWtKg(inPrimaryEq.getEqTareWeightKg());
        HibernateApi.getInstance().save((Object)unit);
        unit.setNewlyCreatedUnit(true);
        unit.recordLifecyleEvent(EventEnum.UNIT_CREATE, inCreateNote);
        if (isPinGenerated) {
            unit.recordUnitEvent((IEventType)EventEnum.UNIT_PIN_ASSIGNED, null, null);
        }
        return unit;
    }

    private static boolean generatePin(Routing inRouting, ScopedBizUnit inLineOperator) {
        if (ContextHelper.getThreadIsFromUpgrade()) {
            return false;
        }
        LineOperator lineOperator = null;
        boolean isPinGenerated = false;
        if (inLineOperator != null) {
            try {
                lineOperator = (LineOperator) HibernateApi.getInstance().downcast((DatabaseEntity)inLineOperator, LineOperator.class);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (lineOperator != null) {
                boolean lineGeneratePin;
                boolean lineUsesPin = lineOperator.getLineopUsePinNbr() != null && lineOperator.getLineopUsePinNbr() != false;
                boolean bl = lineGeneratePin = lineOperator.getLineopGenPinNbr() != null && lineOperator.getLineopGenPinNbr() != false;
                if (lineUsesPin && lineGeneratePin) {
//                    InventorySequenceProvider provider = new InventorySequenceProvider();
//                    Long unitPinNbr = provider.getPinNbrNextSeqValue();
//                    inRouting.setRtgPinNbr(unitPinNbr.toString().trim());
                    isPinGenerated = true;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)"created and assigned PIN to the unit");
                    }
                }
            }
        }
        return isPinGenerated;
    }

    protected static Unit createBreakbulkUnit(Complex inComplex, CarrierVisit inDeclaredIbCv, CarrierVisit inDeclaredObCv, String inUnitId) {
        Unit unit = new Unit();
        unit.setUnitComplex(inComplex);
        unit.setUnitVisitState(UnitVisitStateEnum.ADVISED);
        unit.setUnitCategory(UnitCategoryEnum.STORAGE);
        unit.setUnitDeclaredIbCv(inDeclaredIbCv);
        Routing routing = new Routing();
        routing.setRtgDeclaredCv(inDeclaredObCv);
        unit.setUnitRouting(routing);
        unit.setUnitId(inUnitId);
        unit.setUnitFreightKind(FreightKindEnum.BBK);
        unit.setUnitDeckRqmnt(VslDeckRqmntEnum.EITHER);
        unit.attachNewBaseGoods();
        unit.setNewlyCreatedUnit(true);
        HibernateApi.getInstance().save((Object)unit);
        unit.recordLifecyleEvent(EventEnum.UNIT_CREATE, "BBulk Unit Created");
        return unit;
    }

    public static IServiceable createUnitProxy(String inUnitId) {
//        ProxyServicable proxyServicable = new ProxyServicable(LogicalEntityEnum.UNIT, Unit.getPathsToGuardians(LogicalEntityEnum.UNIT));
//        proxyServicable.setLogEntityId(inUnitId);
//        return proxyServicable;
        return null;
    }

    public static Unit hydrate(Serializable inPrimaryKey) {
        return (Unit)HibernateApi.getInstance().load(Unit.class, inPrimaryKey);
    }

    public void startTrackingFieldChanges() {
        this._tracker = this.createFieldChangeTracker();
    }

    @Nullable
    public FieldChanges endTrackingFieldChanges() {
        if (this._tracker == null) {
            return null;
        }
        FieldChanges changes = this._tracker.getChanges((IEntity)this);
        this._tracker = null;
        return changes;
    }

    public FieldChangeTracker createFieldChangeTracker() {
        return new FieldChangeTracker((IEntity)this, new IMetafieldId[]{IInventoryField.UNIT_VISIT_STATE, IInventoryField.UNIT_DECLARED_IB_CV, IInventoryField.UNIT_CATEGORY, IInventoryField.UNIT_FREIGHT_KIND, IInventoryField.UNIT_DRAY_STATUS, IInventoryField.UNIT_SPECIAL_STOW, IInventoryField.UNIT_SPECIAL_STOW2, IInventoryField.UNIT_SPECIAL_STOW3, IInventoryField.UNIT_DECK_RQMNT, IInventoryField.UNIT_REQUIRES_POWER, IInventoryField.UNIT_IS_POWERED, IInventoryField.UNIT_WANT_POWERED, IInventoryField.UNIT_POWER_RQST_TIME, IInventoryField.UNIT_IS_OOG, IInventoryField.UNIT_OOG_BACK_CM, IInventoryField.UNIT_OOG_FRONT_CM, IInventoryField.UNIT_OOG_LEFT_CM, IInventoryField.UNIT_OOG_RIGHT_CM, IInventoryField.UNIT_OOG_TOP_CM, IInventoryField.UNIT_LINE_OPERATOR, IInventoryField.UNIT_GOODS_AND_CTR_WT_KG, IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_ADVISED, IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_GATE_MEASURED, IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED, IInventoryField.UNIT_SEAL_NBR1, IInventoryField.UNIT_SEAL_NBR2, IInventoryField.UNIT_SEAL_NBR3, IInventoryField.UNIT_SEAL_NBR4, IInventoryField.UNIT_REMARK, IInventoryField.UNIT_FLEX_STRING01, IInventoryField.UNIT_FLEX_STRING02, IInventoryField.UNIT_FLEX_STRING03, IInventoryField.UNIT_FLEX_STRING04, IInventoryField.UNIT_FLEX_STRING05, IInventoryField.UNIT_FLEX_STRING06, IInventoryField.UNIT_FLEX_STRING07, IInventoryField.UNIT_FLEX_STRING08, IInventoryField.UNIT_FLEX_STRING09, IInventoryField.UNIT_FLEX_STRING10, IInventoryField.UNIT_FLEX_STRING11, IInventoryField.UNIT_FLEX_STRING12, IInventoryField.UNIT_FLEX_STRING13, IInventoryField.UNIT_FLEX_STRING14, IInventoryField.UNIT_FLEX_STRING15, UnitField.UNIT_DEPARTURE_ORDER_NBR, UnitField.UNIT_DECLARED_OB_CV, UnitField.UNIT_CURRENT_UFV_INTENDED_OB_CV, UnitField.UNIT_RTG_POL, UnitField.UNIT_RTG_POD1, UnitField.UNIT_RTG_POD2, UnitField.UNIT_RTG_OPT1, UnitField.UNIT_RTG_OPT2, UnitField.UNIT_RTG_OPT3, UnitField.UNIT_RTG_GROUP, UnitField.UNIT_RTG_RETURN_TO_LOCATION, UnitField.UNIT_RTG_TRUCKING_COMPANY, UnitField.UNIT_RTG_CARRIER_SERVICE, UnitField.UNIT_CMDY_ID, UnitField.UNIT_GDS_CONSIGNEE_AS_STRING, UnitField.UNIT_GDS_SHIPPER_AS_STRING, UnitField.UNIT_GDS_ORIGIN, UnitField.UNIT_GDS_DESTINATION, UnitField.UNIT_UE_MNR_STATUS, UnitField.UNIT_UE_PLACARDED, UnitField.UNIT_ARE_PLACARDS_MISMATCHED, UnitField.UNIT_GDS_RFREQ_TEMP_SET_POINT_C, UnitField.UNIT_GDS_RFREQ_CO2_PCT, UnitField.UNIT_GDS_RFREQ_O2_PCT, UnitField.UNIT_GDS_RFREQ_HUMIDITY_PCT, UnitField.UNIT_GDS_RFREQ_VENT_REQUIRED, UnitField.UNIT_GDS_RFREQ_VENT_UNIT, UnitField.UNIT_PRIMARY_EQ_BUILD_DATE, UnitField.UNIT_PRIMARY_EQ_CSC_EXPIRATION, UnitField.UNIT_GRADE_I_D, UnitField.UNIT_EQ_MATERIAL, UnitField.UNIT_CARRIAGE_UE, IInventoryBizMetafield.UNIT_ACRY_ID, IInventoryBizMetafield.UNIT_CHS_ACRY_ID, IInventoryBizMetafield.UNIT_WORST_HAZARD_CLASS, UnitField.UNIT_PRIMARY_EQ_TARE_WEIGHT_KG, UnitField.UNIT_PRIMARY_EQ_SAFE_WEIGHT_KG, UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS, UnitField.UNIT_VGM_ENTITY, UnitField.UNIT_VGM_VERIFIED_DATE, UnitField.UNIT_RTG_BOND_TRUCKING_COMPANY, UnitField.UNIT_RTG_RETURN_TO_LOCATION, UnitField.UNIT_PRIMARY_EQTYPE_ID, UnitField.UNIT_PRIMARY_EQ_TANK_RAILS});
    }

    @Nullable
    public String getUnitWorstHazardClass() {
        String hazClass = this.getUnitGoods() == null ? null : (this.getUnitGoods().getGdsHazards() == null ? null : this.getUnitGoods().getGdsHazards().getWorstHazardClass());
        return hazClass;
    }

    public UnitFacilityVisit adviseToFacility(Facility inFacility, LocPosition inInboundPosition, CarrierVisit inOutboundVisit) throws BizViolation {
        if (inInboundPosition == null) {
            throw BizFailure.create((String)"null inInboundPosition");
        }
        CarrierVisit ibCarrierVisit = inInboundPosition.resolveInboundCarrierVisit();
        Complex complex = ibCarrierVisit.getCvComplex();
        if (inOutboundVisit != null && !ObjectUtils.equals((Object)complex, (Object)inOutboundVisit.getCvComplex())) {
            throw BizFailure.create((String)"outbound visit and inbound visit for different complexes");
        }
        if (!ObjectUtils.equals((Object)complex, (Object)this.getUnitComplex())) {
            throw BizFailure.create((String)"inbound visit not to Unit's Complex");
        }
        UnitFacilityVisit futureUfv = this.getUfvForFacilityLiveOnly(inFacility);
        if (futureUfv == null) {
            CarrierVisit obVisit = inOutboundVisit;
            if (obVisit == null) {
                obVisit = CarrierVisit.getGenericCarrierVisit((Complex)complex);
            }
            futureUfv = UnitFacilityVisit.createUnitFacilityVisit(this, inFacility, inInboundPosition, obVisit);
        } else {
//            IUnitUpdateFilterRules updateFilter;
//            LocPosition existingPos = futureUfv.getUfvArrivePosition();
//            if (!inInboundPosition.isSamePosition(existingPos)) {
//                futureUfv.updatePositionForArrivingUnit(inInboundPosition);
//            }
//            if (inOutboundVisit != null && !ObjectUtils.equals((Object)futureUfv.getUfvIntendedObCv(), (Object)inOutboundVisit) && !(updateFilter = (IUnitUpdateFilterRules)Roastery.getBean((String)"unitUpdateFilter")).ignoreRoutingUpdate()) {
//                LOGGER.warn((Object)("findOrCreateInboundUnit: Change of O/B carrier for existing I/B Unit: " + this + "old/new" + (Object)futureUfv.getUfvIntendedObCv() + " " + (Object)inOutboundVisit));
//                futureUfv.updateObCv(inOutboundVisit);
//            }
        }
        return futureUfv;
    }

    public GoodsBase ensureGoods() {
        GoodsBase goods = this.getUnitGoods();
        if (goods == null) {
            LOGGER.error((Object)("ensureGoods: Unit had no IGoods, attaching now: " + this.getUnitId()));
            this.attachNewBaseGoods();
            goods = this.getUnitGoods();
        }
        return goods;
    }

    public boolean isStorageEmpty() {
        try {
            this.verifyStorageEmpty();
        }
        catch (BizViolation bizViolation) {
            return false;
        }
        return true;
    }

    public void verifyStorageEmpty() throws BizViolation {
        boolean storageEmpty = false;
        if (FreightKindEnum.MTY.equals((Object)this.getUnitFreightKind()) && (UnitCategoryEnum.STORAGE.equals((Object)this.getUnitCategory()) || UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory()))) {
            storageEmpty = true;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("verifyStorageEmpty got called and is returning: " + storageEmpty));
        }
        if (!storageEmpty) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_STORAGE_EMPTY, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public void verifyNotManifestedEmpty() throws BizViolation {
        if (!FreightKindEnum.MTY.equals((Object)this.getUnitFreightKind()) || UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory())) {
            // empty if block
        }
    }

    public void verifyImportOrTranship() throws BizViolation {
        if (!UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory()) && !UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_IMPORT_OR_TRANSSHIP, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public void verifyExportOrTranship() throws BizViolation {
        if (!UnitCategoryEnum.EXPORT.equals((Object)this.getUnitCategory()) && !UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_EXPORT_OR_TRANSSHIP, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public void verifyImportOrTranshipOrDomestic() throws BizViolation {
        if (!(UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory()) || UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory()) || UnitCategoryEnum.DOMESTIC.equals((Object)this.getUnitCategory()))) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_IMPORT_OR_TRANSSHIP_OR_DOMESTIC, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public boolean isExportOrTranshipDrayOff() {
        boolean drayoff = true;
        try {
            this.verifyExportOrTranshipDrayOff();
        }
        catch (BizViolation bizViolation) {
            drayoff = false;
        }
        return drayoff;
    }

    public boolean isDrayIn() {
        DrayStatusEnum drayStatus = this.getUnitDrayStatus();
        return DrayStatusEnum.DRAYIN.equals((Object)drayStatus);
    }

    public boolean isDrayOff() {
        DrayStatusEnum drayStatus = this.getUnitDrayStatus();
        return DrayStatusEnum.FORWARD.equals((Object)drayStatus) || DrayStatusEnum.RETURN.equals((Object)drayStatus) || DrayStatusEnum.OFFSITE.equals((Object)drayStatus) || DrayStatusEnum.TRANSFER.equals((Object)drayStatus);
    }

    public void verifyExportOrTranshipDrayOff() throws BizViolation {
        this.verifyExportOrTranship();
        if (UnitCategoryEnum.EXPORT.equals((Object)this.getUnitCategory()) ? !DrayStatusEnum.RETURN.equals((Object)this.getUnitDrayStatus()) && !DrayStatusEnum.FORWARD.equals((Object)this.getUnitDrayStatus()) && !DrayStatusEnum.OFFSITE.equals((Object)this.getUnitDrayStatus()) && !DrayStatusEnum.TRANSFER.equals((Object)this.getUnitDrayStatus()) : UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory()) && !DrayStatusEnum.FORWARD.equals((Object)this.getUnitDrayStatus()) && !DrayStatusEnum.OFFSITE.equals((Object)this.getUnitDrayStatus())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_DRAYOFF, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public void verifyImportOrTranshipDrayIn() throws BizViolation {
        if (!(!UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory()) && !UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory()) || DrayStatusEnum.DRAYIN.equals((Object)this.getUnitDrayStatus()) || DrayStatusEnum.OFFSITE.equals((Object)this.getUnitDrayStatus()) || DrayStatusEnum.TRANSFER.equals((Object)this.getUnitDrayStatus()))) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__NOT_DRAYIN, null, (Object)this, (Object)this.getUnitCategory(), (Object)this.getUnitFreightKind());
        }
    }

    public void verifyImportHasBls() throws BizViolation {
        if (UnitCategoryEnum.IMPORT.equals((Object)this.getUnitCategory())) {
            // empty if block
        }
    }

    @Nullable
    public final UnitFacilityVisit getOneLiveUfvFromFacilities(List<Facility> inFcyList) {
        UnitFacilityVisit ufv = null;
        if (inFcyList == null || inFcyList.isEmpty()) {
            return null;
        }
        Iterator<Facility> fcyItr = inFcyList.iterator();
        while (fcyItr.hasNext() && ufv == null) {
            Facility fcy = fcyItr.next();
            ufv = this.getUfvForFacilityLiveOnly(fcy);
        }
        return ufv;
    }

    @Nullable
    public final UnitFacilityVisit getUfvForFacilityAndEventTime(Facility inFacility, Date inEventTime) {
        UnitFacilityVisit bestUfv = null;
        Date now = new Date();
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object thisUfv : ufvSet) {
                if (!((UnitFacilityVisit)thisUfv).isForFacility(inFacility)) continue;
                bestUfv = this.getBestUnitFacilityVisitBasedOnTimeComplete(inEventTime, bestUfv, now, (UnitFacilityVisit)thisUfv);
            }
        }
        return bestUfv;
    }

    private UnitFacilityVisit getBestUnitFacilityVisitBasedOnTimeComplete(Date inEventTime, UnitFacilityVisit inBestUfv, Date inNow, UnitFacilityVisit inThisUfv) {
        if (inBestUfv == null) {
            inBestUfv = inThisUfv;
        } else {
            Date tThis;
            Date tBest = inBestUfv.getUfvTimeComplete() == null ? inNow : inBestUfv.getUfvTimeComplete();
            Date date = tThis = inThisUfv.getUfvTimeComplete() == null ? inNow : inThisUfv.getUfvTimeComplete();
            if ((tThis.after(inEventTime) || Unit.getDateTillSeconds(tThis).equals(Unit.getDateTillSeconds(inEventTime))) && (tThis.before(tBest) || tBest.before(inEventTime))) {
                inBestUfv = inThisUfv;
            }
        }
        return inBestUfv;
    }

    private static Date getDateTillSeconds(Date inDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);
        cal.set(14, 0);
        return DateUtil.getDSTSafeCalendarTime((Calendar)cal);
    }

    @Nullable
    public final UnitFacilityVisit getUfvForEventTime(Date inEventTime) {
        UnitFacilityVisit bestUfv = null;
        Date now = new Date();
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object thisUfv : ufvSet) {
                bestUfv = this.getBestUnitFacilityVisitBasedOnTimeComplete(inEventTime, bestUfv, now, (UnitFacilityVisit)thisUfv);
            }
        }
        return bestUfv;
    }

    public void receiveIntoFacility(Facility inFacility, LocPosition inArrivePosition) throws BizViolation {
        UnitFacilityVisit ufv = this.getUfvForFacilityLiveOnly(inFacility);
        if (ufv == null) {
            throw BizFailure.create((String)("receiveIntoFacility: no future UFV for Unit " + this + " in facility " + (Object)inFacility));
        }
        ufv.receiveIntoFacility(inArrivePosition);
    }

    public void checkAndAdjustTransitStateIfAllowed(Facility inFacility) throws BizViolation {
        UnitFacilityVisit activeUfv = this.getActiveUfvNowActiveInAnyUnit();
        if (activeUfv != null && (!activeUfv.isForFacility(inFacility) || DataSourceEnum.IN_GATE.equals((Object)ContextHelper.getThreadDataSource()) && UnitCategoryEnum.THROUGH.equals((Object)this.getUnitCategory()))) {
            BizViolation bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__ALREADY_IN_FACILITY, null, (Object)this, (Object)inFacility);
            if (UfvTransitStateEnum.S20_INBOUND.equals((Object)activeUfv.getUfvTransitState())) {
                String id = activeUfv.getUfvUnit().getUnitId();
                LocPosition ufvPos = activeUfv.getLastYardPosition();
                String pos = ufvPos == null ? null : ufvPos.getPosName();
                String fcyId = activeUfv.getUfvFacility().getFcyId();
                bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT__DEMOTED_IN_ANOTHER_FACILITY, null, (Object)id, (Object)fcyId, (Object)pos);
                LOGGER.warn((Object)bv.getLocalizedMessage());
                activeUfv.revertInboundToAdvised();
                activeUfv.getUfvUnit().revertActiveToAdvised();
                activeUfv.setUfvVisibleInSparcs(Boolean.FALSE);
                HibernateApi.getInstance().flush();
            } else {
                throw bv;
            }
        }
    }

    @Nullable
    public UnitFacilityVisit getActiveUfvNowActiveInAnyUnit() {
        Unit activeUnit;
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (ufv != null) {
            return ufv;
        }
        IUnitFinder unitFinder = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        if (this.getUnitPrimaryUe() != null && this.getUnitPrimaryUe().getUeEquipment() != null && (activeUnit = unitFinder.findActiveUnit(this.getUnitComplex(), this.getUnitPrimaryUe().getUeEquipment())) != null) {
            UnitFacilityVisit activeUfv = activeUnit.getUnitActiveUfvNowActive();
            return activeUfv;
        }
        return null;
    }

    public void deliverOutOfFacility(Facility inFacility) throws BizViolation {
        UnitFacilityVisit ufv;
        UnitCombo combo;
        if (UnitVisitStateEnum.DEPARTED.equals((Object)this.getUnitVisitState()) && (combo = this.getUnitCombo()) != null) {
            Set comboUnits = combo.getUcUnits();
            for (Object unit : comboUnits) {
                if (!UnitVisitStateEnum.DEPARTED.equals((Object)((Unit)unit).getUnitVisitState())) continue;
                return;
            }
        }
        if ((ufv = this.getUnitActiveUfvNowActive()) == null || !ufv.isForFacility(inFacility)) {
            throw BizFailure.create((String)("can not deliver unit, no active UFV in facility " + this));
        }
        ufv.deliverOutOfFacility();
    }

    public void move(LocPosition inNewPosition) throws BizViolation {
        if (!this.isActive()) {
            throw BizFailure.create((String)("move: Unit not Active: " + this));
        }
        Facility facility = inNewPosition.resolveFacility();
        if (facility == null) {
            throw BizFailure.create((String)("move: Can not move a Unit to a generic position: " + (Object)inNewPosition));
        }
        UnitFacilityVisit ufv = this.getUfvForFacilityLiveOnly(facility);
        if (ufv == null) {
            throw BizFailure.create((String)("move: no UFV for Unit " + this + " at Facility " + (Object)facility));
        }
        UnitFacilityVisit activeUfv = this.getUnitActiveUfvNowActive();
        if (activeUfv == null) {
            LOGGER.warn((Object)("move: auto-activation of UFV: " + ufv));
            ufv.makeActive();
        } else if (!activeUfv.equals(ufv)) {
            LOGGER.warn((Object)("move: auto-deactivation of UFV: " + activeUfv + ", AND auto-activation of UFV: " + ufv));
            activeUfv.makeDeparted();
            ufv.makeActive();
        }
        ufv.move(inNewPosition, null);
    }

    public void returnToStorageUnit() throws BizViolation {
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        boolean isInYard = false;
        if (ufv != null) {
            UfvTransitStateEnum transitState = ufv.getUfvTransitState();
            boolean bl = isInYard = UfvTransitStateEnum.S30_ECIN.equals((Object)transitState) || UfvTransitStateEnum.S40_YARD.equals((Object)transitState);
        }
        if (!isInYard) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.RETURN_TO_STORAGE_UNIT_NEEDS_BE_IN_YARD, null, (Object)this.getUnitId());
        }
        if (!FreightKindEnum.MTY.equals((Object)this.getUnitFreightKind())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.RETUNR_TO_STORAGE_UNIT_IS_NOT_EMPTY, null, (Object)this.getUnitId());
        }
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        BizViolation violation = srvcMgr.verifyEventAllowed((IEventType)EventEnum.UNIT_RETURN_TO_STORAGE, (IServiceable)this);
        if (violation != null) {
            throw violation;
        }
        FieldChangeTracker tracker = this.createFieldChangeTracker();
        this.adjustOrderTallyForReturnToStorage();
        this.setUnitCategory(UnitCategoryEnum.STORAGE);
        CarrierVisit genericTruckVisit = CarrierVisit.getGenericTruckVisit((Complex)this.getUnitComplex());
        ufv.updateObCv(genericTruckVisit);
        Routing newRouting = new Routing();
        newRouting.setRtgDeclaredCv(genericTruckVisit);
        this.setUnitRouting(newRouting);
        UnitEquipment unitEquipment = this.getUnitPrimaryUe();
        if (!this.isReservedForBooking()) {
            IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
            equipOrdMgr.emptyReturnCreditTallyForBkg(unitEquipment.getUeEquipment());
        }
        unitEquipment.setUeDepartureOrderItem(null);
        this.recordUnitEvent((IEventType)EventEnum.UNIT_RETURN_TO_STORAGE, tracker.getChanges((IEntity)this), null);
    }

    public void adjustOrderTallyForReturnToShipper() {
        EqBaseOrder departOrder = this.getDepartureOrder();
        if (departOrder != null && (EquipmentOrderSubTypeEnum.BOOK.equals((Object)departOrder.getEqboSubType()) || EquipmentOrderSubTypeEnum.RAIL.equals((Object)departOrder.getEqboSubType()))) {
            LOGGER.info((Object)("Decrementing Booking Tally Receive for return to shipper on " + this.getUnitId()));
            departOrder.decrementTallyRcv(this);
            IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
            UnitEquipment unitEquipment = this.getUnitPrimaryUe();
            if (unitEquipment != null) {
                EqBaseOrderItem eqBaseOrderItem = unitEquipment.getUeDepartureOrderItem();
                if (eqBaseOrderItem != null) {
                    LOGGER.info((Object)("Decrementing Booking Tally for return to shipper on " + this.getUnitId()));
                    equipOrdMgr.decrementTally((Serializable)eqBaseOrderItem.getEqboiGkey());
                }
                unitEquipment.setUeDepartureOrderItem(null);
            }
        }
    }

    public void makeActive() throws BizViolation {
        this.updateUnitVisitState(UnitVisitStateEnum.ACTIVE);
    }

    public void revertActiveToAdvised() throws BizViolation {
        if (!UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState())) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__REVERT_ACTIVE_TO_ADVISED_NOT_ACTIVE, null);
        }
        this.updateUnitVisitState(UnitVisitStateEnum.ADVISED);
    }

    public void makeRetired() throws BizViolation {
        this.updateUnitVisitState(UnitVisitStateEnum.RETIRED);
    }

    protected void makeDeparted() throws BizViolation {
        this.updateUnitVisitState(UnitVisitStateEnum.DEPARTED);
        this.activateNextAdvisedUnit();
    }

    @Override
    public boolean isActive() {
        return UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState());
    }

    public Unit attachCarriage(Equipment inEquipment) throws BizViolation {
        return this.attachEquipment(inEquipment, EqUnitRoleEnum.CARRIAGE, false);
    }

    public Unit attachCarriage(Equipment inEquipment, String inSlotOnCarriage) throws BizViolation {
        return this.attachCarriage(inEquipment, inSlotOnCarriage, false);
    }

    public Unit attachCarriage(Equipment inEquipment, String inSlotOnCarriage, boolean inUseActive) throws BizViolation {
        return this.attachEquipment(inEquipment, EqUnitRoleEnum.CARRIAGE, false, true, true, true, inUseActive, false, false, inSlotOnCarriage);
    }

    public Unit attachAccessory(Equipment inEquipment) throws BizViolation {
        EquipIsoGroupEnum isoGroup = inEquipment.getEqEquipType().getEqtypIsoGroup();
        EqUnitRoleEnum role = EquipIsoGroupEnum.GU.equals((Object)isoGroup) ? EqUnitRoleEnum.ACCESSORY_ON_CHS : EqUnitRoleEnum.ACCESSORY;
        return this.attachEquipment(inEquipment, role, false);
    }

    public Unit attachAccessoryOnChassis(Equipment inEquipment) throws BizViolation {
        return this.attachEquipment(inEquipment, EqUnitRoleEnum.ACCESSORY_ON_CHS, false);
    }

    public Unit attachPayload(Equipment inEquipment) throws BizViolation {
        return this.attachEquipment(inEquipment, EqUnitRoleEnum.PAYLOAD, true);
    }

    public Unit attachEquipment(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed) throws BizViolation {
        boolean isValidated = !DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource());
        return this.attachEquipment(inEquipment, inRole, inSwipeIsAllowed, isValidated, true);
    }

    public void detachPayload(String inCtrId) throws BizViolation {
        Equipment ctr = this.getEquipment(inCtrId, EqUnitRoleEnum.PAYLOAD);
        Unit attachedUnit = this.getAttachedUnit(ctr);
        if (attachedUnit == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS_EQUIP_NOT_ATTACHED_TO_UNIT, null, (Object)inCtrId, (Object)this);
        }
        Collection<Unit> payloadUnits = this.getPayloadUnits();
        if (payloadUnits != null && !payloadUnits.isEmpty()) {
            for (Unit unit : payloadUnits) {
                if (!unit.getUnitEquipment().equals((Object)ctr)) continue;
                unit.detach();
            }
        }
    }

    public Unit attachEquipment(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inStrictlyValidated, boolean inEventRecordAllowed) throws BizViolation {
        return this.attachEquipment(inEquipment, inRole, inSwipeIsAllowed, inStrictlyValidated, inEventRecordAllowed, false);
    }

    public Unit validateLocationAndAttachEquipment(String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti) throws BizViolation {
        return this.validateLocationAndAttachEquipment(inEqId, inRole, inSwipeIsAllowed, inAllowMulti, null);
    }

    public Unit validateLocationAndAttachEquipment(String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, String inSlotLocation, boolean inIsStrictlyValidated) throws BizViolation {
        Equipment eq = this.getEquipment(inEqId, inRole);
        return this.validateLocationAndAttachEq(eq, inRole, inSwipeIsAllowed, inAllowMulti, false, true, false, inSlotLocation, inIsStrictlyValidated);
    }

    public Unit validateLocationAndAttachEquipment(String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, String inSlotLocation) throws BizViolation {
        Equipment eq = this.getEquipment(inEqId, inRole);
        return this.validateLocationAndAttachEq(inEqId, inRole, inSwipeIsAllowed, inAllowMulti, false, true, inSlotLocation);
    }

    public Unit attachEquipmentFromHatchClerk(String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inEventRecordAllowed, String inSlotOnCarriage) throws BizViolation {
        return this.validateLocationAndAttachEq(inEqId, inRole, inSwipeIsAllowed, inAllowMulti, true, true, inSlotOnCarriage);
    }

    public Unit attachEquipmentFromHatchClerk(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inEventRecordAllowed, @Nullable String inSlotOnCarriage) throws BizViolation {
        return this.validateLocationAndAttachEq(inEquipment, inRole, inSwipeIsAllowed, inAllowMulti, true, true, inSlotOnCarriage);
    }

    private Unit validateLocationAndAttachEq(String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inHatchClerkValidation, boolean inEventRecordAllowed, @Nullable String inSlotOnCarriage) throws BizViolation {
        Equipment eq = this.getEquipment(inEqId, inRole);
        return this.validateLocationAndAttachEq(eq, inRole, inSwipeIsAllowed, inAllowMulti, inHatchClerkValidation, inEventRecordAllowed, inSlotOnCarriage);
    }

    private Unit validateLocationAndAttachEq(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inHatchClerkValidation, boolean inEventRecordAllowed, @Nullable String inSlotOnCarriage) throws BizViolation {
        return this.validateLocationAndAttachEq(inEquipment, inRole, inSwipeIsAllowed, inAllowMulti, inHatchClerkValidation, inEventRecordAllowed, false, inSlotOnCarriage);
    }

    private Unit validateLocationAndAttachEq(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inHatchClerkValidation, boolean inEventRecordAllowed, boolean inUseActive, @Nullable String inSlotOnCarriage) throws BizViolation {
        if (EqUnitRoleEnum.PRIMARY.equals((Object)inRole)) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_CANNOT_ATTACH_EQ_IN_ROLE, null, (Object)inEquipment.getEqIdFull(), (Object)((Object)inRole));
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        Equipment unitEq = this.getUnitEquipment();
        Unit newUnit = this.attachEquipment(inEquipment, inRole, inSwipeIsAllowed, Unit.isStrictlyValidated(), inEventRecordAllowed, inAllowMulti, inUseActive, inHatchClerkValidation, true, inSlotOnCarriage);
        return newUnit;
    }

    private Unit validateLocationAndAttachEq(Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inAllowMulti, boolean inHatchClerkValidation, boolean inEventRecordAllowed, boolean inUseActive, @Nullable String inSlotOnCarriage, boolean inIsStrictlyValidated) throws BizViolation {
        if (EqUnitRoleEnum.PRIMARY.equals((Object)inRole)) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_CANNOT_ATTACH_EQ_IN_ROLE, null, (Object)inEquipment.getEqIdFull(), (Object)((Object)inRole));
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        Equipment unitEq = this.getUnitEquipment();
        Unit newUnit = this.attachEquipment(inEquipment, inRole, inSwipeIsAllowed, inIsStrictlyValidated, inEventRecordAllowed, inAllowMulti, inUseActive, inHatchClerkValidation, true, inSlotOnCarriage);
        return newUnit;
    }

    public Unit attachEquipment(@NotNull Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inStrictlyValidated, boolean inEventRecordAllowed, boolean inAllowMulti) throws BizViolation {
        return this.attachEquipment(inEquipment, inRole, inSwipeIsAllowed, inStrictlyValidated, inEventRecordAllowed, inAllowMulti, false, false, false, null);
    }

    private Unit attachEquipment(@NotNull Equipment inEquipment, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inStrictlyValidated, boolean inEventRecordAllowed, boolean inAllowMulti, boolean inMatchActiveUnit, boolean inHatchClerkValidation, boolean inValidateLocation, @Nullable String inSlotOnCarriage) throws BizViolation {
        UnitNode eqNode = new UnitNode(inEquipment);
        String slotIndex = null;
        if (inEquipment.getEqEquipType().isChassis()) {
            slotIndex = Unit.findSlotIndexFromLabel(inEquipment, inSlotOnCarriage);
        }
        UnitNode linkedUnit = this._unitNode.attach(eqNode, inRole, inSwipeIsAllowed, inStrictlyValidated, inAllowMulti, inEventRecordAllowed, inMatchActiveUnit, inHatchClerkValidation, inValidateLocation, slotIndex);
        return linkedUnit.getUnit();
    }

    private static boolean isStrictlyValidated() {
        return !DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource());
    }

    @Nullable
    static String findSlotIndexFromLabel(Equipment inChsEq, @Nullable String inSlotLabel) throws BizViolation {
        String labelIndex = inChsEq.getEqEquipType().getChsSlotIndex(inSlotLabel);
        if (labelIndex != null) {
            return labelIndex;
        }
        if (Strings.isNotEmpty((String)inSlotLabel)) {
            try {
                Integer.parseInt(inSlotLabel);
            }
            catch (NumberFormatException e) {
                throw new BizViolation(IArgoPropertyKeys.INVALID_SLOT_ON_CARRIAGE, (Throwable)e, null, null, null);
            }
        }
        return inSlotLabel;
    }

    private Equipment getEquipment(String inEqId, EqUnitRoleEnum inRole) throws BizViolation {
        Equipment eq = null;
        if (EqUnitRoleEnum.ACCESSORY.equals((Object)inRole) || EqUnitRoleEnum.ACCESSORY_ON_CHS.equals((Object)inRole)) {
            eq = Accessory.findAccessory((String)inEqId);
        } else if (EqUnitRoleEnum.CARRIAGE.equals((Object)inRole)) {
            eq = Chassis.findChassis((String)inEqId);
        } else if (EqUnitRoleEnum.PRIMARY.equals((Object)inRole)) {
            eq = Container.findContainer((String)inEqId);
        } else if (EqUnitRoleEnum.PAYLOAD.equals((Object)inRole)) {
            if (this.isContainer()) {
                eq = Container.findContainer((String)inEqId);
            } else {
                eq = Container.findContainer((String)inEqId);
                if (eq == null) {
                    eq = (Equipment)Chassis.findChassis((String)inEqId);
                }
            }
        } else {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_CANNOT_ATTACH_EQ_IN_ROLE, null, (Object)inEqId, (Object)((Object)inRole));
        }
        if (eq == null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.EQUIPMENT_NOT_FOUND, null, (Object)inEqId);
        }
        return eq;
    }

    public boolean isContainer() {
        Equipment primaryEq = this.getUnitEquipment();
        return primaryEq != null && EquipClassEnum.CONTAINER.equals((Object)primaryEq.getEqClass());
    }

    public void detachCarriage(String inEventNote) throws BizViolation {
        Unit carriageUnit = this.getCarriageUnit();
        if (carriageUnit != null) {
            carriageUnit.detach(this.thisUnit(), inEventNote);
        }
    }

    public void detachAccessoriesOnChassis(String inEventNote) throws BizViolation {
        this.detachUnits(this.getAccessoryOnChsUnits(), inEventNote);
    }

    public void detachAccessories(String inEventNote) throws BizViolation {
        this.detachUnits(this.getAccessoryUnits(), inEventNote);
    }

    public void detachAccessoriesOnPayload(String inEventNote) throws BizViolation {
        this.detachUnits(this.getAccessoryUnits(), inEventNote);
        Collection<Unit> payloadUnits = this.getPayloadUnits();
        for (Unit payloadUnit : payloadUnits) {
            payloadUnit.detachAccessories(inEventNote);
        }
    }

    private void detachUnits(Collection<Unit> inRelatedUnits, String inEventNote) throws BizViolation {
        for (Unit eqUnit : inRelatedUnits) {
            eqUnit.detach(this.thisUnit(), eqUnit.getUnitEquipment().getEqIdFull() + ": " + inEventNote);
        }
    }

    public boolean hasAnyAttachedUe() {
        Set ueSet = this.getUnitUeSet();
        if (!ueSet.isEmpty()) {
            for (Object ue : ueSet) {
                if (EqUnitRoleEnum.PRIMARY.equals((Object)((UnitEquipment)ue).getUeEqRole()) || !((UnitEquipment)ue).hasNotBeenDetached()) continue;
                return true;
            }
        }
        return false;
    }

    @Nullable
    public Unit getCurrentlyAttachedUe(Equipment inEquipment) {
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null) {
            for (Object anUeSet : ueSet) {
                if (!((Unit)anUeSet).hasNotBeenDetached() || !ObjectUtils.equals((Object)((Unit)anUeSet).getUeEquipment(), (Object)inEquipment)) continue;
                return ((Unit)anUeSet);
            }
        }
        return null;
    }

    @Override
    public void setUnitRouting(Routing inRouting) {
        super.setUnitRouting(inRouting);
    }

    public void setUnitRtgTruckingCompany(ScopedBizUnit inTrkc) {
        Routing rtg = this.getUnitRouting();
        if (rtg != null) {
            rtg.setRtgTruckingCompany(inTrkc);
        }
    }

    @Nullable
    public ScopedBizUnit getUnitRtgTruckingCompany() {
        ScopedBizUnit trkc = null;
        Routing rtg = this.getUnitRouting();
        if (rtg != null) {
            trkc = rtg.getRtgTruckingCompany();
        }
        return trkc;
    }

    public void updateUnitRouting(Routing inRouting) {
        this.setUnitRouting(inRouting);
    }

    public void updateUnitObservedPlacards(Set inObservedPlacards) {
        this.setUnitObservedPlacards(inObservedPlacards);
    }

    public void swipeChsByOwnersChs() throws BizViolation {
        Unit chsUe = this.getUeInRole(EqUnitRoleEnum.CARRIAGE);
        if (chsUe != null) {
            chsUe.detach("swiped by owners chassis");
        }
    }

//    public void updateImportDeliveryOrder(ImportDeliveryOrder inIdo) {
//        this.setUnitImportDeliveryOrder(inIdo);
//        if (inIdo == null) {
//            this.recordUnitEvent((IEventType) EventEnum.UNIT_CANCEL_RESERVE, null, IInventoryPropertyKeys.UNIT_CANCEL_RESERVE.getKey());
//        }
//    }

    public void updateImportDeliveryOrderExpiryDate(Date inExpiryDate) {
        this.setUnitImportDeliveryOrderExpiryDate(inExpiryDate);
    }

    public Boolean getUnitIsBookingTempDifferent() {
        int unitTempRounded;
        int bkgTempRounded;
        boolean different = false;
        if (this.getUnitPrimaryUe() == null) {
            return false;
        }
        if (this.getUnitPrimaryUe().getUeDepartureOrderItem() == null) {
            return false;
        }
        EqBaseOrderItem orderItem = this.getUnitPrimaryUe().getUeDepartureOrderItem();
        Double bkgTemp = orderItem.getEqoiTempRequired();
        Double unitTemp = null;
        ReeferRqmnts reefer = this.getUnitGoods().getGdsReeferRqmnts();
        if (reefer != null) {
            unitTemp = reefer.getRfreqTempRequiredC();
        }
        if (bkgTemp == null && unitTemp != null || bkgTemp != null && unitTemp == null) {
            different = true;
        } else if (bkgTemp != null && (bkgTempRounded = bkgTemp.intValue()) != (unitTempRounded = unitTemp.intValue())) {
            different = true;
        }
        return different;
    }

    public Boolean getIsBookingHoldPartialQtyReceived() {
        if (this.getUnitPrimaryUe() == null || this.getUnitPrimaryUe().getUeDepartureOrderItem() == null) {
            return true;
        }
        if (EquipmentOrderSubTypeEnum.BOOK.equals((Object)this.getUnitPrimaryUe().getUeDepartureOrderItem().getEqboiOrder().getEqboSubType()) || EquipmentOrderSubTypeEnum.RAIL.equals((Object)this.getUnitPrimaryUe().getUeDepartureOrderItem().getEqboiOrder().getEqboSubType())) {
            IArgoEquipmentOrderManager eqOrderMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
            return eqOrderMgr.isBookingPartialHoldQtyReceived((Serializable)this.getUnitPrimaryUe().getUeDepartureOrderItem().getEqboiOrder().getEqboGkey());
        }
        return true;
    }

    public Boolean getUnitIsImportDeliveryOrderValid() {
        Boolean valid = Boolean.FALSE;
//        if (this.getUnitImportDeliveryOrder() != null) {
//            Date timeNow = ArgoUtils.timeNow();
//            Date idoExpiryDate = this.getUnitImportDeliveryOrder().getIdoExpiryDate();
//            if (idoExpiryDate != null && (timeNow.before(idoExpiryDate) || DateUtil.isSameDay((Date)idoExpiryDate, (Date)timeNow, (TimeZone)ContextHelper.getThreadUserTimezone()))) {
//                valid = Boolean.TRUE;
//            }
//            Date unitExpiryDate = this.getUnitImportDeliveryOrderExpiryDate();
//            if (!valid.booleanValue() && unitExpiryDate != null && (timeNow.before(unitExpiryDate) || DateUtil.isSameDay((Date)unitExpiryDate, (Date)timeNow, (TimeZone)ContextHelper.getThreadUserTimezone()))) {
//                valid = Boolean.TRUE;
//            }
//            if (!valid.booleanValue() && idoExpiryDate == null && unitExpiryDate == null) {
//                valid = Boolean.TRUE;
//            }
//        }
        return valid;
    }

    public ExportTypeEnum[] getExportFullType() {
        ArrayList<ExportTypeEnum> exportTypes = new ArrayList<ExportTypeEnum>();
        GoodsBase goods = this.ensureGoods();
        if (goods.getGdsIsHazardous() != null && goods.getGdsIsHazardous().booleanValue()) {
            exportTypes.add(ExportTypeEnum.HAZARDOUS);
        }
        if (this.isReefer()) {
            exportTypes.add(ExportTypeEnum.REEFER);
        }
        if (exportTypes.size() <= 0) {
            exportTypes.add(ExportTypeEnum.DRY);
        }
   //     return exportTypes.toArray((T[])new ExportTypeEnum[0]);
        return null;
    }

    public boolean isReefer() {
        Boolean b = this.getUnitRequiresPower();
        return b != null && b != false;
    }

    public boolean isOutOfGuage() {
        Boolean isOog = this.getUnitIsOog();
        return isOog != null && isOog != false;
    }

    @Override
    @Nullable
    public Equipment getPrimaryEq() {
        UnitEquipment primaryUe = this.getUnitPrimaryUe();
        if (primaryUe != null) {
            return primaryUe.getUeEquipment();
        }
        return null;
    }

    public Routing getCopyOfRouting() {
        Routing rtg = this.getUnitRouting();
        return rtg.getDefensiveCopy();
    }

    public Routing getSafeRoutingCopy() {
        Routing rtg = this.getUnitRouting();
        return rtg != null ? rtg.getDefensiveCopy() : Routing.createEmptyRouting();
    }

    public String getUnitOOGCode() {
        Long oogBack = this.getUnitOogBackCm();
        Long oogFront = this.getUnitOogFrontCm();
        Long oogLeft = this.getUnitOogLeftCm();
        Long oogRight = this.getUnitOogRightCm();
        Long oogTop = this.getUnitOogTopCm();
        long lengthVal = (oogBack != null ? oogBack : 0L) + (oogFront != null ? oogFront : 0L);
        char length = lengthVal > 0L ? (char)'L' : '-';
        long widthVal = (oogLeft != null ? oogLeft : 0L) + (oogRight != null ? oogRight : 0L);
        char width = widthVal > 0L ? (char)'W' : '-';
        long heightVal = oogTop != null ? oogTop : 0L;
        char height = heightVal > 0L ? (char)'H' : '-';
        return String.valueOf(height) + ' ' + width + ' ' + length;
    }

    @Nullable
    public Date getUnitLastReeferRecordDate() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Date latestDate = latestRecord != null ? latestRecord.getRfrecTime() : null;
        return latestDate;
    }

    @Nullable
    public Double getUnitLastReeferRecordTemp() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestTemp = latestRecord != null ? latestRecord.getRfrecReturnTmp() : null;
        return latestTemp;
    }

    @Nullable
    public String getUnitLastReeferRecordFaultCode() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        String latestFaultCode = latestRecord != null ? latestRecord.getRfrecFaultCode() : null;
        return latestFaultCode;
    }

    @Nullable
    public String getUnitLastReeferRecordRemark() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        String latestRemark = latestRecord != null ? latestRecord.getRfrecRemark() : null;
        return latestRemark;
    }

    @Nullable
    public Double getUnitLastReeferRecordSupplyTemp() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestSupplyTemp = latestRecord != null ? latestRecord.getRfrecSupplyTmp() : null;
        return latestSupplyTemp;
    }

    @Nullable
    public Double getUnitLastReeferRecordSetPointTemp() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestSetPointTemp = latestRecord != null ? latestRecord.getRfrecSetPointTmp() : null;
        return latestSetPointTemp;
    }

    @Nullable
    public Double getUnitLastReeferRecordO2() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestO2 = latestRecord != null ? latestRecord.getRfrecO2() : null;
        return latestO2;
    }

    @Nullable
    public Double getUnitLastReeferRecordCO2() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestCO2 = latestRecord != null ? latestRecord.getRfrecCO2() : null;
        return latestCO2;
    }

    @Nullable
    public Double getUnitLastReeferRecordHmdty() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestHmdty = latestRecord != null ? latestRecord.getRfrecHmdty() : null;
        return latestHmdty;
    }

    @Nullable
    public Double getUnitLastReeferRecordDefrostTemp() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestDefrostTemp = latestRecord != null ? latestRecord.getRfrecDefrostTmp() : null;
        return latestDefrostTemp;
    }

    @Nullable
    public Double getUnitLastReeferRecordFuelLevel() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestFuelLevel = latestRecord != null ? latestRecord.getRfrecFuelLevel() : null;
        return latestFuelLevel;
    }

    @Nullable
    public Double getUnitLastReeferRecordVentSetting() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        Double latestVentSetting = latestRecord != null ? latestRecord.getRfrecVentSetting() : null;
        return latestVentSetting;
    }

    @Nullable
    public VentUnitEnum getUnitLastReeferRecordVentUnit() {
        ReeferRecord latestRecord = this.getLatestReeferRecord();
        VentUnitEnum latestVentUnit = latestRecord != null ? latestRecord.getRfrecVentUnit() : null;
        return latestVentUnit;
    }

    @Nullable
    public ReeferRecord getLatestReeferRecord() {
        ReeferRecord latestRecord = null;
        Set reefRecordSet = this.getUnitReeferRecordSet();
        if (reefRecordSet != null && !reefRecordSet.isEmpty()) {
            Object[] reefRecords = reefRecordSet.toArray();
            Arrays.sort(reefRecords);
            latestRecord = (ReeferRecord)reefRecords[reefRecords.length - 1];
        }
        return latestRecord;
    }

    @Nullable
    public Date getUnitFirstReeferRecordAfterInTimeDate() {
        UnitFacilityVisit activeUfv = this.getUnitActiveUfvNowActive();
        Date latestDate = null;
        if (activeUfv != null) {
            Date timeIn = activeUfv.getUfvTimeIn();
            ReeferRecord firstRecord = this.getFirstReeferRecordAfterDate(timeIn);
            latestDate = firstRecord != null ? firstRecord.getRfrecTime() : null;
        }
        return latestDate;
    }

    @Nullable
    public Double getUnitFirstReeferRecordAfterInTimeTemp() {
        UnitFacilityVisit activeUfv = this.getUnitActiveUfvNowActive();
        Double latestTemp = null;
        if (activeUfv != null) {
            Date timeIn = activeUfv.getUfvTimeIn();
            ReeferRecord firstRecord = this.getFirstReeferRecordAfterDate(timeIn);
            latestTemp = firstRecord != null ? firstRecord.getRfrecReturnTmp() : null;
        }
        return latestTemp;
    }

    @Nullable
    public String getUnitAttachedPayloadEquipIds() {
        StringBuilder ids = new StringBuilder();
        Set unitEquipments = this.getUnitUeSet();
        if (unitEquipments != null) {
            Iterator iterator = unitEquipments.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                EqUnitRoleEnum currentUeRole;
                UnitEquipment currentUe = (UnitEquipment)iterator.next();
                if (currentUe == null || !EqUnitRoleEnum.PAYLOAD.equals((Object)(currentUeRole = currentUe.getUeEqRole()))) continue;
                if (counter > 0) {
                    ids.append(";");
                }
                Equipment currentUeEquip = currentUe.getUeEquipment();
                ids.append(currentUeEquip.getEqIdFull());
                ++counter;
            }
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    @Nullable
    public String getUnitAttachedEquipIds() {
        StringBuilder ids = new StringBuilder();
        Set unitEquipments = this.getUnitUeSet();
        if (unitEquipments != null) {
            Iterator iterator = unitEquipments.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                EqUnitRoleEnum currentUeRole;
                UnitEquipment currentUe = (UnitEquipment)iterator.next();
                if (currentUe == null || EqUnitRoleEnum.PRIMARY.equals((Object)(currentUeRole = currentUe.getUeEqRole()))) continue;
                if (counter > 0) {
                    ids.append(";");
                }
                Equipment currentUeEquip = currentUe.getUeEquipment();
                ids.append(currentUeEquip.getEqIdFull());
                ++counter;
            }
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    public String getUnitCurrentlyAttachedEquipIds() {
        StringBuilder ids = new StringBuilder();
        Set unitEquipments = this.getUnitUeSet();
        int counter = 0;
        for (Object currentUe : unitEquipments) {
            if (EqUnitRoleEnum.PRIMARY.equals((Object)((UnitEquipment)currentUe).getUeEqRole()) || !((UnitEquipment)currentUe).hasNotBeenDetached()) continue;
            if (counter > 0) {
                ids.append(";");
            }
            ids.append(((UnitEquipment)currentUe).getUeEquipment().getEqIdFull());
            ++counter;
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    public String getUnitCurrentlyAttachedChassisId() {
        Set unitEquipments = this.getUnitUeSet();
        boolean counter = false;
        for (Object currentUe : unitEquipments) {
            Equipment equipment = ((UnitEquipment)currentUe).getUeEquipment();
            if (EqUnitRoleEnum.PRIMARY.equals((Object)((UnitEquipment)currentUe).getUeEqRole()) || !((UnitEquipment)currentUe).hasNotBeenDetached() || !EquipClassEnum.CHASSIS.equals((Object)equipment.getEqClass())) continue;
            return equipment.getEqIdFull();
        }
        return null;
    }

    public String getUnitCurrentlyAttachedAccessoriesIds() {
        StringBuilder ids = new StringBuilder();
        Set unitEquipments = this.getUnitUeSet();
        int counter = 0;
        for (Object currentUe : unitEquipments) {
            if (EqUnitRoleEnum.PRIMARY.equals((Object)((UnitEquipment)currentUe).getUeEqRole()) || !((UnitEquipment)currentUe).hasNotBeenDetached() || !((UnitEquipment)currentUe).getUeEquipment().getEqClass().equals((Object)EquipClassEnum.ACCESSORY)) continue;
            if (counter > 0) {
                ids.append(";");
            }
            ids.append(((UnitEquipment)currentUe).getUeEquipment().getEqIdFull());
            ++counter;
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    public Boolean isUnitBundled() {
        Boolean isUnitBundled = false;
        if (EqUnitRoleEnum.PAYLOAD.equals((Object)this.getUnitEqRole())) {
            isUnitBundled = true;
        } else {
            Set unitEquipments = this.getUnitUeSet();
            if (unitEquipments != null) {
                for (Object currentUe : unitEquipments) {
                    EqUnitRoleEnum currentUeRole;
                    if (currentUe == null || !EqUnitRoleEnum.PAYLOAD.equals((Object)(currentUeRole = ((UnitEquipment)currentUe).getUeEqRole())) || !((UnitEquipment)currentUe).getUnitRelatedUnit().equals(this.thisUnit())) continue;
                    isUnitBundled = true;
                    break;
                }
            }
        }
        return isUnitBundled;
    }

    @Nullable
    private ReeferRecord getFirstReeferRecordAfterDate(Date inDate) {
        Set reefRecordSet;
        ReeferRecord recording = null;
        long givenTimeLong = 0L;
        if (inDate != null) {
            givenTimeLong = inDate.getTime();
        }
        if ((reefRecordSet = this.getUnitReeferRecordSet()) != null && !reefRecordSet.isEmpty()) {
            Object[] reefRecords = reefRecordSet.toArray();
            Arrays.sort(reefRecords);
            for (int i = 0; i < reefRecords.length; ++i) {
                long recTimeLong;
                recording = (ReeferRecord)reefRecords[i];
                Date recTime = recording.getRfrecTime();
                long l = recTimeLong = recTime != null ? recTime.getTime() : 0L;
                if (recTimeLong > givenTimeLong) break;
            }
        }
        return recording;
    }

    public void updateOog(Long inOogBackCm, Long inOogFrontCm, Long inOogLeftCm, Long inOogRightCm, Long inOogTopCm) throws BizViolation {
        this.setUnitIsOog(Boolean.FALSE);
        this.setUnitOogBackCm(this.verifyOog(inOogBackCm));
        this.setUnitOogFrontCm(this.verifyOog(inOogFrontCm));
        this.setUnitOogLeftCm(this.verifyOog(inOogLeftCm));
        this.setUnitOogRightCm(this.verifyOog(inOogRightCm));
        this.setUnitOogTopCm(this.verifyOog(inOogTopCm));
    }

    @Nullable
    private Long verifyOog(Long inOogCm) throws BizViolation {
        Long oog = null;
        if (inOogCm != null && inOogCm.intValue() != 0) {
            if (inOogCm.intValue() > 0) {
                oog = inOogCm;
                this.setUnitIsOog(Boolean.TRUE);
            } else {
                throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__OOG_NEGATIVE, null, (Object)inOogCm);
            }
        }
        return oog;
    }

    public void updateFreightKind(FreightKindEnum inFreightKind) {
        this.setUnitFreightKind(inFreightKind);
    }

    public void updateLineOperator(ScopedBizUnit inLineOp) {
        ScopedBizUnit existingOperator = this.getUnitLineOperator();
        this.setUnitLineOperator(inLineOp);
        if (existingOperator != null && inLineOp != null && !inLineOp.equals((Object)existingOperator)) {
            FieldChanges changes = new FieldChanges();
            changes.setFieldChange(IInventoryField.UNIT_LINE_OPERATOR, (Object)existingOperator, (Object)inLineOp);
            ITranslationContext translationContext = TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext());
            String bzuId = inLineOp == null ? null : inLineOp.getBzuId();
            String eventNote = translationContext.getMessageTranslator().getMessage(IInventoryPropertyKeys.UNIT_LINE_CHANGE, (Object[])new String[]{existingOperator.getBzuId(), bzuId});
            this.recordUnitEvent((IEventType)EventEnum.UNIT_OPERATOR_CHANGE, changes, eventNote);
            LOGGER.warn((Object)(eventNote + " in the Unit " + this.getUnitId() + " by user:" + ContextHelper.getThreadUserId()));
        }
    }

    public boolean isBreakBulk() {
        return this.getPrimaryEq() == null && FreightKindEnum.BBK.equals((Object)this.getUnitFreightKind());
    }

    public void updateRouting(Routing inRouting) {
        this.setUnitRouting(inRouting);
    }

    public void updateSpecialStow(SpecialStow inStow) {
        this.setUnitSpecialStow(inStow);
    }

    public void updateSpecialStow2(SpecialStow inStow) {
        this.setUnitSpecialStow2(inStow);
    }

    public void updateSpecialStow3(SpecialStow inStow) {
        this.setUnitSpecialStow3(inStow);
    }

    public void updateDrayStatus(DrayStatusEnum inNewDrayStatus) throws BizViolation {
        EqBaseOrder order;
        UnitFacilityVisit ufv;
        DrayStatusEnum oldDrayStatus = this.getUnitDrayStatus();
        if (inNewDrayStatus == null && oldDrayStatus == null) {
            return;
        }
        if (inNewDrayStatus != null && inNewDrayStatus.equals((Object)oldDrayStatus)) {
            return;
        }
        if (DrayStatusEnum.DRAYIN.equals((Object)inNewDrayStatus) && (ufv = this.getUnitActiveUfvNowActive()) != null) {
            if (ufv.isTransitStateBeyond(UfvTransitStateEnum.S30_ECIN)) {
                throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__CANT_CHANGE_TO_DRAYIN, null, (Object)this.getUnitId(), (Object)((Object)ufv.getUfvTransitState()));
            }
            CarrierVisit genericTruck = CarrierVisit.getGenericTruckVisit((Complex)this.getUnitComplex());
            ufv.setUfvActualIbCv(genericTruck);
            ufv.setUfvLastKnownPosition(LocPosition.createTruckPosition((ILocation)genericTruck, null, null));
        }
        boolean orderAssigned = this.isAllowedDepartureOrderAssign();
        this.setUnitDrayStatus(inNewDrayStatus);
        if (orderAssigned && !this.isAllowedDepartureOrderAssign() && DrayStatusEnum.RETURN.equals((Object)inNewDrayStatus) && (order = this.getDepartureOrder()) != null) {
            order.decrementTallyRcvCount();
        }
        if (DrayStatusEnum.RETURN.equals((Object)oldDrayStatus) && (order = this.getDepartureOrder()) != null) {
            order.incrementTallyRcv(this);
        }
    }

    public void updateCategory(UnitCategoryEnum inCategory) {
        this.setUnitCategory(inCategory);
    }

    public void updateDeclaredIbCv(CarrierVisit inCv) {
        this.setUnitDeclaredIbCv(inCv);
    }

    public void updateGoodsAndCtrWtKg(Double inGoodsAndCtrWtKg) throws BizViolation {
        UnitUtils.validateUnitGrossWeight(this, inGoodsAndCtrWtKg, ContextHelper.isUpdateFromHumanUser());
        this.setUnitGoodsAndCtrWtKg(inGoodsAndCtrWtKg);
    }

    public void updateVerifiedGrossMassWtKg(Double inGoodsAndCtrVgmWt) throws BizViolation {
        if (inGoodsAndCtrVgmWt != null && inGoodsAndCtrVgmWt > 0.0) {
            UnitUtils.validateUnitVgmWeight(this, inGoodsAndCtrVgmWt, ContextHelper.isUpdateFromHumanUser());
        }
        this.setUnitGoodsAndCtrWtKgVerfiedGross(inGoodsAndCtrVgmWt);
        Double exisitingWeight = this.getUnitGoodsAndCtrWtKgVerfiedGross();
        if (exisitingWeight != null && !exisitingWeight.equals(inGoodsAndCtrVgmWt)) {
            this.setUnitVgmVerifiedDate(ArgoUtils.timeNow());
        }
    }

    public void updateVerifiedEntity(String inVerifiedEntity) {
        this.setUnitVgmEntity(inVerifiedEntity);
    }

    public void updateGrossWeightSource(GrossWeightSourceEnum inGrossWeightSourceEnum) {
        this.setUnitGrossWeightSource(inGrossWeightSourceEnum);
    }

    public void updateVgmVerifiedDate(Date inVgmVerifiedDate) {
        this.setUnitVgmVerifiedDate(inVgmVerifiedDate);
    }

    public void updateGoodsAndCtrWtKgAdvised(Double inGoodsAndCtrWtKgAdvised) {
        this.setUnitGoodsAndCtrWtKgAdvised(inGoodsAndCtrWtKgAdvised);
    }

    public void updateSeals(String inSealNbr1, String inSealNbr2, String inSealNbr3, String inSealNbr4) {
        this.setUnitSealNbr1(inSealNbr1);
        this.setUnitSealNbr2(inSealNbr2);
        this.setUnitSealNbr3(inSealNbr3);
        this.setUnitSealNbr4(inSealNbr4);
        if (inSealNbr1 != null || inSealNbr2 != null || inSealNbr3 != null || inSealNbr4 != null) {
            this.setUnitIsCtrSealed(Boolean.TRUE);
        }
    }

    public void updateSeals(String inSealNbr1, String inSealNbr2, String inSealNbr3, String inSealNbr4, Boolean inIsSealed) {
        this.setUnitIsCtrSealed(inIsSealed);
        this.updateSeals(inSealNbr1, inSealNbr2, inSealNbr3, inSealNbr4);
    }

    public void updateRemarks(String inRemarks) {
        this.setUnitRemark(inRemarks);
    }

    public void attachHazards(Hazards inHazards) {
        this.ensureGoods().attachHazards(inHazards);
    }

    @Nullable
    public UnitEquipDamages getDamages(Equipment inEquipment) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inEquipment);
        UnitEquipDamages damages = null;
        if (ue != null) {
            damages = ue.getUeDamages();
        }
        return damages;
    }

    public boolean isDamaged(Equipment inEq) {
        Unit ue = this.getCurrentlyAttachedUe(inEq);
        return ue != null && UnitUtils.getUnitRepairedDamageStatus(ue);
    }

    public Boolean isUnitDamaged() {
        return UnitUtils.getUnitRepairedDamageStatus(this.getUnitPrimaryUe());
    }

    @Nullable
    public String getServiceRuleName() {
        Collection rules = ServiceRule.findAllServiceRulesForEntity((ILogicalEntity)this);
        if (rules == null || rules.isEmpty()) {
            return null;
        }
        HashSet<String> ruleName = new HashSet<String>();
        for (Object rule : rules) {
            ruleName.add(((ServiceRule)rule).getSrvrulName());
        }
        return ArgoUtils.createStringFromSetValues(ruleName, (String)",");
    }

    @Nullable
    public String getServiceEventType() {
        Collection rules = ServiceRule.findAllServiceRulesForEntity((ILogicalEntity)this);
        if (rules == null || rules.isEmpty()) {
            return null;
        }
        HashSet<String> ruleType = new HashSet<String>();
        for (Object rule : rules) {
            ruleType.add(((ServiceRule)rule).getSrvrulServiceType().getEvnttypeId());
        }
        return ArgoUtils.createStringFromSetValues(ruleType, (String)",");
    }

    public void verifyNoMajorDamage(Equipment inEq) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inEq);
        if (ue != null) {
            if (EqDamageSeverityEnum.MAJOR.equals((Object)ue.getUeDamageSeverity())) {
                throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_HAS_MAJOR_DAMAGE, null, (Object)inEq);
            }
        } else {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inEq, (Object)this);
        }
    }

    public void attachDamages(Equipment inDamagedEq, UnitEquipDamages inDamages) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inDamagedEq);
        if (ue == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inDamagedEq, (Object)this);
        }
        ue.attachDamages(inDamages);
    }

    public UnitEquipDamageItem addDamageItem(Equipment inDamagedEq, EquipDamageType inDamageType, EqComponent inDmgComponent, EqDamageSeverityEnum inDmgSeverity, Date inDmgReported, Date inDmgRepaired) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inDamagedEq);
        if (ue != null) {
            return ue.addDamageItem(inDamageType, inDmgComponent, inDmgSeverity, inDmgReported, inDmgRepaired);
        }
        throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inDamagedEq, (Object)this);
    }

    public UnitEquipDamageItem addDamageItem(Equipment inDamagedEq, UnitEquipDamageItem inDamageItem) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inDamagedEq);
        if (ue != null) {
            return ue.addDamageItem(inDamageItem);
        }
        throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inDamagedEq, (Object)this);
    }

    public void modifyGoodsDetails(String inShipper, String inConsignee, String inOrigin, String inDestination) {
        GoodsBase gds = this.ensureGoods();
        gds.setOrigin(inOrigin);
        gds.setDestination(inDestination);
        gds.updateShipper(inShipper);
        gds.updateConsignee(inConsignee);
    }

    @Override
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

    public String getUnitCalculatedCurrentPositionName() {
        LocPosition currentPosition = this.findCurrentPosition();
        return currentPosition.getPosName();
    }

    @Nullable
    public Unit dismount() throws BizViolation {
        Unit carriageUnit = this.getCarriageUnit();
        if (carriageUnit == null) {
            return null;
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (ufv == null) {
            throw BizFailure.create((String)("Unit.dismount - no Active UFV for Unit: " + this.getUnitId()));
        }
        if (!ufv.isTransitStateAtLeast(UfvTransitStateEnum.S30_ECIN) || !ufv.isTransitStateAtMost(UfvTransitStateEnum.S60_LOADED)) {
            throw BizFailure.create((String)"attempt to detach chassis from Unit not in the yard");
        }
        if (this.acquireDismountLock()) {
            carriageUnit.detach(this.thisUnit(), null);
        }
        return carriageUnit;
    }

    private boolean acquireDismountLock() {
        if (this.getUnitCarriageUnit() == null) {
            return false;
        }
        LOGGER.warn((Object)("acquireDismountLock: Try acquiring lock for dismounting " + this));
        int lockAttempt = 0;
        int lockHash = Math.abs(this.getUnitCarriageUnit().getUnitId().hashCode() % 32);
        String lockQualifier = String.valueOf(lockHash);
        boolean lockAcquired = false;
        while (lockAttempt <= 1 && !lockAcquired) {
            try {
       //         ArgoLockProvider.setLock((LockTypeEnum)LockTypeEnum.UNIT_DISMOUNT, (String)lockQualifier, (Long)this.getUnitActiveUfv().getUfvFacility().getFcyGkey());
                lockAcquired = true;
            }
            catch (Exception e) {
                ++lockAttempt;
                LOGGER.error((Object)("Error while trying to acquire lock to dismount : " + lockQualifier), (Throwable)e);
            }
        }
        LOGGER.warn((Object)("acquireDismountLock: Lock acquired: " + lockAcquired));
        return lockAcquired;
    }

    @Nullable
    private UnitFacilityVisit findAdvisedFirstUfv() {
        UnitFacilityVisit firstUfv = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object ufv : ufvSet) {
                if (!((UnitFacilityVisit)ufv).isFuture()) continue;
                CarrierVisit declaredIbCv = this.getUnitDeclaredIbCv();
                if (!((UnitFacilityVisit)ufv).getUfvFacility().equals((Object)declaredIbCv.getCvFacility())) continue;
                firstUfv = (UnitFacilityVisit)ufv;
            }
        }
        return firstUfv;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Unit[");
        sb.append(this.getUnitId()).append(':').append((Object)this.getUnitEqRole()).append(':');
        if (this.getUnitVisitState() != null) {
            sb.append(this.getUnitVisitState().getKey()).append(':');
        }
        if (this.getUnitCategory() != null) {
            sb.append(this.getUnitCategory().getKey()).append(':');
        }
        if (this.getUnitFreightKind() != null) {
            sb.append(this.getUnitFreightKind().getKey()).append(':');
        }
        sb.append(']');
        return sb.toString();
    }

    @Nullable
    public Date getComplexEntryTime() {
        Date inTime = null;
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet == null) {
            return inTime;
        }
        for (Object ufv : ufvSet) {
            Date ufvInTime = ((UnitFacilityVisit)ufv).getUfvTimeIn();
            if (ufvInTime == null || inTime != null && !ufvInTime.before(inTime)) continue;
            inTime = ufvInTime;
        }
        return inTime;
    }

    public void assignToOrder(EqBaseOrderItem inNewEqoi, Equipment inEq) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inEq);
        if (ue != null) {
            Unit unit = ue.getUeUnit();
            EqBaseOrderItem oldEqoi = null;
            if (inNewEqoi != null) {
                EquipmentOrderSubTypeEnum subType = inNewEqoi.getSubType();
                EqBaseOrderItem eqBaseOrderItem = oldEqoi = EquipmentOrderSubTypeEnum.ERO.equals((Object)subType) ? ue.getUeArrivalOrderItem() : ue.getUeDepartureOrderItem();
                if (inNewEqoi.equals(oldEqoi)) {
                    return;
                }
                if (oldEqoi != null) {
                    oldEqoi.getEqboiOrder().decrementTallyRcv(this);
                }
            }
            ue.setUeOrderItem(inNewEqoi);
            ue.setUeIsReserved(Boolean.FALSE);
            if (inNewEqoi != null) {
                inNewEqoi.getEqboiOrder().incrementTallyRcv(this);
                if ((DataSourceEnum.EDI_PREADVISE.equals((Object)ContextHelper.getThreadDataSource()) || DataSourceEnum.IN_GATE.equals((Object)ContextHelper.getThreadDataSource())) && oldEqoi != null && !ObjectUtils.equals((Object)oldEqoi, (Object)inNewEqoi)) {
                    FieldChanges unitFieldChanges = new FieldChanges();
                    unitFieldChanges.setFieldChange(new FieldChange(IInventoryField.EQBO_NBR, (Object)oldEqoi.getEqboiOrder().getEqboNbr(), (Object)inNewEqoi.getEqboiOrder().getEqboNbr()));
                    this.recordUnitEventOnComboUnits((IEventType)EventEnum.UNIT_ROLL, unitFieldChanges, null);
                    unit.applyDepartureOrderOnPayload(inNewEqoi);
                }
            }
        } else {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inEq, (Object)this);
        }
    }

    public void reserveForOrder(EqBaseOrderItem inEqoi, Equipment inEq) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inEq);
        if (ue == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inEq, (Object)this);
        }
        this.reserveForOrder(inEqoi, ue);
    }

    public void reserveForOrder(EqBaseOrderItem inEqoi, Equipment inEq, Facility inFacility) throws BizViolation {
        Unit ue = this.getCurrentlyAttachedUe(inEq);
        if (ue == null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_NOT_IN_USE, null, (Object)inEq, (Object)this);
        }
        this.reserveForOrder(inEqoi, ue, inFacility);
    }

    public void reserveForOrder(EqBaseOrderItem inEqoi, UnitEquipment inUnitEquipment) throws BizViolation {
        this.reserveForOrder(inEqoi, inUnitEquipment, null);
    }

    public void reserveForOrder(EqBaseOrderItem inEqoi, UnitEquipment inUnitEquipment, Facility inFacility) throws BizViolation {
        if (inUnitEquipment == null) {
            throw BizFailure.create((String)"Reserved called for UnitEquipment 'null'.");
        }
        if (inFacility == null) {
            inFacility = ContextHelper.getThreadFacility();
        }
        UnitFacilityVisit activeUfv = null;
        activeUfv = inFacility != null ? this.getUfvForFacilityNewest(inFacility) : this.getUnitActiveUfvNowActive();
        if (activeUfv == null) {
            throw BizFailure.create((String)"Reserved called for inactive Ufv.");
        }
        CarrierVisit ufvIntendedObCv = activeUfv.getUfvIntendedObCv();
        IArgoEquipmentOrderManager eqOrderMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
        Long orderGkey = inEqoi.getEqboiOrder().getEqboGkey();
        eqOrderMgr.validateOBCarrierForReserve((Serializable)orderGkey, ufvIntendedObCv, this.getUnitId());
        inUnitEquipment.setUeDepartureOrderItem(inEqoi);
        inUnitEquipment.setUeIsReserved(Boolean.TRUE);
        inEqoi.getEqboiOrder().incrementTallyRcv(this);
    }

    public void cancelReservation() throws BizViolation {
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue != null) {
            EqBaseOrderItem orderItem = ue.getUeDepartureOrderItem();
            if (orderItem != null) {
                EquipmentOrderSubTypeEnum subType = orderItem.getEqboiOrder().getEqboSubType();
                if (EquipmentOrderSubTypeEnum.BOOK.equals((Object)subType) && !this.isReservedForBooking()) {
                    throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNRESERVE_EQUIPMENT_IS_NOT_RESERVED, null, (Object)this.getUnitId());
                }
                orderItem.getEqboiOrder().decrementTallyRcv(this);
            }
            ue.setUeDepartureOrderItem(null);
            ue.setUeIsReserved(Boolean.FALSE);
        }
    }

    public void cancelPreadvise() throws BizViolation {
        UnitEquipment ue;
        UserContext uc = ContextHelper.getThreadUserContext();
        if (this.isUnitPreadvised()) {
            ue = this.getUnitPrimaryUe();
            CodedConfig setting = ArgoConfig.CANCEL_PREADVISE_NOT_RETIRE_UNIT;
            if (setting.isSetTo("ACTIVE_ONLY", uc)) {
                if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUnitVisitState())) {
                    this.makeRetired();
                }
            } else if (!setting.isSetTo("ACTIVE_AND_ADVISED", uc)) {
                this.makeRetired();
            }
        } else {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_NOT_PREADVISED_COMPLEX, null, (Object)this.getUnitId(), (Object)this.getUnitComplex());
        }
        ue.setUeArrivalOrderItem(null);
        ue.setUeDepartureOrderItem(null);
    }

    public void cancelPreadvise(Facility inFacility) throws BizViolation {
        UnitEquipment ue;
        UserContext uc = ContextHelper.getThreadUserContext();
        if (this.isUnitPreadvised(inFacility)) {
            ue = this.getUnitPrimaryUe();
            CodedConfig setting = ArgoConfig.CANCEL_PREADVISE_NOT_RETIRE_UNIT;
            if (setting.isSetTo("ACTIVE_ONLY", uc)) {
                if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUnitVisitState())) {
                    this.makeRetired();
                }
            } else if (!setting.isSetTo("ACTIVE_AND_ADVISED", uc)) {
                this.makeRetired();
            }
        } else {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_NOT_PREADVISED, null, (Object)this.getUnitId());
        }
        ue.setUeArrivalOrderItem(null);
        ue.setUeDepartureOrderItem(null);
    }

    public boolean isUnitPreadvised() {
        boolean isAdvised = true;
        if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUnitVisitState())) {
            isAdvised = true;
        } else if (UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState())) {
            Set ufvSet = this.getUnitUfvSet();
            for (Object thisUfv : ufvSet) {
                if (!((UnitFacilityVisit)thisUfv).isTransitStateBeyond(UfvTransitStateEnum.S20_INBOUND)) continue;
                isAdvised = false;
            }
        } else {
            isAdvised = false;
        }
        return isAdvised;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isUnitPreadvised(Facility inFacility) {
        if (!this.isLiveUnit()) return false;
        if (inFacility == null) {
            inFacility = ContextHelper.getThreadFacility();
        }
        UnitFacilityVisit completedUfv = this.getUfvForFacilityCompletedOnly(inFacility);
        UnitFacilityVisit liveUfv = this.getUfvForFacilityLiveOnly(inFacility);
        if (completedUfv != null) {
            if (!DrayStatusEnum.OFFSITE.equals((Object)this.getUnitDrayStatus())) return false;
            if (liveUfv != null && !liveUfv.isTransitStateAtMost(UfvTransitStateEnum.S20_INBOUND)) return false;
            return true;
        }
        if (liveUfv != null && !liveUfv.isTransitStateAtMost(UfvTransitStateEnum.S20_INBOUND)) return false;
        return true;
    }

    public boolean isUnitPreadvisedForOrderItem(EqBaseOrderItem inEqboi) {
        if (this.isUnitPreadvised()) {
            EqBaseOrderItem preadvisedItem;
            UnitEquipment ue = this.getUnitPrimaryUe();
            if (inEqboi != null && ue != null && (preadvisedItem = EquipmentOrderSubTypeEnum.ERO.equals((Object)inEqboi.getEqboiOrder().getEqboSubType()) ? ue.getUeArrivalOrderItem() : ue.getUeDepartureOrderItem()) != null && preadvisedItem.equals(inEqboi)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnitInYard() {
        if (this.getUnitVisitState() == UnitVisitStateEnum.ACTIVE) {
            Facility facility = ContextHelper.getThreadFacility();
            UnitFacilityVisit liveUfv = this.getUfvForFacilityLiveOnly(facility);
            if (liveUfv == null) {
                return false;
            }
            return liveUfv.isTransitStateAtLeast(UfvTransitStateEnum.S30_ECIN) && liveUfv.isTransitStateAtMost(UfvTransitStateEnum.S50_ECOUT);
        }
        return false;
    }

    public IGoods getGoods() {
        return this.getUnitGoods();
    }

    public Set<UnitEquipment> getSubsidiaryEquipment() {
        HashSet<UnitEquipment> resultSet = new HashSet<UnitEquipment>();
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null) {
            for (Object ue : ueSet) {
                if (((UnitEquipment)ue).getUeEqRole() == EqUnitRoleEnum.PRIMARY) continue;
                resultSet.add((UnitEquipment)ue);
            }
        }
        return resultSet;
    }

    @Nullable
    public String getUnitArrivePositionSlot() {
        LocPosition pos = this.getUnitArrivePosition();
        return pos != null ? pos.getPosSlot() : null;
    }

    @Nullable
    public String getUnitArrivePositionOrientation() {
        LocPosition pos = this.getUnitArrivePosition();
        return pos != null ? pos.getPosOrientation() : null;
    }

    @Nullable
    public LocPosition getUnitArrivePosition() {
        Facility facility = this.getUnitDeclaredIbCv().getCvFacility();
        UnitFacilityVisit ufv = this.getUfvForFacilityNewest(facility);
        return ufv != null ? ufv.getUfvArrivePosition() : null;
    }

    @Nullable
    public String getUnitAcryId() {
        Accessory acry = this.getUnitCtrAccessory();
        return acry == null ? null : acry.getEqIdFull();
    }

    @Nullable
    public String getUnitChsAcryId() {
        if (this.isContainer() || this.isChassis()) {
            Accessory acry = this.getUnitChsAccessory();
            return acry == null ? null : acry.getEqIdFull();
        }
        return null;
    }

    @Nullable
    public Object getUnitAcryType() {
        Accessory acry = this.getUnitCtrAccessory();
        return acry == null ? null : acry.getEqEquipType().getEqtypIsoGroup();
    }

    @Nullable
    public Object getUnitChassisAcryType() {
        Accessory acry = this.getUnitChsAccessory();
        return acry == null ? null : acry.getEqEquipType().getEqtypIsoGroup();
    }

    @Nullable
    public Object getUnitAcryEqtype() {
        Accessory acry = this.getUnitCtrAccessory();
        return acry == null ? null : acry.getEqEquipType().getEqtypGkey();
    }

    @Nullable
    public Object getUnitAcryEqtypeId() {
        Accessory acry = this.getUnitCtrAccessory();
        return acry == null ? null : acry.getEqEquipType().getEqtypId();
    }

    @Nullable
    public Object getUnitChsAcryEqtypeId() {
        Accessory acry = this.getUnitChsAccessory();
        return acry == null ? null : acry.getEqEquipType().getEqtypId();
    }

    public Object getPreadvCarrierMode() {
        return this.getUnitDeclaredIbCv().getCvCarrierMode();
    }

    public Object getPreadvCarrierId() {
        return this.getUnitDeclaredIbCv().getCvId();
    }

    @Nullable
    public String getUnitAcryDescription() {
        Accessory acry = this.getUnitCtrAccessory();
        return acry == null ? null : acry.getEqAcryDescription();
    }

    @Nullable
    public String getUnitChassisAcryDescription() {
        Accessory acry = this.getUnitChsAccessory();
        return acry == null ? null : acry.getEqAcryDescription();
    }

    public Object getUnitHazardTableKey() {
        GoodsBase goods = this.getUnitGoods();
        Hazards hazards = goods == null ? null : goods.getGdsHazards();
        return hazards == null ? new Long(0L) : hazards.getHzrdGkey();
    }

    @Nullable
    public Object getHazardsHazItemsVao() {
        GoodsBase goods = this.getUnitGoods();
        Hazards hazards = goods == null ? null : goods.getGdsHazards();
        return hazards == null ? null : hazards.getHazardsHazItemsVao();
    }

    public Object getUnitPayloadTableKey() {
        return this.getUnitGkey();
    }

    public Boolean getUnitHasPayloadEquipment() {
        return this.getUnitIsBundle();
    }

    @Nullable
    public Accessory getUnitCtrAccessory() {
        Unit ue = this.getUeInRole(EqUnitRoleEnum.ACCESSORY);
        return ue == null ? null : Accessory.resolveAccFromEq((Equipment)ue.getUeEquipment());
    }

    @Nullable
    public Accessory getUnitChsAccessory() {
        Unit ue = this.getAccessoryOnChs();
        return ue == null ? null : Accessory.resolveAccFromEq((Equipment)ue.getUeEquipment());
    }

    public Set getUnitAccessories() {
        Set ueSet = this.getUnitUeSet();
        HashSet<Accessory> acrySet = new HashSet<Accessory>();
        if (ueSet != null) {
            for (Object ue : ueSet) {
                if (((UnitEquipment)ue).getUeEqRole() == null || !((UnitEquipment)ue).getUeEqRole().equals((Object)EqUnitRoleEnum.ACCESSORY) && !((UnitEquipment)ue).getUeEqRole().equals((Object)EqUnitRoleEnum.ACCESSORY_ON_CHS)) continue;
                acrySet.add(Accessory.resolveAccFromEq((Equipment)((UnitEquipment)ue).getUeEquipment()));
            }
        }
        return acrySet;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        ScopedBizUnit unitLineOperator;
        super.preProcessInsert(inOutMoreChanges);
        if (inOutMoreChanges.hasFieldChange(IInventoryField.UNIT_WANT_POWERED) && ((Boolean)inOutMoreChanges.getFieldChange(IInventoryField.UNIT_WANT_POWERED).getNewValue()).booleanValue()) {
            this.recordUnitEvent((IEventType)EventEnum.UNIT_WANTS_POWER_YES, null, null, null);
        }
        if (inOutMoreChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR1) || inOutMoreChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR2) || inOutMoreChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR3) || inOutMoreChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR4)) {
            inOutMoreChanges.setFieldChange(IInventoryField.UNIT_IS_CTR_SEALED, (Object) Boolean.TRUE);
        }
        if ((unitLineOperator = this.getUnitLineOperator()) != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            this.propagateLineReeferMonitors(line, false);
        }
    }

    public void preProcessDelete(FieldChanges inChanges) {
        super.preProcessDelete(inChanges);
        this.validateIftChangeAllowed();
        if (this.getUnitArrivalOrderItem() != null) {
            this.getUnitArrivalOrderItem().getEqboiOrder().decrementTallyRcv(this);
        }
        if (this.getUnitDepartureOrderItem() != null) {
            this.getUnitDepartureOrderItem().getEqboiOrder().decrementTallyRcv(this);
            this.getUnitDepartureOrderItem().getEqboiOrder().decrementUECount();
        }
    }

    public void preProcessUpdate(final FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        GoodsBase goods;
        if (!inChanges.hasFieldChange(IInventoryField.UNIT_ACTIVE_UFV)) {
            this.validateIftChangeAllowed();
        }
        FieldChanges vgmChanges = new FieldChanges();
        boolean hasVGMChanged = false;
        if (inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS)) {
            vgmChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS));
            hasVGMChanged = true;
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_VGM_ENTITY)) {
            vgmChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_VGM_ENTITY));
            hasVGMChanged = true;
        }
        if (hasVGMChanged) {
            if (inChanges.hasFieldChange(IInventoryField.UNIT_VGM_VERIFIED_DATE)) {
                vgmChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_VGM_VERIFIED_DATE));
            } else {
                this.updateVGMDate(inChanges, inOutMoreChanges, vgmChanges);
            }
        }
        Boolean isVGMCreate = false;
        Boolean isVGMDelete = false;
        isVGMCreate = inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) != null && inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS).getPriorValue() == null;
        isVGMDelete = inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) != null && inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS).getNewValue() == null;
        if (this.hasVGMchange(inChanges)) {
            if (isVGMDelete.booleanValue() && inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) != null && (Double)inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS).getPriorValue() > 0.0) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_VGM_CANCEL, vgmChanges, this._notes, null, (IEntity)this, inOutMoreChanges);
            } else if (inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) != null && (Double)inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS).getNewValue() > 0.0 && isVGMCreate.booleanValue()) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_VGM_ASSIGNED, vgmChanges, this._notes, null, (IEntity)this, inOutMoreChanges);
            } else {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_VGM_UPDATED, vgmChanges, this._notes, null, (IEntity)this, inOutMoreChanges);
            }
        }
        if (this.hasGateOrYardWeightChanges(inChanges)) {
            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean((String)"messageTranslatorProvider");
            IMessageTranslator translator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
            String notes = null;
            FieldChanges gateYardChanges = new FieldChanges();
            if (inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_GATE_MEASURED)) {
                gateYardChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_GATE_MEASURED));
                notes = translator.getMessage(IInventoryPropertyKeys.GATE_WEIGHT_MODIFIED, new Object[]{""});
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED)) {
                gateYardChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED));
                notes = translator.getMessage(IInventoryPropertyKeys.YARD_WEIGHT_MODIFIED, new Object[]{""});
            }
            this.recordUnitEvent((IEventType)EventEnum.UNIT_PROPERTY_UPDATE, gateYardChanges, notes, null, (IEntity)this, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED)) {
            WorkInstruction wi;
            FieldChanges fcs = new FieldChanges();
            fcs.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED));
            UnitFacilityVisit ufv = this.getActiveUfvNowActiveInAnyUnit();
            if (ufv != null && (wi = ufv.getLastWorkInstruction()) != null) {
                Che che = wi.getWiChe();
                String cheId = che != null ? che.getCheShortName() : null;
                fcs.setFieldChange(new FieldChange(IMovesField.MVHS_FETCH_CHE_ID, (Object)cheId));
            }
            fcs.setFieldChange(new FieldChange(IServicesField.EVNT_APPLIED_BY, (Object)ContextHelper.getThreadUserId()));
            fcs.setFieldChange(new FieldChange(IServicesField.EVNT_APPLIED_DATE, (Object)ArgoUtils.timeNow()));
            this.recordUnitEvent((IEventType)EventEnum.UNIT_EQUIPMENT_MEASURED_WEIGHT, fcs, "Yard Measured Wt updated", null, (IEntity)this, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_WANT_POWERED)) {
            if (((Boolean)inChanges.getFieldChange(IInventoryField.UNIT_WANT_POWERED).getNewValue()).booleanValue()) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_WANTS_POWER_YES, null, null, null, (IEntity)this, inOutMoreChanges);
            } else {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_WANTS_POWER_NO, null, null, null, (IEntity)this, inOutMoreChanges);
            }
        }
        if (UnitUtils.overDimensionsChanged(inChanges)) {
            FieldChanges oogChanges = new FieldChanges();
            if (inChanges.hasFieldChange(IInventoryField.UNIT_OOG_FRONT_CM)) {
                oogChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_OOG_FRONT_CM));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_OOG_BACK_CM)) {
                oogChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_OOG_BACK_CM));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_OOG_LEFT_CM)) {
                oogChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_OOG_LEFT_CM));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_OOG_RIGHT_CM)) {
                oogChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_OOG_RIGHT_CM));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_OOG_TOP_CM)) {
                oogChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_OOG_TOP_CM));
            }
            this.recordUnitEvent((IEventType)EventEnum.UNIT_OVERDIMENSIONS_UPDATE, oogChanges, "Overdimensions updated", null, (IEntity)this, inOutMoreChanges);
        }
        if (UnitUtils.sealsChanged(inChanges)) {
            if (this.getUnitSealNbr1() != null || this.getUnitSealNbr2() != null || this.getUnitSealNbr3() != null || this.getUnitSealNbr4() != null) {
                inOutMoreChanges.setFieldChange(IInventoryField.UNIT_IS_CTR_SEALED, (Object) Boolean.TRUE);
            } else if (inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR1) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR2) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR3) || inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR4)) {
                inOutMoreChanges.setFieldChange(IInventoryField.UNIT_IS_CTR_SEALED, (Object) Boolean.FALSE);
            }
            FieldChanges sealChanges = new FieldChanges();
            if (inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR1)) {
                sealChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_SEAL_NBR1));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR2)) {
                sealChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_SEAL_NBR2));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR3)) {
                sealChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_SEAL_NBR3));
            }
            if (inChanges.hasFieldChange(IInventoryField.UNIT_SEAL_NBR4)) {
                sealChanges.setFieldChange(inChanges.getFieldChange(IInventoryField.UNIT_SEAL_NBR4));
            }
            if (!this.isNewlyCreatedUnit()) {
                this.recordUnitEvent((IEventType)EventEnum.UNIT_SEAL, sealChanges, "Seals Updated", null, (IEntity)this, inOutMoreChanges);
            }
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_IMPORT_DELIVERY_ORDER)) {
            String oldIdoId = null;
//            ImportDeliveryOrder oldIdo = (ImportDeliveryOrder)inChanges.getFieldChange(IInventoryField.UNIT_IMPORT_DELIVERY_ORDER).getPriorValue();
//            if (oldIdo != null) {
//                oldIdoId = oldIdo.getIdoId();
//            }
//            FieldChanges idoChange = new FieldChanges(inChanges);
//            ImportDeliveryOrder ido = this.getUnitImportDeliveryOrder();
//            if (ido != null) {
//                idoChange.removeFieldChange(IInventoryField.UNIT_IMPORT_DELIVERY_ORDER);
//                IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean((String)"messageTranslatorProvider");
//                IMessageTranslator translator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
//                String notes = translator.getMessage(IInventoryPropertyKeys.UNIT_RESERVE, new Object[]{ido});
//                idoChange.setFieldChange(IInventoryField.UNIT_IMPORT_DELIVERY_ORDER, (Object)oldIdoId, (Object)ido.getIdoId());
//                this.recordUnitEvent((IEventType)EventEnum.UNIT_RESERVE, idoChange, notes, null, (IEntity)this, inOutMoreChanges);
//            }
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_LINE_OPERATOR)) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)this.getUnitLineOperator());
            this.propagateLineReeferMonitors(line, true);
        }
        if (inChanges.hasFieldChange(IInventoryField.UNIT_TIME_LAST_STATE_CHANGE) && this.getUnitIsHazard().booleanValue() && (goods = this.getUnitGoods()) != null && goods.getGdsHazards() != null && !goods.getGdsHazards().getAllPlacardIds().isEmpty() && this.getUnitObservedPlacards() == null) {
            inOutMoreChanges.setFieldChange(IInventoryField.UNIT_ARE_PLACARDS_MISMATCHED, (Object) Boolean.TRUE);
        }
        if (inChanges.hasFieldChange(IInventoryField.UE_DEPARTURE_ORDER_ITEM)) {
            new PersistenceTemplatePropagationRequired(ContextHelper.getThreadUserContext()).invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    EqBaseOrderItem orderItem = Unit.this.getUeDepartureOrderItem();
                    if (orderItem == null) {
                        EqBaseOrderItem prevOrderItem = null;
                        Object itemObj = inChanges.getFieldChange(IInventoryField.UE_DEPARTURE_ORDER_ITEM).getPriorValue();
                        prevOrderItem = itemObj instanceof EqBaseOrderItem ? (EqBaseOrderItem)itemObj : (EqBaseOrderItem)HibernateApi.getInstance().load(EqBaseOrderItem.class, (Serializable)itemObj);
                        prevOrderItem.getEqboiOrder().decrementUECount();
                    } else {
                        orderItem.getEqboiOrder().incrementUECount();
                    }
                }
            });
        }
        if (inChanges.hasFieldChange(UnitField.UNIT_GOODS_AND_CTR_WT_KG) && FreightKindEnum.BBK.equals((Object)this.getUnitFreightKind()) && this.getUnitGoods() != null) {
            IInventoryCargoManager cargoMgr = (IInventoryCargoManager)Roastery.getBean((String)"inventoryCargoManager");
            cargoMgr.updateCargoLotWeightOfUnit(this);
        }
        if (inOutMoreChanges != null) {
            HibernateApi.getInstance().flush();
        }
    }

    public boolean hasVGMchange(FieldChanges inChanges) {
        return inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) || inChanges.hasFieldChange(IInventoryField.UNIT_VGM_ENTITY) || inChanges.hasFieldChange(IInventoryField.UNIT_VGM_VERIFIED_DATE);
    }

    private boolean hasGateOrYardWeightChanges(FieldChanges inChanges) {
        return inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_GATE_MEASURED) || inChanges.hasFieldChange(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_YARD_MEASURED);
    }

    public MetafieldIdList getFieldsToRemove() {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS);
        fields.add(IInventoryField.UNIT_VGM_ENTITY);
        fields.add(IInventoryField.UNIT_VGM_VERIFIED_DATE);
        return fields;
    }

    public void removeVGMFieldChanges(FieldChanges inSoureChanges, MetafieldIdList inFields) {
        if (inFields != null) {
            for (IMetafieldId field : inFields) {
                inSoureChanges.removeFieldChange(field);
            }
        }
    }

    private void updateVGMDate(FieldChanges inChanges, FieldChanges inOutMoreChanges, FieldChanges inVgmChanges) {
        Date vgmVerifriedDate = this.getUnitGoodsAndCtrWtKgVerfiedGross() == null ? null : ArgoUtils.timeNow();
        inOutMoreChanges.setFieldChange(IInventoryField.UNIT_VGM_VERIFIED_DATE, (Object)this.getUnitVgmVerifiedDate(), (Object)vgmVerifriedDate);
        inVgmChanges.setFieldChange(IInventoryField.UNIT_VGM_VERIFIED_DATE, (Object)this.getUnitVgmVerifiedDate(), (Object) ArgoUtils.timeNow());
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        if (this.getUnitDepartureOrderItem() == null) {
            this.setUnitIsReserved(Boolean.FALSE);
        }
    }

    public boolean isPrimaryUeBundleable() {
        return this.getUnitPrimaryUe().getUeEquipment().getEqEquipType().isBundleable();
    }

    public Object getUnitReeferRecordTableKey() {
        return this.getUnitGkey();
    }

    public Long getUnitPlacardsTableKey() {
        return this.getUnitGkey();
    }

    public Object getRtgEtd() {
        return this.getUnitGkey();
    }

    public Object getUnitRecordServiceEvent() {
        return Boolean.FALSE;
    }

    public String getUnitDeliveryImpedimentString() {
        return this.getUnitServicesImpedimentString((IEventType)EventEnum.UNIT_DELIVER);
    }

    public String getUnitVesselLoadImpedimentString() {
        return this.getUnitServicesImpedimentString((IEventType)EventEnum.UNIT_LOAD);
    }

    public String getUnitRailRampImpedimentString() {
        return this.getUnitServicesImpedimentString((IEventType)EventEnum.UNIT_RAMP);
    }

    @Nullable
    public String getUnitFlag1ReferenceId() {
        return this.getUnitFlagReferenceId(InventoryConfig.UNIT_FLAG_1);
    }

    private String getUnitFlagReferenceId(StringConfig inUnitFlagConfig) {
        if (inUnitFlagConfig == null) {
            return null;
        }
        String flagId = inUnitFlagConfig.getSetting(ContextHelper.getThreadUserContext());
        if (StringUtils.isEmpty((String)flagId)) {
            return null;
        }
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        return sm.getFlagReferenceIdForEntity(flagId, (ILogicalEntity)this);
    }

    @Nullable
    public String getUnitFlag2ReferenceId() {
        return this.getUnitFlagReferenceId(InventoryConfig.UNIT_FLAG_2);
    }

    @Nullable
    public String getUnitFlag3ReferenceId() {
        return this.getUnitFlagReferenceId(InventoryConfig.UNIT_FLAG_3);
    }

    @Nullable
    public String getUnitFlag4ReferenceId() {
        return this.getUnitFlagReferenceId(InventoryConfig.UNIT_FLAG_4);
    }

    public Set<EquipmentState> getUnitEquipmentStates() {
        Set unitEquipments = this.getUnitUeSet();
        HashSet<EquipmentState> equipmentStates = new HashSet<EquipmentState>();
        if (unitEquipments != null) {
            for (Object unitEquipment : unitEquipments) {
                EquipmentState equipmentState;
                if (!((UnitEquipment)unitEquipment).hasNotBeenDetached() || (equipmentState = ((UnitEquipment)unitEquipment).getUeEquipmentState()) == null) continue;
                equipmentStates.add(equipmentState);
            }
        }
        return equipmentStates;
    }

    @Nullable
    public Unit getUnitEquipment(String inEqId) {
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null && !ueSet.isEmpty()) {
            for (Object tUe : ueSet) {
                Unit ue = (Unit)tUe;
                if (!ue.getUeEquipment().getEqIdFull().equalsIgnoreCase(inEqId)) continue;
                return ue;
            }
        }
        return null;
    }

    @Nullable
    public Set getUnitServiceOrders() {
//        IArgoServiceOrderManager srvomgr = (IArgoServiceOrderManager)Roastery.getBean((String)"serviceOrderManager");
//        return srvomgr.getServiceOrders((Serializable)this.getUnitGkey());
        return null;
    }

    public Set<EquipmentState> getUnitAccEquipmentStates() {
        Set unitEquipments = this.getUnitUeSet();
        HashSet<EquipmentState> equipmentStates = new HashSet<EquipmentState>();
        if (unitEquipments != null) {
            for (Object unitEquipment : unitEquipments) {
                EquipmentState equipmentState = ((UnitEquipment)unitEquipment).getUeEquipmentState();
                if (!equipmentState.getEqsEquipment().getEqClass().equals((Object)EquipClassEnum.ACCESSORY)) continue;
                equipmentStates.add(equipmentState);
            }
        }
        return equipmentStates;
    }

    public Set<EquipmentState> getUnitChsEquipmentStates() {
        Set unitEquipments = this.getUnitUeSet();
        HashSet<EquipmentState> equipmentStates = new HashSet<EquipmentState>();
        if (unitEquipments != null) {
            for (Object unitEquipment : unitEquipments) {
                EquipmentState equipmentState = ((UnitEquipment)unitEquipment).getUeEquipmentState();
                if (!equipmentState.getEqsEquipment().getEqClass().equals((Object)EquipClassEnum.CHASSIS) || !((UnitEquipment)unitEquipment).hasNotBeenDetached()) continue;
                equipmentStates.add(equipmentState);
            }
        }
        return equipmentStates;
    }

    public Set<EquipmentState> getUnitCtrEquipmentStates() {
        Set unitEquipments = this.getUnitUeSet();
        HashSet<EquipmentState> equipmentStates = new HashSet<EquipmentState>();
        if (unitEquipments != null) {
            for (Object unitEquipment : unitEquipments) {
                EquipmentState equipmentState = ((UnitEquipment)unitEquipment).getUeEquipmentState();
                if (!equipmentState.getEqsEquipment().getEqClass().equals((Object)EquipClassEnum.CONTAINER)) continue;
                equipmentStates.add(equipmentState);
            }
        }
        return equipmentStates;
    }

    private String getUnitServicesImpedimentString(IEventType inEventType) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Collection impediments = sm.getImpedimentsForServiceOnEntity((ILogicalEntity)this, inEventType);
        return this.getImpIdsFromImpediments(impediments, inEventType);
    }

    private Map<IEventType, String> getUnitServicesImpedimentByEventType(IEventType[] inEventTypes) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Map impedimentsByEventType = sm.getImpedimentsForServiceOnEntityAndEventTypes((ILogicalEntity)this, inEventTypes);
        HashMap<IEventType, String> impedimentIdsByEventType = new HashMap<IEventType, String>(impedimentsByEventType.size());
        for (int i = 0; i < inEventTypes.length; ++i) {
            IEventType eventType = inEventTypes[i];
            Collection impediments = (Collection)impedimentsByEventType.get((Object)eventType);
            impedimentIdsByEventType.put(eventType, this.getImpIdsFromImpediments(impediments, eventType));
        }
        return impedimentIdsByEventType;
    }

    private String getImpIdsFromImpediments(Collection inImpediments, IEventType inEventType) {
        HashSet impedimentIds = new HashSet();
        if (inImpediments != null && !inImpediments.isEmpty()) {
            UnitUtils.addImpediments(inImpediments, impedimentIds);
        }
        if (LOGGER.isInfoEnabled()) {
            CodeTimer ct = new CodeTimer(LOGGER, Level.INFO);
            StringBuilder allEquipServices = new StringBuilder();
            allEquipServices.append(inEventType != null ? inEventType.getId() : "all");
            allEquipServices.append("; ");
            String description = "calc impediments for services " + allEquipServices + " of " + this.getUnitId();
            ct.report(description);
        }
        String impIds = ArgoUtils.createStringFromSetValues(impedimentIds, (String)",");
        return impIds;
    }

    public IMetafieldId getScopeFieldId() {
        return IInventoryField.UNIT_COMPLEX;
    }

    public IScopeEnum getScopeLevel() {
        return ScopeEnum.COMPLEX;
    }

    public void updateOnPowerStatus(Boolean inOnPower, Boolean inWantOnPower, Boolean inRecordServiceEvent) throws BizViolation {
        Boolean unitRequiresPower;
        Boolean bl = unitRequiresPower = this.getUnitRequiresPower() == null ? Boolean.FALSE : this.getUnitRequiresPower();
        if (!unitRequiresPower.booleanValue()) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__SET_POWER_ON_NON_REEFER, null, (Object)this.getUnitId());
        }
        if (inOnPower != null && !inOnPower.equals(this.getUnitIsPowered())) {
            this.setUnitIsPowered(inOnPower);
            if (inRecordServiceEvent != null && inRecordServiceEvent.booleanValue()) {
                EventEnum eventType = inOnPower != false ? EventEnum.UNIT_POWER_CONNECT : EventEnum.UNIT_POWER_DISCONNECT;
                IEvent event = this.recordUnitEvent((IEventType)eventType, null, null);
                if (inOnPower.booleanValue()) {
                    try {
                        UnitEventExtractManager.createReeferEvent(this, event);
                    }
                    catch (Exception e) {
                        LOGGER.error((Object)("problem recording UnitEventExtractManager.createReeferEvent " + (Object)event));
                        LOGGER.error((Object)("problem recording UnitEventExtractManager Stack Trace " + CarinaUtils.getStackTrace((Throwable)e)));
                    }
                } else {
                    try {
                        UnitEventExtractManager.updateReeferEvent(this, event);
                    }
                    catch (Exception e) {
                        LOGGER.error((Object)("problem recording UnitEventExtractManager.updateReeferEvent " + (Object)event));
                        LOGGER.error((Object)("problem recording UnitEventExtractManager Stack Trace " + CarinaUtils.getStackTrace((Throwable)e)));
                    }
                }
            }
        }
        if (inWantOnPower != null && !inWantOnPower.equals(this.getUnitWantPowered())) {
            this.setUnitWantPowered(inWantOnPower);
            this.setUnitPowerRqstTime(ArgoUtils.timeNow());
        }
    }

    public void updateIsAlarmOnStatus(Boolean inIsAlarmOn, boolean inRecordServiceEvent) throws BizViolation {
        Boolean isAlarmCurrentlyOn = this.getUnitIsAlarmOn();
        if (inIsAlarmOn != null && !inIsAlarmOn.equals(isAlarmCurrentlyOn)) {
            this.setUnitIsAlarmOn(inIsAlarmOn);
            if (inRecordServiceEvent && (isAlarmCurrentlyOn != null || inIsAlarmOn.booleanValue())) {
                EventEnum eventType = EventEnum.UNIT_ALARM;
                FieldChanges fieldChanges = new FieldChanges();
                fieldChanges.setFieldChange(UnitField.UFV_IS_ALARM_ON, (Object)isAlarmCurrentlyOn, (Object)inIsAlarmOn);
                this.recordUnitEvent((IEventType)eventType, fieldChanges, null);
            }
        }
    }

    public void updateRequiresPower(Boolean inPowerRequired) {
        this.setUnitRequiresPower(inPowerRequired);
    }

    public void updateWantPowered(Boolean inPowerRequired) {
        this.setUnitWantPowered(inPowerRequired);
    }

    public void updateUfvIsDirectIbToObMove(Boolean inIsDirectedToOb) throws BizViolation {
        Facility fcy;
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        if (ufv == null && (fcy = ContextHelper.getThreadFacility()) != null) {
            ufv = this.getUfvForFacilityLiveOnly(fcy);
        }
        if (ufv == null) {
            if (inIsDirectedToOb.booleanValue()) {
                throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UFV__ACTIVE_NOT_FOUND_UPDATE_DELIVERY_REQ, null);
            }
            return;
        }
        ufv.updateUfvIsDirectIbToObMove(inIsDirectedToOb);
    }

    public void setUnitWeightToTareWeight() {
        Equipment eq = this.getPrimaryEq();
        Double wt = eq != null ? eq.getEqTareWeightKg() : null;
        this.setUnitGoodsAndCtrWtKg(wt);
    }

    public String getUnitImpediments() {
        return this.getUnitServicesImpedimentString(null);
    }

    public String getUnitActiveImpediments() {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Collection impediments = sm.getImpedimentsForEntity((ILogicalEntity)this);
        if (impediments == null) {
            return null;
        }
        ArrayList<String> impedimentDetailList = null;
        for (Object impediment : impediments) {
            if (!((IImpediment)impediment).isImpedimentActive()) continue;
            if (impedimentDetailList == null) {
                impedimentDetailList = new ArrayList<String>();
            }
            impedimentDetailList.add(UnitUtils.getImpedimentsDetails((IImpediment)impediment));
        }
        return StringUtils.join(impedimentDetailList, (String)"\n");
    }

    public String getUnitNonActiveImpediments() {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Collection impediments = sm.getImpedimentsForEntity((ILogicalEntity)this);
        if (impediments == null) {
            return null;
        }
        ArrayList<String> impedimentDetailList = null;
        for (Object impediment : impediments) {
            if (((IImpediment)impediment).isImpedimentActive()) continue;
            if (impedimentDetailList == null) {
                impedimentDetailList = new ArrayList<String>();
            }
            impedimentDetailList.add(UnitUtils.getImpedimentsDetails((IImpediment)impediment));
        }
        return StringUtils.join(impedimentDetailList, (String)"\n");
    }

    @Override
    public void updateDenormalizedFields() {
        this.updateDenormalizedFields(true);
    }

    public void updateDenormalizedFields(boolean inIgnoreDbImportDataSources) {
        DataSourceEnum dataSource;
        if (ContextHelper.getThreadIsFromUpgrade()) {
            return;
        }
        if (inIgnoreDbImportDataSources && (DataSourceEnum.SNX.equals((Object)(dataSource = ContextHelper.getThreadDataSource())) || DataSourceEnum.DATA_IMPORT.equals((Object)dataSource) || DataSourceEnum.SPARCS_IMPORT.equals((Object)dataSource))) {
            return;
        }
        ImpedimentsBean impedimentsBean = this.calculateImpediments(false);
        if (impedimentsBean != null) {
            this.updateImpediments(impedimentsBean, ArgoUtils.timeNow());
        } else {
            this.setUnitTimeDenormalizeCalc(ArgoUtils.timeNow());
        }
    }

    protected void updateImpediments(ImpedimentsBean inImpedimentsBean, Date inTime) {
        Boolean b = this.getUnitStoppedRoad();
        boolean wasStoppedRoad = b != null && b != false;
        this.setUnitStoppedVessel(inImpedimentsBean.isStopVessel());
        this.setUnitStoppedRoad(inImpedimentsBean.isStopRoad());
        this.setUnitStoppedRail(inImpedimentsBean.isStopRail());
        this.setUnitImpedimentVessel(inImpedimentsBean.getImpedimentVessel());
        this.setUnitImpedimentRoad(inImpedimentsBean.getImpedimentRoad());
        this.setUnitImpedimentRail(inImpedimentsBean.getImpedimentRail());
        this.setUnitTimeDenormalizeCalc(inTime);
        if (wasStoppedRoad != inImpedimentsBean.isStopRoad()) {
            LOGGER.info((Object)("updateImpediments: Road Impediments for " + this + " changed to: " + inImpedimentsBean.getImpedimentRoad()));
            this.toggleRoadAvailability();
        }
    }

    @Nullable
    public ImpedimentsBean calculateImpediments(boolean inForceUpdate) {
        String priorImpedimentVessel = this.getUnitImpedimentVessel();
        String priorImpedimentRoad = this.getUnitImpedimentRoad();
        String priorImpedimentRail = this.getUnitImpedimentRail();
        EventType[] eventTypes = new EventType[]{EventType.resolveIEventType((IEventType)EventEnum.UNIT_LOAD), EventType.resolveIEventType((IEventType)EventEnum.UNIT_DELIVER), EventType.resolveIEventType((IEventType)EventEnum.UNIT_RAMP)};
        Map<IEventType, String> impedimentsByEventType = this.getUnitServicesImpedimentByEventType((IEventType[]) eventTypes);
        String impedimentVessel = impedimentsByEventType.get((Object)eventTypes[0]);
        String impedimentRoad = impedimentsByEventType.get((Object)eventTypes[1]);
        String impedimentRail = impedimentsByEventType.get((Object)eventTypes[2]);
        boolean unitNeedsUpdate = inForceUpdate || !StringUtils.equals((String)impedimentVessel, (String)priorImpedimentVessel) || !StringUtils.equals((String)impedimentRoad, (String)priorImpedimentRoad) || !StringUtils.equals((String)impedimentRail, (String)priorImpedimentRail);
        ImpedimentsBean bean = null;
        if (unitNeedsUpdate) {
            bean = new ImpedimentsBean(this.getUnitGkey(), impedimentVessel, impedimentRoad, impedimentRail);
        }
        return bean;
    }

    public Boolean getUnitIsReefer() {
        return this.isReefer();
    }

    public Boolean getUnitIsHazard() {
        Boolean gdsIsHazard;
        Boolean isHazard = Boolean.FALSE;
        GoodsBase goods = this.getUnitGoods();
        if (goods != null && (gdsIsHazard = goods.getGdsIsHazardous()) != null) {
            isHazard = gdsIsHazard;
        }
        return isHazard;
    }

    @Nullable
    public Long getUnitIBRailVisit() {
        if (this.getUnitDeclaredIbCv() != null && this.getUnitDeclaredIbCv().getCvCarrierMode().equals((Object)LocTypeEnum.TRAIN) && this.getUnitDeclaredIbCv().getCvCvd() != null) {
            return this.getUnitDeclaredIbCv().getCvCvd().getCvdGkey();
        }
        Facility facility = ContextHelper.getThreadFacility();
        UnitFacilityVisit ufv = this.getUfvForFacilityNewest(facility);
        if (ufv != null && ufv.getUfvActualIbCv() != null && ufv.getUfvActualIbCv().getCvCvd() != null) {
            return ufv.getUfvActualIbCv().getCvCvd().getCvdGkey();
        }
        return null;
    }

    @Nullable
    public String getUnitRailCar() {
        Facility facility = ContextHelper.getThreadFacility();
        UnitFacilityVisit ufv = this.getUfvForFacilityNewest(facility);
        if (ufv != null && ufv.getUfvLastKnownPosition() != null) {
            LocPosition pos = ufv.getUfvLastKnownPosition();
            return LocTypeEnum.RAILCAR.equals((Object)pos.getPosLocType()) ? pos.getPosLocId() : "";
        }
        return null;
    }

    private void recordLifecyleEvent(EventEnum inEvent, String inCreateNote) {
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        try {
            srvcMgr.recordEvent((IEventType)inEvent, inCreateNote, null, null, (IServiceable)this, (FieldChanges)null);
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inEvent.getKey() + " on " + this + " : " + e + " (ignored)"));
        }
    }

    @Nullable
    public String getUnitStripServiceNote() {
        return this.getServiceNote(EventEnum.UNIT_STRIP);
    }

    @Nullable
    public String getUnitStuffServiceNote() {
        return this.getServiceNote(EventEnum.UNIT_STUFF);
    }

    private String getServiceNote(EventEnum inEventEnum) {
        EventType eventType;
        if (inEventEnum == null) {
            return null;
        }
        EventManager em = (EventManager) Roastery.getBean((String)"eventManager");
//        List eventList = em.getEventHistory(eventType = EventType.resolveIEventType((IEventType)inEventEnum), (ILogicalEntity)this);
//        if (eventList == null || eventList.isEmpty()) {
//            return null;
//        }
//        StringBuilder ids = new StringBuilder();
//        int count = 0;
//        int listSize = eventList.size();
//        for (Event event : eventList) {
//            ids.append(event.getEventNote());
//            if (++count == listSize) continue;
//            ids.append(';');
//        }
//        return ids.length() > 0 ? ids.toString() : null;
        return null;
    }

    @Nullable
    public String getUnitEquipDmgsItmNote() throws BizViolation {
        UnitEquipDamages damages = this.getDamages(this.getPrimaryEq());
        if (damages == null) {
            return null;
        }
        StringBuilder ids = new StringBuilder();
        List dmgItmList = damages.getDmgsItems();
        if (dmgItmList == null) {
            return null;
        }
        int count = 0;
        int listSize = dmgItmList.size();
        for (Object dmgItem : dmgItmList) {
            ids.append(((UnitEquipDamageItem)dmgItem).getDmgitemDescription());
            if (++count == listSize) continue;
            ids.append(';');
        }
        return ids.length() > 0 ? ids.toString() : null;
    }

    public void updateCommodity(Commodity inCmdy) {
        GoodsBase goods = this.ensureGoods();
        goods.setGdsCommodity(inCmdy);
    }

    @Override
    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote) {
        return this.recordUnitEvent(inUnitEventType, inAlreadyUpdatedFields, inEventNote, null);
    }

    @Override
    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime) {
        IEventType eventToRecord = inUnitEventType;
        if (this.getUnitEqRole() != null && this.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) {
            eventToRecord = UnitEventMap.getRoleSpecificEvent(inUnitEventType, this.getUnitEqRole());
        }
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IEvent evnt = null;
        try {
            evnt = srvcMgr.recordEvent(eventToRecord, inEventNote, null, null, (IServiceable)this, inAlreadyUpdatedFields, inEventTime);
            if (inUnitEventType == EventEnum.UNIT_IN_VESSEL || inUnitEventType == EventEnum.UNIT_DISCH) {
                LOGGER.warn((Object)("Recording event of type " + inUnitEventType.getId() + " on " + this));
            }
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inUnitEventType.getId() + " on " + this + " : " + e + " (ignored)"));
        }
        return evnt;
    }

    public IEvent recordEvent(IEventType inEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime) {
        return this.recordUnitEvent(inEventType, inAlreadyUpdatedFields, inEventNote, inEventTime);
    }

    @Override
    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime, IEntity inRelatedEntity) {
        return this.recordUnitEvent(inUnitEventType, inAlreadyUpdatedFields, inEventNote, inEventTime, inRelatedEntity, null);
    }

    @Override
    @Nullable
    public IEvent recordUnitEvent(IEventType inUnitEventType, FieldChanges inAlreadyUpdatedFields, String inEventNote, Date inEventTime, IEntity inRelatedEntity, FieldChanges inOutMoreChanges) {
        if (inRelatedEntity.getPrimaryKey() == null) {
            HibernateApi.getInstance().save((Object)inRelatedEntity);
        }
        IEventType eventToRecord = inUnitEventType;
        if (this.getUnitEqRole() != null && this.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) {
            eventToRecord = UnitEventMap.getRoleSpecificEvent(inUnitEventType, this.getUnitEqRole());
        }
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IEvent evnt = null;
        try {
            evnt = srvcMgr.recordEvent(eventToRecord, inEventNote, null, null, (IServiceable)this, inAlreadyUpdatedFields, inEventTime, inRelatedEntity, inOutMoreChanges);
        }
        catch (Exception e) {
            LOGGER.error((Object)("problem recording event of type " + inUnitEventType.getId() + " on " + this + " : " + e + " (ignored)"));
        }
        return evnt;
    }

    public void recordUnitEventOnComboUnits(final IEventType inEventType, final FieldChanges inFieldChanges, final String inEventNote) {
        try {
            this.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

                @Override
                public void apply(Unit inUnit) throws BizViolation {
                    inUnit.recordUnitEvent(inEventType, inFieldChanges, inEventNote);
                }

                @Override
                public boolean allowUpdate(Unit inUnit) {
                    return true;
                }
            });
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)inBizViolation);
        }
    }

    protected void toggleRoadAvailability() {
        boolean available;
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue == null) {
            return;
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfvNowActive();
        if (ufv == null) {
            return;
        }
        if (!ufv.isInFacility()) {
            return;
        }
        if (!ufv.isDepartingInland()) {
            return;
        }
        IServicesManager srvcMgr = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Date enableTime = srvcMgr.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_ENABLE_ROAD, (IServiceable)this);
        Date disableTime = srvcMgr.getMostRecentTimeForEvent((IEventType)EventEnum.UNIT_DISABLE_ROAD, (IServiceable)this);
        boolean currentlyEnabled = enableTime != null && (disableTime == null || enableTime.after(disableTime));
        Boolean b = this.getUnitStoppedRoad();
        boolean bl = available = b != null && b == false;
        if (available && !currentlyEnabled) {
            this.recordUnitEvent((IEventType)EventEnum.UNIT_ENABLE_ROAD, null, null);
        } else if (!available && currentlyEnabled) {
            this.recordUnitEvent((IEventType)EventEnum.UNIT_DISABLE_ROAD, null, null);
        }
    }

    @Nullable
    public static UnitYardVisit getUyvForUnit(Serializable inComplexGkey, Serializable inFacilityGKey, Serializable inYardGkey, Unit inUnit) {
        List uyvList;
        Facility facility;
        UnitFacilityVisit ufv;
        if (inComplexGkey.equals(inUnit.getUnitComplex().getCpxGkey()) && (ufv = inUnit.getUfvForFacilityNewest(facility = (Facility)HibernateApi.getInstance().load(Facility.class, inFacilityGKey))) != null && (uyvList = ufv.getUfvUyvList()) != null) {
            for (Object uyv : uyvList) {
                if (!inYardGkey.equals(((UnitYardVisit)uyv).getUyvYard().getYrdGkey())) continue;
                return (UnitYardVisit)uyv;
            }
        }
        return null;
    }

    protected void updateUnitVisitState(UnitVisitStateEnum inNewState) throws BizViolation {
        UnitVisitStateEnum priorState = this.getUnitVisitState();
        if (inNewState.equals((Object)priorState)) {
            return;
        }
        if (!Unit.getValidPhaseTransitions(priorState).contains((Object)inNewState)) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS_ILLEGAL_STATE_TRANSITION, null, (Object)this.getUnitId(), (Object)((Object)priorState), (Object)((Object)inNewState));
        }
        if (UnitVisitStateEnum.RETIRED.equals((Object)inNewState) || UnitVisitStateEnum.DEPARTED.equals((Object)inNewState)) {
            this.retireUfvs();
        }
        this.innerUpdateUnitVisitState(inNewState);
    }

    protected void innerUpdateUnitVisitState(final UnitVisitStateEnum inNewState) throws BizViolation {
        this.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) throws BizViolation {
                inUnit.doInnerUpdateUnitVisitState(inNewState);
            }

            @Override
            public boolean allowUpdate(Unit inUnit) {
                return !UnitVisitStateEnum.RETIRED.equals((Object)inNewState) || inUnit.equals(Unit.this);
            }
        });
    }

    protected void doInnerUpdateUnitVisitState(UnitVisitStateEnum inNewState) throws BizViolation {
        EqBaseOrder order;
        UnitFacilityVisit activeUfv;
        UnitVisitStateEnum priorState = this.getUnitVisitState();
        if (inNewState.equals((Object)priorState)) {
            return;
        }
        EventEnum event = null;
        if (UnitVisitStateEnum.ACTIVE.equals((Object)inNewState)) {
            event = EventEnum.UNIT_ACTIVATE;
            if (this.getUnitEquipment() != null) {
                Complex complex = this.getUnitComplex();
                complex = Complex.hydrate((Serializable)complex.getPrimaryKey());
                Operator operator = complex.getCpxOperator();
                Boolean b = (operator = Operator.hydrate((Serializable)operator.getPrimaryKey())).getOprEnforceSingleEqUse();
                boolean enforceByOperator = b != null && b != false;
                Unit activeUnit = null;
                activeUnit = enforceByOperator ? Unit.getFndr().findActiveUeUsingEqInAnyRole(operator, null, this.getUnitEquipment()) : Unit.getFndr().findActiveUeUsingEqInAnyRole(null, complex, this.getUnitEquipment());
                if (activeUnit != null) {
                    throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_ALREADY_ACTIVE, null, (Object)this, (Object)this.getUnitEquipment().getEqIdFull(), (Object)activeUnit);
                }
            }
        } else if (UnitVisitStateEnum.RETIRED.equals((Object)inNewState)) {
            Boolean isSuppressRecUnitEvent = ContextHelper.isThreadSnxSupressInternalUnitEvents();
            if (isSuppressRecUnitEvent.booleanValue()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("skipping writing UNIT_RETIRED event as it is turned off for " + this.getUnitId()));
                }
            } else {
                event = EventEnum.UNIT_RETIRE;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("writing UNIT_RETIRED event for " + this.getUnitId()));
                }
            }
        } else if (UnitVisitStateEnum.DEPARTED.equals((Object)inNewState) && (activeUfv = this.getUnitActiveUfvNowActive()) != null) {
            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__CANT_DEPART_ACTIVE_UNIT, null, (Object)this, (Object)activeUfv.getUfvFacility());
        }
        boolean orderAssigned = this.isAllowedArrivalOrderAssign();
        this.setUnitVisitState(inNewState);
        this.setUnitTimeLastStateChange(ArgoUtils.timeNow());
        if (orderAssigned && !this.isAllowedArrivalOrderAssign() && (order = this.getArrivalOrder()) != null) {
            order.decrementTallyRcvCount();
        }
        if (UnitVisitStateEnum.ADVISED.equals((Object)priorState) && !UnitVisitStateEnum.ADVISED.equals((Object)inNewState) && !UnitVisitStateEnum.RETIRED.equals((Object)inNewState) && (order = this.getArrivalOrder()) != null) {
            order.incrementTallyRcv(this);
        }
        if (event != null && (!EventEnum.UNIT_ACTIVATE.equals((Object)event) || priorState == null || UnitVisitStateEnum.ADVISED.equals((Object)priorState))) {
            this.recordLifecyleEvent(event, null);
        }
    }

    private void retireUfvs() throws BizViolation {
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object ufv : ufvSet) {
                if (UnitVisitStateEnum.DEPARTED.equals((Object)((UnitFacilityVisit)ufv).getUfvVisitState())) continue;
                ((UnitFacilityVisit)ufv).makeRetired();
            }
        }
    }

    public static Set getValidPhaseTransitions(UnitVisitStateEnum inVisitState) {
        if (UnitVisitStateEnum.ADVISED.equals((Object)inVisitState)) {
            return TRANSITIONS_ADVISED;
        }
        if (UnitVisitStateEnum.ACTIVE.equals((Object)inVisitState)) {
            return TRANSITIONS_ACTIVE;
        }
        if (UnitVisitStateEnum.DEPARTED.equals((Object)inVisitState)) {
            return TRANSITIONS_DEPARTED;
        }
        if (UnitVisitStateEnum.RETIRED.equals((Object)inVisitState)) {
            return TRANSITIONS_RETIRED;
        }
        throw BizFailure.create((String)("invalid input: " + (Object)((Object)inVisitState)));
    }

    public void attachNewBaseGoods() {
        GoodsBase goods = new GoodsBase();
        this.attachNewGoods(goods);
    }

    private void attachNewGoods(GoodsBase inGoodsBase) {
        this.setUnitGoods(inGoodsBase);
        inGoodsBase.setGdsUnit(this);
    }

    @Nullable
    public Unit getUeInRole(EqUnitRoleEnum inRole) {
        if (EqUnitRoleEnum.PAYLOAD.equals((Object)inRole)) {
            throw BizFailure.create((String)inRole.getKey());
        }
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null) {
            for (Object ue : ueSet) {
                if (!((Unit)ue).hasNotBeenDetached() || !((Unit)ue).getUeEqRole().equals((Object)inRole)) continue;
                return ((Unit)ue);
            }
        }
        return null;
    }

    @NotNull
    public Set<Unit> getUnitsInPayloadRole() {
        HashSet<Unit> payloadUnits = new HashSet<Unit>();
        UnitCombo combo = this.getUnitCombo();
        if (combo != null) {
            for (Unit unit : combo.getUcUnitsNullSafe()) {
                if (!EqUnitRoleEnum.PAYLOAD.equals((Object)unit.getUnitEqRole())) continue;
                payloadUnits.add(unit);
            }
        }
        return payloadUnits;
    }

    public boolean isChassisBundle() {
        if (EqUnitRoleEnum.CARRIAGE.equals((Object)this.getUnitEqRole()) && this.getUeInRole(EqUnitRoleEnum.PRIMARY) == null) {
            Set<Unit> payloadUnits = this.getUnitsInPayloadRole();
            return !payloadUnits.isEmpty();
        }
        return false;
    }

    public IValueHolder[] getUnitItineraryVaa() {
        ArrayList ufvList = new ArrayList(this.getUnitUfvSet());
        Collections.sort(ufvList, new Comparator(){

            public int compare(Object inObj1, Object inObj2) {
                int uv2;
                UnitFacilityVisit ufv1 = (UnitFacilityVisit)inObj1;
                UnitFacilityVisit ufv2 = (UnitFacilityVisit)inObj2;
                int uv1 = UnitUtils.getUnitVisitStateValue(ufv1.getUfvVisitState());
                if (uv1 < (uv2 = UnitUtils.getUnitVisitStateValue(ufv2.getUfvVisitState()))) {
                    return -1;
                }
                if (uv1 > uv2) {
                    return 1;
                }
                Date t1 = UnitUtils.disallowNull(ufv1.getUfvTimeIn());
                Date t2 = UnitUtils.disallowNull(ufv2.getUfvTimeIn());
                return t1.compareTo(t2);
            }
        });
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(UnitField.UFV_FACILITY_ID);
        fields.add(UnitField.UFV_FACILITY_POINT_ID);
        fields.add(UnitField.UFV_FACILITY_TERMINAL);
        fields.add(UnitField.UFV_FACILITY_PLACE);
        fields.add(UnitField.UFV_VISIT_STATE);
        fields.add(UnitField.UFV_TIME_IN);
        fields.add(UnitField.UFV_TIME_OUT);
        IValueHolder[] vaoArray = new IValueHolder[ufvList.size()];
        int i = 0;
        for (Object ufv : ufvList) {
            vaoArray[i++] = ((UnitFacilityVisit)ufv).getValueObject(fields);
        }
        return vaoArray;
    }

    public boolean isPrimaryEqAChassis() {
        Equipment primaryEq = this.getPrimaryEq();
        return primaryEq != null && EquipClassEnum.CHASSIS.equals((Object)primaryEq.getEqClass());
    }

    public boolean isPrimaryEqAContainer() {
        Equipment primaryEq = this.getPrimaryEq();
        return primaryEq != null && EquipClassEnum.CONTAINER.equals((Object)primaryEq.getEqClass());
    }

    @Nullable
    public String getUnitFireCodeAsString() {
        String code = null;
        if (this.getUnitGoods() != null && this.getUnitGoods().getGdsHazards() != null && this.getUnitGoods().getGdsHazards().getHzrdWorstFireCode() != null) {
            HazardFireCode fireCode = this.getUnitGoods().getGdsHazards().getHzrdWorstFireCode();
            code = fireCode.getFirecodeFireCode() + " -  " + fireCode.getFirecodeFireCodeClass();
        }
        return code;
    }

    public EquipBasicLengthEnum getBasicLength() {
        Equipment eq = this.getPrimaryEq();
        EquipType eqType = eq == null ? null : eq.getEqEquipType();
        return eqType == null ? EquipBasicLengthEnum.BASIC20 : eqType.getEqtypBasicLength();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        Object weightKg;
//        ImportDeliveryOrder ido;
//        if (ContextHelper.getThreadIsFromUpgrade()) {
//            return null;
//        }
//        BizViolation bv = super.validateChanges(inChanges);
//        if (ContextHelper.isUpdateFromHumanUser()) {
//            FieldChange fc;
//            if (inChanges.hasFieldChange(UnitField.UNIT_ROUTING) && inChanges.hasFieldChange(UnitField.UNIT_RTG_TRUCKING_COMPANY) && (fc = inChanges.getFieldChange(UnitField.UNIT_RTG_TRUCKING_COMPANY)) != null) {
//                this.recordUnitEvent((IEventType)EventEnum.UNIT_TRUCKER_ASSIGNED, inChanges, null, null);
//            }
//            if (inChanges.hasFieldChange(UnitField.UNIT_ROUTING) || inChanges.hasFieldChange(UnitField.UNIT_CATEGORY)) {
//                try {
//                    UnitUtils.validateUfvObCv(this);
//                }
//                catch (BizViolation inBizViolation) {
//                    bv = inBizViolation.appendToChain(bv);
//                }
//            }
//        }
//        if (inChanges.hasFieldChange(IInventoryField.UNIT_IMPORT_DELIVERY_ORDER) && (ido = this.getUnitImportDeliveryOrder()) != null) {
//            ScopedBizUnit idoLine = ido.getIdoLine();
//            ScopedBizUnit unitLine = this.getUnitLineOperator();
//            if (idoLine != null && unitLine != null && !LineOperator.isUnknownLineOperator((ScopedBizUnit)unitLine) && !idoLine.equals((Object)unitLine)) {
//                bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.IDO_INVALID_UNIT_LINE, (BizViolation)bv, (Object)this.getUnitId(), (Object)unitLine.getBzuId(), (Object)idoLine.getBzuId());
//            }
//        }
//        try {
//            this.checkForProtectedFieldUpdate(inChanges);
//        }
//        catch (BizViolation inBizViolation) {
//            bv = inBizViolation.appendToChain(bv);
//        }
//        if (inChanges.hasFieldChange(IInvField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS) && (weightKg = inChanges.getFieldChange(IInvField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS).getNewValue()) instanceof Double) {
//            try {
//                UnitUtils.validateUnitVgmWeight(this, (Double)weightKg);
//            }
//            catch (BizViolation inBizViolation) {
//                bv = inBizViolation.appendToChain(bv);
//            }
//        }
//        if (inChanges.hasFieldChange(IInvField.UNIT_GOODS_AND_CTR_WT_KG) && !this.getUnitIsBundle().booleanValue() && (weightKg = inChanges.getFieldChange(IInvField.UNIT_GOODS_AND_CTR_WT_KG).getNewValue()) instanceof Double) {
//            try {
//                UnitUtils.validateUnitGrossWeight(this, (Double)weightKg);
//            }
//            catch (BizViolation inBizViolation) {
//                bv = inBizViolation.appendToChain(bv);
//            }
//        }
//        return bv;
        return null;
    }

    public void checkForProtectedFieldUpdate(FieldChanges inFcs) throws BizViolation {
        IInventoryFieldUpdateProtection inventoryFieldUpdateProtection = (IInventoryFieldUpdateProtection)Roastery.getBean((String)"fieldUpdateProtection");
        inventoryFieldUpdateProtection.checkForProtectedFieldUpdate(inFcs, this);
    }

    public boolean isReservedForBooking() {
        if (!FreightKindEnum.BBK.equals((Object)this.getUnitFreightKind()) && this.getUnitPrimaryUe() != null && this.getUnitPrimaryUe().getUeDepartureOrderItem() != null) {
            return !UnitCategoryEnum.EXPORT.equals((Object)this.getUnitCategory()) && !UnitCategoryEnum.TRANSSHIP.equals((Object)this.getUnitCategory());
        }
        return false;
    }

    public void adjustOrderTallyForReturnToStorage() {
        EqBaseOrder departureOrder = this.getDepartureOrder();
        if (departureOrder != null && (EquipmentOrderSubTypeEnum.BOOK.equals((Object)departureOrder.getEqboSubType()) || EquipmentOrderSubTypeEnum.RAIL.equals((Object)departureOrder.getEqboSubType()) || EquipmentOrderSubTypeEnum.ELO.equals((Object)departureOrder.getEqboSubType()))) {
            EqBaseOrderItem eqBaseOrderItem;
            LOGGER.info((Object)("Decrementing EDO Tally Receive for return to storage on " + this.getUnitId()));
            departureOrder.decrementTallyRcv(this);
            IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager)Roastery.getBean((String)"equipmentOrderManager");
            UnitEquipment unitEquipment = this.getUnitPrimaryUe();
            if (unitEquipment != null && (eqBaseOrderItem = unitEquipment.getUeDepartureOrderItem()) != null) {
                LOGGER.info((Object)("Decrementing EDO Tally for return to storage on " + this.getUnitId()));
                equipOrdMgr.decrementTally((Serializable)eqBaseOrderItem.getEqboiGkey());
            }
        }
    }

    public boolean isPinNbrUsed() {
        ScopedBizUnit lo = this.getUnitLineOperator();
        if (lo != null) {
            LineOperator lineOperator = null;
            try {
                lineOperator = (LineOperator)HibernateApi.getInstance().downcast((DatabaseEntity)lo, LineOperator.class);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (lineOperator != null) {
                boolean isPin;
                Boolean b = lineOperator.getLineopUsePinNbr();
                boolean bl = isPin = b != null && b != false;
                if (LOGGER.isDebugEnabled() && isPin) {
                    LOGGER.debug((Object)"this unit has PIN generation enabled");
                }
                return isPin;
            }
        }
        return false;
    }

    public boolean isPinNbrAssigned() {
        boolean result = false;
        Routing unitRouting = this.getUnitRouting();
        if (unitRouting != null) {
            String pin = unitRouting.getRtgPinNbr();
            return pin != null && !pin.isEmpty();
        }
        return result;
    }

    public void replaceGoodsWith(GoodsBase inNewGoods) {
        GoodsBase oldGoods = this.getUnitGoods();
        inNewGoods.updateFromGoods(oldGoods);
        this.attachNewGoods(inNewGoods);
        HibernateApi.getInstance().delete((Object)oldGoods);
    }

    public void deleteUfv(UnitFacilityVisit inUfv) throws BizViolation {
        Set ufvSet = this.getUnitUfvSet();
        ufvSet.remove(inUfv);
        if (inUfv.equals(this.getUnitActiveUfv())) {
            UnitFacilityVisit activeUfv = null;
            Date latestDeparture = new Date(0L);
            for (Object ufv : ufvSet) {
                Date timeOut = ((UnitFacilityVisit)ufv).getUfvTimeOut();
                if (ObjectUtils.equals((Object)ufv, (Object)inUfv)) continue;
                if (UnitVisitStateEnum.ACTIVE.equals((Object)((UnitFacilityVisit)ufv).getUfvVisitState())) {
                    activeUfv = ((UnitFacilityVisit)ufv);
                    break;
                }
                if (!UnitVisitStateEnum.DEPARTED.equals((Object)((UnitFacilityVisit)ufv).getUfvVisitState()) || timeOut == null || !timeOut.after(latestDeparture)) continue;
                latestDeparture = timeOut;
                activeUfv = ((UnitFacilityVisit)ufv);
            }
            this.setUnitActiveUfv(activeUfv);
            if (activeUfv == null) {
                this.setUnitVisitState(UnitVisitStateEnum.ADVISED);
            }
        }
        boolean allUfvsComplete = true;
        for (Object anUfvSet : ufvSet) {
            UnitFacilityVisit ufv;
            ufv = (UnitFacilityVisit)anUfvSet;
            if (ufv.isComplete()) continue;
            allUfvsComplete = false;
            break;
        }
        if (allUfvsComplete) {
            this.recordUnitEvent((IEventType)EventEnum.UNIT_CANCEL_PREADVISE, null, null);
            this.makeDeparted();
        }
        HibernateApi.getInstance().delete((Object)inUfv);
    }

    public boolean isDirectDelivery(UnitFacilityVisit inUfv) {
        boolean directIbToOb;
        boolean result = false;
        boolean railResult = false;
        boolean vesselResult = false;
        UserContext userContext = ContextHelper.getThreadUserContext();
        CarrierVisit cv = this.getUnitDeclaredIbCv();
        boolean bl = directIbToOb = inUfv != null && inUfv.getUfvIsDirectIbToObMove() != null && inUfv.getUfvIsDirectIbToObMove() != false;
        if (InventoryConfig.DIRECT_DELIVERY_RAIL.isSetTo("ALWAYS", userContext) && (CarrierVisitPhaseEnum.ARRIVED.equals((Object)cv.getCvVisitPhase()) || CarrierVisitPhaseEnum.WORKING.equals((Object)cv.getCvVisitPhase()) || CarrierVisitPhaseEnum.DEPARTED.equals((Object)cv.getCvVisitPhase())) && (LocTypeEnum.RAILCAR.equals((Object)cv.getCvCarrierMode()) || LocTypeEnum.TRAIN.equals((Object)cv.getCvCarrierMode()))) {
            railResult = true;
        }
        if (LocTypeEnum.VESSEL.equals((Object)cv.getCvCarrierMode()) && directIbToOb) {
            vesselResult = true;
        }
        if (railResult || vesselResult) {
            result = true;
        }
        return result;
    }

    @Nullable
    public Unit getAccessoryOnChs() {
        return this.getUeInRole(EqUnitRoleEnum.ACCESSORY_ON_CHS);
    }

    @Nullable
    public Unit getAccessoryOnCtr() {
        return this.getUeInRole(EqUnitRoleEnum.ACCESSORY);
    }

    @Override
    public Object getFieldValue(IMetafieldId inMetaFieldId) {
//        UeCompatibleField ueCompatibleField = new UeCompatibleField(inMetaFieldId);
//        if (ueCompatibleField.isUeField()) {
//            return ueCompatibleField.getFieldValue(this);
//        }
        UnitFacilityVisit activeUfv = this.getUnitActiveUfvNowActive();
        if (activeUfv != null) {
            if (IInventoryBizMetafield.RFREQ_TEMP_REQUIRED_IN_C.equals((Object)inMetaFieldId)) {
                return activeUfv.getRfreqTempRequiredInC();
            }
            if (IInventoryBizMetafield.RFREQ_TEMP_REQUIRED_IN_F.equals((Object)inMetaFieldId)) {
                return activeUfv.getRfreqTempRequiredInF();
            }
        }
        return super.getFieldValue(inMetaFieldId);
    }

    @Override
    public void setFieldValue(IMetafieldId inMetaFieldId, Object inFieldValue) {
//        UeCompatibleField ueCompatibleField = new UeCompatibleField(inMetaFieldId);
//        if (ueCompatibleField.isUeField()) {
//            ueCompatibleField.setFieldValue(this, inFieldValue);
//            return;
//        }
        super.setFieldValue(inMetaFieldId, inFieldValue);
        if (IInventoryField.UNIT_GRADE_I_D.equals((Object)inMetaFieldId)) {
            this.updateEqsGradeID();
        }
    }

    private void activateNextAdvisedUnit() throws BizViolation {
        Unit advisedUnit;
        IUnitFinder unitFinder = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        if (this.getUnitPrimaryUe() != null && this.getUnitPrimaryUe().getUeEquipment() != null && (advisedUnit = unitFinder.findAdvisedUnitByLandModes(this.getUnitComplex(), this.getUnitPrimaryUe().getUeEquipment())) != null) {
            String unitId = this.getUnitId();
            UnitFacilityVisit advisedUfv = advisedUnit.getUnitActiveUfv();
            HibernateApi.getInstance().flush();
            if (advisedUfv != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("advisedUfv found from the advised Unit's denormalized field: " + unitId + " for " + advisedUfv.getUfvFacility().getFcyId()));
                }
            } else {
                Set ufvSet = advisedUnit.getUnitUfvSet();
                if (ufvSet != null && ufvSet.size() == 1) {
                    for (Object anUfvSet : ufvSet) {
                        UnitFacilityVisit testUfv = (UnitFacilityVisit)anUfvSet;
                        if (testUfv.isInFacility()) {
                            Facility fcy = testUfv.getUfvFacility();
                            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean((String)"messageTranslatorProvider");
                            IMessageTranslator messageTranslator = translatorProvider.getMessageTranslator(ContextHelper.getThreadUserContext().getUserLocale());
                            String msg = MessageTranslatorUtils.getMessage((IMessageTranslator)messageTranslator, (IPropertyKey)IInventoryPropertyKeys.EQ_ALREADY_IN_FACILITY, (Object)unitId, (Object)fcy);
                            LOGGER.warn((Object)msg);
                            continue;
                        }
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug((Object)("advisedUfv found by iterating over  the advised Unit's ufv's: " + unitId));
                        }
                        advisedUfv = testUfv;
                    }
                }
                if (advisedUfv != null) {
                    advisedUfv.makeActive();
                } else if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("advisedUfv not found for the unit: " + unitId));
                }
            }
        }
    }

    public void validateIftChangeAllowed() throws BizFailure {
        ISuperUserManager superUserMgr;
        Facility userFcy = ContextHelper.getThreadFacility();
        if (userFcy == null) {
            return;
        }
        ISystemUserManager systemUserMgr = (ISystemUserManager)PortalApplicationContext.getBean((String)"systemUserManager");
        if (!systemUserMgr.isSystemUser(ContextHelper.getThreadUserId()) && !(superUserMgr = (ISuperUserManager)PortalApplicationContext.getBean((String)"superAdminRoleVoter")).isSuperUser(ContextHelper.getThreadUserId()) && ArgoPrivs.DENY_UNIT_IFT_CHANGE.isAllowed(ContextHelper.getThreadUserContext())) {
            Facility currentFcy;
            UnitFacilityVisit currentUfv = this.getUnitActiveUfv();
            if (currentUfv == null) {
                if (UnitVisitStateEnum.DEPARTED.equals((Object)this.getUnitVisitState())) {
                    currentUfv = this.findMostRecentHistoryUfv();
                } else if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUnitVisitState())) {
                    currentUfv = this.findAdvisedFirstUfv();
                }
            }
            if (currentUfv != null && (currentFcy = currentUfv.getUfvFacility()).isFcyOperational().booleanValue() && !currentFcy.equals((Object)userFcy)) {
                throw BizFailure.create((IPropertyKey)IInventoryPropertyKeys.IFT_CHANGE_NOT_ALLOWED, null, (Object)userFcy.getFcyId(), (Object)currentFcy.getFcyId());
            }
        }
    }

    public void cloneUfvs(Unit inNewUnit) throws BizViolation {
        Set oldUfvs = this.getUnitUfvSet();
        for (Object oldUfv : oldUfvs) {
            ((UnitFacilityVisit)oldUfv).clone(inNewUnit);
        }
    }

    public void touch() {
        Long touchCtr = this.getUnitTouchCtr() != null ? this.getUnitTouchCtr() : 0L;
        this.setUnitTouchCtr(touchCtr + 1L);
    }

    public Class getArchiveClass() {
  //      return ArchiveUnit.class;
        return null;
    }

    public boolean doArchive() {
        UserContext userContext = ContextHelper.getThreadUserContext();
        return ArgoConfig.ARCHIVE_UNITS_PRIOR_TO_PURGE.isOn(userContext);
    }

    public boolean isUnitMarkedForOffisteExam() {
        return ExamEnum.OFFSITE.equals((Object)this.getUnitExam()) || ExamEnum.OFFSITERETURN.equals((Object)this.getUnitExam());
    }

    public boolean isUnitBeingSeized() {
        return InbondEnum.INBOND.equals((Object)this.getUnitInbond());
    }

    public boolean isUnitTestUnit() {
        return this.getUnitId() != null && this.getUnitId().contains("TEST");
    }

    public String getUnitAppliedHoldOrRequiredPermView() {
        HashSet<String> hpvIdSet = new HashSet<String>();
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        Collection impediments = sm.getImpedimentsForServiceOnEntity((ILogicalEntity)this, null);
        for (Object impediment : impediments) {
            if (!((IImpediment)impediment).isImpedimentActive()) continue;
            hpvIdSet.add(((IImpediment)impediment).getFlagType().getHpvId());
        }
        return ArgoUtils.createStringFromSetValues(hpvIdSet, (String)",");
    }

    public Routing ensureUnitRouting() {
        Routing unitRtg = this.getUnitRouting();
        if (unitRtg == null) {
            unitRtg = Routing.createEmptyRouting();
            this.updateUnitRouting(unitRtg);
        }
        return unitRtg;
    }

    @Override
    public void setUnitActiveUfv(UnitFacilityVisit inUnitActiveUfv) {
        UnitFacilityVisit prevActiveUfv = this.getUnitActiveUfv();
        boolean depOrderAssigned = false;
        boolean arrivalOrderAssigned = false;
        if (prevActiveUfv != null && !prevActiveUfv.equals(inUnitActiveUfv)) {
            depOrderAssigned = this.isAllowedDepartureOrderAssign();
            arrivalOrderAssigned = this.isAllowedArrivalOrderAssign();
        }
        super.setUnitActiveUfv(inUnitActiveUfv);
        if (prevActiveUfv != null && !prevActiveUfv.equals(inUnitActiveUfv)) {
            boolean currentDepOrderAssigned = this.isAllowedDepartureOrderAssign();
            boolean currentArrvOrderAssigned = this.isAllowedArrivalOrderAssign();
            if (depOrderAssigned && !currentDepOrderAssigned) {
                this.getDepartureOrder().decrementTallyRcvCount();
            } else if (!depOrderAssigned && currentDepOrderAssigned) {
                this.getDepartureOrder().incrementTallyRcv(this);
            }
            if (arrivalOrderAssigned && !currentArrvOrderAssigned) {
                this.getArrivalOrder().decrementTallyRcvCount();
            } else if (!arrivalOrderAssigned && currentArrvOrderAssigned) {
                this.getArrivalOrder().incrementTallyRcv(this);
            }
        }
    }

    public boolean isAllowedOrderAssign(EqBaseOrder inOrder) {
        if (UnitVisitStateEnum.ADVISED.equals((Object)this.getUnitVisitState())) {
            return false;
        }
        UnitEquipment ue = this.getUnitPrimaryUe();
        if (ue == null) {
            return false;
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        boolean allow = false;
        boolean isArrivalOrder = inOrder.isArrivalOrder();
        if (isArrivalOrder) {
            if (UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState()) && ufv != null) {
                UfvTransitStateEnum transitState = ufv.getUfvTransitState();
                allow = UnitVisitStateEnum.ACTIVE.equals((Object)this.getUnitVisitState()) && (transitState.equals((Object)UfvTransitStateEnum.S30_ECIN) || transitState.equals((Object)UfvTransitStateEnum.S40_YARD) || transitState.equals((Object)UfvTransitStateEnum.S60_LOADED) || transitState.equals((Object)UfvTransitStateEnum.S50_ECOUT));
            } else if (UnitVisitStateEnum.DEPARTED.equals((Object)this.getUnitVisitState()) || UnitVisitStateEnum.RETIRED.equals((Object)this.getUnitVisitState())) {
                allow = true;
            }
        } else if (ufv != null) {
            UfvTransitStateEnum transitState = ufv.getUfvTransitState();
            boolean isUnitInTerminal = transitState.equals((Object)UfvTransitStateEnum.S30_ECIN) || transitState.equals((Object)UfvTransitStateEnum.S40_YARD) || transitState.equals((Object)UfvTransitStateEnum.S60_LOADED) || transitState.equals((Object)UfvTransitStateEnum.S50_ECOUT);
            allow = transitState.equals((Object)UfvTransitStateEnum.S70_DEPARTED) ? this.isAssignedForBooking() : isUnitInTerminal;
        }
        return allow && inOrder.allowUnitAssign(this);
    }

    boolean isAllowedDepartureOrderAssign() {
        EqBaseOrder order = this.getDepartureOrder();
        return order != null && this.isAllowedOrderAssign(order);
    }

    boolean isAllowedArrivalOrderAssign() {
        EqBaseOrder order = this.getArrivalOrder();
        return order != null && this.isAllowedOrderAssign(order);
    }

    @Override
    public Boolean getUnitIsBundle() {
        Boolean isBundle = super.getUnitIsBundle();
        return isBundle == null || isBundle == false ? Boolean.FALSE : Boolean.TRUE;
    }

    public boolean hasPlannedWi(WiMoveKindEnum[] inMoveKinds) {
        UnitFacilityVisit ufv;
        boolean isPlanned = false;
        Set ufvSet = this.getUnitUfvSet();
        Iterator iterator = ufvSet.iterator();
        while (iterator.hasNext() && ((ufv = (UnitFacilityVisit)iterator.next()).isComplete() || !(isPlanned = ufv.hasPlannedWi(inMoveKinds)))) {
        }
        return isPlanned;
    }

    public boolean isLoadedOnVessel() {
        LocPosition curPos = this.findCurrentPosition();
        return curPos != null && curPos.isVesselPosition();
    }

    public void updateCargoQuantityUnit(ServiceQuantityUnitEnum inUnitCargoQuantityUnit) {
        this.setUnitCargoQuantityUnit(inUnitCargoQuantityUnit);
    }

    public ServiceQuantityUnitEnum getCargoQuantityUnit() {
        return this.getUnitCargoQuantityUnit();
    }

    public void updateUnitCargoQuantity(Double inQtyToAdjust) {
        Double existingQty = this.getUnitCargoQuantity() == null ? 0.0 : this.getUnitCargoQuantity();
        this.setUnitCargoQuantity(existingQty + inQtyToAdjust);
    }

    @Nullable
    public String getUnitInboundVesselLloydsId() {
        CarrierVisit inboundCv = this.getInboundCv();
        if (inboundCv.isGenericCv()) {
            return null;
        }
        return inboundCv == null ? null : inboundCv.getCvCvd().getLloydsId();
    }

    @Nullable
    public String getUnitOutboundVesselLloydsId() {
        CarrierVisit outboundCv = this.getOutboundCv();
        if (outboundCv.isGenericCv()) {
            return null;
        }
        return outboundCv == null ? null : outboundCv.getCvCvd().getLloydsId();
    }

    @Nullable
    public String getUnitInboundVesselRadioCallSign() {
        CarrierVisit inboundCv = this.getInboundCv();
        if (inboundCv.isGenericCv()) {
            return null;
        }
        return inboundCv == null ? null : inboundCv.getCvCvd().getRadioCallSign();
    }

    @Nullable
    public String getUnitOutboundVesselRadioCallSign() {
        CarrierVisit outboundCv = this.getOutboundCv();
        return outboundCv == null ? null : outboundCv.getCvCvd().getRadioCallSign();
    }

    public void extactPrePayEvent(IEvent inEvent) {
//        Map<String, String> flexHashMap = UnitEventExtractorPea.getFlexFieldsFromBillingExtractMap(ContextHelper.getThreadUserContext());
//        BillingSequenceProvider billSeq = new BillingSequenceProvider();
//        Long batchId = billSeq.getBatchNextSeqValue();
//        IEvent event = IEvent.resolveIEvent((IEvent)inEvent);
//        UnitEventExtractManager.createOrUpdateChargeableUnitEvent(this, event, batchId, flexHashMap);
    }

    public void extactPrePayEvent(Long inEventGkey) {
//        Map<String, String> flexHashMap = UnitEventExtractorPea.getFlexFieldsFromBillingExtractMap(ContextHelper.getThreadUserContext());
//        BillingSequenceProvider billSeq = new BillingSequenceProvider();
//        Long batchId = billSeq.getBatchNextSeqValue();
//        IEvent event = IEvent.hydrate((Serializable)inEventGkey);
//        UnitEventExtractManager.createOrUpdateChargeableUnitEvent(this, event, batchId, flexHashMap);
    }

    public void propagateLineReeferMonitors(final LineOperator inLine, final boolean inRequireWarning) {
        new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext()).invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                GoodsBase unitGoods = Unit.this.ensureGoods();
                boolean isCntrReeferType = Unit.this.isEquipTypeReefer();
                if (isCntrReeferType) {
                    if (inLine.isPropagateAllowed()) {
                        unitGoods.propagateLineReeferRqments();
                        LOGGER.info((Object)("propagateLineReeferMonitors: Unit[" + (Object)((Object)this) + "]is updated with Line[" + inLine.getBzuId() + "]reefer monitor defaults"));
                    } else {
                        if (inRequireWarning) {
                            MessageCollectorUtils.getMessageCollector().appendMessage(MessageLevelEnum.INFO, IArgoPropertyKeys.LINE_HAS_NO_REEFER_MONITORS, null, new Object[]{inLine.getBzuId()});
                        }
                        LOGGER.info((Object)("propagateLineReeferMonitors: Unit[" + (Object)((Object)this) + "] reefer monitors are set to null, since Line[" + inLine.getBzuId() + "] has no reefer monitor defaults "));
                    }
                }
            }
        });
    }

    public boolean isReeferPlugged() {
        return this.getUnitIsPowered();
    }

    public void updateDefaultReeferMonitorTimes() {
        new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext()).invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                GoodsBase unitGoods = Unit.this.ensureGoods();
                ReeferRqmnts reeferRqmnts = unitGoods.ensureGdsReeferRqmnts();
                Date monitorTime1 = ArgoUtils.getCurrentDateWithHoursAndTime((int)0, (int)0);
                Date monitorTime2 = ArgoUtils.getCurrentDateWithHoursAndTime((int)6, (int)0);
                Date monitorTime3 = ArgoUtils.getCurrentDateWithHoursAndTime((int)12, (int)0);
                Date monitorTime4 = ArgoUtils.getCurrentDateWithHoursAndTime((int)18, (int)0);
                reeferRqmnts.updateReeferMonitors(monitorTime1, monitorTime2, monitorTime3, monitorTime4, null);
            }
        });
    }

    public void applyDepartureOrderOnPayload(final EqBaseOrderItem inNewEqoi) throws BizViolation {
        if (this.getUnitEqRole() != EqUnitRoleEnum.PRIMARY) {
            return;
        }
        if (this.hasNoneInPayloadRole()) {
            return;
        }
        this.applyUpdateOnUnitCombo(new AbstractUnitUpdate(){

            @Override
            public void apply(Unit inUnit) {
                inUnit.setUeOrderItem(inNewEqoi);
            }

            @Override
            public boolean allowUpdate(Unit inUnit) {
                return inUnit.getUnitEqRole() == EqUnitRoleEnum.PAYLOAD;
            }
        });
    }

    private boolean isEquipTypeReefer() {
        EquipIsoGroupEnum eqtypIsoGroup;
        Equipment ueEquipment;
        UnitEquipment unitPrimaryUe = this.getUnitPrimaryUe();
        return unitPrimaryUe != null && (ueEquipment = unitPrimaryUe.getUeEquipment()) != null && (EquipIsoGroupEnum.RE.equals((Object)(eqtypIsoGroup = ueEquipment.getEqEquipType().getEqtypIsoGroup())) || EquipIsoGroupEnum.RS.equals((Object)eqtypIsoGroup) || EquipIsoGroupEnum.RT.equals((Object)eqtypIsoGroup) || EquipIsoGroupEnum.HR.equals((Object)eqtypIsoGroup));
    }

    public boolean hasReeferMonitorTimes() {
        GoodsBase unitGoods = this.getUnitGoods();
        if (unitGoods == null || unitGoods.getGdsReeferRqmnts() == null) {
            return false;
        }
        ReeferRqmnts rqmnts = unitGoods.getGdsReeferRqmnts();
        return rqmnts.getRfreqTimeMonitor1() != null || rqmnts.getRfreqTimeMonitor2() != null || rqmnts.getRfreqTimeMonitor3() != null || rqmnts.getRfreqTimeMonitor4() != null;
    }

    public Boolean reeferNeedsMonitoringNow() {
        if (this.getUnitGoods() == null || this.getUnitGoods().getGdsReeferRqmnts() == null) {
            return false;
        }
        if (this.getUnitRequiresPower() == null || !this.getUnitRequiresPower().booleanValue()) {
            return false;
        }
        UnitFacilityVisit ufv = this.getUnitActiveUfv();
        if (ufv == null || !UfvTransitStateEnum.S40_YARD.equals((Object)ufv.getUfvTransitState())) {
            return false;
        }
        if (this.getUnitIsPowered() == null || !this.getUnitIsPowered().booleanValue() || this.getUnitWantPowered() == null || !this.getUnitWantPowered().booleanValue()) {
            return false;
        }
        Date rfrLastDate = this.getUnitLastReeferRecordDate();
        if (rfrLastDate == null) {
            rfrLastDate = this.getUnitPowerRqstTime();
        }
        if (rfrLastDate == null) {
            LOGGER.error((Object)(" Last record time and power request time of Unit[" + this.getUnitId() + "] is null but still it is considered " + "for monitoring as it is connected to power"));
            return true;
        }
        Date[] reeferMonitorTimes = this.getUnitGoods().getGdsReeferRqmnts().getReeferMonitorTimes();
        if (reeferMonitorTimes[0] == null) {
            return false;
        }
        TimeZone tz = ContextHelper.getThreadUserContext().getTimeZone();
        long now = Calendar.getInstance(tz).getTimeInMillis();
        Long slopTime = ufv.getUfvMonitorSlopTime();
        return Unit.getMonitorIndexOf(reeferMonitorTimes, rfrLastDate.getTime() + slopTime) < Unit.getMonitorIndexOf(reeferMonitorTimes, now);
    }

    private static int getMonitorIndexOf(Date[] inMonitorTimes, long inTimeToSet) {
        Date tempMonitorTime;
        int maxDefinedMonitor;
        int maxNumMonitorTimes = inMonitorTimes.length;
        Long[] monitorTimesInSecs = new Long[maxNumMonitorTimes + 2];
        for (maxDefinedMonitor = 1; maxDefinedMonitor <= maxNumMonitorTimes && (tempMonitorTime = inMonitorTimes[maxDefinedMonitor - 1]) != null; ++maxDefinedMonitor) {
            monitorTimesInSecs[maxDefinedMonitor] = tempMonitorTime.getTime();
        }
        monitorTimesInSecs[0] = monitorTimesInSecs[maxDefinedMonitor - 1] - 86400000L;
        monitorTimesInSecs[maxDefinedMonitor] = monitorTimesInSecs[1] + 86400000L;
        for (int i = 0; i < maxDefinedMonitor; ++i) {
            if (inTimeToSet >= monitorTimesInSecs[i]) continue;
            return i - 1;
        }
        return maxDefinedMonitor;
    }

    public boolean hasBeenUnbundled() {
        if (this.hasNoneInPayloadRole()) {
            IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
            return sm.hasEventTypeBeenRecorded((IEventType)EventEnum.UNIT_UNBUNDLE, (IServiceable)this);
        }
        return false;
    }

    public Boolean stopRoutingUpdate(Serializable inUnitGkey) {
        boolean stopRoutingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource propertySource = PropertySource.findPropertySource((String)"Unit", (Serializable)inUnitGkey, (PropertyGroupEnum)PropertyGroupEnum.ROUTING);
        DataSourceEnum existingDataSource = propertySource == null ? DataSourceEnum.UNKNOWN : propertySource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopRoutingUpdate = line.getLineopStopRtgUpdates() == null ? false : line.getLineopStopRtgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingDataSource) && stopRoutingUpdate;
    }

    public Boolean stopBookingRtgDetailsUpdate(Serializable inUnitGkey) {
        boolean stopBookingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource rtgSource = PropertySource.findPropertySource((String)"Unit", (Serializable)inUnitGkey, (PropertyGroupEnum)PropertyGroupEnum.ROUTING);
        DataSourceEnum existingRtgDataSource = rtgSource == null ? DataSourceEnum.UNKNOWN : rtgSource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopBookingUpdate = line.getLineopStopBkgUpdates() == null ? false : line.getLineopStopBkgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingRtgDataSource) && stopBookingUpdate;
    }

    public Boolean stopBookingCommodityDetailsUpdate(Serializable inUnitGkey) {
        boolean stopBookingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource contentsSource = PropertySource.findPropertySource((String)"GoodsBase", (Serializable)unit.getUnitGoods().getGdsGkey(), (PropertyGroupEnum)PropertyGroupEnum.COMMODITY);
        DataSourceEnum existingCmdtyDataSource = contentsSource == null ? DataSourceEnum.UNKNOWN : contentsSource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopBookingUpdate = line.getLineopStopBkgUpdates() == null ? false : line.getLineopStopBkgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingCmdtyDataSource) && stopBookingUpdate;
    }

    public Boolean stopBookingHazardsDetailsUpdate(Serializable inUnitGkey) {
        boolean stopBookingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource hzrdsSource = PropertySource.findPropertySource((String)"GoodsBase", (Serializable)unit.getUnitGoods().getGdsGkey(), (PropertyGroupEnum)PropertyGroupEnum.HAZARDS);
        DataSourceEnum existingHzrdsDataSource = hzrdsSource == null ? DataSourceEnum.UNKNOWN : hzrdsSource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopBookingUpdate = line.getLineopStopBkgUpdates() == null ? false : line.getLineopStopBkgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingHzrdsDataSource) && stopBookingUpdate;
    }

    public Boolean stopBookingOOGDetailsUpdate(Serializable inUnitGkey) {
        boolean stopBookingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource oogSource = PropertySource.findPropertySource((String)"Unit", (Serializable)inUnitGkey, (PropertyGroupEnum)PropertyGroupEnum.OOG);
        DataSourceEnum existingOogDataSource = oogSource == null ? DataSourceEnum.UNKNOWN : oogSource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopBookingUpdate = line.getLineopStopBkgUpdates() == null ? false : line.getLineopStopBkgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingOogDataSource) && stopBookingUpdate;
    }

    public Boolean stopBookingReeferDetailsUpdate(Serializable inUnitGkey) {
        boolean stopBookingUpdate = false;
        Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, inUnitGkey);
        PropertySource reeferSource = PropertySource.findPropertySource((String)"GoodsBase", (Serializable)unit.getUnitGoods().getGdsGkey(), (PropertyGroupEnum)PropertyGroupEnum.REEFER);
        DataSourceEnum existingReeferDataSource = reeferSource == null ? DataSourceEnum.UNKNOWN : reeferSource.getPrpsrcDataSource();
        ScopedBizUnit unitLineOperator = unit.getUnitLineOperator();
        if (unitLineOperator != null) {
            LineOperator line = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)unitLineOperator);
            stopBookingUpdate = line.getLineopStopBkgUpdates() == null ? false : line.getLineopStopBkgUpdates();
        }
        return DataSourceEnum.EDI_LDLT.equals((Object)existingReeferDataSource) && stopBookingUpdate;
    }

    public Object getUnitActivePayloadBundleVao() {
        ArrayList<ValueObject> activePayloadBundles = new ArrayList<ValueObject>();
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null) {
            for (Object anUeSet : ueSet) {
                UnitEquipment ue = (UnitEquipment)anUeSet;
                if (!EqUnitRoleEnum.PAYLOAD.equals((Object)ue.getUeEqRole())) continue;
                ValueObject valueObject = ue.getValueObject();
                valueObject.setFieldValue(InventoryCompoundField.UE_EQUIP_EQ_ID_FULL, (Object)ue.getUeEquipment().getEqIdFull());
                activePayloadBundles.add(valueObject);
                IValueHolder[] damageItems = (IValueHolder[])ue.getUeDamageVao();
                if (damageItems == null) continue;
                if (damageItems.length > 0) {
                    valueObject.setFieldValue(IInventoryBizMetafield.UNIT_DAMAGE_VAO, (Object)damageItems);
                    continue;
                }
                valueObject.setFieldValue(IInventoryBizMetafield.UE_DAMAGE_VAO, null);
            }
        }
        if (!activePayloadBundles.isEmpty()) {
           // return activePayloadBundles.toArray((T[])new ValueObject[activePayloadBundles.size()]);
        }
        return null;
    }

    public void setUnitActivePayloadBundleVao(Object inUnitActivePayloadBundleVao) throws BizViolation {
        IValueHolder[] unitActivePayloadBundleVao = (IValueHolder[])inUnitActivePayloadBundleVao;
        HashMap<String, UnitEquipment> bundledUeMap = new HashMap<String, UnitEquipment>();
        Set ueSet = this.getUnitUeSet();
        if (ueSet != null) {
            for (Object anUeSet : ueSet) {
                UnitEquipment unitEquipment = (UnitEquipment)anUeSet;
                if (!EqUnitRoleEnum.PAYLOAD.equals((Object)unitEquipment.getUeEqRole())) continue;
                bundledUeMap.put(unitEquipment.getUeEquipment().getEqIdFull(), unitEquipment);
            }
        }
        IUnitManager unitManager = (IUnitManager)Roastery.getBean((String)"unitManager");
        if (inUnitActivePayloadBundleVao != null) {
            for (IValueHolder valueHolder : unitActivePayloadBundleVao) {
                String vhEqId = (String)valueHolder.getFieldValue(InventoryCompoundField.UE_EQUIP_EQ_ID_FULL);
                IValueHolder[] damageItems = (IValueHolder[])valueHolder.getFieldValue(IInventoryBizMetafield.UE_DAMAGE_VAO);
                if (damageItems == null) {
                    damageItems = (IValueHolder[])valueHolder.getFieldValue(IInventoryBizMetafield.UNIT_DAMAGE_VAO);
                }
                if (bundledUeMap.containsKey(vhEqId)) {
                    ((UnitEquipment)bundledUeMap.get(vhEqId)).setUeDamageVao(damageItems);
                    bundledUeMap.remove(vhEqId);
                    continue;
                }
                unitManager.attachEquipment(this, vhEqId, EqUnitRoleEnum.PAYLOAD);
                Unit unitEquipment = this.getUnitEquipment(vhEqId);
                unitEquipment.setUeDamageVao(damageItems);
            }
            for (Map.Entry entry : bundledUeMap.entrySet()) {
                unitManager.unbundleUnitEquipment((UnitEquipment)entry.getValue());
            }
        } else {
            unitManager.unbundleAllPayLoads(this);
        }
    }

    public Boolean isPrimaryEqTank() {
        Equipment eq = this.getPrimaryEq();
        if (eq == null) {
            return false;
        }
        EquipType eqType = eq.getEqEquipType();
        return eqType.isTank();
    }

    public Set<UnitEquipment> getAllAttachedChassis() {
        HashSet<UnitEquipment> allAttachedChassis = new HashSet<UnitEquipment>();
        Set unitEquipments = this.getUnitUeSet();
        for (Object currentUe : unitEquipments) {
            if (!EqUnitRoleEnum.CARRIAGE.equals((Object)((UnitEquipment)currentUe).getUeEqRole())) continue;
            allAttachedChassis.add((UnitEquipment)currentUe);
        }
        return allAttachedChassis;
    }

    public void updateGradeID(EquipGrade inGradeID) {
        if (this.getUnitEquipment() == null) {
            return;
        }
        this.setUnitGradeID(inGradeID);
        this.updateEqsGradeID();
    }

    private void updateEqsGradeID() {
        if (!(this.getUnitEquipmentState() == null || this.getEqsGradeID() != null && this.getEqsGradeID().equals((Object)this.getUnitGradeID()))) {
            this.getUnitEquipmentState().updateGradeID(this.getUnitGradeID());
        }
    }

    public UnitNode getUnitNode() {
        return this._unitNode;
    }

    private void setNewlyCreatedUnit(boolean inNewlyCreatedUnit) {
        this._isNewlyCreatedUnit = inNewlyCreatedUnit;
    }

    public boolean isNewlyCreatedUnit() {
        return this._isNewlyCreatedUnit;
    }

    public static void updateUnitArePlacardsMismatch(final Map<Long, FieldChanges> inChanges) {
        PersistenceTemplate pt = new PersistenceTemplate(ContextHelper.getThreadUserContext());
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    if (inChanges != null && !inChanges.isEmpty()) {
                        for (Long aLong : inChanges.keySet()) {
                            FieldChanges changes;
                            Long entityGkey = aLong;
                            if (entityGkey == null || (changes = (FieldChanges)inChanges.get(entityGkey)) == null) continue;
                            Unit unit = (Unit)HibernateApi.getInstance().get(Unit.class, (Serializable)entityGkey);
                            if (unit == null) {
                                LOGGER.warn((Object)("Failed to load an entity of type: " + Unit.class.getName() + "with gkey: " + entityGkey));
                                continue;
                            }
                            Boolean newValue = null;
                            if (changes.getFieldChange(IInventoryField.UNIT_ARE_PLACARDS_MISMATCHED).getNewValue() == null) continue;
                            newValue = (Boolean)changes.getFieldChange(IInventoryField.UNIT_ARE_PLACARDS_MISMATCHED).getNewValue();
                            unit.setFieldValue(IInventoryField.UNIT_ARE_PLACARDS_MISMATCHED, newValue);
                        }
                    }
                }
                catch (Exception ex) {
                    LOGGER.error((Object)"Unable to set pin mismatch compatibility for Units");
                }
            }
        });
    }

    public void beforePropertySourcePurge() {
        Set ufvSet = this.getUnitUfvSet();
        if (ufvSet != null) {
            for (Object anUfvSet : ufvSet) {
                UnitFacilityVisit ufv = (UnitFacilityVisit)anUfvSet;
                PropertySource.purgePropertySources((DatabaseEntity)ufv);
            }
        }
        PropertySource.purgePropertySources((DatabaseEntity)this.getUnitGoods());
    }

    public Boolean isBreakBulkTruckMove(LocPosition inPos) {
        if (!FreightKindEnum.BBK.equals((Object)this.getUnitFreightKind())) {
            return false;
        }
        if (inPos == null || !LocTypeEnum.TRUCK.equals((Object)inPos.getPosLocType())) {
            return false;
        }
        return true;
    }

    public static Date getMostRecentArrivalTime(Complex inComplex, Equipment inEquipment) {
        Date inTime = null;
        Collection unitSet = Unit.getFndr().findAllUnitsUsingEq(inComplex, inEquipment);
        for (Object unit : unitSet) {
            Date unitInTime = ((Unit)unit).getComplexEntryTime();
            if (unitInTime == null || inTime != null && !unitInTime.after(inTime)) continue;
            inTime = unitInTime;
        }
        return inTime;
    }

    public Boolean isCoupledOperation() {
//        return this.isInternalChassis() && !ArgoXpsUtils.isDecoupledTruckOperation();
        return false;
    }

    public Object getUpdatedFieldValue(IMetafieldId inFieldId) {
        return this.getFieldValue(inFieldId);
    }

    public boolean isVgmRelatedField(IMetafieldId inFieldId) {
        return IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS_VALUES.equals((Object)inFieldId) || IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VGM_VALUES.equals((Object)inFieldId);
    }

    public IMetafieldId getOwingFieldId(IMetafieldId inFieldId) {
        if (IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS_VALUES.equals((Object)inFieldId)) {
            return UnitField.UNIT_GOODS_AND_CTR_WT_KG;
        }
        if (IInventoryBizMetafield.UNIT_GOODS_AND_CTR_WT_KG_VGM_VALUES.equals((Object)inFieldId)) {
            return UnitField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS;
        }
        return inFieldId;
    }

    static {
        TRANSITIONS_ADVISED.add(UnitVisitStateEnum.ACTIVE);
        TRANSITIONS_ADVISED.add(UnitVisitStateEnum.RETIRED);
        TRANSITIONS_ACTIVE.add(UnitVisitStateEnum.DEPARTED);
        TRANSITIONS_ACTIVE.add(UnitVisitStateEnum.RETIRED);
        TRANSITIONS_ACTIVE.add(UnitVisitStateEnum.ADVISED);
        UNIT_PROTECTED_FIELDS = new MetafieldIdList();
        UNIT_PROTECTED_FIELDS.add(IInventoryField.UNIT_OOG_BACK_CM);
        UNIT_PROTECTED_FIELDS.add(IInventoryField.UNIT_OOG_FRONT_CM);
        UNIT_PROTECTED_FIELDS.add(IInventoryField.UNIT_OOG_LEFT_CM);
        UNIT_PROTECTED_FIELDS.add(IInventoryField.UNIT_OOG_RIGHT_CM);
        UNIT_PROTECTED_FIELDS.add(IInventoryField.UNIT_OOG_TOP_CM);
    }

}
