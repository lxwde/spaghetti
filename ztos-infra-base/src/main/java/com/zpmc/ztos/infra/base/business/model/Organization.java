package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.OrganizationDO;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityField;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityPropertyKeys;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.OrgGroup;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Set;
import java.util.TimeZone;

public class Organization extends OrganizationDO {
    public static Organization findOrCreateOrganization(String inOrgCode, String inOrgName, String inOrgType) {
        Organization org = Organization.findOrganization(inOrgCode);
        if (org == null) {
            org = Organization.createOrganization(inOrgCode, inOrgName, inOrgType);
        }
        return org;
    }

    @Nullable
    public static Organization findOrganization(String inOrgCode) {
        IDomainQuery dq = QueryUtils.createDomainQuery("Organization").addDqPredicate(PredicateFactory.eq(ISecurityField.ORG_CODE, inOrgCode));
        return (Organization) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Organization createOrganization(String inOrgCode, String inOrgName, String inOrgType) {
        Organization org = new Organization();
        org.setOrgCode(inOrgCode);
        org.setOrgName(inOrgName);
        org.setOrgType(inOrgType);
        HibernateApi.getInstance().save(org);
        return org;
    }

    public boolean isGroupInOrganization(OrgGroup inGroup) {
        Set groups = this.getOrgGrpList();
        if (groups != null) {
            return groups.contains(inGroup);
        }
        return false;
    }

    public TimeZone getTimeZone() {
//        return InternationalizationUtils.getTimeZone(this.getOrgTimeZone());
        return null;
    }

    @Override
    public boolean isUniqueInClass(String inUniqueField) {
//        SecurityDAO securityDao = (SecurityDAO)Roastery.getBean("securityDAO");
//        Organization duplicate = securityDao.findOrgByUniqueIdByType(this.getOrgType(), ISecurityField.ORG_CODE.getFieldId(), this.getOrgCode());
//        return duplicate == null || duplicate == this;
        return false;
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        if ((inChanges.hasFieldChange(ISecurityField.ORG_CODE) || inChanges.hasFieldChange(ISecurityField.ORG_TYPE)) && !this.isCodeUniqueInClassForCountry()) {
            bizViolation = BizViolation.create(ISecurityPropertyKeys.SECURITY__DUPLICATE_ORGANIZATION_FOR_COUNTRY, (Throwable)null, bizViolation, ISecurityField.ORG_CODE, new Object[]{this.getOrgCode(), this.getOrgCountryId()});
        }
        return bizViolation;
    }

    @Override
    public String getHumanReadableKey() {
        return this.getOrgName() + "(" + this.getOrgCode() + ")";
    }

    private boolean isCodeUniqueInClassForCountry() {
//        SecurityDAO securityDao = (SecurityDAO) Roastery.getBean("securityDAO");
//        Organization duplicate = securityDao.findOrgByUniqueIdByTypeWithinCountry(this.getOrgType(), ISecurityField.ORG_CODE.getFieldId(), this.getOrgCode(), this.getOrgCountryId());
//        return duplicate == null || duplicate == this;
        return false;
    }
}
