package com.zpmc.ztos.infra.base.business.enums.framework;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BeanFactoryKeyEnum {
    ROASTERY("app"),
    PORTAL("com.navis.app.portal"),
    PRESENTATION("com.navis.app.presentation"),
    PRESENTATION_FRAMEWORK("com.navis.framework.presentation"),
    ULC("com.navis.app.ulc"),
    ZK("com.navis.app.zk"),
    MOBILE_AND_STRUTS("com.navis.app.web");

    public static final BeanFactoryKeyEnum[] UI_TIERS;
    private static final Map<String, BeanFactoryKeyEnum> LOOK_UP;
    private final String _key;

    private BeanFactoryKeyEnum(String inKey) {
        this._key = inKey;
    }

    public String getKey() {
        return this._key;
    }

    public static BeanFactoryKeyEnum get(String inKey) {
        return LOOK_UP.get(inKey);
    }

    static {
        UI_TIERS = new BeanFactoryKeyEnum[]{ULC, ZK, MOBILE_AND_STRUTS};
        LOOK_UP = new HashMap<String, BeanFactoryKeyEnum>();
        for (BeanFactoryKeyEnum se : EnumSet.allOf(BeanFactoryKeyEnum.class)) {
            LOOK_UP.put(se.getKey(), se);
        }
    }
}
