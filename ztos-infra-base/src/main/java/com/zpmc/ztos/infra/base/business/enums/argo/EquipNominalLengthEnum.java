package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.enums.core.LengthUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.common.enums.Measure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum EquipNominalLengthEnum implements IMeasurementUnit{
//    NOM10(1, "NOM10", "", "", "NOM10"),
//    NOM20(2, "NOM20", "", "", "NOM20"),
//    NOM22(3, "NOM22", "", "", "NOM22"),
//    NOM23(4, "NOM23", "", "", "NOM23"),
//    NOM32(5, "NOM32", "", "", "NOM32"),
//    NOM40(6, "NOM40", "", "", "NOM40"),
//    NOM42(7, "NOM42", "", "", "NOM42"),
//    NOM44(8, "NOM44", "", "", "NOM44"),
//    NOM45(9, "NOM45", "", "", "NOM45"),
//    NOM24(10, "NOM24", "", "", "NOM24"),
//    NOM30(11, "NOM30", "", "", "NOM30"),
//    NOM48(12, "NOM48", "", "", "NOM48"),
//    NOM51(13, "NOM51", "", "", "NOM51"),
//    NOM53(14, "NOM53", "", "", "NOM53"),
//    NOM546(15, "NOM546", "", "", "NOM546"),
//    NOM60(16, "NOM60", "", "", "NOM60"),
//    NOMXX(17, "NOMXX", "", "", "NOMXX");
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    EquipNominalLengthEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(EquipNominalLengthEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(EquipNominalLengthEnum.class);
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
//    @Override
//    public String getAbbrevation() {
//        return null;
//    }
//
//    @Override
//    public String[] getAbbreviations() {
//        return new String[0];
//    }
//
//    @Override
//    public IPropertyKey getUnitName() {
//        return null;
//    }
//
//    @Override
//    public boolean matches(String var1) {
//        return false;
//    }
//
//    @Override
//    public double getStandardUnits() {
//        return 0;
//    }
//
//    @Override
//    public double getStandardUnitOffset() {
//        return 0;
//    }
//}

public class EquipNominalLengthEnum
        extends Measure {
    public static final EquipNominalLengthEnum NOM10 = new EquipNominalLengthEnum("NOM10", "atom.EquipNominalLengthEnum.NOM10.description", "atom.EquipNominalLengthEnum.NOM10.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 3048.0);
    public static final EquipNominalLengthEnum NOM20 = new EquipNominalLengthEnum("NOM20", "atom.EquipNominalLengthEnum.NOM20.description", "atom.EquipNominalLengthEnum.NOM20.code", "dodgerblue", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 6096.0);
    public static final EquipNominalLengthEnum NOM22 = new EquipNominalLengthEnum("NOM22", "atom.EquipNominalLengthEnum.NOM22.description", "atom.EquipNominalLengthEnum.NOM22.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 6706.0);
    public static final EquipNominalLengthEnum NOM23 = new EquipNominalLengthEnum("NOM23", "atom.EquipNominalLengthEnum.NOM23.description", "atom.EquipNominalLengthEnum.NOM23.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 7010.0);
    public static final EquipNominalLengthEnum NOM32 = new EquipNominalLengthEnum("NOM32", "atom.EquipNominalLengthEnum.NOM32.description", "atom.EquipNominalLengthEnum.NOM32.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 9754.0);
    public static final EquipNominalLengthEnum NOM40 = new EquipNominalLengthEnum("NOM40", "atom.EquipNominalLengthEnum.NOM40.description", "atom.EquipNominalLengthEnum.NOM40.code", "burlywood", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 12192.0);
    public static final EquipNominalLengthEnum NOM42 = new EquipNominalLengthEnum("NOM42", "atom.EquipNominalLengthEnum.NOM42.description", "atom.EquipNominalLengthEnum.NOM42.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 12802.0);
    public static final EquipNominalLengthEnum NOM44 = new EquipNominalLengthEnum("NOM44", "atom.EquipNominalLengthEnum.NOM44.description", "atom.EquipNominalLengthEnum.NOM44.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 13411.0);
    public static final EquipNominalLengthEnum NOM45 = new EquipNominalLengthEnum("NOM45", "atom.EquipNominalLengthEnum.NOM45.description", "atom.EquipNominalLengthEnum.NOM45.code", "firebrick", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 13716.0);
    public static final EquipNominalLengthEnum NOM24 = new EquipNominalLengthEnum("NOM24", "atom.EquipNominalLengthEnum.NOM24.description", "atom.EquipNominalLengthEnum.NOM24.code", "lightpink", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 7315.0);
    public static final EquipNominalLengthEnum NOM30 = new EquipNominalLengthEnum("NOM30", "atom.EquipNominalLengthEnum.NOM30.description", "atom.EquipNominalLengthEnum.NOM30.code", "chocolate", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 9144.0);
    public static final EquipNominalLengthEnum NOM48 = new EquipNominalLengthEnum("NOM48", "atom.EquipNominalLengthEnum.NOM48.description", "atom.EquipNominalLengthEnum.NOM48.code", "darkorange", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 14630.0);
    public static final EquipNominalLengthEnum NOM51 = new EquipNominalLengthEnum("NOM51", "atom.EquipNominalLengthEnum.NOM51.description", "atom.EquipNominalLengthEnum.NOM51.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 15545.0);
    public static final EquipNominalLengthEnum NOM53 = new EquipNominalLengthEnum("NOM53", "atom.EquipNominalLengthEnum.NOM53.description", "atom.EquipNominalLengthEnum.NOM53.code", "indianred", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 16154.0);
    public static final EquipNominalLengthEnum NOM546 = new EquipNominalLengthEnum("NOM546", "atom.EquipNominalLengthEnum.NOM546.description", "atom.EquipNominalLengthEnum.NOM546.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 16612.0);
    public static final EquipNominalLengthEnum NOM60 = new EquipNominalLengthEnum("NOM60", "atom.EquipNominalLengthEnum.NOM60.description", "atom.EquipNominalLengthEnum.NOM60.code", "", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 18288.0);
    public static final EquipNominalLengthEnum NOMXX = new EquipNominalLengthEnum("NOMXX", "atom.EquipNominalLengthEnum.NOMXX.description", "atom.EquipNominalLengthEnum.NOMXX.code", "lightslategray", "", "", (LengthUnitEnum)LengthUnitEnum.MILLIMETERS, 12192.0);

    public static EquipNominalLengthEnum getEnum(String inName) {
        return (EquipNominalLengthEnum) EquipNominalLengthEnum.getEnum(EquipNominalLengthEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EquipNominalLengthEnum.getEnumMap(EquipNominalLengthEnum.class);
    }

    public static List getEnumList() {
        return EquipNominalLengthEnum.getEnumList(EquipNominalLengthEnum.class);
    }

    public static Collection getList() {
        return EquipNominalLengthEnum.getEnumList(EquipNominalLengthEnum.class);
    }

    public static Iterator iterator() {
        return EquipNominalLengthEnum.iterator(EquipNominalLengthEnum.class);
    }

    protected EquipNominalLengthEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, LengthUnitEnum inLengthUnit, double inValue) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, (IMeasurementUnit)inLengthUnit, inValue);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeEquipNominalLengthEnum";
    }
}
