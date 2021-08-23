package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 组织
 *
 * @author yejun
 */
@Data
public abstract class OrganizationDO extends DatabaseEntity
        implements Serializable {

    private Long orgGkey;
    private String orgName;
    private String orgCode;
    private String orgDescription;
    private String orgType;
    private String orgActive;
    private String orgAddressLine1;
    private String orgAddressLine2;
    private String orgAddressLine3;
    private String orgCity;
    private String orgState;
    private String orgCountryId;
    private String orgTimeZone;
    private String orgMailCode;
    private String orgTel;
    private String orgFax;
    private String orgContact;
    private String orgTitle;
    private Date orgCreated;
    private String orgCreator;
    private Date orgChanged;
    private String orgChanger;
    private String orgFtpHostname;
    private String orgFtpUser;
    private String orgFtpPassword;
    private String orgFtpDirectory;
    private String orgIpAddress;
    private String orgPortAddress;
    private Set orgChildList;
    private Set orgPrimaryBuserList;
    private Set orgEmployeeBuserList;
    private Set orgGrpList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getOrgGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getOrgGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof OrganizationDO)) {
            return false;
        }
        OrganizationDO that = (OrganizationDO)other;
        return ((Object)id).equals(that.getOrgGkey());
    }

    public int hashCode() {
        Long id = this.getOrgGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getOrgGkey() {
        return this.orgGkey;
    }

    public void setOrgGkey(Long orgGkey) {
        this.orgGkey = orgGkey;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgDescription() {
        return this.orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgActive() {
        return this.orgActive;
    }

    public void setOrgActive(String orgActive) {
        this.orgActive = orgActive;
    }

    public String getOrgAddressLine1() {
        return this.orgAddressLine1;
    }

    public void setOrgAddressLine1(String orgAddressLine1) {
        this.orgAddressLine1 = orgAddressLine1;
    }

    public String getOrgAddressLine2() {
        return this.orgAddressLine2;
    }

    public void setOrgAddressLine2(String orgAddressLine2) {
        this.orgAddressLine2 = orgAddressLine2;
    }

    public String getOrgAddressLine3() {
        return this.orgAddressLine3;
    }

    public void setOrgAddressLine3(String orgAddressLine3) {
        this.orgAddressLine3 = orgAddressLine3;
    }

    public String getOrgCity() {
        return this.orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public String getOrgState() {
        return this.orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

    public String getOrgCountryId() {
        return this.orgCountryId;
    }

    public void setOrgCountryId(String orgCountryId) {
        this.orgCountryId = orgCountryId;
    }

    public String getOrgTimeZone() {
        return this.orgTimeZone;
    }

    public void setOrgTimeZone(String orgTimeZone) {
        this.orgTimeZone = orgTimeZone;
    }

    public String getOrgMailCode() {
        return this.orgMailCode;
    }

    public void setOrgMailCode(String orgMailCode) {
        this.orgMailCode = orgMailCode;
    }

    public String getOrgTel() {
        return this.orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return this.orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgContact() {
        return this.orgContact;
    }

    public void setOrgContact(String orgContact) {
        this.orgContact = orgContact;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Date getOrgCreated() {
        return this.orgCreated;
    }

    public void setOrgCreated(Date orgCreated) {
        this.orgCreated = orgCreated;
    }

    public String getOrgCreator() {
        return this.orgCreator;
    }

    public void setOrgCreator(String orgCreator) {
        this.orgCreator = orgCreator;
    }

    public Date getOrgChanged() {
        return this.orgChanged;
    }

    public void setOrgChanged(Date orgChanged) {
        this.orgChanged = orgChanged;
    }

    public String getOrgChanger() {
        return this.orgChanger;
    }

    public void setOrgChanger(String orgChanger) {
        this.orgChanger = orgChanger;
    }

    public String getOrgFtpHostname() {
        return this.orgFtpHostname;
    }

    public void setOrgFtpHostname(String orgFtpHostname) {
        this.orgFtpHostname = orgFtpHostname;
    }

    public String getOrgFtpUser() {
        return this.orgFtpUser;
    }

    public void setOrgFtpUser(String orgFtpUser) {
        this.orgFtpUser = orgFtpUser;
    }

    public String getOrgFtpPassword() {
        return this.orgFtpPassword;
    }

    public void setOrgFtpPassword(String orgFtpPassword) {
        this.orgFtpPassword = orgFtpPassword;
    }

    public String getOrgFtpDirectory() {
        return this.orgFtpDirectory;
    }

    public void setOrgFtpDirectory(String orgFtpDirectory) {
        this.orgFtpDirectory = orgFtpDirectory;
    }

    public String getOrgIpAddress() {
        return this.orgIpAddress;
    }

    public void setOrgIpAddress(String orgIpAddress) {
        this.orgIpAddress = orgIpAddress;
    }

    public String getOrgPortAddress() {
        return this.orgPortAddress;
    }

    public void setOrgPortAddress(String orgPortAddress) {
        this.orgPortAddress = orgPortAddress;
    }

    public Set getOrgChildList() {
        return this.orgChildList;
    }

    public void setOrgChildList(Set orgChildList) {
        this.orgChildList = orgChildList;
    }

    public Set getOrgPrimaryBuserList() {
        return this.orgPrimaryBuserList;
    }

    public void setOrgPrimaryBuserList(Set orgPrimaryBuserList) {
        this.orgPrimaryBuserList = orgPrimaryBuserList;
    }

    public Set getOrgEmployeeBuserList() {
        return this.orgEmployeeBuserList;
    }

    public void setOrgEmployeeBuserList(Set orgEmployeeBuserList) {
        this.orgEmployeeBuserList = orgEmployeeBuserList;
    }

    public Set getOrgGrpList() {
        return this.orgGrpList;
    }

    public void setOrgGrpList(Set orgGrpList) {
        this.orgGrpList = orgGrpList;
    }


}
