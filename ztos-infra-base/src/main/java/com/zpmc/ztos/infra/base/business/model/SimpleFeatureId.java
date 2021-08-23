package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IFeatureId;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class SimpleFeatureId implements IFeatureId {
    String _featureId;

    protected SimpleFeatureId(String inFeatureId) {
        this._featureId = inFeatureId;
    }

    public String getAttribute() {
        return this._featureId;
    }

    @Override
    public String getFeatureId() {
        return this._featureId;
    }

    @Override
    public boolean isEnabled(UserContext inContext) {
//        ILicenseManager licenseManager = SecurityBeanUtils.getLicenseManager();
//        return licenseManager.hasFeature(inContext, this);
          return false;
    }

    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }
        if (!(inObject instanceof SimpleFeatureId)) {
            return false;
        }
        SimpleFeatureId simpleFeatureId = (SimpleFeatureId)inObject;
        return !(this._featureId != null ? !this._featureId.equals(simpleFeatureId._featureId) : simpleFeatureId._featureId != null);
    }

    public int hashCode() {
        return this._featureId != null ? this._featureId.hashCode() : 0;
    }

    public String toString() {
        return this._featureId;
    }
}
