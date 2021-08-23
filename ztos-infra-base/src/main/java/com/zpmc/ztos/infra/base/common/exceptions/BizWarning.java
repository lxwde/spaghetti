package com.zpmc.ztos.infra.base.common.exceptions;

import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;

public abstract class BizWarning {

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain) {
        BizViolation bv = BizViolation.create(inKey, inExceptionChain);
        bv.setSeverity(MessageLevelEnum.WARNING);
        return bv;
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1) {
        BizViolation bv = BizViolation.create(inKey, inExceptionChain, inParm1);
        bv.setSeverity(MessageLevelEnum.WARNING);
        return bv;
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1, Object inParm2) {
        BizViolation bv = BizViolation.create(inKey, inExceptionChain, inParm1, inParm2);
        bv.setSeverity(MessageLevelEnum.WARNING);
        return bv;
    }

    public static BizViolation create(IPropertyKey inKey, BizViolation inExceptionChain, Object inParm1, Object inParm2, Object inParm3) {
        BizViolation bv = BizViolation.create(inKey, inExceptionChain, inParm1, inParm2, inParm3);
        bv.setSeverity(MessageLevelEnum.WARNING);
        return bv;
    }

}
