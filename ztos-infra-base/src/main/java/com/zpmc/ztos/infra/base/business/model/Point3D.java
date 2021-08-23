package com.zpmc.ztos.infra.base.business.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Point3D {
    private double _x;
    private double _y;
    private double _z;

    public Point3D() {
        this._x = 0.0;
        this._y = 0.0;
        this._z = 0.0;
    }

    public Point3D(double inX, double inY, double inZ) {
        this._x = inX;
        this._y = inY;
        this._z = inZ;
    }

    public Point3D(DataInput inStream) throws IOException {
        this(inStream.readDouble(), inStream.readDouble(), inStream.readDouble());
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('{');
        b.append(this._x);
        b.append(',');
        b.append(this._y);
        b.append(',');
        b.append(this._z);
        b.append('}');
        return b.toString();
    }

    public void writeDataToStream(DataOutput inOutputStream) throws IOException {
        inOutputStream.writeDouble(this._x);
        inOutputStream.writeDouble(this._y);
        inOutputStream.writeDouble(this._z);
    }

    public int getBinaryLength() {
        return 24;
    }

    public double getX() {
        return this._x;
    }

    public void setX(double inX) {
        this._x = inX;
    }

    public double getY() {
        return this._y;
    }

    public void setY(double inY) {
        this._y = inY;
    }

    public double getZ() {
        return this._z;
    }

    public void setZ(double inZ) {
        this._z = inZ;
    }

    public boolean equals(Object inObj) {
        if (this == inObj) {
            return true;
        }
        if (inObj == null || this.getClass() != inObj.getClass()) {
            return false;
        }
        Point3D point3D = (Point3D)inObj;
        return Double.compare(point3D._x, this._x) == 0 && Double.compare(point3D._y, this._y) == 0 && Double.compare(point3D._z, this._z) == 0;
    }

    public int hashCode() {
        long temp = this._x != 0.0 ? Double.doubleToLongBits(this._x) : 0L;
        int result = (int)(temp ^ temp >>> 32);
        temp = this._y != 0.0 ? Double.doubleToLongBits(this._y) : 0L;
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = this._z != 0.0 ? Double.doubleToLongBits(this._z) : 0L;
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
}
