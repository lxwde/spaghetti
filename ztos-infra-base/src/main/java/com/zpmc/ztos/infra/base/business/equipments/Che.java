package com.zpmc.ztos.infra.base.business.equipments;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.CheDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.xps.AtomizedEnumXpsConverter;
import com.zpmc.ztos.infra.base.business.enums.xps.HasTrailerEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.business.xps.XpsPkeyGenerator;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class Che extends CheDO implements IAuditable,
        IObsoleteable,
        INaturallyKeyedEntity,
        IServiceable {
    private static final CheJobStepStateEnum CHEJOBSTEPSTATEENUM_INITIAL = CheJobStepStateEnum.IDLE;
    private static final Long CHEJOBSTEPSTATEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHEJOBSTEPSTATEENUM_INITIAL, CheJobStepStateEnum.getEnumList(), null);
    private static final CheStatusEnum CHESTATUSENUM_INITIAL = CheStatusEnum.UNKNOWN;
    private static final Long CHESTATUSXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHESTATUSENUM_INITIAL, CheStatusEnum.getEnumList(), null);
    private static final CheTalkStatusEnum CHETALKSTATUSENUM_INITIAL = CheTalkStatusEnum.NEEDSCONFIRM;
    private static final Long CHETALKSTATUSXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHETALKSTATUSENUM_INITIAL, CheTalkStatusEnum.getEnumList(), null);
    private static final CheMessageStatusEnum CHEMESSAGESTATUSENUM_INITIAL = CheMessageStatusEnum.NEEDSCONFIRM;
    private static final Long CHEMESSAGESTATUSXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHEMESSAGESTATUSENUM_INITIAL, CheMessageStatusEnum.getEnumList(), null);
    private static final CheKindEnum CHEKINDENUM_INITIAL = CheKindEnum.UNKN;
    private static final Long CHEKINDXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHEKINDENUM_INITIAL, CheKindEnum.getEnumList(), null);
    private static final CheOperatingModeEnum CHEOPERATINGMODEENUM_INITIAL = CheOperatingModeEnum.UNKNOWN;
    private static final Long CHEOPERATINGMODEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHEOPERATINGMODEENUM_INITIAL, CheOperatingModeEnum.getEnumList(), null);
    private static final CheAssistStateEnum CHEASSISTSTATEENUM_INITIAL = CheAssistStateEnum.NONEED;
    private static final Long CHEASSISTSTATEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(CHEASSISTSTATEENUM_INITIAL, CheAssistStateEnum.getEnumList(), null);
    private static final Logger LOGGER = Logger.getLogger(Che.class);
    private static final String NATURAL_KEY_DELIMITER = "/";

    public Che() {
        this.setXpeCheLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setCheStatusEnum(CHESTATUSENUM_INITIAL);
        this.setCheStatus(CHESTATUSXPS_INITIAL);
        this.setCheMessageStatusEnum(CHEMESSAGESTATUSENUM_INITIAL);
        this.setCheMessageStatus(CHEMESSAGESTATUSXPS_INITIAL);
        this.setCheTalkStatusEnum(CHETALKSTATUSENUM_INITIAL);
        this.setCheTalkStatus(CHETALKSTATUSXPS_INITIAL);
        this.setCheKindEnum(CHEKINDENUM_INITIAL);
        this.setCheKind(CHEKINDXPS_INITIAL);
        this.setCheOperatingModeEnum(CHEOPERATINGMODEENUM_INITIAL);
        this.setCheOperatingMode(CHEOPERATINGMODEXPS_INITIAL);
        this.setCheAssistStateEnum(CHEASSISTSTATEENUM_INITIAL);
        this.setCheAssistState(CHEASSISTSTATEXPS_INITIAL);
        this.setCheJobStepStateEnum(CHEJOBSTEPSTATEENUM_INITIAL);
        this.setCheJobStepState(CHEJOBSTEPSTATEXPS_INITIAL);
        this.setCheLastKnownLocPos(LocPosition.getUnknownPosition());
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public static Che findOrCreateChe(Long inCheId, Yard inYard) {
        Che che = Che.findChe(inCheId, inYard);
        if (che == null) {
            che = new Che();
            che.setCheYard(inYard);
            che.setChePkey(0L);
            che.setCheId(inCheId);
            HibernateApi.getInstance().save((Object)che);
        }
        return che;
    }

    public static Che findOrCreateChe(String inCheShortName, Yard inYard, boolean inAssignIdOnCreate) {
        Che che = Che.findCheByShortName(inCheShortName, inYard);
        if (che == null) {
            che = new Che();
            che.setCheYard(inYard);
            che.setChePkey(Che.getChePkey(inYard.getYrdGkey()));
            if (inAssignIdOnCreate) {
                che.setCheId(Che.findMaxCheIdInYard(inYard) + 1L);
            }
            che.setCheShortName(inCheShortName);
            HibernateApi.getInstance().save((Object)che);
        }
        return che;
    }

    private static Long getChePkey(Long inYrdGkey) {
        return XpsPkeyGenerator.generate((String)"Che", (IMetafieldId) IArgoField.CHE_PKEY, (IMetafieldId) IArgoField.CHE_YARD, (Serializable)inYrdGkey);
    }

    private static Long findMaxCheIdInYard(Yard inYard) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqAggregateField(AggregateFunctionType.MAX, IArgoField.CHE_ID).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_YARD, (Object)inYard.getYrdGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        Long max = (Long)qr.getValue(0, IArgoField.CHE_ID);
        if (max == null) {
            return 0L;
        }
        return max;
    }

    public boolean isItv() {
        return Che.isAutomatedItv(this.getCheKindEnum()) || CheKindEnum.ITV.equals((Object)this.getCheKindEnum());
    }

    public boolean isAutomatedChe() {
        return Che.isAutomatedChe(this.getCheKindEnum());
    }

    public boolean isAutomatedItv() {
        return Che.isAutomatedItv(this.getCheKindEnum());
    }

    public boolean isAutomatedLiftItv() {
        return Che.isAutomatedLiftItv(this.getCheKindEnum());
    }

    public static boolean isAutomatedChe(@Nullable CheKindEnum inCheKind) {
        return CheKindEnum.ASC.equals((Object)inCheKind) || Che.isAutomatedItv(inCheKind);
    }

    public static boolean isAutomatedItv(@Nullable CheKindEnum inCheKind) {
        return CheKindEnum.AGV.equals((Object)inCheKind) || CheKindEnum.ASH.equals((Object)inCheKind);
    }

    public boolean isCheOfKind(@NotNull CheKindEnum inCheKind) {
        return this.getCheKindEnum().equals((Object)inCheKind);
    }

    public static boolean isAutomatedLiftItv(@Nullable CheKindEnum inCheKind) {
        return CheKindEnum.ASH.equals((Object)inCheKind);
    }

    public void updateCheStatusEnum(@Nullable CheStatusEnum inCheFutureStatusEnum, @Nullable Date inCheLastTime) {
        CheStatusEnum currentCheStatusEnum = this.getCheStatusEnum();
        if (inCheFutureStatusEnum == null || inCheFutureStatusEnum == currentCheStatusEnum) {
            return;
        }
        boolean cheBecameAvailable = Che.cheStatusBecomeAvailable(currentCheStatusEnum, inCheFutureStatusEnum);
        boolean cheBecameUnavailable = Che.cheStatusBecomesUnavailable(currentCheStatusEnum, inCheFutureStatusEnum);
        if (cheBecameAvailable || cheBecameUnavailable) {
            this.setCheTimeAvailableUnavailable(inCheLastTime != null ? inCheLastTime : new Date());
        }
        this.setCheStatusEnum(inCheFutureStatusEnum);
    }

    static boolean cheStatusBecomeAvailable(CheStatusEnum inCurrentStatus, CheStatusEnum inFutureStatus) {
        if (inCurrentStatus != inFutureStatus) {
            return CheStatusEnum.WORKING.equals((Object)inFutureStatus);
        }
        return false;
    }

    static boolean cheStatusBecomesUnavailable(CheStatusEnum inCurrentStatus, CheStatusEnum inFutureStatus) {
        if (inCurrentStatus != inFutureStatus) {
            return CheStatusEnum.WORKING.equals((Object)inCurrentStatus);
        }
        return false;
    }

    public boolean isUtrChe() {
        return this.isItv() && !this.isAutomatedChe();
    }

    @NotNull
    public static CheKindEnum[] getAutomatedItvCheKinds() {
        return new CheKindEnum[]{CheKindEnum.AGV, CheKindEnum.ASH};
    }

    @Nullable
    public static Che findChe(Long inCheId, @Nullable Yard inCheYard) {
        if (inCheId == null) {
            return null;
        }
        Che tempChe = new Che();
        tempChe.setCheId(inCheId);
        tempChe.setCheYard(inCheYard);
        return (Che)tempChe.findByNaturalKey(tempChe.getNaturalKey());
    }

    @Nullable
    public static Che findCheByShortNameProxy(String inShortName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_SHORT_NAME, (Object)inShortName));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        Serializable[] cheGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (cheGkey == null || cheGkey.length == 0) {
            return null;
        }
        if (cheGkey.length == 1) {
            return (Che)HibernateApi.getInstance().load(Che.class, cheGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.XPE_CHE_NON_UNIQUE, null, (Object)cheGkey.length);
    }

    @Nullable
    public static Che findCheByShortName(String inShortName, @NotNull Yard inYard) {
        return Che.findCheByNameAndKind(inShortName, inYard, null);
    }

    @Nullable
    public static Che findCheByNameAndKind(@NotNull String inShortName, @NotNull Yard inYard, @Nullable CheKindEnum inCheKindEnum) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_SHORT_NAME, (Object)inShortName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_YARD, (Object)inYard.getYrdGkey()));
        if (inCheKindEnum != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_KIND_ENUM, (Object)((Object)inCheKindEnum)));
        }
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        Serializable[] cheGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (cheGkey == null || cheGkey.length == 0) {
            return null;
        }
        if (cheGkey.length == 1) {
            return (Che)HibernateApi.getInstance().load(Che.class, cheGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IArgoPropertyKeys.XPE_CHE_NON_UNIQUE, null, (Object)cheGkey.length);
    }

    public static Che resolveCheFromPow(PointOfWork inPow) {
        return Che.findChe(inPow.getPointofworkCheId(), inPow.getPointofworkYard());
    }

    public void updateHasActiveAlarm(Boolean inActive) {
        this.setCheHasActiveAlarm(inActive);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setXpeCheLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getXpeCheLifeCycleState();
    }

    @Nullable
    public String getNaturalKey() {
        Object value1 = this.getFieldValue(IArgoField.CHE_ID);
        Object value2 = this.getFieldValue(IArgoField.CHE_YARD);
        StringBuilder cheNaturalKey = new StringBuilder();
        if (value1 != null) {
            cheNaturalKey.append(value1.toString());
        }
        if (value2 != null) {
            cheNaturalKey.append(NATURAL_KEY_DELIMITER);
            cheNaturalKey.append(value2.toString());
        }
        return cheNaturalKey.toString();
    }

    @Nullable
    public INaturallyKeyedEntity findByNaturalKey(String inNaturalKey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName());
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_ID, (Object) Che.getComponentOfNaturalKey(inNaturalKey, 0)));
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_YARD, (Object) Che.getComponentOfNaturalKey(inNaturalKey, 1)));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() > 1) {
            LOGGER.debug((Object)(matches.size() + " " + this.toString() + " found matching natural key " + inNaturalKey + " where 1 was expected."));
        }
        Collections.sort(matches, new GKeyComparator());
        return (INaturallyKeyedEntity)matches.get(0);
    }

    public void preProcessInsert(@NotNull FieldChanges inOutMoreChanges) {
        Long cheLastLandedChePkey;
        Long cheAssignedChePkey;
        Long chePow;
        super.preProcessInsert(inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_JOB_STEP_STATE_ENUM, IArgoField.CHE_JOB_STEP_STATE, CheJobStepStateEnum.class, CHEJOBSTEPSTATEENUM_INITIAL, CHEJOBSTEPSTATEXPS_INITIAL, CheJobStepStateEnum.getEnumList(), inOutMoreChanges, this.getCheJobStepStateEnum(), this.getCheJobStepState());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_STATUS_ENUM, IArgoField.CHE_STATUS, CheStatusEnum.class, CHESTATUSENUM_INITIAL, CHESTATUSXPS_INITIAL, CheStatusEnum.getEnumList(), inOutMoreChanges, this.getCheStatusEnum(), this.getCheStatus());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_MESSAGE_STATUS_ENUM, IArgoField.CHE_MESSAGE_STATUS, CheMessageStatusEnum.class, CHEMESSAGESTATUSENUM_INITIAL, CHEMESSAGESTATUSXPS_INITIAL, CheMessageStatusEnum.getEnumList(), inOutMoreChanges, this.getCheMessageStatusEnum(), this.getCheMessageStatus());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_TALK_STATUS_ENUM, IArgoField.CHE_TALK_STATUS, CheTalkStatusEnum.class, CHETALKSTATUSENUM_INITIAL, CHETALKSTATUSXPS_INITIAL, CheTalkStatusEnum.getEnumList(), inOutMoreChanges, this.getCheTalkStatusEnum(), this.getCheTalkStatus());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_KIND_ENUM, IArgoField.CHE_KIND, CheKindEnum.class, CHEKINDENUM_INITIAL, CHEKINDXPS_INITIAL, CheKindEnum.getEnumList(), inOutMoreChanges, this.getCheKindEnum(), this.getCheKind());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_OPERATING_MODE_ENUM, IArgoField.CHE_OPERATING_MODE, CheOperatingModeEnum.class, CHEOPERATINGMODEENUM_INITIAL, CHEOPERATINGMODEXPS_INITIAL, CheOperatingModeEnum.getEnumList(), inOutMoreChanges, this.getCheOperatingModeEnum(), this.getCheOperatingMode());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_ASSIST_STATE_ENUM, IArgoField.CHE_ASSIST_STATE, CheAssistStateEnum.class, CHEASSISTSTATEENUM_INITIAL, CHEASSISTSTATEXPS_INITIAL, CheAssistStateEnum.getEnumList(), inOutMoreChanges, this.getCheAssistStateEnum(), this.getCheAssistState());
        Long chePoolPkey = this.getChePoolPkey();
        if (chePoolPkey == null || chePoolPkey == 0L) {
            if (this.getChePool() != null) {
                this.setSelfAndFieldChange(IArgoField.CHE_POOL_PKEY, this.getChePool().getPoolPkey(), inOutMoreChanges);
            }
        } else if (this.getChePool() == null) {
            this.setSelfAndFieldChange(IArgoField.CHE_POOL, this.getPoolFromPoolKey(), inOutMoreChanges);
        }
        if ((chePow = this.getChePowPkey()) == null || chePow == 0L) {
            if (this.getChePointOfWork() != null) {
                this.setSelfAndFieldChange(IArgoField.CHE_POW_PKEY, this.getChePointOfWork().getPointofworkPkey(), inOutMoreChanges);
            }
        } else if (this.getChePointOfWork() == null) {
            this.setSelfAndFieldChange(IArgoField.CHE_POINT_OF_WORK, this.getPowFromPowKey(), inOutMoreChanges);
        }
        if ((cheAssignedChePkey = this.getCheAssignedCheId()) == null || cheAssignedChePkey == 0L) {
            if (this.getCheAssignedChe() != null) {
                this.setSelfAndFieldChange(IArgoField.CHE_ASSIGNED_CHE_ID, this.getCheAssignedChe().getChePkey(), inOutMoreChanges);
            }
        } else if (this.getCheAssignedChe() == null) {
            this.setSelfAndFieldChange(IArgoField.CHE_ASSIGNED_CHE, this.getAssignedCheFromCheId(), inOutMoreChanges);
        }
        if ((cheLastLandedChePkey = this.getCheClerkLastLandedCheId()) == null || cheLastLandedChePkey == 0L) {
            if (this.getCheClerkLastLandedChe() != null) {
                this.setSelfAndFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE_ID, this.getCheClerkLastLandedChe().getChePkey(), inOutMoreChanges);
            }
        } else if (this.getCheClerkLastLandedChe() == null) {
            this.setSelfAndFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE, this.getClerkLastLandedCheFromId(), inOutMoreChanges);
        }
    }

    public void preProcessUpdate(@NotNull FieldChanges inChanges, @NotNull FieldChanges inOutMoreChanges) {
        Long newCheId;
        Long newPkey;
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_JOB_STEP_STATE_ENUM, IArgoField.CHE_JOB_STEP_STATE, CheJobStepStateEnum.class, CHEJOBSTEPSTATEENUM_INITIAL, CHEJOBSTEPSTATEXPS_INITIAL, CheJobStepStateEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_STATUS_ENUM, IArgoField.CHE_STATUS, CheStatusEnum.class, CHESTATUSENUM_INITIAL, CHESTATUSXPS_INITIAL, CheStatusEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_MESSAGE_STATUS_ENUM, IArgoField.CHE_MESSAGE_STATUS, CheMessageStatusEnum.class, CHEMESSAGESTATUSENUM_INITIAL, CHEMESSAGESTATUSXPS_INITIAL, CheMessageStatusEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_TALK_STATUS_ENUM, IArgoField.CHE_TALK_STATUS, CheTalkStatusEnum.class, CHETALKSTATUSENUM_INITIAL, CHETALKSTATUSXPS_INITIAL, CheTalkStatusEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_KIND_ENUM, IArgoField.CHE_KIND, CheKindEnum.class, CHEKINDENUM_INITIAL, CHEKINDXPS_INITIAL, CheKindEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_OPERATING_MODE_ENUM, IArgoField.CHE_OPERATING_MODE, CheOperatingModeEnum.class, CHEOPERATINGMODEENUM_INITIAL, CHEOPERATINGMODEXPS_INITIAL, CheOperatingModeEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(this, IArgoField.CHE_ASSIST_STATE_ENUM, IArgoField.CHE_ASSIST_STATE, CheAssistStateEnum.class, CHEASSISTSTATEENUM_INITIAL, CHEASSISTSTATEXPS_INITIAL, CheAssistStateEnum.getEnumList(), inOutMoreChanges, inChanges);
        if (inChanges.hasFieldChange(IArgoField.CHE_POOL_PKEY)) {
            if (!inChanges.hasFieldChange(IArgoField.CHE_POOL)) {
                Long newPoolPkey = (Long)inChanges.getFieldChange(IArgoField.CHE_POOL_PKEY).getNewValue();
                ChePool newPool = ChePool.findByPkey(this.getYardKeyConsideringChanges(inChanges), newPoolPkey);
                this.setSelfAndFieldChange(IArgoField.CHE_POOL, newPool, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.CHE_POOL)) {
            ChePool pool = (ChePool)inChanges.getFieldChange(IArgoField.CHE_POOL).getNewValue();
            newPkey = pool == null ? null : pool.getPoolPkey();
            this.setSelfAndFieldChange(IArgoField.CHE_POOL_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.CHE_POW_PKEY)) {
            if (!inChanges.hasFieldChange(IArgoField.CHE_POINT_OF_WORK)) {
                Long newPowPkey = (Long)inChanges.getFieldChange(IArgoField.CHE_POW_PKEY).getNewValue();
                PointOfWork newPow = PointOfWork.findByPkey(this.getYardKeyConsideringChanges(inChanges), newPowPkey);
                this.setSelfAndFieldChange(IArgoField.CHE_POINT_OF_WORK, newPow, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.CHE_POINT_OF_WORK)) {
            PointOfWork newPow = (PointOfWork)inChanges.getFieldChange(IArgoField.CHE_POINT_OF_WORK).getNewValue();
            newPkey = newPow == null ? null : newPow.getPointofworkPkey();
            this.setSelfAndFieldChange(IArgoField.CHE_POW_PKEY, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.CHE_ASSIGNED_CHE_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.CHE_ASSIGNED_CHE)) {
                Long newAssignedCheId = (Long)inChanges.getFieldChange(IArgoField.CHE_ASSIGNED_CHE_ID).getNewValue();
                Che newAssignedChe = Che.findChe(newAssignedCheId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.CHE_ASSIGNED_CHE, newAssignedChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.CHE_ASSIGNED_CHE)) {
            Che newAssignedChe = (Che)inChanges.getFieldChange(IArgoField.CHE_ASSIGNED_CHE).getNewValue();
            newCheId = newAssignedChe == null ? null : newAssignedChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.CHE_ASSIGNED_CHE_ID, newCheId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE)) {
                Long newLandedCheId = (Long)inChanges.getFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE_ID).getNewValue();
                Che newLandedChe = Che.findChe(newLandedCheId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE, newLandedChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE)) {
            Che newLandedChe = (Che)inChanges.getFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE).getNewValue();
            newCheId = newLandedChe == null ? null : newLandedChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.CHE_CLERK_LAST_LANDED_CHE_ID, newCheId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.CHE_SUSPEND_AUTO_DISPATCH) && this.isAutomatedItv()) {
            FieldChange fc = inChanges.getFieldChange(IArgoField.CHE_SUSPEND_AUTO_DISPATCH);
            Boolean priorValue = (Boolean)fc.getPriorValue();
            Boolean newValue = (Boolean)fc.getNewValue();
            if (Boolean.TRUE.equals(priorValue) && Boolean.FALSE.equals(newValue)) {
                this.invokeRequestLadenDispOrMakeItvIdle(this);
            }
        }
        this.updateEnergyStateFromEnergyLevelIfNecessary(inChanges, inOutMoreChanges);
    }

    private void invokeRequestLadenDispOrMakeItvIdle(@NotNull Che inChe) {
//        String jobName = "REQUEST_LADEN_DISPATCH_OR_MAKE_ITV_IDLE" + UUID.randomUUID() + inChe.getCheId();
//        QuartzJobDetail jobDetail = new QuartzJobDetail(jobName, RequestLadenDispatchOrMakeItvIdleJob.class);
//        jobDetail.setJobParm("CHE_GKEY", (Object)inChe.getCheGkey());
//        jobDetail.setJobParm("USER_CONTEXT", (Object)ContextHelper.getThreadUserContext());
//        jobDetail.setJobClustered(false);
//        Trigger trigger = TriggerHelper.makeSecondlyTriggerInMillis((String)jobName, (String)"DEFAULT", (long)10000L, (int)0, (int)5);
//        jobDetail.setTrigger(trigger);
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug((Object)("firing quartz job: " + (Object)jobDetail));
//        }
//        jobDetail.fire();
    }

    private void updateEnergyStateFromEnergyLevelIfNecessary(@NotNull FieldChanges inChanges, @NotNull FieldChanges inOutMoreChanges) {
        if (inChanges.hasFieldChange(IArgoField.CHE_ENERGY_LEVEL) && !inChanges.hasFieldChange(IArgoField.CHE_ENERGY_STATE_ENUM)) {
            Long energyLevel = (Long)inChanges.getFieldChange(IArgoField.CHE_ENERGY_LEVEL).getNewValue();
            BatteryStateEnum energyState = Che.convertEnergyLevel(ContextHelper.getThreadUserContext(), energyLevel, this.getCheKindEnum());
            LOGGER.debug((Object) String.format("Che Energy Level = %s, Energy State computed as %s for Che %s", new Object[]{energyLevel, energyState, this.getCheShortName()}));
            this.setSelfAndFieldChange(IArgoField.CHE_ENERGY_STATE_ENUM, (Object)energyState, inOutMoreChanges);
        }
    }

    public static BatteryStateEnum convertEnergyLevel(@NotNull UserContext inUserContext, @NotNull Long inEnergyLevel, @NotNull CheKindEnum inCheKindEnum) {
        Document settings = Che.getEnergyStateSettingXml(inUserContext);
        if (settings == null) {
            return BatteryStateEnum.UNKNOWN;
        }
//        Element root = settings.getRootElement();
//        List energyStateEntries = root.getChildren();
//        for (Object energyStateEntry : energyStateEntries) {
//            if (!(energyStateEntry instanceof Element)) continue;
//            String cheKindValue = ((Element)energyStateEntry).getAttributeValue("cheKind");
//            if (!inCheKindEnum.getName().equalsIgnoreCase(cheKindValue)) continue;
//            List energyStates = ((Element)energyStateEntry).getChildren();
//            for (Object energyState : energyStates) {
//                String energyStateName = ((Element)energyState).getAttributeValue("state");
//                String minEnergyLevel = ((Element)energyState).getAttributeValue("minEnergyLevel");
//                String maxEnergyLevel = ((Element)energyState).getAttributeValue("maxEnergyLevel");
//                if (!Che.checkEnergyLevelRange(new Double(inEnergyLevel.longValue()), minEnergyLevel, maxEnergyLevel)) continue;
//                return BatteryStateEnum.getEnum(energyStateName);
//            }
//        }
        LOGGER.error((Object) String.format("Cannot convert energy level %s to energy state for che kind=%s", inEnergyLevel, inCheKindEnum.getName()));
        return BatteryStateEnum.UNKNOWN;
    }

    private static boolean checkEnergyLevelRange(Double inEnergyLevel, String inMinEnergyLevel, String inMaxEnergyLevel) {
        Double minEnergyLevel = null;
        try {
            minEnergyLevel = new Double(inMinEnergyLevel);
        }
        catch (NumberFormatException nfe) {
            LOGGER.error((Object)("Xml setting CHE_ENERGY_STATE has invalid minEnergyLevel - " + inMinEnergyLevel));
        }
        Double maxEnergyLevel = null;
        try {
            maxEnergyLevel = new Double(inMaxEnergyLevel);
        }
        catch (NumberFormatException nfe) {
            LOGGER.error((Object)("Xml setting CHE_ENERGY_STATE has invalid maxEnergyLevel - " + inMaxEnergyLevel));
        }
        return minEnergyLevel != null && maxEnergyLevel != null && inEnergyLevel >= minEnergyLevel && inEnergyLevel <= maxEnergyLevel;
    }

    private static Document getEnergyStateSettingXml(@NotNull UserContext inUserContext) {
        String xmlStringDoc = ArgoConfig.CHE_ENERGY_STATE.getSetting(inUserContext);
        if (xmlStringDoc == null || xmlStringDoc.isEmpty()) {
            LOGGER.warn((Object)"N4 Settings for che energy state not specified.");
            return null;
        }
        LOGGER.debug((Object)("N4 Setting for che energy state is: " + xmlStringDoc));
//        Document settings = XmlUtil.parse(xmlStringDoc);
//        if (settings == null) {
//            LOGGER.warn((Object)"Could not parse XML settings. Cannot derive che energy state.");
//            return null;
//        }
//        return settings;
        return null;
    }

    @Override
    public void setCheLastKnownLocPos(LocPosition inCheLastKnownLocPos) {
        if (LOGGER.isDebugEnabled() && this.getCheShortName() != null && !LocPosition.getUnknownPosition().isSamePosition(this.getCheLastKnownLocPos())) {
            String newLKPos;
            String curLKPos = this.getCheLastKnownLocPos() == null ? "<none>" : this.getCheLastKnownLocPos().toString();
            String string = newLKPos = inCheLastKnownLocPos == null ? "<none>" : inCheLastKnownLocPos.toString();
            if (!curLKPos.equals(newLKPos)) {
                LOGGER.debug((Object)("LastKnownLocPos " + curLKPos + " >> " + newLKPos + ", " + this));
            }
        }
        super.setCheLastKnownLocPos(inCheLastKnownLocPos);
    }

    @Override
    public void setCheJobStepStateEnum(CheJobStepStateEnum inCheJobStepStateEnum) {
        if (LOGGER.isDebugEnabled() && this.getCheShortName() != null && inCheJobStepStateEnum != CHEJOBSTEPSTATEENUM_INITIAL && this.getCheJobStepStateEnum() != CHEJOBSTEPSTATEENUM_INITIAL && inCheJobStepStateEnum != this.getCheJobStepStateEnum() && this.getCheJobStepStateEnum() != CheJobStepStateEnum.IDLE) {
            Throwable logTheCallStack = LOGGER.isTraceEnabled() ? new Throwable() : null;
            LOGGER.debug((Object)("JobStepState " + this.getCheJobStepStateEnum().getName() + " >> " + inCheJobStepStateEnum.getName() + ", " + this), logTheCallStack);
        }
        super.setCheJobStepStateEnum(inCheJobStepStateEnum);
    }

    public String toString() {
        return "Che[" + this.getCheGkey() + ':' + this.getCheShortName() + ']';
    }

    public void setCheAssistStateEnumFromXps() {
        this.setCheAssistStateEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheAssistState(), CheAssistStateEnum.getEnumList(), CHEASSISTSTATEENUM_INITIAL));
    }

    public void setCheJobStepStateEnumFromXps() {
        this.setCheJobStepStateEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheJobStepState(), CheJobStepStateEnum.getEnumList(), CHEJOBSTEPSTATEENUM_INITIAL));
    }

    public void setCheStatusEnumFromXps() {
        this.setCheStatusEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheStatus(), CheStatusEnum.getEnumList(), CHESTATUSENUM_INITIAL));
    }

    public void setCheMessageStatusEnumFromXps() {
        this.setCheMessageStatusEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheMessageStatus(), CheMessageStatusEnum.getEnumList(), CHEMESSAGESTATUSENUM_INITIAL));
    }

    public void setCheTalkStatusEnumFromXps() {
        this.setCheTalkStatusEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheTalkStatus(), CheTalkStatusEnum.getEnumList(), CHETALKSTATUSENUM_INITIAL));
    }

    public void setCheKindEnumFromXps() {
        this.setCheKindEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheKind(), CheKindEnum.getEnumList(), CHEKINDENUM_INITIAL));
    }

    public void setCheOperatingModeEnumFromXps() {
        this.setCheOperatingModeEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getCheOperatingMode(), CheOperatingModeEnum.getEnumList(), CHEOPERATINGMODEENUM_INITIAL));
    }

    public void setCheClerkLastLandedCheFromId() {
        this.setCheClerkLastLandedChe(this.getClerkLastLandedCheFromId());
    }

    public void setCheAssignedCheFromId() {
        this.setCheAssignedChe(this.getAssignedCheFromCheId());
    }

    public void setChePointOfWorkFromPkey() {
        this.setChePointOfWork(this.getPowFromPowKey());
    }

    public void setChePoolFromPkey() {
        this.setChePool(this.getPoolFromPoolKey());
    }

    public void updateChePointOfWork(@Nullable PointOfWork inChePow) {
        this.setChePointOfWork(inChePow);
    }

    public void updateChePoolEntity(@Nullable ChePool inPool) {
        this.setChePool(inPool);
    }

    public void updateCheLastPosition(String inCheLastPosition) {
        super.setCheLastPosition(inCheLastPosition);
        if (!StringUtils.isEmpty((String)inCheLastPosition) && inCheLastPosition.startsWith("Y:")) {
            try {
                String yardSlot = inCheLastPosition.substring(2);
                LocPosition yardPosition = LocPosition.resolvePosition(this.getCheYard().getYrdFacility(), LocTypeEnum.YARD, ContextHelper.getThreadYard().getYrdId(), yardSlot, null, EquipBasicLengthEnum.BASIC20);
                if (yardPosition == null || yardPosition.getPosBin() == null) {
                    LOGGER.error((Object)(inCheLastPosition + " could not be parsed into a Yard Position, no bin could be found for [" + this + "]"));
                } else {
                    this.setCheLastKnownLocPos(yardPosition);
                }
            }
            catch (Exception e) {
                LOGGER.error((Object)(inCheLastPosition + " could not be parsed for [" + this + "] into a Yard Position: " + e.getMessage()));
            }
        }
    }

    @Nullable
    public static Che findByPkey(Serializable inYardGkey, Long inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_YARD, (Object)inYardGkey));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() > 1) {
            LOGGER.error((Object)(matches.size() + " Che found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardGkey + ")"));
            Collections.sort(matches, new GKeyComparator());
        }
        return matches.isEmpty() ? null : (Che)matches.get(0);
    }

    @Nullable
    public static String cheLastPosition(@NotNull LocTypeEnum inLocType, @NotNull String inPosSlot) {
        assert (inLocType != null) : "inLocType is null";
        assert (inPosSlot != null) : "inPosLocation is null";
        if (LocTypeEnum.YARD.equals((Object)inLocType)) {
            return StringUtils.isNotBlank((String)inPosSlot) ? String.format("Y:%s", inPosSlot) : null;
        }
        return null;
    }

    public boolean hasSpecificTrailer() {
        return this.getHasTrailer() != null && (long) HasTrailerEnum.HAS_SPECIFIC_TRAILER.toSerialValue() == this.getHasTrailer();
    }

    public static boolean isCheAvailableToDispatch(@NotNull CheStatusEnum inCheStatusEnum) {
        return CheStatusEnum.WORKING.equals((Object)inCheStatusEnum) || CheStatusEnum.ONLINEREPAIR.equals((Object)inCheStatusEnum);
    }

    public static Che getChe(@NotNull Serializable inCheGkey) {
        Che che = (Che)HibernateApi.getInstance().get(Che.class, inCheGkey);
        assert (che != null) : "che is null for inCheGkey:" + inCheGkey;
        return che;
    }

    @Nullable
    private static String getComponentOfNaturalKey(@NotNull String inNaturalKey, int inIndex) {
        String[] componentStrings = inNaturalKey.split(NATURAL_KEY_DELIMITER);
        return componentStrings.length > inIndex ? componentStrings[inIndex] : null;
    }

    @Nullable
    private PointOfWork getPowFromPowKey() {
        return PointOfWork.findByPkey(this.getCheYard().getYrdGkey(), this.getChePowPkey());
    }

    @Nullable
    private ChePool getPoolFromPoolKey() {
        return ChePool.findByPkey(this.getCheYard().getYrdGkey(), this.getChePoolPkey());
    }

    @Nullable
    private Che getClerkLastLandedCheFromId() {
        Long clerkLastLandedCheId = this.getCheClerkLastLandedCheId();
        return clerkLastLandedCheId == null || clerkLastLandedCheId == 0L ? null : Che.findChe(clerkLastLandedCheId, this.getCheYard());
    }

    @Nullable
    private Che getAssignedCheFromCheId() {
        Long assignedCheId = this.getCheAssignedCheId();
        return assignedCheId == null || assignedCheId == 0L ? null : Che.findChe(assignedCheId, this.getCheYard());
    }

    @Nullable
    private Long getYardKeyConsideringChanges(@NotNull FieldChanges inChanges) {
        Yard yard = this.getYardConsideringChanges(inChanges);
        return yard == null ? null : yard.getYrdGkey();
    }

    public static Che hydrate(Serializable inPrimaryKey) {
        return (Che)HibernateApi.getInstance().load(Che.class, inPrimaryKey);
    }

    @Nullable
    private Yard getYardConsideringChanges(@NotNull FieldChanges inChanges) {
        return inChanges.hasFieldChange(IArgoField.CHE_YARD) ? (Yard)inChanges.getFieldChange(IArgoField.CHE_YARD).getNewValue() : this.getCheYard();
    }

    @Override
    public List getGuardians() {
        return new ArrayList();
    }

    public static MetafieldIdList getPathsToGuardians() {
        return new MetafieldIdList();
    }

    @Override
    @Nullable
    public String getLovKeyNameForPathToGuardian(String inPathToGuardian) {
        return null;
    }

    @Override
    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        return null;
    }

    @Override
    public Boolean skipEventRecording() {
        return false;
    }

    @Override
    public LogicalEntityEnum getLogicalEntityType() {
        return LogicalEntityEnum.CHE;
    }

    @Override
    public String getLogEntityId() {
        return this.getCheShortName();
    }

    @Override
    public String getLogEntityParentId() {
        return this.getCheShortName();
    }

    @Override
    public Complex getLogEntityComplex() {
        return this.getCheYard().getYrdFacility().getFcyComplex();
    }

    @Override
    public void calculateFlags() {
    }

    @Override
    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        return null;
    }

    public static MetafieldIdList getPredicateFields(LogicalEntityEnum inEntity) {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(IArgoField.CHE_ALTERNATIVE_RADIO_ID);
        ids.add(IArgoField.CHE_ASSIST_STATE_ENUM);
        ids.add(IArgoField.CHE_HAS_MDT);
        ids.add(IArgoField.CHE_KIND_ENUM);
        ids.add(IArgoField.CHE_JOB_STEP_STATE_ENUM);
        ids.add(IArgoField.CHE_LANE);
        ids.add(IArgoField.CHE_LAST_POSITION);
        ids.add(IArgoField.CHE_LOGIN_NAME);
        ids.add(IArgoField.CHE_OPERATING_MODE_ENUM);
        ids.add(IArgoField.CHE_PLAN_POSITION);
        ids.add(IArgoField.CHE_POOL);
        ids.add(IArgoField.CHE_SHORT_NAME);
        ids.add(IArgoField.CHE_SCALE_ON);
        ids.add(IArgoField.CHE_SENDS_PDS_WEIGHT);
        ids.add(IArgoField.CHE_STATUS_ENUM);
        ids.add(IArgoField.CHE_SUSPEND_AUTO_DISPATCH);
        ids.add(IArgoField.CHE_TANDEM_LIFT_CAPABLE);
        ids.add(IArgoField.CHE_USES_PDS);
        ids.add(IArgoField.CHE_YARD);
        ids.add(IArgoField.CHE_TALK_STATUS_ENUM);
        ids.add(IArgoField.CHE_MESSAGE_STATUS_ENUM);
        return ids;
    }

    @Nullable
    public static PointOfWork findChePointOfWork(Che inChe) {
        if (CheKindEnum.QC.equals((Object)inChe.getCheKindEnum())) {
            return PointOfWork.findPointOfWorkByQCCheId(inChe.getCheId(), ContextHelper.getThreadYard());
        }
        return inChe.getChePointOfWork();
    }

    @Override
    public void postEventCreation(IEvent inEventCreated) {
    }

    public static class GKeyComparator
            implements Comparator<Che> {
        @Override
        public int compare(Che inChe1, Che inChe2) {
            if (inChe1.getCheGkey() > inChe2.getCheGkey()) {
                return -1;
            }
            if (inChe1.getCheGkey() < inChe2.getCheGkey()) {
                return 1;
            }
            return 0;
        }
    }

}
