package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueHolder;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;


public class ValueObject extends ValueObjectLegacy implements IValueHolder {
    //private SequencedHashMap _valueMap = new SequencedHashMap();
    private HashMap _valueMap = new HashMap();
    private String _entityClassName;
    private Serializable _entityPrimaryKey;
    private IMetafieldId _primaryKeyField;
    private long _expirationTimeMillis;
    private static final String INDENT_2 = "\n      ";
    private static final String INDENT_3 = "\n         ";
    private static final Logger LOGGER = Logger.getLogger(ValueObject.class.getName());

    public ValueObject(String inEntityClass) {
        this._entityClassName = inEntityClass;
    }

    public ValueObject(String inEntityClass, IValueHolder inValueHolder) {
        this._entityClassName = inEntityClass;
        this._entityPrimaryKey = inValueHolder.getEntityPrimaryKey() instanceof String && StringUtils.isEmpty((String)((String)((Object)inValueHolder.getEntityPrimaryKey()))) ? null : inValueHolder.getEntityPrimaryKey();
        this._primaryKeyField = inValueHolder.getPrimaryKeyField();
        for (IMetafieldId metafieldId : inValueHolder.getFields())
            this._valueMap.put((Object) metafieldId, inValueHolder.getFieldValue(metafieldId));
    }

    @Override
    public MetafieldIdList getFields() {
        MetafieldIdList ids = new MetafieldIdList(this._valueMap.size());
        for (Object id : this._valueMap.keySet()) {
            ids.add((IMetafieldId)id);
        }
        return ids;
    }

    public String getEntityClassName() {
        return this._entityClassName;
    }

    @Override
    public Serializable getEntityPrimaryKey() {
        return this._entityPrimaryKey;
    }


    public void setEntityPrimaryKey(Serializable inEntityPrimaryKey) {
        this._entityPrimaryKey = inEntityPrimaryKey instanceof String && StringUtils.isEmpty((String)((String)((Object)inEntityPrimaryKey))) ? null : inEntityPrimaryKey;
    }

    @Override
    public IMetafieldId getPrimaryKeyField() {
        return this._primaryKeyField;
    }

    public void setPrimaryKeyField(IMetafieldId inMetafieldId) {
        this._primaryKeyField = inMetafieldId;
    }

    public void setFieldValue(IMetafieldId inMetaId, Object inFieldValue) {
        this._valueMap.put((Object)inMetaId, inFieldValue);
    }

    @Override
    @Nullable
    public Object getFieldValue(IMetafieldId inMetaId) {
        Object value = this._valueMap.get((Object)inMetaId);
        if (value == null) {
            if (IMetafieldId.PRIMARY_KEY.equals(inMetaId)) {
                return this._entityPrimaryKey;
            }
            if (this._primaryKeyField != null && this._primaryKeyField.equals(inMetaId)) {
                return this._entityPrimaryKey;
            }
        }
        return value;
    }

//    @Override
    public Object getFieldValue(int inIndex) {
        if (inIndex < 0 || inIndex >= this._valueMap.size()) {
            throw new IllegalArgumentException("getFieldValue: invalid index: " + inIndex);
        }
//        Object fieldValue = this._valueMap.getValue(inIndex);
//        return fieldValue;
        return null;
    }

    @Override
    public boolean isFieldPresent(IMetafieldId inMetaId) {
        return this._valueMap.containsKey((Object)inMetaId) || this._primaryKeyField != null && (this._primaryKeyField.equals(inMetaId) || IMetafieldId.PRIMARY_KEY.equals(inMetaId));
    }

    public void setFieldIfNotPresent(IMetafieldId inMetaId, Object inFieldValue) {
        if (!this._valueMap.containsKey((Object)inMetaId)) {
            this._valueMap.put((Object)inMetaId, inFieldValue);
        }
    }

    public void setFieldIfNotPresentOrNull(IMetafieldId inMetaId, Object inFieldValue) {
        if (this.getFieldValue(inMetaId) == null) {
            this.setFieldValue(inMetaId, inFieldValue);
        }
    }

    public void unsetFieldValue(IMetafieldId inMetaId) {
        this._valueMap.remove((Object)inMetaId);
    }

    public void setFieldValues(Map inFieldValues) {
        this._valueMap = new HashMap(inFieldValues);
    }

    public String getFieldAsString(IMetafieldId inMetaId) {
        Object value = this.getFieldValue(inMetaId);
        if (value != null) {
            return value.toString();
        }
        if (!this.isFieldPresent(inMetaId)) {
  //          LOGGER.error((Object)("getFieldAsString: <" + inMetaId + "> does not exist. "));
        }
        return "";
    }

