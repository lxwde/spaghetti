package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;

public class MoveKindAllowedDO extends DatabaseEntity implements Serializable {
    private Long movekindallowedGkey;
    private Long movekindallowedPkey;
    private String movekindallowedBinName;
    private String movekindallowedMoveKinds;
    private Yard movekindallowedYard;
    private AbstractBin movekindallowedBin;

    public Serializable getPrimaryKey() {
        return this.getMovekindallowedGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getMovekindallowedGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof MoveKindAllowedDO)) {
            return false;
        }
        MoveKindAllowedDO that = (MoveKindAllowedDO)other;
        return ((Object)id).equals(that.getMovekindallowedGkey());
    }

    public int hashCode() {
        Long id = this.getMovekindallowedGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getMovekindallowedGkey() {
        return this.movekindallowedGkey;
    }

    public void setMovekindallowedGkey(Long movekindallowedGkey) {
        this.movekindallowedGkey = movekindallowedGkey;
    }

    public Long getMovekindallowedPkey() {
        return this.movekindallowedPkey;
    }

    public void setMovekindallowedPkey(Long movekindallowedPkey) {
        this.movekindallowedPkey = movekindallowedPkey;
    }

    public String getMovekindallowedBinName() {
        return this.movekindallowedBinName;
    }

    public void setMovekindallowedBinName(String movekindallowedBinName) {
        this.movekindallowedBinName = movekindallowedBinName;
    }

    public String getMovekindallowedMoveKinds() {
        return this.movekindallowedMoveKinds;
    }

    public void setMovekindallowedMoveKinds(String movekindallowedMoveKinds) {
        this.movekindallowedMoveKinds = movekindallowedMoveKinds;
    }

    public Yard getMovekindallowedYard() {
        return this.movekindallowedYard;
    }

    public void setMovekindallowedYard(Yard movekindallowedYard) {
        this.movekindallowedYard = movekindallowedYard;
    }

    public AbstractBin getMovekindallowedBin() {
        return this.movekindallowedBin;
    }

    public void setMovekindallowedBin(AbstractBin movekindallowedBin) {
        this.movekindallowedBin = movekindallowedBin;
    }

}
