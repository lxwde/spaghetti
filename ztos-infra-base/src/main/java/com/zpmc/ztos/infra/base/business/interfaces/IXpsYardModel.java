package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.xps.TransferZoneKindEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Set;

public interface IXpsYardModel extends IXpsYardBin {
    public IXpsYardBin getBin(@Nullable String var1);

    @Nullable
    public IXpsYardBin getBinFromSlot(@Nullable String var1);

    public Set<String> getAllBinNames();

    public IXpsYardBlock getYardBlock(String var1);

    @NotNull
    public List<IXpsYardBlock> getYardBlocks();

    @NotNull
    public List<IXpsYardPoint> getYardPoints();

    @NotNull
    public List<IXpsYardPoint> getYardPointsForBlock(@NotNull String var1);

    @NotNull
    public List<IXpsYardPoint> getYardPointsForYard();

    public boolean isYardContainRailTZ();

    @NotNull
    public IXpsTransferZoneAssociationManager getTZAssociationManager();

    @NotNull
    public IXpsYardBin createUnknownBin(String var1);

    @NotNull
    public String getYardCode();

    @NotNull
    public String getYardName();

    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@Nullable String var1);

    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@NotNull IXpsYardBin var1);

    @NotNull
    public TransferZoneKindEnum getTransferZoneKind(@NotNull IXpsYardBlock var1);

    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull String var1);

    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull IXpsYardBin var1);

    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZones(@NotNull IXpsYardBin var1, @NotNull TransferZoneKindEnum var2);

    public IXpsYardBlock getFirstAssociatedTransferZone(@NotNull String var1, @NotNull TransferZoneKindEnum var2);

    public boolean isValidYardSlot(@Nullable String var1);

    public IXpsRailTrack getRailTrack(@NotNull String var1, @NotNull String var2);

    public List<IXpsRailTrack> getRailTracks();
}
