package com.zpmc.ztos.infra.base.business.space;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.io.Serializable;

public class Position implements Serializable {
    private String _posSlot;
    private String _posOrientation;
    private String _posName;
    private String _posOperationalPosId;
    private Long _posTier;
    private AbstractBin _posBin;
    private Point _posAnchor;
    private Double _posOrientationDegrees;
    private String _posSlotOnCarriage;

    public Class getArchiveClass() {
  //      return ArchivePosition.class;
        return null;
    }

    public boolean doArchive() {
        return false;
    }

    public Position() {
    }

    private Position(AbstractBin inAncestorBin, String inSlot, String inOrientation, String inPosSlotOnCarriage) {
        this.setPosSlot(inSlot);
        this.setPosOrientation(inOrientation);
        this.updateBinAndTierFromAncestorAndInternalSlotName(inAncestorBin, inSlot);
        this.setPosName(inSlot);
        this.setPosSlotOnCarriage(inPosSlotOnCarriage);
    }

    public final void updateBinAndTierFromAncestorAndInternalSlotName(AbstractBin inAncestorBin, String inSlotName) {
        if (inAncestorBin != null) {
            if (inSlotName != null) {
                AbstractBin bin = null;
                bin = inSlotName.equals(inAncestorBin.getAbnName()) ? inAncestorBin : inAncestorBin.findDescendantBinFromInternalSlotString(inSlotName, null);
                this.setPosBin(bin);
                Long tier = 0L;
                if (bin != null) {
                    tier = bin.getTierIndexFromInternalSlotString(inSlotName);
                }
                this.setPosTier(tier);
            } else {
                this.setPosBin(inAncestorBin);
                this.setPosTier(0L);
            }
        } else {
            this.setPosBin(null);
            this.setPosTier(null);
        }
        this.setPosOrientationDegrees(null);
        this.setPosAnchor(null);
    }

    public static Position createPosition(AbstractBin inAncestorBin, String inSlot, String inOrientation) {
        return new Position(inAncestorBin, inSlot, inOrientation, null);
    }

    public static Position createPosition(AbstractBin inAncestorBin, String inSlot, String inOrientation, String inSlotOnCarriage) {
        return new Position(inAncestorBin, inSlot, inOrientation, inSlotOnCarriage);
    }

    public boolean isSamePosition(Position inOtherPosition) {
        if (inOtherPosition == null) {
            return false;
        }
        if (!StringUtils.equals((String)this.getPosSlot(), (String)inOtherPosition.getPosSlot())) {
            return false;
        }
        if (!StringUtils.equals((String)this.getPosOperationalPosId(), (String)inOtherPosition.getPosOperationalPosId())) {
            return false;
        }
        if (!StringUtils.equals((String)this.getPosOrientation(), (String)inOtherPosition.getPosOrientation())) {
            return false;
        }
        return StringUtils.equals((String)this.getPosSlotOnCarriage(), (String)inOtherPosition.getPosSlotOnCarriage());
    }

    public boolean equals(Object inOther) {
        return inOther instanceof Position && this.isSamePosition((Position)((Object)inOther));
    }

    public int hashCode() {
        String s = this.getPosSlot();
        return s.hashCode();
    }

    public String getPosSlot() {
        return this._posSlot;
    }

    protected void setPosSlot(String inPosSlot) {
        this._posSlot = inPosSlot;
    }

    public String getPosOrientation() {
        return this._posOrientation;
    }

    protected void setPosOrientation(String inPosOrientation) {
        this._posOrientation = inPosOrientation;
    }

    public String getPosName() {
        return this._posName;
    }

    protected void setPosName(String inPosName) {
        this._posName = inPosName;
    }

    public String getPosOperationalPosId() {
        return this._posOperationalPosId;
    }

    protected void setPosOperationalPosId(String inPosOperationalPosId) {
        this._posOperationalPosId = inPosOperationalPosId;
    }

    public Long getPosTier() {
        return this._posTier;
    }

    public void setPosTier(Long inPosTier) {
        this._posTier = inPosTier;
    }

    public AbstractBin getPosBin() {
        return this._posBin;
    }

    public void setPosBin(AbstractBin inPosBin) {
        this._posBin = inPosBin;
    }

    public Point getPosAnchor() {
        return this._posAnchor;
    }

    public void setPosAnchor(Point inAnchor) {
        this._posAnchor = inAnchor;
    }

    public Double getPosOrientationDegrees() {
        return this._posOrientationDegrees;
    }

    public void setPosOrientationDegrees(Double inOrientationDegrees) {
        this._posOrientationDegrees = inOrientationDegrees;
    }

    public String getPosSlotOnCarriage() {
        return this._posSlotOnCarriage;
    }

    protected void setPosSlotOnCarriage(@Nullable String inPosSlotOnCarriage) {
        this._posSlotOnCarriage = inPosSlotOnCarriage;
    }

    public String toString() {
        return this.getPosName();
    }

}
