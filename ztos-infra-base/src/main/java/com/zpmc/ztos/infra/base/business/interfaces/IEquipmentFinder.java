package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.equipments.EquipType;

public interface IEquipmentFinder {
    public static final String BEAN_ID = "equipmentFinder";

    public EquipType findEquipmentTypeForBombcart();
}
