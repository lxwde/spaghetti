package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.interfaces.INodeLinked;
import com.zpmc.ztos.infra.base.business.interfaces.IUnlinkContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class UnlinkContext<Node extends INodeLinked<Node, Value, Key>, Value, Key>
        implements IUnlinkContext<Node, Value, Key> {
    private boolean _isEventRecordAllowed;
    private Node _unlinkFrom;
    private String _eventNotes;

    public UnlinkContext(Node inUnlinkFrom) {
        this(inUnlinkFrom, null, true);
    }

    public UnlinkContext(Node inUnlinkFrom, String inEventNotes) {
        this(inUnlinkFrom, inEventNotes, true);
    }

    public UnlinkContext(@Nullable Node inUnlinkFrom, @Nullable String inEventNotes, boolean inIsEventRecordAllowed) {
        this._unlinkFrom = inUnlinkFrom;
        this._eventNotes = inEventNotes;
        this._isEventRecordAllowed = inIsEventRecordAllowed;
    }

    @Override
    public Node unlinkFrom() {
        return this._unlinkFrom;
    }

    @Override
    public String getEventNotes() {
        return this._eventNotes;
    }

    @Override
    public boolean isEventRecordAllowed() {
        return this._isEventRecordAllowed;
    }
}
