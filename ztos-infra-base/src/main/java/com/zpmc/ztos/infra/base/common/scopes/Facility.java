package com.zpmc.ztos.infra.base.common.scopes;

import com.zpmc.ztos.infra.base.business.dataobject.FacilityDO;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.ArgoCompoundField;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

public class Facility extends FacilityDO {
    private static final Logger LOGGER = Logger.getLogger(Facility.class);

    public Facility() {
        this.setFcyLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setFcyIsNonOperational(Boolean.FALSE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setFcyLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getFcyLifeCycleState();
    }

    public String toString() {
        return "fcy-" + this.getFcyId();
    }

    public static Facility loadByGkey(Serializable inGkey) {
        return inGkey == null ? null : (Facility) Roastery.getHibernateApi().load(Facility.class, inGkey);
    }

    public static Facility findFacility(String inFcyId) {
        return Facility.findFacility(inFcyId, ContextHelper.getThreadComplex());
    }

    public static Facility findFacility(String inFcyId, Complex inComplex) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Facility").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_ID, (Object)inFcyId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_COMPLEX, (Object)inComplex.getCpxGkey()));
        dq.setScopingEnabled(false);
        return (Facility) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public Boolean isFcyNonOperational() {
        Boolean isNonOp = this.getFcyIsNonOperational();
        if (isNonOp == null || !isNonOp.booleanValue()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean isFcyOperational() {
        Boolean isNonOp = this.getFcyIsNonOperational();
        return isNonOp == null ? Boolean.TRUE : isNonOp == false;
    }

    @Nullable
    public static Facility findFacilityByRoutingPoint(RoutingPoint inRoutingPoint, Complex inComplex, Facility inExcludeFacility) {
        List matches = Facility.findAllFacilitiesByRoutingPoint(inRoutingPoint, inComplex, inExcludeFacility);
        if (matches.size() == 1) {
            return (Facility)matches.get(0);
        }
        if (matches.isEmpty()) {
            return null;
        }
        LOGGER.error((Object)("findFacilityByRoutingPoint: There are multiple facilities in complex <" + inComplex.getCpxId() + "> associated to point <" + inRoutingPoint.getPointId() + ">.  They are:"));
        for (Object facility : matches) {
            LOGGER.error((Object)("    " + facility));
        }
        return (Facility)matches.get(0);
    }

    public static List findAllFacilitiesByRoutingPoint(RoutingPoint inRoutingPoint, Complex inComplex, Facility inExcludeFacility) {
        IDomainQuery dq = Facility.getDomainQueryForAllFacilitiesByRoutingPoint(inRoutingPoint, inComplex, inExcludeFacility);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List findAllActiveFacilitiesByRoutingPoint(RoutingPoint inRoutingPoint, Complex inComplex, Facility inExcludeFacility) {
        IDomainQuery dq = Facility.getDomainQueryForAllFacilitiesByRoutingPoint(inRoutingPoint, inComplex, inExcludeFacility);
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    private static IDomainQuery getDomainQueryForAllFacilitiesByRoutingPoint(RoutingPoint inRoutingPoint, Complex inComplex, Facility inExcludeFacility) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Facility").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_ROUTING_POINT, (Object)inRoutingPoint.getPointGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_COMPLEX, (Object)inComplex.getCpxGkey()));
        if (inExcludeFacility != null) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoField.FCY_GKEY, (Object)inExcludeFacility.getFcyGkey()));
        }
        dq.setScopingEnabled(false);
        return dq;
    }

    public static Facility findOrCreateFacility(String inFcyId, String inFcyName, Complex inComplex, RoutingPoint inRoutingPoint) {
        Facility facility = Facility.findFacility(inFcyId, inComplex);
        if (facility == null) {
            facility = new Facility();
            facility.setFcyId(inFcyId);
        }
        facility.setFcyComplex(inComplex);
        facility.setFcyName(inFcyName);
//        facility.setFcyRoutingPoint(inRoutingPoint);
        HibernateApi.getInstance().saveOrUpdate((Object)facility);
        return facility;
    }

    @Nullable
    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        int entityLevel;
        ScopeCoordinates scope;
        int maxLevel;
        BizViolation bizViol = super.validateChanges(inFieldChanges);
        if (this.getPrimaryKey() == null && (maxLevel = (scope = ContextHelper.getThreadUserContext().getScopeCoordinate()).getMaxScopeLevel()) >= (entityLevel = this.getScopeEnum().getScopeLevel().intValue())) {
            IPropertyKey currentScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(maxLevel - 1))).getDescriptionPropertyKey();
            IPropertyKey requiredScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(entityLevel - 2))).getDescriptionPropertyKey();
            bizViol = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_PRIVILEGE_TO_CHANGE_TOPOLOGY, (BizViolation)bizViol, (Object)currentScope, (Object)requiredScope);
        }
        if (inFieldChanges.hasFieldChange(IArgoField.FCY_COMPLEX) || inFieldChanges.hasFieldChange(IArgoField.FCY_ID)) {
            bizViol = this.checkIfFcyAlreadyExistsForCpx(bizViol);
        }
        return bizViol;
    }

    private BizViolation checkIfFcyAlreadyExistsForCpx(BizViolation inBv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Facility").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_COMPLEX, (Object)this.getFcyComplex().getCpxGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCY_ID, (Object)this.getFcyId()));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.DUPLICATE_FACILITY_FOR_THIS_COMPLEX, (BizViolation)inBv, (IMetafieldId) IArgoField.FCY_ID, (Object)this.getFcyId(), (Object)this.getFcyComplex().getCpxId());
        }
        return inBv;
    }

    public boolean isFacilityRelayTo(Facility inOtherFacility) {
        boolean isRelay = false;
//        if (this.findFacilityRelayTo(inOtherFacility) != null) {
//            isRelay = true;
//        }
        return isRelay;
    }

