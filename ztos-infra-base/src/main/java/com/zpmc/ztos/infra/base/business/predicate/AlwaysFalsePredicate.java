package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;

public class AlwaysFalsePredicate extends AbstractFieldPredicate {

    private static final KeyValuePair[] NO_VALUES = new KeyValuePair[0];

    AlwaysFalsePredicate(IMetafieldId inFieldId) {
        super(inFieldId);
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return "1=2";
    }

    public String toString() {
        return "1=2";
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return NO_VALUES;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        return false;
    }
}
