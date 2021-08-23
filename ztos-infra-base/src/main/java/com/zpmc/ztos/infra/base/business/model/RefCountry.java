package com.zpmc.ztos.infra.base.business.model;


import com.zpmc.ztos.infra.base.business.dataobject.RefCountryDO;
import com.zpmc.ztos.infra.base.business.enums.framework.GeoScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.RefWorld;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;

public class RefCountry extends RefCountryDO implements IObsoleteable, IScopeNodeEntity {
    public RefCountry() {
        this.setLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    @Override
    protected boolean shouldCheckDirty() {
        return false;
    }

    @Override
    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setCntryLifeCycleState(inLifeCycleState);
    }

    @Override
    public LifeCycleStateEnum getLifeCycleState() {
        return this.getCntryLifeCycleState();
    }

    @Nullable
    public static RefCountry findCountry(String inCountryCode) {
        IDomainQuery dq = QueryUtils.createDomainQuery("RefCountry").addDqPredicate(PredicateFactory.eq(IReferenceField.CNTRY_CODE, inCountryCode));
        return (RefCountry) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        bizViolation = this.checkUniqueFieldViolation(bizViolation, inChanges, IReferenceField.CNTRY_NAME);
        bizViolation = this.checkUniqueFieldViolation(bizViolation, inChanges, IReferenceField.CNTRY_ALPHA3_CODE);
        bizViolation = this.checkUniqueFieldViolation(bizViolation, inChanges, IReferenceField.CNTRY_NUM3_CODE);
        bizViolation = this.checkUniqueFieldViolation(bizViolation, inChanges, IReferenceField.CNTRY_OFFICIAL_NAME);
        return bizViolation;
    }

    @Override
    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        RefCountry country = RefCountry.findCountry(this.getCntryCode());
        TransactionParms boundParms = TransactionParms.getBoundParms();
        IMessageCollector messageCollector = boundParms.getMessageCollector();
        if (country != null && messageCollector != null) {
            messageCollector.appendMessage(BizViolation.createFieldViolation(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, null, IReferenceField.CNTRY_CODE, this.getCntryCode()));
        }
    }

    @Override
    public IScopeEnum getScopeEnum() {
        return GeoScopeEnum.COUNTRY;
    }

    @Override
    public IScopeNodeEntity getParent() {
        return RefWorld.getInstance();
    }

    @Override
    public Collection getChildren() {
        return this.getStateList();
    }

    @Override
    public String getId() {
        return this.getCntryCode();
    }

    @Override
    public String getPathName() {
        return this.getId();
    }

    @Override
    public String getHumanReadableKey() {
        return this.getCntryName();
    }

    public static RefCountry loadByGkey(Serializable inGkey) {
        return (RefCountry) Roastery.getHibernateApi().load(RefCountry.class, inGkey);
    }
}
