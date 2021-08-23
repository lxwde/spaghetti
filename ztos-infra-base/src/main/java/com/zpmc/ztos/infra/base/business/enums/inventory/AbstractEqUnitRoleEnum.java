package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IEqNode;
import com.zpmc.ztos.infra.base.business.interfaces.IEqUnitRoleVisitor;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class AbstractEqUnitRoleEnum extends AtomizedEnum {
    protected AbstractEqUnitRoleEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor);
    }

    protected AbstractEqUnitRoleEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, @Nullable String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public boolean isValidFor(EquipClassEnum inEq1Class, EquipClassEnum inEq2Class) {
        EqUnitRoleCompatibility eqUnitRoles = new EqUnitRoleCompatibility(inEq1Class, inEq2Class);
        this.accept(eqUnitRoles);
        return eqUnitRoles.isCompatible();
    }

    public void accept(IEqUnitRoleVisitor inVisitor) {
        if (EqUnitRoleEnum.PRIMARY == this) {
            inVisitor.visitPrimaryRole();
        } else if (EqUnitRoleEnum.CARRIAGE == this) {
            inVisitor.visitCarriageRole();
        } else if (EqUnitRoleEnum.ACCESSORY == this) {
            inVisitor.visitAccessoryRole();
        } else if (EqUnitRoleEnum.ACCESSORY_ON_CHS == this) {
            inVisitor.visitAccessoryOnChsRole();
        } else if (EqUnitRoleEnum.PAYLOAD == this) {
            inVisitor.visitPayloadRole();
        }
    }

    public EventEnum getEqDetachEvent() {
        DetachEventsHolder detachEventsHolder = new DetachEventsHolder();
        this.accept(detachEventsHolder);
        return detachEventsHolder.getEqUnitEvent();
    }

    public EventEnum getMasterDetachEvent(IEqNode inMasterNode) {
        DetachEventsHolder detachEventsHolder = new DetachEventsHolder(inMasterNode);
        this.accept(detachEventsHolder);
        return detachEventsHolder.getMasterUnitEvent();
    }

    private static class DetachEventsHolder
            implements IEqUnitRoleVisitor {
        private EventEnum _masterUnitEvent;
        private EventEnum _eqUnitEvent;
        private IEqNode _masterNode;

        private DetachEventsHolder() {
        }

        private DetachEventsHolder(IEqNode inMasterNode) {
            this._masterNode = inMasterNode;
        }

        @Override
        public void visitPrimaryRole() {
        }

        @Override
        public void visitCarriageRole() {
            this.setMasterUnitEvent(EventEnum.UNIT_DISMOUNT);
            this.setEqUnitEvent(EventEnum.UNIT_CARRIAGE_DISMOUNT);
        }

        @Override
        public void visitAccessoryRole() {
            this.setMasterUnitEvent(EventEnum.UNIT_DISMOUNT);
            this.setEqUnitEvent(EventEnum.UNIT_CTR_ACCESSORY_DISMOUNT);
        }

        @Override
        public void visitAccessoryOnChsRole() {
            this.setMasterUnitEvent(EventEnum.UNIT_DISMOUNT);
            this.setEqUnitEvent(EventEnum.UNIT_CHS_ACCESSORY_DISMOUNT);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void visitPayloadRole() {
//            if (this._masterNode == null) ** GOTO lbl-1000
//            if (this._masterNode.getNodesInRole(new EqUnitRoleEnum[]{EqUnitRoleEnum.PAYLOAD}).isEmpty()) {
//                this._masterUnitEvent = EventEnum.UNIT_UNBUNDLE;
//            } else lbl-1000:
//            // 2 sources

            {
                this._masterUnitEvent = EventEnum.UNIT_SWIPE;
            }
            this._eqUnitEvent = EventEnum.UNIT_BUNDLE_PAYLOAD_SWIPE;
        }

        public EventEnum getMasterUnitEvent() {
            return this._masterUnitEvent;
        }

        public void setMasterUnitEvent(EventEnum inMasterUnitEvent) {
            this._masterUnitEvent = inMasterUnitEvent;
        }

        public EventEnum getEqUnitEvent() {
            return this._eqUnitEvent;
        }

        public void setEqUnitEvent(EventEnum inEqUnitEvent) {
            this._eqUnitEvent = inEqUnitEvent;
        }
    }

    private static class EqUnitRoleCompatibility
            implements IEqUnitRoleVisitor {
        private EquipClassEnum _eq1Class;
        private EquipClassEnum _eq2Class;
        private boolean _isCompatible;

        EqUnitRoleCompatibility(EquipClassEnum inEq1Class, EquipClassEnum inEq2Class) {
            this._eq1Class = inEq1Class;
            this._eq2Class = inEq2Class;
        }

        @Override
        public void visitPrimaryRole() {
            this._isCompatible = EquipClassEnum.CONTAINER == this._eq1Class && EquipClassEnum.CONTAINER == this._eq2Class || this._eq1Class == null && this._eq2Class == EquipClassEnum.CONTAINER;
        }

        @Override
        public void visitCarriageRole() {
            this._isCompatible = EquipClassEnum.CONTAINER == this._eq1Class && EquipClassEnum.CHASSIS == this._eq2Class || EquipClassEnum.CHASSIS == this._eq1Class && EquipClassEnum.CONTAINER == this._eq2Class || this._eq1Class == null && this._eq2Class == EquipClassEnum.CHASSIS;
        }

        @Override
        public void visitAccessoryRole() {
            this._isCompatible = EquipClassEnum.CONTAINER == this._eq1Class && EquipClassEnum.ACCESSORY == this._eq2Class || EquipClassEnum.ACCESSORY == this._eq1Class && EquipClassEnum.CONTAINER == this._eq2Class;
        }

        @Override
        public void visitAccessoryOnChsRole() {
            this._isCompatible = EquipClassEnum.CHASSIS == this._eq1Class && EquipClassEnum.ACCESSORY == this._eq2Class || EquipClassEnum.ACCESSORY == this._eq1Class && EquipClassEnum.CHASSIS == this._eq2Class;
        }

        @Override
        public void visitPayloadRole() {
            this._isCompatible = EquipClassEnum.CHASSIS == this._eq1Class && EquipClassEnum.CHASSIS == this._eq2Class || EquipClassEnum.CONTAINER == this._eq1Class && EquipClassEnum.CONTAINER == this._eq2Class || EquipClassEnum.CHASSIS == this._eq1Class && EquipClassEnum.CONTAINER == this._eq2Class || EquipClassEnum.CONTAINER == this._eq1Class && EquipClassEnum.CHASSIS == this._eq2Class;
        }

        public boolean isCompatible() {
            return this._isCompatible;
        }
    }
}
