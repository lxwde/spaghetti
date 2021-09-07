package geodb;

import junit.framework.Assert;
import geodb.GeoDB;

import org.junit.Test;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeoDB3DTest {

    @Test
    public void test3dPreservation() throws ParseException {
        Geometry g = new WKTReader().read("POINT (10 10 -5)");
        g.setSRID(4326);

        // to and from ewkb
        byte[] ewkb = GeoDB.gToEWKB(g);
        Geometry gFromEwkb = GeoDB.gFromEWKB(ewkb);
        Assert.assertTrue(g.equalsExact(gFromEwkb));
        Assert.assertEquals(4326, gFromEwkb.getSRID());
        Assert.assertEquals(g.getCoordinate().z, gFromEwkb.getCoordinate().z);
    }
}
