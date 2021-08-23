package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.CustomizationComponentContentTypeEnum;

public interface IPlatformComponentType extends ITypeAware{
    public CustomizationComponentContentTypeEnum getComponentContentType();
}
