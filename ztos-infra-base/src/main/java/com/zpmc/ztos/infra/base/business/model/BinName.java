package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.BinNameDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.HibernatingObjRef;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BinName extends BinNameDO implements IBinCopyable {
    public BinName() {
        this.setBnmLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBnmLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBnmLifeCycleState();
    }

    public static BinName findOrCreateBinName(BinNameTable inBinNameTable, long inLogicalPosition, String inUserName) {
        BinName binName = BinName.findBinNameFromTableAndPosition(inBinNameTable, inLogicalPosition);
        if (binName == null) {
            binName = new BinName();
            binName.setBnmNameTable(inBinNameTable);
            binName.setBnmLogicalPosition(inLogicalPosition);
            HibernateApi.getInstance().save((Object)binName);
        }
        binName.setBnmUserName(inUserName);
        return binName;
    }

    public static BinName findBinNameFromTableAndPosition(BinNameTable inBinNameTable, long inLogicalPosition) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)inBinNameTable.getBntGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_LOGICAL_POSITION, (Object)inLogicalPosition));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (BinName) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static Long findPositionFromBinNameAndTable(String inUserName, Serializable inOwningTableGkey) {
        Long posIndex = -1L;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqField(IBinField.BNM_LOGICAL_POSITION).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)inOwningTableGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_USER_NAME, (Object)inUserName));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        IQueryResult qr = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
        if (qr != null && qr.getTotalResultCount() > 0) {
            posIndex = (Long)qr.getValue(0, IBinField.BNM_LOGICAL_POSITION);
        }
        return posIndex;
    }

    public static List<BinName> findBinNamesForOwningBin(AbstractBin inOwningBin) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) BinCompoundField.BNM_OWNING_BIN, (Object)inOwningBin.getAbnGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static List<BinName> findBinNamesForOwningTable(BinNameTable inOwningTable) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)inOwningTable.getBntGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static Serializable[] findBinNamesForOwningTable(Serializable inOwningTableGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"BinName").addDqPredicate(PredicateFactory.eq((IMetafieldId) IBinField.BNM_NAME_TABLE, (Object)inOwningTableGkey));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
    }

    @Override
    public void shallowCopy(Class inClass, Serializable inToTopBinGkey, long inTopBinLevel, IBinCopyable inObjToCopyTo) {
//        BinCopyableUtils.shallowCopy(inClass, this, inToTopBinGkey, inObjToCopyTo, inTopBinLevel);
    }

    @Override
    public List<IMetafieldId> getKeyFields() {
        ArrayList<IMetafieldId> naturalKeyFields = new ArrayList<IMetafieldId>();
        IMetafieldId owningBinType = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNM_NAME_TABLE, (IMetafieldId) IBinField.BNT_OWNING_BIN);
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)owningBinType, (IMetafieldId) IBinField.ABN_BIN_TYPE));
        IMetafieldId owningBinName = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNM_NAME_TABLE, (IMetafieldId) IBinField.BNT_OWNING_BIN);
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)owningBinName, (IMetafieldId) IBinField.ABN_NAME));
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNM_NAME_TABLE, (IMetafieldId) IBinField.BNT_TABLE_NAME));
        naturalKeyFields.add(MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNM_NAME_TABLE, (IMetafieldId) IBinField.BNT_TABLE_INDEX));
        naturalKeyFields.add(IBinField.BNM_LOGICAL_POSITION);
        return naturalKeyFields;
    }

    @Override
    public void addOwnedObjectKeysToListInCopyOrder(List<HibernatingObjRef> inOutKeysToCopy, IBinOwnedEntitiesFilter inOwnedEntitiesFilter) {
        inOutKeysToCopy.add(new HibernatingObjRef(BinName.class, this.getBnmGkey()));
    }

    @Override
    public IMetafieldId getTopBinField(long inTopBinLevel) {
        IMetafieldId topBinGkeyField = this.getBnmNameTable().getBntOwningBin().getTopBinField(inTopBinLevel);
        topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNT_OWNING_BIN, (IMetafieldId)topBinGkeyField);
        topBinGkeyField = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IBinField.BNM_NAME_TABLE, (IMetafieldId)topBinGkeyField);
        return topBinGkeyField;
    }

    @Override
    public boolean hasNamingScheme() {
        return false;
    }

    public void preProcessDelete(FieldChanges inOutMoreChanges) {
        super.preProcessDelete(inOutMoreChanges);
    }

    public void preDelete() {
        this.getBnmNameTable().removeFromCache(this.getBnmLogicalPosition());
        super.preDelete();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        if (inChanges.hasFieldChange(IBinField.BNM_LIFE_CYCLE_STATE) && LifeCycleStateEnum.OBSOLETE.equals(inChanges.getFieldChange(IBinField.BNM_LIFE_CYCLE_STATE).getNewValue())) {
            this.getBnmNameTable().removeFromCache(this.getBnmLogicalPosition());
        }
        return super.validateChanges(inChanges);
    }

}
