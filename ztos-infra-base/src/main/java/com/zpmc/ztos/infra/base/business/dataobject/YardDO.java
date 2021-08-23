package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.XpsImportStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import lombok.Data;
import org.hibernate.annotations.Entity;

import java.io.Serializable;

/**
 *
 * 堆场
 * @author yejun
 */
@Entity
public class YardDO extends DatabaseEntity implements Serializable {

    private Long yrdGkey;
    private String yrdId;
    private String yrdName;
    private LifeCycleStateEnum yrdLifeCycleState;
    private XpsImportStateEnum yrdXpsImportState;
    private byte[] yrdCompiledYard;
    private String yrdSparcsSettings;
    private byte[] yrdBerthTextFile;
    private Facility yrdFacility;
    private AbstractBin yrdBinModel;
  //  private transient FieldHandler _fieldHandler;

    public Serializable getPrimaryKey() {
        return this.getYrdGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getYrdGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof YardDO)) {
            return false;
        }
        YardDO that = (YardDO)other;
        return ((Object)id).equals(that.getYrdGkey());
    }

    public int hashCode() {
        Long id = this.getYrdGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

//    public FieldHandler getFieldHandler() {
//        return this._fieldHandler;
//    }
//
//    public void setFieldHandler(FieldHandler inFieldHandler) {
//        this._fieldHandler = inFieldHandler;
//    }

    public Long getYrdGkey() {
        return this.yrdGkey;
    }

    protected void setYrdGkey(Long yrdGkey) {
        this.yrdGkey = yrdGkey;
    }

    public String getYrdId() {
        return this.yrdId;
    }

    protected void setYrdId(String yrdId) {
        this.yrdId = yrdId;
    }

    public String getYrdName() {
        return this.yrdName;
    }

    protected void setYrdName(String yrdName) {
        this.yrdName = yrdName;
    }

    public LifeCycleStateEnum getYrdLifeCycleState() {
        return this.yrdLifeCycleState;
    }

    public void setYrdLifeCycleState(LifeCycleStateEnum yrdLifeCycleState) {
        this.yrdLifeCycleState = yrdLifeCycleState;
    }

    public XpsImportStateEnum getYrdXpsImportState() {
        return this.yrdXpsImportState;
    }

    public void setYrdXpsImportState(XpsImportStateEnum yrdXpsImportState) {
        this.yrdXpsImportState = yrdXpsImportState;
    }

    public byte[] getYrdCompiledYard() {
//        if (this.getFieldHandler() == null) {
//            return this.yrdCompiledYard;
//        }
//        return (byte[])this.getFieldHandler().readObject((Object)this, "yrdCompiledYard", (Object)this.yrdCompiledYard);
        return null;
    }

    protected void setYrdCompiledYard(byte[] yrdCompiledYard) {
//        this.yrdCompiledYard = this.getFieldHandler() == null ? yrdCompiledYard : (byte[])this.getFieldHandler().writeObject((Object)this, "yrdCompiledYard", (Object)this.yrdCompiledYard, (Object)yrdCompiledYard);
    }

    public String getYrdSparcsSettings() {
//        if (this.getFieldHandler() == null) {
//            return this.yrdSparcsSettings;
//        }
//        return (String)this.getFieldHandler().readObject((Object)this, "yrdSparcsSettings", (Object)this.yrdSparcsSettings);
        return null;
    }

    protected void setYrdSparcsSettings(String yrdSparcsSettings) {
 //       this.yrdSparcsSettings = this.getFieldHandler() == null ? yrdSparcsSettings : (String)this.getFieldHandler().writeObject((Object)this, "yrdSparcsSettings", (Object)this.yrdSparcsSettings, (Object)yrdSparcsSettings);
    }

    public byte[] getYrdBerthTextFile() {
//        if (this.getFieldHandler() == null) {
//            return this.yrdBerthTextFile;
//        }
//        return (byte[])this.getFieldHandler().readObject((Object)this, "yrdBerthTextFile", (Object)this.yrdBerthTextFile);
        return null;
    }

    protected void setYrdBerthTextFile(byte[] yrdBerthTextFile) {
//        this.yrdBerthTextFile = this.getFieldHandler() == null ? yrdBerthTextFile : (byte[])this.getFieldHandler().writeObject((Object)this, "yrdBerthTextFile", (Object)this.yrdBerthTextFile, (Object)yrdBerthTextFile);
    }

    public Facility getYrdFacility() {
        return this.yrdFacility;
    }

    protected void setYrdFacility(Facility yrdFacility) {
        this.yrdFacility = yrdFacility;
    }

    public AbstractBin getYrdBinModel() {
        return this.yrdBinModel;
    }

    protected void setYrdBinModel(AbstractBin yrdBinModel) {
        this.yrdBinModel = yrdBinModel;
    }

}
