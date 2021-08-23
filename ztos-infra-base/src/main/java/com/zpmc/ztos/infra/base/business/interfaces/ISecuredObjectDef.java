package com.zpmc.ztos.infra.base.business.interfaces;

public interface ISecuredObjectDef extends ISecurableObject {
    public IPrivilege getPrivId();

    public boolean isSubtractive();

    public void setSubtractiveFlag(boolean var1);
}
