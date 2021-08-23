package com.zpmc.ztos.infra.base.business.interfaces;

import org.springframework.beans.factory.ListableBeanFactory;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.net.URL;

public interface IRequestContext extends ITranslationContext {

    public static final String BEAN_ID = "RequestContext";

//    public LovFactory getLovFactory();
//
//    public FormProvider getFormProvider();

    @Nullable
    public Object getSessionAttribute(String var1);

    @Nullable
    public String getParameter(String var1);

//    @Deprecated
//    @Nullable
//    public UserCache getUserCache();
//
//    @Nullable
//    public UserInfoBase getUserInfoBase();

    public void setAttribute(String var1, Object var2);

    public Object getAttribute(String var1);

    public ListableBeanFactory getListableBeanFactory();

    public <T> T getBean(String var1);

    @Nullable
    public URL getApplicationUrl();

//    @Nullable
//    public HttpServletRequest getHttpServletRequest();
}
