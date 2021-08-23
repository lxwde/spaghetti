package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.LiftModeEnum;
import com.zpmc.ztos.infra.base.business.equipments.Chassis;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public interface IUnitManager {

    public static final String BEAN_ID = "unitManager";
    public static final String BENTO_ACTION_MARRY = "Marry";
    public static final String BENTO_ACTION_DIVORCE = "Divorce";
    public static final String BENTO_ACTION_VALIDATE = "Validate";
    public static final String BENTO_ACTION_MARRY_DIVORCE_VALIDATE = "action";
    public static final String BENTO_MARRY_DIVORCE_PRIMARY_UYV_GKEY = "primaryUyvGkey";
    public static final String BENTO_MARRY_DIVORCE_ATTACHED_UYV_GKEY = "attachedUyvGkey";
    public static final String BENTO_MARRY_DIVORCE_YARDBLOCK_ID = "yardSlot";
    public static final String BENTO_MARRY_DIVORCE_SLOT_ON_CARRIAGE = "slotOnCarriage";
    public static final String MW_BENTO = "MEN_WORKING_BENTO";
    public static final String MW_XPS_RESULT = "MW_XPS_RESULT";
    public static final String MW_XPS_ERR_MSG = "MW_XPS_ERR_MSG";
    public static final String XPS_MEN_WORKING_SUCCESS = "success";
    public static final String XPS_MEN_WORKING_ERROR = "error";
    public static final String MEN_WORKING_BENTO_USER_NAME = "userName";
    public static final String MEN_WORKING_BENTO_BIN_NAME = "binName";
    public static final String MEN_WORKING_BENTO_ACTION = "action";
    public static final String MEN_WORKING_BENTO_ROW_EXTENT = "rowExtent";
    public static final String MEN_WORKING_BENTO_COLUMN_EXTENT = "columnExtent";
    public static final String MEN_WORKING_BENTO_PARENT_NODE_NAME = "SetMenWorkingInArea";
    public static final String MEN_WORKING_BENTO_RANGE_NODE = "SetMenWorkingInArea";

    public void purgeUnit(Unit var1);

    public List<IMetafieldUserMessage> purgeUnitsByBatch(Serializable[] var1);

    public void purgeBreakbulkUnit(String var1, Complex var2);

    public void purgeBreakbulkUnit(Unit var1);

    public void rectifyUfv(UnitFacilityVisit var1, RectifyParms var2) throws BizViolation;

    public UnitFacilityVisit findOrCreateIncomingRoadUnit(Facility var1, CarrierVisit var2, Equipment var3) throws BizViolation;

    public UnitFacilityVisit findOrCreateIncomingRoadUnit(Facility var1, CarrierVisit var2, Equipment var3, UnitCategoryEnum var4) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnit(Facility var1, String var2, EquipType var3, UnitCategoryEnum var4, FreightKindEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnit(Facility var1, String var2, EquipType var3, UnitCategoryEnum var4, FreightKindEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, RoutingPoint var11) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnit(Facility var1, String var2, EquipType var3, UnitCategoryEnum var4, FreightKindEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, RoutingPoint var11, ScopedBizUnit var12, ScopedBizUnit var13, ScopedBizUnit var14) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnit(Facility var1, String var2, EquipType var3, UnitCategoryEnum var4, FreightKindEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, RoutingPoint var11, ScopedBizUnit var12, ScopedBizUnit var13, ScopedBizUnit var14, boolean var15) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnit(Facility var1, String var2, EquipType var3, UnitCategoryEnum var4, FreightKindEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, RoutingPoint var11, ScopedBizUnit var12, ScopedBizUnit var13, ScopedBizUnit var14, boolean var15, ScopedBizUnit var16) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnitEdi(Facility var1, String var2, EquipType var3, Equipment var4, UnitCategoryEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, RoutingPoint var11, EquipClassEnum var12) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnitEdi(Facility var1, String var2, EquipType var3, Equipment var4, UnitCategoryEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, EquipClassEnum var11, FreightKindEnum var12) throws BizViolation;

    public UnitFacilityVisit findOrCreatePreadvisedUnitEdi(Facility var1, String var2, EquipType var3, Equipment var4, UnitCategoryEnum var5, ScopedBizUnit var6, CarrierVisit var7, CarrierVisit var8, DataSourceEnum var9, String var10, EquipClassEnum var11) throws BizViolation;

    public UnitFacilityVisit findOrCreateStowplanUnit(Equipment var1, CarrierVisit var2, ScopedBizUnit var3, Facility var4) throws BizViolation;

    public UnitFacilityVisit findOrCreateStowplanUnit(Equipment var1, CarrierVisit var2, ScopedBizUnit var3, Facility var4, List<Facility> var5, FreightKindEnum var6, UnitCategoryEnum var7) throws BizViolation;

    public UnitFacilityVisit findOrCreateBargeStowplanUnit(Equipment var1, CarrierVisit var2, ScopedBizUnit var3, Facility var4, List<Facility> var5, UnitCategoryEnum var6) throws BizViolation;

    public UnitFacilityVisit findOrCreateBargeStowplanUnit(Equipment var1, CarrierVisit var2, ScopedBizUnit var3, Facility var4, UnitCategoryEnum var5) throws BizViolation;

    public Unit findOrCreateIncomingBreakbulkUnit(Complex var1, String var2, CarrierVisit var3) throws BizViolation;

    public Unit createBreakbulkUnitInYard(Facility var1, CarrierVisit var2, String var3, String var4, String var5) throws BizViolation;

    public Unit createBreakbulkUnitInContainer(Facility var1, Unit var2, String var3) throws BizViolation;

    public Unit splitBreakbulkUnit(Unit var1, String var2, Double var3) throws BizViolation;

    public UnitFacilityVisit createContainerizedUnit(String var1, Facility var2, CarrierVisit var3, CarrierVisit var4, Equipment var5, String var6, String var7, ScopedBizUnit var8) throws BizViolation;

    public Unit createContainerizedUnitAdvisedToAllFacilities(CarrierVisit var1, CarrierVisit var2, Equipment var3, String var4, String var5, ScopedBizUnit var6) throws BizViolation;

    public IGoods updateGoods(Unit var1, String var2, String var3, Commodity var4, String var5, String var6) throws BizViolation;

    public void loadUnitToOutboundVisit(UnitFacilityVisit var1, ILocation var2, MoveInfoBean var3, String var4, String var5) throws BizViolation;

    public void loadUnitToOutboundVisit(UnitFacilityVisit var1, ILocation var2, MoveInfoBean var3, String var4, String var5, Boolean var6) throws BizViolation;

    public void loadUnitToOutboundVisit(UnitFacilityVisit var1, ILocation var2, MoveInfoBean var3, Boolean var4) throws BizViolation;

    public void dischargeUnitFromInboundVisit(UnitFacilityVisit var1, VisitDetails var2, MoveInfoBean var3, String var4, String var5) throws BizViolation;

    public void dischargeUnitFromInboundVisit(UnitFacilityVisit var1, VisitDetails var2, MoveInfoBean var3, String var4, String var5, boolean var6) throws BizViolation;

    public void dischargeUnitFromInboundVisit(UnitFacilityVisit var1, VisitDetails var2, MoveInfoBean var3, String var4, String var5, boolean var6, String var7) throws BizViolation;

    public void dischargeUnitFromInboundVisit(UnitFacilityVisit var1, VisitDetails var2, MoveInfoBean var3, String var4, String var5, boolean var6, String var7, boolean var8) throws BizViolation;

    public void recordUnitYardMove(UnitFacilityVisit var1, String var2, String var3) throws BizViolation;

    public void recordUnitYardMove(UnitFacilityVisit var1, LocPosition var2) throws BizViolation;

    public void recordAutomatedUnitYardMove(UnitFacilityVisit var1, String var2, String var3, String var4) throws BizViolation;

    public void recordAutomatedUnitYardMove(UnitFacilityVisit var1, LocPosition var2) throws BizViolation;

    public void recordSlotCorrection(UnitFacilityVisit var1, String var2, String var3) throws BizViolation;

    public void updateSeals(UnitFacilityVisit var1, String var2, String var3, String var4, String var5, boolean var6, boolean var7) throws BizViolation;

    public void renumberUnitEquipment(UnitEquipment var1, String var2) throws BizViolation;

    public void renumberUnitEquipment(UnitEquipment var1, String var2, String var3) throws BizViolation;

    public void renumberBreakBulkUnit(Unit var1, String var2, String var3) throws BizViolation;

    public void mergeMeaselyUnits(Unit var1);

    public void mergeMeaselyUnits(Unit var1, boolean var2);

    public UnitFacilityVisit createYardBornUnit(Facility var1, Equipment var2, String var3, String var4) throws BizViolation;

    public UnitFacilityVisit createYardBornUnit(Equipment var1, LocPosition var2, String var3) throws BizViolation;

    public UnitFacilityVisit createYardBornUnitWithLineOp(Equipment var1, LocPosition var2, String var3, ScopedBizUnit var4) throws BizViolation;

    public UnitFacilityVisit createUnitWithoutRectify(Equipment var1, LocPosition var2, String var3, ScopedBizUnit var4) throws BizViolation;

    @Deprecated
    public UnitFacilityVisit detachAndCreateNewUnit(UnitEquipment var1) throws BizViolation;

    public UnitFacilityVisit detach(UnitEquipment var1) throws BizViolation;

    public void validateDeliveryAllowed(String var1) throws BizViolation;

    public UnitFacilityVisit applyFlagToCtrUnitByArrivalCarrier(String var1, ScopedBizUnit var2, CarrierVisit var3, String var4, String var5, String var6, Facility var7) throws BizViolation;

    public Date getMostRecentArrivalTime(Complex var1, Equipment var2);

    public UnitFacilityVisit findOrCreateOutgoingUnit(Equipment var1, Chassis var2, Set var3, Accessory var4, Accessory var5) throws BizViolation;

    public void confirmUnitEquipment(Unit var1, Equipment var2, Set var3, Equipment var4, Equipment var5) throws BizViolation;

    public void confirmUnitEquipment(Unit var1, Equipment var2, Set var3, Equipment var4, Equipment var5, boolean var6) throws BizViolation;

    public void returnUnitsToStorage(List var1) throws BizViolation;

    public Unit updatePinNbr(Unit var1, String var2) throws BizViolation;

    public void updateTruckingCo(Unit var1, Serializable var2);

    public void updateTruckingCo(Unit var1, FieldChange var2);

    public void clearDamages(Equipment var1);

    public Map getReeferUnitsInYardAreaNeedingAttention(UserContext var1, IMessageCollector var2, String var3, boolean var4, Long var5, IMobileTwoFieldSortDomainQueryContext var6);

    public Map getReeferUnitsInYardAreaNeedingAttention(UserContext var1, IMessageCollector var2, String var3, boolean var4, Long var5);

    public Map getReeferUnitAreasNeedingAttention(UserContext var1, IMessageCollector var2, boolean var3);

    public Map getReeferUnitAreasNeedingAttention(UserContext var1, IMessageCollector var2, boolean var3, IMobileTwoFieldSortDomainQueryContext var4);

    public void updateReefer(UserContext var1, IMessageCollector var2, Serializable var3, Map var4) throws BizViolation;

    public void dischargeAndReplanUnitThruXPS(String var1, VisitDetails var2, String[] var3, String[] var4, Boolean[] var5, String[] var6, String var7, String var8, LiftModeEnum var9) throws BizViolation;

    public void dischargeUnitThruXPS(String var1, VisitDetails var2, String[] var3, Boolean[] var4, String[] var5, String var6, String var7, LiftModeEnum var8) throws BizViolation;

    public void dischargeUnitThruXPS(String var1, VisitDetails var2, String[] var3, Boolean[] var4, String[] var5, @Nullable String[] var6, String[] var7, String[] var8, LiftModeEnum var9) throws BizViolation;

    public void loadUnitThruXPS(List<UnitFacilityVisit> var1, VisitDetails var2, String var3, String[] var4, String[] var5, LiftModeEnum var6) throws BizViolation;

    public void railLoadUnitThruXPS(UnitFacilityVisit var1, String var2, String var3, String var4) throws BizViolation;

    public Boolean computeUnitPlacardMismatch(Hazards var1, Collection<Placard> var2);

    @Nullable
    public UnitCategoryEnum deriveUnitCategory(RoutingPoint var1, RoutingPoint var2, RoutingPoint var3, RoutingPoint var4, CarrierVisit var5);

    public List<TbdUnit> createTbdUnits(Facility var1, Long var2, CarrierVisit var3, EquipType var4, ScopedBizUnit var5, RoutingPoint var6, UnitCategoryEnum var7, String var8, String var9) throws BizViolation;

    public List<TbdUnit> createTbdUnits(Facility var1, Long var2, CarrierVisit var3, EquipType var4, ScopedBizUnit var5, RoutingPoint var6, UnitCategoryEnum var7, String var8, String var9, RestowTypeEnum var10) throws BizViolation;

    public void transferUnitToFacility(UnitFacilityVisit var1, Facility var2) throws BizViolation;

    public void returnDeliveredUnitToYard(UnitFacilityVisit var1) throws BizViolation;

    public Map<LocPosition, List<Serializable>> findAllInterestedUfvsBySlot(@NotNull Yard var1, @NotNull List<LocPosition> var2, @NotNull EquipBasicLengthEnum var3) throws BizViolation;

    public void unbundleAllPayLoads(Unit var1) throws BizViolation;

    public void unbundleUnitEquipment(UnitEquipment var1) throws BizViolation;

    public void attachEquipment(Unit var1, String var2, EqUnitRoleEnum var3) throws BizViolation;

    public void hatchClerkAttachEquipment(Unit var1, String var2, EqUnitRoleEnum var3, String var4) throws BizViolation;

    public void updateContainerGenset(Unit var1, String var2) throws BizViolation;

    public void bumpUnit(UnitFacilityVisit var1, @Nullable String var2) throws BizViolation;

    public void bumpUnit(UnitFacilityVisit var1, @Nullable String var2, @Nullable String var3) throws BizViolation;

    public void updateMenWorkingInXPS(String var1, String var2) throws BizViolation;

    public void railDischargeUnitThruXPS(String var1, String[] var2, String var3, String[] var4) throws BizViolation;

    public void railDischargeUnitThruXPS(String var1, String[] var2, String var3, String[] var4, String var5) throws BizViolation;

    public Boolean isAnyUnitInYardForGroupId(Facility var1, Group var2);

    public void handleBentoReqeustAttachOrDetachEq(BentoNode var1) throws BizViolation;

    public IMessageCollector validateAttachChs(Unit var1, Chassis var2);

    public IMessageCollector validateAttachChs(Unit var1, Chassis var2, String var3);

    public Boolean isBreakBulkTruckMove(Unit var1, LocPosition var2);

    public Map<String, Object> requestXPSForLoadPosition(String[] var1, String var2, String var3, String var4) throws BizViolation;

    public boolean canUpdateIbCvAndPosition(UnitFacilityVisit var1, CarrierVisit var2);

    public boolean canUpdateIbCvAndPosition(CarrierVisit var1, CarrierVisit var2);

    public boolean canUpdateObCv(CarrierVisit var1, CarrierVisit var2);

    public boolean isTbaTrain(CarrierVisit var1);

    public UnitFacilityVisit findActiveUfvForUnitDigits(String var1) throws BizViolation;

    public UnitFacilityVisit findActiveUfvForUnitDigits(String var1, @Nullable IPredicate var2) throws BizViolation;

    public boolean isUnitRackSuitable(UnitFacilityVisit var1, Map<String, Object> var2);

    public void marryRequest(Serializable var1, Serializable var2) throws BizViolation;

    public void marryRequest(Serializable var1, Serializable var2, String var3) throws BizViolation;

    public void divorceRequest(Serializable var1, String var2) throws BizViolation;

    public IMessageCollector validateMarryRequest(Serializable var1, Serializable var2) throws BizViolation;

    public void markDischargeMisidentified(UnitFacilityVisit var1) throws BizViolation;
}
