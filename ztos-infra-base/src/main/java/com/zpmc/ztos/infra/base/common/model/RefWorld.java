package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.framework.GeoScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IScopeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IScopeNodeEntity;
import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RefWorld implements IScopeNodeEntity {
    private static RefWorld _instance;
    private List _countries;

    public static RefWorld getInstance() {
        if (_instance == null) {
            _instance = new RefWorld();
        }
        return _instance;
    }

    @Override
    public IScopeEnum getScopeEnum() {
        return GeoScopeEnum.GLOBAL;
    }

    @Override
    @Nullable
    public IScopeNodeEntity getParent() {
        return null;
    }

    @Override
    public Collection getChildren() {
        if (this._countries == null) {
            this._countries = new ArrayList();
            this._countries.add(RefCountry.findCountry("AQ"));
            this._countries.add(RefCountry.findCountry("CA"));
            this._countries.add(RefCountry.findCountry("FR"));
            this._countries.add(RefCountry.findCountry("PE"));
            this._countries.add(RefCountry.findCountry("TR"));
            this._countries.add(RefCountry.findCountry("VU"));
            this._countries.add(RefCountry.findCountry("US"));
            this._countries.add(RefCountry.findCountry("ZW"));
        }
        return Collections.unmodifiableList(this._countries);
    }

    @Override
    @Nullable
    public Serializable getPrimaryKey() {
        return null;
    }

    @Override
    @Nullable
    public String getId() {
        return null;
    }

    @Override
    @Nullable
    public String getPathName() {
        return null;
    }
}
