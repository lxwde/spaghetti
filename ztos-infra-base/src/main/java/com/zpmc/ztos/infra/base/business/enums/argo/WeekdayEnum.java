package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum WeekdayEnum {
//    W1_MONDAY(1, "W1_MONDAY", "", "", "W1_MONDAY"),
//    W2_TUESDAY(2, "W2_TUESDAY", "", "", "W2_TUESDAY"),
//    W3_WEDNESDAY(3, "W3_WEDNESDAY", "", "", "W3_WEDNESDAY"),
//    W4_THURSDAY(4, "W4_THURSDAY", "", "", "W4_THURSDAY"),
//    W5_FRIDAY(5, "W5_FRIDAY", "", "", "W5_FRIDAY"),
//    W6_SATURDAY(6, "W6_SATURDAY", "", "", "W6_SATURDAY"),
//    W7_SUNDAY(7, "W7_SUNDAY", "", "", "W7_SUNDAY");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    WeekdayEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(WeekdayEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(WeekdayEnum.class);
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//}

public class WeekdayEnum
        extends AbstractWeekdayEnum {
    public static final WeekdayEnum W1_MONDAY = new WeekdayEnum("W1_MONDAY", "atom.WeekdayEnum.W1_MONDAY.description", "atom.WeekdayEnum.W1_MONDAY.code", "", "", "");
    public static final WeekdayEnum W2_TUESDAY = new WeekdayEnum("W2_TUESDAY", "atom.WeekdayEnum.W2_TUESDAY.description", "atom.WeekdayEnum.W2_TUESDAY.code", "", "", "");
    public static final WeekdayEnum W3_WEDNESDAY = new WeekdayEnum("W3_WEDNESDAY", "atom.WeekdayEnum.W3_WEDNESDAY.description", "atom.WeekdayEnum.W3_WEDNESDAY.code", "", "", "");
    public static final WeekdayEnum W4_THURSDAY = new WeekdayEnum("W4_THURSDAY", "atom.WeekdayEnum.W4_THURSDAY.description", "atom.WeekdayEnum.W4_THURSDAY.code", "", "", "");
    public static final WeekdayEnum W5_FRIDAY = new WeekdayEnum("W5_FRIDAY", "atom.WeekdayEnum.W5_FRIDAY.description", "atom.WeekdayEnum.W5_FRIDAY.code", "", "", "");
    public static final WeekdayEnum W6_SATURDAY = new WeekdayEnum("W6_SATURDAY", "atom.WeekdayEnum.W6_SATURDAY.description", "atom.WeekdayEnum.W6_SATURDAY.code", "", "", "");
    public static final WeekdayEnum W7_SUNDAY = new WeekdayEnum("W7_SUNDAY", "atom.WeekdayEnum.W7_SUNDAY.description", "atom.WeekdayEnum.W7_SUNDAY.code", "", "", "");

    public static WeekdayEnum getEnum(String inName) {
        return (WeekdayEnum) WeekdayEnum.getEnum(WeekdayEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return WeekdayEnum.getEnumMap(WeekdayEnum.class);
    }

    public static List getEnumList() {
        return WeekdayEnum.getEnumList(WeekdayEnum.class);
    }

    public static Collection getList() {
        return WeekdayEnum.getEnumList(WeekdayEnum.class);
    }

    public static Iterator iterator() {
        return WeekdayEnum.iterator(WeekdayEnum.class);
    }

    protected WeekdayEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeWeekdayEnum";
    }
}
