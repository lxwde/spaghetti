package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.business.interfaces.IXpscachePropertyKeys;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Random;

public class XpsPkeyGenerator {
    private XpsPkeyGenerator() {
    }

    public static Long generate(String inEntityName, IMetafieldId inPkeyMetafield, IMetafieldId inScopeMetafield, Serializable inScopeKey) throws BizFailure {
        Class entityClass = HiberCache.entityName2EntityClass((String)inEntityName);
        int theManagerID = XpsDbManagerId.getTableId(entityClass);
        if (theManagerID < 0) {
            throw BizFailure.create((IPropertyKey) IXpscachePropertyKeys.FAILED_GEN_UNIQUE_PKEY_ATTEMPT, null, (Object)inEntityName, (Object)inScopeMetafield.getFieldId(), (Object)inScopeKey);
        }
        return XpsPkeyGenerator.generate(theManagerID, inEntityName, inPkeyMetafield, inScopeMetafield, inScopeKey);
    }

    public static Long generate(int inManagerID, String inEntityName, IMetafieldId inPkeyMetafield, IMetafieldId inScopeMetafield, Serializable inScopeKey) throws BizFailure {
        return XpsPkeyGenerator.generate(0, Integer.MAX_VALUE, inManagerID, inEntityName, inPkeyMetafield, inScopeMetafield, inScopeKey);
    }

    public static Long generate(int inMinBaseValue, int inMaxBaseValue, int inManagerID, String inEntityName, IMetafieldId inPkeyMetafield, IMetafieldId inScopeMetafield, Serializable inScopeKey) throws BizFailure {
        int numberOfTries = 10000;
        Long result = null;
        Random generator = new Random();
        for (int attempts = 1; attempts <= 10000; ++attempts) {
            boolean keyIsAvailable;
            boolean noNullChar;
            int theKey = generator.nextInt(inMaxBaseValue - inMinBaseValue) + inMinBaseValue;
            if (theKey > 0) {
                theKey = -theKey;
            } else if (theKey == 0) {
                theKey = -1;
            }
            int mgrIdMasked = inManagerID & 0xFF;
            theKey = (int)((long)theKey & 0xFFFFFF00L | (long)mgrIdMasked);
            boolean bl = noNullChar = ((long)theKey & 0xFFL) != 0L && ((long)theKey & 0xFF00L) != 0L && ((long)theKey & 0xFF0000L) != 0L && ((long)theKey & 0xFF000000L) != 0L;
            if (theKey == 0 || !noNullChar) continue;
            IDomainQuery dq = QueryUtils.createDomainQuery((String)inEntityName);
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)inPkeyMetafield, (Object)theKey));
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)inScopeMetafield, (Object)inScopeKey));
            boolean bl2 = keyIsAvailable = !HibernateApi.getInstance().existsByDomainQuery(dq);
            if (!keyIsAvailable) continue;
            result = new Long(theKey);
            break;
        }
        if (result == null) {
            throw BizFailure.create((IPropertyKey) IXpscachePropertyKeys.FAILURE_GENERATE_UNIQUE_PKEY, null, (Object)inEntityName, (Object)inScopeMetafield.getFieldId(), (Object)inScopeKey);
        }
        return result;
    }
}
