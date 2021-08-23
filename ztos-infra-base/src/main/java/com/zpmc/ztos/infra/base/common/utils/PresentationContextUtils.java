package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldDictionary;
import com.zpmc.ztos.infra.base.business.interfaces.IRequestContext;
import com.zpmc.ztos.infra.base.business.interfaces.IRequestContextManager;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class PresentationContextUtils {
    public static final Logger LOGGER = Logger.getLogger(PresentationContextUtils.class);
    public static final String REQUEST_UI_LISTENER_BEAN_ID = "requestContextUIRoundTripListener";

    public static IRequestContextManager getRequestContextManager() {
        return (IRequestContextManager) PortalApplicationContext.getBean("requestContextManager");
    }

    @Nullable
    public static IRequestContext getRequestContext() {
        IRequestContextManager requestContextManager = (IRequestContextManager)PortalApplicationContext.getBean("requestContextManager");
//        return requestContextManager.getContext();
        return null;
    }

    public static IMetafieldDictionary getMetafieldDictionary(@NotNull IMetafieldDictionary inDefaultDictionary) {
        IMetafieldDictionary dictionary = null;
        IRequestContext requestContext = PresentationContextUtils.getRequestContext();
//        if (requestContext != null && !(requestContext instanceof IPreauthenticated)) {
//            dictionary = requestContext.getIMetafieldDictionary();
//        }
        if (dictionary == null) {
            dictionary = inDefaultDictionary;
        }
        return dictionary;
    }

    @Nullable
    public static URL getUrlFromServletRequest(HttpServletRequest inRequest) {
        URL url = null;
        if (inRequest == null) {
            LOGGER.warn((Object)"Unable to get the server URL because the HttpServletRequest is null.");
            return url;
        }
        String baseUrl = inRequest.getScheme() + "://" + inRequest.getServerName() + ":" + inRequest.getServerPort() + inRequest.getContextPath();
        try {
            url = new URL(baseUrl);
            return url;
        }
        catch (MalformedURLException inException) {
            LOGGER.error((Object)("Unable to construct the server URL from the path constructed from HttpServletRequest (path=" + baseUrl + ")."));
            return url;
        }
    }

    public static void switchToChildContext(@NotNull UserContext inChildUserContext) {
        IRequestContext requestContext = PresentationContextUtils.getRequestContext();
//        if (!(requestContext instanceof IRichUiRequestContext)) {
//            throw BizFailure.create("Invalid method call. Request context needs to be instanceof IRichUiRequestContext.");
//        }
//        IRequestUiContextFactory requestContextFactory = ((IRichUiRequestContext)requestContext).getUiRequestContextFactory();
//        requestContextFactory.switchToChildContext(inChildUserContext);
    }

    public static void resetToOriginalContext() {
        IRequestContext requestContext = PresentationContextUtils.getRequestContext();
//        if (!(requestContext instanceof IRichUiRequestContext)) {
//            throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__RESET_TO_ORIGINAL_CONTEXT, null);
//        }
//        IRequestUiContextFactory requestContextFactory = ((IRichUiRequestContext)requestContext).getUiRequestContextFactory();
//        requestContextFactory.resetToOriginalContext();
    }

    private PresentationContextUtils() {
    }
}
