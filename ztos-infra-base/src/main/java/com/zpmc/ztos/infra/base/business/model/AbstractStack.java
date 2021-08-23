package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.AbstractStackDO;
import com.zpmc.ztos.infra.base.business.dataobject.BinNameDO;
import com.zpmc.ztos.infra.base.business.enums.spatial.BinNameTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractStack extends AbstractStackDO {
    public AbstractSection loadAndGetParentAbstractSection() {
        return (AbstractSection)HibernateApi.getInstance().load(AbstractSection.class, (Serializable)this.getAbnParentBin().getAbnGkey());
    }

    public String getColumnName(BinNameTypeEnum inEquipBasicLength) {
        AbstractSection section = (AbstractSection)HibernateApi.getInstance().load(AbstractSection.class, (Serializable)this.getAbnParentBin().getAbnGkey());
        BinNameTable convID = section.getAsnConvIdCol();
        BinNameDO binName = null;
        if (convID != null) {
            binName = convID.findBinNameFromLogicalPosition(this.getAstColIndex() + section.getAsnColTableOffset());
        }
        if (binName != null) {
            return binName.getBnmUserName();
        }
        String columnName = Long.toString(this.getAstColIndex());
        if (columnName.length() == 1) {
            columnName = "0" + columnName;
        }
        return columnName;
    }

    @Override
    public String computeInternalBinName(BinNameTypeEnum inBinNameType) {
        AbstractSection section = (AbstractSection)HibernateApi.getInstance().load(AbstractSection.class, (Serializable)this.getAbnParentBin().getAbnGkey());
        AbstractBlock abstractBlock = (AbstractBlock)HibernateApi.getInstance().load(AbstractBlock.class, (Serializable)section.getAbnParentBin().getAbnGkey());
        return abstractBlock.getBlockName() + section.getRowName(inBinNameType) + this.getColumnName(inBinNameType);
    }

    public List<AbstractStack> findStacksOverhungByThisStack() {
        long sectionGkey = this.getAbnParentBin().getAbnGkey();
        AbstractSection section = (AbstractSection)HibernateApi.getInstance().load(AbstractSection.class, (Serializable) Long.valueOf(sectionGkey));
        IMetafieldId sectionRowIndexMI = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.ABN_PARENT_BIN, (IMetafieldId) IBlockField.ASN_ROW_INDEX);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName()).addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_GRANDPARENT_BIN, (Object)section.getAbnParentBin().getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_GREAT_GRANDPARENT_BIN, (Object)section.getAbnParentBin().getAbnParentBin().getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)sectionRowIndexMI, (Object)section.getAsnRowPairedInto())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)this.getAstColIndex()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public List<AbstractStack> findStacksOverhangingThisStack() {
        long sectionGkey = this.getAbnParentBin().getAbnGkey();
        AbstractSection section = (AbstractSection)HibernateApi.getInstance().load(AbstractSection.class, (Serializable) Long.valueOf(sectionGkey));
        IMetafieldId sectionRowIndexMI = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.ABN_PARENT_BIN, (IMetafieldId) IBlockField.ASN_ROW_INDEX);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName()).addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_GRANDPARENT_BIN, (Object)section.getAbnParentBin().getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.ABN_GREAT_GRANDPARENT_BIN, (Object)section.getAbnParentBin().getAbnParentBin().getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)sectionRowIndexMI, (Object)section.getAsnRowPairedFrom())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)this.getAstColIndex()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public List<AbstractStack> findAdjacentStacks() {
        IPredicate higherColumnPredicate = PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)(this.getAstColIndex() + 1L));
        IPredicate lowerColumnPredicate = PredicateFactory.eq((IMetafieldId) IBlockField.AST_COL_INDEX, (Object)(this.getAstColIndex() - 1L));
        IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName()).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.ABN_PARENT_BIN, (Object)this.getAbnParentBin().getAbnGkey())).addDqPredicate((IPredicate) PredicateFactory.disjunction().add(higherColumnPredicate).add(lowerColumnPredicate));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    @Override
    public Long getTierIndexFromInternalSlotString(String inSlotName) {
        Long tierIndex = 0L;
        String tierString = this.getTierStringFromInternalSlotString(inSlotName);
        if (tierString != null) {
            BinName tierName;
            AbstractBlock block = (AbstractBlock) HibernateApi.getInstance().downcast((DatabaseEntity)this.getAbnParentBin().getAbnParentBin(), AbstractBlock.class);
            BinNameTable binNameTable = block.getAblConvIdTier();
            tierIndex = binNameTable != null ? ((tierName = binNameTable.findBinNameFromUserName(tierString)) != null ? tierName.getBnmLogicalPosition() : super.getTierIndexFromInternalSlotString(inSlotName)) : super.getTierIndexFromInternalSlotString(inSlotName);
        }
        return tierIndex;
    }

    public HashMap<Long, String> getTierNameMap() {
        long minTier = this.getAbnZIndexMin();
        long maxTier = this.getAbnZIndexMax();
        HashMap<Long, String> result = new HashMap<Long, String>();
        for (long tierIndex = minTier; tierIndex < maxTier + 1L; ++tierIndex) {
            result.put(tierIndex, this.getTierName(tierIndex));
        }
        return result;
    }

    public static class ColIndexComparator
            implements Comparator {
        public int compare(Object inO1, Object inO2) {
            AbstractStack stack1 = (AbstractStack)HibernateApi.getInstance().downcast((DatabaseEntity)inO1, AbstractStack.class);
            AbstractStack stack2 = (AbstractStack)HibernateApi.getInstance().downcast((DatabaseEntity)inO2, AbstractStack.class);
            return stack1.getAstColIndex().compareTo(stack2.getAstColIndex());
        }
    }
}
