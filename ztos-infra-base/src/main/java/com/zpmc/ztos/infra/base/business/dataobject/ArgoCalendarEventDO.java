package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.AppCalendarIntervalEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.type.ArgoCalendarEventType;

import java.io.Serializable;
import java.util.Date;

public class ArgoCalendarEventDO extends DatabaseEntity implements Serializable {
    private Long argocalevtGkey;
    private String argocalevtName;
    private String argocalevtDescription;
    private Date argocalevtOccurrDateStart;
    private Date argocalevtRecurrDateEnd;
    private AppCalendarIntervalEnum argocalevtInterval;
    private Facility argocalevtFacility;
    private ArgoCalendarEventType argocalevtEventType;
  //  private ArgoCalendar argocalevtCalendar;

    public Serializable getPrimaryKey() {
        return this.getArgocalevtGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getArgocalevtGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ArgoCalendarEventDO)) {
            return false;
        }
        ArgoCalendarEventDO that = (ArgoCalendarEventDO)other;
        return ((Object)id).equals(that.getArgocalevtGkey());
    }

    public int hashCode() {
        Long id = this.getArgocalevtGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getArgocalevtGkey() {
        return this.argocalevtGkey;
    }

    public void setArgocalevtGkey(Long argocalevtGkey) {
        this.argocalevtGkey = argocalevtGkey;
    }

    public String getArgocalevtName() {
        return this.argocalevtName;
    }

    public void setArgocalevtName(String argocalevtName) {
        this.argocalevtName = argocalevtName;
    }

    public String getArgocalevtDescription() {
        return this.argocalevtDescription;
    }

    public void setArgocalevtDescription(String argocalevtDescription) {
        this.argocalevtDescription = argocalevtDescription;
    }

    public Date getArgocalevtOccurrDateStart() {
        return this.argocalevtOccurrDateStart;
    }

    public void setArgocalevtOccurrDateStart(Date argocalevtOccurrDateStart) {
        this.argocalevtOccurrDateStart = argocalevtOccurrDateStart;
    }

    public Date getArgocalevtRecurrDateEnd() {
        return this.argocalevtRecurrDateEnd;
    }

    public void setArgocalevtRecurrDateEnd(Date argocalevtRecurrDateEnd) {
        this.argocalevtRecurrDateEnd = argocalevtRecurrDateEnd;
    }

    public AppCalendarIntervalEnum getArgocalevtInterval() {
        return this.argocalevtInterval;
    }

    public void setArgocalevtInterval(AppCalendarIntervalEnum argocalevtInterval) {
        this.argocalevtInterval = argocalevtInterval;
    }

    public Facility getArgocalevtFacility() {
        return this.argocalevtFacility;
    }

    public void setArgocalevtFacility(Facility argocalevtFacility) {
        this.argocalevtFacility = argocalevtFacility;
    }

    public ArgoCalendarEventType getArgocalevtEventType() {
        return this.argocalevtEventType;
    }

    public void setArgocalevtEventType(ArgoCalendarEventType argocalevtEventType) {
        this.argocalevtEventType = argocalevtEventType;
    }

//    public ArgoCalendar getArgocalevtCalendar() {
//        return this.argocalevtCalendar;
//    }
//
//    public void setArgocalevtCalendar(ArgoCalendar argocalevtCalendar) {
//        this.argocalevtCalendar = argocalevtCalendar;
//    }
}
