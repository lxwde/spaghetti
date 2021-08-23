package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoCalendarDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CalendarTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class ArgoCalendar extends ArgoCalendarDO {
    public static ArgoCalendar findOrCreateCalendar(CalendarTypeEnum inCalType, String inCalId, String inDescription, boolean inIsDefaultCalendar) {
        ArgoCalendar argoCal = ArgoCalendar.findCalendar(inCalId);
        if (argoCal == null) {
            argoCal = ArgoCalendar.createCalendar(inCalType, inIsDefaultCalendar, inCalId, inDescription);
        }
        return argoCal;
    }

    public static ArgoCalendar findOrCreateDefaultCalendar(CalendarTypeEnum inCalType) {
        ArgoCalendar argoCal = ArgoCalendar.findDefaultCalendar(inCalType);
        if (argoCal == null) {
            argoCal = ArgoCalendar.createCalendar(inCalType, true, "Default " + inCalType.getKey() + " Calendar", "Default " + inCalType.getName() + " Calendar");
        }
        return argoCal;
    }

    private static ArgoCalendar createCalendar(CalendarTypeEnum inCalType, boolean inIsDefaultCalendar, String inCalId, String inCalDescription) {
        ArgoCalendar argoCal = new ArgoCalendar();
        argoCal.setArgocalCalendarType(inCalType);
        argoCal.setArgocalIsDefaultCalendar(inIsDefaultCalendar);
        argoCal.setArgocalId(inCalId);
        argoCal.setArgocalDescription(inCalDescription);
        HibernateApi.getInstance().save((Object)argoCal);
        return argoCal;
    }

    @Nullable
    public static ArgoCalendar findCalendar(String inCalendarId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoCalendar").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCAL_ID, (Object)inCalendarId));
        return (ArgoCalendar)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static ArgoCalendar findDefaultCalendar(CalendarTypeEnum inCalType) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoCalendar").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCAL_CALENDAR_TYPE, (Object)((Object)inCalType))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCAL_IS_DEFAULT_CALENDAR, (Object)true));
        return (ArgoCalendar)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public Long getArgocalCalendarEventsTableKey() {
        return this.getArgocalGkey();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (this.getArgocalIsDefaultCalendar().booleanValue()) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoCalendar").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCAL_CALENDAR_TYPE, (Object)((Object)this.getArgocalCalendarType()))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoCalendarField.ARGOCAL_IS_DEFAULT_CALENDAR, (Object)true)).addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoCalendarField.ARGOCAL_GKEY, (Object)this.getArgocalGkey()));
            Serializable[] gkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
            if (gkeys != null && gkeys.length > 0) {
                ArgoCalendar argoCal = (ArgoCalendar)HibernateApi.getInstance().load(ArgoCalendar.class, gkeys[0]);
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.DEFAULT_CALENDAR_EXISTS, (BizViolation)bv, (Object)argoCal.getArgocalId(), (Object)argoCal.getArgocalCalendarType().getName());
            }
        }
        if (inChanges.hasFieldChange(IArgoCalendarField.ARGOCAL_ID) && !this.isUniqueInClass(IArgoCalendarField.ARGOCAL_ID)) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NON_UNIQUE_CALENDAR_ID, (BizViolation)bv, (Object)this.getArgocalId());
        }
        return bv;
    }

}
