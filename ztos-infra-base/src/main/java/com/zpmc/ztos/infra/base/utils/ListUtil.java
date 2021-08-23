package com.zpmc.ztos.infra.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Java002
 * @Date 2021/6/22 16:51
 */
public class ListUtil {


    /**
     * 将Object转换成列表
     * @param obj
     * @param cla list元素的类型
     * @param <T>
     * @return
     */
    public static <T> List<T> objToList(Object obj, Class<T> cla){
        List<T> list = new ArrayList<T>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
            return list;
        }
        return null;
    }


}
