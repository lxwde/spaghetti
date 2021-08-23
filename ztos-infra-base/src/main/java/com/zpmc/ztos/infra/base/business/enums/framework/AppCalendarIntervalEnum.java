package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppCalendarIntervalEnum extends AtomizedEnum {
    public static final AppCalendarIntervalEnum ONCE = new AppCalendarIntervalEnum("ONCE", "atom.AppCalendarIntervalEnum.ONCE.description", "atom.AppCalendarIntervalEnum.ONCE.code", "", "", "");
    public static final AppCalendarIntervalEnum DAILY = new AppCalendarIntervalEnum("DAILY", "atom.AppCalendarIntervalEnum.DAILY.description", "atom.AppCalendarIntervalEnum.DAILY.code", "", "", "");
    public static final AppCalendarIntervalEnum WEEKLY = new AppCalendarIntervalEnum("WEEKLY", "atom.AppCalendarIntervalEnum.WEEKLY.description", "atom.AppCalendarIntervalEnum.WEEKLY.code", "", "", "");
    public static final AppCalendarIntervalEnum ANNUALLY = new AppCalendarIntervalEnum("ANNUALLY", "atom.AppCalendarIntervalEnum.ANNUALLY.description", "atom.AppCalendarIntervalEnum.ANNUALLY.code", "", "", "");

    public static AppCalendarIntervalEnum getEnum(String inName) {
        return (AppCalendarIntervalEnum) AppCalendarIntervalEnum.getEnum(AppCalendarIntervalEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return AppCalendarIntervalEnum.getEnumMap(AppCalendarIntervalEnum.class);
    }

    public static List getEnumList() {
        return AppCalendarIntervalEnum.getEnumList(AppCalendarIntervalEnum.class);
    }

    public static Collection getList() {
        return AppCalendarIntervalEnum.getEnumList(AppCalendarIntervalEnum.class);
    }

    public static Iterator iterator() {
        return AppCalendarIntervalEnum.iterator(AppCalendarIntervalEnum.class);
    }

    protected AppCalendarIntervalEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeAppCalendarIntervalEnum";
    }

}
