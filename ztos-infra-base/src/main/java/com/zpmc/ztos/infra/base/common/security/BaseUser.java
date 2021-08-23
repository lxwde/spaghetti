package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.BaseUserDO;
import com.zpmc.ztos.infra.base.business.enums.core.UserAuthenticationMethodEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.SecurityStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.Organization;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.SecurityUtils;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;

public class BaseUser extends BaseUserDO {
    public BaseUser() {
        UserOption userOption = new UserOption();
        this.setBuserOption(userOption);
        this.setBuserAuthMethod(UserAuthenticationMethodEnum.INTERNAL);
    }

    public static BaseUser findOrCreateBaseUser(String inUserId, String inPassword, String inFirstName, String inLastName, String inEMail, String inTimeZone, String inTelephone, Organization inEmployerOrg, Organization inPrimaryOrg) {
        BaseUser baseUser = BaseUser.findBaseUser(inUserId);
        if (baseUser == null) {
            baseUser = BaseUser.createBaseUser(inUserId, inPassword, inFirstName, inLastName, inEMail, inTimeZone, inTelephone, inEmployerOrg, inPrimaryOrg);
        }
        return baseUser;
    }

    @Nullable
    public static BaseUser findBaseUser(String inUserId) {
        IDomainQuery dq = QueryUtils.createDomainQuery("BaseUser").addDqPredicate(PredicateFactory.eq(ISecurityField.BUSER_UID, inUserId));
        return (BaseUser) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static BaseUser createBaseUser(String inUserId, String inPassword, String inFirstName, String inLastName, String inEMail, String inTimeZone, String inTelephone, Organization inEmployerOrg, Organization inPrimaryOrg) {
        BaseUser baseUser = new BaseUser();
        baseUser.setBuserUid(inUserId);
        baseUser.setBuserPassword(inPassword);
        baseUser.setBuserFirstName(inFirstName);
        baseUser.setBuserLastName(inLastName);
        baseUser.setBuserEMail(inEMail);
        baseUser.setBuserEmployerOrg(inEmployerOrg);
        baseUser.setBuserPrimaryOrg(inPrimaryOrg);
        baseUser.setBuserTimeZone(inTimeZone);
        baseUser.setBuserTelephone(inTelephone);
        HibernateApi.getInstance().save(baseUser);
        return baseUser;
    }

    public boolean isUserActive() {
        return !StringUtils.equals((String)this.getBuserActive(), (String) SecurityStateEnum.INACTIVE.getKey());
    }

    @Override
    protected boolean shouldCheckDirty() {
        return false;
    }

 //   @Override
    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        if (inLifeCycleState == LifeCycleStateEnum.OBSOLETE) {
            this.setBuserActive(SecurityStateEnum.INACTIVE.getKey());
        } else if (inLifeCycleState == LifeCycleStateEnum.ACTIVE) {
            this.setBuserActive(SecurityStateEnum.ACTIVE.getKey());
        }
    }

//    @Override
    @Nullable
    public LifeCycleStateEnum getLifeCycleState() {
        if (this.getBuserActive().equals(SecurityStateEnum.ACTIVE.getKey())) {
            return LifeCycleStateEnum.ACTIVE;
        }
        if (this.getBuserActive().equals(SecurityStateEnum.INACTIVE.getKey())) {
            return LifeCycleStateEnum.OBSOLETE;
        }
        return null;
    }

    @Nullable
    public String getBuserPrimaryOrgName() {
        String orgname = null;
        Organization org = this.getBuserPrimaryOrg();
        if (org != null) {
            orgname = org.getOrgName();
        }
        return orgname;
    }

    public IValueHolder[] getUserRoleVaos() {
        Set roles = this.getBuserRoleList();
        if (roles == null) {
            return new IValueHolder[0];
        }
        IValueHolder[] vaos = new IValueHolder[roles.size()];
        int i = 0;
        for (Object role : roles) {
            vaos[i++] = ((SecRole)role).getValueObject();
        }
        return vaos;
    }

    public Collection getOrganizationsFromUserGroups() {
        ArrayList orgs = new ArrayList();
        Set groups = this.getBuserGroupList();
        if (groups != null) {
            for (Object group : groups) {
                orgs.addAll(((OrgGroup)group).getGrpOrgList());
            }
        }
        return orgs;
    }

