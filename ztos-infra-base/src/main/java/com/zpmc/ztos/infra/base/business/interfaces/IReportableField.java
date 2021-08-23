package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ValueObject;

public interface IReportableField {
    public boolean getCanUseInReportDesign();

    public boolean getCanUseInFilter();

    public boolean getCanUseInSorting();

    public boolean getUseDescriptiveVersion();

    public IMetafieldId getMetafieldId();

    public String getExternalTag();

    public String getClassType();

    public String getDescription();

    public boolean getCanUseForUpdating();

    public String getFinderMethod();

    public String getUpdateMethod();

    public boolean isBuiltIn();

    public boolean isEnabled();

    public ValueObject extractVao();

    public String getOverrideableLovKey();
}
