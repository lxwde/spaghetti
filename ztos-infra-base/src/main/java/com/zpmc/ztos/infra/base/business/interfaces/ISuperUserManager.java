package com.zpmc.ztos.infra.base.business.interfaces;

public interface ISuperUserManager {
    public static final String BEAN_ID = "superAdminRoleVoter";

    public boolean isSuperUser(String var1);
}
