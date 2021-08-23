package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.ProblemTypeDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.List;

public class ProblemType extends ProblemTypeDO {
    public ProblemType() {
        this.setProbtypeSystemProvided(Boolean.FALSE);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public Long getGkey() {
        return this.getProbtypeGkey();
    }

    public String getHumanReadableKey() {
        return this.getProbtypeId();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (!this.isUniqueInClass(IOptimizationField.PROBTYPE_ID)) {
            return BizViolation.createFieldViolation((IPropertyKey) IOptimizationPropertyKeys.NON_UNIQUE_PROBLEM_ID, null, null, (Object)("[" + this.getProbtypeId() + "]"));
        }
        return bv;
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        super.preProcessDelete(inOutMoreChanges);
        if (this.getProbtypeSystemProvided().booleanValue()) {
            throw BizFailure.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_DELETE_SYSTEM_CREATED_PT, null, (Object)("[" + this.getProbtypeId() + "]"), null);
        }
        IDomainQuery domainQuery = QueryUtils.createDomainQuery((String)"ProblemSolution").addDqPredicate(PredicateFactory.eq((IMetafieldId) IOptimizationField.SOLUTION_PROBLEM_TYPE, (Object)this.getGkey()));
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(domainQuery);
        if (entities != null && !entities.isEmpty()) {
            StringBuilder strBuilder = new StringBuilder();
            for (Object prObj : entities) {
                ProblemSolution problemSolution = (ProblemSolution)prObj;
                if (strBuilder.length() > 0) {
                    strBuilder.append(",");
                }
                strBuilder.append("\n").append(IOptimizationPropertyKeys.SCOPE_LEVEL.toString()).append(problemSolution.getSolutionScopeLevel().toString());
            }
            throw BizFailure.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_DELETE_PT_AS_IT_HAS_PS, null, (Object)("[" + this.getProbtypeId() + "]"), (Object)strBuilder.toString());
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (this.getGkey() != null && this.getProbtypeSystemProvided().booleanValue() && this.verifyRestrictedFieldChanges(inChanges)) {
            throw BizFailure.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_UPDATE_PT_AS_IT_SYSTEM_CREATED, null, (Object)("[" + this.getProbtypeId() + "]"), null);
        }
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        if (this.getGkey() != null && this.getProbtypeSystemProvided().booleanValue() && this.verifyRestrictedFieldChanges(inOutMoreChanges)) {
            throw BizFailure.create((IPropertyKey) IOptimizationPropertyKeys.CANNOT_CREATE_UPDATE_PS_AS_IT_EXIST, null, (Object)("[" + this.getProbtypeId() + "]"), null);
        }
    }

    private boolean verifyRestrictedFieldChanges(FieldChanges inChanges) {
        return inChanges.hasFieldChange(IOptimizationField.PROBTYPE_ID) || inChanges.hasFieldChange(IOptimizationField.PROBTYPE_GKEY) || inChanges.hasFieldChange(IOptimizationField.PROBTYPE_SYSTEM_PROVIDED);
    }

    public static ProblemType hydrate(Serializable inPrimaryKey) {
        return (ProblemType)HibernateApi.getInstance().get(ProblemType.class, inPrimaryKey);
    }

}
