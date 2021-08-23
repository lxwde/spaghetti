package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;

public class HazardFireCodeDO extends DatabaseEntity implements Serializable {
    private Long firecodeGkey;
    private ImdgClassEnum firecodeImdgClass;
    private String firecodeUnNbr;
    private String firecodeFireCode;
    private String firecodeFireCodeClass;
    private String firecodeDescription;
    private Date firecodeCreated;
    private String firecodeCreator;
    private Date firecodeChanged;
    private String firecodeChanger;
    private Complex firecodeComplex;

    public Serializable getPrimaryKey() {
        return this.getFirecodeGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getFirecodeGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HazardFireCodeDO)) {
            return false;
        }
        HazardFireCodeDO that = (HazardFireCodeDO)other;
        return ((Object)id).equals(that.getFirecodeGkey());
    }

    public int hashCode() {
        Long id = this.getFirecodeGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getFirecodeGkey() {
        return this.firecodeGkey;
    }

    protected void setFirecodeGkey(Long firecodeGkey) {
        this.firecodeGkey = firecodeGkey;
    }

    public ImdgClassEnum getFirecodeImdgClass() {
        return this.firecodeImdgClass;
    }

    protected void setFirecodeImdgClass(ImdgClassEnum firecodeImdgClass) {
        this.firecodeImdgClass = firecodeImdgClass;
    }

    public String getFirecodeUnNbr() {
        return this.firecodeUnNbr;
    }

    protected void setFirecodeUnNbr(String firecodeUnNbr) {
        this.firecodeUnNbr = firecodeUnNbr;
    }

    public String getFirecodeFireCode() {
        return this.firecodeFireCode;
    }

    protected void setFirecodeFireCode(String firecodeFireCode) {
        this.firecodeFireCode = firecodeFireCode;
    }

    public String getFirecodeFireCodeClass() {
        return this.firecodeFireCodeClass;
    }

    protected void setFirecodeFireCodeClass(String firecodeFireCodeClass) {
        this.firecodeFireCodeClass = firecodeFireCodeClass;
    }

    public String getFirecodeDescription() {
        return this.firecodeDescription;
    }

    protected void setFirecodeDescription(String firecodeDescription) {
        this.firecodeDescription = firecodeDescription;
    }

    public Date getFirecodeCreated() {
        return this.firecodeCreated;
    }

    protected void setFirecodeCreated(Date firecodeCreated) {
        this.firecodeCreated = firecodeCreated;
    }

    public String getFirecodeCreator() {
        return this.firecodeCreator;
    }

    protected void setFirecodeCreator(String firecodeCreator) {
        this.firecodeCreator = firecodeCreator;
    }

    public Date getFirecodeChanged() {
        return this.firecodeChanged;
    }

    protected void setFirecodeChanged(Date firecodeChanged) {
        this.firecodeChanged = firecodeChanged;
    }

    public String getFirecodeChanger() {
        return this.firecodeChanger;
    }

    protected void setFirecodeChanger(String firecodeChanger) {
        this.firecodeChanger = firecodeChanger;
    }

    public Complex getFirecodeComplex() {
        return this.firecodeComplex;
    }

    protected void setFirecodeComplex(Complex firecodeComplex) {
        this.firecodeComplex = firecodeComplex;
    }
}
