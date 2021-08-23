package com.zpmc.ztos.infra.base.common.model;

import java.util.List;
import java.util.Map;

public class CarinaOracleIndex extends CarinaBaseIndex {
    public CarinaOracleIndex(Map<Object, Object> inMap) {
        super(inMap);
    }

    public CarinaOracleIndex(String inTableName, List<String> inColumnNames, String inIndexName) {
        super(inTableName, inColumnNames, inIndexName);
    }
}
