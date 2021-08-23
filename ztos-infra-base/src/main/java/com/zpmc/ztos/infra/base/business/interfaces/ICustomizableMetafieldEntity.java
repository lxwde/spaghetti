package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;

import java.util.Set;

public interface ICustomizableMetafieldEntity {
    public boolean supportsModelExtensions();

    @Nullable
    public IMetafieldId getModelExtensionDynamicComponent();

    public boolean supportsCodeExtensions();

    public boolean isDynamic();

    public Set<IMetafieldId> getDynamicFields();

    public Set<IMetafieldId> getDynamicFieldsWithInheritedFields();
}
