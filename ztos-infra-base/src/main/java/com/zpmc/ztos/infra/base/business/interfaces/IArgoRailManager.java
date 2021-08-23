package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipNominalLengthEnum;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.Junction;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.EdiPostingContext;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;

public interface IArgoRailManager {

    public static final String BEAN_ID = "argoRailManager";

    public CarrierVisit createOrUpdateTrainVisitDetails(Facility var1, CarrierVisit var2, Date var3);

    public CarrierVisit createOrUpdateTrainVisitDetails(Facility var1, Serializable var2, CarrierVisit var3, Date var4, String var5);

    public void updateRailcarVisibilityInSPARCSOnVisitPhaseChange(CarrierVisit var1);

    @Nullable
    public String getRailcarVisitPosition(Serializable var1);

    public AbstractBin getRailcarVisitLocPositionBin(Serializable var1);

    public ILocation getRailcarVisitILocation(Serializable var1);

    public boolean isRailConfigured(UserContext var1);

    public boolean includeNonOpFacilities(UserContext var1);

    public boolean validateRailTrackPosition(UserContext var1);

    public IDomainQuery getIbOnlyTrains();

    public IServiceable getServiceableTv(CarrierVisit var1);

    public IDomainQuery getUnitsOnBoardRailcarPosition(LocPosition var1);

    @Nullable
    public Junction getUnitsOnBoardTvQueryJunction(Serializable var1);

    public ILocation findRailcarVisit(String var1, String var2) throws BizViolation;

    @Nullable
    public ILocation findOrCreateRailcarVisit(String var1, String var2, String var3) throws BizViolation;

    @Nullable
    public BizViolation validateFacilityChange(CarrierVisit var1);

    public void updateTrainVisitFlexFields(VisitDetails var1, IEdiTvFlexFields var2);

    public double getRailcarCentroidOffset(Serializable var1, int var2, double var3);

    public boolean isCarrierIncompatibleForLoad(LocPosition var1, EquipNominalLengthEnum var2) throws BizViolation;

    @Nullable
    public String findOrCreateRailCar(EdiPostingContext var1, String var2, String var3, ScopedBizUnit var4) throws BizViolation;

//    public Railcars getRailcarPlatformSlotNamesForIntersectingCircle(@NotNull String var1, @NotNull Geometry var2, @NotNull Double var3, @NotNull Geometry var4);

    public boolean isSameTrainVisit(LocPosition var1, LocPosition var2);

//    public Railcars getRailcarPlatformSlotsGeometry(@Nullable String var1, @Nullable String var2, String var3, @Nullable String var4);

    public Boolean isRailPlatformLabelForSlotSettingOn(UserContext var1);
}
