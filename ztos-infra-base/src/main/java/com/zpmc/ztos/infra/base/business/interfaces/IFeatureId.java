package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IFeatureId {
    public String getFeatureId();

    public boolean isEnabled(UserContext var1);
}
