package com.zpmc.ztos.infra.base.common.type;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoCalendarEventTypeDO;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoCalendarField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class ArgoCalendarEventType extends ArgoCalendarEventTypeDO {
    public ArgoCalendarEventType() {
        this.setArgocalevttypeIsPartialDayEventType(Boolean.FALSE);
    }

    public ArgoCalendarEventType(String inName) {
        this.setArgocalevttypeName(inName);
        this.setArgocalevttypeIsPartialDayEventType(Boolean.FALSE);
    }

    @Nullable
    public static ArgoCalendarEventType findOrCreateArgoCalendarEventType(String inEventTypeName) {
        ArgoCalendarEventType eventType = ArgoCalendarEventType.findByName(inEventTypeName);
        if (eventType == null) {
            eventType = new ArgoCalendarEventType(inEventTypeName);
            HibernateApi.getInstance().saveOrUpdate((Object)eventType);
        }
        return eventType;
    }

    @Nullable
    public static ArgoCalendarEventType findByName(String inEventTypeName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoCalendarEventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCALEVTTYPE_NAME, (Object)inEventTypeName));
        return (ArgoCalendarEventType)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

}
