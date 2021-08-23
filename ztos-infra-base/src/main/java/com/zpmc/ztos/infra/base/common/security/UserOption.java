package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.UserOptionDO;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.ISecurityField;
import org.hibernate.Hibernate;

import java.util.Map;

public class UserOption extends UserOptionDO {
    @Override
    public Object getField(IMetafieldId inMetafieldId) {
        if (ISecurityField.USEROPT_WND_VIEW_BOUNDS.equals(inMetafieldId)) {
            Map viewBounds = super.getUseroptWndViewBounds();
            Hibernate.initialize((Object)viewBounds);
            return viewBounds;
        }
        return super.getField(inMetafieldId);
    }
}
