package com.zpmc.ztos.infra.base.business.enums.framework;


import org.apache.commons.lang.enums.ValuedEnum;

public class MessageLevelEnum extends ValuedEnum {

    private static final int ERR_SEVERE = 3;
    private static final int ERR_WARNING = 2;
    private static final int ERR_INFO = 1;
    public static final MessageLevelEnum INFO = new MessageLevelEnum("INFO", 1);
    public static final MessageLevelEnum WARNING = new MessageLevelEnum("WARNING", 2);
    public static final MessageLevelEnum SEVERE = new MessageLevelEnum("SEVERE", 3);

    private MessageLevelEnum(String inName, int inValue) {
        super(inName, inValue);
    }

    public static MessageLevelEnum getMessageLevel(int inString) {
        return (MessageLevelEnum)ValuedEnum.getEnum(MessageLevelEnum.class, (int)inString);
    }
}
