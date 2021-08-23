package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.model.GuiWidgetSubtypeStrings;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.enums.Enum;
public class GuiWidgetSubtypeEnum extends Enum {

    public static final GuiWidgetSubtypeEnum SHORT = new GuiWidgetSubtypeEnum("SHORT");
    public static final GuiWidgetSubtypeEnum MEDIUM = new GuiWidgetSubtypeEnum("MEDIUM");
    public static final GuiWidgetSubtypeEnum LONG = new GuiWidgetSubtypeEnum("LONG");
    public static final GuiWidgetSubtypeEnum LOV_MULTI_ASSIGNMENT = new GuiWidgetSubtypeEnum("LOV_MULTI_ASSIGNMENT");
    public static final GuiWidgetSubtypeEnum LOV_MULTI_ASSIGNMENT_SORTED = new GuiWidgetSubtypeEnum("LOV_MULTI_ASSIGNMENT_SORTED");
    public static final GuiWidgetSubtypeEnum LOV_LOOKUP_MULTI_ASSIGNMENT = new GuiWidgetSubtypeEnum("LOV_LOOKUP_MULTI_ASSIGNMENT");
    public static final GuiWidgetSubtypeEnum LOV_LOOKUP_MULTI_ASSIGNMENT_SORTED = new GuiWidgetSubtypeEnum("LOV_LOOKUP_MULTI_ASSIGNMENT_SORTED");
    public static final GuiWidgetSubtypeEnum LOV_SINGLE_POPUP = new GuiWidgetSubtypeEnum("LOV_SINGLE_POPUP");
    public static final GuiWidgetSubtypeEnum LOV_SINGLE_SELECT = new GuiWidgetSubtypeEnum("LOV_SINGLE_SELECT");
    public static final GuiWidgetSubtypeEnum LOV_SEARCH_POPUP = new GuiWidgetSubtypeEnum("LOV_SEARCH_POPUP");
    public static final GuiWidgetSubtypeEnum LOV_SEARCH_MULTI_ASSIGNMENT = new GuiWidgetSubtypeEnum("searchMultiAssignment");
    public static final GuiWidgetSubtypeEnum LOV_MULTI_ASSIGNMENT_POPUP = new GuiWidgetSubtypeEnum("multiAssignmentPopup");
    public static final GuiWidgetSubtypeEnum LOV_RADIOS = new GuiWidgetSubtypeEnum("radios");
    public static final GuiWidgetSubtypeEnum YEAR_ONLY = new GuiWidgetSubtypeEnum("YEAR_ONLY");
    public static final GuiWidgetSubtypeEnum HOUR_MINUTE_DEPRECATED = new GuiWidgetSubtypeEnum("HOUR_MINUTE_DEPRECATED");
    public static final GuiWidgetSubtypeEnum VERIFIED = new GuiWidgetSubtypeEnum("VERIFIED");
    public static final GuiWidgetSubtypeEnum LENGTH = new GuiWidgetSubtypeEnum("length");
    public static final GuiWidgetSubtypeEnum TIME = new GuiWidgetSubtypeEnum("time");
    public static final GuiWidgetSubtypeEnum MASS = new GuiWidgetSubtypeEnum("mass");
    public static final GuiWidgetSubtypeEnum TEMPERATURE = new GuiWidgetSubtypeEnum("temperature");
    public static final GuiWidgetSubtypeEnum STORAGE = new GuiWidgetSubtypeEnum("storage");
    public static final GuiWidgetSubtypeEnum AREA = new GuiWidgetSubtypeEnum("area");
    public static final GuiWidgetSubtypeEnum VOLUME = new GuiWidgetSubtypeEnum("volume");
    public static final GuiWidgetSubtypeEnum HOURMINUTEONLY = new GuiWidgetSubtypeEnum("hourMinuteOnly");
    public static final GuiWidgetSubtypeEnum CHART = new GuiWidgetSubtypeEnum("chart");

    private GuiWidgetSubtypeEnum(String inName) {
        super(inName);
    }

    @Nullable
    public static GuiWidgetSubtypeEnum valueOf(String inName) {
        return GuiWidgetSubtypeStrings.asGuiWidgetSubtype(inName);
    }

}
