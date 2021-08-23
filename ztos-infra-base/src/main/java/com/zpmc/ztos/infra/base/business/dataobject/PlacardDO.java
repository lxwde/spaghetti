package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class PlacardDO extends DatabaseEntity implements Serializable {
    private Long placardGkey;
    private String placardText;
    private String placardFurtherExplanation;
    private Double placardMinWtKg;
    private EntitySet placardScope;

    public Serializable getPrimaryKey() {
        return this.getPlacardGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getPlacardGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof PlacardDO)) {
            return false;
        }
        PlacardDO that = (PlacardDO)other;
        return ((Object)id).equals(that.getPlacardGkey());
    }

    public int hashCode() {
        Long id = this.getPlacardGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getPlacardGkey() {
        return this.placardGkey;
    }

    protected void setPlacardGkey(Long placardGkey) {
        this.placardGkey = placardGkey;
    }

    public String getPlacardText() {
        return this.placardText;
    }

    protected void setPlacardText(String placardText) {
        this.placardText = placardText;
    }

    public String getPlacardFurtherExplanation() {
        return this.placardFurtherExplanation;
    }

    protected void setPlacardFurtherExplanation(String placardFurtherExplanation) {
        this.placardFurtherExplanation = placardFurtherExplanation;
    }

    public Double getPlacardMinWtKg() {
        return this.placardMinWtKg;
    }

    protected void setPlacardMinWtKg(Double placardMinWtKg) {
        this.placardMinWtKg = placardMinWtKg;
    }

    public EntitySet getPlacardScope() {
        return this.placardScope;
    }

    protected void setPlacardScope(EntitySet placardScope) {
        this.placardScope = placardScope;
    }

}
