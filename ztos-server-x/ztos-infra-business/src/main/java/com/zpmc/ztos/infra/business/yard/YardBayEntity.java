package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 13:59
 */
@Entity
@Table(name = "YARD_BAY")
public class YardBayEntity {
    private long gkey;
    private String id;
    private String altId;
    private String bayStartTier;
    private String bayMaxTier;
    private String bayStartStack;
    private String bayMaxStack;
    private Timestamp created;
    private String creator;
    private Timestamp changed;
    private String changer;
    private Long startX;
    private Long startY;
    private String bayPairedIntoId;
    private String bayPairedFromId;
    private Long lengthsAllowed;

    @Id
    @Column(name = "GKEY")
    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    @Basic
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ALT_ID")
    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    @Basic
    @Column(name = "BAY_START_TIER")
    public String getBayStartTier() {
        return bayStartTier;
    }

    public void setBayStartTier(String bayStartTier) {
        this.bayStartTier = bayStartTier;
    }

    @Basic
    @Column(name = "BAY_MAX_TIER")
    public String getBayMaxTier() {
        return bayMaxTier;
    }

    public void setBayMaxTier(String bayMaxTier) {
        this.bayMaxTier = bayMaxTier;
    }

    @Basic
    @Column(name = "BAY_START_STACK")
    public String getBayStartStack() {
        return bayStartStack;
    }

    public void setBayStartStack(String bayStartStack) {
        this.bayStartStack = bayStartStack;
    }

    @Basic
    @Column(name = "BAY_MAX_STACK")
    public String getBayMaxStack() {
        return bayMaxStack;
    }

    public void setBayMaxStack(String bayMaxStack) {
        this.bayMaxStack = bayMaxStack;
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

    @Basic
    @Column(name = "START_X")
    public Long getStartX() {
        return startX;
    }

    public void setStartX(Long startX) {
        this.startX = startX;
    }

    @Basic
    @Column(name = "START_Y")
    public Long getStartY() {
        return startY;
    }

    public void setStartY(Long startY) {
        this.startY = startY;
    }

    @Basic
    @Column(name = "BAY_PAIRED_INTO_ID")
    public String getBayPairedIntoId() {
        return bayPairedIntoId;
    }

    public void setBayPairedIntoId(String bayPairedIntoId) {
        this.bayPairedIntoId = bayPairedIntoId;
    }

    @Basic
    @Column(name = "BAY_PAIRED_FROM_ID")
    public String getBayPairedFromId() {
        return bayPairedFromId;
    }

    public void setBayPairedFromId(String bayPairedFromId) {
        this.bayPairedFromId = bayPairedFromId;
    }

    @Basic
    @Column(name = "LENGTHS_ALLOWED")
    public Long getLengthsAllowed() {
        return lengthsAllowed;
    }

    public void setLengthsAllowed(Long lengthsAllowed) {
        this.lengthsAllowed = lengthsAllowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardBayEntity that = (YardBayEntity) o;
        return gkey == that.gkey &&
                Objects.equals(id, that.id) &&
                Objects.equals(altId, that.altId) &&
                Objects.equals(bayStartTier, that.bayStartTier) &&
                Objects.equals(bayMaxTier, that.bayMaxTier) &&
                Objects.equals(bayStartStack, that.bayStartStack) &&
                Objects.equals(bayMaxStack, that.bayMaxStack) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer) &&
                Objects.equals(startX, that.startX) &&
                Objects.equals(startY, that.startY) &&
                Objects.equals(bayPairedIntoId, that.bayPairedIntoId) &&
                Objects.equals(bayPairedFromId, that.bayPairedFromId) &&
                Objects.equals(lengthsAllowed, that.lengthsAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, id, altId, bayStartTier, bayMaxTier, bayStartStack, bayMaxStack, created, creator, changed, changer, startX, startY, bayPairedIntoId, bayPairedFromId, lengthsAllowed);
    }
}
