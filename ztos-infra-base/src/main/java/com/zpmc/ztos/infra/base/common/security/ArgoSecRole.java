package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.ArgoSecRoleDO;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityField;
import com.zpmc.ztos.infra.base.business.interfaces.IUserArgoField;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.util.ArrayList;

public class ArgoSecRole extends ArgoSecRoleDO {
    public void applyFieldChanges(FieldChanges inFieldChanges) {
        Operator operator = ContextHelper.getThreadOperator();
        inFieldChanges.setFieldChange(IUserArgoField.ARGROLE_OPERATOR, (Object)operator);
        super.applyFieldChanges(inFieldChanges);
    }

    public static ArgoSecRole createArgoSecRole(String inRoleName) {
        ArgoSecRole role = new ArgoSecRole();
        role.setRoleSecName(inRoleName);
        role.setRolePrivilegesList(new ArrayList());
        HibernateApi.getInstance().save((Object)role);
        return role;
    }

    public static ArgoSecRole findArgoSecRole(String inRoleName) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoSecRole").addDqPredicate(PredicateFactory.eq((IMetafieldId) ISecurityField.ROLE_SEC_NAME, (Object)inRoleName));
        return (ArgoSecRole)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static ArgoSecRole findOrCreateSecRole(String inRoleName) {
        ArgoSecRole role = ArgoSecRole.findArgoSecRole(inRoleName);
        if (role == null) {
            role = ArgoSecRole.createArgoSecRole(inRoleName);
        }
        return role;
    }

}
