package com.zpmc.ztos.infra.business.yard;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author Java002
 * @Date 2021/6/24 13:59
 */
@Entity
@Table(name = "YARD_ALLOCATION_RANGE")
public class YardAllocationRangeEntity {
    private Long id;
    private String blockId;
    private String startBay;
    private String endBay;
    private String startStack;
    private String endStack;
    private String startTier;
    private String endTier;
    private String fillSequence;
    private Timestamp created;
    private String creator;
    private Timestamp changed;
    private String changer;

    @Id
    @Column(name = "GKEY")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "BLOCK_ID")
    public String getBlockId() {
        return blockId;
    }
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    @Column(name = "START_BAY")
    public String getStartBay() {
        return startBay;
    }
    public void setStartBay(String startBay) {
        this.startBay = startBay;
    }

    @Column(name = "END_BAY")
    public String getEndBay() {
        return endBay;
    }
    public void setEndBay(String endBay) {
        this.endBay = endBay;
    }

    @Column(name = "START_STACK")
    public String getStartStack() {
        return startStack;
    }
    public void setStartStack(String startStack) {
        this.startStack = startStack;
    }

    @Column(name = "END_STACK")
    public String getEndStack() {
        return endStack;
    }
    public void setEndStack(String endStack) {
        this.endStack = endStack;
    }

    @Column(name = "START_TIER")
    public String getStartTier() {
        return startTier;
    }
    public void setStartTier(String startTier) {
        this.startTier = startTier;
    }

    @Column(name = "END_TIER")
    public String getEndTier() {
        return endTier;
    }
    public void setEndTier(String endTier) {
        this.endTier = endTier;
    }

    @Column(name = "FILL_SEQUENCE")
    public String getFillSequence() {
        return fillSequence;
    }
    public void setFillSequence(String fillSequence) {
        this.fillSequence = fillSequence;
    }


}
