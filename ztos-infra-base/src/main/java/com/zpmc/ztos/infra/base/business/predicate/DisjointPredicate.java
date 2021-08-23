package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class DisjointPredicate extends GeometryPredicate{
    public DisjointPredicate(IMetafieldId inFieldId, Object inValue) {
        super(1, inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " disjoint ";
    }

}
