package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.plans.WorkQueue;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;

import java.io.Serializable;
import java.util.Date;

public class MoveInfoBean {
    private WiMoveKindEnum _moveKind;
    private Long _distToStart;
    private Long _distOfCarry;
    private Date _timeCarryComplete;
    private Date _timeDispatch;
    private Date _timeFetch;
    private Date _timeDischarge;
    private Date _timePut;
    private Date _timeCarryCheFetchReady;
    private Date _timeCarryChePutReady;
    private Date _timeCarryCheDispatch;
    private Date _timeTzArrival;
    private Long _rehandleCount;
    private Boolean _twinFetch;
    private Boolean _twinCarry;
    private Boolean _twinPut;
    private String _restowAccount;
    private String _serviceOrder;
    private String _restowReason;
    private Long _cheIndexFetch;
    private Long _cheIndexCarry;
    private Long _cheIndexPut;
    private Serializable _yardGkey;
    private boolean _isPartialDischarge;
    private String _wiPow;
    private String _wiBerth;
    private String _carryLoginName;
    private String _fetchLoginName;
    private String _putLoginName;

    public MoveInfoBean(Serializable inYardGkey) {
        this._yardGkey = inYardGkey;
    }

    public static MoveInfoBean createDefaultMoveInfoBean(WiMoveKindEnum inMoveKind, Date inMoveTime) {
        MoveInfoBean mib = new MoveInfoBean(ContextHelper.getThreadYardKey());
        mib.setMoveKind(inMoveKind);
        mib.setTimePut(inMoveTime);
        if (WiMoveKindEnum.VeslDisch.equals((Object)inMoveKind) || WiMoveKindEnum.RailDisch.equals((Object)inMoveKind) || WiMoveKindEnum.Receival.equals((Object)inMoveKind)) {
            mib.setTimeDischarge(inMoveTime);
        }
        return mib;
    }

    public static MoveInfoBean createMoveInfoBean(@NotNull WorkInstruction inWi, @NotNull LocPosition inNewPos, Date inMoveTime) {
        MoveInfoBean mib = null;
        EventEnum serviceType = MoveInfoBean.resolveServiceTypeFromWi(inWi, inNewPos);
        mib = EventEnum.UNIT_LOAD.equals((Object)serviceType) || EventEnum.UNIT_DISCH.equals((Object)serviceType) ? MoveInfoBean.createLoadOrDischMoveInfo(inWi, inMoveTime) : MoveInfoBean.createYardMoveInfo(inWi);
        return mib;
    }

    public Long getDistToStart() {
        return this._distToStart;
    }

    public void setDistToStart(Long inDistToStart) {
        this._distToStart = inDistToStart;
    }

    public Long getDistOfCarry() {
        return this._distOfCarry;
    }

    public void setDistOfCarry(Long inDistOfCarry) {
        this._distOfCarry = inDistOfCarry;
    }

    public Date getTimeCarryComplete() {
        return this._timeCarryComplete;
    }

    public void setTimeCarryComplete(Date inTimeCarryComplete) {
        this._timeCarryComplete = inTimeCarryComplete;
    }

    public Date getTimeDispatch() {
        return this._timeDispatch;
    }

    public void setTimeDispatch(Date inTimeDispatch) {
        this._timeDispatch = inTimeDispatch;
    }

    public Date getTimeFetch() {
        return this._timeFetch;
    }

    public void setTimeFetch(Date inTimeFetch) {
        this._timeFetch = inTimeFetch;
    }

    public Date getTimeDischarge() {
        return this._timeDischarge;
    }

    public void setTimeDischarge(Date inTimeDischarge) {
        this._timeDischarge = inTimeDischarge;
    }

    public Date getTimePut() {
        return this._timePut;
    }

    public void setTimePut(Date inTimePut) {
        this._timePut = inTimePut;
    }

    public Date getTimeCarryCheFetchReady() {
        return this._timeCarryCheFetchReady;
    }

    public void setTimeCarryCheFetchReady(Date inTimeCarryCheFetchReady) {
        this._timeCarryCheFetchReady = inTimeCarryCheFetchReady;
    }

    public Date getTimeCarryChePutReady() {
        return this._timeCarryChePutReady;
    }

    public void setTimeCarryChePutReady(Date inTimeCarryChePutReady) {
        this._timeCarryChePutReady = inTimeCarryChePutReady;
    }

