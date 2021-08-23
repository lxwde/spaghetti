package com.zpmc.ztos.infra.base.business.interfaces;

public interface IPlatformComponent extends IPlatformComponentKey, IEntityAudit{
    public String getComponentSubType();

    public String getComponentBusinessName();

    public String getComponentDesc();

    public boolean isEnabled();

    public String getComponentContent();

    public String getScopeId();
}
