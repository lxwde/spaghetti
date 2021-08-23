package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BookingRollCutoffEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.BookingRollEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.BookingUsageEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.EntityMappingPredicate;

import java.io.Serializable;
import java.util.Date;

public class LineOperatorDO extends ScopedBizUnit implements Serializable {
    private String lineopDOTHazCarrierCertNbr;
    private Boolean lineopBkgUnique;
    private Boolean lineopStopBkgUpdates;
    private Boolean lineopStopRtgUpdates;
    private Boolean lineopBlUnique;
    private BookingUsageEnum lineopBkgUsage;
    private BookingRollEnum lineopBkgRoll;
    private BookingRollCutoffEnum lineopBkgRollCutoff;
    private Boolean lineopBkgAdhoc;
    private Boolean lineopBkgIsNotValidated;
    private Boolean lineopRollLateCtr;
    private Boolean lineopGenPinNbr;
    private Boolean lineopUsePinNbr;
    private Boolean lineopOrderItemNotUnique;
    private Boolean lineopIsOrderNbrUnique;
    private Boolean lineopIsEDOUniqueByFacility;
    private Date lineopReeferTimeMonitor1;
    private Date lineopReeferTimeMonitor2;
    private Date lineopReeferTimeMonitor3;
    private Date lineopReeferTimeMonitor4;
    private Integer lineopReeferOffPowerTimeLimit;
    private Boolean lineopPropagateReeferChanges;
    private String lineopFlexString01;
    private String lineopFlexString02;
    private String lineopFlexString03;
    private String lineopFlexString04;
    private String lineopFlexString05;
    private String lineopFlexString06;
    private String lineopFlexString07;
    private String lineopFlexString08;
    private String lineopFlexString09;
    private String lineopFlexString10;
    private EntityMappingPredicate lineopDemurrageRules;
    private EntityMappingPredicate lineopPowerRules;
    private EntityMappingPredicate lineopLineDemurrageRules;

    public String getLineopDOTHazCarrierCertNbr() {
        return this.lineopDOTHazCarrierCertNbr;
    }

    protected void setLineopDOTHazCarrierCertNbr(String lineopDOTHazCarrierCertNbr) {
        this.lineopDOTHazCarrierCertNbr = lineopDOTHazCarrierCertNbr;
    }

    public Boolean getLineopBkgUnique() {
        return this.lineopBkgUnique;
    }

    protected void setLineopBkgUnique(Boolean lineopBkgUnique) {
        this.lineopBkgUnique = lineopBkgUnique;
    }

    public Boolean getLineopStopBkgUpdates() {
        return this.lineopStopBkgUpdates;
    }

    protected void setLineopStopBkgUpdates(Boolean lineopStopBkgUpdates) {
        this.lineopStopBkgUpdates = lineopStopBkgUpdates;
    }

    public Boolean getLineopStopRtgUpdates() {
        return this.lineopStopRtgUpdates;
    }

    protected void setLineopStopRtgUpdates(Boolean lineopStopRtgUpdates) {
        this.lineopStopRtgUpdates = lineopStopRtgUpdates;
    }

    public Boolean getLineopBlUnique() {
        return this.lineopBlUnique;
    }

    protected void setLineopBlUnique(Boolean lineopBlUnique) {
        this.lineopBlUnique = lineopBlUnique;
    }

    public BookingUsageEnum getLineopBkgUsage() {
        return this.lineopBkgUsage;
    }

    protected void setLineopBkgUsage(BookingUsageEnum lineopBkgUsage) {
        this.lineopBkgUsage = lineopBkgUsage;
    }

    public BookingRollEnum getLineopBkgRoll() {
        return this.lineopBkgRoll;
    }

    protected void setLineopBkgRoll(BookingRollEnum lineopBkgRoll) {
        this.lineopBkgRoll = lineopBkgRoll;
    }

    public BookingRollCutoffEnum getLineopBkgRollCutoff() {
        return this.lineopBkgRollCutoff;
    }

    protected void setLineopBkgRollCutoff(BookingRollCutoffEnum lineopBkgRollCutoff) {
        this.lineopBkgRollCutoff = lineopBkgRollCutoff;
    }

    public Boolean getLineopBkgAdhoc() {
        return this.lineopBkgAdhoc;
    }

    protected void setLineopBkgAdhoc(Boolean lineopBkgAdhoc) {
        this.lineopBkgAdhoc = lineopBkgAdhoc;
    }

    public Boolean getLineopBkgIsNotValidated() {
        return this.lineopBkgIsNotValidated;
    }

    protected void setLineopBkgIsNotValidated(Boolean lineopBkgIsNotValidated) {
        this.lineopBkgIsNotValidated = lineopBkgIsNotValidated;
    }

    public Boolean getLineopRollLateCtr() {
        return this.lineopRollLateCtr;
    }

    protected void setLineopRollLateCtr(Boolean lineopRollLateCtr) {
        this.lineopRollLateCtr = lineopRollLateCtr;
    }

    public Boolean getLineopGenPinNbr() {
        return this.lineopGenPinNbr;
    }

    protected void setLineopGenPinNbr(Boolean lineopGenPinNbr) {
        this.lineopGenPinNbr = lineopGenPinNbr;
    }

    public Boolean getLineopUsePinNbr() {
        return this.lineopUsePinNbr;
    }

    protected void setLineopUsePinNbr(Boolean lineopUsePinNbr) {
        this.lineopUsePinNbr = lineopUsePinNbr;
    }

    public Boolean getLineopOrderItemNotUnique() {
        return this.lineopOrderItemNotUnique;
    }

