package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldDictionary;

import java.util.Locale;

public class DefaultTranslationContext extends AbstractTranslationContext{
    final UserContext _context;
    IMetafieldDictionary _metafieldDictionary;

    public DefaultTranslationContext(UserContext inContext) {
        this._context = inContext;
    }

    @Override
    public Locale getLocale() {
        return this._context.getUserLocale();
    }

    @Override
    public Object getBean(String inBeanName) {
        return Roastery.getBean(inBeanName);
    }

    @Override
    public UserContext getUserContext() {
        return this._context;
    }

}
