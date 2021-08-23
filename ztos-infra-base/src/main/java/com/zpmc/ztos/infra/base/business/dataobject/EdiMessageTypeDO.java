package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageAgencyEnum;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageStandardEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;

import java.io.Serializable;
import java.util.Date;

public class EdiMessageTypeDO {

    private Long edimsgGkey;
    private String edimsgIdVerRel;
    private String edimsgId;
    private EdiMessageClassEnum edimsgClass;
    private EdiMessageStandardEnum edimsgStandard;
    private String edimsgVersion;
    private String edimsgReleaseNbr;
    private String edimsgAssnCode;
    private EdiMessageAgencyEnum edimsgAgency;
    private String edimsgDescription;
    private LifeCycleStateEnum edimsgLifeCycleState;
    private Date edimsgCreated;
    private String edimsgCreator;
    private Date edimsgChanged;
    private String edimsgChanger;
    private EntitySet edimsgScope;

    public Serializable getPrimaryKey() {
        return this.getEdimsgGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdimsgGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiMessageTypeDO)) {
            return false;
        }
        EdiMessageTypeDO that = (EdiMessageTypeDO)other;
        return ((Object)id).equals(that.getEdimsgGkey());
    }

    public int hashCode() {
        Long id = this.getEdimsgGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEdimsgGkey() {
        return this.edimsgGkey;
    }

    protected void setEdimsgGkey(Long edimsgGkey) {
        this.edimsgGkey = edimsgGkey;
    }

    public String getEdimsgIdVerRel() {
        return this.edimsgIdVerRel;
    }

    protected void setEdimsgIdVerRel(String edimsgIdVerRel) {
        this.edimsgIdVerRel = edimsgIdVerRel;
    }

    public String getEdimsgId() {
        return this.edimsgId;
    }

    protected void setEdimsgId(String edimsgId) {
        this.edimsgId = edimsgId;
    }

    public EdiMessageClassEnum getEdimsgClass() {
        return this.edimsgClass;
    }

    protected void setEdimsgClass(EdiMessageClassEnum edimsgClass) {
        this.edimsgClass = edimsgClass;
    }

    public EdiMessageStandardEnum getEdimsgStandard() {
        return this.edimsgStandard;
    }

    protected void setEdimsgStandard(EdiMessageStandardEnum edimsgStandard) {
        this.edimsgStandard = edimsgStandard;
    }

    public String getEdimsgVersion() {
        return this.edimsgVersion;
    }

    protected void setEdimsgVersion(String edimsgVersion) {
        this.edimsgVersion = edimsgVersion;
    }

    public String getEdimsgReleaseNbr() {
        return this.edimsgReleaseNbr;
    }

    protected void setEdimsgReleaseNbr(String edimsgReleaseNbr) {
        this.edimsgReleaseNbr = edimsgReleaseNbr;
    }

    public String getEdimsgAssnCode() {
        return this.edimsgAssnCode;
    }

    protected void setEdimsgAssnCode(String edimsgAssnCode) {
        this.edimsgAssnCode = edimsgAssnCode;
    }

    public EdiMessageAgencyEnum getEdimsgAgency() {
        return this.edimsgAgency;
    }

    protected void setEdimsgAgency(EdiMessageAgencyEnum edimsgAgency) {
        this.edimsgAgency = edimsgAgency;
    }

    public String getEdimsgDescription() {
        return this.edimsgDescription;
    }

    protected void setEdimsgDescription(String edimsgDescription) {
        this.edimsgDescription = edimsgDescription;
    }

    public LifeCycleStateEnum getEdimsgLifeCycleState() {
        return this.edimsgLifeCycleState;
    }

    public void setEdimsgLifeCycleState(LifeCycleStateEnum edimsgLifeCycleState) {
        this.edimsgLifeCycleState = edimsgLifeCycleState;
    }

    public Date getEdimsgCreated() {
        return this.edimsgCreated;
    }

    protected void setEdimsgCreated(Date edimsgCreated) {
        this.edimsgCreated = edimsgCreated;
    }

    public String getEdimsgCreator() {
        return this.edimsgCreator;
    }

    protected void setEdimsgCreator(String edimsgCreator) {
        this.edimsgCreator = edimsgCreator;
    }

    public Date getEdimsgChanged() {
        return this.edimsgChanged;
    }

    protected void setEdimsgChanged(Date edimsgChanged) {
        this.edimsgChanged = edimsgChanged;
    }

    public String getEdimsgChanger() {
        return this.edimsgChanger;
    }

    protected void setEdimsgChanger(String edimsgChanger) {
        this.edimsgChanger = edimsgChanger;
    }

    public EntitySet getEdimsgScope() {
        return this.edimsgScope;
    }

    protected void setEdimsgScope(EntitySet edimsgScope) {
        this.edimsgScope = edimsgScope;
    }
}
