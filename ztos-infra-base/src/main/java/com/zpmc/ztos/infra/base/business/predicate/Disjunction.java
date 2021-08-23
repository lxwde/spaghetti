package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.business.model.AbstractJunction;

import java.util.Iterator;

public class Disjunction extends AbstractJunction {

    @Override
    protected String getOp() {
        return " or ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        int predicateCount = 0;
        Iterator iterator = this.getPredicateIterator();
        while (iterator.hasNext()) {
            IPredicate predicate = (IPredicate)iterator.next();
            ++predicateCount;
            if (!predicate.isSatisfiedBy(inValueSource)) continue;
            return true;
        }
        return predicateCount == 0;
    }
}
