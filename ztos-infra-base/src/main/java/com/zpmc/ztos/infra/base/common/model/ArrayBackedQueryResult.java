package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IBounded;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IQueryResult;
import com.zpmc.ztos.infra.base.business.interfaces.IValueHolder;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class ArrayBackedQueryResult implements IQueryResult {
    private final String _entityName;
    private final IMetafieldId _primaryKeyField;
    private final int _fieldCount;
    private final List _valuesList;
    private final boolean _isQueryBounded;
    private final int _totalResultCount;
    private final int _firstResult;
    private final int _maxResults;
    private final MetafieldIdList _metafieldIds;
    private final Map _metafieldToIndexMap;
    private static final Logger LOGGER = Logger.getLogger(ArrayBackedQueryResult.class);

    public ArrayBackedQueryResult(String inEntityName, IMetafieldId inPrimaryKeyField, MetafieldIdList inMetafields, List inResults, int inTotalResults, IBounded inBounded) {
        this._entityName = inEntityName;
        this._primaryKeyField = inPrimaryKeyField;
        this._fieldCount = inMetafields.getSize();
        this._totalResultCount = inTotalResults;
        this._maxResults = inBounded.getMaxResults();
        this._isQueryBounded = inBounded.isQueryBounded();
        this._firstResult = inBounded.getFirstResult();
        if (inResults.isEmpty()) {
            this._valuesList = inResults;
        } else if (inResults.get(0) instanceof Object[]) {
            this._valuesList = inResults;
        } else {
            this._valuesList = new ArrayList(inResults.size());
            for (Object o : inResults) {
                this._valuesList.add(new Object[]{o});
            }
        }
        this._metafieldIds = new MetafieldIdList(inMetafields);
        this._metafieldToIndexMap = new HashMap();
        for (int i = 0; i < this._metafieldIds.getSize(); ++i) {
            IMetafieldId metafieldId = this._metafieldIds.get(i);
            Integer index = i;
            this._metafieldToIndexMap.put(metafieldId, index);
            if (metafieldId.equals(inPrimaryKeyField)) {
                this._metafieldToIndexMap.put(IMetafieldId.PRIMARY_KEY, index);
            }
            if (!metafieldId.equals(IMetafieldId.PRIMARY_KEY)) continue;
            this._metafieldToIndexMap.put(inPrimaryKeyField, index);
        }
    }

    @Override
    @Deprecated
    public List getRetrievedResults() {
        ArrayList<ValueObject> vaoList = new ArrayList<ValueObject>(this._valuesList.size());
        for (int i = 0; i < this.getCurrentResultCount(); ++i) {
            ValueObject vao = new ValueObject(this._entityName);
            vao.setPrimaryKeyField(this._primaryKeyField);
            Object[] valueArray = (Object[])this._valuesList.get(i);
            int j = 0;
            for (IMetafieldId fieldId : this._metafieldIds) {
                vao.setFieldValue(fieldId, valueArray[j++]);
            }
            int primaryKeyColumn = this.getIndexForMetafieldId(IMetafieldId.PRIMARY_KEY);
            if (primaryKeyColumn >= 0 && primaryKeyColumn < valueArray.length) {
                vao.setEntityPrimaryKey((Serializable)valueArray[primaryKeyColumn]);
                vao.setFieldValue(IMetafieldId.PRIMARY_KEY, valueArray[primaryKeyColumn]);
            }
            vaoList.add(vao);
        }
        return vaoList;
    }

    @Override
    public int getCurrentResultCount() {
        return this._valuesList.size();
    }

    @Override
    public int getLastResult() {
        return this._firstResult + this._valuesList.size() - 1;
    }

    @Override
    @Nullable
    public Object getValue(int inRow, IMetafieldId inFieldId) {
        int relativeRow = inRow - this._firstResult;
        Object[] valueArray = (Object[])this._valuesList.get(relativeRow);
        if (valueArray == null) {
            LOGGER.error((Object)("getValue: no values for row <" + inRow + ">"));
            return null;
        }
        int index = this.getIndexForMetafieldId(inFieldId);
        if (index >= 0) {
            return valueArray[index];
        }
        LOGGER.warn((Object)("getValue: no data available for " + inFieldId));
        return null;
    }

    @Override
    public Object getValue(int inRow, int inIndex) {
        if (inIndex < 0 || inIndex >= this._fieldCount) {
            throw new IllegalArgumentException("invalid index: " + inIndex);
        }
        int relativeRow = inRow - this._firstResult;
        Object[] rowValues = (Object[])this._valuesList.get(relativeRow);
        return rowValues[inIndex];
    }

    public void setValue(int inRow, int inIndex, Object inValue) {
        if (inIndex < 0 || inIndex >= this._fieldCount) {
            throw new IllegalArgumentException("invalid index: " + inIndex);
        }
        int relativeRow = inRow - this._firstResult;
        Object[] rowValues = (Object[])this._valuesList.get(relativeRow);
        rowValues[inIndex] = inValue;
    }

    @Override
    @Nullable
    public IMetafieldId getMetafieldIdForIndex(int inIndex) {
        return inIndex >= 0 && inIndex < this._fieldCount ? this._metafieldIds.get(inIndex) : null;
    }

    @Override
    public int getIndexForMetafieldId(IMetafieldId inMetafieldId) {
        Integer c = (Integer)this._metafieldToIndexMap.get(inMetafieldId);
        return c != null ? c : -1;
    }

    @Override
    public int getTotalResultCount() {
        return this._totalResultCount;
    }

    @Override
    public Iterator getIterator() {
        return this._valuesList.iterator();
    }

    @Override
    public int getMaxResults() {
        return this._maxResults;
    }

    @Override
    public int getFirstResult() {
        return this._firstResult;
    }

    @Override
    public boolean isQueryBounded() {
        return this._isQueryBounded;
    }

    @Override
    public IValueHolder getValueHolder(int inRow) {
        int relativeRow = inRow - this._firstResult;
        Object[] valueArray = (Object[])this._valuesList.get(relativeRow);
        if (valueArray == null) {
            LOGGER.warn((Object)("getValueHolder: no values available for row <" + inRow + ">"));
            valueArray = new Object[]{};
        }
  //      return new ArrayBackedValueHolder(valueArray, this, this._fieldCount);
        return null;
    }

    @Override
    public MetafieldIdList getFieldList() {
        return this._metafieldIds;
    }

    @Override
    public IMetafieldId getPrimaryKeyField() {
        return this._primaryKeyField;
    }
}
