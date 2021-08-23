package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Date;

public interface IEntityAudit {
    public Date getCreated();

    public String getCreator();

    public Date getChanged();

    public String getChanger();
}
