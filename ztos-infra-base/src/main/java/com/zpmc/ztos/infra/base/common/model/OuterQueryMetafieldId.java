package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.AbstractMetafieldId;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class OuterQueryMetafieldId extends AbstractMetafieldId {

    private static final String SUPER_ALIAS_TAG = "~.";
    private final IMetafieldId _fieldId;

    public static IMetafieldId valueOf(IMetafieldId inMetaId) {
        return new OuterQueryMetafieldId(inMetaId);
    }

    private OuterQueryMetafieldId(IMetafieldId inMetaId) {
        this._fieldId = inMetaId;
    }

    @Override
    public String getFieldId() {
        return SUPER_ALIAS_TAG + this._fieldId.getQualifiedId();
    }

    @Override
    @Nullable
    public IMetafieldId getQualifyingMetafieldId() {
        return null;
    }

    @Override
    public IMetafieldId getQualifiedMetafieldId() {
        return this;
    }

    @Override
    public IMetafieldId getMfidLeftMostNode() {
        return this;
    }

    @Override
    public IMetafieldId getMfidRightMostNode() {
        return this;
    }

    @Override
    @Nullable
    public IMetafieldId getMfidExcludeLeftMostNode() {
        return null;
    }

    @Override
    @Nullable
    public IMetafieldId getMfidExcludeRightMostNode() {
        return null;
    }
}
