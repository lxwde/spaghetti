package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.framework.BeanCreationTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.ICustomCodeExtensionDrivenBeanDefinition;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticBizMetafield;
import com.zpmc.ztos.infra.base.business.interfaces.IEBean;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BeanFactoryUtil {

    private static final Logger LOGGER = Logger.getLogger(BeanFactoryUtil.class);

    public static String getClassPathFileName(String inFileName) {
        String classPathName = inFileName;
        if (org.apache.commons.lang.StringUtils.isNotEmpty((String)inFileName) && !inFileName.startsWith("classpath:")) {
            classPathName = "classpath:" + classPathName;
        }
        return classPathName;
    }

    @Nullable
    public static XmlBeanFactory loadAsBeanFactory(String inFileName) {
        LOGGER.info((Object)("Loading Stream " + inFileName));
        XmlBeanFactory beanFactory = null;
        try {
            beanFactory = new XmlBeanFactory(ResourceUtils.loadResource(inFileName));
        }
        catch (Exception e) {
            LOGGER.error((Object)"Load did not succeed. Exception: ", (Throwable)e);
        }
        return beanFactory;
    }

    public static String[] beanNamesIncludingAncestors(ListableBeanFactory inFactory, Class inType) {
        return BeanFactoryUtil.beanNamesForTypeIncludingAncestors(inFactory, inType, false, false);
    }

    public static String[] beanNamesForTypeIncludingAncestors(ListableBeanFactory inLbf, Class inType, boolean inIncludePrototypes, boolean inAllowEagerInit) {
        HierarchicalBeanFactory hbf;
        Assert.notNull((Object)inLbf, (String)"ListableBeanFactory must not be null");
        String[] result = inLbf.getBeanNamesForType(inType, inIncludePrototypes, inAllowEagerInit);
        if (inLbf instanceof HierarchicalBeanFactory && (hbf = (HierarchicalBeanFactory)inLbf).getParentBeanFactory() instanceof ListableBeanFactory) {
            String[] parentResult = BeanFactoryUtil.beanNamesForTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), inType, inIncludePrototypes, inAllowEagerInit);
            ArrayList<String> resultList = new ArrayList<String>();
            for (String beanName : parentResult) {
                if (resultList.contains(beanName) || hbf.containsLocalBean(beanName)) continue;
                resultList.add(beanName);
            }
            resultList.addAll(Arrays.asList(result));
            result = StringUtils.toStringArray(resultList);
        }
        return result;
    }

    public static Class resolveBeanClass(ConfigurableListableBeanFactory inFactory, String inBeanName) {
        Assert.notNull((Object)inBeanName, (String)"'BeanDefinition' can not be 'null'.");
        return inFactory.getType(inBeanName);
    }

    public static String getBeanDiagnostics(ConfigurableListableBeanFactory inFactory, String inBean) {
        StringBuilder buf = new StringBuilder(200);
        try {
            BeanDefinition beanDefinition = inFactory.getBeanDefinition(inBean);
            buf.append("BEAN DEFINITION:\n");
            buf.append((Object)beanDefinition);
            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                buf.append("\nConstructor arguments:\n");
                Map map = argumentValues.getIndexedArgumentValues();
                for (Object number : map.keySet()) {
                    ConstructorArgumentValues.ValueHolder values = (ConstructorArgumentValues.ValueHolder)map.get(number);
                    buf.append("\n" + number + ":" + values.getValue());
                }
                List genericArgs = argumentValues.getGenericArgumentValues();
                if (genericArgs != null && !genericArgs.isEmpty()) {
                    buf.append("\nGeneric arguments:\n");
                    buf.append(argumentValues.getGenericArgumentValues());
                }
            }
            buf.append("\n\n\n Bean TOSTRING:\n");
            Object bean = inFactory.getBean(inBean);
            buf.append(DiagnosticUtils.getDiagnosticDetails(bean, true));
        }
        catch (Throwable t) {
            buf.append("Error getting diagnostic information: " + t + CarinaUtils.getStackTrace(t));
        }
        return buf.toString();
    }

    protected static String[] getAllBeanNames(ConfigurableListableBeanFactory inFactory) {
        String[] beanNames = inFactory.getBeanDefinitionNames();
        for (int i = 0; i < beanNames.length; ++i) {
            String beanName = beanNames[i];
            if (!beanName.startsWith("&")) continue;
            beanNames[i] = beanName.substring(1);
        }
        return beanNames;
    }

    public static List<ValueObject> getBeanValues(ConfigurableListableBeanFactory inFactory, boolean inInitializeBeans) {
        String[] inBeanNames = BeanFactoryUtil.getAllBeanNames(inFactory);
        ArrayList<ValueObject> vos = new ArrayList<ValueObject>(inBeanNames.length);
        for (int i = 0; i < inBeanNames.length; ++i) {
            String beanName = inBeanNames[i];
            try {
                ValueObject vo = new ValueObject("SPRING_BEAN");
                vo.setEntityPrimaryKey((Serializable)((Object)beanName));
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_NAME, (Object)beanName);
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_ORDER, (Object)i);
                vos.add(vo);
                BeanDefinition bf = ApplicationContextUtils.getBeanDefinition(inFactory, beanName);
                if (bf == null) continue;
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_DEFINED_CLASS, (Object)bf.getBeanClassName());
                String path = bf.getResourceDescription();
                String prefix = "class path resource ";
                if (path != null && path.startsWith(prefix)) {
                    path = path.substring(prefix.length());
                }
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_PATH, (Object)path);
                String scope = bf.getScope();
                if (org.apache.commons.lang.StringUtils.isEmpty((String)scope)) {
                    scope = bf.isSingleton() ? "singleton" : (bf.isPrototype() ? "prototype" : scope);
                }
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_SCOPE, (Object)scope);
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_DEBUG, (Object)bf.toString());
                if (inInitializeBeans) {
                    try {
                        Object bean = inFactory.getBean(beanName);
                        Class<?> beanClass = bean.getClass();
                        vo.setFieldValue(IDiagnosticBizMetafield.BEAN_CLASS, (Object)bean.getClass().getName());
                        vo.setFieldValue(IDiagnosticBizMetafield.BEAN_HASH, (Object) Integer.toHexString(bean.hashCode()));
                        Object[] interfaces = beanClass.getInterfaces();
                        if (interfaces != null && interfaces.length > 0) {
                            vo.setFieldValue(IDiagnosticBizMetafield.BEAN_INTERFACE, (Object) StringUtils.arrayToCommaDelimitedString((Object[])interfaces));
                        }
                    }
                    catch (Throwable t) {
                        vo.setFieldValue(IDiagnosticBizMetafield.BEAN_DEBUG, (Object)("FAILED LOAD " + t));
                        LOGGER.error((Object)(beanName + " failed to load : " + t));
                    }
                } else {
                    vo.setFieldValue(IDiagnosticBizMetafield.BEAN_HASH, null);
                    vo.setFieldValue(IDiagnosticBizMetafield.BEAN_CLASS, null);
                }
                if (!vo.isFieldPresent(IDiagnosticBizMetafield.BEAN_INTERFACE)) {
                    vo.setFieldValue(IDiagnosticBizMetafield.BEAN_INTERFACE, null);
                }
                BeanCreationTypeEnum creationType = BeanFactoryUtil.getBeanCreationType(beanName, bf, scope);
                vo.setFieldValue(IDiagnosticBizMetafield.BEAN_CREATION_TYPE, (Object)creationType);
                continue;
            }
            catch (Throwable t) {
                LOGGER.error((Object)(beanName + " BEAN skipped due to : " + t));
            }
        }
        return vos;
    }

    public static BeanCreationTypeEnum getBeanCreationType(String inBeanName, BeanDefinition inBf, String inScope) {
        BeanCreationTypeEnum creationType = BeanCreationTypeEnum.SYSTEM;
        try {
            Class<?> clazz;
            if (inBf instanceof ICustomCodeExtensionDrivenBeanDefinition) {
                creationType = ((ICustomCodeExtensionDrivenBeanDefinition)inBf).getBeanCreationType();
            } else if (org.apache.commons.lang.StringUtils.equals((String)"prototype", (String)inScope) && IEBean.class.isAssignableFrom(clazz = Class.forName(inBf.getBeanClassName()))) {
                creationType = BeanCreationTypeEnum.SYSTEM_OVERRIDDEN;
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)(inBeanName + " BEAN skipped due to : " + t));
        }
        return creationType;
    }

    public static boolean isSingletonBeanInstantiated(ListableBeanFactory inFactory, String inBeanName) {
        return inFactory instanceof SingletonBeanRegistry && ((SingletonBeanRegistry)inFactory).getSingleton(inBeanName) != null;
    }

    private BeanFactoryUtil() {
    }
}
