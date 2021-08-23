package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipmentOrderSubTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.FreightKindEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.UnitCategoryEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IArgoEquipmentOrderManager {
    public static final String BEAN_ID = "equipmentOrderManager";

    public void unReserveFacilityVisitUnits(Serializable[] var1) throws BizViolation;

    public void reserveEquipmentByUfvs(Serializable var1, Serializable[] var2, Serializable var3) throws BizViolation;

    public void assignExportBookingByUfvs(Serializable var1, Serializable var2, Serializable[] var3, boolean var4) throws BizViolation;

    public void assignRailOrderByUfvs(Serializable var1, Serializable var2, Serializable[] var3, boolean var4) throws BizViolation;

    public void offHireOnHireByUfvs(Serializable var1, Serializable[] var2, Serializable var3, Serializable var4, Date var5, Serializable var6) throws BizViolation;

    public IDomainQuery getEquipmentOrdersQueryForLine(Serializable var1);

    public IDomainQuery getEquipmentOrdersQueryForPoolPartners(Serializable var1, EquipClassEnum var2, UnitCategoryEnum var3, FreightKindEnum var4);

    public IDomainQuery getBookingsQueryForLine(Serializable var1);

    public Serializable[] getBookingGkeysForLineAndNbr(Serializable var1, String var2);

    public void validateUfvForUnReserve(Serializable[] var1) throws BizViolation;

    public void validateOBCarrierForReserve(Serializable var1, CarrierVisit var2, String var3) throws BizViolation;

    public void emptyReturnCreditTallyForBkg(Equipment var1);

    public void incrementTally(Serializable var1);

    public void decrementTally(Serializable var1);

    public EquipmentOrderSubTypeEnum getSubTypeForEquipmentOrder(Serializable var1);

    public void adjustTally(Serializable var1);

    public void preadviseEquipment(Serializable var1, Serializable var2, Serializable var3) throws BizViolation;

    public IDomainQuery getEquipmentOrdersQueryForLineAndSubTypes(Serializable var1, Serializable[] var2, EquipClassEnum var3);

    public void assignEROByUfvs(Serializable var1, Serializable[] var2, Serializable var3) throws BizViolation;

    public Serializable getUniqueBookingGkey(ScopedBizUnit var1, String var2, CarrierVisit var3) throws BizViolation;

    public CarrierVisit getBookingVesselVisit(Serializable var1) throws BizViolation;

    public Serializable getEquipmentOrderItem(Serializable var1, EquipType var2);

    public Serializable createBooking(ScopedBizUnit var1, String var2, CarrierVisit var3, FreightKindEnum var4, RoutingPoint var5, RoutingPoint var6, RoutingPoint var7);

    public Serializable createEro(ScopedBizUnit var1, String var2, FreightKindEnum var3);

    public Serializable createEquipmentOrderItem(Serializable var1, EquipType var2, Long var3);

    public void preadviseUnitForOrderItem(Serializable var1, Serializable var2) throws BizViolation;

    public BizResponse preadviseExportFromBarge(Serializable var1, Serializable var2, Serializable var3, UnitCategoryEnum var4) throws BizViolation;

    public EquipmentOrderSubTypeEnum getEquipmentOrderSubType(Serializable var1);

    public ValueObject getOrderDetails(Serializable var1);

    public ValueObject getOrderItemDetails(Serializable var1);

    public IDomainQuery getOrderItemsDqByEqType(Long var1, Long var2, Object[] var3);

    public IDomainQuery getOrderItemDq(IValueHolder var1, Serializable var2);

    public Serializable[] findEqOrderItemsByEqType(Serializable var1, EquipType var2);

    public boolean isEqTypeConformToSubstitution(ScopedBizUnit var1, EquipType var2, EquipType var3);

    @Nullable
    public List getEqOrderItemsQtyNotMet(Long var1);

    public boolean isEqTypeConformToSubstitutionForDsp(ScopedBizUnit var1, EquipType var2, EquipType var3);

    public void preadvisedStorageEmptyUnitForEro(Serializable var1, Equipment var2) throws BizViolation;

    public boolean isBookingPartialHoldQtyReceived(Serializable var1);

    public void updateOrderDetailsToUnit(Serializable var1, Serializable var2, Serializable var3) throws BizViolation;

    public boolean isPreadvisedAndReceiveLimitReached(String var1, ScopedBizUnit var2, CarrierVisit var3, EquipType var4, Serializable var5) throws BizViolation;


}