    public Date getTimeCarryCheDispatch() {
        return this._timeCarryCheDispatch;
    }

    public void setTimeCarryCheDispatch(Date inTimeCarryCheDispatch) {
        this._timeCarryCheDispatch = inTimeCarryCheDispatch;
    }

    public Date getTimeTzArrival() {
        return this._timeTzArrival;
    }

    public void setTimeTzArrival(Date inTimeTzArrival) {
        this._timeTzArrival = inTimeTzArrival;
    }

    public Long getRehandleCount() {
        return this._rehandleCount;
    }

    public void setRehandleCount(Long inRehandleCount) {
        this._rehandleCount = inRehandleCount;
    }

    public Boolean getTwinFetch() {
        return this._twinFetch;
    }

    public void setTwinFetch(Boolean inTwinFetch) {
        this._twinFetch = inTwinFetch;
    }

    public Boolean getTwinCarry() {
        return this._twinCarry;
    }

    public void setTwinCarry(Boolean inTwinCarry) {
        this._twinCarry = inTwinCarry;
    }

    public Boolean getTwinPut() {
        return this._twinPut;
    }

    public void setTwinPut(Boolean inTwinPut) {
        this._twinPut = inTwinPut;
    }

    public String getRestowAccount() {
        return this._restowAccount;
    }

    public void setRestowAccount(String inRestowAccount) {
        this._restowAccount = inRestowAccount;
    }

    public String getCarryLoginName() {
        return this._carryLoginName;
    }

    public void setCarryLoginName(String inCarryLoginName) {
        this._carryLoginName = inCarryLoginName;
    }

    public String getFetchLoginName() {
        return this._fetchLoginName;
    }

    public void setFetchLoginName(String inFetchLoginName) {
        this._fetchLoginName = inFetchLoginName;
    }

    public String getPutLoginName() {
        return this._putLoginName;
    }

    public void setPutLoginName(String inPutLoginName) {
        this._putLoginName = inPutLoginName;
    }

    public String getServiceOrder() {
        return this._serviceOrder;
    }

    public void setServiceOrder(String inServiceOrder) {
        this._serviceOrder = inServiceOrder;
    }

    public String getRestowReason() {
        return this._restowReason;
    }

    public void setRestowReason(String inRestowReason) {
        this._restowReason = inRestowReason;
    }

    public Long getCheIndexFetch() {
        return this._cheIndexFetch;
    }

    public void setCheIndexFetch(Long inCheIndexFetch) {
        this._cheIndexFetch = inCheIndexFetch;
    }

    public Long getCheIndexCarry() {
        return this._cheIndexCarry;
    }

    public void setCheIndexCarry(Long inCheIndexCarry) {
        this._cheIndexCarry = inCheIndexCarry;
    }

    public Long getCheIndexPut() {
        return this._cheIndexPut;
    }

    public void setCheIndexPut(Long inCheIndexPut) {
        this._cheIndexPut = inCheIndexPut;
    }

    public Serializable getYardGkey() {
        return this._yardGkey;
    }

    public void setYardGkey(Serializable inYardGkey) {
        this._yardGkey = inYardGkey;
    }

    public WiMoveKindEnum getMoveKind() {
        return this._moveKind;
    }

    public void setMoveKind(WiMoveKindEnum inMoveKind) {
        this._moveKind = inMoveKind;
    }

    public boolean isPartialDischarge() {
        return this._isPartialDischarge;
    }

    public void setPartialDischarge(boolean inPartialDischarge) {
        this._isPartialDischarge = inPartialDischarge;
    }

    public String getWiPow() {
        return this._wiPow;
    }

    public void setWiPow(String inWiPow) {
        this._wiPow = inWiPow;
    }

    public String getWiBerth() {
        return this._wiBerth;
    }

    public void setWiBerth(String inWiBerth) {
        this._wiBerth = inWiBerth;
    }

    private static EventEnum resolveServiceTypeFromWi(@NotNull WorkInstruction inWi, @NotNull LocPosition inNewPos) {
        if (WiMoveKindEnum.VeslDisch.equals((Object)inWi.getWiMoveKind()) && inNewPos.isQcAccessiblePosition()) {
            return EventEnum.UNIT_DISCH;
        }
        if (WiMoveKindEnum.VeslLoad.equals((Object)inWi.getWiMoveKind()) && (inNewPos.isQcPlatformPosition() || inNewPos.isVesselPosition())) {
            return EventEnum.UNIT_LOAD;
        }
        return EventEnum.UNIT_YARD_MOVE;
    }

