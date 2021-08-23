package com.zpmc.ztos.infra.base.business.plans;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.WorkShiftDO;
import com.zpmc.ztos.infra.base.business.enums.argo.WsResourceAssignmentEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WsWorkStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.xps.AtomizedEnumXpsConverter;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorkShift extends WorkShiftDO {

    private static final WsWorkStateEnum WORKSTATEENUM_INITIAL = WsWorkStateEnum.UNINITIALIZED;
    private static final Long WORKSTATEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(WORKSTATEENUM_INITIAL, WsWorkStateEnum.getEnumList(), null);
    private static final WsResourceAssignmentEnum SHIFTRTGASSIGNMENTENUM_INITIAL = WsResourceAssignmentEnum.POOLED;
    private static final WsResourceAssignmentEnum SHIFTFORKASSIGNMENTENUM_INITIAL = WsResourceAssignmentEnum.POOLED;
    private static final Long SHIFTRTGASSIGNMENTXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(SHIFTRTGASSIGNMENTENUM_INITIAL, WsResourceAssignmentEnum.getEnumList(), null);
    private static final Long SHIFTFORKASSIGNMENTXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(SHIFTFORKASSIGNMENTENUM_INITIAL, WsResourceAssignmentEnum.getEnumList(), null);
    private static final Logger LOGGER = Logger.getLogger(WorkShift.class);

    public WorkShift() {
        this.setWorkshiftShiftRTGAssignmentEnum(SHIFTRTGASSIGNMENTENUM_INITIAL);
        this.setWorkshiftShiftRTGAssignment(SHIFTRTGASSIGNMENTXPS_INITIAL);
        this.setWorkshiftShiftForkAssignmentEnum(SHIFTFORKASSIGNMENTENUM_INITIAL);
        this.setWorkshiftShiftForkAssignment(SHIFTFORKASSIGNMENTXPS_INITIAL);
        this.setWorkshiftWorkStateEnum(WORKSTATEENUM_INITIAL);
        this.setWorkshiftWorkState(WORKSTATEXPS_INITIAL);
    }

    public void setWorkshiftShiftRTGAssignmentEnumFromXps() {
        this.setWorkshiftShiftRTGAssignmentEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getWorkshiftShiftRTGAssignment(), WsResourceAssignmentEnum.getEnumList(), SHIFTRTGASSIGNMENTENUM_INITIAL));
    }

    public void setWorkshiftShiftRTGAssignmentXpsFromEnum() {
        this.setWorkshiftShiftRTGAssignment(AtomizedEnumXpsConverter.findXpsOrdinal(this.getWorkshiftShiftRTGAssignmentEnum(), WsResourceAssignmentEnum.getEnumList(), SHIFTRTGASSIGNMENTXPS_INITIAL));
    }

    public void setWorkshiftShiftForkAssignmentEnumFromXps() {
        this.setWorkshiftShiftForkAssignmentEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getWorkshiftShiftForkAssignment(), WsResourceAssignmentEnum.getEnumList(), SHIFTFORKASSIGNMENTENUM_INITIAL));
    }

    public void setWorkshiftShiftForkAssignmentXpsFromEnum() {
        this.setWorkshiftShiftForkAssignment(AtomizedEnumXpsConverter.findXpsOrdinal(this.getWorkshiftShiftForkAssignmentEnum(), WsResourceAssignmentEnum.getEnumList(), SHIFTFORKASSIGNMENTXPS_INITIAL));
    }

    public void setWorkshiftWorkStateEnumFromXps() {
        this.setWorkshiftWorkStateEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getWorkshiftWorkState(), WsWorkStateEnum.getEnumList(), WORKSTATEENUM_INITIAL));
    }

    public void setWorkshiftWorkStateXpsFromEnum() {
        this.setWorkshiftWorkState(AtomizedEnumXpsConverter.findXpsOrdinal(this.getWorkshiftWorkStateEnum(), WsWorkStateEnum.getEnumList(), WORKSTATEXPS_INITIAL));
    }

    public void setWorkshiftOwnerPowFromPkey() {
        this.setWorkshiftOwnerPow(this.getOwnerPowFromOwnerPowKey());
    }

    public void setWorkshiftNextShiftFromPkey() {
        this.setWorkshiftNextShift(this.getNextShiftFromNextShiftKey());
    }

    public void updateWorkshiftOwnerPow(PointOfWork inOwnerPow) {
        this.setWorkshiftOwnerPow(inOwnerPow);
    }

    public void updateWorkShiftNextShift(WorkShift inNextShift) {
        this.setWorkshiftNextShift(inNextShift);
    }

    public void preProcessInsert(@NotNull FieldChanges inOutMoreChanges) {
        Long csNextShiftKey;
        super.preProcessInsert(inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_SHIFT_R_T_G_ASSIGNMENT_ENUM, IArgoField.WORKSHIFT_SHIFT_R_T_G_ASSIGNMENT, WsResourceAssignmentEnum.class, SHIFTRTGASSIGNMENTENUM_INITIAL, SHIFTRTGASSIGNMENTXPS_INITIAL, WsResourceAssignmentEnum.getEnumList(), inOutMoreChanges, this.getWorkshiftShiftRTGAssignmentEnum(), this.getWorkshiftShiftRTGAssignment());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_SHIFT_FORK_ASSIGNMENT_ENUM, IArgoField.WORKSHIFT_SHIFT_FORK_ASSIGNMENT, WsResourceAssignmentEnum.class, SHIFTFORKASSIGNMENTENUM_INITIAL, SHIFTFORKASSIGNMENTXPS_INITIAL, WsResourceAssignmentEnum.getEnumList(), inOutMoreChanges, this.getWorkshiftShiftForkAssignmentEnum(), this.getWorkshiftShiftForkAssignment());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_WORK_STATE_ENUM, IArgoField.WORKSHIFT_WORK_STATE, WsWorkStateEnum.class, WORKSTATEENUM_INITIAL, WORKSTATEXPS_INITIAL, WsWorkStateEnum.getEnumList(), inOutMoreChanges, this.getWorkshiftWorkStateEnum(), this.getWorkshiftWorkState());
        Long csOwnerPowKey = this.getWorkshiftOwnerPowPkey();
        if (csOwnerPowKey == null || csOwnerPowKey == 0L) {
            if (this.getWorkshiftOwnerPow() != null) {
                this.setSelfAndFieldChange(IArgoField.WORKSHIFT_OWNER_POW_PKEY, this.getWorkshiftOwnerPow().getPointofworkPkey(), inOutMoreChanges);
            }
        } else if (this.getWorkshiftOwnerPow() == null) {
            this.setSelfAndFieldChange(IArgoField.WORKSHIFT_OWNER_POW, this.getOwnerPowFromOwnerPowKey(), inOutMoreChanges);
        }
        if ((csNextShiftKey = this.getWorkshiftNextShiftPkey()) == null || csNextShiftKey == 0L) {
            if (this.getWorkshiftNextShift() != null) {
                this.setSelfAndFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT_PKEY, this.getWorkshiftNextShift().getWorkshiftPkey(), inOutMoreChanges);
            }
        } else if (this.getWorkshiftNextShift() == null) {
            this.setSelfAndFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT, this.getNextShiftFromNextShiftKey(), inOutMoreChanges);
        }
    }

    public void preProcessUpdate(@NotNull FieldChanges inChanges, @NotNull FieldChanges inOutMoreChanges) {
        Long newPkey;
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_SHIFT_R_T_G_ASSIGNMENT_ENUM, IArgoField.WORKSHIFT_SHIFT_R_T_G_ASSIGNMENT, WsResourceAssignmentEnum.class, SHIFTRTGASSIGNMENTENUM_INITIAL, SHIFTRTGASSIGNMENTXPS_INITIAL, WsResourceAssignmentEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_SHIFT_FORK_ASSIGNMENT_ENUM, IArgoField.WORKSHIFT_SHIFT_FORK_ASSIGNMENT, WsResourceAssignmentEnum.class, SHIFTFORKASSIGNMENTENUM_INITIAL, SHIFTFORKASSIGNMENTXPS_INITIAL, WsResourceAssignmentEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.WORKSHIFT_WORK_STATE_ENUM, IArgoField.WORKSHIFT_WORK_STATE, WsWorkStateEnum.class, WORKSTATEENUM_INITIAL, WORKSTATEXPS_INITIAL, WsWorkStateEnum.getEnumList(), inOutMoreChanges, inChanges);
        if (inChanges.hasFieldChange(IArgoField.WORKSHIFT_OWNER_POW_PKEY)) {
            if (!inChanges.hasFieldChange(IArgoField.WORKSHIFT_OWNER_POW)) {
                Long newPowKey = (Long)inChanges.getFieldChange(IArgoField.WORKSHIFT_OWNER_POW_PKEY).getNewValue();
                PointOfWork pow = PointOfWork.findByPkey(this.getYardKeyConsideringChanges(inChanges), newPowKey);
                this.setSelfAndFieldChange(IArgoField.WORKSHIFT_OWNER_POW, pow, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.WORKSHIFT_OWNER_POW)) {
            PointOfWork pow = (PointOfWork)inChanges.getFieldChange(IArgoField.WORKSHIFT_OWNER_POW).getNewValue();
            newPkey = pow == null ? null : pow.getPointofworkPkey();
            this.setSelfAndFieldChange(IArgoField.WORKSHIFT_OWNER_POW_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT_PKEY)) {
            if (!inChanges.hasFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT)) {
                Long newWorkShiftKey = (Long)inChanges.getFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT_PKEY).getNewValue();
                WorkShift cs = WorkShift.findByPkey(this.getYardKeyConsideringChanges(inChanges), newWorkShiftKey);
                this.setSelfAndFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT, cs, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT)) {
            WorkShift cs = (WorkShift)inChanges.getFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT).getNewValue();
            newPkey = cs == null ? null : cs.getWorkshiftPkey();
            this.setSelfAndFieldChange(IArgoField.WORKSHIFT_NEXT_SHIFT_PKEY, newPkey, inOutMoreChanges);
        }
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @Nullable
    public static WorkShift findByPkey(Serializable inYardGkey, Long inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkShift").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_YARD, (Object)inYardGkey));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() > 1) {
            LOGGER.error((Object)(matches.size() + " WorkShift found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardGkey + ")"));
            Collections.sort(matches, new Comparator<WorkShift>(){

                @Override
                public int compare(WorkShift inShift1, WorkShift inShift2) {
                    return inShift2.getWorkshiftGkey().compareTo(inShift1.getWorkshiftGkey());
                }
            });
        }
        return matches.isEmpty() ? null : (WorkShift)matches.get(0);
    }

