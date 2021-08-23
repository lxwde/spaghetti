package com.zpmc.ztos.infra.base.business.enums.cargo;

import org.apache.commons.lang.enums.EnumUtils;

import java.util.EnumSet;
import java.util.List;

public enum CargoExtensionMethodEnum {
    PRE_STRIP(1, "PRE_STRIP", "", "", "PRE_STRIP"),
    POST_STRIP(2, "POST_STRIP", "", "", "POST_STRIP"),
    PRE_STUFF(3, "PRE_STUFF", "", "", "PRE_STUFF"),
    POST_STUFF(4, "POST_STUFF", "", "", "POST_STUFF"),
    PRE_MOVE(5, "PRE_MOVE", "", "", "PRE_MOVE"),
    POST_MOVE(6, "POST_MOVE", "", "", "POST_MOVE"),
    PRE_UNLOAD_VESSEL(7, "PRE_UNLOAD_VESSEL", "", "", "PRE_UNLOAD_VESSEL"),
    POST_UNLOAD_VESSEL(8, "POST_UNLOAD_VESSEL", "", "", "POST_UNLOAD_VESSEL"),
    PRE_UNLOAD_TRUCK(9, "PRE_UNLOAD_TRUCK", "", "", "PRE_UNLOAD_TRUCK"),
    POST_UNLOAD_TRUCK(10, "POST_UNLOAD_TRUCK", "", "", "POST_UNLOAD_TRUCK"),
    PRE_UNLOAD_RAIL(11, "PRE_UNLOAD_RAIL", "", "", "PRE_UNLOAD_RAIL"),
    POST_UNLOAD_RAIL(12, "POST_UNLOAD_RAIL", "", "", "POST_UNLOAD_RAIL"),
    PRE_LOAD_VESSEL(13, "PRE_LOAD_VESSEL", "", "", "PRE_LOAD_VESSEL"),
    POST_LOAD_VESSEL(14, "POST_LOAD_VESSEL", "", "", "POST_LOAD_VESSEL"),
    PRE_LOAD_TRUCK(15, "PRE_LOAD_TRUCK", "", "", "PRE_LOAD_TRUCK"),
    POST_LOAD_TRUCK(16, "POST_LOAD_TRUCK", "", "", "POST_LOAD_TRUCK"),
    PRE_LOAD_RAIL(17, "PRE_LOAD_RAIL", "", "", "PRE_LOAD_RAIL"),
    POST_LOAD_RAIL(18, "POST_LOAD_RAIL", "", "", "POST_LOAD_RAIL"),
    PRE_MANIFEST_CONTAINER(19, "PRE_MANIFEST_CONTAINER", "", "", "PRE_MANIFEST_CONTAINER"),
    POST_MANIFEST_CONTAINER(20, "POST_MANIFEST_CONTAINER", "", "", "POST_MANIFEST_CONTAINER"),
    PRE_MANIFEST_VESSEL(21, "PRE_MANIFEST_VESSEL", "", "", "PRE_MANIFEST_VESSEL"),
    POST_MANIFEST_VESSEL(22, "POST_MANIFEST_VESSEL", "", "", "POST_MANIFEST_VESSEL"),
    PRE_MANIFEST_RAIL(23, "PRE_MANIFEST_RAIL", "", "", "PRE_MANIFEST_RAIL"),
    POST_MANIFEST_RAIL(24, "POST_MANIFEST_RAIL", "", "", "POST_MANIFEST_RAIL"),
    PRE_MANIFEST_TRUCK(25, "PRE_MANIFEST_TRUCK", "", "", "PRE_MANIFEST_TRUCK"),
    POST_MANIFEST_TRUCK(26, "POST_MANIFEST_TRUCK", "", "", "POST_MANIFEST_TRUCK"),
    PRE_MANUFACTURE(27, "PRE_MANUFACTURE", "", "", "PRE_MANUFACTURE"),
    POST_MANUFACTURE(28, "POST_MANUFACTURE", "", "", "POST_MANUFACTURE"),
    PRE_TRANSLOAD(29, "PRE_TRANSLOAD", "", "", "PRE_TRANSLOAD"),
    POST_TRANSLOAD(30, "POST_TRANSLOAD", "", "", "POST_TRANSLOAD");
    private final int key;
    private final String code;
    private final String desc;
    private final String color;
    private final String displayName;

    CargoExtensionMethodEnum(int key, String code, String desc, String color, String displayName) {
        this.key = key;
        this.code = code;
        this.desc = desc;
        this.color = color;
        this.displayName = displayName;
    }

    public static List getEnumList() {
        return EnumUtils.getEnumList(CargoExtensionMethodEnum.class);
    }

    public static EnumSet getEnumSet() {
        return EnumSet.allOf(CargoExtensionMethodEnum.class);
    }

    public int getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

}