package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.BillingExtractEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.GuaranteeOverrideTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.GuaranteeTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PaymentTypeEnum;
import com.zpmc.ztos.infra.base.business.model.Guarantee;
import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.zpmc.ztos.infra.base.business.model.RefState;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class GuaranteeDO extends DatabaseEntity implements Serializable {
    private Long gnteGkey;
    private BillingExtractEntityEnum gnteAppliedToClass;
    private Long gnteAppliedToPrimaryKey;
    private String gnteAppliedToNaturalKey;
    private String gnteGuaranteeId;
    private String gnteN4UserId;
    private String gnteExternalUserId;
    private String gnteExternalContactName;
    private String gnteExternalAddress1;
    private String gnteExternalAddress2;
    private String gnteExternalAddress3;
    private String gnteExternalCity;
    private String gnteExternalMailCode;
    private String gnteExternalTelephone;
    private String gnteExternalFax;
    private String gnteExternalEmailAddress;
    private GuaranteeTypeEnum gnteGuaranteeType;
    private Date gnteGuaranteeStartDay;
    private Date gnteGuaranteeEndDay;
    private Double gnteQuantity;
    private Double gnteGuaranteeAmount;
    private Date gnteVoidedOrExpiredDate;
    private Date gnteVoidedEmailSentDate;
    private Date gnteWaiverExpirationDate;
    private String gnteNotes;
    private PaymentTypeEnum gntePaymentType;
    private String gntePaymentNbr;
    private Date gntePaymentDate;
    private String gnteCheckRoutingNbr;
    private String gnteBankName;
    private String gnteAuthorizationNbr;
    private Date gnteAuthorizationExpiration;
    private String gnteTransactionReference;
    private String gnteCustomerReferenceId;
    private GuaranteeOverrideTypeEnum gnteOverrideValueType;
    private String gnteInvoiceStatus;
    private String gnteInvDraftNbr;
    private Date gnteCreated;
    private String gnteCreator;
    private Date gnteChanged;
    private String gnteChanger;
    private ScopedBizUnit gnteGuaranteeCustomer;
    private RefState gnteExternalState;
    private RefCountry gnteExternalCountry;
    private Guarantee gnteRelatedGuarantee;

    public Serializable getPrimaryKey() {
        return this.getGnteGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getGnteGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof GuaranteeDO)) {
            return false;
        }
        GuaranteeDO that = (GuaranteeDO)other;
        return ((Object)id).equals(that.getGnteGkey());
    }

    public int hashCode() {
        Long id = this.getGnteGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getGnteGkey() {
        return this.gnteGkey;
    }

    public void setGnteGkey(Long gnteGkey) {
        this.gnteGkey = gnteGkey;
    }

    public BillingExtractEntityEnum getGnteAppliedToClass() {
        return this.gnteAppliedToClass;
    }

    public void setGnteAppliedToClass(BillingExtractEntityEnum gnteAppliedToClass) {
        this.gnteAppliedToClass = gnteAppliedToClass;
    }

    public Long getGnteAppliedToPrimaryKey() {
        return this.gnteAppliedToPrimaryKey;
    }

    public void setGnteAppliedToPrimaryKey(Long gnteAppliedToPrimaryKey) {
        this.gnteAppliedToPrimaryKey = gnteAppliedToPrimaryKey;
    }

    public String getGnteAppliedToNaturalKey() {
        return this.gnteAppliedToNaturalKey;
    }

    public void setGnteAppliedToNaturalKey(String gnteAppliedToNaturalKey) {
        this.gnteAppliedToNaturalKey = gnteAppliedToNaturalKey;
    }

    public String getGnteGuaranteeId() {
        return this.gnteGuaranteeId;
    }

    public void setGnteGuaranteeId(String gnteGuaranteeId) {
        this.gnteGuaranteeId = gnteGuaranteeId;
    }

    public String getGnteN4UserId() {
        return this.gnteN4UserId;
    }

    public void setGnteN4UserId(String gnteN4UserId) {
        this.gnteN4UserId = gnteN4UserId;
    }

    public String getGnteExternalUserId() {
        return this.gnteExternalUserId;
    }

    public void setGnteExternalUserId(String gnteExternalUserId) {
        this.gnteExternalUserId = gnteExternalUserId;
    }

    public String getGnteExternalContactName() {
        return this.gnteExternalContactName;
    }

    public void setGnteExternalContactName(String gnteExternalContactName) {
        this.gnteExternalContactName = gnteExternalContactName;
    }

    public String getGnteExternalAddress1() {
        return this.gnteExternalAddress1;
    }

    public void setGnteExternalAddress1(String gnteExternalAddress1) {
        this.gnteExternalAddress1 = gnteExternalAddress1;
    }

    public String getGnteExternalAddress2() {
        return this.gnteExternalAddress2;
    }

    public void setGnteExternalAddress2(String gnteExternalAddress2) {
        this.gnteExternalAddress2 = gnteExternalAddress2;
    }

    public String getGnteExternalAddress3() {
        return this.gnteExternalAddress3;
    }

    public void setGnteExternalAddress3(String gnteExternalAddress3) {
        this.gnteExternalAddress3 = gnteExternalAddress3;
    }

    public String getGnteExternalCity() {
        return this.gnteExternalCity;
    }

    public void setGnteExternalCity(String gnteExternalCity) {
        this.gnteExternalCity = gnteExternalCity;
    }

    public String getGnteExternalMailCode() {
        return this.gnteExternalMailCode;
    }

    public void setGnteExternalMailCode(String gnteExternalMailCode) {
        this.gnteExternalMailCode = gnteExternalMailCode;
    }

    public String getGnteExternalTelephone() {
        return this.gnteExternalTelephone;
    }

    public void setGnteExternalTelephone(String gnteExternalTelephone) {
        this.gnteExternalTelephone = gnteExternalTelephone;
    }

    public String getGnteExternalFax() {
        return this.gnteExternalFax;
    }

    public void setGnteExternalFax(String gnteExternalFax) {
        this.gnteExternalFax = gnteExternalFax;
    }

    public String getGnteExternalEmailAddress() {
        return this.gnteExternalEmailAddress;
    }

    public void setGnteExternalEmailAddress(String gnteExternalEmailAddress) {
        this.gnteExternalEmailAddress = gnteExternalEmailAddress;
    }

    public GuaranteeTypeEnum getGnteGuaranteeType() {
        return this.gnteGuaranteeType;
    }

    public void setGnteGuaranteeType(GuaranteeTypeEnum gnteGuaranteeType) {
        this.gnteGuaranteeType = gnteGuaranteeType;
    }

    public Date getGnteGuaranteeStartDay() {
        return this.gnteGuaranteeStartDay;
    }

    public void setGnteGuaranteeStartDay(Date gnteGuaranteeStartDay) {
        this.gnteGuaranteeStartDay = gnteGuaranteeStartDay;
    }

    public Date getGnteGuaranteeEndDay() {
        return this.gnteGuaranteeEndDay;
    }

    public void setGnteGuaranteeEndDay(Date gnteGuaranteeEndDay) {
        this.gnteGuaranteeEndDay = gnteGuaranteeEndDay;
    }

    public Double getGnteQuantity() {
        return this.gnteQuantity;
    }

    public void setGnteQuantity(Double gnteQuantity) {
        this.gnteQuantity = gnteQuantity;
    }

    public Double getGnteGuaranteeAmount() {
        return this.gnteGuaranteeAmount;
    }

    public void setGnteGuaranteeAmount(Double gnteGuaranteeAmount) {
        this.gnteGuaranteeAmount = gnteGuaranteeAmount;
    }

    public Date getGnteVoidedOrExpiredDate() {
        return this.gnteVoidedOrExpiredDate;
    }

    public void setGnteVoidedOrExpiredDate(Date gnteVoidedOrExpiredDate) {
        this.gnteVoidedOrExpiredDate = gnteVoidedOrExpiredDate;
    }

    public Date getGnteVoidedEmailSentDate() {
        return this.gnteVoidedEmailSentDate;
    }

    public void setGnteVoidedEmailSentDate(Date gnteVoidedEmailSentDate) {
        this.gnteVoidedEmailSentDate = gnteVoidedEmailSentDate;
    }

    public Date getGnteWaiverExpirationDate() {
        return this.gnteWaiverExpirationDate;
    }

    public void setGnteWaiverExpirationDate(Date gnteWaiverExpirationDate) {
        this.gnteWaiverExpirationDate = gnteWaiverExpirationDate;
    }

    public String getGnteNotes() {
        return this.gnteNotes;
    }

    public void setGnteNotes(String gnteNotes) {
        this.gnteNotes = gnteNotes;
    }

    public PaymentTypeEnum getGntePaymentType() {
        return this.gntePaymentType;
    }

    public void setGntePaymentType(PaymentTypeEnum gntePaymentType) {
        this.gntePaymentType = gntePaymentType;
    }

    public String getGntePaymentNbr() {
        return this.gntePaymentNbr;
    }

    public void setGntePaymentNbr(String gntePaymentNbr) {
        this.gntePaymentNbr = gntePaymentNbr;
    }

    public Date getGntePaymentDate() {
        return this.gntePaymentDate;
    }

    public void setGntePaymentDate(Date gntePaymentDate) {
        this.gntePaymentDate = gntePaymentDate;
    }

    public String getGnteCheckRoutingNbr() {
        return this.gnteCheckRoutingNbr;
    }

    public void setGnteCheckRoutingNbr(String gnteCheckRoutingNbr) {
        this.gnteCheckRoutingNbr = gnteCheckRoutingNbr;
    }

    public String getGnteBankName() {
        return this.gnteBankName;
    }

    public void setGnteBankName(String gnteBankName) {
        this.gnteBankName = gnteBankName;
    }

    public String getGnteAuthorizationNbr() {
        return this.gnteAuthorizationNbr;
    }

    public void setGnteAuthorizationNbr(String gnteAuthorizationNbr) {
        this.gnteAuthorizationNbr = gnteAuthorizationNbr;
    }

    public Date getGnteAuthorizationExpiration() {
        return this.gnteAuthorizationExpiration;
    }

    public void setGnteAuthorizationExpiration(Date gnteAuthorizationExpiration) {
        this.gnteAuthorizationExpiration = gnteAuthorizationExpiration;
    }

    public String getGnteTransactionReference() {
        return this.gnteTransactionReference;
    }

    public void setGnteTransactionReference(String gnteTransactionReference) {
        this.gnteTransactionReference = gnteTransactionReference;
    }

    public String getGnteCustomerReferenceId() {
        return this.gnteCustomerReferenceId;
    }

    public void setGnteCustomerReferenceId(String gnteCustomerReferenceId) {
        this.gnteCustomerReferenceId = gnteCustomerReferenceId;
    }

    public GuaranteeOverrideTypeEnum getGnteOverrideValueType() {
        return this.gnteOverrideValueType;
    }

    public void setGnteOverrideValueType(GuaranteeOverrideTypeEnum gnteOverrideValueType) {
        this.gnteOverrideValueType = gnteOverrideValueType;
    }

    public String getGnteInvoiceStatus() {
        return this.gnteInvoiceStatus;
    }

    public void setGnteInvoiceStatus(String gnteInvoiceStatus) {
        this.gnteInvoiceStatus = gnteInvoiceStatus;
    }

    public String getGnteInvDraftNbr() {
        return this.gnteInvDraftNbr;
    }

    public void setGnteInvDraftNbr(String gnteInvDraftNbr) {
        this.gnteInvDraftNbr = gnteInvDraftNbr;
    }

    public Date getGnteCreated() {
        return this.gnteCreated;
    }

    public void setGnteCreated(Date gnteCreated) {
        this.gnteCreated = gnteCreated;
    }

    public String getGnteCreator() {
        return this.gnteCreator;
    }

    public void setGnteCreator(String gnteCreator) {
        this.gnteCreator = gnteCreator;
    }

    public Date getGnteChanged() {
        return this.gnteChanged;
    }

    public void setGnteChanged(Date gnteChanged) {
        this.gnteChanged = gnteChanged;
    }

    public String getGnteChanger() {
        return this.gnteChanger;
    }

    public void setGnteChanger(String gnteChanger) {
        this.gnteChanger = gnteChanger;
    }

    public ScopedBizUnit getGnteGuaranteeCustomer() {
        return this.gnteGuaranteeCustomer;
    }

    public void setGnteGuaranteeCustomer(ScopedBizUnit gnteGuaranteeCustomer) {
        this.gnteGuaranteeCustomer = gnteGuaranteeCustomer;
    }

    public RefState getGnteExternalState() {
        return this.gnteExternalState;
    }

    public void setGnteExternalState(RefState gnteExternalState) {
        this.gnteExternalState = gnteExternalState;
    }

    public RefCountry getGnteExternalCountry() {
        return this.gnteExternalCountry;
    }

    public void setGnteExternalCountry(RefCountry gnteExternalCountry) {
        this.gnteExternalCountry = gnteExternalCountry;
    }

    public Guarantee getGnteRelatedGuarantee() {
        return this.gnteRelatedGuarantee;
    }

    public void setGnteRelatedGuarantee(Guarantee gnteRelatedGuarantee) {
        this.gnteRelatedGuarantee = gnteRelatedGuarantee;
    }
}
