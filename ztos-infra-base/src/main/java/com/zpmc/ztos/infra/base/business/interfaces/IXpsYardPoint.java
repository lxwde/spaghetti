package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ValueObject;

public interface IXpsYardPoint {
    public ValueObject getInfoAsVao();

    public String getBlockCode();

    public int getSequence();

    public int getHorizontal();

    public int getVertical();
}
