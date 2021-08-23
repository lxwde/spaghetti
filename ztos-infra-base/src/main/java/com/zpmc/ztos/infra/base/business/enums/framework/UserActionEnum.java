package com.zpmc.ztos.infra.base.business.enums.framework;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.enums.Enum;
import java.util.List;

public class UserActionEnum extends Enum {
    
    
    private boolean _custom;

    private UserActionEnum(String inName) {
        super(inName);
    }

    public void setCustom(boolean inCustom) {
        this._custom = inCustom;
    }

    public boolean isCustom() {
        return this._custom;
    }

    public static boolean exists(String inName) {
        return UserActionEnum.getEnum(UserActionEnum.class, (String)inName) != null;
    }

    @Nullable
    public static UserActionEnum valueOf(String inName) {
        if (inName == null) {
            return null;
        }
        UserActionEnum userAction = (UserActionEnum) UserActionEnum.getEnum(UserActionEnum.class, (String)inName);
        return userAction == null ? new UserActionEnum(inName) : userAction;
    }

    public static List getAllActions() {
        return UserActionEnum.getEnumList(UserActionEnum.class);
    }

    public String toString() {
        if (this._custom) {
            return "custom" + super.toString();
        }
        return super.toString();
    }
}
