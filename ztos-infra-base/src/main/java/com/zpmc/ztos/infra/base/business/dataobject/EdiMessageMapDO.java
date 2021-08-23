package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.edi.EdiMessageType;
import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;

import java.io.Serializable;
import java.util.Date;

public class EdiMessageMapDO {
    private Long edimapGkey;
    private String edimapId;
    private EdiMessageDirectionEnum edimapDirection;
    private String edimapDic;
    private Boolean edimapIsBuiltIn;
    private String edimapFile;
    private String edimapDesc;
    private Date edimapCreated;
    private String edimapCreator;
    private Date edimapChanged;
    private String edimapChanger;
    private LifeCycleStateEnum edimapLifeCycleState;
    private EntitySet edimapScope;
    private EdiMessageType edimapEdiMsgType;
 //   private transient FieldHandler _fieldHandler;

    public Serializable getPrimaryKey() {
        return this.getEdimapGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEdimapGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EdiMessageMapDO)) {
            return false;
        }
        EdiMessageMapDO that = (EdiMessageMapDO)other;
        return ((Object)id).equals(that.getEdimapGkey());
    }

    public int hashCode() {
        Long id = this.getEdimapGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

//    public FieldHandler getFieldHandler() {
//        return this._fieldHandler;
//    }
//
//    public void setFieldHandler(FieldHandler inFieldHandler) {
//        this._fieldHandler = inFieldHandler;
//    }

    public Long getEdimapGkey() {
        return this.edimapGkey;
    }

    protected void setEdimapGkey(Long edimapGkey) {
        this.edimapGkey = edimapGkey;
    }

    public String getEdimapId() {
        return this.edimapId;
    }

    protected void setEdimapId(String edimapId) {
        this.edimapId = edimapId;
    }

    public EdiMessageDirectionEnum getEdimapDirection() {
        return this.edimapDirection;
    }

    protected void setEdimapDirection(EdiMessageDirectionEnum edimapDirection) {
        this.edimapDirection = edimapDirection;
    }

    public String getEdimapDic() {
//        if (this.getFieldHandler() == null) {
//            return this.edimapDic;
//        }
//        return (String)this.getFieldHandler().readObject((Object)this, "edimapDic", (Object)this.edimapDic);
        return null;
    }

    protected void setEdimapDic(String edimapDic) {
//        this.edimapDic = this.getFieldHandler() == null ? edimapDic : (String)this.getFieldHandler().writeObject((Object)this, "edimapDic", (Object)this.edimapDic, (Object)edimapDic);
    }

    public Boolean getEdimapIsBuiltIn() {
        return this.edimapIsBuiltIn;
    }

    protected void setEdimapIsBuiltIn(Boolean edimapIsBuiltIn) {
        this.edimapIsBuiltIn = edimapIsBuiltIn;
    }

    public String getEdimapFile() {
//        if (this.getFieldHandler() == null) {
//            return this.edimapFile;
//        }
//        return (String)this.getFieldHandler().readObject((Object)this, "edimapFile", (Object)this.edimapFile);
        return null;
    }

    protected void setEdimapFile(String edimapFile) {
   //     this.edimapFile = this.getFieldHandler() == null ? edimapFile : (String)this.getFieldHandler().writeObject((Object)this, "edimapFile", (Object)this.edimapFile, (Object)edimapFile);
    }

    public String getEdimapDesc() {
        return this.edimapDesc;
    }

    protected void setEdimapDesc(String edimapDesc) {
        this.edimapDesc = edimapDesc;
    }

    public Date getEdimapCreated() {
        return this.edimapCreated;
    }

    protected void setEdimapCreated(Date edimapCreated) {
        this.edimapCreated = edimapCreated;
    }

    public String getEdimapCreator() {
        return this.edimapCreator;
    }

    protected void setEdimapCreator(String edimapCreator) {
        this.edimapCreator = edimapCreator;
    }

    public Date getEdimapChanged() {
        return this.edimapChanged;
    }

    protected void setEdimapChanged(Date edimapChanged) {
        this.edimapChanged = edimapChanged;
    }

    public String getEdimapChanger() {
        return this.edimapChanger;
    }

    protected void setEdimapChanger(String edimapChanger) {
        this.edimapChanger = edimapChanger;
    }

    public LifeCycleStateEnum getEdimapLifeCycleState() {
        return this.edimapLifeCycleState;
    }

    public void setEdimapLifeCycleState(LifeCycleStateEnum edimapLifeCycleState) {
        this.edimapLifeCycleState = edimapLifeCycleState;
    }

    public EntitySet getEdimapScope() {
        return this.edimapScope;
    }

    protected void setEdimapScope(EntitySet edimapScope) {
        this.edimapScope = edimapScope;
    }

    public EdiMessageType getEdimapEdiMsgType() {
        return this.edimapEdiMsgType;
    }

    protected void setEdimapEdiMsgType(EdiMessageType edimapEdiMsgType) {
        this.edimapEdiMsgType = edimapEdiMsgType;
    }
}
