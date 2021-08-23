package com.zpmc.ztos.infra.base.business.predicate;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.type.CompareOperationType;
import org.apache.commons.lang.StringUtils;

public class SubQueryAggregatePredicate extends SubQueryAggregatePredicateBase{
    private final String _fieldId;
    protected final CompareOperationType _operation;

    SubQueryAggregatePredicate(IDomainQuery inSubQuery, CompareOperationType inCompareOperation, IMetafieldId inFieldId) {
        super(inSubQuery);
        this._fieldId = inFieldId.getQualifiedId();
        this._operation = inCompareOperation;
    }

    SubQueryAggregatePredicate(IDomainQuery inSubQuery, CompareOperationType inCompareOperation, String inPropertyName) {
        super(inSubQuery);
        this._fieldId = inPropertyName;
        this._operation = inCompareOperation;
    }

    @Override
    String getOp() {
        return this._operation.toString();
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String subAlias = "sub" + inEntityAlias;
        String subQuery = "(" + this.getSubQueryString(subAlias) + ')';
        String replace = subAlias + '.' + "~";
        subQuery = StringUtils.replace((String)subQuery, (String)replace, (String)inEntityAlias);
        String hql = subQuery + this.getOp() + inEntityAlias + '.' + this._fieldId;
        return hql;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        return this._subQuery.getFieldValues();
    }

    public String toString() {
        String subAlias = this._subQuery.getQueryEntityName();
        String str = "(" + this.getSubQueryString(subAlias) + ')' + this.getOp() + this._fieldId;
        str = StringUtils.replace((String)str, (String)(subAlias + '.' + "~"), (String)"~");
        return str;
    }

    protected String getSubQueryString(String inSubAlias) {
        return this._subQuery.toHqlSelectString(inSubAlias) + ' ' + this._subQuery.toHqlObjectQueryString(inSubAlias);
    }

}
