package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * 需要收费的海事事件对象
 * @author yejun
 */
@Data
public class ChargeableMarineEventDO extends DatabaseEntity implements Serializable {

    private Long bexmGkey;
    private Long bexmBatchId;
    private String bexmEventTypeId;
    private Long bexmEventGkey;
    private Double bexmQuantity;
    private String bexmQuantityUnit;
    private String bexmFacilityId;
    private String bexmComplexId;
    private String bexmOperatorId;
    private Long bexmVvdGkey;
    private String bexmVvId;
    private Date bexmVvATA;
    private Date bexmVvATD;
    private Date bexmVvETA;
    private Date bexmVvETD;
    private Date bexmVvTimeStartWork;
    private Date bexmVvTimeEndWork;
    private String bexmIbVoyNbr;
    private String bexmIbCallNbr;
    private String bexmObVoyNbr;
    private String bexmObCallNbr;
    private String bexmServiceId;
    private String bexmVslId;
    private String bexmVslName;
    private String bexmVslTypeId;
    private String bexmVslClassId;
    private Long bexmVslLoaCm;
    private Long bexmVslBeamCm;
    private Double bexmVslNetRevenueTons;
    private Double bexmVslGrossRevenueTons;
    private Boolean bexmVslIsSelfSustaining;
    private String bexmVslOperatorId;
    private String bexmVslOperatorName;
    private Date bexmPerformed;
    private Date bexmInboundFirstFreeDay;
    private Date bexmTimeDischargeComplete;
    private Boolean bexmIsLocked;
    private String bexmStatus;
    private String bexmLastDraftInvNbr;
    private String bexmPayeeCustomerId;
    private BizRoleEnum bexmPayeeRole;
    private String bexmServiceOrder;
    private String bexmNotes;
    private String bexmFlexString01;
    private String bexmFlexString02;
    private String bexmFlexString03;
    private String bexmFlexString04;
    private String bexmFlexString05;
    private String bexmFlexString06;
    private String bexmFlexString07;
    private String bexmFlexString08;
    private String bexmFlexString09;
    private String bexmFlexString10;
    private String bexmFlexString11;
    private String bexmFlexString12;
    private String bexmFlexString13;
    private String bexmFlexString14;
    private String bexmFlexString15;
    private String bexmFlexString16;
    private String bexmFlexString17;
    private String bexmFlexString18;
    private String bexmFlexString19;
    private String bexmFlexString20;
    private String bexmFlexString21;
    private String bexmFlexString22;
    private String bexmFlexString23;
    private String bexmFlexString24;
    private String bexmFlexString25;
    private Date bexmFlexDate01;
    private Date bexmFlexDate02;
    private Date bexmFlexDate03;
    private Date bexmFlexDate04;
    private Date bexmFlexDate05;
    private Long bexmFlexLong01;
    private Long bexmFlexLong02;
    private Long bexmFlexLong03;
    private Long bexmFlexLong04;
    private Long bexmFlexLong05;
    private Double bexmFlexDouble01;
    private Double bexmFlexDouble02;
    private Double bexmFlexDouble03;
    private Double bexmFlexDouble04;
    private Double bexmFlexDouble05;
    private Date bexmCreated;
    private String bexmCreator;
    private Date bexmChanged;
    private String bexmChanger;
    private Facility bexmFacility;

    @Override
    public Serializable getPrimaryKey() {
        return this.getBexmGkey();
    }

    public Long getBexmGkey() {
        return this.bexmGkey;
    }



}
