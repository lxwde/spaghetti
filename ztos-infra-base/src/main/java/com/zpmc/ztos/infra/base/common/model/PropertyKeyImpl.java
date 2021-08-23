package com.zpmc.ztos.infra.base.common.model;


import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;

public class PropertyKeyImpl implements IPropertyKey {

    private final String _key;
    private String _defaultFormat;

    public PropertyKeyImpl(String inKey)
    {
        //this(inKey,inKey);
        this._key = inKey;
    }

    public PropertyKeyImpl(String string, String string2) {
        this._key = string;
        this._defaultFormat = string2;
    }

    public String toString() {
        return "[PropertyKey=" + this._key + "]";
    }



    @Override
    public String getKey() {
        return this._key;
    }

    @Override
    public String getDefaultFormat() {
        return _defaultFormat;
    }

    public boolean equals(Object inO) {
        if (this == inO) {
            return true;
        }
        if (!(inO instanceof IPropertyKey)) {
            return false;
        }
        IPropertyKey propertyKey = (IPropertyKey)inO;
        return this._key.equals(propertyKey.getKey());
    }

    public int hashCode() {
        return this._key.hashCode();
    }
}
