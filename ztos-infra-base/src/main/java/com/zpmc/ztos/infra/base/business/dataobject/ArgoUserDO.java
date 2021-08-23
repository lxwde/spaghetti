package com.zpmc.ztos.infra.base.business.dataobject;


import com.zpmc.ztos.infra.base.business.enums.argo.KeySetOwnerEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.common.security.BaseUser;
import com.zpmc.ztos.infra.base.common.security.BizGroup;

import java.io.Serializable;

public class ArgoUserDO extends BaseUser implements Serializable {
    private Long argouserHorizonDays;
    private KeySetOwnerEnum argouserMyListChoice;
    private BizGroup argouserBizGroup;
    private Operator argouserOperator;
    private Complex argouserComplex;
    private Facility argouserFacility;
    private Yard argouserYard;
    private ScopedBizUnit argouserCompanyBizUnit;

    public Long getArgouserHorizonDays() {
        return this.argouserHorizonDays;
    }

    protected void setArgouserHorizonDays(Long argouserHorizonDays) {
        this.argouserHorizonDays = argouserHorizonDays;
    }

    public KeySetOwnerEnum getArgouserMyListChoice() {
        return this.argouserMyListChoice;
    }

    protected void setArgouserMyListChoice(KeySetOwnerEnum argouserMyListChoice) {
        this.argouserMyListChoice = argouserMyListChoice;
    }

    public BizGroup getArgouserBizGroup() {
        return this.argouserBizGroup;
    }

    protected void setArgouserBizGroup(BizGroup argouserBizGroup) {
        this.argouserBizGroup = argouserBizGroup;
    }

    public Operator getArgouserOperator() {
        return this.argouserOperator;
    }

    protected void setArgouserOperator(Operator argouserOperator) {
        this.argouserOperator = argouserOperator;
    }

    public Complex getArgouserComplex() {
        return this.argouserComplex;
    }

    protected void setArgouserComplex(Complex argouserComplex) {
        this.argouserComplex = argouserComplex;
    }

    public Facility getArgouserFacility() {
        return this.argouserFacility;
    }

    protected void setArgouserFacility(Facility argouserFacility) {
        this.argouserFacility = argouserFacility;
    }

    public Yard getArgouserYard() {
        return this.argouserYard;
    }

    protected void setArgouserYard(Yard argouserYard) {
        this.argouserYard = argouserYard;
    }

    public ScopedBizUnit getArgouserCompanyBizUnit() {
        return this.argouserCompanyBizUnit;
    }

    protected void setArgouserCompanyBizUnit(ScopedBizUnit argouserCompanyBizUnit) {
        this.argouserCompanyBizUnit = argouserCompanyBizUnit;
    }
}
