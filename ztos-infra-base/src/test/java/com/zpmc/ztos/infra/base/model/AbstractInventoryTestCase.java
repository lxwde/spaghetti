package com.zpmc.ztos.infra.base.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.service.ServiceRuleTypeEnum;
import com.zpmc.ztos.infra.base.business.equipments.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.plans.WorkQueue;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsDbManagerId;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.callbacks.CrudDelegate;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.security.*;
import com.zpmc.ztos.infra.base.common.systems.HoldPermissionView;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.InventoryTestUtils;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.FlushMode;
import org.junit.Assert;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class AbstractInventoryTestCase extends BaseArgoTestCase{
    public static final String YARD_IN = "YARD_IN";
    public static final String YARD_OUT = "YARD_OUT";
    private static final String FLEX_FIELD_KEY = "id";
    private static final String FLEX_FIELD_VALUE = "value";
    private IServicesManager _sm = (IServicesManager) Roastery.getBean((String)"servicesManager");
    private static final String INVENTORY_SERVICE_URL = "http://localhost:" + BC.getPort() + "/apex/services/inventoryservice";
    private static final Logger LOGGER = Logger.getLogger(AbstractInventoryTestCase.class);

    public AbstractInventoryTestCase(String inS) {
        super(inS);
        XpsDbManagerId.registerEntity(WorkInstruction.class, (int)32);
        XpsDbManagerId.registerEntity(Che.class, (int)11);
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.startHibernateAtCpx11();
        this.setSetting((IConfig) InventoryConfig.SECURITY_ENFORCE_EQ_STATE_MODIFY, Boolean.FALSE);
        this.endHibernate();
    }

    public IUnitManager getUnitManager() {
        return (IUnitManager) Roastery.getBean((String)"unitManager");
    }

    public UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage);
    }

    public UnitFacilityVisit makeUfv(Equipment inEquipment, UfvTransitStateEnum inStage) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEquipment, inStage);
    }

    public UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inCategory);
    }

    public UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory, String inEqTypId) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inCategory, inEqTypId);
    }

    public UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, UnitCategoryEnum inCategory, String inEqTypId, CarrierVisit inIbCv, CarrierVisit inObCv) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inCategory, inEqTypId, inIbCv, inObCv);
    }

    public UnitFacilityVisit makeUfv(String inEqId, UfvTransitStateEnum inStage, CarrierVisit inIbCv, CarrierVisit inObCv, String inSlot) throws BizViolation {
        return InventoryTestUtils.makeUfv(inEqId, inStage, inIbCv, inObCv, inSlot);
    }

    @Nullable
    public UnitFacilityVisit findOrCreateUfvIncomingByVessel(Container inCtr, CarrierVisit inVesselVisit, String inSlot, String inDoorDirection) throws BizViolation {
        return InventoryTestUtils.findOrCreateUfvIncomingByVessel(inCtr, inVesselVisit, inSlot, inDoorDirection);
    }

    public void setUnitLineOperator(Unit inUnit, LineOperator inLine) {
        inUnit.setUnitLineOperator((ScopedBizUnit)inLine);
    }

    protected CarrierItinerary findOrCreateItinerary(String inId, String[] inUnLocs) {
        return InventoryTestUtils.findOrCreateItinerary(inId, inUnLocs);
    }

    protected CarrierService findOrCreateVesselService(String inServiceId, String inServiceName, String[] inUnLocs) {
        return InventoryTestUtils.findOrCreateVesselService(inServiceId, inServiceName, inUnLocs);
    }

    public void purgeUnitsByEqId(String inEqId, Complex inComplex) {
        InventoryTestUtils.purgeUnitsByEqId(inEqId, inComplex);
    }

    public void purgeUnitsByEqId(String inEqId) {
        InventoryTestUtils.purgeUnitsByEqId(inEqId, ContextHelper.getThreadComplex());
    }

    public void purgeUnitsByEqId(String... inEqIds) {
        InventoryTestUtils.purgeUnitsByEqId(ContextHelper.getThreadComplex(), inEqIds);
    }

    public void purgeUnits(Equipment inEq, Complex inComplex) {
        InventoryTestUtils.purgeUnits(inEq, inComplex);
    }

    protected void purgeEquipment(String inEqId) {
        InventoryTestUtils.purgeEquipment(inEqId);
    }

    protected void purgeEqType(String inEqTypeId) {
        InventoryTestUtils.purgeEqType(inEqTypeId);
    }

    public Unit findOrCreatePreadvisedRoadUnit(CarrierVisit inTruckVisit, Equipment inPrimaryEq, String inPosOnTruck, String inDoorDirection) throws BizViolation {
        return InventoryTestUtils.findOrCreatePreadvisedRoadUnit(inTruckVisit, inPrimaryEq, inPosOnTruck, inDoorDirection, this.getTestLineOp1());
    }

    public Unit findOrCreatePreadvisedRoadUnit(Equipment inPrimaryEq) throws BizViolation {
        return InventoryTestUtils.findOrCreatePreadvisedRoadUnit(inPrimaryEq, this.getTestLineOp1());
    }

    protected Unit findOrCreateIncomingRoadUnit(CarrierVisit inSomeTruck, Equipment inCtr) throws BizViolation {
        return InventoryTestUtils.findOrCreateIncomingRoadUnit(inSomeTruck, inCtr);
    }

    @Nullable
    public Unit findUnitByDeclaredIbCarrier(Equipment inPrimaryEq, CarrierVisit inIbCarrier) {
        if (inPrimaryEq == null || inIbCarrier == null) {
            return null;
        }
        return InventoryTestUtils.findUnitByDeclaredIbCarrier(inPrimaryEq, inIbCarrier);
    }

    public Unit createYardUnit(Equipment inPrimaryEq, Yard inYard, String inYardSlot, CarrierVisit inIbCv, CarrierVisit inObCv) throws BizViolation {
        return InventoryTestUtils.createYardUnit(inPrimaryEq, inYard, inYardSlot, inIbCv, inObCv);
    }

    @Nullable
    public UnitFacilityVisit createInYardUfv(Equipment inPrimaryEq) {
        try {
            return InventoryTestUtils.createInYardUfv(inPrimaryEq, "A12.2");
        }
        catch (BizViolation inViolation) {
            this.failOnException("", (Exception)((Object)inViolation));
            return null;
        }
    }

    @Nullable
    public UnitFacilityVisit createInYardUfv(Equipment inPrimaryEq, @NotNull String inSlot) {
        try {
            return InventoryTestUtils.createInYardUfv(inPrimaryEq, inSlot);
        }
        catch (BizViolation inViolation) {
            this.failOnException("", (Exception)((Object)inViolation));
            return null;
        }
    }

    public void createTestDataUnitsWithChassis(Yard inYard) {
        String equipTypeId = "CH000";
        EquipType chassisType = EquipType.findEquipType((String)equipTypeId);
        if (chassisType == null) {
            chassisType = EquipType.createEquipType((String)equipTypeId, (EquipClassEnum) EquipClassEnum.CHASSIS);
        } else if (!chassisType.getEqtypClass().equals((Object) EquipClassEnum.CHASSIS)) {
            LOGGER.error((Object)"Can't create test data: equipType is not for chassis!");
            return;
        }
        this.createTestYardUnit((Equipment)this.createContainer("CONT11111111", "2200"), (Equipment)this.createChassis("CHASSIS111111", chassisType.getEqtypId()), inYard);
        this.createTestYardUnit((Equipment)this.createContainer("CONT22222222", "2200"), (Equipment)this.createChassis("CHASSIS222222", chassisType.getEqtypId()), inYard);
        this.createTestYardUnit((Equipment)this.createContainer("CONT33333333", "2200"), (Equipment)this.createChassis("CHASSIS333333", chassisType.getEqtypId()), inYard);
        this.createTestYardUnit((Equipment)this.createChassis("CHASSIS8888888", chassisType.getEqtypId()), null, inYard);
        this.createTestYardUnit((Equipment)this.createChassis("CHASSIS7777777", chassisType.getEqtypId()), null, inYard);
    }

    private void createTestYardUnit(Equipment inPrimaryEq, Equipment inCarrierEquip, Yard inYard) {
        Facility facility = inYard.getYrdFacility();
        Complex complex = facility.getFcyComplex();
        Unit unit = this.getFndr().findActiveUnit(complex, inPrimaryEq);
        if (unit == null) {
            try {
                unit = this.createYardUnit(inPrimaryEq, inYard, "Y54.1", CarrierVisit.getGenericTruckVisit((Complex)complex), CarrierVisit.getGenericVesselVisit((Complex)complex));
            }
            catch (BizViolation bv) {
                LOGGER.error((Object)("creation of testUnit for Equipment " + inPrimaryEq.getEqIdFull() + " failed!"));
            }
        }
        if (unit == null) {
            String primEq = "None";
            if (inPrimaryEq != null) {
                primEq = inPrimaryEq.getEqIdFull();
            }
            String carrier = "None";
            if (inCarrierEquip != null) {
                carrier = inCarrierEquip.getEqIdFull();
            }
            LOGGER.error((Object)("Creating TestUnit with PrimaryEquip " + primEq + " and Carrier " + carrier + " testUnit failed!"));
            return;
        }
        if (inCarrierEquip != null) {
            try {
                unit.attachCarriage(inCarrierEquip);
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)("attaching chassis " + inCarrierEquip.getEqIdFull() + " to unit failed"));
            }
        }
    }

    @Nullable
    protected Chassis createChassis(String inChassisId, String inEquipTypeId) {
        return InventoryTestUtils.createChassis(inChassisId, inEquipTypeId);
    }

    @Nullable
    public Container createContainer(String inContainerId, String inContainerEquipType) {
        return InventoryTestUtils.createContainer(inContainerId, inContainerEquipType);
    }

    public void createEquipmentTypesAndRoutingPoints() {
        HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
        EquipType.findOrCreateEquipType((String)"2000");
        EquipType.findOrCreateEquipType((String)"2200");
        EquipType.findOrCreateEquipType((String)"2230");
        EquipType.findOrCreateEquipType((String)"42P0");
        EquipType.findOrCreateEquipType((String)"4200");
        EquipType.findOrCreateEquipType((String)"4231");
        EquipType.findOrCreateEquipType((String)"42R0");
        RoutingPoint.resolveRoutingPointFromPortCode((String)"HJH");
        RoutingPoint.resolveRoutingPointFromPortCode((String)"NYC");
        RoutingPoint.resolveRoutingPointFromPortCode((String)"BOS");
        RoutingPoint.resolveRoutingPointFromPortCode((String)"AKL");
        RoutingPoint.resolveRoutingPointFromPortCode((String)"ADL");
    }

    public void createTestDataForPlacard() {
        LOGGER.info((Object)"Inside UnitManagerPea:createTestDataForPlacard ");
        Placard.createPlacard("Explosives 1", " Division 1.1, 1.2 or 1.3", null);
        Placard.createPlacard("1.4 Explosives 1", null, new Double(454.0));
        Placard.createPlacard("1.5 Blasting Agents 1", null, new Double(454.0));
        Placard.createPlacard("1.6 Explosives 1", null, new Double(454.0));
        Placard.createPlacard("Flammable Gas 2", " Division 2.1", new Double(454.0));
        Placard.createPlacard("Non-Flammable Gas 2", " Division 2.2", new Double(454.0));
        Placard.createPlacard("Oxygen 2", null, new Double(454.0));
        Placard.createPlacard("Poison Gas 2", " Division 2.3", null);
        Placard.createPlacard("Flammable 3", null, new Double(454.0));
        Placard.createPlacard("Gasoline 3", null, null);
        Placard.createPlacard("Combustable 3", null, null);
        Placard.createPlacard("Fuel Oil 3", null, null);
        Placard.createPlacard("Flammable Solid 4", " Division 4.1", new Double(454.0));
        Placard.createPlacard("Spontaneously Combustible 4", " Division 4.2", new Double(454.0));
        Placard.createPlacard("Dangerous When Wet 4", " Division 4.3", null);
        Placard.createPlacard("Oxidiser 5.1", null, new Double(454.0));
        Placard.createPlacard("Organic Peroxide 5.2", null, new Double(454.0));
        Placard.createPlacard("Poison 6", "Division 6.1 packaging Groups I or II", new Double(454.0));
        Placard.createPlacard("Harmful,Stow Away From Foods 6", "Division 6.1 packaging Groups III", new Double(454.0));
        Placard.createPlacard("Radioactive 7", null, null);
        Placard.createPlacard("Corrosive 8", null, new Double(454.0));
        Placard.createPlacard("Miscellaneous 9", null, new Double(454.0));
        Placard.createPlacard("Corrosive", "Subsidiary Risk", null);
        Placard.createPlacard("Marine Pollutant", "Subsidiary Risk", null);
    }

    public CarrierVisit createTestTrainVisit(String inTvId) {
        Long carrierItineraryGkey = null;
        Long carrierServiceGkey = null;
        this.startHibernateAtCpx11();
        CarrierItinerary carrierItinerary = CarrierItinerary.findOrCreateCarrierItinerary((String)inTvId, null);
        carrierItineraryGkey = carrierItinerary.getItinGkey();
        CarrierService carrierServices = CarrierService.findOrCreateCarrierService((String)inTvId, (String)"CarrierService", (LocTypeEnum) LocTypeEnum.TRAIN, (CarrierItinerary)carrierItinerary);
        carrierServiceGkey = carrierServices.getSrvcGkey();
        this.endHibernate();
        IArgoRailManager argoRailManager = (IArgoRailManager) Roastery.getBean((String)"argoRailManager");
        this.startHibernateAtFcy111();
        Facility fcy = this.getFcy111();
        try {
            fcy.getActiveYard();
        }
        catch (BizViolation bv) {
            this.failOnException(bv.toString(), (Exception)((Object)bv));
            LOGGER.error((Object)"failed to initialize yard");
        }
        CarrierVisit obTrainVisit = CarrierVisit.findOrCreateTrainVisit((Facility)fcy, (String)inTvId);
        CarrierVisit outTrain = argoRailManager.createOrUpdateTrainVisitDetails(fcy, obTrainVisit, ArgoUtils.timeNow());
        AbstractInventoryTestCase.assertNotNull((Object)outTrain);
        outTrain.setCvNextFacility(this.getFcy112());
        outTrain.setCvVisitPhase(CarrierVisitPhaseEnum.WORKING);
        this._hibernateApi.saveOrUpdate((Object)outTrain);
        outTrain.getCvCvd().setFieldValue(IArgoField.CVD_SERVICE, (Object)carrierServiceGkey);
        outTrain.getCvCvd().setFieldValue(IArgoField.CVD_ITINERARY, (Object)carrierItineraryGkey);
        outTrain.getCvCvd().getCvdItinerary().addCarrierCall("OAK", "1", "M", new String[]{"LYT"});
        outTrain.getCvCvd().getCvdItinerary().addCarrierCall("LYT", "2", "N", new String[]{"OAK"});
        outTrain.getCvCvd().getCvdItinerary().addCarrierCall("LYT", "3", "N", new String[]{"KHH"});
        this.endHibernate();
        return outTrain;
    }

    public void createTestDataYardUnitsForYIP() {
        this.startHibernateAtYard1111();
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234001", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234101", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234201", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234301", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234401", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234501", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234601", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234701", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU1234801", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU2234601", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU2234701", "2200"), null, ContextHelper.getThreadYard());
        this.createTestYardUnit((Equipment)this.createContainer("CTDU2234801", "2200"), null, ContextHelper.getThreadYard());
        this.endHibernate();
    }

    public void importUnit(Element inElement) {
        this.importEntityViaSnx("Unit", inElement);
    }

    public Element getRootEleFromXmlStrContent(String inXmlString) throws Exception {
//        Document document = new SAXBuilder().build((Reader)new StringReader(inXmlString));
//        return document.getRootElement();
        return null;
    }

    public IUnitManager getMngr() {
        return (IUnitManager) Roastery.getBean((String)"unitManager");
    }

    public IUnitFinder getFndr() {
        Object bean = Roastery.getBean((String)"unitFinder");
        return (IUnitFinder)bean;
    }

    public IUnitReroutePoster getRerouteMngr() {
        Object bean = Roastery.getBean((String)"unitReroutePoster");
        return (IUnitReroutePoster)bean;
    }

    public ICarrierManager getCarrierMngr() {
        Object bean = Roastery.getBean((String)"carrierManager");
        return (ICarrierManager)bean;
    }

    public Unit reloadUnitById(String inPrimaryEqId) {
        Equipment eq = Equipment.findEquipment((String)inPrimaryEqId);
        return eq == null ? null : this.getFndr().findAttachedUnit(ContextHelper.getThreadComplex(), eq);
    }

    @Nullable
    public UnitFacilityVisit reloadUfvById(String inPrimaryEqId) {
        Equipment eq = Equipment.findEquipment((String)inPrimaryEqId);
        if (eq == null) {
            return null;
        }
        Unit unit = this.getFndr().findAttachedUnit(ContextHelper.getThreadComplex(), eq);
        if (unit == null) {
            return null;
        }
        UnitFacilityVisit ufv = unit.getUfvForFacilityNewest(ContextHelper.getThreadFacility());
        return ufv;
    }

    @Nullable
    protected UnitFacilityVisit reloadUfv(Serializable inUfvGkey) {
        return inUfvGkey == null ? null : (UnitFacilityVisit) HibernateApi.getInstance().load(UnitFacilityVisit.class, inUfvGkey);
    }

    @Nullable
    protected CarrierVisit reloadCv(Serializable inCvGkey) {
        return inCvGkey == null ? null : (CarrierVisit) HibernateApi.getInstance().load(CarrierVisit.class, inCvGkey);
    }

    @Nullable
    public Unit reloadUnit(Serializable inUnitGkey) {
        return inUnitGkey == null ? null : Unit.hydrate(inUnitGkey);
    }

    @Nullable
    protected Container findOrCreateTestCtr(String inCtrNbr, String inEqTypeId, String inLineOpId) {
        Container ctr = null;
        try {
            ctr = Container.findOrCreateContainer((String)inCtrNbr, (String)inEqTypeId, (DataSourceEnum) DataSourceEnum.TESTING);
            EquipmentState eqs = EquipmentState.findOrCreateEquipmentState((Equipment)ctr, ContextHelper.getThreadOperator());
            eqs.upgradeEquipmentOperator((ScopedBizUnit) LineOperator.findOrCreateLineOperator((String)inLineOpId), DataSourceEnum.USER_LCL);
        }
        catch (BizViolation bv) {
            this.failOnException("", (Exception)((Object)bv));
        }
        return ctr;
    }

    public void setEqOperator(Equipment inEq, ScopedBizUnit inOperator, DataSourceEnum inDataSource) {
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentState(inEq, ContextHelper.getThreadOperator());
        eqs.upgradeEquipmentOperator(inOperator, inDataSource);
    }

    public void refreshUnit(UnitFacilityVisit inUfv, UserContext inUserContext) {
        BizRequest request = new BizRequest(inUserContext);
        FieldChanges changes = new FieldChanges();
        CrudOperation crud = new CrudOperation(null, 2, "UnitFacilityVisit", changes, (Object[])new Serializable[]{inUfv.getUfvGkey()});
        request.addCrudOperation(crud);
        BizResponse response = CrudDelegate.executeBizRequest((BizRequest)request, (String)"invRefreshUnit");
        this.assertTrueResponseSuccess("refresh unit failed!", (IMessageCollector)response);
    }

    public void purgeNoticeRequestWithId(String inCtrId) {
//        List<NoticeRequest> nrList = this.findContainerNoticeRequestsForUnitId(inCtrId);
//        for (NoticeRequest nr : nrList) {
//            ArgoUtils.carefulDelete((Object)nr);
//        }
    }

