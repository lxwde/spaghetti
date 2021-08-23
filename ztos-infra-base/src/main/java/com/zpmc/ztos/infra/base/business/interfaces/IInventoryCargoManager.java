package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.UnitCategoryEnum;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IInventoryCargoManager {

    public static final String BEAN_ID = "inventoryCargoManager";

    public Collection getBlGoodsBls(Unit var1) throws BizViolation;

    public Collection getValidBillsOfLadingForUnit(Unit var1) throws BizViolation;

    public void assignUnitBillOfLading(Unit var1, Serializable var2) throws BizViolation;

    public IBillOfLading findBillOfLading(String var1, ScopedBizUnit var2, CarrierVisit var3) throws BizViolation;

    public IBillOfLading findOrCreateBillOfLading(String var1, ScopedBizUnit var2, UnitCategoryEnum var3, CarrierVisit var4, DataSourceEnum var5) throws BizViolation;

    public IBlGoodsBl findOrCreateBlGoodsBl(Unit var1, IBillOfLading var2) throws BizViolation;

    public IBlGoodsBl findOrCreateBlGdsBl(Unit var1, IBillOfLading var2) throws BizViolation;

    public IBillOfLading updateBlRouting(IBillOfLading var1, RoutingPoint var2, RoutingPoint var3, RoutingPoint var4, String var5, RoutingPoint var6, RoutingPoint var7) throws BizViolation;

    public void removeUnitBillOfLading(Unit var1, IBillOfLading var2) throws BizViolation;

    public boolean hasBlAttachedGoodsOrItems(IBillOfLading var1);

    public void deleteBillOfLading(IBillOfLading var1);

    public List findBillOfLadings(String var1) throws BizViolation;

    public List findUnitsForBillOfLading(Object var1) throws BizViolation;

    public Unit importDeliveryOrderAssign(String var1, Serializable var2, Serializable var3, Serializable var4, Date var5) throws BizViolation;

    public void moveUnitCallback(UnitFacilityVisit var1) throws BizViolation;

    public void renumberUnitCallback(Unit var1) throws BizViolation;

    public byte[] getUnitIcon(Serializable var1);

    public boolean hasUnitAttachedCargoLots(Unit var1);

    @Nullable
    public Collection getBlsForGoodsBl(Unit var1) throws BizViolation;

    public void stripUnitAndLoadLots(UnitFacilityVisit var1, LocPosition var2) throws BizViolation;

    public void updateCargoLotWeightOfUnit(Unit var1);

    public Object stuffCargoLot(Unit var1, Serializable var2, Double var3, String var4) throws BizViolation;

}
