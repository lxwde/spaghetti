package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备类型对象
 *
 * @author yejun
 */
@Data
public abstract class EquipTypeDO extends ReferenceEntity
implements Serializable {

    private Long eqtypGkey;

    /**
     * 设备id
     */
    private String eqtypId;

    /**
     * 数据源
     */
    private DataSourceEnum eqtypDataSource;

    private Boolean eqtypIsDeprecated;
    private Boolean eqtypIsArchetype;
    private EquipBasicLengthEnum eqtypBasicLength;
    private EquipNominalLengthEnum eqtypNominalLength;
    private EquipNominalHeightEnum eqtypNominalHeight;
    private EquipRfrTypeEnum eqtypRfrType;
    private EquipClassEnum eqtypClass;
    private EquipIsoGroupEnum eqtypIsoGroup;
    private Long eqtypLengthMm;
    private Long eqtypHeightMm;
    private Long eqtypWidthMm;
    private Double eqtypTareWeightKg;
    private Double eqtypSafeWeightKg;
    private String eqtypDescription;
    private Long eqtypPictId;
    private Long eqtypMilliTeus;
    private Boolean eqtypUsesAccessories;
    private Boolean eqtypIsTemperatureControlled;
    private Boolean eqtypOogOk;
    private Boolean eqtypIsUnsealable;
    private Boolean eqtypHasWheels;
    private Boolean eqtypIsOpen;
    private Boolean eqtypFits20;
    private Boolean eqtypFits24;
    private Boolean eqtypFits30;
    private Boolean eqtypFits40;
    private Boolean eqtypFits45;
    private Boolean eqtypFits48;
    private Boolean eqtypFits53;
    private Boolean eqtypIsChassisBombCart;
    private Boolean eqtypIsChassisCassette;
    private Boolean eqtypIsChassisNoPick;
    private Boolean eqtypIsChassisTriaxle;
    private Boolean eqtypIsSuperFreezeReefer;
    private Boolean eqtypIsControlledAtmosphereReefer;
    private Boolean eqtypNoStowOnTopIfEmpty;
    private Boolean eqtypNoStowOnTopIfLaden;
    private Boolean eqtypMustStowBelowDeck;
    private Boolean eqtypMustStowAboveDeck;
    private Date eqtypCreated;
    private String eqtypCreator;
    private Date eqtypChanged;
    private String eqtypChanger;
    private LifeCycleStateEnum eqtypLifeCycleState;
    private Integer eqtypTeuCapacity;
    private String eqtypSlotLabels;
    private Boolean eqtypeIs2x20NotAllowed;
    private EquipEventsTypeToRecordEnum eqtypeEventsTypeToRecordEnum;
    private EntitySet eqtypScope;
    private EquipType eqtypArchetype;
    private EquipType eqtypCloneSource;

    public Serializable getPrimaryKey() {
        return this.getEqtypGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEqtypGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EquipTypeDO)) {
            return false;
        }
        EquipTypeDO that = (EquipTypeDO)other;
        return ((Object)id).equals(that.getEqtypGkey());
    }

    public int hashCode() {
        Long id = this.getEqtypGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEqtypGkey() {
        return this.eqtypGkey;
    }

    protected void setEqtypGkey(Long eqtypGkey) {
        this.eqtypGkey = eqtypGkey;
    }

    public String getEqtypId() {
        return this.eqtypId;
    }

    protected void setEqtypId(String eqtypId) {
        this.eqtypId = eqtypId;
    }

    public DataSourceEnum getEqtypDataSource() {
        return this.eqtypDataSource;
    }

    protected void setEqtypDataSource(DataSourceEnum eqtypDataSource) {
        this.eqtypDataSource = eqtypDataSource;
    }

    public Boolean getEqtypIsDeprecated() {
        return this.eqtypIsDeprecated;
    }

    protected void setEqtypIsDeprecated(Boolean eqtypIsDeprecated) {
        this.eqtypIsDeprecated = eqtypIsDeprecated;
    }

    public Boolean getEqtypIsArchetype() {
        return this.eqtypIsArchetype;
    }

    protected void setEqtypIsArchetype(Boolean eqtypIsArchetype) {
        this.eqtypIsArchetype = eqtypIsArchetype;
    }

    public EquipBasicLengthEnum getEqtypBasicLength() {
        return this.eqtypBasicLength;
    }

    protected void setEqtypBasicLength(EquipBasicLengthEnum eqtypBasicLength) {
        this.eqtypBasicLength = eqtypBasicLength;
    }

    public EquipNominalLengthEnum getEqtypNominalLength() {
        return this.eqtypNominalLength;
    }

    protected void setEqtypNominalLength(EquipNominalLengthEnum eqtypNominalLength) {
        this.eqtypNominalLength = eqtypNominalLength;
    }

    public EquipNominalHeightEnum getEqtypNominalHeight() {
        return this.eqtypNominalHeight;
    }

    protected void setEqtypNominalHeight(EquipNominalHeightEnum eqtypNominalHeight) {
        this.eqtypNominalHeight = eqtypNominalHeight;
    }

    public EquipRfrTypeEnum getEqtypRfrType() {
        return this.eqtypRfrType;
    }

    protected void setEqtypRfrType(EquipRfrTypeEnum eqtypRfrType) {
        this.eqtypRfrType = eqtypRfrType;
    }

    public EquipClassEnum getEqtypClass() {
        return this.eqtypClass;
    }

    protected void setEqtypClass(EquipClassEnum eqtypClass) {
        this.eqtypClass = eqtypClass;
    }

    public EquipIsoGroupEnum getEqtypIsoGroup() {
        return this.eqtypIsoGroup;
    }

    protected void setEqtypIsoGroup(EquipIsoGroupEnum eqtypIsoGroup) {
        this.eqtypIsoGroup = eqtypIsoGroup;
    }

    public Long getEqtypLengthMm() {
        return this.eqtypLengthMm;
    }

    protected void setEqtypLengthMm(Long eqtypLengthMm) {
        this.eqtypLengthMm = eqtypLengthMm;
    }

    public Long getEqtypHeightMm() {
        return this.eqtypHeightMm;
    }

    protected void setEqtypHeightMm(Long eqtypHeightMm) {
        this.eqtypHeightMm = eqtypHeightMm;
    }

    public Long getEqtypWidthMm() {
        return this.eqtypWidthMm;
    }

    protected void setEqtypWidthMm(Long eqtypWidthMm) {
        this.eqtypWidthMm = eqtypWidthMm;
    }

    public Double getEqtypTareWeightKg() {
        return this.eqtypTareWeightKg;
    }

    protected void setEqtypTareWeightKg(Double eqtypTareWeightKg) {
        this.eqtypTareWeightKg = eqtypTareWeightKg;
    }

    public Double getEqtypSafeWeightKg() {
        return this.eqtypSafeWeightKg;
    }

    protected void setEqtypSafeWeightKg(Double eqtypSafeWeightKg) {
        this.eqtypSafeWeightKg = eqtypSafeWeightKg;
    }

    public String getEqtypDescription() {
        return this.eqtypDescription;
    }

    protected void setEqtypDescription(String eqtypDescription) {
        this.eqtypDescription = eqtypDescription;
    }

    public Long getEqtypPictId() {
        return this.eqtypPictId;
    }

    protected void setEqtypPictId(Long eqtypPictId) {
        this.eqtypPictId = eqtypPictId;
    }

    public Long getEqtypMilliTeus() {
        return this.eqtypMilliTeus;
    }

    protected void setEqtypMilliTeus(Long eqtypMilliTeus) {
        this.eqtypMilliTeus = eqtypMilliTeus;
    }

    public Boolean getEqtypUsesAccessories() {
        return this.eqtypUsesAccessories;
    }

    protected void setEqtypUsesAccessories(Boolean eqtypUsesAccessories) {
        this.eqtypUsesAccessories = eqtypUsesAccessories;
    }

    public Boolean getEqtypIsTemperatureControlled() {
        return this.eqtypIsTemperatureControlled;
    }

    protected void setEqtypIsTemperatureControlled(Boolean eqtypIsTemperatureControlled) {
        this.eqtypIsTemperatureControlled = eqtypIsTemperatureControlled;
    }

    public Boolean getEqtypOogOk() {
        return this.eqtypOogOk;
    }

    protected void setEqtypOogOk(Boolean eqtypOogOk) {
        this.eqtypOogOk = eqtypOogOk;
    }

    public Boolean getEqtypIsUnsealable() {
        return this.eqtypIsUnsealable;
    }

    protected void setEqtypIsUnsealable(Boolean eqtypIsUnsealable) {
        this.eqtypIsUnsealable = eqtypIsUnsealable;
    }

    public Boolean getEqtypHasWheels() {
        return this.eqtypHasWheels;
    }

    protected void setEqtypHasWheels(Boolean eqtypHasWheels) {
        this.eqtypHasWheels = eqtypHasWheels;
    }

    public Boolean getEqtypIsOpen() {
        return this.eqtypIsOpen;
    }

    protected void setEqtypIsOpen(Boolean eqtypIsOpen) {
        this.eqtypIsOpen = eqtypIsOpen;
    }

    public Boolean getEqtypFits20() {
        return this.eqtypFits20;
    }

    public void setEqtypFits20(Boolean eqtypFits20) {
        this.eqtypFits20 = eqtypFits20;
    }

    public Boolean getEqtypFits24() {
        return this.eqtypFits24;
    }

    public void setEqtypFits24(Boolean eqtypFits24) {
        this.eqtypFits24 = eqtypFits24;
    }

    public Boolean getEqtypFits30() {
        return this.eqtypFits30;
    }

    public void setEqtypFits30(Boolean eqtypFits30) {
        this.eqtypFits30 = eqtypFits30;
    }

    public Boolean getEqtypFits40() {
        return this.eqtypFits40;
    }

    public void setEqtypFits40(Boolean eqtypFits40) {
        this.eqtypFits40 = eqtypFits40;
    }

    public Boolean getEqtypFits45() {
        return this.eqtypFits45;
    }

    protected void setEqtypFits45(Boolean eqtypFits45) {
        this.eqtypFits45 = eqtypFits45;
    }

    public Boolean getEqtypFits48() {
        return this.eqtypFits48;
    }

    protected void setEqtypFits48(Boolean eqtypFits48) {
        this.eqtypFits48 = eqtypFits48;
    }

    public Boolean getEqtypFits53() {
        return this.eqtypFits53;
    }

    protected void setEqtypFits53(Boolean eqtypFits53) {
        this.eqtypFits53 = eqtypFits53;
    }

    protected Boolean getEqtypIsChassisBombCart() {
        return this.eqtypIsChassisBombCart;
    }

    protected void setEqtypIsChassisBombCart(Boolean eqtypIsChassisBombCart) {
        this.eqtypIsChassisBombCart = eqtypIsChassisBombCart;
    }

    protected Boolean getEqtypIsChassisCassette() {
        return this.eqtypIsChassisCassette;
    }

    protected void setEqtypIsChassisCassette(Boolean eqtypIsChassisCassette) {
        this.eqtypIsChassisCassette = eqtypIsChassisCassette;
    }

    protected Boolean getEqtypIsChassisNoPick() {
        return this.eqtypIsChassisNoPick;
    }

    protected void setEqtypIsChassisNoPick(Boolean eqtypIsChassisNoPick) {
        this.eqtypIsChassisNoPick = eqtypIsChassisNoPick;
    }

    public Boolean getEqtypIsChassisTriaxle() {
        return this.eqtypIsChassisTriaxle;
    }

    protected void setEqtypIsChassisTriaxle(Boolean eqtypIsChassisTriaxle) {
        this.eqtypIsChassisTriaxle = eqtypIsChassisTriaxle;
    }

    public Boolean getEqtypIsSuperFreezeReefer() {
        return this.eqtypIsSuperFreezeReefer;
    }

    public void setEqtypIsSuperFreezeReefer(Boolean eqtypIsSuperFreezeReefer) {
        this.eqtypIsSuperFreezeReefer = eqtypIsSuperFreezeReefer;
    }

    public Boolean getEqtypIsControlledAtmosphereReefer() {
        return this.eqtypIsControlledAtmosphereReefer;
    }

    protected void setEqtypIsControlledAtmosphereReefer(Boolean eqtypIsControlledAtmosphereReefer) {
        this.eqtypIsControlledAtmosphereReefer = eqtypIsControlledAtmosphereReefer;
    }

    public Boolean getEqtypNoStowOnTopIfEmpty() {
        return this.eqtypNoStowOnTopIfEmpty;
    }

    protected void setEqtypNoStowOnTopIfEmpty(Boolean eqtypNoStowOnTopIfEmpty) {
        this.eqtypNoStowOnTopIfEmpty = eqtypNoStowOnTopIfEmpty;
    }

    public Boolean getEqtypNoStowOnTopIfLaden() {
        return this.eqtypNoStowOnTopIfLaden;
    }

    protected void setEqtypNoStowOnTopIfLaden(Boolean eqtypNoStowOnTopIfLaden) {
        this.eqtypNoStowOnTopIfLaden = eqtypNoStowOnTopIfLaden;
    }

    public Boolean getEqtypMustStowBelowDeck() {
        return this.eqtypMustStowBelowDeck;
    }

    protected void setEqtypMustStowBelowDeck(Boolean eqtypMustStowBelowDeck) {
        this.eqtypMustStowBelowDeck = eqtypMustStowBelowDeck;
    }

    public Boolean getEqtypMustStowAboveDeck() {
        return this.eqtypMustStowAboveDeck;
    }

    protected void setEqtypMustStowAboveDeck(Boolean eqtypMustStowAboveDeck) {
        this.eqtypMustStowAboveDeck = eqtypMustStowAboveDeck;
    }

    public Date getEqtypCreated() {
        return this.eqtypCreated;
    }

    protected void setEqtypCreated(Date eqtypCreated) {
        this.eqtypCreated = eqtypCreated;
    }

    public String getEqtypCreator() {
        return this.eqtypCreator;
    }

    protected void setEqtypCreator(String eqtypCreator) {
        this.eqtypCreator = eqtypCreator;
    }

    public Date getEqtypChanged() {
        return this.eqtypChanged;
    }

    protected void setEqtypChanged(Date eqtypChanged) {
        this.eqtypChanged = eqtypChanged;
    }

    public String getEqtypChanger() {
        return this.eqtypChanger;
    }

    protected void setEqtypChanger(String eqtypChanger) {
        this.eqtypChanger = eqtypChanger;
    }

    public LifeCycleStateEnum getEqtypLifeCycleState() {
        return this.eqtypLifeCycleState;
    }

    public void setEqtypLifeCycleState(LifeCycleStateEnum eqtypLifeCycleState) {
        this.eqtypLifeCycleState = eqtypLifeCycleState;
    }

    protected Integer getEqtypTeuCapacity() {
        return this.eqtypTeuCapacity;
    }

    protected void setEqtypTeuCapacity(Integer eqtypTeuCapacity) {
        this.eqtypTeuCapacity = eqtypTeuCapacity;
    }

    public String getEqtypSlotLabels() {
        return this.eqtypSlotLabels;
    }

    protected void setEqtypSlotLabels(String eqtypSlotLabels) {
        this.eqtypSlotLabels = eqtypSlotLabels;
    }

    protected Boolean getEqtypeIs2x20NotAllowed() {
        return this.eqtypeIs2x20NotAllowed;
    }

    protected void setEqtypeIs2x20NotAllowed(Boolean eqtypeIs2x20NotAllowed) {
        this.eqtypeIs2x20NotAllowed = eqtypeIs2x20NotAllowed;
    }

    public EquipEventsTypeToRecordEnum getEqtypeEventsTypeToRecordEnum() {
        return this.eqtypeEventsTypeToRecordEnum;
    }

    protected void setEqtypeEventsTypeToRecordEnum(EquipEventsTypeToRecordEnum eqtypeEventsTypeToRecordEnum) {
        this.eqtypeEventsTypeToRecordEnum = eqtypeEventsTypeToRecordEnum;
    }

    public EntitySet getEqtypScope() {
        return this.eqtypScope;
    }

    protected void setEqtypScope(EntitySet eqtypScope) {
        this.eqtypScope = eqtypScope;
    }

    public EquipType getEqtypArchetype() {
        return this.eqtypArchetype;
    }

    protected void setEqtypArchetype(EquipType eqtypArchetype) {
        this.eqtypArchetype = eqtypArchetype;
    }

    public EquipType getEqtypCloneSource() {
        return this.eqtypCloneSource;
    }

    protected void setEqtypCloneSource(EquipType eqtypCloneSource) {
        this.eqtypCloneSource = eqtypCloneSource;
    }
}
