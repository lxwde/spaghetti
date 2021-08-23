package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;

public class SubQueryCountEqPredicate extends SubQueryCountPredicate {
    SubQueryCountEqPredicate(IDomainQuery inSubQuery, long inCountValue) {
        super(inSubQuery, inCountValue);
    }

    @Override
    String getOp() {
        return " = ";
    }
}
