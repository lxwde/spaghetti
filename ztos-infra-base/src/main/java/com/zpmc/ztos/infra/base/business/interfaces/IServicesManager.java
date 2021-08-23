package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public interface IServicesManager {
    public static final String BEAN_ID = "servicesManager";

    public boolean isEventAllowed(IEventType var1, IServiceable var2);

    public BizViolation verifyEventAllowed(IEventType var1, IServiceable var2);

    public BizViolation verifyEventAllowed(IEventType var1, IServiceable var2, Boolean var3);

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5, FieldChanges var6) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5, FieldChanges var6, Date var7) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5, FieldChanges var6, Date var7, IEntity var8) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5, FieldChanges var6, Date var7, IEntity var8, FieldChanges var9) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, IServiceable var5, Map var6) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, ScopedBizUnit var5, IServiceable var6, Map var7) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, ScopedBizUnit var5, IServiceable var6, Map var7, Date var8) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, ScopedBizUnit var5, IServiceable var6, Map var7, Date var8, Map var9) throws BizViolation;

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, ScopedBizUnit var5, IServiceable var6, Map var7, Date var8, IEntity var9, Map var10, Map var11, FieldChanges var12) throws BizViolation;

    public boolean isFlagValid(LogicalEntityEnum var1, String var2, String var3);

    public void applyPermission(String var1, ILogicalEntity var2, String var3, String var4, boolean var5) throws BizViolation;

    public void releasePaymentHold(Long var1, ILogicalEntity var2) throws BizViolation;

    public Serializable applyPermission(String var1, ILogicalEntity var2, IGuardian var3, String var4, String var5) throws BizViolation;

    public Serializable applyGuardedPermission(String var1, IServiceable var2, IGuardian var3, String var4, String var5) throws BizViolation;

    public Object applyHold(String var1, ILogicalEntity var2, String var3, String var4, boolean var5) throws BizViolation;

    public Serializable applyHold(String var1, ILogicalEntity var2, IGuardian var3, String var4, String var5) throws BizViolation;

    @Nullable
    public Serializable applyGuardedHold(String var1, IServiceable var2, IGuardian var3, String var4, String var5) throws BizViolation;

    public void applyFlag(String var1, IServiceable var2, IGuardian var3, String var4, String var5) throws BizViolation;

    public boolean isEntityFlaggedWith(String var1, ILogicalEntity var2, IGuardian var3, IServiceable var4, String var5);

    public String getFlagReferenceIdForEntity(String var1, ILogicalEntity var2);

    public String[] getActiveFlagIds(ILogicalEntity var1);

    public void copyActiveFlags(ILogicalEntity var1, ILogicalEntity var2, FunctionalAreaEnum var3) throws BizViolation;

    public void copyActiveFlags(ILogicalEntity var1, ILogicalEntity var2, FunctionalAreaEnum var3, Boolean var4, List<String> var5, Boolean var6) throws BizViolation;

    public void copyActiveFlags(ILogicalEntity var1, ILogicalEntity var2) throws BizViolation;

    public IFlagType getFlagTypeByGkey(Serializable var1);

    public IFlagType getFlagTypeById(String var1);

    public IFlagType getFlagTypeByProxyId(String var1);

    public boolean hasEventTypeBeenRecorded(IEventType var1, IServiceable var2);

    public Date getMostRecentTimeForEvent(IEventType var1, IServiceable var2);

    public void purgeEventAndFlagHistory(ILogicalEntity var1);

    public void purgeAllEventsAfterLastOccurrence(IEventType var1, IServiceable var2, Collection<Serializable> var3);

    public void purgeEventsAfterLastOccurrence(String[] var1, IServiceable var2, Collection<Serializable> var3);

    public void purgeEventsAfterLastOccurrence(String[] var1, IServiceable var2, Collection<Serializable> var3, Facility var4);

    public List<IEventType> getPreventedEventTypes(IServiceable var1, String[] var2) throws BizViolation;

    public Collection getEventTypesAffectedByFlagType(ILogicalEntity var1, String var2, LogicalEntityEnum var3, Serializable var4, LogicalEntityEnum var5, Serializable var6);

    public List getApplicableCustomEvents(LogicalEntityEnum[] var1);

    public IEventType getEventType(String var1);

    public IEventType getEventType(Serializable var1);

    public Collection getFlagTypes(LogicalEntityEnum var1, FlagPurposeEnum var2, FlagActionEnum var3, boolean var4);

    public Collection getImpedimentsForEntity(ILogicalEntity var1);

    public Collection getImpedimentsForEntity(ILogicalEntity var1, Collection var2, boolean var3);

    public Collection getImpedimentsForServiceOnEntity(ILogicalEntity var1, IEventType var2);

    public Map<IEventType, Collection> getImpedimentsForServiceOnEntityAndEventTypes(ILogicalEntity var1, IEventType[] var2);

    public List getEventHistory(ILogicalEntity var1);

    public List getEventsByCreatedDate(Date var1, Date var2, IEventType[] var3);

    public Serializable[] getEventGkeysByDateAndType(Date var1, Date var2, IEventType[] var3);

    public IPredicate createActiveFlagPredicate(String var1, LogicalEntityEnum var2, IMetafieldId var3);

    public IPredicate createActiveFlagPredicate(Object var1, LogicalEntityEnum var2, IMetafieldId var3);

    public List getNotifiableEventTypes(LogicalEntityEnum var1);

  //  public INoticeRequest loadNoticeRequest(Serializable var1);

    public List findContainerNoticeRequestsForUser();

  //  public INoticeRequest createContainerNoticeRequest(String var1, String var2, String var3);

    public void updateNoticeRequest(Serializable var1, String var2, String var3, String var4);

    public void deleteNoticeRequest(Serializable var1);

    public void redirectFlags(IServiceable var1, IServiceable var2, boolean var3);

    public void updateEventBillingExtractBatchId(Serializable var1, Long var2);

    public IFlagType findOrCreateTestFlagType(String var1, FlagPurposeEnum var2, boolean var3, LogicalEntityEnum var4);

    public void deleteTestFlagType(String var1);

    public boolean cancelEvent(Long var1);

    public Set<String> getActiveFlagIds(IGuardian[] var1, ILogicalEntity var2);

