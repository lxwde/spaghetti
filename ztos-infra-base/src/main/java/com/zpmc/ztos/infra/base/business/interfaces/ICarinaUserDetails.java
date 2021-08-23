package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;

import java.io.Serializable;
import java.util.Locale;

public interface ICarinaUserDetails
//        extends UserDetails
{
    public Serializable getUserGkey();

    public Locale getLocale();

    public long getSleepDelaySecs();

    public ScopeCoordinates getBroadestAllowedScope();
}
