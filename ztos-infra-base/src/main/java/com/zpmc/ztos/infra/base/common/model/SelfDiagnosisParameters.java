package com.zpmc.ztos.infra.base.common.model;

import java.util.Collections;
import java.util.Map;

public class SelfDiagnosisParameters {
    public static final String BEAN_ID = "selfDiagnosisParameters";
    public static final String CONST_DB_DS_OTHER = "db:datasource:other:tomcat";
    public static final String CONST_DB_DS_ORACLE = "db:datasource:oracle";
    public static final String CONST_DB_DS_BASIC = "db:datasource:basic";
    public static final String CONST_DB_DS_ORACLE_STATS = "db:datasource:oracle:stats";
    public static final String JVM_MAX_PERM_SIZE = "MaxPermSize";
    public static final String JVM_MX_32BIT = "mx:32bit";
    public static final String JVM_MX_64BIT = "mx:64bit";
    public static final String JVM_MX_64BIT_FOR_CONC_GC = "mx:64bit:concgc";
    public static final String DB_NUM_CPUS_PER_INSTANCE = "numCPUsPerInstance";
    public static final String DB_CONN_MAX_LIMIT = "connMaxLimit";
    public static final String DB_CONN_MAX_STMT_LIMIT = "connMaxStatementsLimit";
    public static final String DB_CONN_MAX_ACTIVE = "connMaxActive";
    public static final String DB_CONN_MAX_WAIT = "connMaxWait";
    private Map<String, String> _constants = Collections.emptyMap();
    private Map<String, Threshold> _jvmThresholds = Collections.emptyMap();
    private Map<String, Threshold> _dbThresholds = Collections.emptyMap();

    public String getConstant(String inParmKey) {
        return this._constants.get(inParmKey);
    }

    public void setConstants(Map<String, String> inConstants) {
        this._constants = inConstants;
    }

    public void setDbThresholds(Map<String, Threshold> inDbThresholds) {
        this._dbThresholds = inDbThresholds;
    }

    public void setJvmThresholds(Map<String, Threshold> inJvmThresholds) {
        this._jvmThresholds = inJvmThresholds;
    }

    public Map<String, Threshold> getDbThresholds() {
        return Collections.unmodifiableMap(this._dbThresholds);
    }

    public Map<String, Threshold> getJvmThresholds() {
        return Collections.unmodifiableMap(this._jvmThresholds);
    }

    public static class Threshold {
        private Long _val1;
        private Long _val2;
        private Long _recommended;

        public void setError(Long inError) {
            this._val1 = inError;
        }

        public void setWarn(Long inWarn) {
            this._val2 = inWarn;
        }

        public void setLow(Long inLow) {
            this._val1 = inLow;
        }

        public void setHigh(Long inHigh) {
            this._val2 = inHigh;
        }

        public void setMin(Long inMin) {
            this._val1 = inMin;
        }

        public void setMax(Long inMax) {
            this._val2 = inMax;
        }

        public Long getError() {
            return this._val1;
        }

        public Long getWarn() {
            return this._val2;
        }

        public Long getLow() {
            return this._val1;
        }

        public Long getHigh() {
            return this._val2;
        }

        public Long getMin() {
            return this._val1;
        }

        public Long getMax() {
            return this._val2;
        }

        public Long getRecommended() {
            return this._recommended;
        }

        public void setRecommended(Long inRecommended) {
            this._recommended = inRecommended;
        }
    }

}
