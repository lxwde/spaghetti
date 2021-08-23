package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqDamageSeverityEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.UnitEquipmentUseModeEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipGrade;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.inventory.UnitEquipDamages;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Operator;

import java.io.Serializable;
import java.util.Date;

public class EquipmentStateDO extends DatabaseEntity implements Serializable {
    private Long eqsGkey;
    private UnitEquipmentUseModeEnum eqsEqUseMode;
    private EqDamageSeverityEnum eqsObsoleteDamageSeverity;
    private String eqsOffhireLocation;
    private LocTypeEnum eqsLastPosLocType;
    private String eqsLastPosName;
    private Date eqsTimeLastMove;
    private String eqsFlexString01;
    private String eqsFlexString02;
    private String eqsFlexString03;
    private Long eqsTripCount;
    private Operator eqsOperator;
    private Equipment eqsEquipment;
    private ScopedBizUnit eqsEqOperator;
    private ScopedBizUnit eqsEqPreviousOperator;
    private ScopedBizUnit eqsEqOwner;
    private EquipGrade eqsGradeID;
    private UnitEquipDamages eqsObsoleteDamages;
    private Facility eqsLastFacility;

    public Serializable getPrimaryKey() {
        return this.getEqsGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqsGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipmentStateDO)) {
            return false;
        }
        EquipmentStateDO that = (EquipmentStateDO)other;
        return ((Object)id).equals(that.getEqsGkey());
    }

    public int hashCode() {
        Long id = this.getEqsGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqsGkey() {
        return this.eqsGkey;
    }

    protected void setEqsGkey(Long eqsGkey) {
        this.eqsGkey = eqsGkey;
    }

    public UnitEquipmentUseModeEnum getEqsEqUseMode() {
        return this.eqsEqUseMode;
    }

    protected void setEqsEqUseMode(UnitEquipmentUseModeEnum eqsEqUseMode) {
        this.eqsEqUseMode = eqsEqUseMode;
    }

    public EqDamageSeverityEnum getEqsObsoleteDamageSeverity() {
        return this.eqsObsoleteDamageSeverity;
    }

    protected void setEqsObsoleteDamageSeverity(EqDamageSeverityEnum eqsObsoleteDamageSeverity) {
        this.eqsObsoleteDamageSeverity = eqsObsoleteDamageSeverity;
    }

    public String getEqsOffhireLocation() {
        return this.eqsOffhireLocation;
    }

    protected void setEqsOffhireLocation(String eqsOffhireLocation) {
        this.eqsOffhireLocation = eqsOffhireLocation;
    }

    public LocTypeEnum getEqsLastPosLocType() {
        return this.eqsLastPosLocType;
    }

    public void setEqsLastPosLocType(LocTypeEnum eqsLastPosLocType) {
        this.eqsLastPosLocType = eqsLastPosLocType;
    }

    public String getEqsLastPosName() {
        return this.eqsLastPosName;
    }

    public void setEqsLastPosName(String eqsLastPosName) {
        this.eqsLastPosName = eqsLastPosName;
    }

    public Date getEqsTimeLastMove() {
        return this.eqsTimeLastMove;
    }

    public void setEqsTimeLastMove(Date eqsTimeLastMove) {
        this.eqsTimeLastMove = eqsTimeLastMove;
    }

    public String getEqsFlexString01() {
        return this.eqsFlexString01;
    }

    protected void setEqsFlexString01(String eqsFlexString01) {
        this.eqsFlexString01 = eqsFlexString01;
    }

    public String getEqsFlexString02() {
        return this.eqsFlexString02;
    }

    protected void setEqsFlexString02(String eqsFlexString02) {
        this.eqsFlexString02 = eqsFlexString02;
    }

    public String getEqsFlexString03() {
        return this.eqsFlexString03;
    }

    protected void setEqsFlexString03(String eqsFlexString03) {
        this.eqsFlexString03 = eqsFlexString03;
    }

    public Long getEqsTripCount() {
        return this.eqsTripCount;
    }

    protected void setEqsTripCount(Long eqsTripCount) {
        this.eqsTripCount = eqsTripCount;
    }

    public Operator getEqsOperator() {
        return this.eqsOperator;
    }

    protected void setEqsOperator(Operator eqsOperator) {
        this.eqsOperator = eqsOperator;
    }

    public Equipment getEqsEquipment() {
        return this.eqsEquipment;
    }

    protected void setEqsEquipment(Equipment eqsEquipment) {
        this.eqsEquipment = eqsEquipment;
    }

    public ScopedBizUnit getEqsEqOperator() {
        return this.eqsEqOperator;
    }

    protected void setEqsEqOperator(ScopedBizUnit eqsEqOperator) {
        this.eqsEqOperator = eqsEqOperator;
    }

    public ScopedBizUnit getEqsEqPreviousOperator() {
        return this.eqsEqPreviousOperator;
    }

    protected void setEqsEqPreviousOperator(ScopedBizUnit eqsEqPreviousOperator) {
        this.eqsEqPreviousOperator = eqsEqPreviousOperator;
    }

    public ScopedBizUnit getEqsEqOwner() {
        return this.eqsEqOwner;
    }

    protected void setEqsEqOwner(ScopedBizUnit eqsEqOwner) {
        this.eqsEqOwner = eqsEqOwner;
    }

    public EquipGrade getEqsGradeID() {
        return this.eqsGradeID;
    }

    protected void setEqsGradeID(EquipGrade eqsGradeID) {
        this.eqsGradeID = eqsGradeID;
    }

    public UnitEquipDamages getEqsObsoleteDamages() {
        return this.eqsObsoleteDamages;
    }

    protected void setEqsObsoleteDamages(UnitEquipDamages eqsObsoleteDamages) {
        this.eqsObsoleteDamages = eqsObsoleteDamages;
    }

    public Facility getEqsLastFacility() {
        return this.eqsLastFacility;
    }

    public void setEqsLastFacility(Facility eqsLastFacility) {
        this.eqsLastFacility = eqsLastFacility;
    }
}
