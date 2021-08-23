package com.zpmc.ztos.infra.base.business.dataobject;

import com.alibaba.fastjson.support.geo.Geometry;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 地点位置
 * @author yejun
 */
@Data
public abstract class LocPositionDO extends DatabaseEntity
implements Serializable {

    private LocTypeEnum posLocType;
    private String posLocId;
    private Long posLocGkey;
    private String posSlot;
    private String posOrientation;
    private String posName;
    private Long posTier;
    private Geometry posAnchor;
    private Double posOrientationDegrees;
    private String posOperationalPosId;
    private String posSlotOnCarriage;
    private AbstractBin posBin;

    public LocTypeEnum getPosLocType() {
        return this.posLocType;
    }

    public void setPosLocType(LocTypeEnum posLocType) {
        this.posLocType = posLocType;
    }

    public String getPosLocId() {
        return this.posLocId;
    }

    public void setPosLocId(String posLocId) {
        this.posLocId = posLocId;
    }

    public Long getPosLocGkey() {
        return this.posLocGkey;
    }

    public void setPosLocGkey(Long posLocGkey) {
        this.posLocGkey = posLocGkey;
    }

    public String getPosSlot() {
        return this.posSlot;
    }

    public void setPosSlot(String posSlot) {
        this.posSlot = posSlot;
    }

    public String getPosOrientation() {
        return this.posOrientation;
    }

    public void setPosOrientation(String posOrientation) {
        this.posOrientation = posOrientation;
    }

    public String getPosName() {
        return this.posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Long getPosTier() {
        return this.posTier;
    }

    public void setPosTier(Long posTier) {
        this.posTier = posTier;
    }

    public Geometry getPosAnchor() {
        return this.posAnchor;
    }

    public void setPosAnchor(Geometry posAnchor) {
        this.posAnchor = posAnchor;
    }

    public Double getPosOrientationDegrees() {
        return this.posOrientationDegrees;
    }

    public void setPosOrientationDegrees(Double posOrientationDegrees) {
        this.posOrientationDegrees = posOrientationDegrees;
    }

    public String getPosOperationalPosId() {
        return this.posOperationalPosId;
    }

    public void setPosOperationalPosId(String posOperationalPosId) {
        this.posOperationalPosId = posOperationalPosId;
    }

    public String getPosSlotOnCarriage() {
        return this.posSlotOnCarriage;
    }

    public void setPosSlotOnCarriage(String posSlotOnCarriage) {
        this.posSlotOnCarriage = posSlotOnCarriage;
    }

    public AbstractBin getPosBin() {
        return this.posBin;
    }

    public void setPosBin(AbstractBin posBin) {
        this.posBin = posBin;
    }
}
