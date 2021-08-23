package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;

public abstract class AbstractFieldPredicate implements IPredicate {
    protected final IMetafieldId _fieldId;

    protected AbstractFieldPredicate(IMetafieldId metafieldId) {
        this._fieldId = metafieldId;
    }

    public IMetafieldId getFieldId() {
        return this._fieldId;
    }
}
