package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.BinNameTable;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * section区域
 * @author yejun
 */
@Data
public abstract class AbstractSectionDO extends AbstractBin implements Serializable {
    private Long asnRowIndex;
    private Long asnRowPairedInto;
    private Long asnRowPairedFrom;
    private Long asnColTableOffset;
    private BinNameTable asnConvIdCol;

    public Long getAsnRowIndex() {
        return this.asnRowIndex;
    }

    protected void setAsnRowIndex(Long asnRowIndex) {
        this.asnRowIndex = asnRowIndex;
    }

    public Long getAsnRowPairedInto() {
        return this.asnRowPairedInto;
    }

    protected void setAsnRowPairedInto(Long asnRowPairedInto) {
        this.asnRowPairedInto = asnRowPairedInto;
    }

    public Long getAsnRowPairedFrom() {
        return this.asnRowPairedFrom;
    }

    protected void setAsnRowPairedFrom(Long asnRowPairedFrom) {
        this.asnRowPairedFrom = asnRowPairedFrom;
    }

    public Long getAsnColTableOffset() {
        return this.asnColTableOffset;
    }

    protected void setAsnColTableOffset(Long asnColTableOffset) {
        this.asnColTableOffset = asnColTableOffset;
    }

    public BinNameTable getAsnConvIdCol() {
        return this.asnConvIdCol;
    }

    protected void setAsnConvIdCol(BinNameTable asnConvIdCol) {
        this.asnConvIdCol = asnConvIdCol;
    }
}
