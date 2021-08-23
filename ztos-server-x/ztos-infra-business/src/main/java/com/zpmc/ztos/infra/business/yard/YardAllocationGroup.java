package com.zpmc.ztos.infra.business.yard;

import com.zpmc.ztos.infra.business.base.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "yard_allocation_group")
public class YardAllocationGroup extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "category")
    private Integer category;   // TODO: change to enum

    @Column(name = "carrier_kind")
    private String carrierKind; // TODO: change to enum

    @OneToOne(cascade={CascadeType.REMOVE,CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.EAGER)
    @JoinColumn(name = "allocation_filter_id")
    private YardAllocationFilter yardAllocationFilter;

    @Column(name = "remark")
    private String remark;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCarrierKind() {
        return carrierKind;
    }

    public void setCarrierKind(String carrierKind) {
        this.carrierKind = carrierKind;
    }

    public YardAllocationFilter getYardAllocationFilter() {
        return yardAllocationFilter;
    }

    public void setYardAllocationFilter(YardAllocationFilter yardAllocationFilter) {
        this.yardAllocationFilter = yardAllocationFilter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardAllocationGroup that = (YardAllocationGroup) o;
        return Objects.equals(id, that.id) && Objects.equals(groupName, that.groupName) && Objects.equals(category, that.category) && Objects.equals(carrierKind, that.carrierKind) && Objects.equals(yardAllocationFilter, that.yardAllocationFilter) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, category, carrierKind, yardAllocationFilter, remark);
    }

    @Override
    public String toString() {
        return "YardAllocationGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", category=" + category +
                ", carrierKind='" + carrierKind + '\'' +
                ", yardAllocationFilter=" + yardAllocationFilter +
                ", remark='" + remark + '\'' +
                '}';
    }
}
