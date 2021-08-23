package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class SnxUtil {
    private static final char NATURAL_KEY_FLAG = '^';
    private static final String SNX_BEAN_PREFIX = "snx";
    private static final String SNX_EXPORTER_BEAN_SUFFIX = "Exporter";
    private static final String SNX_IMPORTER_BEAN_SUFFIX = "Importer";
    public static final String SNX_TOPOLOGY = "Topology";
    private static final Logger LOGGER = Logger.getLogger(SnxUtil.class);

    private SnxUtil() {
    }

    public static String getExporterBeanName(String inEntityName) {
        return SNX_BEAN_PREFIX + inEntityName + SNX_EXPORTER_BEAN_SUFFIX;
    }

    public static String getImporterBeanName(String inElementName) {
        return SNX_BEAN_PREFIX + SnxUtil.dehyphenateAndCapitalize(inElementName) + SNX_IMPORTER_BEAN_SUFFIX;
    }

    public static boolean isEntitySnxExportable(String inEntityName) {
        String beanName = SnxUtil.getExporterBeanName(inEntityName);
        return Roastery.containsBean((String)beanName);
    }

    public static IEntityXmlExporter getSnxExporterForEntity(String inEntityName) {
        String exporterBeanName = SnxUtil.getExporterBeanName(inEntityName);
        if (!Roastery.containsBean((String)exporterBeanName)) {
            throw BizFailure.create((String)("No Exporter found for " + inEntityName + ". Expected a bean named <" + exporterBeanName + ">"));
        }
        Object bean = Roastery.getBean((String)exporterBeanName);
        if (!(bean instanceof IEntityXmlExporter)) {
            throw BizFailure.create((String)("Exporter <" + exporterBeanName + "> needs to be refactored per ARGO-7012"));
        }
        return (IEntityXmlExporter)bean;
    }

    public static ISnxImporter getSnxImporterForElement(String inElementName) throws BizViolation {
        String importerBeanName = SnxUtil.getImporterBeanName(inElementName);
        if (!Roastery.containsBean((String)importerBeanName)) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_IMPORTER, null, (Object)importerBeanName, (Object)inElementName);
        }
        return (ISnxImporter) Roastery.getBean((String)importerBeanName);
    }

    public static String dehyphenateAndCapitalize(String inString) {
        boolean capitalize = true;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < inString.length(); ++i) {
            boolean hyphen;
            char c = inString.charAt(i);
            boolean bl = hyphen = c == '-';
            if (!hyphen) {
                result.append(capitalize ? Character.toUpperCase(c) : c);
            }
            capitalize = hyphen;
        }
        return result.toString();
    }

    public static String hyphenate(String inString) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < inString.length(); ++i) {
            char c = inString.charAt(i);
            if (i == 0) {
                result.append(Character.toLowerCase(c));
                continue;
            }
            if (Character.isUpperCase(c)) {
                result.append('-').append(Character.toLowerCase(c));
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }

    public static Element getSnxExport(String inEntityName, Serializable[] inPrimaryKeys) {
        IEntityXmlExporter exporter = SnxUtil.getSnxExporterForEntity(inEntityName);
        exporter.setPrimaryKeys(inPrimaryKeys);
//        Element root = XmlUtil.createRootElement("argo", "http://www.navis.com/argo snx.xsd");
//        Iterator<Element> elementIterator = exporter.getElementIterator();
//        while (elementIterator.hasNext()) {
//            Element e = elementIterator.next();
//            root.addContent((Content)e);
//        }
//        return root;
        return null;
    }

    @Nullable
    public static String externalKey2internalKey(String inFieldId, String inExternalKey) {
        if (StringUtils.isEmpty((String)inExternalKey)) {
            return null;
        }
        IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)inFieldId);
        INaturallyKeyedEntity entity = SnxUtil.convertKey2NaturallyKeyedEntity(metafieldId, inExternalKey, false);
        return entity == null ? inExternalKey : SnxUtil.getExternalKey(entity);
    }

    @Nullable
    public static String internalKey2ExternalKey(String inFieldId, String inInternalKey) {
        if (StringUtils.isEmpty((String)inInternalKey)) {
            return null;
        }
        IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)inFieldId);
        INaturallyKeyedEntity entity = SnxUtil.convertKey2NaturallyKeyedEntity(metafieldId, inInternalKey, true);
        return entity == null ? inInternalKey : SnxUtil.getInternalKey(entity);
    }

    private static INaturallyKeyedEntity convertKey2NaturallyKeyedEntity(IMetafieldId inMetafieldId, String inKey, boolean inIsInternalKey) {
        if (StringUtils.isEmpty((String)inKey)) {
            return null;
        }
        Class propertyClass = HiberCache.getFieldClass((String)inMetafieldId.getFieldId());
        if (propertyClass == null) {
            LOGGER.error((Object)("getNaturallyKeyedEntity: no propertyClass found for " + inMetafieldId.getFieldId()));
            return null;
        }
        boolean isNaturallyKeyedEntity = INaturallyKeyedEntity.class.isAssignableFrom(propertyClass);
        if (!isNaturallyKeyedEntity && SnxUtil.isSyntheticField(inMetafieldId) && (propertyClass = HiberCache.getEntityClassForField((String)inMetafieldId.getFieldId())) != null && INaturallyKeyedEntity.class.isAssignableFrom(propertyClass)) {
            isNaturallyKeyedEntity = true;
        }
        if (!isNaturallyKeyedEntity) {
            return null;
        }
        if (!inIsInternalKey && inKey.charAt(0) == '^') {
            inKey = inKey.substring(1);
        }
        return SnxUtil.convertKey2NaturallyKeyedEntity(propertyClass, inKey, inIsInternalKey);
    }

    private static INaturallyKeyedEntity convertKey2NaturallyKeyedEntity(Class inPropertyClass, String inKey, boolean inIsInternalKey) {
        try {
            INaturallyKeyedEntity entity;
            if (inIsInternalKey) {
                Object primaryKey;
                try {
                    primaryKey = Long.parseLong(inKey);
                }
                catch (NumberFormatException e) {
                    primaryKey = inKey;
                }
                entity = (INaturallyKeyedEntity) HibernateApi.getInstance().load(inPropertyClass, (Serializable)primaryKey);
            } else {
                INaturallyKeyedEntity sample = (INaturallyKeyedEntity)inPropertyClass.newInstance();
                entity = sample.findByNaturalKey(inKey);
                if (entity == null) {
                    LOGGER.error((Object)("Entry not found with ID '" + inKey + "' for '" + inPropertyClass.getSimpleName() + "'"));
                }
            }
            return entity;
        }
        catch (Exception e) {
            LOGGER.error((Object)("convertKey2NaturallyKeyedEntity: could not convert: " + e));
            return null;
        }
    }

    private static boolean isSyntheticField(IMetafieldId inMetafieldId) {
        IMetafieldDictionary meta = Roastery.getMetafieldDictionary();
        IMetafield metafield = meta.findMetafield(inMetafieldId);
        return metafield != null && metafield.isSyntheticField();
    }

    private static String getInternalKey(INaturallyKeyedEntity inEntity) {
        return '^' + inEntity.getNaturalKey();
    }

    @Nullable
    private static String getExternalKey(INaturallyKeyedEntity inEntity) {
        Serializable primaryKey = inEntity == null ? null : inEntity.getPrimaryKey();
        return primaryKey == null ? null : primaryKey.toString();
    }
}
