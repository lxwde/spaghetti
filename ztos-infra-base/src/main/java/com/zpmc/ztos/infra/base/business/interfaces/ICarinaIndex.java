package com.zpmc.ztos.infra.base.business.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ICarinaIndex extends Serializable {
    public static final String INDEX_NAME_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_NAME.getFieldId().toUpperCase();
    public static final String TABLE_NAME_MFID = IDbPerformanceGuiMetafield.DB_PERF_TABLE_NAME.getFieldId().toUpperCase();
    public static final String COLUMN_NAME_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_COLUMN_NAME.getFieldId().toUpperCase();
    public static final String INDEX_DIFF_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_DIFFERENCE.getFieldId().toUpperCase();
    public static final String INCLUDE_COLUMN_NAME_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_INCLUDE_COLUMN_NAME.getFieldId().toUpperCase();
    public static final String NATIVE_SQL_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_NATIVE_SQL.getFieldId().toUpperCase();
    public static final String INDEX_USAGE_MFID = IDbPerformanceGuiMetafield.DB_PERF_INDEX_USAGE.getFieldId().toUpperCase();
    public static final String INDEX_FRAGM_MFID = IDbPerformanceGuiMetafield.DB_PERF_FRAGM_RATIO.getFieldId().toUpperCase();
    public static final String INDEX_USAGE_NEVER = "NEVER";
    public static final String INDEX_USAGE_USED = "USED";
    public static final String DIFF_OK = "OK";
    public static final String DIFF_MISSED = "MISSED";
    public static final String DIFF_EXTRA = "EXTRA";
    public static final String DIFF_DUPLICATED = "DUPLICATED";
    public static final String DIFF_FK = "FK";

    public boolean equals(Object var1);

    public int hashCode();

    public boolean matchesExactly(ICarinaIndex var1);

    public String getIndexName();

    public List<String> getColumnNames();

    public void setColumnNames(List<String> var1);

    public List<String> getIncludeColumnNameList();

    public void setIncludeColumnNameList(List<String> var1);

    public String getTableName();

    public String getNativeSqlQuery();

    public void setNativeSqlQuery(String var1);

    public void setMissedFlag();

    public boolean isDuplicated();

    public boolean isExtra();

    public void setDuplicationFlag();

    public boolean isMulticolumn();

    public void setMissedFkFlag();

    public void setIndexName(String var1);

    public void setExtraFlag();

    public Map<Object, Object> getIndexMap();

    public boolean isNativeSql();

    public String getIndexDiff();

    public void setDiffFlag(String var1);

    public boolean isDatabaseSpecific();

    public void setDatabaseSpecific(boolean var1);

    public String getIndexUsageString();

    public void setIndexUsageString(String var1);

    public String getIndexFragmentationInfo();

    public void setIndexFragmentationInfo(String var1);

    public boolean isDuplicatedBy(ICarinaIndex var1);

}
