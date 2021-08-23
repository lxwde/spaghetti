package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class BizFieldList {
    private boolean _locked;
    private List _list = new ArrayList();

    public void add(IMetafieldId inFieldId, BizRoleEnum inBizRole) {
        this._list.add(new BizFieldEntry(inFieldId, inBizRole));
    }

    public void add(IMetafieldId inFieldId, BizRoleEnum inBizRole, boolean inIsNullViewed) {
        this._list.add(new BizFieldEntry(inFieldId, inBizRole, inIsNullViewed));
    }

    public ListIterator listIterator() {
        if (!this._locked) {
            this._list = Collections.unmodifiableList(this._list);
            this._locked = true;
        }
        return this._list.listIterator();
    }
}
