package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class ContainsPredicate extends GeometryPredicate {
    public ContainsPredicate(IMetafieldId inFieldId, Object inValue) {
        super(6, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " contains ";
    }

}
