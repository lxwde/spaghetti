package com.zpmc.ztos.infra.base.common.database;

import java.io.Serializable;

public class HibernatingObjRef {
    private Class _entityClass;
    private Serializable _objGkey;

    public HibernatingObjRef(Class inEntityClass, Serializable inObjGkey) {
        this._entityClass = inEntityClass;
        this._objGkey = inObjGkey;
    }

    public HibernatingObjRef() {
    }

    public Serializable getObjGkey() {
        return this._objGkey;
    }

    public Class getEntityClass() {
        return this._entityClass;
    }

    public void setEntityClass(Class inEntityClass) {
        this._entityClass = inEntityClass;
    }

    public void setObjGkey(Serializable inObjGkey) {
        this._objGkey = inObjGkey;
    }
}
