package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.core.SessionTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.security.AuthenticationDetailFactory;

import java.io.Serializable;

public class AuthenticationUtils {
    private AuthenticationUtils() {
    }

    public static Serializable getUserGkey(final String inUserId) {
        final Serializable[] key = new Serializable[1];
        PersistenceTemplatePropagationRequired template = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
        IMessageCollector collector = template.invoke(new CarinaPersistenceCallback(){

            @Override
            public void doInTransaction() {
//                SecurityDAO securityDao = (SecurityDAO) Roastery.getBean("securityDAO");
//                BaseUser user = securityDao.findUserByUid(inUserId);
//                if (user != null) {
//                    key[0] = user.getBuserGkey();
//                }
            }
        });
        if (collector.hasError()) {
            throw BizFailure.create("Deny privilege does not exist for privilege:" + collector);
        }
        return key[0];
    }

    public static UserContext getAuthenticatedUserContext(String inUserid, String inPassword, ScopeCoordinates inCoordinates) {
        Serializable userGkey = AuthenticationUtils.getUserGkey(inUserid);
        ISecurityContext secureContext = AuthenticationUtils.getAuthenticatedSecurityContext(inUserid, inPassword);
        UserContext userContext = UserContextUtils.createUserContext(userGkey, inUserid, inCoordinates);
        userContext.setSecuritySessionId(secureContext.getSecuritySessionId());
        if (secureContext instanceof ISecurityContextAware) {
            ((ISecurityContextAware)secureContext).setUserContext(userContext);
        }
        userContext.setBroadestAllowedScope(secureContext.getUserDetails().getBroadestAllowedScope());
        return userContext;
    }

    public static UserContext stashAuthenticatedUserContext(@NotNull String inUserid, String inPassword, ScopeCoordinates inCoordinates) {
        ISecurityContext securityContext = AuthenticationUtils.getAuthenticatedSecurityContext(inUserid, inPassword);
        UserContext userContext = UserContextUtils.createUserContext(securityContext.getUserDetails().getUserGkey(), inUserid, inCoordinates);
        userContext.setSecuritySessionId(securityContext.getSecuritySessionId());
        if (securityContext instanceof ISecurityContextAware) {
            ((ISecurityContextAware)securityContext).setUserContext(userContext);
        }
        ISecurityContextManager ssecurityContextManager = SecurityBeanUtils.getSecurityContextManager();
        ssecurityContextManager.setSecureContext(securityContext);
        ILicenseManager licenseManager = SecurityBeanUtils.getLicenseManager();
        licenseManager.filterLicensedPrivileges(userContext);
        return userContext;
    }

    public static ISecurityContext createAuthenticatedSecurityContext(String inUserid, String inPassword, String inIPAddress, SessionTypeEnum inSessionTypeEnum) {
        IAuthenticationDetails details;
        Serializable userGkey = AuthenticationUtils.getUserGkey(inUserid);
        if (userGkey == null) {
            throw BizFailure.createProgrammingFailure("You must pass in a real user for getAuthenticatedUserContext." + inUserid + " does not exist.");
        }
        IAuthPasswordManager authPasswordManager = (IAuthPasswordManager) PortalApplicationContext.getBean("authPasswordManager");
        IAuthenticationResponse response = authPasswordManager.authenticate(inUserid, inPassword, details = AuthenticationDetailFactory.createAuthenticationDetails(inIPAddress, inSessionTypeEnum));
        if (!response.isAuthenticated()) {
            throw BizFailure.createProgrammingFailure("Authentication Failure for " + inPassword + "/" + inPassword + " from " + response.getMessageCollector());
        }
        ISecurityContext secureContext = response.getSecurityContext();
        return secureContext;
    }

    private static ISecurityContext getAuthenticatedSecurityContext(String inUserid, String inPassword) {
        return AuthenticationUtils.createAuthenticatedSecurityContext(inUserid, inPassword, null, SessionTypeEnum.BACKEND);
    }
}
