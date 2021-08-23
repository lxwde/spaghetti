package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.ExchangeOperationEnum;

import java.io.Serializable;

public interface IEntityImportResponse {
    public String getPrimaryEntityName();

    public Serializable getEntityPrimaryKey();

    public ExchangeOperationEnum getPrimaryAction();

    public String getSummary();

    public IMessageCollector getMessages();

    public boolean isInError();

    public void setAction(ExchangeOperationEnum var1);

    public void setMessageCollector(IMessageCollector var1);
}
