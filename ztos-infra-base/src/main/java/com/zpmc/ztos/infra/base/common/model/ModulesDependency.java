package com.zpmc.ztos.infra.base.common.model;

import java.util.List;

public class ModulesDependency {
    public static final String BEAN_ID = "moduleDependency";
    List _modules;

    public void setModules(List<String> inModules) {
        this._modules = inModules;
    }

    public List<String> getModules() {
        return this._modules;
    }
}