//    public static WorkShift findByCraneActivity(CraneActivity inCraneActivity) {
//        return WorkShift.findByPkey(inCraneActivity.getCraneactivityYard().getYrdGkey(), inCraneActivity.getCraneactivityPositioningObjPkey());
//    }

    public static List<WorkShift> findByVesselVisitRefAndChePow(String inVslVstRef, @NotNull String inChePowName) {
        Long powPkey = 0L;
        IDomainQuery dqPow = QueryUtils.createDomainQuery((String)"PointOfWork").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_NAME, (Object)inChePowName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_YARD, (Object) ContextHelper.getThreadYard().getYrdGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_LIFE_CYCLE_STATE, (Object) LifeCycleStateEnum.ACTIVE)).addDqField(IArgoField.POINTOFWORK_PKEY);
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dqPow);
        if (qr.getTotalResultCount() > 0) {
            powPkey = (Long)qr.getValue(0, IArgoField.POINTOFWORK_PKEY);
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkShift").addDqPredicate((IPredicate)PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_VISIT, (Object)inVslVstRef)).add(PredicateFactory.like((IMetafieldId) IArgoField.WORKSHIFT_VISIT, (String)(inVslVstRef + ".%")))).addDqOrdering(Ordering.asc((IMetafieldId) IArgoField.WORKSHIFT_START_TIME)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.WORKSHIFT_OWNER_POW_PKEY, (Object)powPkey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    private Long getYardKeyConsideringChanges(@NotNull FieldChanges inChanges) {
        Yard yard = this.getYardConsideringChanges(inChanges);
        return yard == null ? null : yard.getYrdGkey();
    }

    @Nullable
    private Yard getYardConsideringChanges(@NotNull FieldChanges inChanges) {
        return inChanges.hasFieldChange(IArgoField.WORKSHIFT_YARD) ? (Yard)inChanges.getFieldChange(IArgoField.WORKSHIFT_YARD).getNewValue() : this.getWorkshiftYard();
    }

    @Nullable
    private PointOfWork getOwnerPowFromOwnerPowKey() {
        return PointOfWork.findByPkey(this.getWorkshiftYard().getYrdGkey(), this.getWorkshiftOwnerPowPkey());
    }

    @Nullable
    private WorkShift getNextShiftFromNextShiftKey() {
        return WorkShift.findByPkey(this.getWorkshiftYard().getYrdGkey(), this.getWorkshiftNextShiftPkey());
    }
}
