package com.zpmc.ztos.infra.base.business.plans;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.Date;

public class MoveHistory implements Serializable {
    private Long _mvhsFetchCheIndex;
    private String _mvhsFetchCheId;
    private Long _mvhsCarryCheIndex;
    private String _mvhsCarryCheId;
    private Long _mvhsPutCheIndex;
    private String _mvhsPutCheId;
    private Long _mvhsDistToStart;
    private Long _mvhsDistOfCarry;
    private Date _mvhsTimeCarryComplete;
    private Date _mvhsTimeDispatch;
    private Date _mvhsTimeFetch;
    private Date _mvhsTimePut;
    private Date _mvhsTimeCarryCheFetchReady;
    private Date _mvhsTimeCarryChePutReady;
    private Date _mvhsTimeCarryCheDispatch;
    private Date _mvhsTimeDischarge;
    private Long _mvhsRehandleCount;
    private Long _mvhsPowPkey;
    private Long _mvhsPoolPkey;
    private Boolean _mvhsTwinFetch;
    private Boolean _mvhsTwinCarry;
    private Boolean _mvhsTwinPut;
    private Boolean _mvhsTandemFetch;
    private Boolean _mvhsTandemPut;
    private Date _mvhsTZArrivalTime;
    private Long _mvhsFetchCheDistance;
    private Long _mvhsCarryCheDistance;
    private Long _mvhsPutCheDistance;
    private Date _mvhsTimeFetchCheDispatch;
    private Date _mvhsTimePutCheDispatch;

    public MoveHistory() {
    }

    public MoveHistory(@NotNull WorkInstruction inWorkInstruction) {
        this._mvhsFetchCheIndex = inWorkInstruction.getMvhsFetchCheIndex();
        this._mvhsFetchCheId = inWorkInstruction.getMvhsFetchCheId();
        this._mvhsCarryCheIndex = inWorkInstruction.getMvhsCarryCheIndex();
        this._mvhsCarryCheId = inWorkInstruction.getMvhsCarryCheId();
        this._mvhsPutCheIndex = inWorkInstruction.getMvhsPutCheIndex();
        this._mvhsPutCheId = inWorkInstruction.getMvhsPutCheId();
        this._mvhsDistToStart = inWorkInstruction.getMvhsDistToStart();
        this._mvhsDistOfCarry = inWorkInstruction.getMvhsDistOfCarry();
        this._mvhsTimeCarryComplete = inWorkInstruction.getMvhsTimeCarryComplete();
        this._mvhsTimeDispatch = inWorkInstruction.getMvhsTimeDispatch();
        this._mvhsTimeFetch = inWorkInstruction.getMvhsTimeFetch();
        this._mvhsTimePut = inWorkInstruction.getMvhsTimePut();
        this._mvhsTimeCarryCheFetchReady = inWorkInstruction.getMvhsTimeCarryCheFetchReady();
        this._mvhsTimeCarryChePutReady = inWorkInstruction.getMvhsTimeCarryChePutReady();
        this._mvhsTimeCarryCheDispatch = inWorkInstruction.getMvhsTimeCarryCheDispatch();
        this._mvhsTimeDischarge = inWorkInstruction.getMvhsTimeDischarge();
        this._mvhsRehandleCount = inWorkInstruction.getMvhsRehandleCount();
        this._mvhsPowPkey = inWorkInstruction.getMvhsPowPkey();
        this._mvhsPoolPkey = inWorkInstruction.getMvhsPoolPkey();
        this._mvhsTwinFetch = inWorkInstruction.getMvhsTwinFetch();
        this._mvhsTwinCarry = inWorkInstruction.getMvhsTwinCarry();
        this._mvhsTwinPut = inWorkInstruction.getMvhsTwinPut();
        this._mvhsTandemFetch = inWorkInstruction.getMvhsTandemFetch();
        this._mvhsTandemPut = inWorkInstruction.getMvhsTandemPut();
        this._mvhsTZArrivalTime = inWorkInstruction.getMvhsTZArrivalTime();
        this._mvhsFetchCheDistance = inWorkInstruction.getMvhsFetchCheDistance();
        this._mvhsCarryCheDistance = inWorkInstruction.getMvhsCarryCheDistance();
        this._mvhsPutCheDistance = inWorkInstruction.getMvhsPutCheDistance();
        this._mvhsTimeFetchCheDispatch = inWorkInstruction.getMvhsTimeFetchCheDispatch();
        this._mvhsTimePutCheDispatch = inWorkInstruction.getMvhsTimePutCheDispatch();
    }

