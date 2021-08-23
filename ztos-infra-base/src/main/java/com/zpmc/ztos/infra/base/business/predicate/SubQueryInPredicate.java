package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class SubQueryInPredicate extends SubQueryPredicateBase {
    SubQueryInPredicate(IDomainQuery inSubQuery, IMetafieldId inMetafieldId) {
        super(inSubQuery, inMetafieldId);
    }

    @Override
    String getOp() {
        return " in ";
    }
}
