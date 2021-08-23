package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoUserDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Organization;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.utils.SecurityUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.HashSet;

public class ArgoUser extends ArgoUserDO {

    private static final Logger LOGGER = Logger.getLogger(ArgoUser.class);

    @Nullable
    public static ArgoUser findArgoUser(String inUserId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoUser").addDqPredicate(PredicateFactory.eq((IMetafieldId) ISecurityField.BUSER_UID, (Object)inUserId));
        return (ArgoUser) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static ArgoUser createArgoUser(String inUserId, String inPassword, String inFirstName, String inLastName) {
        ArgoUser user = new ArgoUser();
        user.setBuserUid(inUserId);
        user.setBuserFirstName(inFirstName);
        user.setBuserLastName(inLastName);
        user.setBuserPassword(SecurityUtils.encryptPasswordMD5((String)inPassword));
        user.setBuserActive("Y");
        LOGGER.info((Object)("createArgoUser: Created ArgoUser <" + user.toString() + ">"));
        HibernateApi.getInstance().save((Object)user);
        return user;
    }

    public static ArgoUser findOrCreateArgoUser(String inUserId, String inPassword, String inFirstName, String inLastName) {
        ArgoUser user = ArgoUser.findArgoUser(inUserId);
        if (user == null) {
            user = ArgoUser.createArgoUser(inUserId, inPassword, inFirstName, inLastName);
        }
        return user;
    }

    public static ArgoUser findByPrimaryKey(Serializable inGkey) {
        return (ArgoUser) HibernateApi.getInstance().get(ArgoUser.class, inGkey);
    }

    public static ArgoUser hydrate(Serializable inPrimaryKey) {
        return (ArgoUser) HibernateApi.getInstance().load(ArgoUser.class, inPrimaryKey);
    }

    public void assignBizGroup(BizGroup inBizGroup) {
        this.setArgouserBizGroup(inBizGroup);
    }

    public HashSet getAffiliatedBizUnits() {
        HashSet list = new HashSet();
        if (this.getArgouserBizGroup() != null) {
 //           list = this.getArgouserBizGroup().getAllBizUnits();
        }
        if (this.getArgouserCompanyBizUnit() != null) {
            list.add(this.getArgouserCompanyBizUnit());
        }
        return list;
    }

    public BizGkeysByRole getAffiliatedBizUnitGkeysByRole() {
        BizGkeysByRole roleList = new BizGkeysByRole();
        this.populateAffiliatedBizUnitGkeysByRole(roleList);
        return roleList;
    }

    public void populateAffiliatedBizUnitGkeysByRole(BizGkeysByRole inOutRoleList) {
        HashSet bizuList = this.getAffiliatedBizUnits();
        if (bizuList != null) {
            for (Object bizu : bizuList) {
                inOutRoleList.addBizUnit((ScopedBizUnit)bizu);
                if (!BizRoleEnum.LINEOP.equals((Object)((ScopedBizUnit)bizu).getBzuRole())) continue;
                inOutRoleList.addBizRoleGkey(BizRoleEnum.VESSELOP, ((ScopedBizUnit)bizu).getBzuGkey());
            }
        }
    }

    public ScopeCoordinates getBroadestAllowedScope() {
        Yard yard = this.getArgouserYard();
        Facility facility = this.getArgouserFacility();
        Complex complex = this.getArgouserComplex();
        Operator operator = this.getArgouserOperator();
        if (yard != null) {
            return new ScopeCoordinates(new Serializable[]{operator.getPrimaryKey(), complex.getPrimaryKey(), facility.getPrimaryKey(), yard.getPrimaryKey()});
        }
        if (facility != null) {
            return new ScopeCoordinates(new Serializable[]{operator.getPrimaryKey(), complex.getPrimaryKey(), facility.getPrimaryKey()});
        }
        if (complex != null) {
            return new ScopeCoordinates(new Serializable[]{operator.getPrimaryKey(), complex.getPrimaryKey()});
        }
        if (operator != null) {
            return new ScopeCoordinates(new Serializable[]{operator.getPrimaryKey()});
        }
        return super.getBroadestAllowedScope();
    }

    public Object getArgouserScope() {
        Yard yard = this.getArgouserYard();
        if (yard != null) {
            return ScopeEnum.YARD;
        }
        Facility facility = this.getArgouserFacility();
        if (facility != null) {
            return ScopeEnum.FACILITY;
        }
        Complex complex = this.getArgouserComplex();
        if (complex != null) {
            return ScopeEnum.COMPLEX;
        }
        Operator operator = this.getArgouserOperator();
        if (operator != null) {
            return ScopeEnum.OPERATOR;
        }
        return "";
    }

    public Object getArgouserScopeGkey() {
        Yard yard = this.getArgouserYard();
        if (yard != null) {
            return yard.getYrdGkey();
        }
        Facility facility = this.getArgouserFacility();
        if (facility != null) {
            return facility.getFcyGkey();
        }
        Complex complex = this.getArgouserComplex();
        if (complex != null) {
            return complex.getCpxGkey();
        }
        Operator operator = this.getArgouserOperator();
        if (operator != null) {
            return operator.getOprGkey();
        }
        return null;
    }

    public void setScope(String inOprId, String inCpxId, String inFcyId, String inYrdId) {
        Operator operator = Operator.findOperator(inOprId);
        if (operator != null) {
            this.setArgouserOperator(operator);
            Complex complex = Complex.findComplex(inCpxId, operator);
            if (complex != null) {
                this.setArgouserComplex(complex);
                Facility facility = Facility.findFacility(inFcyId, complex);
                if (facility != null) {
                    this.setArgouserFacility(facility);
                    Yard yard = Yard.findYard(inYrdId, facility);
                    if (yard != null) {
                        this.setArgouserYard(yard);
                    } else if (inYrdId != null) {
                        throw BizFailure.create((String)("Invalid Yard Id: " + inYrdId));
                    }
                } else if (inFcyId != null) {
                    throw BizFailure.create((String)("Invalid Facility Id: " + inFcyId));
                }
            } else if (inCpxId != null) {
                throw BizFailure.create((String)("Invalid Complex Id: " + inCpxId));
            }
        } else if (inOprId != null) {
            throw BizFailure.create((String)("Invalid Operator Id: " + inOprId));
        }
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        FieldChange scope = inFieldChanges.getFieldChange(IArgoBizMetafield.ARGOUSER_SCOPE);
        FieldChange gkey = inFieldChanges.getFieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY);
        if (scope != null && gkey == null) {
            if (this.getArgouserYard() != null) {
                gkey = new FieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY, (Object)this.getArgouserYard().getYrdGkey());
            } else if (this.getArgouserFacility() != null) {
                gkey = new FieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY, (Object)this.getArgouserFacility().getFcyGkey());
            } else if (this.getArgouserComplex() != null) {
                gkey = new FieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY, (Object)this.getArgouserComplex().getCpxGkey());
            } else if (this.getArgouserOperator() != null) {
                gkey = new FieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY, (Object)this.getArgouserOperator().getOprGkey());
            }
        }
        if (scope != null && gkey != null) {
            Operator operator = null;
            String scopeStr = (String)scope.getNewValue();
            Long gkeyLong = (Long)gkey.getNewValue();
            if (scopeStr.compareTo(ScopeEnum.YARD.getName()) == 0) {
                Yard yard = Yard.loadByGkey(gkeyLong);
                this.setArgouserYard(yard);
                Facility facility = yard.getYrdFacility();
                this.setArgouserFacility(facility);
                this.setArgouserComplex(facility.getFcyComplex());
                operator = facility.getFcyComplex().getCpxOperator();
            } else if (scopeStr.compareTo(ScopeEnum.FACILITY.getName()) == 0) {
                Facility facility = Facility.loadByGkey(gkeyLong);
                this.setArgouserFacility(facility);
                this.setArgouserComplex(facility.getFcyComplex());
                this.setArgouserYard(null);
                operator = facility.getFcyComplex().getCpxOperator();
            } else if (scopeStr.compareTo(ScopeEnum.COMPLEX.getName()) == 0) {
                Complex complex = Complex.loadByGkey(gkeyLong);
                this.setArgouserComplex(complex);
                this.setArgouserFacility(null);
                this.setArgouserYard(null);
                operator = complex.getCpxOperator();
            } else {
                operator = Operator.loadByGkey(gkeyLong);
                this.setArgouserComplex(null);
                this.setArgouserFacility(null);
                this.setArgouserYard(null);
            }
            this.setArgouserOperator(operator);
            this.setDefaultOrganization(operator, inFieldChanges);
        }
        inFieldChanges.removeFieldChange(IArgoBizMetafield.ARGOUSER_SCOPE);
        inFieldChanges.removeFieldChange(IArgoBizMetafield.ARGOUSER_SCOPE_GKEY);
        super.applyFieldChanges(inFieldChanges);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation violations = super.validateChanges(inChanges);
        if (this.getBuserManagedByRole() != null) {
            violations = this.checkRequiredField(violations, IUserArgoField.ARGOUSER_BIZ_GROUP);
        }
        return violations;
    }

    private void setDefaultOrganization(Operator inOperator, FieldChanges inFieldChanges) {
        if (this.getBuserEmployerOrg() == null) {
            Organization employer = Organization.findOrCreateOrganization((String)inOperator.getOprId(), (String)inOperator.getOprId(), (String)"TERM");
            inFieldChanges.setFieldChange(ISecurityBizMetafield.BUSER_EMPLOYER_ORG, (Object)employer);
        }
        if (this.getBuserPrimaryOrg() == null) {
            Organization primOrg = Organization.findOrCreateOrganization((String)inOperator.getOprId(), (String)inOperator.getOprId(), (String)"TERM");
            inFieldChanges.setFieldChange(ISecurityBizMetafield.BUSER_PRIMARY_ORG, (Object)primOrg);
        }
    }
}
