package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.extension.EntityLifecycleInterceptionPointEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;

public class EntityExtensionContext extends AbstractExtensionContext{
    private EntityLifecycleInterceptionPointEnum _interceptionPoint;
    private IEntity _entity;
    private FieldChanges _fieldChanges;

    public EntityExtensionContext(@NotNull UserContext inUserContext, @NotNull IEntity inEntity, FieldChanges inFieldChanges) {
        super(inUserContext);
        this._entity = inEntity;
        this._fieldChanges = inFieldChanges;
    }

    @NotNull
    public IEntity getEntity() {
        return this._entity;
    }

    public void setInterceptionPoint(EntityLifecycleInterceptionPointEnum inInterceptionPoint) {
        this._interceptionPoint = inInterceptionPoint;
    }

    public FieldChanges getFieldChanges() {
        return this._fieldChanges;
    }

    public EntityLifecycleInterceptionPointEnum getInterceptionPoint() {
        return this._interceptionPoint;
    }

    @Override
    public String getBriefDescription() {
        return (Object)((Object)this._interceptionPoint) + " on " + this._entity.getEntityName() + " - " + this._entity.getHumanReadableKey();
    }

    public String toString() {
        return super.toString() + "  for user " + this.getUserContext().getUserId() + " : " + (Object)((Object)this._interceptionPoint) + " on " + this._entity.getEntityName() + " - " + this._entity.getHumanReadableKey() + " with " + this._fieldChanges;
    }
}
