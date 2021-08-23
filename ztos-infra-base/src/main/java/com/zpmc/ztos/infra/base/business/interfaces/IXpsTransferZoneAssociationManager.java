package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public interface IXpsTransferZoneAssociationManager {
    @Nullable
    public IXpsYardBlock getAssociatedTransferZoneBlock(IXpsYardBlock var1, IXpsYardModel var2);

    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZoneBlocks(IXpsYardBlock var1, IXpsYardModel var2);

    @Nullable
    public IXpsYardBlock getAssociatedBlock(IXpsYardBlock var1, IXpsYardModel var2);

    @Nullable
    public IXpsYardBlock getAssociatedBlock(String var1, IXpsYardModel var2);

    @Nullable
    public String getAssociatedBlockName(IXpsYardBlock var1, IXpsYardModel var2);

    public void addRecord(IXpsTransferZoneAssociation var1);

    @Nullable
    public IXpsTransferZoneAssociation getRecord(IXpsYardBlock var1);
}
