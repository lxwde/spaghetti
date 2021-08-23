package com.zpmc.ztos.infra.base.common.model;

import java.io.Serializable;

public class UniqueID implements Serializable {
    private final Long _idLong;

    public UniqueID() {
        this._idLong = (Long)UniqueGkeyFactory.generate();
    }

    public UniqueID(Long inLong) {
        this._idLong = inLong;
    }

    public String toString() {
        return this._idLong.toString();
    }

    public Serializable toGkey() {
        return this._idLong;
    }

    public boolean equals(Object inO) {
        if (inO instanceof UniqueID) {
            return this.toString().equals(inO.toString());
        }
        return false;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
