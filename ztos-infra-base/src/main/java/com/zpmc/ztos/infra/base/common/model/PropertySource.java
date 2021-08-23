package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.dataobject.PropertySourceDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PropertyGroupEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Collection;

public class PropertySource extends PropertySourceDO {
    public static void persistPropertySourceBeans(Collection<PropertySourceBean> inBeans, DataSourceEnum inDataSource, String inUserId) {
        for (PropertySourceBean bean : inBeans) {
            PropertySource.updateDataSource(bean.getEntity(), bean.getPropertyGroup(), inDataSource, inUserId);
        }
    }

    public static void updateDataSource(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup, DataSourceEnum inDataSource, String inUserId) {
        Long primaryKey;
        Class propertyGroupOwner = inPropertyGroup.getOwningEntityClass();
        if (propertyGroupOwner == null || !propertyGroupOwner.isAssignableFrom(inEntity.getClass())) {
            throw BizFailure.create((String)("illegal attempt to persist property source " + inPropertyGroup.getKey() + "for " + inEntity.getHumanReadableKey()));
        }
        String entityName = inEntity.getEntityName();
        PropertySource propertySource = PropertySource.findPropertySourceProxy(entityName, primaryKey = (Long)inEntity.getPrimaryKey(), inPropertyGroup);
        if (propertySource == null) {
            propertySource = new PropertySource();
            propertySource.setPrpsrcAppliedToEntity(entityName);
            propertySource.setPrpsrcAppliedToPrimaryKey(primaryKey);
            propertySource.setPrpsrcPropertyGroup(inPropertyGroup);
            propertySource.setPrpsrcDataSource(inDataSource);
            propertySource.setPrpsrcTimestamp(ArgoUtils.timeNow());
            propertySource.setPrpsrcUserId(inUserId);
            HibernateApi.getInstance().save((Object)propertySource);
        } else {
            Object[] keyHolder = new Long[]{propertySource.getPrpsrcGkey()};
            FieldChanges updateChanges = new FieldChanges();
            updateChanges.setFieldChange(IArgoField.PRPSRC_DATA_SOURCE, (Object)inDataSource);
            updateChanges.setFieldChange(IArgoField.PRPSRC_TIMESTAMP, (Object)ArgoUtils.timeNow());
            updateChanges.setFieldChange(IArgoField.PRPSRC_USER_ID, (Object)inUserId);
            HibernateApi.getInstance().batchUpdate("PropertySource", keyHolder, updateChanges);
        }
    }

    @Nullable
    public static PropertySource findPropertySource(String inEntityName, Serializable inPrimaryKey, PropertyGroupEnum inPropertyGroup) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PropertySource").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_ENTITY, (Object)inEntityName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_PRIMARY_KEY, (Object)inPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_PROPERTY_GROUP, (Object)((Object)inPropertyGroup)));
        return (PropertySource)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static PropertySource findPropertySourceProxy(String inEntityName, Serializable inPrimaryKey, PropertyGroupEnum inPropertyGroup) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PropertySource").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_ENTITY, (Object)inEntityName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_PRIMARY_KEY, (Object)inPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_PROPERTY_GROUP, (Object)((Object)inPropertyGroup)));
        Serializable[] prpsrcGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (prpsrcGkey == null || prpsrcGkey.length == 0) {
            return null;
        }
        if (prpsrcGkey.length == 1) {
            return (PropertySource)HibernateApi.getInstance().load(PropertySource.class, prpsrcGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(prpsrcGkey.length), (Object)dq);
    }

    public static Collection<PropertySource> findPropertySources(String inEntityName, Serializable inPrimaryKey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"PropertySource").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_ENTITY, (Object)inEntityName)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.PRPSRC_APPLIED_TO_PRIMARY_KEY, (Object)inPrimaryKey));
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static DataSourceEnum findDataSourceForProperty(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup) {
        Long primaryKey;
        String entityName = inEntity.getEntityName();
        PropertySource propertySource = PropertySource.findPropertySource(entityName, primaryKey = (Long)inEntity.getPrimaryKey(), inPropertyGroup);
        return propertySource == null ? DataSourceEnum.UNKNOWN : propertySource.getPrpsrcDataSource();
    }

    public static void purgePropertySources(DatabaseEntity inEntity) {
        String entityName = inEntity.getEntityName();
        Long primaryKey = (Long)inEntity.getPrimaryKey();
        Collection<PropertySource> propertySources = PropertySource.findPropertySources(entityName, primaryKey);
        for (PropertySource propertySource : propertySources) {
            if (inEntity instanceof IPropertySourceEntity) {
                ((IPropertySourceEntity)inEntity).beforePropertySourcePurge();
            }
            HibernateApi.getInstance().delete((Object)propertySource);
        }
    }


}
