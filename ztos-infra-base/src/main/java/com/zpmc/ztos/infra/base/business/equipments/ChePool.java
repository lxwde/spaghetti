package com.zpmc.ztos.infra.base.business.equipments;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.ChePoolDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CheKindEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IQueryResult;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class ChePool extends ChePoolDO {
    private static final Logger LOGGER = Logger.getLogger(ChePool.class);

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @Nullable
    public static ChePool findByPkey(Long inYardKey, Long inPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ChePool").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POOL_PKEY, (Object)inPkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.POOL_YARD, (Object)inYardKey));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() > 1) {
            LOGGER.error((Object)(matches.size() + " ChePool found with pKey " + inPkey + " expected only 1 (yard gKey " + inYardKey + ")"));
            Collections.sort(matches, new Comparator<ChePool>(){

                @Override
                public int compare(ChePool inPool1, ChePool inPool2) {
                    return inPool2.getPoolGkey().compareTo(inPool1.getPoolGkey());
                }
            });
        }
        return matches.isEmpty() ? null : (ChePool)matches.get(0);
    }

    public static ChePool hydrate(Serializable inPrimaryKey) {
        return (ChePool)HibernateApi.getInstance().load(ChePool.class, inPrimaryKey);
    }

    public static Set<CheKindEnum> getDistinctCheKindsInChePool(@NotNull ChePool inChePool) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Che").addDqField(IArgoField.CHE_KIND_ENUM).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.CHE_POOL, (Object)inChePool.getPoolGkey()));
        dq.setDqFieldsDistinct(true);
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
        HashSet<CheKindEnum> distinctCheKinds = new HashSet<CheKindEnum>();
        for (int i = 0; i < result.getTotalResultCount(); ++i) {
            distinctCheKinds.add((CheKindEnum)((Object)result.getValue(i, IArgoField.CHE_KIND_ENUM)));
        }
        return distinctCheKinds;
    }

}
