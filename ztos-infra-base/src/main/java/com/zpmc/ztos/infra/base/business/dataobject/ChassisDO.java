package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 底盘设备
 *
 * @author yejun
 */
@Data
public abstract class ChassisDO extends Equipment
        implements Serializable {
    /**
     * 车牌号码
     */
    private String eqLicenseNbr;
    private String eqLicenseState;
    private Date eqFedInspectExp;
    private Date eqStateInspectExp;
    private Long eqAxleCount;
    private Boolean eqIsChassisTriaxle;

    /**
     * 设备兼容20尺箱
     */
    private Boolean eqFits20;

    private Boolean eqFits24;
    private Boolean eqFits30;
    private Boolean eqFits40;
    private Boolean eqFits45;
    private Boolean eqFits48;
    private Boolean eqFits53;
    private Map chsCustomFlexFields;


    public String getEqLicenseNbr() {
        return this.eqLicenseNbr;
    }

    protected void setEqLicenseNbr(String eqLicenseNbr) {
        this.eqLicenseNbr = eqLicenseNbr;
    }

    public String getEqLicenseState() {
        return this.eqLicenseState;
    }

    protected void setEqLicenseState(String eqLicenseState) {
        this.eqLicenseState = eqLicenseState;
    }

    public Date getEqFedInspectExp() {
        return this.eqFedInspectExp;
    }

    protected void setEqFedInspectExp(Date eqFedInspectExp) {
        this.eqFedInspectExp = eqFedInspectExp;
    }

    public Date getEqStateInspectExp() {
        return this.eqStateInspectExp;
    }

    protected void setEqStateInspectExp(Date eqStateInspectExp) {
        this.eqStateInspectExp = eqStateInspectExp;
    }

    public Long getEqAxleCount() {
        return this.eqAxleCount;
    }

    protected void setEqAxleCount(Long eqAxleCount) {
        this.eqAxleCount = eqAxleCount;
    }

    protected Boolean getEqIsChassisTriaxle() {
        return this.eqIsChassisTriaxle;
    }

    protected void setEqIsChassisTriaxle(Boolean eqIsChassisTriaxle) {
        this.eqIsChassisTriaxle = eqIsChassisTriaxle;
    }

    protected Boolean getEqFits20() {
        return this.eqFits20;
    }

    protected void setEqFits20(Boolean eqFits20) {
        this.eqFits20 = eqFits20;
    }

    protected Boolean getEqFits24() {
        return this.eqFits24;
    }

    protected void setEqFits24(Boolean eqFits24) {
        this.eqFits24 = eqFits24;
    }

    protected Boolean getEqFits30() {
        return this.eqFits30;
    }

    protected void setEqFits30(Boolean eqFits30) {
        this.eqFits30 = eqFits30;
    }

    protected Boolean getEqFits40() {
        return this.eqFits40;
    }

    protected void setEqFits40(Boolean eqFits40) {
        this.eqFits40 = eqFits40;
    }

    protected Boolean getEqFits45() {
        return this.eqFits45;
    }

    protected void setEqFits45(Boolean eqFits45) {
        this.eqFits45 = eqFits45;
    }

    protected Boolean getEqFits48() {
        return this.eqFits48;
    }

    protected void setEqFits48(Boolean eqFits48) {
        this.eqFits48 = eqFits48;
    }

    protected Boolean getEqFits53() {
        return this.eqFits53;
    }

    protected void setEqFits53(Boolean eqFits53) {
        this.eqFits53 = eqFits53;
    }

    public Map getChsCustomFlexFields() {
        return this.chsCustomFlexFields;
    }

    protected void setChsCustomFlexFields(Map chsCustomFlexFields) {
        this.chsCustomFlexFields = chsCustomFlexFields;
    }

}
