package com.zpmc.ztos.infra.base.business.interfaces;


import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface IQueryResult extends IBounded, IMetafieldIndexer, Serializable {
    public static final int UNKNOWN_TOTAL_COUNT = -1;

    @Nullable
    public Object getValue(int var1, IMetafieldId var2);

    public Object getValue(int var1, int var2);

    public IValueHolder getValueHolder(int var1);

    public int getCurrentResultCount();

    public int getLastResult();

    public int getTotalResultCount();

    public Iterator getIterator();

    @Deprecated
    public List getRetrievedResults();
}
