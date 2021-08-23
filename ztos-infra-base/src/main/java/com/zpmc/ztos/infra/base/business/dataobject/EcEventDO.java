package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Yard;

import java.io.Serializable;
import java.util.Date;

public class EcEventDO extends DatabaseEntity implements Serializable {
    private Long eceventGkey;
    private Long eceventPkey;
    private Date eceventTimestamp;
    private Long eceventType;
    private Long eceventCheId;
    private String eceventCheName;
    private String eceventOperatorName;
    private Long eceventSubType;
    private String eceventTypeDescription;
    private Long eceventFromCheIdName;
    private Long eceventToCheIdName;
    private String eceventUnitIdName;
    private String eceventPowName;
    private String eceventPoolName;
    private String eceventWorkQueue;
    private Long eceventTravelDistance;
    private String eceventMoveKind;
    private Boolean eceventIsTwinMove;
    private Long eceventStartDistance;
    private Long eceventWorkAssignmentGkey;
    private String eceventWorkAssignmentId;
    private String eceventUnitReference;
    private String eceventTransactionId;
    private LocTypeEnum eceventLocType;
    private String eceventLocId;
    private String eceventLocSlot;
    private String eceventPosOperationalPosId;
    private LocTypeEnum eceventUnladenLocType;
    private String eceventUnladenLocId;
    private String eceventUnladenLocSlot;
    private LocTypeEnum eceventLadenLocType;
    private String eceventLadenLocId;
    private String eceventLadenLocSlot;
    private Date eceventLastEstMoveTime;
    private Yard eceventYard;

