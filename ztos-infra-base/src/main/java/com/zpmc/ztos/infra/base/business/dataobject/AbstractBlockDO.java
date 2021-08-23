package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.BinNameTable;

import java.io.Serializable;

public abstract class AbstractBlockDO extends AbstractBin implements Serializable {

    private BinNameTable ablConvIdRowStd;
    private BinNameTable ablConvIdRowAlt;
    private BinNameTable ablConvIdTier;

    public BinNameTable getAblConvIdRowStd() {
        return this.ablConvIdRowStd;
    }

    protected void setAblConvIdRowStd(BinNameTable ablConvIdRowStd) {
        this.ablConvIdRowStd = ablConvIdRowStd;
    }

    public BinNameTable getAblConvIdRowAlt() {
        return this.ablConvIdRowAlt;
    }

    protected void setAblConvIdRowAlt(BinNameTable ablConvIdRowAlt) {
        this.ablConvIdRowAlt = ablConvIdRowAlt;
    }

    public BinNameTable getAblConvIdTier() {
        return this.ablConvIdTier;
    }

    protected void setAblConvIdTier(BinNameTable ablConvIdTier) {
        this.ablConvIdTier = ablConvIdTier;
    }

}
