package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.inventory.Hazards;
import com.zpmc.ztos.infra.base.business.model.Commodity;

public interface IGoods {
    public Boolean getGdsIsHazardous();

    public Commodity getGdsCommodity();

    public Hazards getGdsHazards();

    public void updateConsignee(Object var1);

    public void updateShipper(Object var1);

    public void setOrigin(String var1);

    public void setDestination(String var1);

    public void attachHazards(Hazards var1);

    public void setCommodity(Commodity var1);

}
