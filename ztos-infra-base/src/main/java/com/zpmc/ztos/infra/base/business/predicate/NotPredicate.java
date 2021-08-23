package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;

public class NotPredicate implements IPredicate {

    private IPredicate _negatedPredicate;

    NotPredicate(IPredicate inNegatedPredicate) {
        this._negatedPredicate = inNegatedPredicate;
    }

    public NotPredicate() {
    }

    public IPredicate getUnnegatedPredicate() {
        return this._negatedPredicate;
    }

    public void setNegatedPredicate(IPredicate inPredicate) {
        this._negatedPredicate = inPredicate;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        return "not " + this._negatedPredicate.toHqlString(inEntityAlias);
    }

    public String toString() {
        return "not " + this._negatedPredicate.toString();
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return this._negatedPredicate.getFieldValues();
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        return !this._negatedPredicate.isSatisfiedBy(inValueSource);
    }
}
