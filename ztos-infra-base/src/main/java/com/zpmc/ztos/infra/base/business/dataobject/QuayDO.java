package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.Portal;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * 桥吊
 * @author yejun
 */
@Data
public abstract class QuayDO extends Portal implements Serializable {

    private String quayId;
    private String quayName;

    /**
     * quay对应设施信息
     */
    private Facility quayFacility;
    private Map quayCustomFlexFields;


    public String getQuayId() {
        return this.quayId;
    }

    protected void setQuayId(String quayId) {
        this.quayId = quayId;
    }

    public String getQuayName() {
        return this.quayName;
    }

    protected void setQuayName(String quayName) {
        this.quayName = quayName;
    }

    public Facility getQuayFacility() {
        return this.quayFacility;
    }

    protected void setQuayFacility(Facility quayFacility) {
        this.quayFacility = quayFacility;
    }

    public Map getQuayCustomFlexFields() {
        return this.quayCustomFlexFields;
    }

    protected void setQuayCustomFlexFields(Map quayCustomFlexFields) {
        this.quayCustomFlexFields = quayCustomFlexFields;
    }

}
