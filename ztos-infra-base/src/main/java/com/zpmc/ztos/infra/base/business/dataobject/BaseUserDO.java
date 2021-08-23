package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.core.UserAuthenticationMethodEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.SalutationEnum;
import com.zpmc.ztos.infra.base.business.model.Organization;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.security.SecRole;
import com.zpmc.ztos.infra.base.common.security.UserAuthenticationProfile;
import com.zpmc.ztos.infra.base.common.security.UserOption;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class BaseUserDO extends DatabaseEntity implements Serializable {
    private Long buserGkey;
    private String buserUid;
    private String buserPassword;
    private String buserFirstName;
    private String buserLastName;
    private String buserMiddleName;
    private SalutationEnum buserSalutation;
    private String buserEMail;
    private String buserTelephone;
    private String buserPager;
    private String buserSms;
    private String buserFax;
    private String buserLocaleLanguage;
    private String buserLocaleCountry;
    private String buserTimeZone;
    private String buserActive;
    private UserAuthenticationMethodEnum buserAuthMethod;
    private String buserComments;
    private Date buserCreated;
    private String buserCreator;
    private Date buserChanged;
    private String buserChanger;
    private Long buserPasswordExpiryOverrideDays;
    private Long buserSleepDelaySecs;
    private Long buserMaxTableRows;
    private Date buserExpiryDateTime;
    private UserAuthenticationProfile buserAuthenticationProfile;
    private Organization buserEmployerOrg;
    private Organization buserPrimaryOrg;
    private UserOption buserOption;
    private SecRole buserManagedByRole;
    private List buserMessageBundleList;
    private Set buserRoleList;
    private Set buserGroupList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getBuserGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBuserGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BaseUserDO)) {
            return false;
        }
        BaseUserDO that = (BaseUserDO)other;
        return ((Object)id).equals(that.getBuserGkey());
    }

    public int hashCode() {
        Long id = this.getBuserGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBuserGkey() {
        return this.buserGkey;
    }

    public void setBuserGkey(Long buserGkey) {
        this.buserGkey = buserGkey;
    }

    public String getBuserUid() {
        return this.buserUid;
    }

    public void setBuserUid(String buserUid) {
        this.buserUid = buserUid;
    }

    public String getBuserPassword() {
        return this.buserPassword;
    }

    public void setBuserPassword(String buserPassword) {
        this.buserPassword = buserPassword;
    }

    public String getBuserFirstName() {
        return this.buserFirstName;
    }

    public void setBuserFirstName(String buserFirstName) {
        this.buserFirstName = buserFirstName;
    }

    public String getBuserLastName() {
        return this.buserLastName;
    }

    public void setBuserLastName(String buserLastName) {
        this.buserLastName = buserLastName;
    }

    public String getBuserMiddleName() {
        return this.buserMiddleName;
    }

    public void setBuserMiddleName(String buserMiddleName) {
        this.buserMiddleName = buserMiddleName;
    }

    public SalutationEnum getBuserSalutation() {
        return this.buserSalutation;
    }

    public void setBuserSalutation(SalutationEnum buserSalutation) {
        this.buserSalutation = buserSalutation;
    }

    public String getBuserEMail() {
        return this.buserEMail;
    }

    public void setBuserEMail(String buserEMail) {
        this.buserEMail = buserEMail;
    }

    public String getBuserTelephone() {
        return this.buserTelephone;
    }

    public void setBuserTelephone(String buserTelephone) {
        this.buserTelephone = buserTelephone;
    }

    public String getBuserPager() {
        return this.buserPager;
    }

    public void setBuserPager(String buserPager) {
        this.buserPager = buserPager;
    }

    public String getBuserSms() {
        return this.buserSms;
    }

    public void setBuserSms(String buserSms) {
        this.buserSms = buserSms;
    }

    public String getBuserFax() {
        return this.buserFax;
    }

    public void setBuserFax(String buserFax) {
        this.buserFax = buserFax;
    }

    public String getBuserLocaleLanguage() {
        return this.buserLocaleLanguage;
    }

    public void setBuserLocaleLanguage(String buserLocaleLanguage) {
        this.buserLocaleLanguage = buserLocaleLanguage;
    }

    public String getBuserLocaleCountry() {
        return this.buserLocaleCountry;
    }

    public void setBuserLocaleCountry(String buserLocaleCountry) {
        this.buserLocaleCountry = buserLocaleCountry;
    }

    public String getBuserTimeZone() {
        return this.buserTimeZone;
    }

    public void setBuserTimeZone(String buserTimeZone) {
        this.buserTimeZone = buserTimeZone;
    }

    public String getBuserActive() {
        return this.buserActive;
    }

    public void setBuserActive(String buserActive) {
        this.buserActive = buserActive;
    }

    public UserAuthenticationMethodEnum getBuserAuthMethod() {
        return this.buserAuthMethod;
    }

    public void setBuserAuthMethod(UserAuthenticationMethodEnum buserAuthMethod) {
        this.buserAuthMethod = buserAuthMethod;
    }

    public String getBuserComments() {
        return this.buserComments;
    }

    public void setBuserComments(String buserComments) {
        this.buserComments = buserComments;
    }

    public Date getBuserCreated() {
        return this.buserCreated;
    }

    public void setBuserCreated(Date buserCreated) {
        this.buserCreated = buserCreated;
    }

    public String getBuserCreator() {
        return this.buserCreator;
    }

    public void setBuserCreator(String buserCreator) {
        this.buserCreator = buserCreator;
    }

    public Date getBuserChanged() {
        return this.buserChanged;
    }

    public void setBuserChanged(Date buserChanged) {
        this.buserChanged = buserChanged;
    }

    public String getBuserChanger() {
        return this.buserChanger;
    }

    public void setBuserChanger(String buserChanger) {
        this.buserChanger = buserChanger;
    }

    public Long getBuserPasswordExpiryOverrideDays() {
        return this.buserPasswordExpiryOverrideDays;
    }

    public void setBuserPasswordExpiryOverrideDays(Long buserPasswordExpiryOverrideDays) {
        this.buserPasswordExpiryOverrideDays = buserPasswordExpiryOverrideDays;
    }

    public Long getBuserSleepDelaySecs() {
        return this.buserSleepDelaySecs;
    }

    public void setBuserSleepDelaySecs(Long buserSleepDelaySecs) {
        this.buserSleepDelaySecs = buserSleepDelaySecs;
    }

    public Long getBuserMaxTableRows() {
        return this.buserMaxTableRows;
    }

    public void setBuserMaxTableRows(Long buserMaxTableRows) {
        this.buserMaxTableRows = buserMaxTableRows;
    }

    public Date getBuserExpiryDateTime() {
        return this.buserExpiryDateTime;
    }

    public void setBuserExpiryDateTime(Date buserExpiryDateTime) {
        this.buserExpiryDateTime = buserExpiryDateTime;
    }

    public UserAuthenticationProfile getBuserAuthenticationProfile() {
        return this.buserAuthenticationProfile;
    }

    public void setBuserAuthenticationProfile(UserAuthenticationProfile buserAuthenticationProfile) {
        this.buserAuthenticationProfile = buserAuthenticationProfile;
    }

    public Organization getBuserEmployerOrg() {
        return this.buserEmployerOrg;
    }

    public void setBuserEmployerOrg(Organization buserEmployerOrg) {
        this.buserEmployerOrg = buserEmployerOrg;
    }

    public Organization getBuserPrimaryOrg() {
        return this.buserPrimaryOrg;
    }

    public void setBuserPrimaryOrg(Organization buserPrimaryOrg) {
        this.buserPrimaryOrg = buserPrimaryOrg;
    }

    public UserOption getBuserOption() {
        return this.buserOption;
    }

    public void setBuserOption(UserOption buserOption) {
        this.buserOption = buserOption;
    }

    public SecRole getBuserManagedByRole() {
        return this.buserManagedByRole;
    }

    public void setBuserManagedByRole(SecRole buserManagedByRole) {
        this.buserManagedByRole = buserManagedByRole;
    }

    public List getBuserMessageBundleList() {
        return this.buserMessageBundleList;
    }

    public void setBuserMessageBundleList(List buserMessageBundleList) {
        this.buserMessageBundleList = buserMessageBundleList;
    }

    public Set getBuserRoleList() {
        return this.buserRoleList;
    }

    public void setBuserRoleList(Set buserRoleList) {
        this.buserRoleList = buserRoleList;
    }

    public Set getBuserGroupList() {
        return this.buserGroupList;
    }

    public void setBuserGroupList(Set buserGroupList) {
        this.buserGroupList = buserGroupList;
    }
}
