package com.zpmc.ztos.infra.base.business.interfaces;

public interface ISecuredObjectManager {
    public static final String BEAN_ID = "securedObjectManager";

//    @Nullable
//    public ConfigAttributeDefinition getPrivileges(ISecurableObject var1);
//
//    @Nullable
//    public ConfigAttributeDefinition getDenials(ISecurableObject var1);

    public boolean secureObject(ISecuredObjectDef var1);
}
