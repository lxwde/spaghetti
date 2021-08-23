package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.common.events.ArgoCalendarEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArgoCalendarMatches {
    private final List _calendarEntries = new ArrayList();

    public void logCalendarMatch(ArgoCalendarEvent inEvent, Date inDate) {
        int duration = 1;
//        ArgoCalendarMatch calendarMatch = new ArgoCalendarMatch(inEvent, inDate, inDate, duration);
//        this._calendarEntries.add(calendarMatch);
    }

    public void logCalendarEntriesManually(ArgoCalendarEvent inEvent, Date inStartDate, Date inEndDate, int inDuration) {
//        ArgoCalendarMatch calendarMatch = new ArgoCalendarMatch(inEvent, inStartDate, inEndDate, inDuration);
//        this._calendarEntries.add(calendarMatch);
    }

    public void logCalendarNoMatch(Date inDate) {
        int duration = 1;
        if (this._calendarEntries.isEmpty()) {
//            ArgoCalendarMatch calendarNoMatch = new ArgoCalendarMatch(null, inDate, inDate, duration);
//            this._calendarEntries.add(calendarNoMatch);
        } else {
//            ArgoCalendarMatch previousEntry = (ArgoCalendarMatch)this._calendarEntries.get(this._calendarEntries.size() - 1);
//            if (previousEntry.getEvent() == null) {
//                previousEntry.incrementDuration();
//                previousEntry.setEndDate(inDate);
//            } else {
//                ArgoCalendarMatch calendarNoMatch = new ArgoCalendarMatch(null, inDate, inDate, duration);
//                this._calendarEntries.add(calendarNoMatch);
//            }
        }
    }

    public List getEntries() {
        return new ArrayList(this._calendarEntries);
    }
}
