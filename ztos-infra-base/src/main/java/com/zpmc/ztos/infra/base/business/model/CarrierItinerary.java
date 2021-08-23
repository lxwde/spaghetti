package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.CarrierItineraryDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public class CarrierItinerary extends CarrierItineraryDO {
    private static final Logger LOGGER = Logger.getLogger(CarrierItinerary.class);

    public CarrierItinerary() {
        this.setItinLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setItinLifeCycleState(inLifeCycleState);
    }

    public static CarrierItinerary findOrCreateCarrierItinerary(String inId, List inCallList) {
        CarrierItinerary itinerary = CarrierItinerary.findCarrierItinerary(inId);
        if (itinerary == null) {
            itinerary = CarrierItinerary.createCarrierItinerary(inId, inCallList);
        }
        return itinerary;
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getItinLifeCycleState();
    }

    public static CarrierItinerary findCarrierItinerary(String inItineraryId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierItinerary").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.ITIN_ID, (Object)inItineraryId));
        return (CarrierItinerary) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static CarrierItinerary findCarrierItineraryProxy(String inItineraryId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"CarrierItinerary").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.ITIN_ID, (Object)inItineraryId));
        Serializable[] itinGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (itinGkey == null || itinGkey.length == 0) {
            return null;
        }
        if (itinGkey.length == 1) {
            return (CarrierItinerary)HibernateApi.getInstance().load(CarrierItinerary.class, itinGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(itinGkey.length), (Object)dq);
    }

    public static CarrierItinerary createCarrierItinerary(String inId, List inCallList) {
        CarrierItinerary itinerary = new CarrierItinerary();
        itinerary.setItinId(inId);
        itinerary.setItinPoints(inCallList);
        HibernateApi.getInstance().saveOrUpdate((Object)itinerary);
        return itinerary;
    }

    public static CarrierItinerary makeDeviantItinerary(CarrierItinerary inSourceItinerary, CarrierVisit inOwnerCv) {
        CarrierItinerary itinerary = new CarrierItinerary();
//        String id = inSourceItinerary.getItinId() + '-' + inOwnerCv.getCvId();
//        if (inOwnerCv.getCvFacility() != null) {
//            id = id + '-' + inOwnerCv.getCvFacility().getId();
//        }
//        itinerary.setItinId(id);
        itinerary.setItinOwnerCv(inOwnerCv);
        HibernateApi.getInstance().save((Object)itinerary);
        return itinerary;
    }

    public PointCall addCarrierCall(String inCallPointId, String inScanCode, String[] inDestinations) {
        String callNumber = String.valueOf(this.getNextCallNumber(inCallPointId));
        return this.addCarrierCall(inCallPointId, callNumber, inScanCode, inDestinations);
    }

    public PointCall addCarrierCall(String inCallPointId, String inCallNumber, String inScanCode, String[] inDestinations) {
        return this.addCarrierCall(inCallPointId, inCallNumber, inScanCode, inDestinations, null);
    }

    public PointCall addCarrierCall(String inCallPointId, String inCallNumber, String inScanCode, String[] inDestinations, Double inTransitDuration) {
        RoutingPoint rp = RoutingPoint.resolveRoutingPointFromPortCode(inCallPointId);
        if (rp == null) {
            throw BizFailure.create((String)("Unknown RoutingPoint: " + inCallPointId));
        }
        PointCall call = new PointCall();
        List<PointCall> itinPoints = this.ensurePointCallList();
        ArrayList<Destination> callDests = new ArrayList<Destination>();
        for (String inDestination : inDestinations) {
            callDests.add(this.createDestination(inDestination, call));
        }
        call.setCallItinerary(this);
        call.setCallNumber(inCallNumber);
        call.setCallOrder(Long.valueOf(itinPoints.size()));
        call.setCallScanCode(inScanCode);
        call.setCallPoint(rp);
        call.setCallDestinations(callDests);
        if (inTransitDuration != null) {
            call.setCallTransitDuration(inTransitDuration);
        }
        itinPoints.add(call);
        return call;
    }

    public boolean isDeviant() {
        return this.getItinOwnerCv() != null;
    }

    private List<PointCall> ensurePointCallList() {
        ArrayList itinPoints = (ArrayList) this.getItinPoints();
        if (itinPoints == null) {
            itinPoints = new ArrayList();
            this.setItinPoints(itinPoints);
        }
        return itinPoints;
    }

    private Destination createDestination(String inDestPointId, PointCall inCall) {
        RoutingPoint rp = RoutingPoint.resolveRoutingPointFromPortCode(inDestPointId);
        if (rp == null) {
            throw BizFailure.create((String)("Unknown RoutingPoint: " + inDestPointId));
        }
        Destination dest = new Destination();
        dest.setDestCall(inCall);
        dest.setDestPoint(rp);
        return dest;
    }

    public IValueHolder[] getItinCallVaa() {
        List pointCalls = this.getItinPoints();
        IValueHolder[] callVaos = new IValueHolder[pointCalls.size()];
        MetafieldIdList fields = new MetafieldIdList();
        fields.add(ICarrierField.CALL_POINT_ID);
        fields.add(ICarrierField.CALL_ORDER);
        fields.add(ICarrierField.CALL_NUMBER);
        fields.add(ICarrierField.CALL_SCAN_CODE);
        fields.add(ICarrierField.CALL_PLACE_NAME);
        fields.add(ICarrierField.CALL_UNLOC_ID);
        fields.add(IArgoBizMetafield.CALL_DEST_VAA);
        fields.add(IArgoBizMetafield.CALL_TRANSIT_DURATION_HOURS);
        int i = 0;
        for (Object pointCall : pointCalls) {
            callVaos[i++] = ((PointCall)pointCall).getValueObject(fields);
        }
        return callVaos;
    }

    public void updateRoute(Object inRouteDto) {
        if (!(inRouteDto instanceof IValueHolder)) {
            LOGGER.error((Object)("updateRoute: DTO does not conform, update not made: " + inRouteDto));
            return;
        }
        List<PointCall> points = this.ensurePointCallList();
        points.clear();
        IValueHolder initVao = (IValueHolder)inRouteDto;
        IValueHolder[] callVaa = (IValueHolder[])initVao.getFieldValue(ICarrierField.ITIN_CALL_VAA);
        if (callVaa != null && callVaa.length > 0) {
            for (IValueHolder callVao : callVaa) {
                String portCode = callVao.getFieldValue(ICarrierField.CALL_POINT_ID).toString();
                IValueHolder[] destVaoArray = (IValueHolder[])callVao.getFieldValue(ICarrierField.CALL_DEST_VAA);
                String[] dests = new String[destVaoArray.length];
                for (int j = 0; j < destVaoArray.length; ++j) {
                    dests[j] = destVaoArray[j].getFieldValue(ICarrierField.DEST_POINT_ID).toString();
                }
                String scanCode = callVao.getFieldValue(ICarrierField.CALL_SCAN_CODE).toString();
                if (scanCode == null) {
                    scanCode = portCode.substring(0, 1);
                }
                Double transitDurationDays = (Double)callVao.getFieldValue(IArgoBizMetafield.CALL_TRANSIT_DURATION_HOURS);
                String callNumber = this.getNextCallNumber(portCode);
                this.addCarrierCall(portCode, callNumber, scanCode, dests, transitDurationDays);
            }
        }
    }

    public String getNextCallNumber(String inCallPointId) {
        if (this.getItinPoints() != null) {
            ArrayList points = new ArrayList(this.getItinPoints());
            Collections.sort(points, new PointCallNumberComparator());
            for (Object pointCall : points) {
                if (pointCall == null || !((PointCall)pointCall).getCallPoint().getPointId().equals(inCallPointId)) continue;
                String callNumber = ((PointCall)pointCall).getCallNumber();
                int intValue = Integer.parseInt(callNumber);
                return intValue + 1 + "";
            }
        }
        return "1";
    }

    public boolean isPointInItinerary(RoutingPoint inPoint) {
        return this.isPrimaryPoint(inPoint) || this.isSubPoint(inPoint);
    }

    public boolean isSubPoint(RoutingPoint inLoadPoint, RoutingPoint inDischPoint, RoutingPoint inPoint) {
        boolean loadPointProvided = inLoadPoint != null;
        boolean foundLoadCall = !loadPointProvided;
        List itinPoints = this.getItinPoints();
        if (itinPoints != null && inPoint != null) {
            for (Object call : this.getItinPoints()) {
                if (call == null) continue;
                RoutingPoint callPoint = ((PointCall)call).getCallPoint();
                if (!foundLoadCall) {
                    foundLoadCall = callPoint.equals(inLoadPoint);
                    continue;
                }
                if (!callPoint.equals(inDischPoint)) continue;
//                String[] dests = call.getCallDests();
//                for (int i = 0; i < dests.length; ++i) {
//                    String dest = dests[i];
//                    if (!dest.equals(inPoint.getPointId())) continue;
//                    return true;
//                }
                if (!loadPointProvided) continue;
                return false;
            }
        }
        return false;
    }

    public boolean isPointAfterCall(RoutingPoint inPod, RoutingPoint inPol, String inPolCallNumber) {
        Iterator it = this.getItinPoints().iterator();
        boolean foundPol = false;
        while (it.hasNext()) {
            PointCall call = (PointCall)it.next();
            if (foundPol) {
                if (!ObjectUtils.equals((Object)inPod, (Object)call.getCallPoint())) continue;
                return true;
            }
            if (!ObjectUtils.equals((Object)inPol, (Object)call.getCallPoint()) || !ObjectUtils.equals((Object)inPolCallNumber, (Object)call.getCallNumber())) continue;
            foundPol = true;
        }
        return false;
    }

    public HashMap getCountriesVisited(RoutingPoint inStartPoint, String inStartCallNumber, RoutingPoint inEndPoint) {
        RefCountry country;
        HashMap<String, RefCountry> countries = new HashMap<String, RefCountry>();
        boolean foundStartPoint = false;
        boolean foundEndPoint = false;
        for (Object call : this.getItinPoints()) {
            if (foundStartPoint) {
                country = ((PointCall)call).getCallPoint().getPointUnLoc().getUnlocCntry();
//                countries.put(country.getCntryCode(), country);
//                if (!ObjectUtils.equals((Object)inEndPoint, (Object)call.getCallPoint())) continue;
//                foundEndPoint = true;
                break;
            }
//            if (!ObjectUtils.equals((Object)inStartPoint, (Object)call.getCallPoint()) || !ObjectUtils.equals((Object)inStartCallNumber, (Object)call.getCallNumber())) continue;
            foundStartPoint = true;
        }
        if (!foundEndPoint) {
            PointCall call;
            Iterator it = this.getItinPoints().iterator();
            while (!(!it.hasNext() || ObjectUtils.equals((Object)inStartPoint, (Object)(call = (PointCall)it.next()).getCallPoint()) && ObjectUtils.equals((Object)inStartCallNumber, (Object)call.getCallNumber()))) {
                country = call.getCallPoint().getPointUnLoc().getUnlocCntry();
  //              countries.put(country.getCntryCode(), country);
                if (!ObjectUtils.equals((Object)inEndPoint, (Object)call.getCallPoint())) continue;
                break;
            }
        }
        return countries;
    }

    public boolean isPointBeforeCall(RoutingPoint inPol, RoutingPoint inPod, String inPodCallNumber) {
        Iterator it = this.getItinPoints().iterator();
        boolean foundPol = false;
        while (it.hasNext()) {
            PointCall call = (PointCall)it.next();
            if (foundPol) {
                if (!ObjectUtils.equals((Object)inPod, (Object)call.getCallPoint()) || !ObjectUtils.equals((Object)inPodCallNumber, (Object)call.getCallNumber())) continue;
                return true;
            }
            if (!ObjectUtils.equals((Object)inPol, (Object)call.getCallPoint())) continue;
            foundPol = true;
        }
        return false;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        super.validateChanges(inFieldChanges);
        BizViolation bizViolation = null;
        if (!this.isUniqueInClass(ICarrierField.ITIN_ID, true)) {
            bizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CARRIER_ITINIARY_WITH_SAME_ID_ALREADY_EXISTS, bizViolation, (Object)this.getItinId());
        }
        return bizViolation;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public boolean isPrimaryPoint(RoutingPoint inPoint) {
        return this.primaryPoint(inPoint, new PrimaryPointLocator()) != null;
    }

    public RoutingPoint primaryPoint(RoutingPoint inSubPoint) {
        return this.primaryPoint(inSubPoint, new SubPointLocator());
    }

    public boolean isSubPoint(RoutingPoint inPoint) {
        return this.primaryPoint(inPoint) != null;
    }

    public static CarrierItinerary hydrate(Serializable inPrimaryKey) {
        return (CarrierItinerary)HibernateApi.getInstance().load(CarrierItinerary.class, inPrimaryKey);
    }

    private RoutingPoint primaryPoint(RoutingPoint inPoint, IPointLocator inPointLocator) {
        List itinPoints = this.getItinPoints();
        if (itinPoints != null) {
            for (Object call : this.getItinPoints()) {
                RoutingPoint point = inPointLocator.locate(inPoint, (PointCall)call);
                if (point == null) continue;
                return point;
            }
        }
        return null;
    }

    public String getHumanReadableKey() {
        return this.getItinId();
    }

    public long getTransitTime(RoutingPoint inPoint1, RoutingPoint inPoint2, String inPoint1CallNumber) {
        PointCall call;
        Iterator it = this.getItinPoints().iterator();
        PointCall point1Call = null;
        while (it.hasNext()) {
            call = (PointCall)it.next();
            if (!inPoint1.equals(call.getCallPoint()) || inPoint1CallNumber == null || !inPoint1CallNumber.equals(call.getCallNumber())) continue;
            point1Call = call;
            break;
        }
        if (point1Call != null && it.hasNext()) {
            call = (PointCall)it.next();
            if (inPoint2 != null && call != null && inPoint2.equals(call.getCallPoint())) {
                return point1Call.getCallTransitTimeMin() == null ? 0L : point1Call.getCallTransitTimeMin();
            }
        }
        return 0L;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(" Itinerary: ");
        sb.append(this.getItinId()).append(" : ").append(this.getItinGkey()).append(" : ");
        List itinPoints = this.getItinPoints();
        if (itinPoints != null && !itinPoints.isEmpty()) {
            int i = 0;
            for (Object itinPoint : itinPoints) {
                PointCall call = (PointCall)itinPoint;
                if (call == null) continue;
                if (i++ == 0) {
                    sb.append(' ');
                } else {
                    sb.append(',');
                }
                if (call.getCallPoint() != null) {
                    sb.append(call.getCallPoint().getPointId());
                }
                sb.append(call.getCallNumber());
                List destSet = call.getCallDestinations();
                if (destSet == null) continue;
                int j = 0;
                for (Object aDestSet : destSet) {
                    Destination dest = (Destination)aDestSet;
                    if (j++ == 0) {
                        sb.append("[");
                    } else {
                        sb.append(",");
                    }
                    if (dest.getDestPoint() == null) continue;
                    sb.append(dest.getDestPoint().getPointId());
                }
                if (j <= 0) continue;
                sb.append("]");
            }
        }
        return sb.toString();
    }

    private class PointCallNumberComparator
            implements Comparator {
        private PointCallNumberComparator() {
        }

        public int compare(Object inObj1, Object inObj2) {
            int callNumber2;
            if (inObj1 == null || inObj2 == null) {
                return 0;
            }
            PointCall call1 = (PointCall)inObj1;
            PointCall call2 = (PointCall)inObj2;
            if (call1.getCallNumber() == null || call2.getCallNumber() == null) {
                return 0;
            }
            int callNumber1 = Integer.parseInt(call1.getCallNumber());
            if (callNumber1 == (callNumber2 = Integer.parseInt(call2.getCallNumber()))) {
                return 0;
            }
            if (callNumber1 < callNumber2) {
                return 1;
            }
            return -1;
        }
    }

    private class SubPointLocator
            implements IPointLocator {
        private SubPointLocator() {
        }

        @Override
        public RoutingPoint locate(RoutingPoint inPoint, PointCall inPointCall) {
            if (inPoint == null || inPointCall == null) {
                return null;
            }
            String[] dests = inPointCall.getCallDests();
            if (dests == null) {
                return null;
            }
            for (int i = 0; i < dests.length; ++i) {
                String dest = dests[i];
                if (!inPoint.getPointId().equals(dest)) continue;
                return inPointCall.getCallPoint();
            }
            return null;
        }
    }

    private class PrimaryPointLocator
            implements IPointLocator {
        private PrimaryPointLocator() {
        }

        @Override
        public RoutingPoint locate(RoutingPoint inPoint, PointCall inPointCall) {
            RoutingPoint callPoint;
            RoutingPoint routingPoint = callPoint = inPointCall == null ? null : inPointCall.getCallPoint();
            if (ObjectUtils.equals((Object)inPoint, (Object)callPoint)) {
                return inPoint;
            }
            return null;
        }
    }

    private static interface IPointLocator {
        public RoutingPoint locate(RoutingPoint var1, PointCall var2);
    }
}
