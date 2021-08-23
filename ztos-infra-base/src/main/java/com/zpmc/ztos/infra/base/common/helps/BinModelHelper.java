package com.zpmc.ztos.infra.base.common.helps;

import com.zpmc.ztos.infra.base.business.interfaces.IBinField;
import com.zpmc.ztos.infra.base.business.interfaces.IBlockField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.AbstractBin;
import com.zpmc.ztos.infra.base.business.model.AbstractBlock;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Comparator;

public class BinModelHelper {
    public static IMetafieldId getModelCodeMetaFieldId() {
        return IBinField.ABN_NAME;
    }

    public static String computeModelInternalBinName(String inBlockName, String inSectionName, String inStackName) {
        StringBuilder result = new StringBuilder();
        if (inBlockName != null) {
            result.append(inBlockName);
        }
        if (inSectionName != null) {
            result.append("0");
            result.append(inSectionName);
        }
        if (inStackName != null) {
            result.append(inStackName);
        }
        return result.toString();
    }

    public static long getMinColumn(AbstractBin inParentBin) {
        IMetafieldId parentBinMF = BinModelHelper.getParentBinMF(inParentBin);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack");
        dq.addDqAggregateField(AggregateFunctionType.MIN, IBlockField.AST_COL_INDEX);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)parentBinMF, (Object)inParentBin.getAbnGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBlockField.AST_COL_INDEX)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    public static long getMaxColumn(AbstractBin inParentBin) {
        IMetafieldId parentBinMF = BinModelHelper.getParentBinMF(inParentBin);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack");
        dq.addDqAggregateField(AggregateFunctionType.MAX, IBlockField.AST_COL_INDEX);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)parentBinMF, (Object)inParentBin.getAbnGkey()));
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBlockField.AST_COL_INDEX)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    public static long getMinTier(AbstractBin inParentBin) {
        IMetafieldId parentBinMF = BinModelHelper.getParentBinMF(inParentBin);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack");
        dq.addDqAggregateField(AggregateFunctionType.MIN, IBinField.ABN_Z_INDEX_MIN);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)parentBinMF, (Object)inParentBin.getAbnGkey()));
        dq.setScopingEnabled(false);
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBinField.ABN_Z_INDEX_MIN)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    public static long getMaxTier(AbstractBin inParentBin) {
        IMetafieldId parentBinMF = BinModelHelper.getParentBinMF(inParentBin);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"AbstractStack");
        dq.addDqAggregateField(AggregateFunctionType.MAX, IBinField.ABN_Z_INDEX_MAX);
        dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)parentBinMF, (Object)inParentBin.getAbnGkey()));
        dq.setScopingEnabled(false);
//        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
//        QueryResult qr = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//        Long returnVal = 0L;
//        if (qr != null && qr.getTotalResultCount() > 0 && (returnVal = (Long)qr.getValue(0, IBinField.ABN_Z_INDEX_MAX)) == null) {
//            returnVal = 0L;
//        }
//        return returnVal;
        return 0;
    }

    private static IMetafieldId getParentBinMF(AbstractBin inParentBin) {
        long parentBinLevel = inParentBin.getBinLevel();
        IMetafieldId parentBinMF = IBinField.ABN_PARENT_BIN;
        for (long level = parentBinLevel; level < 3L; ++level) {
            parentBinMF = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)parentBinMF, (IMetafieldId) IBinField.ABN_PARENT_BIN);
        }
        return parentBinMF;
    }

    @Nullable
    public static AbstractBlock getBlockFromModelBin(AbstractBin inBin) {
//        if (AbstractBin.isBinInstanceOf(inBin, AbstractBlock.class)) {
//            return (AbstractBlock)HibernateApi.getInstance().downcast((HibernatingEntity)inBin, AbstractBlock.class);
//        }
//        if (AbstractBin.isBinInstanceOf(inBin, AbstractSection.class)) {
//            return (AbstractBlock)HibernateApi.getInstance().downcast((HibernatingEntity)inBin.getAbnParentBin(), AbstractBlock.class);
//        }
//        if (AbstractBin.isBinInstanceOf(inBin, AbstractStack.class)) {
//            return (AbstractBlock)HibernateApi.getInstance().downcast((HibernatingEntity)inBin.getAbnParentBin().getAbnParentBin(), AbstractBlock.class);
//        }
        return null;
    }

    public static class ExtendedRowIndexComparator
            implements Comparator {
        public int compare(Object inO1, Object inO2) {
//            AbstractSection ys1 = (AbstractSection)HibernateApi.getInstance().downcast((HibernatingEntity)inO1, AbstractSection.class);
//            AbstractSection ys2 = (AbstractSection)HibernateApi.getInstance().downcast((HibernatingEntity)inO2, AbstractSection.class);
//            if (ys1.getAsnRowPairedInto() != null && ys1.getAsnRowPairedFrom() != null && ys2.getAsnRowPairedInto() != null && ys2.getAsnRowPairedFrom() != null) {
//                if (ys1.getAsnRowPairedInto() == 0L || ys1.getAsnRowPairedFrom() == 0L) {
//                    if (ys2.getAsnRowPairedInto() < ys2.getAsnRowPairedFrom()) {
//                        return ys2.getAsnRowIndex().compareTo(ys1.getAsnRowIndex());
//                    }
//                    return ys1.getAsnRowIndex().compareTo(ys2.getAsnRowIndex());
//                }
//                if (ys1.getAsnRowPairedInto() < ys1.getAsnRowPairedFrom()) {
//                    return ys2.getAsnRowIndex().compareTo(ys1.getAsnRowIndex());
//                }
//                return ys1.getAsnRowIndex().compareTo(ys2.getAsnRowIndex());
//            }
            return 0;
        }
    }

    public static class RowIndexComparator
            implements Comparator {
        public int compare(Object inO1, Object inO2) {
//            AbstractSection ys1 = (AbstractSection)HibernateApi.getInstance().downcast((HibernatingEntity)inO1, AbstractSection.class);
//            AbstractSection ys2 = (AbstractSection)HibernateApi.getInstance().downcast((HibernatingEntity)inO2, AbstractSection.class);
//            return ys1.getAsnRowIndex().compareTo(ys2.getAsnRowIndex());
            return 0;
        }
    }

    public static class ColIndexComparator
            implements Comparator {
        public int compare(Object inO1, Object inO2) {
//            AbstractStack ys1 = (AbstractStack)HibernateApi.getInstance().downcast((HibernatingEntity)inO1, AbstractStack.class);
//            AbstractStack ys2 = (AbstractStack)HibernateApi.getInstance().downcast((HibernatingEntity)inO2, AbstractStack.class);
//            return ys1.getAstColIndex().compareTo(ys2.getAstColIndex());
            return 0;
        }
    }
}
