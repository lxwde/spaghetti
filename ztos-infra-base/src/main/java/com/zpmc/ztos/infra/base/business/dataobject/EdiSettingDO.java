package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiSession;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EdiSettingDO extends DatabaseEntity implements Serializable {
    private Long edistngGkey;
    private String edistngConfigId;
    private String edistngValue;
    private Date edistngCreated;
    private String edistngCreator;
    private Date edistngChanged;
    private String edistngChanger;
    private EdiSession edistngSession;

    public Serializable getPrimaryKey() {
        return this.getEdistngGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdistngGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiSettingDO)) {
            return false;
        }
        EdiSettingDO that = (EdiSettingDO)other;
        return ((Object)id).equals(that.getEdistngGkey());
    }

    public int hashCode() {
        Long id = this.getEdistngGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdistngGkey() {
        return this.edistngGkey;
    }

    protected void setEdistngGkey(Long edistngGkey) {
        this.edistngGkey = edistngGkey;
    }

    public String getEdistngConfigId() {
        return this.edistngConfigId;
    }

    protected void setEdistngConfigId(String edistngConfigId) {
        this.edistngConfigId = edistngConfigId;
    }

    public String getEdistngValue() {
        return this.edistngValue;
    }

    protected void setEdistngValue(String edistngValue) {
        this.edistngValue = edistngValue;
    }

    public Date getEdistngCreated() {
        return this.edistngCreated;
    }

    protected void setEdistngCreated(Date edistngCreated) {
        this.edistngCreated = edistngCreated;
    }

    public String getEdistngCreator() {
        return this.edistngCreator;
    }

    protected void setEdistngCreator(String edistngCreator) {
        this.edistngCreator = edistngCreator;
    }

    public Date getEdistngChanged() {
        return this.edistngChanged;
    }

    protected void setEdistngChanged(Date edistngChanged) {
        this.edistngChanged = edistngChanged;
    }

    public String getEdistngChanger() {
        return this.edistngChanger;
    }

    protected void setEdistngChanger(String edistngChanger) {
        this.edistngChanger = edistngChanger;
    }

    public EdiSession getEdistngSession() {
        return this.edistngSession;
    }

    protected void setEdistngSession(EdiSession edistngSession) {
        this.edistngSession = edistngSession;
    }
}
