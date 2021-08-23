package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.BinTypeDO;
import com.zpmc.ztos.infra.base.business.interfaces.IBinField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

public class BinType extends BinTypeDO {

    public static final boolean IS_SYSTEM_DEFINED = true;
    public static final boolean FLUSH_ON_CREATION = true;

    public BinType() {
        this.setBtpSystemDefined(true);
    }

    public static BinType updateOrCreateBinType(String inId, boolean inIsSystemDefined, String inDescription, long inLevelRestriction, BinContext inBinContext) {
        BinType binType = BinType.findBinType(inId);
        if (binType == null) {
            binType = new BinType();
            binType.setBtpId(inId);
     //       HibernateApi.getInstance().save((Object)binType);
        }
        binType.setBtpSystemDefined(inIsSystemDefined);
        binType.setBtpDescription(inDescription);
        binType.setBtpLevelRestriction(inLevelRestriction);
        binType.setBtpContext(inBinContext);
        return binType;
    }

    public static BinType findOrCreateBinType(String inId, boolean inIsSystemDefined, String inDescription, long inLevelRestriction, BinContext inBinContext, boolean inFlushIfCreating) {
        BinType binType = BinType.findBinType(inId);
        if (binType == null) {
            binType = BinType.updateOrCreateBinType(inId, inIsSystemDefined, inDescription, inLevelRestriction, inBinContext);
            if (inFlushIfCreating) {
   //             HibernateApi.getInstance().flush();
            }
        }
        return binType;
    }

    public static BinType findBinType(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BTP_ID, (Object)inId));
  //      return (BinType)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
        return null;
    }

}
