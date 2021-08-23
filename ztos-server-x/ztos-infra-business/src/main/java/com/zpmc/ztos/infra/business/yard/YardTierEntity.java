package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 14:00
 */
@Entity
@Table(name = "YARD_TIER")
public class YardTierEntity {
    private long gkey;
    private long blockKey;
    private String tierId;
    private Long vcg;
    private Double maxTierWeight;
    private Timestamp created;
    private String creator;
    private Timestamp changed;
    private String changer;

    @Id
    @Column(name = "GKEY")
    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    @Basic
    @Column(name = "BLOCK_KEY")
    public long getBlockKey() {
        return blockKey;
    }

    public void setBlockKey(long blockKey) {
        this.blockKey = blockKey;
    }

    @Basic
    @Column(name = "TIER_ID")
    public String getTierId() {
        return tierId;
    }

    public void setTierId(String tierId) {
        this.tierId = tierId;
    }

    @Basic
    @Column(name = "VCG")
    public Long getVcg() {
        return vcg;
    }

    public void setVcg(Long vcg) {
        this.vcg = vcg;
    }

    @Basic
    @Column(name = "MAX_TIER_WEIGHT")
    public Double getMaxTierWeight() {
        return maxTierWeight;
    }

    public void setMaxTierWeight(Double maxTierWeight) {
        this.maxTierWeight = maxTierWeight;
    }

    @Basic
    @Column(name = "CREATED")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "CHANGED")
    public Timestamp getChanged() {
        return changed;
    }

    public void setChanged(Timestamp changed) {
        this.changed = changed;
    }

    @Basic
    @Column(name = "CHANGER")
    public String getChanger() {
        return changer;
    }

    public void setChanger(String changer) {
        this.changer = changer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardTierEntity that = (YardTierEntity) o;
        return gkey == that.gkey &&
                blockKey == that.blockKey &&
                Objects.equals(tierId, that.tierId) &&
                Objects.equals(vcg, that.vcg) &&
                Objects.equals(maxTierWeight, that.maxTierWeight) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, blockKey, tierId, vcg, maxTierWeight, created, creator, changed, changer);
    }
}
