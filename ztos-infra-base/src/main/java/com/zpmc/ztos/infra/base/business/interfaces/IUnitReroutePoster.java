package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.inventory.Routing;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.BizRequest;
import com.zpmc.ztos.infra.base.common.model.BizResponse;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;

import java.io.Serializable;

public interface IUnitReroutePoster {
    public static final String BEAN_ID = "unitReroutePoster";

    public void updateRouting(BizRequest var1, BizResponse var2) throws BizViolation;

    public void updateRouting(Serializable[] var1, FieldChanges var2) throws BizViolation;

    public void updateRouting(Unit var1, Routing var2) throws BizViolation;

    public void updateDeclaredObCv(Unit var1, CarrierVisit var2) throws BizViolation;

    public void updateIntendedObCv(Unit var1, CarrierVisit var2) throws BizViolation;

}
