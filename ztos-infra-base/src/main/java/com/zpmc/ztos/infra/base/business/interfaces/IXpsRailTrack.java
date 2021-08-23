package com.zpmc.ztos.infra.base.business.interfaces;

public interface IXpsRailTrack {
    public String getTrackName();

    public int getNumSlotsOnTrack();

    public int getOrderInBlock();

    public int getTrackIndex();

    public String getTrackOwnerPlanName();
}
