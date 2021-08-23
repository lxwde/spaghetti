package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.systems.Mappings;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.cfg.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.hibernate.type.Type;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import java.lang.module.Configuration;

public class HibernateMappingUtils {
    public static final String MAPPING_DYNAMIC_COMPONENT = "dynamic-component";
    public static final String MAPPING_NAME = "name";
    public static final String MAPPING_ENTITY_NAME = "entity-name";
    public static final String CLASS_ELEMENT = "class";
    public static final String ATTRIBUTE_TABLE = "table";
    private static final Logger LOGGER = Logger.getLogger(HibernateMappingUtils.class);

    private HibernateMappingUtils() {
    }

    public static String mergeDynamicComponents(Configuration inConfig, String inHbm) {
        String componentFieldId = null;
//        Mappings mappings = inConfig.createMappings();
//        Element element = HibernateMappingUtils.converHbmStringToElement(inHbm);
//        List classes = element.selectNodes(CLASS_ELEMENT);
//        for (Element clazz : classes) {
//            String classname = clazz.attributeValue(MAPPING_NAME);
//            Node component = clazz.selectSingleNode(MAPPING_DYNAMIC_COMPONENT);
//            if (!(component instanceof Element)) continue;
//            String componentName = ((Element)component).attributeValue(MAPPING_NAME);
//            HibernateMappingUtils.addToComponentField(mappings, inConfig, classname, componentName, (Element)component);
//            return componentName;
//        }
        return componentFieldId;
    }

    private static void addToComponentField(Mappings inMappings, Configuration inConfig, String inClassName, String inComponent, Element inXml) {
//        PersistentClass persistentClass = inConfig.getClassMapping(inClassName);
//        if (persistentClass == null) {
//            throw BizFailure.createProgrammingFailure("PersistentClass not found for   " + inClassName);
//        }
//        LogUtils.forceLogAtInfo(LOGGER, "Adding " + inComponent + " for " + inClassName);
//        Property componentProperty = null;
//        try {
//            componentProperty = persistentClass.getProperty(inComponent);
//        }
//        catch (MappingException mappingException) {
//            // empty catch block
//        }
//        String rolename = inClassName + "." + inComponent;
//        Component component = null;
//        if (componentProperty == null) {
//            component = new Component(persistentClass);
//            component.setDynamic(true);
//            component.setRoleName(rolename);
//            component.setNodeName(inComponent);
//            Property property = new Property();
//            property.setName(inComponent);
//            property.setNodeName(inComponent);
//            property.setPropertyAccessorName("property");
//            property.setValue((Value)component);
//            persistentClass.addProperty(property);
//            componentProperty = property;
//        }
//        Map inheritedMeta = persistentClass.getMetaAttributes();
//        component = (Component)componentProperty.getValue();
  //      HbmBinder.bindComponent((Element)inXml, (Component)component, (String)component.getComponentClassName(), (String)inComponent, (String)rolename, (boolean)true, (boolean)false, (Mappings)inMappings, (Map)inheritedMeta, (boolean)false);
    }

    public static Element converHbmStringToElement(String inString) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
//            reader.setEntityResolver(XMLHelper.DEFAULT_DTD_RESOLVER);
            document = reader.read((Reader)new StringReader(inString));
            return document.getRootElement();
        }
        catch (DocumentException e) {
            LOGGER.error((Object)("Problem converting hbm extension document due to" + (Object)((Object)e)));
            throw BizFailure.create("trouble converting string to element: " + inString + " due to " + (Object)((Object)e));
        }
    }

    public static Table getTableFromEntityName(String inEntityName) {
        org.hibernate.cfg.Configuration configuration = PersistenceUtils.getMappingConfiguration();
//        PersistentClass classMapping = configuration.getClassMapping(inEntityName);
//        if (classMapping != null) {
//            return classMapping.getTable();
//        }
        return null;
    }

    @Nullable
    public static Column getColumnObject(Configuration inConfig, ClassMetadata inMetaData, String inPropertyName) {
//        PersistentClass classMapping = inConfig.getClassMapping(inMetaData.getEntityName());
//        Value value = classMapping.getProperty(inPropertyName).getValue();
//        Iterator columns = value.getColumnIterator();
//        Column column = null;
//        while (columns.hasNext()) {
//            Object columnObject = columns.next();
//            if (!(columnObject instanceof Column)) continue;
//            column = (Column)columnObject;
//            break;
//        }
//        return column;
        return null;
    }

    public static int getColumnSize(Column inColumnObject, int inSize) {
        if (inColumnObject != null) {
            inSize = inColumnObject.getLength();
        }
        return inSize;
    }

    public static Column getColumn(Property inProperty) {
        if (inProperty.getValue().getColumnSpan() == 1) {
            Iterator iterator = inProperty.getColumnIterator();
            if (iterator.hasNext()) {
                Column col = (Column)iterator.next();
                return col;
            }
        } else {
            LOGGER.warn((Object)("getColumn does not support multi columns properties " + (Object)inProperty));
        }
        return null;
    }

    public static Column getColumn(Map inColumnMap, OuterJoinLoadable inPersister, String inPropertyKey) {
        String[] columnNames = inPersister.getPropertyColumnNames(inPropertyKey);
        String columnName = columnNames[0];
        return (Column)inColumnMap.get(columnName);
    }

    public static Map getColumnMap(Configuration inConfiguration, String inEntityClassName) {
        HashMap<String, Column> columns = new HashMap<String, Column>();
    //    PersistentClass classMapping = inConfiguration.getClassMapping(inEntityClassName);
//        if (classMapping != null) {
//            Table table = classMapping.getTable();
//            Iterator iterator = table.getColumnIterator();
//            while (iterator.hasNext()) {
//                Column col = (Column)iterator.next();
//                columns.put(col.getName(), col);
//            }
//        } else {
//            LOGGER.error((Object)("Custom class has no classMapping - " + inEntityClassName));
//        }
        return columns;
    }

    public static boolean isDynamicComponent(@NotNull Type inType) {
        return inType.isComponentType() && inType.getReturnedClass().isAssignableFrom(Map.class);
    }
}
