package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.IQueryLoggingConfiguration;
import com.zpmc.ztos.infra.base.common.model.Roastery;

public class QueryLoggingConfigurationUtils {
    private QueryLoggingConfigurationUtils() {
    }

    public static IQueryLoggingConfiguration getConfiguration() {
        return (IQueryLoggingConfiguration) Roastery.getBean("queryLoggingConfiguration");
    }
}
