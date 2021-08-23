package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体集合对象
 *
 * @author yejun
 */
@Data
public class EntitySetDO extends DatabaseEntity
implements Serializable {
    private Long ensetGkey;
    private String ensetName;
    private Operator ensetOperator;

    public Serializable getPrimaryKey() {
        return this.getEnsetGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEnsetGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EntitySetDO)) {
            return false;
        }
        EntitySetDO that = (EntitySetDO)other;
        return ((Object)id).equals(that.getEnsetGkey());
    }

    public int hashCode() {
        Long id = this.getEnsetGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEnsetGkey() {
        return this.ensetGkey;
    }

    protected void setEnsetGkey(Long ensetGkey) {
        this.ensetGkey = ensetGkey;
    }

    public String getEnsetName() {
        return this.ensetName;
    }

    protected void setEnsetName(String ensetName) {
        this.ensetName = ensetName;
    }

    public Operator getEnsetOperator() {
        return this.ensetOperator;
    }

    protected void setEnsetOperator(Operator ensetOperator) {
        this.ensetOperator = ensetOperator;
    }


}
