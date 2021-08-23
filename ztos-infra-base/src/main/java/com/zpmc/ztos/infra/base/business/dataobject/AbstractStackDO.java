package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;

import java.io.Serializable;

/**
 *
 * 堆垛区域
 * @author yejun
 */
public abstract class AbstractStackDO extends AbstractBin implements Serializable {

    private Long astColIndex;

    public Long getAstColIndex() {
        return this.astColIndex;
    }

    protected void setAstColIndex(Long astColIndex) {
        this.astColIndex = astColIndex;
    }

}
