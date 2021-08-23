package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.enums.framework.ConfigClassEnum;

public class ScopeAwareConfig {

    private Long _cnfigScope;
    private String _cnfigScopeGkey;
    private Object _cnfigValue;
    private final ConfigClassEnum _cnfigClass;

    public ScopeAwareConfig(ConfigClassEnum inCnfigClass, Long inCnfigScope, String inCnfigScopeGkey, Object inCnfigValue) {
        this._cnfigClass = inCnfigClass;
        this._cnfigScope = inCnfigScope;
        this._cnfigScopeGkey = inCnfigScopeGkey;
        this._cnfigValue = inCnfigValue;
    }

    public ScopeAwareConfig(ConfigClassEnum inCnfigClass, Object inCnfigValue) {
        this._cnfigClass = inCnfigClass;
        this._cnfigValue = inCnfigValue;
    }

    public Long getCnfigScope() {
        return this._cnfigScope;
    }

    public String getCnfigScopeGkey() {
        return this._cnfigScopeGkey;
    }

    public Object getCnfigValue() {
        return this._cnfigValue;
    }

    public void setCnfigValue(Object inCnfigValue) {
        this._cnfigValue = inCnfigValue;
    }

    public ConfigClassEnum getCnfigClass() {
        return this._cnfigClass;
    }
}
