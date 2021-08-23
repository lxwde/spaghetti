package com.zpmc.ztos.infra.base.business.dataobject;//package com.zpmc.ztos.infra.base.business.dataobject;
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Date;
//
//
///**
// * @Author Java002
// * @Date 2021/6/18 13:05
// */
//@Data
//@Entity
//@Table(name = "vessel_visit")
//public class VesselVisitDO extends CommonDO {
//
//
//    @Column(name = "VESSEL_VISIT_ID")//数据库字段名
//    private String vesselVisitId;
//
//
//    @Column(name = "TERMINAL_KEY")//数据库字段名
//    private Integer terminalKey;
//
//
//    @Column(name = "VESSEL_KEY")//数据库字段名
//    private Integer vesselKey;
//
//
//    @Column(name = "IB_VOYAGE_ID")//数据库字段名
//    private String ibVoyageId;
//
//
//    @Column(name = "OB_VOYAGE_ID")//数据库字段名
//    private String obVoyageId;
//
//
//    @Column(name = "TRADE_CODE")//数据库字段名
//    private String tradeCode;
//
//
//    @Column(name = "TRADE_NAME")//数据库字段名
//    private String tradeName;
//
//
//    @Column(name = "AGENT")//数据库字段名
//    private String agent;
//
//
//    @Column(name = "NEXT_PORT_CODE")//数据库字段名
//    private String nextPortCode;
//
//
//    @Column(name = "NEXT_PORT_NAME")//数据库字段名
//    private String nextPortName;
//
//
//    @Column(name = "TOTAL_BAY")//数据库字段名
//    private Integer totalBay;
//
//
//    @Column(name = "START_BAY")//数据库字段名
//    private String startBay;
//
//
//    @Column(name = "END_BAY")//数据库字段名
//    private String endBay;
//
//
//    @Column(name = "TOTAL_LOAD")//数据库字段名
//    private Integer totalLoad;
//
//
//    @Column(name = "TOTAL_DISCHARGE")//数据库字段名
//    private Integer totalDischarge;
//
//
//    @Column(name = "ETA")//数据库字段名
//    private Date eta;
//
//
//    @Column(name = "ETD")//数据库字段名
//    private Date etd;
//
//
//    @Column(name = "STATE")//数据库字段名
//    private String state;
//
//
//    @Column(name = "VISIT_VERSION")//数据库字段名
//    private Integer visitVersion;
//
//
//    @Column(name = "ata")//数据库字段名
//    private Date ata;
//
//
//    @Column(name = "atd")//数据库字段名
//    private Date atd;
//
//
//    @Column(name = "atb")//数据库字段名
//    private Date atb;
//
//
//    @Column(name = "ataa")//数据库字段名
//    private Date ataa;
//
//
//    @Override
//    public void setPrimaryKey(Serializable var) {
//
//    }
//
//    @Override
//    public Serializable getPrimaryKey() {
//        return null;
//    }
//}
