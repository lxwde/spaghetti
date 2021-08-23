package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IPrivilege extends IFeatureAware, IScoped, IDetailedDiagnostics {
    public IPrivilegeId getPrivilegeId();

    public String getModuleId();

    public IPropertyKey getNameKey();

    public IPropertyKey getDescriptionKey();

    public String getInitialVersion();

    public boolean isAllowed(UserContext var1);

    public String getDebugHistory();
}
