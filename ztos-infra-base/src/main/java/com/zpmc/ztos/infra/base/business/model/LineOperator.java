package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.LineOperatorDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.BookingRollCutoffEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.BookingRollEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.BookingUsageEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.EntityMappingPredicate;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.utils.MessageCollectorUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;

public class LineOperator extends LineOperatorDO implements ISecurable{
    private static final Logger LOGGER = Logger.getLogger(LineOperator.class);

    public LineOperator() {
        this.setLineopBkgRoll(BookingRollEnum.NO);
        this.setLineopBkgRollCutoff(BookingRollCutoffEnum.ALL);
        this.setLineopBkgAdhoc(Boolean.FALSE);
        this.setLineopBkgUsage(BookingUsageEnum.OPTIONAL);
        this.setLineopBkgUnique(Boolean.TRUE);
        this.setLineopBlUnique(Boolean.TRUE);
        this.setLineopGenPinNbr(Boolean.FALSE);
        this.setLineopUsePinNbr(Boolean.FALSE);
        this.setLineopOrderItemNotUnique(Boolean.FALSE);
        this.setLineopIsOrderNbrUnique(Boolean.FALSE);
        this.setLineopRollLateCtr(Boolean.FALSE);
        this.setBzuRole(BizRoleEnum.LINEOP);
        this.setBzuIsEqOperator(Boolean.TRUE);
        this.setBzuIsEqOwner(Boolean.TRUE);
        this.setLineopStopBkgUpdates(Boolean.FALSE);
        this.setLineopStopRtgUpdates(Boolean.FALSE);
    }

