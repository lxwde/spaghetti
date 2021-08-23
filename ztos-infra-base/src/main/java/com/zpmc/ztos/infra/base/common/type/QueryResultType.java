package com.zpmc.ztos.infra.base.common.type;

import java.io.Serializable;

public class QueryResultType implements Serializable {
    private String result;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public QueryResultType() {
    }

    public QueryResultType(String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof QueryResultType)) {
            return false;
        }
        QueryResultType other = (QueryResultType)obj;
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
        boolean _equals = this.result == null && other.getResult() == null || this.result != null && this.result.equals(other.getResult());
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getResult() != null) {
            _hashCode += this.getResult().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
