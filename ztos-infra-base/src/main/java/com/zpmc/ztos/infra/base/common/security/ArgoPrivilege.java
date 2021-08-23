package com.zpmc.ztos.infra.base.common.security;

import java.io.Serializable;

public class ArgoPrivilege extends Privilege {
    private Serializable _oprGkey;

    public ArgoPrivilege(String inPrivilegeName, Serializable inOprGkey, String inModuleId, String inInitialVersion, String inFeature) {
        super(inPrivilegeName, inModuleId, inInitialVersion, inFeature);
        this._oprGkey = inOprGkey;
    }

    public Serializable getOperatorGkey() {
        return this._oprGkey;
    }

}
