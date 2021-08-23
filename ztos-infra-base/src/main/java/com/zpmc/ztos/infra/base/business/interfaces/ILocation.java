package com.zpmc.ztos.infra.base.business.interfaces;


import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

public interface ILocation {
    public LocTypeEnum getLocType();

    public String getLocId();

    public String getCarrierVehicleId();

    public Long getLocGkey();

    public Facility getLocFacility();

    public CarrierVisit getInboundCv();

    public CarrierVisit getOutboundCv();

    public void verifyMoveToAllowed(IEntity var1, String var2) throws BizViolation; //throws BizViolation;

    public void verifyMoveFromAllowed(IEntity var1, String var2) throws BizViolation; //throws BizViolation;
}
