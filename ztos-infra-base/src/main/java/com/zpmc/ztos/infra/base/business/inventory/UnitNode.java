package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.IEqLinkContext;
import com.zpmc.ztos.infra.base.business.interfaces.ILinkContext;
import com.zpmc.ztos.infra.base.business.interfaces.INodeLinkedList;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class UnitNode extends AbstractEqNode<UnitNode, Unit, Equipment>{
    private static final Logger LOGGER = Logger.getLogger(UnitNode.class);

    public UnitNode(Unit inUnit) {
        super(inUnit.getUnitEquipment(), inUnit);
    }

    public UnitNode(Equipment inEq) {
        super(inEq, null);
    }

    @Nullable
    public UnitNode getNext() {
        Unit unit = this.getUnit();
        if (unit == null) {
            return null;
        }
        Unit relatedUnit = unit.getUnitRelatedUnit();
        if (relatedUnit == null) {
            return null;
        }
        return relatedUnit.getUnitNode();
    }

    protected INodeLinkedList<UnitNode, Unit, Equipment> internalCreateParent() {
        UnitCombo unitCombo = UnitCombo.internalCreate();
        return (INodeLinkedList<UnitNode, Unit, Equipment>) new UnitNodeCombo(unitCombo);
    }

    protected void setInternalNext(UnitNode inNextNode) {
        Unit nextUnit = inNextNode == null ? null : inNextNode.getUnit();
        UnitNode currentNext = this.getNext() == null ? null : this.getNext();
        boolean isMounted = currentNext != null && EqUnitRoleEnum.CARRIAGE.equals((Object)currentNext.getRole());
        this.getUnit().setUnitRelatedUnit(nextUnit);
        if (isMounted) {
            this.getUnit().setUnitCarriageUnit(nextUnit);
        }
    }

    public Unit getUnit() {
        return (Unit)this.getValue();
    }

    public Equipment getEquipment() {
        return this.getKey();
    }

    @Override
    @Nullable
    public EquipClassEnum getEqClass() {
        if (this.getEquipment() != null) {
            return this.getEquipment().getEqClass();
        }
        if (this.getUnit() == null) {
            LOGGER.error((Object)"Unit & Equipment both are null, can't detemine equipment class");
            return null;
        }
        Equipment eq = this.getUnit().getUnitEquipment();
        return eq == null ? null : eq.getEqClass();
    }

    @Nullable
    public INodeLinkedList<UnitNode, Unit, Equipment> getParent() {
        Object parent = super.getParent();
        if (parent != null) {
            return (INodeLinkedList<UnitNode, Unit, Equipment>) parent;
        }
        if (this.getUnit() != null && this.getUnit().getUnitCombo() != null) {
            parent = new UnitNodeCombo(this.getUnit().getUnitCombo());
            this.setParent((INodeLinkedList)parent);
        }
        return (INodeLinkedList<UnitNode, Unit, Equipment>) parent;
    }

    @Nullable
    public Equipment getKey() {
        Equipment eq = (Equipment)super.getKey();
        return eq == null ? (this.getUnit() == null ? null : this.getUnit().getUnitEquipment()) : eq;
    }

    @Override
    @Nullable
    public EqUnitRoleEnum getRole() {
        return super.getRole() == null ? (this.getUnit() == null ? null : this.getUnit().getUnitEqRole()) : super.getRole();
    }

    @Override
    public void setInternalRole(EqUnitRoleEnum inRole) {
        if (this.getUnit() == null) {
            return;
        }
        this.getUnit().setUnitEqRole(inRole);
    }

    protected void setInternalParent(INodeLinkedList<UnitNode, Unit, Equipment> inParent) {
        if (inParent == null) {
            ((Unit)this.getValue()).setUnitCombo(null);
            return;
        }
        UnitNodeCombo parent = (UnitNodeCombo)inParent;
        this.getUnit().setUnitCombo(parent.getUnitCombo());
    }

    public boolean hasParent() {
        boolean hasCombo;
        boolean bl = hasCombo = this.getUnit() != null && this.getUnit().getUnitCombo() != null;
        if (!hasCombo) {
            return false;
        }
        if (this.getUnit().getUnitCombo().getUcUnitsNullSafe().isEmpty()) {
            LOGGER.warn((Object)("No units found in combo for unit " + this.getUnit()));
            return false;
        }
        LOGGER.warn((Object)("Units in combo: " + this.getUnit().getUnitCombo().getUcUnitsNullSafe()));
        if (!this.getUnit().getUnitCombo().getUcUnitsNullSafe().contains(this.getUnit())) {
            LOGGER.warn((Object)(this.getUnit() + " not found in combo"));
            return false;
        }
        return true;
    }

    @Override
    protected void doOtherUpdatesPostLink(IEqLinkContext<UnitNode, Unit, Equipment> inContext) throws BizViolation {
        this.getUnit().doOtherUpdatesPostAttach(inContext);
    }

    @Override
    protected void doOtherUpdatesPostUnlink(EqUnitRoleEnum inOldRole, UnitNode inRelatedNode) throws BizViolation {
        Unit relatedUnit = inRelatedNode == null ? null : inRelatedNode.getUnit();
        this.getUnit().updateUnitPropertiesPostDetach(inOldRole, relatedUnit);
    }

    @Override
    protected void recordLinkEvent(EventEnum inEvent, UnitNode inRelatedNode) {
        this.getUnit().recordAttachEvent(inEvent, inRelatedNode);
    }

    @Override
    protected void recordUnlinkEvent(EventEnum inEvent, UnitNode inRelatedNode, EqUnitRoleEnum inOldRole, String inEventNotes) {
        this.getUnit().recordDetachEvent(inEvent, inRelatedNode, inOldRole, inEventNotes);
    }

    protected void setInternalKey(@NotNull Equipment inEquipment) {
        if (this.getUnit() == null) {
            return;
        }
        this.getUnit().setUnitEquipment(inEquipment);
    }

    protected UnitNode findNodeByKey(ILinkContext<UnitNode, Unit, Equipment> inLinkContext, Equipment inEquipment) {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        return this.getUnit().findUnitByEquipment((IEqLinkContext)inLinkContext, inEquipment);
    }

    @Override
    protected void validateLink(@NotNull ILinkContext<UnitNode, Unit, Equipment> inLinkContext) throws BizViolation {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        IEqLinkContext context = (IEqLinkContext)inLinkContext;
        super.validateLink(inLinkContext);
        ((Unit)context.getMasterUnit()).validateUnitAttach(context);
    }

    public String getKeyAsString() {
        return this.getEquipment().getEqIdFull();
    }

    @Override
    protected String getMessage(IPropertyKey inPropertyKey, Object[] inParms) {
        return UnitEquipment.getMessage(inPropertyKey, inParms);
    }

    @Nullable
    public UnitNode getNextAttached() {
        if (this.getNext() == null) {
            return null;
        }
        Unit unit = this.getNext().getUnit();
        if (unit == null) {
            return null;
        }
        return this.getNext().isLive() ? this.getNext() : null;
    }

    public boolean isLive() {
        return this.getUnit() == null || this.getUnit().isLiveUnit();
    }

    protected Unit createValue(Equipment inEquipment) throws BizViolation {
        return this.getUnit().createUnitForAttachedEq(inEquipment);
    }

    private static class UnitNodeCombo
    //        extends AbstractNodeLinkedList<UnitNode, Unit, Equipment>
    {
        private UnitCombo _unitCombo;

        UnitNodeCombo(UnitCombo inUnitCombo) {
            this.setUnitCombo(inUnitCombo);
        }

//        protected INodeLinkedListSource<UnitNode, Unit, Equipment> getLinkedListSource() {
//            return this.getUnitCombo();
//        }

        private void setUnitCombo(UnitCombo inUnitCombo) {
            this._unitCombo = inUnitCombo;
        }

        public UnitCombo getUnitCombo() {
            return this._unitCombo;
        }
    }
}
