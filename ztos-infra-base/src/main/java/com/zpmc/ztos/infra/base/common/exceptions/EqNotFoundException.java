package com.zpmc.ztos.infra.base.common.exceptions;

import com.zpmc.ztos.infra.base.business.interfaces.IArgoPropertyKeys;

public class EqNotFoundException
    extends BizViolation {
    public EqNotFoundException(String string)
    {
        super(IArgoPropertyKeys.EQUIPMENT_NOT_FOUND, null, null, null, new Object[]{string});
    }
}
