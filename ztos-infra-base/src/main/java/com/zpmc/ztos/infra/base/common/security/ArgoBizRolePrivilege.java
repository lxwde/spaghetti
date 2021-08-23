package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoBizRolePrivilegeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityField;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;

import java.io.Serializable;

public class ArgoBizRolePrivilege extends ArgoBizRolePrivilegeDO {
    public static final String PREFIX = "_CONSTRAINED_BY_";
    private static final Logger LOGGER = Logger.getLogger(ArgoBizRolePrivilege.class);

    public static ArgoBizRolePrivilege updateArgoRolePrivilege(String inBizRolePrivId, BizRoleEnum inBizRoleEnum) {
        ArgoBizRolePrivilege argoPriv = ArgoBizRolePrivilege.findArgoRolePrivilegeById(inBizRolePrivId);
        argoPriv.setRoprvRole(inBizRoleEnum);
        return argoPriv;
    }

    public static void removeArgoRolePrivilege(String inBizRolePrivId) {
        ArgoBizRolePrivilege map = ArgoBizRolePrivilege.findArgoRolePrivilegeById(inBizRolePrivId);
        HibernateApi.getInstance().delete((Object)map);
    }

    public static ArgoBizRolePrivilege findArgoRolePrivilegeById(String inBizRolePrivId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoBizRolePrivilege").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.ROPRV_PRIV_ID, (Object)inBizRolePrivId));
        return (ArgoBizRolePrivilege)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static ArgoBizRolePrivilege createArgoRolePrivilege(String inBizRolePrivId, BizRoleEnum inBizRoleEnum, Long inOprGkey) {
        ArgoBizRolePrivilege bizRolePriv = new ArgoBizRolePrivilege();
        bizRolePriv.setRoprvPrivId(inBizRolePrivId);
        bizRolePriv.setRoprvRole(inBizRoleEnum);
        bizRolePriv.setRoprvOperator(Operator.loadByGkey(inOprGkey));
        HibernateApi.getInstance().save((Object)bizRolePriv);
        return bizRolePriv;
    }

    public static String generateNewPrivilegeId(String inOriginalPrivId, BizRoleEnum inBizRole) {
        return inOriginalPrivId + PREFIX + inBizRole.getId();
    }

    public boolean onDelete(Session inSession) throws CallbackException {
        LOGGER.info((Object)"REMOVING  user role mappings");
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"SecRolePrivilegeMapping").addDqPredicate(PredicateFactory.eq((IMetafieldId) ISecurityField.RLPRIVM_PRIV_ID, (Object)this.getRoprvPrivId()));
        Serializable[] mappings = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        for (int i = 0; i < mappings.length; ++i) {
//            SecRolePrivilegeMapping mapping = (SecRolePrivilegeMapping)HibernateApi.getInstance().get(SecRolePrivilegeMapping.class, mappings[i]);
//            mapping.purge();
        }
        return false;
    }

//    public void validate() throws ValidationFailure {
//    }

    public void onLoad(Session inSession, Serializable inSerializable) {
    }

    public boolean onSave(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onUpdate(Session inSession) throws CallbackException {
        return false;
    }
}
