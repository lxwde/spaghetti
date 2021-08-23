package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrder;
import com.zpmc.ztos.infra.base.business.equipments.EqBaseOrderItem;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.inventory.SearchResults;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.inventory.UnitEquipment;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IUnitFinder {
    public static final String BEAN_ID = "unitFinder";
    public static final int UFV_FIND_ALL = 0;
    public static final int UFV_FIND_ACTIVE = 1;
    public static final int UFV_FIND_ADVISED = 2;
    public static final int UFV_FIND_ACTIVE_AND_ADVISED = 3;
    public static final int UFV_FIND_DEPARTED = 4;
    public static final int UFV_FIND_ACTIVE_OR_ADVISEDBYVESSEL = 5;
    public static final int UFV_FIND_YARD_UNITS = 6;
    public static final int UFV_FIND_YARD_AND_LOADED_UNITS = 7;
    public static final int UFV_FIND_YARD_AND_TRAIN_LOADED_UNITS = 8;
    public static final int REF_MON_FIND_POWER_ACTIONS = 0;
    public static final int REF_MON_FIND_ALL_ACTIONS = 1;
    public static final int REF_MON_FIND_MONITORING_ACTIONS = 2;
    public static final int REF_MON_FIND_POWER_ALARM_ACTIONS = 3;

    @Nullable
    public Unit findActiveUnit(Complex var1, Equipment var2);

    @Nullable
    public Unit findActiveUnit(Complex var1, Equipment var2, UnitCategoryEnum var3);

    public Unit findActiveUeUsingEqInAnyRole(Operator var1, Complex var2, Equipment var3);

    public Unit findAdvisedUnitByCarrierBestMatch(Complex var1, Equipment var2, CarrierVisit var3);

    public UnitFacilityVisit findPreadvisedUnit(Facility var1, Equipment var2);

    @Nullable
    public UnitFacilityVisit findPreadvisedUnit(Facility var1, Equipment var2, UnitCategoryEnum var3);

    public UnitFacilityVisit findPreadvisedUnitByCategory(Facility var1, Equipment var2, UnitCategoryEnum var3);

    public List<Unit> findAdvisedUnits(Complex var1, Equipment var2, LocTypeEnum[] var3);

    public List<Unit> findAdvisedUnits(Complex var1, Equipment var2, LocTypeEnum[] var3, UnitCategoryEnum var4);

    public Unit findDepartedUnit(Complex var1, Equipment var2);

    public Unit findAttachedUnit(Complex var1, Equipment var2);

    @Nullable
    public UnitFacilityVisit findLiveUfvByEquipment(Facility var1, Equipment var2);

    public UnitFacilityVisit findUfvByIbCarrier(Facility var1, Equipment var2, CarrierVisit var3, boolean var4);

    public UnitFacilityVisit findUfvByIbCarrier(Facility var1, Equipment var2, CarrierVisit var3, boolean var4, UnitCategoryEnum var5);

    public List<UnitFacilityVisit> findUfvsByObCarrier(Facility var1, Equipment var2, CarrierVisit var3, UnitVisitStateEnum[] var4, UnitCategoryEnum var5);

    public Serializable[] findArrivingUfvByIbCarrier(CarrierVisit var1);

    public UnitFacilityVisit findLiveUfvByDeclaredIBCarrier(Facility var1, Equipment var2, CarrierVisit var3);

    public UnitFacilityVisit findLiveUfvByObCarrier(Facility var1, Equipment var2, CarrierVisit var3);

    public UnitFacilityVisit findLiveUfvByDeclaredOBCarrier(Facility var1, Equipment var2, CarrierVisit var3);

    public UnitFacilityVisit findLiveUfv(Facility var1, Equipment var2, LocTypeEnum var3, CarrierDirectionEnum var4, CarrierVisit var5) throws BizViolation;

    public List findAllUnitsByDeclaredIbCarrier(CarrierVisit var1);

    public List findAllUnitsByDeclaredOBCarrier(CarrierVisit var1);

    public List findAllUnitsByDeclaredIbCarrier(CarrierVisit var1, String var2);

    public Collection findAllUnitsUsingEq(Complex var1, Equipment var2);

    public Collection<Unit> findAdvisedUnitsUsingEq(Complex var1, Equipment var2);

    public Collection<Unit> findAdvisedUnitsUsingEq(Complex var1, Equipment var2, UnitCategoryEnum var3);

    public Serializable findMostInterestingUfvGkeyUsingEq(Facility var1, Equipment var2);

    public Serializable[] findUfvGkeysWithinHorizon(Serializable[] var1);

    public List<String> findUnitIdList(List var1);

    public Collection findAllUeUsingEqInAnyRole(Complex var1, Equipment var2, UnitVisitStateEnum[] var3);

    public UnitFacilityVisit findUfvByPaidThurDayForFacilty(Facility var1, Equipment var2, Date var3);

    public UnitFacilityVisit findUfvByPaidThurDayByExtractGkeyForFacilty(Facility var1, Equipment var2, Date var3, Long var4);

    public List findNotDetachedUnitEquipment(Operator var1, Complex var2, Equipment var3, boolean var4);

    public Unit findNewestPreviousUnit(Equipment var1, Unit var2);

    @Deprecated
    public UnitEquipment findNewestPreviousUeUsingEqInAnyRole(Equipment var1, Unit var2);

    public SearchResults findUfvByDigits(String var1, boolean var2, boolean var3) throws BizViolation;

    public SearchResults findUfvByExactDigits(String var1, boolean var2, boolean var3) throws BizViolation;

    public SearchResults findUfvInYardByDigits(String var1, boolean var2) throws BizViolation;

    public SearchResults findUfvInYardAndOnTrainByDigits(String var1, boolean var2) throws BizViolation;

    public IQueryResult doUfvQuery(String var1, int var2, boolean var3);

    public IQueryResult doUfvQuery(String var1, @Nullable IPredicate var2, int var3, boolean var4);

    public List findAllUfvOnBoard(CarrierVisit var1);

    public Collection findUnitsForOrder(EqBaseOrder var1);

    public Collection<Unit> findUnitsForOrderItem(EqBaseOrderItem var1);

    public IDomainQuery findUnitsForOrderItemDq(EqBaseOrderItem var1);

    public Collection findUnitsAdvisedForOrder(EqBaseOrder var1);

    public Collection findUnitsAdvisedOrReceivedForOrder(EqBaseOrder var1);

    public Collection findUnitsAdvisedOrReceivedForOrderItem(EqBaseOrderItem var1);

    public Collection findUnitsAdvisedForOrderItem(EqBaseOrderItem var1);

    public Collection findUnitsReceivedForOrderItem(EqBaseOrderItem var1);

    public Collection findTbdUnitsForOrderItem(EqBaseOrderItem var1);

    public Collection findUnitsReservedOrDeliveredForOrder(EqBaseOrder var1);

    public Collection findUnitsReservedOrDeliveredForOrderItem(EqBaseOrderItem var1);

    public Collection findUnitsReservedForOrderItem(EqBaseOrderItem var1);

    public Serializable[] findUnitsReservedForOrder(EqBaseOrder var1);

    public Collection findUnitsDeliveredForOrder(EqBaseOrder var1);

    public Collection findUnitsDeliveredForOrderItem(EqBaseOrderItem var1);

    public Unit findAdvisedUnitByLandModes(Complex var1, Equipment var2);

    public Unit findAdvisedUnitByLandModes(Complex var1, Equipment var2, UnitCategoryEnum var3);

    public Unit findActiveUnitByLandModes(Complex var1, Equipment var2);

    public Unit findActiveUnitByLandModes(Complex var1, Equipment var2, UnitCategoryEnum var3);

    public List findAllUfvByPosition(LocPosition var1);

    public List findAllUfvInYardArea(String var1);

    public Unit findActiveBreakbulkUnit(Complex var1, String var2);

    public Unit findAdvisedBreakbulkUnit(Complex var1, String var2, LocTypeEnum var3);

    public Unit findUnitByHostKey(Complex var1, String var2);

    public SearchResults findUfvOnRailByDigits(String var1, boolean var2) throws BizViolation;

    public SearchResults findUfvDepartingByRail(String var1, boolean var2) throws BizViolation;

    public SearchResults findUfvDepartingByGenericCarrier(String var1, boolean var2) throws BizViolation;

    public SearchResults findUfvArrivingByRail(String var1, boolean var2) throws BizViolation;

    public IQueryResult findReeferUnitsInYard(Boolean var1, Boolean var2, boolean var3);

    public IQueryResult findReeferUnitsInYardNeedingAction(boolean var1);

//    public IQueryResult findReeferUnitsInYardNeedingAction(boolean var1, IMobileTwoFieldSortDomainQueryContext var2);
//
//    public IQueryResult findReefersInYardAreaNeedingAttention(String var1, boolean var2, int var3, IMobileTwoFieldSortDomainQueryContext var4);

    public Serializable[] findReefersInAllYardAreaNeedingAttention();

    public IQueryResult findReefersInYardAreaNeedingAttention(String var1, boolean var2, int var3);

    public IQueryResult findRecentlyMonitoredReeferUnitsInYard(long var1);

    @Nullable
    public Serializable findMostRecentUeGkey(Serializable var1, Serializable var2);

    public Map<LocPosition, List<Serializable>> findAllUfvsByPosition(LocPosition[] var1);

    public Unit findLiveUnitByCustomsId(String var1) throws BizViolation;

    public List<UnitFacilityVisit> findUfvsByCarrierPosition(WiMoveKindEnum var1, LocPosition var2);

    public List<UnitFacilityVisit> findUfvsByCarrierPosition(@NotNull Serializable var1, @NotNull String var2);

    public List<WorkInstruction> findWorkInstructionByPosition(LocPosition var1, WiMoveKindEnum var2, WiMoveStageEnum var3);

    public Map<Serializable, IValueHolder> findUfvForWiMap(@NotNull Serializable[] var1, @NotNull IMetafieldId[] var2, @NotNull IMetafieldId[] var3);

    public int findUnitsDeliveredCount(EqBaseOrderItem var1);

    public IDomainQuery findUnitsDeliveredDq(EqBaseOrderItem var1);

    public int findUnitsReservedCount(EqBaseOrderItem var1);

    public IDomainQuery findUnitsReservedDq(EqBaseOrderItem var1);

    public Serializable[] findReeferUnitGoodsForLine(ScopedBizUnit var1);

    public Serializable[] findRfrUnitsInYardNeedingExtMonitors();

    public Collection<Unit> findAllUnitsUsingEqInAnyRole(Complex var1, Equipment var2, UnitVisitStateEnum[] var3);

    public Serializable[] findAllUfvOfMostInterstingUnit(Complex var1, Equipment var2);

    public int findUnitsForBL(String var1);
}
