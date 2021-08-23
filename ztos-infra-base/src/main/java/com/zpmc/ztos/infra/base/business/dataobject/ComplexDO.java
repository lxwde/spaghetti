package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

//import org.springframework.expression.spel.ast.Operator;

/**
 * 其他复合对象
 *
 * @author yejun
 */
@Data
public abstract class ComplexDO extends DatabaseEntity {

    private Long cpxGkey;
    private String cpxId;
    private String cpxName;
    private String cpxTimeZoneId;
    private LifeCycleStateEnum cpxLifeCycleState;
    private Long cpxBerthModelGkey;
    private Operator cpxOperator;
    private Set cpxFcySet;

    public Serializable getPrimaryKey() {
        return this.getCpxGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCpxGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ComplexDO)) {
            return false;
        }
        ComplexDO that = (ComplexDO)other;
        return ((Object)id).equals(that.getCpxGkey());
    }

    public int hashCode() {
        Long id = this.getCpxGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCpxGkey() {
        return this.cpxGkey;
    }

    protected void setCpxGkey(Long cpxGkey) {
        this.cpxGkey = cpxGkey;
    }

    public String getCpxId() {
        return this.cpxId;
    }

    protected void setCpxId(String cpxId) {
        this.cpxId = cpxId;
    }

    public String getCpxName() {
        return this.cpxName;
    }

    protected void setCpxName(String cpxName) {
        this.cpxName = cpxName;
    }

    public String getCpxTimeZoneId() {
        return this.cpxTimeZoneId;
    }

    protected void setCpxTimeZoneId(String cpxTimeZoneId) {
        this.cpxTimeZoneId = cpxTimeZoneId;
    }

    public LifeCycleStateEnum getCpxLifeCycleState() {
        return this.cpxLifeCycleState;
    }

    public void setCpxLifeCycleState(LifeCycleStateEnum cpxLifeCycleState) {
        this.cpxLifeCycleState = cpxLifeCycleState;
    }

    public Long getCpxBerthModelGkey() {
        return this.cpxBerthModelGkey;
    }

    protected void setCpxBerthModelGkey(Long cpxBerthModelGkey) {
        this.cpxBerthModelGkey = cpxBerthModelGkey;
    }

    public Operator getCpxOperator() {
        return this.cpxOperator;
    }

    protected void setCpxOperator(Operator cpxOperator) {
        this.cpxOperator = cpxOperator;
    }

    public Set getCpxFcySet() {
        return this.cpxFcySet;
    }

    protected void setCpxFcySet(Set cpxFcySet) {
        this.cpxFcySet = cpxFcySet;
    }
}
