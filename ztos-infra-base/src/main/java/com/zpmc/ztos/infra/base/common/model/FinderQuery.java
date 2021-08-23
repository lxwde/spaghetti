package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.interfaces.IDataQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IQueryCriteria;
import com.zpmc.ztos.infra.base.business.model.AbstractDataQuery;
//import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FinderQuery extends AbstractDataQuery {

    private Serializable _queryPrimaryKey;
    private Map _parameters = new HashMap();
    private String _finderApiName;
    public static final String PREDICATE_PARAMETER_NAME = "finderQueryPredicateParameter";

    public static FinderQuery valueOf(String inFinderApiName) {
        FinderQuery finderQuery = new FinderQuery();
        finderQuery.setFinderApiName(inFinderApiName);
        return finderQuery;
    }

    @Override
    public IDataQuery createEnhanceableClone() {
        FinderQuery clone = new FinderQuery();
        this.copyDataQuery(clone);
        clone._queryPrimaryKey = this._queryPrimaryKey;
        clone._parameters = new HashMap(this._parameters);
        clone._finderApiName = this._finderApiName;
        return clone;
    }

    @Deprecated
    public void setDefaultDisplayFields(List inDisplayFields) {
        Iterator iterator = inDisplayFields.iterator();
        while (iterator.hasNext()) {
            this._metafieldIds.add((IMetafieldId)iterator.next());
        }
    }

    public void setQueryPrimaryKey(Serializable inQueryPrimaryKey) {
        this._queryPrimaryKey = inQueryPrimaryKey;
    }

    public Serializable getQueryPrimaryKey() {
        return this._queryPrimaryKey;
    }

    @Override
    @Nullable
    public IQueryCriteria getQueryCriteria() {
        return null;
    }

    public void setQueryParameter(String inKey, Serializable inValue) {
        this._parameters.put(inKey, inValue);
    }

    public Serializable getQueryParameter(String inKey) {
        return (Serializable)this._parameters.get(inKey);
    }

    public void setFinderApiName(String inFinderApiName) {
        this._finderApiName = inFinderApiName;
    }

    public String getFinderApiName() {
        return this._finderApiName;
    }

    public int getTotalResultCount() {
        return -1;
    }

    public String toString() {
        return '[' + this.getQueryEntityName() + ':' + this._finderApiName + ']';
    }

}
