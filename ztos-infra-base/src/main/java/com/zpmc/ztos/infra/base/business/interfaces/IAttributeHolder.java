package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Map;

public interface IAttributeHolder<K, V> {
    public V getAttribute(K var1);

    public void setAttribute(K var1, V var2);

    public Map<K, V> getAttributes();

    public void setAttributes(Map<K, V> var1);
}
