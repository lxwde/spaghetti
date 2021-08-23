package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.inventory.*;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;

public class SparcsInventoryUtils {
    private static final char WHEELS = 'W';
    private static final char GROUND = 'G';
    private static final char NONE = '-';
    private static final Character DECK_WHEELS = new Character('W');
    private static final Character DECK_GROUND = new Character('G');
    private static final Character DECK_NONE = new Character('-');
    private static final char EC_PRIORITY_DISPATCH = 'P';
    private static final char EC_PRIORITY_FETCH = 'H';
    private static final char EC_STANDARD_FETCH = 'D';
    private static final char EC_IMMINENT_MOVE = 'I';
    private static final char EC_AUTO_FETCH = 'A';
    private static final char EC_NONE = '-';
    private static final Character ECST_PRIORITY_DISPATCH = new Character('P');
    private static final Character ECST_PRIORITY_FETCH = new Character('H');
    private static final Character ECST_STANDARD_FETCH = new Character('D');
    private static final Character ECST_IMMINENT_MOVE = new Character('I');
    private static final Character ECST_AUTO_FETCH = new Character('A');
    private static final Character ECST_NONE = new Character('-');
    private static final Long MOVE_STAGE_NONE = new Long(7L);
    private static final Long MOVE_STAGE_PLANNED = new Long(0L);
    private static final Long MOVE_STAGE_FETCH_UNDERWAY = new Long(5L);
    private static final Long MOVE_STAGE_CARRY_READY = new Long(1L);
    private static final Long MOVE_STAGE_CARRY_UNDERWAY = new Long(2L);
    private static final Long MOVE_STAGE_CARRY_COMPLETE = new Long(3L);
    private static final Long MOVE_STAGE_PUT_UNDERWAY = new Long(6L);
    private static final Long MOVE_STAGE_PUT_COMPLETE = new Long(8L);
    private static final Long MOVE_STAGE_COMPLETE = new Long(4L);
    private static final char SP_CANCEL_REQUEST = 'C';
    private static final char SP_BYPASS = 'B';
    private static final char SP_SYSTEM_BYPASS = 'U';
    private static final char SP_SUSPEND = 'S';
    private static final char SP_NONE = '-';
    private static final Character SPND_CANCEL_REQUEST = new Character('C');
    private static final Character SPND_BYPASS = new Character('B');
    private static final Character SPND_SYSTEM_BYPASS = new Character('U');
    private static final Character SPND_SUSPEND = new Character('S');
    private static final Character SPND_NONE = new Character('-');
    private static final Logger LOGGER = Logger.getLogger(SparcsInventoryUtils.class);

    public static LocTypeEnum carrierQual2CarrierMode(char inCq) {
        switch (inCq) {
            case 'T': {
                return LocTypeEnum.TRUCK;
            }
            case 'V': {
                return LocTypeEnum.VESSEL;
            }
            case 'R': {
                return LocTypeEnum.TRAIN;
            }
            case ' ': {
                return LocTypeEnum.UNKNOWN;
            }
        }
        return LocTypeEnum.UNKNOWN;
    }

    public static VslDeckRqmntEnum deckBool2DeckRqmnt(char inDeckRqmnt) {
        switch (inDeckRqmnt) {
            case '2': {
                return VslDeckRqmntEnum.EITHER;
            }
            case ' ': {
                return VslDeckRqmntEnum.EITHER;
            }
            case '1': {
                return VslDeckRqmntEnum.ABOVE;
            }
            case '3': {
                return VslDeckRqmntEnum.BELOW;
            }
        }
        LOGGER.error((Object)("carrierQual2CarrierMode: Unknown Deck Rqmnt = " + inDeckRqmnt));
        return VslDeckRqmntEnum.EITHER;
    }

    public static FreightKindEnum getN4FreightKind(char inSq) {
        switch (inSq) {
            case 'F': {
                return FreightKindEnum.FCL;
            }
            case 'L': {
                return FreightKindEnum.LCL;
            }
            case 'E': {
                return FreightKindEnum.MTY;
            }
            case 'K': {
                return FreightKindEnum.FCL;
            }
            case 'U': {
                return FreightKindEnum.FCL;
            }
        }
        LOGGER.error((Object)("carrierQual2CarrierMode: Unknown Status Qualifier = " + inSq));
        return FreightKindEnum.FCL;
    }

