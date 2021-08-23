package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ValueObject;

import java.util.List;

public interface IXpsYardSection extends IXpsYardBin {
    public ValueObject getInfoAsVao();

    public List getStacksAsVaos();

    public int getSectionRowIndex();
}

