package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.SecRoleDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SecRole extends SecRoleDO {
    private static final Logger LOGGER = Logger.getLogger(SecRole.class);

    public SecRole() {
        this.setRoleIsDelegated(Boolean.FALSE);
    }

    public boolean onDelete(Session inSession) throws CallbackException {
        LOGGER.info((Object)"REMOVING  user role mappings");
        for (Object user : this.getRoleBuserList()) {
            LOGGER.info((Object)("Removing ROLE for " + ((BaseUser)user).getBuserUid()));
            ((BaseUser)user).getBuserRoleList().remove(this);
        }
        this.getRoleBuserList().clear();
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

    @Override
    public void applyFieldChanges(FieldChanges inFieldChanges) {
        if (inFieldChanges.hasFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES)) {
            Object newPrivs = inFieldChanges.getFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES).getNewValue();
            Object oldPrivs = inFieldChanges.getFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES).getPriorValue();
            inFieldChanges.removeFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES);
            super.applyFieldChanges(inFieldChanges);
            if (newPrivs instanceof Serializable[]) {
                Serializable[] serializablePrivs = (Serializable[])newPrivs;
                String[] stringPrivs = new String[serializablePrivs.length];
                for (int i = 0; i < stringPrivs.length; ++i) {
                    stringPrivs[i] = (String)((Object)serializablePrivs[i]);
                }
                this.setRolePrivileges(stringPrivs);
                this.removePrivsFromDerivedRoles(newPrivs, oldPrivs);
            } else if (newPrivs == null) {
                this.setRolePrivileges(null);
                this.removePrivsFromDerivedRoles(newPrivs, oldPrivs);
            }
        } else {
            super.applyFieldChanges(inFieldChanges);
        }
    }

    private void removePrivsFromDerivedRoles(Object inNewPrivs, Object inOldPrivs) {
        if (inOldPrivs != null && this.getRoleIsDelegated() != null && this.getRoleIsDelegated().booleanValue()) {
            Collection<Serializable> removedList = null;
            removedList = inNewPrivs != null ? this.findRemovedPriv((Serializable[])inOldPrivs, (Serializable[])inNewPrivs) : Arrays.asList((Serializable[])inOldPrivs);
            if (removedList != null && !removedList.isEmpty()) {
                this.removePrivsFromInheritedRoles(removedList);
            }
        }
    }

    private Collection findRemovedPriv(Serializable[] inPrevPrivs, Serializable[] inNewPrivs) {
        ArrayList<Serializable> removedList = new ArrayList<Serializable>();
        for (int i = 0; i < inPrevPrivs.length; ++i) {
            boolean isContained = false;
            for (int j = 0; j < inNewPrivs.length; ++j) {
                if (inPrevPrivs[i].toString().compareTo(inNewPrivs[j].toString()) != 0) continue;
                isContained = true;
                break;
            }
            if (isContained) continue;
            removedList.add(inPrevPrivs[i]);
        }
        return removedList;
    }

    private void removePrivsFromInheritedRoles(Collection inRemovedList) {
        IDomainQuery dq = QueryUtils.createDomainQuery("SecRole");
        dq.addDqPredicate(PredicateFactory.eq(ISecurityField.ROLE_PARENT, this.getRoleGkey()));
        List entitiesByDomainQuery = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        for (Object childRole : entitiesByDomainQuery) {
            String[] existPriv = ((SecRole)childRole).getRolePrivileges();
            ArrayList<String> cleanList = new ArrayList<String>();
            for (int i = 0; i < existPriv.length; ++i) {
                if (inRemovedList.contains(existPriv[i])) continue;
                cleanList.add(existPriv[i]);
            }
            String[] list = new String[cleanList.size()];
            cleanList.toArray(list);
            ((SecRole)childRole).setRolePrivileges(list);
        }
    }

    public String[] getRolePrivileges() {
        List privs = this.getRolePrivilegesList();
        String[] privArr = new String[privs.size()];
        int i = 0;
//        for (SecRolePrivilegeMapping mapping : privs) {
//            privArr[i++] = mapping.getRlprivmPrivId();
//        }
        return privArr;
    }

    public void addPrivilege(IPrivilege inPrivilege) {
//        SecRolePrivilegeMapping privilegeMapping = this.getPrivilegeMapping(inPrivilege);
//        if (privilegeMapping == null) {
//            SecRolePrivilegeMapping privilege = new SecRolePrivilegeMapping();
//            privilege.setRlprivmPrivId(inPrivilege.getAttribute());
//            privilege.setRlprivmRoleGkey(this);
//            ArrayList<SecRolePrivilegeMapping> rolePrivilegesList = this.getRolePrivilegesList();
//            if (rolePrivilegesList == null) {
//                rolePrivilegesList = new ArrayList<SecRolePrivilegeMapping>();
//            }
//            rolePrivilegesList.add(privilege);
//        }
    }

