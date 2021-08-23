package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface ISecurityBizMetafield {

    public static final IMetafieldId BUSER_EMPLOYER_ORG = MetafieldIdFactory.valueOf("buserEmployerOrg");
    public static final IMetafieldId BUSER_PRIMARY_ORG = MetafieldIdFactory.valueOf("buserPrimaryOrg");
    public static final IMetafieldId BUSER_PRIMARY_ORG_NAME = MetafieldIdFactory.valueOf("buserPrimaryOrgName");
    public static final IMetafieldId BUSER_CURRENT_PASSWORD = MetafieldIdFactory.valueOf("buserCurrentPassword");
    public static final IMetafieldId BUSER_NEW_PASSWORD = MetafieldIdFactory.valueOf("buserNewPassword");
    public static final IMetafieldId BUSER_FULL_NAME = MetafieldIdFactory.valueOf("buserFullName");
    public static final IMetafieldId USER_ROLE_VAOS = MetafieldIdFactory.valueOf("userRoleVaos");
    public static final IMetafieldId VERIFIED_PASSWORD = MetafieldIdFactory.valueOf("verifiedPassword");
    public static final IMetafieldId BUSER_EXPIRY_DATE_TIME = MetafieldIdFactory.valueOf("buserExpiryDateTime");
    public static final IMetafieldId ORG_TYPE = MetafieldIdFactory.valueOf("orgType");
    public static final IMetafieldId ORG_NAME_NOT_REQ = MetafieldIdFactory.valueOf("orgNameNotReq");
    public static final IMetafieldId ORG_CODE_NOT_REQ = MetafieldIdFactory.valueOf("orgCodeNotReq");
    public static final IMetafieldId ORG_TYPE_NOT_REQ = MetafieldIdFactory.valueOf("orgTypeNotReq");
    public static final IMetafieldId ROLE_PRIVILEGES = MetafieldIdFactory.valueOf("rolePrivileges");
    public static final IMetafieldId PRIV_ID = MetafieldIdFactory.valueOf("privId");
    public static final IMetafieldId PRIV_NAME = MetafieldIdFactory.valueOf("privName");
    public static final IMetafieldId PRIV_DESC = MetafieldIdFactory.valueOf("privDesc");
    public static final IMetafieldId PRIV_SCOPE = MetafieldIdFactory.valueOf("privScope");
    public static final IMetafieldId PRIV_FEATURES = MetafieldIdFactory.valueOf("privFeatures");
    public static final IMetafieldId PRIV_DEBUG_HISTORY = MetafieldIdFactory.valueOf("privDebugHistory");
    public static final IMetafieldId PRIV_INITIAL_VERSION = MetafieldIdFactory.valueOf("privInitialVersion");
    public static final IMetafieldId IS_DELEGATED_ADMIN = MetafieldIdFactory.valueOf("isDelegatedAdmin");
    public static final IMetafieldId BUSER_CREATOR = MetafieldIdFactory.valueOf("buserCreator");
    public static final IMetafieldId ROLE_CREATOR = MetafieldIdFactory.valueOf("roleCreator");
    public static final IMetafieldId BUSER_ROLE_LIST = MetafieldIdFactory.valueOf("buserRoleList");
    public static final IMetafieldId MBUSER_UID = MetafieldIdFactory.valueOf("mbuserUid");
    public static final IMetafieldId MBUSER_PASSWORD = MetafieldIdFactory.valueOf("mbuserPassword");
    public static final IMetafieldId USER_PRIVILEGES_CHILD_TABLE = MetafieldIdFactory.valueOf("userPrivilegesChildTable");
    public static final IMetafieldId PRIV_ROLE_NAME = MetafieldIdFactory.valueOf("privRoleName");
    public static final IMetafieldId PRIV_LICENSE_DISABLE_FOR_CURRENT_LOGIN_SCOPE = MetafieldIdFactory.valueOf("privLicenseDisableForCurrentLoginScope");
    public static final IMetafieldId ROLE_PRIVILEGES_CHILD_TABLE = MetafieldIdFactory.valueOf("rolePrivilegesChildTable");
    public static final IMetafieldId USER_ROLE_NAMES = MetafieldIdFactory.valueOf("userRoleNames");
    public static final IMetafieldId ROLE_SEC_NAME = MetafieldIdFactory.valueOf("roleSecName");
    public static final IMetafieldId ROLE_DESCRIPTION = MetafieldIdFactory.valueOf("roleDescription");

}