    public static void setUfvPerXpsCategory(char inXpsCategory, UnitFacilityVisit inUfv) {
        switch (inXpsCategory) {
            case 'E': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.EXPORT);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'I': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.IMPORT);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'T': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.TRANSSHIP);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'D': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.DOMESTIC);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'M': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.STORAGE);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'S': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.THROUGH);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            case 'R': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.THROUGH);
                inUfv.setUfvRestowType(RestowTypeEnum.RESTOW);
                break;
            }
            case 'G': {
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.THROUGH);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
                break;
            }
            default: {
                String unitId = inUfv.getUfvUnit().getUnitId();
                LOGGER.error((Object)("carrierQual2CarrierMode: Unknown Category = " + inXpsCategory + " for Unit " + unitId));
                inUfv.getUfvUnit().setUnitCategory(UnitCategoryEnum.STORAGE);
                inUfv.setUfvRestowType(RestowTypeEnum.NONE);
            }
        }
    }

    public static char getXpsCategory(UnitCategoryEnum inUc, RestowTypeEnum inRestowType) {
        if (RestowTypeEnum.RESTOW.equals((Object)inRestowType)) {
            return 'R';
        }
        if (RestowTypeEnum.SHIFT.equals((Object)inRestowType)) {
            return 'R';
        }
        if (UnitCategoryEnum.EXPORT.equals((Object)inUc)) {
            return 'E';
        }
        if (UnitCategoryEnum.IMPORT.equals((Object)inUc)) {
            return 'I';
        }
        if (UnitCategoryEnum.TRANSSHIP.equals((Object)inUc)) {
            return 'T';
        }
        if (UnitCategoryEnum.DOMESTIC.equals((Object)inUc)) {
            return 'D';
        }
        if (UnitCategoryEnum.STORAGE.equals((Object)inUc)) {
            return 'M';
        }
        if (UnitCategoryEnum.THROUGH.equals((Object)inUc)) {
            return 'S';
        }
        LOGGER.error((Object)("unitCateory2XpsCategory: unhandled category: " + (Object)inUc));
        return 'M';
    }

    public static LocTypeEnum getN4LocationQualifier(char inPq, boolean inComplainIfBad) {
        switch (inPq) {
            case 'T': {
                return LocTypeEnum.TRUCK;
            }
            case 'V': {
                return LocTypeEnum.VESSEL;
            }
            case 'R': {
                return LocTypeEnum.TRAIN;
            }
            case 'Y': {
                return LocTypeEnum.YARD;
            }
            case 'C': {
                return LocTypeEnum.COMMUNITY;
            }
        }
        if (inComplainIfBad) {
            LOGGER.error((Object)("getN4LocationQualifier: Unknown Loc Qualifier = " + inPq));
        }
        return LocTypeEnum.UNKNOWN;
    }

    public static Character getXpsLocationQualifier(LocTypeEnum inLocType) {
        if (LocTypeEnum.YARD.equals((Object)inLocType)) {
            return new Character((char) 111); //ISparcsConsts.LOCQ_YARD;
        }
        if (LocTypeEnum.VESSEL.equals((Object)inLocType)) {
            return new Character((char) 111); //ISparcsConsts.LOCQ_VESSEL;
        }
        if (LocTypeEnum.TRUCK.equals((Object)inLocType)) {
            return new Character((char) 111); //ISparcsConsts.LOCQ_TRUCK;
        }
        if (LocTypeEnum.TRAIN.equals((Object)inLocType) || LocTypeEnum.RAILCAR.equals((Object)inLocType)) {
            return new Character((char) 111); //ISparcsConsts.LOCQ_RAIL;
        }
        if (LocTypeEnum.COMMUNITY.equals((Object)inLocType)) {
            return new Character((char) 111); //ISparcsConsts.LOCQ_COMMUNITY;
        }
        return new Character((char) 111); //ISparcsConsts.LOCQ_UNKNOWN;
    }

    public static DoorDirectionEnum getN4DoorDirection(Long inDoorDir) {
//        if (ISparcsConsts.L_DOOR_UNK.equals(inDoorDir)) {
//            return DoorDirectionEnum.UNKNOWN;
//        }
//        if (ISparcsConsts.L_DOOR_ANY.equals(inDoorDir)) {
//            return DoorDirectionEnum.ANY;
//        }
//        if (ISparcsConsts.L_DOOR_FWD.equals(inDoorDir)) {
//            return DoorDirectionEnum.FWD;
//        }
//        if (ISparcsConsts.L_DOOR_AFT.equals(inDoorDir)) {
//            return DoorDirectionEnum.AFT;
//        }
//        if (ISparcsConsts.L_DOOR_NORTH.equals(inDoorDir)) {
//            return DoorDirectionEnum.NORTH;
//        }
//        if (ISparcsConsts.L_DOOR_SOUTH.equals(inDoorDir)) {
//            return DoorDirectionEnum.SOUTH;
//        }
//        if (ISparcsConsts.L_DOOR_EAST.equals(inDoorDir)) {
//            return DoorDirectionEnum.EAST;
//        }
//        if (ISparcsConsts.L_DOOR_WEST.equals(inDoorDir)) {
//            return DoorDirectionEnum.WEST;
//        }
        return DoorDirectionEnum.UNKNOWN;
    }

    public static Long getXpsDoorDirection(DoorDirectionEnum inDoorEnum) {
        if (DoorDirectionEnum.ANY.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_ANY;
        }
        if (DoorDirectionEnum.FWD.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_FWD;
        }
        if (DoorDirectionEnum.AFT.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_AFT;
        }
        if (DoorDirectionEnum.NORTH.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_NORTH;
        }
        if (DoorDirectionEnum.SOUTH.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_SOUTH;
        }
        if (DoorDirectionEnum.EAST.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_EAST;
        }
        if (DoorDirectionEnum.WEST.equals((Object)inDoorEnum)) {
            return new Long(111);//ISparcsConsts.L_DOOR_WEST;
        }
        return new Long(111);//ISparcsConsts.L_DOOR_UNK;
    }

    public static DoorDirectionEnum doorBools2DoorDirectionEnum(boolean inDoorCare, boolean inDoorFwd) {
        if (!inDoorCare) {
            return DoorDirectionEnum.ANY;
        }
        if (inDoorFwd) {
            return DoorDirectionEnum.FWD;
        }
        return DoorDirectionEnum.AFT;
    }

    public static CarryPositionEnum getN4CarryPosition(long inLongCarryPosition) {
        if (inLongCarryPosition == 1L) {
            return CarryPositionEnum.FWD;
        }
        if (inLongCarryPosition == -1L) {
            return CarryPositionEnum.AFT;
        }
        return CarryPositionEnum.UNKNOWN;
    }

    public static long getXpsCarryPosition(CarryPositionEnum inCarryPositionEnum) {
        if (inCarryPositionEnum == CarryPositionEnum.FWD) {
            return 1L;
        }
        if (inCarryPositionEnum == CarryPositionEnum.AFT) {
            return -1L;
        }
        return 0L;
    }

    public static String getXpsMoveKind(WiMoveKindEnum inWiMoveKind) {
        return inWiMoveKind == null ? WiMoveKindEnum.Other.getKey() : inWiMoveKind.getKey();
    }

    public static WiMoveKindEnum getN4MoveKind(String inMoveKind) {
        return StringUtils.isEmpty((String)inMoveKind) ? WiMoveKindEnum.Other : WiMoveKindEnum.getEnum((String)inMoveKind);
    }

    public static String stripCheckDigitIfNeeded(String inCtrID) {
        if (inCtrID == null) {
            return inCtrID;
        }
        String result = inCtrID;
        UserContext userContext = ContextHelper.getThreadUserContext();
        boolean stripCheckDigit = InventoryConfig.STRIP_CHECK_DIGIT.isOn(userContext);
        if (stripCheckDigit && inCtrID.length() > 10) {
            result = inCtrID.substring(0, 10);
        }
        return result;
    }

    public static DeckingRestrictionEnum getN4DeckingRestriction(Character inDeckRestrict) {
        switch (inDeckRestrict.charValue()) {
            case 'W': {
                return DeckingRestrictionEnum.WHEELS;
            }
            case 'G': {
                return DeckingRestrictionEnum.GROUND;
            }
        }
        return DeckingRestrictionEnum.NONE;
    }

    public static Character getXpsDeckingRestriction(DeckingRestrictionEnum inDeckRestrict) {
        if (DeckingRestrictionEnum.WHEELS.equals((Object)inDeckRestrict)) {
            return DECK_WHEELS;
        }
        if (DeckingRestrictionEnum.GROUND.equals((Object)inDeckRestrict)) {
            return DECK_GROUND;
        }
        return DECK_NONE;
    }

    public static DeckingRestrictionEnum deckingBools2DeckingRestrictionEnum(boolean inRestrictToWheels, boolean inRestrictToGround) {
        if (inRestrictToWheels) {
            return DeckingRestrictionEnum.WHEELS;
        }
        if (inRestrictToGround) {
            return DeckingRestrictionEnum.GROUND;
        }
        return DeckingRestrictionEnum.NONE;
    }

    public static Character getXpsTwinWith(TwinWithEnum inTwinWithEnum) {
        if (TwinWithEnum.PREV.equals((Object)inTwinWithEnum)) {
            return  new Character((char) 111); //ISparcsConsts.TWIN_WITH_PREV;
        }
        if (TwinWithEnum.NEXT.equals((Object)inTwinWithEnum)) {
            return  new Character((char) 111); //ISparcsConsts.TWIN_WITH_NEXT;
        }
        return  new Character((char) 111); //ISparcsConsts.TWIN_WITH_NONE;
    }

    public static TwinWithEnum getN4TwinWith(Character inTwinWithChar) {
//        if (ISparcsConsts.TWIN_WITH_PREV.equals(Character.valueOf(inTwinWithChar.charValue()))) {
//            return TwinWithEnum.PREV;
//        }
//        if (ISparcsConsts.TWIN_WITH_NEXT.equals(Character.valueOf(inTwinWithChar.charValue()))) {
//            return TwinWithEnum.NEXT;
//        }
        return TwinWithEnum.NONE;
    }

    public static TwinWithEnum twinningBools2TwinWithEnum(boolean inTwinWithNext, boolean inTwinWithPrev) {
        if (inTwinWithNext) {
            return TwinWithEnum.NEXT;
        }
        if (inTwinWithPrev) {
            return TwinWithEnum.PREV;
        }
        return TwinWithEnum.NONE;
    }

    public static WiEcStateEnum ecStateBools2WiEcStateEnum(boolean inFetch, boolean inPriorityFetch, boolean inPriorityDispatch) {
        if (inPriorityDispatch) {
            return WiEcStateEnum.PRIORITY_DISPATCH;
        }
        if (inPriorityFetch) {
            return WiEcStateEnum.PRIORITY_FETCH;
        }
        if (inFetch) {
            return WiEcStateEnum.STANDARD_FETCH;
        }
        return WiEcStateEnum.NONE;
    }

    public static WiEcStateEnum getN4EcState(Character inEcState) {
        if (inEcState == null) {
            return WiEcStateEnum.NONE;
        }
        switch (inEcState.charValue()) {
            case 'P': {
                return WiEcStateEnum.PRIORITY_DISPATCH;
            }
            case 'H': {
                return WiEcStateEnum.PRIORITY_FETCH;
            }
            case 'D': {
                return WiEcStateEnum.STANDARD_FETCH;
            }
            case 'I': {
                return WiEcStateEnum.IMMINENT_MOVE;
            }
            case 'A': {
                return WiEcStateEnum.AUTO_FETCH;
            }
        }
        return WiEcStateEnum.NONE;
    }

    public static Character getXpsEcState(WiEcStateEnum inEcState) {
        if (WiEcStateEnum.PRIORITY_DISPATCH.equals((Object)inEcState)) {
            return ECST_PRIORITY_DISPATCH;
        }
        if (WiEcStateEnum.PRIORITY_FETCH.equals((Object)inEcState)) {
            return ECST_PRIORITY_FETCH;
        }
        if (WiEcStateEnum.STANDARD_FETCH.equals((Object)inEcState)) {
            return ECST_STANDARD_FETCH;
        }
        if (WiEcStateEnum.IMMINENT_MOVE.equals((Object)inEcState)) {
            return ECST_IMMINENT_MOVE;
        }
        if (WiEcStateEnum.AUTO_FETCH.equals((Object)inEcState)) {
            return ECST_AUTO_FETCH;
        }
        return ECST_NONE;
    }

    public static WiMoveStageEnum getN4MoveStage(Long inMoveStageLong) {
        return inMoveStageLong == null ? WiMoveStageEnum.NONE : SparcsInventoryUtils.getN4MoveStage(inMoveStageLong.intValue());
    }

    public static WiMoveStageEnum getN4MoveStage(int inMoveStage) {
        switch (inMoveStage) {
            case 7: {
                return WiMoveStageEnum.NONE;
            }
            case 0: {
                return WiMoveStageEnum.PLANNED;
            }
            case 5: {
                return WiMoveStageEnum.FETCH_UNDERWAY;
            }
            case 1: {
                return WiMoveStageEnum.CARRY_READY;
            }
            case 2: {
                return WiMoveStageEnum.CARRY_UNDERWAY;
            }
            case 3: {
                return WiMoveStageEnum.CARRY_COMPLETE;
            }
            case 6: {
                return WiMoveStageEnum.PUT_UNDERWAY;
            }
            case 8: {
                return WiMoveStageEnum.PUT_COMPLETE;
            }
            case 4: {
                return WiMoveStageEnum.COMPLETE;
            }
        }
        return WiMoveStageEnum.NONE;
    }

    public static Long getXpsMoveStage(WiMoveStageEnum inStage) {
        if (WiMoveStageEnum.NONE.equals((Object)inStage)) {
            return MOVE_STAGE_NONE;
        }
        if (WiMoveStageEnum.PLANNED.equals((Object)inStage)) {
            return MOVE_STAGE_PLANNED;
        }
        if (WiMoveStageEnum.FETCH_UNDERWAY.equals((Object)inStage)) {
            return MOVE_STAGE_FETCH_UNDERWAY;
        }
        if (WiMoveStageEnum.CARRY_READY.equals((Object)inStage)) {
            return MOVE_STAGE_CARRY_READY;
        }
        if (WiMoveStageEnum.CARRY_UNDERWAY.equals((Object)inStage)) {
            return MOVE_STAGE_CARRY_UNDERWAY;
        }
        if (WiMoveStageEnum.CARRY_COMPLETE.equals((Object)inStage)) {
            return MOVE_STAGE_CARRY_COMPLETE;
        }
        if (WiMoveStageEnum.PUT_UNDERWAY.equals((Object)inStage)) {
            return MOVE_STAGE_PUT_UNDERWAY;
        }
        if (WiMoveStageEnum.PUT_COMPLETE.equals((Object)inStage)) {
            return MOVE_STAGE_PUT_COMPLETE;
        }
        if (WiMoveStageEnum.COMPLETE.equals((Object)inStage)) {
            return MOVE_STAGE_COMPLETE;
        }
        return MOVE_STAGE_NONE;
    }

    public static WiSuspendStateEnum getN4WiSuspendState(Character inSuspend) {
        if (inSuspend == null) {
            return WiSuspendStateEnum.NONE;
        }
        switch (inSuspend.charValue()) {
            case '-': {
                return WiSuspendStateEnum.NONE;
            }
            case 'C': {
                return WiSuspendStateEnum.CANCEL_REQUEST;
            }
            case 'B': {
                return WiSuspendStateEnum.BYPASS;
            }
            case 'U': {
                return WiSuspendStateEnum.SYSTEM_BYPASS;
            }
            case 'S': {
                return WiSuspendStateEnum.SUSPEND;
            }
        }
        return WiSuspendStateEnum.NONE;
    }

    public static Character getXpsWiSuspendState(WiSuspendStateEnum inSuspend) {
        if (WiSuspendStateEnum.CANCEL_REQUEST.equals((Object)inSuspend)) {
            return SPND_CANCEL_REQUEST;
        }
        if (WiSuspendStateEnum.BYPASS.equals((Object)inSuspend)) {
            return SPND_BYPASS;
        }
        if (WiSuspendStateEnum.SYSTEM_BYPASS.equals((Object)inSuspend)) {
            return SPND_SYSTEM_BYPASS;
        }
        if (WiSuspendStateEnum.SUSPEND.equals((Object)inSuspend)) {
            return SPND_SUSPEND;
        }
        return SPND_NONE;
    }

    public static WiSuspendStateEnum ecSuspendBools2WiSuspendStateEnum(boolean inSuspended, boolean inBypass, boolean inCancelRequested, boolean inCanceledAutoCheDispatch, boolean inCanceledAutomatedJob) {
        if (inCancelRequested) {
            return WiSuspendStateEnum.CANCEL_REQUEST;
        }
        if (inCanceledAutoCheDispatch) {
            return WiSuspendStateEnum.CANCEL_REQUEST;
        }
        if (inCanceledAutomatedJob) {
            return WiSuspendStateEnum.CANCEL_REQUEST;
        }
        if (inBypass) {
            return WiSuspendStateEnum.BYPASS;
        }
        if (inSuspended) {
            return WiSuspendStateEnum.SUSPEND;
        }
        return WiSuspendStateEnum.NONE;
    }

    public static SparcsToolEnum getN4CreateTool(Long inCreatedByTool) {
        return inCreatedByTool == null ? SparcsToolEnum.NO : SparcsInventoryUtils.getN4CreateTool(inCreatedByTool.intValue());
    }

    public static SparcsToolEnum getN4CreateTool(int inCreatedByTool) {
        SparcsToolEnum sparcsTool;
        switch (inCreatedByTool) {
            case 0: {
                sparcsTool = SparcsToolEnum.NO;
                break;
            }
            case 1: {
                sparcsTool = SparcsToolEnum.ARROW;
                break;
            }
            case 2: {
                sparcsTool = SparcsToolEnum.INFO;
                break;
            }
            case 3: {
                sparcsTool = SparcsToolEnum.MAGNIFIER;
                break;
            }
            case 4: {
                sparcsTool = SparcsToolEnum.CORRECTION;
                break;
            }
            case 5: {
                sparcsTool = SparcsToolEnum.CONFIRM;
                break;
            }
            case 26: {
                sparcsTool = SparcsToolEnum.NONCON_CARGO;
                break;
            }
            case 6: {
                sparcsTool = SparcsToolEnum.NONCON_CARGO_PLAN;
                break;
            }
            case 7: {
                sparcsTool = SparcsToolEnum.FILL;
                break;
            }
            case 8: {
                sparcsTool = SparcsToolEnum.RIGHT_TIER;
                break;
            }
            case 9: {
                sparcsTool = SparcsToolEnum.LEFT_TIER;
                break;
            }
            case 10: {
                sparcsTool = SparcsToolEnum.RIGHT_STACK;
                break;
            }
            case 11: {
                sparcsTool = SparcsToolEnum.LEFT_STACK;
                break;
            }
            case 12: {
                sparcsTool = SparcsToolEnum.CENTER_TIER;
                break;
            }
            case 13: {
                sparcsTool = SparcsToolEnum.CENTER_STACK;
                break;
            }
            case 14: {
                sparcsTool = SparcsToolEnum.POWER_FLOW;
                break;
            }
            case 27: {
                sparcsTool = SparcsToolEnum.POWER_SELECT_TWINLIFT;
                break;
            }
            case 15: {
                sparcsTool = SparcsToolEnum.DRAG;
                break;
            }
            case 16: {
                sparcsTool = SparcsToolEnum.LIST;
                break;
            }
            case 17: {
                sparcsTool = SparcsToolEnum.LIVE_RECAP;
                break;
            }
            case 18: {
                sparcsTool = SparcsToolEnum.PROJECTIONS;
                break;
            }
            case 19: {
                sparcsTool = SparcsToolEnum.LEFT_TWINLIFT;
                break;
            }
            case 20: {
                sparcsTool = SparcsToolEnum.RIGHT_TWINLIFT;
                break;
            }
            case 21: {
                sparcsTool = SparcsToolEnum.LEFT_PAIRED20S_LIFT;
                break;
            }
            case 22: {
                sparcsTool = SparcsToolEnum.RIGHT_PAIRED20S_LIFT;
                break;
            }
            case 23: {
                sparcsTool = SparcsToolEnum.PS_PREV_PORTCALL;
                break;
            }
            case 24: {
                sparcsTool = SparcsToolEnum.PS_NEXT_PORTCALL;
                break;
            }
            case 25: {
                sparcsTool = SparcsToolEnum.DAMAGED_SLOT;
                break;
            }
            default: {
                sparcsTool = SparcsToolEnum.NO;
            }
        }
        return sparcsTool;
    }

    public static int getXPSCreateTool(SparcsToolEnum inTool) {
        if (SparcsToolEnum.NO.equals((Object)inTool)) {
            return 0;
        }
        if (SparcsToolEnum.ARROW.equals((Object)inTool)) {
            return 1;
        }
        if (SparcsToolEnum.INFO.equals((Object)inTool)) {
            return 2;
        }
        if (SparcsToolEnum.MAGNIFIER.equals((Object)inTool)) {
            return 3;
        }
        if (SparcsToolEnum.CORRECTION.equals((Object)inTool)) {
            return 4;
        }
        if (SparcsToolEnum.CONFIRM.equals((Object)inTool)) {
            return 5;
        }
        if (SparcsToolEnum.NONCON_CARGO.equals((Object)inTool)) {
            return 26;
        }
        if (SparcsToolEnum.NONCON_CARGO_PLAN.equals((Object)inTool)) {
            return 6;
        }
        if (SparcsToolEnum.FILL.equals((Object)inTool)) {
            return 7;
        }
        if (SparcsToolEnum.RIGHT_TIER.equals((Object)inTool)) {
            return 8;
        }
        if (SparcsToolEnum.LEFT_TIER.equals((Object)inTool)) {
            return 9;
        }
        if (SparcsToolEnum.RIGHT_STACK.equals((Object)inTool)) {
            return 10;
        }
        if (SparcsToolEnum.LEFT_STACK.equals((Object)inTool)) {
            return 11;
        }
        if (SparcsToolEnum.CENTER_TIER.equals((Object)inTool)) {
            return 12;
        }
        if (SparcsToolEnum.CENTER_STACK.equals((Object)inTool)) {
            return 13;
        }
        if (SparcsToolEnum.POWER_FLOW.equals((Object)inTool)) {
            return 14;
        }
        if (SparcsToolEnum.POWER_SELECT_TWINLIFT.equals((Object)inTool)) {
            return 27;
        }
        if (SparcsToolEnum.DRAG.equals((Object)inTool)) {
            return 15;
        }
        if (SparcsToolEnum.LIST.equals((Object)inTool)) {
            return 16;
        }
        if (SparcsToolEnum.LIVE_RECAP.equals((Object)inTool)) {
            return 17;
        }
        if (SparcsToolEnum.PROJECTIONS.equals((Object)inTool)) {
            return 18;
        }
        if (SparcsToolEnum.LEFT_TWINLIFT.equals((Object)inTool)) {
            return 19;
        }
        if (SparcsToolEnum.RIGHT_TWINLIFT.equals((Object)inTool)) {
            return 20;
        }
        if (SparcsToolEnum.LEFT_PAIRED20S_LIFT.equals((Object)inTool)) {
            return 21;
        }
        if (SparcsToolEnum.RIGHT_PAIRED20S_LIFT.equals((Object)inTool)) {
            return 22;
        }
        if (SparcsToolEnum.PS_PREV_PORTCALL.equals((Object)inTool)) {
            return 23;
        }
        if (SparcsToolEnum.PS_NEXT_PORTCALL.equals((Object)inTool)) {
            return 24;
        }
        if (SparcsToolEnum.DAMAGED_SLOT.equals((Object)inTool)) {
            return 25;
        }
        if (SparcsToolEnum.POWER_SELECT_TANDEMLIFT.equals((Object)inTool)) {
            return 28;
        }
        if (SparcsToolEnum.LEFT_TANDEMLIFT.equals((Object)inTool)) {
            return 30;
        }
        if (SparcsToolEnum.RIGHT_TANDEMLIFT.equals((Object)inTool)) {
            return 29;
        }
        if (SparcsToolEnum.POWER_SELECT_QUADLIFT.equals((Object)inTool)) {
            return 31;
        }
        if (SparcsToolEnum.LEFT_QUADLIFT.equals((Object)inTool)) {
            return 33;
        }
        if (SparcsToolEnum.RIGHT_QUADLIFT.equals((Object)inTool)) {
            return 32;
        }
        return 0;
    }

    public static EcHoldEnum getN4EcHold(Long inEcHold) {
        return inEcHold == null ? EcHoldEnum.NONE : SparcsInventoryUtils.getN4EcHold(inEcHold.intValue());
    }

    public static EcHoldEnum getN4EcHold(int inEcHold) {
        EcHoldEnum ecHoldEnum;
        switch (inEcHold) {
            case 0: {
                ecHoldEnum = EcHoldEnum.NOT_YET_EVALUATED;
                break;
            }
            case 1: {
                ecHoldEnum = EcHoldEnum.NONE;
                break;
            }
            case 2: {
                ecHoldEnum = EcHoldEnum.MEN_WORKING;
                break;
            }
            case 3: {
                ecHoldEnum = EcHoldEnum.ON_POWER;
                break;
            }
            case 4: {
                ecHoldEnum = EcHoldEnum.FAIL_TO_DECK;
                break;
            }
            case 5: {
                ecHoldEnum = EcHoldEnum.OVERSTOWED;
                break;
            }
            case 6: {
                ecHoldEnum = EcHoldEnum.SUSPENDED;
                break;
            }
            case 7: {
                ecHoldEnum = EcHoldEnum.YARD_SEQUENCE;
                break;
            }
            case 8: {
                ecHoldEnum = EcHoldEnum.LOAD_SEQUENCE;
                break;
            }
            case 9: {
                ecHoldEnum = EcHoldEnum.DEPENDENT;
                break;
            }
            case 10: {
                ecHoldEnum = EcHoldEnum.PENDING_MOVE;
                break;
            }
            case 11: {
                ecHoldEnum = EcHoldEnum.CHE_LIFT_HEIGHT;
                break;
            }
            case 12: {
                ecHoldEnum = EcHoldEnum.CHE_LIMITATION;
                break;
            }
            case 13: {
                ecHoldEnum = EcHoldEnum.REJECTED;
                break;
            }
            case 14: {
                ecHoldEnum = EcHoldEnum.MULTIPLE_PLANS;
                break;
            }
            case 15: {
                ecHoldEnum = EcHoldEnum.NO_FRAMES;
                break;
            }
            case 16: {
                ecHoldEnum = EcHoldEnum.INFEASIBLE_JOB;
                break;
            }
            case 17: {
                ecHoldEnum = EcHoldEnum.BUFFER_FULL;
                break;
            }
            case 18: {
                ecHoldEnum = EcHoldEnum.MAX_TTS_PER_FETCH_RTG;
                break;
            }
            case 19: {
                ecHoldEnum = EcHoldEnum.MAX_TTS_WITH_NO_RTGS;
                break;
            }
            default: {
                ecHoldEnum = EcHoldEnum.NONE;
            }
        }
        return ecHoldEnum;
    }

    public static Long getXpsEcHold(EcHoldEnum inEcHold) {
        return null;
    }

}
