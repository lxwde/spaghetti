package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipBasicLengthEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IXpsYardBin {
    public String getUiFullPositionWithTier(EquipBasicLengthEnum var1, String var2);

    public String getUiFullPosition(EquipBasicLengthEnum var1);

    @Nullable
    public IXpsYardBlock getBlock();

    public boolean isInterFacilityTransferBin();

    public boolean isProtectedBin();

    public boolean isChassisBin();

    public boolean isLogicalBlock();

    public boolean isMatchBlock(IXpsYardBin var1);
}
