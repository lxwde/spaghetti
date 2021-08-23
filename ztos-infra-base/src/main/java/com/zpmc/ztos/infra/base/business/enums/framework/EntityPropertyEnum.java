package com.zpmc.ztos.infra.base.business.enums.framework;
import org.apache.commons.lang.enums.Enum;
public class EntityPropertyEnum extends Enum{

    public static final EntityPropertyEnum ENTITY_NAME = new EntityPropertyEnum(ResourceTypeEnum.ENTITY_NAME);
    public static final EntityPropertyEnum ENTITY_PLURAL_NAME = new EntityPropertyEnum(ResourceTypeEnum.ENTITY_PLURAL_NAME);
    private final ResourceTypeEnum _resourceType;

    private EntityPropertyEnum(ResourceTypeEnum inType) {
        super(inType.getName());
        this._resourceType = inType;
    }

    public ResourceTypeEnum getResourceType() {
        return this._resourceType;
    }
}
