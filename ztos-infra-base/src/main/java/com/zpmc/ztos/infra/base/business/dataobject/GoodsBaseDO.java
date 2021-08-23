package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.inventory.Hazards;
import com.zpmc.ztos.infra.base.business.inventory.ReeferRqmnts;
import com.zpmc.ztos.infra.base.business.inventory.Unit;
import com.zpmc.ztos.infra.base.business.model.Commodity;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class GoodsBaseDO extends DatabaseEntity
implements Serializable {
    private Long gdsGkey;
    private String gdsObsoleteConsignee;
    private String gdsObsoleteShipper;
    private String gdsOrigin;
    private String gdsDestination;
    private Boolean gdsIsHazardous;
    private String gdsImdgTypes;
    private String gdsThreeMainHazardUNNumbers;
    private String gdsBlNbr;
    private Unit gdsUnit;
    private ScopedBizUnit gdsConsigneeBzu;
    private ScopedBizUnit gdsShipperBzu;
    private Commodity gdsCommodity;
    private Hazards gdsHazards;
    private Set gdsDeclaration;
    private ReeferRqmnts gdsReeferRqmnts;

    public Serializable getPrimaryKey() {
        return this.getGdsGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getGdsGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof GoodsBaseDO)) {
            return false;
        }
        GoodsBaseDO that = (GoodsBaseDO)other;
        return ((Object)id).equals(that.getGdsGkey());
    }

    public int hashCode() {
        Long id = this.getGdsGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getGdsGkey() {
        return this.gdsGkey;
    }

    protected void setGdsGkey(Long gdsGkey) {
        this.gdsGkey = gdsGkey;
    }

    public String getGdsObsoleteConsignee() {
        return this.gdsObsoleteConsignee;
    }

    protected void setGdsObsoleteConsignee(String gdsObsoleteConsignee) {
        this.gdsObsoleteConsignee = gdsObsoleteConsignee;
    }

    public String getGdsObsoleteShipper() {
        return this.gdsObsoleteShipper;
    }

    protected void setGdsObsoleteShipper(String gdsObsoleteShipper) {
        this.gdsObsoleteShipper = gdsObsoleteShipper;
    }

    public String getGdsOrigin() {
        return this.gdsOrigin;
    }

    protected void setGdsOrigin(String gdsOrigin) {
        this.gdsOrigin = gdsOrigin;
    }

    public String getGdsDestination() {
        return this.gdsDestination;
    }

    protected void setGdsDestination(String gdsDestination) {
        this.gdsDestination = gdsDestination;
    }

    public Boolean getGdsIsHazardous() {
        return this.gdsIsHazardous;
    }

    protected void setGdsIsHazardous(Boolean gdsIsHazardous) {
        this.gdsIsHazardous = gdsIsHazardous;
    }

    public String getGdsImdgTypes() {
        return this.gdsImdgTypes;
    }

    protected void setGdsImdgTypes(String gdsImdgTypes) {
        this.gdsImdgTypes = gdsImdgTypes;
    }

    public String getGdsThreeMainHazardUNNumbers() {
        return this.gdsThreeMainHazardUNNumbers;
    }

    protected void setGdsThreeMainHazardUNNumbers(String gdsThreeMainHazardUNNumbers) {
        this.gdsThreeMainHazardUNNumbers = gdsThreeMainHazardUNNumbers;
    }

    public String getGdsBlNbr() {
        return this.gdsBlNbr;
    }

    protected void setGdsBlNbr(String gdsBlNbr) {
        this.gdsBlNbr = gdsBlNbr;
    }

    public Unit getGdsUnit() {
        return this.gdsUnit;
    }

    public void setGdsUnit(Unit gdsUnit) {
        this.gdsUnit = gdsUnit;
    }

    public ScopedBizUnit getGdsConsigneeBzu() {
        return this.gdsConsigneeBzu;
    }

    protected void setGdsConsigneeBzu(ScopedBizUnit gdsConsigneeBzu) {
        this.gdsConsigneeBzu = gdsConsigneeBzu;
    }

    public ScopedBizUnit getGdsShipperBzu() {
        return this.gdsShipperBzu;
    }

    protected void setGdsShipperBzu(ScopedBizUnit gdsShipperBzu) {
        this.gdsShipperBzu = gdsShipperBzu;
    }

    public Commodity getGdsCommodity() {
        return this.gdsCommodity;
    }

    public void setGdsCommodity(Commodity gdsCommodity) {
        this.gdsCommodity = gdsCommodity;
    }

    public Hazards getGdsHazards() {
        return this.gdsHazards;
    }

    protected void setGdsHazards(Hazards gdsHazards) {
        this.gdsHazards = gdsHazards;
    }

    public Set getGdsDeclaration() {
        return this.gdsDeclaration;
    }

    public void setGdsDeclaration(Set gdsDeclaration) {
        this.gdsDeclaration = gdsDeclaration;
    }

    public ReeferRqmnts getGdsReeferRqmnts() {
        return this.gdsReeferRqmnts;
    }

    protected void setGdsReeferRqmnts(ReeferRqmnts gdsReeferRqmnts) {
        this.gdsReeferRqmnts = gdsReeferRqmnts;
    }


}
