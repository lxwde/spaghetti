package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.UnitFacilityVisitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.PlacardedEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class InventoryCargoUtils {
    private static final Logger LOGGER = Logger.getLogger(InventoryCargoUtils.class);
    public static final List<IMetafieldId> BUNDLE_FLAGS = new ArrayList<IMetafieldId>();
    public static final List<IMetafieldId> SLAVE_IDS = new ArrayList<IMetafieldId>();
    public static final List<IMetafieldId> SLAVE_DAMAGES = new ArrayList<IMetafieldId>();
    private static final Object CUSTOM_FIELD_PREFIX = "customFlexFields.";
    public static final IMetafieldId[] UNIT_FLEX_FIELDS_METAFIELDIDS;

    private InventoryCargoUtils() {
    }

    public static List createVosForBillsOfLading(Collection inBls) {
        ArrayList blsVaoList = new ArrayList();
        if (inBls == null) {
            return blsVaoList;
        }
        for (Object inBl : inBls) {
            IBillOfLading bl = (IBillOfLading)inBl;
            ValueObject vao = new ValueObject("IBillOfLading");
            vao.setEntityPrimaryKey((Serializable)bl.getGkey());
            vao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_GKEY, (Object)bl.getGkey().toString());
            InventoryCargoUtils.addBlPropertiestoVao(blsVaoList, bl, vao);
        }
        return blsVaoList;
    }

    private static void addBlPropertiestoVao(List inOutBlsVaoList, IBillOfLading inBl, ValueObject inOutVao) {
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_NBR, (Object)inBl.getNbr());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_ORIGINAL_BL_NBR, (Object)inBl.getOriginalBlNbr());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_CATEGORY, (Object)inBl.getCategory());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_LINE_OPERATOR_ID, (Object)inBl.getLineOperatorId());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_CARRIER_VISIT_ID, (Object)inBl.getCarrierVisitId());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_SHIPPER_NAME, (Object)inBl.getShipperName());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_ORIGIN, (Object)inBl.getOrigin());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_POL_ID, (Object)inBl.getPolId());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_CONSIGNEE_NAME, (Object)inBl.getConsigneeName());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_DESTINATION, (Object)inBl.getDestination());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_BONDED_DESTINATION_ID, (Object)inBl.getBondedDestinationId());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_POD1_ID, (Object)inBl.getPod1Id());
        inOutVao.setFieldValue(IInventoryBizMetafield.BILL_OF_LADING_POD2_ID, (Object)inBl.getPod2Id());
        inOutBlsVaoList.add(inOutVao);
    }

    public static List createVosForBlGoodsBls(Collection inBlGoodsBls) {
        ArrayList blsVaoList = new ArrayList();
        if (inBlGoodsBls == null) {
            return blsVaoList;
        }
        for (Object inBlGoodsBl : inBlGoodsBls) {
            IBlGoodsBl blgdsbl = (IBlGoodsBl)inBlGoodsBl;
            ValueObject vao = new ValueObject("IBlGoodsBl");
            vao.setEntityPrimaryKey(blgdsbl.getGkey());
            IBillOfLading bl = blgdsbl.getBillOfLading();
            InventoryCargoUtils.addBlPropertiestoVao(blsVaoList, bl, vao);
        }
        return blsVaoList;
    }

    public static IUnitFinder getFndr() {
        return (IUnitFinder) Roastery.getBean((String)"unitFinder");
    }

    public static IUnitManager getMngr() {
        return (IUnitManager) Roastery.getBean((String)"unitManager");
    }

    private static void bundleUnbundleHelper(Unit inUnit, String inOldEqId, String inNewEqId) throws BizViolation {
        IUnitManager unitManager = InventoryCargoUtils.getMngr();
        if (inOldEqId != null && !inOldEqId.equalsIgnoreCase(inNewEqId)) {
            Unit ue = inUnit.getUnitEquipment(inOldEqId);
            InventoryCargoUtils.getMngr().unbundleUnitEquipment(ue);
            if (inNewEqId != null) {
                unitManager.attachEquipment(inUnit, inNewEqId, EqUnitRoleEnum.PAYLOAD);
            }
        } else if (inOldEqId == null && inNewEqId != null) {
            unitManager.attachEquipment(inUnit, inNewEqId, EqUnitRoleEnum.PAYLOAD);
        }
    }

    public static void handleBundleEquipmentFields(Unit inUnit, FieldChanges inBundleChanges) throws BizViolation {
        IValueHolder[] slaveDmgVh;
        String newEqId;
        String oldEqId;
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID1)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID1).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID1).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO1) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO1).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID2)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID2).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID2).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO2) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO2).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID3)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID3).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID3).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO3) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO3).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID4)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID4).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID4).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO4) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO4).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID5)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID5).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID5).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO5) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO5).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID6)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID6).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID6).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO6) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO6).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID7)) {
            oldEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID7).getPriorValue();
            newEqId = (String)inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID7).getNewValue();
            InventoryCargoUtils.bundleUnbundleHelper(inUnit, oldEqId, newEqId);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO7) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO7).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, newEqId, slaveDmgVh);
            }
        }
    }

    private static void updateBundleDamages(Unit inUnit, String inNewEqId, IValueHolder[] inSlaveDmgVh) {
        Unit ue = inUnit.getUnitEquipment(inNewEqId);
        if (ue != null && inSlaveDmgVh != null) {
            ue.setUeDamageVao(inSlaveDmgVh);
        }
    }

    public static void validateEDOForLoad(UnitFacilityVisit inUfv) {
        EqBaseOrderItem eqBaseOrderItem;
        IArgoEquipmentOrderManager equipOrdMgr = (IArgoEquipmentOrderManager) Roastery.getBean((String)"equipmentOrderManager");
        UnitEquipment unitPrimaryUe = inUfv.getUfvUnit().getUnitPrimaryUe();
        UnitCategoryEnum unitCategory = inUfv.getUfvUnit().getUnitCategory();
        FreightKindEnum unitFreightKind = inUfv.getUfvUnit().getUnitFreightKind();
        Boolean isCatEDO = UnitCategoryEnum.STORAGE.equals((Object)unitCategory) || UnitCategoryEnum.IMPORT.equals((Object)unitCategory);
        Boolean isFreightKindEDO = FreightKindEnum.MTY.equals((Object)unitFreightKind);
        if (unitPrimaryUe != null && isCatEDO.booleanValue() && isFreightKindEDO.booleanValue() && (eqBaseOrderItem = unitPrimaryUe.getUeDepartureOrderItem()) != null && inUfv.isTransitStatePriorTo(UfvTransitStateEnum.S60_LOADED)) {
            equipOrdMgr.adjustTally((Serializable)unitPrimaryUe.getUeGkey());
        }
    }

    public static Unit[] extractUnitList(CrudOperation inCrudOperation) {
        Serializable[] primaryKeys = (Serializable[])inCrudOperation.getPrimaryKeys();
        Unit[] units = new Unit[primaryKeys.length];
        for (int i = 0; i < primaryKeys.length; ++i) {
            Object o = HibernateApi.getInstance().get(Unit.class, primaryKeys[i]);
            units[i] = (Unit)o;
        }
        if (units.length == 0) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"Unit", (Object)primaryKeys);
        }
        return units;
    }

    public static UnitFacilityVisit[] extractUfvList(CrudOperation inCrudOperation) {
        Serializable[] primaryKeys = (Serializable[])inCrudOperation.getPrimaryKeys();
        UnitFacilityVisit[] ufvs = new UnitFacilityVisit[primaryKeys.length];
        for (int i = 0; i < primaryKeys.length; ++i) {
            Object o = HibernateApi.getInstance().get(UnitFacilityVisit.class, primaryKeys[i]);
            ufvs[i] = (UnitFacilityVisit)o;
        }
        if (ufvs.length == 0) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)primaryKeys);
        }
        return ufvs;
    }

    public static Serializable[] extractUfvUnitGkeys(CrudOperation inCrudOperation) {
        Serializable[] ufvKeys = (Serializable[])inCrudOperation.getPrimaryKeys();
        Serializable[] unitGkeys = new Serializable[ufvKeys.length];
        for (int i = 0; i < ufvKeys.length; ++i) {
            UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKeys[i]);
            unitGkeys[i] = ufv.getUfvUnit().getUnitGkey();
        }
        if (unitGkeys.length == 0) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"Unit", (Object)unitGkeys);
        }
        return unitGkeys;
    }

    public static UnitFacilityVisit findActiveUfv(String inFullEqId, IMetafieldId inField) throws BizViolation {
        return InventoryCargoUtils.findUfv(inFullEqId, inField, 1);
    }

    public static UnitFacilityVisit findUfv(String inFullEqId, IMetafieldId inField, int inDesiredStates) throws BizViolation {
        IQueryResult queryResult = InventoryCargoUtils.getFndr().doUfvQuery(inFullEqId, inDesiredStates, false);
        int totalResultCount = queryResult.getTotalResultCount();
        if (totalResultCount == 0) {
            throw BizViolation.createFieldViolation((IPropertyKey) IInventoryPropertyKeys.FULL_ID_FOUND_NONE, null, (IMetafieldId)inField, (Object)inFullEqId);
        }
        if (totalResultCount > 1) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_MANY, null, (Object)inField, (Object)totalResultCount);
        }
        Serializable ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        if (ufv == null) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)ufvKey);
        }
        return ufv;
    }

    public static UnitFacilityVisit extractUfvFromRequest(String inDigits, ValueObject inResultVao, boolean inBypassInstanceSecurity) throws BizViolation {
        IQueryResult queryResult = InventoryCargoUtils.getFndr().doUfvQuery(inDigits, 5, inBypassInstanceSecurity);
        int totalResultCount = queryResult.getTotalResultCount();
        Long resultCountLong = Long.valueOf(totalResultCount);
        inResultVao.setFieldValue(IInventoryBizMetafield.QUERY_RESULT_COUNT, (Object)resultCountLong);
        if (totalResultCount == 0) {
            if (inBypassInstanceSecurity) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.FULL_ID_FOUND_NONE, null, (Object)inDigits);
            }
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_NO_ACTIVE_UNITS, null, (Object)inDigits);
        }
        if (totalResultCount > 1) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_MANY, null, (Object)resultCountLong);
        }
        Serializable ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        if (ufv == null) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)ufvKey);
        }
        if (inBypassInstanceSecurity) {
            inResultVao.setFieldValue(IInventoryField.UNIT_ID, (Object)ufv.getUfvUnit().getHumanReadableKey());
        } else {
            inResultVao.setFieldValue(IInventoryBizMetafield.UNIT_DIGITS, (Object)ufv.getUfvUnit().getHumanReadableKey());
        }
        inResultVao.setFieldValue(IInventoryField.UFV_GKEY, (Object)ufv.getUfvGkey());
        return ufv;
    }

    public static UnitFacilityVisit extractUfvFromRequest(String inDigits, ValueObject inResultVao, boolean inBypassInstanceSecurity, int inDesiredStates) throws BizViolation {
        IQueryResult queryResult = InventoryCargoUtils.getFndr().doUfvQuery(inDigits, inDesiredStates, inBypassInstanceSecurity);
        int totalResultCount = queryResult.getTotalResultCount();
        Long resultCountLong = Long.valueOf(totalResultCount);
        inResultVao.setFieldValue(IInventoryBizMetafield.QUERY_RESULT_COUNT, (Object)resultCountLong);
        if (totalResultCount == 0) {
            if (inBypassInstanceSecurity) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.FULL_ID_FOUND_NONE, null, (Object)inDigits);
            }
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_NO_ACTIVE_UNITS, null, (Object)inDigits);
        }
        if (totalResultCount > 1) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_MANY, null, (Object)resultCountLong);
        }
        Serializable ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
        UnitFacilityVisit ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        if (ufv == null) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"UnitFacilityVisit", (Object)ufvKey);
        }
        String unitId = (String)queryResult.getValue(0, UnitField.UFV_UNIT_ID);
        if (inBypassInstanceSecurity) {
            inResultVao.setFieldValue(IInventoryField.UNIT_ID, (Object)unitId);
        } else {
            inResultVao.setFieldValue(IInventoryBizMetafield.UNIT_DIGITS, (Object)unitId);
        }
        inResultVao.setFieldValue(IInventoryField.UFV_GKEY, (Object)ufvKey);
        return ufv;
    }

    public static void detachAndCreateNewUnit(Serializable inUeGkey, UnitEquipment inUe) throws BizViolation {
        if (inUe == null) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"Unit", (Object)inUeGkey);
        }
        Facility facility = ContextHelper.getThreadFacility();
        UnitFacilityVisit ufv = inUe.getUeUnit().getUfvForFacilityLiveOnly(facility);
        if (ufv == null) {
            throw BizFailure.create((String)("Attempt to detach equipment but no live UFV for current facility:" + inUe));
        }
        InventoryCargoUtils.getMngr().detachAndCreateNewUnit(inUe);
    }

    public static boolean wiExists(UnitFacilityVisit inUfv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqField(UnitField.WI_UFV_GKEY).addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.WI_UFV_GKEY, (Object)inUfv.getUfvGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId) IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        return qr.getTotalResultCount() > 0;
    }

    public static WorkInstruction[] extractWiList(CrudOperation inCrudOperation) {
        Serializable[] primaryKeys = (Serializable[])inCrudOperation.getPrimaryKeys();
        WorkInstruction[] wis = new WorkInstruction[primaryKeys.length];
        for (int i = 0; i < primaryKeys.length; ++i) {
            Object o = HibernateApi.getInstance().get(WorkInstruction.class, primaryKeys[i]);
            wis[i] = (WorkInstruction)o;
        }
        if (wis.length == 0) {
            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"WorkInstruction", (Object)primaryKeys);
        }
        return wis;
    }

    public static void validateAndRecordObservedPlacards(Unit inUnit, FieldChanges inOldFcs, FieldChanges inNewFcs) {
        Object newValue;
//        FieldChange obsPlacardFC = inOldFcs.getFieldChange(IInventoryField.UNIT_OBSERVED_PLACARDS);
//        List<Serializable> obsPlacards = obsPlacardFC == null || obsPlacardFC.getNewValue() == null ? Collections.emptyList() : ((newValue = obsPlacardFC.getNewValue()) instanceof Serializable[] ? Arrays.asList((Serializable[])newValue) : (List<Serializable>)newValue);
//        IMetafieldId isPlacardedMfldId = IInventoryField.UNIT_PLACARDED;
//        HashSet<ObservedPlacard> currUnitPlacardSet = inUnit.getUnitObservedPlacards();
//        HashSet<Placard> observedPlacardSet = new HashSet<Placard>();
//        if (currUnitPlacardSet == null) {
//            currUnitPlacardSet = new HashSet<ObservedPlacard>();
//        }
//        if ((currUnitPlacardSet == null || currUnitPlacardSet.isEmpty()) && (obsPlacards == null || obsPlacards.isEmpty())) {
//            FieldChange newPlacarded = inOldFcs.getFieldChange(isPlacardedMfldId);
//            if (newPlacarded != null && newPlacarded.getNewValue() != null && (newPlacarded.getNewValue().equals(PlacardedEnum.YES.getKey()) || newPlacarded.getNewValue().equals((Object)PlacardedEnum.YES))) {
//                inNewFcs.setFieldChange(isPlacardedMfldId, (Object)PlacardedEnum.YES);
//            } else if (newPlacarded != null && newPlacarded.getNewValue() != null && !newPlacarded.getNewValue().equals(PlacardedEnum.YES.getKey())) {
//                inNewFcs.setFieldChange(isPlacardedMfldId, newPlacarded.getNewValue());
//            }
//        }
//        int plcrdChangeCnt = 0;
//        if (obsPlacards != null) {
//            Placard placard;
//            if (!currUnitPlacardSet.isEmpty()) {
//                for (Serializable obsPlacard : obsPlacards) {
//                    placard = (Placard)HibernateApi.getInstance().get(Placard.class, obsPlacard);
//                    observedPlacardSet.add(placard);
//                    if (!InventoryCargoUtils.isObservedPlacardPresent(currUnitPlacardSet, placard)) continue;
//                    ++plcrdChangeCnt;
//                }
//            }
//            if (obsPlacards.size() != currUnitPlacardSet.size() || obsPlacards.size() != plcrdChangeCnt) {
//                currUnitPlacardSet.clear();
//                inUnit.updateUnitObservedPlacards(currUnitPlacardSet);
//                HibernateApi.getInstance().flush();
//                if (currUnitPlacardSet == null) {
//                    currUnitPlacardSet = new HashSet();
//                }
//                for (Serializable obsPlacard : obsPlacards) {
//                    placard = (Placard)HibernateApi.getInstance().get(Placard.class, obsPlacard);
//                    observedPlacardSet.add(placard);
//                    ObservedPlacard observedPlacard = new ObservedPlacard();
//                    observedPlacard.setFieldValue(IInventoryField.OBSPLACARD_UNIT, inUnit);
//                    observedPlacard.setFieldValue(IInventoryField.OBSPLACARD_PLACARD, placard);
//                    currUnitPlacardSet.add(observedPlacard);
//                }
//                inNewFcs.setFieldChange(IInventoryField.UNIT_OBSERVED_PLACARDS, currUnitPlacardSet);
//            } else {
//                inNewFcs.removeFieldChange(IInventoryField.UNIT_OBSERVED_PLACARDS);
//            }
//        }
//        if (!currUnitPlacardSet.isEmpty()) {
//            inNewFcs.setFieldChange(isPlacardedMfldId, (Object)PlacardedEnum.YES);
//        } else {
//            PlacardedEnum placardVal = (PlacardedEnum)((Object)inUnit.getFieldValue(isPlacardedMfldId));
//            if (PlacardedEnum.YES.equals((Object)placardVal)) {
//                inNewFcs.setFieldChange(isPlacardedMfldId, (Object)PlacardedEnum.NO);
//            }
//        }
//        GoodsBase unitGoods = inUnit.ensureGoods();
//        Hazards unitHazards = unitGoods.getGdsHazards();
//        boolean arePlacardsMismatched = InventoryCargoUtils.getMngr().computeUnitPlacardMismatch(unitHazards, observedPlacardSet);
//        inNewFcs.setFieldChange(IInventoryField.UNIT_ARE_PLACARDS_MISMATCHED, (Object)arePlacardsMismatched);
    }

