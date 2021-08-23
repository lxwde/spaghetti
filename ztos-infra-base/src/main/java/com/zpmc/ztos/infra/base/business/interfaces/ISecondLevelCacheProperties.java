package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Properties;

public interface ISecondLevelCacheProperties {
    public Properties getEntityProperties();

    public void setEntityProperties(Properties var1);

    public Properties getCollectionProperties();

    public void setCollectionProperties(Properties var1);

    public boolean getOverrideProperty();

    public void setOverrideProperty(boolean var1);
}
