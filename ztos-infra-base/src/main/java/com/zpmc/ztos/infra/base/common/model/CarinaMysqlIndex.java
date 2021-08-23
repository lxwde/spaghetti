package com.zpmc.ztos.infra.base.common.model;

import java.util.List;
import java.util.Map;

public class CarinaMysqlIndex extends CarinaBaseIndex {
    public CarinaMysqlIndex(Map<Object, Object> inMap) {
        super(inMap);
    }

    public CarinaMysqlIndex(String inTableName, List<String> inColumnNames, String inIndexName) {
        super(inTableName, inColumnNames, inIndexName);
    }
}
