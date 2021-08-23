package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;

public class PropertyKeyFactory {

    private PropertyKeyFactory() {
    }

    public static IPropertyKey valueOf(String inKey) {
        return new PropertyKeyImpl(inKey);
    }

    public static IPropertyKey valueOf(IPropertyKey inKey, Object[] inParams) {
        return new ParamAwarePropertyKey(inKey, inParams);
    }

    public static IPropertyKey keyWithFormat(String string, String string2) {
        return new PropertyKeyImpl(string, string2);
    }
}
