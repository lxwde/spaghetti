package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.RoutingPoint;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * 设施
 * @author yejun
 */
@Data
public class FacilityDO extends DatabaseEntity implements Serializable {

    private Long fcyGkey;
    private String fcyId;
    private String fcyName;

    /**
     * 是否运营模式
     */
    private Boolean fcyIsNonOperational;
    private String fcyTimeZoneId;
    private LifeCycleStateEnum fcyLifeCycleState;
    private Long fcyTeuGreen;
    private Long fcyTeuYellow;
    private Long fcyTeuRed;
    private Complex fcyComplex;
    private RoutingPoint fcyRoutingPoint;
//    private EntityMappingPredicate fcyRouteResolverRules;

    /**
     * 相关yard
     */
    private Set fcyYrdSet;

    /**
     * 相关继电器
     */
    private Set fcyRelays;
//    private ESBJmsConnection fcyJmsConnection;

    @Override
    public Serializable getPrimaryKey() {
        return this.getFcyGkey();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getFcyGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof FacilityDO)) {
            return false;
        }
        FacilityDO that = (FacilityDO)other;
        return ((Object)id).equals(that.getFcyGkey());
    }

    @Override
    public int hashCode() {
        Long id = this.getFcyGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getFcyGkey() {
        return this.fcyGkey;
    }

    protected void setFcyGkey(Long fcyGkey) {
        this.fcyGkey = fcyGkey;
    }

    public String getFcyId() {
        return this.fcyId;
    }

    protected void setFcyId(String fcyId) {
        this.fcyId = fcyId;
    }

    public String getFcyName() {
        return this.fcyName;
    }

    protected void setFcyName(String fcyName) {
        this.fcyName = fcyName;
    }

    public Boolean getFcyIsNonOperational() {
        return this.fcyIsNonOperational;
    }

    protected void setFcyIsNonOperational(Boolean fcyIsNonOperational) {
        this.fcyIsNonOperational = fcyIsNonOperational;
    }

    public String getFcyTimeZoneId() {
        return this.fcyTimeZoneId;
    }

    protected void setFcyTimeZoneId(String fcyTimeZoneId) {
        this.fcyTimeZoneId = fcyTimeZoneId;
    }

    public LifeCycleStateEnum getFcyLifeCycleState() {
        return this.fcyLifeCycleState;
    }

    public void setFcyLifeCycleState(LifeCycleStateEnum fcyLifeCycleState) {
        this.fcyLifeCycleState = fcyLifeCycleState;
    }

    public Long getFcyTeuGreen() {
        return this.fcyTeuGreen;
    }

    protected void setFcyTeuGreen(Long fcyTeuGreen) {
        this.fcyTeuGreen = fcyTeuGreen;
    }

    public Long getFcyTeuYellow() {
        return this.fcyTeuYellow;
    }

    protected void setFcyTeuYellow(Long fcyTeuYellow) {
        this.fcyTeuYellow = fcyTeuYellow;
    }

    public Long getFcyTeuRed() {
        return this.fcyTeuRed;
    }

    protected void setFcyTeuRed(Long fcyTeuRed) {
        this.fcyTeuRed = fcyTeuRed;
    }

    public Complex getFcyComplex() {
        return this.fcyComplex;
    }

    protected void setFcyComplex(Complex fcyComplex) {
        this.fcyComplex = fcyComplex;
    }

    public RoutingPoint getFcyRoutingPoint() {
        return this.fcyRoutingPoint;
    }

//    protected void setFcyRoutingPoint(RoutingPoint fcyRoutingPoint) {
//        this.fcyRoutingPoint = fcyRoutingPoint;
//    }
//
//    public EntityMappingPredicate getFcyRouteResolverRules() {
//        return this.fcyRouteResolverRules;
//    }
//
//    protected void setFcyRouteResolverRules(EntityMappingPredicate fcyRouteResolverRules) {
//        this.fcyRouteResolverRules = fcyRouteResolverRules;
//    }

    public Set getFcyYrdSet() {
        return this.fcyYrdSet;
    }

    protected void setFcyYrdSet(Set fcyYrdSet) {
        this.fcyYrdSet = fcyYrdSet;
    }

    public Set getFcyRelays() {
        return this.fcyRelays;
    }

    protected void setFcyRelays(Set fcyRelays) {
        this.fcyRelays = fcyRelays;
    }

//    public ESBJmsConnection getFcyJmsConnection() {
//        return this.fcyJmsConnection;
//    }
//
//    protected void setFcyJmsConnection(ESBJmsConnection fcyJmsConnection) {
//        this.fcyJmsConnection = fcyJmsConnection;
//    }

}
