package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.EntityPropertyEnum;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.sun.istack.Nullable;

import java.util.List;
import java.util.Set;

public interface IMetafieldEntity extends ICustomizableMetafieldEntity{
    public IPropertyKey getPropertyKey(EntityPropertyEnum var1);

    @Deprecated
    public String getEntityName();

    public String getClassName();

    public String getAlias();

    public IEntityId getEntityId();

    public IMetafieldId getPrimaryId();

    @Deprecated
    public IMetafieldId getBusinessKey();

    public MetafieldIdList getEntityFields();

    public Set<IMetafieldId> getAllEntityFields();

    public boolean isObsoletable();

    public IMetafieldId getObsoletableField();

    public boolean hasSubclasses();

    public boolean isSubclass();

    @Nullable
    public String getParentClass();

    @Nullable
    public List<String> getSubclassEntityNames();

    public Object getAttribute(String var1);

}
