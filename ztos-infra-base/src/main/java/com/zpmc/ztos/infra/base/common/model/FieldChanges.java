package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IEFieldChange;
import com.zpmc.ztos.infra.base.business.interfaces.IEFieldChanges;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;

import org.apache.commons.collections.SequencedHashMap;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FieldChanges implements Serializable, IEFieldChanges {

    private final SequencedHashMap _fieldChangeMap;
    private static final int CHARACTERS_TO_OUTPUT = 20;
    private static final int ROWS_TO_OUTPUT = 4;

    @Deprecated
    public FieldChanges(FieldChange[] inFieldChanges) {
        this._fieldChangeMap = new SequencedHashMap(inFieldChanges.length);
        for (int i = 0; i < inFieldChanges.length; ++i) {
            if (inFieldChanges[i] == null) continue;
            this._fieldChangeMap.put((Object)inFieldChanges[i].getMetafieldId(), (Object)inFieldChanges[i]);
        }
    }

    public FieldChanges(int inLength) {
        this._fieldChangeMap = new SequencedHashMap(inLength);
    }

    public FieldChanges(ValueObject inValueObject) {
        this._fieldChangeMap = new SequencedHashMap(inValueObject.getFieldCount());
        Iterator i = inValueObject.getMetafieldIdIterator();
        while (i.hasNext()) {
            IMetafieldId mfid = (IMetafieldId)i.next();
            Object v = inValueObject.getFieldValue(mfid);
            FieldChange fc = new FieldChange(mfid, v);
            this._fieldChangeMap.put((Object)mfid, (Object)fc);
        }
    }

    public FieldChanges(FieldChanges inFieldChanges) {
        this._fieldChangeMap = new SequencedHashMap(inFieldChanges.getFieldChangeCount());
        Iterator<FieldChange> iterator = inFieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            this._fieldChangeMap.put((Object)fc.getMetafieldId(), (Object)fc);
        }
    }

    public FieldChanges() {
        this._fieldChangeMap = new SequencedHashMap(0);
    }

    public FieldChanges(Map inChangesAsMap) {
        this._fieldChangeMap = new SequencedHashMap(0);
        for (Object e : inChangesAsMap.entrySet()) {
            Object key = ((Map.Entry)e).getKey();
            if (!(key instanceof IMetafieldId)) {
                throw BizFailure.create("Illegal Map input parameter to FieldChanges constructor");
            }
            IMetafieldId mfid = (IMetafieldId)key;
            this._fieldChangeMap.put((Object)mfid, (Object)new FieldChange(mfid, ((Map.Entry)e).getValue()));
        }
    }

    public void setFieldChange(FieldChange inFieldChange) {
        if (inFieldChange != null && inFieldChange.getMetafieldId() != null) {
            this._fieldChangeMap.put((Object)inFieldChange.getMetafieldId(), (Object)inFieldChange);
        }
    }

    @Override
    public void setFieldChange(IMetafieldId inFieldId, Object inNewValue) {
        this.setFieldChange(new FieldChange(inFieldId, inNewValue));
    }

    public void setFieldChange(IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        this.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
    }

    public void removeFieldChange(IMetafieldId inFieldId) {
        this._fieldChangeMap.remove((Object)inFieldId);
    }

    public FieldChange getFieldChange(IMetafieldId inFieldId) {
        return (FieldChange)this._fieldChangeMap.get((Object)inFieldId);
    }

    @Override
    public IEFieldChange findFieldChange(IMetafieldId inFieldId) {
        return (IEFieldChange)this._fieldChangeMap.get((Object)inFieldId);
    }

    @Override
    public boolean hasFieldChange(IMetafieldId inFieldId) {
        return this._fieldChangeMap.containsKey((Object)inFieldId);
    }

    public Iterator<FieldChange> getIterator() {
        return this._fieldChangeMap.values().iterator();
    }

    @Override
    public int getFieldChangeCount() {
        return this._fieldChangeMap.size();
    }

    public Set getFieldSet() {
        return this._fieldChangeMap.entrySet();
    }

    protected SequencedHashMap getFieldChangeMap() {
        return this._fieldChangeMap;
    }

    public MetafieldIdList getFieldIds() {
        MetafieldIdList ids = new MetafieldIdList(this._fieldChangeMap.size());
        for (Object id : this._fieldChangeMap.keySet()) {
            ids.add((IMetafieldId)id);
        }
        return ids;
    }

    @Deprecated
    public FieldChange getFieldChange(int inIndex) {
        Object index = this.getFieldChangeMap().get(inIndex);
        Object entry = this.getFieldChangeMap().get(index);
        return (FieldChange)entry;
    }

    @Deprecated
    public FieldChange[] asArray() {
        return (FieldChange[]) this.getFieldChangeMap().values().toArray(new FieldChange[this.getFieldChangeMap().size()]);
    }

    @Deprecated
    public int getLength() {
        return this.getFieldChangeCount();
    }

    public void addFieldChanges(FieldChanges inFieldChanges) {
        Iterator<FieldChange> it = inFieldChanges.getIterator();
        while (it.hasNext()) {
            FieldChange fc = it.next();
            this.setFieldChange(fc);
        }
    }

    public String toString() {
        try {
            if (!this._fieldChangeMap.isEmpty()) {
                String toString = "FieldChanges(" + this._fieldChangeMap.size() + ") \n" + StringUtils.collectionToDelimitedString((Collection)this._fieldChangeMap.values(), (String)"\n", (String)"      -", (String)"");
                return toString;
            }
            return "empty FieldChanges";
        }
        catch (Throwable t) {
            return "Error getting toString with " + t;
        }
    }

//    public String toUserFacingString() {
//        StringBuilder sb = new StringBuilder();
//        try {
//            if (!this._fieldChangeMap.isEmpty()) {
//                Set metafieldIds = this._fieldChangeMap.keySet();
//                int counter = 0;
//                Iterator iterator = metafieldIds.iterator();
//                while (iterator.hasNext()) {
//                    if (++counter > 4) {
//                        sb.append(FrameworkPresentationUtils.getTranslation(FrameworkUiPropertyKeys.LABEL__FRM_FORM_CLOSE_TOO_MANY_VALUES));
//                        break;
//                    }
//                    IMetafieldId metafieldId = (IMetafieldId)iterator.next();
//                    if (metafieldId.getFieldId().equals("messageField")) continue;
//                    String fieldLongName = FrameworkPresentationUtils.getFieldLongLabel(metafieldId);
//                    FieldChange fc = (FieldChange)this._fieldChangeMap.get((Object)metafieldId);
//                    String newValue = "";
//                    if (fc.getNewValue() != null) {
//                        newValue = fc.getNewValue().toString();
//                    }
//                    if (newValue.length() > 20) {
//                        newValue = newValue.substring(0, 20) + "...";
//                    }
//                    newValue.replaceAll("\n", "");
//                    sb.append(fieldLongName + " = " + newValue + "\n");
//                }
//                return sb.toString();
//            }
//            return "empty FieldChanges";
//        }
//        catch (Throwable t) {
//            return "Error getting toUserFacingString with " + t;
//        }
//    }
}
