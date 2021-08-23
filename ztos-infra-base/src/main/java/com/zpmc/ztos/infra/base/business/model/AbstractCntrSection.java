package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.AbstractCntrSectionDO;
import com.zpmc.ztos.infra.base.business.enums.argo.SectionDoorDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.SectionLengthsAllowedEnum;

public abstract class AbstractCntrSection extends AbstractCntrSectionDO {
    public AbstractCntrSection() {
        this.setAcnsnDefaultDoorDirections(SectionDoorDirectionEnum.ANY);
        this.setAcnsnLengthsAllowed(SectionLengthsAllowedEnum.ANY);
    }
}
