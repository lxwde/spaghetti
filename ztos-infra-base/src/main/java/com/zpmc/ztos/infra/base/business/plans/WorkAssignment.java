package com.zpmc.ztos.infra.base.business.plans;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.WorkAssignmentDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CheInstructionTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WaMovePurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WaStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.xps.AtomizedEnumXpsConverter;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.ArgoCompoundField;
import com.zpmc.ztos.infra.base.business.model.FeatureIdFactory;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class WorkAssignment extends WorkAssignmentDO {

//    public static final Set<WaStatusEnum> INCOMPLETE_WA_STATUS_SET = (Set<WaStatusEnum>) ImmutableSet.of((Object)((Object)WaStatusEnum.PENDING_DISPATCH),
//            (Object)((Object)WaStatusEnum.PENDING_REJECTION), (Object)((Object)WaStatusEnum.UNKNOWN),
//            (Object)((Object)WaStatusEnum.SENDING), (Object)((Object)WaStatusEnum.SENT), (Object)((Object)WaStatusEnum.ACCEPTED), (Object[])new WaStatusEnum[0]);
    public static final Set<WaStatusEnum> INCOMPLETE_WA_STATUS_SET = null;
    private static final WaStatusEnum WASTATUSENUM_INITIAL = WaStatusEnum.UNKNOWN;
    private static final WaMovePurposeEnum WAMOVEPURPOSEENUM_INITIAL = WaMovePurposeEnum.UNKNOWN;
    private static final Long WASTATUSXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(WASTATUSENUM_INITIAL, WaStatusEnum.getEnumList(), null);
 //   private static final Collection<WaStatusEnum> ABORTED_OR_REJECTED_WA_STATUS_SET = (Collection<WaStatusEnum>) ImmutableSet.of((Object)((Object)WaStatusEnum.ABORTED), (Object)((Object)WaStatusEnum.REJECTED));
    private static final Collection<WaStatusEnum> ABORTED_OR_REJECTED_WA_STATUS_SET = null;
    private static final Logger LOGGER = Logger.getLogger(WorkAssignment.class);

    public WorkAssignment() {
        this.setWorkassignmentStatusEnum(WASTATUSENUM_INITIAL);
        this.setWorkassignmentStatus(WASTATUSXPS_INITIAL);
        this.setWorkassignmentMovePurposeEnum(WAMOVEPURPOSEENUM_INITIAL);
    }

    @NotNull
    public static WorkAssignment create(@NotNull Che inChe) {
        WorkAssignment wa = new WorkAssignment();
        wa.setWorkassignmentPkey(XpsPkeyGenerator.generate((int)0, (int)0x3FFFFFFF, (int)109, (String)"WorkAssignment", (IMetafieldId) IArgoField.WORKASSIGNMENT_PKEY, (IMetafieldId) ArgoCompoundField.WORKASSIGNMENT_FACILITY, (Serializable) ContextHelper.getThreadFacilityKey()));
        wa.setWorkassignmentChe(inChe);
        wa.setWorkassignmentYard(inChe.getCheYard());
        HibernateApi.getInstance().save((Object)wa);
        if (LOGGER.isDebugEnabled()) {
            Throwable logTheCallStack = LOGGER.isTraceEnabled() ? new Throwable() : null;
            LOGGER.debug((Object)("created " + wa + ", Status = " + wa.getWorkassignmentStatusEnum().getName()), logTheCallStack);
        }
        return wa;
    }

    @NotNull
    public static WorkAssignment create(@NotNull Che inChe, @NotNull WaStatusEnum inStatusEnum, @NotNull WaMovePurposeEnum inMovePurposeEnum) {
        WorkAssignment wa = new WorkAssignment();
        wa.setWorkassignmentPkey(XpsPkeyGenerator.generate((int)0, (int)0x3FFFFFFF, (int)109, (String)"WorkAssignment", (IMetafieldId) IArgoField.WORKASSIGNMENT_PKEY, (IMetafieldId) ArgoCompoundField.WORKASSIGNMENT_FACILITY, (Serializable)ContextHelper.getThreadFacilityKey()));
        wa.setWorkassignmentChe(inChe);
        wa.setWorkassignmentYard(inChe.getCheYard());
        wa.setWorkassignmentStatusEnum(inStatusEnum);
        wa.setWorkassignmentMovePurposeEnum(inMovePurposeEnum);
        HibernateApi.getInstance().save((Object)wa);
        if (LOGGER.isDebugEnabled()) {
            Throwable logTheCallStack = LOGGER.isTraceEnabled() ? new Throwable() : null;
            LOGGER.debug((Object)("created " + wa + ", Status = " + wa.getWorkassignmentStatusEnum().getName()), logTheCallStack);
        }
        return wa;
    }

    @Nullable
    public static WorkAssignment findByPkey(Serializable inYardGkey, @Nullable Long inPkey) {
        if (inPkey == null) {
            return null;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkAssignment").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKASSIGNMENT_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKASSIGNMENT_YARD, (Object)inYardGkey)).addDqOrderings(new Ordering[]{Ordering.desc((IMetafieldId) IArgoField.WORKASSIGNMENT_GKEY)});
        dq.setDqMaxResults(1);
        Serializable[] workAssignmentGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (workAssignmentGkey != null && workAssignmentGkey.length > 0) {
            return (WorkAssignment)HibernateApi.getInstance().load(WorkAssignment.class, workAssignmentGkey[0]);
        }
        return null;
    }

    @Nullable
    public static WorkAssignment hydrate(@Nullable Serializable inPrimaryKey) {
        return (WorkAssignment)HibernateApi.getInstance().load(WorkAssignment.class, inPrimaryKey);
    }

    public static boolean hasSomeActiveStatus(@Nullable WaStatusEnum inStatus) {
        return inStatus != WaStatusEnum.REJECTED && inStatus != WaStatusEnum.COMPLETED && inStatus != WaStatusEnum.PENDING_DISPATCH && inStatus != WaStatusEnum.ABORTED;
    }

    public static boolean isUnorderedWorkAssignment(@Nullable WaMovePurposeEnum inWaMovePurpose) {
        return WaMovePurposeEnum.ASC_UNORDERED.equals((Object)inWaMovePurpose) || WaMovePurposeEnum.AHT_UNORDERED.equals((Object)inWaMovePurpose);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public void setWorkassignmentStatusXpsFromEnum() {
        this.setWorkassignmentStatus(WorkAssignment.getWaStatusXps(this.getWorkassignmentStatusEnum()));
    }

    public void setWorkassignmentStatusEnumFromXps() {
        this.setWorkassignmentStatusEnum(WorkAssignment.getWaStatusEnum(this.getWorkassignmentStatus()));
    }

    public void setWorkassignmentCheFromChePkey() {
        this.setWorkassignmentChe(Che.findByPkey(this.getWorkassignmentYard().getYrdGkey(), this.getWorkassignmentChePkey()));
    }

    public void updateHasActiveAlarm(Boolean inActive) {
        super.setWorkassignmentHasActiveAlarm(inActive);
    }

    public void preProcessInsert(@NotNull FieldChanges inOutMoreChanges) {
        boolean isInitialChePkey;
        LOGGER.debug((Object)("preProcessInsert(" + (Object)inOutMoreChanges + ")"));
        super.preProcessInsert(inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKASSIGNMENT_STATUS_ENUM, IArgoField.WORKASSIGNMENT_STATUS, WaStatusEnum.class, WASTATUSENUM_INITIAL, WASTATUSXPS_INITIAL, WaStatusEnum.getEnumList(), inOutMoreChanges, this.getWorkassignmentStatusEnum(), this.getWorkassignmentStatus());
        boolean isInitialChe = this.getWorkassignmentChe() == null;
        boolean bl = isInitialChePkey = this.getWorkassignmentChePkey() == null;
        if (isInitialChe != isInitialChePkey) {
            if (isInitialChePkey) {
                this.setSelfAndFieldChangeForWorkassignmentChePkey(inOutMoreChanges, this.getWorkassignmentChe());
            } else {
                this.setSelfAndFieldChangeForWorkassignmentChe(inOutMoreChanges, this.getWorkassignmentChePkey());
            }
        }
    }

    public void preProcessUpdate(@NotNull FieldChanges inFieldChanges, @NotNull FieldChanges inOutMoreChanges) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("preProcessUpdate(" + (Object)inFieldChanges + ", " + (Object)inOutMoreChanges + ")"));
        }
        super.preProcessUpdate(inFieldChanges, inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKASSIGNMENT_STATUS_ENUM, IArgoField.WORKASSIGNMENT_STATUS, WaStatusEnum.class, WASTATUSENUM_INITIAL, WASTATUSXPS_INITIAL, WaStatusEnum.getEnumList(), inOutMoreChanges, inFieldChanges);
        if (inFieldChanges.hasFieldChange(IArgoField.WORKASSIGNMENT_CHE) != inFieldChanges.hasFieldChange(IArgoField.WORKASSIGNMENT_CHE_PKEY)) {
            if (inFieldChanges.hasFieldChange(IArgoField.WORKASSIGNMENT_CHE)) {
                Che newChe = (Che)inFieldChanges.getFieldChange(IArgoField.WORKASSIGNMENT_CHE).getNewValue();
                this.setSelfAndFieldChangeForWorkassignmentChePkey(inOutMoreChanges, newChe);
            } else {
                Long newChePkey = (Long)inFieldChanges.getFieldChange(IArgoField.WORKASSIGNMENT_CHE_PKEY).getNewValue();
                this.setSelfAndFieldChangeForWorkassignmentChe(inOutMoreChanges, newChePkey);
            }
        }
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("preProcessInsertOrUpdate(" + (Object)inOutMoreChanges + ")"));
        }
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        if (!inOutMoreChanges.hasFieldChange(IArgoField.WORKASSIGNMENT_LAST_STATUS_UPDATE)) {
            this.setSelfAndFieldChange(IArgoField.WORKASSIGNMENT_LAST_STATUS_UPDATE, new Date(), inOutMoreChanges);
        }
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("preProcessDelete(" + (Object)inOutMoreChanges + ")"));
        }
        super.preProcessDelete(inOutMoreChanges);
        if (DataSourceEnum.PURGE_JOB.equals((Object)ContextHelper.getThreadDataSource())) {
//            IArgoControlManager manager = (IArgoControlManager) Roastery.getBean((String)"controlManager");
//            manager.prepareWorkAssignmentForDelete(this.getPrimaryKey());
        }
    }

    public void updateJobType(CheInstructionTypeEnum inJobType) {
        this.setWorkassignmentJobType(inJobType);
    }

    public void preDelete() {
        this.purgeJobStepProjections();
        super.preDelete();
    }

    public void purgeJobStepProjections() {
//        List<JobStepProjection> projections = JobStepProjection.findAllProjections(this.getPrimaryKey());
//        for (JobStepProjection projection : projections) {
//            projection.purge();
//        }
    }

    @NotNull
    public String toString() {
        return "WorkAssignment[" + this.getWorkassignmentGkey() + "]";
    }

    @NotNull
    public String describeWa() {
        StringBuilder waDesc = new StringBuilder("WorkAssignment[" + this.getWorkassignmentGkey());
        if (this.getWorkassignmentChe() != null) {
            waDesc.append(':').append(this.getWorkassignmentChe().getCheShortName());
        }
        if (this.getWorkassignmentMovePurposeEnum() != null && !WaMovePurposeEnum.UNKNOWN.equals((Object)this.getWorkassignmentMovePurposeEnum())) {
            waDesc.append(':').append(this.getWorkassignmentMovePurposeEnum().getName());
        }
        return waDesc + "]";
    }

    public boolean hasSomeActiveStatus() {
        return WorkAssignment.hasSomeActiveStatus(this.getWorkassignmentStatusEnum());
    }

    public void updateWorkassignmentChePkey(Long inWaChePkey) {
        if (WorkAssignment.shouldLogSuspectUpdates() && inWaChePkey != null && this.getWorkassignmentChePkey() != null && !inWaChePkey.equals(this.getWorkassignmentChePkey())) {
            Throwable logTheCallStack = LOGGER.isTraceEnabled() ? new Throwable() : null;
            LOGGER.warn((Object)("Incoming change of " + this + " CHE PKEY from " + this.getWorkassignmentChePkey() + " to " + inWaChePkey), logTheCallStack);
        }
        super.setWorkassignmentChePkey(inWaChePkey);
    }

    public void updateWorkassignmentChe(Che inWaChe) {
        if (WorkAssignment.shouldLogSuspectUpdates() && inWaChe != null && this.getWorkassignmentChe() != null && !inWaChe.equals(this.getWorkassignmentChe())) {
            Throwable logTheCallStack = LOGGER.isTraceEnabled() ? new Throwable() : null;
            LOGGER.warn((Object)("Incoming change of " + this + " CHE to " + inWaChe), logTheCallStack);
        }
        super.setWorkassignmentChe(inWaChe);
    }

    public boolean isWorkAssignmentFinished() {
        return !INCOMPLETE_WA_STATUS_SET.contains((Object)this.getWorkassignmentStatusEnum());
    }

    public boolean isWorkAssignmentIncomplete() {
        return INCOMPLETE_WA_STATUS_SET.contains((Object)this.getWorkassignmentStatusEnum());
    }

    public boolean isWorkAssignmentAbortedOrRejected() {
        return ABORTED_OR_REJECTED_WA_STATUS_SET.contains((Object)this.getWorkassignmentStatusEnum());
    }

    public boolean isWorkAssignmentWowi() {
        return this.isRepositionWorkAssignment() || this.isServicingWorkAssignment();
    }

    public boolean isRepositionWorkAssignment() {
        return WaMovePurposeEnum.ITV_REPOSITION.equals((Object)this.getWorkassignmentMovePurposeEnum()) || WaMovePurposeEnum.CHE_REPOSITION.equals((Object)this.getWorkassignmentMovePurposeEnum());
    }

    public boolean isProductiveAhtWorkAssignment() {
        return WaMovePurposeEnum.ITV_RECEIVE.equals((Object)this.getWorkassignmentMovePurposeEnum()) || WaMovePurposeEnum.ITV_DELIVER.equals((Object)this.getWorkassignmentMovePurposeEnum());
    }

    public boolean isServicingWorkAssignment() {
        return WaMovePurposeEnum.ITV_SERVICING.equals((Object)this.getWorkassignmentMovePurposeEnum());
    }

    public boolean isUnorderedWorkAssignment() {
        return WorkAssignment.isUnorderedWorkAssignment(this.getWorkassignmentMovePurposeEnum());
    }

    @Nullable
    private static WaStatusEnum getWaStatusEnum(@Nullable Long inWaStatusXps) {
        return AtomizedEnumXpsConverter.findEnumInstance(inWaStatusXps, WaStatusEnum.getEnumList(), WASTATUSENUM_INITIAL);
    }

    @Nullable
    private static Long getWaStatusXps(@Nullable WaStatusEnum inWaStatusEnum) {
        return AtomizedEnumXpsConverter.findXpsOrdinal(inWaStatusEnum, WaStatusEnum.getEnumList(), WASTATUSXPS_INITIAL);
    }

    private static boolean shouldLogSuspectUpdates() {
        return FeatureIdFactory.valueOf((String)"AUTOMATION").isEnabled(ContextHelper.getThreadUserContext());
    }

    private void setSelfAndFieldChangeForWorkassignmentChe(@NotNull FieldChanges inOutMoreChanges, @Nullable Long inWorkassignmentChePkey) {
        Che che = Che.findByPkey(this.getWorkassignmentYard().getYrdGkey(), inWorkassignmentChePkey);
        this.setSelfAndFieldChange(IArgoField.WORKASSIGNMENT_CHE, che, inOutMoreChanges);
    }

    private void setSelfAndFieldChangeForWorkassignmentChePkey(@NotNull FieldChanges inOutMoreChanges, @Nullable Che inWorkassignmentChe) {
        Long chePkey = inWorkassignmentChe == null ? null : inWorkassignmentChe.getChePkey();
        this.setSelfAndFieldChange(IArgoField.WORKASSIGNMENT_CHE_PKEY, chePkey, inOutMoreChanges);
    }

}
