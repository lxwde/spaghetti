package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IPrivilegeId;
import com.zpmc.ztos.infra.base.utils.StringUtils;

public class SimplePrivilegeId implements IPrivilegeId {
    private String _privilegeId;

    public SimplePrivilegeId(String inId) {
        this._privilegeId = inId;
    }

    @Override
    public String getKey() {
        return this._privilegeId;
    }

    public String toString() {
        return "SimplePrivilegeId:" + this._privilegeId;
    }

    public boolean equals(Object inObj) {
        if (this == inObj) {
            return true;
        }
        if (inObj instanceof IPrivilegeId) {
            return StringUtils.equals((String)this._privilegeId, (String)((IPrivilegeId)inObj).getKey());
        }
        return false;
    }

    public int hashCode() {
        return this._privilegeId.hashCode();
    }

}
