package com.zpmc.ztos.infra.base.common.systems;

import com.zpmc.ztos.infra.base.business.dataobject.ServiceRuleDO;
import com.zpmc.ztos.infra.base.business.enums.argo.FlagPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.service.ServiceRuleTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.AbstractJunction;
import com.zpmc.ztos.infra.base.business.model.EntityIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.business.predicate.Join;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplatePropagationRequired;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.LogicalEntityUtil;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class ServiceRule extends ServiceRuleDO implements IObsoleteable
{
    private static final Logger LOGGER = Logger.getLogger(ServiceRule.class);

    public ServiceRule() {
        this.setSrvrulLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    @Nullable
    public static ServiceRule findServiceRule(String inRuleName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_NAME, (Object)inRuleName));
        return (ServiceRule) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static ServiceRule findOrCreateServiceRule(String inRuleName, ServiceRuleTypeEnum inRuleType, SavedPredicate inFilter, IMetafieldId inPathToGuardian, EventType inEventType, FlagType inFlagType, EventType inPrerequisiteEventType) {
        ServiceRule rule = ServiceRule.findServiceRule(inRuleName);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("findOrCreateServiceRule for " + inRuleName));
        }
        if (rule == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no rule for " + inRuleName + " found. Creating new one"));
            }
            rule = ServiceRule.createServiceRule(inRuleName, inRuleType, inFilter, inPathToGuardian, inEventType, inFlagType, inPrerequisiteEventType);
        }
        return rule;
    }

    public static ServiceRule updateOrCreateServiceRule(String inRuleName, ServiceRuleTypeEnum inRuleType, SavedPredicate inFilter, IMetafieldId inPathToGuardian, EventType inEventType, FlagType inFlagType, EventType inPrerequisiteEventType) {
        ServiceRule rule = ServiceRule.findServiceRule(inRuleName);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("updateOrCreateServiceRule for " + inRuleName));
        }
        if (rule == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no rule for " + inRuleName + " found. Creating new one"));
            }
            rule = ServiceRule.createServiceRule(inRuleName, inRuleType, inFilter, inPathToGuardian, inEventType, inFlagType, inPrerequisiteEventType);
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("rule for " + inRuleName + " found. Updating fields."));
            }
            rule.setSrvrulRuleType(inRuleType);
            rule.setSrvrulFilter(inFilter);
            if (inPathToGuardian != null) {
                rule.setSrvrulPathToGuardian(inPathToGuardian.getQualifiedId());
            } else {
                rule.setSrvrulPathToGuardian(null);
            }
            rule.setSrvrulServiceType(inEventType);
            rule.setSrvrulFlagType(inFlagType);
            rule.setSrvrulPrereqServiceType(inPrerequisiteEventType);
            HibernateApi.getInstance().update((Object)rule);
        }
        return rule;
    }

    static ServiceRule createServiceRule(String inRuleName, ServiceRuleTypeEnum inRuleType, SavedPredicate inFilter, IMetafieldId inPathToGuardian, EventType inEventType, FlagType inFlagType, EventType inPrerequisiteEventType) {
        ServiceRule rule = new ServiceRule();
        rule.setSrvrulName(inRuleName);
        rule.setSrvrulRuleType(inRuleType);
        rule.setSrvrulFilter(inFilter);
        if (inPathToGuardian != null) {
            rule.setSrvrulPathToGuardian(inPathToGuardian.getQualifiedId());
        }
        rule.setSrvrulServiceType(inEventType);
        rule.setSrvrulFlagType(inFlagType);
        rule.setSrvrulPrereqServiceType(inPrerequisiteEventType);
        HibernateApi.getInstance().save((Object)rule);
        return rule;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setSrvrulLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getSrvrulLifeCycleState();
    }

    public IMetafieldId getScopeFieldId() {
        return IServicesField.SRVRUL_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IServicesField.SRVRUL_NAME;
    }

    @Nullable
    public BizViolation verifyRuleSatisfied(IServiceable inServiceable) {
        if (!this.appliesToEntity((ILogicalEntity)inServiceable)) {
            return null;
        }
        ServiceRuleTypeEnum ruleType = this.getSrvrulRuleType();
        if (ServiceRuleTypeEnum.SIMPLE_HOLD.equals((Object)ruleType) || ServiceRuleTypeEnum.HOLD_ON_GUARDED.equals((Object)ruleType)) {
            return this.verifyFlag(inServiceable, FlagPurposeEnum.HOLD);
        }
        if (ServiceRuleTypeEnum.SIMPLE_PERMISSION.equals((Object)ruleType) || ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.equals((Object)ruleType)) {
            return this.verifyFlag(inServiceable, FlagPurposeEnum.PERMISSION);
        }
        if (ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)ruleType)) {
            return this.verifyFlagOnGuardian(inServiceable, FlagPurposeEnum.HOLD);
        }
        if (ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)ruleType)) {
            return this.verifyFlagOnGuardian(inServiceable, FlagPurposeEnum.PERMISSION);
        }
        if (ServiceRuleTypeEnum.PREREQUISITE_SERVICE.equals((Object)ruleType)) {
            return this.verifyServiceWasPerformed(inServiceable, this.getSrvrulPrereqServiceType());
        }
        return null;
    }

    @Nullable
    private IMetafieldId getPathToGuardian() {
        String path = this.getSrvrulPathToGuardian();
        return path == null ? null : MetafieldIdFactory.valueOf((String)path);
    }

    @Nullable
    private BizViolation verifyFlag(IServiceable inServiceable, FlagPurposeEnum inPurpose) {
        BizViolation bv = null;
        IMetafieldId pathToGuardian = this.getPathToGuardian();
        Object relatedObject = pathToGuardian != null ? inServiceable.getField(pathToGuardian) : null;
        List guardians = this.extractGuardianEntitiesFromPathToGuardianObject(relatedObject);
        if ((ServiceRuleTypeEnum.HOLD_ON_GUARDED.equals((Object)this.getSrvrulRuleType()) || ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.equals((Object)this.getSrvrulRuleType())) && (guardians == null || guardians.size() <= 0)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"The entered IServiceable does not have a reference to a guardian considered for this guarded rule. Therefore, rule is not valid for this entity");
            }
            return null;
        }
        if (guardians == null) {
            bv = this.verifyFlagAndCreateErrorMessage(inServiceable, null, inPurpose, bv, false);
        } else {
            for (Object guardian : guardians) {
                IGuardian referencedGuardian = (IGuardian)guardian;
                bv = this.verifyFlagAndCreateErrorMessage(inServiceable, referencedGuardian, inPurpose, bv, false);
            }
        }
        return bv;
    }

    @Nullable
    private BizViolation verifyFlagOnGuardian(IServiceable inServiceable, FlagPurposeEnum inPurpose) {
        IMetafieldId pathToGuardian = this.getPathToGuardian();
        if (pathToGuardian == null) {
            throw BizFailure.create((IPropertyKey) IServicesPropertyKeys.NO_PATH_TO_GUARDIAN, null, (Object)this);
        }
        Object relatedObjects = inServiceable.getField(pathToGuardian);
        List guardians = this.extractGuardianEntitiesFromPathToGuardianObject(relatedObjects);
        if (guardians == null || guardians.size() <= 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)"The entered IServiceable does not have a reference to a guardian considered for this rule. Therefore, rule is not valid for this entity");
            }
            return null;
        }
        BizViolation bv = null;
        for (Object guardian1 : guardians) {
            IGuardian guardian = (IGuardian)guardian1;
            if (guardian.isCorrectEntityType(this.getSrvrulFlagType().getAppliesTo())) {
                bv = this.verifyFlagAndCreateErrorMessage(inServiceable, guardian, inPurpose, bv, true);
                continue;
            }
            if (!LOGGER.isDebugEnabled()) continue;
            LOGGER.debug((Object)("guardian type is " + (Object)guardian.getLogicalEntityType() + ", but rule is for type " + (Object)this.getSrvrulFlagType().getAppliesTo()));
        }
        return bv;
    }

    @Nullable
    private List extractGuardianEntitiesFromPathToGuardianObject(Object inRelatedObjects) {
        ArrayList<Object> guardians = null;
        if (inRelatedObjects != null) {
            guardians = new ArrayList<Object>();
            if (inRelatedObjects instanceof IGuardian) {
                guardians.add(inRelatedObjects);
            } else if (inRelatedObjects instanceof Collection) {
                for (Object guardianOfColl : (Collection)inRelatedObjects) {
                    if (IGuardian.class.isAssignableFrom(guardianOfColl.getClass())) {
                        guardians.add(guardianOfColl);
                        continue;
                    }
                    LOGGER.error((Object)("verifyFlag: calculated guardian is collection - but an entry is of wrong class, class=" + guardianOfColl.getClass() + ", value=" + guardianOfColl));
                }
            } else {
                LOGGER.error((Object)("verifyFlag: calculated guardian is of wrong class, class=" + inRelatedObjects.getClass() + ", value=" + inRelatedObjects));
            }
        }
        return guardians;
    }

    private BizViolation verifyFlagAndCreateErrorMessage(IServiceable inServiceable, IGuardian inGuardian, FlagPurposeEnum inPurpose, BizViolation inBv, boolean inIsGuardianCheck) {
        FlagType flagType = this.getSrvrulFlagType();
        boolean hasActiveFlag = inIsGuardianCheck ? flagType.isActiveFlagPresent((ILogicalEntity)inGuardian, null, inServiceable) : flagType.isActiveFlagPresent((ILogicalEntity)inServiceable, inGuardian, null);
        boolean isGuardedCheck = !inIsGuardianCheck && inGuardian != null;
        Object[] params = this.createMessageParamsForVerifyFlag(flagType, inServiceable, inIsGuardianCheck, isGuardedCheck, inGuardian, this.getSrvrulServiceType());
        if (inPurpose == FlagPurposeEnum.HOLD && hasActiveFlag) {
            inBv = isGuardedCheck ? BizViolation.create((IPropertyKey) IServicesPropertyKeys.GUARDED_HOLD_PREVENTS_EVENT_RECORDING, (Throwable)null, (BizViolation)inBv, null, (Object[])params) : BizViolation.create((IPropertyKey) IServicesPropertyKeys.HOLD_PREVENTS_EVENT_RECORDING, (Throwable)null, (BizViolation)inBv, null, (Object[])params);
        } else if (inPurpose == FlagPurposeEnum.PERMISSION && !hasActiveFlag) {
            inBv = isGuardedCheck ? BizViolation.create((IPropertyKey) IServicesPropertyKeys.NO_GUARDED_PERMISSION_FOR_EVENT_RECORDING, (Throwable)null, (BizViolation)inBv, null, (Object[])params) : BizViolation.create((IPropertyKey) IServicesPropertyKeys.NO_PERMISSION_FOR_EVENT_RECORDING, (Throwable)null, (BizViolation)inBv, null, (Object[])params);
        }
        return inBv;
    }

    private Object[] createMessageParamsForVerifyFlag(FlagType inFlagType, IServiceable inServiceable, boolean inIsGuardianCheck, boolean inGuardedCheck, IGuardian inGuardian, EventType inEventType) {
        Object[] params = inGuardedCheck ? new Object[8] : new Object[6];
        params[0] = inFlagType.getFlgtypId();
        params[1] = inServiceable.getLogicalEntityType().getName();
        params[2] = ": " + inServiceable.getLogEntityId();
        if (inIsGuardianCheck) {
            params[3] = inGuardian.getLogicalEntityType().getName();
            params[4] = ": " + inGuardian.getLogEntityId();
            params[5] = inEventType.getEvnttypeId();
        } else {
            params[3] = inServiceable.getLogicalEntityType().getName();
            params[4] = ": " + inServiceable.getLogEntityId();
            params[5] = inEventType.getEvnttypeId();
        }
        if (inGuardedCheck) {
            params[5] = inGuardian.getLogicalEntityType().getName();
            params[6] = ": " + inGuardian.getLogEntityId();
            params[7] = inEventType.getEvnttypeId();
        }
        return params;
    }

    @Nullable
    private BizViolation verifyServiceWasPerformed(IServiceable inServiceable, EventType inSrvrulPrereqServiceType) {
        IServicesManager sm = (IServicesManager) Roastery.getBean((String)"servicesManager");
        if (!sm.hasEventTypeBeenRecorded((IEventType)inSrvrulPrereqServiceType, inServiceable)) {
            Object[] params = new Object[]{inServiceable.getLogicalEntityType(), inServiceable.getLogEntityId(), inSrvrulPrereqServiceType.getEvnttypeId(), this.getSrvrulServiceType().getEvnttypeId()};
            return BizViolation.create((IPropertyKey) IServicesPropertyKeys.REQ_EVENT_TYPE_NOT_RECORDED, (Throwable)null, null, null, (Object[])params);
        }
        return null;
    }

    public boolean appliesToEntity(ILogicalEntity inEntity) {
        EventType eventType = this.getSrvrulServiceType();
        if (!eventType.getAppliesToEntityType().equals((Object)inEntity.getLogicalEntityType())) {
            return false;
        }
        return this.isFilterForEntity(inEntity);
    }

    public boolean isFilterForEntity(ILogicalEntity inEntity) {
        SavedPredicate filter = this.getSrvrulFilter();
        if (filter == null) {
            return true;
        }
        IPredicate predicate = filter.getExecutablePredicate();
        return predicate == null || predicate.isSatisfiedBy((IValueSource)inEntity);
    }

    @Nullable
    public IMetafieldId getServiceRulePathToGuardianFilterField() {
        return this.getPathToGuardian();
    }

    public static IPredicate formServiceRulePathToGuardianFilterFieldPredicate(IMetafieldId inMetafieldId, final PredicateVerbEnum inVerb, final Object inValue) {
        final IPredicate[] predIntf = new IPredicate[]{null};
        PersistenceTemplatePropagationRequired pt = new PersistenceTemplatePropagationRequired(UserContextUtils.getSystemUserContext());
        IMessageCollector collector = pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                predIntf[0] = PredicateFactory.createPredicate((UserContext) ContextHelper.getThreadUserContext(), (IMetafieldId) IServicesField.SRVRUL_PATH_TO_GUARDIAN, (PredicateVerbEnum)inVerb, (Object)inValue);
            }
        });
        return predIntf[0];
    }

    @Nullable
    public IValueHolder getServiceRuleFilterVao() {
        SavedPredicate predicate = this.getSrvrulFilter();
        return predicate == null ? null : predicate.getPredicateVao();
    }

    private boolean isCompatibleFlagAndRuleType(FlagType inFlagType, ServiceRuleTypeEnum inServiceRuleType) {
        if (inFlagType == null || inServiceRuleType == null) {
            return true;
        }
        if (FlagPurposeEnum.HOLD.equals((Object)inFlagType.getFlgtypPurpose())) {
            if (ServiceRuleTypeEnum.SIMPLE_HOLD.equals((Object)inServiceRuleType)) {
                return true;
            }
            if (ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)inServiceRuleType)) {
                return true;
            }
            if (ServiceRuleTypeEnum.HOLD_ON_GUARDED.equals((Object)inServiceRuleType)) {
                return true;
            }
        } else if (FlagPurposeEnum.PERMISSION.equals((Object)inFlagType.getFlgtypPurpose())) {
            if (ServiceRuleTypeEnum.SIMPLE_PERMISSION.equals((Object)inServiceRuleType)) {
                return true;
            }
            if (ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)inServiceRuleType)) {
                return true;
            }
            if (ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.equals((Object)inServiceRuleType)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isGuardianRequired(ServiceRuleTypeEnum inServiceRuleType) {
        if (ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)inServiceRuleType)) {
            return true;
        }
        if (ServiceRuleTypeEnum.HOLD_ON_GUARDED.equals((Object)inServiceRuleType)) {
            return true;
        }
        if (ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)inServiceRuleType)) {
            return true;
        }
        return ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.equals((Object)inServiceRuleType);
    }

    private boolean isCompatibleServiceTypeAndFlagType(EventType inEventType, FlagType inFlagType) {
        LogicalEntityEnum flagTypeAppliesTo;
        LogicalEntityEnum eventTypeAppliesTo = inEventType.getEvnttypeAppliesTo();
        return eventTypeAppliesTo.equals((Object)(flagTypeAppliesTo = inFlagType.getFlgtypAppliesTo()));
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        boolean ruleRequiresFlagType;
        FieldChange fieldChange = inFieldChanges.getFieldChange(IServicesField.SRVRUL_RULE_TYPE);
        ServiceRuleTypeEnum ruleTypeEnum = fieldChange != null ? (ServiceRuleTypeEnum)((Object)fieldChange.getNewValue()) : this.getSrvrulRuleType();
        boolean ruleRequiresGuardian = this.isGuardianRequired(ruleTypeEnum);
        boolean ruleRequiresPrereq = ServiceRuleTypeEnum.PREREQUISITE_SERVICE.equals((Object)ruleTypeEnum);
        boolean bl = ruleRequiresFlagType = !ruleRequiresPrereq;
        if (!ruleRequiresGuardian) {
            inFieldChanges.removeFieldChange(IServicesField.SRVRUL_PATH_TO_GUARDIAN);
            inFieldChanges.setFieldChange(IServicesField.SRVRUL_PATH_TO_GUARDIAN, null);
        }
        if (!ruleRequiresPrereq) {
            inFieldChanges.removeFieldChange(IServicesField.SRVRUL_PREREQ_SERVICE_TYPE);
            inFieldChanges.setFieldChange(IServicesField.SRVRUL_PREREQ_SERVICE_TYPE, null);
        }
        if (!ruleRequiresFlagType) {
            inFieldChanges.removeFieldChange(IServicesField.SRVRUL_FLAG_TYPE);
            inFieldChanges.setFieldChange(IServicesField.SRVRUL_FLAG_TYPE, null);
        }
        super.applyFieldChanges(inFieldChanges);
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        List allRules;
        FieldChange fieldChange;
        BizViolation bizViolation = super.validateChanges(inFieldChanges);
        FlagType flagType = this.getSrvrulFlagType();
        ServiceRuleTypeEnum ruleTypeEnum = this.getSrvrulRuleType();
        EventType eventType = this.getSrvrulServiceType();
        EventType prereqEventType = this.getSrvrulPrereqServiceType();
        if (flagType != null && ruleTypeEnum != null && !this.isCompatibleFlagAndRuleType(flagType, ruleTypeEnum)) {
            String ruleTypeKey = ruleTypeEnum.getKey();
            int flagTypeKey = flagType.getFlgtypPurpose().getKey();
            bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.INCOMPATIBLE_RULETYPE_AND_FLAGTYPE, (BizViolation)bizViolation, (Object)flagTypeKey, (Object)ruleTypeKey);
        }
        boolean isGuardianRule = this.isGuardianRule();
        if (flagType != null && eventType != null) {
            if (!isGuardianRule) {
                if (!this.isCompatibleServiceTypeAndFlagType(eventType, flagType)) {
                    Object[] parms = new Object[]{flagType.getId(), flagType.getFlgtypAppliesTo(), eventType.getEvnttypeId(), eventType.getEvnttypeAppliesTo()};
                    bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.INCOMPATIBLE_EVENTTYPE_AND_FLAGTYPE, (Throwable)null, (BizViolation)bizViolation, null, (Object[])parms);
                }
            } else {
                String pathToGuardian = this.getSrvrulPathToGuardian();
                IMetafieldId pathToGuardianId = MetafieldIdFactory.valueOf((String)pathToGuardian);
                if (pathToGuardianId != null && !this.isCompatibleGuardianTypeAndFlagType(pathToGuardianId, flagType)) {
                    Object[] parms = new Object[4];
                    parms[0] = flagType.getId();
                    parms[1] = flagType.getFlgtypAppliesTo();
                    bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.INCOMPATIBLE_PATH_TO_GUARDIAN_AND_FLAGTYPE, (Throwable)null, (BizViolation)bizViolation, null, (Object[])parms);
                }
            }
        }
        if ((fieldChange = inFieldChanges.getFieldChange(IServicesField.SRVRUL_LIFE_CYCLE_STATE)) != null && LifeCycleStateEnum.ACTIVE.equals(fieldChange.getNewValue())) {
            bizViolation = this.validateEventTypeActive(eventType, bizViolation);
            if (ServiceRuleTypeEnum.PREREQUISITE_SERVICE.equals((Object)ruleTypeEnum)) {
                if (prereqEventType != null) {
                    bizViolation = this.validateEventTypeActive(prereqEventType, bizViolation);
                }
            } else if (flagType != null) {
                IDomainQuery dq = QueryUtils.createDomainQuery((String)"FlagType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLGTYP_GKEY, (Object)flagType.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLGTYP_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
                Serializable[] eventGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
                if (eventGkeys.length == 0 || eventGkeys[0] == null) {
                    bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.SERVICE_RULE_FLAG_TYPE_IS_OBSOLETE, (BizViolation)bizViolation, (Object)this.getSrvrulName(), (Object)flagType.getFlgtypId());
                }
            }
        }
        if (flagType != null && !(allRules = (List) ServiceRule.findActiveServiceRuleForFlagType(flagType)).isEmpty()) {
            ServiceRule sr = (ServiceRule)allRules.get(0);
            if (!(allRules.size() <= 1 && sr.getSrvrulName().equals(this.getSrvrulName()) || this.getSrvrulRuleType().equals((Object)sr.getSrvrulRuleType()))) {
                Object[] params = new Object[]{this.getSrvrulName(), sr.getSrvrulRuleType(), sr.getSrvrulFlagType().getFlgtypId(), this.getSrvrulRuleType()};
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.SERVICE_RULE_FOR_FLAG_WITH_DIFFERENT_RULE_TYPE, (Throwable)null, (BizViolation)bizViolation, null, (Object[])params);
            }
        }
        boolean ruleRequiresGuardian = this.isGuardianRequired(ruleTypeEnum);
        boolean ruleRequiresPrereq = ServiceRuleTypeEnum.PREREQUISITE_SERVICE.equals((Object)ruleTypeEnum);
        boolean ruleRequiresFlagType = !ruleRequiresPrereq;
        bizViolation = this.validateConditionalField(IServicesField.SRVRUL_PATH_TO_GUARDIAN, ruleRequiresGuardian, this.getSrvrulPathToGuardian(), ruleTypeEnum, bizViolation);
        bizViolation = this.validateConditionalField(IServicesField.SRVRUL_PREREQ_SERVICE_TYPE, ruleRequiresPrereq, prereqEventType, ruleTypeEnum, bizViolation);
        bizViolation = this.validateConditionalField(IServicesField.SRVRUL_FLAG_TYPE, ruleRequiresFlagType, this.getSrvrulFlagType(), ruleTypeEnum, bizViolation);
        if (ruleRequiresPrereq) {
            EventType preReqEvnt = this.getSrvrulPrereqServiceType();
            EventType ruleEvnt = this.getSrvrulServiceType();
            if (preReqEvnt != null && ruleEvnt != null && !preReqEvnt.getEvnttypeAppliesTo().equals((Object)ruleEvnt.getEvnttypeAppliesTo())) {
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.ERROR_SERVICE_RULE_PREREQUISITE_AND_EVENT_TYPE_FOR_DIFFERENT_ENTITIES, (BizViolation)bizViolation, (Object)preReqEvnt.getEvnttypeId(), (Object)ruleEvnt.getEvnttypeId());
            }
        }
        return bizViolation;
    }

    private BizViolation validateEventTypeActive(EventType inEventType, BizViolation inBizViolation) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EventType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_GKEY, (Object)inEventType.getEvnttypeGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.EVNTTYPE_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        Serializable[] eventGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (eventGkeys.length == 0 || eventGkeys[0] == null) {
            inBizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.SERVICE_RULE_EVENT_TYPE_IS_OBSOLETE, (BizViolation)inBizViolation, (Object)this.getSrvrulName(), (Object)inEventType.getEvnttypeId());
        }
        return inBizViolation;
    }

    private boolean isCompatibleGuardianTypeAndFlagType(IMetafieldId inPathToGuardianId, FlagType inFlagType) {
        LogicalEntityEnum[] possibleEntities = LogicalEntityUtil.getPathToGuardianCompatibleLogEntityTypes((IMetafieldId)inPathToGuardianId);
        if (possibleEntities != null) {
            LogicalEntityEnum flagEntity = inFlagType.getFlgtypAppliesTo();
            for (LogicalEntityEnum possibleEntity : possibleEntities) {
                if (!flagEntity.equals((Object)possibleEntity)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isGuardianRule() {
        ServiceRuleTypeEnum ruleType = this.getSrvrulRuleType();
        return ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)this.getSrvrulRuleType()) || ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)ruleType);
    }

    private BizViolation validateConditionalField(IMetafieldId inField, boolean inValueRequired, Object inValue, ServiceRuleTypeEnum inRuleType, BizViolation inBv) {
        if (inValueRequired && inValue == null) {
            return BizViolation.createFieldViolation((IPropertyKey) IServicesPropertyKeys.RULETYPE_REQUIRES_FIELD, (BizViolation)inBv, (IMetafieldId)inField, (Object)((Object)inRuleType));
        }
        if (!inValueRequired && inValue != null) {
            LOGGER.error((Object)("validateConditionalField: field " + (Object)inField + " should be null, but has value " + inValue + " for " + this));
        }
        return inBv;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFieldValue(IMetafieldId inMetafieldId, Object inFieldValue) {
//        if (!ServicesBizMetafield.SERVICE_RULE_FILTER_VAO.equals((Object)inMetafieldId)) {
//            super.setFieldValue(inMetafieldId, inFieldValue);
//            return;
//        }
        if (inFieldValue != null && !(inFieldValue instanceof IValueHolder)) {
            LOGGER.error((Object)("setFieldValue: invalid input " + inFieldValue));
            return;
        }
        SavedPredicate predicate = this.getSrvrulFilter();
        if (predicate != null) {
            this.setSrvrulFilter(null);
            ArgoUtils.carefulDelete((Object)predicate);
        }
        if (inFieldValue == null) {
            this.setSrvrulFilter(null);
            return;
        }
        predicate = new SavedPredicate((IValueHolder)inFieldValue);
        this.setSrvrulFilter(predicate);
    }

    public String toString() {
        return "ServiceRule:" + this.getSrvrulName();
    }

    public static Collection findActiveServiceRuleForFlagType(FlagType inFlagType) {
        IDomainQuery flagTypesQuery = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object)inFlagType.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(flagTypesQuery);
    }

    public static Collection findActiveServiceRuleForEntityTypeAndFlagType(LogicalEntityEnum inEntityType, FlagType inFlagType) {
        IDomainQuery flagTypesQuery = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object)inFlagType.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (IMetafieldId) IServicesField.EVNTTYPE_APPLIES_TO), (Object)inEntityType)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(flagTypesQuery);
    }

    public static Collection findActiveServiceRuleForEntityTypeAndFlagTypes(LogicalEntityEnum inEntityType, Serializable[] inFlagTypeGkeys) {
        IDomainQuery flagTypesQuery = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.in((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object[])inFlagTypeGkeys)).addDqPredicate(PredicateFactory.eq((IMetafieldId) MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (IMetafieldId) IServicesField.EVNTTYPE_APPLIES_TO), (Object)inEntityType)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(flagTypesQuery);
    }

    public static Collection findActiveServiceRulesForEntity(ILogicalEntity inEntity) {
        return ServiceRule.findServiceRulesForEntity(inEntity, null, true);
    }

    public static Collection findActiveServiceRulesForEntityAndEventType(ILogicalEntity inEntity, EventType inEventType) {
        return ServiceRule.findServiceRulesForEntity(inEntity, inEventType, true);
    }

    public static Map<IEventType, Collection> findActiveServiceRulesForEntityAndEventTypes(ILogicalEntity inEntity, IEventType[] inEventTypes) {
        return ServiceRule.findServiceRulesForEntityAndEventTypes(inEntity, inEventTypes, true);
    }

    public static Collection findAllServiceRulesForEntity(ILogicalEntity inEntity) {
        return ServiceRule.findServiceRulesForEntity(inEntity, null, false);
    }

    public static Collection findAllServiceRulesForEntityAndEventType(ILogicalEntity inEntity, EventType inEventType) {
        return ServiceRule.findServiceRulesForEntity(inEntity, inEventType, false);
    }

    private static Collection findServiceRulesForEntity(ILogicalEntity inEntity, EventType inEventType, boolean inIncludeActiveRulesOnly) {
        List allRules = ServiceRule.getAllRulesForEntityType(inEntity.getLogicalEntityType(), inEventType, inIncludeActiveRulesOnly);
        return ServiceRule.getFilteredRules(inEntity, allRules);
    }

    public static Collection getFilteredRules(ILogicalEntity inEntity, List inAllRules) {
        ArrayList<ServiceRule> filteredRules = new ArrayList<ServiceRule>();
        for (Object result : inAllRules) {
            boolean isGuardianRule;
            ServiceRule rule;
            if (result instanceof Object[]) {
                rule = (ServiceRule)((Object[])result)[0];
            } else if (result instanceof ServiceRule) {
                rule = (ServiceRule)result;
            } else {
                LOGGER.error((Object)"Result for findServiceRulesForEntity is neither Object[] nor Entity!");
                continue;
            }
            ServiceRuleTypeEnum ruleType = rule.getSrvrulRuleType();
            boolean bl = isGuardianRule = ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)ruleType) || ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)ruleType);
            if (isGuardianRule) {
                boolean isGuardianOfGuardianRule;
                boolean bl2 = isGuardianOfGuardianRule = rule.getSrvrulFlagType().getAppliesTo() != null && rule.getSrvrulFlagType().getAppliesTo().equals((Object)inEntity.getLogicalEntityType());
                if (!isGuardianOfGuardianRule && !rule.isFilterForEntity(inEntity)) continue;
                filteredRules.add(rule);
                continue;
            }
            if (!rule.appliesToEntity(inEntity)) continue;
            filteredRules.add(rule);
        }
        return filteredRules;
    }

    public static List getAllRulesForEntityType(LogicalEntityEnum inEntityType, EventType inEventType, boolean inIncludeActiveRulesOnly) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule");
        if (inEventType != null) {
            Long evnttypeGkey = inEventType.getEvnttypeGkey();
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (Object)evnttypeGkey));
        } else {
            String flagAlias = "flags";
            IEntityId flagId = EntityIdFactory.valueOf((String)"FlagType", (String)flagAlias);
            Join flagJoin = PredicateFactory.createJoin((JoinTypeEnum) JoinTypeEnum.LEFT_OUTER_JOIN, (IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (String)flagAlias);
            IMetafieldId entityAwareFlagAppliesToField = MetafieldIdFactory.getEntityAwareMetafieldId((IEntityId)flagId, (IMetafieldId) IServicesField.FLGTYP_APPLIES_TO);
            AbstractJunction eitherFlagOrEventAppliesTo = PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId)entityAwareFlagAppliesToField, (Object)inEntityType)).add(PredicateFactory.eq((IMetafieldId) MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (IMetafieldId) IServicesField.EVNTTYPE_APPLIES_TO), (Object)inEntityType));
            dq.addDqPredicate((IPredicate)eitherFlagOrEventAppliesTo).addDqJoin(flagJoin);
        }
        if (inIncludeActiveRulesOnly) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    private static Map<IEventType, Collection> findServiceRulesForEntityAndEventTypes(ILogicalEntity inEntity, IEventType[] inEventTypes, boolean inIncludeActiveRulesOnly) {
        Object[] eventTypeGkeys = new Serializable[inEventTypes.length];
        for (int i = 0; i < inEventTypes.length; ++i) {
            EventType eventType = EventType.resolveIEventType(inEventTypes[i]);
            eventTypeGkeys[i] = eventType.getEvnttypeGkey();
        }
        HashMap<IEventType, Collection> rulesByEventType = new HashMap<IEventType, Collection>();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule");
        if (eventTypeGkeys.length > 0) {
            if (eventTypeGkeys.length == 1) {
                dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (Object)eventTypeGkeys[0]));
            } else {
                dq.addDqPredicate(PredicateFactory.in((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (Object[])eventTypeGkeys));
            }
        } else {
            String flagAlias = "flags";
            IEntityId flagId = EntityIdFactory.valueOf((String)"FlagType", (String)flagAlias);
            Join flagJoin = PredicateFactory.createJoin((JoinTypeEnum) JoinTypeEnum.LEFT_OUTER_JOIN, (IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (String)flagAlias);
            IMetafieldId entityAwareFlagAppliesToField = MetafieldIdFactory.getEntityAwareMetafieldId((IEntityId)flagId, (IMetafieldId) IServicesField.FLGTYP_APPLIES_TO);
            AbstractJunction eitherFlagOrEventAppliesTo = PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId)entityAwareFlagAppliesToField, (Object)inEntity.getLogicalEntityType())).add(PredicateFactory.eq((IMetafieldId) MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IServicesField.SRVRUL_SERVICE_TYPE, (IMetafieldId) IServicesField.EVNTTYPE_APPLIES_TO), (Object)inEntity.getLogicalEntityType()));
            dq.addDqPredicate((IPredicate)eitherFlagOrEventAppliesTo).addDqJoin(flagJoin);
        }
        if (inIncludeActiveRulesOnly) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        }
        List allRules = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        for (Object result : allRules) {
            ServiceRule rule;
            if (result instanceof Object[]) {
                rule = (ServiceRule)((Object[])result)[0];
            } else if (result instanceof ServiceRule) {
                rule = (ServiceRule)result;
            } else {
                LOGGER.error((Object)"Result for findServiceRulesForEntity is neither Object[] nor Entity!");
                continue;
            }
            ServiceRuleTypeEnum ruleType = rule.getSrvrulRuleType();
            boolean isGuardianRule = ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.equals((Object)ruleType) || ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.equals((Object)ruleType);
            ArrayList<ServiceRule> filteredRules = (ArrayList<ServiceRule>)rulesByEventType.get(rule.getSrvrulServiceType());
            if (filteredRules == null) {
                filteredRules = new ArrayList<ServiceRule>();
                rulesByEventType.put((IEventType) rule.getSrvrulServiceType(), filteredRules);
            }
            if (isGuardianRule) {
                boolean isGuardianOfGuardianRule;
                boolean bl = isGuardianOfGuardianRule = rule.getSrvrulFlagType().getAppliesTo() != null && rule.getSrvrulFlagType().getAppliesTo().equals((Object)inEntity.getLogicalEntityType());
                if (!isGuardianOfGuardianRule && !rule.isFilterForEntity(inEntity)) continue;
                filteredRules.add(rule);
                continue;
            }
            if (!rule.appliesToEntity(inEntity)) continue;
            filteredRules.add(rule);
        }
        return rulesByEventType;
    }

    public boolean isHoldingRule() {
        ServiceRuleTypeEnum type = this.getSrvrulRuleType();
        return type == ServiceRuleTypeEnum.SIMPLE_HOLD || type == ServiceRuleTypeEnum.HOLD_ON_GUARDED || type == ServiceRuleTypeEnum.HOLD_ON_GUARDIAN;
    }

    void updateFilter(SavedPredicate inNewFilter) {
        SavedPredicate oldFilter = this.getSrvrulFilter();
        this.setSrvrulFilter(inNewFilter);
        ArgoUtils.carefulDelete((Object)oldFilter);
    }

}
