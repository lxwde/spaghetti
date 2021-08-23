package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.interfaces.IPrivilegeId;
import com.zpmc.ztos.infra.base.common.model.SimplePrivilegeId;

public class PrivilegeIdFactory {
    private PrivilegeIdFactory() {
    }

    public static IPrivilegeId valueOf(String inPrivilegeId) {
        return new SimplePrivilegeId(inPrivilegeId);
    }
}
