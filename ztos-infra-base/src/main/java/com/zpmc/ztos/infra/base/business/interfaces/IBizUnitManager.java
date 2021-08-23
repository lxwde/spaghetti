package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.model.Container;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IBizUnitManager {
    public static final String BEAN_ID = "bizUnitManager";

    @Nullable
    public ScopedBizUnit findOrCreateScopedBizUnit(String var1, BizRoleEnum var2);

    public ScopedBizUnit findOrCreateScopedBizUnitProxy(String var1, BizRoleEnum var2);

    public void upgradeEqOwner(Equipment var1, ScopedBizUnit var2, DataSourceEnum var3);

    public void upgradeEqOperator(Equipment var1, ScopedBizUnit var2, DataSourceEnum var3);

    public void upgradeEqOffhireLocation(Equipment var1, String var2, DataSourceEnum var3);

    public void upgradeFlexField(Equipment var1, int var2, String var3);

    @Nullable
    public ScopedBizUnit findEqOperator(Equipment var1);

    public List<Serializable> findEquipmentsFromEquipmentState(IMetafieldId var1, Serializable var2);

    @Nullable
    public ScopedBizUnit findEqOwner(Equipment var1);

    @Nullable
    public String findEqOffhireLocation(Equipment var1);

    @Nullable
    public String findFlexField(Equipment var1, int var2);

    public void upgradeEqOwner(Serializable var1, ScopedBizUnit var2, DataSourceEnum var3);

    public void upgradeEqOperator(Serializable var1, ScopedBizUnit var2, DataSourceEnum var3);

    public void upgradeEqOffhireLocation(Serializable var1, String var2);

    public void upgradeFlexField(Serializable var1, int var2, String var3);

    public Serializable findOrCreateEquipmentState(Equipment var1);

    public IDomainQuery findEqFromEquipmentStateDomainQuery(IMetafieldId var1, Serializable var2);

    public IDomainQuery findEqExistsEquipmentStateStateDq();

    public void validateCtrDelete(Container var1) throws BizViolation;

}
