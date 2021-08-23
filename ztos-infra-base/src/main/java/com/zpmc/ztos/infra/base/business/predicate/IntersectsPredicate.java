package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class IntersectsPredicate extends GeometryPredicate {
    public IntersectsPredicate(IMetafieldId inFieldId, Object inValue) {
        super(7, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " intersects ";
    }

}
