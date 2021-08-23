package com.zpmc.ztos.infra.base.business.interfaces;

public interface IExecutableSchemaExtension {
//    @NotNull
//    public SchemalExtensionPreExecutionStatus getPreExecutionStatus();
//
//    @Nullable
//    public Collection<IHibernateMappingExtension> getMappings();

    public IMessageCollector getPreExecutionMessageCollector();
}
