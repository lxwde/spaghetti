package com.zpmc.ztos.infra.base.business.interfaces;

public interface IXpsTransferZoneAssociation {
    public String getStackBlock();

    public String getTZBlock();

    public String getHighLow();

    public boolean isHigh();

    public String getRowColumn();

    public String getStartIndex();

    public String getEndIndex();
}
