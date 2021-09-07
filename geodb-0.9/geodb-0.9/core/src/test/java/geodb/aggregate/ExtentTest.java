package geodb.aggregate;

import static geodb.GeoDBTestUtils.createPoint;
import static geodb.GeoDBTestUtils.createPolygon;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.locationtech.jts.geom.Point;
import geodb.GeoDB;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class ExtentTest {

    private Extent extent;

    @Before
    public void initExtent() throws SQLException {
        extent = new Extent();
        extent.init(null);
    }

    @Test
    public void extentEmptyCollection() throws SQLException {
        Object result = extent.getResult();
        assertThat(result, is(nullValue()));
    }

    @Test
    public void extentOnePoint() throws SQLException {
        extent.add(createPoint(3, 5));
        Object result = extent.getResult();
        assertThat(result, is(not(nullValue())));
        Geometry envelope = GeoDB.gFromWKB((byte[]) result);
        assertThat(envelope.getArea(), is(0.0));

        assertThat(envelope, is(instanceOf(Point.class)));
        assertThat((Point)envelope, is(equalTo(createPoint(3, 5))));
    }

    @Test
    public void extentTwoPoints() throws SQLException {
        extent.add(createPoint(3, 5));
        extent.add(createPoint(5, 3));
        Object result = extent.getResult();
        assertThat(result, is(not(nullValue())));
        Geometry envelope = GeoDB.gFromWKB((byte[]) result);

        assertThat(envelope.getArea(), is(4.0));
        assertTrue(envelope.contains(createPoint(4, 4)));
        assertTrue(envelope.contains(createPoint(4.99999, 4.9999)));
        assertFalse(envelope.contains(createPoint(5.00001, 5.00001)));
    }

    @Test
    public void extentTrianglePolygon() throws SQLException {
        extent.add(createPolygon(0, 1, 2, 2, 2, 0, 0, 1));
        Object result = extent.getResult();
        assertThat(result, is(not(nullValue())));
        Geometry envelope = GeoDB.gFromWKB((byte[]) result);

        assertThat(envelope.getArea(), is(4.0));
        assertTrue(envelope.contains(createPoint(1, 1)));
        assertTrue(envelope.contains(createPoint(1.99999, 1.9999)));
        assertFalse(envelope.contains(createPoint(2.00001, 2.00001)));
        assertTrue(envelope.contains(createPoint(0.00001, 0.00001)));
    }

    @Test
    public void extentTwoTrianglePolygon() throws SQLException {
        extent.add(createPolygon(0, 1, 2, 2, 2, 0, 0, 1));
        extent.add(createPolygon(1, 1, 3, 2, 3, 0, 1, 1));
        Object result = extent.getResult();
        assertThat(result, is(not(nullValue())));
        Geometry envelope = GeoDB.gFromWKB((byte[]) result);

        assertThat(envelope.getArea(), is(6.0));
        assertTrue(envelope.contains(createPoint(1, 1)));
        assertTrue(envelope.contains(createPoint(2.99999, 1.9999)));
        assertFalse(envelope.contains(createPoint(3.00001, 2.00001)));
        assertTrue(envelope.contains(createPoint(0.00001, 0.00001)));
    }
}
