package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IBinField {

//    public static final IMetafieldId TEST = MetafieldIdFactory.valueOf("vesselClassKey");
//    public static final IMetafieldId VESSEL_TABLE = MetafieldIdFactory.valueOf("VESSEL");
    public static final IMetafieldId BNT_GKEY = MetafieldIdFactory.valueOf((String)"bntGkey");
    public static final IMetafieldId BNT_OWNING_BIN = MetafieldIdFactory.valueOf((String)"bntOwningBin");
    public static final IMetafieldId BNT_TABLE_INDEX = MetafieldIdFactory.valueOf((String)"bntTableIndex");
    public static final IMetafieldId BNT_TABLE_NAME = MetafieldIdFactory.valueOf((String)"bntTableName");
    public static final IMetafieldId BNT_BIN_NAME_SET = MetafieldIdFactory.valueOf((String)"bntBinNameSet");
    public static final IMetafieldId BNT_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"bntLifeCycleState");
    public static final IMetafieldId BNM_GKEY = MetafieldIdFactory.valueOf((String)"bnmGkey");
    public static final IMetafieldId BNM_NAME_TABLE = MetafieldIdFactory.valueOf((String)"bnmNameTable");
    public static final IMetafieldId BNM_LOGICAL_POSITION = MetafieldIdFactory.valueOf((String)"bnmLogicalPosition");
    public static final IMetafieldId BNM_USER_NAME = MetafieldIdFactory.valueOf((String)"bnmUserName");
    public static final IMetafieldId BNM_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"bnmLifeCycleState");
    public static final IMetafieldId ABN_GKEY = MetafieldIdFactory.valueOf((String)"abnGkey");
    public static final IMetafieldId ABN_SUB_TYPE = MetafieldIdFactory.valueOf((String)"abnSubType");
    public static final IMetafieldId ABN_NAME = MetafieldIdFactory.valueOf((String)"abnName");
    public static final IMetafieldId ABN_NAME_ALT = MetafieldIdFactory.valueOf((String)"abnNameAlt");
    public static final IMetafieldId ABN_LONG_NAME = MetafieldIdFactory.valueOf((String)"abnLongName");
    public static final IMetafieldId ABN_PARENT_BIN = MetafieldIdFactory.valueOf((String)"abnParentBin");
    public static final IMetafieldId ABN_ABSTRACT_BIN_SET = MetafieldIdFactory.valueOf((String)"abnAbstractBinSet");
    public static final IMetafieldId ABN_BIN_ANCESTOR_SET = MetafieldIdFactory.valueOf((String)"abnBinAncestorSet");
    public static final IMetafieldId ABN_POLYGON = MetafieldIdFactory.valueOf((String)"abnPolygon");
    public static final IMetafieldId ABN_CENTERLINE = MetafieldIdFactory.valueOf((String)"abnCenterline");
    public static final IMetafieldId ABN_GEODETIC_ANCHOR_LATITUDE = MetafieldIdFactory.valueOf((String)"abnGeodeticAnchorLatitude");
    public static final IMetafieldId ABN_GEODETIC_ANCHOR_LONGITUDE = MetafieldIdFactory.valueOf((String)"abnGeodeticAnchorLongitude");
    public static final IMetafieldId ABN_GEODETIC_ANCHOR_ORIENTATION = MetafieldIdFactory.valueOf((String)"abnGeodeticAnchorOrientation");
    public static final IMetafieldId ABN_BIN_LEVEL = MetafieldIdFactory.valueOf((String)"abnBinLevel");
    public static final IMetafieldId ABN_Z_INDEX_MIN = MetafieldIdFactory.valueOf((String)"abnZIndexMin");
    public static final IMetafieldId ABN_Z_INDEX_MAX = MetafieldIdFactory.valueOf((String)"abnZIndexMax");
    public static final IMetafieldId ABN_VERTICAL_MIN = MetafieldIdFactory.valueOf((String)"abnVerticalMin");
    public static final IMetafieldId ABN_VERTICAL_MAX = MetafieldIdFactory.valueOf((String)"abnVerticalMax");
    public static final IMetafieldId ABN_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"abnLifeCycleState");
    public static final IMetafieldId ABN_BIN_TYPE = MetafieldIdFactory.valueOf((String)"abnBinType");
    public static final IMetafieldId ABN_BIN_NAME_TABLE_SET = MetafieldIdFactory.valueOf((String)"abnBinNameTableSet");
    public static final IMetafieldId ABN_CREATED = MetafieldIdFactory.valueOf((String)"abnCreated");
    public static final IMetafieldId ABN_CREATOR = MetafieldIdFactory.valueOf((String)"abnCreator");
    public static final IMetafieldId ABN_CHANGED = MetafieldIdFactory.valueOf((String)"abnChanged");
    public static final IMetafieldId ABN_CHANGER = MetafieldIdFactory.valueOf((String)"abnChanger");
    public static final IMetafieldId BAN_GKEY = MetafieldIdFactory.valueOf((String)"banGkey");
    public static final IMetafieldId BAN_BIN = MetafieldIdFactory.valueOf((String)"banBin");
    public static final IMetafieldId BAN_BIN_LEVEL = MetafieldIdFactory.valueOf((String)"banBinLevel");
    public static final IMetafieldId BAN_ANCESTOR_LEVEL = MetafieldIdFactory.valueOf((String)"banAncestorLevel");
    public static final IMetafieldId BAN_ANCESTOR_BIN = MetafieldIdFactory.valueOf((String)"banAncestorBin");
    public static final IMetafieldId BAN_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"banLifeCycleState");
    public static final IMetafieldId BCX_GKEY = MetafieldIdFactory.valueOf((String)"bcxGkey");
    public static final IMetafieldId BCX_ID = MetafieldIdFactory.valueOf((String)"bcxId");
    public static final IMetafieldId BCX_DESCRIPTION = MetafieldIdFactory.valueOf((String)"bcxDescription");
    public static final IMetafieldId BCX_SYSTEM_DEFINED = MetafieldIdFactory.valueOf((String)"bcxSystemDefined");
    public static final IMetafieldId BTP_GKEY = MetafieldIdFactory.valueOf((String)"btpGkey");
    public static final IMetafieldId BTP_ID = MetafieldIdFactory.valueOf((String)"btpId");
    public static final IMetafieldId BTP_DESCRIPTION = MetafieldIdFactory.valueOf((String)"btpDescription");
    public static final IMetafieldId BTP_SYSTEM_DEFINED = MetafieldIdFactory.valueOf((String)"btpSystemDefined");
    public static final IMetafieldId BTP_LEVEL_RESTRICTION = MetafieldIdFactory.valueOf((String)"btpLevelRestriction");
    public static final IMetafieldId BTP_CONTEXT = MetafieldIdFactory.valueOf((String)"btpContext");

}
