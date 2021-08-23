package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 14:00
 */
@Entity
@Table(name = "YARD_STACK")
public class YardStackEntity {
    private long gkey;
    private long blockKey;
    private String rowId;
    private String topTierId;
    private String botTierId;
    private Long maxHeight;
    private Boolean isAccept20;
    private Boolean isAccept40;
    private Boolean isAccept45;
    private Boolean isAccept48;
    private Boolean isAccept53;
    private Double maxWeight20;
    private Double maxWeight40;
    private Double maxWeight45;
    private Double maxWeight48;
    private Double maxWeight53;
    private Timestamp created;
    private String creator;
    private Timestamp changed;
    private String changer;
    private Long startX;
    private Long startY;

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
    @Column(name = "ROW_ID")
    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    @Basic
    @Column(name = "TOP_TIER_ID")
    public String getTopTierId() {
        return topTierId;
    }

    public void setTopTierId(String topTierId) {
        this.topTierId = topTierId;
    }

    @Basic
    @Column(name = "BOT_TIER_ID")
    public String getBotTierId() {
        return botTierId;
    }

    public void setBotTierId(String botTierId) {
        this.botTierId = botTierId;
    }

    @Basic
    @Column(name = "MAX_HEIGHT")
    public Long getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Long maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Basic
    @Column(name = "IS_ACCEPT_20")
    public Boolean getAccept20() {
        return isAccept20;
    }

    public void setAccept20(Boolean accept20) {
        isAccept20 = accept20;
    }

    @Basic
    @Column(name = "IS_ACCEPT_40")
    public Boolean getAccept40() {
        return isAccept40;
    }

    public void setAccept40(Boolean accept40) {
        isAccept40 = accept40;
    }

    @Basic
    @Column(name = "IS_ACCEPT_45")
    public Boolean getAccept45() {
        return isAccept45;
    }

    public void setAccept45(Boolean accept45) {
        isAccept45 = accept45;
    }

    @Basic
    @Column(name = "IS_ACCEPT_48")
    public Boolean getAccept48() {
        return isAccept48;
    }

    public void setAccept48(Boolean accept48) {
        isAccept48 = accept48;
    }

    @Basic
    @Column(name = "IS_ACCEPT_53")
    public Boolean getAccept53() {
        return isAccept53;
    }

    public void setAccept53(Boolean accept53) {
        isAccept53 = accept53;
    }

    @Basic
    @Column(name = "MAX_WEIGHT_20")
    public Double getMaxWeight20() {
        return maxWeight20;
    }

    public void setMaxWeight20(Double maxWeight20) {
        this.maxWeight20 = maxWeight20;
    }

    @Basic
    @Column(name = "MAX_WEIGHT_40")
    public Double getMaxWeight40() {
        return maxWeight40;
    }

    public void setMaxWeight40(Double maxWeight40) {
        this.maxWeight40 = maxWeight40;
    }

    @Basic
    @Column(name = "MAX_WEIGHT_45")
    public Double getMaxWeight45() {
        return maxWeight45;
    }

    public void setMaxWeight45(Double maxWeight45) {
        this.maxWeight45 = maxWeight45;
    }

    @Basic
    @Column(name = "MAX_WEIGHT_48")
    public Double getMaxWeight48() {
        return maxWeight48;
    }

    public void setMaxWeight48(Double maxWeight48) {
        this.maxWeight48 = maxWeight48;
    }

    @Basic
    @Column(name = "MAX_WEIGHT_53")
    public Double getMaxWeight53() {
        return maxWeight53;
    }

    public void setMaxWeight53(Double maxWeight53) {
        this.maxWeight53 = maxWeight53;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardStackEntity that = (YardStackEntity) o;
        return gkey == that.gkey &&
                blockKey == that.blockKey &&
                Objects.equals(rowId, that.rowId) &&
                Objects.equals(topTierId, that.topTierId) &&
                Objects.equals(botTierId, that.botTierId) &&
                Objects.equals(maxHeight, that.maxHeight) &&
                Objects.equals(isAccept20, that.isAccept20) &&
                Objects.equals(isAccept40, that.isAccept40) &&
                Objects.equals(isAccept45, that.isAccept45) &&
                Objects.equals(isAccept48, that.isAccept48) &&
                Objects.equals(isAccept53, that.isAccept53) &&
                Objects.equals(maxWeight20, that.maxWeight20) &&
                Objects.equals(maxWeight40, that.maxWeight40) &&
                Objects.equals(maxWeight45, that.maxWeight45) &&
                Objects.equals(maxWeight48, that.maxWeight48) &&
                Objects.equals(maxWeight53, that.maxWeight53) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer) &&
                Objects.equals(startX, that.startX) &&
                Objects.equals(startY, that.startY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, blockKey, rowId, topTierId, botTierId, maxHeight, isAccept20, isAccept40, isAccept45, isAccept48, isAccept53, maxWeight20, maxWeight40, maxWeight45, maxWeight48, maxWeight53, created, creator, changed, changer, startX, startY);
    }
}
