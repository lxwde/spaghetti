package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PowerChargeEnum extends AtomizedEnum {
    public static final PowerChargeEnum HOUR = new PowerChargeEnum("HOUR", "atom.PowerChargeEnum.HOUR.description", "atom.PowerChargeEnum.HOUR.code", "", "", "");
    public static final PowerChargeEnum DAY = new PowerChargeEnum("DAY", "atom.PowerChargeEnum.DAY.description", "atom.PowerChargeEnum.DAY.code", "", "", "");
    public static final PowerChargeEnum DAY_24HOUR = new PowerChargeEnum("DAY_24HOUR", "atom.PowerChargeEnum.DAY_24HOUR.description", "atom.PowerChargeEnum.DAY_24HOUR.code", "", "", "");

    public static PowerChargeEnum getEnum(String inName) {
        return (PowerChargeEnum) PowerChargeEnum.getEnum(PowerChargeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return PowerChargeEnum.getEnumMap(PowerChargeEnum.class);
    }

    public static List getEnumList() {
        return PowerChargeEnum.getEnumList(PowerChargeEnum.class);
    }

    public static Collection getList() {
        return PowerChargeEnum.getEnumList(PowerChargeEnum.class);
    }

    public static Iterator iterator() {
        return PowerChargeEnum.iterator(PowerChargeEnum.class);
    }

    protected PowerChargeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypePowerChargeEnum";
    }
}
