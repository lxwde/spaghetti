package com.zpmc.ztos.infra.base.common.scopes;

import com.zpmc.ztos.infra.base.business.dataobject.FacilityRelayDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;

public class FacilityRelay extends FacilityRelayDO {
    public String toString() {
        return "fcy-" + this.getFcyrelayFacility().getFcyId() + " to " + this.getFcyrelayToFacility().getFcyId();
    }

    public static FacilityRelay loadByGkey(Long inGkey) {
        return (FacilityRelay) Roastery.getHibernateApi().load(FacilityRelay.class, (Serializable)inGkey);
    }

    public static FacilityRelay findFacilityRelay(Facility inFromFacility, Facility inToFacility) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FacilityRelay").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_FACILITY, (Object)inFromFacility.getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_TO_FACILITY, (Object)inToFacility.getFcyGkey()));
        return (FacilityRelay) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static FacilityRelay findFacilityRelayWithoutScoping(Facility inFromFacility, Facility inToFacility) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FacilityRelay").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_FACILITY, (Object)inFromFacility.getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_TO_FACILITY, (Object)inToFacility.getFcyGkey()));
        dq.setScopingEnabled(false);
        return (FacilityRelay)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static FacilityRelay findOrCreateFacilityRelay(Facility inFromFacility, Facility inToFacility, LocTypeEnum inCarrierModeDefault, Long inTransitTimeHrs, Long inCutoffLeadTimeHrs) {
        FacilityRelay relay = FacilityRelay.findFacilityRelay(inFromFacility, inToFacility);
        if (relay == null) {
            relay = new FacilityRelay();
            relay.setFcyrelayFacility(inFromFacility);
            relay.setFcyrelayToFacility(inToFacility);
        }
        relay.setFcyrelayCarrierModeDefault(inCarrierModeDefault);
        relay.setFcyrelayTransitTimeHrs(inTransitTimeHrs);
        relay.setFcyrelayCutoffLeadTimeHrs(inCutoffLeadTimeHrs);
        HibernateApi.getInstance().saveOrUpdate((Object)relay);
        return relay;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        BizViolation bv = null;
        super.validateChanges(inFieldChanges);
        if (inFieldChanges.hasFieldChange(IArgoField.FCYRELAY_FACILITY) || inFieldChanges.hasFieldChange(IArgoField.FCYRELAY_TO_FACILITY)) {
            bv = this.checkIfRelayAlreadyExists(bv);
            bv = this.checkIfSameFacility(bv);
        }
        return bv;
    }

    private BizViolation checkIfRelayAlreadyExists(BizViolation inBv) {
        BizViolation bv = inBv;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"FacilityRelay").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_FACILITY, (Object)this.getFcyrelayFacility().getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.FCYRELAY_TO_FACILITY, (Object)this.getFcyrelayToFacility().getFcyGkey()));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.DUPLICATE_FACILITY_RELAY, (BizViolation)inBv, (Object)this.getFcyrelayFacility().getFcyId(), (Object)this.getFcyrelayToFacility().getFcyId());
        }
        return bv;
    }

    private BizViolation checkIfSameFacility(BizViolation inBv) {
        Facility toFcy;
        BizViolation bv = inBv;
        Facility fromFcy = this.getFcyrelayFacility();
        if (fromFcy.equals(toFcy = this.getFcyrelayToFacility())) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.SAME_FACILITY_RELAY, (BizViolation)inBv, (Object)this.getFcyrelayFacility().getFcyId());
        }
        return bv;
    }

}
