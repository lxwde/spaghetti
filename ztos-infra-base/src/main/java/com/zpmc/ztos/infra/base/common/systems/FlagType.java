package com.zpmc.ztos.infra.base.common.systems;

import com.zpmc.ztos.infra.base.business.dataobject.FlagTypeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.FlagActionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.FlagPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.business.predicate.Disjunction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class FlagType extends FlagTypeDO {
    private static final Logger LOGGER = Logger.getLogger(FlagType.class);

    public FlagType() {
        this.setFlgtypIsFlagReferenceIdUnique(Boolean.FALSE);
        this.setFlgtypIsFlagReferenceIdRequired(Boolean.FALSE);
        this.setFlgtypIsAddFlagReferenceIdRequired(Boolean.FALSE);
        this.setFlgtypIsVetoFlagReferenceIdRequired(Boolean.FALSE);
        this.setFlgtypLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setFlgtypLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getFlgtypLifeCycleState();
    }

    public static FlagType findOrCreateFlagType(String inId, String inDescription, FlagPurposeEnum inPurpose, boolean inIsReferenceUnique, LogicalEntityEnum inAppliesToEntityType) {
        FlagType flagType;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("findOrCreateFlagType for id " + inId));
        }
        if ((flagType = FlagType.findFlagType(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no flag type for id" + inId + " found. Creating new one"));
            }
            flagType = FlagType.createFlagType(inId, inDescription, inIsReferenceUnique, inPurpose, inAppliesToEntityType);
        }
        return flagType;
    }

    public static FlagType updateOrCreateFlagType(String inId, String inDescription, FlagPurposeEnum inPurpose, boolean inIsReferenceUnique, LogicalEntityEnum inAppliesToEntityType) {
        FlagType flagType;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("updateOrCreateFlagType for id " + inId));
        }
        if ((flagType = FlagType.findFlagType(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no flag type for id" + inId + " found. Creating new one"));
            }
            flagType = FlagType.createFlagType(inId, inDescription, inIsReferenceUnique, inPurpose, inAppliesToEntityType);
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("flag type for id" + inId + " found. Updating fields."));
            }
            flagType.setFlgtypLifeCycleState(LifeCycleStateEnum.ACTIVE);
            flagType.setFlgtypDescription(inDescription);
            flagType.setFlgtypIsFlagReferenceIdUnique(inIsReferenceUnique);
            flagType.setFlgtypPurpose(inPurpose);
            flagType.setFlgtypAppliesTo(inAppliesToEntityType);
            HibernateApi.getInstance().update((Object)flagType);
        }
        return flagType;
    }

    public static Collection findActiveFlagsOnEntity(ILogicalEntity inLogicalEntity) {
        List flags = HibernateApi.getInstance().findEntitiesByDomainQuery(FlagType.findActiveFlagsOnEntityDq(inLogicalEntity));
        return flags;
    }

    protected static Collection findActiveFlagsOnEntity(ILogicalEntity inLogicalEntity, Boolean inAllowCovert) {
        List flags = HibernateApi.getInstance().findEntitiesByDomainQuery(FlagType.findActiveFlagsOnEntityDq(inLogicalEntity, inAllowCovert));
        return flags;
    }

    public static IDomainQuery findActiveFlagsOnEntityDq(ILogicalEntity inLogicalEntity) {
        IDomainQuery dq = FlagType.findAllActiveFlagsDq(inLogicalEntity);
        IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"FlagType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLGTYP_IS_COVERT_HOLD_REQUIRED, (Object) Boolean.TRUE)).addDqField(IServicesField.FLGTYP_GKEY);
        dq.addDqPredicate(PredicateFactory.subQueryNotIn((IDomainQuery)subQuery, (IMetafieldId) IServicesField.FLAG_FLAG_TYPE));
        return dq;
    }

    protected static IDomainQuery findActiveFlagsOnEntityDq(ILogicalEntity inLogicalEntity, Boolean inAllowCovert) {
        if (inAllowCovert != null && inAllowCovert.booleanValue()) {
            return FlagType.findAllActiveFlagsDq(inLogicalEntity);
        }
        return FlagType.findActiveFlagsOnEntityDq(inLogicalEntity);
    }

    private static IDomainQuery findAllActiveFlagsDq(ILogicalEntity inLogicalEntity) {
        IDomainQuery subQueryNoUniqueRefId = QueryUtils.createDomainQuery((String)"Veto").addDqPredicate(PredicateFactory.eqProperty((IMetafieldId) IServicesField.VETO_BLOCKED_FLAG, (IMetafieldId) OuterQueryMetafieldId.valueOf((IMetafieldId) IServicesField.FLAG_GKEY)));
        IPredicate entityVetokey = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_PRIMARY_KEY, (Object)inLogicalEntity.getPrimaryKey());
        IPredicate entityVetoclass = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_CLASS, (Object)inLogicalEntity.getLogicalEntityType());
        Conjunction entityVeto = (Conjunction) PredicateFactory.conjunction().add(entityVetokey).add(entityVetoclass);
        subQueryNoUniqueRefId.addDqPredicate((IPredicate)entityVeto);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_APPLIED_TO_CLASS, (Object)inLogicalEntity.getLogicalEntityType())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY, (Object)inLogicalEntity.getPrimaryKey())).addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.subQueryExists((IDomainQuery)subQueryNoUniqueRefId)));
        return dq;
    }

    private static FlagType createFlagType(String inId, String inDescription, boolean inIsReferenceUnique, FlagPurposeEnum inPurpose, LogicalEntityEnum inAppliesToEntityType) {
        if (inId == null || inPurpose == null || inAppliesToEntityType == null) {
            throw BizFailure.create((String)"Method createFlagType called with anvalid parameters: either id, purpose or appliesToEntityType is null");
        }
        FlagType flagType = new FlagType();
        flagType.setFlgtypId(inId);
        flagType.setFlgtypDescription(inDescription);
        flagType.setFlgtypIsFlagReferenceIdUnique(inIsReferenceUnique);
        flagType.setFlgtypPurpose(inPurpose);
        flagType.setFlgtypAppliesTo(inAppliesToEntityType);
        HibernateApi.getInstance().save((Object)flagType);
        return flagType;
    }

    public List findMatchingActiveFlags(ILogicalEntity inLogicalEntity, IGuardian inReferenceGuardianEntity, IServiceable inServiceable, String inReferenceId, boolean inCheckReferenceId) {
        return this.findActiveFlagsForEntity(inLogicalEntity, inReferenceGuardianEntity, inServiceable, inReferenceId, inCheckReferenceId);
    }

    public boolean isActiveFlagPresent(ILogicalEntity inLogicalEntity, IGuardian inReferencedGuardianEntity, IServiceable inServiceable) {
        List flags = this.findActiveFlagsForEntity(inLogicalEntity, inReferencedGuardianEntity, inServiceable, null, false);
        return !flags.isEmpty();
    }

    private List findActiveFlagsForEntity(ILogicalEntity inLogicalEntity, IGuardian inReferencedGuardianEntity, IServiceable inServiceable, String inReferenceId, boolean inCheckReferenceId) {
        if (inServiceable != null && inReferencedGuardianEntity != null) {
            throw BizFailure.create((String)"method not used correctly: situation with an entered reference guardian and a serviceable is not possible!");
        }
        IDomainQuery subQuery = QueryUtils.createDomainQuery((String)"Veto").addDqPredicate(PredicateFactory.eqProperty((IMetafieldId) IServicesField.VETO_BLOCKED_FLAG, (IMetafieldId) OuterQueryMetafieldId.valueOf((IMetafieldId) IServicesField.FLAG_GKEY)));
        IPredicate entityVetokey = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_PRIMARY_KEY, (Object)inLogicalEntity.getPrimaryKey());
        IPredicate entityVetoclass = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_CLASS, (Object)inLogicalEntity.getLogicalEntityType());
        Conjunction entityVeto = (Conjunction) PredicateFactory.conjunction().add(entityVetokey).add(entityVetoclass);
        IPredicate refVetoNull = PredicateFactory.isNull((IMetafieldId) IServicesField.VETO_REFERENCE_ID);
        IPredicate refFlagNull = PredicateFactory.isNull((IMetafieldId) OuterQueryMetafieldId.valueOf((IMetafieldId) IServicesField.FLAG_REFERENCE_ID));
        Conjunction bothNull = (Conjunction) PredicateFactory.conjunction().add(refVetoNull).add(refFlagNull);
        IPredicate bothSame = PredicateFactory.eqProperty((IMetafieldId) IServicesField.VETO_REFERENCE_ID, (IMetafieldId) OuterQueryMetafieldId.valueOf((IMetafieldId) IServicesField.FLAG_REFERENCE_ID));
        Disjunction eitherBothNullOrTheSame = (Disjunction) PredicateFactory.disjunction().add((IPredicate)bothNull).add(bothSame);
        boolean isReferenceUnique = this.getFlgtypIsFlagReferenceIdUnique();
        if (isReferenceUnique) {
            entityVeto.add((IPredicate)eitherBothNullOrTheSame);
        }
        if (inServiceable != null && inReferencedGuardianEntity == null) {
            IPredicate serviceableVetokey = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_PRIMARY_KEY, (Object)inServiceable.getPrimaryKey());
            IPredicate serviceableVetoclass = PredicateFactory.eq((IMetafieldId) IServicesField.VETO_APPLIED_TO_CLASS, (Object)inServiceable.getLogicalEntityType());
            Conjunction serviceableVeto = (Conjunction) PredicateFactory.conjunction().add(serviceableVetokey).add(serviceableVetoclass);
            if (isReferenceUnique) {
                serviceableVeto.add((IPredicate)eitherBothNullOrTheSame);
            }
            Disjunction orPred = (Disjunction) PredicateFactory.disjunction().add((IPredicate)entityVeto).add((IPredicate)serviceableVeto);
            subQuery.addDqPredicate((IPredicate)orPred);
        } else {
            subQuery.addDqPredicate((IPredicate)entityVeto);
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Flag").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_FLAG_TYPE, (Object)this.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_APPLIED_TO_CLASS, (Object)inLogicalEntity.getLogicalEntityType())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_APPLIED_TO_PRIMARY_KEY, (Object)inLogicalEntity.getPrimaryKey())).addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.subQueryExists((IDomainQuery)subQuery))).addDqOrdering(Ordering.desc((IMetafieldId) IServicesField.FLAG_APPLIED_DATE));
        if (inReferencedGuardianEntity != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_GUARDIAN_CLASS, (Object)inReferencedGuardianEntity.getLogicalEntityType()));
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_GUARDIAN_PRIMARY_KEY, (Object)inReferencedGuardianEntity.getPrimaryKey()));
        } else {
            dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId) IServicesField.FLAG_GUARDIAN_CLASS));
        }
        Boolean isMultipleAllowed = this.getFlgtypIsMultipleAllowed();
        isMultipleAllowed = isMultipleAllowed != null && isMultipleAllowed != false;
        if (!this.isDataSourceFromGate() && (inCheckReferenceId && isReferenceUnique || isMultipleAllowed.booleanValue())) {
            if (StringUtils.isEmpty((String)inReferenceId)) {
                dq.addDqPredicate(PredicateFactory.isNull((IMetafieldId) IServicesField.FLAG_REFERENCE_ID));
            } else {
                dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_REFERENCE_ID, (Object)inReferenceId));
            }
        }
        List flags = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return flags;
    }

    public IMetafieldId getScopeFieldId() {
        return IServicesField.FLGTYP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IServicesField.FLGTYP_ID;
    }

    public static FlagType findFlagType(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FlagType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLGTYP_ID, (Object)inId));
        return (FlagType) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        FieldChange fieldChange;
        Boolean flgtypIsCovertHold;
        LogicalEntityEnum flgtypAppliesTo;
        FieldChange appliesToChanged;
        BizViolation bizViolation = super.validateChanges(inChanges);
        if (this.getFlgtypGkey() != null && (appliesToChanged = inChanges.getFieldChange(IServicesField.FLGTYP_APPLIES_TO)) != null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object)this.getFlgtypGkey()));
            List serviceRules = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
            if (!serviceRules.isEmpty()) {
                String conflictingServiceRules = "";
                for (Object serviceRule1 : serviceRules) {
                    ServiceRule serviceRule = (ServiceRule)serviceRule1;
                    conflictingServiceRules = serviceRule.getSrvrulName() + "; ";
                }
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE_WITH_DIFF_APPLIES_TO_ENTITY, (BizViolation)bizViolation, (Object)this.getFlgtypId(), (Object)conflictingServiceRules);
            }
        }
        if ((flgtypAppliesTo = this.getFlgtypAppliesTo()) == null) {
            bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_REQUIRED_FIELD_APPLIES_TO_MISSING, (BizViolation)bizViolation);
        }
        if ((flgtypIsCovertHold = this.getFlgtypIsCovertHoldRequired()) != null && flgtypIsCovertHold.booleanValue() && !flgtypAppliesTo.equals((Object) LogicalEntityEnum.UNIT)) {
            bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_COVERT_ONLY_APPLIES_TO_UNIT, (BizViolation)bizViolation);
        }
        if ((fieldChange = inChanges.getFieldChange(IServicesField.FLGTYP_LIFE_CYCLE_STATE)) != null && LifeCycleStateEnum.OBSOLETE.equals(fieldChange.getNewValue())) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object)this.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
            List serviceRules = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
            if (!serviceRules.isEmpty()) {
                StringBuilder conflictingServiceRules = new StringBuilder();
                for (Object serviceRule : serviceRules) {
                    ServiceRule rule = (ServiceRule)serviceRule;
                    conflictingServiceRules.append(rule.getSrvrulName());
                    conflictingServiceRules.append("; ");
                }
                bizViolation = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE, (BizViolation)bizViolation, (Object)this.getFlgtypId(), (Object)conflictingServiceRules.toString());
            }
        }
        return bizViolation;
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ServiceRule").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_FLAG_TYPE, (Object)this.getFlgtypGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.SRVRUL_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE));
        List serviceRules = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (!serviceRules.isEmpty()) {
            StringBuilder conflictingServiceRules = new StringBuilder();
            for (Object serviceRule : serviceRules) {
                ServiceRule rule = (ServiceRule)serviceRule;
                conflictingServiceRules.append(rule.getSrvrulName());
                conflictingServiceRules.append("; ");
            }
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_HAS_REFERENCING_SERVICE_RULE, (BizViolation)bv, (Object)this.getFlgtypId(), (Object)conflictingServiceRules.toString());
        }
        dq = QueryUtils.createDomainQuery((String)"Flag").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLAG_FLAG_TYPE, (Object)this.getFlgtypGkey()));
        Serializable[] eventGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (eventGkeys != null && eventGkeys.length != 0) {
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.FLAG_TYPE_HAS_REFERENCING_FLAGS, (BizViolation)bv, (Object)this.getFlgtypId());
        }
        return bv;
    }

    public FlagPurposeEnum getPurpose() {
        return this.getFlgtypPurpose();
    }

    public LogicalEntityEnum getAppliesTo() {
        return this.getFlgtypAppliesTo();
    }

    public String toString() {
        return "FlagType:" + this.getFlgtypId();
    }

    public String getId() {
        return this.getFlgtypId();
    }

    public String getDescription() {
        return this.getFlgtypDescription();
    }

    public boolean isFlagReferenceIdUnique() {
        return this.getFlgtypIsFlagReferenceIdUnique();
    }

    public boolean isFlagMultipleAllowed() {
        return this.getFlgtypIsMultipleAllowed() != null && this.getFlgtypIsMultipleAllowed() != false;
    }

    public boolean isFlagReferenceIdRequired() {
        return this.getFlgtypIsFlagReferenceIdRequired() != null ? this.getFlgtypIsFlagReferenceIdRequired() : false;
    }

    public boolean isCovertHoldRequired() {
        return this.getFlgtypIsCovertHoldRequired() != null ? this.getFlgtypIsCovertHoldRequired() : false;
    }

    public boolean isFlagActionValid(FlagActionEnum inActionEnum) {
        boolean permissionAction;
        FlagPurposeEnum purpose = this.getPurpose();
        boolean holdAction = FlagActionEnum.ADD_HOLD.equals((Object)inActionEnum) || FlagActionEnum.RELEASE_HOLD.equals((Object)inActionEnum);
        boolean bl = permissionAction = FlagActionEnum.GRANT_PERMISSION.equals((Object)inActionEnum) || FlagActionEnum.CANCEL_PERMISSION.equals((Object)inActionEnum);
        if (FlagPurposeEnum.PERMISSION.equals((Object)purpose) && holdAction || FlagPurposeEnum.HOLD.equals((Object)purpose) && permissionAction) {
            LOGGER.warn((Object)("HoldsPermissionsWebserviceManager: Invalid combination of action " + (Object)inActionEnum + " with the flag " + this.getId() + " of type " + (Object)purpose));
            return false;
        }
        return true;
    }

    @Nullable
    public String getHpvId() {
      //  return this.getFlgtypHoldPermView() != null ? this.getFlgtypHoldPermView().getHpvId() : null;
        return null;
    }

    public boolean isDataSourceFromGate() {
        DataSourceEnum threadDataSource = ContextHelper.getThreadDataSource();
        return DataSourceEnum.IN_GATE.equals((Object)threadDataSource) || DataSourceEnum.GATE_CLERK.equals((Object)threadDataSource) || DataSourceEnum.AUTO_GATE.equals((Object)threadDataSource);
    }

}