//    public static boolean isObservedPlacardPresent(Set<ObservedPlacard> inObservedPlacardSet, Placard inPlacard) {
//        for (ObservedPlacard obsP : inObservedPlacardSet) {
//            if (!inPlacard.getPlacardText().equalsIgnoreCase(obsP.getObsplacardPlacard().getPlacardText())) continue;
//            return true;
//        }
//        return false;
//    }

    public static void applyServiceEventRecord(Unit inUnit, FieldChanges inChanges) throws BizViolation {
        IServicesManager servicesMgr = (IServicesManager) Roastery.getBean((String)"servicesManager");
//        LogicalEntityIdentifier serviceables = new LogicalEntityIdentifier((Serializable)inUnit.getUnitGkey(), LogicalEntityEnum.UNIT);
//        servicesMgr.recordEvent(serviceables, inChanges);
    }

    public static boolean ignoreOverridableErrors(FieldChanges inChanges) {
        Boolean skipValidation;
        if (inChanges != null && inChanges.hasFieldChange(IInventoryBizMetafield.RDT_IGNORE_CUSTOM_VALIDATION) && (skipValidation = (Boolean)inChanges.getFieldChange(IInventoryBizMetafield.RDT_IGNORE_CUSTOM_VALIDATION).getNewValue()).booleanValue()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"skipping custom validation as force flag is set");
            }
            inChanges.removeFieldChange(IInventoryBizMetafield.RDT_IGNORE_CUSTOM_VALIDATION);
            return true;
        }
        return false;
    }

    public static void doDetach(Map<String, UnitEquipment> inEqIdUeMap, String inEqId, UnitEquipment inUe) throws BizViolation {
        if (EqUnitRoleEnum.PAYLOAD.equals((Object)inUe.getUeEqRole()) && inEqIdUeMap.containsKey(inEqId)) {
            InventoryCargoUtils.detachAndCreateNewUnit(inUe.getUeGkey(), inUe);
        }
    }

    @Nullable
    public static String get40FtBinName(String inA20FtBinName) throws BizViolation {
        String binName;
        AbstractBin bin;
        LocPosition pos = LocPosition.createYardPosition((Yard) ContextHelper.getThreadYard(), (String)inA20FtBinName, null, (EquipBasicLengthEnum) EquipBasicLengthEnum.BASIC20, (boolean)true);
        if (pos != null && (bin = pos.getPosBin()) != null && (binName = bin.getAbnNameAlt()) != null && !binName.isEmpty()) {
            StringBuilder binName40 = new StringBuilder(binName);
            int lastDot = inA20FtBinName.lastIndexOf(46);
            if (lastDot > 0) {
                String tier = inA20FtBinName.substring(lastDot + 1, inA20FtBinName.length());
                binName40.append(".").append(tier);
                LocPosition.createYardPosition((Yard) ContextHelper.getThreadYard(), (String)binName40.toString(), null, (EquipBasicLengthEnum) EquipBasicLengthEnum.BASIC40, (boolean)true);
                return binName40.toString();
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean validateUfvForPositionCorrection(UnitFacilityVisit inUfv, String inPos, EquipBasicLengthEnum inEqLength) throws BizViolation {
        LocPosition newPosition;
        inUfv.getUfvUnit().getUnitPrimaryUe().getUeEquipment().getEqEquipType().getEqtypBasicLength();
        if (EquipBasicLengthEnum.BASIC40.equals((Object)inEqLength)) {
            String binName40ft = InventoryCargoUtils.get40FtBinName(inPos);
            newPosition = LocPosition.createYardPosition((Yard) ContextHelper.getThreadYard(), (String)binName40ft, null, (EquipBasicLengthEnum)inEqLength, (boolean)true);
        } else {
            newPosition = LocPosition.createYardPosition((Yard) ContextHelper.getThreadYard(), (String)inPos, null, (EquipBasicLengthEnum)inEqLength, (boolean)true);
        }
        LocPosition currPosition = inUfv.getUfvLastKnownPosition();
        LocTypeEnum currLocType = currPosition.getPosLocType();
        String unitId = inUfv.getUfvUnit().getUnitId();
        Boolean isPositionCorrected = false;
        if (currLocType != null) {
            if (LocTypeEnum.VESSEL.equals((Object)currLocType) || LocTypeEnum.TRAIN.equals((Object)currLocType)) {
                CarrierVisit cv = inUfv.getInboundCarrierVisit();
                if (cv == null) {
                    throw BizFailure.create((String)("There is no inbound carrier visit associated for unit " + unitId));
                }
                CarrierVisitPhaseEnum cvPhaseEnum = cv.getCvVisitPhase();
                if ((CarrierVisitPhaseEnum.ARRIVED.equals((Object)cvPhaseEnum) || CarrierVisitPhaseEnum.WORKING.equals((Object)cvPhaseEnum)) && !UfvTransitStateEnum.S60_LOADED.equals((Object)inUfv.getUfvTransitState())) {
                    MoveInfoBean info = MoveInfoBean.createDefaultMoveInfoBean(WiMoveKindEnum.VeslDisch, ArgoUtils.timeNow());
//                    info.setRestowReason(inUfv.getUfvHandlingReason() != null ? inUfv.getUfvHandlingReason().getKey() : null);
//                    InventoryCargoUtils.getMngr().dischargeUnitFromInboundVisit(inUfv, cv.getCvCvd(), info, inPos, null);
                    return true;
                }
                if (UfvTransitStateEnum.S60_LOADED.equals((Object)inUfv.getUfvTransitState())) {
                    cv = currPosition.resolveOutboundCarrierVisit();
                    if (cv == null) {
                        throw BizFailure.create((String)("There is no outbound carrier visit associated for unit " + unitId));
                    }
                    cvPhaseEnum = cv.getCvVisitPhase();
                }
                String errStr = "Unit " + unitId + " on " + cvPhaseEnum.getKey().substring(2) + " " + currLocType.getName();
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.YARD_INV_INVALID_POSITION_UPDATE, null, (Object)errStr);
            }
            if (LocTypeEnum.YARD.equals((Object)currLocType)) {
                inUfv.correctPosition(newPosition, false);
                return true;
            }
            if (!LocTypeEnum.TRUCK.equals((Object)currLocType)) return isPositionCorrected;
            if (UfvTransitStateEnum.S60_LOADED.equals((Object)inUfv.getUfvTransitState())) {
                String errStr = "Unit " + unitId + " loaded on TRUCK";
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.YARD_INV_INVALID_POSITION_UPDATE, null, (Object)errStr);
            }
            inUfv.correctPosition(newPosition, false);
            return true;
        }
        inUfv.correctPosition(newPosition, false);
        return true;
    }

    public static void registerOverridableError(String inFailureMessage) {
        TransactionParms parms = TransactionParms.getBoundParms();
        IMessageCollector ms = parms.getMessageCollector();
        if (ms != null) {
            IErrorOverrides overrides = ms.getErrorOverrides();
            if (overrides != null) {
                if (!overrides.ignoreAllOverrideableErrors()) {
                    overrides.registerOverridableError(IArgoPropertyKeys.GROOVY_OVERRIDABLE_FAILURE);
                    ms.appendMessage(MessageLevelEnum.SEVERE, IArgoPropertyKeys.GROOVY_OVERRIDABLE_FAILURE, "cc ", new Object[]{inFailureMessage});
                }
            } else {
                LOGGER.error((Object)"registerOverridableError: This request does NOT support overrideable errors!");
            }
        }
    }

    public static boolean ignoreAllOverrideableErrors() {
        IErrorOverrides overrides;
        TransactionParms parms = TransactionParms.getBoundParms();
        IMessageCollector mc = parms.getMessageCollector();
        if (mc != null && (overrides = mc.getErrorOverrides()) != null) {
            boolean willIgnoreAllOverrideableErrors = overrides.ignoreAllOverrideableErrors();
            return willIgnoreAllOverrideableErrors;
        }
        return false;
    }

    public static void handleAttachEquipmentFields(Unit inUnit, FieldChanges inBundleChanges) throws BizViolation {
        IValueHolder[] slaveDmgVh;
        ArrayList<String> slaveList = new ArrayList<String>();
        Set ueSet = inUnit.getUnitUeSet();
        if (ueSet != null) {
            for (Object ue : ueSet) {
                slaveList.add(((UnitEquipment)ue).getUeEquipment().getEqIdFull());
            }
        }
        FieldChange slaveEuipFc = null;
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID1)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID1);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO1) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO1).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID2)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID2);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO2) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO2).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID3)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID3);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO3) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO3).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID4)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID4);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO4) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO4).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID5)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID5);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO5) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO5).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID6)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID6);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO6) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO6).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
        if (inBundleChanges.hasFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID7)) {
            slaveEuipFc = inBundleChanges.getFieldChange(IInventoryBizMetafield.UNIT_SLAVE_ID7);
            InventoryCargoUtils.hatchClerkBundleUnbundleHelper(inUnit, slaveList, slaveEuipFc);
            if (inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO7) != null) {
                slaveDmgVh = (IValueHolder[])inBundleChanges.getFieldChange(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO7).getNewValue();
                InventoryCargoUtils.updateBundleDamages(inUnit, slaveEuipFc.getNewValue().toString(), slaveDmgVh);
            }
        }
    }

    public static void hatchClerkBundleUnbundleHelper(Unit inUnit, List inSlaveList, FieldChange inSlaveEquipId) throws BizViolation {
        IUnitManager manager = InventoryCargoUtils.getMngr();
        if (inSlaveEquipId != null && !inSlaveList.contains(inSlaveEquipId.getNewValue())) {
            manager.attachEquipment(inUnit, (String)inSlaveEquipId.getNewValue(), EqUnitRoleEnum.PAYLOAD);
        }
    }

    public static FieldChanges applyInpsectionData(Unit inUnit, FieldChanges inChanges) throws BizViolation {
        FieldChange rdtApplicationFC = inChanges.getFieldChange(IInventoryBizMetafield.RDT_APPLICATION_NAME);
        String eventNote = "Yard Inspection Data updated";
        if (rdtApplicationFC != null) {
            String appName = (String)rdtApplicationFC.getNewValue();
            if ("RailInspectionProgram".equalsIgnoreCase(appName)) {
                eventNote = "Rail Inspection Data updated";
            }
            inChanges.removeFieldChange(IInventoryBizMetafield.RDT_APPLICATION_NAME);
        }
        FieldChanges fcs = new FieldChanges();
        FieldChanges bundleChanges = new FieldChanges();
        FieldChange accDmgFc = null;
        IMetafieldId isPlacardedMfldId = IInventoryField.UNIT_PLACARDED;
        boolean recordSealEvent = false;
        Iterator iterator = inChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = (FieldChange)iterator.next();
            IMetafieldId metafieldId = fc.getMetafieldId();
            Object unitValue = inUnit.getFieldValue(metafieldId);
            FieldChange recordedFc = new FieldChange(metafieldId, unitValue, fc.getNewValue());
            IMetafieldId fieldId = fc.getMetafieldId();
            if (SLAVE_IDS.contains((Object)fieldId) || BUNDLE_FLAGS.contains((Object)fieldId) || IInventoryBizMetafield.UNIT_UNBUNDLE_ALL.equals((Object)fieldId) || SLAVE_DAMAGES.contains((Object)fieldId)) {
                bundleChanges.setFieldChange(fc);
                continue;
            }
            if (IInventoryField.UNIT_OBSERVED_PLACARDS.equals((Object)fc.getMetafieldId()) || isPlacardedMfldId.equals((Object)fc.getMetafieldId()) && fc.getNewValue() != null && fc.getNewValue().equals(PlacardedEnum.YES.getKey())) {
                InventoryCargoUtils.validateAndRecordObservedPlacards(inUnit, inChanges, fcs);
                continue;
            }
            if (IInventoryBizMetafield.UNIT_ACRY_ID.equals((Object)fc.getMetafieldId())) {
                InventoryCargoUtils.getMngr().updateContainerGenset(inUnit, (String)fc.getNewValue());
                continue;
            }
            if (IInventoryBizMetafield.ACCESSORY_DAMAGES_VAO.equals((Object)fc.getMetafieldId())) {
                accDmgFc = fc;
                continue;
            }
            if (IInventoryField.UNIT_REMARK.equals((Object)fc.getMetafieldId()) && unitValue == null && fc.getNewValue() != null && "".equals(fc.getNewValue())) continue;
            if (UnitField.UNIT_GRADE_I_D.equals((Object)fc.getMetafieldId())) {
                if (fc.getNewValue() == null) continue;
                try {
                    Long eqGrdGkey = Long.valueOf((String)fc.getNewValue());
                    EquipGrade equipGrade = (EquipGrade)HibernateApi.getInstance().get(EquipGrade.class, (Serializable)eqGrdGkey);
                    recordedFc = new FieldChange(UnitField.UNIT_GRADE_I_D, unitValue, (Object)equipGrade);
                    fcs.setFieldChange(recordedFc);
                }
                catch (NumberFormatException numberFormatException) {}
                continue;
            }
            fcs.setFieldChange(recordedFc);
        }
        if (accDmgFc != null) {
            Set ueSet = inUnit.getUnitUeSet();
            for (Object ue : ueSet) {
                if (!EqUnitRoleEnum.ACCESSORY.equals((Object)((UnitEquipment)ue).getUeEqRole())) continue;
                ((UnitEquipment)ue).setMobileDamagesVao(accDmgFc.getNewValue());
                break;
            }
        }
