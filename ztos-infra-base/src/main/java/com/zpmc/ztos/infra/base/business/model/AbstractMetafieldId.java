package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEntityId;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public abstract class AbstractMetafieldId implements IMetafieldId {

    private IEntityId _entityId;

    @Override
    public abstract String getFieldId();

    @Override
    public String getQualifiedId() {
        return this.getFieldId();
    }

    public boolean equals(Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO instanceof String) {
            String meta = (String)inO;
            return StringUtils.equals((String)meta, (String)this.getQualifiedId());
        }
        if (!(inO instanceof IMetafieldId)) {
            return false;
        }
        IMetafieldId abstractMetafieldId = (IMetafieldId)inO;
        String qid = this.getQualifiedId();
        return StringUtils.equals((String)qid, (String)abstractMetafieldId.getQualifiedId());
    }

    @Override
    public boolean isEntityAware() {
        return this._entityId != null;
    }

    @Override
    public IEntityId getEntityId() {
        return this._entityId;
    }

    public void setEntityId(IEntityId inEntityId) {
        this._entityId = inEntityId;
    }

    public int hashCode() {
        String qid = this.getQualifiedId();
        return qid != null ? qid.hashCode() : 0;
    }

    @Nullable
    public String toString() {
        return this.getQualifiedId();
    }

    public int compareTo(@NotNull Object inObject) {
        if (inObject instanceof IMetafieldId) {
            IMetafieldId other = (IMetafieldId)inObject;
            return this.getQualifiedId().compareTo(other.getQualifiedId());
        }
        return -1;
    }
}
