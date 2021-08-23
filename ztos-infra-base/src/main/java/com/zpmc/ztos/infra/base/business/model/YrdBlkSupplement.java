package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.YrdBlkSupplementDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Set;

public class YrdBlkSupplement extends YrdBlkSupplementDO {
    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        Complex cpx;
        super.preProcessInsertOrUpdate(inOutMoreChanges);
        if (this.getYbsYard() != null && (cpx = this.getComplex()) != null) {
            this.setSelfAndFieldChange(IArgoField.YBS_COMPLEX, cpx, inOutMoreChanges);
        }
    }

    @Nullable
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bvChain = super.validateChanges(inChanges);
        Yard ybsYard = null;
        String blockId = null;
        FieldChange fc = inChanges.getFieldChange(IArgoField.YBS_YARD);
        if (fc != null) {
            ybsYard = (Yard)fc.getNewValue();
        }
        if ((fc = inChanges.getFieldChange(IArgoField.YBS_BLOCK_ID)) != null) {
            blockId = (String)fc.getNewValue();
        }
        if (ybsYard != null && !StringUtils.isEmpty((String)blockId)) {
            AbstractBin ybsYardModel = ybsYard.getYrdBinModel();
            if (ybsYardModel == null) {
                return BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_YARD_MODEL_FOR_YBY_YARD, (BizViolation)bvChain, (Object)ybsYard.getId());
            }
            BinContext binContext = BinContext.findBinContext((String)"STOWAGE_CONTAINERS");
            if (binContext == null) {
                throw BizFailure.createProgrammingFailure((String)("Failed to verify " + blockId + " is a valid block in Yard"));
            }
            AbstractBin aBin = ybsYardModel.findDescendantBinFromInternalSlotString(blockId, binContext);
            if (aBin == null) {
                return BizViolation.create((IPropertyKey) IArgoPropertyKeys.BLOCK_DOES_NOT_EXIST_IN_YBY_YARD, (BizViolation)bvChain, (Object)blockId, (Object)ybsYard.getId());
            }
        }
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(IArgoField.YBS_YARD);
        ids.add(IArgoField.YBS_BLOCK_ID);
        if (!this.isUniqueInClass(ids)) {
            bvChain = BizViolation.create((IPropertyKey) IArgoPropertyKeys.DUPLICATE_YARD_BLOCK_COMBINATION, (BizViolation)bvChain, (Object)this.getYbsYard().getId(), (Object)this.getYbsBlockId());
        }
        return bvChain;
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        Set ybsSet = this.getYbsYbySet();
        if (ybsSet != null && !ybsSet.isEmpty()) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.DELETE_SHARED_YARDS, (BizViolation)bv);
        }
        return bv;
    }

    public Serializable getYbsSharedYardsTableKey() {
        return this.getYbsGkey();
    }

    @Nullable
    private Complex getComplex() {
        return this.getYbsYard().getYrdFacility().getFcyComplex();
    }

    @Nullable
    public static YrdBlkSupplement findYrdBlkSupplement(@NotNull Yard inYard, @NotNull String inBlockId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"YrdBlkSupplement").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YBS_YARD, (Object)inYard.getYrdGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YBS_BLOCK_ID, (Object)inBlockId));
        return (YrdBlkSupplement)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static YrdBlkSupplement findOrCreateYrdBlkSupplement(@NotNull Yard inYard, @NotNull String inBlockId) {
        YrdBlkSupplement result = YrdBlkSupplement.findYrdBlkSupplement(inYard, inBlockId);
        if (result == null) {
            result = YrdBlkSupplement.createYrdBlkSupplement(inYard, inBlockId);
        }
        return result;
    }

    public static YrdBlkSupplement createYrdBlkSupplement(@NotNull Yard inYard, @NotNull String inBlockId) {
        YrdBlkSupplement result = new YrdBlkSupplement();
        result.setYbsYard(inYard);
        result.setYbsBlockId(inBlockId);
        result.setYbsComplex(inYard.getYrdFacility().getFcyComplex());
        HibernateApi.getInstance().save((Object)result);
        return result;
    }

    public boolean isSharedWithYard(@NotNull Yard inYard) {
        return this.getYrdBlkSharedYardFor(inYard) != null;
    }

    @Nullable
    YrdBlkSharedYard getYrdBlkSharedYardFor(@NotNull Yard inYard) {
        YrdBlkSharedYard result = null;
        Set yrd2YbySet = this.getYbsYbySet();
        if (yrd2YbySet != null) {
            for (Object ybsYby : yrd2YbySet) {
                if (!inYard.equals(((YrdBlkSharedYard)ybsYby).getYbyYard())) continue;
                result = (YrdBlkSharedYard)ybsYby;
                break;
            }
        }
        return result;
    }

    private YrdBlkSharedYard addSharedYard(@NotNull Yard inYard) {
        YrdBlkSharedYard result = new YrdBlkSharedYard();
        result.setFieldValue(IArgoField.YBY_YRD_SUPL, this.getPrimaryKey());
        result.setFieldValue(IArgoField.YBY_YARD, inYard.getPrimaryKey());
        HibernateApi.getInstance().save((Object)result);
        return result;
    }

    public YrdBlkSharedYard shareWithYard(@NotNull Yard inYard) {
        YrdBlkSharedYard result = this.addSharedYard(inYard);
        YrdBlkSupplement otherYrdBlkSupl = YrdBlkSupplement.findOrCreateYrdBlkSupplement(inYard, this.getYbsBlockId());
        if (!otherYrdBlkSupl.isSharedWithYard(this.getYbsYard())) {
            otherYrdBlkSupl.addSharedYard(this.getYbsYard());
        }
        return result;
    }

    private void removeSharedYard(@NotNull Yard inYard) {
        Set ybySet;
        YrdBlkSharedYard yby = this.getYrdBlkSharedYardFor(inYard);
        if (yby != null && (ybySet = this.getYbsYbySet()) != null) {
            ybySet.remove(yby);
        }
    }

    public void undoShareWithYard(@NotNull Yard inYard) {
        this.removeSharedYard(inYard);
        YrdBlkSupplement otherYrdBlkSupl = YrdBlkSupplement.findYrdBlkSupplement(inYard, this.getYbsBlockId());
        if (otherYrdBlkSupl != null) {
            otherYrdBlkSupl.removeSharedYard(this.getYbsYard());
        }
    }
}
