package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;

public class HardCodedResourceKey extends PropertyKeyImpl {
    final String _hardCodedValue;

    public static HardCodedResourceKey valueOf(IPropertyKey inKey, String inHardCodedValue) {
        return new HardCodedResourceKey(inKey, inHardCodedValue);
    }

    @Override
    public String toString() {
        return super.toString() + " = " + this._hardCodedValue;
    }

    public String getHardCodedValue() {
        return this._hardCodedValue;
    }

    private HardCodedResourceKey(IPropertyKey inKey, String inHardCodedValue) {
        super(inKey.getKey());
        this._hardCodedValue = inHardCodedValue;
    }
}
