package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.business.interfaces.IScopeEnum;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

public abstract class AbstractScopeEnum extends AtomizedEnum implements IScopeEnum {
    private final Long _scopeLevel;

    protected AbstractScopeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, Long inScopeLevel) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._scopeLevel = inScopeLevel;
    }

    @Override
    public Long getScopeLevel() {
        return this._scopeLevel;
    }

}
