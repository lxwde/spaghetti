package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.HazardousGoodsDO;
import com.zpmc.ztos.infra.base.business.enums.inventory.HazardsNumberTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.ImdgClassEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IInventoryField;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.Placard;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Set;

public class HazardousGoods extends HazardousGoodsDO {

    public static HazardousGoods createOrUpdateHazardousGoods(String inShippingName, String inUnNumber, ImdgClassEnum inHzgoodsClass, Placard inPlacard, Boolean inIsPlacardOptional) {
        return HazardousGoods.createOrUpdateHazardousGoods(inShippingName, inUnNumber, inHzgoodsClass, inPlacard, inIsPlacardOptional, HazardsNumberTypeEnum.UN);
    }

    public static HazardousGoods createOrUpdateHazardousGoods(String inShippingName, String inUnNumber, ImdgClassEnum inHzgoodsClass, Placard inPlacard, Boolean inIsPlacardOptional, HazardsNumberTypeEnum inHazNbrType) {
        HazardousGoods hzGoods = HazardousGoods.findHazardousGoods(inUnNumber);
        if (hzGoods == null) {
            hzGoods = new HazardousGoods();
        }
        hzGoods.setHzgoodsProperShippingName(inShippingName);
        hzGoods.setHzgoodsUnNbr(inUnNumber);
        hzGoods.setHzgoodsImdgClass(inHzgoodsClass);
        hzGoods.setHzgoodsPlacard(inPlacard);
        hzGoods.setHzgoodsIsPlacardOptional(inIsPlacardOptional);
        hzGoods.setHzgoodsNbrType(inHazNbrType);
        HibernateApi.getInstance().saveOrUpdate((Object)hzGoods);
        return hzGoods;
    }

    @Nullable
    public static HazardousGoods findHazardousGoods(String inUnNumber) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"HazardousGoods").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.HZGOODS_UN_NBR, (Object)inUnNumber));
        return (HazardousGoods)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public IMetafieldId getScopeFieldId() {
        return IInventoryField.HZGOODS_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IInventoryField.HZGOODS_UN_NBR;
    }

    public Object clone() throws CloneNotSupportedException {
        HazardousGoods clone = (HazardousGoods)super.clone();
        clone.setHzgoodsGkey(null);
        clone.setHzgoodsCreated(null);
        clone.setHzgoodsCreator(null);
        clone.setHzgoodsChanger(null);
        clone.setHzgoodsChanged(null);
        clone.setHzgoodsSubsidiaryRisks(null);
        return clone;
    }

    public void updateHzgoodsSubsidiaryRisks(Set inHzgoodsSubsidiaryRisks) {
        this.setHzgoodsSubsidiaryRisks(inHzgoodsSubsidiaryRisks);
    }

    public static HazardousGoods hydrate(Serializable inPrimaryKey) {
        return (HazardousGoods)HibernateApi.getInstance().load(HazardousGoods.class, inPrimaryKey);
    }
}
