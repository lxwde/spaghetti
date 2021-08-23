package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.inventory.UnitVisitStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.GoodsBase;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;

public class UnitField implements IInventoryField {
    public static final IMetafieldId POINT_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.POINT_UN_LOC, (IMetafieldId) IArgoRefField.UNLOC_PLACE_NAME);
    public static final IMetafieldId EQ_BASIC_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_BASIC_LENGTH);
    public static final IMetafieldId EQ_EQTYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_ID);
    public static final IMetafieldId EQ_EQTYPE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_CLASS);
    public static final IMetafieldId EQ_EQTYPE_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_ISO_GROUP);
    public static final IMetafieldId EQ_EQTYPE_HEIGHT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_NOMINAL_HEIGHT);
    public static final IMetafieldId EQ_NOMINAL_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_NOMINAL_LENGTH);
    public static final IMetafieldId EQ_RFR_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_RFR_TYPE);
    public static final IMetafieldId EQ_EQTYPE_IS_SUPER_FREEZE_REEFER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_IS_SUPER_FREEZE_REEFER);
    public static final IMetafieldId EQ_EQTYPE_IS_TEMPERATURE_CONTROLLED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_IS_TEMPERATURE_CONTROLLED);
    public static final IMetafieldId EQ_EQTYPE_IS_CONTROLLED_ATMOSPHERE_REEFER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_IS_CONTROLLED_ATMOSPHERE_REEFER);
    public static final IMetafieldId EQ_EQTYPE_PICT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_PICT_ID);
    public static final IMetafieldId EQ_EQTYPE_IS_CHASSIS_BOMB_CART = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_IS_CHASSIS_BOMB_CART);
    public static final IMetafieldId EQ_EQTYPE_IS_CHASSIS_CASSETTE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE, (IMetafieldId) IArgoRefField.EQTYP_IS_CHASSIS_CASSETTE);
    public static final IMetafieldId EQS_EQ_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_GKEY);
    public static final IMetafieldId EQS_EQ_ID_FULL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_FULL);
    public static final IMetafieldId EQS_EQ_ID_NO_CHECK_DIGIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_NO_CHECK_DIGIT);
    public static final IMetafieldId EQS_EQ_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_CLASS);
    public static final IMetafieldId EQS_EQ_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE);
    public static final IMetafieldId EQS_EQ_EQTYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_ID);
    public static final IMetafieldId EQS_EQ_EQTYPE_PICT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_PICT_ID);
    public static final IMetafieldId EQS_EQ_EQTYPE_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_ISO_GROUP);
    public static final IMetafieldId EQS_EQ_EQTYPE_HEIGHT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_HEIGHT);
    public static final IMetafieldId EQS_EQ_NOMINAL_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_NOMINAL_LENGTH);
    public static final IMetafieldId EQS_EQ_RFR_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId)EQ_RFR_TYPE);
    public static final IMetafieldId EQS_EQ_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQ_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId EQS_EQ_OWNER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQ_OWNER, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId EQS_EQ_PREVIOUS_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQ_PREVIOUS_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId EQS_EQ_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_GRADE_I_D, (IMetafieldId) IArgoRefField.EQGRD_ID);
    public static final IMetafieldId EQS_EQ_SCOPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_SCOPE);
    public static final IMetafieldId EQS_EQ_DATA_SOURCE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_DATA_SOURCE);
    public static final IMetafieldId EQS_EQ_LENGTH_MM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_LENGTH_MM);
    public static final IMetafieldId EQS_EQ_HEIGHT_MM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_HEIGHT_MM);
    public static final IMetafieldId EQS_EQ_WIDTH_MM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_WIDTH_MM);
    public static final IMetafieldId EQS_EQ_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ISO_GROUP);
    public static final IMetafieldId EQS_EQ_TARE_WEIGHT_KG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_TARE_WEIGHT_KG);
    public static final IMetafieldId EQS_EQ_SAFE_WEIGHT_KG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_SAFE_WEIGHT_KG);
    public static final IMetafieldId EQS_EQ_LEASE_EXPIRATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_LEASE_EXPIRATION);
    public static final IMetafieldId EQS_EQ_BUILD_DATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_BUILD_DATE);
    public static final IMetafieldId EQS_EQ_NO_STOW_ON_TOP_IF_EMPTY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_NO_STOW_ON_TOP_IF_EMPTY);
    public static final IMetafieldId EQS_EQ_NO_STOW_ON_TOP_IF_LADEN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_NO_STOW_ON_TOP_IF_LADEN);
    public static final IMetafieldId EQS_EQ_MUST_STOW_BELOW_DECK = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_MUST_STOW_BELOW_DECK);
    public static final IMetafieldId EQS_EQ_MUST_STOW_ABOVE_DECK = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_MUST_STOW_ABOVE_DECK);
    public static final IMetafieldId EQS_EQ_IS_P_O_S = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_IS_P_O_S);
    public static final IMetafieldId EQS_EQ_IS_INSULATED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_IS_INSULATED);
    public static final IMetafieldId EQS_EQ_MATERIAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_MATERIAL);
    public static final IMetafieldId EQS_EQ_TRANSPONDER_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_TRANSPONDER_TYPE);
    public static final IMetafieldId EQS_EQ_TRANSPONDER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_TRANSPONDER_ID);
    public static final IMetafieldId EQS_EQ_TIME_TRANSPONDER_INSTALL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_TIME_TRANSPONDER_INSTALL);
    public static final IMetafieldId EQS_EQ_USES_ACCESSORIES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_USES_ACCESSORIES);
    public static final IMetafieldId EQS_EQ_IS_TEMPERATURE_CONTROLLED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_IS_TEMPERATURE_CONTROLLED);
    public static final IMetafieldId EQS_EQ_OOG_OK = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_OOG_OK);
    public static final IMetafieldId EQS_EQ_IS_UNSEALABLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_IS_UNSEALABLE);
    public static final IMetafieldId EQS_EQ_HAS_WHEELS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_HAS_WHEELS);
    public static final IMetafieldId EQS_EQ_IS_OPEN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_IS_OPEN);
    public static final IMetafieldId EQS_EQ_STRENGTH_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EQS_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_STRENGTH_CODE);
    public static final IMetafieldId UFV_UNIT_EQS_EQTYP_PICT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT_PRIMARY_EQS, (IMetafieldId)EQS_EQ_EQTYPE_PICT_ID);
    public static final IMetafieldId UFV_UNIT_EQS_EQ_USES_ACCESSORIES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT_PRIMARY_EQS, (IMetafieldId)EQS_EQ_USES_ACCESSORIES);
    public static final IMetafieldId UNIT_CARRIAGE_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIAGE_UE, (IMetafieldId)UE_EQUIPMENT_STATE);
    public static final IMetafieldId UNIT_CARRIAGE_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIAGE_UE, (IMetafieldId)UE_EQUIPMENT);
    public static final IMetafieldId UNIT_CARRIAGE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIAGE_EQ, (IMetafieldId) IArgoRefField.EQ_ID_FULL);
    public static final IMetafieldId UNIT_MNR_STATUS_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_MNR_STATUS, (IMetafieldId) IArgoRefField.MNRSTAT_ID);
    public static final IMetafieldId UNIT_HAZARDS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_HAZARDS);
    public static final IMetafieldId UNIT_HAZARDS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_HAZARDS, (IMetafieldId)HZRD_GKEY);
    public static final IMetafieldId UNIT_ORIGIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_ORIGIN);
    public static final IMetafieldId UNIT_DESTIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_DESTINATION);
    public static final IMetafieldId GDS_COMMODITY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_COMMODITY, (IMetafieldId) IArgoRefField.CMDY_ID);
    public static final IMetafieldId UNIT_CMDTY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_COMMODITY);
    public static final IMetafieldId UNIT_CMDY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CMDTY, (IMetafieldId) IArgoRefField.CMDY_ID);
    public static final IMetafieldId UNIT_DECLARED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_DECLARED_CV);
    public static final IMetafieldId UNIT_DECLARED_OB_CV_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV, (IMetafieldId) IArgoField.CV_ID);
    public static final IMetafieldId UNIT_CARRIER_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_CARRIER_SERVICE);
    public static final IMetafieldId UNIT_LINE_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId TBDU_LINE_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)TBDU_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UNIT_LINE_OPERATOR_SCAC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_SCAC);
    public static final IMetafieldId UNIT_LINE_OPERATOR_BIC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_BIC);
    public static final IMetafieldId UNIT_LINE_OPERATOR_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_GKEY);
    public static final IMetafieldId UNIT_LINE_OPERATOR_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_LINE_OPERATOR, (IMetafieldId) IArgoRefField.BZU_NAME);
    public static final IMetafieldId UNIT_DECLARED_IB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UNIT_DECLARED_IB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UNIT_DECLARED_IB_ETA = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UNIT_DECLARED_OB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UNIT_DECLARED_OB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UNIT_DECLARED_OB_VES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CVD, (IMetafieldId)MetafieldIdFactory.valueOf((String)"vvdVessel"));
    public static final IMetafieldId UNIT_CARRIER_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIER_SERVICE, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UNIT_DECLARED_IB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UNIT_DECLARED_OB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UNIT_SPECIAL_STOW_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_SPECIAL_STOW, (IMetafieldId) IArgoRefField.STW_ID);
    public static final IMetafieldId UNIT_SPECIAL_STOW_ID2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_SPECIAL_STOW2, (IMetafieldId) IArgoRefField.STW_ID);
    public static final IMetafieldId UNIT_SPECIAL_STOW_ID3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_SPECIAL_STOW3, (IMetafieldId) IArgoRefField.STW_ID);
    public static final IMetafieldId UNIT_GDS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_GKEY);
    public static final IMetafieldId UNIT_GDS_REEFER_RQMNTS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_REEFER_RQMNTS);
    public static final IMetafieldId GDS_REEFER_RQMNTS_TEMP_REQUIRED_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId UNIT_TEMP_REQUIRED_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId UNIT_GDS_RFREQ_VENT_REQUIRED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_VENT_REQUIRED);
    public static final IMetafieldId UNIT_GDS_RFREQ_VENT_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_VENT_UNIT);
    public static final IMetafieldId UNIT_GDS_RFREQ_HUMIDITY_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_HUMIDITY_PCT);
    public static final IMetafieldId UNIT_GDS_RFREQ_O2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_O2_PCT);
    public static final IMetafieldId UNIT_GDS_RFREQ_CO2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_C_O2_PCT);
    public static final IMetafieldId UNIT_GDS_RFREQ_TEMP_SET_POINT_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId UNIT_GDS_RFREQ_TEMP_LIMIT_MIN_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_LIMIT_MIN_C);
    public static final IMetafieldId UNIT_GDS_RFREQ_TEMP_REQUIRED_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId UNIT_GDS_RFREQ_TEMP_LIMIT_MAX_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_LIMIT_MAX_C);
    public static final IMetafieldId UNIT_GDS_RFREQ_TEMP_SHOW_FAHRENHEIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_SHOW_FAHRENHEIT);
    public static final IMetafieldId UNIT_GDS_TEMP_SET_POINT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryBizMetafield.GDS_TEMP_REQUIRED);
    public static final IMetafieldId UNIT_GDS_TEMP_LIMIT_MIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId) IInventoryBizMetafield.GDS_TEMP_LIMIT_MIN);
    public static final IMetafieldId UNIT_GDS_TEMP_LIMIT_MAX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryBizMetafield.GDS_TEMP_LIMIT_MAX);
    public static final IMetafieldId UNIT_GDS_RFREQ_LATEST_ON_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_LATEST_ON_POWER_TIME);
    public static final IMetafieldId UNIT_GDS_RFREQ_REQUESTED_OFF_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_REQUESTED_OFF_POWER_TIME);
    public static final IMetafieldId UNIT_GDS_RFREQ_TIME_MONITOR1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TIME_MONITOR1);
    public static final IMetafieldId UNIT_GDS_RFREQ_TIME_MONITOR2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TIME_MONITOR2);
    public static final IMetafieldId UNIT_GDS_RFREQ_TIME_MONITOR3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TIME_MONITOR3);
    public static final IMetafieldId UNIT_GDS_RFREQ_TIME_MONITOR4 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TIME_MONITOR4);
    public static final IMetafieldId UNIT_GDS_RFREQ_EXTENDED_TIME_MONITORS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_EXTENDED_TIME_MONITORS);
    public static final IMetafieldId UFV_GDS_RFREQ_EXTENDED_TIME_MONITORS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_EXTENDED_TIME_MONITORS);
    public static final IMetafieldId UFV_GDS_RFREQ_MINUTES_BEFORE_UNPLUG_WARNING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_MINUTES_BEFORE_UNPLUG_WARNING));
    public static final IMetafieldId UNIT_GDS_RFREQ_MINUTES_BEFORE_UNPLUG_WARNING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_MINUTES_BEFORE_UNPLUG_WARNING);
    public static final IMetafieldId UNIT_GDS_IS_HAZARDOUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryField.GDS_IS_HAZARDOUS);
    public static final IMetafieldId UNIT_DECLARED_IB_CV_DETAIL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UNIT_DECLARED_IB_CV_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV_DETAIL, (IMetafieldId)IArgoField.CVD_SERVICE);
    public static final IMetafieldId UNIT_DECLARED_OB_CV_DETAIL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UNIT_DECLARED_OB_CV_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_OB_CV_DETAIL, (IMetafieldId)IArgoField.CVD_SERVICE);
    public static final IMetafieldId UNIT_REFRECORD_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_TIME);
    public static final IMetafieldId UNIT_REFRECORD_RETURN_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_RETURN_TMP);
    public static final IMetafieldId UNIT_REFRECORD_VENT_SETTING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_VENT_SETTING);
    public static final IMetafieldId UNIT_REFRECORD_VENT_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_VENT_UNIT);
    public static final IMetafieldId UNIT_REFRECORD_HMDTY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_HMDTY);
    public static final IMetafieldId UNIT_REFRECORD_O2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_O2);
    public static final IMetafieldId UNIT_REFRECORD_CO2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_C_O2);
    public static final IMetafieldId UNIT_REFRECORD_IS_POWERED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_IS_POWERED);
    public static final IMetafieldId UNIT_REFRECORD_IS_BULB_ON = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_IS_BULB_ON);
    public static final IMetafieldId UNIT_REFRECORD_DEFROST_INTERVAL_HOURS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_DEFROST_INTERVAL_HOURS);
    public static final IMetafieldId UNIT_REFRECORD_DRAINS_OPEN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_ARE_DRAINS_OPEN);
    public static final IMetafieldId UNIT_REFRECORD_FAN_SETTING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_FAN_SETTING);
    public static final IMetafieldId UNIT_REFRECORD_MIN_MONITORED_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_MIN_MONITORED_TMP);
    public static final IMetafieldId UNIT_REFRECORD_MAX_MONITORED_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_MAX_MONITORED_TMP);
    public static final IMetafieldId UNIT_REFRECORD_DEFROST_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_DEFROST_TMP);
    public static final IMetafieldId UNIT_REFRECORD_SET_POINT_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_SET_POINT_TMP);
    public static final IMetafieldId UNIT_REFRECORD_SUPPLY_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_SUPPLY_TMP);
    public static final IMetafieldId UNIT_REFRECORD_FUEL_LEVEL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_FUEL_LEVEL);
    public static final IMetafieldId UNIT_REFRECORD_REMARK = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_REMARK);
    public static final IMetafieldId UNIT_REFRECORD_IS_ALARM_ON = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_IS_ALARM_ON);
    public static final IMetafieldId UNIT_REFRECORD_DATASOURCE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_REEFER_RECORD_SET, (IMetafieldId)RFREC_DATA_SOURCE);
    public static final IMetafieldId UNIT_RTG_OPL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_O_P_L);
    public static final IMetafieldId UNIT_RTG_OPL_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPL, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId UNIT_RTG_OPL_CNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPL_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_CNTRY);
    public static final IMetafieldId UNIT_RTG_POL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_P_O_L);
    public static final IMetafieldId UNIT_RTG_POL_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POL, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId UNIT_RTG_POL_CNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POL_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_CNTRY);
    public static final IMetafieldId UNIT_RTG_POD1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_P_O_D1);
    public static final IMetafieldId UNIT_RTG_POD1_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD1, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId UNIT_RTG_POD1_CNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD1_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_CNTRY);
    public static final IMetafieldId UNIT_RTG_POD2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_P_O_D2);
    public static final IMetafieldId UNIT_RTG_POD2_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD2, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId UNIT_RTG_POD2_CNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD2_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_CNTRY);
    public static final IMetafieldId UNIT_RTG_PROJ_POD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_PROJECTED_P_O_D);
    public static final IMetafieldId UNIT_RTG_PROJ_POD_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_PROJ_POD, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId UNIT_RTG_PROJ_PODP_CNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_PROJ_POD_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_CNTRY);
    public static final IMetafieldId UNIT_RTG_OPT1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_O_P_T1);
    public static final IMetafieldId UNIT_RTG_OPT2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_O_P_T2);
    public static final IMetafieldId UNIT_RTG_OPT3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_O_P_T3);
    public static final IMetafieldId UNIT_RTG_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_GROUP);
    public static final IMetafieldId UNIT_RTG_DESCRIPTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_DESCRIPTION);
    public static final IMetafieldId UNIT_RTG_RETURN_TO_LOCATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_RETURN_TO_LOCATION);
    public static final IMetafieldId UNIT_RTG_TRUCKING_COMPANY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_TRUCKING_COMPANY);
    public static final IMetafieldId UNIT_RTG_PIN_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_PIN_NBR);
    public static final IMetafieldId UFV_RTG_RETURN_TO_LOCATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_RETURN_TO_LOCATION);
    public static final IMetafieldId UFV_RTG_PIN_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_PIN_NBR);
    public static final IMetafieldId UNIT_RTG_DECLARED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_DECLARED_CV);
    public static final IMetafieldId UNIT_RTG_DECLARED_OB_CV_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UNIT_RTG_DECLARED_OB_CV_VEHICLE_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_DECLARED_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASS_TYPE);
    public static final IMetafieldId UNIT_RTG_DECLARED_OB_CV_CLASSIFICATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_DECLARED_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VESSEL_CLASSIFICATION);
    public static final IMetafieldId UNIT_RTG_DECLARED_CV_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UNIT_RTG_CARRIER_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_CARRIER_SERVICE);
    public static final IMetafieldId UNIT_RTG_CARRIER_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_CARRIER_SERVICE, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UNIT_OPL_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPL, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_OPL_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OPL_ID);
    public static final IMetafieldId UNIT_OPL_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPL, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_OPL_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPL, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_POL_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POL, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_POL_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POL, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_POL_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POL, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_POD_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD1, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId TBDU_POD1_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)TBDU_P_O_D1, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_POD_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD1, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_POD_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD1, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_POD2_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD2, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_POD2_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD2, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_POD2_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_POD2, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_PROJ_POD_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_PROJ_POD, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_PROJ_POD_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_PROJ_POD, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_PROJ_POD_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_PROJ_POD, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_OPT1_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT1, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_OPT1_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT1, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_OPT1_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT1, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_OPT2_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT2, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_OPT2_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT2, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_OPT2_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT2, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_OPT3_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT3, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UNIT_OPT3_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT3, (IMetafieldId)POINT_NAME);
    public static final IMetafieldId UNIT_OPT3_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_OPT3, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId UNIT_GROUP_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RTG_GROUP, (IMetafieldId) IArgoRefField.GRP_ID);
    public static final IMetafieldId UNIT_GDS_DESTINATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_DESTINATION);
    public static final IMetafieldId UNIT_IMPORT_DELIVERY_ORDER_EXPIRATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_IMPORT_DELIVERY_ORDER, (IMetafieldId)IInventoryField.IDO_EXPIRY_DATE);
    public static final IMetafieldId UFV_DEPARTURE_ORDER_ITEM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DEPARTURE_ORDER_ITEM);
    public static final IMetafieldId UNIT_DEPARTURE_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DEPARTURE_ORDER_ITEM, (IMetafieldId)EQBOI_ORDER);
    public static final IMetafieldId UNIT_DEPARTURE_ORDER_SUB_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DEPARTURE_ORDER, (IMetafieldId)EQBO_SUB_TYPE);
    public static final IMetafieldId UNIT_DEPARTURE_ORDER_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DEPARTURE_ORDER, (IMetafieldId)EQBO_NBR);
    public static final IMetafieldId UNIT_ARRIVAL_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ARRIVAL_ORDER_ITEM, (IMetafieldId)EQBOI_ORDER);
    public static final IMetafieldId UNIT_ARRIVAL_ORDER_SUB_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ARRIVAL_ORDER, (IMetafieldId)EQBO_SUB_TYPE);
    public static final IMetafieldId UNIT_ARRIVAL_ORDER_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ARRIVAL_ORDER, (IMetafieldId)EQBO_NBR);
    public static final IMetafieldId UFV_UNIT_DEPARTURE_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DEPARTURE_ORDER);
    public static final IMetafieldId UNIT_CURRENT_UFV_TRANSIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_TRANSIT_STATE);
    public static final IMetafieldId UNIT_CURRENT_UFV_VISIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_VISIT_STATE);
    public static final IMetafieldId UNIT_CURRENT_POSITION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_LAST_KNOWN_POSITION);
    public static final IMetafieldId UNIT_CURRENT_POS_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CURRENT_POSITION, (IMetafieldId)IInventoryField.POS_LOC_TYPE);
    public static final IMetafieldId UFV_CURRENT_POS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CURRENT_POSITION);
    public static final IMetafieldId UFV_CURRENT_POS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CURRENT_POS, (IMetafieldId)POS_LOC_GKEY);
    public static final IMetafieldId UNIT_DOOR_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_DOOR_DIRECTION);
    public static final IMetafieldId UNIT_CURRENT_UFV_ACTUAL_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_ACTUAL_OB_CV);
    public static final IMetafieldId UNIT_CURRENT_UFV_INTENDED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_INTENDED_OB_CV);
    public static final IMetafieldId UNIT_CURRENT_UFV_ACTUAL_OB_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CURRENT_UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UNIT_CURRENT_UFV_ACTUAL_IB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryField.UFV_ACTUAL_IB_CV);
    public static final IMetafieldId UNIT_CURRENT_UFV_ACTUAL_IB_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CURRENT_UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING01 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING01);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING02 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING02);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING03 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING03);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING04 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING04);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING05 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING05);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING06 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING06);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING07 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING07);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING08 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING08);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING09 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING09);
    public static final IMetafieldId UNIT_UFV_FLEX_STRING10 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_STRING10);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE01 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE01);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE02 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE02);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE03 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE03);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE04 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE04);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE05 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE05);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE06 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE06);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE07 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE07);
    public static final IMetafieldId UNIT_UFV_FLEX_DATE08 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_FLEX_DATE08);
    public static final IMetafieldId UNIT_UFV_IS_CALLING_CSI_COUNTRY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)IInventoryBizMetafield.UFV_IS_CALLING_CSI_COUNTRY);
    public static final IMetafieldId UFV_ACTUAL_INBOUND_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_ACTUAL_OUTBOUND_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_INTENDED_OUTBOUND_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_ACTUAL_INBOUND_CV_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_INBOUND_CV_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_ACTUAL_OUTBOUND_CV_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OUTBOUND_CV_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_INTENDED_OUTBOUND_CV_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OUTBOUND_CV_OPERATOR, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_UNIT_VISIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_VISIT_STATE);
    public static final IMetafieldId UFV_UNIT_CATEGORY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CATEGORY);
    public static final IMetafieldId UFV_CURRENT_POSITION_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CURRENT_POS_TYPE);
    public static final IMetafieldId UFV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OPERATOR);
    public static final IMetafieldId UFV_LINE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_LINE_OPERATOR);
    public static final IMetafieldId UFV_LINE_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_LINE_OPERATOR_ID);
    public static final IMetafieldId UFV_LINE_OPERATOR_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_LINE_OPERATOR_GKEY);
    public static final IMetafieldId UFV_UNIT_EQ_RFR_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_RFR_TYPE));
    public static final IMetafieldId UFV_CARRIAGE_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CARRIAGE_EQS);
    public static final IMetafieldId UFV_CARRIAGE_EQS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_EQS, (IMetafieldId)EQS_GKEY);
    public static final IMetafieldId UFV_CARRIAGE_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CARRIAGE_UNIT);
    public static final IMetafieldId UFV_CARRIAGE_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CARRIAGE_EQ);
    public static final IMetafieldId UFV_CURRENT_UFV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ACTIVE_UFV);
    public static final IMetafieldId UFV_UNIT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ID);
    public static final IMetafieldId UFV_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQUIPMENT);
    public static final IMetafieldId UFV_EQ_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_EQ, (IMetafieldId) IArgoRefField.EQ_CLASS);
    public static final IMetafieldId UFV_CARRIAGE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_EQ, (IMetafieldId) IArgoRefField.EQ_CLASS);
    public static final IMetafieldId UFV_UNIT_STOP_ROAD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_STOPPED_ROAD);
    public static final IMetafieldId UFV_UNIT_STOP_RAIL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_STOPPED_RAIL);
    public static final IMetafieldId UFV_UNIT_STOP_VESSEL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_STOPPED_VESSEL);
    public static final IMetafieldId UFV_ROUTING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ROUTING);
    public static final IMetafieldId UFV_UNIT_IMPORT_DELIVERY_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_IMPORT_DELIVERY_ORDER);
    public static final IMetafieldId UFV_UNIT_IMPORT_DELIVERY_ORDER_EXPIRATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_IMPORT_DELIVERY_ORDER, (IMetafieldId)IDO_EXPIRY_DATE);
    public static final IMetafieldId UFV_SPECIAL_STOW = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_SPECIAL_STOW);
    public static final IMetafieldId UFV_IS_HAZARDS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_IS_HAZARDOUS);
    public static final IMetafieldId UFV_GOODS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GOODS);
    public static final IMetafieldId UFV_GOODS_COMMODITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_COMMODITY);
    public static final IMetafieldId UFV_GDS_REEFER_RQMNTS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_REEFER_RQMNTS);
    public static final IMetafieldId UFV_TEMP_REQUIRED_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GDS_REEFER_RQMNTS, (IMetafieldId)RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId GDS_CONSIGNEE_AS_STRING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)IInventoryBizMetafield.GDS_CONSIGNEE_AS_STRING);
    public static final IMetafieldId UFV_GOODS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_GKEY);
    public static final IMetafieldId UFV_CATEGORY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CATEGORY);
    public static final IMetafieldId UFV_FREIGHT_KIND = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FREIGHT_KIND);
    public static final IMetafieldId UFV_REQUIRES_POWER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_REQUIRES_POWER);
    public static final IMetafieldId UFV_GDS_RFREQ_LATEST_ON_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_LATEST_ON_POWER_TIME);
    public static final IMetafieldId UFV_GDS_RFREQ_REQUESTED_OFF_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_REQUESTED_OFF_POWER_TIME);
    public static final IMetafieldId UFV_POWER_RQST_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_POWER_RQST_TIME);
    public static final IMetafieldId UFV_GDS_RFREQ_TIME_MONITOR1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_TIME_MONITOR1);
    public static final IMetafieldId UFV_GDS_RFREQ_TIME_MONITOR2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_TIME_MONITOR2);
    public static final IMetafieldId UFV_GDS_RFREQ_TIME_MONITOR3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_TIME_MONITOR3);
    public static final IMetafieldId UFV_GDS_RFREQ_TIME_MONITOR4 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_TIME_MONITOR4);
    public static final IMetafieldId UFV_IS_POWERED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_IS_POWERED);
    public static final IMetafieldId UFV_WANT_POWERED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_WANT_POWERED);
    public static final IMetafieldId UFV_IS_ALARM_ON = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_IS_ALARM_ON);
    public static final IMetafieldId UFV_DRAY_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_DRAY_STATUS);
    public static final IMetafieldId UFV_IS_OOG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_IS_OOG);
    public static final IMetafieldId UFV_IS_HAZARDOUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)IInventoryField.GDS_IS_HAZARDOUS);
    public static final IMetafieldId UFV_HAZARDS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_HAZARDS);
    public static final IMetafieldId UFV_RFR_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_PRIMARY_RFR_TYPE);
    public static final IMetafieldId UFV_HAZARDS_WORST_FIRE_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_HAZARDS, (IMetafieldId)IInventoryField.HZRD_WORST_FIRE_CODE);
    public static final IMetafieldId UFV_HAZARDS_WORST_FIRE_CODE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_HAZARDS_WORST_FIRE_CODE, (IMetafieldId)IInventoryField.FIRECODE_FIRE_CODE_CLASS);
    public static final IMetafieldId UFV_ORIGIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ORIGIN);
    public static final IMetafieldId UFV_DESTIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DESTIN);
    public static final IMetafieldId UFV_CMDTY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CMDTY);
    public static final IMetafieldId UFV_POD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_POD1);
    public static final IMetafieldId UFV_POD2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_POD2);
    public static final IMetafieldId UFV_PROJ_POD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_PROJ_POD);
    public static final IMetafieldId UFV_OPT1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_OPT1);
    public static final IMetafieldId UFV_OPT2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_OPT2);
    public static final IMetafieldId UFV_OPT3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_OPT3);
    public static final IMetafieldId UFV_POD_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POD, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_POD2_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POD2, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_PROJ_POD_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PROJ_POD, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_OPT1_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_OPT1, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_OPT2_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_OPT2, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_OPT3_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_OPT3, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UFV_DEST = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_DESTINATION);
    public static final IMetafieldId UFV_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_GROUP);
    public static final IMetafieldId UFV_POL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_POL);
    public static final IMetafieldId UFV_OPL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_OPL);
    public static final IMetafieldId UFV_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_LOC_TYPE);
    public static final IMetafieldId UFV_POS_LOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_LOC_ID);
    public static final IMetafieldId UFV_POS_LOC_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_LOC_GKEY);
    public static final IMetafieldId UFV_POS_SLOT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_SLOT);
    public static final IMetafieldId UFV_POS_ORIENTATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_ORIENTATION);
    public static final IMetafieldId UNIT_UFV_POS_ORIENTATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_POS_ORIENTATION);
    public static final IMetafieldId UFV_POS_SLOT_ON_CARRIAGE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_SLOT_ON_CARRIAGE);
    public static final IMetafieldId UFV_POS_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_NAME);
    public static final IMetafieldId UFV_POS_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_BIN);
    public static final IMetafieldId UFV_POS_ANCHOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_ANCHOR);
    public static final IMetafieldId UFV_POS_ORIENTATION_DEGREES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_ORIENTATION_DEGREES);
    public static final IMetafieldId UFV_POS_TIER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LAST_KNOWN_POSITION, (IMetafieldId)POS_TIER);
    public static final IMetafieldId UFV_ARRIVE_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ARRIVE_POSITION, (IMetafieldId)POS_LOC_TYPE);
    public static final IMetafieldId UFV_ARRIVE_POS_LOC_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ARRIVE_POSITION, (IMetafieldId)POS_LOC_GKEY);
    public static final IMetafieldId UFV_ARRIVE_POS_LOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ARRIVE_POSITION, (IMetafieldId)POS_LOC_ID);
    public static final IMetafieldId UFV_ARRIVE_POS_SLOT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ARRIVE_POSITION, (IMetafieldId)POS_SLOT);
    public static final IMetafieldId UFV_CURRENT_POS_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CURRENT_UFV, (IMetafieldId)UFV_POS_NAME);
    public static final IMetafieldId UFV_DECLARED_IB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_IB_CV);
    public static final IMetafieldId UFV_DECLARED_IB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_IB_CVD);
    public static final IMetafieldId UFV_DECLARED_IB_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_IB_CARRIER_MODE);
    public static final IMetafieldId UFV_DECLARED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_OB_CV);
    public static final IMetafieldId UFV_DECLARED_OB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_OB_CV_DETAIL);
    public static final IMetafieldId UFV_DECLARED_OB_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_INTENDED_OB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UFV_INTENDED_OB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UFV_INTENDED_OB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UFV_INTENDED_OB_ETD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoField.CVD_E_T_D);
    public static final IMetafieldId UFV_INTENDED_OB_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_ACTUAL_IB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UFV_ACTUAL_IB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UFV_ACTUAL_IB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UFV_ACTUAL_IB_CVD_CLASS_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_CLASS_ID);
    public static final IMetafieldId UFV_ACTUAL_IB_CVD_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_TYPE_ID);
    public static final IMetafieldId UFV_ACTUAL_IB_CVD_ETA = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_ACTUAL_IB_CV_A_T_A = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_A_T_A);
    public static final IMetafieldId UFV_ACTUAL_IB_CV_A_T_D = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_A_T_D);
    public static final IMetafieldId UFV_ACTUAL_IB_CVD_VEHICLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_VEHICLE_NAME);
    public static final IMetafieldId UFV_ACTUAL_IB_VISIT_CALL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VISIT_CALL_NBR);
    public static final IMetafieldId UFV_ACTUAL_IB_VOY_NBR_OR_TRAIN_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VOY_NBR_OR_TRAIN_ID);
    public static final IMetafieldId UFV_ACTUAL_IB_CARRIER_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_OPERATOR_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_CARRIER_MODE);
    public static final IMetafieldId UFV_ACTUAL_OB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_CVD);
    public static final IMetafieldId UFV_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_COMPLEX);
    public static final IMetafieldId UFV_FACILITY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_FACILITY, (IMetafieldId)IArgoField.FCY_ID);
    public static final IMetafieldId FCY_POINT_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IArgoField.FCY_ROUTING_POINT, (IMetafieldId) IArgoRefField.POINT_TERMINAL);
    public static final IMetafieldId FCY_POINT_UNLOC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IArgoField.FCY_ROUTING_POINT, (IMetafieldId) IArgoRefField.POINT_UN_LOC);
    public static final IMetafieldId FCY_POINT_UNLOC_PLACE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)FCY_POINT_UNLOC, (IMetafieldId) IArgoRefField.UNLOC_PLACE_NAME);
    public static final IMetafieldId UFV_FACILITY_POINT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_FACILITY, (IMetafieldId)IArgoField.FCY_ID);
    public static final IMetafieldId UFV_FACILITY_TERMINAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_FACILITY, (IMetafieldId)FCY_POINT_TERMINAL);
    public static final IMetafieldId UFV_FACILITY_PLACE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_FACILITY, (IMetafieldId)FCY_POINT_UNLOC_PLACE);
    public static final IMetafieldId UFV_TIME_DENORMALIZE_CALC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_TIME_DENORMALIZE_CALC);
    public static final IMetafieldId UFV_TIME_LAST_STATE_CHANGE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_TIME_LAST_STATE_CHANGE);
    public static final IMetafieldId UFV_DEPARTURE_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DEPARTURE_ORDER);
    public static final IMetafieldId UFV_DEPARTURE_ORDER_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DEPARTURE_ORDER_NBR);
    public static final IMetafieldId UFV_ARRIVAL_ORDER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ARRIVAL_ORDER);
    public static final IMetafieldId UFV_ARRIVAL_ORDER_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_ARRIVAL_ORDER_NBR);
    public static final IMetafieldId UNIT_CARRIAGE_EQUIPMENT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIAGE_UNIT, (IMetafieldId)UNIT_EQUIPMENT);
    public static final IMetafieldId UNIT_CARRIAGE_UNIT_EQ_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_CARRIAGE_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_FULL);
    public static final IMetafieldId UNIT_COMBO_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.UNIT_COMBO, (IMetafieldId)IInventoryField.UC_GKEY);
    public static final IMetafieldId UFV_RTG_TRUCKING_COMPANY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_TRUCKING_COMPANY);
    public static final IMetafieldId UFV_SHIPPER_BZU = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_SHIPPER_BZU);
    public static final IMetafieldId UFV_CONSIGNEE_BZU = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_CONSIGNEE_BZU);
    public static final IMetafieldId UFV_UNIT_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GKEY);
    public static final IMetafieldId UFV_UNIT_COMBO_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_COMBO_GKEY);
    public static final IMetafieldId UFV_UNIT_REMARK = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_REMARK);
    public static final IMetafieldId UFV_UNIT_CHS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_CARRIAGE_UNIT_EQ_ID);
    public static final IMetafieldId UFV_UNIT_RTG_DECLARED_OB_CV_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_DECLARED_OB_CV_MODE);
    public static final IMetafieldId UFV_UNIT_GDS_TEMP_SET_POINT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_TEMP_SET_POINT);
    public static final IMetafieldId UFV_UNIT_EQS_EQ_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQS, (IMetafieldId)EQS_EQ_GRADE_ID);
    public static final IMetafieldId UFV_FLEX01 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING01);
    public static final IMetafieldId UFV_FLEX02 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING02);
    public static final IMetafieldId UFV_FLEX03 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING03);
    public static final IMetafieldId UFV_FLEX04 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING04);
    public static final IMetafieldId UFV_FLEX05 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING05);
    public static final IMetafieldId UFV_FLEX06 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING06);
    public static final IMetafieldId UFV_FLEX07 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING07);
    public static final IMetafieldId UFV_FLEX08 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING08);
    public static final IMetafieldId UFV_FLEX09 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING09);
    public static final IMetafieldId UFV_FLEX10 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING10);
    public static final IMetafieldId UFV_FLEX11 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING11);
    public static final IMetafieldId UFV_FLEX12 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING12);
    public static final IMetafieldId UFV_FLEX13 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING13);
    public static final IMetafieldId UFV_FLEX14 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING14);
    public static final IMetafieldId UFV_FLEX15 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_FLEX_STRING15);
    public static final IMetafieldId UFV_ACTUAL_OB_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_GOODS_AND_CTR_WT_KG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GOODS_AND_CTR_WT_KG);
    public static final IMetafieldId UFV_DECLARED_OB_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_OB_CARRIER_MODE);
    public static final IMetafieldId UFV_DECLARED_IB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DECLARED_IB_ID);
    public static final IMetafieldId UFV_IMDG_TYPES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_IMDG_TYPES);
    public static final IMetafieldId UNIT_IMDG_TYPES = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_IMDG_TYPES);
    public static final IMetafieldId UFV_HAZARDS_WORST_FIRECODE_FIRE_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_HAZARDS_WORST_FIRE_CODE, (IMetafieldId)FIRECODE_FIRE_CODE);
    public static final IMetafieldId UNIT_HAZARDY_WORST_FIRECODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_HAZARDS, (IMetafieldId)HZRD_WORST_FIRE_CODE);
    public static final IMetafieldId GDS_HAZARDY_WORST_FIRECODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_HAZARDS, (IMetafieldId)HZRD_WORST_FIRE_CODE);
    public static final IMetafieldId UNIT_HAZARDY_WORST_FIRECODE_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_HAZARDY_WORST_FIRECODE, (IMetafieldId)FIRECODE_FIRE_CODE);
    public static final IMetafieldId GDS_HAZARDY_WORST_FIRECODE_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_HAZARDY_WORST_FIRECODE, (IMetafieldId)FIRECODE_FIRE_CODE);
    public static final IMetafieldId UNIT_HAZARDY_WORST_FIRECODE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_HAZARDY_WORST_FIRECODE, (IMetafieldId)FIRECODE_FIRE_CODE_CLASS);
    public static final IMetafieldId GDS_HAZARDY_WORST_FIRECODE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_HAZARDY_WORST_FIRECODE, (IMetafieldId)FIRECODE_FIRE_CODE_CLASS);
    public static final IMetafieldId UFV_GDS_RFREQ_TEMP_SET_POINT_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_TEMP_SET_POINT_C);
    public static final IMetafieldId UFV_GDS_RFREQ_VENT_REQUIRED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_VENT_REQUIRED);
    public static final IMetafieldId UFV_GDS_RFREQ_VENT_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_VENT_UNIT);
    public static final IMetafieldId UFV_GDS_RFREQ_HUMIDITY_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_HUMIDITY_PCT);
    public static final IMetafieldId UFV_GDS_RFREQ_O2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_O2_PCT);
    public static final IMetafieldId UFV_GDS_RFREQ_CO2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_CO2_PCT);
    public static final IMetafieldId UFV_OOG_BACK_CM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OOG_BACK_CM);
    public static final IMetafieldId UFV_OOG_FRONT_CM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OOG_FRONT_CM);
    public static final IMetafieldId UFV_OOG_LEFT_CM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OOG_LEFT_CM);
    public static final IMetafieldId UFV_OOG_RIGHT_CM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OOG_RIGHT_CM);
    public static final IMetafieldId UFV_OOG_TOP_CM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_OOG_TOP_CM);
    public static final IMetafieldId UFV_GDS_DESTINATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_DESTINATION);
    public static final IMetafieldId UFV_SEAL_NBR1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_SEAL_NBR1);
    public static final IMetafieldId UFV_SEAL_NBR2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_SEAL_NBR2);
    public static final IMetafieldId UFV_SEAL_NBR3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_SEAL_NBR3);
    public static final IMetafieldId UFV_SEAL_NBR4 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_SEAL_NBR4);
    public static final IMetafieldId UFV_POS_PARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POS_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId UFV_POS_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POS_PARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId UFV_POS_GREAT_GRANDPARENT_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POS_GRANDPARENT_BIN, (IMetafieldId)IBinField.ABN_PARENT_BIN);
    public static final IMetafieldId UFV_UNIT_IMPORT_DELIVERY_ORDER_EXPIRY_DATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_IMPORT_DELIVERY_ORDER_EXPIRY_DATE);
    public static final IMetafieldId MVE_FACILITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_FACILITY);
    public static final IMetafieldId MVE_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_COMPLEX);
    public static final IMetafieldId MVE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_OPERATOR);
    public static final IMetafieldId MVE_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_UNIT);
    public static final IMetafieldId MVE_UNIT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)MVE_UNIT, (IMetafieldId)UNIT_ID);
    public static final IMetafieldId MVE_EQUIPMENT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)MVE_UNIT, (IMetafieldId)UNIT_PRIMARY_EQ);
    public static final IMetafieldId MVE_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)MVE_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE);
    public static final IMetafieldId MVE_EQTYPE_NOMINAL_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)MVE_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_NOMINAL_LENGTH);
    public static final IMetafieldId MVE_ACTUAL_IB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_IB_CARRIER_MODE);
    public static final IMetafieldId MVE_ACTUAL_IB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_IB_ID);
    public static final IMetafieldId MVE_ACTUAL_INBOUND_CV_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_INBOUND_CV_OPERATOR_ID);
    public static final IMetafieldId MVE_ACTUAL_OB_CARRIER_MODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_OB_CARRIER_MODE);
    public static final IMetafieldId MVE_ACTUAL_OB_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_OB_ID);
    public static final IMetafieldId MVE_ACTUAL_OUTBOUND_CV_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IServicesMovesField.MVE_UFV, (IMetafieldId)UFV_ACTUAL_OUTBOUND_CV_OPERATOR_ID);
    public static final IMetafieldId UYV_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_UNIT);
    public static final IMetafieldId UYV_UNIT_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_GKEY);
    public static final IMetafieldId UYV_REQUIRES_POWER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_REQUIRES_POWER);
    public static final IMetafieldId UYV_VISIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_VISIT_STATE);
    public static final IMetafieldId UYV_CARRIAGE_UE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_CARRIAGE_UE);
    public static final IMetafieldId UYV_CARRIAGE_UE_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_CARRIAGE_UE, (IMetafieldId)UE_GKEY);
    public static final IMetafieldId UYV_CARRIAGE_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_CARRIAGE_UE, (IMetafieldId)UE_EQUIPMENT);
    public static final IMetafieldId UYV_CATEGORY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_CATEGORY);
    public static final IMetafieldId UYV_GOODS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_GOODS);
    public static final IMetafieldId UYV_REEFER_RQMT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_GOODS, (IMetafieldId)GDS_REEFER_RQMNTS);
    public static final IMetafieldId UYV_HAZARDS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_HAZARDS);
    public static final IMetafieldId UYV_ORIGIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_ORIGIN);
    public static final IMetafieldId UYV_DESTIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_DESTIN);
    public static final IMetafieldId UYV_CMDTY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_CMDTY);
    public static final IMetafieldId UYV_POD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_RTG_POD1);
    public static final IMetafieldId UYV_POD_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_POD, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UYV_POL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_RTG_POL);
    public static final IMetafieldId UYV_POL_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_POL, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId UYV_LAST_KNOWN_POSITION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_LAST_KNOWN_POSITION);
    public static final IMetafieldId UYV_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_POS_LOC_TYPE);
    public static final IMetafieldId UYV_POS_LOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_POS_LOC_ID);
    public static final IMetafieldId UYV_POS_LOC_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_POS_LOC_GKEY);
    public static final IMetafieldId UYV_POS_SLOT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_POS_SLOT);
    public static final IMetafieldId UYV_POS_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_POS_NAME);
    public static final IMetafieldId UYV_FACILITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_FACILITY);
    public static final IMetafieldId UYV_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_COMPLEX);
    public static final IMetafieldId UYV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_OPERATOR);
    public static final IMetafieldId UYV_VISIBLE_IN_SPARCS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)UFV_VISIBLE_IN_SPARCS);
    public static final IMetafieldId GDS_UFV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_ACTIVE_UFV);
    public static final IMetafieldId GDS_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_PRIMARY_EQS);
    public static final IMetafieldId GDS_EQUIPMENT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_PRIMARY_EQ);
    public static final IMetafieldId GDS_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_PRIMARY_EQTYPE);
    public static final IMetafieldId GDS_REEFER_RQMT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_REEFER_RQMNTS);
    public static final IMetafieldId GDS_RFREQ_TEMP_SET_POINT_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TEMP_REQUIRED_C);
    public static final IMetafieldId GDS_RFREQ_TEMP_LIMIT_MAX_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TEMP_LIMIT_MAX_C);
    public static final IMetafieldId GDS_RFREQ_TEMP_LIMIT_MIN_C = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TEMP_LIMIT_MIN_C);
    public static final IMetafieldId GDS_RFREQ_CO2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_C_O2_PCT);
    public static final IMetafieldId GDS_RFREQ_O2_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_O2_PCT);
    public static final IMetafieldId GDS_RFREQ_HUMIDITY_PCT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_HUMIDITY_PCT);
    public static final IMetafieldId GDS_RFREQ_TIME_MONITOR1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TIME_MONITOR1);
    public static final IMetafieldId GDS_RFREQ_TIME_MONITOR2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TIME_MONITOR2);
    public static final IMetafieldId GDS_RFREQ_TIME_MONITOR3 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TIME_MONITOR3);
    public static final IMetafieldId GDS_RFREQ_TIME_MONITOR4 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TIME_MONITOR4);
    public static final IMetafieldId GDS_RFREQ_VENT_REQUIRED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_VENT_REQUIRED);
    public static final IMetafieldId GDS_RFREQ_VENT_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_VENT_UNIT);
    public static final IMetafieldId GDS_RFREQ_REQUESTED_OFF_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_REQUESTED_OFF_POWER_TIME);
    public static final IMetafieldId GDS_RFREQ_LATEST_ON_POWER_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_LATEST_ON_POWER_TIME);
    public static final IMetafieldId GDS_RFREQ_TEMP_SHOW_FAHRENHEIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_REEFER_RQMNTS, (IMetafieldId)IInventoryField.RFREQ_TEMP_SHOW_FAHRENHEIT);
    public static final IMetafieldId GDS_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_OPERATOR);
    public static final IMetafieldId GDS_SHIPPER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_SHIPPER_BZU, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_GDS_SHIPPER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_SHIPPER_ID);
    public static final IMetafieldId GDS_SHIPPER_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_SHIPPER_BZU, (IMetafieldId) IArgoRefField.BZU_NAME);
    public static final IMetafieldId GDS_SHIPPER_CREDIT_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_SHIPPER_BZU, (IMetafieldId) IArgoRefField.BZU_CREDIT_STATUS);
    public static final IMetafieldId GDS_CONSIGNEE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_CONSIGNEE_BZU, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_GDS_CONSIGNEE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)GDS_CONSIGNEE_ID);
    public static final IMetafieldId GDS_CONSIGNEE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_CONSIGNEE_BZU, (IMetafieldId) IArgoRefField.BZU_NAME);
    public static final IMetafieldId GDS_CONSIGNEE_CREDIT_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_CONSIGNEE_BZU, (IMetafieldId) IArgoRefField.BZU_CREDIT_STATUS);
    public static final IMetafieldId GDSDOC_GOODS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDSDOC_GOODS_DECLARATION, (IMetafieldId)GDSDECL_GOODS);
    public static final IMetafieldId UE_DECLARED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UE_UNIT, (IMetafieldId)UNIT_DECLARED_OB_CV);
    public static final IMetafieldId UNIT_GDS_ORIGIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_ORIGIN);
    public static final IMetafieldId UNIT_GDS_CONSIGNEE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_CONSIGNEE_ID);
    public static final IMetafieldId UNIT_GDS_CONSIGNEE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_CONSIGNEE_NAME);
    public static final IMetafieldId UNIT_GDS_CONSIGNEE_AS_STRING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryBizMetafield.GDS_CONSIGNEE_AS_STRING);
    public static final IMetafieldId UNIT_GDS_SHIPPER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_SHIPPER_ID);
    public static final IMetafieldId UNIT_GDS_SHIPPER_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_SHIPPER_NAME);
    public static final IMetafieldId UNIT_GDS_SHIPPER_AS_STRING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryBizMetafield.GDS_SHIPPER_AS_STRING);
    public static final IMetafieldId UNIT_GDS_BL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)GDS_BL_NBR);
    public static final IMetafieldId UNIT_GDS_MOST_SEVERE_HAZARD_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)IInventoryBizMetafield.GOODS_MOST_SEVERE_HAZARD_CLASS);
    public static final IMetafieldId UFV_GDS_SHIPPER_AS_STRING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_SHIPPER_AS_STRING);
    public static final IMetafieldId UFV_GDS_CONSIGNEE_AS_STRING = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_CONSIGNEE_AS_STRING);
    public static final IMetafieldId UFV_GDS_ORIGIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_ORIGIN);
    public static final IMetafieldId UFV_IB_DISCH_COMPLETE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoField.CVD_TIME_DISCHARGE_COMPLETE);
    public static final IMetafieldId UFV_IB_FIRST_FREE_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoField.CVD_INBOUND_FIRST_FREE_DAY);
    public static final IMetafieldId UFV_IB_TIME_FIRST_AVAILABILITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoField.CVD_TIME_FIRST_AVAILABILITY);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_TIME_FIRST_AVAILABILITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoField.CVD_TIME_FIRST_AVAILABILITY);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_TIME_DISCHARGE_COMPLETE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoField.CVD_TIME_DISCHARGE_COMPLETE);
    public static final IMetafieldId UFV_DECLARED_IB_FIRST_FREE_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoField.CVD_INBOUND_FIRST_FREE_DAY);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_CLASS_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_CLASS_ID);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_TYPE_ID);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_ETA = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_DECLARED_IB_CVD_VEHICLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_VEHICLE_NAME);
    public static final IMetafieldId UFV_DECLARED_IB_VISIT_CALL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VISIT_CALL_NBR);
    public static final IMetafieldId UFV_DELCARED_IB_VOY_NBR_OR_TRAIN_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VOY_NBR_OR_TRAIN_ID);
    public static final IMetafieldId UFV_DELCARED_IB_CARRIER_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_DECLARED_IB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_OPERATOR_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CARRIER_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoBizMetafield.CARRIER_OPERATOR_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_ETA = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_A_T_A = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_ACTUAL_OB_CV_A_T_D = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_A_T_D);
    public static final IMetafieldId UFV_ACTUAL_OB_CV_A_T_A = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_A_T_A);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_CLASS_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_CLASS_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_VEHICLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_VEHICLE_NAME);
    public static final IMetafieldId UFV_ACTUAL_OB_VISIT_CALL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VISIT_CALL_NBR);
    public static final IMetafieldId UFV_ACTUAL_OB_VOY_NBR_OR_TRAIN_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_OB_VOY_NBR_OR_TRAIN_ID);
    public static final IMetafieldId UFV_OB_CVD_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoField.CVD_SERVICE);
    public static final IMetafieldId UFV_INTENDED_CVD_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoField.CVD_SERVICE);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_OB_CVD_SERVICE, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UFV_ACTUAL_OB_CVD_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_TYPE_ID);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_CLASS_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_CLASS_ID);
    public static final IMetafieldId UFV_INTENDED_OB_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_OPERATOR_ID);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_ETA = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_A_T_A = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoField.CVD_E_T_A);
    public static final IMetafieldId UFV_INTENDED_OB_CV_A_T_D = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_A_T_D);
    public static final IMetafieldId UFV_INTENDED_OB_CV_A_T_A = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CV, (IMetafieldId)IArgoField.CV_A_T_A);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_CVD_SERVICE, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_TYPE_ID);
    public static final IMetafieldId UFV_INTENDED_OB_CVD_VEHICLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_VEHICLE_NAME);
    public static final IMetafieldId UFV_INTENDED_OB_VISIT_CALL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_IB_VISIT_CALL_NBR);
    public static final IMetafieldId UFV_INTENDED_OB_VOY_NBR_OR_TRAIN_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_INTENDED_OB_CVD, (IMetafieldId)IArgoBizMetafield.CARRIER_OB_VOY_NBR_OR_TRAIN_ID);
    public static final IMetafieldId UFV_IB_CVD_SERVICE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CVD, (IMetafieldId)IArgoField.CVD_SERVICE);
    public static final IMetafieldId UFV_IB_CVD_SERVICE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_IB_CVD_SERVICE, (IMetafieldId) IArgoRefField.SRVC_ID);
    public static final IMetafieldId UFV_GDS_BL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_BL_NBR);
    public static final IMetafieldId UFV_UNIT_GROUP_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GROUP_ID);
    public static final IMetafieldId WI_UFV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_UYV, (IMetafieldId)UYV_UFV);
    public static final IMetafieldId WI_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_UNIT);
    public static final IMetafieldId WI_UNIT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)UNIT_ID);
    public static final IMetafieldId WI_UNIT_LINE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)UNIT_LINE_OPERATOR);
    public static final IMetafieldId WI_VISIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)UNIT_VISIT_STATE);
    public static final IMetafieldId WI_YARD = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_UYV, (IMetafieldId)UYV_YARD);
    public static final IMetafieldId WI_FACILITY = IMovesField.WI_FACILITY;
    public static final IMetafieldId WI_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_FACILITY, (IMetafieldId)IArgoField.FCY_COMPLEX);
    public static final IMetafieldId WI_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_COMPLEX, (IMetafieldId)IArgoField.CPX_OPERATOR);
    public static final IMetafieldId WI_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)UNIT_PRIMARY_EQS);
    public static final IMetafieldId WI_EQUIPMENT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UE, (IMetafieldId)UE_EQUIPMENT);
    public static final IMetafieldId WI_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE);
    public static final IMetafieldId WI_EQTYPE_NOMINAL_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_NOMINAL_LENGTH);
    public static final IMetafieldId WI_EQTYPE_BASIC_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_BASIC_LENGTH);
    public static final IMetafieldId WI_EQ_LENGTH_MM = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_LENGTH_MM);
    public static final IMetafieldId WI_GOODS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)UNIT_GOODS);
    public static final IMetafieldId WI_VISIBLE_IN_SPARCS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_VISIBLE_IN_SPARCS);
    public static final IMetafieldId WI_UFV_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_GKEY);
    public static final IMetafieldId WI_UFV_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_LOC_TYPE);
    public static final IMetafieldId WI_UFV_POS_LOC_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_LOC_GKEY);
    public static final IMetafieldId WI_UFV_POS_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_BIN);
    public static final IMetafieldId WI_UFV_DOOR_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_DOOR_DIRECTION);
    public static final IMetafieldId WI_UFV_POS_TIER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_TIER);
    public static final IMetafieldId WI_UFV_POS_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_NAME);
    public static final IMetafieldId WI_UFV_POS_SLOT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_SLOT);
    public static final IMetafieldId WI_UFV_POS_SLOT_ON_CARRIAGE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_POS_SLOT_ON_CARRIAGE);
    public static final IMetafieldId WI_POS_LOC_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_LOC_TYPE);
    public static final IMetafieldId WI_POS_LOC_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_LOC_GKEY);
    public static final IMetafieldId WI_POS_SLOT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_SLOT);
    public static final IMetafieldId WI_POS_TIER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_TIER);
    public static final IMetafieldId WI_POS_SLOT_ON_CARRIAGE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_SLOT_ON_CARRIAGE);
    public static final IMetafieldId WI_POS_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_NAME);
    public static final IMetafieldId WI_POS_BIN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_POSITION, (IMetafieldId)POS_BIN);
    public static final IMetafieldId WI_UFV_INTENDED_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_INTENDED_OB_CV);
    public static final IMetafieldId WI_UFV_ACTUAL_OB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_ACTUAL_OB_CV);
    public static final IMetafieldId WI_UFV_ACTUAL_IB_CV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_ACTUAL_IB_CV);
    public static final IMetafieldId WI_UFV_TIME_IN = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_TIME_IN);
    public static final IMetafieldId WI_UFV_TIME_OUT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_TIME_OUT);
    public static final IMetafieldId WI_FETCH_CHE_ID = IMovesField.MVHS_FETCH_CHE_ID;
    public static final IMetafieldId WI_CARRY_CHE_ID = IMovesField.MVHS_CARRY_CHE_ID;
    public static final IMetafieldId WI_PUT_CHE_ID = IMovesField.MVHS_PUT_CHE_ID;
    public static final IMetafieldId WI_CARRY_CHE_DISPATCH_TIME = IMovesField.MVHS_TIME_CARRY_CHE_DISPATCH;
    public static final IMetafieldId WI_LINE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_LINE_OPERATOR);
    public static final IMetafieldId WI_UFV_ACTUAL_INBOUND_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_ACTUAL_INBOUND_CV_OPERATOR);
    public static final IMetafieldId WI_UFV_INTENDED_OUTBOUND_CV_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_INTENDED_OUTBOUND_CV_OPERATOR);
    public static final IMetafieldId WI_UFV_RTG_TRUCKING_COMPANY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)UFV_RTG_TRUCKING_COMPANY);
    public static final IMetafieldId HZRDIP_PLACRD_TEXT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)HZRDIP_PLACARD, (IMetafieldId)PLACARD_TEXT);
    public static final IMetafieldId WQ_FACILITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WQ_YARD, (IMetafieldId)IArgoField.YRD_FACILITY);
    public static final IMetafieldId WQ_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WQ_FACILITY, (IMetafieldId)IArgoField.FCY_COMPLEX);
    public static final IMetafieldId WQ_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WQ_COMPLEX, (IMetafieldId)IArgoField.CPX_OPERATOR);
    public static final IMetafieldId UNIT_RTG_BOND_TRUCKING_COMPANY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_BOND_TRUCKING_COMPANY);
    public static final IMetafieldId UFV_RTG_BOND_TRUCKING_COMPANY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RTG_BOND_TRUCKING_COMPANY);
    public static final IMetafieldId UNIT_RTG_BOND_DESTINATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ROUTING, (IMetafieldId)RTG_BONDED_DESTINATION);
    public static final IMetafieldId UFV_GROSS_WEIGHT_SOURCE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GROSS_WEIGHT_SOURCE);
    public static final IMetafieldId UFV_UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS);
    public static final IMetafieldId UFV_UNIT_VGM_ENTITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_VGM_ENTITY);
    public static final IMetafieldId UFV_UNIT_GDS_BL_NBR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GDS_BL_NBR);
    public static final IMetafieldId[] BUNDLE_FIELDS = new IMetafieldId[]{IInventoryBizMetafield.UNIT_SLAVE_ID1, IInventoryBizMetafield.UNIT_SLAVE_ID2, IInventoryBizMetafield.UNIT_SLAVE_ID3, IInventoryBizMetafield.UNIT_SLAVE_ID4, IInventoryBizMetafield.UNIT_SLAVE_ID5, IInventoryBizMetafield.UNIT_SLAVE_ID6, IInventoryBizMetafield.UNIT_SLAVE_ID7, IInventoryBizMetafield.UNIT_SLAVE_ID8};
    public static final IMetafieldId DMGITEM_TYPE_DESC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)DMGITEM_TYPE, (IMetafieldId) IArgoRefField.EQDMGTYP_DESCRIPTION);
    public static final IMetafieldId DMGITEM_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)DMGITEM_TYPE, (IMetafieldId) IArgoRefField.EQDMGTYP_ID);
    public static final IMetafieldId DMGITEM_CMP_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)DMGITEM_COMPONENT, (IMetafieldId) IArgoRefField.EQCMP_ID);
    public static final IMetafieldId DMGITEM_CMP_DESC = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)DMGITEM_COMPONENT, (IMetafieldId) IArgoRefField.EQCMP_DESCRIPTION);
    public static final IMetafieldId UFV_AGENT1 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_AGENT1);
    public static final IMetafieldId UFV_AGENT2 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_AGENT2);
    public static final IMetafieldId UNIT_AGENT1_MASTER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT1, (IMetafieldId) IArgoRefField.BZU_BIZU);
    public static final IMetafieldId UNIT_AGENT2_MASTER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT2, (IMetafieldId) IArgoRefField.BZU_BIZU);
    public static final IMetafieldId UNIT_AGENT1_CREDIT_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT1, (IMetafieldId) IArgoRefField.BZU_CREDIT_STATUS);
    public static final IMetafieldId UNIT_AGENT2_CREDIT_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT2, (IMetafieldId) IArgoRefField.BZU_CREDIT_STATUS);
    public static final IMetafieldId UNIT_AGENT1_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT1, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UNIT_AGENT2_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_AGENT2, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UNIT_IS_DIRECT_IB_TO_OB_MOVE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)UFV_IS_DIRECT_IB_TO_OB_MOVE);
    public static final IMetafieldId UFV_UFV_ACTUAL_IB_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_IB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UFV_UFV_ACTUAL_OB_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_OPERATOR);
    public static final IMetafieldId UNIT_HAS_ACTIVE_GATE_TRANS = MetafieldIdFactory.valueOf((String)"UNIT_HAS_ACTIVE_GATE_TRANS");
    public static final IMetafieldId HZGOODS_PLACARD_TEXT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)HZGOODS_PLACARD, (IMetafieldId)PLACARD_TEXT);
    public static final IMetafieldId HZGOODS_PLACARD_MIN_WT_KG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)HZGOODS_PLACARD, (IMetafieldId)PLACARD_MIN_WT_KG);
    public static final IMetafieldId HZGOODS_PLACARD_FURTHER_EXPLANATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)HZGOODS_PLACARD, (IMetafieldId)PLACARD_FURTHER_EXPLANATION);
    public static final IMetafieldId SUBRISK_PLACARD_TEXT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)SUBRISK_PLACARD, (IMetafieldId)PLACARD_TEXT);
    public static final IMetafieldId SUBRISK_PLACARD_MIN_WT_KG = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)SUBRISK_PLACARD, (IMetafieldId)PLACARD_MIN_WT_KG);
    public static final IMetafieldId SUBRISK_PLACARD_FURTHER_EXPLANATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)SUBRISK_PLACARD, (IMetafieldId)PLACARD_FURTHER_EXPLANATION);
    public static final IMetafieldId GOODS_UNIT_REQUIRES_POWER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.GDS_UNIT, (IMetafieldId)UNIT_REQUIRES_POWER);
    public static final IMetafieldId GOODS_UNIT_LINE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.GDS_UNIT, (IMetafieldId)UNIT_LINE_OPERATOR);
    public static final IMetafieldId GOODS_UNIT_RFREQ_EXTENDED_TIME_MONITORS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.GDS_UNIT, (IMetafieldId)UNIT_GDS_RFREQ_EXTENDED_TIME_MONITORS);
    public static final IMetafieldId GOODS_UNIT_ACTIVE_UFV = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.GDS_UNIT, (IMetafieldId)UNIT_ACTIVE_UFV);
    public static final IMetafieldId GOODS_UNIT_UFV_TRANSIT_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GOODS_UNIT_ACTIVE_UFV, (IMetafieldId)UFV_TRANSIT_STATE);
    public static final IMetafieldId UFV_GUARANTEE_PARTY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_GUARANTEE_PARTY_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ROLE);
    public static final IMetafieldId UFV_LINE_GUARANTEE_PARTY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LINE_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_POWER_GUARANTEE_PARTY_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POWER_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ID);
    public static final IMetafieldId UFV_LINE_GUARANTEE_PARTY_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_LINE_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ROLE);
    public static final IMetafieldId UFV_POWER_GUARANTEE_PARTY_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_POWER_GUARANTEE_PARTY, (IMetafieldId) IArgoRefField.BZU_ROLE);
    public static final IMetafieldId UFV_ACTUAL_OB_CV_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_ACTUAL_OB_CV, (IMetafieldId)IArgoField.CV_ID);
    public static final IMetafieldId EQUIPMENT_OWNER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)IArgoBizMetafield.EQUIPMENT_OWNER_ID);
    public static final IMetafieldId UNIT_EQ_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_ISO_GROUP);
    public static final IMetafieldId UNIT_EQ_ISO_CODE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_ID);
    public static final IMetafieldId UNIT_EQ_SUB_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_CLASS);
    public static final IMetafieldId UNIT_EQ_EQTYPE_HEIGHT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)EQ_EQTYPE_HEIGHT);
    public static final IMetafieldId UNIT_EQ_NOMINAL_LENGTH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId)EQ_NOMINAL_LENGTH);
    public static final IMetafieldId UFV_REFRECORD_RETURN_TMP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryBizMetafield.UNIT_LAST_REEFER_RECORD_TEMP);
    public static final IMetafieldId UFV_REFRECORD_TIME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)IInventoryBizMetafield.UNIT_LAST_REEFER_RECORD_DATE);
    public static final IMetafieldId UFV_STORAGE_RULE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_ID);
    public static final IMetafieldId UFV_STORAGE_RULE_START_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_START_DAY);
    public static final IMetafieldId UFV_STORAGE_RULE_END_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_END_DAY);
    public static final IMetafieldId UFV_STORAGE_RULE_IS_START_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_START_DAY_INCLUDED);
    public static final IMetafieldId UFV_STORAGE_RULE_IS_END_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_END_DAY_INCLUDED);
    public static final IMetafieldId UFV_STORAGE_RULE_IS_FREE_TIME_CHGED_IF_EXCEEDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_FREE_TIME_CHGED_IF_EXCEEDED);
    public static final IMetafieldId UFV_STORAGE_RULE_CALENDAR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_CALENDAR);
    public static final IMetafieldId UFV_POWER_RULE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_ID);
    public static final IMetafieldId UFV_POWER_RULE_START_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_START_DAY);
    public static final IMetafieldId UFV_POWER_RULE_END_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_END_DAY);
    public static final IMetafieldId UFV_POWER_RULE_IS_START_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_START_DAY_INCLUDED);
    public static final IMetafieldId UFV_POWER_RULE_IS_END_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_END_DAY_INCLUDED);
    public static final IMetafieldId UFV_POWER_RULE_IS_FREE_TIME_CHGED_IF_EXCEEDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_FREE_TIME_CHGED_IF_EXCEEDED);
    public static final IMetafieldId UFV_POWER_RULE_CALENDAR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_POWER_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_CALENDAR);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_ID);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_START_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_START_DAY);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_END_DAY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_END_DAY);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_IS_START_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_START_DAY_INCLUDED);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_IS_END_DAY_INCLUDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_END_DAY_INCLUDED);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_IS_FREE_TIME_CHGED_IF_EXCEEDED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_IS_FREE_TIME_CHGED_IF_EXCEEDED);
    public static final IMetafieldId UFV_LINE_STORAGE_RULE_CALENDAR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryBizMetafield.UFV_LINE_STORAGE_CALC_DTO, (IMetafieldId)IInventoryField.SRULE_CALENDAR);
    public static final IMetafieldId GDSDOC_GOODS_DECLARATION_GOODS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.GDSDOC_GOODS_DECLARATION, (IMetafieldId)IInventoryField.GDSDECL_GOODS);
    public static final IMetafieldId UNIT_EQ_ID_FULL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_FULL);
    public static final IMetafieldId UFV_UNIT_EQ_ID_FULL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQ_ID_FULL);
    public static final IMetafieldId UNIT_RELATED_EQ_ID_FULL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.UNIT_RELATED_UNIT, (IMetafieldId)UNIT_EQ_ID_FULL);
    public static final IMetafieldId UFV_UNIT_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQ_ROLE);
    public static final IMetafieldId UNIT_RELATED_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.UNIT_RELATED_UNIT, (IMetafieldId)IInventoryField.UNIT_GKEY);
    public static final IMetafieldId UFV_RELATED_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.UFV_UNIT, (IMetafieldId)UNIT_RELATED_GKEY);
    public static final IMetafieldId UNIT_RELATED_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RELATED_UNIT, (IMetafieldId)UNIT_EQ_ROLE);
    public static final IMetafieldId UFV_RELATED_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RELATED_ROLE);
    public static final IMetafieldId UNIT_RELATED_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RELATED_UNIT, (IMetafieldId)UNIT_EQUIPMENT_STATE);
    public static final IMetafieldId UFV_RELATED_EQS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RELATED_EQS);
    public static final IMetafieldId UFV_RELATED_EQS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_RELATED_EQS, (IMetafieldId)EQS_GKEY);
    public static final IMetafieldId UFV_RELATED_UNIT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IInventoryField.UFV_UNIT, (IMetafieldId)IInventoryField.UNIT_RELATED_UNIT);
    public static final IMetafieldId UFV_RELATED_UNIT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_RELATED_UNIT, (IMetafieldId)IInventoryField.UNIT_ID);
    public static final IMetafieldId UNIT_EQ_GRADE = UNIT_GRADE_I_D;
    public static final IMetafieldId UNIT_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQ_GRADE, (IMetafieldId) IArgoRefField.EQGRD_ID);
    public static final IMetafieldId UNIT_EQS_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_GRADE_I_D);
    public static final IMetafieldId UNIT_EQS_EQ_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_EQ_GRADE_ID);
    public static final IMetafieldId UFV_UNIT_EQ_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GRADE_ID);
    public static final IMetafieldId UFV_RELATED_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_RELATED_UNIT, (IMetafieldId)UNIT_GRADE_ID);
    public static final IMetafieldId UFV_RELATED_DAMAGE_SEVERITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_RELATED_UNIT, (IMetafieldId)UNIT_DAMAGE_SEVERITY);
    public static final IMetafieldId UNIT_RELATED_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RELATED_UNIT, (IMetafieldId)UNIT_EQUIPMENT);
    public static final IMetafieldId UFV_RELATED_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_RELATED_EQ);
    public static final IMetafieldId UFV_RELATED_EQ_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_RELATED_EQ, (IMetafieldId) IArgoRefField.EQ_CLASS);
    public static final IMetafieldId UNIT_RELATED_EQ_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RELATED_EQ, (IMetafieldId) IArgoRefField.EQ_GKEY);
    public static final IMetafieldId UNIT_EQ_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_EQ_OPERATOR_ID);
    public static final IMetafieldId UNIT_RELATED_EQ_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_RELATED_UNIT, (IMetafieldId)UNIT_EQ_OPERATOR_ID);
    public static final IMetafieldId UYV_RELATED_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_RELATED_EQ);
    public static final IMetafieldId UYV_RELATED_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_RELATED_ROLE);
    public static final IMetafieldId UYV_UNIT_ROLE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_EQ_ROLE);
    public static final IMetafieldId UYV_EQ = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_EQUIPMENT);
    public static final IMetafieldId UFV_CARRIAGE_EQ_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_UE, (IMetafieldId)UE_EQ_GRADE_ID);
    public static final IMetafieldId UFV_CARRIAGE_EQS_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_EQS, (IMetafieldId)EQS_EQ_GRADE_ID);
    public static final IMetafieldId UFV_CARRIAGE_EQ_OFF_HIRE_LOCATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_UE, (IMetafieldId)UE_OFFHIRE_LOCATION);
    public static final IMetafieldId UFV_CARRIAGE_EQ_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_UE, (IMetafieldId)UE_EQ_OPERATOR_ID);
    public static final IMetafieldId UFV_CARRIAGE_EQ_OWNER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_UE, (IMetafieldId)UE_EQ_OWNER_ID);
    public static final IMetafieldId UFV_CARRIAGE_DAMAGE_SEVERITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_CARRIAGE_UE, (IMetafieldId)UE_DAMAGE_SEVERITY);
    public static final IMetafieldId UNIT_EQ_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)IInventoryField.EQS_EQ_OPERATOR);
    public static final IMetafieldId UFV_EQ_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQ_OPERATOR);
    public static final IMetafieldId UFV_DAMAGE_SEVERITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_DAMAGE_SEVERITY);
    public static final IMetafieldId UNIT_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_EQUIP_TYPE);
    public static final IMetafieldId UFV_UNIT_UNIT_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQTYPE);
    public static final IMetafieldId UFV_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQTYPE);
    public static final IMetafieldId UNIT_EQ_OWNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)IInventoryField.EQS_EQ_OWNER);
    public static final IMetafieldId UFV_EQ_OWNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQ_OWNER);
    public static final IMetafieldId UNIT_EQ_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_CLASS);
    public static final IMetafieldId UYV_OWNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_EQ_OWNER);
    public static final IMetafieldId UYV_EQTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)UNIT_EQTYPE);
    public static final IMetafieldId UNIT_EQS_FLEX_STRING01 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_FLEX_STRING01);
    public static final IMetafieldId UNIT_EQS_FLEX_STRING02 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_FLEX_STRING02);
    public static final IMetafieldId UNIT_EQS_FLEX_STRING03 = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_FLEX_STRING03);
    public static final IMetafieldId UNIT_RFR_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_RFR_TYPE);
    public static final IMetafieldId UNIT_EQTYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_ID);
    public static final IMetafieldId UNIT_EQS_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)EQS_GKEY);
    public static final IMetafieldId UNIT_EQ_MATERIAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_MATERIAL);
    public static final IMetafieldId UNIT_OFFHIRE_LOCATION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT_STATE, (IMetafieldId)IInventoryField.EQS_OFFHIRE_LOCATION);
    public static final IMetafieldId UNIT_EQ_ID_NBR_ONLY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_NBR_ONLY);
    public static final IMetafieldId UNIT_EQ_ID_PREFIX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ID_PREFIX);
    public static final IMetafieldId GDS_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_GRADE_ID);
    public static final IMetafieldId GDS_EQS_GRADE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQS, (IMetafieldId)EQS_EQ_GRADE_ID);
    public static final IMetafieldId GDS_EQ_OPERATOR_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_EQ_OPERATOR_ID);
    public static final IMetafieldId UNIT_EQUIP_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_ISO_GROUP);
    public static final IMetafieldId UNIT_EQUIP_MATERIAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQUIPMENT, (IMetafieldId) IArgoRefField.EQ_MATERIAL);
    public static final IMetafieldId GDS_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_EQUIP_ISO_GROUP);
    public static final IMetafieldId GDS_EQ_MATERIAL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_EQUIP_MATERIAL);
    public static final IMetafieldId GDS_DAMAGE_SERVERITY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)UNIT_DAMAGE_SEVERITY);
    public static final IMetafieldId UNIT_EQTYPE_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_ISO_GROUP);
    public static final IMetafieldId UFV_EQTYP_ISO_GROUP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQTYPE_ISO_GROUP);
    public static final IMetafieldId UNIT_EQTYPE_ARCHETYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_ARCHETYPE);
    public static final IMetafieldId UFV_EQTYP_ARCHETYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQTYPE_ARCHETYPE);
    public static final IMetafieldId UNIT_EQTYP_HEIGHT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_EQTYPE, (IMetafieldId) IArgoRefField.EQTYP_NOMINAL_HEIGHT);
    public static final IMetafieldId UFV_EQTYPE_HEIGHT = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_EQTYP_HEIGHT);
    public static final IMetafieldId UNIT_DECLARED_IB_CV_FCY_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_DECLARED_IB_CV, (IMetafieldId)MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IArgoField.CV_FACILITY, (IMetafieldId)IArgoField.FCY_GKEY));
    public static final IMetafieldId UFV_GOODS_AND_CTR_WT_KG_VERFIED_GROSS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS);
    private static final MetafieldIdList YIP_MOBILE_FIELDS = new MetafieldIdList();
    public static final IMetafieldId[] PREADVISE_FIELDS;
    public static final String PREADVISE_UFV_FLEX_FIELDS = "PREADVISE_UFV_FLEX_FIELDS";
    public static final MetafieldIdList UFV_CUSTOM_FIELDS;
    public static final MetafieldIdList UNIT_DAMAGE_EVENT_FIELDS;
    public static final IMetafieldId[] REEFER_FIELDS;
    public static final IMetafieldId[] UFV_AUDIT_FIELDS;
    public static final IMetafieldId[] IGNORE_PROPERY_UPDATE_EVENT;
    public static final IMetafieldId[] TBDU_FLEX_FIELDS;
    private static final char DELIMITTER = '.';
    private static final Logger LOGGER;
    public static final long MILLIS_PER_DAY = 86400000L;

    @Nullable
    public static IMetafieldId getQualifiedField(IMetafieldId inMfid, String inBaseEntity) {
        String leftNode = inMfid.getQualifiedId();
        assert (leftNode != null);
        int delimitter = leftNode.indexOf(46);
        if (delimitter > 0) {
            leftNode = leftNode.substring(0, delimitter);
        }
        String entityOfField = HiberCache.getEntityNameForField((String)leftNode);
        Class entityClass = HiberCache.getEntityClassForField((String)leftNode);
        return UnitField.getQualifiedField(inMfid, inBaseEntity, entityOfField, entityClass);
    }

    @Nullable
    public static IMetafieldId getQualifiedField(IMetafieldId inMfid, String inBaseEntity, String inEntityOfField, Class inEntityClass) {
        if (inEntityClass != null && GoodsBase.class.isAssignableFrom(inEntityClass)) {
            inEntityOfField = "GoodsBase";
        }
        if ("Unit".equals(inBaseEntity)) {
            if ("UnitFacilityVisit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_ACTIVE_UFV, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return inMfid;
            }
            if ("EquipmentState".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_PRIMARY_EQS, (IMetafieldId)inMfid);
            }
            if ("Equipment".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Container".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Chassis".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("EquipType".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_PRIMARY_EQTYPE, (IMetafieldId)inMfid);
            }
            if ("GoodsBase".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UNIT_GOODS, (IMetafieldId)inMfid);
            }
            if ("EqBaseOrderItem".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UE_DEPARTURE_ORDER, (IMetafieldId)inMfid);
            }
        } else if ("UnitFacilityVisit".equals(inBaseEntity)) {
            if ("UnitFacilityVisit".equals(inEntityOfField)) {
                return inMfid;
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_UNIT, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_UE, (IMetafieldId)inMfid);
            }
            if ("EquipmentState".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQS, (IMetafieldId)inMfid);
            }
            if ("Equipment".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Container".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Chassis".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("EquipType".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_PRIMARY_EQTYPE, (IMetafieldId)inMfid);
            }
            if ("GoodsBase".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UFV_GOODS, (IMetafieldId)inMfid);
            }
        } else if ("UnitYardVisit".equals(inBaseEntity)) {
            if ("UnitYardVisit".equals(inEntityOfField)) {
                return inMfid;
            }
            if ("UnitFacilityVisit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UFV, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_UNIT, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_UE, (IMetafieldId)inMfid);
            }
            if ("EquipmentState".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_EQS, (IMetafieldId)inMfid);
            }
            if ("Equipment".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Container".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("Chassis".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_EQ, (IMetafieldId)inMfid);
            }
            if ("EquipType".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_PRIMARY_EQTYPE, (IMetafieldId)inMfid);
            }
            if ("GoodsBase".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)UYV_GOODS, (IMetafieldId)inMfid);
            }
        } else if ("GoodsBase".equals(inBaseEntity)) {
            if ("UnitFacilityVisit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UFV, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_UNIT, (IMetafieldId)inMfid);
            }
            if ("EquipmentState".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQS, (IMetafieldId)inMfid);
            }
            if ("Equipment".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("Container".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("Chassis".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("EquipType".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)GDS_EQTYPE, (IMetafieldId)inMfid);
            }
            if ("GoodsBase".equals(inEntityOfField)) {
                return inMfid;
            }
        } else if ("WorkInstruction".equals(inBaseEntity)) {
            if ("UnitYardVisit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IMovesField.WI_UYV, (IMetafieldId)inMfid);
            }
            if ("UnitFacilityVisit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UFV, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UNIT, (IMetafieldId)inMfid);
            }
            if ("Unit".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_UE, (IMetafieldId)inMfid);
            }
            if ("EquipmentState".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQS, (IMetafieldId)inMfid);
            }
            if ("Equipment".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("Container".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("Chassis".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQUIPMENT, (IMetafieldId)inMfid);
            }
            if ("EquipType".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_EQTYPE, (IMetafieldId)inMfid);
            }
            if ("GoodsBase".equals(inEntityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)WI_GOODS, (IMetafieldId)inMfid);
            }
            if ("WorkInstruction".equals(inEntityOfField)) {
                return inMfid;
            }
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("getQualifiedField: could not map <" + (Object)inMfid + "> of class <" + inEntityOfField + '>'));
        }
        return inMfid;
    }

    public static MetafieldIdList getSecuredFields() {
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(UNIT_NEEDS_REVIEW);
        fields.add(UNIT_DECLARED_IB_CV);
        fields.add(UNIT_FREIGHT_KIND);
        fields.add(UNIT_DRAY_STATUS);
        fields.add(UNIT_SPECIAL_STOW);
        fields.add(UNIT_SPECIAL_STOW2);
        fields.add(UNIT_SPECIAL_STOW3);
        fields.add(UNIT_DECK_RQMNT);
        fields.add(UNIT_REQUIRES_POWER);
        fields.add(UNIT_VALIDATE_RELEASE_GROUP);
        fields.add(UNIT_IS_TRANSFERABLE_RELEASE);
        fields.add(IInventoryField.UNIT_LINE_OPERATOR);
        fields.add(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG);
        fields.add(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS);
        fields.add(IInventoryField.UNIT_VGM_ENTITY);
        fields.add(IInventoryField.UNIT_IGNORE_PAYLOAD_WEIGHTS);
        fields.add(IInventoryField.UNIT_IGNORE_PAYLOAD_HEIGHTS);
        fields.add(IInventoryField.UNIT_FLEX_STRING01);
        fields.add(IInventoryField.UNIT_FLEX_STRING02);
        fields.add(IInventoryField.UNIT_FLEX_STRING03);
        fields.add(IInventoryField.UNIT_FLEX_STRING04);
        fields.add(IInventoryField.UNIT_FLEX_STRING05);
        fields.add(IInventoryField.UNIT_FLEX_STRING06);
        fields.add(IInventoryField.UNIT_FLEX_STRING07);
        fields.add(IInventoryField.UNIT_FLEX_STRING08);
        fields.add(IInventoryField.UNIT_FLEX_STRING09);
        fields.add(IInventoryField.UNIT_FLEX_STRING10);
        fields.add(IInventoryField.UNIT_FLEX_STRING11);
        fields.add(IInventoryField.UNIT_FLEX_STRING12);
        fields.add(IInventoryField.UNIT_FLEX_STRING13);
        fields.add(IInventoryField.UNIT_FLEX_STRING14);
        fields.add(IInventoryField.UNIT_FLEX_STRING15);
        fields.add(IInventoryField.UNIT_VALIDATE_RELEASE_GROUP);
        fields.add(IInventoryField.UNIT_IS_TRANSFERABLE_RELEASE);
        fields.add(IInventoryField.UNIT_IGNORE_PAYLOAD_WEIGHTS);
        fields.add(IInventoryField.UNIT_IGNORE_PAYLOAD_HEIGHTS);
        fields.add(IInventoryField.UNIT_NEEDS_REVIEW);
        fields.add(IInventoryField.RTG_GROUP);
        fields.add(IInventoryField.RTG_TRUCKING_COMPANY);
        fields.add(IInventoryField.RTG_P_O_L);
        fields.add(IInventoryField.RTG_O_P_L);
        fields.add(IInventoryField.UNIT_GRADE_I_D);
        fields.add(IInventoryField.UE_IS_FOLDED);
        fields.add(IInventoryField.GDS_CONSIGNEE_BZU);
        fields.add(IInventoryField.GDS_SHIPPER_BZU);
        fields.add(IInventoryBizMetafield.GDS_CONSIGNEE_AS_STRING);
        fields.add(IInventoryBizMetafield.GDS_SHIPPER_AS_STRING);
        fields.add(IInventoryField.GDS_COMMODITY);
        fields.add(IInventoryField.GDS_ORIGIN);
        fields.add(IInventoryField.GDS_DESTINATION);
        fields.add(UFV_TIME_EC_IN);
        fields.add(UFV_TIME_IN);
        fields.add(UFV_TIME_OUT);
        fields.add(UFV_HORIZON);
        fields.add(UFV_DLV_TIME_APPNTMNT);
        fields.add(IInventoryField.UFV_TIME_INVENTORY);
        fields.add(IInventoryField.UFV_TIME_COMPLETE);
        fields.add(IInventoryField.UFV_RESTOW_TYPE);
        fields.add(IInventoryField.UFV_HANDLING_REASON);
        fields.add(IInventoryField.UFV_YARD_STOWAGE_TYPE);
        fields.add(IInventoryField.UFV_IS_SPARCS_STOPPED);
        fields.add(IInventoryField.UFV_HAS_CHANGED);
        fields.add(IInventoryField.UFV_VERIFIED_FOR_LOAD);
        fields.add(IInventoryField.UFV_VERIFIED_YARD_POSITION);
        fields.add(IInventoryField.UFV_ANOMALY_FOR_EXP_DKNG);
        fields.add(IInventoryField.UFV_ACTUAL_IB_CV);
        fields.add(IInventoryField.UFV_LAST_FREE_DAY);
        fields.add(IInventoryField.UFV_PAID_THRU_DAY);
        fields.add(IInventoryField.UFV_LINE_PAID_THRU_DAY);
        fields.add(IInventoryField.UFV_POWER_PAID_THRU_DAY);
        fields.add(IInventoryField.UFV_FLEX_STRING01);
        fields.add(IInventoryField.UFV_FLEX_STRING02);
        fields.add(IInventoryField.UFV_FLEX_STRING03);
        fields.add(IInventoryField.UFV_FLEX_STRING04);
        fields.add(IInventoryField.UFV_FLEX_STRING05);
        fields.add(IInventoryField.UFV_FLEX_STRING06);
        fields.add(IInventoryField.UFV_FLEX_STRING07);
        fields.add(IInventoryField.UFV_FLEX_STRING08);
        fields.add(IInventoryField.UFV_FLEX_STRING09);
        fields.add(IInventoryField.UFV_FLEX_STRING10);
        fields.add(IInventoryField.UFV_FLEX_DATE01);
        fields.add(IInventoryField.UFV_FLEX_DATE02);
        fields.add(IInventoryField.UFV_FLEX_DATE03);
        fields.add(IInventoryField.UFV_FLEX_DATE04);
        fields.add(IInventoryField.UFV_FLEX_DATE05);
        fields.add(IInventoryField.UFV_FLEX_DATE06);
        fields.add(IInventoryField.UFV_FLEX_DATE07);
        fields.add(IInventoryField.UFV_FLEX_DATE08);
        return fields;
    }

    public static IPredicate getCurrentAndRecentUnitsPredicate(int inDaysOfHistory, String inEntityName) {
        IMetafieldId qualifiedVisitStateMfdId = UnitField.getQualifiedField(IInventoryField.UFV_VISIT_STATE, inEntityName);
        IMetafieldId qualifiedTimeCompleteMfdId = UnitField.getQualifiedField(IInventoryField.UFV_TIME_COMPLETE, inEntityName);
        Date earliestDate = new Date(ArgoUtils.timeNowMillis() - 86400000L * (long)inDaysOfHistory);
        return PredicateFactory.disjunction().add(PredicateFactory.eq((IMetafieldId)qualifiedVisitStateMfdId, (Object)((Object) UnitVisitStateEnum.ACTIVE))).add(PredicateFactory.eq((IMetafieldId)qualifiedVisitStateMfdId, (Object)((Object)UnitVisitStateEnum.ADVISED))).add((IPredicate)PredicateFactory.conjunction().add(PredicateFactory.eq((IMetafieldId)qualifiedVisitStateMfdId, (Object)((Object)UnitVisitStateEnum.DEPARTED))).add(PredicateFactory.ge((IMetafieldId)qualifiedTimeCompleteMfdId, (Object)earliestDate)));
    }

    public static MetafieldIdList getMobileYardInspectionFields() {
        return YIP_MOBILE_FIELDS;
    }

    static {
        YIP_MOBILE_FIELDS.add(UNIT_ID);
        YIP_MOBILE_FIELDS.add(UNIT_REMARK);
        YIP_MOBILE_FIELDS.add(UNIT_LINE_OPERATOR);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_UE_EQ_GKEY);
        YIP_MOBILE_FIELDS.add(UNIT_GOODS);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQTYPE);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQTYPE_ID);
        YIP_MOBILE_FIELDS.add(UNIT_SEAL_NBR1);
        YIP_MOBILE_FIELDS.add(UNIT_SEAL_NBR2);
        YIP_MOBILE_FIELDS.add(UNIT_SEAL_NBR3);
        YIP_MOBILE_FIELDS.add(UNIT_SEAL_NBR4);
        YIP_MOBILE_FIELDS.add(UNIT_OOG_BACK_CM);
        YIP_MOBILE_FIELDS.add(UNIT_OOG_FRONT_CM);
        YIP_MOBILE_FIELDS.add(UNIT_OOG_LEFT_CM);
        YIP_MOBILE_FIELDS.add(UNIT_OOG_RIGHT_CM);
        YIP_MOBILE_FIELDS.add(UNIT_OOG_TOP_CM);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_UE_EQ_GKEY);
        YIP_MOBILE_FIELDS.add(UNIT_IS_POWERED);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQ_TARE_WEIGHT_KG);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQ_SAFE_WEIGHT_KG);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQ_BUILD_DATE);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQ_CSC_EXPIRATION);
        YIP_MOBILE_FIELDS.add(UNIT_PRIMARY_EQ_TANK_RAILS);
        YIP_MOBILE_FIELDS.add(UNIT_GRADE_I_D);
        YIP_MOBILE_FIELDS.add(UNIT_EQ_MATERIAL);
        YIP_MOBILE_FIELDS.add(UNIT_UE_MNR_STATUS);
        YIP_MOBILE_FIELDS.add(UNIT_DAMAGE_VAO);
        YIP_MOBILE_FIELDS.add(MOBILE_DAMAGES_VAO);
        YIP_MOBILE_FIELDS.add(UNIT_OBSERVED_PLACARDS);
        YIP_MOBILE_FIELDS.add(UNIT_UE_PLACARDED);
        YIP_MOBILE_FIELDS.add(UNIT_GDS_RFREQ_TEMP_SET_POINT_C);
        YIP_MOBILE_FIELDS.add(UNIT_IS_POWERED);
        YIP_MOBILE_FIELDS.add(IInventoryBizMetafield.UNIT_ACRY_ID);
        YIP_MOBILE_FIELDS.add(IInventoryBizMetafield.UNIT_ACRY_EQTYPE_ID);
        YIP_MOBILE_FIELDS.add(UNIT_FREIGHT_KIND);
        YIP_MOBILE_FIELDS.add(UNIT_CATEGORY);
        YIP_MOBILE_FIELDS.add(IInventoryBizMetafield.UNIT_WORST_HAZARD_CLASS);
        YIP_MOBILE_FIELDS.add(IInventoryField.UNIT_GOODS_AND_CTR_WT_KG);
        YIP_MOBILE_FIELDS.add(UNIT_FREIGHT_KIND);
        YIP_MOBILE_FIELDS.add(UNIT_DOOR_DIRECTION);
        PREADVISE_FIELDS = new IMetafieldId[]{UNIT_DECLARED_IB_CARRIER_MODE, UNIT_DECLARED_IB_ID, UNIT_GOODS_AND_CTR_WT_KG, UNIT_GOODS_AND_CTR_WT_KG_VERFIED_GROSS, UNIT_VGM_ENTITY, UNIT_SEAL_NBR1, UNIT_SEAL_NBR2, UNIT_SEAL_NBR3, UNIT_SEAL_NBR4, UNIT_DEPARTURE_ORDER_NBR, UNIT_DEPARTURE_ORDER, UNIT_DEPARTURE_ORDER_ITEM, UNIT_ARRIVAL_ORDER_NBR, UNIT_ARRIVAL_ORDER_ITEM, UNIT_ARRIVAL_ORDER, UNIT_CATEGORY, UNIT_DECLARED_OB_CV, UNIT_RTG_POL, UNIT_RTG_POD1, UNIT_RTG_POD2, UNIT_GDS_DESTINATION, UNIT_GDS_ORIGIN, UNIT_RTG_TRUCKING_COMPANY, UNIT_FREIGHT_KIND, UNIT_REMARK, UNIT_GDS_SHIPPER_AS_STRING, UNIT_GDS_CONSIGNEE_AS_STRING, UNIT_CMDTY, UNIT_GDS_RFREQ_TEMP_SET_POINT_C, UNIT_GDS_RFREQ_HUMIDITY_PCT, UNIT_GDS_RFREQ_VENT_REQUIRED, UNIT_GDS_RFREQ_VENT_UNIT, UNIT_GDS_RFREQ_O2_PCT, UNIT_GDS_RFREQ_CO2_PCT, UNIT_GDS_RFREQ_LATEST_ON_POWER_TIME, UNIT_GDS_RFREQ_REQUESTED_OFF_POWER_TIME, UNIT_OOG_BACK_CM, UNIT_OOG_FRONT_CM, UNIT_OOG_LEFT_CM, UNIT_OOG_RIGHT_CM, UNIT_OOG_TOP_CM, IInventoryBizMetafield.HAZARDS_HAZ_ITEMS_VAO, UNIT_IS_DIRECT_IB_TO_OB_MOVE, UNIT_SPECIAL_STOW, UNIT_FLEX_STRING01, UNIT_FLEX_STRING02, UNIT_FLEX_STRING03, UNIT_FLEX_STRING04, UNIT_FLEX_STRING05, UNIT_FLEX_STRING06, UNIT_FLEX_STRING07, UNIT_FLEX_STRING08, UNIT_FLEX_STRING09, UNIT_FLEX_STRING10, UNIT_FLEX_STRING11, UNIT_FLEX_STRING12, UNIT_FLEX_STRING13, UNIT_FLEX_STRING14, UNIT_FLEX_STRING15};
        UFV_CUSTOM_FIELDS = new MetafieldIdList(new IMetafieldId[]{UFV_FLEX_STRING01, UFV_FLEX_STRING02, UFV_FLEX_STRING03, UFV_FLEX_STRING04, UFV_FLEX_STRING05, UFV_FLEX_STRING06, UFV_FLEX_STRING07, UFV_FLEX_STRING08, UFV_FLEX_STRING09, UFV_FLEX_STRING10, UFV_FLEX_DATE01, UFV_FLEX_DATE02, UFV_FLEX_DATE03, UFV_FLEX_DATE04, UFV_FLEX_DATE05, UFV_FLEX_DATE06, UFV_FLEX_DATE07, UFV_FLEX_DATE08});
        UNIT_DAMAGE_EVENT_FIELDS = new MetafieldIdList(new IMetafieldId[]{DMGITEM_TYPE, DMGITEM_COMPONENT, DMGITEM_SEVERITY, DMGITEM_LOCATION, DMGITEM_QUANTITY, DMGITEM_LENGTH, DMGITEM_WIDTH, DMGITEM_DEPTH, DMGITEM_DESCRIPTION, DMGITEM_REPAIRED, DMGITEM_REPORTED});
        REEFER_FIELDS = new IMetafieldId[]{RFREC_TIME, RFREC_RETURN_TMP, RFREC_VENT_SETTING, RFREC_VENT_UNIT, RFREC_HMDTY, RFREC_O2, RFREC_C_O2, RFREC_FAULT_CODE, RFREC_IS_POWERED, RFREC_HAS_MALFUNCTION, RFREC_IS_BULB_ON, RFREC_DEFROST_INTERVAL_HOURS, RFREC_REEFER_HOURS, RFREC_ARE_DRAINS_OPEN, RFREC_FAN_SETTING, RFREC_MIN_MONITORED_TMP, RFREC_MAX_MONITORED_TMP, RFREC_DEFROST_TMP, RFREC_SET_POINT_TMP, RFREC_SUPPLY_TMP, RFREC_FUEL_LEVEL, RFREC_COMPUTER_ID, RFREC_TASK_ID, RFREC_REMARK, RFREC_IS_ALARM_ON, RFREC_DATA_SOURCE};
        UFV_AUDIT_FIELDS = new IMetafieldId[]{IInventoryField.UFV_RESTOW_TYPE, IInventoryField.UFV_HANDLING_REASON, IInventoryField.UFV_TIME_EC_IN, IInventoryField.UFV_DLV_TIME_APPNTMNT, IInventoryField.UFV_TIME_INVENTORY, IInventoryField.UFV_TIME_COMPLETE, IInventoryField.UFV_TIME_IN, IInventoryField.UFV_TIME_OUT, IInventoryField.UFV_DOOR_DIRECTION};
        IGNORE_PROPERY_UPDATE_EVENT = new IMetafieldId[]{IInventoryBizMetafield.UNIT_SEAL_NBR1, IInventoryBizMetafield.UNIT_SEAL_NBR2, IInventoryBizMetafield.UNIT_SEAL_NBR3, IInventoryBizMetafield.UNIT_SEAL_NBR4, IInventoryField.UNIT_SEAL_NBR1, IInventoryField.UNIT_SEAL_NBR2, IInventoryField.UNIT_SEAL_NBR3, IInventoryField.UNIT_SEAL_NBR4, UFV_SEAL_NBR1, UFV_SEAL_NBR2, UFV_SEAL_NBR3, UFV_SEAL_NBR4, IInventoryField.UNIT_OOG_BACK_CM, IInventoryField.UNIT_OOG_FRONT_CM, IInventoryField.UNIT_OOG_LEFT_CM, IInventoryField.UNIT_OOG_RIGHT_CM, IInventoryField.UNIT_OOG_TOP_CM, UFV_OOG_BACK_CM, UFV_OOG_FRONT_CM, UFV_OOG_LEFT_CM, UFV_OOG_RIGHT_CM, UFV_OOG_TOP_CM, UNIT_PRIMARY_EQ_TANK_RAILS, IInventoryField.UNIT_DAMAGE_VAO};
        TBDU_FLEX_FIELDS = new IMetafieldId[]{IInventoryField.TBDU_FLEX_STRING01, IInventoryField.TBDU_FLEX_STRING02, IInventoryField.TBDU_FLEX_STRING03, IInventoryField.TBDU_FLEX_STRING04, IInventoryField.TBDU_FLEX_DATE01, IInventoryField.TBDU_FLEX_DATE02, IInventoryField.TBDU_FLEX_DATE03, IInventoryField.TBDU_FLEX_DATE04};
        LOGGER = Logger.getLogger(UnitField.class);
    }

}
