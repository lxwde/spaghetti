package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;

public class SubQueryCountGtPredicate extends SubQueryCountPredicate {
    SubQueryCountGtPredicate(IDomainQuery inSubQuery, long inCountValue) {
        super(inSubQuery, inCountValue);
    }

    @Override
    String getOp() {
        return " > ";
    }
}
