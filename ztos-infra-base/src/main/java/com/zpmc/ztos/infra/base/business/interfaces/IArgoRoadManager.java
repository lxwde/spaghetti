package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.VisitDetails;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IArgoRoadManager {

    public static final String BEAN_ID = "argoRoadManager";

    public String findTruckTransactionForOrderItem(Serializable var1);

    public ITruckTransaction getRecentProcessedTruckTransaction(Serializable var1, VisitDetails var2);

    public IMetafieldId getAppointmentFlexFieldCounterpart(IMetafieldId var1);

    public ITruckVisitDetails getTvdFromCvGkey(Long var1);

    public ITruckTransaction getTruckTransactionDetails(Long var1);

    public boolean cancelAppointmentForOrderItem(Serializable var1) throws BizViolation;

    public boolean doesUnitHaveUncanceledTransaction(Serializable var1);

    public boolean doesUnitHaveActiveTransaction(Serializable var1);

    public List findMatchingCargoTruckVisits(String var1, Serializable var2, Serializable var3) throws BizViolation;

    public Serializable findMatchingDeliverCargoTransaction(Serializable var1, Serializable var2, Serializable var3, String var4, String var5) throws BizViolation;

    public Serializable findMatchingReceiveCargoTransaction(Serializable var1, Serializable var2, Serializable var3) throws BizViolation;

    public String submitUnloadCargoTransaction(Serializable var1, Serializable var2, String var3, Double var4) throws BizViolation;

    public void submitCargoTruckVisit(Serializable var1) throws BizViolation;

    public String submitLoadCargoTransaction(Serializable var1, Serializable var2, Double var3) throws BizViolation;

    public String submitLoadCargoTransaction(Serializable var1, Serializable var2, Double var3, FieldChanges var4) throws BizViolation;

    public CarrierVisit createTestVisit(String var1, String var2);

    public boolean findAppointmentForOrder(Serializable var1);

    public void purgeTranCargoLots(Long var1);

    public boolean isPrinterOrConsoleExistForDocType(Serializable var1);

    public List findTruckTransactionsForBlItem(Serializable var1);

    public List findTruckTransactionsForCrgSoItem(Serializable var1);

    public List findGateAppointmentsForBlItem(Serializable var1);

    public List findGateAppointmentsForBl(Serializable var1);

    public List findTruckTransactionsForBl(Serializable var1);

    @Nullable
    public Serializable findActiveReceiveTransactionForUnit(Serializable var1) throws BizViolation;

    public void unLinkCancelledAppointmentForOrder(Serializable var1);

    public void updateTranNextStage(Serializable var1, Serializable var2, Long var3, Double var4);
}
