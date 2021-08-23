package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiMailbox;
import com.zpmc.ztos.infra.base.business.edi.EdiSession;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EdiSessionMailboxDO extends DatabaseEntity implements Serializable {
    private Long edisessmlbxGkey;
    private Boolean edisessmlbxIsPrimary;
    private Date edisessmlbxCreated;
    private String edisessmlbxCreator;
    private Date edisessmlbxChanged;
    private String edisessmlbxChanger;
    private LifeCycleStateEnum edisessmlbxLifeCycleState;
    private EdiSession edisessmlbxSession;
    private EdiMailbox edisessmlbxMailbox;

    public Serializable getPrimaryKey() {
        return this.getEdisessmlbxGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdisessmlbxGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiSessionMailboxDO)) {
            return false;
        }
        EdiSessionMailboxDO that = (EdiSessionMailboxDO)other;
        return ((Object)id).equals(that.getEdisessmlbxGkey());
    }

    public int hashCode() {
        Long id = this.getEdisessmlbxGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdisessmlbxGkey() {
        return this.edisessmlbxGkey;
    }

    protected void setEdisessmlbxGkey(Long edisessmlbxGkey) {
        this.edisessmlbxGkey = edisessmlbxGkey;
    }

    public Boolean getEdisessmlbxIsPrimary() {
        return this.edisessmlbxIsPrimary;
    }

    public void setEdisessmlbxIsPrimary(Boolean edisessmlbxIsPrimary) {
        this.edisessmlbxIsPrimary = edisessmlbxIsPrimary;
    }

    public Date getEdisessmlbxCreated() {
        return this.edisessmlbxCreated;
    }

    protected void setEdisessmlbxCreated(Date edisessmlbxCreated) {
        this.edisessmlbxCreated = edisessmlbxCreated;
    }

    public String getEdisessmlbxCreator() {
        return this.edisessmlbxCreator;
    }

    protected void setEdisessmlbxCreator(String edisessmlbxCreator) {
        this.edisessmlbxCreator = edisessmlbxCreator;
    }

    public Date getEdisessmlbxChanged() {
        return this.edisessmlbxChanged;
    }

    protected void setEdisessmlbxChanged(Date edisessmlbxChanged) {
        this.edisessmlbxChanged = edisessmlbxChanged;
    }

    public String getEdisessmlbxChanger() {
        return this.edisessmlbxChanger;
    }

    protected void setEdisessmlbxChanger(String edisessmlbxChanger) {
        this.edisessmlbxChanger = edisessmlbxChanger;
    }

    public LifeCycleStateEnum getEdisessmlbxLifeCycleState() {
        return this.edisessmlbxLifeCycleState;
    }

    public void setEdisessmlbxLifeCycleState(LifeCycleStateEnum edisessmlbxLifeCycleState) {
        this.edisessmlbxLifeCycleState = edisessmlbxLifeCycleState;
    }

    public EdiSession getEdisessmlbxSession() {
        return this.edisessmlbxSession;
    }

    protected void setEdisessmlbxSession(EdiSession edisessmlbxSession) {
        this.edisessmlbxSession = edisessmlbxSession;
    }

    public EdiMailbox getEdisessmlbxMailbox() {
        return this.edisessmlbxMailbox;
    }

    protected void setEdisessmlbxMailbox(EdiMailbox edisessmlbxMailbox) {
        this.edisessmlbxMailbox = edisessmlbxMailbox;
    }
}
