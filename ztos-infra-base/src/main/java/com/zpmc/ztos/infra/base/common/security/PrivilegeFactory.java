package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.enums.framework.UserActionEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IFeatureId;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IMutablePrivilege;
import com.zpmc.ztos.infra.base.business.interfaces.IPrivilege;
import com.zpmc.ztos.infra.base.common.model.DelegatingPrivilege;
import com.zpmc.ztos.infra.base.common.model.HardCodedResourceKey;

import java.util.List;

public class PrivilegeFactory {

    private PrivilegeFactory() {
    }

    public static IPrivilege valueOf(String inPrivilegeName) {
        DelegatingPrivilege privilege = new DelegatingPrivilege(inPrivilegeName);
        return privilege;
    }

    public static IMutablePrivilege createPrivilege(String inPrivilegeName, String inModuleId, String inInitialVersion, List<IFeatureId> inFeatureIds) {
        return new Privilege(inPrivilegeName, inModuleId, inInitialVersion, inFeatureIds);
    }

    public static String getPrivilegeName(IMetafieldId inFieldId, UserActionEnum inAction) {
        return "field." + inAction.getName() + "." + inFieldId.getFieldId();
    }

    public static IMutablePrivilege createPrivilege(IMetafieldId inFieldId, UserActionEnum inAction) {
        String privName = PrivilegeFactory.getPrivilegeName(inFieldId, inAction);
        Privilege privilege = new Privilege(privName, null, null, (String)null);
        HardCodedResourceKey desc = HardCodedResourceKey.valueOf(privilege.getDescriptionKey(), inFieldId.getFieldId());
        privilege.setDescriptionKey(desc);
        privilege.setNameKey(desc);
        return privilege;
    }
}
