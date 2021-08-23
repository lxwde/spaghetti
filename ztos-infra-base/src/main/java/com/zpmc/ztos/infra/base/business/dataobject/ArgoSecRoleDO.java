package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.security.SecRole;

import java.io.Serializable;

public class ArgoSecRoleDO extends SecRole implements Serializable {
    private Operator argroleOperator;

    public Operator getArgroleOperator() {
        return this.argroleOperator;
    }

    public void setArgroleOperator(Operator argroleOperator) {
        this.argroleOperator = argroleOperator;
    }
}
