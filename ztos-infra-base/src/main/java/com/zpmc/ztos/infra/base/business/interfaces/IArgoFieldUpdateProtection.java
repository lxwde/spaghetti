package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.Container;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;

public interface IArgoFieldUpdateProtection {
    public static final String BEAN_ID = "fieldUpdateProtection";

    public void checkForProtectedFieldUpdate(FieldChanges var1, Container var2) throws BizViolation;
}
