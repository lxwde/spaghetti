package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IDecisionManager {
    public static final String BEAN_ID = "roleDecisionManager";

  //  public boolean isAllowed(UserContext var1, Object var2, ConfigAttributeDefinition var3);

    public boolean isAllowed(UserContext var1, ISecurableObject var2);
}
