package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.PlacardDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class Placard extends PlacardDO {
    private static final Logger LOGGER = Logger.getLogger(Placard.class);

    public static Placard createPlacard(String inText, String inExplanation, Double inMinWtInKg) {
        Placard placard = new Placard();
        placard.setPlacardText(inText);
        placard.setPlacardFurtherExplanation(inExplanation);
        placard.setPlacardMinWtKg(inMinWtInKg);
        HibernateApi.getInstance().saveOrUpdate((Object)placard);
        return placard;
    }

    public static Placard findPlacard(String inText) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Placard").addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.PLACARD_TEXT, (Object)inText));
        return (Placard)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Placard findPlacardProxy(String inText) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Placard").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.PLACARD_TEXT, (Object)inText));
        Serializable[] placardGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (placardGkey == null || placardGkey.length == 0) {
            return null;
        }
        if (placardGkey.length == 1) {
            return (Placard)HibernateApi.getInstance().load(Placard.class, placardGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(placardGkey.length), (Object)dq);
    }

    public String toString() {
        return "Placard Id:" + this.getPlacardText();
    }

    public IMetafieldId getScopeFieldId() {
        return IInventoryField.PLACARD_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IInventoryField.PLACARD_TEXT;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public static Placard hydrate(Serializable inPrimaryKey) {
        return (Placard)HibernateApi.getInstance().load(Placard.class, inPrimaryKey);
    }

}
