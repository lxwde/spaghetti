package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarinaSqlserverIndex extends CarinaBaseIndex {
    protected List<String> _includeColumnNameList;

    public CarinaSqlserverIndex(Map<Object, Object> inMap) {
        super(inMap);
    }

    public CarinaSqlserverIndex(String inTableName, List<String> inColumnNames, String inIndexName, List<String> inIncludeColumnNames) {
        super(inTableName, inColumnNames, inIndexName);
        super.setIncludeColumnNameList(inIncludeColumnNames);
    }

    public CarinaSqlserverIndex(String inTableName, List<String> inColumnNames, String inIndexName) {
        super(inTableName, inColumnNames, inIndexName);
    }

    @Override
    public boolean equals(Object inObject) {
        if (!(inObject instanceof CarinaBaseIndex)) {
            return false;
        }
        ICarinaIndex inIndex = (ICarinaIndex)inObject;
        String concatenatedColumnNames = StringUtil.join(this.getColumnNames().iterator(), null);
        String concatenatedInColumnNames = StringUtil.join(inIndex.getColumnNames().iterator(), null);
        String concatenatedIncludeColumnNames = StringUtil.join(this.getIncludeColumnNameList().iterator(), null);
        String concatenatedInIncludeColumnNames = StringUtil.join(inIndex.getIncludeColumnNameList().iterator(), null);
        boolean criteria1 = inIndex.getTableName().equalsIgnoreCase(this.getTableName()) && concatenatedColumnNames.equalsIgnoreCase(concatenatedInColumnNames) && concatenatedIncludeColumnNames.equalsIgnoreCase(concatenatedInIncludeColumnNames);
        boolean criteria2 = inIndex.getIndexName().equalsIgnoreCase(this.getIndexName());
        return criteria2 || criteria1;
    }

    @Override
    public int hashCode() {
        return this._indexMap.hashCode();
    }

    @Override
    public List<String> getIncludeColumnNameList() {
        return this._includeColumnNameList == null ? new ArrayList() : this._includeColumnNameList;
    }

    @Override
    public void setIncludeColumnNameList(List<String> inIncludeColumnNameList) {
        this._includeColumnNameList = new ArrayList<String>();
        for (String columnName : inIncludeColumnNameList) {
            this._includeColumnNameList.add(columnName.toUpperCase());
        }
    }
}
