package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collection;

public interface IPrivilegeManager {
    public static final String BEAN_ID = "privilegeManager";

    @Nullable
    public IPrivilege findPrivilege(IPrivilegeId var1);

    public Collection<IPrivilege> getAllPrivileges();

    public Collection<IPrivilege> getScopedPrivileges(UserContext var1);

    public Collection<IFeatureId> getAllFeatures(MetafieldIdList var1);

    public Collection<IPrivilege> getPrivilegesByFeature(IFeatureId var1);
}
