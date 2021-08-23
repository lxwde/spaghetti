package com.zpmc.ztos.infra.base.business.interfaces;

public interface IDiagnosticEventLogger {
    public static final String BEAN_ID = "diagnosticEventLogger";

    public void log(IDiagnosticEvent var1);
}
