package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IParameterAware;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;

public class ParamAwarePropertyKey extends PropertyKeyImpl
        implements IParameterAware {

    private final transient Object[] _parms;

    protected ParamAwarePropertyKey(IPropertyKey inKey, Object[] inParams) {
        super(inKey.getKey());
        this._parms = inParams;
    }

    @Override
    public Object[] getParms() {
        return this._parms;
    }
}
