package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.UnLocCodeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CompassDirectionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PointTransitToModeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.StatusCodeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;

public class UnLocCode extends UnLocCodeDO {
    protected static final Logger LOGGER = Logger.getLogger(UnLocCode.class);

    public UnLocCode(EntitySet inUnLocRefSet, String inUnLocId) {
        this();
        this.setUnlocScope(inUnLocRefSet);
        this.setUnlocId(inUnLocId);
        this.setUnlocLatNorS(CompassDirectionEnum.NORTH);
        this.setUnlocLongEorW(CompassDirectionEnum.EAST);
    }

    public UnLocCode() {
        this.setUnlocIsPort(Boolean.FALSE);
        this.setUnlocIsRailTerminal(Boolean.FALSE);
        this.setUnlocIsRoadTerminal(Boolean.FALSE);
        this.setUnlocIsAirport(Boolean.FALSE);
        this.setUnlocIsMultimodal(Boolean.FALSE);
        this.setUnlocIsFixedTransport(Boolean.FALSE);
        this.setUnlocIsBorderCrossing(Boolean.FALSE);
        this.setUnlocIsFunctionUnknown(Boolean.FALSE);
        this.setUnlocLatNorS(CompassDirectionEnum.NORTH);
        this.setUnlocLongEorW(CompassDirectionEnum.EAST);
        this.setUnlocLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setUnlocLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getUnlocLifeCycleState();
    }

    public static UnLocCode findUnLocCode(String inUnLocId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnLocCode").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.UNLOC_ID, (Object)inUnLocId));
        return (UnLocCode) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static UnLocCode findUnLocCodeProxy(String inUnLocId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnLocCode").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.UNLOC_ID, (Object)inUnLocId));
        Serializable[] unLocGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (unLocGkey == null || unLocGkey.length == 0) {
            return null;
        }
        if (unLocGkey.length == 1) {
            return (UnLocCode)HibernateApi.getInstance().load(UnLocCode.class, unLocGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(unLocGkey.length), (Object)dq);
    }

    public static UnLocCode createUnLocCode(String inUnLocId, PointTransitToModeEnum inTransitToMode) {
        String empty = "";
        UnLocCode loc = new UnLocCode();
        loc.setUnlocId(inUnLocId);
//        RefCountry country = RefCountry.findCountry((String)inUnLocId.substring(0, 2));
//        if (country != null) {
//            loc.setUnlocCntry(country);
//        }
        loc.setUnlocPlaceCode(inUnLocId.substring(2, 5));
        loc.setUnlocPlaceName(inUnLocId.toLowerCase());
        loc.setUnlocIsPort(inTransitToMode == null || PointTransitToModeEnum.VESSEL.equals((Object)inTransitToMode) ? Boolean.TRUE : Boolean.FALSE);
        loc.setUnlocIsRailTerminal(PointTransitToModeEnum.RAIL.equals((Object)inTransitToMode) ? Boolean.TRUE : Boolean.FALSE);
        loc.setUnlocIsRoadTerminal(PointTransitToModeEnum.TRUCK.equals((Object)inTransitToMode) ? Boolean.TRUE : Boolean.FALSE);
        loc.setUnlocIsAirport(Boolean.FALSE);
        loc.setUnlocIsMultimodal(Boolean.FALSE);
        loc.setUnlocIsFixedTransport(Boolean.FALSE);
        loc.setUnlocIsBorderCrossing(Boolean.FALSE);
        loc.setUnlocIsFunctionUnknown(Boolean.FALSE);
        loc.setUnlocStatus(StatusCodeEnum.AA);
        loc.setUnlocSubDiv("");
        loc.setUnlocLatitude("");
        loc.setUnlocLatNorS(CompassDirectionEnum.NORTH);
        loc.setUnlocLongitude("");
        loc.setUnlocLongEorW(CompassDirectionEnum.EAST);
        loc.setUnlocRemarks("created on the fly - fix me!");
        HibernateApi.getInstance().save((Object)loc);
        LOGGER.info((Object)("createUnLocCode: " + loc.getUnlocId()));
        return loc;
    }

    public String toString() {
        return "UnLocCode Id:" + this.getUnlocId();
    }

    public static UnLocCode findOrCreateUnLocCode(String inUnLocId) {
        UnLocCode unLocCode = UnLocCode.findUnLocCode(inUnLocId);
        if (unLocCode == null) {
            unLocCode = UnLocCode.createUnLocCode(inUnLocId, PointTransitToModeEnum.VESSEL);
        }
        return unLocCode;
    }

    public static UnLocCode findOrCreateUnLocCodeByPlaceCode(String inPlaceCode) {
        if (inPlaceCode.length() != 3) {
            LOGGER.error((Object)("findOrCreateUnLocCodeByPortCode: expected three character code as input, but received:  <" + inPlaceCode + ">"));
            return UnLocCode.findOrCreateUnLocCode("?????");
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnLocCode").addDqPredicate(PredicateFactory.like((IMetafieldId) IArgoRefField.UNLOC_PLACE_CODE, (String)inPlaceCode));
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (matches.size() == 1) {
            return (UnLocCode)matches.get(0);
        }
        if (matches.size() > 1) {
            LOGGER.warn((Object)("findOrCreateUnLocCodeByPortCode: multiple posible matches for  <" + inPlaceCode + ">, so guessing!"));
            return (UnLocCode)matches.get(0);
        }
        LOGGER.warn((Object)("findOrCreateUnLocCodeByPortCode: no match for  <" + inPlaceCode + ">, so adding to UnLoc table"));
        return UnLocCode.createUnLocCode("??" + inPlaceCode, PointTransitToModeEnum.VESSEL);
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.UNLOC_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.UNLOC_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }
}
