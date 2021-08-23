package com.zpmc.ztos.infra.base.common.events;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoCalendarEventDO;
import com.zpmc.ztos.infra.base.business.enums.framework.AppCalendarIntervalEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ArgoCalendarEvent extends ArgoCalendarEventDO {
    public boolean matches(Date inDate, TimeZone inTimeZone) {
        AppCalendarIntervalEnum interval;
        if (inDate == null) {
            return false;
        }
        if (inTimeZone == null) {
            inTimeZone = TimeZone.getTimeZone("GMT");
        }
        if ((interval = this.getArgocalevtInterval()) == AppCalendarIntervalEnum.ONCE) {
            return this.handleIntervalONCE(inDate, inTimeZone);
        }
        if (interval == AppCalendarIntervalEnum.DAILY) {
            return this.handleIntervalDAILY(inDate, inTimeZone);
        }
        if (interval == AppCalendarIntervalEnum.WEEKLY) {
            return this.handleIntervalWEEKLY(inDate, inTimeZone);
        }
        if (interval == AppCalendarIntervalEnum.ANNUALLY) {
            return this.handleIntervalANNUALLY(inDate, inTimeZone);
        }
        throw new UnsupportedOperationException("Unsupported interval type: " + (Object)interval);
    }

    private boolean handleIntervalONCE(Date inDate, TimeZone inTimeZone) {
        if (this.getArgocalevtOccurrDateStart() == null) {
            return false;
        }
        Calendar candidate = Calendar.getInstance(inTimeZone);
        candidate.setTime(inDate);
        candidate.set(11, 0);
        candidate.set(12, 0);
        candidate.set(13, 0);
        candidate.set(14, 0);
        Calendar start = Calendar.getInstance(inTimeZone);
        start.setTime(this.getArgocalevtOccurrDateStart());
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        boolean candidateSameTime = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) == DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start);
        return candidateSameTime;
    }

    private boolean handleIntervalDAILY(Date inDate, TimeZone inTimeZone) {
        if (this.getArgocalevtOccurrDateStart() == null) {
            return false;
        }
        Calendar candidate = Calendar.getInstance(inTimeZone);
        candidate.setTime(inDate);
        candidate.set(11, 0);
        candidate.set(12, 0);
        candidate.set(13, 0);
        candidate.set(14, 0);
        Calendar start = Calendar.getInstance(inTimeZone);
        start.setTime(this.getArgocalevtOccurrDateStart());
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        if (this.getArgocalevtRecurrDateEnd() == null) {
            boolean candidateLaterTime = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start);
            return candidateLaterTime;
        }
        Calendar end = Calendar.getInstance(inTimeZone);
        end.setTime(this.getArgocalevtRecurrDateEnd());
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        boolean candidateInRange = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start) && DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) <= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)end);
        return candidateInRange;
    }

    private boolean handleIntervalWEEKLY(Date inDate, TimeZone inTimeZone) {
        boolean sameWeekDay;
        if (this.getArgocalevtOccurrDateStart() == null) {
            return false;
        }
        Calendar candidate = Calendar.getInstance(inTimeZone);
        candidate.setTime(inDate);
        candidate.set(11, 0);
        candidate.set(12, 0);
        candidate.set(13, 0);
        candidate.set(14, 0);
        Calendar start = Calendar.getInstance(inTimeZone);
        start.setTime(this.getArgocalevtOccurrDateStart());
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        int candidateDayOfWeek = candidate.get(7);
        int occurrStartDayOfWeek = start.get(7);
        boolean bl = sameWeekDay = candidateDayOfWeek == occurrStartDayOfWeek;
        if (!sameWeekDay) {
            return false;
        }
        if (this.getArgocalevtRecurrDateEnd() == null) {
            boolean candidateLaterTime = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start);
            return candidateLaterTime;
        }
        Calendar end = Calendar.getInstance(inTimeZone);
        end.setTime(this.getArgocalevtRecurrDateEnd());
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        boolean candidateInRange = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start) && DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) <= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)end);
        return candidateInRange;
    }

    private boolean handleIntervalANNUALLY(Date inDate, TimeZone inTimeZone) {
        int occurrStartMonthOfYear;
        boolean sameMonthOfYear;
        boolean sameDayOfMonth;
        if (this.getArgocalevtOccurrDateStart() == null) {
            return false;
        }
        Calendar candidate = Calendar.getInstance(inTimeZone);
        candidate.setTime(inDate);
        candidate.set(11, 0);
        candidate.set(12, 0);
        candidate.set(13, 0);
        candidate.set(14, 0);
        Calendar start = Calendar.getInstance(inTimeZone);
        start.setTime(this.getArgocalevtOccurrDateStart());
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        int candidateDayOfMonth = candidate.get(5);
        int occurrStartDayOfMonth = start.get(5);
        boolean bl = sameDayOfMonth = candidateDayOfMonth == occurrStartDayOfMonth;
        if (!sameDayOfMonth) {
            return false;
        }
        int candidateMonthOfYear = candidate.get(2);
        boolean bl2 = sameMonthOfYear = candidateMonthOfYear == (occurrStartMonthOfYear = start.get(2));
        if (!sameMonthOfYear) {
            return false;
        }
        if (this.getArgocalevtRecurrDateEnd() == null) {
            boolean candidateLaterTime = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start);
            return candidateLaterTime;
        }
        Calendar end = Calendar.getInstance(inTimeZone);
        end.setTime(this.getArgocalevtRecurrDateEnd());
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        boolean candidateInRange = DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) >= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)start) && DateUtil.getDSTSafeCalendarTimeInMs((Calendar)candidate) <= DateUtil.getDSTSafeCalendarTimeInMs((Calendar)end);
        return candidateInRange;
    }

    @Nullable
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = null;
        if (AppCalendarIntervalEnum.ONCE == this.getArgocalevtInterval() && this.getArgocalevtRecurrDateEnd() != null) {
            bizViolation = BizViolation.createFieldViolation((IPropertyKey) IFrameworkPropertyKeys.APPCALENDAREVENT__ONCE_EVENT_MUST_NOT_HAVE_RECURR_END_DATE, bizViolation, (IMetafieldId) IArgoCalendarField.ARGOCALEVT_RECURR_DATE_END);
        }
        if (AppCalendarIntervalEnum.DAILY == this.getArgocalevtInterval() && this.getArgocalevtRecurrDateEnd() == null) {
            bizViolation = BizViolation.createFieldViolation((IPropertyKey) IFrameworkPropertyKeys.APPCALENDAREVENT__DAILY_EVENT_MUST_HAVE_RECURR_END_DATE, (BizViolation)bizViolation, (IMetafieldId) IArgoCalendarField.ARGOCALEVT_RECURR_DATE_END);
        }
        if (this.getArgocalevtOccurrDateStart() != null && this.getArgocalevtRecurrDateEnd() != null && this.getArgocalevtRecurrDateEnd().before(this.getArgocalevtOccurrDateStart())) {
            bizViolation = BizViolation.createFieldViolation((IPropertyKey) IFrameworkPropertyKeys.APPCALENDAREVENT__RECURR_END_DATE_MUST_NOT_BE_BEFORE_OCCURR_START_DATE, (BizViolation)bizViolation, (IMetafieldId) IArgoCalendarField.ARGOCALEVT_RECURR_DATE_END);
        }
        MetafieldIdList idList = new MetafieldIdList();
        idList.add(IArgoCalendarField.ARGOCALEVT_OCCURR_DATE_START);
        idList.add(IArgoCalendarField.ARGOCALEVT_CALENDAR);
        idList.add(IArgoCalendarField.ARGOCALEVT_EVENT_TYPE);
        if (!this.isUniqueInClass(idList)) {
            bizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CALENDAR_EVENT_EXISTS_FOR_DATE, (BizViolation)bizViolation, (Object)this.getArgocalevtOccurrDateStart());
        }
        return bizViolation;
    }

}
