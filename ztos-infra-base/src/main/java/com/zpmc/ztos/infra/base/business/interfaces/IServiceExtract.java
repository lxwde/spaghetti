package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.WeekdayEnum;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;

public interface IServiceExtract extends IValueSource {
    public static final String INVOICED = "INVOICED";
    public static final String PARTIAL = "PARTIAL";
    public static final String QUEUED = "QUEUED";
    public static final String GUARANTEED = "GUARANTEED";
    public static final String DRAFT = "DRAFT";
    public static final String CANCELLED = "CANCELLED";

    public String getServiceId();

    public Serializable getServiceExtractGkey();

    public Double getServiceExtractQuantity();

    public String getServiceExtractQuantityUnit();

    @Override
    public Object getFieldValue(IMetafieldId var1);

    public double getNullSafeQuantity();

    public boolean isInventoryStorageService();

    public boolean isInventoryLineStorageService();

    public boolean isInventoryReeferService();

    public String getServiceEntityId();

    public Date getPerformedDay();

    public Date getServicePaidThruDay();

  //  public TieredCalculation getTieredCalculation(IMetafieldId var1);

    @Nullable
    public WeekdayEnum getPerformedDayOfWeekUnit();

    @Nullable
    public WeekdayEnum getPerformedDayOfWeekMarine();

    public String getEventType();

    public String getStatus();

    public Long getEventGkey();

    public Long getPerformedTimeOfDayMarine();

    public Long getPerformedTimeOfDayUnit();

    public IMetafieldId getStatusFieldId();

    public void setStatusInvoicedOrPartial();

    public IMetafieldId getDraftInvNbrField();

    public void setLastDraftInvNbr(String var1);

    public void setStatusQueued();

    public void setStatusDraft();

    public void setStatusCancelled();

    public String getPrimaryCustomerId();

    @Nullable
    public String getSecondaryCustomerId();

    public Date getServiceExtractEventEndTime();

    public String getFacilityId();

    public String getComplexId();

    public void setFacility(Facility var1);

    public Facility getFacility();

    public String toString();

    @Nullable
    public Date getEventEndTime();

    @Nullable
    public Date getRuleStartDate();

    @Nullable
    public Date getRuleEndDate();

    public Date getTimeDischargeComplete();

    public Date getCarrierETA();

    @Nullable
    public Date getOBCarrierETA();

    @Nullable
    public Date getFirstAvailability();

    public Date getTimeFirstFreeDay();

    public boolean isOverrideValue();

    @Nullable
    public String getOverrideValueType();

    @Nullable
    public Double getOverrideValue();

    @Nullable
    public String getFixedPriceWaiverId();

    @Nullable
    public String getGnteInvProcessingStatus();

    public String getGuaranteeId();

    public String getLastDraftInvNbr();

    public boolean isTimeBasedEvent();

    public Date getIBCarrierATA();

    public Date getOBCarrierATA();

    public Date getIBCarrierATD();

    public Date getOBCarrierATD();

    public Date getVesselATA();

    public Date getVesselATD();

    public Date getServiceGuaranteeThruDay();

    public String getNotes();
}
