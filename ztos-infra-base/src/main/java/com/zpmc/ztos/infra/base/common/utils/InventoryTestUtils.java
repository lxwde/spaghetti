package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.XpeSharedParametersDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.ServerModeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.TwinWithEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.WqTypeEnum;
import com.zpmc.ztos.infra.base.business.equipments.Chassis;
import com.zpmc.ztos.infra.base.business.equipments.ChePool;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.plans.WorkQueue;
import com.zpmc.ztos.infra.base.business.plans.WorkShift;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.SharedParameters;
import com.zpmc.ztos.infra.base.business.xps.XpeSharedParametersParameters;
import com.zpmc.ztos.infra.base.business.xps.XpsDbManagerId;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.DefaultServerConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class InventoryTestUtils {
    private static final Logger LOGGER = Logger.getLogger(InventoryTestUtils.class);

    private InventoryTestUtils() {
    }

    public static WorkShift findOrCreateWorkShift(String inName, PointOfWork inPow, Date inStartTime, long inDuration) {
        Yard yard = ContextHelper.getThreadYard();
        assert (yard != null);
        Long yrdGkey = yard.getYrdGkey();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkShift").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_OWNER_POW, (Object)inPow.getPointofworkGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_SHIFT_NAME, (Object)inName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_YARD, (Object)yrdGkey));
        List shiftList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        WorkShift shift = shiftList == null || shiftList.isEmpty() ? InventoryTestUtils.createWorkShift(inName, inPow, yard) : (WorkShift)shiftList.get(0);
        shift.setWorkshiftStartTime(inStartTime != null ? inStartTime : new Date());
        shift.setWorkshiftDuration(Long.valueOf(inDuration));
        HibernateApi.getInstance().update((Object)shift);
        return shift;
    }

    private static WorkShift createWorkShift(String inName, PointOfWork inPow, Yard inYard) {
        WorkShift shift = new WorkShift();
        shift.setWorkshiftShiftName(inName);
        shift.setWorkshiftYard(inYard);
        shift.updateWorkshiftOwnerPow(inPow);
        shift.setWorkshiftPkey(InventoryTestUtils.getWorkShiftPkey(inYard.getYrdGkey()));
        HibernateApi.getInstance().save((Object)shift);
        return shift;
    }

    private static Long getWorkShiftPkey(Long inYardKey) {
        return XpsPkeyGenerator.generate((int)25, (String)"WorkShift", (IMetafieldId) IArgoField.WORKSHIFT_PKEY, (IMetafieldId) IArgoField.WORKSHIFT_YARD, (Serializable)inYardKey);
    }

    public static PointOfWork findOrCreatePointOfWork(String inPowName, ChePool inPool) {
        Yard yard = ContextHelper.getThreadYard();
        assert (yard != null);
        Long yrdGkey = yard.getYrdGkey();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PointOfWork").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_NAME, (Object)inPowName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_YARD, (Object)yrdGkey));
        List powList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        PointOfWork pow = powList == null || powList.isEmpty() ? InventoryTestUtils.createPointOfWork(inPowName, yard) : (PointOfWork)powList.get(0);
        if (inPool != null) {
            pow.updatePointofworkOwnerPool(inPool);
        }
        HibernateApi.getInstance().update((Object)pow);
        return pow;
    }

    private static PointOfWork createPointOfWork(String inPowName, Yard inYard) {
        PointOfWork pow = new PointOfWork();
        pow.setPointofworkYard(ContextHelper.getThreadYard());
        pow.setPointofworkName(inPowName);
        pow.setPointofworkPkey(InventoryTestUtils.getPointOfWorkPkey(inYard.getYrdGkey()));
        HibernateApi.getInstance().save((Object)pow);
        return pow;
    }

    private static Long getPointOfWorkPkey(Long inYrdGkey) {
        return XpsPkeyGenerator.generate((int)10, (String)"PointOfWork", (IMetafieldId) IArgoField.POINTOFWORK_PKEY, (IMetafieldId) IArgoField.POINTOFWORK_YARD, (Serializable)inYrdGkey);
    }

    public static UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage) throws BizViolation {
        Container ctr = InventoryTestUtils.findOrCreateTestContainer(inEqId, "2200");
        return InventoryTestUtils.makeUfv((Equipment)ctr, inStage);
    }

    public static UnitFacilityVisit makeUfv(Equipment inEquipment, UfvTransitStateEnum inStage) throws BizViolation {
        Complex complex = ContextHelper.getThreadComplex();
        CarrierVisit ibCv = CarrierVisit.getGenericTruckVisit((Complex)complex);
        CarrierVisit obCv = CarrierVisit.getGenericVesselVisit((Complex)complex);
        UnitFacilityVisit ufv = InventoryTestUtils.innerMakeUfv(inEquipment, ibCv, obCv, UnitCategoryEnum.STORAGE);
        RectifyParms parms = new RectifyParms();
        parms.setUfvTransitState(inStage);
        parms.setIbCv(ibCv);
        parms.setObCv(obCv);
        parms.setSlot(null);
        parms.setEraseHistory(false);
        InventoryTestUtils.getUnitManager().rectifyUfv(ufv, parms);
        return ufv;
    }

    public static UnitFacilityVisit makeUfv(String inEqId, String inEqTypeId, UfvTransitStateEnum inStage) throws BizViolation {
        Container ctr = InventoryTestUtils.findOrCreateTestContainer(inEqId, inEqTypeId);
        return InventoryTestUtils.makeUfv((Equipment)ctr, inStage);
    }

    public static UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inCategory, "4200");
    }

    public static UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory, String inEqTypId) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inCategory, inEqTypId, CarrierVisit.getGenericTruckVisit((Complex) ContextHelper.getThreadComplex()), CarrierVisit.getGenericVesselVisit((Complex) ContextHelper.getThreadComplex()));
    }

    public static UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory, String inEqTypId, CarrierVisit inIbCv, CarrierVisit inObCv) throws BizViolation {
        UnitFacilityVisit ufv = InventoryTestUtils.innerMakeUfv(inEqId, inEqTypId, inIbCv, inObCv, inCategory);
        RectifyParms parms = new RectifyParms();
        parms.setUfvTransitState(inStage);
        parms.setIbCv(inIbCv);
        parms.setObCv(inObCv);
        parms.setSlot(null);
        parms.setEraseHistory(false);
        InventoryTestUtils.getUnitManager().rectifyUfv(ufv, parms);
        return ufv;
    }

    public static UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, CarrierVisit inIbCv, CarrierVisit inObCv, String inSlot) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, "4200", inStage, inIbCv, inObCv, inSlot);
    }

    public static UnitFacilityVisit makeUfv(String inEqId, String inEqTypeId, UfvTransitStateEnum inStage, CarrierVisit inIbCv, CarrierVisit inObCv, String inSlot) throws BizViolation {
        Yard yard;
        UnitCategoryEnum category = LocTypeEnum.VESSEL.equals((Object)inIbCv.getCvCarrierMode()) ? (LocTypeEnum.VESSEL.equals((Object)inObCv.getCvCarrierMode()) ? UnitCategoryEnum.TRANSSHIP : UnitCategoryEnum.IMPORT) : (LocTypeEnum.VESSEL.equals((Object)inObCv.getCvCarrierMode()) ? UnitCategoryEnum.EXPORT : UnitCategoryEnum.STORAGE);
        UnitFacilityVisit ufv = InventoryTestUtils.innerMakeUfv(inEqId, inEqTypeId, inIbCv, inObCv, category);
        if (UfvTransitStateEnum.S40_YARD.equals((Object)inStage) && ((yard = ContextHelper.getThreadYard()) == null || !yard.isValidSlotName(inSlot))) {
            inSlot = null;
        }
        RectifyParms parms = new RectifyParms();
        parms.setUfvTransitState(inStage);
        parms.setIbCv(inIbCv);
        parms.setObCv(inObCv);
        parms.setSlot(inSlot);
        parms.setEraseHistory(false);
        InventoryTestUtils.getUnitManager().rectifyUfv(ufv, parms);
        return ufv;
    }

    private static UnitFacilityVisit innerMakeUfv(String inEqId, String inEqTypeId, CarrierVisit inIbCv, CarrierVisit inObCv, UnitCategoryEnum inCategory) throws BizViolation {
        Container ctr = InventoryTestUtils.findOrCreateTestContainer(inEqId, inEqTypeId);
        return InventoryTestUtils.innerMakeUfv((Equipment)ctr, inIbCv, inObCv, inCategory);
    }

    private static UnitFacilityVisit innerMakeUfv(Equipment inEquipment, CarrierVisit inIbCv, CarrierVisit inObCv, UnitCategoryEnum inCategory) throws BizViolation {
        Yard yard = ContextHelper.getThreadYard();
        Facility facility = yard.getYrdFacility();
        Complex complex = facility.getFcyComplex();
        Unit unit = InventoryTestUtils.getUnitFinder().findActiveUnit(complex, inEquipment, inCategory);
        if (unit != null) {
            throw new IllegalArgumentException("active Unit for " + inEquipment.getEqIdFull() + " already exists: " + unit);
        }
        UnitFacilityVisit ufv = InventoryTestUtils.getUnitManager().createContainerizedUnit(null, facility, inIbCv, inObCv, inEquipment, null, null, null);
        unit = ufv.getUfvUnit();
        unit.setUnitCategory(inCategory);
        return ufv;
    }

    public static Container findOrCreateTestContainer(String inCtrNbr, String inEqTypeId) throws BizViolation {
        return Container.findOrCreateContainer((String)inCtrNbr, (String)inEqTypeId, (DataSourceEnum) DataSourceEnum.TESTING);
    }

    public static Container findOrCreateTestContainer(String inCtrNbr, String inEqTypeId, Long inEquipmentOperator) throws BizViolation {
        Container ctr = Container.findOrCreateContainer((String)inCtrNbr, (String)inEqTypeId, (DataSourceEnum) DataSourceEnum.TESTING);
        ctr.setFieldValue(IArgoBizMetafield.EQUIPMENT_OPERATOR, (Object)inEquipmentOperator);
        InventoryTestUtils.getHibernateApi().saveOrUpdate((Object)ctr);
        return ctr;
    }

    @Nullable
    public static UnitFacilityVisit findOrCreateUfvIncomingByVessel(Container inCtr, CarrierVisit inVesselVisit, String inSlot, String inDoorDirection) throws BizViolation {
        UnitFacilityVisit ufv = InventoryTestUtils.getUnitManager().findOrCreateStowplanUnit((Equipment)inCtr, inVesselVisit, null, null);
        if (ufv != null) {
            LocPosition stowPosition = LocPosition.createVesselPosition((ILocation)inVesselVisit, (String)inSlot, (String)inDoorDirection);
            ufv.setUfvArrivePosition(stowPosition);
            ufv.setUfvLastKnownPosition(stowPosition);
        }
        return ufv;
    }

    public static CarrierItinerary findOrCreateItinerary(String inId, String[] inUnLocs) {
        CarrierItinerary itinerary = CarrierItinerary.findCarrierItinerary((String)inId);
        if (itinerary == null) {
            itinerary = CarrierItinerary.createCarrierItinerary((String)inId, null);
            for (String unLoc : inUnLocs) {
                String portCode = unLoc.substring(2);
                RoutingPoint p = RoutingPoint.findOrCreateRoutingPoint((String)portCode, (String)unLoc);
                itinerary.addCarrierCall(p.getPointId(), "1", "0", new String[]{p.getPointId(), p.getPointId()});
            }
        }
        return itinerary;
    }

    public static CarrierService findOrCreateVesselService(String inServiceId, String inServiceName, String[] inUnLocs) {
        CarrierService service = CarrierService.findCarrierService((String)inServiceId);
        if (service == null) {
            CarrierItinerary itinerary = InventoryTestUtils.findOrCreateItinerary(inServiceId, inUnLocs);
            service = CarrierService.createCarrierService((String)inServiceId, (String)inServiceName, (LocTypeEnum) LocTypeEnum.VESSEL, (CarrierItinerary)itinerary);
        }
        return service;
    }

    public static void purgeUnitsByEqId(String inEqId, Complex inComplex) {
        Equipment eq = Equipment.findEquipment((String)inEqId);
        if (eq != null) {
            InventoryTestUtils.purgeUnits(eq, inComplex);
        }
    }

    public static void purgeUnitsByEqId(Complex inComplex, String... inEqIds) {
        for (String eqId : inEqIds) {
            InventoryTestUtils.purgeUnitsByEqId(eqId, inComplex);
        }
    }

    public static void purgeUnitsByEqId(String inEqId) {
        InventoryTestUtils.purgeUnitsByEqId(inEqId, ContextHelper.getThreadComplex());
    }

    public static void purgeUnits(Equipment inEq, Complex inComplex) {
        Collection units = InventoryTestUtils.getUnitFinder().findAllUnitsUsingEq(inComplex, inEq);
        for (Object unit : units) {
            InventoryTestUtils.getUnitManager().purgeUnit((Unit)unit);
        }
    }

    public static void purgeHistoryForUnit(String inUnitId) {
        IDomainQuery delEcEventsByUnitId = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.ECEVENT_UNIT_ID_NAME, (Object)inUnitId));
        HibernateApi.getInstance().deleteByDomainQuery(delEcEventsByUnitId);
    }

    public static void purgeHistoryForUnitAndRef(String inUnitId, @Nullable String inUnitRef) {
        InventoryTestUtils.purgeHistoryForUnit(inUnitId);
        InventoryTestUtils.purgeHistoryForUnitRef(inUnitRef);
    }

    public static void purgeHistoryForUnitRef(String inUnitRef) {
        if (inUnitRef != null) {
            IDomainQuery delEcEventsByUnitRef = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.ECEVENT_UNIT_REFERENCE, (Object)inUnitRef));
            HibernateApi.getInstance().deleteByDomainQuery(delEcEventsByUnitRef);
        }
    }

    public static void purgeEquipment(String inEqId) {
        Equipment eq = Equipment.findEquipment((String)inEqId);
        if (eq != null) {
            boolean saveState = InventoryTestUtils.getHibernateApi().getObsoletableDeleteEnabled();
            InventoryTestUtils.getHibernateApi().setObsoletableDeleteEnabled(false);
            InventoryTestUtils.getHibernateApi().delete((Object)eq);
            InventoryTestUtils.getHibernateApi().setObsoletableDeleteEnabled(saveState);
        }
    }

    public static void purgeEqType(String inEqTypeId) {
        EquipType eqType = EquipType.findEquipType((String)inEqTypeId);
        if (eqType != null) {
            boolean saveState = InventoryTestUtils.getHibernateApi().getObsoletableDeleteEnabled();
            InventoryTestUtils.getHibernateApi().setObsoletableDeleteEnabled(false);
            InventoryTestUtils.getHibernateApi().delete((Object)eqType);
            InventoryTestUtils.getHibernateApi().setObsoletableDeleteEnabled(saveState);
        }
    }

    public static Unit findOrCreatePreadvisedRoadUnit(CarrierVisit inTruckVisit, Equipment inPrimaryEq, String inPosOnTruck, String inDoorDirection, ScopedBizUnit inLineOp) throws BizViolation {
        Facility facility = ContextHelper.getThreadFacility();
        assert (facility != null);
        Complex complex = facility.getFcyComplex();
        CarrierVisit departCv = CarrierVisit.getGenericCarrierVisit((Complex)complex);
        IUnitManager um = (IUnitManager) Roastery.getBean((String)"unitManager");
        UnitFacilityVisit ufv = um.findOrCreatePreadvisedUnit(facility, inPrimaryEq.getEqIdFull(), inPrimaryEq.getEqEquipType(), UnitCategoryEnum.EXPORT, FreightKindEnum.FCL, inLineOp, inTruckVisit, departCv, DataSourceEnum.TESTING, null);
        return ufv.getUfvUnit();
    }

    public static Unit findOrCreatePreadvisedRoadUnit(Equipment inPrimaryEq, ScopedBizUnit inLineOp) throws BizViolation {
        Facility facility = ContextHelper.getThreadFacility();
        assert (facility != null);
        Complex complex = facility.getFcyComplex();
        CarrierVisit arriveCv = CarrierVisit.getGenericTruckVisit((Complex)complex);
        CarrierVisit departCv = CarrierVisit.getGenericCarrierVisit((Complex)complex);
        IUnitManager um = (IUnitManager) Roastery.getBean((String)"unitManager");
        UnitFacilityVisit ufv = um.findOrCreatePreadvisedUnit(facility, inPrimaryEq.getEqIdFull(), inPrimaryEq.getEqEquipType(), UnitCategoryEnum.EXPORT, FreightKindEnum.FCL, inLineOp, arriveCv, departCv, DataSourceEnum.TESTING, null);
        return ufv.getUfvUnit();
    }

    public static Unit findOrCreateIncomingRoadUnit(CarrierVisit inSomeTruck, Equipment inCtr) throws BizViolation {
        Facility facility = ContextHelper.getThreadFacility();
        UnitFacilityVisit ufv = InventoryTestUtils.getUnitManager().findOrCreateIncomingRoadUnit(facility, inSomeTruck, inCtr);
        return ufv.getUfvUnit();
    }

    @Nullable
    public static Unit findUnitByDeclaredIbCarrier(Equipment inPrimaryEq, CarrierVisit inIbCarrier) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Unit").addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_DECLARED_IB_CV, (Object)inIbCarrier.getCvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_PRIMARY_EQ, (Object)inPrimaryEq.getEqGkey())).addDqOrdering(Ordering.asc((IMetafieldId) UnitField.UNIT_VISIT_STATE));
        List matches = InventoryTestUtils.getHibernateApi().findEntitiesByDomainQuery(dq);
        return !matches.isEmpty() ? (Unit)matches.get(0) : null;
    }

    public static Unit createYardUnit(Equipment inPrimaryEq, Yard inYard, String inYardSlot, CarrierVisit inIbCv, CarrierVisit inObCv) throws BizViolation {
        Facility facility = inYard.getYrdFacility();
        Complex complex = facility.getFcyComplex();
        Unit unit = InventoryTestUtils.getUnitFinder().findActiveUnit(complex, inPrimaryEq);
        if (unit != null) {
            throw new IllegalArgumentException("active Unit for " + (Object)inPrimaryEq + " already exists: " + unit);
        }
        if (!inYard.isValidSlotName(inYardSlot)) {
            inYardSlot = null;
        }
        UnitFacilityVisit ufv = InventoryTestUtils.getUnitManager().createYardBornUnit(facility, inPrimaryEq, inYardSlot, "test");
        ufv.updateArrivePosition(LocPosition.createLocPosition((ILocation)inIbCv, null, null));
        ufv.updateObCv(inObCv);
        return ufv.getUfvUnit();
    }

    public static UnitFacilityVisit createInYardUfv(Equipment inPrimaryEq, @NotNull String inSlot) throws BizViolation {
        CarrierVisit someTruck = CarrierVisit.getGenericTruckVisit((Complex) ContextHelper.getThreadComplex());
        CarrierVisit someVessel = CarrierVisit.getGenericVesselVisit((Complex) ContextHelper.getThreadComplex());
        Unit unit = InventoryTestUtils.createYardUnit(inPrimaryEq, ContextHelper.getThreadYard(), inSlot, someTruck, someVessel);
        return unit.getUfvForFacilityLiveOnly(ContextHelper.getThreadFacility());
    }

    @Nullable
    public static Chassis createChassis(String inChassisId, String inEquipTypeId) {
        Chassis chassis = null;
        try {
            chassis = Chassis.findOrCreateChassis((String)inChassisId, (String)inEquipTypeId, (DataSourceEnum) DataSourceEnum.UNKNOWN);
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)("creation of Chassis " + inChassisId + " failed with error" + (Object)((Object)inBizViolation)));
        }
        return chassis;
    }

    @Nullable
    public static Container createContainer(String inContainerId, String inContainerEquipType) {
        Container container = null;
        try {
            container = Container.findOrCreateContainer((String)inContainerId, (String)inContainerEquipType, (DataSourceEnum) DataSourceEnum.UNKNOWN);
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)("creation of Container " + inContainerId + " failed!"));
        }
        return container;
    }

    @NotNull
    public static WorkQueue findOrCreateWorkQueue(String inWqName, WqTypeEnum inWqTypeEnum) {
        return InventoryTestUtils.findOrCreateWorkQueue(inWqName, inWqTypeEnum, true);
    }

    @NotNull
    public static WorkQueue findOrCreateWorkQueue(String inWqName, WqTypeEnum inWqTypeEnum, boolean inIsActive) {
        XpsDbManagerId.registerEntity(WorkQueue.class, (int)1);
        WorkQueue wq = null;
        wq = WorkQueue.findOrCreateWorkQueue(ContextHelper.getThreadYardKey(), inWqName);
        wq.updateWqType(inWqTypeEnum);
        wq.setActive(inIsActive);
        return wq;
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, WorkQueue inWq, WiMoveKindEnum inMoveKind, WiMoveStageEnum inMoveStage) {
        return InventoryTestUtils.createWorkInstruction(inUyv, inWq, inMoveKind, inMoveStage, (String)null);
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, WorkQueue inWq, WiMoveKindEnum inMoveKind, WiMoveStageEnum inMoveStage, @Nullable String inSlotName) {
        try {
            LocPosition position = LocPosition.createYardPosition((Yard)inUyv.getUyvYard(), (String)inSlotName, null, (EquipBasicLengthEnum)inUyv.getUyvUfv().getBasicLength(), (boolean)true);
            return InventoryTestUtils.createWorkInstruction(inUyv, inWq, inMoveKind, inMoveStage, position);
        }
        catch (BizViolation inViolation) {
            throw BizFailure.wrap((Throwable)inViolation);
        }
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, WorkQueue inWq, WiMoveKindEnum inMoveKind, WiMoveStageEnum inMoveStage, @Nullable LocPosition inPosition) {
        Double moveNumber = 1.0;
        WorkInstruction nextWi = inUyv.getNextWorkInstruction();
        if (nextWi != null && inPosition != null && inPosition.isCarrierPosition()) {
            moveNumber = nextWi.calculateNewWiMoveNumberNext();
        }
        return InventoryTestUtils.createWorkInstruction(inUyv, inWq, inMoveKind, inMoveStage, inPosition, moveNumber);
    }

    public static WorkInstruction createWorkInstruction(UnitYardVisit inUyv, WorkQueue inWq, WiMoveKindEnum inMoveKind, WiMoveStageEnum inMoveStage, @Nullable LocPosition inPosition, Double inMoveNumber) {
        XpsDbManagerId.registerEntity(WorkInstruction.class, (int)32);
        LocPosition pos = inPosition == null ? inUyv.getUyvUfv().getUfvLastKnownPosition() : inPosition;
        WorkInstruction wi = WorkInstruction.createWorkInstruction(inUyv, inMoveNumber, inWq, inWq.getNextSequenceNbrForNewWi(), pos);
        wi.setFieldValue(IMovesField.WI_MOVE_KIND, (Object)inMoveKind);
        wi.setFieldValue(IMovesField.WI_MOVE_STAGE, (Object)inMoveStage);
        if (inPosition != null && inPosition.isCarrierPosition()) {
            wi.setFieldValue(IMovesField.WI_CARRIER_LOC_TYPE, (Object)inPosition.getPosLocType());
            wi.setFieldValue(IMovesField.WI_CARRIER_LOC_ID, inPosition.getPosLocId());
        }
        InventoryTestUtils.getHibernateApi().save((Object)wi);
        InventoryTestUtils.getHibernateApi().flush();
        return wi;
    }

    public static WorkInstruction getWorkInstructionByUnitAndMoveNum(String inUnitId, Double inMoveNumber) {
        try {
            WorkInstruction workInstruction = null;
            Complex cpx = ContextHelper.getThreadComplex();
            Equipment equipment = Equipment.findEquipment((String)inUnitId);
            Unit unit = InventoryTestUtils.getUnitFinder().findActiveUnit(cpx, equipment);
            UnitYardVisit uyv = Unit.getUyvForUnit(cpx.getCpxGkey(), ContextHelper.getThreadFacilityKey(), ContextHelper.getThreadYardKey(), unit);
            if (uyv != null) {
                IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId) IMovesField.WI_UYV, (Object)uyv.getUyvGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IMovesField.WI_MOVE_NUMBER, (Object)inMoveNumber));
                List wiList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
                if (wiList != null && !wiList.isEmpty()) {
                    workInstruction = (WorkInstruction)wiList.get(0);
                }
            }
            return workInstruction;
        }
        catch (Exception inEx) {
            throw BizFailure.create((String)"No WorkInstruction found for given Unit and Move Number");
        }
    }

    public static String getTestSettingsForCheWeightLimits() {
        String xmlWeightLimitsString = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n\n<!--\n  ~ Copyright (c) 2014 Navis LLC. All Rights Reserved.\n  ~\n  -->\n\n<cheWeightLimitEntries xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xsi:schemaLocation=\"schemas/che-weight-limits.xsd\">\n    <cheWeightLimitEntry cheKind=\"QC\">\n        <handling name=\"LIFT\">\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"30000\"/>\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"65000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"65000\"/>\n        </handling>\n    </cheWeightLimitEntry>\n    <cheWeightLimitEntry cheKind=\"ASC\">\n        <handling name=\"LIFT\">\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"65000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"30000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"65000\"/>\n        </handling>\n    </cheWeightLimitEntry>\n    <cheWeightLimitEntry cheKind=\"AGV\">\n        <handling name=\"CARRY\">\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"65000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"32500\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"65000\"/>\n        </handling>\n        <handling name=\"LIFT\">\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"60000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"30000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"34000\"/>\n        </handling>\n    </cheWeightLimitEntry>\n    <cheWeightLimitEntry cheKind=\"ASH\">\n        <handling name=\"CARRY\">\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"45000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"32500\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"34000\"/>\n        </handling>\n        <handling name=\"LIFT\">\n            <mode name=\"TWIN\" lengthFt=\"20\" capacityKg=\"45000\"/>\n            <mode name=\"SINGLE\" lengthFt=\"20\" capacityKg=\"32500\"/>\n            <mode name=\"SINGLE\" lengthFt=\"40\" capacityKg=\"34000\"/>\n        </handling>\n    </cheWeightLimitEntry>\n</cheWeightLimitEntries>";
        return xmlWeightLimitsString;
    }

    @Nullable
    public static String setTestCheWeightLimitsSettingForTest(@Nullable String inXmlString) {
        IConfigProvider cp = (IConfigProvider) Roastery.getBean((String)"configProvider");
        UserContext userContext = ContextHelper.getThreadUserContext();
        ScopeCoordinates scopeCoordinates = userContext.getScopeCoordinate();
        long scope = scopeCoordinates.getMaxScopeLevel();
        Serializable scopeGkey = scopeCoordinates.getScopeLevelCoord((int)scope);
        Object existingLimits = cp.getEffectiveSetting(userContext, (IConfig) ArgoConfig.CHE_WEIGHT_LIMITS);
        cp.persistConfigSetting(userContext, (IConfig)ArgoConfig.CHE_WEIGHT_LIMITS, (Object)inXmlString, Long.valueOf(scope), scopeGkey);
        return existingLimits != null ? existingLimits.toString() : null;
    }

    @Nullable
    public static String createOrUpdateEcParameter(@NotNull String inParamId, String inParamValue) {
        HashMap<String, String> paramParams = (HashMap<String, String>) ArgoXpsUtils.getEcParams();
        if (paramParams == null) {
            paramParams = new HashMap<String, String>();
        }
        String existingValue = (String)paramParams.get(inParamId);
        paramParams.put(inParamId, inParamValue);
        InventoryTestUtils.createOrUpdateSharedParameters("ECPRM1", paramParams);
        return existingValue;
    }

    public static void createOrUpdateSharedParameters(String inParamId, Map<String, String> inParamMap) {
        XpeSharedParametersDO sharedParameters = InventoryTestUtils.findOrCreateSharedParameters(inParamId);
        ArrayList<XpeSharedParametersParameters> params = new ArrayList<XpeSharedParametersParameters>();
        for (Map.Entry<String, String> entry : inParamMap.entrySet()) {
            params.add(InventoryTestUtils.createSharedParametersParam(entry.getKey()));
            params.add(InventoryTestUtils.createSharedParametersParam(entry.getValue()));
        }
        sharedParameters.setSharedparametersParameters(params);
        Roastery.getHibernateApi().update((Object)sharedParameters);
    }

    public static XpeSharedParametersDO findOrCreateSharedParameters(String inParamId) {
        XpeSharedParametersDO parameters = null;
        Serializable sharedParamGkey = SharedParameters.findSharedParametersForParamName((String)inParamId, (String) SharedParameters.getDefaultStrategyName(), (Serializable) ContextHelper.getThreadYardKey());
        if (sharedParamGkey != null) {
            parameters = (XpeSharedParametersDO) Roastery.getHibernateApi().load(XpeSharedParametersDO.class, sharedParamGkey);
        } else {
            parameters = new XpeSharedParametersDO();
            parameters.setSharedparametersId(inParamId);
            parameters.setSharedparametersName("");
            parameters.setSharedparametersYard(ContextHelper.getThreadYard());
            parameters.setSharedparametersPkey(XpsPkeyGenerator.generate((int)45, (String)"XpeSharedParametersDO", (IMetafieldId) IXpsField.SHAREDPARAMETERS_PKEY, (IMetafieldId) IXpsField.SHAREDPARAMETERS_YARD, (Serializable) ContextHelper.getThreadYardKey()));
            Roastery.getHibernateApi().save((Object)parameters);
        }
        return parameters;
    }

    private static XpeSharedParametersParameters createSharedParametersParam(String inParam) {
        XpeSharedParametersParameters param = new XpeSharedParametersParameters();
        param.setValue(inParam);
        return param;
    }

    public static void twinUpPlans(@NotNull WorkInstruction inWi1, @NotNull WorkInstruction inWi2) {
        LOGGER.info((Object)("twinUpPlans(" + inWi1.getWiUfv().getPrimaryEqId() + ", " + inWi2.getWiUfv().getPrimaryEqId() + ")"));
//        Assert.assertNotSame((Object)inWi1, (Object)inWi2);
//        Assert.assertEquals((String)"Work Instructions can be twinned only from SAME work queue", (Object)inWi1.getWiWorkQueue(), (Object)inWi2.getWiWorkQueue());
//        Assert.assertEquals((String)"WI with immediate Q-sequence numbers can only be twinned", (long)1L, (long)(inWi2.getWiSequence() - inWi1.getWiSequence()));
        inWi1.updateWiTwinWith(TwinWithEnum.NEXT);
        inWi2.updateWiTwinWith(TwinWithEnum.PREV);
        HibernateApi.getInstance().update((Object)inWi1);
        HibernateApi.getInstance().update((Object)inWi2);
        HibernateApi.getInstance().flush();
//        Assert.assertEquals((Object)inWi2, (Object)inWi1.getTwinCompanion(true));
//        Assert.assertEquals((Object)inWi1, (Object)inWi2.getTwinCompanion(true));
    }

    public static void loadSystemProvidedCodeExtensions() {
        ILifecycleService extensionLifecycleService = (ILifecycleService) PortalApplicationContext.getBean((String)"codeExtensionLifecycleService");
        if (!extensionLifecycleService.hasInitialized()) {
            extensionLifecycleService.initialize((IServerConfig)new DefaultServerConfig(ServerModeEnum.STANDALONE_FACELESS));
            extensionLifecycleService.setInitialized(true);
        }
    }

    public static void findOrCreateBombcart() {
        EquipType bombcartEquipmentType = EquipType.findOrCreateEquipType((String)"40CB");
        bombcartEquipmentType.updateChsSlotLabel("1,2,3,4,5");
        HibernateApi.getInstance().update((Object)bombcartEquipmentType);
    }

    public static void findOrCreateCasette() {
        EquipType cassetteEquipmentType = EquipType.findOrCreateEquipType((String)"40CT");
        cassetteEquipmentType.updateChsSlotLabel("1,2,3,4,5");
        HibernateApi.getInstance().update((Object)cassetteEquipmentType);
    }

    public static void setupTestDeliveryGateMove(WorkInstruction inWi, Long inTvGkey, Long inTranGkey, LocPosition inPlannedPos, Boolean inSwappableDelivery, WiMoveKindEnum inMoveKind) {
        inWi.updateWiTvGkey(inTvGkey);
        inWi.updateWiTranGkey(inTranGkey);
        inWi.updateWiPosition(inPlannedPos);
        inWi.updateWiIsSwappableDelivery(inSwappableDelivery);
        inWi.updateWiMoveKind(inMoveKind);
    }

    private static IUnitManager getUnitManager() {
        return (IUnitManager) Roastery.getBean((String)"unitManager");
    }

    private static IUnitFinder getUnitFinder() {
        return (IUnitFinder) Roastery.getBean((String)"unitFinder");
    }

    private static HibernateApi getHibernateApi() {
        return HibernateApi.getInstance();
    }

}
