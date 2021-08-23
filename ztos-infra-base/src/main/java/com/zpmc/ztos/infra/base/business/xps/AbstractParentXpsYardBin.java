package com.zpmc.ztos.infra.base.business.xps;

public abstract class AbstractParentXpsYardBin extends AbstractXpsYardBin {
    private Integer _coordConvId20;
    private Integer _coordConvId40;
    private int _convTableOffset;

    public AbstractParentXpsYardBin(AbstractParentXpsYardBin inParentBin, int inCoordConvId20, int inCoordConvId40, int inConvTableOffset) {
        super(inParentBin);
        this._coordConvId20 = new Integer(inCoordConvId20);
        this._coordConvId40 = inCoordConvId40 == 0 ? this._coordConvId20 : new Integer(inCoordConvId40);
        this._convTableOffset = inConvTableOffset;
    }

    @Override
    public Integer getCoordConvId20() {
        return this._coordConvId20;
    }

    @Override
    public Integer getCoordConvId40() {
        return this._coordConvId40;
    }

    @Override
    public int getConvTableOffset() {
        return this._convTableOffset;
    }
}
