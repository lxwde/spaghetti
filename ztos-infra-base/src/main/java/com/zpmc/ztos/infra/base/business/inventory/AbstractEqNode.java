package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public abstract class AbstractEqNode<Node extends AbstractEqNode<Node, Value, Key>, Value, Key> extends NodeLinked<Node, Value, Key>
        implements IEqNode<Node, Value, Key> {

    private EqUnitRoleEnum _role;
    private static final Logger LOGGER = Logger.getLogger(AbstractEqNode.class);

    public AbstractEqNode(@NotNull Key inKey, @Nullable Value inValue) {
        super(inKey, inValue);
    }

    @Override
    public EqUnitRoleEnum getRole() {
        return this._role;
    }

    @Override
    public Node attach(Node inNode, EqUnitRoleEnum inRole) throws BizViolation {
        boolean strictlyValidated = DataSourceEnum.SNX != AbstractEqNode.getDataSourceEnum();
        return this.attach(inNode, inRole, true, strictlyValidated, false, true);
    }

    @Override
    public Node attach(Node inNode, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inStrictlyValidated, boolean inMultipleEquipmentAllowed, boolean inEventRecordAllowed) throws BizViolation {
        return this.attach(inNode, inRole, inSwipeIsAllowed, inStrictlyValidated, inMultipleEquipmentAllowed, inEventRecordAllowed, false, false, false, null);
    }

    @Override
    public Node attach(Node inNode, EqUnitRoleEnum inRole, boolean inSwipeIsAllowed, boolean inStrictlyValidated, boolean inMultipleEquipmentAllowed, boolean inEventRecordAllowed, boolean inMatchActiveUnit, boolean inHatchClerkValidation, boolean inValidateLocation, @Nullable String inSlotOnCarriage) throws BizViolation {
//        EqLinkContext context = new EqLinkContext(this, (AbstractEqNode)inNode, inRole, inStrictlyValidated, inSwipeIsAllowed, inMultipleEquipmentAllowed, inEventRecordAllowed, inMatchActiveUnit, AbstractEqNode.getDataSourceEnum(), inHatchClerkValidation, inValidateLocation, inSlotOnCarriage);
//        try {
//            ((AbstractEqNode)context.getNode1()).link(context);
//        }
//        catch (AlreadyLinkedException e) {
//            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNITS__EQUIP_ALREADY_ATTACHED, null, (Object)inNode.getKeyAsString(), (Object)this.getValue());
//        }
//        catch (BidirectionalLinkException e) {
//            throw BizViolation.create((IPropertyKey)IInventoryPropertyKeys.UNIT_ATTACH_CANNOT_BE_BIDIRECTIONAL, null, (Object)inNode.getKeyAsString(), (Object)this.getKeyAsString(), (Object)((Object)this.getRole()));
//        }
//        return (Node)context.getReturnNode();
        return null;
    }

    protected void linkNode(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        IEqLinkContext context = (IEqLinkContext)inLinkContext;
        if (context.getRole() == EqUnitRoleEnum.PRIMARY) {
            this.setKey((Key) ((AbstractEqNode)context.getNode2()).getKey());
            this.primaryEquipmentChanged(this.getValue());
        } else {
            super.linkNode(inLinkContext);
        }
    }

    protected void validateLink(@NotNull ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        IEqLinkContext linkContext = (IEqLinkContext)inLinkContext;
        this.validateIfRoleCompatible(linkContext);
        this.validateIfRoleInUse(linkContext);
        this.validateIfNodeIsInUse(linkContext);
    }

    private void validateIfRoleCompatible(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        AbstractEqNode node = (AbstractEqNode)inLinkContext.getNode2();
        if (inLinkContext.getRole().isValidFor(this.getEqClass(), node.getEqClass())) {
            return;
        }
        if (this.getEqClass() == EquipClassEnum.CHASSIS && node.getEqClass() == EquipClassEnum.CHASSIS) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.BARE_CHASSIS_CAN_NOT_HAVE_CARRIAGE_CHASSIS, null, (Object)((AbstractEqNode)inLinkContext.getNode2()).getKey(), (Object)((AbstractEqNode)inLinkContext.getNode1()).getKey());
        }
        throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_CANNOT_ATTACH_EQ_IN_ROLE, null, (Object)this.getKey(), (Object)((Object)inLinkContext.getRole()));
    }

    private void validateIfNodeIsInUse(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
//        if (inLinkContext.getNodeInUseValidationRule() != NodeSwipeRuleEnum.VALIDATE) {
//            return;
//        }
        Node nodeInUse = this.getNodeInUse(inLinkContext);
        if (nodeInUse != null) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__EQUIP_IN_USE, null, (Object)((AbstractEqNode)inLinkContext.getReturnNode()).getKeyAsString(), inLinkContext.getMasterUnit(), (Object)nodeInUse.getKeyAsString());
        }
    }

    private void validateIfRoleInUse(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
//        if (inLinkContext.getRoleInUseValidationRule() != NodeSwipeRuleEnum.VALIDATE) {
//            return;
//        }
        Node nodeInUse = this.getNodeInSameRole(inLinkContext);
        if (nodeInUse != null) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNITS__ROLE_IN_USE, null, (Object)((AbstractEqNode)inLinkContext.getReturnNode()).getKeyAsString(), (Object)this, (Object)nodeInUse.getKeyAsString());
        }
    }

    protected void beforeLinking(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        IEqLinkContext context = (IEqLinkContext)inLinkContext;
        this.swipeNodesInUseIfNeeded(context);
        this.swipeNodesInSameRoleIfNeeded(context);
    }

    private void swipeNodesInUseIfNeeded(IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
//        if (inLinkContext.getNodeInUseValidationRule() != NodeSwipeRuleEnum.SWIPE) {
//            return;
//        }
        for (AbstractEqNode node : inLinkContext.getNodesInUse()) {
            String swipeNotes = this.getMessage(IInventoryPropertyKeys.UNIT_SWIPE_NOTES, new Object[]{node.getKeyAsString(), ((AbstractEqNode)inLinkContext.getReturnNode()).getKeyAsString()});
            UnlinkContext unlinkContext = new UnlinkContext(inLinkContext.getReturnNode(), swipeNotes);
            node.detach((IUnlinkContext<Node, Value, Key>)unlinkContext);
        }
    }

    private void swipeNodesInSameRoleIfNeeded(IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
//        if (inLinkContext.getRoleInUseValidationRule() != NodeSwipeRuleEnum.SWIPE) {
//            return;
//        }
        for (AbstractEqNode node : inLinkContext.getNodesInSameRole()) {
            AbstractEqNode unlinkFromNode = EqUnitRoleEnum.CARRIAGE == inLinkContext.getRole() ? (AbstractEqNode)inLinkContext.getNode1() : (AbstractEqNode)inLinkContext.getNode2();
            String swipeNotes = this.getMessage(IInventoryPropertyKeys.UNIT_SWIPE_NOTES, new Object[]{node.getKeyAsString(), ((AbstractEqNode)inLinkContext.getReturnNode()).getKeyAsString()});
            UnlinkContext unlinkContext = new UnlinkContext((INodeLinked)unlinkFromNode, swipeNotes);
            node.detach((IUnlinkContext<Node, Value, Key>)unlinkContext);
        }
    }

    protected void afterLinking(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        if (!(inLinkContext instanceof IEqLinkContext)) {
            throw BizFailure.createProgrammingFailure((String)"inLinkContext is not of type IEqLinkContext");
        }
        IEqLinkContext context = (IEqLinkContext)inLinkContext;
        this.reviseRolePostAttach(context);
        AbstractEqNode node1 = (AbstractEqNode)inLinkContext.getNode1();
        AbstractEqNode node2 = (AbstractEqNode)inLinkContext.getNode2();
        ((AbstractEqNode)context.getNode1()).doOtherUpdatesPostLink(context);
        if (context.isEventRecordAllowed()) {
            if (context.getNode1Event() != null) {
                node1.recordLinkEvent(context.getNode1Event(), node2);
            }
            if (context.getNode2Event() != null) {
                node2.recordLinkEvent(context.getNode2Event(), node1);
            }
        }
    }

    void setRole(EqUnitRoleEnum inRole) {
        this._role = inRole;
        this.setInternalRole(inRole);
    }

    protected void reviseRolePostAttach(IEqLinkContext<Node, Value, Key> inLinkContext) {
        ((AbstractEqNode)inLinkContext.getReturnNode()).setRole(inLinkContext.getRole());
        if (inLinkContext.getRole() == EqUnitRoleEnum.CARRIAGE && ((AbstractEqNode)inLinkContext.getMasterNode()).getRole() == null) {
            ((AbstractEqNode)inLinkContext.getMasterNode()).setRole(EqUnitRoleEnum.PRIMARY);
        }
        if (EqUnitRoleEnum.PAYLOAD == inLinkContext.getRole() && EquipClassEnum.CHASSIS == ((AbstractEqNode)inLinkContext.getNode2()).getEqClass() && ((AbstractEqNode)inLinkContext.getNode2()).getRole() == EqUnitRoleEnum.PRIMARY) {
            ((AbstractEqNode)inLinkContext.getNode2()).setRole(EqUnitRoleEnum.CARRIAGE);
        }
    }

    @Nullable
    private Node getNodeInUse(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) {
        return (Node)(inLinkContext.getNodesInUse().isEmpty() ? null : (AbstractEqNode)inLinkContext.getNodesInUse().iterator().next());
    }

    @Override
    public void detach() throws BizViolation {
        this.detach(null, true, true);
    }

    @Override
    public void detach(@Nullable String inEventNotes, boolean inIsEventRecordingAllowed, boolean inPostUnlinkUpdatesAllowed) throws BizViolation {
        if (EqUnitRoleEnum.CARRIAGE == this.getRole()) {
            Collection<Node> nodes = this.getNodesInRole(EqUnitRoleEnum.PRIMARY, EqUnitRoleEnum.PAYLOAD);
            for (AbstractEqNode node : nodes) {
                node.detach(inEventNotes, inIsEventRecordingAllowed, inPostUnlinkUpdatesAllowed);
            }
        } else {
            AbstractEqNode masterNode;
            AbstractEqNode eqNode;
            AbstractEqNode nextNode = (AbstractEqNode)this.getNext();
            if (nextNode == null) {
                return;
            }
            if (this.getRole() == EqUnitRoleEnum.PRIMARY && EqUnitRoleEnum.CARRIAGE == nextNode.getRole()) {
                eqNode = nextNode;
                masterNode = this;
            } else {
                masterNode = nextNode;
                eqNode = this;
            }
            EqUnitRoleEnum eqUnitOldRole = eqNode.getRole();
            EqUnitRoleEnum masterUnitOldRole = masterNode.getRole();
            LOGGER.warn((Object)("Detach " + this + " from " + (Object)this.getNext()));
            boolean unlinked = this.unlink();
            LOGGER.warn((Object)("Is " + this + " detached from " + (Object)this.getNext() + "?: " + unlinked + ", check next unit " + (Object)this.getNext()));
            if (unlinked) {
                masterNode.reviseRolePostDetach(eqUnitOldRole);
                eqNode.reviseRolePostDetach(masterUnitOldRole);
                if (inPostUnlinkUpdatesAllowed) {
                    eqNode.doOtherUpdatesPostUnlink(eqUnitOldRole, masterNode);
                }
                if (inIsEventRecordingAllowed) {
                    String masterNodeNotes = null;
                    String eqNodeNotes = null;
                    if (masterUnitOldRole == EqUnitRoleEnum.PRIMARY && eqUnitOldRole == EqUnitRoleEnum.CARRIAGE) {
                        masterNodeNotes = this.getMessage(IInventoryPropertyKeys.UNIT_DISMOUNTED, new Object[]{eqNode.getKeyAsString()});
                        eqNodeNotes = this.getMessage(IInventoryPropertyKeys.UNIT_DISMOUNTED, new Object[]{masterNode.getKeyAsString()});
                    }
                    if (masterNodeNotes == null) {
                        masterNodeNotes = inEventNotes;
                    }
                    if (eqNodeNotes == null) {
                        eqNodeNotes = inEventNotes;
                    }
     //               this.recordUnlinkEvent(masterUnitOldRole, eqUnitOldRole, eqNode, masterNode, eqNodeNotes, masterNodeNotes);
                }
            }
        }
    }

    void reviseRolePostDetach(EqUnitRoleEnum inRelatedRole) {
        if (this.getRole() == EqUnitRoleEnum.CARRIAGE) {
            if (this.getNodesInRole(EqUnitRoleEnum.PRIMARY, EqUnitRoleEnum.PAYLOAD).isEmpty()) {
                this.setRole(EqUnitRoleEnum.PRIMARY);
            }
            return;
        }
        if (this.getRole() == EqUnitRoleEnum.PAYLOAD) {
            if (inRelatedRole == EqUnitRoleEnum.ACCESSORY) {
                return;
            }
            if (this.getEqClass().equals((Object) EquipClassEnum.CHASSIS)) {
                if (inRelatedRole == EqUnitRoleEnum.CARRIAGE) {
                    if (!this.getNodesInRole(EqUnitRoleEnum.PAYLOAD).isEmpty()) {
                        this.setRole(EqUnitRoleEnum.CARRIAGE);
                        return;
                    }
                }
                if (inRelatedRole == EqUnitRoleEnum.PAYLOAD) {
                    if (!this.getNodesInRole(EqUnitRoleEnum.CARRIAGE).isEmpty()) {
                        return;
                    }
                }
            }
            this.setRole(EqUnitRoleEnum.PRIMARY);
            return;
        }
        if (this.getRole() == EqUnitRoleEnum.ACCESSORY || this.getRole() == EqUnitRoleEnum.ACCESSORY_ON_CHS) {
            this.setRole(EqUnitRoleEnum.PRIMARY);
        }
    }

    private void recordUnlinkEvent(EqUnitRoleEnum inMasterUnitOldRole, EqUnitRoleEnum inEqUnitOldRole, Node inEqNode, Node inMasterNode, String inEqNodeEventNotes, String inMasterNodeEventNotes) {
        EventEnum masterNodeEvent = inEqUnitOldRole.getMasterDetachEvent((IEqNode)inMasterNode);
        EventEnum eqNodeEvent = inEqUnitOldRole.getEqDetachEvent();
        if (eqNodeEvent != null) {
            LOGGER.warn((Object)("Record " + (Object)eqNodeEvent + " for " + inEqNode));
            ((AbstractEqNode)inEqNode).recordUnlinkEvent(eqNodeEvent, inMasterNode, inMasterUnitOldRole, inEqNodeEventNotes);
        }
        if (masterNodeEvent != null) {
            LOGGER.warn((Object)("Record " + (Object)masterNodeEvent + " for " + inMasterNode));
            ((AbstractEqNode)inMasterNode).recordUnlinkEvent(masterNodeEvent, inEqNode, inEqUnitOldRole, inMasterNodeEventNotes);
        }
    }

    @Override
    public void detach(IUnlinkContext<Node, Value, Key> inUnlinkContext) throws BizViolation {
        boolean nodesAreLinked;
        AbstractEqNode unlinkFrom = (AbstractEqNode)inUnlinkContext.unlinkFrom();
        if (unlinkFrom == null) {
            this.detach(inUnlinkContext.getEventNotes(), inUnlinkContext.isEventRecordAllowed(), true);
            return;
        }
        if (this.equals(unlinkFrom)) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_CANNOT_DETACH_FROM_ITSELF, null, (Object)this.getKey());
        }
        AbstractEqNode nextNode = (AbstractEqNode)this.getNext();
        boolean bl = nodesAreLinked = nextNode != null && unlinkFrom.equals(nextNode) || unlinkFrom.getNext() != null && ((AbstractEqNode)unlinkFrom.getNext()).equals(this);
        if (!nodesAreLinked) {
            throw BizViolation.create((IPropertyKey) IInventoryPropertyKeys.UNIT_CANNOT_DETACH_AS_NOT_ATTACHED, null, (Object)this.getKeyAsString(), (Object)unlinkFrom.getKeyAsString());
        }
        if (nextNode != null && unlinkFrom.equals(nextNode)) {
            this.detach(inUnlinkContext.getEventNotes(), inUnlinkContext.isEventRecordAllowed(), true);
        } else {
            unlinkFrom.detach(inUnlinkContext.getEventNotes(), inUnlinkContext.isEventRecordAllowed(), true);
        }
    }

    @Nullable
    private Node getNodeInSameRole(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) {
        return (Node)(inLinkContext.getNodesInSameRole().isEmpty() ? null : (AbstractEqNode)inLinkContext.getNodesInSameRole().iterator().next());
    }

    protected abstract void setInternalRole(EqUnitRoleEnum var1);

    private static DataSourceEnum getDataSourceEnum() {
        return TransactionParms.isBound() ? ContextHelper.getThreadDataSource() : DataSourceEnum.TESTING;
    }

    @Override
    public Collection<Node> getNodesInRole(EqUnitRoleEnum... inRoles) {
        return this.getNodesInRole(false, inRoles);
    }

    private Collection<Node> getNodesInRole(boolean inIncludeLiveOnly, EqUnitRoleEnum... inRoles) {
        if (!this.hasParent()) {
            return Collections.emptyList();
        }
        HashSet<AbstractEqNode> relatedUnits = new HashSet<AbstractEqNode>();
        block0: for (AbstractEqNode node : this.getParent()) {
            if (!this.isNodeRelated((Node) node, inIncludeLiveOnly)) continue;
            for (EqUnitRoleEnum role : inRoles) {
                if (!role.equals((Object)node.getRole())) continue;
                relatedUnits.add(node);
                continue block0;
            }
        }
   //     return (Collection<Node>) relatedUnits;
        return null;
    }

    private boolean isNodeRelated(Node inNode, boolean inIncludeLiveOnly) {
        if (inIncludeLiveOnly) {
            return inNode.isLive() && inNode.getNextAttached() != null && ((AbstractEqNode)inNode.getNextAttached()).equals(this) || this.getNextAttached() != null && ((AbstractEqNode)this.getNextAttached()).equals(inNode);
        }
        return inNode.getNext() != null && ((AbstractEqNode)inNode.getNext()).equals(this) || this.getNext() != null && ((AbstractEqNode)this.getNext()).equals(inNode);
    }

    @Override
    public Collection<Node> getActiveNodesInRole(EqUnitRoleEnum... inRoles) {
        return this.getNodesInRole(true, inRoles);
    }

    protected void primaryEquipmentChanged(Value inValue) {
    }

    protected ILinkContext<Node, Value, Key> createContext(@NotNull Node inNode1, @NotNull Node inNode2) throws BizViolation {
//        return new EqLinkContext(inNode1, inNode2, EqUnitRoleEnum.PRIMARY, true, true, false, true, AbstractEqNode.getDataSourceEnum());
        return null;
    }

    protected void doOtherUpdatesPostLink(@NotNull IEqLinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
    }

    protected void doOtherUpdatesPostUnlink(EqUnitRoleEnum inOldRole, Node inRelatedNode) throws BizViolation {
    }

    protected void recordLinkEvent(EventEnum inEvent, Node inRelatedNode) {
    }

    protected void recordUnlinkEvent(EventEnum inEvent, Node inRelatedNode, EqUnitRoleEnum inOldRole, String inEventNotes) {
    }

    protected String getMessage(IPropertyKey inPropertyKey, Object[] inParms) {
        return (Object)inPropertyKey + ":" + Arrays.asList(inParms);
    }
}
