package com.zpmc.ztos.infra.base.common.scopes;

import java.io.Serializable;

public class ScopeCoordinateIdsWsType implements Serializable {
    private String operatorId;
    private String complexId;
    private String facilityId;
    private String yardId;
    private String externalUserId;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public ScopeCoordinateIdsWsType() {
    }

    public ScopeCoordinateIdsWsType(String operatorId, String complexId, String facilityId, String yardId, String externalUserId) {
        this.operatorId = operatorId;
        this.complexId = complexId;
        this.facilityId = facilityId;
        this.yardId = yardId;
        this.externalUserId = externalUserId;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getComplexId() {
        return this.complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getFacilityId() {
        return this.facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getYardId() {
        return this.yardId;
    }

    public void setYardId(String yardId) {
        this.yardId = yardId;
    }

    public String getExternalUserId() {
        return this.externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ScopeCoordinateIdsWsType)) {
            return false;
        }
        ScopeCoordinateIdsWsType other = (ScopeCoordinateIdsWsType)obj;
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
        boolean _equals = (this.operatorId == null && other.getOperatorId() == null || this.operatorId != null && this.operatorId.equals(other.getOperatorId())) && (this.complexId == null && other.getComplexId() == null || this.complexId != null && this.complexId.equals(other.getComplexId())) && (this.facilityId == null && other.getFacilityId() == null || this.facilityId != null && this.facilityId.equals(other.getFacilityId())) && (this.yardId == null && other.getYardId() == null || this.yardId != null && this.yardId.equals(other.getYardId())) && (this.externalUserId == null && other.getExternalUserId() == null || this.externalUserId != null && this.externalUserId.equals(other.getExternalUserId()));
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getOperatorId() != null) {
            _hashCode += this.getOperatorId().hashCode();
        }
        if (this.getComplexId() != null) {
            _hashCode += this.getComplexId().hashCode();
        }
        if (this.getFacilityId() != null) {
            _hashCode += this.getFacilityId().hashCode();
        }
        if (this.getYardId() != null) {
            _hashCode += this.getYardId().hashCode();
        }
        if (this.getExternalUserId() != null) {
            _hashCode += this.getExternalUserId().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
