package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.model.ValueObject;

import java.util.List;
import java.util.Map;

public interface IExtensionTypeManager {
    public static final String BEAN_ID = "extensionTypeManager";

    public Map<String, IExtensionType> getAllExtensions(UserContext var1);

    public List<ValueObject> getAllExtensionVAOs(UserContext var1);

    public ValueObject findExtensionVAO(IExtensionTypeId var1);

    public IExtensionType findExtension(IExtensionTypeId var1);
}
