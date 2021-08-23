package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.ExamEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.InbondEnum;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

import java.util.Set;

public interface IBillOfLading {
    public Long getGkey();

    public String getNbr();

    public String getLineOperatorId();

    public String getCarrierVisitId();

    public String getShipperName();

    public String getConsigneeName();

    public String getOriginalBlNbr();

    public String getPod1Id();

    public String getPod2Id();

    public String getPolId();

    public String getOrigin();

    public String getDestination();

    public String getBondedDestinationId();

    public AtomizedEnum getCategory();

    public Set getBlItems();

    public ExamEnum getExamStatus();

    public InbondEnum getInbondStatus();

    public void validateBlAndUnitCompatability(Unit var1) throws BizViolation;

    public Long getCarrierVisitGkey();
}
