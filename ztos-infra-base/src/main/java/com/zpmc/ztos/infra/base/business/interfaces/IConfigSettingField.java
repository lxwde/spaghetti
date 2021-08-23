package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IConfigSettingField {
    public static final IMetafieldId CNFIG_GKEY = MetafieldIdFactory.valueOf("cnfigGkey");
    public static final IMetafieldId CNFIG_ID = MetafieldIdFactory.valueOf("cnfigId");
    public static final IMetafieldId CNFIG_SCOPE = MetafieldIdFactory.valueOf("cnfigScope");
    public static final IMetafieldId CNFIG_SCOPE_GKEY = MetafieldIdFactory.valueOf("cnfigScopeGkey");
    public static final IMetafieldId CNFIG_VALUE = MetafieldIdFactory.valueOf("cnfigValue");
    public static final IMetafieldId CNFIG_LARGE_TEXT_VALUE = MetafieldIdFactory.valueOf("cnfigLargeTextValue");
    public static final IMetafieldId CNFIG_CREATED = MetafieldIdFactory.valueOf("cnfigCreated");
    public static final IMetafieldId CNFIG_CREATOR = MetafieldIdFactory.valueOf("cnfigCreator");
    public static final IMetafieldId CNFIG_CHANGED = MetafieldIdFactory.valueOf("cnfigChanged");
    public static final IMetafieldId CNFIG_CHANGER = MetafieldIdFactory.valueOf("cnfigChanger");
    public static final IMetafieldId MFDO_GKEY = MetafieldIdFactory.valueOf("mfdoGkey");
    public static final IMetafieldId MFDO_SCOPE = MetafieldIdFactory.valueOf("mfdoScope");
    public static final IMetafieldId MFDO_SCOPE_GKEY = MetafieldIdFactory.valueOf("mfdoScopeGkey");
    public static final IMetafieldId MFDO_ID = MetafieldIdFactory.valueOf("mfdoId");
    public static final IMetafieldId MFDO_IMPORTANCE = MetafieldIdFactory.valueOf("mfdoImportance");
    public static final IMetafieldId MFDO_SHORT_NAME = MetafieldIdFactory.valueOf("mfdoShortName");
    public static final IMetafieldId MFDO_LONG_NAME = MetafieldIdFactory.valueOf("mfdoLongName");
    public static final IMetafieldId MFDO_HELP_LABEL = MetafieldIdFactory.valueOf("mfdoHelpLabel");
    public static final IMetafieldId MFDO_GROUP_ID = MetafieldIdFactory.valueOf("mfdoGroupId");
    public static final IMetafieldId MFDO_MEASURED_USER_UNIT = MetafieldIdFactory.valueOf("mfdoMeasuredUserUnit");
    public static final IMetafieldId MFDO_MAX_CHARS = MetafieldIdFactory.valueOf("mfdoMaxChars");
    public static final IMetafieldId MFDO_CASE_VALIDATION = MetafieldIdFactory.valueOf("mfdoCaseValidation");
    public static final IMetafieldId MFDO_EXTRA_X_M_L_DEFINITION = MetafieldIdFactory.valueOf("mfdoExtraXMLDefinition");
    public static final IMetafieldId MFDO_WIDGET_TYPE = MetafieldIdFactory.valueOf("mfdoWidgetType");
    public static final IMetafieldId MFDO_CHOICE_LIST = MetafieldIdFactory.valueOf("mfdoChoiceList");
    public static final IMetafieldId MFDO_COMMENT = MetafieldIdFactory.valueOf("mfdoComment");
    public static final IMetafieldId MFDCH_GKEY = MetafieldIdFactory.valueOf("mfdchGkey");
    public static final IMetafieldId MFDCH_METAFIELD = MetafieldIdFactory.valueOf("mfdchMetafield");
    public static final IMetafieldId MFDCH_VALUE = MetafieldIdFactory.valueOf("mfdchValue");
    public static final IMetafieldId MFDCH_DESCRIPTION = MetafieldIdFactory.valueOf("mfdchDescription");
    public static final IMetafieldId CFGVAR_GKEY = MetafieldIdFactory.valueOf("cfgvarGkey");
    public static final IMetafieldId CFGVAR_NAME = MetafieldIdFactory.valueOf("cfgvarName");
    public static final IMetafieldId CFGVAR_DESCRIPTION = MetafieldIdFactory.valueOf("cfgvarDescription");
    public static final IMetafieldId CFGVAR_ENABLED = MetafieldIdFactory.valueOf("cfgvarEnabled");
    public static final IMetafieldId CFGVAR_VARIFORM_XML = MetafieldIdFactory.valueOf("cfgvarVariformXml");
    public static final IMetafieldId CFGVAR_VARIFORM_IDS = MetafieldIdFactory.valueOf("cfgvarVariformIds");
    public static final IMetafieldId CFGVAR_SCOPE_LEVEL = MetafieldIdFactory.valueOf("cfgvarScopeLevel");
    public static final IMetafieldId CFGVAR_SCOPE_GKEY = MetafieldIdFactory.valueOf("cfgvarScopeGkey");
    public static final IMetafieldId CFGVAR_CREATED = MetafieldIdFactory.valueOf("cfgvarCreated");
    public static final IMetafieldId CFGVAR_CREATOR = MetafieldIdFactory.valueOf("cfgvarCreator");
    public static final IMetafieldId CFGVAR_CHANGED = MetafieldIdFactory.valueOf("cfgvarChanged");
    public static final IMetafieldId CFGVAR_CHANGER = MetafieldIdFactory.valueOf("cfgvarChanger");
    public static final IMetafieldId MODVER_GKEY = MetafieldIdFactory.valueOf("modverGkey");
    public static final IMetafieldId MODVER_MODULE_NAME = MetafieldIdFactory.valueOf("modverModuleName");
    public static final IMetafieldId MODVER_DB_VERSION_NUMBER = MetafieldIdFactory.valueOf("modverDbVersionNumber");
    public static final IMetafieldId MODVER_CHANGED = MetafieldIdFactory.valueOf("modverChanged");
    public static final IMetafieldId MODVER_HISTORY = MetafieldIdFactory.valueOf("modverHistory");
    public static final IMetafieldId UP_ACT_GKEY = MetafieldIdFactory.valueOf("upActGkey");
    public static final IMetafieldId UPGRADE_ACTION_CLASS_NAME = MetafieldIdFactory.valueOf("upgradeActionClassName");
    public static final IMetafieldId UPGRADE_ACTION_SUCCEDED = MetafieldIdFactory.valueOf("upgradeActionSucceded");
    public static final IMetafieldId UPGRADE_ACTION_MODULE_VERSION = MetafieldIdFactory.valueOf("upgradeActionModuleVersion");
    public static final IMetafieldId UPGRADE_ACTION_POSITION = MetafieldIdFactory.valueOf("upgradeActionPosition");
    public static final IMetafieldId UPGRADE_ACTION_CREATED = MetafieldIdFactory.valueOf("upgradeActionCreated");
    public static final IMetafieldId UPGRADE_ACTION_DURATION = MetafieldIdFactory.valueOf("upgradeActionDuration");
    public static final IMetafieldId UPGRADE_ACTION_MESSAGE = MetafieldIdFactory.valueOf("upgradeActionMessage");

}

