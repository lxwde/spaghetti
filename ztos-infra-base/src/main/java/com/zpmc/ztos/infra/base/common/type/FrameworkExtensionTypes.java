package com.zpmc.ztos.infra.base.common.type;

import com.zpmc.ztos.infra.base.business.interfaces.IExtensionTypeId;
import com.zpmc.ztos.infra.base.business.model.ExtensionTypeIdFactory;

public class FrameworkExtensionTypes {
    public static final IExtensionTypeId LIBRARY = ExtensionTypeIdFactory.valueOf("LIBRARY");
    public static final IExtensionTypeId TRANSACTED_BUSINESS_FUNCTION = ExtensionTypeIdFactory.valueOf("TRANSACTED_BUSINESS_FUNCTION");
    public static final IExtensionTypeId ENTITY_LIFECYCLE_INTERCEPTION = ExtensionTypeIdFactory.valueOf("ENTITY_LIFECYCLE_INTERCEPTION");
    public static final IExtensionTypeId TABLE_VIEW_COMMAND = ExtensionTypeIdFactory.valueOf("TABLE_VIEW_COMMAND");
    public static final IExtensionTypeId UI_COMMAND_PROVIDER = ExtensionTypeIdFactory.valueOf("UI_COMMAND_PROVIDER");
    public static final IExtensionTypeId FORM_SUBMISSION_INTERCEPTION = ExtensionTypeIdFactory.valueOf("FORM_SUBMISSION_INTERCEPTION");
    public static final IExtensionTypeId LOV_FACTORY = ExtensionTypeIdFactory.valueOf("LOV_FACTORY");
    public static final IExtensionTypeId SERVER_LIFECYCLE = ExtensionTypeIdFactory.valueOf("SERVER_LIFECYCLE");
    public static final IExtensionTypeId SERVER_UI_TIER_LIFECYCLE = ExtensionTypeIdFactory.valueOf("SERVER_UI_TIER_LIFECYCLE");
    public static final IExtensionTypeId BEAN_PROTOTYPE = ExtensionTypeIdFactory.valueOf("BEAN_PROTOTYPE");
    public static final IExtensionTypeId REQUEST_SIMPLE_READ = ExtensionTypeIdFactory.valueOf("REQUEST_SIMPLE_READ");
    public static final IExtensionTypeId MOBILE_QUERY_RESULT_PROVIDER = ExtensionTypeIdFactory.valueOf("MOBILE_QUERY_RESULT_PROVIDER");

    private FrameworkExtensionTypes() {
    }
}
