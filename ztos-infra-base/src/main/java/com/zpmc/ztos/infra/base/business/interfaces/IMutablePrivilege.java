package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;

import java.util.List;

public interface IMutablePrivilege extends IPrivilege{
    public void setDescriptionKey(IPropertyKey var1);

    public void setNameKey(IPropertyKey var1);

    public void setScopeCoordinates(ScopeCoordinates var1);

    public void setFeatureIds(List<IFeatureId> var1);

    public void setDebugHistory(String var1);

    public void appendSource(IDetailedDiagnostics var1);
}
