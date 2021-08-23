package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

import java.util.Iterator;

public class Conjunction extends AbstractJunction{

    @Override
    protected String getOp() {
        return " and ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Iterator iterator = this.getPredicateIterator();
        while (iterator.hasNext()) {
            IPredicate predicate = (IPredicate)iterator.next();
            if (predicate.isSatisfiedBy(inValueSource)) continue;
            return false;
        }
        return true;
    }
}