//    public void recordEvent(LogicalEntityIdentifier var1, FieldChanges var2) throws BizViolation;

    public Set<String> getVetoedFlagIds(IGuardian[] var1, ILogicalEntity var2);

    public String[] getVetoedFlagIds(ILogicalEntity var1);

    public IPredicate createVetoedFlagPredicate(Object var1, LogicalEntityEnum var2, IMetafieldId var3);

    @Nullable
    public IPredicate createActiveFlagPredicateForHpv(Object var1, LogicalEntityEnum var2, IMetafieldId var3);

    @Nullable
    public IGuardian findReferencedGuardian(String var1, ILogicalEntity var2);

    public void updateAllEvents(LogicalEntityEnum var1, Serializable var2, FieldChanges var3);

    public IEvent getMostRecentEvent(IEventType var1, IServiceable var2);

    public IImpediment convertToImpediment(Serializable var1);

    public IImpediment convertToImpediment(Serializable var1, ILogicalEntity var2);

    public IEvent recordEvent(IEventType var1, String var2, Double var3, ServiceQuantityUnitEnum var4, ScopedBizUnit var5, IServiceable var6, Map var7, FieldChanges var8, Date var9, IEntity var10, Map var11) throws BizViolation;

    public IDomainQuery enhanceDomainQueryByLogicalEntityEnum(IDomainQuery var1, Serializable var2, LogicalEntityEnum var3);

//   public IDomainQuery enhanceDomainQueryByLogicalIdentifier(IDomainQuery var1, LogicalEntityIdentifier var2);

}
