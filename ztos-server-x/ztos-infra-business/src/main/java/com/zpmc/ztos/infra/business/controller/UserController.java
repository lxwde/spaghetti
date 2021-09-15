package com.zpmc.ztos.infra.business.controller;

import com.zpmc.ztos.infra.business.account.User;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.crs.CrsRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/geo")
    ResponseEntity<Geometry> getGeometry() {
        org.geolatte.geom.Point<C2D> point1 = Geometries.mkPoint(new C2D(12.34343, 12.232424), CrsRegistry.getProjectedCoordinateReferenceSystemForEPSG(3785));
        return ResponseEntity.ok(point1);
    }
}
