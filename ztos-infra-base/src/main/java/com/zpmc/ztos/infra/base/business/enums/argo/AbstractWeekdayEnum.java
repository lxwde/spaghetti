package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

public class AbstractWeekdayEnum extends AtomizedEnum {
    protected AbstractWeekdayEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public int getWeekdayIntValue() {
        if (WeekdayEnum.W7_SUNDAY.equals((Object)this)) {
            return 1;
        }
        if (WeekdayEnum.W1_MONDAY.equals((Object)this)) {
            return 2;
        }
        if (WeekdayEnum.W2_TUESDAY.equals((Object)this)) {
            return 3;
        }
        if (WeekdayEnum.W3_WEDNESDAY.equals((Object)this)) {
            return 4;
        }
        if (WeekdayEnum.W4_THURSDAY.equals((Object)this)) {
            return 5;
        }
        if (WeekdayEnum.W5_FRIDAY.equals((Object)this)) {
            return 6;
        }
        return 7;
    }
}
