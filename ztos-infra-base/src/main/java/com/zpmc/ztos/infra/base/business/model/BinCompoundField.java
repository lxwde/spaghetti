package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IBinField;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class BinCompoundField {

    public static final IMetafieldId ABN_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.ABN_PARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId ABN_GREAT_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)ABN_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId ABN_GREAT_GRANDPARENT_BIN_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)ABN_GREAT_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_GKEY);
    public static final IMetafieldId ABN_GREAT_GREAT_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)ABN_GREAT_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId ABN_BIN_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.ABN_BIN_TYPE, (IMetafieldId)IBinField.BTP_ID);
    public static final IMetafieldId ABN_BIN_TYPE_LEVEL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.ABN_BIN_TYPE, (IMetafieldId)IBinField.BTP_LEVEL_RESTRICTION);
    public static final IMetafieldId ABN_CONTEXT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.ABN_BIN_TYPE, (IMetafieldId)IBinField.BTP_CONTEXT);
    public static final IMetafieldId ABN_PARENT_BIN_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.ABN_PARENT_BIN, (IMetafieldId)IBinField.ABN_NAME);
    public static final IMetafieldId ABN_GRANDPARENT_BIN_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)ABN_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_NAME);
    public static final IMetafieldId BAN_PARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BAN_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId BAN_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)BAN_PARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId BAN_GREAT_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)BAN_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId BAN_GREAT_GREAT_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)BAN_GREAT_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId BNM_OWNING_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BNM_NAME_TABLE, (IMetafieldId)IBinField.BNT_OWNING_BIN);
    public static final IMetafieldId BNT_OWNING_BIN_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BNT_OWNING_BIN, (IMetafieldId)IBinField.ABN_NAME);
    public static final IMetafieldId BNM_NAME_TABLE_INDEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BNM_NAME_TABLE, (IMetafieldId)IBinField.BNT_TABLE_INDEX);
    public static final IMetafieldId BNM_NAME_TABLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BNM_NAME_TABLE, (IMetafieldId)IBinField.BNT_TABLE_NAME);
    public static final IMetafieldId BAN_BIN_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BAN_BIN, (IMetafieldId)IBinField.ABN_NAME);
    public static final IMetafieldId BAN_BIN_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.BAN_BIN, (IMetafieldId)IBinField.ABN_GKEY);
    public static final IMetafieldId ABN_GRANDPARENT_BIN_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)ABN_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_GKEY);
    public static final IMetafieldId ABN_PARENT_BIN_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IBinField.ABN_PARENT_BIN, (IMetafieldId)IBinField.ABN_GKEY);

}
