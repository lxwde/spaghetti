package com.zpmc.ztos.infra.base.common.database;

import com.alibaba.fastjson.support.geo.Geometry;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.RelativeTimeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.ReferenceEntity;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.utils.DiagnosticUtils;
import com.zpmc.ztos.infra.base.common.utils.PersistenceUtils;
import com.zpmc.ztos.infra.base.common.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.ComponentType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

public class HiberCache implements IDetailedDiagnostics {
    private static HiberCache _instance;
    private final Map _entityName2ClassMeta = new HashMap();
    private final Map _entityName2Class = new HashMap();
    private final Map _entityAlias2EntityName = new HashMap();
    private final Map _entityClass2EntityName = new HashMap();
    private final Map<String, String> _className2EntityAlias = new HashMap<String, String>();
    private final Map<Class, String> _enums = new HashMap<Class, String>();
    private final Map _fieldId2FieldType = new HashMap();
    private final Map<String, Class> _fieldId2FieldClass = new HashMap<String, Class>();
    private final Map _fieldId2EntityClass = new HashMap();
    private final Set _primaryKeyFields = new HashSet();
    private final Set _dbFields = new HashSet();
    private static final Logger LOGGER;
    private static final char PARM_FLAG = '&';
    private static final String PERSISTED_DATE_FORMAT = "yyyyMMddHHmmssz";

    public static synchronized void reload() {
        _instance = null;
        HiberCache.getInstance();
    }

    static synchronized HiberCache getInstance() {
        if (_instance == null) {
            _instance = new HiberCache();
        }
        return _instance;
    }

