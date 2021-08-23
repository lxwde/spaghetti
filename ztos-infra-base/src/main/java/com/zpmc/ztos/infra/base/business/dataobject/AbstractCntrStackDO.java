package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.AbstractStack;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 集装箱堆垛区域
 * @author yejun
 */
@Data
public abstract class AbstractCntrStackDO extends AbstractStack implements Serializable {

    private Boolean acnskAllows20s;
    private Boolean acnskAllows24s;
    private Boolean acnskAllows30s;
    private Boolean acnskAllows35s;
    private Boolean acnskAllows40s;
    private Boolean acnskAllows45s;
    private Boolean acnskAllows48s;
    private Boolean acnskAllows53s;

    public Boolean getAcnskAllows20s() {
        return this.acnskAllows20s;
    }

    protected void setAcnskAllows20s(Boolean acnskAllows20s) {
        this.acnskAllows20s = acnskAllows20s;
    }

    public Boolean getAcnskAllows24s() {
        return this.acnskAllows24s;
    }

    protected void setAcnskAllows24s(Boolean acnskAllows24s) {
        this.acnskAllows24s = acnskAllows24s;
    }

    public Boolean getAcnskAllows30s() {
        return this.acnskAllows30s;
    }

    protected void setAcnskAllows30s(Boolean acnskAllows30s) {
        this.acnskAllows30s = acnskAllows30s;
    }

    public Boolean getAcnskAllows35s() {
        return this.acnskAllows35s;
    }

    protected void setAcnskAllows35s(Boolean acnskAllows35s) {
        this.acnskAllows35s = acnskAllows35s;
    }

    public Boolean getAcnskAllows40s() {
        return this.acnskAllows40s;
    }

    protected void setAcnskAllows40s(Boolean acnskAllows40s) {
        this.acnskAllows40s = acnskAllows40s;
    }

    public Boolean getAcnskAllows45s() {
        return this.acnskAllows45s;
    }

    protected void setAcnskAllows45s(Boolean acnskAllows45s) {
        this.acnskAllows45s = acnskAllows45s;
    }

    public Boolean getAcnskAllows48s() {
        return this.acnskAllows48s;
    }

    protected void setAcnskAllows48s(Boolean acnskAllows48s) {
        this.acnskAllows48s = acnskAllows48s;
    }

    public Boolean getAcnskAllows53s() {
        return this.acnskAllows53s;
    }

    protected void setAcnskAllows53s(Boolean acnskAllows53s) {
        this.acnskAllows53s = acnskAllows53s;
    }

}
