package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.extension.PersistenceEventStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.extension.PersistenceEventTypeEnum;
import com.zpmc.ztos.infra.base.common.database.ExecutedDDLHolder;
import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.io.Serializable;
import java.util.Date;

public interface IPersistenceEventRecorder {
    public static final String BEAN_ID = "persistenceEventRecorder";

    public Serializable record(UserContext var1, PersistenceEventTypeEnum var2, PersistenceEventStatusEnum var3, IExecutedSchemaExtension var4, Date var5);

    public Serializable record(PersistenceEventTypeEnum var1, ExecutedDDLHolder var2, Date var3, PersistenceEventStatusEnum var4);

    public void updateEvent(@NotNull Serializable var1, IExecutedSchemaExtension var2, PersistenceEventStatusEnum var3);

}