    private static MoveInfoBean createLoadOrDischMoveInfo(@NotNull WorkInstruction inWi, Date inMoveTime) {
        EcEvent evQCPL;
        EcEvent evQCFL;
        MoveInfoBean mib = new MoveInfoBean(ContextHelper.getThreadYardKey());
        WiMoveKindEnum moveKind = inWi.getWiMoveKind();
        mib.setMoveKind(moveKind);
        UnitFacilityVisit wiUfv = inWi.getWiUfv();
        String cheName = null;
        if (WiMoveKindEnum.VeslDisch.equals((Object)moveKind) || WiMoveKindEnum.RailDisch.equals((Object)moveKind)) {
            mib.setCheIndexFetch(inWi.getMvhsFetchCheIndex());
            mib.setCheIndexCarry(inWi.getMvhsFetchCheIndex());
            mib.setCheIndexPut(inWi.getMvhsFetchCheIndex());
            mib.setTimeDispatch(inWi.getMvhsTimeFetchCheDispatch());
            inWi.updateMvhsTimeDischarge(inMoveTime);
            mib.setTimeDischarge(inMoveTime);
            cheName = inWi.getMvhsFetchCheId();
            if (cheName != null && inWi.getWiCheWorkAssignment() != null && (evQCFL = EcEvent.findEarliestEcEventOfType((String)cheName, (String)"QCFL", (Long)inWi.getWiCheWorkAssignment().getWorkassignmentGkey())) != null) {
                mib.setTimeFetch(evQCFL.getEceventTimestamp());
            }
        } else {
            mib.setCheIndexFetch(inWi.getMvhsPutCheIndex());
            mib.setCheIndexCarry(inWi.getMvhsPutCheIndex());
            mib.setCheIndexPut(inWi.getMvhsPutCheIndex());
            mib.setTimeDispatch(inWi.getMvhsTimePutCheDispatch());
            inWi.updateMvhsTimeDischarge(null);
            mib.setTimeDischarge(null);
            cheName = inWi.getMvhsPutCheId();
            if (cheName != null && inWi.getWiCheWorkAssignment() != null && (evQCFL = EcEvent.findLatestEcEventOfType((String)cheName, (String)"QCFL", (Long)inWi.getWiCheWorkAssignment().getWorkassignmentGkey())) != null) {
                mib.setTimeFetch(evQCFL.getEceventTimestamp());
            }
        }
        if (cheName != null && inWi.getWiCheWorkAssignment() != null && (evQCPL = EcEvent.findLatestEcEventOfType((String)cheName, (String)"QCPL", (Long)inWi.getWiCheWorkAssignment().getWorkassignmentGkey())) != null) {
            mib.setTimePut(evQCPL.getEceventTimestamp());
        }
        mib.setTimeCarryCheDispatch(null);
        mib.setTimeCarryCheFetchReady(null);
        mib.setTimeCarryComplete(null);
        mib.setTwinFetch(inWi.getMvhsTwinFetch());
        mib.setTwinCarry(inWi.getMvhsTwinCarry());
        mib.setTwinPut(inWi.getMvhsTwinPut());
        MoveInfoBean.setCommonMoveInfoFields(mib, inWi);
        return mib;
    }

    private static MoveInfoBean createYardMoveInfo(@NotNull WorkInstruction inWi) {
        MoveInfoBean mib = new MoveInfoBean(ContextHelper.getThreadYardKey());
        WiMoveKindEnum moveKind = inWi.getWiMoveKind();
        mib.setMoveKind(moveKind);
        mib.setTimeDischarge(null);
        mib.setCheIndexFetch(inWi.getMvhsFetchCheIndex());
        mib.setCheIndexCarry(inWi.getMvhsCarryCheIndex());
        mib.setCheIndexPut(inWi.getMvhsPutCheIndex());
        mib.setTimeDispatch(inWi.getMvhsTimeDispatch());
        mib.setTimeFetch(inWi.getMvhsTimeFetch());
        mib.setTimeCarryComplete(inWi.getMvhsTimeCarryComplete());
        mib.setTimePut(inWi.getMvhsTimePut());
        mib.setTimeCarryCheFetchReady(inWi.getMvhsTimeCarryCheFetchReady());
        mib.setTimeCarryChePutReady(inWi.getMvhsTimeCarryChePutReady());
        mib.setTimeCarryCheDispatch(inWi.getMvhsTimeCarryCheDispatch());
        mib.setTimeTzArrival(inWi.getMvhsTZArrivalTime());
        mib.setDistToStart(inWi.getMvhsDistToStart());
        mib.setDistOfCarry(inWi.getMvhsDistOfCarry());
        mib.setRehandleCount(inWi.getMvhsRehandleCount());
        MoveInfoBean.setCommonMoveInfoFields(mib, inWi);
        return mib;
    }

