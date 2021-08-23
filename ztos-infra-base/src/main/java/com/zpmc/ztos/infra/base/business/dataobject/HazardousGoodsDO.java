package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.inventory.HazardsNumberTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.Placard;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class HazardousGoodsDO extends DatabaseEntity implements Serializable {

    private Long hzgoodsGkey;
    private String hzgoodsUnNbr;
    private HazardsNumberTypeEnum hzgoodsNbrType;
    private ImdgClassEnum hzgoodsImdgClass;
    private Boolean hzgoodsIsPlacardOptional;
    private String hzgoodsProperShippingName;
    private Date hzgoodsCreated;
    private String hzgoodsCreator;
    private Date hzgoodsChanged;
    private String hzgoodsChanger;
    private EntitySet hzgoodsScope;
    private Placard hzgoodsPlacard;
    private Set hzgoodsSubsidiaryRisks;

    public Serializable getPrimaryKey() {
        return this.getHzgoodsGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getHzgoodsGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof HazardousGoodsDO)) {
            return false;
        }
        HazardousGoodsDO that = (HazardousGoodsDO)other;
        return ((Object)id).equals(that.getHzgoodsGkey());
    }

    public int hashCode() {
        Long id = this.getHzgoodsGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getHzgoodsGkey() {
        return this.hzgoodsGkey;
    }

    protected void setHzgoodsGkey(Long hzgoodsGkey) {
        this.hzgoodsGkey = hzgoodsGkey;
    }

    public String getHzgoodsUnNbr() {
        return this.hzgoodsUnNbr;
    }

    protected void setHzgoodsUnNbr(String hzgoodsUnNbr) {
        this.hzgoodsUnNbr = hzgoodsUnNbr;
    }

    public HazardsNumberTypeEnum getHzgoodsNbrType() {
        return this.hzgoodsNbrType;
    }

    protected void setHzgoodsNbrType(HazardsNumberTypeEnum hzgoodsNbrType) {
        this.hzgoodsNbrType = hzgoodsNbrType;
    }

    public ImdgClassEnum getHzgoodsImdgClass() {
        return this.hzgoodsImdgClass;
    }

    protected void setHzgoodsImdgClass(ImdgClassEnum hzgoodsImdgClass) {
        this.hzgoodsImdgClass = hzgoodsImdgClass;
    }

    public Boolean getHzgoodsIsPlacardOptional() {
        return this.hzgoodsIsPlacardOptional;
    }

    protected void setHzgoodsIsPlacardOptional(Boolean hzgoodsIsPlacardOptional) {
        this.hzgoodsIsPlacardOptional = hzgoodsIsPlacardOptional;
    }

    public String getHzgoodsProperShippingName() {
        return this.hzgoodsProperShippingName;
    }

    protected void setHzgoodsProperShippingName(String hzgoodsProperShippingName) {
        this.hzgoodsProperShippingName = hzgoodsProperShippingName;
    }

    public Date getHzgoodsCreated() {
        return this.hzgoodsCreated;
    }

    protected void setHzgoodsCreated(Date hzgoodsCreated) {
        this.hzgoodsCreated = hzgoodsCreated;
    }

    public String getHzgoodsCreator() {
        return this.hzgoodsCreator;
    }

    protected void setHzgoodsCreator(String hzgoodsCreator) {
        this.hzgoodsCreator = hzgoodsCreator;
    }

    public Date getHzgoodsChanged() {
        return this.hzgoodsChanged;
    }

    protected void setHzgoodsChanged(Date hzgoodsChanged) {
        this.hzgoodsChanged = hzgoodsChanged;
    }

    public String getHzgoodsChanger() {
        return this.hzgoodsChanger;
    }

    protected void setHzgoodsChanger(String hzgoodsChanger) {
        this.hzgoodsChanger = hzgoodsChanger;
    }

    public EntitySet getHzgoodsScope() {
        return this.hzgoodsScope;
    }

    protected void setHzgoodsScope(EntitySet hzgoodsScope) {
        this.hzgoodsScope = hzgoodsScope;
    }

    public Placard getHzgoodsPlacard() {
        return this.hzgoodsPlacard;
    }

    protected void setHzgoodsPlacard(Placard hzgoodsPlacard) {
        this.hzgoodsPlacard = hzgoodsPlacard;
    }

    public Set getHzgoodsSubsidiaryRisks() {
        return this.hzgoodsSubsidiaryRisks;
    }

    protected void setHzgoodsSubsidiaryRisks(Set hzgoodsSubsidiaryRisks) {
        this.hzgoodsSubsidiaryRisks = hzgoodsSubsidiaryRisks;
    }

}
