package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.YrdBlkSharedYardDO;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class YrdBlkSharedYard extends YrdBlkSharedYardDO {
    @Nullable
    public BizViolation validateChanges(FieldChanges inChanges) {
        YrdBlkSupplement yardBlockSupl;
        BizViolation bvChain = super.validateChanges(inChanges);
        FieldChange fc = inChanges.getFieldChange(IArgoField.YBY_YRD_SUPL);
        if (fc != null && (yardBlockSupl = (YrdBlkSupplement)fc.getNewValue()) != null) {
            Yard ybyYard = null;
            fc = inChanges.getFieldChange(IArgoField.YBY_YARD);
            if (fc != null) {
                ybyYard = (Yard)fc.getNewValue();
            }
            if (ybyYard != null) {
                MetafieldIdList ids = new MetafieldIdList();
                ids.add(IArgoField.YBY_YARD);
                ids.add(IArgoField.YBY_YRD_SUPL);
                if (!this.isUniqueInClass(ids)) {
                    return BizViolation.create((IPropertyKey) IArgoPropertyKeys.DUPLICATE_YARD_BLOCK_SHARED_YARD_COMBINATION, (BizViolation)bvChain, (Object)yardBlockSupl.getYbsYard().getId(), (Object)yardBlockSupl.getYbsBlockId(), (Object)ybyYard.getId());
                }
                Yard ybsYard = yardBlockSupl.getYbsYard();
                if (ybyYard.equals(ybsYard)) {
                    return BizViolation.create((IPropertyKey) IArgoPropertyKeys.ILLEGAL_ATTEMPT_TO_SHARE_BLOCK, (BizViolation)bvChain, (Object)yardBlockSupl.getYbsYard().getId(), (Object)yardBlockSupl.getYbsBlockId(), (Object)ybyYard.getId());
                }
            }
        }
        return bvChain;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
    }

}
