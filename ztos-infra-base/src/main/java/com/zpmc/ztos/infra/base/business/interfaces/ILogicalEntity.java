package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface ILogicalEntity extends IEntity {

    @Nullable
    public LogicalEntityEnum getLogicalEntityType();

    public String getLogEntityId();

    public String getLogEntityParentId();

    public void calculateFlags();

    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed();

    @Nullable
    public Complex getLogEntityComplex();

}
