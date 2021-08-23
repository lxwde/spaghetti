package com.zpmc.ztos.infra.base.common.database;

import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEventLogger;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticRequestManager;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.events.SqlPerformanceEvent;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.DiagnosticUtils;
import com.zpmc.ztos.infra.base.common.utils.QueryLoggingConfigurationUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlPerformanceInterceptor extends EmptyInterceptor
  //      implements IPerformanceInterceptor
{
    private String _lastPreparedSelectStatement;
    private static final Logger LOGGER = Logger.getLogger(SqlPerformanceInterceptor.class);

    public String onPrepareStatement(String inSql) {
        TransactionParms tp;
        DataSourceDatabaseType databaseTypeBean;
        String databaseType;
        this._lastPreparedSelectStatement = inSql = super.onPrepareStatement(inSql);
        if (inSql != null && (inSql.startsWith("insert") || inSql.startsWith("INSERT")) && "oracle".equals(databaseType = (databaseTypeBean = (DataSourceDatabaseType) PortalApplicationContext.getBean("dataSourceDatabaseType")).getDatabaseType())) {
            return this._lastPreparedSelectStatement;
        }
        if (inSql != null && QueryUtils.isSqlCommentsOn() && (tp = TransactionParms.getBoundParms()) != null) {
            UserContext uc = tp.getUserContext();
            this._lastPreparedSelectStatement = QueryUtils.insertDefaultComment(inSql, uc);
            return this._lastPreparedSelectStatement;
        }
        return inSql;
    }

//    @Override
    public void afterStatementExecute(long inDurationMilliseconds, ResultSet inResultSet, PreparedStatement inStatement) {
        try {
            IDiagnosticRequestManager manager;
            IDiagnosticEvent event = null;
            if (QueryLoggingConfigurationUtils.getConfiguration().isLoggingEnabled() && inDurationMilliseconds >= QueryLoggingConfigurationUtils.getConfiguration().getThresholdInMillis()) {
                IDiagnosticEventLogger diagnosticEventLogger = (IDiagnosticEventLogger) Roastery.getBean("diagnosticEventLogger");
                event = this.createSqlPerformanceEvent(inDurationMilliseconds);
                diagnosticEventLogger.log(event);
            }
            if ((manager = DiagnosticUtils.shouldRecordDiagnostic()) != null) {
                if (event == null) {
                    event = this.createSqlPerformanceEvent(inDurationMilliseconds);
                }
                manager.recordEvent(event);
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Error occured while logging diagnostic Sql query execution for performance: " + t));
        }
    }

    private IDiagnosticEvent createSqlPerformanceEvent(long inDurationMilliseconds) {
        SqlPerformanceEvent event = new SqlPerformanceEvent(inDurationMilliseconds, this._lastPreparedSelectStatement);
        event.appendStackTrace("afterStatementExecute");
        return event;
    }

    protected final String getLastPreparedSelectStatement() {
        return this._lastPreparedSelectStatement;
    }

}
