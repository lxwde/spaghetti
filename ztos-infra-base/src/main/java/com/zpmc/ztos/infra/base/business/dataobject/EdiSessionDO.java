package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiMessageMap;
import com.zpmc.ztos.infra.base.business.edi.EdiTradingPartner;
import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.Extension;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EdiSessionDO extends DatabaseEntity implements Serializable {
    private Long edisessGkey;
    private String edisessName;
    private String edisessDesc;
    private EdiMessageClassEnum edisessMessageClass;
    private EdiMessageDirectionEnum edisessDirection;
    private Date edisessLastRunTimestamp;
    private EdiStatusEnum edisessLastRunStatus;
    private String edisessFileExt;
    private String edisessDelimeter;
    private Long edisessTranCntlNbr;
    private Long edisessMsgSeqNbr;
    private Boolean edisessInterNumChkFlag;
    private Date edisessCreated;
    private String edisessCreator;
    private Date edisessChanged;
    private String edisessChanger;
    private Boolean edisessIsAutoPosted;
    private LifeCycleStateEnum edisessLifeCycleState;
    private Complex edisessComplex;
    private Extension edisessLoadExtension;
    private Extension edisessPostExtension;
    private Extension edisessExtractExtension;
    private SavedPredicate edisessPredicate;
    private EdiTradingPartner edisessTradingPartner;
    private EdiMessageMap edisessMsgMap;
    private Set edisessSettings;

    public Serializable getPrimaryKey() {
        return this.getEdisessGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdisessGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiSessionDO)) {
            return false;
        }
        EdiSessionDO that = (EdiSessionDO)other;
        return ((Object)id).equals(that.getEdisessGkey());
    }

    public int hashCode() {
        Long id = this.getEdisessGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdisessGkey() {
        return this.edisessGkey;
    }

    protected void setEdisessGkey(Long edisessGkey) {
        this.edisessGkey = edisessGkey;
    }

    public String getEdisessName() {
        return this.edisessName;
    }

    protected void setEdisessName(String edisessName) {
        this.edisessName = edisessName;
    }

    public String getEdisessDesc() {
        return this.edisessDesc;
    }

    protected void setEdisessDesc(String edisessDesc) {
        this.edisessDesc = edisessDesc;
    }

    public EdiMessageClassEnum getEdisessMessageClass() {
        return this.edisessMessageClass;
    }

    protected void setEdisessMessageClass(EdiMessageClassEnum edisessMessageClass) {
        this.edisessMessageClass = edisessMessageClass;
    }

    public EdiMessageDirectionEnum getEdisessDirection() {
        return this.edisessDirection;
    }

    protected void setEdisessDirection(EdiMessageDirectionEnum edisessDirection) {
        this.edisessDirection = edisessDirection;
    }

    public Date getEdisessLastRunTimestamp() {
        return this.edisessLastRunTimestamp;
    }

    protected void setEdisessLastRunTimestamp(Date edisessLastRunTimestamp) {
        this.edisessLastRunTimestamp = edisessLastRunTimestamp;
    }

    public EdiStatusEnum getEdisessLastRunStatus() {
        return this.edisessLastRunStatus;
    }

    protected void setEdisessLastRunStatus(EdiStatusEnum edisessLastRunStatus) {
        this.edisessLastRunStatus = edisessLastRunStatus;
    }

    public String getEdisessFileExt() {
        return this.edisessFileExt;
    }

    protected void setEdisessFileExt(String edisessFileExt) {
        this.edisessFileExt = edisessFileExt;
    }

    public String getEdisessDelimeter() {
        return this.edisessDelimeter;
    }

    protected void setEdisessDelimeter(String edisessDelimeter) {
        this.edisessDelimeter = edisessDelimeter;
    }

    public Long getEdisessTranCntlNbr() {
        return this.edisessTranCntlNbr;
    }

    protected void setEdisessTranCntlNbr(Long edisessTranCntlNbr) {
        this.edisessTranCntlNbr = edisessTranCntlNbr;
    }

    public Long getEdisessMsgSeqNbr() {
        return this.edisessMsgSeqNbr;
    }

    protected void setEdisessMsgSeqNbr(Long edisessMsgSeqNbr) {
        this.edisessMsgSeqNbr = edisessMsgSeqNbr;
    }

    public Boolean getEdisessInterNumChkFlag() {
        return this.edisessInterNumChkFlag;
    }

    protected void setEdisessInterNumChkFlag(Boolean edisessInterNumChkFlag) {
        this.edisessInterNumChkFlag = edisessInterNumChkFlag;
    }

    public Date getEdisessCreated() {
        return this.edisessCreated;
    }

    protected void setEdisessCreated(Date edisessCreated) {
        this.edisessCreated = edisessCreated;
    }

    public String getEdisessCreator() {
        return this.edisessCreator;
    }

    protected void setEdisessCreator(String edisessCreator) {
        this.edisessCreator = edisessCreator;
    }

    public Date getEdisessChanged() {
        return this.edisessChanged;
    }

    protected void setEdisessChanged(Date edisessChanged) {
        this.edisessChanged = edisessChanged;
    }

    public String getEdisessChanger() {
        return this.edisessChanger;
    }

    protected void setEdisessChanger(String edisessChanger) {
        this.edisessChanger = edisessChanger;
    }

    public Boolean getEdisessIsAutoPosted() {
        return this.edisessIsAutoPosted;
    }

    protected void setEdisessIsAutoPosted(Boolean edisessIsAutoPosted) {
        this.edisessIsAutoPosted = edisessIsAutoPosted;
    }

    public LifeCycleStateEnum getEdisessLifeCycleState() {
        return this.edisessLifeCycleState;
    }

    public void setEdisessLifeCycleState(LifeCycleStateEnum edisessLifeCycleState) {
        this.edisessLifeCycleState = edisessLifeCycleState;
    }

    public Complex getEdisessComplex() {
        return this.edisessComplex;
    }

    protected void setEdisessComplex(Complex edisessComplex) {
        this.edisessComplex = edisessComplex;
    }

    public Extension getEdisessLoadExtension() {
        return this.edisessLoadExtension;
    }

    protected void setEdisessLoadExtension(Extension edisessLoadExtension) {
        this.edisessLoadExtension = edisessLoadExtension;
    }

    public Extension getEdisessPostExtension() {
        return this.edisessPostExtension;
    }

    protected void setEdisessPostExtension(Extension edisessPostExtension) {
        this.edisessPostExtension = edisessPostExtension;
    }

    public Extension getEdisessExtractExtension() {
        return this.edisessExtractExtension;
    }

    protected void setEdisessExtractExtension(Extension edisessExtractExtension) {
        this.edisessExtractExtension = edisessExtractExtension;
    }

    public SavedPredicate getEdisessPredicate() {
        return this.edisessPredicate;
    }

    protected void setEdisessPredicate(SavedPredicate edisessPredicate) {
        this.edisessPredicate = edisessPredicate;
    }

    public EdiTradingPartner getEdisessTradingPartner() {
        return this.edisessTradingPartner;
    }

    protected void setEdisessTradingPartner(EdiTradingPartner edisessTradingPartner) {
        this.edisessTradingPartner = edisessTradingPartner;
    }

    public EdiMessageMap getEdisessMsgMap() {
        return this.edisessMsgMap;
    }

    protected void setEdisessMsgMap(EdiMessageMap edisessMsgMap) {
        this.edisessMsgMap = edisessMsgMap;
    }

    public Set getEdisessSettings() {
        return this.edisessSettings;
    }

    protected void setEdisessSettings(Set edisessSettings) {
        this.edisessSettings = edisessSettings;
    }
}
