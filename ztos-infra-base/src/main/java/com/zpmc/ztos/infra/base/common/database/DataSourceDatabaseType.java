package com.zpmc.ztos.infra.base.common.database;

import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.SelfDiagnosisParameters;

import javax.sql.DataSource;

public class DataSourceDatabaseType {
    public static final String BEAN_ID = "dataSourceDatabaseType";
    private javax.sql.DataSource _dataSource;

    public void setDataSource(javax.sql.DataSource inDataSource) {
        this._dataSource = inDataSource;
    }

    public String getDatabaseType() {
        SelfDiagnosisParameters parameters = (SelfDiagnosisParameters) PortalApplicationContext.getBean("selfDiagnosisParameters");
        String oracleDatasourceClassName = parameters.getConstant("db:datasource:oracle");
        String tomcatDatasourceClassName = parameters.getConstant("db:datasource:other:tomcat");
        if (tomcatDatasourceClassName.equals(this._dataSource.getClass().getName())) {
            DataSource ds = (DataSource)this._dataSource;
//            String driverClassName = ds.getDriverClassName().toLowerCase();
            String driverClassName = ds.getClass().getName().toLowerCase();
            if (driverClassName.equals("org.postgresql.driver")) {
                return "postgres";
            }
            if (driverClassName.equals("com.mysql.jdbc.driver")) {
                return "mysql";
            }
            if (driverClassName.equals("net.sourceforge.jtds.jdbc.driver")) {
                return "sqlserver";
            }
            if (driverClassName.equals("org.hsqldb.jdbcdriver")) {
                return "hsql";
            }
        } else if (oracleDatasourceClassName.equals(this._dataSource.getClass().getName())) {
            return "oracle";
        }
        return "unknown";
    }

}
