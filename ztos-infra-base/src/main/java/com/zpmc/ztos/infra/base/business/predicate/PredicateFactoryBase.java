package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.model.Conjunction;

public abstract class PredicateFactoryBase {
    
    protected static final String SUPER_ALIAS_TAG = "~.";

    public static IPredicate pkEq(Object inValue) {
        return new PkEqPredicate(inValue);
    }

    public static Conjunction conjunction() {
        return new Conjunction();
    }

    public static Disjunction disjunction() {
        return new Disjunction();
    }

    public static IPredicate not(IPredicate inPredicate) {
        return new NotPredicate(inPredicate);
    }

    public static IPredicate subQueryCountGt(IDomainQuery inSubQuery, long inValue) {
        return new SubQueryCountGtPredicate(inSubQuery, inValue);
    }

    public static IPredicate subQueryCountEq(IDomainQuery inSubQuery, long inValue) {
        return new SubQueryCountEqPredicate(inSubQuery, inValue);
    }

    public static IPredicate subQueryIn(IDomainQuery inSubQuery, IMetafieldId inFieldId) {
        return new SubQueryInPredicate(inSubQuery, inFieldId);
    }

    public static IPredicate subQueryNotIn(IDomainQuery inSubQuery, IMetafieldId inFieldId) {
        return new SubQueryNotInPredicate(inSubQuery, inFieldId);
    }

    public static IPredicate subQueryExists(IDomainQuery inSubQuery) {
        return new SubQueryExistsPredicate(inSubQuery);
    }
}
