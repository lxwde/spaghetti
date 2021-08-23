package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Junction implements IPredicate {

    private final List<IPredicate> _predicates = new ArrayList<IPredicate>();

    public Junction add(IPredicate inPredicate) {
        this._predicates.add(inPredicate);
        return this;
    }

    protected abstract String getOp();

    @Override
    public KeyValuePair[] getFieldValues() {
        ArrayList<KeyValuePair> typedValues = new ArrayList<KeyValuePair>();
        for (IPredicate predicate : this._predicates) {
            KeyValuePair[] subvalues = predicate.getFieldValues();
            typedValues.addAll(Arrays.asList(subvalues));
        }
        return typedValues.toArray(new KeyValuePair[typedValues.size()]);
    }

    public int getPredicateCount() {
        return this._predicates.size();
    }

    public Iterator getPredicateIterator() {
        return this._predicates.iterator();
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        StringBuilder buffer = new StringBuilder("(");
        Iterator<IPredicate> iter = this._predicates.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next().toHqlString(inEntityAlias));
            if (!iter.hasNext()) continue;
            buffer.append(this.getOp());
        }
        return buffer.append(')').toString();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder().append('(');
        int i = 0;
        for (IPredicate predicate : this._predicates) {
            if (i++ > 0) {
                buf.append(this.getOp());
            }
            buf.append(predicate);
        }
        buf.append(')');
        return buf.toString();
    }
}
