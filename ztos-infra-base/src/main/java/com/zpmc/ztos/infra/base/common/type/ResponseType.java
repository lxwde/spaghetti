package com.zpmc.ztos.infra.base.common.type;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ResponseType {
    private String status;
    private String statusDescription;
    private MessageCollectorType messageCollector;
    private QueryResultType[] queryResults;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public ResponseType() {
    }

    public ResponseType(String status, String statusDescription, MessageCollectorType messageCollector, QueryResultType[] queryResults) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.messageCollector = messageCollector;
        this.queryResults = queryResults;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return this.statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public MessageCollectorType getMessageCollector() {
        return this.messageCollector;
    }

    public void setMessageCollector(MessageCollectorType messageCollector) {
        this.messageCollector = messageCollector;
    }

    public QueryResultType[] getQueryResults() {
        return this.queryResults;
    }

    public void setQueryResults(QueryResultType[] queryResults) {
        this.queryResults = queryResults;
    }

    public QueryResultType getQueryResults(int i) {
        return this.queryResults[i];
    }

    public void setQueryResults(int i, QueryResultType _value) {
        this.queryResults[i] = _value;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ResponseType)) {
            return false;
        }
        ResponseType other = (ResponseType)obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
        }
        this.__equalsCalc = obj;
        boolean _equals = (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && (this.statusDescription == null && other.getStatusDescription() == null || this.statusDescription != null && this.statusDescription.equals(other.getStatusDescription())) && (this.messageCollector == null && other.getMessageCollector() == null || this.messageCollector != null && this.messageCollector.equals(other.getMessageCollector())) && (this.queryResults == null && other.getQueryResults() == null || this.queryResults != null && Arrays.equals(this.queryResults, other.getQueryResults()));
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getStatus() != null) {
            _hashCode += this.getStatus().hashCode();
        }
        if (this.getStatusDescription() != null) {
            _hashCode += this.getStatusDescription().hashCode();
        }
        if (this.getMessageCollector() != null) {
            _hashCode += this.getMessageCollector().hashCode();
        }
        if (this.getQueryResults() != null) {
            for (int i = 0; i < Array.getLength(this.getQueryResults()); ++i) {
                Object obj = Array.get(this.getQueryResults(), i);
                if (obj == null || obj.getClass().isArray()) continue;
                _hashCode += obj.hashCode();
            }
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
