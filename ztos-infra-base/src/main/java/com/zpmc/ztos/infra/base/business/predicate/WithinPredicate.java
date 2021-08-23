package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class WithinPredicate extends GeometryPredicate {
    public WithinPredicate(IMetafieldId inFieldId, Object inValue) {
        super(4, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " within ";
    }

}
