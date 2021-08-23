package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.OrderPurposeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.OrderPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.SystemOrderPurposeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class OrderPurpose extends OrderPurposeDO implements IOrderPurpose{
    private static final Logger LOGGER = Logger.getLogger(OrderPurpose.class);

    public OrderPurpose() {
        this.setOrderpurposeIsBuiltInEvent(Boolean.FALSE);
        this.setOrderpurposeLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static OrderPurpose findOrCreateOrderPurpose(String inId, String inDescription) {
        OrderPurpose orderPurpose;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("findOrCreateOrderPurpose for id:" + inId));
        }
        if ((orderPurpose = OrderPurpose.findOrderPurpose(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no order purpose for id " + inId + " found. Creating new one"));
            }
            orderPurpose = OrderPurpose.createOrderPurpose(inId, inDescription);
        }
        return orderPurpose;
    }

    public static OrderPurpose updateOrCreateOrderPurpose(String inId, String inDescription) {
        OrderPurpose orderPurpose;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info((Object)("updateOrCreateOrderPurpose for id:" + inId));
        }
        if ((orderPurpose = OrderPurpose.findOrderPurpose(inId)) == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("no order purpose for id " + inId + " found. Creating new one"));
            }
            orderPurpose = OrderPurpose.createOrderPurpose(inId, inDescription);
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("order purpose for id " + inId + " found. Updating fields."));
            }
            orderPurpose.setOrderpurposeDescription(inDescription);
            orderPurpose.setOrderpurposeLifeCycleState(LifeCycleStateEnum.ACTIVE);
            HibernateApi.getInstance().update((Object)orderPurpose);
        }
        return orderPurpose;
    }

    static OrderPurpose createOrderPurpose(String inId, String inDescription) {
        OrderPurpose orderPurpose = new OrderPurpose();
        orderPurpose.setOrderpurposeId(inId);
        orderPurpose.setOrderpurposeDescription(inDescription);
        orderPurpose.setOrderpurposeIsBuiltInEvent(Boolean.FALSE);
        HibernateApi.getInstance().save((Object)orderPurpose);
        return orderPurpose;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setOrderpurposeLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getOrderpurposeLifeCycleState();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.ORDERPURPOSE_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.ORDERPURPOSE_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public static OrderPurpose findOrderPurpose(String inOrderPurposeId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"OrderPurpose").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.ORDERPURPOSE_ID, (Object)inOrderPurposeId));
        return (OrderPurpose) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static OrderPurpose findOrderPurposeByExternalId(String inExternalId) {
        String orderPurposeId = inExternalId;
        OrderPurposeEnum orderPurposeEnum = SystemOrderPurposeEnum.getOrderPurposeEnumByExternalId(inExternalId);
        if (orderPurposeEnum != null) {
            orderPurposeId = orderPurposeEnum.getId();
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"OrderPurpose").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.ORDERPURPOSE_ID, (Object)orderPurposeId));
        return (OrderPurpose)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static OrderPurpose resolveIOrderPurpose(IOrderPurpose inIOrderPurpose) {
        if (inIOrderPurpose == null) {
            return null;
        }
        if (inIOrderPurpose instanceof OrderPurpose) {
            return (OrderPurpose)inIOrderPurpose;
        }
        OrderPurpose orderPurpose = OrderPurpose.findOrderPurpose(inIOrderPurpose.getId());
        if (orderPurpose == null) {
            throw BizFailure.create((String)("No order purpose exists for id " + inIOrderPurpose.getId()));
        }
        return orderPurpose;
    }

    public static void resolveAllBuiltInOrderPurposes() {
        ContextHelper.setThreadDataSource(DataSourceEnum.USER_DBA);
        Iterator iterator = OrderPurposeEnum.iterator();
        while (iterator.hasNext()) {
            OrderPurposeEnum orderPurposeEnum = (OrderPurposeEnum)iterator.next();
            OrderPurpose.createOrUpdateBuiltInOrderPurpose(orderPurposeEnum);
        }
    }

    private static OrderPurpose createOrUpdateBuiltInOrderPurpose(OrderPurposeEnum inOrderPurposeEnum) {
        String orderPurposeId = inOrderPurposeEnum.getId();
        OrderPurpose orderPurpose = OrderPurpose.findOrderPurpose(orderPurposeId);
        if (orderPurpose == null) {
            if (!inOrderPurposeEnum.isObsolete()) {
                orderPurpose = new OrderPurpose();
                orderPurpose.setOrderpurposeId(orderPurposeId);
                orderPurpose.setOrderpurposeDescription(inOrderPurposeEnum.getDescription());
                orderPurpose.setOrderpurposeIsBuiltInEvent(Boolean.TRUE);
                Roastery.getHibernateApi().save((Object)orderPurpose);
                HibernateApi.getInstance().flush();
            }
        } else {
            boolean updated = false;
            if (inOrderPurposeEnum.isObsolete()) {
                if (!LifeCycleStateEnum.OBSOLETE.equals((Object)orderPurpose.getOrderpurposeLifeCycleState())) {
                    orderPurpose.setLifeCycleState(LifeCycleStateEnum.OBSOLETE);
                    OrderPurposeEnum replacedBy = inOrderPurposeEnum.getReplacedBy();
                    String replacedByKey = replacedBy == null ? "none" : replacedBy.getKey();
                    LOGGER.warn((Object)("made order purpose <" + orderPurposeId + "> obsolete, replaced by " + replacedByKey));
                    updated = true;
                }
            } else if (LifeCycleStateEnum.OBSOLETE.equals((Object)orderPurpose.getOrderpurposeLifeCycleState())) {
                orderPurpose.setLifeCycleState(LifeCycleStateEnum.ACTIVE);
                LOGGER.warn((Object)("made order purpose <" + orderPurposeId + "> NOT obsolete"));
                updated = true;
            }
            if (updated) {
                Roastery.getHibernateApi().update((Object)orderPurpose);
                HibernateApi.getInstance().flush();
            }
        }
        return orderPurpose;
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        BizViolation bizViolation = super.validateChanges(inFieldChanges);
        if (this.getOrderpurposeGkey() != null) {
            DataSourceEnum dataSource = ContextHelper.getThreadDataSource();
            if (this.getOrderpurposeIsBuiltInEvent() != null && this.getOrderpurposeIsBuiltInEvent().booleanValue() && !DataSourceEnum.USER_DBA.equals((Object)dataSource)) {
                MetafieldIdList metafieldIdList = inFieldChanges.getFieldIds();
                for (IMetafieldId metafieldId : metafieldIdList) {
                    if (IArgoRefField.ORDERPURPOSE_DESCRIPTION.equals((Object)metafieldId) || IArgoRefField.ORDERPURPOSE_IS_BUILT_IN_EVENT.equals((Object)metafieldId)) continue;
                    bizViolation = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CAN_NOT_UPDATE_BUILT_IN_EVENT, (BizViolation)bizViolation);
                }
            }
        }
        return bizViolation;
    }

    public void setFieldValue(IMetafieldId inMetafieldId, Object inFieldValue) {
        super.setFieldValue(inMetafieldId, inFieldValue);
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        if (this.getOrderpurposeIsBuiltInEvent().booleanValue()) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CAN_NOT_DELETE_BUILT_IN_EVENT, (BizViolation)bv, (Object)this.getOrderpurposeId());
        }
        return bv;
    }

    @Override
    public String getId() {
        return this.getOrderpurposeId();
    }

    @Override
    public String getDescription() {
        String orderPurposeDescription = this.getOrderpurposeDescription();
        if (orderPurposeDescription != null) {
            return orderPurposeDescription;
        }
        return "";
    }

    @Override
    public boolean isBuiltInEventType() {
        return this.getOrderpurposeIsBuiltInEvent();
    }

    @Override
    public String getExternalId() {
        OrderPurposeEnum orderPurposeEnum = OrderPurposeEnum.getEnum(this.getOrderpurposeId());
        if (orderPurposeEnum != null && !StringUtils.isEmpty((String)orderPurposeEnum.getExternalId())) {
            return orderPurposeEnum.getExternalId();
        }
        return this.getOrderpurposeId();
    }

    public String toString() {
        return "OrderPurpose:" + this.getOrderpurposeId();
    }
}
