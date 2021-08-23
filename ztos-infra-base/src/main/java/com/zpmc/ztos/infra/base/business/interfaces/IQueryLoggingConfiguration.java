package com.zpmc.ztos.infra.base.business.interfaces;

public interface IQueryLoggingConfiguration {
    public static final String BEAN_ID = "queryLoggingConfiguration";
    public static final String LOGGING_ENABLED_PROPNAME = "com.navis.framework.persistence.diagnostic.loggingEnabled";
    public static final String LOG_DEFAULT_THRESHOLD_IN_MILLIS_PROPNAME = "com.navis.framework.persistence.diagnostic.thresholdInMillis";
    public static final boolean DEFAULT_LOGGING_ENABLED = true;
    public static final long DEFAULT_THRESHOLD_IN_MILLIS = 10000L;

    public void loadLoggingEnabled();

    public void setLoggingEnabled(boolean var1);

    public boolean isLoggingEnabled();

    public void loadThresholdInMillis();

    public void setThresholdInMillis(long var1);

    public long getThresholdInMillis();

    public void reset();
}
