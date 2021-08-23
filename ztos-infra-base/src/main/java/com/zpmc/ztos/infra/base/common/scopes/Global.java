package com.zpmc.ztos.infra.base.common.scopes;

import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IScopeNodeEntity;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Collection;

public class Global implements IScopeNodeEntity {

    private static Global _instance;

    public static Global getInstance() {
        if (_instance == null) {
            _instance = new Global();
        }
        return _instance;
    }

    public IScopeEnum getScopeEnum() {
        return ScopeEnum.GLOBAL;
    }

    public IScopeNodeEntity getParent() {
        return null;
    }

    public Collection getChildren() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Operator");
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public Serializable getPrimaryKey() {
        return null;
    }

    public String getId() {
        return null;
    }

    public String getPathName() {
        return null;
    }

}
