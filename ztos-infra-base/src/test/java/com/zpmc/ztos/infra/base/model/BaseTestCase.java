package com.zpmc.ztos.infra.base.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.AuthenticationUtils;
import com.zpmc.ztos.infra.base.common.utils.TestUtils;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.ResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class BaseTestCase extends TestCase {

    private UserContext _testUserContext;
    protected final String _adminUid = "admin";
    protected final String _adminPassword = "admin";
    protected static final String ADMIN_UID = "admin";
    protected static final String ADMIN_PASSWORD = "admin";
    @Deprecated
    protected final Long _adminGkey = 2002L;
    protected ResourceLoader _loader = (ResourceLoader) new DefaultResourceLoader();
    private static final Logger LOGGER = Logger.getLogger(BaseTestCase.class);

    public BaseTestCase(String inS) {
        super(inS);
    }

    protected Properties getJBossContextProps() throws Exception {
        String url = "localhost:1099";
        Object user = null;
        Object password = null;
        Properties properties = null;
        properties = new Properties();
        properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        properties.put("java.naming.provider.url", url);
        properties.put("java.naming.factory.url.pkgs", "org.jboss.naming");
        return properties;
    }

    protected UserContext getAuthenticatedUserContext(String inUserid, String inPassword) {
        return AuthenticationUtils.getAuthenticatedUserContext(inUserid, inPassword, new ScopeCoordinates(new Serializable[0]));
    }

    protected UserContext getAuthenticatedUserContext(String inUserid, String inPassword, ScopeCoordinates inCoordinates) {
        return AuthenticationUtils.getAuthenticatedUserContext(inUserid, inPassword, inCoordinates);
    }

    public void assertTrueResponseSuccess(String inErrMsg, IMessageCollector inResponse) {
        if (inResponse.containsMessageLevel(MessageLevelEnum.SEVERE)) {
            LOGGER.error((Object)inErrMsg);
        }
        this.assertTrueResponseSuccess(inResponse);
    }

    @Deprecated
    public void assertTrueResponseSuccess(IMessageCollector inMessageCollector) {
        TestUtils.assertTrueResponseSuccess(inMessageCollector);
    }

    @Deprecated
    public void assertResponseFailed(IMessageCollector inResponse, IPropertyKey inExpectedErrorKey) {
        TestUtils.assertResponseFailed(inResponse, inExpectedErrorKey);
    }

    @Deprecated
    public void assertResponseFailed(String inPrintMsg, IMessageCollector inResponse, IPropertyKey inExpectedErrorKey) {
        TestUtils.assertResponseFailed(inPrintMsg, inResponse, inExpectedErrorKey);
    }

    @Deprecated
    public void assertResponseFailed(IMessageCollector inResponse, IPropertyKey inExpectedErrorKey, String inExpectedErrorMessage) {
        TestUtils.assertResponseFailed(inResponse, inExpectedErrorKey, inExpectedErrorMessage);
    }

    @Deprecated
    public void assertResponseFailed(String inPrintMsg, IMessageCollector inResponse, IPropertyKey inExpectedErrorKey, String inExpectedErrorMessage) {
        TestUtils.assertResponseFailed(inPrintMsg, inResponse, inExpectedErrorKey, inExpectedErrorMessage);
    }

    protected UserContext getTestUser() {
        if (this._testUserContext == null) {
            this._testUserContext = this.getAuthenticatedUserContext("admin", "admin");
        }
        return this._testUserContext;
    }

    protected void assertTimestamp(String inErrorMsg, int inYear, int inMon, int inDayOfMonth, int inHourOfDay, int inMin, int inSec, TimeZone inTimeZone, Date inActualDate) {
        BaseTestCase.assertNotNull((String)(inErrorMsg + ", date is null,"), (Object)inActualDate);
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTime(inActualDate);
        BaseTestCase.assertEquals((String)(inErrorMsg + ", year is wrong,"), (int)inYear, (int)c.get(1));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", month is wrong,"), (int)inMon, (int)c.get(2));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", day is wrong,"), (int)inDayOfMonth, (int)c.get(5));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", hour is wrong,"), (int)inHourOfDay, (int)c.get(11));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", minute is wrong,"), (int)inMin, (int)c.get(12));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", second is wrong,"), (int)inSec, (int)c.get(13));
    }

    public void assertTimesAreWithInASecond(Date inMatch, Date inExpectedTime) {
        BaseTestCase.assertTrue((String)(inMatch + " must be with in a second of " + inExpectedTime), (Math.abs(inMatch.getTime() - inExpectedTime.getTime()) <= 999L ? 1 : 0) != 0);
    }

    public void assertDate(String inErrorMsg, int inYear, int inMon, int inDayOfMonth, TimeZone inTimeZone, Date inActualDate) {
        Calendar c = Calendar.getInstance(inTimeZone);
        c.setTime(inActualDate);
        BaseTestCase.assertEquals((String)(inErrorMsg + ", year is wrong,"), (int)inYear, (int)c.get(1));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", month is wrong,"), (int)inMon, (int)c.get(2));
        BaseTestCase.assertEquals((String)(inErrorMsg + ", day is wrong,"), (int)inDayOfMonth, (int)c.get(5));
    }

    public static void assertMatches(@NotNull String inString, @NotNull String[] inMatches) {
        for (String match : inMatches) {
            BaseTestCase.assertTrue((String)(match + " was not in " + inString), (boolean)inString.contains(match));
        }
    }

    public static void assertExcludes(@NotNull String inString, @NotNull String[] inExclusions) {
        for (String match : inExclusions) {
            BaseTestCase.assertFalse((String)(match + " was in " + inString), (boolean)inString.contains(match));
        }
    }
}
