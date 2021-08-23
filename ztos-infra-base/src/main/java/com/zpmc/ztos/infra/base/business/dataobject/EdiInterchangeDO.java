package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiTradingPartner;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiStatusEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EdiInterchangeDO extends DatabaseEntity implements Serializable {
    private Long ediintGkey;
    private String ediintInterchangeNbr;
    private String ediintDelimiters;
    private EdiMessageDirectionEnum ediintDirection;
    private Date ediintReceived;
    private String ediintFileName;
    private EdiStatusEnum ediintStatus;
    private Date ediintCreated;
    private String ediintCreator;
    private Date ediintChanged;
    private String ediintChanger;
    private EdiTradingPartner ediintTradingPartner;
//    private EdiMailbox ediintMailbox;
    private Set ediBatchSet;
    private Set ediintSegmentSet;

    public Serializable getPrimaryKey() {
        return this.getEdiintGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdiintGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiInterchangeDO)) {
            return false;
        }
        EdiInterchangeDO that = (EdiInterchangeDO)other;
        return ((Object)id).equals(that.getEdiintGkey());
    }

    public int hashCode() {
        Long id = this.getEdiintGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdiintGkey() {
        return this.ediintGkey;
    }

    protected void setEdiintGkey(Long ediintGkey) {
        this.ediintGkey = ediintGkey;
    }

    public String getEdiintInterchangeNbr() {
        return this.ediintInterchangeNbr;
    }

    protected void setEdiintInterchangeNbr(String ediintInterchangeNbr) {
        this.ediintInterchangeNbr = ediintInterchangeNbr;
    }

    public String getEdiintDelimiters() {
        return this.ediintDelimiters;
    }

    protected void setEdiintDelimiters(String ediintDelimiters) {
        this.ediintDelimiters = ediintDelimiters;
    }

    public EdiMessageDirectionEnum getEdiintDirection() {
        return this.ediintDirection;
    }

    protected void setEdiintDirection(EdiMessageDirectionEnum ediintDirection) {
        this.ediintDirection = ediintDirection;
    }

    public Date getEdiintReceived() {
        return this.ediintReceived;
    }

    protected void setEdiintReceived(Date ediintReceived) {
        this.ediintReceived = ediintReceived;
    }

    public String getEdiintFileName() {
        return this.ediintFileName;
    }

    protected void setEdiintFileName(String ediintFileName) {
        this.ediintFileName = ediintFileName;
    }

    public EdiStatusEnum getEdiintStatus() {
        return this.ediintStatus;
    }

    protected void setEdiintStatus(EdiStatusEnum ediintStatus) {
        this.ediintStatus = ediintStatus;
    }

    public Date getEdiintCreated() {
        return this.ediintCreated;
    }

    protected void setEdiintCreated(Date ediintCreated) {
        this.ediintCreated = ediintCreated;
    }

    public String getEdiintCreator() {
        return this.ediintCreator;
    }

    protected void setEdiintCreator(String ediintCreator) {
        this.ediintCreator = ediintCreator;
    }

    public Date getEdiintChanged() {
        return this.ediintChanged;
    }

    protected void setEdiintChanged(Date ediintChanged) {
        this.ediintChanged = ediintChanged;
    }

    public String getEdiintChanger() {
        return this.ediintChanger;
    }

    protected void setEdiintChanger(String ediintChanger) {
        this.ediintChanger = ediintChanger;
    }

    public EdiTradingPartner getEdiintTradingPartner() {
        return this.ediintTradingPartner;
    }

    protected void setEdiintTradingPartner(EdiTradingPartner ediintTradingPartner) {
        this.ediintTradingPartner = ediintTradingPartner;
    }

//    public EdiMailbox getEdiintMailbox() {
//        return this.ediintMailbox;
//    }
//
//    protected void setEdiintMailbox(EdiMailbox ediintMailbox) {
//        this.ediintMailbox = ediintMailbox;
//    }

    public Set getEdiBatchSet() {
        return this.ediBatchSet;
    }

    protected void setEdiBatchSet(Set ediBatchSet) {
        this.ediBatchSet = ediBatchSet;
    }

    public Set getEdiintSegmentSet() {
        return this.ediintSegmentSet;
    }

    protected void setEdiintSegmentSet(Set ediintSegmentSet) {
        this.ediintSegmentSet = ediintSegmentSet;
    }

}