    public Long getMvhsFetchCheIndex() {
        return this._mvhsFetchCheIndex;
    }

    public void setMvhsFetchCheIndex(Long inMvhsFetchCheIndex) {
        this._mvhsFetchCheIndex = inMvhsFetchCheIndex;
    }

    public String getMvhsFetchCheId() {
        return this._mvhsFetchCheId;
    }

    public void setMvhsFetchCheId(String inMvhsFetchCheId) {
        this._mvhsFetchCheId = inMvhsFetchCheId;
    }

    public Long getMvhsCarryCheIndex() {
        return this._mvhsCarryCheIndex;
    }

    public void setMvhsCarryCheIndex(Long inMvhsCarryCheIndex) {
        this._mvhsCarryCheIndex = inMvhsCarryCheIndex;
    }

    public String getMvhsCarryCheId() {
        return this._mvhsCarryCheId;
    }

    public void setMvhsCarryCheId(String inMvhsCarryCheId) {
        this._mvhsCarryCheId = inMvhsCarryCheId;
    }

    public Long getMvhsPutCheIndex() {
        return this._mvhsPutCheIndex;
    }

    public void setMvhsPutCheIndex(Long inMvhsPutCheIndex) {
        this._mvhsPutCheIndex = inMvhsPutCheIndex;
    }

    public String getMvhsPutCheId() {
        return this._mvhsPutCheId;
    }

    public void setMvhsPutCheId(String inMvhsPutCheId) {
        this._mvhsPutCheId = inMvhsPutCheId;
    }

    public Long getMvhsDistToStart() {
        return this._mvhsDistToStart;
    }

    public void setMvhsDistToStart(Long inMvhsDistToStart) {
        this._mvhsDistToStart = inMvhsDistToStart;
    }

    public Long getMvhsDistOfCarry() {
        return this._mvhsDistOfCarry;
    }

    public void setMvhsDistOfCarry(Long inMvhsDistOfCarry) {
        this._mvhsDistOfCarry = inMvhsDistOfCarry;
    }

    public Date getMvhsTimeCarryComplete() {
        return this._mvhsTimeCarryComplete;
    }

    public void setMvhsTimeCarryComplete(Date inMvhsTimeCarryComplete) {
        this._mvhsTimeCarryComplete = inMvhsTimeCarryComplete;
    }

    public Date getMvhsTimeDispatch() {
        return this._mvhsTimeDispatch;
    }

    public void setMvhsTimeDispatch(Date inMvhsTimeDispatch) {
        this._mvhsTimeDispatch = inMvhsTimeDispatch;
    }

    public Date getMvhsTimeFetch() {
        return this._mvhsTimeFetch;
    }

    public void setMvhsTimeFetch(Date inMvhsTimeFetch) {
        this._mvhsTimeFetch = inMvhsTimeFetch;
    }

    public Date getMvhsTimePut() {
        return this._mvhsTimePut;
    }

    public void setMvhsTimePut(Date inMvhsTimePut) {
        this._mvhsTimePut = inMvhsTimePut;
    }

    public Date getMvhsTimeCarryCheFetchReady() {
        return this._mvhsTimeCarryCheFetchReady;
    }

    public void setMvhsTimeCarryCheFetchReady(Date inMvhsTimeCarryCheFetchReady) {
        this._mvhsTimeCarryCheFetchReady = inMvhsTimeCarryCheFetchReady;
    }

    public Date getMvhsTimeCarryChePutReady() {
        return this._mvhsTimeCarryChePutReady;
    }

    public void setMvhsTimeCarryChePutReady(Date inMvhsTimeCarryChePutReady) {
        this._mvhsTimeCarryChePutReady = inMvhsTimeCarryChePutReady;
    }

    public Date getMvhsTimeCarryCheDispatch() {
        return this._mvhsTimeCarryCheDispatch;
    }

