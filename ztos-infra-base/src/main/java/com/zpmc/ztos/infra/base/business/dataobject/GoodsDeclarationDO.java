package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.GoodsBase;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public abstract class GoodsDeclarationDO extends DatabaseEntity implements Serializable {
    private Long gdsdeclGkey;
    private Long gdsdeclSeq;
    private String gdsdeclDescription;
    private Long gdsdeclQuantity;
    private String gdsdeclQuantityType;
    private GoodsBase gdsdeclGoods;
    private Set gdsdeclDocuments;

    public Serializable getPrimaryKey() {
        return this.getGdsdeclGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getGdsdeclGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof GoodsDeclarationDO)) {
            return false;
        }
        GoodsDeclarationDO that = (GoodsDeclarationDO)other;
        return ((Object)id).equals(that.getGdsdeclGkey());
    }

    public int hashCode() {
        Long id = this.getGdsdeclGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getGdsdeclGkey() {
        return this.gdsdeclGkey;
    }

    protected void setGdsdeclGkey(Long gdsdeclGkey) {
        this.gdsdeclGkey = gdsdeclGkey;
    }

    public Long getGdsdeclSeq() {
        return this.gdsdeclSeq;
    }

    protected void setGdsdeclSeq(Long gdsdeclSeq) {
        this.gdsdeclSeq = gdsdeclSeq;
    }

    public String getGdsdeclDescription() {
        return this.gdsdeclDescription;
    }

    protected void setGdsdeclDescription(String gdsdeclDescription) {
        this.gdsdeclDescription = gdsdeclDescription;
    }

    public Long getGdsdeclQuantity() {
        return this.gdsdeclQuantity;
    }

    protected void setGdsdeclQuantity(Long gdsdeclQuantity) {
        this.gdsdeclQuantity = gdsdeclQuantity;
    }

    public String getGdsdeclQuantityType() {
        return this.gdsdeclQuantityType;
    }

    protected void setGdsdeclQuantityType(String gdsdeclQuantityType) {
        this.gdsdeclQuantityType = gdsdeclQuantityType;
    }

    public GoodsBase getGdsdeclGoods() {
        return this.gdsdeclGoods;
    }

    protected void setGdsdeclGoods(GoodsBase gdsdeclGoods) {
        this.gdsdeclGoods = gdsdeclGoods;
    }

    public Set getGdsdeclDocuments() {
        return this.gdsdeclDocuments;
    }

    protected void setGdsdeclDocuments(Set gdsdeclDocuments) {
        this.gdsdeclDocuments = gdsdeclDocuments;
    }

}