//    public List<NoticeRequest> findContainerNoticeRequestsForUnitId(String inEqId) {
//        IDomainQuery dqContainerNoticeRequestsForUnitId = QueryUtils.createDomainQuery((String)"NoticeRequest").addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.NOTICE_APPLIED_TO_CLASS, (Object)LogicalEntityEnum.UNIT)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IServicesField.NOTICE_APPLIED_TO_NATURAL_ID, (Object)inEqId)).addDqOrdering(Ordering.desc((IMetafieldId)IServicesField.NOTICE_CREATED));
//        return HibernateApi.getInstance().findEntitiesByDomainQuery(dqContainerNoticeRequestsForUnitId);
//    }

    public BizResponse stowPlanContainer(FieldChanges inChanges) {
        return this.stowPlanContainer(this.getTestUserContextAtYrd1111(), inChanges);
    }

    public BizResponse stowPlanContainer(String inCtrId, String inCvId) {
        return this.stowPlanContainer(this.getTestUserContextAtYrd1111(), this.getDefaultVesselStowplanFieldChanges(inCtrId, inCvId));
    }

    public FieldChanges getDefaultVesselStowplanFieldChanges(String inCtrId, String inCvId) {
        this.startHibernateAtYard1111();
        EquipType eqType = EquipType.findOrCreateEquipType((String)"2200");
        Container ctr = this.findOrCreateTestCtr(inCtrId, "2200");
        AbstractInventoryTestCase.assertNotNull((Object)ctr);
        Long gkey = this.getTestLineOp1().getBzuGkey();
        ctr.setFieldValue(IArgoBizMetafield.EQUIPMENT_OPERATOR, (Object)gkey);
        CarrierVisit cv = this.createTestVesselVisit(inCvId, "POPEYE");
        Long cvGkey = cv.getCvGkey();
        cv.safelyUpdateVisitPhase(CarrierVisitPhaseEnum.INBOUND);
        FieldChanges changes = this.getVesselStowplanFieldChanges(cv, inCtrId, "POPEYE", eqType);
        this.endHibernate();
        return changes;
    }

    public FieldChanges getDefaultVesselStowplanFieldChanges(String inCtrId, String inCvId, String inLineId) {
        this.startHibernateAtYard1111();
        EquipType eqType = EquipType.findOrCreateEquipType((String)"2200");
        Container ctr = this.findOrCreateTestCtr(inCtrId, "2200");
        AbstractInventoryTestCase.assertNotNull((Object)ctr);
        Long gkey = this.getTestLineOp1().getBzuGkey();
        ctr.setFieldValue(IArgoBizMetafield.EQUIPMENT_OPERATOR, (Object)gkey);
        CarrierVisit cv = this.createTestVesselVisit(inCvId, inLineId);
        Long cvGkey = cv.getCvGkey();
        cv.safelyUpdateVisitPhase(CarrierVisitPhaseEnum.INBOUND);
        FieldChanges changes = this.getVesselStowplanFieldChanges(cv, inCtrId, inLineId, eqType);
        this.endHibernate();
        return changes;
    }

    public BizResponse stowPlanContainer(UserContext inUc, FieldChanges inChanges) {
        BizRequest request = new BizRequest(inUc);
        CrudOperation crud = new CrudOperation(null, 2, "Unit", inChanges, null);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invEnterStowplan");
    }

    public Serializable findOrCreateGroovyInDatabase(String inId, String inGroovyScript) {
        boolean isCreating;
//        DigitalAsset digitalAsset = DigitalAsset.findGroovyClass((String)inId);
//        boolean bl = isCreating = digitalAsset == null;
//        if (isCreating) {
//            digitalAsset = DigitalAsset.createDigitalAsset((DigitalAssetTypeEnum)DigitalAssetTypeEnum.GROOVY, (String)inId, (String)inId, (byte[])inGroovyScript.getBytes());
//            HibernateApi.getInstance().save((Object)digitalAsset);
//        } else if (inGroovyScript != null) {
//            FieldChanges newChanges = new FieldChanges();
//            newChanges.setFieldChange(IArgoBizMetafield.DA_GROOVY_CODE, (Object)inGroovyScript);
//            digitalAsset.applyFieldChanges(newChanges);
//        }
//        LOGGER.info((Object)("Loaded Groovy Class :" + inGroovyScript));
//        return digitalAsset.getDaGkey();
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected BentoNode invokeBentoServer(BentoNode inRequest, int inTbdRequestMessageId, IMessageIdMapper inMapper) {
        BentoNode response = null;
        int soTimeOut = 2000;
        int connectTimeOut = 4000;
        int clientBufferSize = 5000;
        Socket clientSocket = null;
        FilterOutputStream outputStream = null;
        FilterInputStream inputStream = null;
        try {
            clientSocket = new Socket();
            LOGGER.info((Object)("Setting Time out as  " + soTimeOut));
            clientSocket.setSoTimeout(soTimeOut);
            clientSocket.setReceiveBufferSize(clientBufferSize);
            clientSocket.setSendBufferSize(clientBufferSize);
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", (int) ArgoConfig.TBDUNIT_BENTO_SERVER_PORT.getValue(ContextHelper.getThreadUserContext()));
            LOGGER.info((Object)("Trying to connect with socketaddress as  " + socketAddress.toString()));
            clientSocket.connect(socketAddress, connectTimeOut);
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            ((DataOutputStream)outputStream).flush();
            inputStream = new DataInputStream(clientSocket.getInputStream());
//            IMessage outMessage = new IMessage(inTbdRequestMessageId);
//            inRequest.writeToStream((DataOutput)outMessage);
//            outMessage.send((DataOutputStream)outputStream, inMapper);
//            IMessage inMessage = new IMessage();
//            inMessage.receive((DataInputStream)inputStream, inMapper);
//            BentoNode bentoNode = response = new BentoNode((DataInput)inMessage);
//            return bentoNode;

        }
        catch (IOException ex) {
            this.failOnException("Failed to communicate with bento server", ex);
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            }
            catch (IOException ex) {
                this.failOnException("Failed cleaning-up connection", ex);
            }
        }
        return response;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void tryClientSocketConnection() throws InterruptedException {
        int soTimeOut = 2000;
        int connectTimeOut = 1000;
        int clientBufferSize = 5000;
        Socket clientSocket = new Socket();
        try {
            LOGGER.info((Object)("Setting Time out as  " + soTimeOut));
            clientSocket.setSoTimeout(soTimeOut);
            clientSocket.setReceiveBufferSize(clientBufferSize);
            clientSocket.setSendBufferSize(clientBufferSize);
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", (int) ArgoConfig.TBDUNIT_BENTO_SERVER_PORT.getValue(ContextHelper.getThreadUserContext()));
            for (int repeat = 0; repeat <= 10; ++repeat) {
                try {
                    LOGGER.info((Object)("Trying to connect with socketaddress as  " + socketAddress.toString()));
                    clientSocket.connect(socketAddress, connectTimeOut);
                    break;
                }
                catch (Exception ex) {
                    Thread.sleep(1000L);
                    continue;
                }
            }
        }
        catch (IOException ex) {
            LOGGER.error((Object)ex);
        }
        finally {
            try {
                clientSocket.close();
            }
            catch (IOException ex) {
                this.failOnException("Failed cleaning-up connection", ex);
            }
        }
    }

    public Unit attachFailOnException(String inEqId1, String inEqId2, EqUnitRoleEnum inRole) {
        return this.attachFailOnException(inEqId1, inEqId2, inRole, true);
    }

    public Unit attachFailOnException(String inEqId1, String inEqId2, EqUnitRoleEnum inRole, boolean inSwipeAllowed) {
        Unit unit = this.reloadUnitById(inEqId1);
        AbstractInventoryTestCase.assertNotNull((Object)unit);
        Equipment eq = Equipment.findEquipment((String)inEqId2);
        try {
            return unit.attachEquipment(eq, inRole, inSwipeAllowed, ContextHelper.getThreadDataSource() != DataSourceEnum.SNX, true, true);
        }
        catch (BizViolation inBizViolation) {
            this.failOnException("", (Exception)((Object)inBizViolation));
            return null;
        }
    }

    public Unit attach(String inEqId1, String inEqId2, EqUnitRoleEnum inRole) throws BizViolation {
        return this.attach(inEqId1, inEqId2, inRole, true);
    }

    public Unit attach(String inEqId1, String inEqId2, EqUnitRoleEnum inRole, boolean inSwipeAllowed) throws BizViolation {
        Unit unit = this.reloadUnitById(inEqId1);
        AbstractInventoryTestCase.assertNotNull((Object)unit);
        Equipment eq = Equipment.findEquipment((String)inEqId2);
        return unit.attachEquipment(eq, inRole, inSwipeAllowed, true, true, true);
    }

    public Unit attachMulti(String inEqId1, String inEqId2, EqUnitRoleEnum inRole) throws BizViolation {
        Unit unit = this.reloadUnitById(inEqId1);
        AbstractInventoryTestCase.assertNotNull((Object)unit);
        return this.attachFromInventoryMultipleEqAllowed(unit, inEqId2, inRole);
    }

    public Unit attachFromInventoryMultipleEqAllowed(Unit inUnit, String inEqId, EqUnitRoleEnum inRole) throws BizViolation {
        return this.attachFromInventoryMultipleEqAllowed(inUnit, inEqId, inRole, false);
    }

    public Unit attachFromInventoryMultipleEqAllowed(Unit inUnit, String inEqId, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed) throws BizViolation {
        return inUnit.validateLocationAndAttachEquipment(inEqId, inRole, inSwipeIsAllowed, true);
    }

    public Unit validateLocAndAttach(String inEqId1, String inEqId2, EqUnitRoleEnum inRole) throws BizViolation {
        return this.validateLocationAndAttach(inEqId1, inEqId2, inRole, true);
    }

    public Unit validateLocationAndAttach(String inEqId1, String inEqId2, EqUnitRoleEnum inRole, boolean inSwipeAllowed) throws BizViolation {
        Unit unit = this.reloadUnitById(inEqId1);
        AbstractInventoryTestCase.assertNotNull((Object)unit);
        return unit.validateLocationAndAttachEquipment(inEqId2, inRole, inSwipeAllowed, true);
    }

    public BizResponse invokeAttachEquipment(String inEqid, Serializable inUnitGkey, EqUnitRoleEnum inEqUnitRoleEnum) {
        BizRequest request = new BizRequest(this.getTestUserContextAtYrd1111());
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(IInventoryBizMetafield.UE_NON_PRIMARY_ROLE, (Object)inEqUnitRoleEnum);
        changes.setFieldChange(UnitField.UNIT_EQ_ID_FULL, (Object)inEqid);
        changes.setFieldChange(IInventoryBizMetafield.UNIT_ALLOW_SWIPE, (Object)true);
        CrudOperation crud = new CrudOperation(null, 2, "Unit", changes, new Object[]{inUnitGkey});
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invAttachEquipment");
    }

    public BizResponse invokeDetachEquipment(Serializable inUnitGkey) {
        return this.invokeDetachEquipment(inUnitGkey, null);
    }

    public BizResponse invokeDetachEquipment(Serializable inUnitGkey, Serializable inFromUnitGkey) {
        BizRequest request = new BizRequest(this.getTestUserContextAtYrd1111());
        request.setParameter(IInventoryField.UNIT_GKEY.getFieldId(), inFromUnitGkey);
        FieldChanges changes = new FieldChanges();
        CrudOperation crud = new CrudOperation(null, 2, "Unit", changes, new Object[]{inUnitGkey});
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invDetachEquipment");
    }

    public Serializable createWorkInstruction(Serializable inUyvGkey, WiMoveKindEnum inMoveKind, WiMoveStageEnum inMoveStage) {
        return this.createWorkInstruction(inUyvGkey, inMoveKind, inMoveStage, null);
    }

    public Serializable createWorkInstruction(final Serializable inUyvGkey, final WiMoveKindEnum inMoveKind, final WiMoveStageEnum inMoveStage, final LocPosition inPosition) {
        final Serializable[] wiGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                UnitYardVisit uyv = UnitYardVisit.hydrate(inUyvGkey);
                WorkQueue wq = WorkQueue.findOrCreateWorkQueue((Serializable)AbstractInventoryTestCase.this.getYrd1111().getYrdGkey(), 26008L);
                WorkInstruction wi = InventoryTestUtils.createWorkInstruction(uyv, wq, inMoveKind, inMoveStage, inPosition);
                Assert.assertNotNull((Object)wi);
                HibernateApi.getInstance().flush();
                wiGkey[0] = wi.getPrimaryKey();
            }
        });
        return wiGkey[0];
    }

    public BizResponse assignImportDeliveryOrder(@NotNull String inEqIdFull, @NotNull Serializable inIdoGkey, Serializable inTrkcGkey, Serializable inEqTypeGkey) {
        return this.assignImportDeliveryOrder(this.getTestUserContextAtYrd1111(), inEqIdFull, inIdoGkey, inTrkcGkey, inEqTypeGkey);
    }

    public BizResponse assignImportDeliveryOrder(@NotNull UserContext inUserContext, @NotNull String inEqIdFull, @NotNull Serializable inIdoGkey, Serializable inTrkcGkey, Serializable inEqTypeGkey) {
        BizRequest request = new BizRequest(inUserContext);
        request.setParameter(IInventoryField.IDO_GKEY.toString(), inIdoGkey);
        request.setParameter(UnitField.UNIT_PRIMARY_EQ_ID_FULL.toString(), (Serializable)((Object)inEqIdFull));
        if (inTrkcGkey != null) {
            request.setParameter(IInventoryBizMetafield.UNIT_RTG_TRUCKING_COMPANY.getFieldId(), inTrkcGkey);
        }
        if (inEqTypeGkey != null) {
            request.setParameter(UnitField.UNIT_PRIMARY_EQTYPE.getFieldId(), inEqTypeGkey);
        }
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invImportDeliveryOrderAssign");
    }

    public Serializable extractUfvGkeyFromResponse(BizResponse inBizResponse) {
        Serializable ufvGkey = null;
        IQueryResult queryResult = inBizResponse.getQueryResult();
        for (int row = queryResult.getFirstResult(); row <= queryResult.getLastResult(); ++row) {
            ufvGkey = (Serializable)queryResult.getValue(row, IInventoryField.UFV_GKEY);
        }
        AbstractInventoryTestCase.assertNotNull(ufvGkey);
        return ufvGkey;
    }

    public BizResponse updatePhysicalStatus(Serializable[] inUfvGkey, FieldChanges inUpdatechanges, UserContext inUserContext) {
        BizRequest request = new BizRequest(inUserContext);
        CrudOperation crud = new CrudOperation(null, 2, "UnitFacilityVisit", inUpdatechanges, (Object[])inUfvGkey);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invUpdatePhysicalStatus");
    }

    public BizResponse updatePhysicalStatus(Serializable[] inUfvGkey, FieldChanges inUpdatechanges) {
        return this.updatePhysicalStatus(inUfvGkey, inUpdatechanges, this.getTestUserContextAtYrd1111());
    }

    public BizResponse updateRouting(Serializable[] inUfvGkeys, FieldChanges inRoutingChanges) {
        return this.updateRouting(inUfvGkeys, inRoutingChanges, this.getTestUserContextAtYrd1111());
    }

    public BizResponse updateRouting(Serializable[] inUfvGkeys, FieldChanges inRoutingChanges, UserContext inUserContext) {
        BizRequest request = new BizRequest(inUserContext);
        CrudOperation crud = new CrudOperation(null, 2, "UnitFacilityVisit", inRoutingChanges, (Object[])inUfvGkeys);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invUpdateRouting");
    }

    public BizResponse updateDetails(Serializable[] inUfvGkeys, FieldChanges inUpdateDetailsChanges) {
        return this.updateDetails(inUfvGkeys, inUpdateDetailsChanges, this.getTestUserContextAtYrd1111());
    }

    public BizResponse updateDetails(Serializable[] inUfvGkeys, FieldChanges inUpdateDetailsChanges, UserContext inUserContext) {
        BizRequest request = new BizRequest(inUserContext);
        CrudOperation crud = new CrudOperation(null, 2, "UnitFacilityVisit", inUpdateDetailsChanges, (Object[])inUfvGkeys);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invUpdateDetails");
    }

    public FieldChanges getVesselStowplanFieldChanges(CarrierVisit inIbCv, String inCtrId, String inLineId) {
        return this.getVesselStowplanFieldChanges(inIbCv, inCtrId, inLineId, "123456", "HJH", "OAK");
    }

    public FieldChanges getVesselStowplanFieldChanges(CarrierVisit inIbCv, String inCtrId, String inLineId, EquipType inEqType) {
        return this.getVesselStowplanFieldChanges(inIbCv, inCtrId, inLineId, "123456", "HJH", "OAK", inEqType);
    }

    public FieldChanges getVesselStowplanFieldChanges(CarrierVisit inIbCv, String inCtrId, String inLineId, String inStowPos, String inPolId, String inPodId) {
        EquipType eqtyp2200 = EquipType.findOrCreateEquipType((String)"2200");
        return this.getVesselStowplanFieldChanges(inIbCv, inCtrId, inLineId, inStowPos, inPolId, inPodId, eqtyp2200);
    }

    public FieldChanges getVesselStowplanFieldChanges(CarrierVisit inIbCv, String inCtrId, String inLineId, String inStowPos, String inPolId, String inPodId, EquipType inEqType) {
        return this.getVesselStowplanFieldChanges(inIbCv, inCtrId, inLineId, inStowPos, inPolId, inPodId, inEqType, UnitCategoryEnum.IMPORT, inEqType.getEqtypTareWeightKg(), FreightKindEnum.MTY);
    }

    public FieldChanges getVesselStowplanFieldChanges(CarrierVisit inIbCv, String inCtrId, String inLineId, String inStowPos, String inPolId, String inPodId, EquipType inEqType, UnitCategoryEnum inCategory, Double inGrossWtinKg, FreightKindEnum inFreightKind) {
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(IInventoryBizMetafield.UNIT_INBOUND_VESSEL, (Object)inIbCv.getCvCvd().getCvdGkey());
        changes.setFieldChange(UnitField.UNIT_DECLARED_IB_ID, (Object)inIbCv.getCvId());
        changes.setFieldChange(UnitField.UNIT_DECLARED_IB_CV, (Object)inIbCv.getCvGkey());
        changes.setFieldChange(UnitField.UNIT_PRIMARY_EQ_ID_FULL, (Object)inCtrId);
        changes.setFieldChange(UnitField.UNIT_PRIMARY_EQTYPE_ID, (Object)inEqType.getEqtypId());
        changes.setFieldChange(IInventoryBizMetafield.UNIT_ARRIVE_POSITION_SLOT, (Object)inStowPos);
        changes.setFieldChange(UnitField.UNIT_LINE_OPERATOR_ID, (Object)inLineId);
        changes.setFieldChange(UnitField.UNIT_CATEGORY, (Object)inCategory);
        changes.setFieldChange(UnitField.UNIT_GOODS_AND_CTR_WT_KG, (Object)inGrossWtinKg);
        changes.setFieldChange(UnitField.UNIT_FREIGHT_KIND, (Object)inFreightKind);
        changes.setFieldChange(UnitField.UNIT_POL_ID, (Object)inPolId);
        changes.setFieldChange(UnitField.UNIT_POD_ID, (Object)inPodId);
        return changes;
    }

    public FieldChanges getSrvcRuleFcs(String inSrvRulId, Serializable inFlagTypeGkey, Serializable inEventTypeGkey, IMetafieldId inPathToGuardian) {
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(IServicesField.SRVRUL_NAME, (Object)inSrvRulId);
        changes.setFieldChange(IServicesField.SRVRUL_FLAG_TYPE, (Object)inFlagTypeGkey);
        changes.setFieldChange(IServicesField.SRVRUL_SERVICE_TYPE, (Object)inEventTypeGkey);
        changes.setFieldChange(IServicesField.SRVRUL_RULE_TYPE, (Object) ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN);
        changes.setFieldChange(IServicesField.SRVRUL_PATH_TO_GUARDIAN, (Object)inPathToGuardian.getFieldId());
        return changes;
    }

    public BizResponse createServiceRule(FieldChanges inSrvcRuleFcs) {
        return CrudDelegate.getBizDelegate().requestCreate(this.getTestUserContextAtYrd1111(), "ServiceRule", null, inSrvcRuleFcs);
    }

    public BizResponse loadUnitToVessel(Serializable inCvdGkey, String inCtrId, Boolean inAttachChassis) {
        return this.loadUnitToVessel(this.getTestUserContextAtYrd1111(), inCvdGkey, inCtrId, inAttachChassis);
    }

    public BizResponse loadUnitToVessel(UserContext inUc, Serializable inCvdGkey, String inCtrId, Boolean inAttachChassis) {
        BizRequest request = new BizRequest(inUc);
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(IInventoryBizMetafield.UNIT_LOAD_VESSEL, (Object)inCvdGkey);
        changes.setFieldChange(IInventoryBizMetafield.UNIT_DIGITS, (Object)inCtrId);
        changes.setFieldChange(IInventoryBizMetafield.UNIT_STOW_POS, (Object)"123456");
        changes.setFieldChange(IInventoryBizMetafield.UNIT_ATTACH_CHASSIS, (Object)inAttachChassis);
        CrudOperation crud = new CrudOperation(null, 2, "Unit", changes, new Object[0]);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invLoadToVessel");
    }

    public BizResponse assignExportBookingByUfvs(Serializable[] inUfvGkeys, Serializable inBkgGkey) {
        return this.assignExportBookingByUfvs(this.getTestUserContextAtYrd1111(), inUfvGkeys, inBkgGkey);
    }

    public BizResponse assignExportBookingByUfvs(UserContext inUserContext, Serializable[] inUfvGkeys, Serializable inBkgGkey) {
        return this.assignExportBookingByUfvs(inUserContext, inUfvGkeys, inBkgGkey, null, null);
    }

    public BizResponse assignExportBookingByUfvs(Serializable[] inUfvGkeys, Serializable inBkgGkey, Boolean inUpdateUnitWithItem, Serializable inEqoItemGkey) {
        return this.assignExportBookingByUfvs(this.getTestUserContextAtYrd1111(), inUfvGkeys, inBkgGkey, inUpdateUnitWithItem, inEqoItemGkey);
    }

    public BizResponse assignExportBookingByUfvs(UserContext inUserContext, Serializable[] inUfvGkeys, Serializable inBkgGkey, @Nullable Boolean inUpdateUnitWithItem, @Nullable Serializable inEqoItemGkey) {
        BizRequest req = new BizRequest(inUserContext);
        req.setParameter(IInventoryBizMetafield.UNIT_FACILITY_VISIT_KEYS.getFieldId(), (Serializable)inUfvGkeys);
        req.setParameter(IInventoryBizMetafield.ORDER_KEY.getFieldId(), (Serializable)((Object) String.valueOf(inBkgGkey)));
        if (inUpdateUnitWithItem != null) {
            req.setParameter(IInventoryBizMetafield.UPDATE_UNIT_WITH_ITEM_VALUES.getFieldId(), (Serializable)inUpdateUnitWithItem);
        }
        if (inEqoItemGkey != null) {
            req.setParameter(IInventoryBizMetafield.EQO_ITEM.getFieldId(), inEqoItemGkey);
        }
        return CrudDelegate.executeBizRequest((BizRequest)req, (String)"invAssignExportBookingByUfvs");
    }

    public FieldChanges getRectifyChanges(UfvTransitStateEnum inUfvTransitStateEnum, UnitVisitStateEnum inUnitVisitStateEnum) {
        FieldChanges rectifyChanges = new FieldChanges();
        rectifyChanges.setFieldChange(IInventoryField.UFV_TRANSIT_STATE, (Object)inUfvTransitStateEnum);
        rectifyChanges.setFieldChange(IInventoryField.UNIT_VISIT_STATE, (Object)inUnitVisitStateEnum);
        rectifyChanges.setFieldChange(IInventoryBizMetafield.ERASE_HISTORY, (Object) Boolean.TRUE);
        return rectifyChanges;
    }

    public BizResponse rectifyUnit(FieldChanges inRectifyChanges, Serializable[] inUfvGkeys) {
        return this.rectifyUnit(this.getTestUserContextAtYrd1111(), inRectifyChanges, inUfvGkeys);
    }

    public BizResponse rectifyUnit(UserContext inUc, FieldChanges inRectifyChanges, Serializable[] inUfvGkeys) {
        BizRequest rectifyReq = new BizRequest(inUc);
        CrudOperation rectifyCrudOperation = new CrudOperation(null, 1, "Unit", inRectifyChanges, (Object[])inUfvGkeys);
        rectifyReq.addCrudOperation(rectifyCrudOperation);
        return CrudDelegate.executeBizRequest((BizRequest)rectifyReq, (String)"invRectifyUnit");
    }

    public void assertUnitNotSame(Serializable inExistingUfvGkey, Serializable inAdvisedUfvGkey) {
        this.assertUnitNotSame(this.getTestUserContextAtYrd1111(), inExistingUfvGkey, inAdvisedUfvGkey);
    }

    public void assertUnitNotSame(UserContext inUc, Serializable inExistingUfvGkey, Serializable inAdvisedUfvGkey) {
        this.startHibernateWithUser(inUc);
        UnitFacilityVisit existingUfv = UnitFacilityVisit.hydrate(inExistingUfvGkey);
        Unit existingUnit = existingUfv.getUfvUnit();
        UnitFacilityVisit advisedUfv = UnitFacilityVisit.hydrate(inAdvisedUfvGkey);
        Unit advisedUnit = advisedUfv.getUfvUnit();
        AbstractInventoryTestCase.assertFalse((boolean)existingUnit.equals(advisedUnit));
        this.endHibernate();
    }

    public void assertUnitSame(UserContext inUc, Serializable inExistingUfvGkey, Serializable inAdvisedUfvGkey) {
        this.startHibernateWithUser(inUc);
        UnitFacilityVisit existingUfv = UnitFacilityVisit.hydrate(inExistingUfvGkey);
        Unit existingUnit = existingUfv.getUfvUnit();
        UnitFacilityVisit advisedUfv = UnitFacilityVisit.hydrate(inAdvisedUfvGkey);
        Unit advisedUnit = advisedUfv.getUfvUnit();
        AbstractInventoryTestCase.assertTrue((boolean)existingUnit.equals(advisedUnit));
        this.endHibernate();
    }

    public void assertUfvUnitState(Serializable inUfvGkey, UfvTransitStateEnum inUfvState, UnitVisitStateEnum inUnitVisitState) {
        this.assertUfvUnitState(this.getTestUserContextAtYrd1111(), inUfvGkey, inUfvState, inUnitVisitState);
    }

    public void assertUfvUnitState(UserContext inUc, Serializable inUfvGkey, UfvTransitStateEnum inUfvState, UnitVisitStateEnum inUnitVisitState) {
        this.startHibernateWithUser(inUc);
        UnitFacilityVisit ufv = UnitFacilityVisit.hydrate(inUfvGkey);
        AbstractInventoryTestCase.assertEquals((Object)((Object)inUfvState), (Object)((Object)ufv.getUfvTransitState()));
        Unit unit = ufv.getUfvUnit();
        AbstractInventoryTestCase.assertEquals((Object)((Object)inUnitVisitState), (Object)((Object)unit.getUnitVisitState()));
        this.endHibernate();
    }

    public BizResponse submit(FieldChanges inInput, String inApiName) {
        return this.submit(inInput, inApiName, this.getTestUserContextAtYrd1111());
    }

    public BizResponse submit(FieldChanges inInput, String inApiName, UserContext inUserContext) {
        BizRequest request = new BizRequest(inUserContext);
        CrudOperation crudOperation = new CrudOperation(null, 1, "", inInput, null);
        request.addCrudOperation(crudOperation);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)inApiName);
    }

    public BizResponse createTbdUnits(FieldChanges inTbdUnitChanges) {
        return this.createTbdUnits(this.getTestUserContextAtYrd1111(), inTbdUnitChanges);
    }

    public BizResponse createTbdUnits(UserContext inUserContext, FieldChanges inTbdUnitChanges) {
        BizRequest request = new BizRequest(inUserContext);
        CrudOperation crudOperation = new CrudOperation(null, 1, "TbdUnit", inTbdUnitChanges, null);
        request.addCrudOperation(crudOperation);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invCreateTbdUnits");
    }

    public void assertUfvPosition(@NotNull Serializable inUfvGkey, LocTypeEnum inLocTypeEnum) {
        this.assertUfvPosition(this.getTestUserContextAtYrd1111(), inUfvGkey, inLocTypeEnum);
    }

    public void assertUfvPosition(UserContext inUc, @NotNull Serializable inUfvGkey, LocTypeEnum inLocTypeEnum) {
        this.startHibernateWithUser(inUc);
        UnitFacilityVisit ufv = UnitFacilityVisit.hydrate(inUfvGkey);
        LocPosition pos = ufv.getUfvLastKnownPosition();
        AbstractInventoryTestCase.assertEquals((Object)inLocTypeEnum, (Object)pos.getPosLocType());
        this.endHibernate();
    }

    public void assertUnitIbCarrierMode(@NotNull Serializable inUfvGkey, LocTypeEnum inLocTypeEnum) {
        this.assertUnitIbCarrierMode(this.getTestUserContextAtYrd1111(), inUfvGkey, inLocTypeEnum);
    }

    public void assertUnitIbCarrierMode(UserContext inUc, @NotNull Serializable inUfvGkey, LocTypeEnum inLocTypeEnum) {
        this.startHibernateWithUser(inUc);
        UnitFacilityVisit ufv = UnitFacilityVisit.hydrate(inUfvGkey);
        Unit unit = ufv.getUfvUnit();
        AbstractInventoryTestCase.assertTrue((boolean)inLocTypeEnum.equals((Object)unit.getUnitDeclaredIbCv().getCvCarrierMode()));
        this.endHibernate();
    }

    public BizResponse assignUnitBillOfLading(UserContext inUc, @NotNull Serializable inUfvGkey, Serializable inBlGKey) {
        BizRequest request = new BizRequest(inUc);
        request.setParameter(IInventoryBizMetafield.UNIT_FACILITY_VISIT_KEY.getFieldId(), inUfvGkey);
        request.setParameter(IInventoryBizMetafield.BILL_OF_LADING_GKEY.getFieldId(), inBlGKey);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invAssignUnitBillOfLading");
    }

    public BizResponse assignUnitBillOfLading(@NotNull Serializable inUfvGkey, Serializable inBlGKey) {
        return this.assignUnitBillOfLading(this.getTestUserContextAtYrd1111(), inUfvGkey, inBlGKey);
    }

    public CarrierItinerary createCarrierItinerary(String inCurrentPortId, String inItineraryId) {
        CarrierItinerary itinerary = CarrierItinerary.findOrCreateCarrierItinerary((String)inItineraryId, null);
        itinerary.addCarrierCall("HJH", "1", "H", new String[]{"NYC", "BOS"});
        itinerary.addCarrierCall(inCurrentPortId, "1", "L", new String[]{"NYC", "BOS"});
        itinerary.addCarrierCall("ADL", "1", "A", new String[]{"NYC", "BOS"});
        itinerary.addCarrierCall("LAX", "1", "B", new String[]{"NYC", "BOS"});
        itinerary.addCarrierCall("LAX", "1", "H", new String[]{"NYC", "BOS"});
        itinerary.addCarrierCall("LYT", "1", "H", new String[]{"NYC", "BOS"});
        return itinerary;
    }

    public FieldChanges getDischargeFcs(Serializable inVisitDetailsGkey, String inCtrId, String inYardSlot) {
        FieldChanges dischChanges = new FieldChanges();
        dischChanges.setFieldChange(IInventoryBizMetafield.UNIT_DISCH_VESSEL, (Object)inVisitDetailsGkey);
        dischChanges.setFieldChange(IInventoryBizMetafield.UNIT_DIGITS, (Object)inCtrId);
        dischChanges.setFieldChange(IInventoryBizMetafield.UNIT_YARD_SLOT, (Object)inYardSlot);
        return dischChanges;
    }

    public FieldChanges getDefaultDischargeFcs(Long inVisitDetailsGkey, String inCtrId) {
        return this.getDefaultDischargeFcs(this.getTestUserContextAtYrd1111(), inVisitDetailsGkey, inCtrId);
    }

    public FieldChanges getDefaultDischargeFcs(UserContext inUc, Long inCvGkey, String inCtrId) {
        this.startHibernateWithUser(inUc);
        CarrierVisit carierVisit = CarrierVisit.loadByGkey((Long)inCvGkey);
        CarrierVisitPhaseEnum visitPhase = CarrierVisitPhaseEnum.ARRIVED;
        if (carierVisit.isValidPhaseTransition(visitPhase)) {
            carierVisit.safelyUpdateVisitPhase(visitPhase);
        }
        Long vvDetailsGkey = carierVisit.getCvCvd().getCvdGkey();
        this.endHibernate();
        return this.getDischargeFcs(vvDetailsGkey, inCtrId, "DC");
    }

    public FieldChanges getDefaultDischargeFcs(String inCvId, String inCtrId) {
        return this.getDefaultDischargeFcs(this.getTestUserContextAtYrd1111(), inCvId, inCtrId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FieldChanges getDefaultDischargeFcs(UserContext inUc, String inCvId, String inCtrId) {
        Long vvDetailsGkey = null;
        try {
            this.startHibernateWithUser(inUc);
            CarrierVisit carierVisit = CarrierVisit.findCarrierVisit((Facility) ContextHelper.getThreadFacility(), (LocTypeEnum) LocTypeEnum.VESSEL, (String)inCvId);
            CarrierVisitPhaseEnum visitPhase = CarrierVisitPhaseEnum.ARRIVED;
            if (carierVisit.isValidPhaseTransition(visitPhase)) {
                carierVisit.safelyUpdateVisitPhase(visitPhase);
            }
            vvDetailsGkey = carierVisit.getCvCvd().getCvdGkey();
        }
        catch (BizViolation inBizViolation) {
            this.failOnException("", (Exception)((Object)inBizViolation));
        }
        finally {
            this.endHibernate();
        }
        return this.getDischargeFcs(vvDetailsGkey, inCtrId, "DC");
    }

    public BizResponse dischargeFromVessel(UserContext inUc, String inCvId, String inCtrId) {
        FieldChanges dischChanges = this.getDefaultDischargeFcs(inCvId, inCtrId);
        BizRequest dischRequest = new BizRequest(inUc);
        CrudOperation crudOperation = new CrudOperation(null, 1, "Unit", dischChanges, null);
        dischRequest.addCrudOperation(crudOperation);
        return CrudDelegate.executeBizRequest((BizRequest)dischRequest, (String)"invDischargeFromVessel");
    }

    public BizResponse dischargeFromVessel(String inCvId, String inCtrId) {
        return this.dischargeFromVessel(this.getTestUserContextAtYrd1111(), inCvId, inCtrId);
    }

    public BizResponse dischargeFromVessel(FieldChanges inDischChanges) {
        return this.dischargeFromVessel(this.getTestUserContextAtYrd1111(), inDischChanges);
    }

    public BizResponse dischargeFromVessel(UserContext inUc, FieldChanges inDischChanges) {
        BizRequest dischRequest = new BizRequest(inUc);
        CrudOperation crudOperation = new CrudOperation(null, 1, "Unit", inDischChanges, null);
        dischRequest.addCrudOperation(crudOperation);
        return CrudDelegate.executeBizRequest((BizRequest)dischRequest, (String)"invDischargeFromVessel");
    }

    public BizResponse offHireOnHireByUfvs(@NotNull Serializable[] inUfvGkeys, Serializable inLineGkey, Date inEvntAppliedDate) {
        return this.offHireOnHireByUfvs(this.getTestUserContextAtYrd1111(), inUfvGkeys, inLineGkey, inEvntAppliedDate);
    }

    public BizResponse offHireOnHireByUfvs(UserContext inUc, @NotNull Serializable[] inUfvGkeys, Serializable inLineGkey, Date inEvntAppliedDate) {
        BizRequest request = new BizRequest(inUc);
        request.setParameter(IInventoryBizMetafield.UNIT_FACILITY_VISIT_KEYS.getFieldId(), (Serializable)inUfvGkeys);
        request.setParameter(UnitField.UFV_LINE_OPERATOR.getFieldId(), inLineGkey);
        request.setParameter(IServicesField.EVNT_APPLIED_DATE.getFieldId(), (Serializable)inEvntAppliedDate);
        return this.offHireOnHireByUfvs(inUc, inUfvGkeys, inLineGkey, inEvntAppliedDate, null, null);
    }

    public BizResponse offHireOnHireByUfvs(@NotNull Serializable[] inUfvGkeys, Serializable inLineGkey, Date inEvntAppliedDate, Serializable inEdoGkey, Serializable inEroGkey) {
        return this.offHireOnHireByUfvs(this.getTestUserContextAtYrd1111(), inUfvGkeys, inLineGkey, inEvntAppliedDate, inEdoGkey, inEroGkey);
    }

    public BizResponse offHireOnHireByUfvs(UserContext inUc, @NotNull Serializable[] inUfvGkeys, Serializable inLineGkey, Date inEvntAppliedDate, Serializable inEdoGkey, Serializable inEroGkey) {
        BizRequest request = new BizRequest(inUc);
        request.setParameter(IInventoryBizMetafield.UNIT_FACILITY_VISIT_KEYS.getFieldId(), (Serializable)inUfvGkeys);
        request.setParameter(UnitField.UFV_LINE_OPERATOR.getFieldId(), inLineGkey);
        request.setParameter(IServicesField.EVNT_APPLIED_DATE.getFieldId(), (Serializable)inEvntAppliedDate);
        if (inEdoGkey != null) {
            request.setParameter(IInventoryBizMetafield.EDO_NBR.getFieldId(), inEdoGkey);
        }
        if (inEroGkey != null) {
            request.setParameter(IInventoryBizMetafield.IDO_UNIT_TABLE_KEY.getFieldId(), (Serializable)((Object)inEroGkey.toString()));
        }
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invOffHireOnHireByUfvs");
    }

    public Serializable createUnitInYard(String inCtrId, UnitCategoryEnum inCategoryEnum, FreightKindEnum inKindEnum) {
        return this.createUnit(inCtrId, inCategoryEnum, inKindEnum, UfvTransitStateEnum.S40_YARD);
    }

    public Serializable createUnit(final String inCtrId, final UnitCategoryEnum inCategoryEnum, final FreightKindEnum inKindEnum, final UfvTransitStateEnum inStateEnum) {
        final Serializable[] yrdUfvGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    UnitFacilityVisit ufv = null;
                    ufv = AbstractInventoryTestCase.this.makeUfv(inCtrId, inStateEnum, inCategoryEnum);
                    Unit unit = ufv.getUfvUnit();
                    unit.updateFreightKind(inKindEnum);
                    yrdUfvGkey[0] = ufv.getUfvGkey();
                }
                catch (BizViolation inBizViolation) {
                    AbstractInventoryTestCase.this.failOnException("", (Exception)((Object)inBizViolation));
                }
            }
        });
        return yrdUfvGkey[0];
    }

    public FieldChanges getDefaultLineOperatorChanges() {
        this.startHibernateAtYard1111();
        ScopedBizUnit lop = this.getTestLineOp2();
        AbstractInventoryTestCase.assertNotNull((Object)lop);
        Long lopGkey = lop.getBzuGkey();
        this.endHibernate();
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(UnitField.UFV_LINE_OPERATOR, (Object)lopGkey);
        return changes;
    }

    public BizResponse updateLineOperator(Serializable[] inUfvGkeys) {
        return this.updateLineOperator(this.getDefaultLineOperatorChanges(), inUfvGkeys);
    }

    public BizResponse updateLineOperator(FieldChanges inUpdateChanges, Serializable[] inUfvGkeys) {
        return this.updateLineOperator(this.getTestUserContextAtYrd1111(), inUpdateChanges, inUfvGkeys);
    }

    public BizResponse updateLineOperator(UserContext inUc, FieldChanges inUpdateChanges, Serializable[] inUfvGkeys) {
        BizRequest request = new BizRequest(inUc);
        CrudOperation crud = new CrudOperation(null, 2, "UnitFacilityVisit", inUpdateChanges, (Object[])inUfvGkeys);
        request.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)request, (String)"invUpdateLineOperator");
    }

    public IServicesManager getServiceManager() {
        return (IServicesManager) Roastery.getBean((String)"servicesManager");
    }

    public EventManager getEventManager() {
        return (EventManager) Roastery.getBean((String)"eventManager");
    }

    @Nullable
    public EventType getEvnttyp(IEventType inEventType) {
        return EventType.resolveIEventType((IEventType)inEventType);
    }

    public void assertUnitCount(String inCtrId, int inExpectedCount) {
        this.assertUnitCount(this.getTestUserContextAtYrd1111(), inCtrId, inExpectedCount);
    }

    public void assertUnitCount(UserContext inUc, String inCtrId, int inExpectedCount) {
        this.startHibernateWithUser(inUc);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Unit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.UNIT_ID, (Object)inCtrId));
        int count = this._hibernateApi.findCountByDomainQuery(dq);
        AbstractInventoryTestCase.assertEquals((int)inExpectedCount, (int)count);
        this.endHibernate();
    }

    public ChargeableUnitEvent findByUfvGkeyAndEventType(Serializable inUfvGkey, String inEqId, String inEventType) {
        return this.findByUfvGkeyAndEventType(inUfvGkey, inEqId, inEventType, Boolean.FALSE);
    }

    public ChargeableUnitEvent findByUfvGkeyAndEventType(Serializable inUfvGkey, String inEqId, String inEventType, Boolean inForceFind) {
        final IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_UFV_GKEY, (Object)inUfvGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EQ_ID, (Object)inEqId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inEventType));
        ChargeableUnitEvent cue = (ChargeableUnitEvent)new EntityResolver(){

            public IEntity executeStrategy() {
                return AbstractInventoryTestCase.this._hibernateApi.getUniqueEntityByDomainQuery(dq);
            }
        }.resolveEntity();
        if (cue == null && inForceFind.booleanValue()) {
            this.extractUnitEvents(new Serializable[]{inUfvGkey}, inEventType);
            cue = (ChargeableUnitEvent)this._hibernateApi.getUniqueEntityByDomainQuery(dq);
        }
        return cue;
    }

    public void extractUnitEvents(Serializable[] inUfvGkeys, String inEventType) {
        try {
            this.setCueSetting(inEventType);
            this.getEventExtractor().extractUnitEvents(inUfvGkeys);
        }
        catch (BizViolation inBizViolation) {
            this.failOnException("", (Exception)((Object)inBizViolation));
        }
    }

    public IUnitEventExtractor getEventExtractor() {
        return (IUnitEventExtractor) Roastery.getBean((String)"unitEventExtractor");
    }

    public List<Guarantee> findGuaranteesForUfv(UnitFacilityVisit inUfv) {
//        List<Object> gnteList = new ArrayList<Guarantee>();
//        ChargeableUnitEvent cue = this.findByUfvGkeyAndEventType(inUfv.getUfvGkey(), inUfv.getUfvUnit().getUnitId(), ChargeableUnitEventTypeEnum.STORAGE.getKey());
//        if (cue != null) {
//            gnteList = Guarantee.findGuaranteesForCue((ChargeableUnitEvent)cue);
//        }
//        return gnteList;
        return null;
    }

    public IValueHolder createRule(String inMetafieldId, Object inValue, String inSruleId, Long inSruleGkey, int inAmountOfFreeDays, IValueHolder inSibling) {
        ValueObject vao = new ValueObject("EntityMappingPredicate");
        FieldValue fv1 = new FieldValue(IInventoryBizMetafield.STORAGE_RULE_TABLE_KEY, (Object)inSruleGkey, inSruleId);
        FieldValue fv2 = new FieldValue(IInventoryBizMetafield.UFV_FREE_DAYS_FOR_RULE, (Object)inAmountOfFreeDays, String.valueOf(inAmountOfFreeDays));
        FieldValue[] fv = new FieldValue[]{fv1, fv2};
        vao.setFieldValue(IArgoField.EMAPP_METAFIELD, (Object) MetafieldIdFactory.valueOf((String)inMetafieldId));
        vao.setFieldValue(IArgoField.EMAPP_VERB, (Object) PredicateVerbEnum.EQ);
        vao.setFieldValue(IArgoField.EMAPP_VALUE, inValue);
        vao.setFieldValue(IArgoField.EMAPP_NEGATED, (Object) Boolean.FALSE);
        vao.setFieldValue(IArgoField.EMAPP_UI_VALUE, (Object)"ui value");
        vao.setFieldValue(IArgoField.EMAPP_NEXT_PREDICATE, (Object)inSibling);
        vao.setFieldValue(IArgoField.EMAPP_SUB_PREDICATE, null);
        vao.setFieldValue(IArgoBizMetafield.EMAPP_MAPPED_VALUES, (Object)fv);
        return vao;
    }

    public IMessageCollector createHoldOrPermission(final String inEvntId, final String inFlgTypId, final String inSrvcRuleId, final ServiceRuleTypeEnum inServiceRuleTypeEnum, final boolean inIsHoldType, final boolean isOnEquipment) {
        final Serializable[] hpvGkey = new Serializable[1];
//        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){
//
//            public void doInTransaction() {
//                HoldPermissionView hpv = HoldPermissionView.findHoldPermissionView((String)inFlgTypId);
//                if (hpv == null) {
//                    hpv = HoldPermissionView.createHoldPermissionView((String)inFlgTypId);
//                }
//                Assert.assertNotNull((Object)hpv);
//                hpvGkey[0] = hpv.getHpvGkey();
//            }
//        });
//        IMessageCollector mc = this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){
//
//            public void doInTransaction() {
//                HoldPermissionView hpv = (HoldPermissionView)AbstractInventoryTestCase.this._hibernateApi.load(HoldPermissionView.class, hpvGkey[0]);
//                Assert.assertNotNull((Object)hpv);
//                LogicalEntityEnum entityEnum = isOnEquipment ? LogicalEntityEnum.EQ : LogicalEntityEnum.UNIT;
//                EventType testEventType = EventType.findOrCreateEventType((String)inEvntId, (String)inEvntId, (LogicalEntityEnum)entityEnum, null);
//                FlagType holdOrPermFlagType = ServiceTestUtils.findOrCreateFlagType((String)inFlgTypId, (String)"flag", (boolean)inIsHoldType, (LogicalEntityEnum)entityEnum);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_APPLY_EVENT_TYPE, (Object)testEventType);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_HOLD_PERM_VIEW, (Object)hpv);
//                ServiceTestUtils.findOrCreateServiceRule((String)inSrvcRuleId, (ServiceRuleTypeEnum)inServiceRuleTypeEnum, null, null, (EventType)testEventType, (FlagType)holdOrPermFlagType);
//            }
//        });
//        return mc;
        return null;
    }

    public IMessageCollector createHoldOrPermission(final String inEvntId, final String inFlgTypId, final String inSrvcRuleId, final ServiceRuleTypeEnum inServiceRuleTypeEnum, final boolean inIsHoldType, final LogicalEntityEnum inLogicalEntity) {
        final Serializable[] hpvGkey = new Serializable[1];
//        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){
//
//            public void doInTransaction() {
//                HoldPermissionView hpv = HoldPermissionView.findHoldPermissionView((String)inFlgTypId);
//                if (hpv == null) {
//                    hpv = HoldPermissionView.createHoldPermissionView((String)inFlgTypId);
//                }
//                Assert.assertNotNull((Object)hpv);
//                hpvGkey[0] = hpv.getHpvGkey();
//            }
//        });
//        IMessageCollector mc = this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){
//
//            public void doInTransaction() {
//                HoldPermissionView hpv = (HoldPermissionView)AbstractInventoryTestCase.this._hibernateApi.load(HoldPermissionView.class, hpvGkey[0]);
//                Assert.assertNotNull((Object)hpv);
//                EventType testEventType = EventType.findOrCreateEventType((String)inEvntId, (String)inEvntId, (LogicalEntityEnum)inLogicalEntity, null);
//                FlagType holdOrPermFlagType = ServiceTestUtils.findOrCreateFlagType((String)inFlgTypId, (String)"flag", (boolean)inIsHoldType, (LogicalEntityEnum)inLogicalEntity);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_APPLY_EVENT_TYPE, (Object)testEventType);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_HOLD_PERM_VIEW, (Object)hpv);
//                ServiceTestUtils.findOrCreateServiceRule((String)inSrvcRuleId, (ServiceRuleTypeEnum)inServiceRuleTypeEnum, null, null, (EventType)testEventType, (FlagType)holdOrPermFlagType);
//            }
//        });
//        return mc;
        return null;
    }

    public FieldChanges getDefaultWavierChanges() {
        FieldChanges wavierFcs = new FieldChanges();
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_QUANTITY, (Object)12000.0);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_AMOUNT, (Object)120.0);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object) GuaranteeTypeEnum.WAIVER);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object) GuaranteeOverrideTypeEnum.FREE_NOCHARGE);
        return wavierFcs;
    }

    public FieldChanges getWavierChanges(Serializable inExtractGkey, Serializable inLineGkey, String inCtrId, Date inWaiverStartDay, Date inWaiverEndDay, Date inWavierExpirationDay) {
        FieldChanges wavierFcs = this.getDefaultWavierChanges();
        wavierFcs.setFieldChange(IInventoryBizMetafield.EXTRACT_EVENT_TYPE, (Object)inExtractGkey);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_CUSTOMER, (Object)inLineGkey);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_APPLIED_TO_NATURAL_KEY, (Object)inCtrId);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_START_DAY, (Object)inWaiverStartDay);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_END_DAY, (Object)inWaiverEndDay);
        wavierFcs.setFieldChange(IArgoExtractField.GNTE_WAIVER_EXPIRATION_DATE, (Object)inWavierExpirationDay);
        return wavierFcs;
    }

    public FieldChanges getGuaraneeChanges(Serializable inExtractGkey, Serializable inLineGkey, String inCtrId, Date inGnteStartDay, Date inGnteEndDay) {
        FieldChanges guaranteeFcs = this.getDefaultGuaraneeChanges();
        guaranteeFcs.setFieldChange(IInventoryBizMetafield.EXTRACT_EVENT_TYPE, (Object)inExtractGkey);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_CUSTOMER, (Object)inLineGkey);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_APPLIED_TO_NATURAL_KEY, (Object)inCtrId);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_START_DAY, (Object)inGnteStartDay);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_END_DAY, (Object)inGnteEndDay);
        return guaranteeFcs;
    }

    public FieldChanges getDefaultGuaraneeChanges() {
        FieldChanges guaranteeFcs = new FieldChanges();
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object) GuaranteeTypeEnum.OAC);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_QUANTITY, (Object)12000.0);
        guaranteeFcs.setFieldChange(IArgoExtractField.GNTE_GUARANTEE_AMOUNT, (Object)120.0);
        return guaranteeFcs;
    }

    public BizResponse recordWavier(UserContext inUserContext, FieldChanges inWavierFcs) {
        CrudOperation wavierCrud = new CrudOperation(null, 1, "Guarantee", inWavierFcs, null);
        BizRequest recordWavierReq = new BizRequest(this.getTestUserContextAtYrd1111());
        recordWavierReq.addCrudOperation(wavierCrud);
        return CrudDelegate.executeBizRequest((BizRequest)recordWavierReq, (String)"invRecordWaiverForUfv");
    }

    public BizResponse recordWavier(FieldChanges inWavierFcs) {
        return this.recordWavier(this.getTestUserContextAtYrd1111(), inWavierFcs);
    }

    public BizResponse recordGuarantee(FieldChanges inGuaranteeFcs) {
        return this.recordGuarantee(this.getTestUserContextAtYrd1111(), inGuaranteeFcs);
    }

    public BizResponse recordGuarantee(UserContext inUserContext, FieldChanges inGuaranteeFcs) {
        CrudOperation crud = new CrudOperation(null, 1, "Guarantee", inGuaranteeFcs, null);
        BizRequest recordGnteReq = new BizRequest(this.getTestUserContextAtYrd1111());
        recordGnteReq.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)recordGnteReq, (String)"invRecordGuaranteeForUfv");
    }

    public BizResponse updateGuarantee(UserContext inUserContext, Serializable[] inGnteGkeys, FieldChanges inGuaranteeFcs) {
        CrudOperation crud = new CrudOperation(null, 2, "Guarantee", inGuaranteeFcs, (Object[])inGnteGkeys);
        BizRequest recordGnteReq = new BizRequest(this.getTestUserContextAtYrd1111());
        recordGnteReq.addCrudOperation(crud);
        return CrudDelegate.executeBizRequest((BizRequest)recordGnteReq, (String)"invUpdateGuarantee");
    }

    public BizResponse updateGuarantee(Serializable[] inGnteGkeys, FieldChanges inGuaranteeFcs) {
        return this.updateGuarantee(this.getTestUserContextAtYrd1111(), inGnteGkeys, inGuaranteeFcs);
    }

    public void findOrCreateEventType(String inEvntId, String inEventDescription, LogicalEntityEnum inEntityType, SavedPredicate inPredicate) {
        EventType.findOrCreateEventType((String)inEvntId, (String)inEventDescription, (LogicalEntityEnum)inEntityType, (SavedPredicate)inPredicate);
    }

    public String getEntityExtractXmlFromClassPath() {
        return this.loadFileAsStringFromClasspath("UnitExtractMap.xml");
    }

    public String getEntityExtractNoUpdateXmlFromClassPath() {
        return this.loadFileAsStringFromClasspath("UnitExtractNoUpdateMap.xml");
    }

    public String getEntityExtractFlexFieldXmlFromClassPath() {
        return this.loadFileAsStringFromClasspath("UnitExtractFlexFieldMap.xml");
    }

    public String constructStorageUfv(UnitFacilityVisit inUfv) {
        StringBuilder sb = new StringBuilder();
        try {
            IValueHolder strgDto = inUfv.getUfvStorageCalcDto();
            MetafieldIdList strgFieldList = strgDto.getFields();
            for (IMetafieldId mfId : strgFieldList) {
                IMetafield singleMf = Roastery.getMetafieldDictionary().findMetafield(mfId);
                String label = null;
                if (singleMf != null) {
                    label = TranslationUtils.getTranslationContext((UserContext) ContextHelper.getThreadUserContext()).getMessageTranslator().getMessage(singleMf.getShortLabelKey());
                }
                label = label == null ? mfId.getFieldId() : label;
                sb.append(label).append(" : ").append(strgDto.getFieldValue(mfId)).append("\n");
            }
        }
        catch (BizViolation inBizViolation) {
            LOGGER.error((Object)inBizViolation.getLocalizedMessage());
        }
        List<Guarantee> gnteList = this.findGuaranteesForUfv(inUfv);
        for (Guarantee guarantee : gnteList) {
            sb.append("With following Guaratnees:");
            sb.append((Object)guarantee).append("\n");
        }
        return sb.toString();
    }

    public void neutralizeSetting(IConfig inConfig) {
        this.startHibernateAtYard1111();
        this.setSetting(inConfig, null);
        this.endHibernate();
        this.startHibernateAtCpx11();
        this.setSetting(inConfig, null);
        this.endHibernate();
        this.startHibernateAtFcy111();
        this.setSetting(inConfig, null);
        this.endHibernate();
        this.startHibernateWithUser(this.getTestUserContextAtOpr1());
        this.setSetting(inConfig, null);
        this.endHibernate();
        this.startHibernateWithGlobalUser();
        this.setSetting(inConfig, null);
        this.endHibernate();
    }

    public Serializable createUser(final String inUserId) {
        final Serializable[] userGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                ArgoUser user = ArgoUser.findOrCreateArgoUser((String)inUserId, (String)inUserId, (String)"first", (String)"last");
                user.setScope(AbstractInventoryTestCase.this.getOpr1().getId(), AbstractInventoryTestCase.this.getCpx11().getId(), AbstractInventoryTestCase.this.getFcy111().getId(), AbstractInventoryTestCase.this.getYrd1111().getId());
                userGkey[0] = user.getPrimaryKey();
                user.setBuserActive("Y");
            }
        });
        return userGkey[0];
    }

    public Serializable createRole(final String inRoleId, final IPrivilege[] inArgoPrivs) {
        final Serializable[] roleGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                ArgoSecRole role = ArgoSecRole.findOrCreateSecRole((String)inRoleId);
                role.setRoleSecName(inRoleId);
                role.setRoleDescription(inRoleId);
                role.setRoleCreator("admin");
                role.setArgroleOperator(ContextHelper.getThreadOperator());
                for (IPrivilege inprivilege : inArgoPrivs) {
                    role.addPrivilege(inprivilege);
                }
                roleGkey[0] = role.getRoleGkey();
            }
        });
        return roleGkey[0];
    }

    public void purgeUserAndRole(final String inUserId, final String inRoleId) {
        try {
            HibernateApi.getInstance().setObsoletableDeleteEnabled(false);
            this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    BaseUser user = BaseUser.findBaseUser((String)inUserId);
                    if (user != null) {
                        HibernateApi.getInstance().delete((Object)user);
                    }
                }
            });
            this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    ArgoSecRole role = ArgoSecRole.findArgoSecRole((String)inRoleId);
                    if (role != null) {
                        HibernateApi.getInstance().delete((Object)role);
                    }
                }
            });
        }
        finally {
            HibernateApi.getInstance().setObsoletableDeleteEnabled(true);
        }
    }

    public void purgeBizGroup(final String inBizGroup) {
        try {
            HibernateApi.getInstance().setObsoletableDeleteEnabled(false);
            this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

                public void doInTransaction() {
                    BizGroup bizGroup = BizGroup.find((String)inBizGroup);
                    if (bizGroup != null) {
                        HibernateApi.getInstance().delete((Object)bizGroup);
                    }
                }
            });
        }
        finally {
            HibernateApi.getInstance().setObsoletableDeleteEnabled(true);
        }
    }

    public Serializable createUser(String inUserId, Serializable inOperGkey) {
        return this.createUser(inUserId, inOperGkey, null);
    }

    public Serializable createUser(String inUserId, Serializable inOperGkey, Serializable inBizgrpGkey) {
        return this.createUser(inUserId, inOperGkey, inBizgrpGkey, null);
    }

    public Serializable createUser(String inUserId, Serializable inOperGkey, Serializable inBizgrpGkey, Serializable inAgentGkey) {
        Serializable userGkey = this.createUser(inUserId);
        this.startHibernateAtYard1111();
        ArgoUser user = (ArgoUser)this._hibernateApi.load(ArgoUser.class, userGkey);
        if (inBizgrpGkey != null) {
            BizGroup bizGroup = (BizGroup)this._hibernateApi.load(BizGroup.class, inBizgrpGkey);
            user.assignBizGroup(bizGroup);
        }
        ScopedBizUnit bizUnit = null;
        if (inOperGkey != null) {
            bizUnit = (ScopedBizUnit)this._hibernateApi.load(ScopedBizUnit.class, inOperGkey);
        }
        if (inAgentGkey != null) {
            bizUnit = (ScopedBizUnit)this._hibernateApi.load(ScopedBizUnit.class, inAgentGkey);
        }
        if (bizUnit != null) {
            user.setFieldValue(IUserArgoField.ARGOUSER_COMPANY_BIZ_UNIT, (Object)bizUnit);
        }
        this.endHibernate();
        return userGkey;
    }

    public void modifyPrivRoleContraint(String inNewPrivId, BizRoleEnum inRole) {
        this.startHibernateAtYard1111();
        FieldChanges fcs = new FieldChanges();
        fcs.setFieldChange(IArgoField.ROPRV_OPERATOR, (Object)this.getOpr1().getOprGkey());
        fcs.setFieldChange(IArgoField.ROPRV_ROLE, (Object)inRole);
        ArgoBizRolePrivilege priv = ArgoBizRolePrivilege.findArgoRolePrivilegeById((String)inNewPrivId);
        if (priv == null) {
            ArgoBizRolePrivilege.createArgoRolePrivilege((String)inNewPrivId, (BizRoleEnum)inRole, (Long)((Long) ContextHelper.getThreadOperatorKey()));
        }
//        ArgoBizRolePrivilegeManager privMgr = (ArgoBizRolePrivilegeManager)Roastery.getBean((String)"argoBizRolePrivilegeManager");
//        ArgoPrivilege privilege = new ArgoPrivilege(inNewPrivId, ContextHelper.getThreadOperatorKey(), null, null, null);
//        privMgr.addPrivilege(privilege);
        this.endHibernate();
    }

    public Serializable createRole(String inRoleId) {
        this.startHibernateAtYard1111();
        ArgoSecRole role = ArgoSecRole.findOrCreateSecRole((String)inRoleId);
        role.setRoleSecName(inRoleId);
        role.setRoleDescription(inRoleId);
        role.setRoleCreator("admin");
        role.setArgroleOperator(ContextHelper.getThreadOperator());
        Long roleGkey = role.getRoleGkey();
        this.endHibernate();
        return roleGkey;
    }

    public void addConstraintPrivilegeToRole(Serializable inRoleGkey, String inNewPrivId) {
        this.startHibernateAtYard1111();
        ArrayList<String> privsList = new ArrayList<String>();
        privsList.add(inNewPrivId);
        ArgoSecRole role = (ArgoSecRole) HibernateApi.getInstance().load(ArgoSecRole.class, inRoleGkey);
        String[] privs = privsList.toArray(new String[privsList.size()]);
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES, (Object)role.getRolePrivileges(), (Object)privs);
        role.applyFieldChanges(changes);
        this.endHibernate();
    }

    public void assignUserToRole(final Serializable inUserGkey, final Serializable inRoleGkey) {
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                SecRole role = (SecRole)AbstractInventoryTestCase.this._hibernateApi.load(SecRole.class, inRoleGkey);
                ArgoUser user = ArgoUser.findByPrimaryKey((Serializable)inUserGkey);
                HashSet<ArgoUser> userList = new HashSet<ArgoUser>();
                userList.add(user);
                role.setRoleBuserList(userList);
            }
        });
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                SecRole role = (SecRole)AbstractInventoryTestCase.this._hibernateApi.load(SecRole.class, inRoleGkey);
                ArgoUser user = ArgoUser.findByPrimaryKey((Serializable)inUserGkey);
                HashSet<SecRole> roles = new HashSet<SecRole>();
                roles.add(role);
                user.setBuserRoleList(roles);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void attachHazardsToUnit(String inPrimaryEqId, Serializable inHzrdGkey) {
        this.startHibernateAtYard1111();
        Hazards haz = Hazards.hydrate(inHzrdGkey);
        try {
            Unit unit = this.findOrCreatePreadvisedRoadUnit(inPrimaryEqId);
            unit.attachHazards(haz);
        }
        catch (BizViolation inBizViolation) {
            this.failOnException("", (Exception)((Object)inBizViolation));
        }
        finally {
            this.endHibernate();
        }
    }

    public void attachHazardsToUnit(Unit inUnit, Serializable inHzrdGkey) {
        this.startHibernateAtYard1111();
        Hazards haz = Hazards.hydrate(inHzrdGkey);
        inUnit = Unit.hydrate(inUnit.getUnitGkey());
        inUnit.attachHazards(haz);
        this.endHibernate();
    }

    public LocPosition createYardPos(Unit inUnit, String inSlot) {
        try {
            return LocPosition.createYardPosition((Yard) ContextHelper.getThreadYard(), (String)inSlot, (String)"1", (EquipBasicLengthEnum)inUnit.getBasicLength(), (boolean)false);
        }
        catch (BizViolation inBizViolation) {
            this.failOnException("", (Exception)((Object)inBizViolation));
            return null;
        }
    }

    @Nullable
    private Container createContainer(String inContainerId) {
        return this.createContainer(inContainerId, "2200");
    }

    private Unit findOrCreatePreadvisedRoadUnit(String inPrimaryEqId) throws BizViolation {
        return this.findOrCreatePreadvisedRoadUnit((Equipment)this.createContainer(inPrimaryEqId));
    }

    private void setCueSetting(String inEventType) {
        if (ChargeableUnitEventTypeEnum.STORAGE.getKey().equals(inEventType)) {
            this.setStorageCueCpx();
        } else {
            this.setSetting((IConfig) InventoryConfig.BILLING_CREATE_REEFER, Boolean.TRUE, this.getTestUserContextAtCpx11());
        }
    }

    public IMessageCollector createHoldOrPermission(String[] inUnitIds, String inEvntId, String inFlgTypId, String inSrvcRuleId, ServiceRuleTypeEnum inServiceRuleTypeEnum, boolean inIsHoldType, boolean isOnEquipment) {
        return this.createHoldOrPermission(inUnitIds, inEvntId, inFlgTypId, inSrvcRuleId, inServiceRuleTypeEnum, inIsHoldType, isOnEquipment, false);
    }

    public IMessageCollector createHoldOrPermission(final String[] inUnitIds, final String inEvntId, final String inFlgTypId, final String inSrvcRuleId, final ServiceRuleTypeEnum inServiceRuleTypeEnum, final boolean inIsHoldType, final boolean isOnEquipment, final boolean inIsFilterReq) {
        final Serializable[] hpvGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                HoldPermissionView hpv = HoldPermissionView.findHoldPermissionView((String)inFlgTypId);
                if (hpv == null) {
                    hpv = HoldPermissionView.createHoldPermissionView((String)inFlgTypId);
                }
                Assert.assertNotNull((Object)hpv);
                hpvGkey[0] = hpv.getHpvGkey();
            }
        });
        IMessageCollector mc = this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                HoldPermissionView hpv = (HoldPermissionView)AbstractInventoryTestCase.this._hibernateApi.load(HoldPermissionView.class, hpvGkey[0]);
                Assert.assertNotNull((Object)hpv);
                LogicalEntityEnum entityEnum = isOnEquipment ? LogicalEntityEnum.EQ : LogicalEntityEnum.UNIT;
                EventType testEventType = EventType.findOrCreateEventType((String)inEvntId, (String)inEvntId, (LogicalEntityEnum)entityEnum, null);
