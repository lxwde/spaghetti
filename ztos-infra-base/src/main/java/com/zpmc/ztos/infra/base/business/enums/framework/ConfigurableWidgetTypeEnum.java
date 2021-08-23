package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfigurableWidgetTypeEnum extends AtomizedEnum {
    public static final ConfigurableWidgetTypeEnum DEFAULT = new ConfigurableWidgetTypeEnum("DEFAULT", "atom.ConfigurableWidgetTypeEnum.DEFAULT.description", "atom.ConfigurableWidgetTypeEnum.DEFAULT.code", "", "", "");
    public static final ConfigurableWidgetTypeEnum LOV_POPUP = new ConfigurableWidgetTypeEnum("LOV_POPUP", "atom.ConfigurableWidgetTypeEnum.LOV_POPUP.description", "atom.ConfigurableWidgetTypeEnum.LOV_POPUP.code", "", "", "");

    public static ConfigurableWidgetTypeEnum getEnum(String inName) {
        return (ConfigurableWidgetTypeEnum) ConfigurableWidgetTypeEnum.getEnum(ConfigurableWidgetTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ConfigurableWidgetTypeEnum.getEnumMap(ConfigurableWidgetTypeEnum.class);
    }

    public static List getEnumList() {
        return ConfigurableWidgetTypeEnum.getEnumList(ConfigurableWidgetTypeEnum.class);
    }

    public static Collection getList() {
        return ConfigurableWidgetTypeEnum.getEnumList(ConfigurableWidgetTypeEnum.class);
    }

    public static Iterator iterator() {
        return ConfigurableWidgetTypeEnum.iterator(ConfigurableWidgetTypeEnum.class);
    }

    protected ConfigurableWidgetTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeConfigurableWidgetTypeEnum";
    }

}
