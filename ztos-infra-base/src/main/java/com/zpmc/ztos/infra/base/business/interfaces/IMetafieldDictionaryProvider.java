package com.zpmc.ztos.infra.base.business.interfaces;

public interface IMetafieldDictionaryProvider {
    public static final String BEAN_ID = "MetafieldDictionaryProvider";

    public IMetafieldDictionary getMetafieldDictionary();

    public void reset();

}
