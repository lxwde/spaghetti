package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.CalendarTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class ArgoCalendarDO extends DatabaseEntity implements Serializable {
    private Long argocalGkey;
    private String argocalId;
    private String argocalDescription;
    private CalendarTypeEnum argocalCalendarType;
    private Boolean argocalIsDefaultCalendar;
    private Set argocalCalendarEvent;

    public Serializable getPrimaryKey() {
        return this.getArgocalGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getArgocalGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ArgoCalendarDO)) {
            return false;
        }
        ArgoCalendarDO that = (ArgoCalendarDO)other;
        return ((Object)id).equals(that.getArgocalGkey());
    }

    public int hashCode() {
        Long id = this.getArgocalGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getArgocalGkey() {
        return this.argocalGkey;
    }

    public void setArgocalGkey(Long argocalGkey) {
        this.argocalGkey = argocalGkey;
    }

    public String getArgocalId() {
        return this.argocalId;
    }

    public void setArgocalId(String argocalId) {
        this.argocalId = argocalId;
    }

    public String getArgocalDescription() {
        return this.argocalDescription;
    }

    public void setArgocalDescription(String argocalDescription) {
        this.argocalDescription = argocalDescription;
    }

    public CalendarTypeEnum getArgocalCalendarType() {
        return this.argocalCalendarType;
    }

    public void setArgocalCalendarType(CalendarTypeEnum argocalCalendarType) {
        this.argocalCalendarType = argocalCalendarType;
    }

    public Boolean getArgocalIsDefaultCalendar() {
        return this.argocalIsDefaultCalendar;
    }

    public void setArgocalIsDefaultCalendar(Boolean argocalIsDefaultCalendar) {
        this.argocalIsDefaultCalendar = argocalIsDefaultCalendar;
    }

    public Set getArgocalCalendarEvent() {
        return this.argocalCalendarEvent;
    }

    public void setArgocalCalendarEvent(Set argocalCalendarEvent) {
        this.argocalCalendarEvent = argocalCalendarEvent;
    }
}
