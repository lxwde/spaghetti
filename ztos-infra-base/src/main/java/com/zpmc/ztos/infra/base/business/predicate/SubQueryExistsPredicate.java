package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import org.apache.commons.lang.StringUtils;

public class SubQueryExistsPredicate extends SubQueryPredicateBase
{

    SubQueryExistsPredicate(IDomainQuery inSubQuery) {
        super(inSubQuery, null);
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String subAlias = "sub" + inEntityAlias;
        String subQuery = this.getSubQueryString(subAlias);
        String replace = subAlias + '.' + "~";
        subQuery = StringUtils.replace((String)subQuery, (String)replace, (String)inEntityAlias);
        String hql = "exists (" + subQuery + ')';
        return hql;
    }

    @Override
    public String toString() {
        String subAlias = this._subQuery.getQueryEntityName();
        String str = "exists (" + this.getSubQueryString(subAlias) + ')';
        str = StringUtils.replace((String)str, (String)(subAlias + '.' + "~"), (String)"~");
        return str;
    }
}
