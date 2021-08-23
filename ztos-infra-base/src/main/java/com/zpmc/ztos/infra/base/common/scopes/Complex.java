package com.zpmc.ztos.infra.base.common.scopes;

import com.zpmc.ztos.infra.base.business.dataobject.ComplexDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.TimeZone;

public class Complex extends ComplexDO {

    private static final Logger LOGGER = Logger.getLogger((String) Complex.class.getName());

    public Complex() {
        this.setCpxLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setCpxLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getCpxLifeCycleState();
    }

    public String toString() {
        return "cpx-" + this.getCpxId();
    }

    public static Complex loadByGkey(Long inGkey) {
        return (Complex) Roastery.getHibernateApi().load(Complex.class, (Serializable)inGkey);
    }

    public static Complex findComplex(String inCpxId, Operator inOperator) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Complex").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_ID, (Object)inCpxId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_OPERATOR, (Object)inOperator.getPrimaryKey()));
        return (Complex) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);

    }

    public static Complex findOrCreateComplex(String inComplexId, String inCpxName, Operator inOperator, String inTimeZoneId) {
        Complex complex = Complex.findComplex(inComplexId, inOperator);
        boolean isNew = false;
        if (complex == null) {
            complex = new Complex();
            complex.setCpxId(inComplexId);
            isNew = true;
        }
        complex.setCpxOperator(inOperator);
        complex.setCpxName(inCpxName);
        TimeZone tz = TimeZone.getTimeZone(inTimeZoneId);
        if (tz == null) {
            LOGGER.error((Object)("findOrCreateComplex: could not resolve timezone <" + inTimeZoneId + "> for Complex <" + inComplexId + ">"));
        } else if (LOGGER.isInfoEnabled()) {
            String s = isNew ? "created" : "updated";
            LOGGER.info((Object)("findOrCreateComplex: " + s + " Complex <" + inComplexId + "> with timezone <" + tz.getDisplayName() + ">, useDaylightTime = <" + tz.useDaylightTime() + ">"));
        }
        complex.setCpxTimeZoneId(inTimeZoneId);
 //       HibernateApi.getInstance().saveOrUpdate((Object)complex);
        return complex;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        int entityLevel;
        ScopeCoordinates scope;
        int maxLevel;
        BizViolation bizViol = super.validateChanges(inFieldChanges);
        if (this.getPrimaryKey() == null && (maxLevel = (scope = ContextHelper.getThreadUserContext().getScopeCoordinate()).getMaxScopeLevel()) >= (entityLevel = this.getScopeEnum().getScopeLevel().intValue())) {
            IPropertyKey currentScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(maxLevel - 1))).getDescriptionPropertyKey();
            IPropertyKey requiredScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(entityLevel - 2))).getDescriptionPropertyKey();
            bizViol = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_PRIVILEGE_TO_CHANGE_TOPOLOGY, null, (Object)currentScope, (Object)requiredScope);
        }
        if (inFieldChanges.hasFieldChange(IArgoField.CPX_OPERATOR) || inFieldChanges.hasFieldChange(IArgoField.CPX_ID)) {
            bizViol = this.checkIfCpxAlreadyExistsForAnOpr(bizViol);
        }
        return bizViol;
    }

    private BizViolation checkIfCpxAlreadyExistsForAnOpr(BizViolation inBv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Complex").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_OPERATOR, (Object)this.getCpxOperator().getOprGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_ID, (Object)this.getCpxId()));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.DUPLICATE_COMPLEX_FOR_THIS_OPERATOR, (BizViolation)inBv, (IMetafieldId) IArgoField.CPX_ID, (Object)this.getCpxId(), (Object)this.getCpxOperator().getOprId());
        }
        return inBv;
    }

    public IScopeEnum getScopeEnum() {
        return ScopeEnum.COMPLEX;
    }

//    public IScopeNodeEntity getParent() {
//        return this.getCpxOperator();
//    }

    public Collection getChildren() {
        return this.getCpxFcySet();
    }

    public String getId() {
        return this.getCpxId();
    }

    public String getPathName() {
        Operator operator = this.getCpxOperator();
        return operator.getOprId() + "/" + this.getCpxId();
    }

    public String getCpxPathName() {
        return this.getPathName();
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(this.getCpxTimeZoneId());
    }

    public boolean updateBerthModelGkey(Long inBrmGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Complex").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CPX_BERTH_MODEL_GKEY, (Object)inBrmGkey));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        boolean complexExists = Roastery.getHibernateApi().existsByDomainQuery(dq);
        if (complexExists) {
            LOGGER.error((Object)("updateBerthModelGkey: Tried to assign BerthModel with gkey = '" + inBrmGkey + "' to Complex = '" + this.getCpxName() + "' but this BerthModel is already owned by another Complex!"));
            return false;
        }
        this.setCpxBerthModelGkey(inBrmGkey);
        return true;
    }

    public static Complex hydrate(Serializable inPrimaryKey) {
        return (Complex)HibernateApi.getInstance().load(Complex.class, inPrimaryKey);
    }


    @Override
    public void preDelete() {

    }
}
