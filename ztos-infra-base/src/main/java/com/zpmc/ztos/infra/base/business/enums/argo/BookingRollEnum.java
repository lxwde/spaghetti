package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum BookingRollEnum {
//    SINGLE(1, "single", "single booking", "", "SINGLE"),
//    ROLL(2, "roll", "roll booking", "", "ROLL"),
//    ADVISED(3, "advised", "advised booking", "", "ADVISED");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    BookingRollEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(BookingRollEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(BookingRollEnum.class);
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

public class BookingRollEnum
        extends AtomizedEnum {
    public static final BookingRollEnum NO = new BookingRollEnum("NO", "atom.BookingRollEnum.NO.description", "atom.BookingRollEnum.NO.code", "", "", "");
    public static final BookingRollEnum ROLL = new BookingRollEnum("ROLL", "atom.BookingRollEnum.ROLL.description", "atom.BookingRollEnum.ROLL.code", "", "", "");
    public static final BookingRollEnum ADVISED = new BookingRollEnum("ADVISED", "atom.BookingRollEnum.ADVISED.description", "atom.BookingRollEnum.ADVISED.code", "", "", "");

    public static BookingRollEnum getEnum(String string) {
        return (BookingRollEnum) BookingRollEnum.getEnum(BookingRollEnum.class, (String)string);
    }

    public static Map getEnumMap() {
        return BookingRollEnum.getEnumMap(BookingRollEnum.class);
    }

    public static List getEnumList() {
        return BookingRollEnum.getEnumList(BookingRollEnum.class);
    }

    public static Collection getList() {
        return BookingRollEnum.getEnumList(BookingRollEnum.class);
    }

    public static Iterator iterator() {
        return BookingRollEnum.iterator(BookingRollEnum.class);
    }

    protected BookingRollEnum(String string, String string2, String string3, String string4, String string5, String string6) {
        super(string, string2, string3, string4, string5, string6);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeBookingRollEnum";
    }
}
