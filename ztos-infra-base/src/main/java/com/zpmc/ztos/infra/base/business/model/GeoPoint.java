package com.zpmc.ztos.infra.base.business.model;

public class GeoPoint {
    private double _longitude;
    private double _latitude;
    private double _altitudeMeters;
    public static final double CM_PER_M = 100.0;
    public static final double M_PER_KM = 1000.0;
    public static final double CM_PER_KM = 100000.0;
    public static final double EARTH_RADIUS_CM = 6.371009E8;
    public static final double KM_PER_DEG = 111.19508372419142;
    public static final double DEG_PER_KM = 0.008993203354928916;

    public GeoPoint() {
        this._longitude = 0.0;
        this._latitude = 0.0;
        this._altitudeMeters = 0.0;
    }

    public GeoPoint(double inLatitude, double inLongitude, double inInAltitude) {
        this._longitude = inLongitude;
        this._latitude = inLatitude;
        this._altitudeMeters = inInAltitude;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('{');
        b.append(this._longitude);
        b.append(',');
        b.append(this._latitude);
        b.append(',');
        b.append(this._altitudeMeters);
        b.append('}');
        return b.toString();
    }

    public double getLongitude() {
        return this._longitude;
    }

    public double getLatitude() {
        return this._latitude;
    }

    public double getAltitudeMeters() {
        return this._altitudeMeters;
    }

    public boolean equals(Object inObj) {
        if (this == inObj) {
            return true;
        }
        if (inObj == null || this.getClass() != inObj.getClass()) {
            return false;
        }
        GeoPoint geoPoint = (GeoPoint)inObj;
        return Double.compare(geoPoint._longitude, this._longitude) == 0 && Double.compare(geoPoint._latitude, this._latitude) == 0 && Double.compare(geoPoint._altitudeMeters, this._altitudeMeters) == 0;
    }

    public int hashCode() {
        int result = 17;
        long temp = this._longitude != 0.0 ? Double.doubleToLongBits(this._longitude) : 0L;
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = this._latitude != 0.0 ? Double.doubleToLongBits(this._latitude) : 0L;
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = this._altitudeMeters != 0.0 ? Double.doubleToLongBits(this._altitudeMeters) : 0L;
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public double computeDistanceToCm(GeoPoint inToGeoPoint) {
        double lat1 = this.getLatitude();
        double lat2 = inToGeoPoint.getLatitude();
        double lon1 = this.getLongitude();
        double lon2 = inToGeoPoint.getLongitude();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);
        double c = 2.0 * Math.asin(Math.sqrt(a));
        return 6.371009E8 * c;
    }

    public double computeAzimuthToDegrees(GeoPoint inToGeoPoint) {
        double lat1 = this.getLatitude();
        double lat2 = inToGeoPoint.getLatitude();
        double lon1 = this.getLongitude();
        double lon2 = inToGeoPoint.getLongitude();
        double dLon = Math.toRadians(lon2 - lon1);
        double x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) - Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(dLon);
        double y = Math.sin(dLon) * Math.cos(Math.toRadians(lat2));
        double preMod = Math.atan2(y, x);
        if (preMod < 0.0) {
            preMod += Math.PI * 2;
        }
        double angleRadians = preMod % (Math.PI * 2);
        return Math.toDegrees(angleRadians);
    }

    public static double getCartesianDegreesFromCompassDegrees(double inCompassAngleInDegrees) {
        double compassAngleInDegrees = inCompassAngleInDegrees % 360.0;
        if (compassAngleInDegrees < 0.0) {
            compassAngleInDegrees += 360.0;
        }
        if (compassAngleInDegrees >= 0.0 && compassAngleInDegrees < 270.0) {
            return 90.0 - compassAngleInDegrees;
        }
        return 450.0 - compassAngleInDegrees;
    }

    public static double getCompassDegreesFromCartesianDegrees(double inCartesianAngleInDegrees) {
        int factor;
        double cartesianAngleInDegrees = inCartesianAngleInDegrees;
        int n = factor = cartesianAngleInDegrees < 0.0 ? 1 : -1;
        while (Math.abs(cartesianAngleInDegrees) > 180.0) {
            cartesianAngleInDegrees += (double)(factor * 360);
        }
        if (cartesianAngleInDegrees > 90.0 && cartesianAngleInDegrees <= 180.0) {
            return 450.0 - cartesianAngleInDegrees;
        }
        return 90.0 - cartesianAngleInDegrees;
    }
}
