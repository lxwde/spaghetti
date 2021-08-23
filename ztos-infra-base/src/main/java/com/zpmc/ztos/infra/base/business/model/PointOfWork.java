package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.PointOfWorkDO;
import com.zpmc.ztos.infra.base.business.enums.argo.PowDispatchModeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PowUncompletedWorkSortEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.enums.xps.AtomizedEnumXpsConverter;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.ChePool;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PointOfWork extends PointOfWorkDO {
    private static final PowDispatchModeEnum POWDISPATCHMODEENUM_INITIAL = PowDispatchModeEnum.STOP;
    private static final Long POWDISPATCHMODEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(POWDISPATCHMODEENUM_INITIAL, PowDispatchModeEnum.getEnumList(), null);
    private static final WiMoveKindEnum QCLASTMOVETYPEENUM_INITIAL = WiMoveKindEnum.Other;
    private static final Long QCLASTMOVETYPEXPS_INITIAL = AtomizedEnumXpsConverter.findXpsOrdinal(QCLASTMOVETYPEENUM_INITIAL, WiMoveKindEnum.getEnumList(), null);
    private static final Logger LOGGER = Logger.getLogger(PointOfWork.class);
    private static final PowUncompletedWorkSortEnum POWUNCOMPLETEDWORKSORT_INITIAL = PowUncompletedWorkSortEnum.SEQORDER;

    public PointOfWork() {
        this.setPointofworkDispatchModeEnum(POWDISPATCHMODEENUM_INITIAL);
        this.setPointofworkDispatchMode(POWDISPATCHMODEXPS_INITIAL);
        this.setPointofworkQcLastMoveTypeEnum(QCLASTMOVETYPEENUM_INITIAL);
        this.setPointofworkDispatchMode(QCLASTMOVETYPEXPS_INITIAL);
        this.setPointofworkLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setPointofworkUncompletedWorkSort(POWUNCOMPLETEDWORKSORT_INITIAL);
    }

    @Nullable
    public static PointOfWork findPointOfWorkByName(String inPowName, Yard inYard) {
        return PointOfWork.findPointOfWorkByName(inPowName, inYard.getYrdGkey());
    }

    @Nullable
    public static PointOfWork findPointOfWorkByName(@NotNull String inPowName, @NotNull Serializable inYardGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PointOfWork").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_NAME, (Object)inPowName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_YARD, (Object)inYardGkey));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (PointOfWork) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static PointOfWork findPointOfWorkByQCCheId(Long inCheId, Yard inYard) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PointOfWork").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_CHE_ID, (Object)inCheId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_YARD, (Object)inYard.getYrdGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (PointOfWork)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static PointOfWork findPointOfWorkByCheId(Long inCheId, Serializable inYardGkey) {
        Yard yard = (Yard)HibernateApi.getInstance().load(Yard.class, inYardGkey);
        return PointOfWork.findPointOfWorkByQCCheId(inCheId, yard);
    }

    public static PointOfWork findPowByChe(Che inChe) {
        return PointOfWork.findPointOfWorkByCheId(inChe.getCheId(), inChe.getCheYard().getYrdGkey());
    }

    public static PointOfWork hydrate(Long inPowGkey) {
        return (PointOfWork) Roastery.getHibernateApi().load(PointOfWork.class, (Serializable)inPowGkey);
    }

    public void updatePointofworkOwnerPool(ChePool inPool) {
        this.setPointofworkOwnerPool(inPool);
    }

    public void updatePointofworkLastQuayOrRailAccessTime(Date inDate) {
        this.setPointofworkLastQuayOrRailAccess(inDate);
    }

    public void setPointofworkDispatchModeEnumFromXps() {
        this.setPointofworkDispatchModeEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getPointofworkDispatchMode(), PowDispatchModeEnum.getEnumList(), POWDISPATCHMODEENUM_INITIAL));
    }

    public void setPointofworkQcLastMoveTypeEnumFromXps() {
        this.setPointofworkQcLastMoveTypeEnum(AtomizedEnumXpsConverter.findEnumInstance(this.getPointofworkQcLastMoveType(), WiMoveKindEnum.getEnumList(), QCLASTMOVETYPEENUM_INITIAL));
    }

    public void setPointofworkOwnerPoolFromReference() {
        this.setPointofworkOwnerPool(ChePool.findByPkey(this.getPointofworkYard().getYrdGkey(), this.getPointofworkOwnerPoolReference()));
    }

    public void setPointofworkVesselVisitFromId() {
        this.setPointofworkVesselVisit(CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), this.getPointofworkVesselVisitReference()));
    }

    public void setPointofworkQcVesselVisitFromId() {
        this.setPointofworkQcVesselVisit(CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), this.getPointofworkQcVesselVisitReference()));
    }

    public void setPointofworkYardCrane1FromId() {
        this.setPointofworkYardCrane1(Che.findChe(this.getPointofworkYardCrane1Id(), this.getPointofworkYard()));
    }

    public void setPointofworkYardCrane2FromId() {
        this.setPointofworkYardCrane2(Che.findChe(this.getPointofworkYardCrane2Id(), this.getPointofworkYard()));
    }

    public void setPointofworkYardCrane3FromId() {
        this.setPointofworkYardCrane3(Che.findChe(this.getPointofworkYardCrane3Id(), this.getPointofworkYard()));
    }

    public void setPointofworkYardCrane4FromId() {
        this.setPointofworkYardCrane4(Che.findChe(this.getPointofworkYardCrane4Id(), this.getPointofworkYard()));
    }

    public void setPointofworkYardCrane5FromId() {
        this.setPointofworkYardCrane5(Che.findChe(this.getPointofworkYardCrane5Id(), this.getPointofworkYard()));
    }

    public void updateHasActiveAlarm(Boolean inActive) {
        this.setPointofworkHasActiveAlarm(inActive);
    }

    public void preProcessInsert(@NotNull FieldChanges inOutMoreChanges) {
        Che newChe;
        String vesselRefId;
        String visitId;
        super.preProcessInsert(inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.POINTOFWORK_QC_LAST_MOVE_TYPE_ENUM, IArgoField.POINTOFWORK_QC_LAST_MOVE_TYPE, WiMoveKindEnum.class, QCLASTMOVETYPEENUM_INITIAL, QCLASTMOVETYPEXPS_INITIAL, WiMoveKindEnum.getEnumList(), inOutMoreChanges, this.getPointofworkQcLastMoveTypeEnum(), this.getPointofworkQcLastMoveType());
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.POINTOFWORK_DISPATCH_MODE_ENUM, IArgoField.POINTOFWORK_DISPATCH_MODE, PowDispatchModeEnum.class, POWDISPATCHMODEENUM_INITIAL, POWDISPATCHMODEXPS_INITIAL, PowDispatchModeEnum.getEnumList(), inOutMoreChanges, this.getPointofworkDispatchModeEnum(), this.getPointofworkDispatchMode());
        Long poolPkey = this.getPointofworkOwnerPoolReference();
        if (poolPkey == null || poolPkey == 0L) {
            if (this.getPointofworkOwnerPool() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_OWNER_POOL_REFERENCE, this.getPointofworkOwnerPool().getPoolPkey(), inOutMoreChanges);
            }
        } else if (this.getPointofworkOwnerPool() == null) {
            ChePool pool = ChePool.findByPkey(this.getPointofworkYard().getYrdGkey(), this.getPointofworkOwnerPoolReference());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_OWNER_POOL, pool, inOutMoreChanges);
        }
        if ((visitId = this.getPointofworkVesselVisitReference()) == null || visitId.isEmpty()) {
            if (this.getPointofworkVesselVisit() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT_REFERENCE, this.getPointofworkVesselVisit().getCvId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkVesselVisit() == null) {
            CarrierVisit newCv = CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), visitId);
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT, newCv, inOutMoreChanges);
        }
        if ((vesselRefId = this.getPointofworkQcVesselVisitReference()) == null || vesselRefId.isEmpty()) {
            if (this.getPointofworkQcVesselVisit() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT_REFERENCE, this.getPointofworkQcVesselVisit().getCvId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkQcVesselVisit() == null) {
            CarrierVisit newCv = CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), vesselRefId);
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT, newCv, inOutMoreChanges);
        }
        if (this.getPointofworkYardCrane1Id() == null) {
            if (this.getPointofworkYardCrane1() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1_ID, this.getPointofworkYardCrane1().getCheId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkYardCrane1() == null) {
            newChe = Che.findChe(this.getPointofworkYardCrane1Id(), this.getPointofworkYard());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1, newChe, inOutMoreChanges);
        }
        if (this.getPointofworkYardCrane2Id() == null) {
            if (this.getPointofworkYardCrane2() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2_ID, this.getPointofworkYardCrane2().getCheId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkYardCrane2() == null) {
            newChe = Che.findChe(this.getPointofworkYardCrane2Id(), this.getPointofworkYard());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2, newChe, inOutMoreChanges);
        }
        if (this.getPointofworkYardCrane3Id() == null) {
            if (this.getPointofworkYardCrane3() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3_ID, this.getPointofworkYardCrane3().getCheId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkYardCrane3() == null) {
            newChe = Che.findChe(this.getPointofworkYardCrane3Id(), this.getPointofworkYard());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3, newChe, inOutMoreChanges);
        }
        if (this.getPointofworkYardCrane4Id() == null) {
            if (this.getPointofworkYardCrane4() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4_ID, this.getPointofworkYardCrane4().getCheId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkYardCrane4() == null) {
            newChe = Che.findChe(this.getPointofworkYardCrane4Id(), this.getPointofworkYard());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4, newChe, inOutMoreChanges);
        }
        if (this.getPointofworkYardCrane5Id() == null) {
            if (this.getPointofworkYardCrane5() != null) {
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5_ID, this.getPointofworkYardCrane5().getCheId(), inOutMoreChanges);
            }
        } else if (this.getPointofworkYardCrane5() == null) {
            newChe = Che.findChe(this.getPointofworkYardCrane5Id(), this.getPointofworkYard());
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5, newChe, inOutMoreChanges);
        }
    }

    public void preProcessUpdate(@NotNull FieldChanges inChanges, @NotNull FieldChanges inOutMoreChanges) {
        Che newChe;
        Che newCraneChe;
        Long newCraneId;
        Object newId;
        CarrierVisit cv;
        CarrierVisit newCv;
        String visitId;
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.POINTOFWORK_QC_LAST_MOVE_TYPE_ENUM, IArgoField.POINTOFWORK_QC_LAST_MOVE_TYPE, WiMoveKindEnum.class, QCLASTMOVETYPEENUM_INITIAL, QCLASTMOVETYPEXPS_INITIAL, WiMoveKindEnum.getEnumList(), inOutMoreChanges, inChanges);
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges((IEntity)this, IArgoField.POINTOFWORK_DISPATCH_MODE_ENUM, IArgoField.POINTOFWORK_DISPATCH_MODE, PowDispatchModeEnum.class, POWDISPATCHMODEENUM_INITIAL, POWDISPATCHMODEXPS_INITIAL, PowDispatchModeEnum.getEnumList(), inOutMoreChanges, inChanges);
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_OWNER_POOL_REFERENCE)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_OWNER_POOL)) {
                Long newPoolPkey = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_OWNER_POOL_REFERENCE).getNewValue();
                ChePool newPool = ChePool.findByPkey(this.getYardKeyConsideringChanges(inChanges), newPoolPkey);
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_OWNER_POOL, newPool, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_OWNER_POOL)) {
            ChePool newPool = (ChePool)inChanges.getFieldChange(IArgoField.POINTOFWORK_OWNER_POOL).getNewValue();
            Long newPkey = newPool == null ? null : newPool.getPoolPkey();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_OWNER_POOL_REFERENCE, newPkey, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT_REFERENCE)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT)) {
                visitId = (String)inChanges.getFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT_REFERENCE).getNewValue();
                newCv = CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), visitId);
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT, newCv, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT)) {
            cv = (CarrierVisit)inChanges.getFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT).getNewValue();
            newId = cv == null ? null : cv.getCvId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_VESSEL_VISIT_REFERENCE, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT_REFERENCE)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT)) {
                visitId = (String)inChanges.getFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT_REFERENCE).getNewValue();
                newCv = CarrierVisit.findVesselVisit(this.getPointofworkYard().getYrdFacility(), visitId);
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT, newCv, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT)) {
            cv = (CarrierVisit)inChanges.getFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT).getNewValue();
            newId = cv == null ? null : cv.getCvId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_QC_VESSEL_VISIT_REFERENCE, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1)) {
                newCraneId = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1_ID).getNewValue();
                newCraneChe = Che.findChe(newCraneId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1, newCraneChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1)) {
            newChe = (Che)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1).getNewValue();
            newId = newChe == null ? null : newChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE1_ID, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2)) {
                newCraneId = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2_ID).getNewValue();
                newCraneChe = Che.findChe(newCraneId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2, newCraneChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2)) {
            newChe = (Che)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2).getNewValue();
            newId = newChe == null ? null : newChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE2_ID, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3)) {
                newCraneId = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3_ID).getNewValue();
                newCraneChe = Che.findChe(newCraneId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3, newCraneChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3)) {
            newChe = (Che)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3).getNewValue();
            newId = newChe == null ? null : newChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE3_ID, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4)) {
                newCraneId = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4_ID).getNewValue();
                newCraneChe = Che.findChe(newCraneId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4, newCraneChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4)) {
            newChe = (Che)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4).getNewValue();
            newId = newChe == null ? null : newChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE4_ID, newId, inOutMoreChanges);
        }
        if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5_ID)) {
            if (!inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5)) {
                newCraneId = (Long)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5_ID).getNewValue();
                newCraneChe = Che.findChe(newCraneId, this.getYardConsideringChanges(inChanges));
                this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5, newCraneChe, inOutMoreChanges);
            }
        } else if (inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5)) {
            newChe = (Che)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5).getNewValue();
            newId = newChe == null ? null : newChe.getCheId();
            this.setSelfAndFieldChange(IArgoField.POINTOFWORK_YARD_CRANE5_ID, newId, inOutMoreChanges);
        }
    }

    @Nullable
    public static PointOfWork findByPkey(Long inYardKey, Long inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PointOfWork").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POINTOFWORK_YARD, (Object)inYardKey));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() > 1) {
            LOGGER.error((Object)(matches.size() + " PointOfWork found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardKey + ")"));
            Collections.sort(matches, new Comparator<PointOfWork>(){

                @Override
                public int compare(PointOfWork inPow1, PointOfWork inPow2) {
                    return inPow2.getPointofworkGkey().compareTo(inPow1.getPointofworkGkey());
                }
            });
        }
        return matches.isEmpty() ? null : (PointOfWork)matches.get(0);
    }

    @Nullable
    public static Che findCheForPointOfWork(String inPowName) {
        Che che = null;
        if (inPowName != null) {
            PointOfWork pow = PointOfWork.findPointOfWorkByName(inPowName, ContextHelper.getThreadYard());
            che = PointOfWork.findCheForPointOfWork(pow);
        }
        return che;
    }

    @Nullable
    public static Che findCheForPointOfWork(PointOfWork inPow) {
        Long powCheId;
        if (inPow != null && (powCheId = inPow.getPointofworkCheId()) != null) {
            return Che.findChe(powCheId, ContextHelper.getThreadYard());
        }
        return null;
    }

    @Nullable
    private Long getYardKeyConsideringChanges(@NotNull FieldChanges inChanges) {
        Yard yard = this.getYardConsideringChanges(inChanges);
        return yard == null ? null : yard.getYrdGkey();
    }

    @Nullable
    private Yard getYardConsideringChanges(@NotNull FieldChanges inChanges) {
        return inChanges.hasFieldChange(IArgoField.POINTOFWORK_YARD) ? (Yard)inChanges.getFieldChange(IArgoField.POINTOFWORK_YARD).getNewValue() : this.getPointofworkYard();
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setPointofworkLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getPointofworkLifeCycleState();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }
}