    public Serializable getPrimaryKey() {
        return this.getEceventGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getEceventGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof EcEventDO)) {
            return false;
        }
        EcEventDO that = (EcEventDO)other;
        return ((Object)id).equals(that.getEceventGkey());
    }

    public int hashCode() {
        Long id = this.getEceventGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getEceventGkey() {
        return this.eceventGkey;
    }

    public void setEceventGkey(Long eceventGkey) {
        this.eceventGkey = eceventGkey;
    }

    public Long getEceventPkey() {
        return this.eceventPkey;
    }

    public void setEceventPkey(Long eceventPkey) {
        this.eceventPkey = eceventPkey;
    }

    public Date getEceventTimestamp() {
        return this.eceventTimestamp;
    }

    public void setEceventTimestamp(Date eceventTimestamp) {
        this.eceventTimestamp = eceventTimestamp;
    }

    public Long getEceventType() {
        return this.eceventType;
    }

    public void setEceventType(Long eceventType) {
        this.eceventType = eceventType;
    }

    public Long getEceventCheId() {
        return this.eceventCheId;
    }

    public void setEceventCheId(Long eceventCheId) {
        this.eceventCheId = eceventCheId;
    }

    public String getEceventCheName() {
        return this.eceventCheName;
    }

    public void setEceventCheName(String eceventCheName) {
        this.eceventCheName = eceventCheName;
    }

    public String getEceventOperatorName() {
        return this.eceventOperatorName;
    }

    public void setEceventOperatorName(String eceventOperatorName) {
        this.eceventOperatorName = eceventOperatorName;
    }

    public Long getEceventSubType() {
        return this.eceventSubType;
    }

    public void setEceventSubType(Long eceventSubType) {
        this.eceventSubType = eceventSubType;
    }

    public String getEceventTypeDescription() {
        return this.eceventTypeDescription;
    }

    public void setEceventTypeDescription(String eceventTypeDescription) {
        this.eceventTypeDescription = eceventTypeDescription;
    }

    public Long getEceventFromCheIdName() {
        return this.eceventFromCheIdName;
    }

    public void setEceventFromCheIdName(Long eceventFromCheIdName) {
        this.eceventFromCheIdName = eceventFromCheIdName;
    }

    public Long getEceventToCheIdName() {
        return this.eceventToCheIdName;
    }

    public void setEceventToCheIdName(Long eceventToCheIdName) {
        this.eceventToCheIdName = eceventToCheIdName;
    }

    public String getEceventUnitIdName() {
        return this.eceventUnitIdName;
    }

    public void setEceventUnitIdName(String eceventUnitIdName) {
        this.eceventUnitIdName = eceventUnitIdName;
    }

    public String getEceventPowName() {
        return this.eceventPowName;
    }

    public void setEceventPowName(String eceventPowName) {
        this.eceventPowName = eceventPowName;
    }

    public String getEceventPoolName() {
        return this.eceventPoolName;
    }

    public void setEceventPoolName(String eceventPoolName) {
        this.eceventPoolName = eceventPoolName;
    }

    public String getEceventWorkQueue() {
        return this.eceventWorkQueue;
    }

    public void setEceventWorkQueue(String eceventWorkQueue) {
        this.eceventWorkQueue = eceventWorkQueue;
    }

    public Long getEceventTravelDistance() {
        return this.eceventTravelDistance;
    }

    public void setEceventTravelDistance(Long eceventTravelDistance) {
        this.eceventTravelDistance = eceventTravelDistance;
    }

    public String getEceventMoveKind() {
        return this.eceventMoveKind;
    }

    public void setEceventMoveKind(String eceventMoveKind) {
        this.eceventMoveKind = eceventMoveKind;
    }

    public Boolean getEceventIsTwinMove() {
        return this.eceventIsTwinMove;
    }

    public void setEceventIsTwinMove(Boolean eceventIsTwinMove) {
        this.eceventIsTwinMove = eceventIsTwinMove;
    }

    public Long getEceventStartDistance() {
        return this.eceventStartDistance;
    }

    public void setEceventStartDistance(Long eceventStartDistance) {
        this.eceventStartDistance = eceventStartDistance;
    }

    public Long getEceventWorkAssignmentGkey() {
        return this.eceventWorkAssignmentGkey;
    }

    public void setEceventWorkAssignmentGkey(Long eceventWorkAssignmentGkey) {
        this.eceventWorkAssignmentGkey = eceventWorkAssignmentGkey;
    }

    public String getEceventWorkAssignmentId() {
        return this.eceventWorkAssignmentId;
    }

    public void setEceventWorkAssignmentId(String eceventWorkAssignmentId) {
        this.eceventWorkAssignmentId = eceventWorkAssignmentId;
    }

    public String getEceventUnitReference() {
        return this.eceventUnitReference;
    }

    public void setEceventUnitReference(String eceventUnitReference) {
        this.eceventUnitReference = eceventUnitReference;
    }

    public String getEceventTransactionId() {
        return this.eceventTransactionId;
    }

    public void setEceventTransactionId(String eceventTransactionId) {
        this.eceventTransactionId = eceventTransactionId;
    }

    public LocTypeEnum getEceventLocType() {
        return this.eceventLocType;
    }

    public void setEceventLocType(LocTypeEnum eceventLocType) {
        this.eceventLocType = eceventLocType;
    }

    public String getEceventLocId() {
        return this.eceventLocId;
    }

    public void setEceventLocId(String eceventLocId) {
        this.eceventLocId = eceventLocId;
    }

    public String getEceventLocSlot() {
        return this.eceventLocSlot;
    }

    public void setEceventLocSlot(String eceventLocSlot) {
        this.eceventLocSlot = eceventLocSlot;
    }

    public String getEceventPosOperationalPosId() {
        return this.eceventPosOperationalPosId;
    }

    public void setEceventPosOperationalPosId(String eceventPosOperationalPosId) {
        this.eceventPosOperationalPosId = eceventPosOperationalPosId;
    }

    public LocTypeEnum getEceventUnladenLocType() {
        return this.eceventUnladenLocType;
    }

    public void setEceventUnladenLocType(LocTypeEnum eceventUnladenLocType) {
        this.eceventUnladenLocType = eceventUnladenLocType;
    }

    public String getEceventUnladenLocId() {
        return this.eceventUnladenLocId;
    }

    public void setEceventUnladenLocId(String eceventUnladenLocId) {
        this.eceventUnladenLocId = eceventUnladenLocId;
    }

    public String getEceventUnladenLocSlot() {
        return this.eceventUnladenLocSlot;
    }

    public void setEceventUnladenLocSlot(String eceventUnladenLocSlot) {
        this.eceventUnladenLocSlot = eceventUnladenLocSlot;
    }

    public LocTypeEnum getEceventLadenLocType() {
        return this.eceventLadenLocType;
    }

    public void setEceventLadenLocType(LocTypeEnum eceventLadenLocType) {
        this.eceventLadenLocType = eceventLadenLocType;
    }

    public String getEceventLadenLocId() {
        return this.eceventLadenLocId;
    }

    public void setEceventLadenLocId(String eceventLadenLocId) {
        this.eceventLadenLocId = eceventLadenLocId;
    }

    public String getEceventLadenLocSlot() {
        return this.eceventLadenLocSlot;
    }

    public void setEceventLadenLocSlot(String eceventLadenLocSlot) {
        this.eceventLadenLocSlot = eceventLadenLocSlot;
    }

    public Date getEceventLastEstMoveTime() {
        return this.eceventLastEstMoveTime;
    }

    public void setEceventLastEstMoveTime(Date eceventLastEstMoveTime) {
        this.eceventLastEstMoveTime = eceventLastEstMoveTime;
    }

    public Yard getEceventYard() {
        return this.eceventYard;
    }

    public void setEceventYard(Yard eceventYard) {
        this.eceventYard = eceventYard;
    }

}
