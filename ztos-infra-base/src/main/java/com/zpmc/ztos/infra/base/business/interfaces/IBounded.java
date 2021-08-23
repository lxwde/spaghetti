package com.zpmc.ztos.infra.base.business.interfaces;

public interface IBounded {

    public static final int FIRST_QUERY_RESULT = 0;
    public static final int UNBOUNDED_MAX = 0;

    public int getMaxResults();

    public int getFirstResult();

    public boolean isQueryBounded();

}
