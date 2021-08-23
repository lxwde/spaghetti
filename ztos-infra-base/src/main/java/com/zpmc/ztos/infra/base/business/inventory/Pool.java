package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.PoolDO;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Set;

public class Pool extends PoolDO {
    public Pool() {
        this.setPoolAdminLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static Pool findOrCreatePool(String inPoolId, String inPoolName) {
        Pool pool = Pool.findPool(inPoolId);
        if (pool == null) {
            pool = Pool.createPool(inPoolId, inPoolName);
        }
        return pool;
    }

    @Nullable
    public static Pool findPool(String inPoolId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Pool").addDqPredicate(PredicateFactory.eq((IMetafieldId) IPoolsField.POOL_ID, (Object)inPoolId));
        try {
            return (Pool) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getPoolComplex() == null) {
            this.setSelfAndFieldChange(IPoolField.POOL_COMPLEX, (Object) ContextHelper.getThreadComplex(), inOutMoreChanges);
        }
    }

    public static Pool createPool(String inPoolId, String inPoolName) {
        Pool pool = new Pool();
        pool.setPoolId(inPoolId);
        pool.setPoolName(inPoolName);
        pool.setPoolComplex(ContextHelper.getThreadComplex());
        HibernateApi.getInstance().save((Object)pool);
        return pool;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setPoolAdminLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getPoolAdminLifeCycleState();
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        bv = this.checkRequiredField(bv, IPoolField.POOL_ID);
        bv = this.checkRequiredField(bv, IPoolField.POOL_NAME);
        MetafieldIdList mfil = new MetafieldIdList();
        mfil.add(IPoolField.POOL_ID);
        mfil.add(IPoolField.POOL_COMPLEX);
        if (!this.isUniqueInClass(mfil)) {
            bv = new BizViolation(IFrameworkPropertyKeys.CRUD__DUPLICATE_NATURAL_KEY, null, bv, null, new Object[]{MetafieldIdFactory.valueOf((String)IPoolField.POOL_ID.toString()), this.getFieldValue(IPoolField.POOL_ID)});
        }
        return bv;
    }

    public boolean truckCoAuthorized(ScopedBizUnit inTruckCo) {
        Set poolTruckCos = this.getPoolTruckCos();
        if (poolTruckCos != null && !poolTruckCos.isEmpty()) {
            for (Object poolTruckCo1 : poolTruckCos) {
//                PoolTruckCo poolTruckCo = (PoolTruckCo)poolTruckCo1;
//                ScopedBizUnit ptcSbu = poolTruckCo.getPooltrkcoTruckingCompany();
//                if (ptcSbu == null || !ptcSbu.equals((Object)inTruckCo)) continue;
//                return poolTruckCo.getPooltrkcoBanned() == false;
            }
            return false;
        }
        return true;
    }

    public Long getPoolMemberTableKey() {
        return this.getPoolGkey();
    }

    public Long getPoolTruckCoTableKey() {
        return this.getPoolGkey();
    }

    public Long getPoolEquipmentTableKey() {
        return this.getPoolGkey();
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public static Pool hydrate(@NotNull Serializable inPrimaryKey) {
        return (Pool)HibernateApi.getInstance().load(Pool.class, inPrimaryKey);
    }
}
