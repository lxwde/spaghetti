package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.AbstractSectionDO;
import com.zpmc.ztos.infra.base.business.dataobject.BinNameDO;
import com.zpmc.ztos.infra.base.business.enums.spatial.BinNameTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.helps.BinModelHelper;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Comparator;

public abstract class AbstractSection extends AbstractSectionDO implements IParentInBinModel {
    @Override
    public String computeInternalBinName(BinNameTypeEnum inBinNameType) {
        AbstractBlock abstractBlock = (AbstractBlock)HibernateApi.getInstance().load(AbstractBlock.class, (Serializable)this.getAbnParentBin().getAbnGkey());
        return abstractBlock.getBlockName() + this.getRowName(inBinNameType);
    }

    public String getAsnRowNameStandard() {
        return this.getRowName(BinNameTypeEnum.STANDARD);
    }

    public String getAsnRowNameAlternate() {
        return this.getRowName(BinNameTypeEnum.ALTERNATE);
    }

    @Nullable
    public String getRowName(BinNameTypeEnum inBinNameType) {
        AbstractBlock block = (AbstractBlock)HibernateApi.getInstance().load(AbstractBlock.class, (Serializable)this.getAbnParentBin().getAbnGkey());
        BinNameTable convID = inBinNameType == BinNameTypeEnum.STANDARD ? block.getAblConvIdRowStd() : block.getAblConvIdRowAlt();
        BinNameDO binName = null;
        if (convID != null) {
            binName = convID.findBinNameFromLogicalPosition(this.getAsnRowIndex());
        }
        if (binName != null) {
            return binName.getBnmUserName();
        }
        String rowName = Long.toString(2L * this.getAsnRowIndex() - 1L);
        if (rowName.length() == 1) {
            rowName = "00" + rowName;
        } else if (rowName.length() == 2) {
            rowName = "0" + rowName;
        }
        return rowName;
    }

    @Override
    public long getMinColumn() {
        return BinModelHelper.getMinColumn(this);
    }

    @Override
    public long getMaxColumn() {
        return BinModelHelper.getMaxColumn(this);
    }

    @Override
    public long getMaxTier() {
        return BinModelHelper.getMaxTier(this);
    }

    @Override
    public String getUiFullPositionWithTier(BinNameTypeEnum inBinNameType, String inTierName) {
        String binName = this.computeInternalBinName(inBinNameType);
        if (inTierName != null && inTierName.isEmpty()) {
            return binName;
        }
        return binName + "." + inTierName;
    }

    @Nullable
    public AbstractStack findStackFromColIndex(long inColumnIndex) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.ABN_PARENT_BIN, (Object)this.getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)inColumnIndex));
    //    dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (AbstractStack) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static Serializable[] findAllSectionsReferringToBinNameTable(Serializable[] inNameTableGkeys) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractSection").addDqPredicate(PredicateFactory.in((IMetafieldId) IBlockField.ASN_CONV_ID_COL, (Object[])inNameTableGkeys));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);

    }

    public static class RowIndexComparator
            implements Comparator {
        public int compare(Object inO1, Object inO2) {
//            AbstractSection section1 = (AbstractSection) HibernateApi.getInstance().downcast((HibernatingEntity)inO1, AbstractSection.class);
//            AbstractSection section2 = (AbstractSection) HibernateApi.getInstance().downcast((HibernatingEntity)inO2, AbstractSection.class);
//            return section1.getAsnRowIndex().compareTo(section2.getAsnRowIndex());
            return 0;
        }
    }
}
