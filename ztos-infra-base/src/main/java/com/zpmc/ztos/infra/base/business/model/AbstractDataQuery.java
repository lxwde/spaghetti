package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDataQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IEntityId;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.model.Ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDataQuery implements IDataQuery {
    protected MetafieldIdList _metafieldIds = new MetafieldIdList();
    protected List _orderings = new ArrayList();
    private String _entityName;
    private int _firstResult;
    private int _maxResults;
    private boolean _isCountTotalNeeded = true;

    @Override
    public void setQueryEntityName(String inEntityName) {
        this._entityName = inEntityName;
    }

    @Override
    public IDataQuery createEnhanceableCloneWithoutPredicates() {
        return this.createEnhanceableClone();
    }

    @Override
    public String getQueryEntityName() {
        return this._entityName;
    }

    @Override
    public IEntityId getEntityId() {
        return EntityIdFactory.valueOf(this._entityName);
    }

    @Override
    public int getMaxResults() {
        return this._maxResults;
    }

    @Override
    public void setMaxResults(int inMaxResults) {
        this._maxResults = inMaxResults;
    }

    @Override
    public int getFirstResult() {
        return this._firstResult;
    }

    @Override
    public void setFirstResult(int inFirstResult) {
        this._firstResult = inFirstResult;
    }

    @Override
    public boolean isQueryBounded() {
        return this._maxResults > 0;
    }

    @Override
    public void setOrderings(Ordering[] inOrderings) {
        this.setOrderings(Arrays.asList(inOrderings));
    }

    @Override
    public void setOrderings(List inOrderings) {
        this._orderings = new ArrayList(inOrderings);
    }

    @Override
    public List getOrderings() {
        return new ArrayList(this._orderings);
    }

    @Override
    @Deprecated
    public List getDefaultDisplayFields() {
        ArrayList<IMetafieldId> fields = new ArrayList<IMetafieldId>();
        for (IMetafieldId metafieldId : this._metafieldIds) {
            fields.add(metafieldId);
        }
        return fields;
    }

    @Override
    @Deprecated
    public String[] getFields() {
        return this._metafieldIds.asQualifiedStringArray();
    }

    @Override
    public MetafieldIdList getMetafieldIds() {
        return new MetafieldIdList(this._metafieldIds);
    }

    @Override
    public void addMetafieldIds(MetafieldIdList inFieldIds) {
        this._metafieldIds.addAll(inFieldIds);
    }

    @Override
    public void setMetafieldIds(MetafieldIdList inFieldIds) {
        this._metafieldIds = new MetafieldIdList(inFieldIds);
    }

    @Override
    public boolean isTotalCountRequired() {
        return this._isCountTotalNeeded;
    }

    @Override
    public void setRequireTotalCount(boolean inIsCountTotalNeeded) {
        this._isCountTotalNeeded = inIsCountTotalNeeded;
    }

    @Override
    @Deprecated
    public void addFields(String[] inFieldIds) {
        if (inFieldIds == null) {
            return;
        }
        for (String inFieldId : inFieldIds) {
            IMetafieldId id = MetafieldIdFactory.valueOf(inFieldId);
            this._metafieldIds.add(id);
        }
    }

    protected final void copyDataQuery(@NotNull IDataQuery inDestQuery) {
        inDestQuery.setOrderings(this._orderings);
        inDestQuery.setQueryEntityName(this._entityName);
        inDestQuery.setMetafieldIds(this._metafieldIds);
        inDestQuery.setFirstResult(this._firstResult);
        inDestQuery.setMaxResults(this._maxResults);
        inDestQuery.setRequireTotalCount(this._isCountTotalNeeded);
    }

}
