package com.zpmc.ztos.infra.business.config;

import com.zaxxer.hikari.pool.HikariProxyConnection;
import org.geolatte.geom.codec.db.oracle.DefaultConnectionFinder;

import java.lang.reflect.Field;
import java.sql.Connection;

public class OracleConnectionFinder extends DefaultConnectionFinder {
    @Override
    public Connection find(Connection con) {
        try {
            Field delegate = ((HikariProxyConnection) con).getClass().getSuperclass().getDeclaredField("delegate");
            delegate.setAccessible(true);
            return (Connection) delegate.get(con);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(
                    "Couldn't get at the OracleSpatial Connection object from the PreparedStatement."
            );
        }
    }
}
