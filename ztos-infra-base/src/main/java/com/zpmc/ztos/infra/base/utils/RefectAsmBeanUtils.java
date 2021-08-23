//package com.zpmc.ztos.infra.base.utils;
//
//import com.esotericsoftware.reflectasm.MethodAccess;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 使用google的高速缓存ASM实现的beancopy
// * 兼容编译器自动生成的关于boolean类型的参数 get方法是is的！
// */
//public class RefectAsmBeanUtils {
//
//    //静态的，类型为HashMap的成员变量，用于存储缓存数据
//    private static Map<Class, MethodAccess> methodMap = new HashMap<Class, MethodAccess>();
//
//    private static Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();
//
//    private static Map<Class, List<String>> fieldMap = new HashMap<Class, List<String>>();
//
//    /**
//     * Description <br>
//     * 重写bean的copy方法
//     */
//    public static void copyProperties(Object target, Object source) {
//        MethodAccess descMethodAccess = methodMap.get(target.getClass());
//        if (descMethodAccess == null) {
//            descMethodAccess = cache(target);
//        }
//        MethodAccess orgiMethodAccess = methodMap.get(source.getClass());
//        if (orgiMethodAccess == null) {
//            orgiMethodAccess = cache(source);
//        }
//
//        List<String> fieldList = fieldMap.get(source.getClass());
//        for (String field : fieldList) {
//            String getKey = source.getClass().getName() + "." + "get" + field;
//            String setkey = target.getClass().getName() + "." + "set" + field;
//            Integer setIndex = methodIndexMap.get(setkey);
//            if (setIndex != null) {
//                int getIndex = methodIndexMap.get(getKey);
//                // 参数一需要反射的对象
//                // 参数二class.getDeclaredMethods 对应方法的index
//                // 参数对三象集合
//                descMethodAccess.invoke(target, setIndex.intValue(), orgiMethodAccess.invoke(source, getIndex));
//            }
//        }
//    }
//
//    /**
//     * Description <br>
//     * 单例模式
//     */
//    private static MethodAccess cache(Object orgi) {
//        synchronized (orgi.getClass()) {
//            MethodAccess methodAccess = MethodAccess.get(orgi.getClass());
//            Field[] fields = orgi.getClass().getDeclaredFields();
//            List<String> fieldList = new ArrayList<String>(fields.length);
//            for (Field field : fields) {
//                if (Modifier.isPrivate(field.getModifiers())
//                        && !Modifier.isStatic(field.getModifiers())) { // 是否是私有的，是否是静态的
//                    // 非公共私有变量
//                    String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
//                    int getIndex = 0; // 获取get方法的下标
//                    try {
//                        getIndex = methodAccess.getIndex("get" + fieldName);
//                    } catch (Exception e) {
//                        getIndex = methodAccess.getIndex("is" + (fieldName.replaceFirst("Is", "")));
//                    }
//                    int setIndex = 0; // 获取set方法的下标
//                    try {
//                        setIndex = methodAccess.getIndex("set" + fieldName);
//                    } catch (Exception e) {
//                        setIndex = methodAccess.getIndex("set" + fieldName.replaceFirst("Is", ""));
//                    }
//                    methodIndexMap.put(orgi.getClass().getName() + "." + "get" + fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
//                    methodIndexMap.put(orgi.getClass().getName() + "." + "set" + fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
//                    fieldList.add(fieldName); // 将属性名称放入集合里
//                }
//            }
//            fieldMap.put(orgi.getClass(), fieldList); // 将类名，属性名称注册到map中
//            methodMap.put(orgi.getClass(), methodAccess);
//            return methodAccess;
//        }
//    }
//
//}
