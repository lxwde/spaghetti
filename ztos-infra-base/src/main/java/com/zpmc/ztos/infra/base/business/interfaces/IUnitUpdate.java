package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

public interface IUnitUpdate {
    public void apply(Unit var1) throws BizViolation;

    public boolean allowUpdate(Unit var1);

    public boolean applyOnAll(Unit var1);
}
