package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ISecondLevelCacheProperties;

import java.util.Properties;

public class SecondLevelCacheProperties implements ISecondLevelCacheProperties {
    private Properties _entityProperties;
    private Properties _collectionProperties;
    private boolean _overrideProperty = false;
    private static final boolean OVERRIDE_DEFAULT = false;

    @Override
    public Properties getEntityProperties() {
        return this._entityProperties;
    }

    @Override
    public void setEntityProperties(Properties inEntityProperties) {
        this._entityProperties = inEntityProperties;
    }

    @Override
    public Properties getCollectionProperties() {
        return this._collectionProperties;
    }

    @Override
    public void setCollectionProperties(Properties inCollectionProperties) {
        this._collectionProperties = inCollectionProperties;
    }

    @Override
    public boolean getOverrideProperty() {
        return this._overrideProperty;
    }

    @Override
    public void setOverrideProperty(boolean inOverrideProperty) {
        this._overrideProperty = inOverrideProperty;
    }
}
