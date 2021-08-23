package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class ArgoCalendarEventTypeDO extends DatabaseEntity implements Serializable {
    private Long argocalevttypeGkey;
    private String argocalevttypeName;
    private Boolean argocalevttypeIsPartialDayEventType;
    private String argocalevttypeDescription;

    public Serializable getPrimaryKey() {
        return this.getArgocalevttypeGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getArgocalevttypeGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ArgoCalendarEventTypeDO)) {
            return false;
        }
        ArgoCalendarEventTypeDO that = (ArgoCalendarEventTypeDO)other;
        return ((Object)id).equals(that.getArgocalevttypeGkey());
    }

    public int hashCode() {
        Long id = this.getArgocalevttypeGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getArgocalevttypeGkey() {
        return this.argocalevttypeGkey;
    }

    public void setArgocalevttypeGkey(Long argocalevttypeGkey) {
        this.argocalevttypeGkey = argocalevttypeGkey;
    }

    public String getArgocalevttypeName() {
        return this.argocalevttypeName;
    }

    public void setArgocalevttypeName(String argocalevttypeName) {
        this.argocalevttypeName = argocalevttypeName;
    }

    public Boolean getArgocalevttypeIsPartialDayEventType() {
        return this.argocalevttypeIsPartialDayEventType;
    }

    public void setArgocalevttypeIsPartialDayEventType(Boolean argocalevttypeIsPartialDayEventType) {
        this.argocalevttypeIsPartialDayEventType = argocalevttypeIsPartialDayEventType;
    }

    public String getArgocalevttypeDescription() {
        return this.argocalevttypeDescription;
    }

    public void setArgocalevttypeDescription(String argocalevttypeDescription) {
        this.argocalevttypeDescription = argocalevttypeDescription;
    }

}