    @Nullable
    public String getFieldString(IMetafieldId inMetaId) {
        Object value = this.getFieldValue(inMetaId);
        if (value instanceof String) {
            return (String)value;
        }
        if (value == null) {
            return null;
        }
       // //LOGGER.error((Object)("getFieldAsString: attempt to fetch " + inMetaId + " from " + this + " as a String"));
        return value.toString();
    }

    @Nullable
    public Double getFieldDouble(IMetafieldId inMetaId) {
        Object value = this.getFieldValue(inMetaId);
        if (value instanceof Double) {
            return (Double)value;
        }
       // //LOGGER.error((Object)("getFieldDouble: attempt to fetch " + inMetaId + " from " + this.toString() + " as a Double"));
        return null;
    }

    @Nullable
    public Long getFieldLong(IMetafieldId inMetaId) {
        Object value = this.getFieldValue(inMetaId);
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long)value;
        }
       // //LOGGER.error((Object)("getFieldLong: attempt to fetch " + inMetaId + " from " + this.toString() + " as a Long"));
        return null;
    }

    @Nullable
    public Date getFieldDate(IMetafieldId inMetaId) {
        Object value = this.getFieldValue(inMetaId);
        if (null == value) {
            return null;
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        //LOGGER.error((Object)("getFieldDate: attempt to fetch " + inMetaId + " from " + this.toString() + " as a Timestamp"));
        return null;
    }

    public long getFieldLongValue(IMetafieldId inMetaId, long inDefaultResult) {
        Object value = this.getFieldValue(inMetaId);
        if (value instanceof Long) {
            return (Long)value;
        }
        return inDefaultResult;
    }

    public double getFieldDoubleValue(IMetafieldId inMetaId, double inDefaultResult) {
        Object value = this.getFieldValue(inMetaId);
        if (value instanceof Double) {
            return (Double)value;
        }
        return inDefaultResult;
    }

    @Override
    public int getFieldCount() {
        return this._valueMap.size();
    }

    public Iterator getMetafieldIdIterator() {
        return this._valueMap.keySet().iterator();
    }

    public Iterator getValueIterator() {
        return this._valueMap.values().iterator();
    }

    public Map getMetafieldIdValueMapCopy() {
        //SequencedHashMap result = new SequencedHashMap();
        HashMap result = new HashMap();
        result.putAll(this._valueMap);
        return result;
    }

    public void appendValueObject(ValueObject inVAO) {
        this._valueMap.putAll(inVAO.getValueMap());
    }

    public String toString() {
        return "value object:  class=" + this.getEntityClassName() + "  key=" + this.getEntityPrimaryKey();
    }

    public StringBuffer getDetailsForLog() {
        StringBuffer strBuf = new StringBuffer("\n      value object:  class=" + this.getEntityClassName() + "  key=" + this.getEntityPrimaryKey());
        TreeSet<String> set = new TreeSet<String>();
        set.addAll(this.getStringFields());
        for (String fieldId : set) {
            Object fieldValue = this.getFieldValue(fieldId);
            strBuf.append("\n         <").append(fieldId).append("> = ");
            strBuf.append("'").append(ValueObject.fieldValue2String(fieldValue)).append("'");
        }
        return strBuf;
    }

    @Override
    protected Map getValueMap() {
        return this._valueMap;
    }

    public long getTimeToLive() {
        return this._expirationTimeMillis;
    }

    public void setTimeToLive(long inTimeToLiveSeconds) {
        this._expirationTimeMillis = TimeUtils.getCurrentTimeMillis() + inTimeToLiveSeconds * 1000L;
    }

    public void setAbsoluteExpirationTime(long inExpirationTimeMillis) {
        this._expirationTimeMillis = inExpirationTimeMillis;
    }

    public boolean hasExpired() {
        return this._expirationTimeMillis != 0L && TimeUtils.getCurrentTimeMillis() > this._expirationTimeMillis;
    }

    @Nullable
    public static String fieldValue2String(Object inValue) {
        if (inValue instanceof Object[]) {
            String delimiter = ((Object[])inValue).length > 3 ? "\n" : ",";
            String fieldStr = org.springframework.util.StringUtils.arrayToDelimitedString((Object[])((Object[])inValue), (String)delimiter);
            return "[" + fieldStr + "]";
        }
        if (inValue instanceof Character) {
            Character c = (Character)inValue;
            if (Character.isISOControl(c.charValue())) {
                return "0x" + String.format("%02X", c.charValue());
            }
        } else if (inValue == null) {
            return null;
        }
        return inValue.toString();
    }

}