    public static IEntity newInstanceForEntity(String inEntityName) {
        try {
            Class entityClass = HiberCache.entityName2EntityClass(inEntityName);
            if (entityClass == null) {
                LOGGER.error((Object)("newInstanceForEntity: HiberCache does not contain '" + inEntityName + "'"));
                throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__CAN_NOT_INSTANTIATE_ENTITY, null, inEntityName);
            }
            if (Modifier.isAbstract(entityClass.getModifiers())) {
                LOGGER.error((Object)("newInstanceForEntity requested for abstract class " + inEntityName));
                return null;
            }
            Object o = entityClass.newInstance();
            if (!(o instanceof IEntity)) {
                LOGGER.error((Object)("Created entity " + inEntityName + "is not of the type 'IEntity'"));
                LOGGER.error((Object)"Include the following line in your .hbm.xml file:");
                LOGGER.error((Object)"<meta attribute=\"extends\">com.navis.framework.persistence.DatabaseEntity</meta>");
            }
            if (o instanceof IDynamicHibernatingEntity) {
                ((IDynamicHibernatingEntity)o).setEntityName(inEntityName);
            }
            return (IEntity)o;
        }
        catch (Exception e) {
            LOGGER.error((Object)("newInstanceForEntity: Failed to instantiate entity of class '" + inEntityName + "'"), (Throwable)e);
            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__CAN_NOT_INSTANTIATE_ENTITY, e, inEntityName);
        }
    }

    public static Class entityName2EntityClass(String inEntityAlias) {
        ClassMetadata classMetadata = HiberCache.getClassMetadata(inEntityAlias);
        if (classMetadata != null) {
  //          return classMetadata.getMappedClass(EntityMode.POJO);
        }
        return (Class) HiberCache.getInstance()._entityName2Class.get(inEntityAlias);
    }

    @Deprecated
    public static ClassMetadata getClassMetadata(Class inPersistentClass) {
        String entityName = HiberCache.getEntityNameFromClassName(inPersistentClass.getName());
        return HiberCache.getClassMetadata(entityName);
    }

    @Nullable
    public static ClassMetadata getClassMetadata(String inEntityName) {
        SessionFactory hiberFactory = PersistenceUtils.getSessionFactory();
        ClassMetadata meta = hiberFactory.getClassMetadata(inEntityName);
        if (meta != null) {
            return meta;
        }
        return (ClassMetadata)HiberCache.getInstance()._entityName2ClassMeta.get(inEntityName);
//        return null;
    }

    @Deprecated
    public static String getEntityAliasUsedForAuditFields(Class inPersistentClass) {
        return HiberCache.getInstance()._className2EntityAlias.get(inPersistentClass.getName());
    }

    public static String getEntityAliasUsedForAuditFields(String inHibernatingEntityName) {
        return HiberCache.getInstance()._className2EntityAlias.get(inHibernatingEntityName);
    }

    public static void setEntityAliasUsedForAuditFields(Class inPersistentClass, String inPrefix) {
        HiberCache.getInstance()._className2EntityAlias.put(inPersistentClass.getName(), inPrefix);
    }

    public static Type getFieldType(String inFieldId) {
        return (Type) HiberCache.getInstance()._fieldId2FieldType.get(inFieldId);
    }

    public static Class getFieldClass(String inFieldId) {
        HiberCache hc = HiberCache.getInstance();
        return hc._fieldId2FieldClass.get(inFieldId);
    }

    public static Class getEntityClassForField(String inFieldId) {
        HiberCache hc = HiberCache.getInstance();
        return (Class)hc._fieldId2EntityClass.get(inFieldId);
    }

    public static String getEntityNameForField(String inFieldId) {
        HiberCache hc = HiberCache.getInstance();
        Object clazz = hc._fieldId2EntityClass.get(inFieldId);
        return (String)hc._entityClass2EntityName.get(clazz);
    }

    public static boolean isPrimaryKeyField(String inFieldId) {
        return HiberCache.getInstance()._primaryKeyFields.contains(inFieldId);
    }

    public static String getEntityNameFromClassName(String inFullClassName) {
        int lastDot = inFullClassName.lastIndexOf(46);
        String shortname = inFullClassName.substring(lastDot + 1, inFullClassName.length());
        return shortname;
    }

    public static String getFullEntityNameFromClassName(Type inFieldType) {
        Class associatedClass = inFieldType.getReturnedClass();
        if (inFieldType.isEntityType() && Map.class.isAssignableFrom(associatedClass)) {
            String associatedEntityName = ((EntityType)inFieldType).getAssociatedEntityName();
            return associatedEntityName;
        }
        return associatedClass.getName();
    }

    public static List getAllEntityNames() {
        HiberCache hc = HiberCache.getInstance();
        Set keys = hc._entityName2Class.keySet();
        ArrayList names = new ArrayList(keys);
        Collections.sort(names);
        return names;
    }

    private HiberCache() {
//        SessionFactory hiberFactory = (SessionFactory)Roastery.getBean("hibernateSessionFactory");
//        try {
//            Map classMetas = hiberFactory.getAllClassMetadata();
//            ArrayList<ClassBean> classList = new ArrayList<ClassBean>(classMetas.keySet().size());
//            for (String entityName : classMetas.keySet()) {
//                ClassMetadata classMeta = (ClassMetadata)classMetas.get(entityName);
//                Class pojoClass = classMeta.getMappedClass(EntityMode.POJO);
//                if (pojoClass.isAssignableFrom(Map.class)) {
//                    LOGGER.error((Object)("HiberCache: custom class based on Map <" + entityName + "> is NOT supported! \n"));
//                    continue;
//                }
//                Class entityClass = null;
//                try {
//                    entityClass = classMeta.getMappedClass(EntityMode.POJO);
//                }
//                catch (Throwable e) {
//                    LOGGER.error((Object)("HiberCache: Unable to instantiate <" + entityName + ">\n" + e));
//                }
//                int depth = 0;
//                for (Class superclass = entityClass.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
//                    ++depth;
//                }
//                classList.add(new ClassBean(classMeta, depth, entityClass));
//            }
//            Collections.sort(classList);
//            for (ClassBean classBean : classList) {
//                Class entityClass = classBean.getEntityClass();
//                String fullname = entityClass.getName();
//                LOGGER.debug((Object)("HiberCache: registering entity class: " + fullname + " inheritance depth = " + classBean.getInheritanceDepth()));
//                String shortname = HiberCache.getEntityNameFromClassName(fullname);
//                this._entityName2Class.put(shortname, entityClass);
//                this._entityClass2EntityName.put(entityClass, shortname);
//                ClassMetadata meta = classBean.getMetadata();
//                if (meta == null) {
//                    LOGGER.error((Object)("No class found for " + fullname));
//                    continue;
//                }
//                String idPropertyName = meta.getIdentifierPropertyName();
//                int i = 0;
//                while (Character.isLowerCase(idPropertyName.charAt(i))) {
//                    ++i;
//                }
//                String alias = idPropertyName.substring(0, i);
//                String hibernateEntityName = entityClass.getName();
//                if (DynamicHibernatingEntity.class.isAssignableFrom(entityClass)) {
//                    MetaAttribute attribute;
//                    hibernateEntityName = meta.getEntityName();
//                    PersistentClass classMapping = PersistenceUtils.getPersistentClass(meta.getEntityName());
//                    shortname = HiberCache.getEntityNameFromClassName(hibernateEntityName);
//                    if (classMapping != null && (attribute = classMapping.getMetaAttribute("n4alias")) != null) {
//                        alias = attribute.getValue();
//                    }
//                }
//                this._entityAlias2EntityName.put(alias, shortname);
//                this._className2EntityAlias.put(hibernateEntityName, alias);
//                this._entityName2ClassMeta.put(shortname, meta);
//                String[] names = meta.getPropertyNames();
//                Type[] types = meta.getPropertyTypes();
//                for (int j = 0; j < names.length; ++j) {
//                    if (!this._dbFields.contains(names[j])) {
//                        LOGGER.debug((Object)("HiberCache: caching " + names[j] + " as " + (Object)types[j]));
//                        this.registerField(names[j], types[j]);
//                        this._fieldId2EntityClass.put(names[j], entityClass);
//                        if (!(types[j] instanceof ComponentType)) continue;
//                        this.addComponent((ComponentType)types[j]);
//                        continue;
//                    }
//                    if (!DynamicExtensionConsts.CUSTOM_ENTITY_FIELDS.getQualifiedId().equals(names[j]) && !DynamicExtensionConsts.CUSTOM_FLEX_FIELDS.getQualifiedId().equals(names[j]) || !(types[j] instanceof ComponentType)) continue;
//                    this.addComponent((ComponentType)types[j]);
//                }
//                if (this._dbFields.contains(idPropertyName)) continue;
//                this._dbFields.add(idPropertyName);
//                this._fieldId2FieldType.put(idPropertyName, meta.getIdentifierType());
//                this._fieldId2EntityClass.put(idPropertyName, entityClass);
//                this._primaryKeyFields.add(idPropertyName);
//            }
//        }
//        catch (Throwable t) {
//            throw new RuntimeException("failure initializing DatabaseEntity" + t.getMessage());
//        }
    }

    private void addComponent(ComponentType inCt) {
        String[] subnames = inCt.getPropertyNames();
        Type[] subtypes = inCt.getSubtypes();
        for (int i = 0; i < subnames.length; ++i) {
            LOGGER.debug((Object)("HiberCache: caching component " + subnames[i] + " as " + (Object)subtypes[i]));
            this.registerField(subnames[i], subtypes[i]);
            Class entityClass = inCt.getReturnedClass();
            this._fieldId2EntityClass.put(subnames[i], entityClass);
            if (!(subtypes[i] instanceof ComponentType)) continue;
            this.addComponent((ComponentType)subtypes[i]);
        }
    }

    private void registerField(String inField, Type inType) {
        this._dbFields.add(inField);
        this._fieldId2FieldType.put(inField, inType);
        this._fieldId2FieldClass.put(inField, inType.getReturnedClass());
        Class typeClass = inType.getReturnedClass();
        if (AtomizedEnum.class.isAssignableFrom(typeClass)) {
            this._enums.put(typeClass, inType.getName());
        }
    }

    public static String property2String(IMetafieldId inMetafieldId, Object inValue) {
        boolean isValueLong;
        if (inValue == null) {
            return null;
        }
        if (inMetafieldId == null) {
            String error = "property2String: null inMetafieldId with value  <" + inValue + "> of Class <" + inValue.getClass() + '>';
            throw new IllegalArgumentException(error);
        }
        Class propertyClass = HiberCache.getFieldClass(inMetafieldId.getFieldId());
        if (propertyClass == null) {
            return inValue.toString();
        }
        boolean isValueDouble = inValue instanceof Double || StringUtil.isDouble(inValue);
        boolean bl = isValueLong = inValue instanceof Number || StringUtil.isLong(inValue);
        if (String.class.isAssignableFrom(propertyClass) && inValue instanceof String) {
            return (String)inValue;
        }
        if (inValue instanceof String && ((String)inValue).charAt(0) == '&') {
            return (String)inValue;
        }
        if (AtomizedEnum.class.isAssignableFrom(propertyClass) && inValue instanceof AtomizedEnum) {
            return ((AtomizedEnum)inValue).getKey();
        }
        if (Long.class.isAssignableFrom(propertyClass) && isValueLong) {
            return inValue.toString();
        }
        if (Double.class.isAssignableFrom(propertyClass) && isValueDouble) {
            return inValue.toString();
        }
        if (Integer.class.isAssignableFrom(propertyClass) && inValue instanceof Integer) {
            return inValue.toString();
        }
        if (Boolean.class.isAssignableFrom(propertyClass) && inValue instanceof Boolean) {
            return inValue.toString();
        }
        if (Date.class.isAssignableFrom(propertyClass) && inValue instanceof Date) {
            return HiberCache.getPersistedDateFormat().format(inValue);
        }
        if (Date.class.isAssignableFrom(propertyClass) && inValue instanceof RelativeTimeEnum) {
            return ((RelativeTimeEnum)inValue).getKey();
        }
        if (DatabaseEntity.class.isAssignableFrom(propertyClass) && (inValue instanceof Long || inValue instanceof String)) {
            return inValue.toString();
        }
        if (Geometry.class.isAssignableFrom(propertyClass) && inValue instanceof Geometry) {
            return inValue.toString();
        }
        if (DatabaseEntity.class.isAssignableFrom(propertyClass) && inValue instanceof DatabaseEntity) {
            DatabaseEntity he = (DatabaseEntity)inValue;
            Serializable primaryKey = he.getPrimaryKey();
            if (primaryKey == null) {
                String error = "property2String: could not encode field <" + inMetafieldId + "> of Class <" + propertyClass + "> with value <" + inValue + "> because it has no primary key (not yet persisted?";
                throw new IllegalArgumentException(error);
            }
            return primaryKey.toString();
        }
        String error = "property2String: could not encode field <" + inMetafieldId + "> of Class <" + propertyClass + "> with value <" + inValue + "> of Class <" + inValue.getClass() + '>';
        throw new IllegalArgumentException(error);
    }

    public static Object string2Property(IMetafieldId inMetafieldId, String inPersistedString) {
        if (StringUtils.isEmpty((String)inPersistedString)) {
            return null;
        }
        if (inMetafieldId == null) {
            String error = "string2Property: null inMetafieldId with input  <" + inPersistedString + '>';
            throw new IllegalArgumentException(error);
        }
        Class propertyClass = HiberCache.getFieldClass(inMetafieldId.getFieldId());
        if (inPersistedString.charAt(0) == '&') {
            return inPersistedString;
        }
        if (propertyClass == null) {
            return inPersistedString;
        }
//        if (ConversionUtils.isSimpleConversionType(propertyClass)) {
//            return ConversionUtils.convertSimpleTypeFromString(propertyClass, inPersistedString);
//        }
//        if (Date.class.isAssignableFrom(propertyClass)) {
//            Date date;
//            try {
//                date = HiberCache.getPersistedDateFormat().parse(inPersistedString);
//            }
//            catch (ParseException e) {
//                RelativeTimeEnum timeEnum = RelativeTimeEnum.getEnum(inPersistedString);
//                if (timeEnum != null) {
//                    return timeEnum;
//                }
//                String error = "string2Property: could not decode date for field <" + inMetafieldId + "> with persisted value <" + inPersistedString + ">";
//                throw new IllegalArgumentException(error);
//            }
//            return date;
//        }
//        if (DatabaseEntity.class.isAssignableFrom(propertyClass)) {
//            Object value = inPersistedString;
//            try {
//                value = Long.valueOf(inPersistedString);
//            }
//            catch (Exception e) {
//                // empty catch block
//            }
//            return value;
//        }
//        if (Geometry.class.isAssignableFrom(propertyClass)) {
//            WKTReader fromText = new WKTReader();
//            try {
//                return fromText.read(inPersistedString);
//            }
//            catch (com.vividsolutions.jts.io.ParseException pe) {
//                String error = "Invalid String representation for SPATIAL argument " + inMetafieldId;
//                throw new IllegalArgumentException(error);
//            }
//        }
        String error = "string2Property: could not decipher field <" + inMetafieldId + "> of Class <" + propertyClass + "> with value <" + inPersistedString + ">";
        throw new IllegalArgumentException(error);
    }

    public static void registerComputedField(IMetafieldId inField, Class inValueClass, String inEntityName) {
        LOGGER.info((Object)("registerComputedField: inField = " + inField));
        Class fieldClass = HiberCache.entityName2EntityClass(inEntityName);
        HiberCache instance = HiberCache.getInstance();
        String fieldId = inField.getFieldId();
        instance._fieldId2EntityClass.put(fieldId, fieldClass);
        instance._fieldId2FieldClass.put(fieldId, inValueClass);
    }

    public static boolean isSyntheticField(String inFieldId) {
        return !HiberCache.getInstance()._dbFields.contains(inFieldId);
    }

    public static List getAllReferenceEntityNames() {
        ArrayList refEntityNames = new ArrayList();
        HiberCache hc = HiberCache.getInstance();
        Map entityClass2EntityName = hc._entityClass2EntityName;
        for (Object entityClass : entityClass2EntityName.keySet()) {
            if (!ReferenceEntity.class.isAssignableFrom((Class)entityClass)) continue;
            refEntityNames.add(entityClass2EntityName.get(entityClass));
        }
        Collections.sort(refEntityNames);
        return refEntityNames;
    }

    public static boolean isDbNullable(IMetafieldId inMetafieldId) {
//        Metafield metafield = Roastery.getMetafieldDictionary().findMetafield(inMetafieldId);
//        if (metafield == null) {
//            return false;
//        }
//        return FuzzyBoolean.TRUE.equals(metafield.getDbNullable());
        return false;
    }

    public static boolean doesEntitySupportCodeExtensions(String inEntity) {
//        IMetafieldEntity entity = Roastery.getMetafieldDictionary().getEntityEntry(EntityIdFactory.valueOf(inEntity));
//        if (entity == null) {
//            return false;
//        }
//        return entity.supportsCodeExtensions();
        return false;
    }

    @Override
    public String getDetailedDiagnostics() {
        StringBuilder buf = new StringBuilder("\n\nHiberCache Contents \n");
        buf.append("\n_fieldId2FieldType " + DiagnosticUtils.getFormattedContents(this._fieldId2FieldType, true));
        buf.append("\n_fieldId2FieldClass " + DiagnosticUtils.getFormattedContents(this._fieldId2FieldClass, true));
        buf.append("\n_fieldId2EntityClass " + DiagnosticUtils.getFormattedContents(this._fieldId2EntityClass, true));
        buf.append("\n\n_primaryKeyFields " + DiagnosticUtils.convertToDebugFormat(this._primaryKeyFields));
        buf.append("\n\n_entityAlias2EntityName " + DiagnosticUtils.getFormattedContents(this._entityAlias2EntityName, true));
        buf.append("\n_entityClass2EntityName " + DiagnosticUtils.getFormattedContents(this._entityClass2EntityName, false));
        buf.append("\n_class2EntityAlias " + DiagnosticUtils.getFormattedContents(this._className2EntityAlias, false));
        return buf.toString();
    }

    public static Map<Class, String> getAtomizedEnums() {
        return new HashMap<Class, String>(HiberCache.getInstance()._enums);
    }

    public static String getDebugString() {
        return HiberCache.getInstance().getDetailedDiagnostics();
    }

    private static SimpleDateFormat getPersistedDateFormat() {
        return new SimpleDateFormat(PERSISTED_DATE_FORMAT);
    }

    static {
        LOGGER = Logger.getLogger(HiberCache.class);
    }

    private class ClassBean
            implements Comparable {
        private final ClassMetadata _metadata;
        private final int _inheritanceDepth;
        private final Class _entityClass;

        ClassBean(ClassMetadata inMetadata, int inInheritanceDepth, Class inEntityClass) {
            this._inheritanceDepth = inInheritanceDepth;
            this._entityClass = inEntityClass;
            this._metadata = inMetadata;
        }

        public ClassMetadata getMetadata() {
            return this._metadata;
        }

        public Class getEntityClass() {
            return this._entityClass;
        }

        public int getInheritanceDepth() {
            return this._inheritanceDepth;
        }

        public int compareTo(@NotNull Object inObject) {
            ClassBean otherBean = (ClassBean)inObject;
            return this._inheritanceDepth - otherBean.getInheritanceDepth();
        }
    }

}
