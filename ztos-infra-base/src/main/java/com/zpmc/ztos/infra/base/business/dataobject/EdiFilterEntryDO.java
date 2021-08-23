package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.edi.EdiFilterFieldIdEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class EdiFilterEntryDO extends DatabaseEntity implements Serializable {
    private Long edifltrenGkey;
    private EdiFilterFieldIdEnum edifltrenFieldId;
    private String edifltrenFromValue;
    private String edifltrenToValue;
    private Date edifltrenCreated;
    private String edifltrenCreator;
    private Date edifltrenChanged;
    private String edifltrenChanger;
    private EntitySet edifltrenScope;
 //   private EdiFilter edifltrenFilter;

    public Serializable getPrimaryKey() {
        return this.getEdifltrenGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdifltrenGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiFilterEntryDO)) {
            return false;
        }
        EdiFilterEntryDO that = (EdiFilterEntryDO)other;
        return ((Object)id).equals(that.getEdifltrenGkey());
    }

    public int hashCode() {
        Long id = this.getEdifltrenGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdifltrenGkey() {
        return this.edifltrenGkey;
    }

    protected void setEdifltrenGkey(Long edifltrenGkey) {
        this.edifltrenGkey = edifltrenGkey;
    }

    public EdiFilterFieldIdEnum getEdifltrenFieldId() {
        return this.edifltrenFieldId;
    }

    protected void setEdifltrenFieldId(EdiFilterFieldIdEnum edifltrenFieldId) {
        this.edifltrenFieldId = edifltrenFieldId;
    }

    public String getEdifltrenFromValue() {
        return this.edifltrenFromValue;
    }

    protected void setEdifltrenFromValue(String edifltrenFromValue) {
        this.edifltrenFromValue = edifltrenFromValue;
    }

    public String getEdifltrenToValue() {
        return this.edifltrenToValue;
    }

    protected void setEdifltrenToValue(String edifltrenToValue) {
        this.edifltrenToValue = edifltrenToValue;
    }

    public Date getEdifltrenCreated() {
        return this.edifltrenCreated;
    }

    protected void setEdifltrenCreated(Date edifltrenCreated) {
        this.edifltrenCreated = edifltrenCreated;
    }

    public String getEdifltrenCreator() {
        return this.edifltrenCreator;
    }

    protected void setEdifltrenCreator(String edifltrenCreator) {
        this.edifltrenCreator = edifltrenCreator;
    }

    public Date getEdifltrenChanged() {
        return this.edifltrenChanged;
    }

    protected void setEdifltrenChanged(Date edifltrenChanged) {
        this.edifltrenChanged = edifltrenChanged;
    }

    public String getEdifltrenChanger() {
        return this.edifltrenChanger;
    }

    protected void setEdifltrenChanger(String edifltrenChanger) {
        this.edifltrenChanger = edifltrenChanger;
    }

    public EntitySet getEdifltrenScope() {
        return this.edifltrenScope;
    }

    protected void setEdifltrenScope(EntitySet edifltrenScope) {
        this.edifltrenScope = edifltrenScope;
    }

//    public EdiFilter getEdifltrenFilter() {
//        return this.edifltrenFilter;
//    }
//
//    protected void setEdifltrenFilter(EdiFilter edifltrenFilter) {
//        this.edifltrenFilter = edifltrenFilter;
//    }
}