//    @Nullable
//    private SecRolePrivilegeMapping getPrivilegeMapping(IPrivilege inPrivilege) {
//        IDomainQuery dq = QueryUtils.createDomainQuery("SecRolePrivilegeMapping");
//        dq.addDqPredicate(PredicateFactory.eq(ISecurityField.RLPRIVM_ROLE_GKEY, this.getRoleGkey()));
//        dq.addDqPredicate(PredicateFactory.eq(ISecurityField.RLPRIVM_PRIV_ID, inPrivilege.getAttribute()));
//        List entitiesByDomainQuery = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
//        if (!entitiesByDomainQuery.isEmpty()) {
//            return (SecRolePrivilegeMapping)entitiesByDomainQuery.get(0);
//        }
//        return null;
//    }

    public void removePrivilege(IPrivilege inPrivilege) {
//        SecRolePrivilegeMapping privilegeMapping = this.getPrivilegeMapping(inPrivilege);
//        if (privilegeMapping != null) {
//            List rolePrivilegesList = this.getRolePrivilegesList();
//            rolePrivilegesList.remove(privilegeMapping);
//        }
    }

    protected void setRolePrivileges(String[] inPrivs) {
//        ArrayList<Object> privs = this.getRolePrivilegesList();
//        if (privs == null) {
//            privs = new ArrayList<Object>();
//            this.setRolePrivilegesList(privs);
//        }
//        HashMap<String, SecRolePrivilegeMapping> privmap = new HashMap<String, SecRolePrivilegeMapping>();
//        for (SecRolePrivilegeMapping secRolePrivilegeMapping : privs) {
//            privmap.put(secRolePrivilegeMapping.getRlprivmPrivId(), secRolePrivilegeMapping);
//        }
//        privs.clear();
//        if (inPrivs != null) {
//            for (int i = 0; i < inPrivs.length; ++i) {
//                String string = inPrivs[i];
//                if (privmap.containsKey(string)) {
//                    privs.add(privmap.get(string));
//                    privmap.remove(string);
//                    continue;
//                }
//                SecRolePrivilegeMapping newMapping = new SecRolePrivilegeMapping();
//                newMapping.setRlprivmPrivId(string);
//                newMapping.setRlprivmRoleGkey(this);
//                privs.add(newMapping);
//            }
//        }
//        privmap.clear();
    }

    public List getRolePrivilegeIds() {
        List privs = this.getRolePrivilegesList();
        ArrayList<String> privilegeIds = new ArrayList<String>(privs.size());
//        for (SecRolePrivilegeMapping mapping : privs) {
//            privilegeIds.add(mapping.getRlprivmPrivId());
//        }
        return privilegeIds;
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        if (inChanges.hasFieldChange(ISecurityField.ROLE_SEC_NAME) && !this.isUniqueInClass(ISecurityField.ROLE_SEC_NAME, false)) {
            bizViolation = BizViolation.create(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, bizViolation, ISecurityField.ROLE_SEC_NAME, inChanges.getFieldChange(ISecurityField.ROLE_SEC_NAME).getNewValue(), this.getRoleSecName());
        }
        return bizViolation;
    }

    @Override
    public String getHumanReadableKey() {
        return this.getRoleSecName();
    }


}
