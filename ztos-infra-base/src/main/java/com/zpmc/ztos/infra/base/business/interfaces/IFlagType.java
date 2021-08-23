package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.FlagActionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.FlagPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IFlagType {
    public String getId();

    public Long getFlgtypGkey();

    public String getDescription();

    public FlagPurposeEnum getPurpose();

    public LogicalEntityEnum getAppliesTo();

    public boolean isFlagReferenceIdUnique();

    public boolean isFlagReferenceIdRequired();

    public boolean isCovertHoldRequired();

    public LogicalEntityEnum getFlgtypAppliesTo();

    public boolean isFlagActionValid(FlagActionEnum var1);

    @Nullable
    public String getHpvId();

    public boolean isFlagMultipleAllowed();
}
