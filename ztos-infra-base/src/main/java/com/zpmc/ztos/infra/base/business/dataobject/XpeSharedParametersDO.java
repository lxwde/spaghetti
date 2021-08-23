package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.List;

public class XpeSharedParametersDO extends DatabaseEntity implements Serializable {
    private Long sharedparametersGkey;
    private Long sharedparametersPkey;
    private String sharedparametersId;
    private Long sharedparametersVersion;
    private String sharedparametersName;
    private Yard sharedparametersYard;
    private List sharedparametersParameters;

    public Serializable getPrimaryKey() {
        return this.getSharedparametersGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getSharedparametersGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof XpeSharedParametersDO)) {
            return false;
        }
        XpeSharedParametersDO that = (XpeSharedParametersDO)other;
        return ((Object)id).equals(that.getSharedparametersGkey());
    }

    public int hashCode() {
        Long id = this.getSharedparametersGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getSharedparametersGkey() {
        return this.sharedparametersGkey;
    }

    public void setSharedparametersGkey(Long sharedparametersGkey) {
        this.sharedparametersGkey = sharedparametersGkey;
    }

    public Long getSharedparametersPkey() {
        return this.sharedparametersPkey;
    }

    public void setSharedparametersPkey(Long sharedparametersPkey) {
        this.sharedparametersPkey = sharedparametersPkey;
    }

    public String getSharedparametersId() {
        return this.sharedparametersId;
    }

    public void setSharedparametersId(String sharedparametersId) {
        this.sharedparametersId = sharedparametersId;
    }

    public Long getSharedparametersVersion() {
        return this.sharedparametersVersion;
    }

    public void setSharedparametersVersion(Long sharedparametersVersion) {
        this.sharedparametersVersion = sharedparametersVersion;
    }

    public String getSharedparametersName() {
        return this.sharedparametersName;
    }

    public void setSharedparametersName(String sharedparametersName) {
        this.sharedparametersName = sharedparametersName;
    }

    public Yard getSharedparametersYard() {
        return this.sharedparametersYard;
    }

    public void setSharedparametersYard(Yard sharedparametersYard) {
        this.sharedparametersYard = sharedparametersYard;
    }

    public List getSharedparametersParameters() {
        return this.sharedparametersParameters;
    }

    public void setSharedparametersParameters(List sharedparametersParameters) {
        this.sharedparametersParameters = sharedparametersParameters;
    }

}
