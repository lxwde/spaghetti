package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class SubQueryNotInPredicate extends SubQueryPredicateBase {

    SubQueryNotInPredicate(IDomainQuery inSubQuery, IMetafieldId inMetafieldId) {
        super(inSubQuery, inMetafieldId);
    }

    @Override
    String getOp() {
        return " not in ";
    }
}
