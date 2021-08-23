package com.zpmc.ztos.infra.base.common.type;

import java.io.Serializable;

public class MessageType implements Serializable {
    private String message;
    private String severityLevel;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public MessageType() {
    }

    public MessageType(String message, String severityLevel) {
        this.message = message;
        this.severityLevel = severityLevel;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverityLevel() {
        return this.severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MessageType)) {
            return false;
        }
        MessageType other = (MessageType)obj;
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
        boolean _equals = (this.message == null && other.getMessage() == null || this.message != null && this.message.equals(other.getMessage())) && (this.severityLevel == null && other.getSeverityLevel() == null || this.severityLevel != null && this.severityLevel.equals(other.getSeverityLevel()));
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getMessage() != null) {
            _hashCode += this.getMessage().hashCode();
        }
        if (this.getSeverityLevel() != null) {
            _hashCode += this.getSeverityLevel().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
