package com.zpmc.ztos.infra.base.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDataQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IEntityId;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.impls.DomainQueryImpl;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.SecurityUtils;
import org.apache.commons.lang.ObjectUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

//import static com.sun.jmx.defaults.ServiceName.DOMAIN;

public class QueryUtils {

    public static final String DEV_SQL_COMMENT_PREFIX = " Dev- ";
    public static final String DEV_SQL_COMMENT_SUFFIX = " -Dev";
    public static final String SQL_COMMENT_PREFIX = "/*";
    public static final String SQL_COMMENT_SUFFIX = "*/";
    public static final String N4_SQL_COMMENT_PREFIX = "/* sys- ";
    public static final String N4_SQL_COMMENT_SUFFIX = " -sys */";
    public static final String SQL_COMMENT_SYSTEM_PROP = "com.navis.sql.comments";

    public static final String DOMAIN = "com.navis";
    public static final String FRAMEWORK = "framework";
    private static final String CALLED_BY_STRING = ", Called by ";


    protected QueryUtils() {
    }

    public static IDomainQuery createDomainQuery(String string) {
        return new DomainQueryImpl(string);
    }

    public static IDomainQuery createDomainQuery(IEntityId entityId) {
        return new DomainQueryImpl(entityId.getEntityName());
    }

    public static boolean ensureAllFieldsAreInQuery(MetafieldIdList metafieldIdList, IDataQuery dataQuery) {
        boolean bl = false;
        MetafieldIdList metafieldIdList2 = dataQuery.getMetafieldIds();
        for (IMetafieldId metafieldId : metafieldIdList) {
            boolean bl2 = false;
            for (int i = 0; i < metafieldIdList2.getSize(); ++i) {
                IMetafieldId metafieldId2 = metafieldIdList2.get(i);
                if (!ObjectUtils.equals((Object)metafieldId, (Object)metafieldId2)) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            MetafieldIdList metafieldIdList3 = new MetafieldIdList(metafieldId);
            dataQuery.addMetafieldIds(metafieldIdList3);
            bl = true;
        }
        return bl;
    }

    @NotNull
    public static String getSessionInfo(@NotNull UserContext inUserContext) {
        String sessionInfoComment = "*unknown user context*";
        if (inUserContext != null) {
            String userId = inUserContext.getUserId();
            String sessionType = SecurityUtils.getSessionType(inUserContext);
            sessionInfoComment = "User:" + userId + ", Session : " + sessionType;
            InetAddress addr = null;
            try {
                addr = InetAddress.getLocalHost();
                if (addr != null) {
                    sessionInfoComment = sessionInfoComment + ", Host : " + addr.getCanonicalHostName();
                }
            }
            catch (UnknownHostException unknownHostException) {
                // empty catch block
            }
        }
        return sessionInfoComment;
    }

    @NotNull
    public static String buildDefaultComment(@NotNull UserContext inUserContext) {
        String comment = QueryUtils.getSessionInfo(inUserContext);
        String callerClass = null;
        int codeLine = 0;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements != null) {
            int lastNavisClassInStackTrace = 0;
            for (int i = 1; i < elements.length; ++i) {
                callerClass = elements[i].getClassName();
                if (callerClass.indexOf(DOMAIN) != 0) continue;
                lastNavisClassInStackTrace = i;
                if (callerClass.contains(FRAMEWORK)) continue;
                codeLine = elements[i].getLineNumber();
                break;
            }
            callerClass = elements[lastNavisClassInStackTrace].getClassName();
            codeLine = elements[lastNavisClassInStackTrace].getLineNumber();
        }
        if (callerClass != null) {
            comment = comment + CALLED_BY_STRING + callerClass + ":" + codeLine;
        }
        return N4_SQL_COMMENT_PREFIX + comment + N4_SQL_COMMENT_SUFFIX;
    }

    public static boolean isSqlCommentsOn() {
        String string = System.getProperty(SQL_COMMENT_SYSTEM_PROP);
        string = string == null ? "false" : string;
        return "true".equals(string);
    }

    public static String insertDefaultComment(@NotNull String string, @NotNull UserContext userContext) {
        int n;
        int n2 = string.indexOf(N4_SQL_COMMENT_PREFIX);
        if (n2 >= 0) {
            return string;
        }
        String string2 = QueryUtils.buildDefaultComment(userContext);
        n2 = string.indexOf(DEV_SQL_COMMENT_PREFIX);
        if (n2 >= 0 && (n = string.indexOf(DEV_SQL_COMMENT_SUFFIX)) > 0) {
            String string3 = string.substring(n2, n + DEV_SQL_COMMENT_SUFFIX.length());
            String string4 = string.substring(n + DEV_SQL_COMMENT_PREFIX.length() + 2);
            StringBuilder stringBuilder = new StringBuilder(string2);
            stringBuilder = stringBuilder.insert(string2.length() - N4_SQL_COMMENT_SUFFIX.length(), string3);
            return stringBuilder + string4;
        }
        return string2 + string;
    }
}
