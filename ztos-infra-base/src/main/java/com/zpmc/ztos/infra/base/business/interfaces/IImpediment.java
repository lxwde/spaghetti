package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.FlagActionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.FlagStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ServiceRuleTypeRoleEnum;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public interface IImpediment extends Comparable {

    public Serializable getFlagGkey();

    public ILogicalEntity getAffectedEntity();

    public IFlagType getFlagType();

    public Date getApplyDate();

    public String getAppliedBy();

    public String getReferenceId();

    public String getNote();

    public Collection getVetoes();

    public LogicalEntityEnum getAppliedToClass();

    public Long getAppliedToPrimaryKey();

    public String getAppliedToNaturalKey();

    public LogicalEntityEnum getGuardianClass();

    public Long getGuardianPrimaryKey();

    public String getGuardianNaturalKey();

    public FlagStatusEnum getStatus();

    public ServiceRuleTypeRoleEnum getRuleTypeRole();

    public String getTableUiValue();

    public boolean isSameImpediment(IImpediment var1);

    public boolean isImpedimentActive();

    @Nullable
    public String getHpvId();

    public Boolean isMultipleAllowed();

    @Nullable
    public FlagActionEnum getFlagAction();
}
