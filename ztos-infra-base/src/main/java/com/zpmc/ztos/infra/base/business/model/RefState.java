package com.zpmc.ztos.infra.base.business.model;


import com.zpmc.ztos.infra.base.business.dataobject.RefStateDO;
import com.zpmc.ztos.infra.base.business.enums.framework.GeoScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collection;
import java.util.Collections;

public class RefState extends RefStateDO implements IScopeNodeEntity {
    @Nullable
    public static RefState findState(String inCountryCode, String inStateCode) {
        IMetafieldId stateCountry = MetafieldIdFactory.getCompoundMetafieldId(IReferenceField.STATE_CNTRY, IReferenceField.CNTRY_CODE);
        IDomainQuery dq = QueryUtils.createDomainQuery("RefState").addDqPredicate(PredicateFactory.eq(stateCountry, inCountryCode)).addDqPredicate(PredicateFactory.eq(IReferenceField.STATE_CODE, inStateCode));
        return (RefState) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Override
    public IScopeEnum getScopeEnum() {
        return GeoScopeEnum.STATE;
    }

    @Override
    public IScopeNodeEntity getParent() {
        return this.getStateCntry();
    }

    @Override
    public Collection getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String getId() {
        return this.getStateCode();
    }

    @Override
    public String getPathName() {
        RefCountry ctry = this.getStateCntry();
        return ctry.getId() + "/" + this.getId();
    }

    public String getStatePathName() {
        return this.getPathName();
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        String stateCode;
        BizViolation bv = super.validateChanges(inChanges);
        RefCountry cntry = this.getStateCntry();
        String cntrycode = cntry.getCntryCode();
        RefState state = RefState.findState(cntrycode, stateCode = this.getStateCode());
        if (state != null && !state.getStateGkey().equals(this.getStateGkey())) {
            bv = BizViolation.create(IFrameworkPropertyKeys.FRAMEWORK__STATE_ALREADY_EXISTS_FOR_COUNTRY, bv, stateCode, cntry.getCntryName());
        }
        return bv;
    }
}
