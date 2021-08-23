package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IEventType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitEventMap {
    private static final Map<IEventType, ArrayList<EventEnum>> EVENT_MAP = new HashMap<IEventType, ArrayList<EventEnum>>();
    private static final Map<IEventType, IEventType> ROLE_EVENT_MAP = new HashMap<IEventType, IEventType>();
    private static final Map<EqUnitRoleEnum, RoleEnum> ROLE_ENUM_MAP = new HashMap<EqUnitRoleEnum, RoleEnum>();

    private UnitEventMap() {
    }

    public static IEventType getRoleSpecificEvent(IEventType inEventType, EqUnitRoleEnum inEqUnitRole) {
        RoleEnum role = RoleEnum.getRole(inEqUnitRole);
        return role == null ? inEventType : role.getCompatibleEventType(inEventType);
    }

    private static void mapEvents(EventEnum inMainEvent, EventEnum... inCompatibleEvents) {
        ArrayList<EventEnum> list = new ArrayList<EventEnum>(3);
        for (RoleEnum roleEnum : RoleEnum.values()) {
            list.add(roleEnum.index(), inCompatibleEvents[roleEnum.index()]);
        }
        EVENT_MAP.put((IEventType)inMainEvent, list);
        for (EventEnum roleEnum : inCompatibleEvents) {
            ROLE_EVENT_MAP.put((IEventType)roleEnum, (IEventType)inMainEvent);
        }
    }

    public static IEventType getPrimaryEventType(IEventType inRoleEventType) {
        return ROLE_EVENT_MAP.get((Object)inRoleEventType);
    }

    public static boolean containsRoleEvent(IEventType inEventType) {
        return EVENT_MAP.containsKey((Object)inEventType) || ROLE_EVENT_MAP.containsKey((Object)inEventType);
    }

    public static String getEventKey(Serializable inUnitGkey, String inEventId) {
        return inUnitGkey + "-" + inEventId;
    }

    private static void mapRoles() {
        ROLE_ENUM_MAP.put(EqUnitRoleEnum.CARRIAGE, RoleEnum.CARRIAGE);
        ROLE_ENUM_MAP.put(EqUnitRoleEnum.PAYLOAD, RoleEnum.PAYLOAD);
        ROLE_ENUM_MAP.put(EqUnitRoleEnum.ACCESSORY, RoleEnum.ACCESSORY);
        ROLE_ENUM_MAP.put(EqUnitRoleEnum.ACCESSORY_ON_CHS, RoleEnum.ACCESSORY);
    }

    static {
        UnitEventMap.mapRoles();
        UnitEventMap.mapEvents(EventEnum.UNIT_BRING_BACK_INTO_YARD, EventEnum.UNIT_CARRIAGE_BRING_BACK_INTO_YARD, EventEnum.UNIT_PAYLOAD_BRING_BACK_INTO_YARD, EventEnum.UNIT_ACCESSORY_BRING_BACK_INTO_YARD);
        UnitEventMap.mapEvents(EventEnum.UNIT_BRING_BACK_INTO_YARD, EventEnum.UNIT_CARRIAGE_BRING_BACK_INTO_YARD, EventEnum.UNIT_PAYLOAD_BRING_BACK_INTO_YARD, EventEnum.UNIT_ACCESSORY_BRING_BACK_INTO_YARD);
        UnitEventMap.mapEvents(EventEnum.UNIT_BRING_TO_COMMUNITY, EventEnum.UNIT_CARRIAGE_BRING_TO_COMMUNITY, EventEnum.UNIT_PAYLOAD_BRING_TO_COMMUNITY, EventEnum.UNIT_ACCESSORY_BRING_TO_COMMUNITY);
        UnitEventMap.mapEvents(EventEnum.UNIT_DELIVER, EventEnum.UNIT_CARRIAGE_DELIVER, EventEnum.UNIT_PAYLOAD_DELIVER, EventEnum.UNIT_ACCESSORY_DELIVER);
        UnitEventMap.mapEvents(EventEnum.UNIT_DELIVER_REJECTED, EventEnum.UNIT_CARRIAGE_DELIVER_REJECTED, EventEnum.UNIT_PAYLOAD_DELIVER_REJECTED, EventEnum.UNIT_ACCESSORY_DELIVER_REJECTED);
        UnitEventMap.mapEvents(EventEnum.UNIT_DERAMP, EventEnum.UNIT_DERAMP, EventEnum.UNIT_PAYLOAD_DERAMP, EventEnum.UNIT_ACCESSORY_DERAMP);
        UnitEventMap.mapEvents(EventEnum.UNIT_DISCH, EventEnum.UNIT_DISCH, EventEnum.UNIT_PAYLOAD_DISCH, EventEnum.UNIT_ACCESSORY_DISCH);
        UnitEventMap.mapEvents(EventEnum.UNIT_DRAY_IN, EventEnum.UNIT_CARRIAGE_DRAY_IN, EventEnum.UNIT_PAYLOAD_DRAY_IN, EventEnum.UNIT_ACCESSORY_DRAY_IN);
        UnitEventMap.mapEvents(EventEnum.UNIT_IFT_IN, EventEnum.UNIT_CARRIAGE_IFT_IN, EventEnum.UNIT_PAYLOAD_IFT_IN, EventEnum.UNIT_ACCESSORY_IFT_IN);
        UnitEventMap.mapEvents(EventEnum.UNIT_IFT_OUT, EventEnum.UNIT_CARRIAGE_IFT_OUT, EventEnum.UNIT_PAYLOAD_IFT_OUT, EventEnum.UNIT_ACCESSORY_IFT_OUT);
        UnitEventMap.mapEvents(EventEnum.UNIT_IN_GATE, EventEnum.UNIT_CARRIAGE_IN_GATE, EventEnum.UNIT_PAYLOAD_IN_GATE, EventEnum.UNIT_ACCESSORY_IN_GATE);
        UnitEventMap.mapEvents(EventEnum.UNIT_IN_RAIL, EventEnum.UNIT_IN_RAIL, EventEnum.UNIT_PAYLOAD_IN_RAIL, EventEnum.UNIT_ACCESSORY_IN_RAIL);
        UnitEventMap.mapEvents(EventEnum.UNIT_IN_VESSEL, EventEnum.UNIT_IN_VESSEL, EventEnum.UNIT_PAYLOAD_IN_VESSEL, EventEnum.UNIT_ACCESSORY_IN_VESSEL);
        UnitEventMap.mapEvents(EventEnum.UNIT_LOAD, EventEnum.UNIT_LOAD, EventEnum.UNIT_PAYLOAD_LOAD, EventEnum.UNIT_LOAD);
        UnitEventMap.mapEvents(EventEnum.UNIT_OUT_GATE, EventEnum.UNIT_CARRIAGE_OUT_GATE, EventEnum.UNIT_PAYLOAD_OUT_GATE, EventEnum.UNIT_ACCESSORY_OUT_GATE);
        UnitEventMap.mapEvents(EventEnum.UNIT_OUT_RAIL, EventEnum.UNIT_OUT_RAIL, EventEnum.UNIT_PAYLOAD_OUT_RAIL, EventEnum.UNIT_ACCESSORY_OUT_RAIL);
        UnitEventMap.mapEvents(EventEnum.UNIT_OUT_VESSEL, EventEnum.UNIT_OUT_VESSEL, EventEnum.UNIT_PAYLOAD_OUT_VESSEL, EventEnum.UNIT_OUT_VESSEL);
        UnitEventMap.mapEvents(EventEnum.UNIT_POSITION_CORRECTION, EventEnum.UNIT_CARRIAGE_POSITION_CORRECTION, EventEnum.UNIT_PAYLOAD_POSITION_CORRECTION, EventEnum.UNIT_ACCESSORY_POSITION_CORRECTION);
        UnitEventMap.mapEvents(EventEnum.UNIT_RAMP, EventEnum.UNIT_RAMP, EventEnum.UNIT_PAYLOAD_RAMP, EventEnum.UNIT_ACCESSORY_RAMP);
        UnitEventMap.mapEvents(EventEnum.UNIT_RECEIVE, EventEnum.UNIT_CARRIAGE_RECEIVE, EventEnum.UNIT_PAYLOAD_RECEIVE, EventEnum.UNIT_ACCESSORY_RECEIVE);
        UnitEventMap.mapEvents(EventEnum.UNIT_REROUTE, EventEnum.UNIT_REROUTE, EventEnum.UNIT_PAYLOAD_REROUTE, EventEnum.UNIT_ACCESSORY_REROUTE);
        UnitEventMap.mapEvents(EventEnum.UNIT_RETURN_TO_INBOUND_CARRIER, EventEnum.UNIT_RETURN_TO_INBOUND_CARRIER, EventEnum.UNIT_PAYLOAD_RETURN_TO_INBOUND_CARRIER, EventEnum.UNIT_ACCESSORY_RETURN_TO_INBOUND_CARRIER);
        UnitEventMap.mapEvents(EventEnum.UNIT_ROLL, EventEnum.UNIT_ROLL, EventEnum.UNIT_PAYLOAD_ROLL, EventEnum.UNIT_ROLL);
        UnitEventMap.mapEvents(EventEnum.UNIT_SHIFT_ON_CARRIER, EventEnum.UNIT_SHIFT_ON_CARRIER, EventEnum.UNIT_PAYLOAD_SHIFT_ON_CARRIER, EventEnum.UNIT_ACCESSORY_SHIFT_ON_CARRIER);
        UnitEventMap.mapEvents(EventEnum.UNIT_YARD_MOVE, EventEnum.UNIT_CARRIAGE_YARD_MOVE, EventEnum.UNIT_PAYLOAD_YARD_MOVE, EventEnum.UNIT_ACCESSORY_YARD_MOVE);
        UnitEventMap.mapEvents(EventEnum.UNIT_YARD_SHIFT, EventEnum.UNIT_CARRIAGE_YARD_SHIFT, EventEnum.UNIT_PAYLOAD_YARD_SHIFT, EventEnum.UNIT_ACCESSORY_YARD_SHIFT);
    }

    private static enum RoleEnum {
        CARRIAGE(0),
        PAYLOAD(1),
        ACCESSORY(2);

        private int _roleIndex;

        private RoleEnum(int inRoleIndex) {
            this._roleIndex = inRoleIndex;
        }

        public static RoleEnum getRole(EqUnitRoleEnum inRole) {
            return (RoleEnum)((Object)ROLE_ENUM_MAP.get((Object)inRole));
        }

        public IEventType getCompatibleEventType(IEventType inEventType) {
            IEventType roleSpecificEvent = null;
            try {
                roleSpecificEvent = (IEventType)((List)EVENT_MAP.get((Object)inEventType)).get(this.index());
            }
            catch (Exception exception) {
                // empty catch block
            }
            return roleSpecificEvent == null ? inEventType : roleSpecificEvent;
        }

        public int index() {
            return this._roleIndex;
        }
    }
}