    public static LineOperator findLineOperatorById(String inLineId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"LineOperator").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inLineId));
        return (LineOperator) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public BookingRollCutoffEnum ensureLineopBkgRollCutoff() {
        return this.getLineopBkgRollCutoff() != null ? this.getLineopBkgRollCutoff() : BookingRollCutoffEnum.ALL;
    }

    public Boolean isLineopBkgNotValidated() {
        return this.getLineopBkgIsNotValidated() != null && this.getLineopBkgIsNotValidated() != false;
    }

    public static LineOperator findOrCreateLineOperator(String inLineId) {
        LineOperator line = LineOperator.findLineOperatorById(inLineId);
        if (line == null) {
            line = LineOperator.createLineOperator(inLineId);
        }
        return line;
    }

    public static LineOperator findOrCreateLineOperatorProxy(String inLineId) {
        LineOperator line = LineOperator.findLineOperatorProxyById(inLineId);
        if (line == null) {
            line = LineOperator.createLineOperator(inLineId);
        }
        return line;
    }

    public static LineOperator findLineOperatorProxyById(String inLineId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"LineOperator").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inLineId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object)BizRoleEnum.LINEOP)));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        if (bzuGkey.length == 1) {
            return (LineOperator)HibernateApi.getInstance().load(LineOperator.class, bzuGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(bzuGkey.length), (Object)dq);
    }

    public static LineOperator createLineOperator(String inLineId) {
        LineOperator line = new LineOperator();
        line.setBzuId(inLineId);
        line.initializeDefaultProperties();
        HibernateApi.getInstance().save((Object)line);
        return line;
    }

    public Object getLineopRepresentationTableKey() {
        return this.getBzuGkey();
    }

    public Object getLineopTruckingCompanyTableKey() {
        return this.getBzuGkey();
    }

    @Override
    public Object getRepAgentTableKey() {
        return this.getBzuGkey();
    }

    public Object getPodruleTableKey() {
        return this.getBzuGkey();
    }

    public boolean isActive() {
        return this.getBzuLifeCycleState().getName().compareTo(LifeCycleStateEnum.ACTIVE.getName()) == 0;
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        if (!inFieldChanges.hasFieldChange(IArgoRefField.BZU_IS_EQ_OPERATOR)) {
            inFieldChanges.setFieldChange(IArgoRefField.BZU_IS_EQ_OPERATOR, (Object) Boolean.TRUE);
        }
        if (!inFieldChanges.hasFieldChange(IArgoRefField.BZU_IS_EQ_OWNER)) {
            inFieldChanges.setFieldChange(IArgoRefField.BZU_IS_EQ_OWNER, (Object) Boolean.TRUE);
        }
        super.applyFieldChanges(inFieldChanges);
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation violations = super.validateChanges(inChanges);
        if (this.getPrimaryKey() != null) {
            if (this.isPropagateChangesChecked()) {
                if (!this.canPropagateMonitorTimes()) {
                    MessageCollectorUtils.getMessageCollector().appendMessage(MessageLevelEnum.WARNING, IArgoPropertyKeys.UNABLE_PROPAGATE_MONITOR_TIMES, null, null);
                }
                if (!this.canPropagateOffPowerTimeLimit()) {
                    MessageCollectorUtils.getMessageCollector().appendMessage(MessageLevelEnum.WARNING, IArgoPropertyKeys.UNABLE_PROPAGATE_OFF_POWER_LIMIT_TIME, null, null);
                }
            }
            if (!this.isMonitorTimesInOrder()) {
                MessageCollectorUtils.getMessageCollector().appendMessage(MessageLevelEnum.SEVERE, IArgoPropertyKeys.MONITOR_TIMES_NOT_CHRONOLOGICAL_ORDER, null, null);
            }
        }
        return violations;
    }

    private boolean isMonitorTimesInOrder() {
        Date monitor1 = this.getLineopReeferTimeMonitor1();
        Date monitor2 = this.getLineopReeferTimeMonitor2();
        Date monitor3 = this.getLineopReeferTimeMonitor3();
        Date monitor4 = this.getLineopReeferTimeMonitor4();
        Date tempDate = null;
        if (monitor1 != null) {
            tempDate = monitor1;
        }
        if (monitor2 != null) {
            if (tempDate != null && tempDate.after(monitor2)) {
                return false;
            }
            tempDate = monitor2;
        }
        if (monitor3 != null) {
            if (tempDate != null && tempDate.after(monitor3)) {
                return false;
            }
            tempDate = monitor3;
        }
        if (monitor4 != null) {
            if (tempDate != null && tempDate.after(monitor4)) {
                return false;
            }
            tempDate = monitor4;
        }
        return true;
    }

    public boolean isBookingRequired() {
        boolean required = false;
        if (BookingUsageEnum.REQUIRED.equals((Object)this.getLineopBkgUsage())) {
            required = true;
        }
        return required;
    }

    public boolean canCreateAdHocBookings() {
        boolean canCreate = false;
        if (this.getLineopBkgAdhoc().booleanValue() && (BookingUsageEnum.OPTIONAL.equals((Object)this.getLineopBkgUsage()) || BookingUsageEnum.REQUIRED.equals((Object)this.getLineopBkgUsage()))) {
            canCreate = true;
        }
        return canCreate;
    }

    public boolean isBookingNotUsed() {
        boolean notUsed = false;
        if (BookingUsageEnum.NOTUSED.equals((Object)this.getLineopBkgUsage())) {
            notUsed = true;
        }
        return notUsed;
    }

    public boolean isLateCtrRolled() {
        boolean rolled = false;
        if (this.getLineopRollLateCtr() != null && this.getLineopRollLateCtr().booleanValue()) {
            rolled = true;
        }
        return rolled;
    }

    public IValueHolder getLineStorageRuleDto() {
        EntityMappingPredicate rules = this.getLineopDemurrageRules();
        return rules == null ? null : rules.getEntityMappingVao();
    }

    public void setLineStorageRuleDto(Object inValue) {
        EntityMappingPredicate oldRules = this.getLineopDemurrageRules();
        if (oldRules != null) {
            HibernateApi.getInstance().delete((Object)oldRules);
        }
        EntityMappingPredicate rules = inValue == null ? null : new EntityMappingPredicate((IValueHolder)inValue);
        this.setLineopDemurrageRules(rules);
    }

    public IValueHolder getLinePowerRuleDto() {
        EntityMappingPredicate rules = this.getLineopPowerRules();
        return rules == null ? null : rules.getEntityMappingVao();
    }

    public void setLinePowerRuleDto(Object inValue) {
        EntityMappingPredicate oldRules = this.getLineopPowerRules();
        if (oldRules != null) {
            HibernateApi.getInstance().delete((Object)oldRules);
        }
        EntityMappingPredicate rules = inValue == null ? null : new EntityMappingPredicate((IValueHolder)inValue);
        this.setLineopPowerRules(rules);
    }

    public static LineOperator resolveLineOprFromScopedBizUnit(ScopedBizUnit inScopedBizUnit) {
        LineOperator line = null;
        if (inScopedBizUnit != null && BizRoleEnum.LINEOP.equals((Object)inScopedBizUnit.getBzuRole())) {
            line = (LineOperator)HibernateApi.getInstance().downcast((DatabaseEntity)inScopedBizUnit, LineOperator.class);
        }
        return line;
    }

    public static LineOperator getUnknownLineOperator() {
        String lineOpName = "UNK";
        IBizUnitManager bizUnitManager = (IBizUnitManager) Roastery.getBean((String)"bizUnitManager");
        return (LineOperator)bizUnitManager.findOrCreateScopedBizUnit(lineOpName, BizRoleEnum.LINEOP);
    }

    public static LineOperator getUnknownLineOperatorProxy() {
        String lineOpName = "UNK";
        IBizUnitManager bizUnitManager = (IBizUnitManager)Roastery.getBean((String)"bizUnitManager");
        return (LineOperator)bizUnitManager.findOrCreateScopedBizUnitProxy(lineOpName, BizRoleEnum.LINEOP);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public static boolean isUnknownLineOperator(ScopedBizUnit inOperator) {
        return inOperator == null || "UNK".equals(inOperator.getBzuId());
    }

    public Object getGateRestrictTableKey() {
        return this.getBzuGkey();
    }

    public void updateLineopOrderItemNotUnique(Boolean inLineopOrderItemNotUnique) {
        this.setLineopOrderItemNotUnique(inLineopOrderItemNotUnique);
    }

    public void updateLineopIsOrderNbrUnique(Boolean inLineopIsOrderNbrUnique) {
        this.setLineopIsOrderNbrUnique(inLineopIsOrderNbrUnique);
    }

    public void updateLineopIsEDOUniqueByFacility(Boolean inLineopIsEDOUniqueByFacility) {
        this.setLineopIsEDOUniqueByFacility(inLineopIsEDOUniqueByFacility);
    }

    @Override
    public BizFieldList getBizFieldList() {
        BizFieldList list = new BizFieldList();
        list.add(IArgoRefField.BZU_GKEY, BizRoleEnum.LINEOP);
        return list;
    }

    public String getLineopNotes() {
        return super.getBzuNotes();
    }

    public IValueHolder getLineLineStorageRuleDto() {
        EntityMappingPredicate rules = this.getLineopLineDemurrageRules();
        return rules == null ? null : rules.getEntityMappingVao();
    }

    public void setLineLineStorageRuleDto(Object inValue) {
        EntityMappingPredicate oldRules = this.getLineopLineDemurrageRules();
        if (oldRules != null) {
            HibernateApi.getInstance().delete((Object)oldRules);
        }
        EntityMappingPredicate rules = inValue == null ? null : new EntityMappingPredicate((IValueHolder)inValue);
        this.setLineopLineDemurrageRules(rules);
    }

    protected void setLineopNotes(String inLineopNotes) {
        super.setBzuNotes(inLineopNotes);
    }

    private boolean canPropagateMonitorTimes() {
        return this.getLineopReeferTimeMonitor1() != null || this.getLineopReeferTimeMonitor2() != null || this.getLineopReeferTimeMonitor3() != null || this.getLineopReeferTimeMonitor4() != null;
    }

    private boolean canPropagateOffPowerTimeLimit() {
        return this.getLineopReeferOffPowerTimeLimit() != null;
    }

    public boolean isPropagateAllowed() {
        return this.canPropagateMonitorTimes() && this.canPropagateOffPowerTimeLimit();
    }

    public boolean isPropagateRequired() {
        return this.isPropagateChangesChecked() && this.isPropagateAllowed();
    }

    public boolean isPropagateChangesChecked() {
        return this.getLineopPropagateReeferChanges() != null && this.getLineopPropagateReeferChanges() != false;
    }

    public void propagateLineDefaultMonitorsToReeferUnits(boolean inIsValidate) {
        if (!inIsValidate || this.isPropagateRequired()) {
            IArgoReeferMonitorManager manager = (IArgoReeferMonitorManager)Roastery.getBean((String)"reeferMonitorManager");
            manager.propagateLineDefaultMonitorsToReeferUnits(this);
        }
    }

}
