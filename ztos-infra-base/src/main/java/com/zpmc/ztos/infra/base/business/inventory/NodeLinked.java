package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.ILinkContext;
import com.zpmc.ztos.infra.base.business.interfaces.INodeLinked;
import com.zpmc.ztos.infra.base.business.interfaces.INodeLinkedList;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public abstract class NodeLinked<Node extends NodeLinked<Node, Value, Key>, Value, Key>
        implements INodeLinked<Node, Value, Key> {
    private static final Logger LOGGER = Logger.getLogger(NodeLinked.class);
    private Value _value;
    private Key _key;
    private Node _next;
    private INodeLinkedList<Node, Value, Key> _parent;

    public NodeLinked(@NotNull Key inKey, @Nullable Value inValue) {
        this._key = inKey;
        this._value = inValue;
    }

    @Override
    public Node link(@NotNull Node inNode) throws BizViolation {
//        ILinkContext<NodeLinked, Value, Key> nodeContext = (ILinkContext<NodeLinked, Value, Key>) this.createContext(this, inNode);
//        return (Node)this.link((ILinkContext)nodeContext);
        return null;
    }

    @Override
    public Node link(@NotNull ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        Node node = this.getNodeIfAlreadyAttached(inLinkContext);
        if (node != null) {
            return node;
        }
        this.validateIfSameNode(inLinkContext);
        this.validateIfBidirectional(inLinkContext);
        this.validateLink(inLinkContext);
        this.beforeLinking(inLinkContext);
        this.linkNode(inLinkContext);
        this.afterLinking(inLinkContext);
        return (Node)((NodeLinked)inLinkContext.getNode2());
    }

    @Nullable
    private Node getNodeIfAlreadyAttached(ILinkContext<Node, Value, Key> inLinkContext) {
        if (!inLinkContext.isAlreadyLinked()) {
            return null;
        }
        if (inLinkContext.isStrictlyValidated()) {
     //       throw new AlreadyLinkedException();
        }
        return (Node)((NodeLinked)inLinkContext.getNode2());
    }

    private void validateIfBidirectional(ILinkContext<Node, Value, Key> inLinkContext) {
        NodeLinked node = (NodeLinked)inLinkContext.getNode2();
        INodeLinked nextNode = node.getNext();
        if (nextNode == null) {
            return;
        }
        if (this.equals(nextNode)) {
      //      throw new BidirectionalLinkException();
        }
    }

    private void validateIfSameNode(ILinkContext<Node, Value, Key> inLinkContext) {
        if (this.isSameNode((Node)((NodeLinked)inLinkContext.getNode2()))) {
     //       throw new AlreadyLinkedException();
        }
    }

    protected void linkNode(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
        NodeLinked node = (NodeLinked)inLinkContext.getNode2();
        this.createValueIfNull((Node)node);
        if (node.hasParent()) {
            node.getParent().add(this);
        } else {
//            INodeLinkedList<NodeLinked, Value, Key> parent;
//            if (this.canReuseParent()) {
//                INodeLinked nextNode = this.getNext();
//                if (nextNode != null) {
//                    this.getParent().unParent((Node) nextNode);
//                }
//                parent = (INodeLinkedList<NodeLinked, Value, Key>) this.getParent();
//            } else {
//                parent = (INodeLinkedList<NodeLinked, Value, Key>) this.internalCreateParent();
//                parent.add(this);
//            }
//            parent.add(node);
        }
        this.setNext((Node) node);
    }

    @Override
    public Node resolveNode(@NotNull ILinkContext<Node, Value, Key> inLinkContext, @NotNull Node inNode) {
        Node node;
        if (((NodeLinked)inNode).getValue() != null) {
            return inNode;
        }
        if (((NodeLinked)inNode).getKey() == null) {
            return inNode;
        }
        if (this.hasParent()) {
            for (NodeLinked node2 : this.getParent()) {
                if (!node2.isLive() || node2.getKey() == null || !node2.getKey().equals(((NodeLinked)inNode).getKey())) continue;
                return (Node)node2;
            }
        }
        return (node = this.findNodeByKey(inLinkContext, (Key) ((NodeLinked)inNode).getKey())) == null ? inNode : node;
    }

    protected abstract Node findNodeByKey(ILinkContext<Node, Value, Key> var1, Key var2);

    void setValue(@NotNull Value inValue) {
        this._value = inValue;
    }

    protected void validateLink(@NotNull ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
    }

    @Override
    public boolean unlink() {
        try {
            if (!this.hasParent()) {
                return false;
            }
            INodeLinked nextNode = this.getNext();
            if (nextNode == null) {
                return false;
            }
            this.setNext(null);
            if (!this.isNodeReferred()) {
                this.getParent().unParent((Node) this);
            } else if (((NodeLinked)nextNode).isNodeReferred()) {
//                INodeLinkedList<NodeLinked, Value, Key> newParent = this.internalCreateParent();
 //               newParent.add(this);
            }
            if (!((NodeLinked)nextNode).isNodeReferred() && ((NodeLinked)nextNode).getParent() != null) {
                ((NodeLinked)nextNode).getParent().unParent(nextNode);
            }
        }
        catch (Throwable ex) {
            LOGGER.warn((Object)("Dismount failed for unit: " + this.getKeyAsString()));
            LOGGER.warn((Object)("Error trace: " + CarinaUtils.getStackTrace((Throwable)ex)));
            return false;
        }
        return true;
    }

    @Override
    public void createValueIfNull(Node inNode) throws BizViolation {
        if (((NodeLinked)inNode).isValueTobeDerived()) {
            ((NodeLinked)inNode).setValue(this.createValue((Key) ((NodeLinked)inNode).getKey()));
        }
        if (this.isValueTobeDerived()) {
            this.setValue((Value) ((NodeLinked)inNode).createValue(this.getKey()));
        }
    }

    protected abstract Value createValue(Key var1) throws BizViolation;

    private boolean canReuseParent() {
        if (!this.hasParent()) {
            return false;
        }
        int i = 0;
        for (NodeLinked node : this.getParent()) {
            if (this.isNodeInPath((Node)node)) continue;
            ++i;
        }
        return i <= 1;
    }

    @Override
    public Node getNext() {
        return this._next;
    }

    @Override
    public INodeLinkedList<Node, Value, Key> getParent() {
        return this._parent;
    }

    @Override
    public Value getValue() {
        return this._value;
    }

    @Override
    public Key getKey() {
        return this._key;
    }

    protected boolean isValueTobeDerived() {
        return this._value == null && this.getKey() != null;
    }

    public boolean equals(Object inNode) {
        if (inNode == null) {
            return false;
        }
        if (inNode == this) {
            return true;
        }
        if (!(inNode instanceof INodeLinked)) {
            return false;
        }
        INodeLinked node = (INodeLinked)inNode;
        if (this.getValue() != null && node.getValue() != null) {
            return this.getValue().equals(node.getValue());
        }
        if (this.getKey() != null && node.getKey() != null) {
            return this.getKey().equals(node.getKey());
        }
        return false;
    }

    @Override
    public boolean isSameNode(@NotNull Node inNode) {
        return this.equals(inNode);
    }

    @Override
    public boolean isNodeInPath(@NotNull Node inNode) {
        if (((NodeLinked)inNode).equals(this)) {
            return true;
        }
        INodeLinked nextNode = ((NodeLinked)inNode).getNext();
        if (nextNode == null) {
            return false;
        }
        while (nextNode != null) {
            if (((NodeLinked)nextNode).equals(this)) {
                return true;
            }
            nextNode = ((NodeLinked)nextNode).getNext();
        }
        return false;
    }

    @Override
    public boolean isNodeReferred() {
        if (!this.hasParent()) {
            return false;
        }
        if (this.getNext() != null) {
            return true;
        }
        for (NodeLinked node : this.getParent()) {
            if (node.equals(this) || node.getNext() == null || !this.equals(node.getNext())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasParent() {
        return this.getParent() != null;
    }

    private void setNext(@Nullable Node inNextNode) {
        this._next = inNextNode;
        this.setInternalNext(inNextNode);
    }

    public String toString() {
        return this._value == null ? this.getKey().toString() : this._value.toString();
    }

    public int hashCode() {
        return this.getValue() != null ? this.getValue().hashCode() : this.getKey().hashCode();
    }

    protected final void setParent(INodeLinkedList<Node, Value, Key> inList) {
        this._parent = inList;
        this.setInternalParent(inList);
    }

    @Override
    public String getKeyAsString() {
        return this.getKey() == null ? this.getValue().toString() : this.getKey().toString();
    }

    protected ILinkContext<Node, Value, Key> createContext(@NotNull Node inNode1, @NotNull Node inNode2) throws BizViolation {
   //     return new LinkContext(inNode1, inNode2);
        return null;
    }

    protected final void setKey(@NotNull Key inKey) {
        this._key = inKey;
        this.setInternalKey(inKey);
    }

    protected void beforeLinking(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
    }

    protected void afterLinking(ILinkContext<Node, Value, Key> inLinkContext) throws BizViolation {
    }

    protected void setInternalKey(@NotNull Key inKey) {
    }

    protected void setInternalNext(@Nullable Node inNextNode) {
    }

    protected void setInternalParent(INodeLinkedList<Node, Value, Key> inParent) {
    }

    protected abstract INodeLinkedList<Node, Value, Key> internalCreateParent();

    @Override
    public Node getNextAttached() {
        return (Node)this.getNext();
    }

    @Override
    public boolean isLive() {
        return true;
    }
}
