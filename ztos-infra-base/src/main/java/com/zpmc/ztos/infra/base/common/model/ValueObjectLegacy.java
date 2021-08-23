package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.sun.istack.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public abstract class ValueObjectLegacy implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ValueObjectLegacy.class.getName());

    @Deprecated
    public boolean isFieldPresent(String inFieldId) {
        return this.getValueMap().containsKey(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Deprecated
    public Object getFieldValue(String inFieldId) {
        return this.getValueMap().get(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Deprecated
    protected List<String> getStringFields() {
        ArrayList<String> fields = new ArrayList<String>();
        for (Object o : this.getValueMap().keySet()) {
            IMetafieldId id = (IMetafieldId)o;
            fields.add(id.getQualifiedId());
        }
        return fields;
    }

    @Deprecated
    public Iterator<String> getFieldIterator() {
        return this.getStringFields().iterator();
    }

    @Deprecated
    public Map<String, ?> getValueMapCopy() {
//        SequencedHashMap result = new SequencedHashMap();
//        Map sourceMap = this.getValueMap();
//        for (IMetafieldId id : sourceMap.keySet()) {
//            result.put(id.getQualifiedId(), sourceMap.get(id));
//        }
//        return result;
        return null;
    }

    @Deprecated
    public Iterator getEntryIterator() {
        return this.getValueMapCopy().entrySet().iterator();
    }

    /**
     * @return
     */
    public KeyValuePair[] getFieldsAsArray() {
        KeyValuePair[] fields = new KeyValuePair[this.getValueMap().size()];
        int fieldIndex = 0;
        Iterator fieldIter = this.getEntryIterator();
        while (fieldIter.hasNext()) {
            Map.Entry fieldEntry = (Map.Entry)fieldIter.next();
            fields[fieldIndex++] = new KeyValuePair(((IMetafieldId)fieldEntry.getKey()).getQualifiedId(), fieldEntry.getValue());
        }
        return fields;
    }

    @Deprecated
    public void flattenRelationalFields() {
        Map<String, ?> valueMapCopy = this.getValueMapCopy();
        for (String key : valueMapCopy.keySet()) {
            int index = key.lastIndexOf(46);
            if (index <= 0) continue;
            String flatKey = key.substring(index + 1, key.length());
            this.setFieldValue(flatKey, this.getFieldValue(key));
            this.unsetFieldValue(key);
        }
    }

    @Deprecated
    @Nullable
    public String getFieldString(String inFieldId) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof String) {
            return (String)value;
        }
        if (value == null) {
            return null;
        }
 //       LOGGER.error((Object)("getFieldAsString: attempt to fetch " + inFieldId + " from " + this + " as a String"));
        return value.toString();
    }

    @Deprecated
    public String getFieldAsString(String inFieldId) {
        Object value = this.getFieldValue(inFieldId);
        if (value != null) {
            return value.toString();
        }
        if (!this.isFieldPresent(inFieldId)) {
 //           LOGGER.error((Object)("getFieldAsString: <" + inFieldId + "> does not exist. "));
        }
        return "";
    }

    @Deprecated
    public double getFieldDoubleValue(String inFieldId, double inDefaultResult) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof Double) {
            return (Double)value;
        }
        return inDefaultResult;
    }

    @Deprecated
    @Nullable
    public Double getFieldDouble(String inFieldId) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof Double) {
            return (Double)value;
        }
      //  LOGGER.error((Object)("getFieldDouble: attempt to fetch " + inFieldId + " from " + this.toString() + " as a Double"));
        return null;
    }

    @Deprecated
    public Long getFieldLong(String inFieldId) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof Long) {
            return (Long)value;
        }
     //   LOGGER.error((Object)("getFieldLong: attempt to fetch " + inFieldId + " from " + this.toString() + " as a Long"));
        return 0L;
    }

    @Deprecated
    public long getFieldLongValue(String inFieldId, long inDefaultResult) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof Long) {
            return (Long)value;
        }
        return inDefaultResult;
    }

    @Deprecated
    @Nullable
    public Date getFieldDate(String inFieldId) {
        Object value = this.getFieldValue(inFieldId);
        if (value instanceof Date) {
            return (Date)value;
        }
     //   LOGGER.error((Object)("getFieldDate: attempt to fetch " + inFieldId + " from " + this.toString() + " as a Timestamp"));
        return null;
    }

    @Deprecated
    public void setFieldValue(String inFieldId, Object inFieldValue) {
        this.getValueMap().put(MetafieldIdFactory.valueOf(inFieldId), inFieldValue);
    }

    @Deprecated
    public void setFieldIfNotPresent(String inFieldId, Object inFieldValue) {
        if (!this.isFieldPresent(inFieldId)) {
            this.setFieldValue(inFieldId, inFieldValue);
        }
    }

    @Deprecated
    public void setFieldIfNotPresentOrNull(String inFieldId, Object inFieldValue) {
        if (this.getFieldValue(inFieldId) == null) {
            this.setFieldValue(inFieldId, inFieldValue);
        }
    }

    @Deprecated
    public void unsetFieldValue(String inFieldId) {
        this.getValueMap().remove(MetafieldIdFactory.valueOf(inFieldId));
    }

    protected abstract Map getValueMap();

}
