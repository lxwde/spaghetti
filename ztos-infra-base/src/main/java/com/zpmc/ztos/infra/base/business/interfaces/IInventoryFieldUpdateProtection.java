package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;

public interface IInventoryFieldUpdateProtection extends IArgoFieldUpdateProtection{
    public void checkForProtectedFieldUpdate(FieldChanges var1, Unit var2) throws BizViolation;

    public void validateProtectedPosition(UnitFacilityVisit var1, LocPosition var2, LocPosition var3) throws BizViolation;

    public boolean isPositionUpdateProtected(LocPosition var1);
}
