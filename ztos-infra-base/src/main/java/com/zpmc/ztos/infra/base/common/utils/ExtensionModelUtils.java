package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.ISchemaExtensionLoader;
import com.zpmc.ztos.infra.base.common.model.Roastery;

public class ExtensionModelUtils {
    private static final String EXTENSION = "com.navis.external.model.extension";
    private static final String ENABLED = "enabled";
    private static final String DISABLED = "disabled";

//    public static ISchemaSnapshotBizFacade getSchemaSnapshotBizFacade() {
//        return (ISchemaSnapshotBizFacade) PortalApplicationContext.getBean("schemaSnapshotBizFacade");
//    }

    public static ISchemaExtensionLoader getSchemaExtensionLoader() {
        return (ISchemaExtensionLoader) Roastery.getBean("schemaExtensionLoader");
    }

    public static boolean schemaExtensionsEnabled() {
        String prop = System.getProperty(EXTENSION);
        return !DISABLED.equals(prop);
    }

    public static void setSchemaExtensionsEnabled(boolean inEnable) {
        if (inEnable) {
            System.setProperty(EXTENSION, ENABLED);
        } else {
            System.setProperty(EXTENSION, DISABLED);
        }
    }
}
