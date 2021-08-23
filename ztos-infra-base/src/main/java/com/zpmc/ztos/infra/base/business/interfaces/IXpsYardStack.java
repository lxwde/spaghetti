package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ValueObject;

public interface IXpsYardStack extends IXpsYardBin {
    public static final short ALT1 = -4;
    public static final short ALT2 = -3;
    public static final short EVEN = -2;
    public static final short ODD = -1;
    public static final short SAME = 1;
    public static final short OPPOSITE = 2;
    public static final short UNDEFINED = 0;
    public static final short NOM_20s_SLOT_MASK = 1;
    public static final short NOM_40s_SLOT_MASK = 2;
    public static final short NOM_45s_SLOT_MASK = 4;
    public static final short NOM_48s_SLOT_MASK = 8;
    public static final short NOM_53s_SLOT_MASK = 16;
    public static final short NOM_24s_SLOT_MASK = 32;
    public static final short NOM_35s_SLOT_MASK = 64;
    public static final short NOM_60s_SLOT_MASK = 1024;
    public static final short NOM_30s_SLOT_MASK = 512;
    public static final short NOM_40s_LESSTHAN_45s_SLOT_MASK = 66;
    public static final short NOM_ALL_20s_SLOT_MASK = 545;
    public static final short NOM_ALL_40s_SLOT_MASK = 94;
    public static final short NOM_ALL_LENS_SLOT_MASK = 639;
    public static final short AIR_REEFER_MASK = 128;
    public static final short PORTHOLE_REEFER_MASK = 256;
    public static final short WATER_REEFER_MASK = 2048;
    public static final short INTEGRAL_REEFER_MASK = 2176;
    public static final short REEFER_ANY_MASK = 2432;
    public static final short K_SUPPORTED_BY_DECK = 4096;
    public static final short K_RESTRICTED_USE_MASK = 8192;
    public static final short K_HAZARDS_PROHIBITED_MASK = 16384;
    public static final short K_COOL_STOW_PROHIBITED_MASK = 1024;
    public static final short K_NO_SLOT_ATTRIBUTES = 0;
    public static final short K_ALL_SLOT_ATTRIBUTES = 32767;

    public ValueObject getInfoAsVao();

}
