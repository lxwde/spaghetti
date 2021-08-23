package com.zpmc.ztos.infra.base.business.interfaces;

import org.dom4j.Element;

import java.io.Serializable;
import java.util.Iterator;

public interface IEntityXmlExporter {
    public void setPrimaryKeys(Serializable[] var1);

    public void setPrimaryKeysViaFilter(IPredicate var1);

    public Iterator<Element> getElementIterator();

    public Serializable[] getPrimaryKeysOfExportedEntities();
}