    @Override
    public void applyFieldChanges(FieldChanges inFieldChanges) {
        boolean hasPasswordChange = false;
        String newPassword = null;
        IMessageCollector mc = TransactionParms.getBoundParms().getMessageCollector();
        if (inFieldChanges.hasFieldChange(ISecurityBizMetafield.BUSER_NEW_PASSWORD)) {
            if (!inFieldChanges.hasFieldChange(ISecurityBizMetafield.BUSER_CURRENT_PASSWORD)) {
                inFieldChanges.removeFieldChange(ISecurityBizMetafield.BUSER_NEW_PASSWORD);
                mc.appendMessage(BizViolation.create(ISecurityPropertyKeys.SECURITY__PASSWORD_MISSING_CURRENT_PASSWORD, null));
            } else {
                String currentPassword = (String)inFieldChanges.getFieldChange(ISecurityBizMetafield.BUSER_CURRENT_PASSWORD).getNewValue();
                if (!StringUtils.equals((String)this.getBuserPassword(), (String)currentPassword)) {
                    mc.appendMessage(BizViolation.create(ISecurityPropertyKeys.SECURITY__PASSWORD_DOES_NOT_MATCH_OLD_PASSWORD, null));
                } else {
                    hasPasswordChange = true;
                    newPassword = (String)inFieldChanges.getFieldChange(ISecurityBizMetafield.BUSER_NEW_PASSWORD).getNewValue();
                }
                inFieldChanges.removeFieldChange(ISecurityBizMetafield.BUSER_CURRENT_PASSWORD);
            }
        } else if (inFieldChanges.hasFieldChange(ISecurityBizMetafield.BUSER_CURRENT_PASSWORD)) {
            inFieldChanges.removeFieldChange(ISecurityBizMetafield.BUSER_CURRENT_PASSWORD);
            TransactionParms.getBoundParms().getMessageCollector().appendMessage(BizViolation.create(IFrameworkPropertyKeys.CRUD__FIELD_REQUIRED, null, ISecurityBizMetafield.BUSER_NEW_PASSWORD));
        } else if (inFieldChanges.hasFieldChange(ISecurityField.BUSER_PASSWORD)) {
            hasPasswordChange = true;
            newPassword = (String)inFieldChanges.getFieldChange(ISecurityField.BUSER_PASSWORD).getNewValue();
        }
        if (hasPasswordChange) {
            boolean passwordRecycled;
            UserAuthenticationProfile authProfile = this.getUserAuthenticationProfile();
            long recycleLimit = FrameworkConfig.PASSWORD_NO_RECYLCE_COUNT.getValue(authProfile.getScopedSecuritySystemContext());
            boolean bl = passwordRecycled = StringUtils.equals((String)newPassword, (String)this.getBuserPassword()) || authProfile.isPasswordRecycled(recycleLimit - 1L, newPassword);
            if (passwordRecycled) {
                mc.appendMessage(BizViolation.create(ISecurityPropertyKeys.SECURITY__PASSWORD_RECYCLE_NOT_ALLOWED, null, recycleLimit));
            } else {
                authProfile.recordLastPassword(this.getBuserPassword());
                authProfile.setUsrauthPasswordLastChanged(TimeUtils.getCurrentTime());
            }
        }
        super.applyFieldChanges(inFieldChanges);
    }

