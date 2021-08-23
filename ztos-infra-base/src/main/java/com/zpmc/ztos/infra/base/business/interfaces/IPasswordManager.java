package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IPasswordManager {
    public static final String BEAN_ID = "passwordManager";

    public BizResponse validatePassword(UserContext var1, String var2);
}
