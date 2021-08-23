package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


/**
 *
 * bin名表
 * @author yejun
 */
@Data
public abstract class BinNameTableDO extends DatabaseEntity implements Serializable {

    private Long bntGkey;
    private Long bntTableIndex;
    private String bntTableName;
    private LifeCycleStateEnum bntLifeCycleState;
    private AbstractBin bntOwningBin;
    private Set bntBinNameSet;

    public Serializable getPrimaryKey() {
        return this.getBntGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBntGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BinNameTableDO)) {
            return false;
        }
        BinNameTableDO that = (BinNameTableDO)other;
        return ((Object)id).equals(that.getBntGkey());
    }

    public int hashCode() {
        Long id = this.getBntGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBntGkey() {
        return this.bntGkey;
    }

    protected void setBntGkey(Long bntGkey) {
        this.bntGkey = bntGkey;
    }

    public Long getBntTableIndex() {
        return this.bntTableIndex;
    }

    protected void setBntTableIndex(Long bntTableIndex) {
        this.bntTableIndex = bntTableIndex;
    }

    public String getBntTableName() {
        return this.bntTableName;
    }

    protected void setBntTableName(String bntTableName) {
        this.bntTableName = bntTableName;
    }

    public LifeCycleStateEnum getBntLifeCycleState() {
        return this.bntLifeCycleState;
    }

    public void setBntLifeCycleState(LifeCycleStateEnum bntLifeCycleState) {
        this.bntLifeCycleState = bntLifeCycleState;
    }

    public AbstractBin getBntOwningBin() {
        return this.bntOwningBin;
    }

    protected void setBntOwningBin(AbstractBin bntOwningBin) {
        this.bntOwningBin = bntOwningBin;
    }

    public Set getBntBinNameSet() {
        return this.bntBinNameSet;
    }

    protected void setBntBinNameSet(Set bntBinNameSet) {
        this.bntBinNameSet = bntBinNameSet;
    }


}
