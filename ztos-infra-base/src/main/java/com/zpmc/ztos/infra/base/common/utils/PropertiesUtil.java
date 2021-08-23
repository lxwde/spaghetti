package com.zpmc.ztos.infra.base.common.utils;

import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties merge(Properties inInto, Properties inSource, boolean inOverride) {
        Properties copy = (Properties)inInto.clone();
        Enumeration<?> propertyNames = inSource.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyKey = (String)propertyNames.nextElement();
            if (!inOverride && copy.getProperty(propertyKey) != null) continue;
            String propertyValue = inSource.getProperty(propertyKey);
            copy.setProperty(propertyKey, propertyValue);
        }
        return copy;
    }

    public static Properties merge(Properties inInto, Properties inSource) {
        return PropertiesUtil.merge(inInto, inSource, true);
    }
}
