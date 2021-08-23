package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.AbstractBlockDO;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.helps.BinModelHelper;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractBlock extends AbstractBlockDO implements IParentInBinModel {

    public String getBlockName() {
        return this.getAbnName();
    }

    public static IMetafieldId getBlockCodeMetaFieldId() {
        return IBinField.ABN_NAME;
    }

    public void setBinNameTableRefs(int inRowConvIdStd, int inRowConvIdAlt, int inTierConvId) {
        BinNameTable binNameTable;
        if (inRowConvIdStd > 0) {
            binNameTable = BinNameTable.findBinNameTableFromTableIndex(inRowConvIdStd, this.getAbnParentBin());
            this.setAblConvIdRowStd(binNameTable);
        }
        if (inRowConvIdAlt > 0) {
            binNameTable = BinNameTable.findBinNameTableFromTableIndex(inRowConvIdAlt, this.getAbnParentBin());
            this.setAblConvIdRowAlt(binNameTable);
        }
        if (inTierConvId > 0) {
            binNameTable = BinNameTable.findBinNameTableFromTableIndex(inTierConvId, this.getAbnParentBin());
            this.setAblConvIdTier(binNameTable);
        }
    }

    public long getMinRow() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractSection");
        dq.addDqAggregateField(AggregateFunctionType.MIN, IBlockField.ASN_ROW_INDEX);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.ABN_PARENT_BIN, (Object)this.getAbnGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBlockField.ASN_ROW_INDEX)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    public long getMaxRow() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractSection");
        dq.addDqAggregateField(AggregateFunctionType.MAX, IBlockField.ASN_ROW_INDEX);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.ABN_PARENT_BIN, (Object)this.getAbnGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBlockField.ASN_ROW_INDEX)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    @Override
    public long getBinLevel() {
        return 0;
    }

 //   @Override
    public String getTierName(Long inTier) {
        BinName binName;
        BinNameTable convID = this.getAblConvIdTier();
        BinName binName2 = binName = convID != null ? convID.findBinNameFromLogicalPosition(inTier) : null;
        if (binName != null) {
            return binName.getBnmUserName();
        }
 //       return super.getTierName(inTier);
        return "";
    }

    @Nullable
    public List<AbstractStack> findStacksInLogicalArea(long inMinRow, long inMaxRow, long inMinCol, long inMaxCol) {
        IMetafieldId sectionRowIndex = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.ABN_PARENT_BIN, (IMetafieldId) IBlockField.ASN_ROW_INDEX);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack").addDqPredicate(PredicateFactory.ge((IMetafieldId)sectionRowIndex, (Object)inMinRow)).addDqPredicate(PredicateFactory.le((IMetafieldId)sectionRowIndex, (Object)inMaxRow)).addDqPredicate(PredicateFactory.ge((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)inMinCol)).addDqPredicate(PredicateFactory.le((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)inMaxCol));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        this.addAncestorBinJoin(dq, true);
//        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return null;
    }

    @Nullable
    public AbstractStack findStackByRowAndColumn(long inRow, long inCol) {
        IMetafieldId sectionRowIndex = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.ABN_PARENT_BIN, (IMetafieldId) IBlockField.ASN_ROW_INDEX);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack").addDqPredicate(PredicateFactory.eq((IMetafieldId)sectionRowIndex, (Object)inRow)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)inCol));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        this.addAncestorBinJoin(dq, true);
//        List stacks = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
//        return stacks != null ? (!stacks.isEmpty() ? (AbstractStack)stacks.get(0) : null) : null;
        return null;
    }

    @Override
    public long getMinColumn() {
        return BinModelHelper.getMinColumn(this);
    }

    @Override
    public long getMaxColumn() {
        return BinModelHelper.getMaxColumn(this);
    }

    public long getMinTier() {
        return BinModelHelper.getMinTier(this);
    }

    @Override
    public long getMaxTier() {
        return BinModelHelper.getMaxTier(this);
    }

    @Nullable
    public AbstractBlock getBlock() {
        return this;
    }

    public static AbstractBlock downcast(AbstractBin inBin) {
 //       return (AbstractBlock)Roastery.getHibernateApi().downcast((HibernatingEntity)inBin, AbstractBlock.class);
        return null;
    }

    @Nullable
    public static AbstractBlock findYardBlockByCode(@NotNull Serializable inYardBinModelGkey, @NotNull String inBlockCode) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractBlock").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.ABN_PARENT_BIN, (Object)inYardBinModelGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) AbstractBlock.getBlockCodeMetaFieldId(), (Object)inBlockCode));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        List blocksList = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
//        return blocksList != null ? (!blocksList.isEmpty() ? (AbstractBlock)blocksList.get(0) : null) : null;
        return null;
    }

}
