package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RelativeTimeEnum extends AtomizedEnum {
    public static final RelativeTimeEnum TODAY = new RelativeTimeEnum("TODAY", "atom.RelativeTimeEnum.TODAY.description", "atom.RelativeTimeEnum.TODAY.code", "", "", "");
    public static final RelativeTimeEnum YESTERDAY = new RelativeTimeEnum("YESTERDAY", "atom.RelativeTimeEnum.YESTERDAY.description", "atom.RelativeTimeEnum.YESTERDAY.code", "", "", "");
    public static final RelativeTimeEnum WITHIN_10M = new RelativeTimeEnum("WITHIN_10M", "atom.RelativeTimeEnum.WITHIN_10M.description", "atom.RelativeTimeEnum.WITHIN_10M.code", "", "", "");
    public static final RelativeTimeEnum WITHIN_1H = new RelativeTimeEnum("WITHIN_1H", "atom.RelativeTimeEnum.WITHIN_1H.description", "atom.RelativeTimeEnum.WITHIN_1H.code", "", "", "");
    public static final RelativeTimeEnum TODAY_PERIOD1 = new RelativeTimeEnum("TODAY_PERIOD1", "atom.RelativeTimeEnum.TODAY_PERIOD1.description", "atom.RelativeTimeEnum.TODAY_PERIOD1.code", "", "", "");
    public static final RelativeTimeEnum TODAY_PERIOD2 = new RelativeTimeEnum("TODAY_PERIOD2", "atom.RelativeTimeEnum.TODAY_PERIOD2.description", "atom.RelativeTimeEnum.TODAY_PERIOD2.code", "", "", "");
    public static final RelativeTimeEnum TODAY_PERIOD3 = new RelativeTimeEnum("TODAY_PERIOD3", "atom.RelativeTimeEnum.TODAY_PERIOD3.description", "atom.RelativeTimeEnum.TODAY_PERIOD3.code", "", "", "");
    public static final RelativeTimeEnum TODAY_PERIOD4 = new RelativeTimeEnum("TODAY_PERIOD4", "atom.RelativeTimeEnum.TODAY_PERIOD4.description", "atom.RelativeTimeEnum.TODAY_PERIOD4.code", "", "", "");
    public static final RelativeTimeEnum YESTERDAY_PERIOD1 = new RelativeTimeEnum("YESTERDAY_PERIOD1", "atom.RelativeTimeEnum.YESTERDAY_PERIOD1.description", "atom.RelativeTimeEnum.YESTERDAY_PERIOD1.code", "", "", "");
    public static final RelativeTimeEnum YESTERDAY_PERIOD2 = new RelativeTimeEnum("YESTERDAY_PERIOD2", "atom.RelativeTimeEnum.YESTERDAY_PERIOD2.description", "atom.RelativeTimeEnum.YESTERDAY_PERIOD2.code", "", "", "");
    public static final RelativeTimeEnum YESTERDAY_PERIOD3 = new RelativeTimeEnum("YESTERDAY_PERIOD3", "atom.RelativeTimeEnum.YESTERDAY_PERIOD3.description", "atom.RelativeTimeEnum.YESTERDAY_PERIOD3.code", "", "", "");
    public static final RelativeTimeEnum YESTERDAY_PERIOD4 = new RelativeTimeEnum("YESTERDAY_PERIOD4", "atom.RelativeTimeEnum.YESTERDAY_PERIOD4.description", "atom.RelativeTimeEnum.YESTERDAY_PERIOD4.code", "", "", "");
    public static final RelativeTimeEnum LAST_SEVEN_DAYS = new RelativeTimeEnum("LAST_SEVEN_DAYS", "atom.RelativeTimeEnum.LAST_SEVEN_DAYS.description", "atom.RelativeTimeEnum.LAST_SEVEN_DAYS.code", "", "", "");
    public static final RelativeTimeEnum CURRENT_MONTH = new RelativeTimeEnum("CURRENT_MONTH", "atom.RelativeTimeEnum.CURRENT_MONTH.description", "atom.RelativeTimeEnum.CURRENT_MONTH.code", "", "", "");
    public static final RelativeTimeEnum BEF_NOW = new RelativeTimeEnum("BEF_NOW", "atom.RelativeTimeEnum.BEF_NOW.description", "atom.RelativeTimeEnum.BEF_NOW.code", "", "", "");
    public static final RelativeTimeEnum NEW_10M = new RelativeTimeEnum("NEW_10M", "atom.RelativeTimeEnum.NEW_10M.description", "atom.RelativeTimeEnum.NEW_10M.code", "", "", "");
    public static final RelativeTimeEnum NEW_1H = new RelativeTimeEnum("NEW_1H", "atom.RelativeTimeEnum.NEW_1H.description", "atom.RelativeTimeEnum.NEW_1H.code", "", "", "");
    public static final RelativeTimeEnum NEW_1D = new RelativeTimeEnum("NEW_1D", "atom.RelativeTimeEnum.NEW_1D.description", "atom.RelativeTimeEnum.NEW_1D.code", "", "", "");
    public static final RelativeTimeEnum NEW_10D = new RelativeTimeEnum("NEW_10D", "atom.RelativeTimeEnum.NEW_10D.description", "atom.RelativeTimeEnum.NEW_10D.code", "", "", "");
    public static final RelativeTimeEnum AFT_NOW = new RelativeTimeEnum("AFT_NOW", "atom.RelativeTimeEnum.AFT_NOW.description", "atom.RelativeTimeEnum.AFT_NOW.code", "", "", "");
    public static final RelativeTimeEnum OLD_1D = new RelativeTimeEnum("OLD_1D", "atom.RelativeTimeEnum.OLD_1D.description", "atom.RelativeTimeEnum.OLD_1D.code", "", "", "");
    public static final RelativeTimeEnum OLD_10D = new RelativeTimeEnum("OLD_10D", "atom.RelativeTimeEnum.OLD_10D.description", "atom.RelativeTimeEnum.OLD_10D.code", "", "", "");
    public static final RelativeTimeEnum OLD_100D = new RelativeTimeEnum("OLD_100D", "atom.RelativeTimeEnum.OLD_100D.description", "atom.RelativeTimeEnum.OLD_100D.code", "", "", "");

    public static RelativeTimeEnum getEnum(String inName) {
        return (RelativeTimeEnum) RelativeTimeEnum.getEnum(RelativeTimeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return RelativeTimeEnum.getEnumMap(RelativeTimeEnum.class);
    }

    public static List getEnumList() {
        return RelativeTimeEnum.getEnumList(RelativeTimeEnum.class);
    }

    public static Collection getList() {
        return RelativeTimeEnum.getEnumList(RelativeTimeEnum.class);
    }

    public static Iterator iterator() {
        return RelativeTimeEnum.iterator(RelativeTimeEnum.class);
    }

    protected RelativeTimeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.zpmc.ztos.infra.base.business.enums.framework.RelativeTimeEnum";
    }

}
