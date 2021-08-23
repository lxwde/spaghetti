package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import com.zpmc.ztos.infra.base.common.security.BaseUser;

import java.io.Serializable;
import java.util.Date;

public class UserAuthenticationProfileDO extends DatabaseEntity implements Serializable {
    private Long usrauthGkey;
    private Boolean usrauthLocked;
    private Long usrauthFailedLogins;
    private Date usrauthFirstFailedLogin;
    private Date usrauthLastFailedLogin;
    private Date usrauthLastSuccessfulLogin;
    private Date usrauthPasswordLastChanged;
    private String usrauthPreviousPasswords;
    private BaseUser usrauthUser;

    @Override
    public Serializable getPrimaryKey() {
        return this.getUsrauthGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUsrauthGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UserAuthenticationProfileDO)) {
            return false;
        }
        UserAuthenticationProfileDO that = (UserAuthenticationProfileDO)other;
        return ((Object)id).equals(that.getUsrauthGkey());
    }

    public int hashCode() {
        Long id = this.getUsrauthGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUsrauthGkey() {
        return this.usrauthGkey;
    }

    public void setUsrauthGkey(Long usrauthGkey) {
        this.usrauthGkey = usrauthGkey;
    }

    public Boolean getUsrauthLocked() {
        return this.usrauthLocked;
    }

    public void setUsrauthLocked(Boolean usrauthLocked) {
        this.usrauthLocked = usrauthLocked;
    }

    public Long getUsrauthFailedLogins() {
        return this.usrauthFailedLogins;
    }

    public void setUsrauthFailedLogins(Long usrauthFailedLogins) {
        this.usrauthFailedLogins = usrauthFailedLogins;
    }

    public Date getUsrauthFirstFailedLogin() {
        return this.usrauthFirstFailedLogin;
    }

    public void setUsrauthFirstFailedLogin(Date usrauthFirstFailedLogin) {
        this.usrauthFirstFailedLogin = usrauthFirstFailedLogin;
    }

    public Date getUsrauthLastFailedLogin() {
        return this.usrauthLastFailedLogin;
    }

    public void setUsrauthLastFailedLogin(Date usrauthLastFailedLogin) {
        this.usrauthLastFailedLogin = usrauthLastFailedLogin;
    }

    public Date getUsrauthLastSuccessfulLogin() {
        return this.usrauthLastSuccessfulLogin;
    }

    public void setUsrauthLastSuccessfulLogin(Date usrauthLastSuccessfulLogin) {
        this.usrauthLastSuccessfulLogin = usrauthLastSuccessfulLogin;
    }

    public Date getUsrauthPasswordLastChanged() {
        return this.usrauthPasswordLastChanged;
    }

    public void setUsrauthPasswordLastChanged(Date usrauthPasswordLastChanged) {
        this.usrauthPasswordLastChanged = usrauthPasswordLastChanged;
    }

    public String getUsrauthPreviousPasswords() {
        return this.usrauthPreviousPasswords;
    }

    public void setUsrauthPreviousPasswords(String usrauthPreviousPasswords) {
        this.usrauthPreviousPasswords = usrauthPreviousPasswords;
    }

    public BaseUser getUsrauthUser() {
        return this.usrauthUser;
    }

    public void setUsrauthUser(BaseUser usrauthUser) {
        this.usrauthUser = usrauthUser;
    }
}
