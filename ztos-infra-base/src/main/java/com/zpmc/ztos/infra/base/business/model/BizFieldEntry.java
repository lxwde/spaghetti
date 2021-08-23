package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class BizFieldEntry {
    private IMetafieldId _metaFieldId;
    private BizRoleEnum _bizRole;
    private boolean _isNullViewed;

    public BizFieldEntry(IMetafieldId inFieldId, BizRoleEnum inBizRole) {
        this._metaFieldId = inFieldId;
        this._bizRole = inBizRole;
        this._isNullViewed = false;
    }

    public BizFieldEntry(IMetafieldId inFieldId, BizRoleEnum inBizRole, boolean inIsNullViewed) {
        this._metaFieldId = inFieldId;
        this._bizRole = inBizRole;
        this._isNullViewed = inIsNullViewed;
    }

    public IMetafieldId getFieldId() {
        return this._metaFieldId;
    }

    public BizRoleEnum getBizRole() {
        return this._bizRole;
    }

    public boolean isNullViewed() {
        return this._isNullViewed;
    }
}
