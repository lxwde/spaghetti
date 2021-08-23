package com.zpmc.ztos.infra.base.business.interfaces;

public interface ILinkContext<Node extends INodeLinked<Node, Value, Key>, Value, Key> {
    public Node getNode1();

    public Node getNode2();

    public boolean isStrictlyValidated();

    public boolean isAlreadyLinked();

}
