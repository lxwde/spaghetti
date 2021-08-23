package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CalendarTypeEnum extends AtomizedEnum {
    public static final CalendarTypeEnum STORAGE = new CalendarTypeEnum("STORAGE", "atom.CalendarTypeEnum.STORAGE.description", "atom.CalendarTypeEnum.STORAGE.code", "", "", "");
    public static final CalendarTypeEnum POWER = new CalendarTypeEnum("POWER", "atom.CalendarTypeEnum.POWER.description", "atom.CalendarTypeEnum.POWER.code", "", "", "");
    public static final CalendarTypeEnum GENERAL = new CalendarTypeEnum("GENERAL", "atom.CalendarTypeEnum.GENERAL.description", "atom.CalendarTypeEnum.GENERAL.code", "", "", "");

    public static CalendarTypeEnum getEnum(String inName) {
        return (CalendarTypeEnum) CalendarTypeEnum.getEnum(CalendarTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CalendarTypeEnum.getEnumMap(CalendarTypeEnum.class);
    }

    public static List getEnumList() {
        return CalendarTypeEnum.getEnumList(CalendarTypeEnum.class);
    }

    public static Collection getList() {
        return CalendarTypeEnum.getEnumList(CalendarTypeEnum.class);
    }

    public static Iterator iterator() {
        return CalendarTypeEnum.iterator(CalendarTypeEnum.class);
    }

    protected CalendarTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCalendarTypeEnum";
    }
}
