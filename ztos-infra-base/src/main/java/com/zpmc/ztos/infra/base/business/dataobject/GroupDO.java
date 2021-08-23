package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.OrderPurpose;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.common.scopes.Facility;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class GroupDO extends ReferenceEntity implements Serializable {
    private Long grpGkey;
    private String grpId;
    private String grpDescription;
    private Date grpTimeStartDelivery;
    private Date grpTimeEndDelivery;
    private String grpFlexString01;
    private String grpFlexString02;
    private String grpFlexString03;
    private Date grpCreated;
    private String grpCreator;
    private Date grpChanged;
    private String grpChanger;
    private LifeCycleStateEnum grpLifeCycleState;
    private EntitySet grpScope;
    private Facility grpDestinationFacility;
    private OrderPurpose grpPurpose;
    private ScopedBizUnit grpObsoleteTruckingCompany;
    private Set grpBzuSet;

    public Serializable getPrimaryKey() {
        return this.getGrpGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getGrpGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof GroupDO)) {
            return false;
        }
        GroupDO that = (GroupDO)other;
        return ((Object)id).equals(that.getGrpGkey());
    }

    public int hashCode() {
        Long id = this.getGrpGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getGrpGkey() {
        return this.grpGkey;
    }

    protected void setGrpGkey(Long grpGkey) {
        this.grpGkey = grpGkey;
    }

    public String getGrpId() {
        return this.grpId;
    }

    protected void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    public String getGrpDescription() {
        return this.grpDescription;
    }

    protected void setGrpDescription(String grpDescription) {
        this.grpDescription = grpDescription;
    }

    public Date getGrpTimeStartDelivery() {
        return this.grpTimeStartDelivery;
    }

    protected void setGrpTimeStartDelivery(Date grpTimeStartDelivery) {
        this.grpTimeStartDelivery = grpTimeStartDelivery;
    }

    public Date getGrpTimeEndDelivery() {
        return this.grpTimeEndDelivery;
    }

    protected void setGrpTimeEndDelivery(Date grpTimeEndDelivery) {
        this.grpTimeEndDelivery = grpTimeEndDelivery;
    }

    public String getGrpFlexString01() {
        return this.grpFlexString01;
    }

    protected void setGrpFlexString01(String grpFlexString01) {
        this.grpFlexString01 = grpFlexString01;
    }

    public String getGrpFlexString02() {
        return this.grpFlexString02;
    }

    protected void setGrpFlexString02(String grpFlexString02) {
        this.grpFlexString02 = grpFlexString02;
    }

    public String getGrpFlexString03() {
        return this.grpFlexString03;
    }

    protected void setGrpFlexString03(String grpFlexString03) {
        this.grpFlexString03 = grpFlexString03;
    }

    public Date getGrpCreated() {
        return this.grpCreated;
    }

    protected void setGrpCreated(Date grpCreated) {
        this.grpCreated = grpCreated;
    }

    public String getGrpCreator() {
        return this.grpCreator;
    }

    protected void setGrpCreator(String grpCreator) {
        this.grpCreator = grpCreator;
    }

    public Date getGrpChanged() {
        return this.grpChanged;
    }

    protected void setGrpChanged(Date grpChanged) {
        this.grpChanged = grpChanged;
    }

    public String getGrpChanger() {
        return this.grpChanger;
    }

    protected void setGrpChanger(String grpChanger) {
        this.grpChanger = grpChanger;
    }

    public LifeCycleStateEnum getGrpLifeCycleState() {
        return this.grpLifeCycleState;
    }

    public void setGrpLifeCycleState(LifeCycleStateEnum grpLifeCycleState) {
        this.grpLifeCycleState = grpLifeCycleState;
    }

    public EntitySet getGrpScope() {
        return this.grpScope;
    }

    protected void setGrpScope(EntitySet grpScope) {
        this.grpScope = grpScope;
    }

    public Facility getGrpDestinationFacility() {
        return this.grpDestinationFacility;
    }

    protected void setGrpDestinationFacility(Facility grpDestinationFacility) {
        this.grpDestinationFacility = grpDestinationFacility;
    }

    public OrderPurpose getGrpPurpose() {
        return this.grpPurpose;
    }

    protected void setGrpPurpose(OrderPurpose grpPurpose) {
        this.grpPurpose = grpPurpose;
    }

    public ScopedBizUnit getGrpObsoleteTruckingCompany() {
        return this.grpObsoleteTruckingCompany;
    }

    protected void setGrpObsoleteTruckingCompany(ScopedBizUnit grpObsoleteTruckingCompany) {
        this.grpObsoleteTruckingCompany = grpObsoleteTruckingCompany;
    }

    public Set getGrpBzuSet() {
        return this.grpBzuSet;
    }

    protected void setGrpBzuSet(Set grpBzuSet) {
        this.grpBzuSet = grpBzuSet;
    }
}