//    @Nullable
//    public FacilityRelay findFacilityRelayTo(Facility inOtherFacility) {
//        FacilityRelay relay = null;
//        if (this.getFcyRelays() != null) {
//            for (Object o : this.getFcyRelays()) {
//                FacilityRelay itRelay = (FacilityRelay)o;
//                if (!itRelay.getFcyrelayToFacility().equals(inOtherFacility)) continue;
//                relay = itRelay;
//                break;
//            }
//        }
//        return relay;
//    }

//    @Nullable
//    public FacilityRelay findFacilityRelayToPoint(RoutingPoint inToPoint) {
//        FacilityRelay relay = null;
//        if (this.getFcyRelays() != null) {
//            for (Object o : this.getFcyRelays()) {
//                FacilityRelay itRelay = (FacilityRelay)o;
//                if (!itRelay.getFcyrelayToFacility().getFcyRoutingPoint().equals(inToPoint)) continue;
//                relay = itRelay;
//                break;
//            }
//        }
//        return relay;
//    }

    @Nullable
    public Yard getActiveYard() throws BizViolation {
        Yard yard = null;
        if (this.getFcyYrdSet() != null) {
            for (Object o : this.getFcyYrdSet()) {
                Yard y = (Yard)o;
                if (LifeCycleStateEnum.OBSOLETE.equals((Object)y.getLifeCycleState())) continue;
                if (yard != null) {
                    throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TWO_ACTIVE_YARDS_IN_FACILITY, null, (Object)yard.getYrdId(), (Object)y.getYrdId(), (Object)this.getFcyId());
                }
                yard = y;
            }
        }
        return yard;
    }

    public IScopeEnum getScopeEnum() {
        return ScopeEnum.FACILITY;
    }

    public IScopeNodeEntity getParent() {
        return (IScopeNodeEntity) this.getFcyComplex();
    }

    public Collection getChildren() {
        return this.getFcyYrdSet();
    }

    public String getId() {
        return this.getFcyId();
    }

    public String getPathName() {
        Complex complex = this.getFcyComplex();
        Operator operator = complex.getCpxOperator();
        return operator.getOprId() + "/" + complex.getCpxId() + "/" + this.getFcyId();
    }

    public String getFcyPathName() {
        return this.getPathName();
    }

    public TimeZone getTimeZone() {
        if (StringUtils.isEmpty((String)this.getFcyTimeZoneId())) {
            return TimeZone.getTimeZone(this.getFcyComplex().getCpxTimeZoneId());
        }
        return TimeZone.getTimeZone(this.getFcyTimeZoneId());
    }

    public void setRouteResolverRuleDto(Object inValue) {
//        EntityMappingPredicate oldRules = this.getFcyRouteResolverRules();
//        if (oldRules != null) {
//            HibernateApi.getInstance().delete((Object)oldRules);
//        }
//        EntityMappingPredicate rules = inValue == null ? null : new EntityMappingPredicate((IValueHolder)inValue);
//        this.setFcyRouteResolverRules(rules);
    }

//    @Nullable
//    public IValueHolder getRouteResolverRuleDto() {
//        EntityMappingPredicate rules = this.getFcyRouteResolverRules();
//        return rules == null ? null : rules.getEntityMappingVao();
//    }

    public boolean isRelayPoint(RoutingPoint inPoint) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FacilityRelay").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_FACILITY, (Object)this.getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) ArgoCompoundField.RELAY_TO_FCY_ROUTING_POINT, (Object)inPoint.getPointGkey()));
        return HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    public static Facility hydrate(Serializable inPrimaryKey) {
        return (Facility) HibernateApi.getInstance().load(Facility.class, inPrimaryKey);
    }
}
