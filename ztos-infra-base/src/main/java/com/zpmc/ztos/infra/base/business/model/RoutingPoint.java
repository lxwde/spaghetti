package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.RoutingPointDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PointTransitToModeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public class RoutingPoint extends RoutingPointDO {

    public static final String ENCODING_UN_LOC_CODE = "UNLOCCODE";
    public static final String ENCODING_SCHED_D = "SCHED_D";
    public static final String ENCODING_SCHED_K = "SCHED_K";
    public static final String ENCODING_SPLC_CODE = "SPLC";
    public static final String ENCODING_PORT_CODE = "PORT_CODE";
    private static final Logger LOGGER = Logger.getLogger((String) RoutingPoint.class.getName());

    public RoutingPoint() {
        this.setPointLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setPointDataSource(DataSourceEnum.UNKNOWN);
        this.setPointUnusedColumn1(PointTransitToModeEnum.UNKNOWN);
        this.setPointIsPlaceholderPoint(Boolean.FALSE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setPointLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getPointLifeCycleState();
    }

    public static RoutingPoint findRoutingPoint(String inPointId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_ID, (Object)inPointId));
        return (RoutingPoint) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static RoutingPoint findRoutingPointProxy(String inPointId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_ID, (Object)inPointId));
        Serializable[] pointGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (pointGkey == null || pointGkey.length == 0) {
            return null;
        }
        if (pointGkey.length == 1) {
            return (RoutingPoint)HibernateApi.getInstance().load(RoutingPoint.class, pointGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(pointGkey.length), (Object)dq);
    }

    public static RoutingPoint createRoutingPoint(String inPointId, UnLocCode inPointUnLoc, RoutingPoint inPointActualPOD) {
        RoutingPoint point = new RoutingPoint();
        point.setPointId(inPointId);
        point.setPointUnLoc(inPointUnLoc);
        point.setPointUnusedColumn1(PointTransitToModeEnum.UNKNOWN);
        point.setPointActualPOD(inPointActualPOD);
        HibernateApi.getInstance().save((Object)point);
        if (inPointActualPOD == null) {
            point.setPointActualPOD(point);
        }
        HibernateApi.getInstance().saveOrUpdate((Object)point);
        LOGGER.info((Object)("createRoutingPoint: Created RoutingPoint <" + point.getPointId() + ">"));
        return point;
    }

    public static RoutingPoint importSparcsPortCode(String inPortCode, String inPortName, String inActualPod, String inTransitToMode, String inUnLocId) {
        RoutingPoint point = StringUtils.isEmpty((String)inUnLocId) || inUnLocId.length() < 5 ? RoutingPoint.resolveRoutingPointFromPortCode(inPortCode) : RoutingPoint.findOrCreateRoutingPoint(inPortCode, inUnLocId);
        HibernateApi.getInstance().save((Object)point);
        RoutingPoint actualPod = point;
        if (!StringUtils.isEmpty((String)inActualPod) && !StringUtils.equals((String)inActualPod, (String)inPortCode) && (actualPod = RoutingPoint.findRoutingPoint(inActualPod)) == null) {
            LOGGER.warn((Object)("importSparcsPortCode: could not resolve actual POD '" + actualPod + "' when importing port code '" + inPortCode + "'"));
            actualPod = point;
        }
        point.setPointActualPOD(actualPod);
        point.setPointTerminal(inPortName);
        UnLocCode un = point.getPointUnLoc();
        if (un.getUnlocPlaceName() == null) {
            un.setUnlocPlaceName(inPortName);
        }
        HibernateApi.getInstance().saveOrUpdate((Object)point);
        return point;
    }

    public static RoutingPoint findOrCreateRoutingPoint(String inPointId, String inUnLocCode) {
        UnLocCode un = UnLocCode.findOrCreateUnLocCode(inUnLocCode);
        RoutingPoint rp = RoutingPoint.findRoutingPoint(inPointId);
        if (rp == null) {
            rp = RoutingPoint.createRoutingPoint(inPointId, un, null);
        } else if (!ObjectUtils.equals((Object)un, (Object)rp.getPointUnLoc())) {
            UnLocCode existing = rp.getPointUnLoc();
            String existingId = existing == null ? null : existing.getUnlocId();
            LOGGER.info((Object)("findOrCreateRoutingPoint: persisted RoutingPoint has different UnLoc than specified, rp = " + rp + " inUnLocCode = " + inUnLocCode + " point's existing UN = " + existingId));
        }
        return rp;
    }

    @Nullable
    public static RoutingPoint resolveRoutingPointFromEncoding(String inEncodingScheme, String inCode) {
        RoutingPoint point;
        if (ENCODING_UN_LOC_CODE.equals(inEncodingScheme)) {
            point = RoutingPoint.resolveRoutingPointFromUnLoc(inCode);
        } else if (ENCODING_SCHED_D.equals(inEncodingScheme) || ENCODING_SCHED_K.equals(inEncodingScheme)) {
            point = RoutingPoint.resolveRoutingPointFromScheduleCode(inEncodingScheme, inCode);
        } else {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint");
            if (ENCODING_SPLC_CODE.equals(inEncodingScheme)) {
                dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_SPLC_CODE, (Object)inCode));
            } else if (ENCODING_PORT_CODE.equals(inEncodingScheme)) {
                dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_ID, (Object)inCode));
            } else {
                return RoutingPoint.resolveRoutingPointFromPortCode(inCode);
            }
            dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
            point = (RoutingPoint)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        }
        return point;
    }

    @Nullable
    public static RoutingPoint resolveRoutingPointFromScheduleCode(String inEncodingScheme, String inScheduleCode) {
        RoutingPoint point = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint");
        if (ENCODING_SCHED_D.equals(inEncodingScheme)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_SCHEDULE_D_CODE, (Object)inScheduleCode));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.POINT_SCHEDULE_K_CODE, (Object)inScheduleCode));
        }
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        Serializable[] points = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (points.length == 1) {
            point = RoutingPoint.hydrate(points[0]);
        } else if (points.length != 0) {
            point = RoutingPoint.hydrate(points[0]);
            LOGGER.warn((Object)("resolveRoutingPoint: guessing routing point <" + point.getPointId() + "> for given " + inEncodingScheme + " code <" + inScheduleCode + ">"));
        }
        return point;
    }

    public static RoutingPoint hydrate(Serializable inPrimaryKey) {
        return (RoutingPoint)HibernateApi.getInstance().load(RoutingPoint.class, inPrimaryKey);
    }

    public static RoutingPoint resolveRoutingPointFromUnLoc(String inUnLocCode) {
        RoutingPoint routingPoint = null;
        UnLocCode un = UnLocCode.findUnLocCode(inUnLocCode);
        if (un != null) {
            IMetafieldId pointUnLocId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.POINT_UN_LOC, (IMetafieldId) IArgoRefField.UNLOC_ID);
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"RoutingPoint").addDqPredicate(PredicateFactory.eq((IMetafieldId)pointUnLocId, (Object)inUnLocCode));
            dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
            List points = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
            if (points.size() == 1) {
                routingPoint = (RoutingPoint)points.get(0);
            } else if (points.isEmpty()) {
                routingPoint = RoutingPoint.findOrCreateRoutingPoint(un.getUnlocPlaceCode(), inUnLocCode);
            } else {
                for (Object rp : points) {
                    if (!StringUtils.equals((String)((RoutingPoint)rp).getPointId(), (String)un.getUnlocPlaceCode())) continue;
                    routingPoint = (RoutingPoint)rp;
                }
                if (routingPoint == null) {
                    routingPoint = (RoutingPoint)points.get(0);
                    LOGGER.warn((Object)("resolveRoutingPoint: guessing routing point <" + routingPoint.getPointId() + "> for <" + inUnLocCode + ">"));
                }
            }
        }
        if (routingPoint == null) {
            LOGGER.info((Object)("resolveRoutingPointFromUnLoc: could not resolve UnLoc code <" + inUnLocCode + ">"));
        }
        return routingPoint;
    }

    public static RoutingPoint resolveRoutingPointFromPortCode(String inPortCode) {
        RoutingPoint routingPoint = RoutingPoint.findRoutingPoint(inPortCode);
        if (routingPoint == null) {
            IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnLocCode").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.UNLOC_PLACE_CODE, (Object)inPortCode)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.UNLOC_IS_PORT, (Object) Boolean.TRUE));
            List uns = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
            if (!uns.isEmpty()) {
                UnLocCode un = (UnLocCode)uns.get(0);
                routingPoint = RoutingPoint.findOrCreateRoutingPoint(un.getUnlocPlaceCode(), un.getUnlocId());
                if (uns.size() > 1) {
                    LOGGER.warn((Object)("resolveRoutingPoint: guessed routing point <" + routingPoint.getPointId() + "> is for <" + un.getUnlocId() + ">"));
                }
            } else if (inPortCode != null && inPortCode.length() == 3) {
                routingPoint = RoutingPoint.findOrCreateRoutingPoint(inPortCode, "XXXXX");
                LOGGER.warn((Object)("resolveRoutingPoint: could not find a UnLoc for <" + routingPoint.getPointId() + ">"));
            }
        }
        if (routingPoint == null) {
            LOGGER.warn((Object)("resolveRoutingPoint: could not resolve location code <" + inPortCode + ">"));
        }
        return routingPoint;
    }

    public String toString() {
        return "RoutingPointId:" + this.getPointId();
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        return bv;
    }

    @Nullable
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        bv = this.validateScheduleCodeChange(bv, inChanges);
        return bv;
    }

    private BizViolation validateScheduleCodeChange(BizViolation inBizViolation, FieldChanges inChanges) {
        for (IMetafieldId scheduleMfId : new IMetafieldId[]{IArgoRefField.POINT_SCHEDULE_D_CODE, IArgoRefField.POINT_SCHEDULE_K_CODE}) {
            String scheduleCode;
            if (!inChanges.hasFieldChange(scheduleMfId) || !StringUtils.isNotBlank((String)(scheduleCode = (String)inChanges.getFieldChange(scheduleMfId).getNewValue()))) continue;
            try {
                Long.parseLong(scheduleCode);
            }
            catch (NumberFormatException inNumberFormatException) {
                inBizViolation = BizViolation.createFieldViolation((IPropertyKey)IFrameworkPropertyKeys.VALIDATION__INVALID_LONG_STRING, (BizViolation)inBizViolation, (IMetafieldId)scheduleMfId, (Object)scheduleCode);
            }
        }
        return inBizViolation;
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        if (ObjectUtils.equals((Object)this, (Object)this.getPointActualPOD())) {
            this.setSelfAndFieldChange(IArgoRefField.POINT_ACTUAL_P_O_D, null, inOutMoreChanges);
        }
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        if (this.getPointActualPOD() == null) {
            this.setPointActualPOD(this);
        }
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.POINT_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.POINT_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public String getPointUnlocId() {
        UnLocCode theUNLocCode = this.getPointUnLoc();
        if (theUNLocCode == null) {
            return null;
        }
        return theUNLocCode.getUnlocId();
    }

    public Boolean isPointPlaceholderPoint() {
        return this.getPointIsPlaceholderPoint() != null && this.getPointIsPlaceholderPoint() != false;
    }

    public void updatePointSplcCode(String inPointSplcCode) {
        super.setPointSplcCode(inPointSplcCode);
    }

}
