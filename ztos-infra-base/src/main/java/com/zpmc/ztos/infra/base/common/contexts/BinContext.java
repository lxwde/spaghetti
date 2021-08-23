package com.zpmc.ztos.infra.base.common.contexts;

import com.zpmc.ztos.infra.base.business.dataobject.BinContextDO;
import com.zpmc.ztos.infra.base.business.interfaces.IBinField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class BinContext extends BinContextDO {

    public static final boolean IS_SYSTEM_DEFINED = true;
    public static final boolean FLUSH_ON_CREATION = true;

    public BinContext() {
        this.setBcxSystemDefined(true);
    }

    public static BinContext updateOrCreateBinContext(String inId, boolean inIsSystemDefined, String inDescription) {
        BinContext binContext = BinContext.findBinContext(inId);
        if (binContext == null) {
            binContext = new BinContext();
            binContext.setBcxId(inId);
            HibernateApi.getInstance().save((Object)binContext);
        }
        binContext.setBcxSystemDefined(inIsSystemDefined);
        binContext.setBcxDescription(inDescription);
        return binContext;
    }

    private static BinContext createBinContext(String inId, boolean inIsSystemDefined, String inDescription) {
        BinContext binContext = new BinContext();
        binContext.setBcxId(inId);
        binContext.setBcxSystemDefined(inIsSystemDefined);
        binContext.setBcxDescription(inDescription);
        Roastery.getHibernateApi().save((Object)binContext);
        return binContext;
    }

    public static BinContext findOrCreateBinContext(String inId, boolean inIsSystemDefined, String inDescription, boolean inFlushIfCreating) {
        BinContext binContext = BinContext.findBinContext(inId);
        if (binContext == null) {
            binContext = BinContext.createBinContext(inId, inIsSystemDefined, inDescription);
            if (inFlushIfCreating) {
                HibernateApi.getInstance().flush();
            }
        } else {
            binContext.setBcxSystemDefined(inIsSystemDefined);
            binContext.setBcxDescription(inDescription);
        }
        return binContext;
    }

    @Nullable
    public static BinContext findBinContext(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinContext").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BCX_ID, (Object)inId));
        return (BinContext) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    @Override
    public void preDelete() {

    }
}
