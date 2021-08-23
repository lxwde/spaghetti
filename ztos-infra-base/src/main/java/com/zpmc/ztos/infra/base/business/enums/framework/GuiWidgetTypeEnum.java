package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.utils.AtomizedEnumUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.enums.Enum;

public class GuiWidgetTypeEnum extends Enum {
    public static final GuiWidgetTypeEnum TEXT = new GuiWidgetTypeEnum("text");
    public static final GuiWidgetTypeEnum PERCENT = new GuiWidgetTypeEnum("percent");
    public static final GuiWidgetTypeEnum PASSWORD = new GuiWidgetTypeEnum("password");
    public static final GuiWidgetTypeEnum DECIMAL = new GuiWidgetTypeEnum("decimal");
    public static final GuiWidgetTypeEnum PRICE = new GuiWidgetTypeEnum("price");
    public static final GuiWidgetTypeEnum INTEGER = new GuiWidgetTypeEnum("integer");
    public static final GuiWidgetTypeEnum DATE = new GuiWidgetTypeEnum("date");
    public static final GuiWidgetTypeEnum DATE_TIME = new GuiWidgetTypeEnum("dateTime");
    public static final GuiWidgetTypeEnum TIME = new GuiWidgetTypeEnum("time");
    public static final GuiWidgetTypeEnum LOV = new GuiWidgetTypeEnum("lov");
    public static final GuiWidgetTypeEnum XML = new GuiWidgetTypeEnum("xml");
    public static final GuiWidgetTypeEnum PREDICATE = new GuiWidgetTypeEnum("predicate");
    public static final GuiWidgetTypeEnum IMAGE = new GuiWidgetTypeEnum("image");
    public static final GuiWidgetTypeEnum MOBILE_BUTTON = new GuiWidgetTypeEnum("mobileButton");
    public static final GuiWidgetTypeEnum MOBILE_LABEL = new GuiWidgetTypeEnum("mobileLabel");
    public static final GuiWidgetTypeEnum MOBILE_SELECTABLE_AREA = new GuiWidgetTypeEnum("mobileArea");
    public static final GuiWidgetTypeEnum MOBILE_VALUE_TABLE = new GuiWidgetTypeEnum("mobileTable");
    public static final GuiWidgetTypeEnum MOBILE_LINK = new GuiWidgetTypeEnum("mobileLink");
    public static final GuiWidgetTypeEnum MOBILE_LIST = new GuiWidgetTypeEnum("mobileList");
    public static final GuiWidgetTypeEnum CHILD = new GuiWidgetTypeEnum("child");
    public static final GuiWidgetTypeEnum YES_NO = new GuiWidgetTypeEnum("yesno");
    public static final GuiWidgetTypeEnum YES_NO_RADIO = new GuiWidgetTypeEnum("yesNoRadio");
    public static final GuiWidgetTypeEnum CHECKBOX = new GuiWidgetTypeEnum("checkbox");
    public static final GuiWidgetTypeEnum TEXT_MEASUREMENT = new GuiWidgetTypeEnum("textMeasurement");
    public static final GuiWidgetTypeEnum CUSTOM = new GuiWidgetTypeEnum("custom");

    private GuiWidgetTypeEnum(String inName) {
        super(inName);
    }

    @Nullable
    public static GuiWidgetTypeEnum valueOf(String inGuiWidgetTypeString) {
        GuiWidgetTypeEnum wt = (GuiWidgetTypeEnum) AtomizedEnumUtils.safeGetEnum(GuiWidgetTypeEnum.class, inGuiWidgetTypeString);
        return wt;
    }
}
