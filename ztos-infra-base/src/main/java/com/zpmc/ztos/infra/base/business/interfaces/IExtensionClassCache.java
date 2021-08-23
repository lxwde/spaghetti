package com.zpmc.ztos.infra.base.business.interfaces;

import java.io.Serializable;

public interface IExtensionClassCache extends IDetailedDiagnostics{

    public int getCacheSize();

    public int getCacheMaxSize();

    public boolean isCacheFull();

    public String getCacheEntryStatus(Serializable var1);

}
