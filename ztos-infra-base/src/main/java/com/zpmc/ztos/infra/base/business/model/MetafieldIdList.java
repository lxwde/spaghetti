package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;

public class MetafieldIdList implements Serializable,
        Iterable<IMetafieldId> {
    private final List<IMetafieldId> _metafields;

    public MetafieldIdList() {
        this._metafields = new ArrayList<IMetafieldId>();
    }

    public MetafieldIdList(int inSize) {
        this._metafields = new ArrayList<IMetafieldId>(inSize);
    }

    public MetafieldIdList(IMetafieldId... inIds) {
        this._metafields = inIds != null ? new ArrayList<IMetafieldId>(Arrays.asList(inIds)) : new ArrayList<IMetafieldId>();
    }


    public MetafieldIdList(Collection inCollection) {
        if (inCollection != null) {
            this._metafields = new ArrayList<IMetafieldId>(inCollection);
            for (int i = 0; i < this._metafields.size(); ++i) {
                IMetafieldId o = this._metafields.get(i);
//                if (o instanceof String) {
//                    IMetafieldId id = MetafieldIdFactory.valueOf((String)((Object)o));
//                    this._metafields.set(i, id);
//                    continue;
//                }
                if (o instanceof IMetafieldId) continue;
                throw new IllegalArgumentException("list must be either String or IMetafieldId objects but is instead " + o.getClass());
            }
        } else {
            this._metafields = new ArrayList<IMetafieldId>();
        }
    }

    public MetafieldIdList(MetafieldIdList inIds) {
        this._metafields = inIds == null ? new ArrayList<IMetafieldId>() : new ArrayList<IMetafieldId>(inIds._metafields);
    }

    public MetafieldIdList(String[] inStrings) {
        int size = inStrings.length;
        this._metafields = new ArrayList<IMetafieldId>(size);
        int i = 0;
        while (i < size) {
            String string = inStrings[i++];
            IMetafieldId metafieldId = MetafieldIdFactory.valueOf(string);
            this._metafields.add(metafieldId);
        }
    }

    @Override
    public Iterator<IMetafieldId> iterator() {
        return this._metafields.iterator();
    }

    public ListIterator<IMetafieldId> listIterator() {
        return this._metafields.listIterator();
    }

    public IMetafieldId get(int inDex) {
        return this._metafields.get(inDex);
    }

    public void set(int inDex, IMetafieldId inMetafieldId) {
        this._metafields.set(inDex, inMetafieldId);
    }

    public void add(IMetafieldId inMetafieldId) {
        this._metafields.add(inMetafieldId);
    }

    public void remove(IMetafieldId inMetafieldId) {
        this._metafields.remove(inMetafieldId);
    }

    public void removeAll(MetafieldIdList inMetafieldIdList) {
        this._metafields.removeAll(inMetafieldIdList._metafields);
    }

    public boolean addAll(MetafieldIdList inMetafieldId) {
        return this._metafields.addAll(inMetafieldId._metafields);
    }

    public boolean retainAll(MetafieldIdList inMetafieldIds) {
        return this._metafields.retainAll(inMetafieldIds._metafields);
    }

    public boolean containsSameFields(MetafieldIdList inMetafieldIds) {
        return this._metafields.containsAll(inMetafieldIds._metafields) && inMetafieldIds._metafields.containsAll(this._metafields);
    }

    public int getSize() {
        return this._metafields.size();
    }

    public String[] asStringArray() {
        String[] result = new String[this._metafields.size()];
        int index = 0;
        for (IMetafieldId id : this._metafields) {
            result[index++] = id.getFieldId();
        }
        return result;
    }

    public String[] asQualifiedStringArray() {
        String[] result = new String[this._metafields.size()];
        int index = 0;
        for (IMetafieldId id : this._metafields) {
            result[index++] = id.getQualifiedId();
        }
        return result;
    }

    public IMetafieldId[] asMetafieldIdArray() {
        IMetafieldId[] result = new IMetafieldId[this._metafields.size()];
        int index = 0;
        for (IMetafieldId id : this._metafields) {
            result[index++] = id;
        }
        return result;
    }

    public boolean contains(IMetafieldId inId) {
        return this._metafields.contains(inId);
    }

    public int indexOf(IMetafieldId inId) {
        return this._metafields.indexOf(inId);
    }

    public IMetafieldId getMetafieldIdFromQualifiedId(String inQualifiedString) {
        if (inQualifiedString == null) {
            return null;
        }
        for (IMetafieldId metafieldId : this._metafields) {
            if (!StringUtils.equals((String)inQualifiedString, (String)metafieldId.getQualifiedId())) continue;
            return metafieldId;
        }
        return null;
    }

    public List<IMetafieldId> getFields() {
        return new ArrayList<IMetafieldId>(this._metafields);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        IMetafieldId[] metafieldIds = this._metafields.toArray(new IMetafieldId[this._metafields.size()]);
        for (int i = 0; i < metafieldIds.length; ++i) {
            IMetafieldId metafieldId = metafieldIds[i];
            sb.append(i > 0 ? ", " : "");
            sb.append(metafieldId.getQualifiedId());
        }
        return sb.toString();
    }

}
