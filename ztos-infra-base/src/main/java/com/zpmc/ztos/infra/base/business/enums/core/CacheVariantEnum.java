package com.zpmc.ztos.infra.base.business.enums.core;

import com.sun.istack.NotNull;

public enum CacheVariantEnum {

    XPS_OBJECT,
    XPS_IMPORT,
    XPS_JOURNAL,
    XPS_JOURNAL_FAILURES,
    MISC,
    NETWORK_NODE{

        @Override
        @NotNull
        public String getNameOfCache(@NotNull String inBaseCacheName) {
            return "ARGO:NETWORK_NODES";
        }

        @Override
        @NotNull
        public String getNameOfCache(@NotNull String inOprId, @NotNull String inCpxId, @NotNull String inFcyId, @NotNull String inYrdId) {
            return "ARGO:NETWORK_NODES";
        }
    }
    ,
    SIGNAL;


    @NotNull
    public String getNameOfCache(@NotNull String inBaseCacheName) {
        String cacheName = String.format("%s/%s", inBaseCacheName, this.name());
        return cacheName;
    }

    @NotNull
    public String getNameOfCache(@NotNull String inOprId, @NotNull String inCpxId, @NotNull String inFcyId, @NotNull String inYrdId) {
        String baseCacheName = CacheVariantEnum.getBaseNameOfCache(inOprId, inCpxId, inFcyId, inYrdId);
        String cacheName = this.getNameOfCache(baseCacheName);
        return cacheName;
    }

    @NotNull
    public static String getBaseNameOfCache(@NotNull String inOprId, @NotNull String inCpxId, @NotNull String inFcyId, @NotNull String inYrdId) {
        String cacheName = String.format("XPS:%s/%s/%s/%s", inOprId, inCpxId, inFcyId, inYrdId);
        return cacheName;
    }

    @NotNull
    public static String[] parseNameOfCache(@NotNull String inCacheName) {
        if (!inCacheName.startsWith("XPS:")) {
            throw new IllegalArgumentException("Name of cache '" + inCacheName + "' must start with XPS:");
        }
        String[] cacheCoordinates = inCacheName.substring("XPS:".length()).split("/");
        if (cacheCoordinates.length != 4) {
            throw new IllegalArgumentException("Name of cache '" + inCacheName + "' must match 'XPS:Operator/Complex/Facility/Yard'");
        }
        return cacheCoordinates;
    }

}
