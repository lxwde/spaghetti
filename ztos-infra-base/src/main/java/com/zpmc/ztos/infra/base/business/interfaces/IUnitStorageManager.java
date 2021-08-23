package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.BillingExtractEntityEnum;
import com.zpmc.ztos.infra.base.business.inventory.UnitFacilityVisit;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import org.apache.commons.lang.mutable.MutableDouble;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;

public interface IUnitStorageManager {
    public static final String BEAN_ID = "unitStorageManager";

    public void validateIsStorageOwed(UnitFacilityVisit var1, String var2) throws BizViolation;

    public void validateIsStorageOwed(UnitFacilityVisit var1) throws BizViolation;

    public void validateIsStorageOwed(UnitFacilityVisit var1, Date var2, String var3) throws BizViolation;

    public void validateIsStorageOwed(UnitFacilityVisit var1, Date var2) throws BizViolation;

    public void validateIsLineStorageOwed(UnitFacilityVisit var1, Date var2) throws BizViolation;

    public void validateIsPowerOwed(UnitFacilityVisit var1, Date var2) throws BizViolation;

    public boolean isStorageOwed(UnitFacilityVisit var1, String var2);

    public boolean isStorageOwed(UnitFacilityVisit var1, Date var2);

    public boolean isStorageOwed(UnitFacilityVisit var1, Date var2, String var3);

    public int getStorageDaysOwed(UnitFacilityVisit var1, String var2);

    public int getStorageDaysTotal(UnitFacilityVisit var1, String var2);

    public int getPowerDaysTotal(UnitFacilityVisit var1, String var2);

    public int getPowerHoursTotal(UnitFacilityVisit var1, String var2);

    public int getPowerDaysOwed(UnitFacilityVisit var1, String var2);

//    public EdiInvoice getInvoiceForUnit(UnitFacilityVisit var1, Date var2, String var3, String var4, ScopedBizUnit var5, ScopedBizUnit var6, String var7, Date var8, String var9) throws BizViolation;

//    public EdiPayment payInvoice(PaymentTypeEnum var1, Date var2, String var3, String var4, Long var5, String var6, Date var7, Long var8, Date var9, String var10, Double var11, String var12, ScopedBizUnit var13, String var14, Double var15);

    @Nullable
    public String getPaymentReceiptUrl(Long var1, Long var2);

    public String getInvoiceReportUrl(Long var1);

    public boolean deleteInvoice(String var1) throws BizViolation;

//    public EdiInvoice deleteInvoiceItems(String var1, Object[] var2) throws BizViolation;

    public String finalizeInvoice(String var1, Date var2) throws BizViolation;

//    public UnitStorageCalculation getStorageDaysCalculation(UnitFacilityVisit var1, Date var2, Date var3, String var4);
//
//    public UnitStorageCalculation getStorageDaysCalculation(UnitFacilityVisit var1, Date var2, Date var3, String var4, boolean var5);

    public int getFreeDays(UnitFacilityVisit var1, String var2);

    public Date getLastFreeDay(UnitFacilityVisit var1, String var2);

    public Date getFirstFreeDay(UnitFacilityVisit var1, String var2);

    public IValueHolder getStorageCalculationDto(UnitFacilityVisit var1, String var2);

    public String getLastFreeDayAsStringForUi(UnitFacilityVisit var1, String var2);

    public boolean isStoragePaid(UnitFacilityVisit var1, String var2);

    public boolean isStoragePaid(UnitFacilityVisit var1);

    public boolean isPerContainerCreditLimitValid(ScopedBizUnit var1, Double var2, Serializable var3, BillingExtractEntityEnum var4, Serializable var5, MutableDouble var6) throws BizViolation;

    public boolean isPerContainerCreditLimitValidForUfv(ScopedBizUnit var1, Double var2, Serializable var3, Serializable var4, MutableDouble var5) throws BizViolation;

    @Nullable
    public Date getRuleStartTimeFor(UnitFacilityVisit var1, String var2);

    @Nullable
    public Date getRuleEndTimeFor(UnitFacilityVisit var1, String var2);

    @Nullable
    public Date findPaidThruDayForComplex(Serializable var1, String var2);
}
