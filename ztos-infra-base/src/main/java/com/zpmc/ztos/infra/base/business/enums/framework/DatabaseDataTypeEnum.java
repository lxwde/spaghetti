package com.zpmc.ztos.infra.base.business.enums.framework;

import com.sun.istack.NotNull;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DatabaseDataTypeEnum {
    BLOB("blob"),
    CLOB("clob"),
    BINARY("binary"),
    VARBINARY("varbinary"),
    VARBINARY_MAX("varbinary(max)"),
    NVARCHAR("nvarchar"),
    NVARCHAR_MAX("nvarchar(max)"),
    TEXT("text"),
    VARCHAR("varchar"),
    VARCHAR2("varchar2"),
    BYTEA("bytea"),
    TINYINT("tinyint"),
    DATETIME("datetime"),
    FLOAT("float"),
    INT("int"),
    BIGINT("bigint"),
    SMALLINT("smallint"),
    LONGBLOB("longblob"),
    DATE("date"),
    TIME("time"),
    DOUBLE("double"),
    LONGTEXT("longtext"),
    NUMBER("number"),
    TIMESTAMP("timestamp"),
    TIMESTAMP6("timestamp(6)"),
    INT2("int2"),
    FLOAT4("float4"),
    FLOAT8("float8"),
    INT4("int4"),
    INT8("int8"),
    BOOL("bool"),
    BIT("bit"),
    CHAR("char"),
    SDO_GEOMETRY("sdo_geometry"),
    GEOMETRY("geometry"),
    USER_DEFINED("USER-DEFINED"),
    CHARACTER("character"),
    NCHAR("nchar"),
    NUMERIC("numeric"),
    DECIMAL("decimal"),
    UNKNOWN("unknown");

    private static final Map<String, DatabaseDataTypeEnum> TYPE_MAP;
    private String _typeKey;

    private DatabaseDataTypeEnum(String inTypeKey) {
        this._typeKey = inTypeKey.toLowerCase();
    }

    @NotNull
    public String getTypeKey() {
        return this._typeKey;
    }

    @NotNull
    public static DatabaseDataTypeEnum getType(@NotNull String inTypeKey) {
        if (inTypeKey == null) {
            return UNKNOWN;
        }
        DatabaseDataTypeEnum type = TYPE_MAP.get(inTypeKey.toLowerCase());
        if (type == null) {
            return UNKNOWN;
        }
        return type;
    }

    static {
        TYPE_MAP = new HashMap<String, DatabaseDataTypeEnum>();
        for (DatabaseDataTypeEnum type : EnumSet.allOf(DatabaseDataTypeEnum.class)) {
            TYPE_MAP.put(type.getTypeKey(), type);
        }
    }
}
