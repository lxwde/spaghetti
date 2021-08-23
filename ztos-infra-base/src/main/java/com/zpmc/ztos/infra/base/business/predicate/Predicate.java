package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.type.CompareOperationType;

import java.util.Collection;
import java.util.Map;

public class Predicate extends PredicateFactoryBase{
    private Predicate() {
    }

    public static IPredicate eq(String inPropertyName, Object inValue) {
        return new EqPredicate(MetafieldIdFactory.valueOf(inPropertyName), inValue);
    }

    public static IPredicate like(String inPropertyName, String inValue) {
        return new LikePredicate(MetafieldIdFactory.valueOf(inPropertyName), (Object)inValue);
    }

    public static IPredicate ilike(String inPropertyName, String inValue) {
        return new IlikePredicate(MetafieldIdFactory.valueOf(inPropertyName), (Object)inValue);
    }

    public static IPredicate gt(String inPropertyName, Object inValue) {
        return new GtPredicate(MetafieldIdFactory.valueOf(inPropertyName), inValue);
    }

    public static IPredicate lt(String inPropertyName, Object inValue) {
        return new LtPredicate(MetafieldIdFactory.valueOf(inPropertyName), inValue);
    }

    public static IPredicate le(String inPropertyName, Object inValue) {
        return new LePredicate(MetafieldIdFactory.valueOf(inPropertyName), inValue);
    }

    public static IPredicate ge(String inPropertyName, Object inValue) {
        return new GePredicate(MetafieldIdFactory.valueOf(inPropertyName), inValue);
    }

    public static IPredicate in(String inPropertyName, Object[] inValues) {
        return new InPredicate(MetafieldIdFactory.valueOf(inPropertyName), inValues);
    }

    public static IPredicate in(String inPropertyName, Collection inValues) {
        return new InPredicate(MetafieldIdFactory.valueOf(inPropertyName), inValues.toArray());
    }

    public static IPredicate isNull(String inPropertyName) {
        return new NullPredicate(MetafieldIdFactory.valueOf(inPropertyName));
    }

    public static IPredicate eqProperty(String inPropertyName, String inOtherPropertyName) {
        return new EqPropertyPredicate(MetafieldIdFactory.valueOf(inPropertyName), MetafieldIdFactory.valueOf(inOtherPropertyName));
    }

    public static IPredicate ltProperty(String inPropertyName, String inOtherPropertyName) {
        return new LtPropertyPredicate(MetafieldIdFactory.valueOf(inPropertyName), MetafieldIdFactory.valueOf(inOtherPropertyName));
    }

    public static IPredicate leProperty(String inPropertyName, String inOtherPropertyName) {
        return new LePropertyPredicate(MetafieldIdFactory.valueOf(inPropertyName), MetafieldIdFactory.valueOf(inOtherPropertyName));
    }

    public static IPredicate isNotNull(String inPropertyName) {
        return new NotNullPredicate(MetafieldIdFactory.valueOf(inPropertyName));
    }

    public static IPredicate allEq(Map inPropertyNameValues) {
        Conjunction conj = Predicate.conjunction();
        for (Object me : inPropertyNameValues.entrySet()) {
            conj.add(Predicate.eq((String)((Map.Entry)me).getKey(), ((Map.Entry)me).getValue()));
        }
        return conj;
    }

    public static IPredicate subQueryAggregateOp(IDomainQuery inSubQuery, CompareOperationType inCompareOperation, String inPropertyName) {
        return new SubQueryAggregatePredicate(inSubQuery, inCompareOperation, inPropertyName);
    }

    public static String superQueryField(String inFieldId) {
        return "~." + inFieldId;
    }
}
