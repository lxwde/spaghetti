package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.common.utils.DatabaseUtils;
import com.zpmc.ztos.infra.base.common.utils.StringUtil;

import java.util.*;

public class CarinaBaseIndex implements ICarinaIndex {
    protected List<String> _columnNames = new ArrayList<String>();
    protected Map<Object, Object> _indexMap = new HashMap<Object, Object>();
    protected boolean _isDatabaseSpecific;

    protected CarinaBaseIndex() {
    }

    public CarinaBaseIndex(String string, List<String> list, String string2) {
        if (string2 == null) {
            String[] arrstring = new String[list.size()];
            string2 = DatabaseUtils.generateIndexName(string, string2, list.toArray(arrstring));
        }
        this._indexMap.put(TABLE_NAME_MFID, string);
        this._indexMap.put(INDEX_NAME_MFID, string2);
        this._indexMap.put(INDEX_DIFF_MFID, "OK");
        this.setColumnNames(list);
    }

    public CarinaBaseIndex(Map<Object, Object> map) {
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            this._indexMap.put(entry.getKey().toString().toUpperCase(), entry.getValue());
        }
        this._indexMap.put(INDEX_DIFF_MFID, "OK");
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarinaBaseIndex)) {
            return false;
        }
        CarinaBaseIndex carinaBaseIndex = (CarinaBaseIndex)object;
        String string = StringUtil.join(this.getColumnNames().iterator(), null);
        String string2 = StringUtil.join(carinaBaseIndex.getColumnNames().iterator(), null);
        boolean bl = carinaBaseIndex.getTableName().equalsIgnoreCase(this.getTableName()) && string.equalsIgnoreCase(string2);
        boolean bl2 = carinaBaseIndex.getIndexName().equalsIgnoreCase(this.getIndexName());
        return bl2 || bl;
    }

    @Override
    public boolean matchesExactly(ICarinaIndex iCarinaIndex) {
        String string = StringUtil.join(this.getColumnNames().iterator(), null);
        String string2 = StringUtil.join(iCarinaIndex.getColumnNames().iterator(), null);
        boolean bl = iCarinaIndex.getTableName().equalsIgnoreCase(this.getTableName()) && string.equalsIgnoreCase(string2);
        boolean bl2 = iCarinaIndex.getIndexName().equalsIgnoreCase(this.getIndexName());
        return bl2 && bl;
    }

    @Override
    public boolean isDuplicatedBy(ICarinaIndex iCarinaIndex) {
        List<String> list = this.getColumnNames();
        List<String> list2 = iCarinaIndex.getColumnNames();
        if (list.get(0) == null) {
            return false;
        }
        if (list2.get(0) == null) {
            return true;
        }
        if (list.size() > 2) {
            return false;
        }
        return list2.size() == 1 && list.get(0).equalsIgnoreCase(list2.get(0));
    }

    @Override
    public int hashCode() {
        return this._indexMap.hashCode();
    }

    @Override
    public String getTableName() {
        Object object = this._indexMap.get(TABLE_NAME_MFID);
        return object == null ? "" : object.toString();
    }

    @Override
    public String getIndexName() {
        Object object = this._indexMap.get(INDEX_NAME_MFID);
        return object == null ? "" : object.toString();
    }

    @Override
    public String getNativeSqlQuery() {
        Object object = this._indexMap.get(NATIVE_SQL_MFID);
        return object == null ? "" : object.toString();
    }

    @Override
    public List<String> getColumnNames() {
        return this._columnNames;
    }

    @Override
    public Map<Object, Object> getIndexMap() {
        return new HashMap<Object, Object>(this._indexMap);
    }

    @Override
    public boolean isMulticolumn() {
        return this._columnNames.size() > 1;
    }

    @Override
    public boolean isNativeSql() {
        return this._indexMap.get(NATIVE_SQL_MFID) != null;
    }

    @Override
    public void setNativeSqlQuery(String string) {
        this._indexMap.put(NATIVE_SQL_MFID, string);
    }

    @Override
    public final void setColumnNames(List<String> list) {
        this._columnNames = new ArrayList<String>();
        for (String string : list) {
            this._columnNames.add(string.toUpperCase());
        }
    }

    @Override
    public void setIndexName(String string) {
        this._indexMap.put(INDEX_NAME_MFID, string);
    }

    @Override
    public String getIndexDiff() {
        return this._indexMap.get(INDEX_DIFF_MFID).toString();
    }

    @Override
    public void setMissedFlag() {
        this._indexMap.put(INDEX_DIFF_MFID, "MISSED");
    }

    @Override
    public void setExtraFlag() {
        this._indexMap.put(INDEX_DIFF_MFID, "EXTRA");
    }

    @Override
    public void setDuplicationFlag() {
        this._indexMap.put(INDEX_DIFF_MFID, "DUPLICATED");
    }

    @Override
    public boolean isDuplicated() {
        String string = this._indexMap.get(INDEX_DIFF_MFID).toString();
        return "DUPLICATED".equalsIgnoreCase(string);
    }

    @Override
    public boolean isExtra() {
        String string = this._indexMap.get(INDEX_DIFF_MFID).toString();
        return "EXTRA".equalsIgnoreCase(string);
    }

    @Override
    public void setMissedFkFlag() {
        this._indexMap.put(INDEX_DIFF_MFID, "FK");
    }

    @Override
    public void setDiffFlag(String string) {
        if ("MISSED".equalsIgnoreCase(string)) {
            this.setMissedFlag();
        } else if ("EXTRA".equalsIgnoreCase(string)) {
            this.setExtraFlag();
        } else if ("DUPLICATED".equalsIgnoreCase(string)) {
            this.setDuplicationFlag();
        } else if ("FK".equalsIgnoreCase(string)) {
            this.setMissedFkFlag();
        }
    }

    @Override
    public List<String> getIncludeColumnNameList() {
        return Collections.emptyList();
    }

    @Override
    public void setIncludeColumnNameList(List<String> list) {
    }

    @Override
    public boolean isDatabaseSpecific() {
        return this._isDatabaseSpecific;
    }

    @Override
    public void setDatabaseSpecific(boolean bl) {
        this._isDatabaseSpecific = bl;
    }

    @Override
    public String getIndexUsageString() {
        return (String)this._indexMap.get(INDEX_USAGE_MFID);
    }

    @Override
    public void setIndexUsageString(String string) {
        this._indexMap.put(INDEX_USAGE_MFID, string);
    }

    @Override
    public String getIndexFragmentationInfo() {
        return (String)this._indexMap.get(INDEX_FRAGM_MFID);
    }

    @Override
    public void setIndexFragmentationInfo(String string) {
        this._indexMap.put(INDEX_FRAGM_MFID, string);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("Index name: " + this.getIndexName() + "\n");
        stringBuilder.append("On table: " + this.getTableName() + "\n");
        stringBuilder.append("Column names: " + this.getColumnNames() + "\n");
        stringBuilder.append("Native SQL: " + this.getNativeSqlQuery() + "\n");
        return stringBuilder.toString();
    }
}
