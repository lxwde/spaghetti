package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.equipments.ChePool;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.PointOfWork;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CheDO extends DatabaseEntity implements Serializable {
    private Long cheGkey;
    private LifeCycleStateEnum xpeCheLifeCycleState;
    private Long chePkey;
    private Long chePoolPkey;
    private Long chePowPkey;
    private String cheVesselCode;
    private Long cheStatus;
    private CheStatusEnum cheStatusEnum;
    private Long cheTalkStatus;
    private CheTalkStatusEnum cheTalkStatusEnum;
    private Long cheMessageStatus;
    private CheMessageStatusEnum cheMessageStatusEnum;
    private Long cheMtsStatus;
    private Long cheIttDispatchMode;
    private Date cheLastTime;
    private Date cheTimeCompleteLastJob;
    private Date cheTimeLogInOut;
    private Date cheTimeAvailableUnavailable;
    private Date cheTimeOutOfService;
    private Date cheTimeLift;
    private Date cheTimeFinish;
    private Date cheTimeDispatch;
    private Long cheJobDistance;
    private Long cheToJobDistance;
    private Long cheLadenTravel;
    private Long cheEmptyTravel;
    private String cheTerminal;
    private String chePlannedTerminal;
    private Long cheMtsPlatformSize;
    private String cheLastPosition;
    private String cheLastPut;
    private String chePlanPosition;
    private String cheRangeSelBlock;
    private Long cheRangeFirstRow;
    private Long cheRangeLastRow;
    private Long cheRangeFirstColumn;
    private Long cheRangeLastColumn;
    private String cheLoginName;
    private Long cheAllowedBoxes;
    private Long cheCurrentPositionSectionIndex;
    private Long cheLastPositionSectionIndex;
    private Long cheNominatedChassisSectionIndex;
    private Long cheAssignedCheId;
    private String cheQualifiers;
    private Long cheAlternativeRadioId;
    private Long cheDefaultEcCommunicator;
    private Long cheCurrentEcCommunicator;
    private Long cheMaximumWeight;
    private String cheFullName;
    private Long cheLastWorkInstructionReference;
    private String cheShortName;
    private String cheDefaultProgram;
    private Long cheKind;
    private CheKindEnum cheKindEnum;
    private Long cheOperatingMode;
    private CheOperatingModeEnum cheOperatingModeEnum;
    private Boolean cheHasMdt;
    private Long cheMaximumHeight;
    private Long cheIcon;
    private Long cheId;
    private Long cheScreenType;
    private Long cheScreenHorizontal;
    private Long cheScreenVertical;
    private Long cheInProgram;
    private Long cheNumFunctionKeys;
    private Long cheJobStepState;
    private CheJobStepStateEnum cheJobStepStateEnum;
    private Date cheJobStepCompleteTime;
    private Date cheLastJobstepTransition;
    private Long cheCurrentlyToggledWiRef;
    private Date cheAcceptJobDonePressTime;
    private String cheExtendedAddress;
    private Long cheMaximumTeu;
    private Boolean cheIsInJobStepMode;
    private Long cheClerkPowReference;
    private String cheClerkVesselCode;
    private Long cheClerkContainerKey;
    private Long cheClerkLastLandedCheId;
    private Long cheClerkTeuLanded;
    private Long cheTrailer;
    private Long cheAssistState;
    private CheAssistStateEnum cheAssistStateEnum;
    private Boolean cheScaleOn;
    private Boolean cheInCommsFail;
    private Boolean cheChassisFetchReq;
    private Boolean cheNoLoginRequired;
    private Boolean cheAutomationActive;
    private Boolean cheDispatchRequested;
    private Boolean cheManualDispatch;
    private Boolean cheSendsPdsWeight;
    private Boolean cheSuspendAutoDispatch;
    private Boolean cheTwinCarryCapable;
    private Boolean cheManualDispatchPending;
    private Boolean cheHasOverheightGear;
    private Boolean cheFirstLiftTookPlace;
    private Boolean cheTwinLiftCapable;
    private Boolean cheMtsDamaged;
    private Boolean cheUsesPds;
    private Boolean cheConfigurableTrailer;
    private Boolean cheNominalLength20Capable;
    private Boolean cheNominalLength40Capable;
    private Boolean cheNominalLength45Capable;
    private Boolean cheNominalLength24Capable;
    private Boolean cheNominalLength48Capable;
    private Boolean cheNominalLength53Capable;
    private Boolean cheNominalLength30Capable;
    private Boolean cheNominalLength60Capable;
    private String cheAutoCheTechnicalStatus;
    private String cheAutoCheOperationalStatus;
    private String cheAutoCheWorkStatus;
    private Long cheTwinDiffWgtAllowance;
    private Long cheScaleWeightUnit;
    private Float relativeQueuePositionForQC;
    private Boolean waitingForTruckInsert;
    private String cheAttachedChassisId;
    private Boolean cheTandemLiftCapable;
    private Long cheMaxTandemWeight;
    private Integer cheProximityRadius;
    private Boolean cheQuadLiftCapable;
    private Long cheMaxQuadWeight;
    private String cheDispatchInfo;
    private Long cheLane;
    private Long hasTrailer;
    private Long cheWorkLoad;
    private Long cheAutoCheRunningHours;
    private Long cheEnergyLevel;
    private BatteryStateEnum cheEnergyStateEnum;
    private Long cheLiftCapacity;
    private Double cheMaximumWeightInKg;
    private Double cheMaxQuadWeightInKg;
    private Double cheMaxTandemWeightInKg;
    private Double cheLiftCapacityInKg;
    private CheLiftOperationalStatusEnum cheLiftOperationalStatus;
    private Double cheTwinDiffWgtAllowanceInKg;
    private Boolean cheIsOcrDataBeingAccepted;
    private String cheECStateFlexString1;
    private String cheECStateFlexString2;
    private String cheECStateFlexString3;
    private Long cheAllowedChassisKinds;
    private Boolean cheIsCharging;
    private Boolean cheHasActiveAlarm;
    private Yard cheYard;
    private ChePool chePool;
    private PointOfWork chePointOfWork;
    private Che cheAssignedChe;
    private Che cheClerkLastLandedChe;
    private List cheAlarms;
    private LocPosition cheLastKnownLocPos;

    public Serializable getPrimaryKey() {
        return this.getCheGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getCheGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof CheDO)) {
            return false;
        }
        CheDO that = (CheDO)other;
        return ((Object)id).equals(that.getCheGkey());
    }

    public int hashCode() {
        Long id = this.getCheGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getCheGkey() {
        return this.cheGkey;
    }

    public void setCheGkey(Long cheGkey) {
        this.cheGkey = cheGkey;
    }

    public LifeCycleStateEnum getXpeCheLifeCycleState() {
        return this.xpeCheLifeCycleState;
    }

    public void setXpeCheLifeCycleState(LifeCycleStateEnum xpeCheLifeCycleState) {
        this.xpeCheLifeCycleState = xpeCheLifeCycleState;
    }

    public Long getChePkey() {
        return this.chePkey;
    }

    public void setChePkey(Long chePkey) {
        this.chePkey = chePkey;
    }

    public Long getChePoolPkey() {
        return this.chePoolPkey;
    }

    public void setChePoolPkey(Long chePoolPkey) {
        this.chePoolPkey = chePoolPkey;
    }

    public Long getChePowPkey() {
        return this.chePowPkey;
    }

    public void setChePowPkey(Long chePowPkey) {
        this.chePowPkey = chePowPkey;
    }

    public String getCheVesselCode() {
        return this.cheVesselCode;
    }

    public void setCheVesselCode(String cheVesselCode) {
        this.cheVesselCode = cheVesselCode;
    }

    public Long getCheStatus() {
        return this.cheStatus;
    }

    public void setCheStatus(Long cheStatus) {
        this.cheStatus = cheStatus;
    }

    public CheStatusEnum getCheStatusEnum() {
        return this.cheStatusEnum;
    }

    public void setCheStatusEnum(CheStatusEnum cheStatusEnum) {
        this.cheStatusEnum = cheStatusEnum;
    }

    public Long getCheTalkStatus() {
        return this.cheTalkStatus;
    }

    public void setCheTalkStatus(Long cheTalkStatus) {
        this.cheTalkStatus = cheTalkStatus;
    }

    public CheTalkStatusEnum getCheTalkStatusEnum() {
        return this.cheTalkStatusEnum;
    }

    public void setCheTalkStatusEnum(CheTalkStatusEnum cheTalkStatusEnum) {
        this.cheTalkStatusEnum = cheTalkStatusEnum;
    }

    public Long getCheMessageStatus() {
        return this.cheMessageStatus;
    }

    public void setCheMessageStatus(Long cheMessageStatus) {
        this.cheMessageStatus = cheMessageStatus;
    }

    public CheMessageStatusEnum getCheMessageStatusEnum() {
        return this.cheMessageStatusEnum;
    }

    public void setCheMessageStatusEnum(CheMessageStatusEnum cheMessageStatusEnum) {
        this.cheMessageStatusEnum = cheMessageStatusEnum;
    }

    public Long getCheMtsStatus() {
        return this.cheMtsStatus;
    }

    public void setCheMtsStatus(Long cheMtsStatus) {
        this.cheMtsStatus = cheMtsStatus;
    }

    public Long getCheIttDispatchMode() {
        return this.cheIttDispatchMode;
    }

    public void setCheIttDispatchMode(Long cheIttDispatchMode) {
        this.cheIttDispatchMode = cheIttDispatchMode;
    }

    public Date getCheLastTime() {
        return this.cheLastTime;
    }

    public void setCheLastTime(Date cheLastTime) {
        this.cheLastTime = cheLastTime;
    }

    public Date getCheTimeCompleteLastJob() {
        return this.cheTimeCompleteLastJob;
    }

    public void setCheTimeCompleteLastJob(Date cheTimeCompleteLastJob) {
        this.cheTimeCompleteLastJob = cheTimeCompleteLastJob;
    }

    public Date getCheTimeLogInOut() {
        return this.cheTimeLogInOut;
    }

    public void setCheTimeLogInOut(Date cheTimeLogInOut) {
        this.cheTimeLogInOut = cheTimeLogInOut;
    }

    public Date getCheTimeAvailableUnavailable() {
        return this.cheTimeAvailableUnavailable;
    }

    public void setCheTimeAvailableUnavailable(Date cheTimeAvailableUnavailable) {
        this.cheTimeAvailableUnavailable = cheTimeAvailableUnavailable;
    }

    public Date getCheTimeOutOfService() {
        return this.cheTimeOutOfService;
    }

    public void setCheTimeOutOfService(Date cheTimeOutOfService) {
        this.cheTimeOutOfService = cheTimeOutOfService;
    }

    public Date getCheTimeLift() {
        return this.cheTimeLift;
    }

    public void setCheTimeLift(Date cheTimeLift) {
        this.cheTimeLift = cheTimeLift;
    }

    public Date getCheTimeFinish() {
        return this.cheTimeFinish;
    }

    public void setCheTimeFinish(Date cheTimeFinish) {
        this.cheTimeFinish = cheTimeFinish;
    }

    public Date getCheTimeDispatch() {
        return this.cheTimeDispatch;
    }

    public void setCheTimeDispatch(Date cheTimeDispatch) {
        this.cheTimeDispatch = cheTimeDispatch;
    }

    public Long getCheJobDistance() {
        return this.cheJobDistance;
    }

    public void setCheJobDistance(Long cheJobDistance) {
        this.cheJobDistance = cheJobDistance;
    }

    public Long getCheToJobDistance() {
        return this.cheToJobDistance;
    }

    public void setCheToJobDistance(Long cheToJobDistance) {
        this.cheToJobDistance = cheToJobDistance;
    }

    public Long getCheLadenTravel() {
        return this.cheLadenTravel;
    }

    public void setCheLadenTravel(Long cheLadenTravel) {
        this.cheLadenTravel = cheLadenTravel;
    }

    public Long getCheEmptyTravel() {
        return this.cheEmptyTravel;
    }

    public void setCheEmptyTravel(Long cheEmptyTravel) {
        this.cheEmptyTravel = cheEmptyTravel;
    }

    public String getCheTerminal() {
        return this.cheTerminal;
    }

    public void setCheTerminal(String cheTerminal) {
        this.cheTerminal = cheTerminal;
    }

    public String getChePlannedTerminal() {
        return this.chePlannedTerminal;
    }

    public void setChePlannedTerminal(String chePlannedTerminal) {
        this.chePlannedTerminal = chePlannedTerminal;
    }

    public Long getCheMtsPlatformSize() {
        return this.cheMtsPlatformSize;
    }

    public void setCheMtsPlatformSize(Long cheMtsPlatformSize) {
        this.cheMtsPlatformSize = cheMtsPlatformSize;
    }

    public String getCheLastPosition() {
        return this.cheLastPosition;
    }

    public void setCheLastPosition(String cheLastPosition) {
        this.cheLastPosition = cheLastPosition;
    }

    public String getCheLastPut() {
        return this.cheLastPut;
    }

    public void setCheLastPut(String cheLastPut) {
        this.cheLastPut = cheLastPut;
    }

    public String getChePlanPosition() {
        return this.chePlanPosition;
    }

    public void setChePlanPosition(String chePlanPosition) {
        this.chePlanPosition = chePlanPosition;
    }

    public String getCheRangeSelBlock() {
        return this.cheRangeSelBlock;
    }

    public void setCheRangeSelBlock(String cheRangeSelBlock) {
        this.cheRangeSelBlock = cheRangeSelBlock;
    }

    public Long getCheRangeFirstRow() {
        return this.cheRangeFirstRow;
    }

    public void setCheRangeFirstRow(Long cheRangeFirstRow) {
        this.cheRangeFirstRow = cheRangeFirstRow;
    }

    public Long getCheRangeLastRow() {
        return this.cheRangeLastRow;
    }

    public void setCheRangeLastRow(Long cheRangeLastRow) {
        this.cheRangeLastRow = cheRangeLastRow;
    }

    public Long getCheRangeFirstColumn() {
        return this.cheRangeFirstColumn;
    }

    public void setCheRangeFirstColumn(Long cheRangeFirstColumn) {
        this.cheRangeFirstColumn = cheRangeFirstColumn;
    }

    public Long getCheRangeLastColumn() {
        return this.cheRangeLastColumn;
    }

    public void setCheRangeLastColumn(Long cheRangeLastColumn) {
        this.cheRangeLastColumn = cheRangeLastColumn;
    }

    public String getCheLoginName() {
        return this.cheLoginName;
    }

    public void setCheLoginName(String cheLoginName) {
        this.cheLoginName = cheLoginName;
    }

    public Long getCheAllowedBoxes() {
        return this.cheAllowedBoxes;
    }

    public void setCheAllowedBoxes(Long cheAllowedBoxes) {
        this.cheAllowedBoxes = cheAllowedBoxes;
    }

    public Long getCheCurrentPositionSectionIndex() {
        return this.cheCurrentPositionSectionIndex;
    }

    public void setCheCurrentPositionSectionIndex(Long cheCurrentPositionSectionIndex) {
        this.cheCurrentPositionSectionIndex = cheCurrentPositionSectionIndex;
    }

    public Long getCheLastPositionSectionIndex() {
        return this.cheLastPositionSectionIndex;
    }

    public void setCheLastPositionSectionIndex(Long cheLastPositionSectionIndex) {
        this.cheLastPositionSectionIndex = cheLastPositionSectionIndex;
    }

    public Long getCheNominatedChassisSectionIndex() {
        return this.cheNominatedChassisSectionIndex;
    }

    public void setCheNominatedChassisSectionIndex(Long cheNominatedChassisSectionIndex) {
        this.cheNominatedChassisSectionIndex = cheNominatedChassisSectionIndex;
    }

    public Long getCheAssignedCheId() {
        return this.cheAssignedCheId;
    }

    public void setCheAssignedCheId(Long cheAssignedCheId) {
        this.cheAssignedCheId = cheAssignedCheId;
    }

    public String getCheQualifiers() {
        return this.cheQualifiers;
    }

    public void setCheQualifiers(String cheQualifiers) {
        this.cheQualifiers = cheQualifiers;
    }

    public Long getCheAlternativeRadioId() {
        return this.cheAlternativeRadioId;
    }

    public void setCheAlternativeRadioId(Long cheAlternativeRadioId) {
        this.cheAlternativeRadioId = cheAlternativeRadioId;
    }

    public Long getCheDefaultEcCommunicator() {
        return this.cheDefaultEcCommunicator;
    }

    public void setCheDefaultEcCommunicator(Long cheDefaultEcCommunicator) {
        this.cheDefaultEcCommunicator = cheDefaultEcCommunicator;
    }

    public Long getCheCurrentEcCommunicator() {
        return this.cheCurrentEcCommunicator;
    }

    public void setCheCurrentEcCommunicator(Long cheCurrentEcCommunicator) {
        this.cheCurrentEcCommunicator = cheCurrentEcCommunicator;
    }

    public Long getCheMaximumWeight() {
        return this.cheMaximumWeight;
    }

    public void setCheMaximumWeight(Long cheMaximumWeight) {
        this.cheMaximumWeight = cheMaximumWeight;
    }

    public String getCheFullName() {
        return this.cheFullName;
    }

    public void setCheFullName(String cheFullName) {
        this.cheFullName = cheFullName;
    }

    public Long getCheLastWorkInstructionReference() {
        return this.cheLastWorkInstructionReference;
    }

    public void setCheLastWorkInstructionReference(Long cheLastWorkInstructionReference) {
        this.cheLastWorkInstructionReference = cheLastWorkInstructionReference;
    }

    public String getCheShortName() {
        return this.cheShortName;
    }

    public void setCheShortName(String cheShortName) {
        this.cheShortName = cheShortName;
    }

    public String getCheDefaultProgram() {
        return this.cheDefaultProgram;
    }

    public void setCheDefaultProgram(String cheDefaultProgram) {
        this.cheDefaultProgram = cheDefaultProgram;
    }

    public Long getCheKind() {
        return this.cheKind;
    }

    public void setCheKind(Long cheKind) {
        this.cheKind = cheKind;
    }

    public CheKindEnum getCheKindEnum() {
        return this.cheKindEnum;
    }

    public void setCheKindEnum(CheKindEnum cheKindEnum) {
        this.cheKindEnum = cheKindEnum;
    }

    public Long getCheOperatingMode() {
        return this.cheOperatingMode;
    }

    public void setCheOperatingMode(Long cheOperatingMode) {
        this.cheOperatingMode = cheOperatingMode;
    }

    public CheOperatingModeEnum getCheOperatingModeEnum() {
        return this.cheOperatingModeEnum;
    }

    public void setCheOperatingModeEnum(CheOperatingModeEnum cheOperatingModeEnum) {
        this.cheOperatingModeEnum = cheOperatingModeEnum;
    }

    public Boolean getCheHasMdt() {
        return this.cheHasMdt;
    }

    public void setCheHasMdt(Boolean cheHasMdt) {
        this.cheHasMdt = cheHasMdt;
    }

    public Long getCheMaximumHeight() {
        return this.cheMaximumHeight;
    }

    public void setCheMaximumHeight(Long cheMaximumHeight) {
        this.cheMaximumHeight = cheMaximumHeight;
    }

    public Long getCheIcon() {
        return this.cheIcon;
    }

    public void setCheIcon(Long cheIcon) {
        this.cheIcon = cheIcon;
    }

    public Long getCheId() {
        return this.cheId;
    }

    public void setCheId(Long cheId) {
        this.cheId = cheId;
    }

    public Long getCheScreenType() {
        return this.cheScreenType;
    }

    public void setCheScreenType(Long cheScreenType) {
        this.cheScreenType = cheScreenType;
    }

    public Long getCheScreenHorizontal() {
        return this.cheScreenHorizontal;
    }

    public void setCheScreenHorizontal(Long cheScreenHorizontal) {
        this.cheScreenHorizontal = cheScreenHorizontal;
    }

    public Long getCheScreenVertical() {
        return this.cheScreenVertical;
    }

    public void setCheScreenVertical(Long cheScreenVertical) {
        this.cheScreenVertical = cheScreenVertical;
    }

    public Long getCheInProgram() {
        return this.cheInProgram;
    }

    public void setCheInProgram(Long cheInProgram) {
        this.cheInProgram = cheInProgram;
    }

    public Long getCheNumFunctionKeys() {
        return this.cheNumFunctionKeys;
    }

    public void setCheNumFunctionKeys(Long cheNumFunctionKeys) {
        this.cheNumFunctionKeys = cheNumFunctionKeys;
    }

    public Long getCheJobStepState() {
        return this.cheJobStepState;
    }

    public void setCheJobStepState(Long cheJobStepState) {
        this.cheJobStepState = cheJobStepState;
    }

    public CheJobStepStateEnum getCheJobStepStateEnum() {
        return this.cheJobStepStateEnum;
    }

    public void setCheJobStepStateEnum(CheJobStepStateEnum cheJobStepStateEnum) {
        this.cheJobStepStateEnum = cheJobStepStateEnum;
    }

    public Date getCheJobStepCompleteTime() {
        return this.cheJobStepCompleteTime;
    }

    public void setCheJobStepCompleteTime(Date cheJobStepCompleteTime) {
        this.cheJobStepCompleteTime = cheJobStepCompleteTime;
    }

    public Date getCheLastJobstepTransition() {
        return this.cheLastJobstepTransition;
    }

    public void setCheLastJobstepTransition(Date cheLastJobstepTransition) {
        this.cheLastJobstepTransition = cheLastJobstepTransition;
    }

    public Long getCheCurrentlyToggledWiRef() {
        return this.cheCurrentlyToggledWiRef;
    }

    public void setCheCurrentlyToggledWiRef(Long cheCurrentlyToggledWiRef) {
        this.cheCurrentlyToggledWiRef = cheCurrentlyToggledWiRef;
    }

    public Date getCheAcceptJobDonePressTime() {
        return this.cheAcceptJobDonePressTime;
    }

    public void setCheAcceptJobDonePressTime(Date cheAcceptJobDonePressTime) {
        this.cheAcceptJobDonePressTime = cheAcceptJobDonePressTime;
    }

    public String getCheExtendedAddress() {
        return this.cheExtendedAddress;
    }

    public void setCheExtendedAddress(String cheExtendedAddress) {
        this.cheExtendedAddress = cheExtendedAddress;
    }

    public Long getCheMaximumTeu() {
        return this.cheMaximumTeu;
    }

    public void setCheMaximumTeu(Long cheMaximumTeu) {
        this.cheMaximumTeu = cheMaximumTeu;
    }

    public Boolean getCheIsInJobStepMode() {
        return this.cheIsInJobStepMode;
    }

    public void setCheIsInJobStepMode(Boolean cheIsInJobStepMode) {
        this.cheIsInJobStepMode = cheIsInJobStepMode;
    }

    public Long getCheClerkPowReference() {
        return this.cheClerkPowReference;
    }

    public void setCheClerkPowReference(Long cheClerkPowReference) {
        this.cheClerkPowReference = cheClerkPowReference;
    }

    public String getCheClerkVesselCode() {
        return this.cheClerkVesselCode;
    }

    public void setCheClerkVesselCode(String cheClerkVesselCode) {
        this.cheClerkVesselCode = cheClerkVesselCode;
    }

    public Long getCheClerkContainerKey() {
        return this.cheClerkContainerKey;
    }

    public void setCheClerkContainerKey(Long cheClerkContainerKey) {
        this.cheClerkContainerKey = cheClerkContainerKey;
    }

    public Long getCheClerkLastLandedCheId() {
        return this.cheClerkLastLandedCheId;
    }

    public void setCheClerkLastLandedCheId(Long cheClerkLastLandedCheId) {
        this.cheClerkLastLandedCheId = cheClerkLastLandedCheId;
    }

    public Long getCheClerkTeuLanded() {
        return this.cheClerkTeuLanded;
    }

    public void setCheClerkTeuLanded(Long cheClerkTeuLanded) {
        this.cheClerkTeuLanded = cheClerkTeuLanded;
    }

    public Long getCheTrailer() {
        return this.cheTrailer;
    }

    public void setCheTrailer(Long cheTrailer) {
        this.cheTrailer = cheTrailer;
    }

    public Long getCheAssistState() {
        return this.cheAssistState;
    }

    public void setCheAssistState(Long cheAssistState) {
        this.cheAssistState = cheAssistState;
    }

    public CheAssistStateEnum getCheAssistStateEnum() {
        return this.cheAssistStateEnum;
    }

    public void setCheAssistStateEnum(CheAssistStateEnum cheAssistStateEnum) {
        this.cheAssistStateEnum = cheAssistStateEnum;
    }

    public Boolean getCheScaleOn() {
        return this.cheScaleOn;
    }

    public void setCheScaleOn(Boolean cheScaleOn) {
        this.cheScaleOn = cheScaleOn;
    }

    public Boolean getCheInCommsFail() {
        return this.cheInCommsFail;
    }

    public void setCheInCommsFail(Boolean cheInCommsFail) {
        this.cheInCommsFail = cheInCommsFail;
    }

    public Boolean getCheChassisFetchReq() {
        return this.cheChassisFetchReq;
    }

    public void setCheChassisFetchReq(Boolean cheChassisFetchReq) {
        this.cheChassisFetchReq = cheChassisFetchReq;
    }

    public Boolean getCheNoLoginRequired() {
        return this.cheNoLoginRequired;
    }

    public void setCheNoLoginRequired(Boolean cheNoLoginRequired) {
        this.cheNoLoginRequired = cheNoLoginRequired;
    }

    public Boolean getCheAutomationActive() {
        return this.cheAutomationActive;
    }

    public void setCheAutomationActive(Boolean cheAutomationActive) {
        this.cheAutomationActive = cheAutomationActive;
    }

    public Boolean getCheDispatchRequested() {
        return this.cheDispatchRequested;
    }

    public void setCheDispatchRequested(Boolean cheDispatchRequested) {
        this.cheDispatchRequested = cheDispatchRequested;
    }

    public Boolean getCheManualDispatch() {
        return this.cheManualDispatch;
    }

    public void setCheManualDispatch(Boolean cheManualDispatch) {
        this.cheManualDispatch = cheManualDispatch;
    }

    public Boolean getCheSendsPdsWeight() {
        return this.cheSendsPdsWeight;
    }

    public void setCheSendsPdsWeight(Boolean cheSendsPdsWeight) {
        this.cheSendsPdsWeight = cheSendsPdsWeight;
    }

    public Boolean getCheSuspendAutoDispatch() {
        return this.cheSuspendAutoDispatch;
    }

    public void setCheSuspendAutoDispatch(Boolean cheSuspendAutoDispatch) {
        this.cheSuspendAutoDispatch = cheSuspendAutoDispatch;
    }

    public Boolean getCheTwinCarryCapable() {
        return this.cheTwinCarryCapable;
    }

    public void setCheTwinCarryCapable(Boolean cheTwinCarryCapable) {
        this.cheTwinCarryCapable = cheTwinCarryCapable;
    }

    public Boolean getCheManualDispatchPending() {
        return this.cheManualDispatchPending;
    }

    public void setCheManualDispatchPending(Boolean cheManualDispatchPending) {
        this.cheManualDispatchPending = cheManualDispatchPending;
    }

    public Boolean getCheHasOverheightGear() {
        return this.cheHasOverheightGear;
    }

    public void setCheHasOverheightGear(Boolean cheHasOverheightGear) {
        this.cheHasOverheightGear = cheHasOverheightGear;
    }

    public Boolean getCheFirstLiftTookPlace() {
        return this.cheFirstLiftTookPlace;
    }

    public void setCheFirstLiftTookPlace(Boolean cheFirstLiftTookPlace) {
        this.cheFirstLiftTookPlace = cheFirstLiftTookPlace;
    }

    public Boolean getCheTwinLiftCapable() {
        return this.cheTwinLiftCapable;
    }

    public void setCheTwinLiftCapable(Boolean cheTwinLiftCapable) {
        this.cheTwinLiftCapable = cheTwinLiftCapable;
    }

    public Boolean getCheMtsDamaged() {
        return this.cheMtsDamaged;
    }

    public void setCheMtsDamaged(Boolean cheMtsDamaged) {
        this.cheMtsDamaged = cheMtsDamaged;
    }

    public Boolean getCheUsesPds() {
        return this.cheUsesPds;
    }

    public void setCheUsesPds(Boolean cheUsesPds) {
        this.cheUsesPds = cheUsesPds;
    }

    public Boolean getCheConfigurableTrailer() {
        return this.cheConfigurableTrailer;
    }

    public void setCheConfigurableTrailer(Boolean cheConfigurableTrailer) {
        this.cheConfigurableTrailer = cheConfigurableTrailer;
    }

    public Boolean getCheNominalLength20Capable() {
        return this.cheNominalLength20Capable;
    }

    public void setCheNominalLength20Capable(Boolean cheNominalLength20Capable) {
        this.cheNominalLength20Capable = cheNominalLength20Capable;
    }

    public Boolean getCheNominalLength40Capable() {
        return this.cheNominalLength40Capable;
    }

    public void setCheNominalLength40Capable(Boolean cheNominalLength40Capable) {
        this.cheNominalLength40Capable = cheNominalLength40Capable;
    }

    public Boolean getCheNominalLength45Capable() {
        return this.cheNominalLength45Capable;
    }

    public void setCheNominalLength45Capable(Boolean cheNominalLength45Capable) {
        this.cheNominalLength45Capable = cheNominalLength45Capable;
    }

    public Boolean getCheNominalLength24Capable() {
        return this.cheNominalLength24Capable;
    }

    public void setCheNominalLength24Capable(Boolean cheNominalLength24Capable) {
        this.cheNominalLength24Capable = cheNominalLength24Capable;
    }

    public Boolean getCheNominalLength48Capable() {
        return this.cheNominalLength48Capable;
    }

    public void setCheNominalLength48Capable(Boolean cheNominalLength48Capable) {
        this.cheNominalLength48Capable = cheNominalLength48Capable;
    }

    public Boolean getCheNominalLength53Capable() {
        return this.cheNominalLength53Capable;
    }

    public void setCheNominalLength53Capable(Boolean cheNominalLength53Capable) {
        this.cheNominalLength53Capable = cheNominalLength53Capable;
    }

    public Boolean getCheNominalLength30Capable() {
        return this.cheNominalLength30Capable;
    }

    public void setCheNominalLength30Capable(Boolean cheNominalLength30Capable) {
        this.cheNominalLength30Capable = cheNominalLength30Capable;
    }

    public Boolean getCheNominalLength60Capable() {
        return this.cheNominalLength60Capable;
    }

    public void setCheNominalLength60Capable(Boolean cheNominalLength60Capable) {
        this.cheNominalLength60Capable = cheNominalLength60Capable;
    }

    public String getCheAutoCheTechnicalStatus() {
        return this.cheAutoCheTechnicalStatus;
    }

    public void setCheAutoCheTechnicalStatus(String cheAutoCheTechnicalStatus) {
        this.cheAutoCheTechnicalStatus = cheAutoCheTechnicalStatus;
    }

    public String getCheAutoCheOperationalStatus() {
        return this.cheAutoCheOperationalStatus;
    }

    public void setCheAutoCheOperationalStatus(String cheAutoCheOperationalStatus) {
        this.cheAutoCheOperationalStatus = cheAutoCheOperationalStatus;
    }

    public String getCheAutoCheWorkStatus() {
        return this.cheAutoCheWorkStatus;
    }

    public void setCheAutoCheWorkStatus(String cheAutoCheWorkStatus) {
        this.cheAutoCheWorkStatus = cheAutoCheWorkStatus;
    }

    public Long getCheTwinDiffWgtAllowance() {
        return this.cheTwinDiffWgtAllowance;
    }

    public void setCheTwinDiffWgtAllowance(Long cheTwinDiffWgtAllowance) {
        this.cheTwinDiffWgtAllowance = cheTwinDiffWgtAllowance;
    }

    public Long getCheScaleWeightUnit() {
        return this.cheScaleWeightUnit;
    }

    public void setCheScaleWeightUnit(Long cheScaleWeightUnit) {
        this.cheScaleWeightUnit = cheScaleWeightUnit;
    }

    public Float getRelativeQueuePositionForQC() {
        return this.relativeQueuePositionForQC;
    }

    public void setRelativeQueuePositionForQC(Float relativeQueuePositionForQC) {
        this.relativeQueuePositionForQC = relativeQueuePositionForQC;
    }

    public Boolean getWaitingForTruckInsert() {
        return this.waitingForTruckInsert;
    }

    public void setWaitingForTruckInsert(Boolean waitingForTruckInsert) {
        this.waitingForTruckInsert = waitingForTruckInsert;
    }

    public String getCheAttachedChassisId() {
        return this.cheAttachedChassisId;
    }

    public void setCheAttachedChassisId(String cheAttachedChassisId) {
        this.cheAttachedChassisId = cheAttachedChassisId;
    }

    public Boolean getCheTandemLiftCapable() {
        return this.cheTandemLiftCapable;
    }

    public void setCheTandemLiftCapable(Boolean cheTandemLiftCapable) {
        this.cheTandemLiftCapable = cheTandemLiftCapable;
    }

    public Long getCheMaxTandemWeight() {
        return this.cheMaxTandemWeight;
    }

    public void setCheMaxTandemWeight(Long cheMaxTandemWeight) {
        this.cheMaxTandemWeight = cheMaxTandemWeight;
    }

    public Integer getCheProximityRadius() {
        return this.cheProximityRadius;
    }

    public void setCheProximityRadius(Integer cheProximityRadius) {
        this.cheProximityRadius = cheProximityRadius;
    }

    public Boolean getCheQuadLiftCapable() {
        return this.cheQuadLiftCapable;
    }

    public void setCheQuadLiftCapable(Boolean cheQuadLiftCapable) {
        this.cheQuadLiftCapable = cheQuadLiftCapable;
    }

    public Long getCheMaxQuadWeight() {
        return this.cheMaxQuadWeight;
    }

    public void setCheMaxQuadWeight(Long cheMaxQuadWeight) {
        this.cheMaxQuadWeight = cheMaxQuadWeight;
    }

    public String getCheDispatchInfo() {
        return this.cheDispatchInfo;
    }

    public void setCheDispatchInfo(String cheDispatchInfo) {
        this.cheDispatchInfo = cheDispatchInfo;
    }

    public Long getCheLane() {
        return this.cheLane;
    }

    public void setCheLane(Long cheLane) {
        this.cheLane = cheLane;
    }

    public Long getHasTrailer() {
        return this.hasTrailer;
    }

    public void setHasTrailer(Long hasTrailer) {
        this.hasTrailer = hasTrailer;
    }

    public Long getCheWorkLoad() {
        return this.cheWorkLoad;
    }

    public void setCheWorkLoad(Long cheWorkLoad) {
        this.cheWorkLoad = cheWorkLoad;
    }

    public Long getCheAutoCheRunningHours() {
        return this.cheAutoCheRunningHours;
    }

    public void setCheAutoCheRunningHours(Long cheAutoCheRunningHours) {
        this.cheAutoCheRunningHours = cheAutoCheRunningHours;
    }

    public Long getCheEnergyLevel() {
        return this.cheEnergyLevel;
    }

    public void setCheEnergyLevel(Long cheEnergyLevel) {
        this.cheEnergyLevel = cheEnergyLevel;
    }

    public BatteryStateEnum getCheEnergyStateEnum() {
        return this.cheEnergyStateEnum;
    }

    public void setCheEnergyStateEnum(BatteryStateEnum cheEnergyStateEnum) {
        this.cheEnergyStateEnum = cheEnergyStateEnum;
    }

    public Long getCheLiftCapacity() {
        return this.cheLiftCapacity;
    }

    public void setCheLiftCapacity(Long cheLiftCapacity) {
        this.cheLiftCapacity = cheLiftCapacity;
    }

    public Double getCheMaximumWeightInKg() {
        return this.cheMaximumWeightInKg;
    }

    public void setCheMaximumWeightInKg(Double cheMaximumWeightInKg) {
        this.cheMaximumWeightInKg = cheMaximumWeightInKg;
    }

    public Double getCheMaxQuadWeightInKg() {
        return this.cheMaxQuadWeightInKg;
    }

    public void setCheMaxQuadWeightInKg(Double cheMaxQuadWeightInKg) {
        this.cheMaxQuadWeightInKg = cheMaxQuadWeightInKg;
    }

    public Double getCheMaxTandemWeightInKg() {
        return this.cheMaxTandemWeightInKg;
    }

    public void setCheMaxTandemWeightInKg(Double cheMaxTandemWeightInKg) {
        this.cheMaxTandemWeightInKg = cheMaxTandemWeightInKg;
    }

    public Double getCheLiftCapacityInKg() {
        return this.cheLiftCapacityInKg;
    }

    public void setCheLiftCapacityInKg(Double cheLiftCapacityInKg) {
        this.cheLiftCapacityInKg = cheLiftCapacityInKg;
    }

    public CheLiftOperationalStatusEnum getCheLiftOperationalStatus() {
        return this.cheLiftOperationalStatus;
    }

    public void setCheLiftOperationalStatus(CheLiftOperationalStatusEnum cheLiftOperationalStatus) {
        this.cheLiftOperationalStatus = cheLiftOperationalStatus;
    }

    public Double getCheTwinDiffWgtAllowanceInKg() {
        return this.cheTwinDiffWgtAllowanceInKg;
    }

    public void setCheTwinDiffWgtAllowanceInKg(Double cheTwinDiffWgtAllowanceInKg) {
        this.cheTwinDiffWgtAllowanceInKg = cheTwinDiffWgtAllowanceInKg;
    }

    public Boolean getCheIsOcrDataBeingAccepted() {
        return this.cheIsOcrDataBeingAccepted;
    }

    public void setCheIsOcrDataBeingAccepted(Boolean cheIsOcrDataBeingAccepted) {
        this.cheIsOcrDataBeingAccepted = cheIsOcrDataBeingAccepted;
    }

    public String getCheECStateFlexString1() {
        return this.cheECStateFlexString1;
    }

    public void setCheECStateFlexString1(String cheECStateFlexString1) {
        this.cheECStateFlexString1 = cheECStateFlexString1;
    }

    public String getCheECStateFlexString2() {
        return this.cheECStateFlexString2;
    }

    public void setCheECStateFlexString2(String cheECStateFlexString2) {
        this.cheECStateFlexString2 = cheECStateFlexString2;
    }

    public String getCheECStateFlexString3() {
        return this.cheECStateFlexString3;
    }

    public void setCheECStateFlexString3(String cheECStateFlexString3) {
        this.cheECStateFlexString3 = cheECStateFlexString3;
    }

    public Long getCheAllowedChassisKinds() {
        return this.cheAllowedChassisKinds;
    }

    public void setCheAllowedChassisKinds(Long cheAllowedChassisKinds) {
        this.cheAllowedChassisKinds = cheAllowedChassisKinds;
    }

    public Boolean getCheIsCharging() {
        return this.cheIsCharging;
    }

    public void setCheIsCharging(Boolean cheIsCharging) {
        this.cheIsCharging = cheIsCharging;
    }

    public Boolean getCheHasActiveAlarm() {
        return this.cheHasActiveAlarm;
    }

    public void setCheHasActiveAlarm(Boolean cheHasActiveAlarm) {
        this.cheHasActiveAlarm = cheHasActiveAlarm;
    }

    public Yard getCheYard() {
        return this.cheYard;
    }

    public void setCheYard(Yard cheYard) {
        this.cheYard = cheYard;
    }

    public ChePool getChePool() {
        return this.chePool;
    }

    public void setChePool(ChePool chePool) {
        this.chePool = chePool;
    }

    public PointOfWork getChePointOfWork() {
        return this.chePointOfWork;
    }

    public void setChePointOfWork(PointOfWork chePointOfWork) {
        this.chePointOfWork = chePointOfWork;
    }

    public Che getCheAssignedChe() {
        return this.cheAssignedChe;
    }

    public void setCheAssignedChe(Che cheAssignedChe) {
        this.cheAssignedChe = cheAssignedChe;
    }

    public Che getCheClerkLastLandedChe() {
        return this.cheClerkLastLandedChe;
    }

    public void setCheClerkLastLandedChe(Che cheClerkLastLandedChe) {
        this.cheClerkLastLandedChe = cheClerkLastLandedChe;
    }

    public List getCheAlarms() {
        return this.cheAlarms;
    }

    public void setCheAlarms(List cheAlarms) {
        this.cheAlarms = cheAlarms;
    }

    public LocPosition getCheLastKnownLocPos() {
        return this.cheLastKnownLocPos;
    }

    public void setCheLastKnownLocPos(LocPosition cheLastKnownLocPos) {
        this.cheLastKnownLocPos = cheLastKnownLocPos;
    }

}
