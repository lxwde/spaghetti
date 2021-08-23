package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.hibernate.cfg.Configuration;

public interface ISchemaExtensionLoader {
    public static final String BEAN_ID = "schemaExtensionLoader";

    public boolean processSchemaExtensions(UserContext var1, IReloadableSessionFactory var2, Boolean var3);

    public void applySchemaExtensionToConfigurationIfRequired(Configuration var1);

    public void restartPersistenceWithoutExtensions(UserContext var1, IReloadableSessionFactory var2);

    @NotNull
    public IExecutableSchemaExtension getExecutableSchemaExtension();

    @Nullable
    public IExecutedSchemaExtension getLastExecutedSchemaExtension();
}
