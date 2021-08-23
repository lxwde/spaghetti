package com.zpmc.ztos.infra.base.common.type;

import com.zpmc.ztos.infra.base.business.interfaces.IExtensionTypeId;
import com.zpmc.ztos.infra.base.business.model.ExtensionTypeIdFactory;

public class OptimizationExtensionTypes {
    public static final IExtensionTypeId OPT_DATA_PROVIDER = ExtensionTypeIdFactory.valueOf((String)"OPT_DATA_PROVIDER");
    public static final IExtensionTypeId OPT_SOLVE_STRATEGY = ExtensionTypeIdFactory.valueOf((String)"OPT_SOLVE_STRATEGY");
    public static final IExtensionTypeId OPT_CONFIGURATION_PROVIDER = ExtensionTypeIdFactory.valueOf((String)"OPT_CONFIGURATION_PROVIDER");
    public static final IExtensionTypeId OPT_RESULT_HANDLER = ExtensionTypeIdFactory.valueOf((String)"OPT_RESULT_HANDLER");

    private OptimizationExtensionTypes() {
    }

}
