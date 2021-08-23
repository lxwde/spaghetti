package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import org.apache.commons.lang.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FieldChangeTracker {

    private Map _initialValues = new LinkedHashMap();

    public FieldChangeTracker(IEntity inEntity, IMetafieldId[] inFields) {
        if (inEntity != null) {
            for (int i = 0; i < inFields.length; ++i) {
                IMetafieldId field = inFields[i];
                this._initialValues.put(field, inEntity.getFieldValue(field));
            }
        }
    }

    public FieldChanges getChanges(IEntity inEntity) {
        FieldChanges fieldChanges = new FieldChanges();
        if (inEntity != null) {
            Set entries = this._initialValues.entrySet();
            for (Object entry : entries) {
                Object value1;
                IMetafieldId field = (IMetafieldId)((Map.Entry)entry).getKey();
                Object value0 = ((Map.Entry)entry).getValue();
                if (ObjectUtils.equals(value0, (Object)(value1 = inEntity.getFieldValue(field)))) continue;
                fieldChanges.setFieldChange(field, value0, value1);
            }
        }
        return fieldChanges;
    }

}
