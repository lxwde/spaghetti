package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.EcEventDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.LogUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EcEvent extends EcEventDO implements IXpeEcEventAccessor, IXpeEcEventMutator {
    private static final Logger LOGGER = Logger.getLogger(EcEvent.class);

    public EcEvent() {
        this(ArgoUtils.timeNow());
    }

    public EcEvent(@NotNull Date inEventTime) {
        this.setEceventPkey(0L);
        this.setEceventTimestamp(inEventTime);
    }

    @NotNull
    public static EcEvent create(@NotNull String inEceventTypeDescription, @Nullable Date inEventTime, @NotNull Che inChe, @Nullable String inUnitIdName, @Nullable String inWorkQueueName, @Nullable String inWiMoveKind, @Nullable Boolean inIsTwinMove, @Nullable Long inWaGkey, @Nullable String inWaId, @Nullable String inUnitReference, @Nullable String inTransactionId, @Nullable LocPosition inPosition, @Nullable String inTrolleyType, boolean inSuppressDuplicates) {
        String powName = inChe.getChePointOfWork() == null ? null : inChe.getChePointOfWork().getPointofworkName();
//        return EcEvent.create(inEceventTypeDescription, inEventTime, inChe, inUnitIdName, inWorkQueueName, powName, inWiMoveKind, inIsTwinMove, inWaGkey, inWaId, inUnitReference, inTransactionId, inPosition, EcEventMapper.getEcEventType(inEceventTypeDescription), EcEventMapper.getSubEcEventType(inEceventTypeDescription, inTrolleyType), inSuppressDuplicates);
        return null;
    }

    @NotNull
    public static EcEvent create(@NotNull String inEceventTypeDescription, @Nullable Date inEventTime, @NotNull Che inChe, @Nullable String inUnitIdName, @Nullable String inWorkQueueName, @Nullable String inPowName, @Nullable String inWiMoveKind, @Nullable Boolean inIsTwinMove, @Nullable Long inWaGkey, @Nullable String inWaId, @Nullable String inUnitReference, @Nullable String inTransactionId, @Nullable LocPosition inPosition, @Nullable String inTrolleyType, boolean inSuppressDuplicates) {
//        return EcEvent.create(inEceventTypeDescription, inEventTime, inChe, inUnitIdName, inWorkQueueName, inPowName, inWiMoveKind, inIsTwinMove, inWaGkey, inWaId, inUnitReference, inTransactionId, inPosition, EcEventMapper.getEcEventType(inEceventTypeDescription), EcEventMapper.getSubEcEventType(inEceventTypeDescription, inTrolleyType), inSuppressDuplicates);
        return null;
    }

    @NotNull
    public static EcEvent create(@NotNull String inEceventTypeDescription, @Nullable Date inEventTime, @NotNull Che inChe, @Nullable String inUnitIdName, @Nullable String inWorkQueueName, @Nullable String inWiMoveKind, @Nullable Boolean inIsTwinMove, @Nullable Long inWaGkey, @Nullable String inWaId, @Nullable String inUnitReference, @Nullable String inTransactionId, @Nullable LocPosition inPosition, @Nullable Long inEceventType, @Nullable Long inEceventSubType, boolean inSuppressDuplicates) {
        String powName = inChe.getChePointOfWork() == null ? null : inChe.getChePointOfWork().getPointofworkName();
        return EcEvent.create(inEceventTypeDescription, inEventTime, inChe, inUnitIdName, inWorkQueueName, powName, inWiMoveKind, inIsTwinMove, inWaGkey, inWaId, inUnitReference, inTransactionId, inPosition, inEceventType, inEceventSubType, inSuppressDuplicates);
    }

    @NotNull
    public static EcEvent create(@NotNull String inEceventTypeDescription, @Nullable Date inEventTime, @NotNull Che inChe, @Nullable String inUnitIdName, @Nullable String inWorkQueueName, @Nullable String inPowName, @Nullable String inWiMoveKind, @Nullable Boolean inIsTwinMove, @Nullable Long inWaGkey, @Nullable String inWaId, @Nullable String inUnitReference, @Nullable String inTransactionId, @Nullable LocPosition inPosition, @Nullable Long inEceventType, @Nullable Long inEceventSubType, boolean inSuppressDuplicates) {
        String poolName;
        String operationalPosId;
        LocTypeEnum locType;
        String locSlot;
        String locId;
        if (inPosition == null) {
            locId = null;
            locSlot = null;
            locType = null;
            operationalPosId = null;
        } else {
            locId = inPosition.getPosLocId();
            locSlot = inPosition.getPosSlot();
            locType = inPosition.getPosLocType();
            operationalPosId = inPosition.getPosOperationalPosId();
        }
        String string = poolName = inChe.getChePool() == null ? null : inChe.getChePool().getPoolName();
        if (inSuppressDuplicates) {
            EcEvent.logErrorIfMayCauseDuplicates(inEceventTypeDescription, IArgoField.ECEVENT_TYPE_DESCRIPTION.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inUnitIdName, IArgoField.ECEVENT_UNIT_ID_NAME.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inWorkQueueName, IArgoField.ECEVENT_WORK_QUEUE.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inWiMoveKind, IArgoField.ECEVENT_MOVE_KIND.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inWaId, IArgoField.ECEVENT_WORK_ASSIGNMENT_ID.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inUnitReference, IArgoField.ECEVENT_UNIT_REFERENCE.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inTransactionId, IArgoField.ECEVENT_TRANSACTION_ID.toString());
            EcEvent.logErrorIfMayCauseDuplicates(locId, IArgoField.ECEVENT_LOC_ID.toString());
            EcEvent.logErrorIfMayCauseDuplicates(locSlot, IArgoField.ECEVENT_LOC_SLOT.toString());
            EcEvent.logErrorIfMayCauseDuplicates(operationalPosId, IArgoField.ECEVENT_POS_OPERATIONAL_POS_ID.toString());
            EcEvent.logErrorIfMayCauseDuplicates(inPowName, IArgoField.ECEVENT_POW_NAME.toString());
            EcEvent.logErrorIfMayCauseDuplicates(poolName, IArgoField.ECEVENT_POOL_NAME.toString());
            IDomainQuery domainQuery = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_YARD, (Object)inChe.getCheYard().getYrdGkey()));
            if (inEventTime != null) {
                domainQuery.addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TIMESTAMP, (Object)inEventTime));
            }
            domainQuery.addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TYPE, (Object)inEceventType)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_CHE_ID, (Object)inChe.getCheId())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_CHE_NAME, (Object)inChe.getCheShortName())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_OPERATOR_NAME, (Object)inChe.getCheLoginName())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_SUB_TYPE, (Object)inEceventSubType)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TYPE_DESCRIPTION, (Object)inEceventTypeDescription)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_FROM_CHE_ID_NAME, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TO_CHE_ID_NAME, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_UNIT_ID_NAME, (Object)inUnitIdName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_POW_NAME, (Object)inPowName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_POOL_NAME, (Object)poolName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_QUEUE, (Object)inWorkQueueName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_MOVE_KIND, (Object)inWiMoveKind)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_IS_TWIN_MOVE, (Object)inIsTwinMove)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_ASSIGNMENT_GKEY, (Object)inWaGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_ASSIGNMENT_ID, (Object)inWaId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_UNIT_REFERENCE, (Object)inUnitReference)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TRANSACTION_ID, (Object)inTransactionId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LOC_TYPE, (Object)((Object)locType))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LOC_ID, (Object)locId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LOC_SLOT, (Object)locSlot)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_POS_OPERATIONAL_POS_ID, (Object)operationalPosId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_UNLADEN_LOC_TYPE, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_UNLADEN_LOC_ID, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_UNLADEN_LOC_SLOT, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LADEN_LOC_TYPE, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LADEN_LOC_ID, null)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_LADEN_LOC_SLOT, null));
            List existingEcEvents = HibernateApi.getInstance().findEntitiesByDomainQuery(domainQuery);
            if (!existingEcEvents.isEmpty()) {
                EcEvent firstFoundExistingEcEvent = (EcEvent)existingEcEvents.get(0);
                if (LOGGER.isDebugEnabled() && existingEcEvents.size() > 1) {
                    LOGGER.debug((Object) String.format("duplicates suppressed; first of multiple matching EC events(%s) returned", existingEcEvents));
                }
                LogUtils.forceLogAtInfo((Logger)LOGGER, (Object) String.format("suppressing duplicate for EcEvent(%s)", firstFoundExistingEcEvent));
                return firstFoundExistingEcEvent;
            }
        }
        EcEvent ece = inEventTime != null ? new EcEvent(inEventTime) : new EcEvent();
        ece.setEceventType(inEceventType);
        ece.setEceventCheId(inChe.getCheId());
        ece.setEceventCheName(inChe.getCheShortName());
        ece.setEceventOperatorName(inChe.getCheLoginName());
        ece.setEceventSubType(inEceventSubType);
        ece.setEceventTypeDescription(inEceventTypeDescription);
        ece.setEceventUnitIdName(inUnitIdName);
        ece.setEceventPowName(inPowName);
        ece.setEceventPoolName(poolName);
        ece.setEceventWorkQueue(inWorkQueueName);
        ece.setEceventMoveKind(inWiMoveKind);
        ece.setEceventIsTwinMove(inIsTwinMove);
        ece.setEceventWorkAssignmentGkey(inWaGkey);
        ece.setEceventWorkAssignmentId(inWaId);
        ece.setEceventUnitReference(inUnitReference);
        ece.setEceventTransactionId(inTransactionId);
        ece.setEceventYard(inChe.getCheYard());
        ece.setEceventLocType(locType);
        ece.setEceventLocId(locId);
        ece.setEceventLocSlot(locSlot);
        ece.setEceventPosOperationalPosId(operationalPosId);
        HibernateApi.getInstance().save((Object)ece);
        return ece;
    }

    @Nullable
    public static EcEvent findByPkey(@NotNull Serializable inYardGkey, @Nullable Long inPkey) {
        return null;
    }

    @NotNull
    public static List<EcEvent> findByWaAndEventType(@NotNull Long inWaGkey, @NotNull String inEcEventType) {
        IDomainQuery ecEventQuery = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_ASSIGNMENT_GKEY, (Object)inWaGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TYPE_DESCRIPTION, (Object)inEcEventType)).addDqOrdering(Ordering.desc((IMetafieldId)IArgoField.ECEVENT_TIMESTAMP));
        List events = HibernateApi.getInstance().findEntitiesByDomainQuery(ecEventQuery);
        if (events == null) {
            return Collections.emptyList();
        }
        return events;
    }

    public static boolean isAnyEventMatchingWaAndEventType(@NotNull Long inWaGkey, @NotNull String inEcEventType) {
        IDomainQuery ecEventQuery = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_ASSIGNMENT_GKEY, (Object)inWaGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TYPE_DESCRIPTION, (Object)inEcEventType));
        return HibernateApi.getInstance().existsByDomainQuery(ecEventQuery);
    }

    @Nullable
    public static EcEvent findEarliestEcEventOfType(String inCheName, String inEcEventType, Long inWaGkey) {
        return EcEvent.findEcEventOfType(inCheName, inEcEventType, inWaGkey, Ordering.asc((IMetafieldId)IArgoField.ECEVENT_TIMESTAMP));
    }

    @Nullable
    public static EcEvent findLatestEcEventOfType(String inCheName, String inEcEventType, Long inWaGkey) {
        return EcEvent.findEcEventOfType(inCheName, inEcEventType, inWaGkey, Ordering.desc((IMetafieldId)IArgoField.ECEVENT_TIMESTAMP));
    }

    @Nullable
    public static EcEvent findEcEventOfType(String inCheName, String inEcEventType, Long inWaGkey, @NotNull Ordering inDqOrdering) {
        IDomainQuery ecEventQuery = QueryUtils.createDomainQuery((String)"EcEvent").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_CHE_NAME, (Object)inCheName)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_TYPE_DESCRIPTION, (Object)inEcEventType)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.ECEVENT_WORK_ASSIGNMENT_GKEY, (Object)inWaGkey)).addDqOrdering(inDqOrdering);
        ecEventQuery.setMaxResults(1);
        EcEvent ecEvent = (EcEvent)HibernateApi.getInstance().getUniqueEntityByDomainQuery(ecEventQuery);
        if (ecEvent == null) {
            LOGGER.debug((Object)("There is no EcEvent that match Che(" + inCheName + "), Type(" + inEcEventType + "), and Wa(" + inWaGkey + ")"));
        }
        return ecEvent;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public Object clone() throws CloneNotSupportedException {
        EcEvent newEvent = (EcEvent)super.clone();
        newEvent.setEceventGkey(null);
        return newEvent;
    }

    @Override
    @Nullable
    public Long getEceventXpsUnladenLocType() {
        return 0L;
    }

    @Override
    @Nullable
    public Long getEceventXpsLocType() {
        return 0L;
    }

    @Override
    @Nullable
    public Long getEceventXpsLadenLocType() {
        return 0L;
    }

    @Override
    public void setEceventXpsLocType(@Nullable Long inXpsLadenLocType) {
    }

    @Override
    public void setEceventXpsUnladenLocType(@Nullable Long inXpsLadenLocType) {
    }

    @Override
    public void setEceventXpsLadenLocType(@Nullable Long inXpsLadenLocType) {
    }

    public String toString() {
        StringBuilder ecEventStr = new StringBuilder("EcEvent[" + this.getEceventGkey());
        if (this.getEceventCheName() != null) {
            ecEventStr.append(":" + this.getEceventCheName());
        }
        if (this.getEceventTypeDescription() != null) {
            ecEventStr.append(":" + this.getEceventTypeDescription());
        }
        if (this.getEceventUnitIdName() != null) {
            ecEventStr.append(":" + this.getEceventUnitIdName());
        }
        return ecEventStr.append("]").toString();
    }

    private static void logErrorIfMayCauseDuplicates(@Nullable String inFieldValue, @NotNull String inFieldName) {
        if (inFieldValue == null) {
            return;
        }
        if (inFieldValue.isEmpty()) {
            LOGGER.error((Object) String.format("the field(%s) has an empty string value, which may result in duplicate entries", inFieldName));
            return;
        }
    }
}