    private static void setCommonMoveInfoFields(@NotNull MoveInfoBean inOutMoveInfoBean, @NotNull WorkInstruction inWi) {
        PointOfWork pow;
        inOutMoveInfoBean.setTwinFetch(inWi.getMvhsTwinFetch());
        inOutMoveInfoBean.setTwinCarry(inWi.getMvhsTwinCarry());
        inOutMoveInfoBean.setTwinPut(inWi.getMvhsTwinPut());
        inOutMoveInfoBean.setRestowAccount(inWi.getWiRestowAccount());
        inOutMoveInfoBean.setRestowReason(inWi.getWiRestowReason());
        inOutMoveInfoBean.setServiceOrder(inWi.getWiServiceOrder());
        WorkQueue wq = inWi.getWiWorkQueue();
        if (wq != null && (pow = wq.getWqPowViaWorkShift()) != null) {
            inOutMoveInfoBean.setWiPow(pow.getPointofworkName());
        }
    }

    public static MoveInfoBean extractMoveInfoFromMoveEvent(MoveEvent inEvent) {
        if (inEvent == null) {
            return null;
        }
        MoveInfoBean infoBean = new MoveInfoBean(ContextHelper.getThreadYardKey());
        infoBean.setCarryLoginName(inEvent.getMveCheCarryLoginName());
        infoBean.setFetchLoginName(inEvent.getMveCheFetchLoginName());
        infoBean.setPutLoginName(inEvent.getMveChePutLoginName());
        if (inEvent.getMveCheCarry() != null) {
            infoBean.setCheIndexCarry(inEvent.getMveCheCarry().getCheId());
        }
        if (inEvent.getMveCheFetch() != null) {
            infoBean.setCheIndexFetch(inEvent.getMveCheFetch().getCheId());
        }
        if (inEvent.getMveChePut() != null) {
            infoBean.setCheIndexPut(inEvent.getMveChePut().getCheId());
        }
        infoBean.setDistOfCarry(inEvent.getMveDistOfCarry());
        infoBean.setDistToStart(inEvent.getMveDistToStart());
        infoBean.setMoveKind(inEvent.getMveMoveKind());
        infoBean.setRehandleCount(inEvent.getMveRehandleCount());
        infoBean.setRestowAccount(inEvent.getMveRestowAccount());
        infoBean.setRestowReason(inEvent.getMveRestowReason());
        infoBean.setServiceOrder(inEvent.getMveServiceOrder());
        infoBean.setTimeCarryCheDispatch(inEvent.getMveTimeCarryCheDispatch());
        infoBean.setTimeCarryCheFetchReady(inEvent.getMveTimeCarryCheFetchReady());
        infoBean.setTimeCarryChePutReady(inEvent.getMveTimeCarryChePutReady());
        infoBean.setTimeCarryComplete(inEvent.getMveTimeCarryComplete());
        infoBean.setTimeDischarge(inEvent.getMveTimeDischarge());
        infoBean.setTimeDispatch(inEvent.getMveTimeDispatch());
        infoBean.setTimeFetch(inEvent.getMveTimeFetch());
        infoBean.setTimePut(inEvent.getMveTimePut());
        infoBean.setTimeTzArrival(inEvent.getMveTZArrivalTime());
        infoBean.setTwinCarry(inEvent.getMveTwinCarry());
        infoBean.setTwinFetch(inEvent.getMveTwinFetch());
        infoBean.setTwinPut(inEvent.getMveTwinPut());
        infoBean.setWiBerth(inEvent.getMveBerth());
        infoBean.setWiPow(inEvent.getMvePOW());
        return infoBean;
    }
}
