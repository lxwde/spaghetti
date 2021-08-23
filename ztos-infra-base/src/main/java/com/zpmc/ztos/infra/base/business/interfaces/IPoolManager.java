package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipIsoGroupEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipNominalHeightEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipNominalLengthEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.inventory.Pool;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.util.List;

public interface IPoolManager {

    public static final String BEAN_ID = "poolManager";

    public boolean eqUseAllowed(Complex var1, ScopedBizUnit var2, ScopedBizUnit var3, Equipment var4) throws BizViolation;

    public List findPoolPartners(ScopedBizUnit var1, EquipClassEnum var2, EquipNominalLengthEnum var3, EquipIsoGroupEnum var4, EquipNominalHeightEnum var5);

    public Pool determinePoolForEquipment(Equipment var1);

    public void purgePool(Pool var1);
}
