package com.zpmc.ztos.infra.base.business.interfaces;

public interface IOrderPurpose {
    public String getId();

    public String getDescription();

    public boolean isBuiltInEventType();

    public String getExternalId();
}
