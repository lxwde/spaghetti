package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

import java.util.ArrayList;
import java.util.List;

public class AbstractResourceTypeEnum extends AtomizedEnum {

    private int _maxLength = 20;
    private final AbstractResourceTypeEnum _parentTypeEnum;
    private final List _childList = new ArrayList();
    private final String _propertyPrefix;
    private final String _propertySuffix;

    public AbstractResourceTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, AbstractResourceTypeEnum inParentEnum, int inMaxLength, String inPropertyPrefix, String inPropertySuffix) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._maxLength = inMaxLength;
        this._parentTypeEnum = inParentEnum;
        if (this._parentTypeEnum != null) {
            this._parentTypeEnum.addChild(this);
        }
        this._propertyPrefix = inPropertyPrefix;
        this._propertySuffix = inPropertySuffix;
    }

    public int getMaxLength() {
        return this._maxLength;
    }

    private void addChild(AbstractResourceTypeEnum inChildType) {
        this._childList.add(inChildType);
    }

    public List getChildResourceTypes() {
        return this._childList;
    }

    public AbstractResourceTypeEnum getParentTypeEnum() {
        return this._parentTypeEnum;
    }

    public String getPropertyPrefix() {
        return this._propertyPrefix;
    }

    public String getPropertySuffix() {
        return this._propertySuffix;
    }

    public String getQualifiedPropertyId(String inId) {
        return this.getPropertyPrefix() + inId + this.getPropertySuffix();
    }

    public IPropertyKey getQualifiedPropertyKey(String inId) {
        return PropertyKeyFactory.valueOf(this.getQualifiedPropertyId(inId));
    }

}
