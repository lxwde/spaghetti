package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public abstract class SubQueryAggregatePredicateBase implements IPredicate {
    protected final IDomainQuery _subQuery;
    protected static final String SUB_ALIAS_PREFIX = "sub";
    protected static final String SUPER_REF = "~";
    protected static final char DOT = '.';

    SubQueryAggregatePredicateBase(IDomainQuery inSubQuery) {
        this._subQuery = inSubQuery;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        throw new UnsupportedOperationException("You can not use an aggregate predicate in a filter.");
    }

    abstract String getOp();
}
