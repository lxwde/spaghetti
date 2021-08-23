package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.BinNameTable;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public abstract class BinNameDO extends DatabaseEntity implements Serializable {

    private Long bnmGkey;
    private Long bnmLogicalPosition;
    private String bnmUserName;
    private LifeCycleStateEnum bnmLifeCycleState;
    private BinNameTable bnmNameTable;

    public Serializable getPrimaryKey() {
        return this.getBnmGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBnmGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BinNameDO)) {
            return false;
        }
        BinNameDO that = (BinNameDO)other;
        return ((Object)id).equals(that.getBnmGkey());
    }

    public int hashCode() {
        Long id = this.getBnmGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBnmGkey() {
        return this.bnmGkey;
    }

    protected void setBnmGkey(Long bnmGkey) {
        this.bnmGkey = bnmGkey;
    }

    public Long getBnmLogicalPosition() {
        return this.bnmLogicalPosition;
    }

    protected void setBnmLogicalPosition(Long bnmLogicalPosition) {
        this.bnmLogicalPosition = bnmLogicalPosition;
    }

    public String getBnmUserName() {
        return this.bnmUserName;
    }

    protected void setBnmUserName(String bnmUserName) {
        this.bnmUserName = bnmUserName;
    }

    public LifeCycleStateEnum getBnmLifeCycleState() {
        return this.bnmLifeCycleState;
    }

    public void setBnmLifeCycleState(LifeCycleStateEnum bnmLifeCycleState) {
        this.bnmLifeCycleState = bnmLifeCycleState;
    }

    public BinNameTable getBnmNameTable() {
        return this.bnmNameTable;
    }

    protected void setBnmNameTable(BinNameTable bnmNameTable) {
        this.bnmNameTable = bnmNameTable;
    }
}
