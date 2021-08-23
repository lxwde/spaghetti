package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;
import org.apache.commons.lang.StringUtils;

public class SubQueryPredicateBase implements IPredicate {
    protected final IMetafieldId _fieldId;
    protected final IDomainQuery _subQuery;
    protected static final String SUB_ALIAS_PREFIX = "sub";
    protected static final String SUPER_REF = "~";
    protected static final char DOT = '.';

    SubQueryPredicateBase(IDomainQuery inSubQuery, IMetafieldId inMetafieldId) {
        this._subQuery = inSubQuery;
        this._fieldId = inMetafieldId;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String subAlias = SUB_ALIAS_PREFIX + inEntityAlias;
        String subQuery = "(" + this.getSubQueryString(subAlias) + ')';
        String replace = subAlias + '.' + SUPER_REF;
        subQuery = StringUtils.replace((String)subQuery, (String)replace, (String)inEntityAlias);
        String hql = FieldUtils.getAliasedField(inEntityAlias, this._fieldId) + this.getOp() + '(' + subQuery + ')';
        return hql;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return this._subQuery.getFieldValues();
    }

    String getOp() {
        return " in ";
    }

    public String toString() {
        String subAlias = this._subQuery.getQueryEntityName();
        String str = String.valueOf(this._fieldId) + this.getOp() + '(' + this.getSubQueryString(subAlias) + ')';
        str = StringUtils.replace((String)str, (String)(subAlias + '.' + SUPER_REF), (String)SUPER_REF);
        return str;
    }

    protected String getSubQueryString(String inSubAlias) {
        return this._subQuery.toHqlSelectString(inSubAlias) + ' ' + this._subQuery.toHqlObjectQueryString(inSubAlias);
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        throw new UnsupportedOperationException("You can not use an in subquery predicate in a filter.");
    }

}
