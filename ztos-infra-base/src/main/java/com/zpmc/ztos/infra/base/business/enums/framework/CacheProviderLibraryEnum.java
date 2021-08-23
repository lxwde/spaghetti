package com.zpmc.ztos.infra.base.business.enums.framework;

public enum CacheProviderLibraryEnum {
    CARINA("CARINA"),
    EHCACHE("EHCACHE"),
    HAZELCAST("HAZELCAST"),
    NONE("NONE");

    private final String _key;

    private CacheProviderLibraryEnum(String inKey) {
        this._key = inKey;
    }

    public String getKey() {
        return this._key;
    }
}
