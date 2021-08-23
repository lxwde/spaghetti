package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 国家
 *
 * @author yejun
 */
@Data
public abstract class RefCountryDO extends DatabaseEntity
implements Serializable {

    private String cntryCode;
    private String cntryAlpha3Code;
    private String cntryNum3Code;
    private String cntryName;
    private String cntryOfficialName;
    private LifeCycleStateEnum cntryLifeCycleState;
    private Set stateList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getCntryCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        String id = this.getCntryCode();
        if (id == null) {
            return false;
        }
        if (!(other instanceof RefCountryDO)) {
            return false;
        }
        RefCountryDO that = (RefCountryDO)other;
        return id.equals(that.getCntryCode());
    }

    public int hashCode() {
        String id = this.getCntryCode();
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    public String getCntryCode() {
        return this.cntryCode;
    }

    protected void setCntryCode(String cntryCode) {
        this.cntryCode = cntryCode;
    }

    public String getCntryAlpha3Code() {
        return this.cntryAlpha3Code;
    }

    protected void setCntryAlpha3Code(String cntryAlpha3Code) {
        this.cntryAlpha3Code = cntryAlpha3Code;
    }

    public String getCntryNum3Code() {
        return this.cntryNum3Code;
    }

    protected void setCntryNum3Code(String cntryNum3Code) {
        this.cntryNum3Code = cntryNum3Code;
    }

    public String getCntryName() {
        return this.cntryName;
    }

    protected void setCntryName(String cntryName) {
        this.cntryName = cntryName;
    }

    public String getCntryOfficialName() {
        return this.cntryOfficialName;
    }

    protected void setCntryOfficialName(String cntryOfficialName) {
        this.cntryOfficialName = cntryOfficialName;
    }

    public LifeCycleStateEnum getCntryLifeCycleState() {
        return this.cntryLifeCycleState;
    }

    public void setCntryLifeCycleState(LifeCycleStateEnum cntryLifeCycleState) {
        this.cntryLifeCycleState = cntryLifeCycleState;
    }

    public Set getStateList() {
        return this.stateList;
    }

    protected void setStateList(Set stateList) {
        this.stateList = stateList;
    }

}
