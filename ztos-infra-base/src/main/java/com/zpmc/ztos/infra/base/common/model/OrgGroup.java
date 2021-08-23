package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.dataobject.OrgGroupDO;
import com.zpmc.ztos.infra.base.business.interfaces.IFrameworkPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityField;
import com.zpmc.ztos.infra.base.business.model.Organization;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Set;

public class OrgGroup extends OrgGroupDO {
    private static final Logger LOGGER = Logger.getLogger(OrgGroup.class);

    public boolean isOrganizationInGroup(Organization inOrg) {
        Set orgs = this.getGrpOrgList();
        if (orgs != null) {
            return orgs.contains(inOrg);
        }
        return false;
    }

    public boolean onSave(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onUpdate(Session inSession) throws CallbackException {
        return false;
    }

    public boolean onDelete(Session inSession) throws CallbackException {
        LOGGER.info((Object)"REMOVING  user group mappings");
//        for (BaseUser user : this.getGrpBuserList()) {
//            Set groups = user.getBuserGroupList();
//            LOGGER.info((Object)("Removing group for " + user.getBuserUid()));
//            groups.remove(this);
//        }
        this.getGrpBuserList().clear();
        return false;
    }

    public void onLoad(Session inSession, Serializable inSerializable) {
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        Set orgs;
        BizViolation bizViolation = super.validateChanges(inChanges);
        bizViolation = this.checkUniqueFieldViolation(bizViolation, inChanges, ISecurityField.GRP_NAME);
        if (inChanges.hasFieldChange(ISecurityField.GRP_TYPE) && (orgs = this.getGrpOrgList()) != null && !orgs.isEmpty()) {
            bizViolation = BizViolation.create(IFrameworkPropertyKeys.FRAMEWORK__CANNOT_MODIFY_GROUP_TYPE, bizViolation);
        }
        return bizViolation;
    }
}
