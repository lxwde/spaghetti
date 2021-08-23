package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.BinAncestorDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.HibernatingObjRef;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BinAncestor extends BinAncestorDO
        implements IBinCopyable, IObsoleteable {
    public BinAncestor() {
        this.setBanLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBanLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBanLifeCycleState();
    }

    public static BinAncestor updateOrCreateBinAncestor(AbstractBin inBin, AbstractBin inAncestorBin) {
        BinAncestor binAncestor = BinAncestor.findBinAncestorFromBinAndAncestorBin(inBin, inAncestorBin);
        if (binAncestor == null) {
            binAncestor = BinAncestor.createBinAncestor(inBin, inAncestorBin);
        } else {
            binAncestor.setBanBinLevel(inBin.getBinLevel());
            binAncestor.setBanAncestorLevel(inAncestorBin.getBinLevel());
        }
        return binAncestor;
    }

    public static BinAncestor createBinAncestor(AbstractBin inBin, AbstractBin inAncestorBin) {
        BinAncestor binAncestor = new BinAncestor();
        binAncestor.setBanBin(inBin);
        binAncestor.setBanAncestorBin(inAncestorBin);
        binAncestor.setBanBinLevel(inBin.getBinLevel());
        binAncestor.setBanAncestorLevel(inAncestorBin.getBinLevel());
        HibernateApi.getInstance().save((Object)binAncestor);
        return binAncestor;
    }

    @Nullable
    public static BinAncestor findBinAncestorFromBinAndAncestorBin(AbstractBin inBin, AbstractBin inAncestorBin) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinAncestor").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BAN_BIN, (Object)inBin.getAbnGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BAN_ANCESTOR_BIN, (Object)inAncestorBin.getAbnGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (BinAncestor) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static List<BinAncestor> findBinAncestorsForBin(AbstractBin inBin) {
        return BinAncestor.findBinAncestorsForBin(inBin, true);
    }

    public static List<BinAncestor> findBinAncestorsForBin(AbstractBin inBin, boolean inIncludeActiveFilter) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinAncestor").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BAN_BIN, (Object)inBin.getAbnGkey()));
        if (inIncludeActiveFilter) {
            dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public boolean isBanOwnedByBin(AbstractBin inOwningBin) {
        for (AbstractBin binAncestor = this.getBanBin(); binAncestor != null; binAncestor = binAncestor.getAbnParentBin()) {
            if (!binAncestor.equals(inOwningBin)) continue;
            return true;
        }
        return false;
    }

    public static void batchDeleteAncestorsForBins(List<Serializable> inBinGkeys) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinAncestor").addDqPredicate(PredicateFactory.in((IMetafieldId) BinCompoundField.BAN_BIN_GKEY, inBinGkeys));
        dq.setFilter(ObsoletableFilterFactory.createShowAllFilter());
        Object[] banGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (banGkeys.length > 0) {
            Roastery.getHibernateApi().batchDelete("BinAncestor", banGkeys);
        }
    }

    public boolean isOwningBinActive() {
        return this.getBanBin() != null && LifeCycleStateEnum.ACTIVE.equals((Object)this.getBanBin().getLifeCycleState());
    }

    @Override
    public void shallowCopy(Class inClass, Serializable inToTopBinGkey, long inTopBinLevel, IBinCopyable inObjToCopyTo) {
    }

    @Override
    public List<IMetafieldId> getKeyFields() {
        ArrayList<IMetafieldId> naturalKeyFields = new ArrayList<IMetafieldId>();
        naturalKeyFields.add(IBinField.BAN_ANCESTOR_LEVEL);
        naturalKeyFields.add(IBinField.BAN_BIN_LEVEL);
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BAN_BIN, (IMetafieldId) IBinField.ABN_NAME));
        return naturalKeyFields;
    }

    @Override
    public void addOwnedObjectKeysToListInCopyOrder(List<HibernatingObjRef> inOutKeysToCopy, IBinOwnedEntitiesFilter inOwnedEntitiesFilter) {
    }

    @Override
    public boolean hasNamingScheme() {
        return false;
    }

    @Override
    public IMetafieldId getTopBinField(long inTopBinLevel) {
        IMetafieldId topBinGkeyField = this.getBanBin().getTopBinField(inTopBinLevel);
        topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BAN_BIN, (IMetafieldId)topBinGkeyField);
        return topBinGkeyField;
    }

    public static Long getMaxBinAncestorLevelInDb() {
        IDomainQuery maxLevelDq = QueryUtils.createDomainQuery((String)"BinAncestor").addDqAggregateField(AggregateFunctionType.MAX, IBinField.BAN_BIN_LEVEL);
        maxLevelDq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult maxLevelResult = Roastery.getHibernateApi().findValuesByDomainQuery(maxLevelDq);
        Long maxLevel = null;
        if (maxLevelResult.getTotalResultCount() > 0) {
            maxLevel = (Long)maxLevelResult.getValue(0, 0);
        }
        return maxLevel;
    }
}
