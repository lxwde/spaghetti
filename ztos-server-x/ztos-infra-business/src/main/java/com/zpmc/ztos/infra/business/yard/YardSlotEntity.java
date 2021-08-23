package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 14:00
 */
@Entity
@Table(name = "YARD_SLOT")
public class YardSlotEntity {
    private long gkey;
    private long blockKey;
    private String slotLoc;
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
    @Column(name = "SLOT_LOC")
    public String getSlotLoc() {
        return slotLoc;
    }

    public void setSlotLoc(String slotLoc) {
        this.slotLoc = slotLoc;
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
        YardSlotEntity that = (YardSlotEntity) o;
        return gkey == that.gkey &&
                blockKey == that.blockKey &&
                Objects.equals(slotLoc, that.slotLoc) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, blockKey, slotLoc, created, creator, changed, changer);
    }
}
