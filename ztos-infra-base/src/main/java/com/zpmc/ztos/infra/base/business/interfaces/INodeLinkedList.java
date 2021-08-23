package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

public interface INodeLinkedList <Node extends INodeLinked<Node, Value, Key>, Value, Key> extends Iterable<Node> {
    public boolean contains(@NotNull Node var1);

    public int size();

    public void clear() throws BizViolation;

    public void add(@NotNull Node var1);

    public void reParent(@NotNull INodeLinkedList<Node, Value, Key> var1, @NotNull Node var2);

    public void unParent(@NotNull Node var1);

}
