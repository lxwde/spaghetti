package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImdgClassEnum extends AtomizedEnum {

    public static final int NUM_IMDG_COMPAT_GRPS = 13;
    public static final int NUM_EXPLOSIVE_CLASSES = 6;
    public static final ImdgClassEnum IMDG_1 = new ImdgClassEnum(1, "1", 2);
    public static final ImdgClassEnum IMDG_11 = new ImdgClassEnum(1, "1.1", 2);
    public static final ImdgClassEnum IMDG_12 = new ImdgClassEnum(2, "1.2", 3);
    public static final ImdgClassEnum IMDG_13 = new ImdgClassEnum(3, "1.3", 4);
    public static final ImdgClassEnum IMDG_14 = new ImdgClassEnum(4, "1.4", 5);
    public static final ImdgClassEnum IMDG_15 = new ImdgClassEnum(5, "1.5", 6);
    public static final ImdgClassEnum IMDG_16 = new ImdgClassEnum(6, "1.6", 28);
    public static final ImdgClassEnum IMDG_2 = new ImdgClassEnum(7, "2", 32);
    public static final ImdgClassEnum IMDG_21 = new ImdgClassEnum(8, "2.1", 7);
    public static final ImdgClassEnum IMDG_22 = new ImdgClassEnum(9, "2.2", 8);
    public static final ImdgClassEnum IMDG_23 = new ImdgClassEnum(10, "2.3", 9);
    public static final ImdgClassEnum IMDG_3 = new ImdgClassEnum(11, "3", 10);
    public static final ImdgClassEnum IMDG_31 = new ImdgClassEnum(12, "3.1", 11);
    public static final ImdgClassEnum IMDG_32 = new ImdgClassEnum(13, "3.2", 12);
    public static final ImdgClassEnum IMDG_33 = new ImdgClassEnum(14, "3.3", 13);
    public static final ImdgClassEnum IMDG_41 = new ImdgClassEnum(15, "4.1", 14);
    public static final ImdgClassEnum IMDG_42 = new ImdgClassEnum(16, "4.2", 15);
    public static final ImdgClassEnum IMDG_43 = new ImdgClassEnum(17, "4.3", 16);
    public static final ImdgClassEnum IMDG_51 = new ImdgClassEnum(18, "5.1", 17);
    public static final ImdgClassEnum IMDG_52 = new ImdgClassEnum(19, "5.2", 18);
    public static final ImdgClassEnum IMDG_61 = new ImdgClassEnum(20, "6.1", 19);
    public static final ImdgClassEnum IMDG_62 = new ImdgClassEnum(21, "6.2", 20);
    public static final ImdgClassEnum IMDG_7 = new ImdgClassEnum(22, "7", 21);
    public static final ImdgClassEnum IMDG_8 = new ImdgClassEnum(23, "8", 22);
    public static final ImdgClassEnum IMDG_9 = new ImdgClassEnum(24, "9", 23);
    public static final ImdgClassEnum IMDG_X = new ImdgClassEnum(74, "X", 23);
    public static final ImdgClassEnum RISK_MPL = new ImdgClassEnum(76, "MPL", 23);
    public static final ImdgClassEnum RISK_SMPL = new ImdgClassEnum(77, "SMPL", 23);
    public static final ImdgClassEnum RISK_CMPL = new ImdgClassEnum(75, "CMPL", 23);
    private static final char[] COMPATIBILITY_GROUP_CODES = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'N', 'S'};
    private ImdgClassEnum _baseClass;
    private int _baseTrait;
    private int _compatibilityGroupTrait;
    private int _sparcsIconId;

    public static void generateExplosives(ImdgClassEnum inBaseClass) {
        for (int cg = 26; cg <= 38; ++cg) {
            char compatibilityGroupCode = COMPATIBILITY_GROUP_CODES[cg - 26];
            ImdgClassEnum imdg = new ImdgClassEnum(inBaseClass, inBaseClass.getKey() + compatibilityGroupCode, inBaseClass.getSparcsIconId());
            imdg.setCompatibilityGroupTrait(cg);
        }
    }

    public static ImdgClassEnum getEnum(String inName) {
        return (ImdgClassEnum) ImdgClassEnum.getEnum(ImdgClassEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ImdgClassEnum.getEnumMap(ImdgClassEnum.class);
    }

    public static List getEnumList() {
        return ImdgClassEnum.getEnumList(ImdgClassEnum.class);
    }

    public static Collection getList() {
        return ImdgClassEnum.getEnumList(ImdgClassEnum.class);
    }

    public static Iterator iterator() {
        return ImdgClassEnum.iterator(ImdgClassEnum.class);
    }

    private ImdgClassEnum(ImdgClassEnum inBaseClass, String inKey, int inIconId) {
        this(inBaseClass.getBaseTrait(), inKey, inIconId);
        this._baseClass = inBaseClass;
    }

    private ImdgClassEnum(int inTrait, String inKey, int inIconId) {
        super(inKey, "ImdgDescription" + inKey, inKey, "", "");
        this._baseTrait = inTrait;
        this._sparcsIconId = inIconId;
    }

    public int getSparcsIconId() {
        return this._sparcsIconId;
    }

    public int getBaseTrait() {
        return this._baseTrait;
    }

    public int getCompatibilityGroupTrait() {
        return this._compatibilityGroupTrait;
    }

    private void setCompatibilityGroupTrait(int inCompatibilityGroupTrait) {
        this._compatibilityGroupTrait = inCompatibilityGroupTrait;
    }

    public String getMappingClassName() {
        return "com.navis.inventory.business.imdg.UserTypeImdgClass";
    }

    public ImdgClassEnum getBaseClass() {
        return this._baseClass == null ? this : this._baseClass;
    }

    static {
        ImdgClassEnum.generateExplosives(IMDG_11);
        ImdgClassEnum.generateExplosives(IMDG_12);
        ImdgClassEnum.generateExplosives(IMDG_13);
        ImdgClassEnum.generateExplosives(IMDG_14);
        ImdgClassEnum.generateExplosives(IMDG_15);
        ImdgClassEnum.generateExplosives(IMDG_16);
    }
}
