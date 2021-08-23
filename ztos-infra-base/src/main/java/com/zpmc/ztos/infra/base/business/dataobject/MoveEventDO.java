package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.FreightKindEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.UnitCategoryEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveKindEnum;
import com.zpmc.ztos.infra.base.business.equipments.Che;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.events.Event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MoveEventDO extends Event implements Serializable {
    private WiMoveKindEnum mveMoveKind;
    private Boolean mveExclude;
    private Long mveDistToStart;
    private Long mveDistOfCarry;
    private Date mveTimeCarryComplete;
    private Date mveTimeDispatch;
    private Date mveTimeFetch;
    private Date mveTimeDischarge;
    private Date mveTimePut;
    private Date mveTimeCarryCheFetchReady;
    private Date mveTimeCarryChePutReady;
    private Date mveTimeCarryCheDispatch;
    private Date mveTZArrivalTime;
    private Long mveRehandleCount;
    private Boolean mveTwinFetch;
    private Boolean mveTwinCarry;
    private Boolean mveTwinPut;
    private String mveRestowAccount;
    private String mveServiceOrder;
    private String mveRestowReason;
    private Boolean mveProcessed;
    private String mvePOW;
    private String mveCheCarryLoginName;
    private String mveChePutLoginName;
    private String mveCheFetchLoginName;
    private String mveBerth;
    private UnitCategoryEnum mveCategory;
    private FreightKindEnum mveFreightKind;
    private UnitFacilityVisit mveUfv;
    private ScopedBizUnit mveLineSnapshot;
    private CarrierVisit mveCarrier;
    private Che mveCheFetch;
    private Che mveCheCarry;
    private Che mveChePut;
    private Che mveCheQuayCrane;
    private List cheMoves;
    private LocPosition mveFromPosition;
    private LocPosition mveToPosition;
    private Map mveCustomFlexFields;

    public WiMoveKindEnum getMveMoveKind() {
        return this.mveMoveKind;
    }

    protected void setMveMoveKind(WiMoveKindEnum mveMoveKind) {
        this.mveMoveKind = mveMoveKind;
    }

    public Boolean getMveExclude() {
        return this.mveExclude;
    }

    protected void setMveExclude(Boolean mveExclude) {
        this.mveExclude = mveExclude;
    }

    public Long getMveDistToStart() {
        return this.mveDistToStart;
    }

    protected void setMveDistToStart(Long mveDistToStart) {
        this.mveDistToStart = mveDistToStart;
    }

    public Long getMveDistOfCarry() {
        return this.mveDistOfCarry;
    }

    protected void setMveDistOfCarry(Long mveDistOfCarry) {
        this.mveDistOfCarry = mveDistOfCarry;
    }

    public Date getMveTimeCarryComplete() {
        return this.mveTimeCarryComplete;
    }

    protected void setMveTimeCarryComplete(Date mveTimeCarryComplete) {
        this.mveTimeCarryComplete = mveTimeCarryComplete;
    }

    public Date getMveTimeDispatch() {
        return this.mveTimeDispatch;
    }

    protected void setMveTimeDispatch(Date mveTimeDispatch) {
        this.mveTimeDispatch = mveTimeDispatch;
    }

    public Date getMveTimeFetch() {
        return this.mveTimeFetch;
    }

    protected void setMveTimeFetch(Date mveTimeFetch) {
        this.mveTimeFetch = mveTimeFetch;
    }

    public Date getMveTimeDischarge() {
        return this.mveTimeDischarge;
    }

    protected void setMveTimeDischarge(Date mveTimeDischarge) {
        this.mveTimeDischarge = mveTimeDischarge;
    }

    public Date getMveTimePut() {
        return this.mveTimePut;
    }

    protected void setMveTimePut(Date mveTimePut) {
        this.mveTimePut = mveTimePut;
    }

    public Date getMveTimeCarryCheFetchReady() {
        return this.mveTimeCarryCheFetchReady;
    }

    protected void setMveTimeCarryCheFetchReady(Date mveTimeCarryCheFetchReady) {
        this.mveTimeCarryCheFetchReady = mveTimeCarryCheFetchReady;
    }

    public Date getMveTimeCarryChePutReady() {
        return this.mveTimeCarryChePutReady;
    }

    protected void setMveTimeCarryChePutReady(Date mveTimeCarryChePutReady) {
        this.mveTimeCarryChePutReady = mveTimeCarryChePutReady;
    }

    public Date getMveTimeCarryCheDispatch() {
        return this.mveTimeCarryCheDispatch;
    }

    protected void setMveTimeCarryCheDispatch(Date mveTimeCarryCheDispatch) {
        this.mveTimeCarryCheDispatch = mveTimeCarryCheDispatch;
    }

    public Date getMveTZArrivalTime() {
        return this.mveTZArrivalTime;
    }

    protected void setMveTZArrivalTime(Date mveTZArrivalTime) {
        this.mveTZArrivalTime = mveTZArrivalTime;
    }

    public Long getMveRehandleCount() {
        return this.mveRehandleCount;
    }

    protected void setMveRehandleCount(Long mveRehandleCount) {
        this.mveRehandleCount = mveRehandleCount;
    }

    public Boolean getMveTwinFetch() {
        return this.mveTwinFetch;
    }

    protected void setMveTwinFetch(Boolean mveTwinFetch) {
        this.mveTwinFetch = mveTwinFetch;
    }

    public Boolean getMveTwinCarry() {
        return this.mveTwinCarry;
    }

    protected void setMveTwinCarry(Boolean mveTwinCarry) {
        this.mveTwinCarry = mveTwinCarry;
    }

    public Boolean getMveTwinPut() {
        return this.mveTwinPut;
    }

    protected void setMveTwinPut(Boolean mveTwinPut) {
        this.mveTwinPut = mveTwinPut;
    }

    public String getMveRestowAccount() {
        return this.mveRestowAccount;
    }

    protected void setMveRestowAccount(String mveRestowAccount) {
        this.mveRestowAccount = mveRestowAccount;
    }

    public String getMveServiceOrder() {
        return this.mveServiceOrder;
    }

    protected void setMveServiceOrder(String mveServiceOrder) {
        this.mveServiceOrder = mveServiceOrder;
    }

    public String getMveRestowReason() {
        return this.mveRestowReason;
    }

    protected void setMveRestowReason(String mveRestowReason) {
        this.mveRestowReason = mveRestowReason;
    }

    public Boolean getMveProcessed() {
        return this.mveProcessed;
    }

    protected void setMveProcessed(Boolean mveProcessed) {
        this.mveProcessed = mveProcessed;
    }

    public String getMvePOW() {
        return this.mvePOW;
    }

    protected void setMvePOW(String mvePOW) {
        this.mvePOW = mvePOW;
    }

    public String getMveCheCarryLoginName() {
        return this.mveCheCarryLoginName;
    }

    protected void setMveCheCarryLoginName(String mveCheCarryLoginName) {
        this.mveCheCarryLoginName = mveCheCarryLoginName;
    }

    public String getMveChePutLoginName() {
        return this.mveChePutLoginName;
    }

    protected void setMveChePutLoginName(String mveChePutLoginName) {
        this.mveChePutLoginName = mveChePutLoginName;
    }

    public String getMveCheFetchLoginName() {
        return this.mveCheFetchLoginName;
    }

    protected void setMveCheFetchLoginName(String mveCheFetchLoginName) {
        this.mveCheFetchLoginName = mveCheFetchLoginName;
    }

    public String getMveBerth() {
        return this.mveBerth;
    }

    protected void setMveBerth(String mveBerth) {
        this.mveBerth = mveBerth;
    }

    public UnitCategoryEnum getMveCategory() {
        return this.mveCategory;
    }

    protected void setMveCategory(UnitCategoryEnum mveCategory) {
        this.mveCategory = mveCategory;
    }

    public FreightKindEnum getMveFreightKind() {
        return this.mveFreightKind;
    }

    protected void setMveFreightKind(FreightKindEnum mveFreightKind) {
        this.mveFreightKind = mveFreightKind;
    }

    public UnitFacilityVisit getMveUfv() {
        return this.mveUfv;
    }

    protected void setMveUfv(UnitFacilityVisit mveUfv) {
        this.mveUfv = mveUfv;
    }

    public ScopedBizUnit getMveLineSnapshot() {
        return this.mveLineSnapshot;
    }

    protected void setMveLineSnapshot(ScopedBizUnit mveLineSnapshot) {
        this.mveLineSnapshot = mveLineSnapshot;
    }

    public CarrierVisit getMveCarrier() {
        return this.mveCarrier;
    }

    protected void setMveCarrier(CarrierVisit mveCarrier) {
        this.mveCarrier = mveCarrier;
    }

    public Che getMveCheFetch() {
        return this.mveCheFetch;
    }

    protected void setMveCheFetch(Che mveCheFetch) {
        this.mveCheFetch = mveCheFetch;
    }

    public Che getMveCheCarry() {
        return this.mveCheCarry;
    }

    protected void setMveCheCarry(Che mveCheCarry) {
        this.mveCheCarry = mveCheCarry;
    }

    public Che getMveChePut() {
        return this.mveChePut;
    }

    protected void setMveChePut(Che mveChePut) {
        this.mveChePut = mveChePut;
    }

    public Che getMveCheQuayCrane() {
        return this.mveCheQuayCrane;
    }

    protected void setMveCheQuayCrane(Che mveCheQuayCrane) {
        this.mveCheQuayCrane = mveCheQuayCrane;
    }

    public List getCheMoves() {
        return this.cheMoves;
    }

    protected void setCheMoves(List cheMoves) {
        this.cheMoves = cheMoves;
    }

    public LocPosition getMveFromPosition() {
        return this.mveFromPosition;
    }

    protected void setMveFromPosition(LocPosition mveFromPosition) {
        this.mveFromPosition = mveFromPosition;
    }

    public LocPosition getMveToPosition() {
        return this.mveToPosition;
    }

    protected void setMveToPosition(LocPosition mveToPosition) {
        this.mveToPosition = mveToPosition;
    }

    public Map getMveCustomFlexFields() {
        return this.mveCustomFlexFields;
    }

    protected void setMveCustomFlexFields(Map mveCustomFlexFields) {
        this.mveCustomFlexFields = mveCustomFlexFields;
    }

}
