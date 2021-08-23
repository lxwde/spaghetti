package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.ICallingContextManager;
import com.zpmc.ztos.infra.base.business.interfaces.IGenericEntityImportBizFacade;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldDiagnostics;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;

public class PortalBeanUtils {

    public static ICallingContextManager getCallingContextManager() {
        return (ICallingContextManager)PortalApplicationContext.getBean("callingContextManager");
    }

    public static IMetafieldDiagnostics getIMetafieldDiagnostics() {
        return (IMetafieldDiagnostics) PortalApplicationContext.getBean("metafieldDiagnostics");
    }

    public static IGenericEntityImportBizFacade getGenericEntityImportBizFacade() {
        return (IGenericEntityImportBizFacade)PortalApplicationContext.getBean("genericEntityImportBizFacade");
    }

    private PortalBeanUtils() {
    }
}
