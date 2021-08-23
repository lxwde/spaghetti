package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;

public class XpeSharedParametersParameters extends DatabaseEntity implements Serializable {
    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
