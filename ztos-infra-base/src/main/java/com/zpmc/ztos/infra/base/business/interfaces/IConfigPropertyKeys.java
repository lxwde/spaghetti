package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

public interface IConfigPropertyKeys {
    public static final IPropertyKey LABEL_EDIT_METAFIELD = PropertyKeyFactory.keyWithFormat("LABEL_EDIT_METAFIELD", "Edit Metafield - {0}");
    public static final IPropertyKey LABEL_EDIT_OPTIONS = PropertyKeyFactory.keyWithFormat("LABEL_EDIT_OPTIONS", "Edit Options");
    public static final IPropertyKey LABEL_ALLOW_OPTIONS = PropertyKeyFactory.keyWithFormat("LABEL_ALLOW_OPTIONS", "Allow only options from pick list");
    public static final IPropertyKey LABEL_DEFUNCT_OPTION = PropertyKeyFactory.keyWithFormat("LABEL_DEFUNCT_OPTION", "Option No Longer Exists");
    public static final IPropertyKey WARN_SAVE_BEFORE_EDIT_OPTIONS = PropertyKeyFactory.keyWithFormat("WARN_SAVE_BEFORE_EDIT_OPTIONS", "You must first save before editing options.");
    public static final IPropertyKey LABEL_DEFAULT_DEFINITION = PropertyKeyFactory.keyWithFormat("LABEL_DEFAULT_DEFINITION", "Default Definition");
    public static final IPropertyKey WARN_DELETE_CUSTOM_OPTION = PropertyKeyFactory.keyWithFormat("WARN_DELETE_CUSTOM_OPTION", "Are you sure you want to delete one or more options? nDeleting an option will NOT modify any rows which uses this values. nn The deleted value will still appear in the picklist for entries still assigned to this value.");
    public static final IPropertyKey ERROR_CONFIG_SETTING_NOT_FOUND = PropertyKeyFactory.keyWithFormat("ERROR_CONFIG_SETTING_NOT_FOUND", "Could not find Configuration Setting: id={0}");
    public static final IPropertyKey ERROR_ON_SAVE_CONFIG_SETTING = PropertyKeyFactory.keyWithFormat("ERROR_ON_SAVE_CONFIG_SETTING", "Error saving Configuration Setting. Id={0} Value={1} ScopeLevel={2} ScopeGKey={3}");
    public static final IPropertyKey EXTA_XML = PropertyKeyFactory.keyWithFormat("EXTA_XML", "Extra XML");
    public static final IPropertyKey GEN_ATTRIBUTES = PropertyKeyFactory.keyWithFormat("GEN_ATTRIBUTES", "General Attributes");

}
