package com.zpmc.ztos.infra.base.common.model;

public class SecuritySessionID extends UniqueID{

    @Override
    public boolean equals(Object inO) {
        if (inO == this) {
            return true;
        }
        if (inO instanceof SecuritySessionID) {
            return super.equals(inO);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
