package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.inventory.HazardPackingGroupEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.HazardsNumberTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.inventory.HazardFireCode;
import com.zpmc.ztos.infra.base.business.inventory.Hazards;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class HazardItemDO extends DatabaseEntity implements Serializable {
    private Long hzrdiGkey;
    private ImdgClassEnum hzrdiImdgClass;
    private String hzrdiUNnum;
    private HazardsNumberTypeEnum hzrdiNbrType;
    private Boolean hzrdiLtdQty;
    private String hzrdiPackageType;
    private String hzrdiInhalationZone;
    private ImdgClassEnum hzrdiImdgCode;
    private String hzrdiExplosiveClass;
    private String hzrdiPageNumber;
    private Double hzrdiFlashPoint;
    private String hzrdiTechName;
    private String hzrdiProperName;
    private String hzrdiEMSNumber;
    private String hzrdiERGNumber;
    private String hzrdiMFAG;
    private HazardPackingGroupEnum hzrdiPackingGroup;
    private String hzrdiHazIdUpper;
    private String hzrdiSubstanceLower;
    private Double hzrdiWeight;
    private String hzrdiPlannerRef;
    private Long hzrdiQuantity;
    private String hzrdiMoveMethod;
    private ImdgClassEnum hzrdiSecondaryIMO1;
    private ImdgClassEnum hzrdiSecondaryIMO2;
    private String hzrdiDeckRestrictions;
    private Boolean hzrdiMarinePollutants;
    private String hzrdiDcLgRef;
    private String hzrdiEmergencyTelephone;
    private String hzrdiNotes;
    private Long hzrdiSeq;
    private Hazards hzrdiHazards;
    private HazardFireCode hzrdiFireCode;
    private Set hzrdiPlacardSet;

    public Serializable getPrimaryKey() {
        return this.getHzrdiGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getHzrdiGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HazardItemDO)) {
            return false;
        }
        HazardItemDO that = (HazardItemDO)other;
        return ((Object)id).equals(that.getHzrdiGkey());
    }

    public int hashCode() {
        Long id = this.getHzrdiGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getHzrdiGkey() {
        return this.hzrdiGkey;
    }

    protected void setHzrdiGkey(Long hzrdiGkey) {
        this.hzrdiGkey = hzrdiGkey;
    }

    public ImdgClassEnum getHzrdiImdgClass() {
        return this.hzrdiImdgClass;
    }

    protected void setHzrdiImdgClass(ImdgClassEnum hzrdiImdgClass) {
        this.hzrdiImdgClass = hzrdiImdgClass;
    }

    public String getHzrdiUNnum() {
        return this.hzrdiUNnum;
    }

    protected void setHzrdiUNnum(String hzrdiUNnum) {
        this.hzrdiUNnum = hzrdiUNnum;
    }

    public HazardsNumberTypeEnum getHzrdiNbrType() {
        return this.hzrdiNbrType;
    }

    public void setHzrdiNbrType(HazardsNumberTypeEnum hzrdiNbrType) {
        this.hzrdiNbrType = hzrdiNbrType;
    }

    public Boolean getHzrdiLtdQty() {
        return this.hzrdiLtdQty;
    }

    public void setHzrdiLtdQty(Boolean hzrdiLtdQty) {
        this.hzrdiLtdQty = hzrdiLtdQty;
    }

    public String getHzrdiPackageType() {
        return this.hzrdiPackageType;
    }

    public void setHzrdiPackageType(String hzrdiPackageType) {
        this.hzrdiPackageType = hzrdiPackageType;
    }

    public String getHzrdiInhalationZone() {
        return this.hzrdiInhalationZone;
    }

    public void setHzrdiInhalationZone(String hzrdiInhalationZone) {
        this.hzrdiInhalationZone = hzrdiInhalationZone;
    }

    public ImdgClassEnum getHzrdiImdgCode() {
        return this.hzrdiImdgCode;
    }

    protected void setHzrdiImdgCode(ImdgClassEnum hzrdiImdgCode) {
        this.hzrdiImdgCode = hzrdiImdgCode;
    }

    public String getHzrdiExplosiveClass() {
        return this.hzrdiExplosiveClass;
    }

    public void setHzrdiExplosiveClass(String hzrdiExplosiveClass) {
        this.hzrdiExplosiveClass = hzrdiExplosiveClass;
    }

    public String getHzrdiPageNumber() {
        return this.hzrdiPageNumber;
    }

    public void setHzrdiPageNumber(String hzrdiPageNumber) {
        this.hzrdiPageNumber = hzrdiPageNumber;
    }

    public Double getHzrdiFlashPoint() {
        return this.hzrdiFlashPoint;
    }

    public void setHzrdiFlashPoint(Double hzrdiFlashPoint) {
        this.hzrdiFlashPoint = hzrdiFlashPoint;
    }

    public String getHzrdiTechName() {
        return this.hzrdiTechName;
    }

    public void setHzrdiTechName(String hzrdiTechName) {
        this.hzrdiTechName = hzrdiTechName;
    }

    public String getHzrdiProperName() {
        return this.hzrdiProperName;
    }

    public void setHzrdiProperName(String hzrdiProperName) {
        this.hzrdiProperName = hzrdiProperName;
    }

    public String getHzrdiEMSNumber() {
        return this.hzrdiEMSNumber;
    }

    public void setHzrdiEMSNumber(String hzrdiEMSNumber) {
        this.hzrdiEMSNumber = hzrdiEMSNumber;
    }

    public String getHzrdiERGNumber() {
        return this.hzrdiERGNumber;
    }

    public void setHzrdiERGNumber(String hzrdiERGNumber) {
        this.hzrdiERGNumber = hzrdiERGNumber;
    }

    public String getHzrdiMFAG() {
        return this.hzrdiMFAG;
    }

    public void setHzrdiMFAG(String hzrdiMFAG) {
        this.hzrdiMFAG = hzrdiMFAG;
    }

    public HazardPackingGroupEnum getHzrdiPackingGroup() {
        return this.hzrdiPackingGroup;
    }

    public void setHzrdiPackingGroup(HazardPackingGroupEnum hzrdiPackingGroup) {
        this.hzrdiPackingGroup = hzrdiPackingGroup;
    }

    public String getHzrdiHazIdUpper() {
        return this.hzrdiHazIdUpper;
    }

    public void setHzrdiHazIdUpper(String hzrdiHazIdUpper) {
        this.hzrdiHazIdUpper = hzrdiHazIdUpper;
    }

    public String getHzrdiSubstanceLower() {
        return this.hzrdiSubstanceLower;
    }

    public void setHzrdiSubstanceLower(String hzrdiSubstanceLower) {
        this.hzrdiSubstanceLower = hzrdiSubstanceLower;
    }

    public Double getHzrdiWeight() {
        return this.hzrdiWeight;
    }

    public void setHzrdiWeight(Double hzrdiWeight) {
        this.hzrdiWeight = hzrdiWeight;
    }

    public String getHzrdiPlannerRef() {
        return this.hzrdiPlannerRef;
    }

    public void setHzrdiPlannerRef(String hzrdiPlannerRef) {
        this.hzrdiPlannerRef = hzrdiPlannerRef;
    }

    public Long getHzrdiQuantity() {
        return this.hzrdiQuantity;
    }

    public void setHzrdiQuantity(Long hzrdiQuantity) {
        this.hzrdiQuantity = hzrdiQuantity;
    }

    public String getHzrdiMoveMethod() {
        return this.hzrdiMoveMethod;
    }

    public void setHzrdiMoveMethod(String hzrdiMoveMethod) {
        this.hzrdiMoveMethod = hzrdiMoveMethod;
    }

    public ImdgClassEnum getHzrdiSecondaryIMO1() {
        return this.hzrdiSecondaryIMO1;
    }

    public void setHzrdiSecondaryIMO1(ImdgClassEnum hzrdiSecondaryIMO1) {
        this.hzrdiSecondaryIMO1 = hzrdiSecondaryIMO1;
    }

    public ImdgClassEnum getHzrdiSecondaryIMO2() {
        return this.hzrdiSecondaryIMO2;
    }

    public void setHzrdiSecondaryIMO2(ImdgClassEnum hzrdiSecondaryIMO2) {
        this.hzrdiSecondaryIMO2 = hzrdiSecondaryIMO2;
    }

    public String getHzrdiDeckRestrictions() {
        return this.hzrdiDeckRestrictions;
    }

    public void setHzrdiDeckRestrictions(String hzrdiDeckRestrictions) {
        this.hzrdiDeckRestrictions = hzrdiDeckRestrictions;
    }

    public Boolean getHzrdiMarinePollutants() {
        return this.hzrdiMarinePollutants;
    }

    public void setHzrdiMarinePollutants(Boolean hzrdiMarinePollutants) {
        this.hzrdiMarinePollutants = hzrdiMarinePollutants;
    }

    public String getHzrdiDcLgRef() {
        return this.hzrdiDcLgRef;
    }

    public void setHzrdiDcLgRef(String hzrdiDcLgRef) {
        this.hzrdiDcLgRef = hzrdiDcLgRef;
    }

    public String getHzrdiEmergencyTelephone() {
        return this.hzrdiEmergencyTelephone;
    }

    public void setHzrdiEmergencyTelephone(String hzrdiEmergencyTelephone) {
        this.hzrdiEmergencyTelephone = hzrdiEmergencyTelephone;
    }

    public String getHzrdiNotes() {
        return this.hzrdiNotes;
    }

    public void setHzrdiNotes(String hzrdiNotes) {
        this.hzrdiNotes = hzrdiNotes;
    }

    public Long getHzrdiSeq() {
        return this.hzrdiSeq;
    }

    public void setHzrdiSeq(Long hzrdiSeq) {
        this.hzrdiSeq = hzrdiSeq;
    }

    public Hazards getHzrdiHazards() {
        return this.hzrdiHazards;
    }

    protected void setHzrdiHazards(Hazards hzrdiHazards) {
        this.hzrdiHazards = hzrdiHazards;
    }

    public HazardFireCode getHzrdiFireCode() {
        return this.hzrdiFireCode;
    }

    public void setHzrdiFireCode(HazardFireCode hzrdiFireCode) {
        this.hzrdiFireCode = hzrdiFireCode;
    }

    public Set getHzrdiPlacardSet() {
        return this.hzrdiPlacardSet;
    }

    public void setHzrdiPlacardSet(Set hzrdiPlacardSet) {
        this.hzrdiPlacardSet = hzrdiPlacardSet;
    }

}
