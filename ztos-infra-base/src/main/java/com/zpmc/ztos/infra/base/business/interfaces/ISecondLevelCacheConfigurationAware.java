package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;

public interface ISecondLevelCacheConfigurationAware {
    public FuzzyBoolean isSecondLevelCacheEnabled();
}
