package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.RailConeStatusEnum;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Map;

public interface IRailcarVisitLocation {
    public void validateConeStatusForCntr(LocPosition var1, RailConeStatusEnum var2, String var3) throws BizViolation;

    public Map<String, AtomizedEnum> determineCarrierIncompatibleReason(LocPosition var1, DatabaseEntity var2, DatabaseEntity var3, RailConeStatusEnum var4, boolean var5);

    public Map<Serializable, AtomizedEnum> determineConeStatusForInboundCtr(LocPosition var1, Serializable var2) throws BizViolation;

    public Map<Serializable, LocPosition> findAllUfvOnBoardRailcarVisit() throws BizViolation;

    public void resetCarrierIncompatibleReasonForWiDeletion(LocPosition var1, DatabaseEntity var2);

}
