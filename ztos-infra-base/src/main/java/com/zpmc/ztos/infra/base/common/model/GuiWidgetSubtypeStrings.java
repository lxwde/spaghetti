package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetSubtypeEnum;
import com.zpmc.ztos.infra.base.common.utils.AtomizedEnumUtils;
import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class GuiWidgetSubtypeStrings {

    public static final String YEAR_ONLY = "accuracyYear";
    public static final String GUIT_SUBYPE_ACC_HR_MIN = "accuracyHourMinute";
    public static final String SHORT = "short";
    public static final String MEDIUM = "medium";
    public static final String LONG = "long";
    public static final String LOV_SINGLE_POPUP = "singlePopup";
    public static final String LOV_SINGLE_SELECT = "singleSelect";
    public static final String LOV_MULTI_ASSIGNMENT = "multiAssignment";
    public static final String LOV_MULTI_ASSIGNMENT_SORTED = "multiAssignmentSorted";
    public static final String LOV_LOOKUP_MULTI_ASSIGNMENT = "lookupMultiAssignment";
    public static final String LOV_LOOKUP_MULTI_ASSIGNMENT_SORTED = "lookupMultiAssignmentSorted";
    public static final String LOV_SEARCH = "search";
    public static final String LOV_RADIOS = "radios";
    public static final String VERIFIED = "verified";
    public static final String HOURMINUTE = "hourMinuteOnly";

    private GuiWidgetSubtypeStrings() {
    }

    @Deprecated
    @Nullable
    public static GuiWidgetSubtypeEnum asGuiWidgetSubtype(String inGuiWidgetSubtypeString) {
        GuiWidgetSubtypeEnum gwst = null;
        if (StringUtils.isNotEmpty((String)inGuiWidgetSubtypeString)) {
            gwst = StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LONG) ? GuiWidgetSubtypeEnum.LONG : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)MEDIUM) ? GuiWidgetSubtypeEnum.MEDIUM : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)SHORT) ? GuiWidgetSubtypeEnum.SHORT : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_MULTI_ASSIGNMENT) ? GuiWidgetSubtypeEnum.LOV_MULTI_ASSIGNMENT : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_MULTI_ASSIGNMENT_SORTED) ? GuiWidgetSubtypeEnum.LOV_MULTI_ASSIGNMENT_SORTED : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_LOOKUP_MULTI_ASSIGNMENT) ? GuiWidgetSubtypeEnum.LOV_LOOKUP_MULTI_ASSIGNMENT : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_LOOKUP_MULTI_ASSIGNMENT_SORTED) ? GuiWidgetSubtypeEnum.LOV_LOOKUP_MULTI_ASSIGNMENT_SORTED : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_SEARCH) ? GuiWidgetSubtypeEnum.LOV_SEARCH_POPUP : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_SINGLE_POPUP) ? GuiWidgetSubtypeEnum.LOV_SINGLE_POPUP : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_RADIOS) ? GuiWidgetSubtypeEnum.LOV_RADIOS : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)LOV_SINGLE_SELECT) ? GuiWidgetSubtypeEnum.LOV_SINGLE_SELECT : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)GUIT_SUBYPE_ACC_HR_MIN) ? GuiWidgetSubtypeEnum.HOUR_MINUTE_DEPRECATED : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)YEAR_ONLY) ? GuiWidgetSubtypeEnum.YEAR_ONLY : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)VERIFIED) ? GuiWidgetSubtypeEnum.VERIFIED : (StringUtils.equals((String)inGuiWidgetSubtypeString, (String)HOURMINUTE) ? GuiWidgetSubtypeEnum.HOURMINUTEONLY : (GuiWidgetSubtypeEnum) AtomizedEnumUtils.safeGetEnum(GuiWidgetSubtypeEnum.class, inGuiWidgetSubtypeString)))))))))))))));
        }
        return gwst;
    }

}
