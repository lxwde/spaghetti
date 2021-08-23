package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class CrossesPredicate extends GeometryPredicate {
    public CrossesPredicate(IMetafieldId inFieldId, Object inValue) {
        super(3, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " crosses ";
    }

}
