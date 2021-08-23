package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipRfrTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.TankRailTypeEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * 集装箱
 * @author yejun
 */

@Data
public class ContainerDO extends Equipment implements Serializable {

    /**
     * 相关设备类型
     */
    private EquipRfrTypeEnum eqRfrType;

    private Boolean eqIsStarvent;
    private Boolean eqIsSuperFreezeReefer;
    private Boolean eqIsControlledAtmosphereReefer;
    private Boolean eqIsVented;
    private TankRailTypeEnum eqTankRails;
    private Map ctrCustomFlexFields;

    public EquipRfrTypeEnum getEqRfrType() {
        return this.eqRfrType;
    }

    protected void setEqRfrType(EquipRfrTypeEnum eqRfrType) {
        this.eqRfrType = eqRfrType;
    }

    protected Boolean getEqIsStarvent() {
        return this.eqIsStarvent;
    }

    protected void setEqIsStarvent(Boolean eqIsStarvent) {
        this.eqIsStarvent = eqIsStarvent;
    }

    protected Boolean getEqIsSuperFreezeReefer() {
        return this.eqIsSuperFreezeReefer;
    }

    protected void setEqIsSuperFreezeReefer(Boolean eqIsSuperFreezeReefer) {
        this.eqIsSuperFreezeReefer = eqIsSuperFreezeReefer;
    }

    protected Boolean getEqIsControlledAtmosphereReefer() {
        return this.eqIsControlledAtmosphereReefer;
    }

    protected void setEqIsControlledAtmosphereReefer(Boolean eqIsControlledAtmosphereReefer) {
        this.eqIsControlledAtmosphereReefer = eqIsControlledAtmosphereReefer;
    }

    public Boolean getEqIsVented() {
        return this.eqIsVented;
    }

    protected void setEqIsVented(Boolean eqIsVented) {
        this.eqIsVented = eqIsVented;
    }

    public TankRailTypeEnum getEqTankRails() {
        return this.eqTankRails;
    }

    protected void setEqTankRails(TankRailTypeEnum eqTankRails) {
        this.eqTankRails = eqTankRails;
    }

    public Map getCtrCustomFlexFields() {
        return this.ctrCustomFlexFields;
    }

    protected void setCtrCustomFlexFields(Map ctrCustomFlexFields) {
        this.ctrCustomFlexFields = ctrCustomFlexFields;
    }



}
