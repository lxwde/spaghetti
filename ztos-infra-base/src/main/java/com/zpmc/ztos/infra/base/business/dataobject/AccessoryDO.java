package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.equipments.Equipment;

import java.io.Serializable;
import java.util.Map;

public class AccessoryDO extends Equipment implements Serializable {
    private String eqAcryDescription;
    private Map accCustomFlexFields;

    public String getEqAcryDescription() {
        return this.eqAcryDescription;
    }

    protected void setEqAcryDescription(String eqAcryDescription) {
        this.eqAcryDescription = eqAcryDescription;
    }

    public Map getAccCustomFlexFields() {
        return this.accCustomFlexFields;
    }

    protected void setAccCustomFlexFields(Map accCustomFlexFields) {
        this.accCustomFlexFields = accCustomFlexFields;
    }
}