    public void setMvhsTimeCarryCheDispatch(Date inMvhsTimeCarryCheDispatch) {
        this._mvhsTimeCarryCheDispatch = inMvhsTimeCarryCheDispatch;
    }

    public Date getMvhsTimeDischarge() {
        return this._mvhsTimeDischarge;
    }

    public void setMvhsTimeDischarge(Date inMvhsTimeDischarge) {
        this._mvhsTimeDischarge = inMvhsTimeDischarge;
    }

    public Long getMvhsRehandleCount() {
        return this._mvhsRehandleCount;
    }

    public void setMvhsRehandleCount(Long inMvhsRehandleCount) {
        this._mvhsRehandleCount = inMvhsRehandleCount;
    }

    public Long getMvhsPowPkey() {
        return this._mvhsPowPkey;
    }

    public void setMvhsPowPkey(Long inMvhsPowPkey) {
        this._mvhsPowPkey = inMvhsPowPkey;
    }

    public Long getMvhsPoolPkey() {
        return this._mvhsPoolPkey;
    }

    public void setMvhsPoolPkey(Long inMvhsPoolPkey) {
        this._mvhsPoolPkey = inMvhsPoolPkey;
    }

    public Boolean getMvhsTwinFetch() {
        return this._mvhsTwinFetch;
    }

    public void setMvhsTwinFetch(Boolean inMvhsTwinFetch) {
        this._mvhsTwinFetch = inMvhsTwinFetch;
    }

    public Boolean getMvhsTwinCarry() {
        return this._mvhsTwinCarry;
    }

    public void setMvhsTwinCarry(Boolean inMvhsTwinCarry) {
        this._mvhsTwinCarry = inMvhsTwinCarry;
    }

    public Boolean getMvhsTwinPut() {
        return this._mvhsTwinPut;
    }

    public void setMvhsTwinPut(Boolean inMvhsTwinPut) {
        this._mvhsTwinPut = inMvhsTwinPut;
    }

    public Boolean getMvhsTandemFetch() {
        return this._mvhsTandemFetch;
    }

    public void setMvhsTandemFetch(Boolean inMvhsTandemFetch) {
        this._mvhsTandemFetch = inMvhsTandemFetch;
    }

    public Boolean getMvhsTandemPut() {
        return this._mvhsTandemPut;
    }

    public void setMvhsTandemPut(Boolean inMvhsTandemPut) {
        this._mvhsTandemPut = inMvhsTandemPut;
    }

    public Date getMvhsTZArrivalTime() {
        return this._mvhsTZArrivalTime;
    }

    public void setMvhsTZArrivalTime(Date inMvhsTZArrivalTime) {
        this._mvhsTZArrivalTime = inMvhsTZArrivalTime;
    }

    public Long getMvhsFetchCheDistance() {
        return this._mvhsFetchCheDistance;
    }

    public void setMvhsFetchCheDistance(Long inMvhsFetchCheDistance) {
        this._mvhsFetchCheDistance = inMvhsFetchCheDistance;
    }

    public Long getMvhsCarryCheDistance() {
        return this._mvhsCarryCheDistance;
    }

    public void setMvhsCarryCheDistance(Long inMvhsCarryCheDistance) {
        this._mvhsCarryCheDistance = inMvhsCarryCheDistance;
    }

    public Long getMvhsPutCheDistance() {
        return this._mvhsPutCheDistance;
    }

    public void setMvhsPutCheDistance(Long inMvhsPutCheDistance) {
        this._mvhsPutCheDistance = inMvhsPutCheDistance;
    }

    public Date getMvhsTimeFetchCheDispatch() {
        return this._mvhsTimeFetchCheDispatch;
    }

    public void setMvhsTimeFetchCheDispatch(Date inMvhsTimeFetchCheDispatch) {
        this._mvhsTimeFetchCheDispatch = inMvhsTimeFetchCheDispatch;
    }

    public Date getMvhsTimePutCheDispatch() {
        return this._mvhsTimePutCheDispatch;
    }

    public void setMvhsTimePutCheDispatch(Date inMvhsTimePutCheDispatch) {
        this._mvhsTimePutCheDispatch = inMvhsTimePutCheDispatch;
    }

}
