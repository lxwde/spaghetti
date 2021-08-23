package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.ILicenseManager;
import com.zpmc.ztos.infra.base.business.interfaces.ISecuredObjectManager;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityContextManager;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.ApplicationModuleSettings;

public class SecurityBeanUtils {
    private static final String CUSTOM_SECURED_MANAGER = "uiCustomSecuredActionElementManager";
    private static final String SECURED_METAFIELD_MANAGER = "securedMetafieldManager";

//    public static ISecurityAdminBizFacade getAdminBizFacade() {
//        return (ISecurityAdminBizFacade) PortalApplicationContext.getBean("securityAdminBizFacade");
//    }
//
//    public static IUserAuthenticationProfileBizFacade getUserAuthenticationProfileBizFacade() {
//        return (IUserAuthenticationProfileBizFacade)PortalApplicationContext.getBean("userAuthenticationProfileManager");
//    }
//
//    public static SecurityModuleSettings getSecurityModuleSettings() {
//        return (SecurityModuleSettings)PortalApplicationContext.getBean("securityModuleSettings");
//    }

    public static ILicenseManager getLicenseManager() {
        return (ILicenseManager)PortalApplicationContext.getBean("licenseManager");
    }

    public static ISecurityContextManager getSecurityContextManager() {
        return (ISecurityContextManager)PortalApplicationContext.getBean("securityContextManager");
    }

    public static ApplicationModuleSettings getApplicationModuleSettings() {
        return (ApplicationModuleSettings)PortalApplicationContext.getBean("appModuleSettings");
    }

    public static ISecuredObjectManager getMetafieldManager() {
        return (ISecuredObjectManager)PortalApplicationContext.getBean(SECURED_METAFIELD_MANAGER);
    }

    public static ISecuredObjectManager getSecuredObjectManager() {
        return (ISecuredObjectManager)PortalApplicationContext.getBean("securedObjectManager");
    }

    public static ISecuredObjectManager getCustomSecuredObjectManager() {
        return (ISecuredObjectManager)PortalApplicationContext.getBean(CUSTOM_SECURED_MANAGER);
    }

//    public static ICustomUISecurableObjectSource getCustomUISecurableObjectSource() {
//        return (ICustomUISecurableObjectSource)PortalApplicationContext.getBean("customUISecurableObjectSource");
//    }
//
//    public static ILdapService getLdapService() {
//        return (ILdapService)PortalApplicationContext.getBean("ldapServices");
//    }

    private SecurityBeanUtils() {
    }

}
