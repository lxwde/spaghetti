package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.predicate.EntityMappingPredicate;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class EntityMappingValueDO extends DatabaseEntity implements Serializable {

    private Long emapvGkey;
    private String emapvMetafield;
    private String emapvValue;
    private String emapvUiValue;
    private EntityMappingPredicate emapvEmapp;

    public Serializable getPrimaryKey() {
        return this.getEmapvGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEmapvGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EntityMappingValueDO)) {
            return false;
        }
        EntityMappingValueDO that = (EntityMappingValueDO)other;
        return ((Object)id).equals(that.getEmapvGkey());
    }

    public int hashCode() {
        Long id = this.getEmapvGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEmapvGkey() {
        return this.emapvGkey;
    }

    protected void setEmapvGkey(Long emapvGkey) {
        this.emapvGkey = emapvGkey;
    }

    public String getEmapvMetafield() {
        return this.emapvMetafield;
    }

    public void setEmapvMetafield(String emapvMetafield) {
        this.emapvMetafield = emapvMetafield;
    }

    public String getEmapvValue() {
        return this.emapvValue;
    }

    public void setEmapvValue(String emapvValue) {
        this.emapvValue = emapvValue;
    }

    public String getEmapvUiValue() {
        return this.emapvUiValue;
    }

    public void setEmapvUiValue(String emapvUiValue) {
        this.emapvUiValue = emapvUiValue;
    }

    public EntityMappingPredicate getEmapvEmapp() {
        return this.emapvEmapp;
    }

    public void setEmapvEmapp(EntityMappingPredicate emapvEmapp) {
        this.emapvEmapp = emapvEmapp;
    }

}
