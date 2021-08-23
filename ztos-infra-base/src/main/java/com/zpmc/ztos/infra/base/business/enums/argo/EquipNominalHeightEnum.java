package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.enums.core.LengthUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.common.enums.Measure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EquipNominalHeightEnum {
//    NOM21(1, "NOM21", "21", "", "NOM21"),
//    NOM40(2, "NOM40", "40", "", "NOM40"),
//    NOM43(3, "NOM43", "43", "", "NOM43"),
//    NOM49(4, "NOM49", "49", "", "NOM49"),
//    NOM76(5, "NOM76", "76", "", "NOM76"),
//    NOM80(6, "NOM80", "80", "", "NOM80"),
//    NOM86(7, "NOM86", "86", "", "NOM86"),
//    NOM90(8, "NOM90", "90", "", "NOM90"),
//    NOM91(9, "NOM91", "91", "", "NOM91"),
//    NOM96(10, "NOM96", "96", "", "NOM96"),
//    NOM100(11, "NOM100", "100", "", "NOM100"),
//    NOM102(12, "NOM102", "102", "", "NOM102"),
//    NOM106(13, "NOM106", "106", "", "NOM106"),
//    NOM114(14, "NOM114", "114", "", "NOM114"),
//    NOM116(15, "NOM116", "116", "", "NOM116"),
//    NOM130(16, "NOM130", "130", "", "NOM130"),
//    NOMNA(17, "NOMNA", "na", "", "NOMNA");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipNominalHeightEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipNominalHeightEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipNominalHeightEnum.class);
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public double getValueInUnits(IMeasurementUnit inUnits) {
//        return 0.0;
////        if (this._storageUnits.equals(inUnits)) {
////            return this._value;
////        }
////        return UnitUtils.convertTo(this._value, this._storageUnits, inUnits);
//    }
//
//
//}

public class EquipNominalHeightEnum
        extends Measure {
    public static final EquipNominalHeightEnum NOM21 = new EquipNominalHeightEnum("NOM21", "atom.EquipNominalHeightEnum.NOM21.description", "atom.EquipNominalHeightEnum.NOM21.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 635.0);
    public static final EquipNominalHeightEnum NOM40 = new EquipNominalHeightEnum("NOM40", "atom.EquipNominalHeightEnum.NOM40.description", "atom.EquipNominalHeightEnum.NOM40.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 1219.0);
    public static final EquipNominalHeightEnum NOM43 = new EquipNominalHeightEnum("NOM43", "atom.EquipNominalHeightEnum.NOM43.description", "atom.EquipNominalHeightEnum.NOM43.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 1295.0);
    public static final EquipNominalHeightEnum NOM49 = new EquipNominalHeightEnum("NOM49", "atom.EquipNominalHeightEnum.NOM49.description", "atom.EquipNominalHeightEnum.NOM49.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 1448.0);
    public static final EquipNominalHeightEnum NOM76 = new EquipNominalHeightEnum("NOM76", "atom.EquipNominalHeightEnum.NOM76.description", "atom.EquipNominalHeightEnum.NOM76.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2286.0);
    public static final EquipNominalHeightEnum NOM80 = new EquipNominalHeightEnum("NOM80", "atom.EquipNominalHeightEnum.NOM80.description", "atom.EquipNominalHeightEnum.NOM80.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2438.0);
    public static final EquipNominalHeightEnum NOM86 = new EquipNominalHeightEnum("NOM86", "atom.EquipNominalHeightEnum.NOM86.description", "atom.EquipNominalHeightEnum.NOM86.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2591.0);
    public static final EquipNominalHeightEnum NOM90 = new EquipNominalHeightEnum("NOM90", "atom.EquipNominalHeightEnum.NOM90.description", "atom.EquipNominalHeightEnum.NOM90.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2743.0);
    public static final EquipNominalHeightEnum NOM91 = new EquipNominalHeightEnum("NOM91", "atom.EquipNominalHeightEnum.NOM91.description", "atom.EquipNominalHeightEnum.NOM91.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2769.0);
    public static final EquipNominalHeightEnum NOM96 = new EquipNominalHeightEnum("NOM96", "atom.EquipNominalHeightEnum.NOM96.description", "atom.EquipNominalHeightEnum.NOM96.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2896.0);
    public static final EquipNominalHeightEnum NOM10 = new EquipNominalHeightEnum("NOM10", "atom.EquipNominalHeightEnum.NOM10.description", "atom.EquipNominalHeightEnum.NOM10.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3048.0);
    public static final EquipNominalHeightEnum NOM102 = new EquipNominalHeightEnum("NOM102", "atom.EquipNominalHeightEnum.NOM102.description", "atom.EquipNominalHeightEnum.NOM102.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3099.0);
    public static final EquipNominalHeightEnum NOM106 = new EquipNominalHeightEnum("NOM106", "atom.EquipNominalHeightEnum.NOM106.description", "atom.EquipNominalHeightEnum.NOM106.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3231.0);
    public static final EquipNominalHeightEnum NOM114 = new EquipNominalHeightEnum("NOM114", "atom.EquipNominalHeightEnum.NOM114.description", "atom.EquipNominalHeightEnum.NOM114.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3454.0);
    public static final EquipNominalHeightEnum NOM116 = new EquipNominalHeightEnum("NOM116", "atom.EquipNominalHeightEnum.NOM116.description", "atom.EquipNominalHeightEnum.NOM116.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3505.0);
    public static final EquipNominalHeightEnum NOM130 = new EquipNominalHeightEnum("NOM130", "atom.EquipNominalHeightEnum.NOM130.description", "atom.EquipNominalHeightEnum.NOM130.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3962.0);
    public static final EquipNominalHeightEnum NOMNA = new EquipNominalHeightEnum("NOMNA", "atom.EquipNominalHeightEnum.NOMNA.description", "atom.EquipNominalHeightEnum.NOMNA.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 2591.0);

    public static EquipNominalHeightEnum getEnum(String inName) {
        return (EquipNominalHeightEnum) EquipNominalHeightEnum.getEnum(EquipNominalHeightEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipNominalHeightEnum.getEnumMap(EquipNominalHeightEnum.class);
    }

    public static List getEnumList() {
        return EquipNominalHeightEnum.getEnumList(EquipNominalHeightEnum.class);
    }

    public static Collection getList() {
        return EquipNominalHeightEnum.getEnumList(EquipNominalHeightEnum.class);
    }

    public static Iterator iterator() {
        return EquipNominalHeightEnum.iterator(EquipNominalHeightEnum.class);
    }

    protected EquipNominalHeightEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, LengthUnitEnum inHeightUnit, double inValue) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, (IMeasurementUnit)inHeightUnit, inValue);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipNominalHeightEnum";
    }
}
