package com.zpmc.ztos.infra.base.common.model;

import java.util.List;
import java.util.Map;

public class CarinaPostgresIndex extends CarinaBaseIndex {
    public CarinaPostgresIndex(Map<Object, Object> inMap) {
        super(inMap);
    }

    public CarinaPostgresIndex(String inTableName, List<String> inColumnNames, String inIndexName) {
        super(inTableName, inColumnNames, inIndexName);
    }
}
