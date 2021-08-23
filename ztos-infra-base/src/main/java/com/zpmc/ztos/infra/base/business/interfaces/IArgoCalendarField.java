package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IArgoCalendarField {
    public static final IMetafieldId ARGOCAL_GKEY = MetafieldIdFactory.valueOf((String)"argocalGkey");
    public static final IMetafieldId ARGOCAL_ID = MetafieldIdFactory.valueOf((String)"argocalId");
    public static final IMetafieldId ARGOCAL_DESCRIPTION = MetafieldIdFactory.valueOf((String)"argocalDescription");
    public static final IMetafieldId ARGOCAL_CALENDAR_TYPE = MetafieldIdFactory.valueOf((String)"argocalCalendarType");
    public static final IMetafieldId ARGOCAL_IS_DEFAULT_CALENDAR = MetafieldIdFactory.valueOf((String)"argocalIsDefaultCalendar");
    public static final IMetafieldId ARGOCAL_CALENDAR_EVENT = MetafieldIdFactory.valueOf((String)"argocalCalendarEvent");
    public static final IMetafieldId ARGOCALEVT_GKEY = MetafieldIdFactory.valueOf((String)"argocalevtGkey");
    public static final IMetafieldId ARGOCALEVT_NAME = MetafieldIdFactory.valueOf((String)"argocalevtName");
    public static final IMetafieldId ARGOCALEVT_DESCRIPTION = MetafieldIdFactory.valueOf((String)"argocalevtDescription");
    public static final IMetafieldId ARGOCALEVT_OCCURR_DATE_START = MetafieldIdFactory.valueOf((String)"argocalevtOccurrDateStart");
    public static final IMetafieldId ARGOCALEVT_RECURR_DATE_END = MetafieldIdFactory.valueOf((String)"argocalevtRecurrDateEnd");
    public static final IMetafieldId ARGOCALEVT_INTERVAL = MetafieldIdFactory.valueOf((String)"argocalevtInterval");
    public static final IMetafieldId ARGOCALEVT_FACILITY = MetafieldIdFactory.valueOf((String)"argocalevtFacility");
    public static final IMetafieldId ARGOCALEVT_EVENT_TYPE = MetafieldIdFactory.valueOf((String)"argocalevtEventType");
    public static final IMetafieldId ARGOCALEVT_CALENDAR = MetafieldIdFactory.valueOf((String)"argocalevtCalendar");
    public static final IMetafieldId ARGOCALEVTTYPE_GKEY = MetafieldIdFactory.valueOf((String)"argocalevttypeGkey");
    public static final IMetafieldId ARGOCALEVTTYPE_NAME = MetafieldIdFactory.valueOf((String)"argocalevttypeName");
    public static final IMetafieldId ARGOCALEVTTYPE_IS_PARTIAL_DAY_EVENT_TYPE = MetafieldIdFactory.valueOf((String)"argocalevttypeIsPartialDayEventType");
    public static final IMetafieldId ARGOCALEVTTYPE_DESCRIPTION = MetafieldIdFactory.valueOf((String)"argocalevttypeDescription");
}
