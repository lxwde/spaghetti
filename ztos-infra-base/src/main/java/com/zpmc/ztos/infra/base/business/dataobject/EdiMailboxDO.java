package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiTradingPartner;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiCommunicationTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EdiMailboxDO extends DatabaseEntity implements Serializable {
    private Long edimlbxGkey;
    private String edimlbxName;
    private String edimlbxSenderQualifier;
    private String edimlbxSenderId;
    private String edimlbxReceiverQualifier;
    private String edimlbxReceiverId;
    private Long edimlbxLastInterchange;
    private Long edimlbxLastMessageNbr;
    private EdiMessageDirectionEnum edimlbxDirection;
    private String edimlbxDirectory;
    private EdiCommunicationTypeEnum edimlbxCommType;
    private String edimlbxCommAddr;
    private String edimlbxCommUserId;
    private String edimlbxCommPasswd;
    private String edimlbxCommFolder;
    private Date edimlbxCreated;
    private String edimlbxCreator;
    private Date edimlbxChanged;
    private String edimlbxChanger;
    private LifeCycleStateEnum edimlbxLifeCycleState;
    private Complex edimlbxComplex;
    private EdiTradingPartner edimlbxTradingPartner;
    private Set ediQueuedInterchangeSet;

    public Serializable getPrimaryKey() {
        return this.getEdimlbxGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdimlbxGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiMailboxDO)) {
            return false;
        }
        EdiMailboxDO that = (EdiMailboxDO)other;
        return ((Object)id).equals(that.getEdimlbxGkey());
    }

    public int hashCode() {
        Long id = this.getEdimlbxGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdimlbxGkey() {
        return this.edimlbxGkey;
    }

    protected void setEdimlbxGkey(Long edimlbxGkey) {
        this.edimlbxGkey = edimlbxGkey;
    }

    public String getEdimlbxName() {
        return this.edimlbxName;
    }

    protected void setEdimlbxName(String edimlbxName) {
        this.edimlbxName = edimlbxName;
    }

    public String getEdimlbxSenderQualifier() {
        return this.edimlbxSenderQualifier;
    }

    protected void setEdimlbxSenderQualifier(String edimlbxSenderQualifier) {
        this.edimlbxSenderQualifier = edimlbxSenderQualifier;
    }

    public String getEdimlbxSenderId() {
        return this.edimlbxSenderId;
    }

    protected void setEdimlbxSenderId(String edimlbxSenderId) {
        this.edimlbxSenderId = edimlbxSenderId;
    }

    public String getEdimlbxReceiverQualifier() {
        return this.edimlbxReceiverQualifier;
    }

    protected void setEdimlbxReceiverQualifier(String edimlbxReceiverQualifier) {
        this.edimlbxReceiverQualifier = edimlbxReceiverQualifier;
    }

    public String getEdimlbxReceiverId() {
        return this.edimlbxReceiverId;
    }

    protected void setEdimlbxReceiverId(String edimlbxReceiverId) {
        this.edimlbxReceiverId = edimlbxReceiverId;
    }

    public Long getEdimlbxLastInterchange() {
        return this.edimlbxLastInterchange;
    }

    protected void setEdimlbxLastInterchange(Long edimlbxLastInterchange) {
        this.edimlbxLastInterchange = edimlbxLastInterchange;
    }

    public Long getEdimlbxLastMessageNbr() {
        return this.edimlbxLastMessageNbr;
    }

    protected void setEdimlbxLastMessageNbr(Long edimlbxLastMessageNbr) {
        this.edimlbxLastMessageNbr = edimlbxLastMessageNbr;
    }

    public EdiMessageDirectionEnum getEdimlbxDirection() {
        return this.edimlbxDirection;
    }

    protected void setEdimlbxDirection(EdiMessageDirectionEnum edimlbxDirection) {
        this.edimlbxDirection = edimlbxDirection;
    }

    public String getEdimlbxDirectory() {
        return this.edimlbxDirectory;
    }

    protected void setEdimlbxDirectory(String edimlbxDirectory) {
        this.edimlbxDirectory = edimlbxDirectory;
    }

    public EdiCommunicationTypeEnum getEdimlbxCommType() {
        return this.edimlbxCommType;
    }

    protected void setEdimlbxCommType(EdiCommunicationTypeEnum edimlbxCommType) {
        this.edimlbxCommType = edimlbxCommType;
    }

    public String getEdimlbxCommAddr() {
        return this.edimlbxCommAddr;
    }

    protected void setEdimlbxCommAddr(String edimlbxCommAddr) {
        this.edimlbxCommAddr = edimlbxCommAddr;
    }

    public String getEdimlbxCommUserId() {
        return this.edimlbxCommUserId;
    }

    protected void setEdimlbxCommUserId(String edimlbxCommUserId) {
        this.edimlbxCommUserId = edimlbxCommUserId;
    }

    public String getEdimlbxCommPasswd() {
        return this.edimlbxCommPasswd;
    }

    protected void setEdimlbxCommPasswd(String edimlbxCommPasswd) {
        this.edimlbxCommPasswd = edimlbxCommPasswd;
    }

    public String getEdimlbxCommFolder() {
        return this.edimlbxCommFolder;
    }

    protected void setEdimlbxCommFolder(String edimlbxCommFolder) {
        this.edimlbxCommFolder = edimlbxCommFolder;
    }

    public Date getEdimlbxCreated() {
        return this.edimlbxCreated;
    }

    protected void setEdimlbxCreated(Date edimlbxCreated) {
        this.edimlbxCreated = edimlbxCreated;
    }

    public String getEdimlbxCreator() {
        return this.edimlbxCreator;
    }

    protected void setEdimlbxCreator(String edimlbxCreator) {
        this.edimlbxCreator = edimlbxCreator;
    }

    public Date getEdimlbxChanged() {
        return this.edimlbxChanged;
    }

    protected void setEdimlbxChanged(Date edimlbxChanged) {
        this.edimlbxChanged = edimlbxChanged;
    }

    public String getEdimlbxChanger() {
        return this.edimlbxChanger;
    }

    protected void setEdimlbxChanger(String edimlbxChanger) {
        this.edimlbxChanger = edimlbxChanger;
    }

    public LifeCycleStateEnum getEdimlbxLifeCycleState() {
        return this.edimlbxLifeCycleState;
    }

    public void setEdimlbxLifeCycleState(LifeCycleStateEnum edimlbxLifeCycleState) {
        this.edimlbxLifeCycleState = edimlbxLifeCycleState;
    }

    public Complex getEdimlbxComplex() {
        return this.edimlbxComplex;
    }

    protected void setEdimlbxComplex(Complex edimlbxComplex) {
        this.edimlbxComplex = edimlbxComplex;
    }

    public EdiTradingPartner getEdimlbxTradingPartner() {
        return this.edimlbxTradingPartner;
    }

    protected void setEdimlbxTradingPartner(EdiTradingPartner edimlbxTradingPartner) {
        this.edimlbxTradingPartner = edimlbxTradingPartner;
    }

    public Set getEdiQueuedInterchangeSet() {
        return this.ediQueuedInterchangeSet;
    }

    protected void setEdiQueuedInterchangeSet(Set ediQueuedInterchangeSet) {
        this.ediQueuedInterchangeSet = ediQueuedInterchangeSet;
    }
}
