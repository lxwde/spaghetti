package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

public interface INodeLinked<Node extends INodeLinked<Node, Value, Key>, Value, Key> {
    public Node link(@NotNull ILinkContext<Node, Value, Key> var1) throws BizViolation;

    public Node link(@NotNull Node var1) throws BizViolation;

    public Node getNext();

    public INodeLinkedList<Node, Value, Key> getParent();

    public boolean isNodeInPath(@NotNull Node var1);

    public boolean isNodeReferred();

    public boolean isSameNode(@NotNull Node var1);

    public boolean hasParent();

    public Key getKey();

    public Value getValue();

    public void createValueIfNull(Node var1) throws BizViolation;

    public boolean unlink();

    public Node resolveNode(@NotNull ILinkContext<Node, Value, Key> var1, @NotNull Node var2);

    public String getKeyAsString();

    public Node getNextAttached();

    public boolean isLive();
}
