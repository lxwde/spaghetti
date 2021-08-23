package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public interface IGuardian extends ILogicalEntity {
    @Nullable
    public List getSupportedBathToGuardians();

    public boolean isCorrectEntityType(LogicalEntityEnum var1);
}
