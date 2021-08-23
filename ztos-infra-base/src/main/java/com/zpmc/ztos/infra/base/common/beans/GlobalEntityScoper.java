package com.zpmc.ztos.infra.base.common.beans;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.GlobalScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Global;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalEntityScoper extends BaseEntityScoper{
    private final List<IScopeEnum> _definedScopes;
    private final List<IScopeEnum> _supportedScopes;
    private static final Logger LOGGER = Logger.getLogger(GlobalEntityScoper.class);

    public GlobalEntityScoper() {
        ArrayList<GlobalScopeEnum> scopes = new ArrayList<GlobalScopeEnum>(1);
        scopes.add(GlobalScopeEnum.GLOBAL);
        this._definedScopes = Collections.unmodifiableList(scopes);
        this._supportedScopes = this._definedScopes;
    }

    @Override
    public IScopeEnum getScopeEnum(int inLevel) {
        switch (inLevel) {
            case 1: {
                return GlobalScopeEnum.GLOBAL;
            }
        }
        LOGGER.error((Object)"getScopeEnum() only supports GLOBAL");
        return null;
    }

    @Override
    public IScopeEnum getScopeEnum(String inLevelKey) {
        return GlobalScopeEnum.getEnum(inLevelKey);
    }

    @Override
    @NotNull
    public List<IScopeEnum> getScopeEnumList() {
        return this._definedScopes;
    }

    @Override
    @NotNull
    public List<IScopeEnum> getSupportedFlexibleScopeEnumList() {
        return this._supportedScopes;
    }

    @Override
    @NotNull
    public IScopeEnum getMostSpecificSupportedScopeEnum() {
        return GlobalScopeEnum.GLOBAL;
    }

    @Override
    @NotNull
    public ScopeCoordinates getScopeCoordinates(IScopeEnum inScopeLevel, Serializable inCoordinate) {
        if (inScopeLevel == GlobalScopeEnum.GLOBAL) {
            return ScopeCoordinates.GLOBAL_SCOPE;
        }
        String errMsg = "Should not pass an illegal scope or any inCoordinate";
        LOGGER.error((Object)errMsg);
        throw new IllegalArgumentException(errMsg);
    }

    @Override
    public String getScopeLevelName(int inLevel) {
        ITranslationContext context = TranslationUtils.getTranslationContext(TransactionParms.getBoundParms().getUserContext());
        IMessageTranslator messageTranslator = context.getMessageTranslator();
        if ((long)inLevel == GlobalScopeEnum.GLOBAL.getScopeLevel()) {
            return messageTranslator.getMessage(GlobalScopeEnum.GLOBAL.getDescriptionPropertyKey());
        }
        return null;
    }

    @Override
    public IScopeNodeEntity getScopeNodeEntity(ScopeCoordinates inCoordinates) {
        return Global.getInstance();
    }

    @Override
    protected IScope lookupEntityScope(String inEntityClassName, ScopeCoordinates inScopeCoordinate) {
        return null;
    }

    @Override
    public String[] getUserScopeCoordsAsStringArray(UserContext inUserContext) {
        return new String[0];
    }
}
