package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public interface IEquipStateManager {
    public static final String BEAN_ID = "equipStateManager";

    public void upgradeEqGrade(Serializable var1, EquipGrade var2);

    @Nullable
    public EquipGrade findEquipGrade(Equipment var1);

    public void purgeEqState(Equipment var1) throws BizViolation;

    public ILogicalEntity findEqsLogicalEntity(Equipment var1);

    public void recordUnitEvent(Equipment var1, FieldChanges var2);

}
