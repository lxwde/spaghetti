package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IExtensionType {

    public String getTypeId();

    public String getName();

    public String getDescription();

//    public CodeExtensionInstantiationMode getExtensionInstantiationMode();

//    public CodeExtensionTransactionMode getTransactionMode();

    public Class getRequiredInterface();

    public Class getAbstractBaseClass();

//    public ExtensionTriggerMappingEnum getTriggerMappingMode();

    public String getModule();

    public String getInitialVersion();

    public String getDebugSourceString();

    public List<IFeatureId> getFeatureIds();
}
