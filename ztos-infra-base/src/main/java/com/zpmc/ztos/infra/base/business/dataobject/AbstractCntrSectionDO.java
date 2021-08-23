package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.SectionDoorDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.SectionLengthsAllowedEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractSection;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 集装箱Section区域
 * @author yejun
 */
@Data
public abstract class AbstractCntrSectionDO extends AbstractSection implements Serializable {

    /**
     * 默认箱门方向
     */
    private SectionDoorDirectionEnum acnsnDefaultDoorDirections;

    /**
     * 允许长度
     */
    private SectionLengthsAllowedEnum acnsnLengthsAllowed;


    public SectionDoorDirectionEnum getAcnsnDefaultDoorDirections() {
        return this.acnsnDefaultDoorDirections;
    }

    protected void setAcnsnDefaultDoorDirections(SectionDoorDirectionEnum acnsnDefaultDoorDirections) {
        this.acnsnDefaultDoorDirections = acnsnDefaultDoorDirections;
    }

    public SectionLengthsAllowedEnum getAcnsnLengthsAllowed() {
        return this.acnsnLengthsAllowed;
    }

    protected void setAcnsnLengthsAllowed(SectionLengthsAllowedEnum acnsnLengthsAllowed) {
        this.acnsnLengthsAllowed = acnsnLengthsAllowed;
    }

}
