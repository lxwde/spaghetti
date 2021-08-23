package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IConfig;
import com.zpmc.ztos.infra.base.business.interfaces.IEConfigurationProvider;
import com.zpmc.ztos.infra.base.common.utils.PersistenceUtils;

public abstract class AbstractConfigurationProvider implements IEConfigurationProvider {

    @Override
    public String getConfigValue(IConfig inConfig) {
        PersistenceUtils.getSessionFactory();
//        ConfigProvider configProvider = (ConfigProvider)Roastery.getBean((String)"configProvider");
//        Object setting = configProvider.getEffectiveSetting(this.getProblemSolver().getUserContext(), inConfig);
//        return setting == null ? null : setting.toString();
          return "";
    }

    @Override
    public String getConfigValue(String inKey) {
        PersistenceUtils.getSessionFactory();
//        ConfigProvider configProvider = (ConfigProvider)Roastery.getBean((String)"configProvider");
//        IConfig setting = configProvider.findConfigById(inKey);
//        if (setting == null) {
//            return null;
//        }
//        Object settingValue = configProvider.getEffectiveSetting(this.getProblemSolver().getUserContext(), setting);
//        return settingValue == null ? null : settingValue.toString();
        return "";
    }

//    @Override
//    public String getProviderName() {
//        if (this.getProblemSolver() != null) {
//            return "Configuration Provider:" + this.getProblemSolver().getConfigProviderName();
//        }
//        return null;
//    }

}
