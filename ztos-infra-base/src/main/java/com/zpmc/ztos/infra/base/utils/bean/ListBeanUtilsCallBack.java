package com.zpmc.ztos.infra.base.utils.bean;

/**
 * @Author Java002
 * @Date 2021/6/29 8:41
 */
@FunctionalInterface
public interface ListBeanUtilsCallBack<S, T> {

    void callBack(S t, T s);
}
