package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.extension.SchemaExtensionExecutionStatusEnum;

import java.util.Date;

public interface IExecutedSchemaExtension {
    public IExecutableSchemaExtension getExecutableSchemaExtension();

    @NotNull
    public SchemaExtensionExecutionStatusEnum getExecutionStatus();

    public Date getExtensionExecutionStartTime();

    public Date getExtensionExecutionEndTime();

    public IExecutedDDL getExecutedDDL();

    public IMessageCollector getExecutionMessageCollector();

    public void setExecutionStatus(SchemaExtensionExecutionStatusEnum var1);

    public void setExecutionCollector(IMessageCollector var1);

    public void setExecutionEndTime(Date var1);

    public void setExecutedDDL(IExecutedDDL var1);
}