//        FieldChangeTracker tracker = inUnit.createFieldChangeTracker();
//        inUnit.applyFieldChanges(fcs);
//        InventoryCargoUtils.handleBundleEquipmentFields(inUnit, bundleChanges);
//        FieldChanges finalFcs = tracker.getChanges((IEntity)inUnit);
//        if (finalFcs.getFieldChangeCount() != 0) {
//            InventoryCargoUtils.recordUnitUpdateEvent(inUnit, eventNote, finalFcs);
//        }
        return fcs;
    }

    public static void recordUnitUpdateEvent(Unit inUnit, String inEventNote, FieldChanges inFinalFcs) {
        for (IMetafieldId ignoreProperty : UnitField.IGNORE_PROPERY_UPDATE_EVENT) {
            if (!inFinalFcs.hasFieldChange(ignoreProperty)) continue;
            inFinalFcs.removeFieldChange(ignoreProperty);
        }
        if (inFinalFcs.getFieldChangeCount() > 0) {
            Iterator fieldChangeIterator = inFinalFcs.getIterator();
            while (fieldChangeIterator.hasNext()) {
                FieldChange fieldChange = (FieldChange)fieldChangeIterator.next();
                Object priorValue = fieldChange.getPriorValue();
                Object newValue = fieldChange.getNewValue();
                if ((priorValue == null || newValue == null || !priorValue.equals(newValue)) && (priorValue != null && !"".equals(priorValue) || newValue != null && !"".equals(newValue))) continue;
                fieldChangeIterator.remove();
            }
            inUnit.recordUnitEvent((IEventType) EventEnum.UNIT_PROPERTY_UPDATE, inFinalFcs, inEventNote);
        }
    }

    public static Che resolveCheByShortName(String inCraneId) {
        Che currentChe = null;
        Serializable yardKey = ContextHelper.getYardKey((UserContext) ContextHelper.getThreadUserContext());
        currentChe = MoveEvent.resolveCheByShortName(inCraneId, yardKey);
        return currentChe;
    }

    public static void populateContactDetails(FieldChanges inOutFieldChanges) {
        Long custGkey;
        if (inOutFieldChanges.hasFieldChange(IArgoExtractField.GNTE_GUARANTEE_CUSTOMER) && (custGkey = (Long)inOutFieldChanges.getFieldChange(IArgoExtractField.GNTE_GUARANTEE_CUSTOMER).getNewValue()) != null) {
            ScopedBizUnit scopedBizUnit = ScopedBizUnit.loadByPrimaryKey((Serializable)custGkey);
            if (scopedBizUnit == null) {
                throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FAILURE__FIND_BY_PK, null, (Object)"ScopedBizUnit", (Object)custGkey);
            }
            ValueObject contacts = scopedBizUnit.getValueObject(InventoryCargoUtils.getContactFields());
            if (contacts != null) {
                HashMap<IMetafieldId, IMetafieldId> hashMap = InventoryCargoUtils.contactFieldsMap();
                for (IMetafieldId metafieldId : hashMap.keySet()) {
                    IMetafieldId contactField = hashMap.get((Object)metafieldId);
                    Object value = contacts.getFieldValue(contactField);
                    inOutFieldChanges.setFieldChange(metafieldId, value);
                }
            }
        }
    }

    public static HashMap<IMetafieldId, IMetafieldId> contactFieldsMap() {
        HashMap<IMetafieldId, IMetafieldId> fieldsMap = new HashMap<IMetafieldId, IMetafieldId>();
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_CONTACT_NAME, ArgoCompoundField.BZU_CT_NAME);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_ADDRESS1, ArgoCompoundField.BZU_CT_ADDR1);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_ADDRESS2, ArgoCompoundField.BZU_CT_ADDR2);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_ADDRESS3, ArgoCompoundField.BZU_CT_ADDR3);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_CITY, ArgoCompoundField.BZU_CT_CITY);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_MAIL_CODE, ArgoCompoundField.BZU_CT_MAIL_CODE);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_STATE, ArgoCompoundField.BZU_CT_STATE);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_COUNTRY, ArgoCompoundField.BZU_CT_COUNTRY);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_EMAIL_ADDRESS, ArgoCompoundField.BZU_CT_EMAIL);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_TELEPHONE, ArgoCompoundField.BZU_CT_TEL);
        fieldsMap.put(IArgoExtractField.GNTE_EXTERNAL_FAX, ArgoCompoundField.BZU_CT_FAX);
        return fieldsMap;
    }

    public static MetafieldIdList getContactFields() {
        return new MetafieldIdList(new IMetafieldId[]{ArgoCompoundField.BZU_CT_NAME, ArgoCompoundField.BZU_CT_ADDR1, ArgoCompoundField.BZU_CT_ADDR2, ArgoCompoundField.BZU_CT_ADDR3, ArgoCompoundField.BZU_CT_CITY, ArgoCompoundField.BZU_CT_MAIL_CODE, ArgoCompoundField.BZU_CT_STATE, ArgoCompoundField.BZU_CT_COUNTRY, ArgoCompoundField.BZU_CT_EMAIL, ArgoCompoundField.BZU_CT_TEL, ArgoCompoundField.BZU_CT_FAX});
    }

    public static void updateGuaranteeFieldChanges(FieldChanges inOutFieldChanges, BizRequest inRequest) {
        FieldChange fieldChange;
        if (inOutFieldChanges.hasFieldChange(IInventoryBizMetafield.EXTRACT_EVENT_TYPE)) {
            fieldChange = inOutFieldChanges.getFieldChange(IInventoryBizMetafield.EXTRACT_EVENT_TYPE);
            inOutFieldChanges.setFieldChange(IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, fieldChange.getNewValue());
            inOutFieldChanges.removeFieldChange(IInventoryBizMetafield.EXTRACT_EVENT_TYPE);
        }
        if (inOutFieldChanges.hasFieldChange(UnitField.UFV_UNIT_ID)) {
            fieldChange = inOutFieldChanges.getFieldChange(UnitField.UFV_UNIT_ID);
            inOutFieldChanges.setFieldChange(IArgoExtractField.GNTE_APPLIED_TO_NATURAL_KEY, fieldChange.getNewValue());
            inOutFieldChanges.removeFieldChange(UnitField.UFV_UNIT_ID);
        }
        if (inRequest.getUserContext() != null) {
            inOutFieldChanges.setFieldChange(IArgoExtractField.GNTE_N4_USER_ID, (Object)inRequest.getUserContext().getUserId());
        }
        inOutFieldChanges.setFieldChange(IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object) BillingExtractEntityEnum.INV);
        InventoryCargoUtils.populateContactDetails(inOutFieldChanges);
    }

    public static UnitFacilityVisit obtainUFvForDischargeOrLoad(ValueObject inResultVao, String inDigits) throws BizViolation {
        UnitFacilityVisit ufv;
        IQueryResult queryResult = InventoryCargoUtils.getFndr().doUfvQuery(inDigits, 1, false);
        int totalResultCount = queryResult.getTotalResultCount();
        if (totalResultCount == 0) {
            ufv = InventoryCargoUtils.extractUfvFromRequest(inDigits, inResultVao, false, 2);
        } else {
            if (totalResultCount > 1) {
                throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_INSPECTOR_FOUND_MANY, null, (Object)totalResultCount);
            }
            Serializable ufvKey = (Serializable)queryResult.getValue(0, UnitField.UFV_GKEY);
            ufv = (UnitFacilityVisit)HibernateApi.getInstance().get(UnitFacilityVisit.class, ufvKey);
        }
        inResultVao.setFieldValue(IInventoryField.UFV_GKEY, (Object)ufv.getUfvGkey());
        inResultVao.setFieldValue(IInventoryBizMetafield.UNIT_DIGITS, (Object)ufv.getUfvUnit().getHumanReadableKey());
        inResultVao.setFieldValue(IInventoryField.UNIT_ID, (Object)ufv.getUfvUnit().getHumanReadableKey());
        return ufv;
    }

    @Nullable
    public static Object getNewFieldValue(FieldChanges inFieldChanges, IMetafieldId inMetafieldId) {
        FieldChange fieldChange = inFieldChanges.getFieldChange(inMetafieldId);
        if (fieldChange != null) {
            return fieldChange.getNewValue();
        }
        return null;
    }

    public static Object getRequiredField(IMetafieldId inFieldId, FieldChanges inChanges) throws BizViolation {
        FieldChange fieldChange = inChanges.getFieldChange(inFieldId);
        if (fieldChange == null) {
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
    public static Object getFieldChangeNewValue(FieldChanges inFieldChanges, IMetafieldId inFieldId) {
        FieldChange filedChange = inFieldChanges.getFieldChange(inFieldId);
        return filedChange != null ? filedChange.getNewValue() : null;
    }

    @Nullable
    public static EquipNominalLengthEnum convertToEquipmentNominalLength(@Nullable Integer inContainerLengthFt) throws IllegalArgumentException {
        if (inContainerLengthFt == null) {
            return null;
        }
        return EquipNominalLengthEnum.getEnum((String)("NOM" + inContainerLengthFt));
    }

    @Nullable
    public static int convertFromEquipmentNominalLength(@NotNull EquipNominalLengthEnum inEquipNominalLengthEnum) {
        String lengthElement = inEquipNominalLengthEnum.getKey().substring(3);
        return Integer.parseInt(lengthElement);
    }

    @Nullable
    public static Integer getUnitLength(@NotNull String inUnitId) {
        if (inUnitId == null) {
            return null;
        }
        IUnitFinder unitFinder = (IUnitFinder) Roastery.getBean((String)"unitFinder");
        SearchResults results = null;
        try {
            results = unitFinder.findUfvByExactDigits(inUnitId, true, false);
        }
        catch (BizViolation inBizViolation) {
            LOGGER.warn((Object)("Could not find ufv for " + inUnitId));
            return null;
        }
        UnitFacilityVisitDO ufv = null;
        if (results != null) {
            ufv = (UnitFacilityVisit)HibernateApi.getInstance().load(UnitFacilityVisit.class, results.getFoundPrimaryKey());
        }
        try {
            EquipNominalLengthEnum nominalLengthEnum = ufv.getUfvUnit().getPrimaryEq().getEqEquipType().getEqtypNominalLength();
            return InventoryCargoUtils.convertFromEquipmentNominalLength(nominalLengthEnum);
        }
        catch (Exception ex) {
            LOGGER.warn((Object)("Could not update unit measured weight for " + inUnitId + " : " + ex.getMessage()));
            return null;
        }
    }

    public static IMessageCollector applyFlexFieldsToUnit(Serializable inUnitGkey, FieldChanges inFieldChanges) {
        BizResponse messageCollector = new BizResponse();
        FieldChanges unitFlexFieldChanges = new FieldChanges();
        Unit unit = Unit.hydrate(inUnitGkey);
        if (unit != null) {
            try {
                for (int i = 0; i < UNIT_FLEX_FIELDS_METAFIELDIDS.length; ++i) {
                    FieldChange fieldChange = inFieldChanges.getFieldChange(UNIT_FLEX_FIELDS_METAFIELDIDS[i]);
                    if (fieldChange == null) continue;
                    IMetafieldId metafieldId = fieldChange.getMetafieldId();
                    Object unitValue = unit.getFieldValue(metafieldId);
                    FieldChange recordedFc = new FieldChange(metafieldId, unitValue, fieldChange.getNewValue());
                    unitFlexFieldChanges.setFieldChange(recordedFc);
                }
                Set<IMetafieldId> customFields = InventoryCargoUtils.getEntityDynamicFields(unit);
                if (customFields != null) {
                    for (IMetafieldId customMfd : customFields) {
                        FieldChange fieldChange = inFieldChanges.getFieldChange(customMfd);
                        if (fieldChange == null) continue;
                        IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)(CUSTOM_FIELD_PREFIX + customMfd.getFieldId()));
                        Object unitValue = unit.getFieldValue(metafieldId);
                        FieldChange recordedFc = new FieldChange(metafieldId, unitValue, fieldChange.getNewValue());
                        unitFlexFieldChanges.setFieldChange(recordedFc);
                    }
                }
                if (unitFlexFieldChanges.getFieldChangeCount() > 0) {
                    unit.applyFieldChanges(unitFlexFieldChanges);
                }
            }
            catch (Exception ex) {
                LOGGER.error((Object)("Problem updating Flex fields to unit - " + ex));
                messageCollector.appendMessage(MessageLevelEnum.SEVERE, IFrameworkPropertyKeys.FAILURE__NAVIS, "Problem updating Flex fields to unit", null);
            }
        }
        return messageCollector;
    }

    public static void populateFlexFields(Serializable inUnitGkey, ValueObject inOutVao) {
        Unit unit = Unit.hydrate(inUnitGkey);
        if (unit != null) {
            for (int i = 0; i < UNIT_FLEX_FIELDS_METAFIELDIDS.length; ++i) {
                inOutVao.setFieldValue(UNIT_FLEX_FIELDS_METAFIELDIDS[i], unit.getFieldValue(UNIT_FLEX_FIELDS_METAFIELDIDS[i]));
            }
            Set<IMetafieldId> customFields = InventoryCargoUtils.getEntityDynamicFields(unit);
            if (customFields != null) {
                for (IMetafieldId mfid : customFields) {
                    IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)(CUSTOM_FIELD_PREFIX + mfid.toString()));
                    inOutVao.setFieldValue(mfid, unit.getFieldValue(metafieldId));
                }
            }
        }
    }

    @Nullable
    private static Set<IMetafieldId> getEntityDynamicFields(DatabaseEntity inEntity) {
        if (inEntity.getEntityName() != null) {
            IMetafieldEntity entityEntry = Roastery.getMetafieldDictionary().getEntityEntry(EntityIdFactory.valueOf((String)inEntity.getEntityName()));
            return entityEntry != null ? entityEntry.getDynamicFields() : null;
        }
        return null;
    }

    static {
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID1);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID2);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID3);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID4);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID5);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID6);
        BUNDLE_FLAGS.add(IInventoryBizMetafield.UNIT_BUNDLE_ID7);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID1);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID2);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID3);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID4);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID5);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID6);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID7);
        SLAVE_IDS.add(IInventoryBizMetafield.UNIT_SLAVE_ID8);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO1);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO2);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO3);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO4);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO5);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO6);
        SLAVE_DAMAGES.add(IInventoryBizMetafield.BUNDLE_DAMAGE_VAO7);
        UNIT_FLEX_FIELDS_METAFIELDIDS = new IMetafieldId[]{UnitField.UNIT_FLEX_STRING01, UnitField.UNIT_FLEX_STRING02, UnitField.UNIT_FLEX_STRING03, UnitField.UNIT_FLEX_STRING04, UnitField.UNIT_FLEX_STRING05, UnitField.UNIT_FLEX_STRING06, UnitField.UNIT_FLEX_STRING07, UnitField.UNIT_FLEX_STRING08, UnitField.UNIT_FLEX_STRING09, UnitField.UNIT_FLEX_STRING10, UnitField.UNIT_FLEX_STRING11, UnitField.UNIT_FLEX_STRING12, UnitField.UNIT_FLEX_STRING13, UnitField.UNIT_FLEX_STRING14, UnitField.UNIT_FLEX_STRING15};
    }
}
