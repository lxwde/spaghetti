package com.zpmc.ztos.infra.base.common.events;

import com.zpmc.ztos.infra.base.business.enums.framework.FrameworkDiagnosticEventTypesEnum;

import java.util.Date;

public class SqlPerformanceEvent extends AbstractDiagnosticEvent{
    public SqlPerformanceEvent(long inDurationInMillis, String inPreparedSqlString) {
        super(FrameworkDiagnosticEventTypesEnum.SQL_SELECT.name(), new Date());
        this.setDuration(inDurationInMillis);
        this.setDescription(inPreparedSqlString);
    }
}
