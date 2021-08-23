package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IFeatureId;

public class FeatureIdFactory {
    private FeatureIdFactory() {
    }

    public static IFeatureId valueOf(String inFeatureId) {
        return new SimpleFeatureId(inFeatureId);
    }

}
