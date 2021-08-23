package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.systems.DbMetafield;

import java.io.Serializable;

public class DbMetafieldChoiceDO extends DatabaseEntity implements Serializable {
    private Long mfdchGkey;
    private String mfdchValue;
    private String mfdchDescription;
    private DbMetafield mfdchMetafield;

    @Override
    public Serializable getPrimaryKey() {
        return this.getMfdchGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getMfdchGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof DbMetafieldChoiceDO)) {
            return false;
        }
        DbMetafieldChoiceDO that = (DbMetafieldChoiceDO)other;
        return ((Object)id).equals(that.getMfdchGkey());
    }

    public int hashCode() {
        Long id = this.getMfdchGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getMfdchGkey() {
        return this.mfdchGkey;
    }

    public void setMfdchGkey(Long mfdchGkey) {
        this.mfdchGkey = mfdchGkey;
    }

    public String getMfdchValue() {
        return this.mfdchValue;
    }

    public void setMfdchValue(String mfdchValue) {
        this.mfdchValue = mfdchValue;
    }

    public String getMfdchDescription() {
        return this.mfdchDescription;
    }

    public void setMfdchDescription(String mfdchDescription) {
        this.mfdchDescription = mfdchDescription;
    }

    public DbMetafield getMfdchMetafield() {
        return this.mfdchMetafield;
    }

    public void setMfdchMetafield(DbMetafield mfdchMetafield) {
        this.mfdchMetafield = mfdchMetafield;
    }

}
