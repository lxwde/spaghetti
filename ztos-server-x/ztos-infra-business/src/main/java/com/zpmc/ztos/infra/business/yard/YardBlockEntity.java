package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author Java002
 * @Date 2021/6/24 13:59
 */
@Entity
@Table(name = "YARD_BLOCK")
public class YardBlockEntity {
    private long gkey;
    private String id;
    private Long length;
    private Long width;
    private Long height;
    private Long startX;
    private Long endX;
    private Long startY;
    private Long endY;
    private Long xOffsetOrg;
    private Long yOffsetOrg;
    private Double rotationAngleOrg;
    private Long bayWidth;
    private Long bayGap;
    private Long laneWidth;
    private Long laneGap;
    private Boolean isBayInverted;
    private Boolean isLaneInverted;
    private Timestamp created;
    private String creator;
    private Timestamp changed;
    private String changer;
    private Boolean roadwayPos;
    private Boolean roadwayNum;
    private Long blockType;
    private Long cheKind;
    private Long angleRotation;
    private Short rowNum;

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
    @Column(name = "END_X")
    public Long getEndX() {
        return endX;
    }

    public void setEndX(Long endX) {
        this.endX = endX;
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
    @Column(name = "BAY_WIDTH")
    public Long getBayWidth() {
        return bayWidth;
    }

    public void setBayWidth(Long bayWidth) {
        this.bayWidth = bayWidth;
    }

    @Basic
    @Column(name = "BAY_GAP")
    public Long getBayGap() {
        return bayGap;
    }

    public void setBayGap(Long bayGap) {
        this.bayGap = bayGap;
    }

    @Basic
    @Column(name = "LANE_WIDTH")
    public Long getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(Long laneWidth) {
        this.laneWidth = laneWidth;
    }

    @Basic
    @Column(name = "LANE_GAP")
    public Long getLaneGap() {
        return laneGap;
    }

    public void setLaneGap(Long laneGap) {
        this.laneGap = laneGap;
    }

    @Basic
    @Column(name = "IS_BAY_INVERTED")
    public Boolean getBayInverted() {
        return isBayInverted;
    }

    public void setBayInverted(Boolean bayInverted) {
        isBayInverted = bayInverted;
    }

    @Basic
    @Column(name = "IS_LANE_INVERTED")
    public Boolean getLaneInverted() {
        return isLaneInverted;
    }

    public void setLaneInverted(Boolean laneInverted) {
        isLaneInverted = laneInverted;
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
    @Column(name = "ROADWAY_POS")
    public Boolean getRoadwayPos() {
        return roadwayPos;
    }

    public void setRoadwayPos(Boolean roadwayPos) {
        this.roadwayPos = roadwayPos;
    }

    @Basic
    @Column(name = "ROADWAY_NUM")
    public Boolean getRoadwayNum() {
        return roadwayNum;
    }

    public void setRoadwayNum(Boolean roadwayNum) {
        this.roadwayNum = roadwayNum;
    }

    @Basic
    @Column(name = "BLOCK_TYPE")
    public Long getBlockType() {
        return blockType;
    }

    public void setBlockType(Long blockType) {
        this.blockType = blockType;
    }

    @Basic
    @Column(name = "CHE_KIND")
    public Long getCheKind() {
        return cheKind;
    }

    public void setCheKind(Long cheKind) {
        this.cheKind = cheKind;
    }

    @Basic
    @Column(name = "ANGLE_ROTATION")
    public Long getAngleRotation() {
        return angleRotation;
    }

    public void setAngleRotation(Long angleRotation) {
        this.angleRotation = angleRotation;
    }

    @Basic
    @Column(name = "ROW_NUM")
    public Short getRowNum() {
        return rowNum;
    }

    public void setRowNum(Short rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YardBlockEntity that = (YardBlockEntity) o;
        return gkey == that.gkey &&
                Objects.equals(id, that.id) &&
                Objects.equals(length, that.length) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(startX, that.startX) &&
                Objects.equals(endX, that.endX) &&
                Objects.equals(startY, that.startY) &&
                Objects.equals(endY, that.endY) &&
                Objects.equals(xOffsetOrg, that.xOffsetOrg) &&
                Objects.equals(yOffsetOrg, that.yOffsetOrg) &&
                Objects.equals(rotationAngleOrg, that.rotationAngleOrg) &&
                Objects.equals(bayWidth, that.bayWidth) &&
                Objects.equals(bayGap, that.bayGap) &&
                Objects.equals(laneWidth, that.laneWidth) &&
                Objects.equals(laneGap, that.laneGap) &&
                Objects.equals(isBayInverted, that.isBayInverted) &&
                Objects.equals(isLaneInverted, that.isLaneInverted) &&
                Objects.equals(created, that.created) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(changed, that.changed) &&
                Objects.equals(changer, that.changer) &&
                Objects.equals(roadwayPos, that.roadwayPos) &&
                Objects.equals(roadwayNum, that.roadwayNum) &&
                Objects.equals(blockType, that.blockType) &&
                Objects.equals(cheKind, that.cheKind) &&
                Objects.equals(angleRotation, that.angleRotation) &&
                Objects.equals(rowNum, that.rowNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gkey, id, length, width, height, startX, endX, startY, endY, xOffsetOrg, yOffsetOrg, rotationAngleOrg, bayWidth, bayGap, laneWidth, laneGap, isBayInverted, isLaneInverted, created, creator, changed, changer, roadwayPos, roadwayNum, blockType, cheKind, angleRotation, rowNum);
    }
}
