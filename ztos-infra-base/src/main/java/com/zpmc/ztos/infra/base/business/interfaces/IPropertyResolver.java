package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

public interface IPropertyResolver {
    public Object resolve(String var1, String var2) throws BizViolation;
}
