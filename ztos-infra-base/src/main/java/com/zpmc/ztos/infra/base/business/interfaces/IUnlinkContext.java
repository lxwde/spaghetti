package com.zpmc.ztos.infra.base.business.interfaces;

public interface IUnlinkContext<Node extends INodeLinked<Node, Value, Key>, Value, Key> {
    public Node unlinkFrom();

    public String getEventNotes();

    public boolean isEventRecordAllowed();
}
