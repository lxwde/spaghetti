package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.EquipPrefixDO;
import com.zpmc.ztos.infra.base.business.enums.argo.CheckDigitAlgorithmEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoRefField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.EntitySet;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public class EquipPrefix extends EquipPrefixDO {
    public EquipPrefix(EntitySet inEqpfxRefSet, String inPrefix, EquipClassEnum inEqtypClass) {
        this();
        this.setEqpfxScope(inEqpfxRefSet);
        this.setEqpfxPrefix(inPrefix);
        this.setEqpfxEqClass(inEqtypClass);
    }

    @Nullable
    public static String extractPrefix(String inFullId) {
        String prefix = null;
        int firstDigit = -1;
        for (int i = 0; i < inFullId.length(); ++i) {
            char c = inFullId.charAt(i);
            if (!Character.isDigit(c)) continue;
            firstDigit = i;
            break;
        }
        if (firstDigit > 0) {
            prefix = inFullId.substring(0, firstDigit);
        }
        return prefix;
    }

    public EquipPrefix() {
        this.setEqpfxLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqpfxLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqpfxLifeCycleState();
    }

    public static EquipPrefix findEquipPrefix(String inPrefix) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipPrefix").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQPFX_PREFIX, (Object)inPrefix));
  //      return (EquipPrefix)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return null;
    }

    @Nullable
    public static EquipPrefix findEquipPrefixForId(String inFullId) {
        EquipPrefix equipPrefix = null;
        String prefix = EquipPrefix.extractPrefix(inFullId);
        if (prefix != null) {
            equipPrefix = EquipPrefix.findEquipPrefix(prefix);
        }
        return equipPrefix;
    }

    @Nullable
    public static CheckDigitAlgorithmEnum findEquipPrefixCheckDigitAlgorithm(String inFullId) {
        String prefix = EquipPrefix.extractPrefix(inFullId);
//        if (prefix != null) {
//            IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipPrefix").addDqField(IArgoRefField.EQPFX_CHECK_DIGIT_ALGM).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQPFX_PREFIX, (Object)prefix));
//            QueryResult result = HibernateApi.getInstance().findValuesByDomainQuery(dq);
//            if (result == null || result.getTotalResultCount() == 0) {
//                return null;
//            }
//            if (result.getTotalResultCount() == 1) {
//                return (CheckDigitAlgorithmEnum)((Object)result.getValue(0, IArgoRefField.EQPFX_CHECK_DIGIT_ALGM));
//            }
//            throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)result.getTotalResultCount(), (Object)dq);
//        }
        return null;
    }

    public static EquipPrefix createEquipPrefix(String inPrefix, EquipClassEnum inClass) {
        EquipPrefix eqpfx = new EquipPrefix();
        eqpfx.setEqpfxPrefix(inPrefix);
        eqpfx.setEqpfxCheckDigitAlgm(CheckDigitAlgorithmEnum.NONE);
        eqpfx.setEqpfxOwnerId(inPrefix.substring(0, inPrefix.length() - 1));
        eqpfx.setEqpfxEqClass(inClass);
        //HibernateApi.getInstance().saveOrUpdate((Object)eqpfx);
        return eqpfx;
    }

    public static EquipPrefix createEquipPrefix(String inPrefix) {
        return EquipPrefix.createEquipPrefix(inPrefix, EquipClassEnum.CONTAINER);
    }

    public static EquipPrefix findOrCreateEquipPrefix(String inPrefix) {
        EquipPrefix eqpfx = EquipPrefix.findEquipPrefix(inPrefix);
        if (eqpfx == null) {
            eqpfx = EquipPrefix.createEquipPrefix(inPrefix);
        }
        return eqpfx;
    }

    public static EquipPrefix findOrCreateEquipPrefix(String inPrefix, EquipClassEnum inClass) {
        EquipPrefix eqpfx = EquipPrefix.findEquipPrefix(inPrefix);
        if (eqpfx == null) {
            eqpfx = EquipPrefix.createEquipPrefix(inPrefix, inClass);
        }
        return eqpfx;
    }

    public static List findPrefixes(ScopedBizUnit inLine, Boolean inOwned) {
        List prefixes = null;
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"EquipPrefix");
//        if (inOwned.booleanValue()) {
//            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQPFX_OWNER_ID, (Object)inLine.getBzuId()));
//            prefixes = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
//        } else {
//            Disjunction disjunction = new Disjunction();
//            disjunction.add(PredicateFactory.ne((IMetafieldId)IArgoRefField.EQPFX_OWNER_ID, (Object)inLine.getBzuId()));
//            disjunction.add(PredicateFactory.isNull((IMetafieldId)IArgoRefField.EQPFX_OWNER_ID));
//            dq.addDqPredicate((PredicateIntf)disjunction);
//            prefixes = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
//            Iterator it = prefixes.iterator();
//            while (it.hasNext()) {
//                EquipPrefix prefix = (EquipPrefix)it.next();
//                ScopedBizUnit line = ScopedBizUnit.findScopedBizUnit(prefix.getEqpfxOwnerId(), BizRoleEnum.LINEOP);
//                if (line == null) continue;
//                it.remove();
//            }
//        }
        return prefixes;
    }

    public String toString() {
        return "EquipPrefix Id:" + this.getEqpfxPrefix();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQPFX_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQPFX_PREFIX;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }
}
