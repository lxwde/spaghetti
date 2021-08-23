package com.zpmc.ztos.infra.base.common.systems;

import com.zpmc.ztos.infra.base.business.dataobject.HoldPermissionViewDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class HoldPermissionView extends HoldPermissionViewDO {
    public static HoldPermissionView createHoldPermissionView(String inId) {
        HoldPermissionView holdPermissionView = new HoldPermissionView();
        holdPermissionView.setHpvId(inId);
        HibernateApi.getInstance().save((Object)holdPermissionView);
        return holdPermissionView;
    }

    @Nullable
    public static HoldPermissionView findHoldPermissionView(String inId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"HoldPermissionView").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.HPV_ID, (Object)inId));
        return (HoldPermissionView)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FlagType").addDqPredicate(PredicateFactory.eq((IMetafieldId) IServicesField.FLGTYP_HOLD_PERM_VIEW, (Object)this.getHpvGkey()));
        Serializable[] flagGkeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (flagGkeys != null && flagGkeys.length > 0) {
            bv = BizViolation.create((IPropertyKey) IServicesPropertyKeys.ERROR_DELETING_HOLD_PERM_VIEW, null, null);
        }
        return bv;
    }

}
