package com.zpmc.ztos.infra.base.common.type;

import java.io.Serializable;

public class GenericInvokeResponseWsType implements Serializable {
    private ResponseType commonResponse;
    private String status;
    private String responsePayLoad;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public GenericInvokeResponseWsType() {
    }

    public GenericInvokeResponseWsType(ResponseType commonResponse, String status, String responsePayLoad) {
        this.commonResponse = commonResponse;
        this.status = status;
        this.responsePayLoad = responsePayLoad;
    }

    public ResponseType getCommonResponse() {
        return this.commonResponse;
    }

    public void setCommonResponse(ResponseType commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsePayLoad() {
        return this.responsePayLoad;
    }

    public void setResponsePayLoad(String responsePayLoad) {
        this.responsePayLoad = responsePayLoad;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GenericInvokeResponseWsType)) {
            return false;
        }
        GenericInvokeResponseWsType other = (GenericInvokeResponseWsType)obj;
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
        boolean _equals = (this.commonResponse == null && other.getCommonResponse() == null || this.commonResponse != null && this.commonResponse.equals(other.getCommonResponse())) && (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && (this.responsePayLoad == null && other.getResponsePayLoad() == null || this.responsePayLoad != null && this.responsePayLoad.equals(other.getResponsePayLoad()));
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getCommonResponse() != null) {
            _hashCode += this.getCommonResponse().hashCode();
        }
        if (this.getStatus() != null) {
            _hashCode += this.getStatus().hashCode();
        }
        if (this.getResponsePayLoad() != null) {
            _hashCode += this.getResponsePayLoad().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
