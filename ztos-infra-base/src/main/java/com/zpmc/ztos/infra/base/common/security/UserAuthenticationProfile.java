package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.dataobject.UserAuthenticationProfileDO;
import com.zpmc.ztos.infra.base.common.model.FrameworkConfig;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class UserAuthenticationProfile extends UserAuthenticationProfileDO {
    private static final String DELIMITER = ",";

    public UserAuthenticationProfile() {
        this.setUsrauthLocked(Boolean.FALSE);
        this.setUsrauthFailedLogins(0L);
    }

    public void resetFailureLocks() {
        this.setUsrauthLocked(Boolean.FALSE);
        this.setUsrauthFailedLogins(0L);
        this.setUsrauthFirstFailedLogin(null);
        this.setUsrauthLastFailedLogin(null);
    }

    public final UserContext getScopedSecuritySystemContext() {
        ScopeCoordinates broadestAllowedScope = this.getUsrauthUser().getBroadestAllowedScope();
        UserContext systemUserContext = UserContextUtils.getSystemUserContext(broadestAllowedScope);
        return systemUserContext;
    }

    public boolean isExpired() {
        boolean expired = false;
        Date lastChanged = this.getUsrauthPasswordLastChanged();
        Long userPasswordExpiryDays = this.getUsrauthUser().getBuserPasswordExpiryOverrideDays();
        if (FrameworkConfig.AUTHENTICATION_NEW_USER_PASSWORD_CHANGE_PROMPT.isOn(this.getScopedSecuritySystemContext()) && this.getUsrauthLastSuccessfulLogin() == null) {
            expired = userPasswordExpiryDays == null || userPasswordExpiryDays != 0L;
        } else if (lastChanged != null) {
            long expiryDays;
            long l = expiryDays = userPasswordExpiryDays != null ? userPasswordExpiryDays.longValue() : FrameworkConfig.PASSWORD_EXPIRY_DAYS.getValue(this.getScopedSecuritySystemContext());
            if (expiryDays > 0L) {
                double differenceInDays = (double)(TimeUtils.getCurrentTimeMillis() - lastChanged.getTime()) / 8.64E7;
                expired = differenceInDays > (double)expiryDays;
            }
        }
        return expired;
    }

    public boolean isAccountExpired() {
        boolean expired = false;
        Date userAccountExpiryDate = this.getUsrauthUser().getBuserExpiryDateTime();
        if (userAccountExpiryDate != null) {
            String tzStr = this.getUsrauthUser().getBuserTimeZone();
            TimeZone tz = tzStr == null ? this.getScopedSecuritySystemContext().getTimeZone() : TimeZone.getTimeZone(this.getUsrauthUser().getBuserTimeZone());
            Date now = DateUtil.getTodaysDate(tz);
            expired = userAccountExpiryDate.before(now);
        }
        return expired;
    }

    public long incrementFailureCount() {
        long currentCount = this.getUsrauthFailedLogins();
        Date timeNow = TimeUtils.getCurrentTime();
        if (currentCount == 0L) {
            this.setUsrauthFirstFailedLogin(timeNow);
        }
        this.setUsrauthLastFailedLogin(timeNow);
        this.setUsrauthFailedLogins(currentCount + 1L);
        return this.getUsrauthFailedLogins();
    }

    public boolean isLocked() {
        return this.getUsrauthLocked();
    }

    public void recordLastPassword(String inRecentOldPassword) {
        this.setUsrauthPasswordLastChanged(TimeUtils.getCurrentTime());
        if (StringUtils.isEmpty((String)inRecentOldPassword)) {
            return;
        }
        long maxTokens = FrameworkConfig.PASSWORD_NO_RECYLCE_COUNT.getMaxValue();
        String previousPassWords = this.getUsrauthPreviousPasswords();
        String passwordBank = StringUtils.isNotEmpty((String)previousPassWords) ? inRecentOldPassword + DELIMITER + previousPassWords : inRecentOldPassword;
        StringTokenizer tokenizer = new StringTokenizer(passwordBank, DELIMITER);
        if ((long)tokenizer.countTokens() > maxTokens) {
            int truncateIndex = passwordBank.lastIndexOf(DELIMITER);
            passwordBank = passwordBank.substring(0, truncateIndex);
        }
        this.setUsrauthPreviousPasswords(passwordBank);
    }

    public boolean isPasswordRecycled(long inRecycleLimit, String inNewPassword) {
        int occurrence;
        boolean isRecyledFlag = false;
        String oldPasswordBank = this.getUsrauthPreviousPasswords();
        if (StringUtils.isNotBlank((String)oldPasswordBank) && (occurrence = oldPasswordBank.indexOf(inNewPassword)) > -1) {
            StringTokenizer tokenizer = new StringTokenizer(StringUtils.substring((String)oldPasswordBank, (int)0, (int)(occurrence + inNewPassword.length())), DELIMITER);
            isRecyledFlag = (long)tokenizer.countTokens() <= inRecycleLimit;
        }
        return isRecyledFlag;
    }
}
