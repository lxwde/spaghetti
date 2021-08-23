package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UfvTransitStateEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;

import java.util.Date;

public class RectifyParms {
    private boolean _generateCue = true;
    private UfvTransitStateEnum _ufvTransitState;
    private UnitVisitStateEnum _unitVisitState;
    private Date _timeIn;
    private Date _timeOut;
    private Date _timeOfLoading;
    private Date _timeOfLastMove;
    private Date _timeComplete;
    private CarrierVisit _ibCv;
    private CarrierVisit _obCv;
    private String _slot;
    private LocPosition _position;
    private boolean _eraseHistory;
    private EventEnum _eventToRecord;
    private String _eventNote;
    private boolean _insistOnValidYardBin;
    private Boolean _visibleInSparcs;

    public UfvTransitStateEnum getUfvTransitState() {
        return this._ufvTransitState;
    }

    public void setUfvTransitState(UfvTransitStateEnum inUfvTransitState) {
        this._ufvTransitState = inUfvTransitState;
    }

    public UnitVisitStateEnum getUnitVisitState() {
        return this._unitVisitState;
    }

    public void setUnitVisitState(UnitVisitStateEnum inUnitVisitState) {
        this._unitVisitState = inUnitVisitState;
    }

    public Date getTimeIn() {
        return this._timeIn;
    }

    public void setTimeIn(Date inTimeIn) {
        this._timeIn = inTimeIn;
    }

    public Date getTimeOut() {
        return this._timeOut;
    }

    public void setTimeOut(Date inTimeOut) {
        this._timeOut = inTimeOut;
    }

    public Date getTimeOfLoading() {
        return this._timeOfLoading;
    }

    public void setTimeOfLoading(Date inTimeOfLoading) {
        this._timeOfLoading = inTimeOfLoading;
    }

    public Date getTimeOfLastMove() {
        return this._timeOfLastMove;
    }

    public void setTimeOfLastMove(Date inTimeOfLastMove) {
        this._timeOfLastMove = inTimeOfLastMove;
    }

    public Date getTimeComplete() {
        return this._timeOfLastMove;
    }

    public void setTimeComplete(Date inTimeComplete) {
        this._timeComplete = inTimeComplete;
    }

    public CarrierVisit getIbCv() {
        return this._ibCv;
    }

    public void setIbCv(CarrierVisit inIbCv) {
        this._ibCv = inIbCv;
    }

    public CarrierVisit getObCv() {
        return this._obCv;
    }

    public void setObCv(CarrierVisit inObCv) {
        this._obCv = inObCv;
    }

    public String getSlot() {
        return this._slot;
    }

    public void setSlot(String inSlot) {
        this._slot = inSlot;
    }

    public LocPosition getPosition() {
        return this._position;
    }

    public void setPosition(LocPosition inPosition) {
        this._position = inPosition;
    }

    public boolean isEraseHistory() {
        return this._eraseHistory;
    }

    public void setEraseHistory(boolean inEraseHistory) {
        this._eraseHistory = inEraseHistory;
    }

    public EventEnum getEventToRecord() {
        return this._eventToRecord;
    }

    public void setEventToRecord(EventEnum inEventToRecord) {
        this._eventToRecord = inEventToRecord;
    }

    public String getEventNote() {
        return this._eventNote;
    }

    public void setEventNote(String inEventNote) {
        this._eventNote = inEventNote;
    }

    public boolean isInsistOnValidYardBin() {
        return this._insistOnValidYardBin;
    }

    public void setInsistOnValidYardBin(boolean inInsistOnValidYardBin) {
        this._insistOnValidYardBin = inInsistOnValidYardBin;
    }

    public boolean generateCue() {
        return this._generateCue;
    }

    public void setGenerateCue(boolean inGenerateCue) {
        this._generateCue = inGenerateCue;
    }

    public Boolean isVisibleInSparcs() {
        return this._visibleInSparcs;
    }

    public void setVisibleInSparcs(Boolean inVisibleInSparcs) {
        this._visibleInSparcs = inVisibleInSparcs;
    }
}
