package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 13:59
 */
@Entity
@Table(name = "YARD_SECTION")
public class YardSectionEntity {
    private long gkey;
    private String id;
    private Long kind;
    private Long length;
    private Long width;
    private Long height;
    private Long startX;
    private Long startY;
    private Long endX;
    private Long endY;
    private Long xOffsetOrg;
    private Long yOffsetOrg;
    private Double rotationAngleOrg;
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
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "KIND")
    public Long getKind() {
        return kind;
    }

    public void setKind(Long kind) {
        this.kind = kind;
    }

    @Basic
    @Column(name = "LENGTH")
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Basic
    @Column(name = "WIDTH")
    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Basic
    @Column(name = "HEIGHT")
    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
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
    @Column(name = "END_X")
    public Long getEndX() {
        return endX;
    }

    public void setEndX(Long endX) {
        this.endX = endX;
    }

    @Basic
    @Column(name = "END_Y")
    public Long getEndY() {
        return endY;
    }

    public void setEndY(Long endY) {
        this.endY = endY;
    }

    @Basic
    @Column(name = "X_OFFSET_ORG")
    public Long getxOffsetOrg() {
        return xOffsetOrg;
    }

    public void setxOffsetOrg(Long xOffsetOrg) {
        this.xOffsetOrg = xOffsetOrg;
    }

    @Basic
    @Column(name = "Y_OFFSET_ORG")
    public Long getyOffsetOrg() {
        return yOffsetOrg;
    }

    public void setyOffsetOrg(Long yOffsetOrg) {
        this.yOffsetOrg = yOffsetOrg;
    }

    @Basic
    @Column(name = "ROTATION_ANGLE_ORG")
    public Double getRotationAngleOrg() {
        return rotationAngleOrg;
    }

    public void setRotationAngleOrg(Double rotationAngleOrg) {
        this.rotationAngleOrg = rotationAngleOrg;
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
        YardSectionEntity that = (YardSectionEntity) o;
        return gkey == that.gkey &&
                Objects.equals(id, that.id) &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(length, that.length) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(startX, that.startX) &&
                Objects.equals(startY, that.startY) &&
                Objects.equals(endX, that.endX) &&
                Objects.equals(endY, that.endY) &&
                Objects.equals(xOffsetOrg, that.xOffsetOrg) &&
                Objects.equals(yOffsetOrg, that.yOffsetOrg) &&
                Objects.equals(rotationAngleOrg, that.rotationAngleOrg) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, id, kind, length, width, height, startX, startY, endX, endY, xOffsetOrg, yOffsetOrg, rotationAngleOrg, created, creator, changed, changer);
    }
}
