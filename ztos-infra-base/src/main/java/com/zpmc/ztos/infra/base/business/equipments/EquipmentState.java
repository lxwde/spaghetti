package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EquipmentStateDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitEquipmentUseModeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.PropertySource;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EquipmentState extends EquipmentStateDO {
    private static final Logger LOGGER = Logger.getLogger(EquipmentState.class);
    private static final BizFieldList BIZ_FIELDS = new BizFieldList();

    public EquipmentState() {
        this.setEqsEqUseMode(UnitEquipmentUseModeEnum.UNKNOWN);
        this.setEqsLastPosLocType(LocTypeEnum.UNKNOWN);
    }

    public static EquipmentState findEquipmentState(Equipment inEq, Operator inOpr) {
        if (inEq == null) {
            throw BizFailure.create((String)"null EQ passed to findEquipmentState()");
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipmentState").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.EQS_EQUIPMENT, (Object)inEq.getEqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.EQS_OPERATOR, (Object)inOpr.getOprGkey()));
        dq.setBypassInstanceSecurity(true);
        IEntity eqs = HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return (EquipmentState)eqs;
    }

    public static EquipmentState findOrCreateEquipmentState(Equipment inEquipment, Operator inOperator) {
        return EquipmentState.findOrCreateEquipmentState(inEquipment, inOperator, null);
    }

    public static EquipmentState findOrCreateEquipmentState(Equipment inEquipment, Operator inOperator, ScopedBizUnit inLineOperator) {
        EquipmentState eqs;
        if (inEquipment.getEqGkey() == null) {
            HibernateApi.getInstance().save((Object)inEquipment);
        }
        if ((eqs = EquipmentState.findEquipmentState(inEquipment, inOperator)) == null) {
            eqs = EquipmentState.createEquipmentState(inEquipment, inOperator, inLineOperator);
        }
        return eqs;
    }

    private static EquipmentState createEquipmentState(Equipment inEquipment, Operator inOperator, ScopedBizUnit inLineOperator) {
        ScopedBizUnit operator = null;
        EquipmentState eqs = new EquipmentState();
        eqs.setEqsEquipment(inEquipment);
        eqs.setEqsOperator(inOperator);
        MasterBizUnit masterOperator = inEquipment.getEqObsoleteOperator();
//        Object object = masterOperator == null ? (inLineOperator == null ? LineOperator.getUnknownLineOperator() : inLineOperator) : (operator = masterOperator.findScopedBizUnitProxy());
//        if (operator == null) {
//            operator = LineOperator.getUnknownLineOperatorProxy();
//        }
        eqs.setEqsEqOperator(operator);
        MasterBizUnit masterOwner = inEquipment.getEqObsoleteOwner();
//        ScopedBizUnit owner = masterOwner == null ? (inLineOperator == null ? LineOperator.getUnknownLineOperator() : inLineOperator) : masterOwner.findScopedBizUnitProxy();
//        eqs.setEqsEqOwner(owner);
        HibernateApi.getInstance().save((Object)eqs);
        return eqs;
    }

    public static EquipmentState findOrCreateEquipmentStateProxy(Equipment inEquipment, Operator inOperator, ScopedBizUnit inLineOperator) {
        UserContext userContext;
        EquipmentState eqs;
        if (inEquipment.getEqGkey() == null) {
            HibernateApi.getInstance().save((Object)inEquipment);
        }
        if ((eqs = EquipmentState.findEquipmentStateProxy(inEquipment, inOperator)) == null) {
            return EquipmentState.createEquipmentState(inEquipment, inOperator, inLineOperator);
        }
        if (inLineOperator != null && (userContext = ContextHelper.getThreadUserContext()) != null && InventoryConfig.SECURITY_ENFORCE_EQ_STATE_MODIFY.isOn(userContext)) {
            IPoolManager poolMgr = (IPoolManager) Roastery.getBean((String)"poolManager");
            try {
                poolMgr.eqUseAllowed(ContextHelper.getThreadComplex(), inLineOperator, null, eqs.getEqsEquipment());
            }
            catch (BizViolation bv) {
                MessageCollectorUtils.appendExceptionChain((BizViolation)bv);
            }
        }
        return eqs;
    }

    public static EquipmentState findEquipmentStateProxy(Equipment inEq, Operator inOpr) {
        if (inEq == null) {
            throw BizFailure.create((String)"null EQ passed to findEquipmentState()");
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipmentState").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.EQS_EQUIPMENT, (Object)inEq.getEqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.EQS_OPERATOR, (Object)inOpr.getOprGkey()));
        dq.setBypassInstanceSecurity(true);
        Serializable[] eqsGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (eqsGkey == null || eqsGkey.length == 0) {
            return null;
        }
        if (eqsGkey.length == 1) {
            return (EquipmentState)HibernateApi.getInstance().load(EquipmentState.class, eqsGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(eqsGkey.length), (Object)dq);
    }

    public static EquipmentState createEquipmentStateFromUe(UnitEquipment inUe) {
        Unit unit = inUe.getUeUnit();
        Operator opr = unit.getUnitComplex().getCpxOperator();
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentState(inUe.getUeEquipment(), opr);
        UnitFacilityVisit ufv = unit.getUnitActiveUfv();
        LocPosition pos = ufv != null ? ufv.getUfvLastKnownPosition() : LocPosition.getUnknownPosition();
        eqs.setEqsLastPosLocType(pos.getPosLocType());
        eqs.setEqsLastPosName(pos.getPosName());
        Date t = ufv != null ? ufv.getUfvTimeOfLastMove() : null;
        eqs.setEqsTimeLastMove(t);
        eqs.setEqsEqUseMode(inUe.getUeObsoleteEqUseMode());
        eqs.setEqsGradeID(inUe.getUeObsoleteGradeID());
        eqs.setEqsOffhireLocation(inUe.getUeObsoleteReturnToLocation());
        inUe.setUeEquipmentState(eqs);
        return eqs;
    }

    public static void upgradeEqOperator(Equipment inEquipment, ScopedBizUnit inEqOperator, DataSourceEnum inDataSourceEnum) {
        if (inEqOperator == null) {
            return;
        }
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), inEqOperator);
        eqs.upgradeEquipmentOperator(inEqOperator, inDataSourceEnum);
    }

    public static void upgradeEqGrade(Equipment inEquipment, EquipGrade inEquipGrade) {
        if (inEquipGrade == null) {
            return;
        }
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), null);
        eqs.updateGradeID(inEquipGrade);
    }

    public void updateGradeID(EquipGrade inGradeID) {
        this.setEqsGradeID(inGradeID);
    }

    public static void upgradeEqOwner(Equipment inEquipment, ScopedBizUnit inEqOwner, DataSourceEnum inDataSourceEnum) {
        if (inEqOwner == null) {
            return;
        }
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), inEqOwner);
        eqs.upgradeEquipmentOwner(inEqOwner, inDataSourceEnum);
    }

    public static void upgradeOffhireLocation(Equipment inEquipment, String inOffhireLocation, DataSourceEnum inThreadDataSource) {
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), null);
        eqs.updateOffhireLocation(inOffhireLocation);
    }

    public static void upgradeGrade(Equipment inEquipment, EquipGrade inGrade, DataSourceEnum inThreadDataSource) {
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), null);
        eqs.setEqsGradeID(inGrade);
    }

    public void setTripCount(Long inTripCount) {
        if (inTripCount == null) {
            return;
        }
        this.setEqsTripCount(inTripCount);
    }

    public static ScopedBizUnit getEqOperator(Equipment inEquipment) {
        EquipmentState eqs = EquipmentState.findEquipmentState(inEquipment, ContextHelper.getThreadOperator());
        return eqs == null ? LineOperator.getUnknownLineOperator() : eqs.getEqsEqOperator();
    }

    public static ScopedBizUnit getEqOwner(Equipment inEquipment) {
        EquipmentState eqs = EquipmentState.findEquipmentState(inEquipment, ContextHelper.getThreadOperator());
        return eqs == null ? LineOperator.getUnknownLineOperator() : eqs.getEqsEqOwner();
    }

    public void incrementTripCount() {
        Long tripCount = this.getEqsTripCount();
        if (tripCount == null) {
            tripCount = Long.parseLong("1");
        } else {
            Long l = tripCount;
            Long l2 = tripCount = Long.valueOf(tripCount + 1L);
        }
        this.setEqsTripCount(tripCount);
    }

    public void decrementTripCount() {
        Long tripCount = this.getEqsTripCount();
        if (tripCount != null && tripCount > 0L) {
            Long l = tripCount;
            Long l2 = tripCount = Long.valueOf(tripCount - 1L);
            this.setEqsTripCount(tripCount);
        }
    }

    public String getEqsHoldOrPermName() {
        StringBuffer buf = new StringBuffer();
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        String[] flagIds = sm.getActiveFlagIds((ILogicalEntity)this);
        this.addFlagIds(flagIds, buf);
        return buf.toString();
    }

    public static IPredicate formEqsHoldOrPermNamePredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        IPredicate predicate = sm.createActiveFlagPredicate(inValue, LogicalEntityEnum.EQ, UnitField.UNIT_EQS_GKEY);
        if (PredicateVerbEnum.NE.equals((Object)inVerb) || PredicateVerbEnum.NULL.equals((Object)inVerb)) {
            return PredicateFactory.not((IPredicate)predicate);
        }
        return predicate;
    }

    protected UnitEquipDamageItem addDamageItem(EquipDamageType inDamageType, EqComponent inDmgComponent, EqDamageSeverityEnum inDmgSeverity, Date inDmgReported, Date inDmgRepaired) throws BizViolation {
        UnitEquipDamageItem damageItem = null;
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            damageItem = ue.addDamageItem(inDamageType, inDmgComponent, inDmgSeverity, inDmgReported, inDmgRepaired);
        }
        return damageItem;
    }

    protected UnitEquipDamageItem addDamageItem(UnitEquipDamageItem inDamageItem) throws BizViolation {
        UnitEquipDamageItem damageItem = null;
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            damageItem = ue.addDamageItem(inDamageItem);
        }
        return damageItem;
    }

    public String toString() {
        Equipment eq = this.getEqsEquipment();
        String eqId = eq != null ? eq.getEqIdFull() : "??";
        return "EQS[" + eqId + ':' + this.getEqsGkey() + ']';
    }

    public LogicalEntityEnum getLogicalEntityType() {
        return LogicalEntityEnum.EQ;
    }

    public String getLogEntityId() {
        Equipment eq = this.getEqsEquipment();
        return eq.getEqIdFull();
    }

    public String getHumanReadableKey() {
        Equipment eq = this.getEqsEquipment();
        return eq.getEqIdFull();
    }

    public String getLogEntityParentId() {
        return this.getLogEntityId();
    }

    public List getSupportedBathToGuardians() {
        ArrayList<IMetafieldId> paths = new ArrayList<IMetafieldId>();
        paths.add(IInventoryBizMetafield.UNIT_EQUIPMENT_STATES);
        Equipment equipment = this.getEqsEquipment();
        EquipClassEnum equipClassEnum = equipment.getEqClass();
        if (EquipClassEnum.ACCESSORY.equals((Object)equipClassEnum)) {
            paths.add(IInventoryBizMetafield.UNIT_ACC_EQUIPMENT_STATES);
        } else if (EquipClassEnum.CHASSIS.equals((Object)equipClassEnum)) {
            paths.add(IInventoryBizMetafield.UNIT_CHS_EQUIPMENT_STATES);
        } else if (EquipClassEnum.CONTAINER.equals((Object)equipClassEnum)) {
            paths.add(IInventoryBizMetafield.UNIT_CTR_EQUIPMENT_STATES);
        }
        return paths;
    }

    public boolean isCorrectEntityType(LogicalEntityEnum inExpectedType) {
        return LogicalEntityEnum.EQ.equals((Object)inExpectedType);
    }

    public void calculateFlags() {
        UnitEquipment ue = this.getAttachedUe();
        if (ue == null) {
            return;
        }
        Unit unit = ue.getUeUnit();
        unit.updateDenormalizedFields();
    }

    public BizViolation verifyApplyFlagToEntityAllowed() {
        return null;
    }

    public Complex getLogEntityComplex() {
        return null;
    }

    public Object getEqsDamageVao() {
        UnitEquipment ue = this.getAttachedUe();
        return ue == null ? null : ue.getUeDamageVao();
    }

    public void setEqsDamageVao(Object inValue) {
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            ue.setUeDamageVao(inValue);
        }
    }

    public BizFieldList getBizFieldList() {
        return BIZ_FIELDS;
    }

    public void attachDamages(UnitEquipDamages inNewDamages, boolean inDeleteOldDamages) throws BizViolation {
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            ue.attachDamages(inNewDamages);
        }
    }

    private void recordUnitEvent(EventEnum inEventType, FieldChanges inChanges, String inNotes) {
        IUnitFinder uf = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        List ueList = uf.findNotDetachedUnitEquipment(this.getEqsOperator(), ContextHelper.getThreadComplex(), this.getEqsEquipment(), false);
        if (ueList == null || ueList.isEmpty()) {
            return;
        }
        Unit recentUnit = null;
        for (Object ue : ueList) {
            Unit unit = ((UnitEquipment)ue).getUeUnit();
            if (UnitVisitStateEnum.ACTIVE == unit.getUnitVisitState()) {
                recentUnit = unit;
                break;
            }
            if (UnitVisitStateEnum.ADVISED == unit.getUnitVisitState()) {
                recentUnit = unit;
            }
            if (recentUnit != null || UnitVisitStateEnum.DEPARTED != unit.getUnitVisitState()) continue;
            recentUnit = unit;
        }
        if (recentUnit != null) {
            recentUnit.recordUnitEvent((IEventType)inEventType, inChanges, inNotes);
        }
    }

    private UnitEquipment getAttachedUe() {
        IUnitFinder uf = (IUnitFinder)Roastery.getBean((String)"unitFinder");
        Unit unit = uf.findAttachedUnit(ContextHelper.getThreadComplex(), this.getEqsEquipment());
        if (unit == null) {
            return null;
        }
        return unit.getCurrentlyAttachedUe(this.getEqsEquipment());
    }

    public Serializable getEqsUnitEquipmentTableKey() {
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            return ue.getUeGkey();
        }
        return null;
    }

    public UnitEquipDamages getEqsDamages() {
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            return ue.getUeDamages();
        }
        return null;
    }

    public EqDamageSeverityEnum getEqsDamageSeverity() {
        UnitEquipment ue = this.getAttachedUe();
        if (ue != null) {
            return ue.getUeDamageSeverity();
        }
        return null;
    }

    public String getEqsDmgsOwnerEntityName() {
        UnitEquipDamages damages = this.getEqsDamages();
        if (damages != null) {
            return damages.getDmgsOwnerEntityName();
        }
        return null;
    }

    private void addFlagIds(String[] inFlagIds, StringBuffer inBuf) {
        for (int i = 0; i < inFlagIds.length; ++i) {
            if (inBuf.length() > 0) {
                inBuf.append(',');
            }
            inBuf.append(inFlagIds[i]);
        }
    }

    public void upgradeEquipmentOperator(ScopedBizUnit inOperator, DataSourceEnum inDataSource) {
        if (inOperator != null) {
            boolean isAllowUpdateChangerChanged = false;
            ScopedBizUnit existingOperator = this.getEqsEqOperator();
            if (existingOperator == null) {
                this.setEqsEqOperator(inOperator);
                if (this.getEqsEqOwner() == null) {
                    this.setEqsEqOwner(inOperator);
                }
                isAllowUpdateChangerChanged = true;
            } else if (!inOperator.equals((Object)existingOperator)) {
                if (LineOperator.isUnknownLineOperator((ScopedBizUnit)existingOperator)) {
                    this.setEqsEqPreviousOperator(existingOperator);
                    this.setEqsEqOperator(inOperator);
                    if (LineOperator.isUnknownLineOperator((ScopedBizUnit)this.getEqsEqOwner())) {
                        this.setEqsEqOwner(inOperator);
                    }
                    isAllowUpdateChangerChanged = true;
                } else {
                    PropertySource propertySource = PropertySource.findPropertySource((String)"EquipmentState", (Serializable)this.getEqsGkey(), (PropertyGroupEnum)PropertyGroupEnum.CTR_OPERATOR);
                    DataSourceEnum existingDataSource = propertySource == null ? DataSourceEnum.UNKNOWN : propertySource.getPrpsrcDataSource();
                    String eqId = this.getEqsEquipment().getEqIdFull();
                    if (this.getEqsEquipment().shouldAcceptUpdate(inDataSource, existingDataSource, PropertyGroupEnum.CTR_OPERATOR)) {
                        IMessageCollector mc;
                        this.setEqsEqPreviousOperator(existingOperator);
                        this.setEqsEqOperator(inOperator);
                        if (existingOperator.equals((Object)this.getEqsEqOwner())) {
                            this.setEqsEqOwner(inOperator);
                        }
                        isAllowUpdateChangerChanged = true;
                        LOGGER.warn((Object)("upgradeOperator: upgraded existing Equipment " + eqId + " from <" + (Object)existingOperator + ">, to <" + (Object)inOperator + ">"));
                        if (inDataSource.equals((Object)DataSourceEnum.EDI_STOW) && (mc = MessageCollectorUtils.getMessageCollector()) != null) {
                            mc.appendMessage(MessageLevelEnum.WARNING, IArgoPropertyKeys.WARNING_EQOPR_UPDATED, null, new Object[]{eqId, existingOperator.getBzuId(), inOperator.getBzuId()});
                        }
                    } else {
                        LOGGER.warn((Object)("upgradeEqType: discrepant eqType for " + eqId + " in DB: <" + (Object)existingOperator + ">, input: <" + (Object)inOperator + ">" + " not updated as existing datasource <" + (Object)existingDataSource + ">, is of higher quality than input datasource <" + (Object)inDataSource + ">"));
                        IMessageCollector mc = MessageCollectorUtils.getMessageCollector();
                        if (mc != null) {
//                            mc.appendMessage(MessageLevelEnum.WARNING, IArgoPropertyKeys.WARNING_OPERATOR_NOT_UPDATED, null, new Object[]{eqId, existingOperator.getBzuId(), inOperator.getBzuId(), existingDataSource.getName(), inDataSource.getName()});
                        }
                    }
                }
            }
            this.updateEquipmentChangerChanged(isAllowUpdateChangerChanged);
        }
    }

    private void updateEquipmentChangerChanged(boolean inIsAllowUpdateChangerChanged) {
        if (inIsAllowUpdateChangerChanged) {
            String eqChanger = ContextHelper.getThreadUserContext() != null ? ContextHelper.getThreadUserId() : null;
            this.getEqsEquipment().setFieldValue(IArgoRefField.EQ_CHANGED, (Object)new Date());
            this.getEqsEquipment().setFieldValue(IArgoRefField.EQ_CHANGER, (Object)eqChanger);
        }
    }

    public static void upgradeFlexField(Equipment inEquipment, int inWhichFlexField, String inFlexValue) {
        EquipmentState eqs = EquipmentState.findOrCreateEquipmentStateProxy(inEquipment, ContextHelper.getThreadOperator(), null);
        eqs.upgradeFlexField(inWhichFlexField, inFlexValue);
    }

    public void updateOffhireLocation(String inOffhireLocation) {
        this.setEqsOffhireLocation(inOffhireLocation);
    }

    public void upgradeFlexField(int inWhichFlexField, String inFlexValue) {
        switch (inWhichFlexField) {
            case 1: {
                this.setEqsFlexString01(inFlexValue);
                break;
            }
            case 2: {
                this.setEqsFlexString02(inFlexValue);
                break;
            }
            case 3: {
                this.setEqsFlexString03(inFlexValue);
                break;
            }
            default: {
                throw BizFailure.create((String)("bad value for inWhichFlexField: " + inWhichFlexField));
            }
        }
    }

    private void recordOperatorChangeEvent(ScopedBizUnit inExistingOperator, ScopedBizUnit inOperator) {
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(IInventoryField.EQS_EQ_OPERATOR, (Object)inExistingOperator, (Object)inOperator);
        ITranslationContext translationContext = TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext());
        String eventNote = translationContext.getMessageTranslator().getMessage(IInventoryPropertyKeys.UNIT_OPERATOR_CHANGE, (Object[])new String[]{inExistingOperator.getBzuId(), inOperator.getBzuId()});
        this.recordUnitEvent(EventEnum.UNIT_OPERATOR_CHANGE, changes, eventNote);
        LOGGER.warn((Object)(eventNote + " in the Unit " + this.getEqsEquipment().getEqIdFull() + " by user:" + ContextHelper.getThreadUserId()));
    }

    public void upgradeEquipmentOwner(ScopedBizUnit inOwner, DataSourceEnum inDataSource) {
        if (inOwner != null) {
            boolean isAllowUpdateChangerChanged = false;
            ScopedBizUnit existingOwner = this.getEqsEqOwner();
            if (existingOwner == null || LineOperator.isUnknownLineOperator((ScopedBizUnit)existingOwner)) {
                this.setEqsEqOwner(inOwner);
                isAllowUpdateChangerChanged = true;
            } else if (!inOwner.equals((Object)existingOwner)) {
                PropertySource propertySource = PropertySource.findPropertySource((String)"EquipmentState", (Serializable)this.getEqsGkey(), (PropertyGroupEnum)PropertyGroupEnum.CTR_OPERATOR);
                DataSourceEnum existingDataSource = propertySource == null ? DataSourceEnum.UNKNOWN : propertySource.getPrpsrcDataSource();
                String eqId = this.getEqsEquipment().getEqIdFull();
                if (this.getEqsEquipment().shouldAcceptUpdate(inDataSource, existingDataSource, PropertyGroupEnum.CTR_OPERATOR)) {
                    this.setEqsEqOwner(inOwner);
                    isAllowUpdateChangerChanged = true;
                    LOGGER.warn((Object)("upgradeOwner: upgraded existing Equipment " + eqId + " from <" + (Object)existingOwner + ">, to <" + (Object)inOwner + ">"));
                } else {
                    LOGGER.warn((Object)("upgradeEqType: discrepant eqType for " + eqId + " in DB: <" + (Object)existingOwner + ">, input: <" + (Object)inOwner + ">" + " not updated as existing datasource <" + (Object)existingDataSource + ">, is of higher quality than input datasource <" + (Object)inDataSource + ">"));
                    IMessageCollector mc = MessageCollectorUtils.getMessageCollector();
                    if (mc != null) {
//                        mc.appendMessage(MessageLevelEnum.WARNING, IArgoPropertyKeys.WARNING_OWNER_NOT_UPDATED, null, new Object[]{eqId, existingOwner.getBzuId(), inOwner.getBzuId(), existingDataSource.getName(), inDataSource.getName()});
                    }
                }
            }
            this.updateEquipmentChangerChanged(isAllowUpdateChangerChanged);
        }
    }

    protected static void restoreEquipmentState(UnitEquipment inRenumberedUe, UnitEquipment inPreviousUe) {
        UnitFacilityVisit recentUfv;
        EquipmentState es = inRenumberedUe.getUeEquipmentState();
        if (!es.equals(inPreviousUe.getUeEquipmentState())) {
            return;
        }
        Operator prevUseOperator = inPreviousUe.getUeUnit().getUnitComplex().getCpxOperator();
        Operator currUseOperator = inRenumberedUe.getUeUnit().getUnitComplex().getCpxOperator();
        if (!currUseOperator.equals((Object)prevUseOperator)) {
            return;
        }
        if (inRenumberedUe.getUeEquipment().equals((Object)inPreviousUe.getUeEquipment())) {
            return;
        }
        EquipmentState newEs = EquipmentState.findOrCreateEquipmentState(inRenumberedUe.getUeEquipment(), es.getEqsOperator());
        inRenumberedUe.setUeEquipmentState(newEs);
        LocPosition pos = inPreviousUe.getUeUnit().findCurrentPosition();
        if (LocPosition.getUnknownPosition() == pos && (recentUfv = inPreviousUe.getUeUnit().findMostRecentHistoryUfv()) != null) {
            pos = recentUfv.getUfvLastKnownPosition();
            es.setEqsTimeLastMove(recentUfv.getUfvTimeOfLastMove());
        }
        es.setEqsLastFacility(pos.resolveFacility());
        es.setEqsLastPosLocType(pos.getPosLocType());
        es.setEqsLastPosName(pos.getPosName());
    }

    protected void mergeWith(EquipmentState inEquipmentState) {
        if (inEquipmentState.getEqsTimeLastMove() != null) {
            this.setEqsTimeLastMove(inEquipmentState.getEqsTimeLastMove());
        }
        if (inEquipmentState.getEqsEqUseMode() != null) {
            this.setEqsEqUseMode(inEquipmentState.getEqsEqUseMode());
        }
        if (inEquipmentState.getEqsFlexString01() != null) {
            this.setEqsFlexString01(inEquipmentState.getEqsFlexString01());
        }
        if (inEquipmentState.getEqsFlexString02() != null) {
            this.setEqsFlexString02(inEquipmentState.getEqsFlexString02());
        }
        if (inEquipmentState.getEqsFlexString03() != null) {
            this.setEqsFlexString03(inEquipmentState.getEqsFlexString03());
        }
        if (inEquipmentState.getEqsGradeID() != null) {
            this.setEqsGradeID(inEquipmentState.getEqsGradeID());
        }
        if (inEquipmentState.getEqsOffhireLocation() != null) {
            this.setEqsOffhireLocation(inEquipmentState.getEqsOffhireLocation());
        }
        if (inEquipmentState.getEqsLastFacility() != null) {
            this.setEqsLastFacility(inEquipmentState.getEqsLastFacility());
        }
        if (inEquipmentState.getEqsLastPosLocType() != null) {
            this.setEqsLastPosLocType(inEquipmentState.getEqsLastPosLocType());
        }
        if (inEquipmentState.getEqsLastPosName() != null) {
            this.setEqsLastPosName(inEquipmentState.getEqsLastPosName());
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        ScopedBizUnit previousOpr = null;
        if (inChanges != null && inChanges.hasFieldChange(IInventoryField.EQS_EQ_OPERATOR) && (previousOpr = (ScopedBizUnit)inChanges.getFieldChange(IInventoryField.EQS_EQ_OPERATOR).getPriorValue()) != null) {
            inOutMoreChanges.setFieldChange(IInventoryField.EQS_EQ_PREVIOUS_OPERATOR, (Object)previousOpr);
        }
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (inChanges != null && inChanges.hasFieldChange(IInventoryField.EQS_EQ_OPERATOR) && previousOpr != null && !LineOperator.isUnknownLineOperator((ScopedBizUnit)previousOpr)) {
            this.recordOperatorChangeEvent(previousOpr, this.getEqsEqOperator());
            HibernateApi.getInstance().flush();
        }
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        FieldChanges fcs = inAuditEvent.getFieldChanges();
        if ((fcs.hasFieldChange(IInventoryField.EQS_EQ_OPERATOR) || fcs.hasFieldChange(IInventoryField.EQS_EQ_OWNER)) && !LineOperator.isUnknownLineOperator((ScopedBizUnit)this.getEqsEqOperator())) {
            return inAuditEvent;
        }
        return null;
    }

    public List getGuardians() {
        return Collections.EMPTY_LIST;
    }

    public String getLovKeyNameForPathToGuardian(String inPathToGuardian) {
        return null;
    }

    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        return null;
    }

    public Boolean skipEventRecording() {
        return false;
    }

    public void postEventCreation(IEvent inEventCreated) {
    }

    public boolean onSave(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onUpdate(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onDelete(Session inSession) throws CallbackException {
        IServicesManager sm = (IServicesManager)Roastery.getBean((String)"servicesManager");
        sm.purgeEventAndFlagHistory((ILogicalEntity)this);
        return false;
    }

    public void onLoad(Session inSession, Serializable inSerializableId) {
    }

    static {
        BIZ_FIELDS.add(IInventoryField.EQS_EQ_OPERATOR, BizRoleEnum.LINEOP);
        BIZ_FIELDS.add(IInventoryField.EQS_EQ_OWNER, BizRoleEnum.LINEOP);
    }
}
