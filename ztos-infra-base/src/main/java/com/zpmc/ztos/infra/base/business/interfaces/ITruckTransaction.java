package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.equipments.Chassis;
import com.zpmc.ztos.infra.base.business.model.Accessory;

import java.io.Serializable;

public interface ITruckTransaction {
    public Serializable getTranGkey();

    public Long getTranNbr();

    public String getTranAppointmentNbr();

    public Boolean getTranChsIsOwners();

    public String getTranChsNbr();

    public boolean isBareChassis();

    public Chassis getTranChassis();

    public IValueHolder[] getTranChsDamagesVao();

    public Accessory getTranCtrAccessory();

    public Accessory getTranChsAccessory();

    public String getTranChsAccNbr();

    public String getTranCtrAccNbr();

    public Integer getTranCtrTruckPosition();
}
