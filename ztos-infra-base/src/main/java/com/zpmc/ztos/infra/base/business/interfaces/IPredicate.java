package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;

import java.util.function.Predicate;

public interface IPredicate extends IMetaFilter, Predicate<IValueHolder> {
    @NotNull
    public String toHqlString(String var1);

    public KeyValuePair[] getFieldValues();

    @Override
    default public boolean test(@NotNull IValueHolder valueHolder) {
        return this.isSatisfiedBy(valueHolder);
    }
}
