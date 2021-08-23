package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EqBaseOrderItemDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.UnitEquipment;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.inventory.UnitField;
import com.zpmc.ztos.infra.base.business.model.AbstractJunction;
import com.zpmc.ztos.infra.base.business.model.EquipCondition;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Date;

public class EqBaseOrderItem extends EqBaseOrderItemDO {
    public void updateParentOrder(EqBaseOrder inEqOrder) {
        if (inEqOrder == null) {
            throw BizFailure.create((String)"Update EquipmentOrderItem ParentOrder is called with null value for new parent order. This is not a valid entry!");
        }
        this.setEqboiOrder(inEqOrder);
    }

    public IDomainQuery getUnitsReceivedForOrderItemDQ() {
        AbstractJunction departed = PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_VISIT_STATE, (Object)((Object) UnitVisitStateEnum.DEPARTED))).add(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_CURRENT_UFV_TRANSIT_STATE, (Object)((Object) UfvTransitStateEnum.S70_DEPARTED)));
        AbstractJunction retired = PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_VISIT_STATE, (Object)((Object) UnitVisitStateEnum.RETIRED))).add(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_CURRENT_UFV_TRANSIT_STATE, (Object)((Object) UfvTransitStateEnum.S99_RETIRED)));
        AbstractJunction positionNotTruck = PredicateFactory.disjunction().add(PredicateFactory.isNull((IMetafieldId) UnitField.UNIT_CURRENT_POS_TYPE)).add(PredicateFactory.ne((IMetafieldId) UnitField.UNIT_CURRENT_POS_TYPE, (Object) LocTypeEnum.TRUCK));
        AbstractJunction truckNotReturnToShipper = PredicateFactory.conjunction().add(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_CURRENT_POS_TYPE, (Object) LocTypeEnum.TRUCK)).add(PredicateFactory.ne((IMetafieldId) UnitField.UNIT_DRAY_STATUS, (Object) DrayStatusEnum.RETURN));
        AbstractJunction notTruckOrNonReturnDray = PredicateFactory.disjunction().add((IPredicate)positionNotTruck).add((IPredicate)truckNotReturnToShipper);
        AbstractJunction junctionDepartedTruck = PredicateFactory.conjunction().add((IPredicate)departed);
        if (!EquipmentOrderSubTypeEnum.ERO.equals((Object)this.getEqboiOrder().getEqboSubType())) {
            junctionDepartedTruck.add((IPredicate)notTruckOrNonReturnDray);
        }
        UfvTransitStateEnum[] allowedActiveSates = new UfvTransitStateEnum[]{UfvTransitStateEnum.S30_ECIN, UfvTransitStateEnum.S40_YARD, UfvTransitStateEnum.S50_ECOUT, UfvTransitStateEnum.S60_LOADED};
        IPredicate allowedActiveStatesPred = PredicateFactory.in((IMetafieldId) UnitField.UNIT_CURRENT_UFV_TRANSIT_STATE, (Object[])allowedActiveSates);
        AbstractJunction visitStates = PredicateFactory.disjunction().add((IPredicate)junctionDepartedTruck).add(allowedActiveStatesPred);
        if (EquipmentOrderSubTypeEnum.ERO.equals((Object)this.getEqboiOrder().getEqboSubType())) {
            visitStates.add((IPredicate)retired);
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Unit");
        if (EquipmentOrderSubTypeEnum.ERO.equals((Object)this.getEqboiOrder().getEqboSubType())) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_ARRIVAL_ORDER_ITEM, (Object)this.getEqboiGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_FREIGHT_KIND, (Object) FreightKindEnum.MTY));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) UnitField.UNIT_DEPARTURE_ORDER_ITEM, (Object)this.getEqboiGkey())).addDqPredicate(PredicateFactory.in((IMetafieldId) UnitField.UNIT_CATEGORY, (Object[])new Object[]{UnitCategoryEnum.EXPORT, UnitCategoryEnum.DOMESTIC, UnitCategoryEnum.TRANSSHIP}));
        }
        dq.addDqPredicate((IPredicate)visitStates).addDqPredicate(PredicateFactory.ne((IMetafieldId) UnitField.UNIT_VISIT_STATE, (Object)((Object) UnitVisitStateEnum.ADVISED))).setFullLeftOuterJoin(true);
        return dq;
    }

    public void validateUnitMatchesForLoadOut(UnitFacilityVisit inUfv) throws BizViolation {
    }

    public void validateUnitEquipmentCondition(UnitEquipment inUnitEquipment) throws BizViolation {
    }

    public void validateUnitEquipmentGrade(UnitEquipment inUnitEquipment) throws BizViolation {
    }

    public Double getEqoiTempRequired() {
        return null;
    }

    public EquipCondition getEqoiEqCondition() {
        return null;
    }

    public EquipGrade getEqoiEqGrade() {
        return null;
    }

    public Double getEqoiSafeWeightMin() {
        return null;
    }

    public Double getEqoiTareWeightMax() {
        return null;
    }

    public Date getEqoiCscExpirationMin() {
        return null;
    }

    public String getEqoiCscExpiration() {
        return null;
    }

    public EquipMaterialEnum getEqoiEqMaterial() {
        return null;
    }

    public Date getEqoiManufactureDateMin() {
        return null;
    }

    public Date getEqoiManufactureDateMax() {
        return null;
    }

    public Object[] getEqboiHeightWidthGrp() {
        return null;
    }

    public String getEqoiRemarks() {
        return null;
    }

    public EquipmentOrderSubTypeEnum getSubType() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EqBaseOrder").addDqField(IInventoryField.EQBO_SUB_TYPE).addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.EQBO_GKEY, (Object)this.getEqboiOrder().getEqboGkey()));
        IQueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        EquipmentOrderSubTypeEnum subType = null;
        if (result.getTotalResultCount() != 0) {
            subType = (EquipmentOrderSubTypeEnum)result.getValue(0, IInventoryField.EQBO_SUB_TYPE);
        }
        return subType;
    }

    public Long getEqoiTally() {
        return 0L;
    }

    public Long getEqoiQty() {
        return 0L;
    }

    protected void setEqoiTally(Long inNewEqoiTally) {
    }

    protected void setEqoiQty(Long inNewEqoiQty) {
    }

    public static EqBaseOrderItem hydrate(Serializable inPrimaryKey) {
        return (EqBaseOrderItem)HibernateApi.getInstance().load(EqBaseOrderItem.class, inPrimaryKey);
    }
}
