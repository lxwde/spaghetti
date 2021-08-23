package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.inventory.UnitField;

public interface IUeFields extends IInvField {
    public static final IMetafieldId UNIT_UE_SET = UC_UNITS;
    public static final IMetafieldId UE_GKEY = UNIT_GKEY;
    public static final IMetafieldId UE_EQ_ROLE = UNIT_EQ_ROLE;
    public static final IMetafieldId UE_UNIT = UNIT_GKEY;
    public static final IMetafieldId UE_EQUIPMENT = UNIT_EQUIPMENT;
    public static final IMetafieldId UE_EQUIPMENT_STATE = UNIT_EQUIPMENT_STATE;
    public static final IMetafieldId UE_CONDITION_I_D = UNIT_CONDITION_I_D;
    public static final IMetafieldId UE_DAMAGE_SEVERITY = UNIT_DAMAGE_SEVERITY;
    public static final IMetafieldId UE_BAD_NBR = UNIT_BAD_NBR;
    public static final IMetafieldId UE_IS_FOLDED = UNIT_IS_FOLDED;
    public static final IMetafieldId UE_SPARCS_DAMAGE_CODE = UNIT_SPARCS_DAMAGE_CODE;
    public static final IMetafieldId UE_DAMAGES = UNIT_DAMAGES;
    public static final IMetafieldId UE_ARRIVAL_ORDER_ITEM = UNIT_ARRIVAL_ORDER_ITEM;
    public static final IMetafieldId UE_DEPARTURE_ORDER_ITEM = UNIT_DEPARTURE_ORDER_ITEM;
    public static final IMetafieldId UE_IS_RESERVED = UNIT_IS_RESERVED;
    public static final IMetafieldId UE_MNR_STATUS = UNIT_MNR_STATUS;
    public static final IMetafieldId UE_PLACARDED = UNIT_PLACARDED;
    public static final IMetafieldId UE_VISIT_STATE = UNIT_VISIT_STATE;
    public static final IMetafieldId UE_UFV = UnitField.UNIT_ACTIVE_UFV;
    public static final IMetafieldId UNIT_PRIMARY_UE = null;
    public static final IMetafieldId UNIT_CARRIAGE_UE = UNIT_CARRIAGE_UNIT;
}
