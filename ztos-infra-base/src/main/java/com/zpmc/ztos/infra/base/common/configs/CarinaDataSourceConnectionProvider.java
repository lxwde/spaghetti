package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.util.PropertiesHelper;
import org.springframework.orm.hibernate3.LocalDataSourceConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class CarinaDataSourceConnectionProvider extends LocalDataSourceConnectionProvider {
    private int _defaultIsolationLevel = 2;
    private static final Logger LOGGER = Logger.getLogger(CarinaDataSourceConnectionProvider.class);

    public void configure(Properties inProps) throws HibernateException {
        Integer isolation;
        super.configure(inProps);
        if (inProps.containsKey("hibernate.connection.isolation") && (isolation = PropertiesHelper.getInteger((String)"hibernate.connection.isolation", (Properties)inProps)) != null) {
            this._defaultIsolationLevel = isolation;
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        if (connection == null) {
            LOGGER.warn((Object)"Cannot get connection between this node and the database. Please test network connection to the database.");
            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__CONNECTION, null);
        }
        if (connection.getTransactionIsolation() != this._defaultIsolationLevel) {
            connection.setTransactionIsolation(this._defaultIsolationLevel);
        }
        return connection;
    }

}
