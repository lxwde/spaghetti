package com.zpmc.ztos.infra.base.business.interfaces;

public interface IYardPosition {
    public String getBlock();

    public String getRow();

    public String getCol();

    public String getTier();

    public void setBlock(String var1);

    public void setRow(String var1);

    public void setCol(String var1);

    public void setTier(String var1);
}