    protected void setLineopOrderItemNotUnique(Boolean lineopOrderItemNotUnique) {
        this.lineopOrderItemNotUnique = lineopOrderItemNotUnique;
    }

    public Boolean getLineopIsOrderNbrUnique() {
        return this.lineopIsOrderNbrUnique;
    }

    protected void setLineopIsOrderNbrUnique(Boolean lineopIsOrderNbrUnique) {
        this.lineopIsOrderNbrUnique = lineopIsOrderNbrUnique;
    }

    public Boolean getLineopIsEDOUniqueByFacility() {
        return this.lineopIsEDOUniqueByFacility;
    }

    protected void setLineopIsEDOUniqueByFacility(Boolean lineopIsEDOUniqueByFacility) {
        this.lineopIsEDOUniqueByFacility = lineopIsEDOUniqueByFacility;
    }

    public Date getLineopReeferTimeMonitor1() {
        return this.lineopReeferTimeMonitor1;
    }

    protected void setLineopReeferTimeMonitor1(Date lineopReeferTimeMonitor1) {
        this.lineopReeferTimeMonitor1 = lineopReeferTimeMonitor1;
    }

    public Date getLineopReeferTimeMonitor2() {
        return this.lineopReeferTimeMonitor2;
    }

    protected void setLineopReeferTimeMonitor2(Date lineopReeferTimeMonitor2) {
        this.lineopReeferTimeMonitor2 = lineopReeferTimeMonitor2;
    }

    public Date getLineopReeferTimeMonitor3() {
        return this.lineopReeferTimeMonitor3;
    }

    protected void setLineopReeferTimeMonitor3(Date lineopReeferTimeMonitor3) {
        this.lineopReeferTimeMonitor3 = lineopReeferTimeMonitor3;
    }

    public Date getLineopReeferTimeMonitor4() {
        return this.lineopReeferTimeMonitor4;
    }

    protected void setLineopReeferTimeMonitor4(Date lineopReeferTimeMonitor4) {
        this.lineopReeferTimeMonitor4 = lineopReeferTimeMonitor4;
    }

    public Integer getLineopReeferOffPowerTimeLimit() {
        return this.lineopReeferOffPowerTimeLimit;
    }

    protected void setLineopReeferOffPowerTimeLimit(Integer lineopReeferOffPowerTimeLimit) {
        this.lineopReeferOffPowerTimeLimit = lineopReeferOffPowerTimeLimit;
    }

    public Boolean getLineopPropagateReeferChanges() {
        return this.lineopPropagateReeferChanges;
    }

    protected void setLineopPropagateReeferChanges(Boolean lineopPropagateReeferChanges) {
        this.lineopPropagateReeferChanges = lineopPropagateReeferChanges;
    }

    public String getLineopFlexString01() {
        return this.lineopFlexString01;
    }

    protected void setLineopFlexString01(String lineopFlexString01) {
        this.lineopFlexString01 = lineopFlexString01;
    }

    public String getLineopFlexString02() {
        return this.lineopFlexString02;
    }

    protected void setLineopFlexString02(String lineopFlexString02) {
        this.lineopFlexString02 = lineopFlexString02;
    }

    public String getLineopFlexString03() {
        return this.lineopFlexString03;
    }

    protected void setLineopFlexString03(String lineopFlexString03) {
        this.lineopFlexString03 = lineopFlexString03;
    }

    public String getLineopFlexString04() {
        return this.lineopFlexString04;
    }

    protected void setLineopFlexString04(String lineopFlexString04) {
        this.lineopFlexString04 = lineopFlexString04;
    }

    public String getLineopFlexString05() {
        return this.lineopFlexString05;
    }

    protected void setLineopFlexString05(String lineopFlexString05) {
        this.lineopFlexString05 = lineopFlexString05;
    }

    public String getLineopFlexString06() {
        return this.lineopFlexString06;
    }

    protected void setLineopFlexString06(String lineopFlexString06) {
        this.lineopFlexString06 = lineopFlexString06;
    }

    public String getLineopFlexString07() {
        return this.lineopFlexString07;
    }

    protected void setLineopFlexString07(String lineopFlexString07) {
        this.lineopFlexString07 = lineopFlexString07;
    }

    public String getLineopFlexString08() {
        return this.lineopFlexString08;
    }

    protected void setLineopFlexString08(String lineopFlexString08) {
        this.lineopFlexString08 = lineopFlexString08;
    }

    public String getLineopFlexString09() {
        return this.lineopFlexString09;
    }

    protected void setLineopFlexString09(String lineopFlexString09) {
        this.lineopFlexString09 = lineopFlexString09;
    }

    public String getLineopFlexString10() {
        return this.lineopFlexString10;
    }

    protected void setLineopFlexString10(String lineopFlexString10) {
        this.lineopFlexString10 = lineopFlexString10;
    }

    public EntityMappingPredicate getLineopDemurrageRules() {
        return this.lineopDemurrageRules;
    }

    protected void setLineopDemurrageRules(EntityMappingPredicate lineopDemurrageRules) {
        this.lineopDemurrageRules = lineopDemurrageRules;
    }

    public EntityMappingPredicate getLineopPowerRules() {
        return this.lineopPowerRules;
    }

    protected void setLineopPowerRules(EntityMappingPredicate lineopPowerRules) {
        this.lineopPowerRules = lineopPowerRules;
    }

    public EntityMappingPredicate getLineopLineDemurrageRules() {
        return this.lineopLineDemurrageRules;
    }

    protected void setLineopLineDemurrageRules(EntityMappingPredicate lineopLineDemurrageRules) {
        this.lineopLineDemurrageRules = lineopLineDemurrageRules;
    }
}
