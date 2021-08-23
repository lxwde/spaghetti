package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IServicesMovesField {

    public static final IMetafieldId MVE_MOVE_KIND = MetafieldIdFactory.valueOf((String)"mveMoveKind");
    public static final IMetafieldId MVE_UFV = MetafieldIdFactory.valueOf((String)"mveUfv");
    public static final IMetafieldId MVE_LINE_SNAPSHOT = MetafieldIdFactory.valueOf((String)"mveLineSnapshot");
    public static final IMetafieldId MVE_CARRIER = MetafieldIdFactory.valueOf((String)"mveCarrier");
    public static final IMetafieldId MVE_EXCLUDE = MetafieldIdFactory.valueOf((String)"mveExclude");
    public static final IMetafieldId MVE_FROM_POSITION = MetafieldIdFactory.valueOf((String)"mveFromPosition");
    public static final IMetafieldId MVE_TO_POSITION = MetafieldIdFactory.valueOf((String)"mveToPosition");
    public static final IMetafieldId MVE_CHE_FETCH = MetafieldIdFactory.valueOf((String)"mveCheFetch");
    public static final IMetafieldId MVE_CHE_CARRY = MetafieldIdFactory.valueOf((String)"mveCheCarry");
    public static final IMetafieldId MVE_CHE_PUT = MetafieldIdFactory.valueOf((String)"mveChePut");
    public static final IMetafieldId MVE_CHE_QUAY_CRANE = MetafieldIdFactory.valueOf((String)"mveCheQuayCrane");
    public static final IMetafieldId MVE_DIST_TO_START = MetafieldIdFactory.valueOf((String)"mveDistToStart");
    public static final IMetafieldId MVE_DIST_OF_CARRY = MetafieldIdFactory.valueOf((String)"mveDistOfCarry");
    public static final IMetafieldId MVE_TIME_CARRY_COMPLETE = MetafieldIdFactory.valueOf((String)"mveTimeCarryComplete");
    public static final IMetafieldId MVE_TIME_DISPATCH = MetafieldIdFactory.valueOf((String)"mveTimeDispatch");
    public static final IMetafieldId MVE_TIME_FETCH = MetafieldIdFactory.valueOf((String)"mveTimeFetch");
    public static final IMetafieldId MVE_TIME_DISCHARGE = MetafieldIdFactory.valueOf((String)"mveTimeDischarge");
    public static final IMetafieldId MVE_TIME_PUT = MetafieldIdFactory.valueOf((String)"mveTimePut");
    public static final IMetafieldId MVE_TIME_CARRY_CHE_FETCH_READY = MetafieldIdFactory.valueOf((String)"mveTimeCarryCheFetchReady");
    public static final IMetafieldId MVE_TIME_CARRY_CHE_PUT_READY = MetafieldIdFactory.valueOf((String)"mveTimeCarryChePutReady");
    public static final IMetafieldId MVE_TIME_CARRY_CHE_DISPATCH = MetafieldIdFactory.valueOf((String)"mveTimeCarryCheDispatch");
    public static final IMetafieldId MVE_T_Z_ARRIVAL_TIME = MetafieldIdFactory.valueOf((String)"mveTZArrivalTime");
    public static final IMetafieldId MVE_REHANDLE_COUNT = MetafieldIdFactory.valueOf((String)"mveRehandleCount");
    public static final IMetafieldId MVE_TWIN_FETCH = MetafieldIdFactory.valueOf((String)"mveTwinFetch");
    public static final IMetafieldId MVE_TWIN_CARRY = MetafieldIdFactory.valueOf((String)"mveTwinCarry");
    public static final IMetafieldId MVE_TWIN_PUT = MetafieldIdFactory.valueOf((String)"mveTwinPut");
    public static final IMetafieldId MVE_RESTOW_ACCOUNT = MetafieldIdFactory.valueOf((String)"mveRestowAccount");
    public static final IMetafieldId MVE_SERVICE_ORDER = MetafieldIdFactory.valueOf((String)"mveServiceOrder");
    public static final IMetafieldId MVE_RESTOW_REASON = MetafieldIdFactory.valueOf((String)"mveRestowReason");
    public static final IMetafieldId MVE_PROCESSED = MetafieldIdFactory.valueOf((String)"mveProcessed");
    public static final IMetafieldId MVE_P_O_W = MetafieldIdFactory.valueOf((String)"mvePOW");
    public static final IMetafieldId CHE_MOVES = MetafieldIdFactory.valueOf((String)"cheMoves");
    public static final IMetafieldId MVE_CHE_CARRY_LOGIN_NAME = MetafieldIdFactory.valueOf((String)"mveCheCarryLoginName");
    public static final IMetafieldId MVE_CHE_PUT_LOGIN_NAME = MetafieldIdFactory.valueOf((String)"mveChePutLoginName");
    public static final IMetafieldId MVE_CHE_FETCH_LOGIN_NAME = MetafieldIdFactory.valueOf((String)"mveCheFetchLoginName");
    public static final IMetafieldId MVE_BERTH = MetafieldIdFactory.valueOf((String)"mveBerth");
    public static final IMetafieldId MVE_CATEGORY = MetafieldIdFactory.valueOf((String)"mveCategory");
    public static final IMetafieldId MVE_FREIGHT_KIND = MetafieldIdFactory.valueOf((String)"mveFreightKind");
    public static final IMetafieldId MVEU_ID = MetafieldIdFactory.valueOf((String)"mveuId");
    public static final IMetafieldId MVEU_MOVE_KIND = MetafieldIdFactory.valueOf((String)"mveuMoveKind");
    public static final IMetafieldId MVEU_UFV = MetafieldIdFactory.valueOf((String)"mveuUfv");
    public static final IMetafieldId MVEU_APPLIED_DATE = MetafieldIdFactory.valueOf((String)"mveuAppliedDate");
    public static final IMetafieldId MVEU_EVENT_TYPE_ID = MetafieldIdFactory.valueOf((String)"mveuEventTypeId");
    public static final IMetafieldId POS_ANCHOR = MetafieldIdFactory.valueOf((String)"posAnchor");
    public static final IMetafieldId POS_LOC_GKEY = MetafieldIdFactory.valueOf((String)"posLocGkey");
    public static final IMetafieldId POS_LOC_ID = MetafieldIdFactory.valueOf((String)"posLocId");
    public static final IMetafieldId POS_LOC_TYPE = MetafieldIdFactory.valueOf((String)"posLocType");
    public static final IMetafieldId POS_NAME = MetafieldIdFactory.valueOf((String)"posName");
    public static final IMetafieldId POS_ORIENTATION = MetafieldIdFactory.valueOf((String)"posOrientation");
    public static final IMetafieldId POS_ORIENTATION_DEGREES = MetafieldIdFactory.valueOf((String)"posOrientationDegrees");
    public static final IMetafieldId POS_SLOT = MetafieldIdFactory.valueOf((String)"posSlot");
    public static final IMetafieldId POS_TIER = MetafieldIdFactory.valueOf((String)"posTier");
    public static final IMetafieldId POS_BIN = MetafieldIdFactory.valueOf((String)"posBin");

}
