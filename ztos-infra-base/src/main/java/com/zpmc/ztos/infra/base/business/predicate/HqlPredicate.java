package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;

public class HqlPredicate implements IPredicate {

    final String _hql;
    final KeyValuePair[] _keyValuePair;

    HqlPredicate(String inHql, KeyValuePair[] inValuePair) {
        this._hql = inHql;
        this._keyValuePair = inValuePair == null ? new KeyValuePair[0] : inValuePair;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return this._keyValuePair;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return this._hql;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        throw new UnsupportedOperationException("You can not use a free HQL in a filter.");
    }

}
