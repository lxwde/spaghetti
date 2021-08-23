package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class SecurityUtils {

    public static final String LCASE_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String UCASE_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NORMAL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMERALS = "0123456789";
    public static final String SPECIAL_CHARS = "!@#$%^&*()_-+=[{]}\\|;:'\",<>.?/`~";
    private static final String HEXMAP = "0123456789abcdef";
    private static final Logger LOGGER = Logger.getLogger(SecurityUtils.class);

    private SecurityUtils() {
    }

    @Nullable
    public static BizResponse validatePassword(IMetafieldId inPasswordFieldId, UserContext inContext, FieldChanges inChanges) {
        if (inChanges.hasFieldChange(inPasswordFieldId)) {
            FieldChange fieldChange = inChanges.getFieldChange(inPasswordFieldId);
            String rawpassword = (String)fieldChange.getNewValue();
            IPasswordManager passwordManager = (IPasswordManager) PortalApplicationContext.getBean("passwordManager");
            BizResponse passwordResponse = passwordManager.validatePassword(inContext, rawpassword);
            return passwordResponse;
        }
        return null;
    }

    public static void encryptPassword(FieldChanges inOutChanges) {
        SecurityUtils.encryptPassword(inOutChanges, ISecurityField.BUSER_PASSWORD);
        SecurityUtils.encryptPassword(inOutChanges, ISecurityBizMetafield.BUSER_NEW_PASSWORD);
        SecurityUtils.encryptPassword(inOutChanges, ISecurityBizMetafield.BUSER_CURRENT_PASSWORD);
    }

    private static void encryptPassword(FieldChanges inOutChanges, IMetafieldId inField) {
        if (inOutChanges.hasFieldChange(inField)) {
            FieldChange fieldChange = inOutChanges.getFieldChange(inField);
            String rawpassword = (String)fieldChange.getNewValue();
            String encryptedPass = SecurityUtils.encryptPassword(rawpassword);
            FieldChange encryptedFieldChange = new FieldChange(inField, fieldChange.getPriorValue(), (Object)encryptedPass);
            inOutChanges.setFieldChange(encryptedFieldChange);
        }
    }

    public static String encryptPassword(String inPassword) {
//        PasswordEncoder encoder = (PasswordEncoder)PortalApplicationContext.getBean("passwordEncoder");
//        String encryptedPass = encoder.encodePassword(inPassword, null);
//        return encryptedPass;
        return "";
    }

    @Deprecated
    @Nullable
    public static String encryptPasswordMD5(String inPassword) {
        String encryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(inPassword.getBytes());
            encryptedPassword = SecurityUtils.bytesToHexString(md.digest());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        return encryptedPassword;
    }

    public static String bytesToHexString(byte[] inBytes) {
        StringBuilder hashString = new StringBuilder();
        for (byte inByte : inBytes) {
            hashString.append(HEXMAP.charAt(inByte >> 4 & 0xF));
            hashString.append(HEXMAP.charAt(inByte & 0xF));
        }
        return hashString.toString();
    }

//    public static ConfigAttributeDefinition getConfigAttributeDefinition(String[] inPrivileges) {
//        ArrayList<IPrivilege> privs = new ArrayList<IPrivilege>();
//        PrivilegeManager privManager = (PrivilegeManager) Roastery.getBean("privilegeManager");
//        for (String privId : inPrivileges) {
//            IPrivilege realPriv = privManager.findPrivilege(PrivilegeIdFactory.valueOf(privId));
//            if (realPriv == null) {
//                LOGGER.error((Object)(privId + " does not exist."));
//                continue;
//            }
//            privs.add(realPriv);
//        }
//        return new ConfigAttributeDefinition(privs);
//    }
//
//    public static void logAuthorities(Logger inLogger, String inLabel, GrantedAuthority[] inOutGrantedAuthorities) {
//        if (inLogger.isDebugEnabled()) {
//            inLogger.debug((Object)(inLabel + " "));
//            for (GrantedAuthority authority : inOutGrantedAuthorities) {
//                inLogger.debug((Object)authority);
//            }
//        }
//    }
//
//    public static String getReadableStringForPrivileges(ConfigAttributeDefinition inAttributeDefinition) {
//        return SecurityUtils.getReadableStringForPrivileges(inAttributeDefinition, false);
//    }
//
//    public static String getReadableStringForPrivileges(ConfigAttributeDefinition inAttributeDefinition, boolean inAddFeatures) {
//        ArrayList<String> privIds = new ArrayList<String>();
//        for (ConfigAttribute config : inAttributeDefinition.getConfigAttributes()) {
//            IPrivilege priv;
//            StringBuilder buf = new StringBuilder(config.getAttribute());
//            if (inAddFeatures && config instanceof IPrivilege && !(priv = (IPrivilege)config).hasNoFeatures()) {
//                buf.append("[").append(StringUtils.collectionToCommaDelimitedString(priv.getFeatureIds())).append(']');
//            }
//            privIds.add(buf.toString());
//        }
//        return StringUtils.collectionToDelimitedString(privIds, (String)", ");
//    }

    public static String getLicenseDebugForUser(UserContext inContext) {
//        ILicenseManager licenseManager = SecurityBeanUtils.getLicenseManager();
//        IActivatedLicense license = licenseManager.getEffectiveLicense(inContext.getScopeCoordinate());
//        ISecurityContextManager ssecurityContextManager = SecurityBeanUtils.getSecurityContextManager();
//        ISecurityContext context = ssecurityContextManager.getSecureContext(inContext.getSecuritySessionId());
//        return "\n User Security" + context + "\n Effective license:  " + license;
        return null;
    }

    public static BizResponse validateExpiryDate(IMetafieldId inExpiryDateFieldId, UserContext inUserContext, FieldChanges inChanges, BizResponse inResponse) {
        Date now;
        FieldChange fieldChange;
        Date expiryDate;
        if (inChanges.hasFieldChange(inExpiryDateFieldId) && (expiryDate = (Date)(fieldChange = inChanges.getFieldChange(inExpiryDateFieldId)).getNewValue()) != null && expiryDate.before(now = DateUtil.getTodaysDate(inUserContext.getTimeZone()))) {
            if (inResponse == null) {
                inResponse = new BizResponse();
            }
            inResponse.appendMessage(MessageLevelEnum.SEVERE, IFrameworkUiPropertyKeys.ERROR__BUSER_EXPIRY_DATETIME, null, null);
        }
        return inResponse;
    }

    @NotNull
    public static String getSessionType(UserContext inUserContext) {
        String sessionType = "unknown";
        try {
//            ISecurityContextManager securityContextManager = SecurityBeanUtils.getSecurityContextManager();
//            ISecurityContext secureContext = securityContextManager.getSecureContext(inUserContext.getSecuritySessionId());
//            if (secureContext != null) {
//                sessionType = secureContext.getAuthenticationDetails().getSessionType().getName();
//            }
        }
        catch (BizFailure bizFailure) {
            // empty catch block
        }
        return sessionType;
    }
}
