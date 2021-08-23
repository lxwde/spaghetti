package com.zpmc.ztos.infra.base.business.enums.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IEqLinkContext;

public enum NodeSwipeRuleEnum {

    IGNORE,
    VALIDATE,
    SWIPE;


    public static NodeSwipeRuleEnum getRoleInUseValidationRule(@NotNull IEqLinkContext inLinkContext) {
        EqUnitRoleEnum role = inLinkContext.getRole();
        if (EqUnitRoleEnum.PAYLOAD == role || EqUnitRoleEnum.PRIMARY == role) {
            return IGNORE;
        }
        if (DataSourceEnum.IN_GATE == inLinkContext.getDataSourceEnum()) {
            return SWIPE;
        }
        if (DataSourceEnum.USER_LCL != inLinkContext.getDataSourceEnum()) {
            return VALIDATE;
        }
        if (EqUnitRoleEnum.CARRIAGE == role) {
            return inLinkContext.isSwipeAllowed() ? SWIPE : VALIDATE;
        }
        if (inLinkContext.isMultipleNodeAllowed()) {
            return IGNORE;
        }
        return inLinkContext.isSwipeAllowed() ? SWIPE : VALIDATE;
    }

    public static NodeSwipeRuleEnum getNodeInUseValidationRule(@NotNull IEqLinkContext inLinkContext) {
        EqUnitRoleEnum role = inLinkContext.getRole();
        if (!inLinkContext.isStrictlyValidated()) {
            return IGNORE;
        }
        if (inLinkContext.isMultipleNodeAllowed() && EqUnitRoleEnum.CARRIAGE == role) {
            return IGNORE;
        }
        if (inLinkContext.isSwipeAllowed()) {
            return SWIPE;
        }
        return VALIDATE;
    }
}
