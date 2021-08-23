package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BookingRollCutoffEnum extends AtomizedEnum {

    public static final BookingRollCutoffEnum NO = new BookingRollCutoffEnum("NO", "atom.BookingRollCutoffEnum.NO.description", "atom.BookingRollCutoffEnum.NO.code", "", "", "");
    public static final BookingRollCutoffEnum NEWVESSEL = new BookingRollCutoffEnum("NEWVESSEL", "atom.BookingRollCutoffEnum.NEWVESSEL.description", "atom.BookingRollCutoffEnum.NEWVESSEL.code", "", "", "");
    public static final BookingRollCutoffEnum ALL = new BookingRollCutoffEnum("ALL", "atom.BookingRollCutoffEnum.ALL.description", "atom.BookingRollCutoffEnum.ALL.code", "", "", "");

    public static BookingRollCutoffEnum getEnum(String inName) {
        return (BookingRollCutoffEnum) BookingRollCutoffEnum.getEnum(BookingRollCutoffEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return BookingRollCutoffEnum.getEnumMap(BookingRollCutoffEnum.class);
    }

    public static List getEnumList() {
        return BookingRollCutoffEnum.getEnumList(BookingRollCutoffEnum.class);
    }

    public static Collection getList() {
        return BookingRollCutoffEnum.getEnumList(BookingRollCutoffEnum.class);
    }

    public static Iterator iterator() {
        return BookingRollCutoffEnum.iterator(BookingRollCutoffEnum.class);
    }

    protected BookingRollCutoffEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeBookingRollCutoffEnum";
    }

}