    private void setNewPassword(String inPassword) {
        this.setBuserPassword(inPassword);
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(ISecurityField.BUSER_UID)) {
            ISystemUserManager systemUserMgr = (ISystemUserManager) PortalApplicationContext.getBean("systemUserManager");
            if (systemUserMgr.isSystemUser(this.getBuserUid())) {
                bizViolation = BizViolation.create(ISecurityPropertyKeys.SECURITY__INVALID_UID_FORMAT, bizViolation, this.getBuserUid());
            }
            if (!this.isUniqueInClass(ISecurityField.BUSER_UID)) {
                bizViolation = BizViolation.create(ISecurityPropertyKeys.SECURITY__DUPLICATE_UID, bizViolation, ISecurityField.BUSER_UID, this.getBuserUid());
            }
        }
        if (!this.isPasswordToBeGenerated()) {
            bizViolation = this.checkRequiredField(bizViolation, ISecurityField.BUSER_PASSWORD);
        }
        bizViolation = this.checkRequiredField(bizViolation, ISecurityField.BUSER_LAST_NAME);
        bizViolation = this.checkRequiredField(bizViolation, ISecurityField.BUSER_FIRST_NAME);
        return bizViolation;
    }

    @Override
    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        if (this.isPasswordToBeGenerated() && StringUtils.isEmpty((String)this.getBuserPassword())) {
            String encryptedPass = SecurityUtils.encryptPassword(this.getBuserUid() + "P!ssw0rd");
            inOutMoreChanges.setFieldChange(ISecurityField.BUSER_PASSWORD, encryptedPass);
        }
    }

    public UserAuthenticationProfile getUserAuthenticationProfile() {
        UserAuthenticationProfile profile = this.getBuserAuthenticationProfile();
        if (profile == null) {
            profile = new UserAuthenticationProfile();
            profile.setUsrauthPasswordLastChanged(this.getBuserCreated());
            profile.setUsrauthUser(this);
            this.setBuserAuthenticationProfile(profile);
        }
        return profile;
    }

    public String[] getUserRoleNames() {
        Set roleList = this.getBuserRoleList();
        if (roleList == null || roleList.isEmpty()) {
            return new String[0];
        }
        String[] roleNames = new String[roleList.size()];
        int i = 0;
        for (Object secRole : roleList) {
            roleNames[i++] = ((SecRole)secRole).getRoleSecName();
        }
        return roleNames;
    }

    @Override
    public void setFieldValue(IMetafieldId inMetaFieldId, Object inFieldValue) {
        IMetafieldId qualifyingMetafieldId = inMetaFieldId.getQualifyingMetafieldId();
        if (ISecurityField.BUSER_OPTION.equals(qualifyingMetafieldId)) {
            UserOption userOption = this.getBuserOption();
            if (userOption == null) {
                userOption = new UserOption();
                this.setBuserOption(userOption);
            }
            userOption.setFieldValue(inMetaFieldId.getQualifiedMetafieldId(), inFieldValue);
        } else if (ISecurityBizMetafield.BUSER_NEW_PASSWORD.equals(inMetaFieldId)) {
            this.setNewPassword((String)inFieldValue);
        } else {
            super.setFieldValue(inMetaFieldId, inFieldValue);
        }
    }

    public String getBuserFullName() {
        return this.getBuserFirstName() + " " + this.getBuserLastName();
    }

    @Override
    public String getHumanReadableKey() {
        return this.getBuserUid();
    }

    @Override
    public Serializable getPrimaryKey() {
        return this.getBuserGkey();
    }

 //   @Override
    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public ScopeCoordinates getBroadestAllowedScope() {
        return ScopeCoordinates.GLOBAL_SCOPE;
    }

    public Boolean getIsDelegatedAdmin() {
        Boolean isDelegated = Boolean.FALSE;
        Set roleSet = this.getBuserRoleList();
        for (Object role : roleSet) {
            Boolean isDelegatedBool = ((SecRole)role).getRoleIsDelegated();
            if (isDelegatedBool == null || !isDelegatedBool.booleanValue()) continue;
            isDelegated = Boolean.TRUE;
            break;
        }
        return isDelegated;
    }

    public static IPredicate formBuserFullNamePredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IMetafieldId rightQualified = inMetafieldId.getMfidExcludeRightMostNode();
        IMetafieldId firstQualified = rightQualified == null ? ISecurityField.BUSER_FIRST_NAME : MetafieldIdFactory.getCompoundMetafieldId(rightQualified, ISecurityField.BUSER_FIRST_NAME);
        IMetafieldId lastQualified = rightQualified == null ? ISecurityField.BUSER_LAST_NAME : MetafieldIdFactory.getCompoundMetafieldId(rightQualified, ISecurityField.BUSER_LAST_NAME);
        String firstName = null;
        String lastName = null;
        if (inValue != null) {
            StringTokenizer st = new StringTokenizer((String)inValue, " ", false);
            if (st.hasMoreTokens()) {
                firstName = st.nextToken();
            }
            if (st.hasMoreTokens()) {
                lastName = st.nextToken();
            }
        }
        if (PredicateVerbEnum.NE.equals(inVerb)) {
            return PredicateFactory.not(PredicateFactory.conjunction().add(PredicateFactory.eq(firstQualified, firstName)).add(PredicateFactory.eq(lastQualified, lastName)));
        }
        if (PredicateVerbEnum.NOT_NULL.equals(inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.isNotNull(firstQualified)).add(PredicateFactory.isNotNull(lastQualified));
        }
        if (PredicateVerbEnum.NULL.equals(inVerb)) {
            return PredicateFactory.conjunction().add(PredicateFactory.isNull(firstQualified)).add(PredicateFactory.isNull(lastQualified));
        }
 //       Junction predicate = PredicateFactory.conjunction().add(PredicateFactory.eq(firstQualified, firstName)).add(PredicateFactory.eq(lastQualified, lastName));
//        return predicate;
        return null;
    }

    private boolean isPasswordToBeGenerated() {
        boolean tobeGenerated = false;
        if (this.getBuserGkey() == null) {
            TransactionParms parms = TransactionParms.getBoundParms();
            UserContext uc = parms == null ? UserContextUtils.getSystemUserContext() : parms.getUserContext();
            UserAuthenticationMethodEnum authMethod = this.getBuserAuthMethod();
            tobeGenerated = authMethod != UserAuthenticationMethodEnum.INTERNAL && (authMethod != UserAuthenticationMethodEnum.DEFAULT || !FrameworkConfig.USER_AUTHENTICATION_METHOD_DEFAULT.isSetTo("INTERNAL", uc));
        }
        return tobeGenerated;
    }

}
