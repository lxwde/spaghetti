package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import org.apache.commons.lang.StringUtils;

public abstract class SubQueryCountPredicate extends SubQueryAggregatePredicateBase {
    private final Long _countValue;

    SubQueryCountPredicate(IDomainQuery inSubQuery, long inCountValue) {
        super(inSubQuery);
        this._countValue = inCountValue;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String subAlias = "sub" + inEntityAlias;
        String hql = "(" + this._subQuery.toHqlCountString(subAlias) + ')' + this.getOp() + this._countValue;
        String replace = subAlias + '.' + "~";
        hql = StringUtils.replace((String)hql, (String)replace, (String)inEntityAlias);
        return hql;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return this._subQuery.getFieldValues();
    }

    public String toString() {
        String subAlias = this._subQuery.getQueryEntityName();
        String str = "(" + this._subQuery.toHqlCountString(subAlias) + ')' + this.getOp() + this._countValue;
        str = StringUtils.replace((String)str, (String)(subAlias + '.' + "~"), (String)"~");
        return str;
    }
}
