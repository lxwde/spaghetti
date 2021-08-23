package com.zpmc.ztos.infra.base.business.interfaces;

public interface IDomainQueryTuner {
    public IDomainQuery tune(QueryTypeEnum var1, IDomainQuery var2);

    public static enum QueryTypeEnum {
        FETCH_PRIMARY_KEYS,
        FETCH_VALUES;

    }

}
