package com.zpmc.ztos.infra.base.common.callbacks;

import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.model.Roastery;

public abstract class CarinaPersistenceCallback {
    private Object _result;
    private HibernateApi _hibernateApi;

    protected CarinaPersistenceCallback() {
        this._hibernateApi = (HibernateApi) Roastery.getBean("hibernateApi");
    }

    protected HibernateApi getHibernateApi() {
        return this._hibernateApi;
    }

    protected CarinaPersistenceCallback(Object inResult) {
        this._result = inResult;
    }

    public abstract void doInTransaction();

    public Object getResult() {
        return this._result;
    }

    public void setResult(Object inResult) {
        this._result = inResult;
    }
}
