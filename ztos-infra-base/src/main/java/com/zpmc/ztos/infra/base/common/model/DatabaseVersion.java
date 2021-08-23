package com.zpmc.ztos.infra.base.common.model;

public class DatabaseVersion {

    public static final DatabaseVersion ORACLE8 = new DatabaseVersion("ORACLE8");
    public static final DatabaseVersion ORACLE9 = new DatabaseVersion("ORACLE9");
    public static final DatabaseVersion ORACLE10 = new DatabaseVersion("ORACLE10");
    public static final DatabaseVersion ORACLE10_RAC = new DatabaseVersion("ORACLE10_RAC");
    public static final DatabaseVersion ORACLE11 = new DatabaseVersion("ORACLE11");
    public static final DatabaseVersion ORACLE11_RAC = new DatabaseVersion("ORACLE11_RAC");
    public static final DatabaseVersion ORACLE12 = new DatabaseVersion("ORACLE12");
    public static final DatabaseVersion ORACLE12_RAC = new DatabaseVersion("ORACLE12_RAC");
    public static final DatabaseVersion SQLSERVER_2005 = new DatabaseVersion("SQLSERVER_2005");
    public static final DatabaseVersion SQLSERVER_2008 = new DatabaseVersion("SQLSERVER_2008");
    public static final DatabaseVersion SQLSERVER_2012 = new DatabaseVersion("SQLSERVER_2012");
    public static final DatabaseVersion SQLSERVER_2014 = new DatabaseVersion("SQLSERVER_2014");
    public static final DatabaseVersion MYSQL5_6 = new DatabaseVersion("MYSQL5_6");
    public static final DatabaseVersion POSTGRES9_3 = new DatabaseVersion("POSTGRES9_3");
    public static final DatabaseVersion UNKNOWN = new DatabaseVersion("UNKNOWN");
    private final String _version;

    private DatabaseVersion(String inVersion) {
        this._version = inVersion;
    }

    public String getVersion() {
        return this._version;
    }

    public String toString() {
        return this._version;
    }
}
