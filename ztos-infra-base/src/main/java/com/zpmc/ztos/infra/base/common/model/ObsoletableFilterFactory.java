package com.zpmc.ztos.infra.base.common.model;


import com.zpmc.ztos.infra.base.business.enums.framework.AllLifeCycleStatesEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IQueryFilter;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

public class ObsoletableFilterFactory {

    private ObsoletableFilterFactory() {
    }

    public static IQueryFilter createShowAllFilter() {
        return ObsoletableFilterFactory.createFilter(AllLifeCycleStatesEnum.ALL);
    }

    public static IQueryFilter createShowActiveFilter() {
        return ObsoletableFilterFactory.createFilter(LifeCycleStateEnum.ACTIVE);
    }

    public static IQueryFilter createShowObsoleteableFilter() {
        return ObsoletableFilterFactory.createFilter(LifeCycleStateEnum.OBSOLETE);
    }

    private static IQueryFilter createFilter(AtomizedEnum inFilteredValue) {
//        return new DefaultQueryFilter("obsoleteableFilter", "lifeCycleState", inFilteredValue);
        return null;
    }
}
