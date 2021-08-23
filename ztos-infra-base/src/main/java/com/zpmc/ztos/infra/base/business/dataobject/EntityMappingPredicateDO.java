package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.predicate.EntityMappingPredicate;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.List;

public class EntityMappingPredicateDO extends DatabaseEntity implements Serializable {
    private Long emappGkey;
    private PredicateVerbEnum emappVerb;
    private String emappMetafield;
    private String emappValue;
    private String emappUiValue;
    private Boolean emappNegated;
    private EntityMappingPredicate emappNextPredicate;
    private EntityMappingPredicate emappSubPredicate;
    private List emappValues;

    public Serializable getPrimaryKey() {
        return this.getEmappGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEmappGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EntityMappingPredicateDO)) {
            return false;
        }
        EntityMappingPredicateDO that = (EntityMappingPredicateDO)other;
        return ((Object)id).equals(that.getEmappGkey());
    }

    public int hashCode() {
        Long id = this.getEmappGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEmappGkey() {
        return this.emappGkey;
    }

    protected void setEmappGkey(Long emappGkey) {
        this.emappGkey = emappGkey;
    }

    public PredicateVerbEnum getEmappVerb() {
        return this.emappVerb;
    }

    protected void setEmappVerb(PredicateVerbEnum emappVerb) {
        this.emappVerb = emappVerb;
    }

    public String getEmappMetafield() {
        return this.emappMetafield;
    }

    protected void setEmappMetafield(String emappMetafield) {
        this.emappMetafield = emappMetafield;
    }

    public String getEmappValue() {
        return this.emappValue;
    }

    protected void setEmappValue(String emappValue) {
        this.emappValue = emappValue;
    }

    public String getEmappUiValue() {
        return this.emappUiValue;
    }

    protected void setEmappUiValue(String emappUiValue) {
        this.emappUiValue = emappUiValue;
    }

    public Boolean getEmappNegated() {
        return this.emappNegated;
    }

    protected void setEmappNegated(Boolean emappNegated) {
        this.emappNegated = emappNegated;
    }

    public EntityMappingPredicate getEmappNextPredicate() {
        return this.emappNextPredicate;
    }

    protected void setEmappNextPredicate(EntityMappingPredicate emappNextPredicate) {
        this.emappNextPredicate = emappNextPredicate;
    }

    public EntityMappingPredicate getEmappSubPredicate() {
        return this.emappSubPredicate;
    }

    protected void setEmappSubPredicate(EntityMappingPredicate emappSubPredicate) {
        this.emappSubPredicate = emappSubPredicate;
    }

    public List getEmappValues() {
        return this.emappValues;
    }

    protected void setEmappValues(List emappValues) {
        this.emappValues = emappValues;
    }

}
