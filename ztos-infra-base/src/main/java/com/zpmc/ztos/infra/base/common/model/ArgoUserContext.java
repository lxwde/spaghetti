package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.argo.KeySetOwnerEnum;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.security.ArgoUser;
import com.zpmc.ztos.infra.base.common.security.BizGkeysByRole;
import com.zpmc.ztos.infra.base.common.utils.UserContextUtils;
import org.apache.commons.lang.ObjectUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.TimeZone;

public class ArgoUserContext extends UserContext {
    private boolean _consoleShown;
    private Serializable _currentCarrierVisitGkey;
    private Serializable _currentGateGkey;
    private BizGkeysByRole _bizGkeysByRole;
    private Serializable _consoleGkey;
    private String _userLanguage;
    private String _userEmailAddress;
    private int _horizonDays = -1;
    private static final long serialVersionUID = -8664628636802029896L;

    public ArgoUserContext(Object inUserGkey, String inUserId, ScopeCoordinates inScopeCoordinates) {
        super(inUserGkey, inUserId, inScopeCoordinates);
    }

    public Serializable getCurrentCarrierVisitGkey() {
        return this._currentCarrierVisitGkey;
    }

    public void setCurrentCarrierVisitGkey(Serializable inCurrentCarrierVisitGkey) {
        this._currentCarrierVisitGkey = inCurrentCarrierVisitGkey;
    }

    public Serializable getCurrentGateGkey() {
        return this._currentGateGkey;
    }

    public void setCurrentGateGkey(Serializable inCurrentGateGkey) {
        this._currentGateGkey = inCurrentGateGkey;
    }

    public Serializable getConsoleGkey() {
        return this._consoleGkey;
    }

    public void setConsoleGkey(Serializable inConsoleGkey) {
        this._consoleGkey = inConsoleGkey;
    }

    public void setTimeZone(TimeZone inTimeZone) {
        super.setTimeZone(inTimeZone);
    }

    public TimeZone getBusinessTimeZone() {
        final Object[] tzHolder = new Object[1];
        PersistenceTemplate pt = new PersistenceTemplate(UserContextUtils.getSystemUserContext());
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                tzHolder[0] = ContextHelper.getThreadFacility().getTimeZone();
            }
        });
        if (tzHolder[0] == null) {
            return super.getTimeZone();
        }
        return (TimeZone)tzHolder[0];
    }

    public BizGkeysByRole getUserBizGkeysByRole() {
        if (this._bizGkeysByRole == null) {
            ArgoUser user = this.getPersistedUser();
            this._bizGkeysByRole = user != null ? user.getAffiliatedBizUnitGkeysByRole() : new BizGkeysByRole();
        }
        return this._bizGkeysByRole;
    }

    public int getUserHorizonDays() {
        int defaultHorizonDays = 30;
        if (this._horizonDays < 0) {
            Long days;
            ArgoUser user = this.getPersistedUser();
            this._horizonDays = user != null ? ((days = user.getArgouserHorizonDays()) == null ? 30 : days.intValue()) : 30;
        }
        return this._horizonDays;
    }

    public KeySetOwnerEnum getUserMyListChoice() {
        return (KeySetOwnerEnum)((Object)this.getUserMyListConfig()[0]);
    }

    public Long getUserMyListKey() {
        return (Long)this.getUserMyListConfig()[1];
    }

    private Object[] getUserMyListConfig() {
        ArgoUser user = this.getPersistedUser();
        if (user == null) {
            return new Object[]{KeySetOwnerEnum.USER, new Long(-1L)};
        }
        KeySetOwnerEnum choice = user.getArgouserMyListChoice();
        if (KeySetOwnerEnum.BIZGROUP.equals((Object)choice) && user.getArgouserBizGroup() != null) {
            return new Object[]{KeySetOwnerEnum.BIZGROUP, user.getArgouserBizGroup().getBizgrpGkey()};
        }
        if (KeySetOwnerEnum.COMPANY.equals((Object)choice) && user.getArgouserCompanyBizUnit() != null) {
            return new Object[]{KeySetOwnerEnum.COMPANY, user.getArgouserCompanyBizUnit().getBzuGkey()};
        }
        return new Object[]{KeySetOwnerEnum.USER, user.getBuserGkey()};
    }

    @Nullable
    private ArgoUser getPersistedUser() {
        ArgoUser user = null;
        Object userGkey = super.getUserKey();
        if (!ObjectUtils.equals((Object)userGkey, (Object)"-system-")) {
            user = ArgoUser.findByPrimaryKey((Serializable)userGkey);
        }
        return user;
    }

    public void setHorizonDays(int inHorizonDays) {
        this._horizonDays = inHorizonDays;
    }

    public String getUserLanguage() {
        ArgoUser user;
        if (this._userLanguage == null && (user = ArgoUser.findByPrimaryKey((Serializable)super.getUserKey())) != null) {
            this._userLanguage = user.getBuserLocaleLanguage();
        }
        return this._userLanguage;
    }

    public String getUserEmailAddress() {
        ArgoUser user;
        if (this._userEmailAddress == null && (user = ArgoUser.findByPrimaryKey((Serializable)super.getUserKey())) != null) {
            this._userEmailAddress = user.getBuserEMail();
        }
        return this._userEmailAddress;
    }

    public void setUserBizGkeysByRole(BizGkeysByRole inBizGkeysByRole) {
        this._bizGkeysByRole = inBizGkeysByRole;
    }

    public void setConsoleWindowShown(boolean inConsoleShown) {
        this._consoleShown = inConsoleShown;
    }

    public boolean isConsoleWindowShown() {
        return this._consoleShown;
    }

}
