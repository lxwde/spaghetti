package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.argo.PropertyGroupEnum;

public class PropertySourceBean {
    private DatabaseEntity _entity;
    private PropertyGroupEnum _propertyGroup;

    private PropertySourceBean(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup) {
        this._entity = inEntity;
        this._propertyGroup = inPropertyGroup;
    }

    public DatabaseEntity getEntity() {
        return this._entity;
    }

    public PropertyGroupEnum getPropertyGroup() {
        return this._propertyGroup;
    }

    public String getEntityName() {
        return this._entity.getEntityName();
    }

    public Long getPrimaryKey() {
        return (Long)this._entity.getPrimaryKey();
    }

    public boolean equals(Object inThat) {
        if (this == inThat) {
            return true;
        }
        if (inThat == null || this.getClass() != inThat.getClass()) {
            return false;
        }
        PropertySourceBean that = (PropertySourceBean)inThat;
        return this._entity.equals((Object)that.getEntity()) && this._propertyGroup.equals((Object)that.getPropertyGroup());
    }

    public int hashCode() {
        return this._propertyGroup.hashCode();
    }

    public static PropertySourceBean createPropertySourceBean(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup) {
        return new PropertySourceBean(inEntity, inPropertyGroup);
    }

    public String toString() {
        return this.getEntityName() + ":" + this.getPrimaryKey() + ":" + this._propertyGroup.getKey();
    }
}
