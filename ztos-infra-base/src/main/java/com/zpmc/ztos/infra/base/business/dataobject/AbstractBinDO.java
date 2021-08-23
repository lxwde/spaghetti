package com.zpmc.ztos.infra.base.business.dataobject;


import com.alibaba.fastjson.support.geo.Geometry;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.BinType;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * 几何学位置信息
 * @author yejun
 */
@Data
public abstract class AbstractBinDO extends DatabaseEntity {

    private Long abnGkey;
    private String abnSubType;
    private String abnName;
    private String abnNameAlt;
    private String abnLongName;
    private Geometry abnPolygon;
    private Geometry abnCenterline;
    private Double abnGeodeticAnchorLatitude;
    private Double abnGeodeticAnchorLongitude;
    private Double abnGeodeticAnchorOrientation;
    private Long abnBinLevel;
    private Long abnZIndexMin;
    private Long abnZIndexMax;
    private Double abnVerticalMin;
    private Double abnVerticalMax;
    private LifeCycleStateEnum abnLifeCycleState;
    private Date abnCreated;
    private String abnCreator;
    private Date abnChanged;
    private String abnChanger;
    private AbstractBin abnParentBin;
    private BinType abnBinType;
    private Set abnAbstractBinSet;
    private Set abnBinAncestorSet;
    private Set abnBinNameTableSet;


    public Serializable getPrimaryKey() {
        return this.getAbnGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getAbnGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof AbstractBinDO)) {
            return false;
        }
        AbstractBinDO that = (AbstractBinDO)other;
        return ((Object)id).equals(that.getAbnGkey());
    }

    public int hashCode() {
        Long id = this.getAbnGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getAbnGkey() {
        return this.abnGkey;
    }

    protected void setAbnGkey(Long abnGkey) {
        this.abnGkey = abnGkey;
    }

    public String getAbnSubType() {
        return this.abnSubType;
    }

    protected void setAbnSubType(String abnSubType) {
        this.abnSubType = abnSubType;
    }

    public String getAbnName() {
        return this.abnName;
    }

    protected void setAbnName(String abnName) {
        this.abnName = abnName;
    }

    public String getAbnNameAlt() {
        return this.abnNameAlt;
    }

    protected void setAbnNameAlt(String abnNameAlt) {
        this.abnNameAlt = abnNameAlt;
    }

    public String getAbnLongName() {
        return this.abnLongName;
    }

    protected void setAbnLongName(String abnLongName) {
        this.abnLongName = abnLongName;
    }

    public Geometry getAbnPolygon() {
        return this.abnPolygon;
    }

    protected void setAbnPolygon(Geometry abnPolygon) {
        this.abnPolygon = abnPolygon;
    }

    public Geometry getAbnCenterline() {
        return this.abnCenterline;
    }

    protected void setAbnCenterline(Geometry abnCenterline) {
        this.abnCenterline = abnCenterline;
    }

    public Double getAbnGeodeticAnchorLatitude() {
        return this.abnGeodeticAnchorLatitude;
    }

    protected void setAbnGeodeticAnchorLatitude(Double abnGeodeticAnchorLatitude) {
        this.abnGeodeticAnchorLatitude = abnGeodeticAnchorLatitude;
    }

    public Double getAbnGeodeticAnchorLongitude() {
        return this.abnGeodeticAnchorLongitude;
    }

    protected void setAbnGeodeticAnchorLongitude(Double abnGeodeticAnchorLongitude) {
        this.abnGeodeticAnchorLongitude = abnGeodeticAnchorLongitude;
    }

    public Double getAbnGeodeticAnchorOrientation() {
        return this.abnGeodeticAnchorOrientation;
    }

    protected void setAbnGeodeticAnchorOrientation(Double abnGeodeticAnchorOrientation) {
        this.abnGeodeticAnchorOrientation = abnGeodeticAnchorOrientation;
    }

    protected Long getAbnBinLevel() {
        return this.abnBinLevel;
    }

    protected void setAbnBinLevel(Long abnBinLevel) {
        this.abnBinLevel = abnBinLevel;
    }

    public Long getAbnZIndexMin() {
        return this.abnZIndexMin;
    }

    protected void setAbnZIndexMin(Long abnZIndexMin) {
        this.abnZIndexMin = abnZIndexMin;
    }

    public Long getAbnZIndexMax() {
        return this.abnZIndexMax;
    }

    protected void setAbnZIndexMax(Long abnZIndexMax) {
        this.abnZIndexMax = abnZIndexMax;
    }

    public Double getAbnVerticalMin() {
        return this.abnVerticalMin;
    }

    protected void setAbnVerticalMin(Double abnVerticalMin) {
        this.abnVerticalMin = abnVerticalMin;
    }

    public Double getAbnVerticalMax() {
        return this.abnVerticalMax;
    }

    protected void setAbnVerticalMax(Double abnVerticalMax) {
        this.abnVerticalMax = abnVerticalMax;
    }

    public LifeCycleStateEnum getAbnLifeCycleState() {
        return this.abnLifeCycleState;
    }

    public void setAbnLifeCycleState(LifeCycleStateEnum abnLifeCycleState) {
        this.abnLifeCycleState = abnLifeCycleState;
    }

    public Date getAbnCreated() {
        return this.abnCreated;
    }

    protected void setAbnCreated(Date abnCreated) {
        this.abnCreated = abnCreated;
    }

    public String getAbnCreator() {
        return this.abnCreator;
    }

    protected void setAbnCreator(String abnCreator) {
        this.abnCreator = abnCreator;
    }

    public Date getAbnChanged() {
        return this.abnChanged;
    }

    protected void setAbnChanged(Date abnChanged) {
        this.abnChanged = abnChanged;
    }

    public String getAbnChanger() {
        return this.abnChanger;
    }

    protected void setAbnChanger(String abnChanger) {
        this.abnChanger = abnChanger;
    }

    public AbstractBin getAbnParentBin() {
        return this.abnParentBin;
    }

    protected void setAbnParentBin(AbstractBin abnParentBin) {
        this.abnParentBin = abnParentBin;
    }

    public BinType getAbnBinType() {
        return this.abnBinType;
    }

    protected void setAbnBinType(BinType abnBinType) {
        this.abnBinType = abnBinType;
    }

    public Set getAbnAbstractBinSet() {
        return this.abnAbstractBinSet;
    }

    protected void setAbnAbstractBinSet(Set abnAbstractBinSet) {
        this.abnAbstractBinSet = abnAbstractBinSet;
    }

    public Set getAbnBinAncestorSet() {
        return this.abnBinAncestorSet;
    }

    protected void setAbnBinAncestorSet(Set abnBinAncestorSet) {
        this.abnBinAncestorSet = abnBinAncestorSet;
    }

    public Set getAbnBinNameTableSet() {
        return this.abnBinNameTableSet;
    }

    protected void setAbnBinNameTableSet(Set abnBinNameTableSet) {
        this.abnBinNameTableSet = abnBinNameTableSet;
    }

}
