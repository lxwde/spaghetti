package com.zpmc.ztos.infra.base.business.xps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class XpsTransferZoneAssociationManager implements IXpsTransferZoneAssociationManager {
    private final List<IXpsTransferZoneAssociation> _records = new ArrayList<IXpsTransferZoneAssociation>();

    @Override
    @Nullable
    public IXpsYardBlock getAssociatedTransferZoneBlock(IXpsYardBlock inYardBlock, IXpsYardModel inYardModel) {
        List<IXpsYardBlock> transferZoneBlocks = this.getAssociatedTransferZoneBlocks(inYardBlock, inYardModel);
        return !transferZoneBlocks.isEmpty() ? transferZoneBlocks.get(0) : null;
    }

    @Override
    @NotNull
    public List<IXpsYardBlock> getAssociatedTransferZoneBlocks(IXpsYardBlock inYardBlock, IXpsYardModel inYardModel) {
        ArrayList<IXpsYardBlock> tzList = new ArrayList<IXpsYardBlock>();
        for (IXpsTransferZoneAssociation tzRecord : this._records) {
            if (!tzRecord.getStackBlock().equalsIgnoreCase(inYardBlock.getBlockName())) continue;
            String tzBlockName = tzRecord.getTZBlock();
            IXpsYardBlock yardBlock = inYardModel != null ? inYardModel.getYardBlock(tzBlockName) : null;
            tzList.add(yardBlock);
        }
        return tzList;
    }

    @Override
    @Nullable
    public IXpsTransferZoneAssociation getRecord(IXpsYardBlock inTZBlock) {
        for (IXpsTransferZoneAssociation tzRecord : this._records) {
            if (!tzRecord.getTZBlock().equalsIgnoreCase(inTZBlock.getBlockName())) continue;
            return tzRecord;
        }
        return null;
    }

    @Override
    @Nullable
    public IXpsYardBlock getAssociatedBlock(IXpsYardBlock inPossibleTransferZoneBlock, IXpsYardModel inYardModel) {
        for (IXpsTransferZoneAssociation tzRecord : this._records) {
            if (!tzRecord.getTZBlock().equalsIgnoreCase(inPossibleTransferZoneBlock.getBlockName())) continue;
            IXpsYardBlock yardBlock = inYardModel != null ? inYardModel.getYardBlock(tzRecord.getStackBlock()) : null;
            return yardBlock;
        }
        return null;
    }

    @Override
    @Nullable
    public IXpsYardBlock getAssociatedBlock(@NotNull String inPossibleTransferZoneSlot, @NotNull IXpsYardModel inYardModel) {
        IXpsYardBin yardBin = inYardModel.getBinFromSlot(inPossibleTransferZoneSlot);
        if (yardBin == null) {
            return null;
        }
        IXpsYardBlock yardBlock = yardBin.getBlock();
        if (yardBlock == null) {
            return null;
        }
        return this.getAssociatedBlock(yardBlock, inYardModel);
    }

    @Override
    @Nullable
    public String getAssociatedBlockName(IXpsYardBlock inPossibleTransferZoneBlock, IXpsYardModel inYardModel) {
        for (IXpsTransferZoneAssociation tzRecord : this._records) {
            if (!tzRecord.getTZBlock().equalsIgnoreCase(inPossibleTransferZoneBlock.getBlockName())) continue;
            return tzRecord.getStackBlock();
        }
        return null;
    }

    @Override
    public void addRecord(IXpsTransferZoneAssociation inRecord) {
        this._records.add(inRecord);
    }
}
