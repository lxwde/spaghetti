package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.sun.istack.Nullable;

import java.io.Serializable;

public interface IMetafieldId extends Serializable, Comparable {
    public static final IMetafieldId PRIMARY_KEY = MetafieldIdFactory.valueOf("id");

    public String getFieldId();

    public String getQualifiedId();

    public IMetafieldId getMfidLeftMostNode();

    public IMetafieldId getMfidRightMostNode();

    @Nullable
    public IMetafieldId getMfidExcludeLeftMostNode();

    @Nullable
    public IMetafieldId getMfidExcludeRightMostNode();

    @Nullable
    public IMetafieldId getQualifyingMetafieldId();

    @Nullable
    public IMetafieldId getQualifiedMetafieldId();

    public boolean isEntityAware();

    @Nullable
    public IEntityId getEntityId();
}
