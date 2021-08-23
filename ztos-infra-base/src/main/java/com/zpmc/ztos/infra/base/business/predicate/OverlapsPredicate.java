package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class OverlapsPredicate extends GeometryPredicate {
    public OverlapsPredicate(IMetafieldId inFieldId, Object inValue) {
        super(5, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " overlaps ";
    }

}
