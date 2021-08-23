package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.ICodeExtensionValidator;
import com.zpmc.ztos.infra.base.business.interfaces.IExtensionTypeManager;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;

public class ExtensionBeanUtils {
//    public static IExtensionAdminBizFacade getExtensionAdminBizFacade() {
//        return (IExtensionAdminBizFacade)PortalApplicationContext.getBean("extensionAdminBizFacade");
//    }
//
    public static ICodeExtensionValidator getCodeValidator() {
        return (ICodeExtensionValidator)PortalApplicationContext.getBean("codeExtensionValidator");
    }

//    public static IExtensionClassProvider getExtensionClassProvider() {
//        return (IExtensionClassProvider)PortalApplicationContext.getBean("extensionClassProvider");
//    }

    public static IExtensionTypeManager getExtensionTypeManager() {
        return (IExtensionTypeManager)PortalApplicationContext.getBean("extensionTypeManager");
    }

//    public static IExtensionSystemLoader getExtensionSystemLoader() {
//        return (IExtensionSystemLoader)PortalApplicationContext.getBean("extensionSystemLoader");
//    }
//
//    public static IExtensionBizFacade getExtensionBizFacade() {
//        return (IExtensionBizFacade)PortalApplicationContext.getBean("extensionBizFacade");
//    }
//
//    public static IExtensionTransactionHandler getExtensionTransactionHandler() {
//        return (IExtensionTransactionHandler) PortalApplicationContext.getBean("extensionTransactionHandler");
//    }

    private ExtensionBeanUtils() {
    }
}
