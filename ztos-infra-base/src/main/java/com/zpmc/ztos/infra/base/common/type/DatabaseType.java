package com.zpmc.ztos.infra.base.common.type;

import java.io.Serializable;

public class DatabaseType implements Serializable {

    public static final DatabaseType ORACLE = new DatabaseType("ORACLE");
    public static final DatabaseType MYSQL = new DatabaseType("MYSQL");
    public static final DatabaseType POSTGRES = new DatabaseType("POSTGRES");
    public static final DatabaseType SQLSERVER = new DatabaseType("SQLSERVER");
    public static final DatabaseType INFORMIX = new DatabaseType("INFORMIX");
    public static final DatabaseType HSQL = new DatabaseType("HSQL");
    public static final DatabaseType UNKNOWN = new DatabaseType("UNKNOWN");
    private final String _name;

    private DatabaseType(String inName) {
        this._name = inName;
    }

    public String getName() {
        return this._name;
    }

    public String toString() {
        return this._name;
    }
}
