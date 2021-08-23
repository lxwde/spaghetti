package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipIsoGroupEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipMaterialEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.MasterBizUnit;
import com.zpmc.ztos.infra.base.business.model.Point3D;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备对象
 *
 * @author yejun
 */
@Data
public abstract class EquipmentDO extends ReferenceEntity
        implements Serializable {

    private Long eqGkey;
    private String eqIdNoCheckDigit;
    private DataSourceEnum eqDataSource;
    private String eqIdPrefix;
    private String eqIdNbrOnly;
    private String eqIdCheckDigit;
    private String eqIdFull;
    private EquipClassEnum eqClass;
    private Long eqLengthMm;
    private Long eqHeightMm;
    private Long eqWidthMm;
    private EquipIsoGroupEnum eqIsoGroup;
    private Double eqTareWeightKg;
    private Double eqSafeWeightKg;
    private Date eqLeaseExpiration;
    private Date eqBuildDate;
    private Boolean eqNoStowOnTopIfEmpty;
    private Boolean eqNoStowOnTopIfLaden;
    private Boolean eqMustStowBelowDeck;
    private Boolean eqMustStowAboveDeck;
    private Boolean eqIsPOS;
    private Boolean eqIsInsulated;
    private EquipMaterialEnum eqMaterial;

    /**
     * 应答器设备类型
     */
    private String eqTransponderType;
    /**
     *应答器设备id
     */
    private String eqTransponderId;
    private Date eqTimeTransponderInstall;
    private Boolean eqUsesAccessories;
    private Boolean eqIsTemperatureControlled;
    private Boolean eqOogOk;
    private Boolean eqIsUnsealable;
    private Boolean eqHasWheels;
    private Boolean eqIsOpen;
    private String eqStrengthCode;
    private String eqCscExpiration;
    private Date eqCreated;
    private String eqCreator;
    private Date eqChanged;
    private String eqChanger;
    private LifeCycleStateEnum eqLifeCycleState;
    private EntitySet eqScope;
    private EquipType eqEquipType;
    private MasterBizUnit eqObsoleteOwner;
    private MasterBizUnit eqObsoleteOperator;
    private MasterBizUnit eqObsoletePreviousOperator;
    private Point3D eqLocPdsCoordinates;

    @Override
    public Serializable getPrimaryKey() {
        return this.getEqGkey();
    }
    public Long getEqGkey() {
        return this.eqGkey;
    }

    protected void setEqGkey(Long eqGkey) {
        this.eqGkey = eqGkey;
    }

    public String getEqIdNoCheckDigit() {
        return this.eqIdNoCheckDigit;
    }

    protected void setEqIdNoCheckDigit(String eqIdNoCheckDigit) {
        this.eqIdNoCheckDigit = eqIdNoCheckDigit;
    }

    public DataSourceEnum getEqDataSource() {
        return this.eqDataSource;
    }

    protected void setEqDataSource(DataSourceEnum eqDataSource) {
        this.eqDataSource = eqDataSource;
    }

    public String getEqIdPrefix() {
        return this.eqIdPrefix;
    }

    protected void setEqIdPrefix(String eqIdPrefix) {
        this.eqIdPrefix = eqIdPrefix;
    }

    public String getEqIdNbrOnly() {
        return this.eqIdNbrOnly;
    }

    protected void setEqIdNbrOnly(String eqIdNbrOnly) {
        this.eqIdNbrOnly = eqIdNbrOnly;
    }

    public String getEqIdCheckDigit() {
        return this.eqIdCheckDigit;
    }

    protected void setEqIdCheckDigit(String eqIdCheckDigit) {
        this.eqIdCheckDigit = eqIdCheckDigit;
    }

    public String getEqIdFull() {
        return this.eqIdFull;
    }

    protected void setEqIdFull(String eqIdFull) {
        this.eqIdFull = eqIdFull;
    }

    public EquipClassEnum getEqClass() {
        return this.eqClass;
    }

    protected void setEqClass(EquipClassEnum eqClass) {
        this.eqClass = eqClass;
    }

    public Long getEqLengthMm() {
        return this.eqLengthMm;
    }

    protected void setEqLengthMm(Long eqLengthMm) {
        this.eqLengthMm = eqLengthMm;
    }

    public Long getEqHeightMm() {
        return this.eqHeightMm;
    }

    protected void setEqHeightMm(Long eqHeightMm) {
        this.eqHeightMm = eqHeightMm;
    }

    public Long getEqWidthMm() {
        return this.eqWidthMm;
    }

    protected void setEqWidthMm(Long eqWidthMm) {
        this.eqWidthMm = eqWidthMm;
    }

    public EquipIsoGroupEnum getEqIsoGroup() {
        return this.eqIsoGroup;
    }

    protected void setEqIsoGroup(EquipIsoGroupEnum eqIsoGroup) {
        this.eqIsoGroup = eqIsoGroup;
    }

    public Double getEqTareWeightKg() {
        return this.eqTareWeightKg;
    }

    protected void setEqTareWeightKg(Double eqTareWeightKg) {
        this.eqTareWeightKg = eqTareWeightKg;
    }

    public Double getEqSafeWeightKg() {
        return this.eqSafeWeightKg;
    }

    protected void setEqSafeWeightKg(Double eqSafeWeightKg) {
        this.eqSafeWeightKg = eqSafeWeightKg;
    }

    public Date getEqLeaseExpiration() {
        return this.eqLeaseExpiration;
    }

    protected void setEqLeaseExpiration(Date eqLeaseExpiration) {
        this.eqLeaseExpiration = eqLeaseExpiration;
    }

    public Date getEqBuildDate() {
        return this.eqBuildDate;
    }

    protected void setEqBuildDate(Date eqBuildDate) {
        this.eqBuildDate = eqBuildDate;
    }

    protected Boolean getEqNoStowOnTopIfEmpty() {
        return this.eqNoStowOnTopIfEmpty;
    }

    protected void setEqNoStowOnTopIfEmpty(Boolean eqNoStowOnTopIfEmpty) {
        this.eqNoStowOnTopIfEmpty = eqNoStowOnTopIfEmpty;
    }

    protected Boolean getEqNoStowOnTopIfLaden() {
        return this.eqNoStowOnTopIfLaden;
    }

    protected void setEqNoStowOnTopIfLaden(Boolean eqNoStowOnTopIfLaden) {
        this.eqNoStowOnTopIfLaden = eqNoStowOnTopIfLaden;
    }

    protected Boolean getEqMustStowBelowDeck() {
        return this.eqMustStowBelowDeck;
    }

    protected void setEqMustStowBelowDeck(Boolean eqMustStowBelowDeck) {
        this.eqMustStowBelowDeck = eqMustStowBelowDeck;
    }

    protected Boolean getEqMustStowAboveDeck() {
        return this.eqMustStowAboveDeck;
    }

    protected void setEqMustStowAboveDeck(Boolean eqMustStowAboveDeck) {
        this.eqMustStowAboveDeck = eqMustStowAboveDeck;
    }

    protected Boolean getEqIsPOS() {
        return this.eqIsPOS;
    }

    protected void setEqIsPOS(Boolean eqIsPOS) {
        this.eqIsPOS = eqIsPOS;
    }

    protected Boolean getEqIsInsulated() {
        return this.eqIsInsulated;
    }

    protected void setEqIsInsulated(Boolean eqIsInsulated) {
        this.eqIsInsulated = eqIsInsulated;
    }

    public EquipMaterialEnum getEqMaterial() {
        return this.eqMaterial;
    }

    protected void setEqMaterial(EquipMaterialEnum eqMaterial) {
        this.eqMaterial = eqMaterial;
    }

    public String getEqTransponderType() {
        return this.eqTransponderType;
    }

    protected void setEqTransponderType(String eqTransponderType) {
        this.eqTransponderType = eqTransponderType;
    }

    public String getEqTransponderId() {
        return this.eqTransponderId;
    }

    protected void setEqTransponderId(String eqTransponderId) {
        this.eqTransponderId = eqTransponderId;
    }

    public Date getEqTimeTransponderInstall() {
        return this.eqTimeTransponderInstall;
    }

    protected void setEqTimeTransponderInstall(Date eqTimeTransponderInstall) {
        this.eqTimeTransponderInstall = eqTimeTransponderInstall;
    }

    protected Boolean getEqUsesAccessories() {
        return this.eqUsesAccessories;
    }

    protected void setEqUsesAccessories(Boolean eqUsesAccessories) {
        this.eqUsesAccessories = eqUsesAccessories;
    }

    protected Boolean getEqIsTemperatureControlled() {
        return this.eqIsTemperatureControlled;
    }

    protected void setEqIsTemperatureControlled(Boolean eqIsTemperatureControlled) {
        this.eqIsTemperatureControlled = eqIsTemperatureControlled;
    }

    protected Boolean getEqOogOk() {
        return this.eqOogOk;
    }

    protected void setEqOogOk(Boolean eqOogOk) {
        this.eqOogOk = eqOogOk;
    }

    protected Boolean getEqIsUnsealable() {
        return this.eqIsUnsealable;
    }

    protected void setEqIsUnsealable(Boolean eqIsUnsealable) {
        this.eqIsUnsealable = eqIsUnsealable;
    }

    protected Boolean getEqHasWheels() {
        return this.eqHasWheels;
    }

    protected void setEqHasWheels(Boolean eqHasWheels) {
        this.eqHasWheels = eqHasWheels;
    }

    public Boolean getEqIsOpen() {
        return this.eqIsOpen;
    }

    protected void setEqIsOpen(Boolean eqIsOpen) {
        this.eqIsOpen = eqIsOpen;
    }

    public String getEqStrengthCode() {
        return this.eqStrengthCode;
    }

    protected void setEqStrengthCode(String eqStrengthCode) {
        this.eqStrengthCode = eqStrengthCode;
    }

    public String getEqCscExpiration() {
        return this.eqCscExpiration;
    }

    protected void setEqCscExpiration(String eqCscExpiration) {
        this.eqCscExpiration = eqCscExpiration;
    }

    public Date getEqCreated() {
        return this.eqCreated;
    }

    protected void setEqCreated(Date eqCreated) {
        this.eqCreated = eqCreated;
    }

    public String getEqCreator() {
        return this.eqCreator;
    }

    protected void setEqCreator(String eqCreator) {
        this.eqCreator = eqCreator;
    }

    public Date getEqChanged() {
        return this.eqChanged;
    }

    protected void setEqChanged(Date eqChanged) {
        this.eqChanged = eqChanged;
    }

    public String getEqChanger() {
        return this.eqChanger;
    }

    protected void setEqChanger(String eqChanger) {
        this.eqChanger = eqChanger;
    }

    public LifeCycleStateEnum getEqLifeCycleState() {
        return this.eqLifeCycleState;
    }

    public void setEqLifeCycleState(LifeCycleStateEnum eqLifeCycleState) {
        this.eqLifeCycleState = eqLifeCycleState;
    }

    public EntitySet getEqScope() {
        return this.eqScope;
    }

    protected void setEqScope(EntitySet eqScope) {
        this.eqScope = eqScope;
    }

    public EquipType getEqEquipType() {
        return this.eqEquipType;
    }

    protected void setEqEquipType(EquipType eqEquipType) {
        this.eqEquipType = eqEquipType;
    }

    public MasterBizUnit getEqObsoleteOwner() {
        return this.eqObsoleteOwner;
    }

    protected void setEqObsoleteOwner(MasterBizUnit eqObsoleteOwner) {
        this.eqObsoleteOwner = eqObsoleteOwner;
    }

    public MasterBizUnit getEqObsoleteOperator() {
        return this.eqObsoleteOperator;
    }

    protected void setEqObsoleteOperator(MasterBizUnit eqObsoleteOperator) {
        this.eqObsoleteOperator = eqObsoleteOperator;
    }

    public MasterBizUnit getEqObsoletePreviousOperator() {
        return this.eqObsoletePreviousOperator;
    }

    protected void setEqObsoletePreviousOperator(MasterBizUnit eqObsoletePreviousOperator) {
        this.eqObsoletePreviousOperator = eqObsoletePreviousOperator;
    }

    public Point3D getEqLocPdsCoordinates() {
        return this.eqLocPdsCoordinates;
    }

    protected void setEqLocPdsCoordinates(Point3D eqLocPdsCoordinates) {
        this.eqLocPdsCoordinates = eqLocPdsCoordinates;
    }


}
