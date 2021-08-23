package com.zpmc.ztos.infra.base.business.plans;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.WorkQueueDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveStageEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.WiSuspendStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.WqTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.MovesCompoundField;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class WorkQueue extends WorkQueueDO {
    private static final String FLIP_WORK_QUEUE_NAME = "FLIP";
    private static final Logger LOGGER = Logger.getLogger(WorkQueue.class);

    public WorkQueue() {
        this.setWqType(WqTypeEnum.YARD);
        this.setWqhasWisSentToTLS(Boolean.FALSE);
        this.setWqSaveCompletedMoves(Boolean.FALSE);
        this.setWqIsBlue(Boolean.FALSE);
        this.setWqPermanent(Boolean.FALSE);
        this.setWqServiceOrderQueue(Boolean.FALSE);
        this.setWqManualSequenceMode(Boolean.FALSE);
        this.setWqYardLoadbackQueue(Boolean.FALSE);
        this.setWqAllowCntr40(Boolean.TRUE);
        this.setWqAllowCntr20(Boolean.TRUE);
        this.setWqPosLocType(LocTypeEnum.UNKNOWN);
        this.setWqUseWqProd(Boolean.FALSE);
        this.setWqLastActivationChange(ArgoUtils.timeNow());
    }

    public Long getLastSequenceNbr() {
        return this.getLastSequenceNbr(false);
    }

    public Long getLastIncompleteSequenceNbr() {
        return this.getLastSequenceNbr(true);
    }

    private Long getLastSequenceNbr(boolean inExcludeCompleteInstructions) {
        IQueryResult qr;
        Long max;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MAX, IMovesField.WI_SEQUENCE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        if (inExcludeCompleteInstructions) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE));
        }
        if ((max = (Long)(qr = HibernateApi.getInstance().findValuesByDomainQuery(dq)).getValue(0, IMovesField.WI_SEQUENCE)) == null) {
            return 0L;
        }
        return max;
    }

    @Nullable
    public WorkQueue findNextWorkQueue() {
        String vvId = this.getWqPosLocId();
        if (vvId == null) {
            return null;
        }
        PointOfWork pow = this.getWqPowViaWorkShift();
        if (pow == null || pow.getPointofworkCheId() == null) {
            return null;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WQ_GKEY, (Object)this.getPrimaryKey())).addDqPredicate(PredicateFactory.ge((IMetafieldId)IMovesField.WQ_ORDER, (Object)this.getWqOrder())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_POS_LOC_ID, (Object)vvId)).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId) MovesCompoundField.WQ_CRANE_ID)).addDqPredicate(PredicateFactory.eq((IMetafieldId) MovesCompoundField.WQ_CRANE_ID, (Object)pow.getPointofworkCheId())).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WQ_ORDER));
        dq.setMaxResults(1);
        List results = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return !results.isEmpty() ? (WorkQueue)results.get(0) : null;
    }

    @Nullable
    public WorkQueue findPreviousWorkQueue() {
        String vvId = this.getWqPosLocId();
        if (vvId == null) {
            return null;
        }
        PointOfWork pow = this.getWqPowViaWorkShift();
        if (pow == null || pow.getPointofworkCheId() == null) {
            return null;
        }

        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WQ_GKEY, (Object)this.getPrimaryKey())).addDqPredicate(PredicateFactory.le((IMetafieldId) IMovesField.WQ_ORDER, (Object)this.getWqOrder())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_POS_LOC_ID, (Object)vvId)).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId) MovesCompoundField.WQ_CRANE_ID)).addDqPredicate(PredicateFactory.eq((IMetafieldId) MovesCompoundField.WQ_CRANE_ID, (Object)pow.getPointofworkCheId())).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WQ_ORDER));
        dq.setMaxResults(1);
        List results = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        return results.isEmpty() ? null : (WorkQueue)results.get(0);
    }

    public Long getFirstSequenceNbr() {
        return this.getFirstSequenceNbr(false);
    }

    public Long getFirstIncompleteSequenceNbr() {
        return this.getFirstSequenceNbr(true);
    }

    private Long getFirstSequenceNbr(boolean inExcludeCompleteInstructions) {
        IQueryResult qr;
        Long min;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MIN, IMovesField.WI_SEQUENCE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        if (inExcludeCompleteInstructions) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE));
        }
        if ((min = (Long)(qr = HibernateApi.getInstance().findValuesByDomainQuery(dq)).getValue(0, IMovesField.WI_SEQUENCE)) == null) {
            return 0L;
        }
        return min;
    }

    public boolean hasIncompleteWorkInstructions() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.COMPLETE));
        return HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public int getNextSequenceNbrForNewWi() {
        Long lastSeq = this.getLastSequenceNbr() + 1L;
        return lastSeq.intValue();
    }

    public Long getLastActualSequenceNbr() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MAX, IMovesField.WI_ACTUAL_SEQUENCE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        Long max = (Long)qr.getValue(0, IMovesField.WI_ACTUAL_SEQUENCE);
        if (max == null) {
            return 0L;
        }
        return max;
    }

    @Nullable
    public Date getEstimatedStartTime() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MIN, IMovesField.WI_ESTIMATED_MOVE_TIME).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        if (qr.getCurrentResultCount() > 0) {
            return (Date)qr.getValue(0, IMovesField.WI_ESTIMATED_MOVE_TIME);
        }
        return null;
    }

    @Nullable
    public Date getEstimatedRestartTimeAfterLastStatusChange() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MIN, IMovesField.WI_ESTIMATED_MOVE_TIME).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.ge((IMetafieldId)IMovesField.WI_ESTIMATED_MOVE_TIME, (Object)this.getWqLastActivationChange()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        if (qr.getCurrentResultCount() > 0) {
            return (Date)qr.getValue(0, IMovesField.WI_ESTIMATED_MOVE_TIME);
        }
        return null;
    }

    @Nullable
    public Date getEstimatedEndTime() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MAX, IMovesField.WI_ESTIMATED_MOVE_TIME).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        if (qr.getCurrentResultCount() > 0) {
            return (Date)qr.getValue(0, IMovesField.WI_ESTIMATED_MOVE_TIME);
        }
        return null;
    }

    @Nullable
    public List<WorkInstruction> getUnderwayLoadWorkInstructions() {
        IDomainQuery dqWI = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqField(IMovesField.WI_SEQUENCE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_STAGE, (Object) WiMoveStageEnum.CARRY_UNDERWAY)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_MOVE_KIND, (Object)WiMoveKindEnum.VeslLoad));
        dqWI.addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_SEQUENCE));
        List wis = HibernateApi.getInstance().findEntitiesByDomainQuery(dqWI);
        return wis;
    }

    public void updateWqName(String inWqName) {
        this.setWqName(inWqName);
    }

    public void updateWqType(WqTypeEnum inWqTypeEnum) {
        this.setWqType(inWqTypeEnum);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        super.preProcessInsertOrUpdate(inOutMoreChanges);
    }

    public static WorkQueue findOrCreateWorkQueue(Serializable inYardGkey, Long inWqPkey) {
        WorkQueue wq = WorkQueue.findByPkey(inYardGkey, inWqPkey);
        if (wq == null) {
            wq = new WorkQueue();
            wq.setWqPkey(inWqPkey);
            Yard yard = (Yard)HibernateApi.getInstance().load(Yard.class, inYardGkey);
            yard.getId();
            wq.setWqYard(yard);
            HibernateApi.getInstance().save((Object)wq);
        }
        return wq;
    }

    public static WorkQueue findOrCreateWorkQueue(Serializable inYardGkey, String inWqName) {
        WorkQueue wq;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_NAME, (Object)inWqName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_YARD, (Object)inYardGkey));
        int count = HibernateApi.getInstance().findCountByDomainQuery(dq);
        if (count > 1) {
            dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_NAME, (Object)inWqName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_YARD, (Object)inYardGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_IS_BLUE, (Object)true));
            LOGGER.error((Object)("Found (" + count + ") duplicate work queue (" + inWqName + ") within same yard gkey(" + inYardGkey + ") : consider removing the inactive Work Queue"));
        }
        if ((wq = (WorkQueue)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq)) == null) {
            Yard yard = (Yard)HibernateApi.getInstance().load(Yard.class, inYardGkey);
            yard.getId();
            wq = new WorkQueue();
            wq.setWqPkey(XpsPkeyGenerator.generate((int)1, (String)"WorkQueue", (IMetafieldId)IMovesField.WQ_PKEY, (IMetafieldId)IMovesField.WQ_YARD, (Serializable) ContextHelper.getThreadYardKey()));
            wq.setWqYard(yard);
            wq.setWqName(inWqName);
            wq.setActive(true);
            if (WorkQueue.isWQNameReservedForPermanent(inWqName)) {
                wq.setWqPermanent(true);
                LOGGER.debug((Object)("Setting the Permanent flag on for new queue " + wq.getWqName()));
            }
            HibernateApi.getInstance().save((Object)wq);
        }
        return wq;
    }

    public static WorkQueue findOrCreateFlipWorkQueue(Serializable inYardGkey) {
        return WorkQueue.findOrCreateWorkQueue(inYardGkey, FLIP_WORK_QUEUE_NAME);
    }

    @Nullable
    public static WorkQueue findWorkQueue(@NotNull String inWqName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_NAME, (Object)inWqName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_YARD, (Object)ContextHelper.getThreadYardKey()));
        return (WorkQueue)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static List<WorkQueue> findWorkQueues(@NotNull String inWqName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_NAME, (Object)inWqName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_YARD, (Object)ContextHelper.getThreadYardKey())).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WQ_IS_BLUE));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static boolean isWQNameReservedForPermanent(String inWqName) {
        return inWqName.equals(WorkQueue.getBackgroundWqName()) || inWqName.equals(WorkQueue.getTransferWqName()) || inWqName.equals(WorkQueue.getRailTransferWqName()) || inWqName.equals(FLIP_WORK_QUEUE_NAME);
    }

    public static String getBackgroundWqName() {
        return InventoryConfig.BACKGROUND_WQ_NAME.getSetting(ContextHelper.getThreadUserContext());
    }

    public static String getTransferWqName() {
        return InventoryConfig.TRANSFER_WQ_NAME.getSetting(ContextHelper.getThreadUserContext());
    }

    public static String getRailTransferWqName() {
        return InventoryConfig.RAIL_TRANSFER_WQ_NAME.getSetting(ContextHelper.getThreadUserContext());
    }

    @Nullable
    public static WorkQueue findByPkey(Serializable inYardGkey, Long inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_YARD, (Object)inYardGkey));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() > 1) {
            LOGGER.error((Object)(matches.size() + " WorkQueue found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardGkey + ")"));
            Collections.sort(matches, new Comparator<WorkQueue>(){

                @Override
                public int compare(WorkQueue inWq1, WorkQueue inWq2) {
                    return inWq2.getWqGkey().compareTo(inWq1.getWqGkey());
                }
            });
        }
        return matches.isEmpty() ? null : (WorkQueue)matches.get(0);
    }

    public static WorkQueue hydrate(Long inWqGkey) {
        return (WorkQueue) Roastery.getHibernateApi().load(WorkQueue.class, (Serializable)inWqGkey);
    }

    public void setFirstShiftFromFirstShiftPkey() {
        this.setWqFirstRelatedShift(this.getFirstShiftFromFirstShiftPkey());
    }

    public void obsoleteSetPowFromPowPkey() {
        this.setWqPow(this.obsoleteGetPowFromPowPkey());
    }

    public void clearWqPowReference() {
        this.setWqPow(null);
    }

    public void updateWqCycleCompanion(WorkQueue inWq) {
        this.setWqCycleCompanion(inWq);
    }

    public void updateWqFirstRelatedShift(WorkShift inWorkShift) {
        this.setWqFirstRelatedShift(inWorkShift);
    }

    public void updateWqPosLocId(@Nullable String inPosLocId) {
        this.setWqPosLocId(inPosLocId);
    }

    public void updateWqPosLocType(@Nullable LocTypeEnum inPosLocType) {
        this.setWqPosLocType(inPosLocType);
    }

    public void updateWqCode(@Nullable String inWqCode) {
        this.setWqCode(inWqCode);
    }

    public void updateWqDeck(@Nullable String inWqDeck) {
        this.setWqDeck(inWqDeck);
    }

    public void updateWqRow(@Nullable String inWqRow) {
        this.setWqRow(inWqRow);
    }

    public void updateWqFirstRelatedShiftPkey(@Nullable Long inWorkShiftPkey) {
        this.setWqFirstRelatedShiftPkey(inWorkShiftPkey);
    }

    public void obsoleteUpdateWqPowPkey(@Nullable Long inPowPkey) {
        this.setWqPowPkey(inPowPkey);
    }

    public void updateWqPkey(@Nullable Long inPkey) {
        this.setWqPkey(inPkey);
    }

    public void updateWqYard(@Nullable Yard inYard) {
        this.setWqYard(inYard);
    }

    public void preProcessInsert(@NotNull FieldChanges inOutMoreChanges) {
        Long wqFirstRelatedShiftPkey;
        super.preProcessInsert(inOutMoreChanges);
        Long wqPowPkey = this.getWqPowPkey();
        if (wqPowPkey == null || wqPowPkey == 0L) {
            if (this.getWqPow() != null) {
                this.setSelfAndFieldChange(IMovesField.WQ_POW_PKEY, this.getWqPow().getPointofworkPkey(), inOutMoreChanges);
            }
        } else if (this.getWqPow() == null) {
            this.setSelfAndFieldChange(IMovesField.WQ_POW, (Object)this.obsoleteGetPowFromPowPkey(), inOutMoreChanges);
        }
        if ((wqFirstRelatedShiftPkey = this.getWqFirstRelatedShiftPkey()) == null || wqFirstRelatedShiftPkey == 0L) {
            if (this.getWqFirstRelatedShift() != null) {
                this.setSelfAndFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT_PKEY, this.getWqFirstRelatedShift().getWorkshiftPkey(), inOutMoreChanges);
            }
        } else if (this.getWqFirstRelatedShift() == null) {
            this.setSelfAndFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT, (Object)this.getFirstShiftFromFirstShiftPkey(), inOutMoreChanges);
        }
    }

    public void preProcessUpdate(@NotNull FieldChanges inChanges, @NotNull FieldChanges inOutMoreChanges) {
        Long newPkey;
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (inChanges.hasFieldChange(IMovesField.WQ_POW_PKEY)) {
            if (!inChanges.hasFieldChange(IMovesField.WQ_POW)) {
                Long newPowPkey = (Long)inChanges.getFieldChange(IMovesField.WQ_POW_PKEY).getNewValue();
                PointOfWork pow = PointOfWork.findByPkey((Long)this.getYardKeyConsideringChanges(inChanges), (Long)newPowPkey);
                this.setSelfAndFieldChange(IMovesField.WQ_POW, (Object)pow, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WQ_POW)) {
            PointOfWork pow = (PointOfWork)inChanges.getFieldChange(IMovesField.WQ_POW).getNewValue();
            newPkey = pow == null ? null : pow.getPointofworkPkey();
            this.setSelfAndFieldChange(IMovesField.WQ_POW_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT_PKEY)) {
            if (!inChanges.hasFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT)) {
                Long newFirstShiftPkey = (Long)inChanges.getFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT_PKEY).getNewValue();
                WorkShift firstShift = WorkShift.findByPkey((Serializable)this.getYardKeyConsideringChanges(inChanges), (Long)newFirstShiftPkey);
                this.setSelfAndFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT, (Object)firstShift, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT)) {
            WorkShift firstShift = (WorkShift)inChanges.getFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT).getNewValue();
            newPkey = firstShift == null ? null : firstShift.getWorkshiftPkey();
            this.setSelfAndFieldChange(IMovesField.WQ_FIRST_RELATED_SHIFT_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IMovesField.WQ_IS_BLUE)) {
            this.setSelfAndFieldChange(IMovesField.WQ_LAST_ACTIVATION_CHANGE, ArgoUtils.timeNow(), inOutMoreChanges);
        }
    }

    public void setCallSlaveFromTlsSortKey() {
        Long tlsSortKey = this.getWqTlsSortKey();
        if (tlsSortKey != null && tlsSortKey >= -1L && tlsSortKey <= 9L) {
            this.setWqAbsBerthCallSlaveId(tlsSortKey);
            this.setWqTlsSortKey(0L);
        } else {
            Long slaveId = this.getWqAbsBerthCallSlaveId();
            if (slaveId == null) {
                this.setWqAbsBerthCallSlaveId(-1L);
            }
        }
    }

    @Nullable
    public PointOfWork getWqPowViaWorkShift() {
        WorkShift ws = this.getWqFirstRelatedShift();
        if (ws == null) {
            return null;
        }
        return ws.getWorkshiftOwnerPow();
    }

    @Override
    @Deprecated
    @Nullable
    public PointOfWork getWqPow() {
        return super.getWqPow();
    }

    @Nullable
    private WorkShift getFirstShiftFromFirstShiftPkey() {
        return WorkShift.findByPkey((Serializable)this.getWqYard().getYrdGkey(), (Long)this.getWqFirstRelatedShiftPkey());
    }

    @Nullable
    private PointOfWork obsoleteGetPowFromPowPkey() {
        return PointOfWork.findByPkey((Long)this.getWqYard().getYrdGkey(), (Long)this.getWqPowPkey());
    }

    @Nullable
    public CarrierVisit getOwnerFromLocation() {
        CarrierVisit cv = null;
        if (LocTypeEnum.VESSEL.equals((Object)this.getWqPosLocType()) || LocTypeEnum.TRAIN.equals((Object)this.getWqPosLocType())) {
            try {
                cv = CarrierVisit.findCarrierVisit((Facility)this.getWqYard().getLocFacility(), (LocTypeEnum)this.getWqPosLocType(), (String)this.getWqPosLocId());
            }
            catch (BizViolation bz) {
                LOGGER.error((Object)("WorkQueue unable to resolve location into an owner CarrierVisit entity " + bz.getMessage()));
                cv = null;
            }
            if (cv == null) {
                LOGGER.debug((Object)"WorkQueue resolved location into a null owner CarrierVisit entity");
            }
        }
        return cv;
    }

    @Nullable
    private Long getYardKeyConsideringChanges(@NotNull FieldChanges inChanges) {
        Yard yard = inChanges.hasFieldChange(IMovesField.WQ_YARD) ? (Yard)inChanges.getFieldChange(IMovesField.WI_UYV).getNewValue() : this.getWqYard();
        if (yard == null) {
            return null;
        }
        return yard.getYrdGkey();
    }

    @Nullable
    public WorkInstruction getWiAfterWi(@NotNull WorkInstruction inWorkInstruction) {
        return null;
    }

    @Nullable
    public WorkInstruction getWiBeforeWi(@NotNull WorkInstruction inWorkInstruction) {
        return null;
    }

    public void updateWqVesselLcg(Long inWqVesselLcg) {
        this.setWqVesselLcg(inWqVesselLcg);
    }

    public AbstractBin getWqSectionFromFirstWi() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqAggregateField(AggregateFunctionType.MIN, IMovesField.WI_SEQUENCE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (workInstructions == null || workInstructions.isEmpty()) {
            return null;
        }
        AbstractBin wqBin = null;
        for (int w = 0; w < workInstructions.size(); ++w) {
            WorkInstruction wi = (WorkInstruction)workInstructions.get(w);
            WiMoveKindEnum mvKind = wi.getWiMoveKind();
            if (mvKind == WiMoveKindEnum.VeslDisch) {
                wqBin = wi.getWiFromPosition().getPosBin();
                break;
            }
            if (mvKind != WiMoveKindEnum.VeslLoad) continue;
            wqBin = wi.getWiToPosition().getPosBin();
            break;
        }
        AbstractBin cellSection = null;
        if (wqBin != null) {
            cellSection = wqBin.findAncestorBinAtLevel(Long.valueOf(3L));
        }
        return cellSection;
    }

    public Boolean getActive() {
        return this.getWqIsBlue();
    }

    public void setActive(Boolean inActive) {
        this.setWqIsBlue(inActive);
    }

    public void updateWqPow(@NotNull PointOfWork inPointOfWork) {
        this.setWqPow(inPointOfWork);
        Double wqOrder = this.getWqOrder();
        if (wqOrder == null) {
            Double nextSeq = this.getLastWqOrderNbr(inPointOfWork) + 1.0;
            this.setWqOrder(nextSeq);
        }
    }

    @NotNull
    public Double getLastWqOrderNbr(@NotNull PointOfWork inPointOfWork) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkQueue").addDqAggregateField(AggregateFunctionType.MAX, IMovesField.WQ_ORDER).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WQ_POW_PKEY, (Object)inPointOfWork.getPointofworkPkey()));
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        Double max = (Double)qr.getValue(0, IMovesField.WQ_ORDER);
        if (max == null) {
            return 0.0;
        }
        return max;
    }

    public boolean isWiNextInSequence(@NotNull WorkInstruction inWorkInstruction) {
        if (!this.equals(inWorkInstruction.getWiWorkQueue())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)(inWorkInstruction + " does not belong to " + this.toString()));
            }
            return false;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.lt((IMetafieldId)IMovesField.WI_SEQUENCE, (Object)inWorkInstruction.getWiSequence())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_SUSPEND_STATE, (Object)((Object)WiSuspendStateEnum.NONE))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_CARRIER_LOC_ID, (Object)inWorkInstruction.getWiCarrierLocId()));
        if (WiMoveKindEnum.VeslLoad.equals((Object)inWorkInstruction.getWiMoveKind())) {
            dq.addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PLANNED, WiMoveStageEnum.CARRY_READY, WiMoveStageEnum.CARRY_UNDERWAY, WiMoveStageEnum.CARRY_COMPLETE})));
        } else if (WiMoveKindEnum.VeslDisch.equals((Object)inWorkInstruction.getWiMoveKind())) {
            dq.addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PLANNED})));
        }
        WorkInstruction twinWi = inWorkInstruction.getTwinCompanion();
        List workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Found the following list " + workInstructions + " before " + inWorkInstruction));
        }
        return workInstructions.isEmpty() || workInstructions.size() == 1 && twinWi != null && workInstructions.contains(twinWi);
    }

    public boolean doesWqHaveAny40sOrTwins() {
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate((IPredicate)PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId)InventoryCompoundField.WI_UYV_UFV_UNIT_UE_UEEQUIP_EQTYPE_BASIC_LENGTH, (Object)EquipBasicLengthEnum.BASIC40)).add(PredicateFactory.eq((IMetafieldId)IMovesField.WI_TWIN_WITH, (Object)((Object)TwinWithEnum.NEXT)))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey()));
//        return HibernateApi.getInstance().existsByDomainQuery(dq);
        return false;
    }

    @Nullable
    public WorkInstruction calculatePotentialLoadPlanSwap(@NotNull WorkInstruction inWiBeingHandled) {
        if (Boolean.TRUE.equals(inWiBeingHandled.getWiIsStrictLoad())) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - it is marked as requiring strict load plan"));
            return null;
        }
        WorkInstruction nextPlannedWI = this.getNextLoadWiInSequence(inWiBeingHandled.getWiCarrierLocId());
        if (nextPlannedWI == null) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - no next load planned for WQ"));
            return null;
        }
        if (nextPlannedWI.equals(inWiBeingHandled)) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - because it is already the next load planned for WQ"));
            return null;
        }
        if (nextPlannedWI.getWiSequence() >= inWiBeingHandled.getWiSequence()) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - because next sequenced WI " + nextPlannedWI + " has sequence number " + nextPlannedWI.getWiSequence() + " >= " + inWiBeingHandled.getWiSequence()));
            return null;
        }
        if (inWiBeingHandled.getTwinCompanion() != null) {
            if (nextPlannedWI.getTwinCompanion() == null) {
                LOGGER.debug((Object)("Computed potential load plan swap " + nextPlannedWI.describeWi() + " was SINGLE and WI being handled " + inWiBeingHandled.describeWi() + " is twin. Cannot swap."));
                return null;
            }
            if (!inWiBeingHandled.getWiTwinWith().equals((Object)nextPlannedWI.getWiTwinWith())) {
                LOGGER.debug((Object)("The computed potential load plan swap " + nextPlannedWI.describeWi() + " (for " + inWiBeingHandled.describeWi() + ") had " + "the wrong twin flag. Using it's twin companion " + nextPlannedWI.getTwinCompanion().describeWi() + " instead."));
                nextPlannedWI = nextPlannedWI.getTwinCompanion();
            }
        }
        if (inWiBeingHandled.equals(nextPlannedWI)) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - because it is already the next load planned for WQ"));
            return null;
        }
        if (!this.validateUnitsSwappableForVesselLoad(inWiBeingHandled, nextPlannedWI)) {
            return null;
        }
        if (inWiBeingHandled.equals(nextPlannedWI.getTwinCompanion())) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - because next sequenced WI " + nextPlannedWI + " is its twin companion"));
            return null;
        }
        if (nextPlannedWI.getOtherWorkInstructionsInTandemSet().contains(inWiBeingHandled)) {
            LOGGER.debug((Object)("No Load Swap calculated for " + inWiBeingHandled + " - because next sequenced WI " + nextPlannedWI + " is part of the same tandem set"));
            return null;
        }
        LOGGER.debug((Object)("Load Swap calculated for " + inWiBeingHandled + " - swap with " + nextPlannedWI));
        return nextPlannedWI;
    }

    private boolean validateUnitsSwappableForVesselLoad(WorkInstruction inWiBeingHandled, WorkInstruction inNextPlannedWI) {
        WorkInstruction twinLiftForWiToSwapWith;
        UnitFacilityVisit ufvOnQC = inWiBeingHandled.getWiUfv();
        UnitFacilityVisit nextPlannedUfv = inNextPlannedWI.getWiUfv();
        if (!nextPlannedUfv.isUfvSwappableForVesselLoad(ufvOnQC).booleanValue()) {
            return false;
        }
        WorkInstruction twinLiftForWiOnQc = inWiBeingHandled.getWiTwinIntendedPut() != false ? inWiBeingHandled.getTwinCompanion() : null;
        WorkInstruction workInstruction = twinLiftForWiToSwapWith = inNextPlannedWI.getWiTwinIntendedPut() != false ? inNextPlannedWI.getTwinCompanion() : null;
        if (twinLiftForWiOnQc != null && twinLiftForWiToSwapWith != null) {
            UnitFacilityVisit twinUfvOnQC = twinLiftForWiOnQc.getWiUfv();
            UnitFacilityVisit twinNextPlannedUfv = twinLiftForWiToSwapWith.getWiUfv();
            if (twinUfvOnQC != null && twinNextPlannedUfv != null && !twinNextPlannedUfv.isUfvSwappableForVesselLoad(twinUfvOnQC).booleanValue()) {
                LOGGER.debug((Object)("No Load Swap calculated for twin companion" + twinLiftForWiOnQc + " - stow factor/weight tolerance " + twinLiftForWiToSwapWith + " stow factors: " + twinUfvOnQC.obtainUfvStowFactor() + " versus " + twinNextPlannedUfv.obtainUfvStowFactor()));
                return false;
            }
        } else if (inWiBeingHandled.isPartOfTandemSet() && inNextPlannedWI.isPartOfTandemSet()) {
            WorkInstruction tandemPairOfSwapWi;
            WorkInstruction tandemPairOfWiOnQc = inWiBeingHandled.getWiIsTandemWithNext() != false ? inWiBeingHandled.getNextWiInWq() : inWiBeingHandled.getPrevWiInWq();
            WorkInstruction workInstruction2 = tandemPairOfSwapWi = inNextPlannedWI.getWiIsTandemWithNext() != false ? inNextPlannedWI.getNextWiInWq() : inNextPlannedWI.getPrevWiInWq();
            if (tandemPairOfWiOnQc != null && tandemPairOfSwapWi != null) {
                UnitFacilityVisit tandemUfvOnQC = tandemPairOfWiOnQc.getWiUfv();
                UnitFacilityVisit tandemNextPlannedUfv = tandemPairOfSwapWi.getWiUfv();
                if (tandemUfvOnQC != null && tandemNextPlannedUfv != null && !tandemNextPlannedUfv.isUfvSwappableForVesselLoad(tandemUfvOnQC).booleanValue()) {
                    LOGGER.debug((Object)("No Load Swap calculated for Tandem companion" + tandemPairOfWiOnQc + " - stow factor/weight tolerance " + tandemPairOfSwapWi + " stow factors: " + tandemUfvOnQC.obtainUfvStowFactor() + " versus " + tandemNextPlannedUfv.obtainUfvStowFactor()));
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean existStaleWiInWq(Date inLastActivationTime) {
        if (inLastActivationTime == null) {
            return false;
        }
        IDomainQuery staleWIQuery = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_SUSPEND_STATE, Arrays.asList(new WiSuspendStateEnum[]{WiSuspendStateEnum.BYPASS, WiSuspendStateEnum.SYSTEM_BYPASS})))).addDqPredicate(PredicateFactory.lt((IMetafieldId)IMovesField.WI_TIME_MODIFIED, (Object)inLastActivationTime));
        if (this.getWqType().equals((Object) WqTypeEnum.LOAD)) {
            staleWIQuery.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_UNDERWAY, WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE}))));
        } else if (this.getWqType().equals((Object) WqTypeEnum.DISCH)) {
            staleWIQuery.addDqPredicate(PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PLANNED})));
        }
        return HibernateApi.getInstance().existsByDomainQuery(staleWIQuery);
    }

    @Nullable
    public WorkInstruction getNextLoadWiInSequence(@Nullable String inCarrierLocId) {
        List workInstructions;
        if (!WqTypeEnum.LOAD.equals((Object)this.getWqType())) {
            LOGGER.debug((Object)"Not a load work queue, returning null");
            return null;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_WORK_QUEUE, (Object)this.getWqGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_SUSPEND_STATE, (Object)((Object) WiSuspendStateEnum.NONE))).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_UNDERWAY, WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE})))).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_SEQUENCE));
        if (inCarrierLocId != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_CARRIER_LOC_ID, (Object)inCarrierLocId));
        }
        if (!(workInstructions = HibernateApi.getInstance().findEntitiesByDomainQuery(dq)).isEmpty()) {
            WorkInstruction result = (WorkInstruction)workInstructions.get(0);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Found the following next load WI: " + result));
            }
            return result;
        }
        return null;
    }

    public boolean isWqUpToDateForDispatch() {
        Date lastActivationChange = this.getWqLastActivationChange();
        if (lastActivationChange == null) {
            return false;
        }
        return !this.existStaleWiInWq(lastActivationChange);
    }

    public String toString() {
        return "WorkQueue[" + this.getWqGkey() + ":" + this.getWqName() + "]";
    }
}
