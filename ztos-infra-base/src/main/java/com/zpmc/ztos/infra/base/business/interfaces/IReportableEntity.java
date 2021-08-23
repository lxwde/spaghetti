package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public interface IReportableEntity extends Comparable {
    public String getReportingEntityName();

    public String getDisplayName();

    public String getQueryBaseEntityName();

    public String getJoinedEntityName();

    public String getPathToJoinedEntity();

    public String getJoinedEntityFieldPrefix();

    public String getModuleName();

    public void setModuleName(String var1);

    public String getDescription();

    public Map<String, IReportableField> getReportDesignFields();

    public Map<String, IMetafieldId> getSortingFields();

    public Map<String, IReportableField> getSortingReportableFields();

    public MetafieldIdList getFilterCriteriaFields();

    public void addReportableField(IReportableField var1);

    public IMetafieldId translate(String var1);

    public boolean isUseDescriptiveVersion(String var1);

    public MetafieldIdList translate(List var1) throws BizViolation;

    public IReportableField getReportableField(IMetafieldId var1);

    public Map<String, IReportableField> getAllReportableFields();

    public boolean hasUpdateableField();

    public Map<String, IMetafieldId> getUpdateableFields();

    public void setMetafieldDictionary(IMetafieldDictionary var1);

    public boolean isBuiltIn();

    public boolean isEnabled();

    public List<ValueObject> extractVao();

    @Nullable
    public Map<String, IReportableField> getConfigReportableFields();

//    public Map<IMetafieldId, LovKey> getOverrideableLovKeys();
}
