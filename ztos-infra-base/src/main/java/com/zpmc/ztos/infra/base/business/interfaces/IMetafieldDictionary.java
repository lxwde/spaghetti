package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMetafieldDictionary {
    public boolean isFieldDefined(IMetafieldId var1);

    @Nullable
    public IMetafield findMetafield(IMetafieldId var1);

    public IFieldValidator getValidator();

    public MetafieldIdList getAllMetafieldIds();

    public IMetafieldEntity getEntityEntry(IEntityId var1);

    public List<IEntityId> getAllEntityIds();

    public Map<String, Collection<IMetafieldGroup>> getAllGroupsByCategory();

    public Map<String, IMetafieldGroup> getAllGroupsByGroupId();

    @Nullable
    public Set<IMetafieldGroup> getGroupIds(String var1);

    public Set getEnabledCustomedMetafields(String var1);

    public boolean isStale();
}
