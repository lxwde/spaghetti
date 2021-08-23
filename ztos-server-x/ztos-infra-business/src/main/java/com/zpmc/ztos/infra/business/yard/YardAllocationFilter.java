package com.zpmc.ztos.infra.business.yard;

import com.zpmc.ztos.infra.business.base.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "yard_allocation_filter")
public class YardAllocationFilter extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "iso_type")
    private String isoType;

    @Column(name = "pod")
    private String pod;

    @Column(name = "trade_code")
    private String tradeCode;

    @Column(name = "freight_kind")
    private Integer freightKind; // TODO: change to enum

    @Column(name = "category")
    private Integer category;    // TODO: change to enum

    @Column(name = "is_reefer")
    private Boolean isReefer;

    @Column(name = "is_hazardous")
    private Boolean isHazardous;

    @Column(name = "is_damaged")
    private Boolean isDamaged;

    @Column(name = "is_high_cube")
    private Boolean isHighCube;

    @Column(name = "is_out_of_gauge")
    private Boolean isOutOfGauge;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoType() {
        return isoType;
    }

    public void setIsoType(String isoType) {
        this.isoType = isoType;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Integer getFreightKind() {
        return freightKind;
    }

    public void setFreightKind(Integer freightKind) {
        this.freightKind = freightKind;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getReefer() {
        return isReefer;
    }

    public void setReefer(Boolean reefer) {
        isReefer = reefer;
    }

    public Boolean getHazardous() {
        return isHazardous;
    }

    public void setHazardous(Boolean hazardous) {
        isHazardous = hazardous;
    }

    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    public Boolean getHighCube() {
        return isHighCube;
    }

    public void setHighCube(Boolean highCube) {
        isHighCube = highCube;
    }

    public Boolean getOutOfGauge() {
        return isOutOfGauge;
    }

    public void setOutOfGauge(Boolean outOfGauge) {
        isOutOfGauge = outOfGauge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardAllocationFilter that = (YardAllocationFilter) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isoType, that.isoType) && Objects.equals(pod, that.pod) && Objects.equals(tradeCode, that.tradeCode) && Objects.equals(freightKind, that.freightKind) && Objects.equals(category, that.category) && Objects.equals(isReefer, that.isReefer) && Objects.equals(isHazardous, that.isHazardous) && Objects.equals(isDamaged, that.isDamaged) && Objects.equals(isHighCube, that.isHighCube) && Objects.equals(isOutOfGauge, that.isOutOfGauge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isoType, pod, tradeCode, freightKind, category, isReefer, isHazardous, isDamaged, isHighCube, isOutOfGauge);
    }

    @Override
    public String toString() {
        return "YardAllocationFilter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isoType='" + isoType + '\'' +
                ", pod='" + pod + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", freightKind=" + freightKind +
                ", category=" + category +
                ", isReefer=" + isReefer +
                ", isHazardous=" + isHazardous +
                ", isDamaged=" + isDamaged +
                ", isHighCube=" + isHighCube +
                ", isOutOfGauge=" + isOutOfGauge +
                "} " + super.toString();
    }
}
