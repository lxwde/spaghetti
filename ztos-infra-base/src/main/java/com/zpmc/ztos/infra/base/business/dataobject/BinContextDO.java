package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data

/**
 *
 * bin相关内容描述
 * @author yejun
 */
public abstract class BinContextDO extends DatabaseEntity {

    private Long bcxGkey;
    private String bcxId;
    private String bcxDescription;
    private Boolean bcxSystemDefined;

    public Serializable getPrimaryKey() {
        return this.getBcxGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBcxGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BinContextDO)) {
            return false;
        }
        BinContextDO that = (BinContextDO)other;
        return ((Object)id).equals(that.getBcxGkey());
    }

    public int hashCode() {
        Long id = this.getBcxGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBcxGkey() {
        return this.bcxGkey;
    }

    protected void setBcxGkey(Long bcxGkey) {
        this.bcxGkey = bcxGkey;
    }

    public String getBcxId() {
        return this.bcxId;
    }

    protected void setBcxId(String bcxId) {
        this.bcxId = bcxId;
    }

    public String getBcxDescription() {
        return this.bcxDescription;
    }

    protected void setBcxDescription(String bcxDescription) {
        this.bcxDescription = bcxDescription;
    }

    public Boolean getBcxSystemDefined() {
        return this.bcxSystemDefined;
    }

    protected void setBcxSystemDefined(Boolean bcxSystemDefined) {
        this.bcxSystemDefined = bcxSystemDefined;
    }


}
