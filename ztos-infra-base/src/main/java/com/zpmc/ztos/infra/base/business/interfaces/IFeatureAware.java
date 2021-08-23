package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IFeatureAware {
    public List<IFeatureId> getFeatureIds();

    public boolean hasFeature(IFeatureId var1);

    public boolean hasNoFeatures();
}