//                FlagType holdOrPermFlagType = ServiceTestUtils.findOrCreateFlagType((String)inFlgTypId, (String)"flag", (boolean)inIsHoldType, (LogicalEntityEnum)entityEnum);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_APPLY_EVENT_TYPE, (Object)testEventType);
//                holdOrPermFlagType.setFieldValue(IServicesField.FLGTYP_HOLD_PERM_VIEW, (Object)hpv);
//                ValueObject vao = UniversalQueryTestUtils.getTestPredicateVao((IMetafieldId)IInventoryField.UNIT_ID, (PredicateVerbEnum)PredicateVerbEnum.IN, (Object)inUnitIds);
//                SavedPredicate sp = null;
//                if (inIsFilterReq) {
//                    sp = new SavedPredicate((IValueHolder)vao);
//                }
//                ServiceTestUtils.findOrCreateServiceRule((String)inSrvcRuleId, (ServiceRuleTypeEnum)inServiceRuleTypeEnum, (SavedPredicate)sp, null, (EventType)testEventType, (FlagType)holdOrPermFlagType);
            }
        });
        return mc;
    }

    public void applyHoldOrPermissionOnServiceable(final String inCtrId, final String inFlgTypId, final boolean inIsApplyHold, final boolean isOnEquipment) {
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    Unit unit = AbstractInventoryTestCase.this.reloadUnitById(inCtrId);
                    if (inIsApplyHold) {
                        AbstractInventoryTestCase.this._sm.applyHold(inFlgTypId, (ILogicalEntity)AbstractInventoryTestCase.this.getServiceable(unit, isOnEquipment), null, null, AbstractInventoryTestCase.this.getNote(inCtrId, true));
                    } else {
                        AbstractInventoryTestCase.this._sm.applyPermission(inFlgTypId, (ILogicalEntity)AbstractInventoryTestCase.this.getServiceable(unit, isOnEquipment), null, null, AbstractInventoryTestCase.this.getNote(inCtrId, !inIsApplyHold));
                    }
                }
                catch (BizViolation inBizViolation) {
                    AbstractInventoryTestCase.this.failOnException("", (Exception)((Object)inBizViolation));
                }
            }
        });
    }

    public IServiceable getServiceable(final Unit inUnit, boolean isOnEquipment) {
        final EquipmentState[] eqs = new EquipmentState[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                UnitEquipment ue = inUnit.getUnitPrimaryUe();
                if (ue != null) {
                    eqs[0] = ue.getUeEquipmentState();
                    Assert.assertNotNull((Object)eqs[0]);
                }
            }
        });
        return isOnEquipment ? (IServiceable) eqs[0] : (IServiceable) inUnit;
    }

    public String getNote(String inCtrId, boolean inApplied) {
        String APPLIED_RELEASED = inApplied ? "applied" : "released / granted";
        return "Hold / Permission " + APPLIED_RELEASED + " on " + inCtrId;
    }

    public void releaseHoldOnServiceable(final String inCtrId, final String inFlgTypeId, final boolean isOnEquipment) {
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                IServiceable srvc = AbstractInventoryTestCase.this.getServiceable(AbstractInventoryTestCase.this.reloadUnitById(inCtrId), isOnEquipment);
                try {
                    AbstractInventoryTestCase.this._sm.applyPermission(inFlgTypeId, (ILogicalEntity)srvc, null, null, AbstractInventoryTestCase.this.getNote(inCtrId, false));
                }
                catch (BizViolation inBizViolation) {
                    AbstractInventoryTestCase.this.failOnException("", (Exception)((Object)inBizViolation));
                }
            }
        });
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                Unit unit = AbstractInventoryTestCase.this.reloadUnitById(inCtrId);
                Assert.assertNotNull((Object)unit);
                IServiceable srvc = AbstractInventoryTestCase.this.getServiceable(unit, isOnEquipment);
                Assert.assertFalse((boolean)AbstractInventoryTestCase.this._sm.isEntityFlaggedWith(inFlgTypeId, (ILogicalEntity)srvc, null, null, null));
                if (isOnEquipment) {
                    EquipmentState eqs = (EquipmentState)srvc;
                    Assert.assertNotSame((Object)inFlgTypeId, (Object)eqs.getEqsHoldOrPermName());
                    Assert.assertTrue((!inFlgTypeId.equals(eqs.getEqsHoldOrPermName()) ? 1 : 0) != 0);
                } else {
                    Set activeImpediments = AbstractInventoryTestCase.this._sm.getActiveFlagIds(unit.getGuardianArray(), (ILogicalEntity)unit);
                    Assert.assertFalse((boolean)activeImpediments.contains(inFlgTypeId));
                    Set vetoedImpediments = AbstractInventoryTestCase.this._sm.getVetoedFlagIds(unit.getGuardianArray(), (ILogicalEntity)unit);
                    Assert.assertTrue((boolean)vetoedImpediments.contains(inFlgTypeId));
                }
            }
        });
    }

    public void nullifyConfigSettings(IConfig... inConfigsToNullify) {
        if (inConfigsToNullify != null && inConfigsToNullify.length > 0) {
            ArrayList<String> configIdList = new ArrayList<String>(inConfigsToNullify.length);
            for (IConfig config : inConfigsToNullify) {
                configIdList.add(config.getConfigId());
            }
            this.startHibernateAtYard1111();
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"ConfigSetting").addDqPredicate(PredicateFactory.in((IMetafieldId) IConfigSettingField.CNFIG_ID, configIdList));
            dq.setScopingEnabled(false);
            this._hibernateApi.deleteByDomainQuery(dq);
            this.endHibernate();
        }
    }

    public void setStorageCueCpx() {
        this.setStorageCueCpx(new String[]{"ACCESSORY", "ACCESSORY_ON_CHS", "CARRIAGE", "PAYLOAD", "PRIMARY"});
    }

    public void nullifyStorageCueCpx() {
        this.neutralizeSetting((IConfig) InventoryConfig.BILLING_CREATE_STORAGE);
    }

    public void nullifyStorageCueFcy() {
        this.neutralizeExtractSetting(new IConfig[]{InventoryConfig.BILLING_CREATE_STORAGE});
    }

    public void setStorageCueFcy() {
        this.setStorageCueFcy(new String[]{"ACCESSORY", "ACCESSORY_ON_CHS", "CARRIAGE", "PAYLOAD", "PRIMARY"});
    }

    public void setStorageCueCpx(Object inValue) {
        if (inValue != null) {
            this.setStorageCueCpx(inValue, new IConfig[]{InventoryConfig.BILLING_CREATE_STORAGE});
        } else {
            this.neutralizeExtractSetting(new IConfig[]{InventoryConfig.BILLING_CREATE_STORAGE});
        }
    }

    public void setLineStorageCueCpx() {
        this.setLineStorageCueCpx(new String[]{"ACCESSORY", "ACCESSORY_ON_CHS", "CARRIAGE", "PAYLOAD", "PRIMARY"});
    }

    public void nullifyLineStorageCueCpx() {
        this.neutralizeSetting((IConfig) InventoryConfig.BILLING_CREATE_LINE_STORAGE);
    }

    public void setLineStorageCueFcy() {
        this.setLineStorageCueFcy(new String[]{"ACCESSORY", "ACCESSORY_ON_CHS", "CARRIAGE", "PAYLOAD", "PRIMARY"});
    }

    public void nullifyLineStorageCueFcy() {
        this.neutralizeExtractSetting(new IConfig[]{InventoryConfig.BILLING_CREATE_LINE_STORAGE});
    }

    public void setLineStorageCueCpx(Object inValue) {
        this.setLineStorageCueCpx(inValue, new IConfig[]{InventoryConfig.BILLING_CREATE_LINE_STORAGE});
    }

    public void setStorageCueFcy(Object inValue) {
        this.setStorageCueFcy(inValue, new IConfig[]{InventoryConfig.BILLING_CREATE_STORAGE});
    }

    public void setLineStorageCueFcy(Object inValue) {
        this.setLineStorageCueFcy(inValue, new IConfig[]{InventoryConfig.BILLING_CREATE_LINE_STORAGE});
    }

    public String getXmlFromSpecifiedPath(String inDirectory, String inFileName) {
        return ArgoUtils.loadClasspathFileAsString((String)(inDirectory + File.separator + inFileName));
    }

    protected void neutralizeExtractSetting(IConfig... inConfigs) {
        if (inConfigs != null && inConfigs.length > 0) {
            for (IConfig cfg : inConfigs) {
                this.neutralizeSetting(cfg);
            }
        }
    }

    protected void assertTimestamp(String inMsg, Date inDate1, Date inDate2) {
        Calendar c = Calendar.getInstance(ContextHelper.getThreadUserTimezone());
        c.setTime(inDate1);
        this.assertTimestamp(inMsg, c.get(1), c.get(2), c.get(5), c.get(11), c.get(12), c.get(13), ContextHelper.getThreadUserTimezone(), inDate2);
    }

    protected void assertTimestamp(String inErrorMsg, int inYear, int inMon, int inDayOfMonth, int inHourOfDay, int inMin, int inSec, TimeZone inTimeZone, Date inActualDate) {
        AbstractInventoryTestCase.assertNotNull((String)(inErrorMsg + ", date is null,"), (Object)inActualDate);
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTime(inActualDate);
        this.assertYear(inErrorMsg, inYear, c);
        this.assertMonth(inErrorMsg, inMon, c);
        this.assertDayOfMonth(inErrorMsg, inDayOfMonth, c);
        this.assertHourOfDay(inErrorMsg, inHourOfDay, c);
        this.assertMinute(inErrorMsg, inMin, c);
        this.assertSecond(inErrorMsg, inSec, c);
    }

    protected void assertYear(String inErrorMsg, int inYear, Calendar inCalendar) {
        AbstractInventoryTestCase.assertEquals((String)(inErrorMsg + ", year is wrong,"), (int)inYear, (int)inCalendar.get(1));
    }

    protected void assertMonth(String inErrorMsg, int inMon, Calendar inCalendar) {
        AbstractInventoryTestCase.assertEquals((String)(inErrorMsg + ", month is wrong,"), (int)inMon, (int)inCalendar.get(2));
    }

    protected void assertDayOfMonth(String inErrorMsg, int inDayOfMonth, Calendar inCalendar) {
        AbstractInventoryTestCase.assertEquals((String)(inErrorMsg + ", day is wrong,"), (int)inDayOfMonth, (int)inCalendar.get(5));
    }

    protected void assertHourOfDay(String inErrorMsg, int inHourOfDay, Calendar inCalendar) {
        AbstractInventoryTestCase.assertEquals((String)(inErrorMsg + ", hour is wrong,"), (int)inHourOfDay, (int)inCalendar.get(11));
    }

    protected void assertMinute(String inErrorMsg, int inMin, Calendar inCalendar) {
        AbstractInventoryTestCase.assertEquals((String)(inErrorMsg + ", minute is wrong,"), (int)inMin, (int)inCalendar.get(12));
    }

    protected void assertSecond(String inErrorMsg, int inSec, Calendar inCalendar) {
        int offset = 1;
        int calendarsec = inCalendar.get(13);
        boolean secIsSame = inSec == calendarsec || inSec + offset == calendarsec;
        AbstractInventoryTestCase.assertTrue((String)(inErrorMsg + ", second is wrong,"), (boolean)secIsSame);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Serializable createTestReefer(String inCtrId, String inVesselId, String inYardPosition) {
        this.startHibernateAtYard1111();
        Yard yard = ContextHelper.getThreadYard();
        Facility facility = yard.getYrdFacility();
        Complex complex = facility.getFcyComplex();
        CarrierVisit vesselVisit = CarrierVisit.findOrCreateVesselVisit((Facility)facility, (String)inVesselId);
        CarrierVisit truckVisit = CarrierVisit.getGenericTruckVisit((Complex)complex);
        Long unitGKey = null;
        try {
            Container ctr = this.findOrCreateTestContainer(inCtrId, "4231");
            Unit unit = this.createYardUnit((Equipment)ctr, yard, inYardPosition, vesselVisit, truckVisit);
            unit.updateRequiresPower(true);
            unitGKey = unit.getUnitGkey();
        }
        catch (BizViolation e) {
            AbstractInventoryTestCase.fail((String)e.getMessage());
        }
        finally {
            this.endHibernate();
        }
        return unitGKey;
    }

    public Serializable createUfv(final String inCtrId, final UfvTransitStateEnum inUfvTransitStateEnum, final UnitCategoryEnum inUnitCategoryEnum) {
        final Serializable[] ufvGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                try {
                    UnitFacilityVisit ufv = AbstractInventoryTestCase.this.makeUfv(inCtrId, inUfvTransitStateEnum);
                    Assert.assertNotNull((Object)ufv);
                    Unit unit = ufv.getUfvUnit();
                    Assert.assertNotNull((Object)unit);
                    unit.updateCategory(inUnitCategoryEnum);
                    ufvGkey[0] = ufv.getUfvGkey();
                }
                catch (BizViolation inBizViolation) {
                    Assert.fail((String)inBizViolation.getMessage());
                }
            }
        });
        return ufvGkey[0];
    }

    @Nullable
    protected EventType evnttyp(IEventType inEventType) {
        return EventType.resolveIEventType((IEventType)inEventType);
    }

    public CarrierVisit createVesselVisitValidToPurge(String inCvId, String inLineId) {
        CarrierVisit cv = this.createTestVesselVisit(inCvId, inLineId);
        Date dateToPurge = this.getValidDateForPurging(ArgoConfig.PURGE_BILL_OF_LADDING_DAYS_TO_RETAIN);
        cv.setFieldValue(IArgoField.CV_A_T_A, (Object)dateToPurge);
        cv.setFieldValue(IArgoField.CV_A_T_D, (Object)new Date(dateToPurge.getTime() + 60000L));
        return cv;
    }

    public String constructExtractXmlForFlexFields(Map<String, String> inMetafieldExtrnlTagMap) {
//        Element flexE = new Element("flexfield-mappings");
//        ArrayList<Element> flexMappingList = new ArrayList<Element>();
//        for (Map.Entry<String, String> entry : inMetafieldExtrnlTagMap.entrySet()) {
//            Element flexFieldMappingE = new Element("flexfield-mapping");
//            XmlUtil.setAttribute((Element)flexFieldMappingE, (String)FLEX_FIELD_KEY, (Object)entry.getKey(), null);
//            XmlUtil.setAttribute((Element)flexFieldMappingE, (String)FLEX_FIELD_VALUE, (Object)entry.getValue(), null);
//            flexMappingList.add(flexFieldMappingE);
//        }
//        flexE.addContent(flexMappingList);
//        String xmlAsString = XmlUtil.toString((Element)flexE, (boolean)true);
//        LOGGER.error((Object)("Constructed Xml \n" + xmlAsString));
//        return xmlAsString;
        return null;
    }

}
