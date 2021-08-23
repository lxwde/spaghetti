package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AuthenticationStatusEnum extends AtomizedEnum {
    public static final AuthenticationStatusEnum SUCCESS = new AuthenticationStatusEnum("SUCCESS", "atom.AuthenticationStatusEnum.SUCCESS.description", "atom.AuthenticationStatusEnum.SUCCESS.code", "", "", "");
    public static final AuthenticationStatusEnum BAD_PASSWORD = new AuthenticationStatusEnum("BADPASS", "atom.AuthenticationStatusEnum.BAD_PASSWORD.description", "atom.AuthenticationStatusEnum.BAD_PASSWORD.code", "", "", "");
    public static final AuthenticationStatusEnum BAD_USERID = new AuthenticationStatusEnum("BADID", "atom.AuthenticationStatusEnum.BAD_USERID.description", "atom.AuthenticationStatusEnum.BAD_USERID.code", "", "", "");
    public static final AuthenticationStatusEnum INACTIVE_USER = new AuthenticationStatusEnum("INACTIVE", "atom.AuthenticationStatusEnum.INACTIVE_USER.description", "atom.AuthenticationStatusEnum.INACTIVE_USER.code", "", "", "");
    public static final AuthenticationStatusEnum LOCKED_USER = new AuthenticationStatusEnum("LOCKED", "atom.AuthenticationStatusEnum.LOCKED_USER.description", "atom.AuthenticationStatusEnum.LOCKED_USER.code", "", "", "");
    public static final AuthenticationStatusEnum EXPIRED_PASSWORD = new AuthenticationStatusEnum("EXPIRED", "atom.AuthenticationStatusEnum.EXPIRED_PASSWORD.description", "atom.AuthenticationStatusEnum.EXPIRED_PASSWORD.code", "", "", "");
    public static final AuthenticationStatusEnum EXPIRED_ACCOUNT = new AuthenticationStatusEnum("EXPIREDACCT", "atom.AuthenticationStatusEnum.EXPIRED_ACCOUNT.description", "atom.AuthenticationStatusEnum.EXPIRED_ACCOUNT.code", "", "", "");
    public static final AuthenticationStatusEnum FORBIDDEN_IP_ADDRESS = new AuthenticationStatusEnum("BADIP", "atom.AuthenticationStatusEnum.FORBIDDEN_IP_ADDRESS.description", "atom.AuthenticationStatusEnum.FORBIDDEN_IP_ADDRESS.code", "", "", "");
    public static final AuthenticationStatusEnum BAD_SEARCH_ACCOUNT = new AuthenticationStatusEnum("BADSHACN", "atom.AuthenticationStatusEnum.BAD_SEARCH_ACCOUNT.description", "atom.AuthenticationStatusEnum.BAD_SEARCH_ACCOUNT.code", "", "", "");
    public static final AuthenticationStatusEnum BAD_SSO_TOKEN = new AuthenticationStatusEnum("BADSSOTOKEN", "atom.AuthenticationStatusEnum.BAD_SSO_TOKEN.description", "atom.AuthenticationStatusEnum.BAD_SSO_TOKEN.code", "", "", "");
    public static final AuthenticationStatusEnum EXPIRED_SSO_TOKEN = new AuthenticationStatusEnum("EXPIREDSSOTOKEN", "atom.AuthenticationStatusEnum.EXPIRED_SSO_TOKEN.description", "atom.AuthenticationStatusEnum.EXPIRED_SSO_TOKEN.code", "", "", "");
    public static final AuthenticationStatusEnum SYSTEM_ERROR = new AuthenticationStatusEnum("SYSTEMERROR", "atom.AuthenticationStatusEnum.SYSTEM_ERROR.description", "atom.AuthenticationStatusEnum.SYSTEM_ERROR.code", "", "", "");

    public static AuthenticationStatusEnum getEnum(String inName) {
        return (AuthenticationStatusEnum) AuthenticationStatusEnum.getEnum(AuthenticationStatusEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return AuthenticationStatusEnum.getEnumMap(AuthenticationStatusEnum.class);
    }

    public static List getEnumList() {
        return AuthenticationStatusEnum.getEnumList(AuthenticationStatusEnum.class);
    }

    public static Collection getList() {
        return AuthenticationStatusEnum.getEnumList(AuthenticationStatusEnum.class);
    }

    public static Iterator iterator() {
        return AuthenticationStatusEnum.iterator(AuthenticationStatusEnum.class);
    }

    protected AuthenticationStatusEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeAuthenticationStatusEnum";
    }
}
