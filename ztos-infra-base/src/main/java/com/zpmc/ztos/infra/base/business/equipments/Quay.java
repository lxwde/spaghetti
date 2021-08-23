package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.QuayDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class Quay extends QuayDO {

    public static Quay findOrCreateQuay(Facility inFacility, String inQuayId) {
        Quay quay = Quay.findQuay(inFacility, inQuayId);
        if (quay == null) {
            quay = Quay.createQuay(inFacility, inQuayId);
        }
        return quay;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        BizViolation bizViol = null;
        super.validateChanges(inFieldChanges);
        if (!DataSourceEnum.SNX.equals((Object) ContextHelper.getThreadDataSource())) {
            Facility fcy = ContextHelper.getThreadFacility();
            if (fcy == null) {
                FieldChange fcQuayFcy = inFieldChanges.getFieldChange(IArgoField.QUAY_FACILITY);
                fcy = fcQuayFcy != null ? (Facility)fcQuayFcy.getNewValue() : this.getQuayFacility();
            }
            FieldChange fc = inFieldChanges.getFieldChange(IArgoField.QUAY_ID);
            String quayId = null;
            if (fc != null) {
                quayId = (String)fc.getNewValue();
            }
            IDomainQuery dq = Quay.getDomainQuery(fcy, quayId);
            if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
                bizViol = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.DUPLICATE_QUAY_FOR_THIS_FACILITY, bizViol, (IMetafieldId)IArgoField.QUAY_ID, (Object)quayId);
            }
        }
        return bizViol;
    }

    private static Quay createQuay(Facility inFacility, String inQuayId) {
        Quay quay = new Quay();
        quay.setQuayFacility(inFacility);
        quay.setQuayId(inQuayId);
        quay.setQuayName(inQuayId);
        HibernateApi.getInstance().save((Object)quay);
        return quay;
    }

    public static Quay findQuay(Facility inFacility, String inQuayId) {
        IDomainQuery dq = Quay.getDomainQuery(inFacility, inQuayId);
        return (Quay) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    private static IDomainQuery getDomainQuery(Facility inFacility, String inQuayId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Quay").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.QUAY_FACILITY, (Object)inFacility.getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.QUAY_ID, (Object)inQuayId));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return dq;
    }

    @Override
    public Serializable getPrimaryKey() {
        return null;
    }
}
