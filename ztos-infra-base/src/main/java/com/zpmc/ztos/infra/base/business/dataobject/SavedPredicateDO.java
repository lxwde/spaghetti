package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.core.PredicateParmEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.List;

public class SavedPredicateDO extends DatabaseEntity implements Serializable {

    private Long prdctGkey;
    private PredicateVerbEnum prdctVerb;
    private String prdctMetafield;
    private String prdctValue;
    private String prdctUiValue;
    private Long prdctOrder;
    private Boolean prdctNegated;
    private PredicateParmEnum prdctParameterType;
    private String prdctParameterLabel;
    private String prdctParameterInternalName;
    private SavedPredicate prdctParentPredicate;
    private List prdctChildPrdctList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getPrdctGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPrdctGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof SavedPredicateDO)) {
            return false;
        }
        SavedPredicateDO that = (SavedPredicateDO)other;
        return ((Object)id).equals(that.getPrdctGkey());
    }

    public int hashCode() {
        Long id = this.getPrdctGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPrdctGkey() {
        return this.prdctGkey;
    }

    public void setPrdctGkey(Long prdctGkey) {
        this.prdctGkey = prdctGkey;
    }

    public PredicateVerbEnum getPrdctVerb() {
        return this.prdctVerb;
    }

    public void setPrdctVerb(PredicateVerbEnum prdctVerb) {
        this.prdctVerb = prdctVerb;
    }

    public String getPrdctMetafield() {
        return this.prdctMetafield;
    }

    public void setPrdctMetafield(String prdctMetafield) {
        this.prdctMetafield = prdctMetafield;
    }

    public String getPrdctValue() {
        return this.prdctValue;
    }

    public void setPrdctValue(String prdctValue) {
        this.prdctValue = prdctValue;
    }

    public String getPrdctUiValue() {
        return this.prdctUiValue;
    }

    public void setPrdctUiValue(String prdctUiValue) {
        this.prdctUiValue = prdctUiValue;
    }

    public Long getPrdctOrder() {
        return this.prdctOrder;
    }

    public void setPrdctOrder(Long prdctOrder) {
        this.prdctOrder = prdctOrder;
    }

    public Boolean getPrdctNegated() {
        return this.prdctNegated;
    }

    public void setPrdctNegated(Boolean prdctNegated) {
        this.prdctNegated = prdctNegated;
    }

    public PredicateParmEnum getPrdctParameterType() {
        return this.prdctParameterType;
    }

    public void setPrdctParameterType(PredicateParmEnum prdctParameterType) {
        this.prdctParameterType = prdctParameterType;
    }

    public String getPrdctParameterLabel() {
        return this.prdctParameterLabel;
    }

    public void setPrdctParameterLabel(String prdctParameterLabel) {
        this.prdctParameterLabel = prdctParameterLabel;
    }

    public String getPrdctParameterInternalName() {
        return this.prdctParameterInternalName;
    }

    public void setPrdctParameterInternalName(String prdctParameterInternalName) {
        this.prdctParameterInternalName = prdctParameterInternalName;
    }

    public SavedPredicate getPrdctParentPredicate() {
        return this.prdctParentPredicate;
    }

    public void setPrdctParentPredicate(SavedPredicate prdctParentPredicate) {
        this.prdctParentPredicate = prdctParentPredicate;
    }

    public List getPrdctChildPrdctList() {
        return this.prdctChildPrdctList;
    }

    public void setPrdctChildPrdctList(List prdctChildPrdctList) {
        this.prdctChildPrdctList = prdctChildPrdctList;
    }
}
