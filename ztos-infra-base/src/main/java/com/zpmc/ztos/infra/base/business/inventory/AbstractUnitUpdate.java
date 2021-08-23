package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IUnitUpdate;

public abstract class AbstractUnitUpdate implements IUnitUpdate {
    @Override
    public boolean applyOnAll(Unit inUnit) {
        return EqUnitRoleEnum.PRIMARY == inUnit.getUnitEqRole();
    }

    @Override
    public boolean allowUpdate(Unit inUnit) {
        return true;
    }
}
