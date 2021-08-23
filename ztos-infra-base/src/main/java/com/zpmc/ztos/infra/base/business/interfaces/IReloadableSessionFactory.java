package com.zpmc.ztos.infra.base.business.interfaces;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

//import java.lang.module.Configuration;

public interface IReloadableSessionFactory {

    public SessionFactory rebuildSessionFactory() throws Exception;

    public IExecutedDDL executeSchemaExtensionDDL() throws Exception;

    public Date getInitialCreationTime();

    public Long getJvmExecutionSequence();

    public int getSessionFactoryReloadSequenceForJvm();

    public Configuration createSystemLoadedConfiguration();
}
