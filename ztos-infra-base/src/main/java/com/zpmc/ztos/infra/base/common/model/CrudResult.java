package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

import java.io.Serializable;

public class CrudResult implements Serializable {

    boolean _hadViolations;
    Object _newInstanceKey;
    Object _crudOperationKey;

    public CrudResult() {
    }

    public CrudResult(Object inCrudOperationKey) {
        this._crudOperationKey = inCrudOperationKey;
    }

    public void registerViolation(BizViolation inViolations) {
        this._hadViolations = inViolations != null;
    }

    public void registerNewInstanceKey(Object inPrimaryKey) {
        this._newInstanceKey = inPrimaryKey;
    }

    public boolean hadViolations() {
        return this._hadViolations;
    }

    public boolean isHadViolations() {
        return this._hadViolations;
    }

    public void setHadViolations(boolean inHadViolations) {
        this._hadViolations = inHadViolations;
    }

    public void setNewInstanceKey(Serializable inNewInstanceKey) {
        this._newInstanceKey = inNewInstanceKey;
    }

    public Object getNewInstanceKey() {
        return this._newInstanceKey;
    }

    public void setCrudOperationKey(Serializable inCrudOperationKey) {
        this._crudOperationKey = inCrudOperationKey;
    }

    public Object getCrudOperationKey() {
        return this._crudOperationKey;
    }

}
