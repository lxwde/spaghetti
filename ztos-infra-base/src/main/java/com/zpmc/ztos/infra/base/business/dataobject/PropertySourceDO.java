package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PropertyGroupEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class PropertySourceDO extends DatabaseEntity implements Serializable {
    private Long prpsrcGkey;
    private String prpsrcAppliedToEntity;
    private Long prpsrcAppliedToPrimaryKey;
    private PropertyGroupEnum prpsrcPropertyGroup;
    private DataSourceEnum prpsrcDataSource;
    private Date prpsrcTimestamp;
    private String prpsrcUserId;

    public Serializable getPrimaryKey() {
        return this.getPrpsrcGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPrpsrcGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PropertySourceDO)) {
            return false;
        }
        PropertySourceDO that = (PropertySourceDO)other;
        return ((Object)id).equals(that.getPrpsrcGkey());
    }

    public int hashCode() {
        Long id = this.getPrpsrcGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPrpsrcGkey() {
        return this.prpsrcGkey;
    }

    protected void setPrpsrcGkey(Long prpsrcGkey) {
        this.prpsrcGkey = prpsrcGkey;
    }

    public String getPrpsrcAppliedToEntity() {
        return this.prpsrcAppliedToEntity;
    }

    protected void setPrpsrcAppliedToEntity(String prpsrcAppliedToEntity) {
        this.prpsrcAppliedToEntity = prpsrcAppliedToEntity;
    }

    public Long getPrpsrcAppliedToPrimaryKey() {
        return this.prpsrcAppliedToPrimaryKey;
    }

    protected void setPrpsrcAppliedToPrimaryKey(Long prpsrcAppliedToPrimaryKey) {
        this.prpsrcAppliedToPrimaryKey = prpsrcAppliedToPrimaryKey;
    }

    public PropertyGroupEnum getPrpsrcPropertyGroup() {
        return this.prpsrcPropertyGroup;
    }

    protected void setPrpsrcPropertyGroup(PropertyGroupEnum prpsrcPropertyGroup) {
        this.prpsrcPropertyGroup = prpsrcPropertyGroup;
    }

    public DataSourceEnum getPrpsrcDataSource() {
        return this.prpsrcDataSource;
    }

    protected void setPrpsrcDataSource(DataSourceEnum prpsrcDataSource) {
        this.prpsrcDataSource = prpsrcDataSource;
    }

    public Date getPrpsrcTimestamp() {
        return this.prpsrcTimestamp;
    }

    protected void setPrpsrcTimestamp(Date prpsrcTimestamp) {
        this.prpsrcTimestamp = prpsrcTimestamp;
    }

    public String getPrpsrcUserId() {
        return this.prpsrcUserId;
    }

    protected void setPrpsrcUserId(String prpsrcUserId) {
        this.prpsrcUserId = prpsrcUserId;
    }

}
