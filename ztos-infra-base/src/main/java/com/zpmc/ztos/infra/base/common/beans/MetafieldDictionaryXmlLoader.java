package com.zpmc.ztos.infra.base.common.beans;

import org.apache.log4j.Logger;

public class MetafieldDictionaryXmlLoader {
    private MetafieldDictionaryXmlReader _xmlReader;
    private static final Logger LOGGER = Logger.getLogger(MetafieldDictionaryXmlLoader.class);

//    @Override
//    protected IFileXmlReader getFileReader() {
//        return this._xmlReader;
//    }
//
//    @Override
//    public void loadDictionary(IMetafieldDictionaryMutator inMFD) {
//        this._xmlReader = new MetafieldDictionaryXmlReader(inMFD);
//        this.loadFiles();
//    }

    public void setUseHiberCache(boolean inUseHiberCache) {
        LOGGER.error((Object)"hibercache setting is no longer used.");
    }
}
