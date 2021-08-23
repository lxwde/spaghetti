package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.StorageRuleDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CalendarTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.Disjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.ArgoCalendar;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class StorageRule extends StorageRuleDO {
    public StorageRule() {
        this.setSruleIsStartDayIncluded(Boolean.TRUE);
        this.setSruleIsEndDayIncluded(Boolean.TRUE);
        this.setSruleIsRuleForPower(Boolean.FALSE);
        this.setSruleIsFreeTimeChgedIfExceeded(Boolean.FALSE);
    }

    public static StorageRule findOrCreateStorageRule(String inRuleId, Boolean inIsStartIncluded, Boolean inIsEndIncluded, Boolean inIsFreedaysIncluded, Boolean inIsGratisIncluded, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule rule = StorageRule.findStorageRule(inRuleId);
        if (rule == null) {
            rule = StorageRule.createStorageRule(inRuleId, Boolean.FALSE, inIsStartIncluded, inIsEndIncluded, inIsFreedaysIncluded, inIsGratisIncluded, inStartDay, inEndDay, inCalendar);
        }
        return rule;
    }

    public static StorageRule findOrCreatePowerRule(String inRuleId, Boolean inIsStartIncluded, Boolean inIsEndIncluded, Boolean inIsFreedaysIncluded, Boolean inIsGratisIncluded, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule rule = StorageRule.findStorageRule(inRuleId);
        if (rule == null) {
            rule = StorageRule.createStorageRule(inRuleId, Boolean.TRUE, inIsStartIncluded, inIsEndIncluded, inIsFreedaysIncluded, inIsGratisIncluded, inStartDay, inEndDay, inCalendar);
        }
        return rule;
    }

    public static StorageRule createStorageRule(String inRuleId, Boolean inIsRuleForPower, Boolean inIsStartIncluded, Boolean inIsEndIncluded, Boolean inIsFreedaysIncluded, Boolean inIsGratisIncluded, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule rule = new StorageRule();
        rule.setSruleId(inRuleId);
        rule.setSruleIsRuleForPower(inIsRuleForPower);
        rule.setSruleIsStartDayIncluded(inIsStartIncluded);
        rule.setSruleIsEndDayIncluded(inIsEndIncluded);
        rule.setSruleIsFreedaysIncluded(inIsFreedaysIncluded);
        rule.setSruleIsGratisIncluded(inIsGratisIncluded);
        rule.setSruleStartDay(inStartDay);
        rule.setSruleEndDay(inEndDay);
        rule.setSruleCalendar(inCalendar);
        HibernateApi.getInstance().save((Object)rule);
        return rule;
    }

    public static StorageRule findOrCreateStorageRule(String inId, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule srule = StorageRule.findStorageRule(inId);
        if (srule == null) {
            srule = StorageRule.createStorageRule(inId, inStartDay, inEndDay, inCalendar);
        }
        return srule;
    }

    public static StorageRule createStorageRule(String inId, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule srule = new StorageRule();
        srule.setSruleId(inId);
        srule.setSruleStartDay(inStartDay);
        srule.setSruleEndDay(inEndDay);
        srule.setSruleIsFreedaysIncluded(Boolean.TRUE);
        srule.setSruleIsGratisIncluded(Boolean.TRUE);
        srule.setSruleIsRuleForPower(Boolean.FALSE);
        srule.setSruleCalendar(inCalendar);
        srule.setSruleIsFreeTimeChgedIfExceeded(Boolean.FALSE);
        HibernateApi.getInstance().save((Object)srule);
        return srule;
    }

    public static StorageRule createPowerStorageRule(String inId, String inStartDay, String inEndDay, ArgoCalendar inCalendar) {
        StorageRule srule = new StorageRule();
        srule.setSruleId(inId);
        srule.setSruleStartDay(inStartDay);
        srule.setSruleEndDay(inEndDay);
        srule.setSruleIsFreedaysIncluded(Boolean.TRUE);
        srule.setSruleIsGratisIncluded(Boolean.TRUE);
        srule.setSruleIsRuleForPower(Boolean.TRUE);
        srule.setSruleIsFreeTimeChgedIfExceeded(Boolean.FALSE);
        srule.setSruleCalendar(inCalendar);
        HibernateApi.getInstance().save((Object)srule);
        return srule;
    }

    public static StorageRule findStorageRule(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"StorageRule").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.SRULE_ID, (Object)inId));
        return (StorageRule)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static void create(StorageRule inSrule) {
        HibernateApi.getInstance().save((Object)inSrule);
    }

    public static StorageRule getStorageRule(Serializable inRuleGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"StorageRule").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.SRULE_GKEY, (Object)inRuleGkey));
        return (StorageRule)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public IMetafieldId getScopeFieldId() {
        return IInventoryField.SRULE_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IInventoryField.SRULE_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        if (this.getSruleCalendar() == null) {
            CalendarTypeEnum calType;
            if ((this.getSruleIsFreedaysIncluded().booleanValue() || this.getSruleIsGratisIncluded().booleanValue()) && ArgoCalendar.findDefaultCalendar((CalendarTypeEnum)(calType = this.getSruleIsRuleForPower() != false ? CalendarTypeEnum.POWER : CalendarTypeEnum.STORAGE)) == null) {
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.DEFAULT_CALENDAR_DOES_NOT_EXIST, (BizViolation)bv, (Object)calType);
            }
        } else {
            CalendarTypeEnum calendarTypeEnum = this.getSruleCalendar().getArgocalCalendarType();
            if (calendarTypeEnum.equals((Object)CalendarTypeEnum.STORAGE) && this.getSruleIsRuleForPower().booleanValue() || calendarTypeEnum.equals((Object)CalendarTypeEnum.POWER) && !this.getSruleIsRuleForPower().booleanValue()) {
                bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.WRONG_CALENDAR_TYPE_FOR_RULE_TYPE, (BizViolation)bv, (Object)this.getSruleCalendar().getArgocalId(), (Object)this.getSruleCalendar().getArgocalCalendarType(), (Object)this.getSruleId());
            }
        }
        return bv;
    }

    public BizViolation validateDeletion() {
        BizViolation bizViolation = super.validateDeletion();
        Long sruleGkey = this.getSruleGkey();
        if (sruleGkey != null) {
            Disjunction ruleDisjunction = PredicateFactory.disjunction();
            ruleDisjunction.add(PredicateFactory.eq((IMetafieldId)IArgoField.EMAPV_METAFIELD, (Object)IInventoryBizMetafield.STORAGE_RULE_TABLE_KEY.getFieldId())).add(PredicateFactory.eq((IMetafieldId)IArgoField.EMAPV_METAFIELD, (Object)IInventoryBizMetafield.LINE_STORAGE_RULE_TABLE_KEY.getFieldId())).add(PredicateFactory.eq((IMetafieldId)IArgoField.EMAPV_METAFIELD, (Object)IInventoryBizMetafield.POWER_RULE_TABLE_KEY.getFieldId()));
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"EntityMappingValue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.EMAPV_VALUE, (Object)sruleGkey.toString())).addDqPredicate((IPredicate)ruleDisjunction);
            boolean exists = HibernateApi.getInstance().existsByDomainQuery(dq);
            if (exists) {
                bizViolation = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.STORAGE_RULE_ID_REFERENCED, (BizViolation)bizViolation, (Object)this.getSruleId());
            }
        }
        return bizViolation;
    }

}
