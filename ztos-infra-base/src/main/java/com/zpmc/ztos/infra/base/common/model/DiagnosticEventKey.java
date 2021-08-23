package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;

import java.io.Serializable;

public class DiagnosticEventKey implements Serializable {

    private Integer _sequence;
    private Integer _eventHash;

    public static DiagnosticEventKey valueOf(IDiagnosticEvent inEvent) {
        return new DiagnosticEventKey(inEvent.hashCode(), inEvent.getEventSequence());
    }

    public int getSequence() {
        return this._sequence;
    }

    public void setSequence(int inSequence) {
        this._sequence = inSequence;
    }

    DiagnosticEventKey(Integer inHash, Integer inSequence) {
        this._eventHash = inHash;
        this._sequence = inSequence;
    }

    public Integer getEventHash() {
        return this._eventHash;
    }

    public String toString() {
        return this._sequence + " - " + this._eventHash;
    }
}
