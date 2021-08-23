package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class TouchesPredicate extends GeometryPredicate {
    public TouchesPredicate(IMetafieldId inFieldId, Object inValue) {
        super(2, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " touches ";
    }
}
