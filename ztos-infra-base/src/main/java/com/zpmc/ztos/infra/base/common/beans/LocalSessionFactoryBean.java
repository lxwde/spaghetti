package com.zpmc.ztos.infra.base.common.beans;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.event.EventListeners;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.transaction.JTATransactionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.*;
import org.springframework.orm.hibernate4.SpringSessionContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class LocalSessionFactoryBean extends AbstractSessionFactoryBean implements BeanClassLoaderAware {
    private static final ThreadLocal<DataSource> configTimeDataSourceHolder = new ThreadLocal();
    private static final ThreadLocal<TransactionManager> configTimeTransactionManagerHolder = new ThreadLocal();
    private static final ThreadLocal<Object> configTimeRegionFactoryHolder = new ThreadLocal();
    private static final ThreadLocal<CacheProvider> configTimeCacheProviderHolder = new ThreadLocal();
    private static final ThreadLocal<LobHandler> configTimeLobHandlerHolder = new ThreadLocal();
    private Class<? extends Configuration> configurationClass = Configuration.class;
    private Resource[] configLocations;
    private String[] mappingResources;
    private Resource[] mappingLocations;
    private Resource[] cacheableMappingLocations;
    private Resource[] mappingJarLocations;
    private Resource[] mappingDirectoryLocations;
    private Properties hibernateProperties;
    private TransactionManager jtaTransactionManager;
    private Object cacheRegionFactory;
    private CacheProvider cacheProvider;
    private LobHandler lobHandler;
    private Interceptor entityInterceptor;
    private NamingStrategy namingStrategy;
    private TypeDefinitionBean[] typeDefinitions;
    private FilterDefinition[] filterDefinitions;
    private Properties entityCacheStrategies;
    private Properties collectionCacheStrategies;
    private Map<String, Object> eventListeners;
    private boolean schemaUpdate = false;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private Configuration configuration;

    public static DataSource getConfigTimeDataSource() {
        return configTimeDataSourceHolder.get();
    }

    public static TransactionManager getConfigTimeTransactionManager() {
        return configTimeTransactionManagerHolder.get();
    }

    static Object getConfigTimeRegionFactory() {
        return configTimeRegionFactoryHolder.get();
    }

    public static CacheProvider getConfigTimeCacheProvider() {
        return configTimeCacheProviderHolder.get();
    }

    public static LobHandler getConfigTimeLobHandler() {
        return configTimeLobHandlerHolder.get();
    }

    public void setConfigurationClass(Class<?> configurationClass) {
        if (configurationClass == null || !Configuration.class.isAssignableFrom(configurationClass)) {
            throw new IllegalArgumentException("'configurationClass' must be assignable to [org.hibernate.cfg.Configuration]");
        }
        this.configurationClass = (Class<? extends Configuration>) configurationClass;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocations = new Resource[]{configLocation};
    }

    public void setConfigLocations(Resource[] configLocations) {
        this.configLocations = configLocations;
    }

    public void setMappingResources(String[] mappingResources) {
        this.mappingResources = mappingResources;
    }

    public void setMappingLocations(Resource[] mappingLocations) {
        this.mappingLocations = mappingLocations;
    }

    public void setCacheableMappingLocations(Resource[] cacheableMappingLocations) {
        this.cacheableMappingLocations = cacheableMappingLocations;
    }

    public void setMappingJarLocations(Resource[] mappingJarLocations) {
        this.mappingJarLocations = mappingJarLocations;
    }

    public void setMappingDirectoryLocations(Resource[] mappingDirectoryLocations) {
        this.mappingDirectoryLocations = mappingDirectoryLocations;
    }

    public void setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    public Properties getHibernateProperties() {
        if (this.hibernateProperties == null) {
            this.hibernateProperties = new Properties();
        }
        return this.hibernateProperties;
    }

    public void setJtaTransactionManager(TransactionManager jtaTransactionManager) {
        this.jtaTransactionManager = jtaTransactionManager;
    }

    public void setCacheRegionFactory(Object cacheRegionFactory) {
        this.cacheRegionFactory = cacheRegionFactory;
    }

    @Deprecated
    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public void setLobHandler(LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }

    public void setEntityInterceptor(Interceptor entityInterceptor) {
        this.entityInterceptor = entityInterceptor;
    }

    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public void setTypeDefinitions(TypeDefinitionBean[] typeDefinitions) {
        this.typeDefinitions = typeDefinitions;
    }

    public void setFilterDefinitions(FilterDefinition[] filterDefinitions) {
        this.filterDefinitions = filterDefinitions;
    }

    public void setEntityCacheStrategies(Properties entityCacheStrategies) {
        this.entityCacheStrategies = entityCacheStrategies;
    }

    public void setCollectionCacheStrategies(Properties collectionCacheStrategies) {
        this.collectionCacheStrategies = collectionCacheStrategies;
    }

    public void setEventListeners(Map<String, Object> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public void setSchemaUpdate(boolean schemaUpdate) {
        this.schemaUpdate = schemaUpdate;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    protected SessionFactory buildSessionFactory() throws Exception {
        boolean overrideClassLoader;
        Configuration config = this.newConfiguration();
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            configTimeDataSourceHolder.set(dataSource);
        }
        if (this.jtaTransactionManager != null) {
            configTimeTransactionManagerHolder.set(this.jtaTransactionManager);
        }
        if (this.cacheRegionFactory != null) {
            configTimeRegionFactoryHolder.set(this.cacheRegionFactory);
        }
        if (this.cacheProvider != null) {
            configTimeCacheProviderHolder.set(this.cacheProvider);
        }
        if (this.lobHandler != null) {
            configTimeLobHandlerHolder.set(this.lobHandler);
        }
        Thread currentThread = Thread.currentThread();
        ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
        boolean bl = overrideClassLoader = this.beanClassLoader != null && !this.beanClassLoader.equals(threadContextClassLoader);
        if (overrideClassLoader) {
            currentThread.setContextClassLoader(this.beanClassLoader);
        }
        try {
            String[] strategyAndRegion;
            if (this.isExposeTransactionAwareSessionFactory()) {
                config.setProperty("hibernate.current_session_context_class", SpringSessionContext.class.getName());
            }
            if (this.jtaTransactionManager != null) {
                config.setProperty("hibernate.transaction.factory_class", JTATransactionFactory.class.getName());
                config.setProperty("hibernate.transaction.manager_lookup_class", LocalTransactionManagerLookup.class.getName());
            } else {
                config.setProperty("hibernate.transaction.factory_class", SpringTransactionFactory.class.getName());
            }
            if (this.entityInterceptor != null) {
                config.setInterceptor(this.entityInterceptor);
            }
//            if (this.namingStrategy != null) {
//                config.setNamingStrategy(this.namingStrategy);
//            }
            if (this.typeDefinitions != null) {
                Method sessionFactory = Configuration.class.getMethod("createMappings", new Class[0]);
                Method addTypeDef = sessionFactory.getReturnType().getMethod("addTypeDef", String.class, String.class, Properties.class);
                Object mappings = ReflectionUtils.invokeMethod((Method)sessionFactory, (Object)config);
                for (TypeDefinitionBean typeDef : this.typeDefinitions) {
                    ReflectionUtils.invokeMethod((Method)addTypeDef, (Object)mappings, (Object[])new Object[]{typeDef.getTypeName(), typeDef.getTypeClass(), typeDef.getParameters()});
                }
            }
            if (this.filterDefinitions != null) {
                for (FilterDefinition filterDefinition : this.filterDefinitions) {
                    config.addFilterDefinition(filterDefinition);
                }
            }
            if (this.configLocations != null) {
                for (Resource resource : this.configLocations) {
                    config.configure(resource.getURL());
                }
            }
            if (this.hibernateProperties != null) {
                config.addProperties(this.hibernateProperties);
            }
            if (dataSource != null) {
//                void var6_14;
//                Class<LocalDataSourceConnectionProvider> class_ = LocalDataSourceConnectionProvider.class;
//                if (this.isUseTransactionAwareDataSource() || dataSource instanceof TransactionAwareDataSourceProxy) {
//                    Class<TransactionAwareDataSourceConnectionProvider> class_2 = TransactionAwareDataSourceConnectionProvider.class;
//                } else if (config.getProperty("hibernate.transaction.manager_lookup_class") != null) {
//                    Class<LocalJtaDataSourceConnectionProvider> class_3 = LocalJtaDataSourceConnectionProvider.class;
//                }
//                config.setProperty("hibernate.connection.provider_class", var6_14.getName());
            }
            if (this.cacheRegionFactory != null) {
                config.setProperty("hibernate.cache.region.factory_class", "org.springframework.orm.hibernate3.LocalRegionFactoryProxy");
            } else if (this.cacheProvider != null) {
//                config.setProperty("hibernate.cache.provider_class", LocalCacheProviderProxy.class.getName());
            }
            if (this.mappingResources != null) {
                for (String string : this.mappingResources) {
                    ClassPathResource resource = new ClassPathResource(string.trim(), this.beanClassLoader);
                    config.addInputStream(resource.getInputStream());
                }
            }
            if (this.mappingLocations != null) {
                for (Resource resource : this.mappingLocations) {
                    config.addInputStream(resource.getInputStream());
                }
            }
            if (this.cacheableMappingLocations != null) {
                for (Resource resource : this.cacheableMappingLocations) {
                    config.addCacheableFile(resource.getFile());
                }
            }
            if (this.mappingJarLocations != null) {
                for (Resource resource : this.mappingJarLocations) {
                    config.addJar(resource.getFile());
                }
            }
            if (this.mappingDirectoryLocations != null) {
                for (Resource resource : this.mappingDirectoryLocations) {
                    File file = resource.getFile();
                    if (!file.isDirectory()) {
                        throw new IllegalArgumentException("Mapping directory location [" + (Object)resource + "] does not denote a directory");
                    }
                    config.addDirectory(file);
                }
            }
            this.postProcessMappings(config);
            config.buildMappings();
            if (this.entityCacheStrategies != null) {
                Enumeration<?> enumeration = this.entityCacheStrategies.propertyNames();
                while (enumeration.hasMoreElements()) {
                    String className = (String)enumeration.nextElement();
                    strategyAndRegion = StringUtils.commaDelimitedListToStringArray((String)this.entityCacheStrategies.getProperty(className));
                    if (strategyAndRegion.length > 1) {
                        Method method = Configuration.class.getMethod("setCacheConcurrencyStrategy", String.class, String.class, String.class);
                        ReflectionUtils.invokeMethod((Method)method, (Object)config, (Object[])new Object[]{className, strategyAndRegion[0], strategyAndRegion[1]});
                        continue;
                    }
                    if (strategyAndRegion.length <= 0) continue;
                    config.setCacheConcurrencyStrategy(className, strategyAndRegion[0]);
                }
            }
            if (this.collectionCacheStrategies != null) {
                Enumeration<?> enumeration = this.collectionCacheStrategies.propertyNames();
                while (enumeration.hasMoreElements()) {
                    String collRole = (String)enumeration.nextElement();
                    strategyAndRegion = StringUtils.commaDelimitedListToStringArray((String)this.collectionCacheStrategies.getProperty(collRole));
                    if (strategyAndRegion.length > 1) {
                        config.setCollectionCacheConcurrencyStrategy(collRole, strategyAndRegion[0], strategyAndRegion[1]);
                        continue;
                    }
                    if (strategyAndRegion.length <= 0) continue;
                    config.setCollectionCacheConcurrencyStrategy(collRole, strategyAndRegion[0]);
                }
            }
            if (this.eventListeners != null) {
                for (Map.Entry<String, Object> entry : this.eventListeners.entrySet()) {
                    String listenerType = entry.getKey();
                    Object object = entry.getValue();
                    if (object instanceof Collection) {
                        Collection listeners = (Collection)object;
                        EventListeners listenerRegistry = config.getEventListeners();
                        Object[] listenerArray = (Object[]) Array.newInstance(listenerRegistry.getListenerClassFor(listenerType), listeners.size());
                        listenerArray = listeners.toArray(listenerArray);
                        config.setListeners(listenerType, listenerArray);
                        continue;
                    }
                    config.setListener(listenerType, object);
                }
            }
            this.postProcessConfiguration(config);
            this.logger.info((Object)"Building new Hibernate SessionFactory");
            this.configuration = config;
            SessionFactory sessionFactory = this.newSessionFactory(config);
            return sessionFactory;
        }
        finally {
            if (dataSource != null) {
                configTimeDataSourceHolder.remove();
            }
            if (this.jtaTransactionManager != null) {
                configTimeTransactionManagerHolder.remove();
            }
            if (this.cacheRegionFactory != null) {
                configTimeRegionFactoryHolder.remove();
            }
            if (this.cacheProvider != null) {
                configTimeCacheProviderHolder.remove();
            }
            if (this.lobHandler != null) {
                configTimeLobHandlerHolder.remove();
            }
            if (overrideClassLoader) {
                currentThread.setContextClassLoader(threadContextClassLoader);
            }
        }
    }

    protected Configuration newConfiguration() throws HibernateException {
        return (Configuration) BeanUtils.instantiateClass(this.configurationClass);
    }

    protected void postProcessMappings(Configuration config) throws HibernateException {
    }

    protected void postProcessConfiguration(Configuration config) throws HibernateException {
    }

    protected SessionFactory newSessionFactory(Configuration config) throws HibernateException {
        return config.buildSessionFactory();
    }

    public final Configuration getConfiguration() {
        if (this.configuration == null) {
            throw new IllegalStateException("Configuration not initialized yet");
        }
        return this.configuration;
    }

    protected void afterSessionFactoryCreation() throws Exception {
        if (this.schemaUpdate) {
            this.updateDatabaseSchema();
        }
    }

    public void destroy() throws HibernateException {
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            configTimeDataSourceHolder.set(dataSource);
        }
        try {
            super.destroy();
        }
        finally {
            if (dataSource != null) {
                configTimeDataSourceHolder.remove();
            }
        }
    }

    public void updateDatabaseSchema() throws DataAccessException {
        this.logger.info((Object)"Updating database schema for Hibernate SessionFactory");
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            configTimeDataSourceHolder.set(dataSource);
        }
        try {
            HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());
            hibernateTemplate.setFlushMode(0);
            hibernateTemplate.execute(new HibernateCallback<Object>(){

                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Connection con = session.connection();
                    Dialect dialect = Dialect.getDialect((Properties) LocalSessionFactoryBean.this.getConfiguration().getProperties());
                    DatabaseMetadata metadata = new DatabaseMetadata(con, dialect);
                    String[] sql = LocalSessionFactoryBean.this.getConfiguration().generateSchemaUpdateScript(dialect, metadata);
                    LocalSessionFactoryBean.this.executeSchemaScript(con, sql);
                    return null;
                }
            });
        }
        finally {
            if (dataSource != null) {
                configTimeDataSourceHolder.remove();
            }
        }
    }

    public void validateDatabaseSchema() throws DataAccessException {
        this.logger.info((Object)"Validating database schema for Hibernate SessionFactory");
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            configTimeDataSourceHolder.set(dataSource);
        }
        try {
            HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());
            hibernateTemplate.setFlushMode(0);
            hibernateTemplate.execute(new HibernateCallback<Object>(){

                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Connection con = session.connection();
                    Dialect dialect = Dialect.getDialect((Properties) LocalSessionFactoryBean.this.getConfiguration().getProperties());
                    DatabaseMetadata metadata = new DatabaseMetadata(con, dialect, false);
                    LocalSessionFactoryBean.this.getConfiguration().validateSchema(dialect, metadata);
                    return null;
                }
            });
        }
        finally {
            if (dataSource != null) {
                configTimeDataSourceHolder.remove();
            }
        }
    }

    public void dropDatabaseSchema() throws DataAccessException {
        this.logger.info((Object)"Dropping database schema for Hibernate SessionFactory");
        HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());
        hibernateTemplate.execute(new HibernateCallback<Object>(){

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                Dialect dialect = Dialect.getDialect((Properties) LocalSessionFactoryBean.this.getConfiguration().getProperties());
                String[] sql = LocalSessionFactoryBean.this.getConfiguration().generateDropSchemaScript(dialect);
                LocalSessionFactoryBean.this.executeSchemaScript(con, sql);
                return null;
            }
        });
    }

    public void createDatabaseSchema() throws DataAccessException {
        this.logger.info((Object)"Creating database schema for Hibernate SessionFactory");
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            configTimeDataSourceHolder.set(dataSource);
        }
        try {
            HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());
            hibernateTemplate.execute(new HibernateCallback<Object>(){

                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Connection con = session.connection();
                    Dialect dialect = Dialect.getDialect((Properties) LocalSessionFactoryBean.this.getConfiguration().getProperties());
                    String[] sql = LocalSessionFactoryBean.this.getConfiguration().generateSchemaCreationScript(dialect);
                    LocalSessionFactoryBean.this.executeSchemaScript(con, sql);
                    return null;
                }
            });
        }
        finally {
            if (dataSource != null) {
                configTimeDataSourceHolder.remove();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void executeSchemaScript(Connection con, String[] sql) throws SQLException {
        if (sql != null && sql.length > 0) {
            boolean oldAutoCommit = con.getAutoCommit();
            if (!oldAutoCommit) {
                con.setAutoCommit(true);
            }
            try {
                Statement stmt = con.createStatement();
                try {
                    for (String sqlStmt : sql) {
                        this.executeSchemaStatement(stmt, sqlStmt);
                    }
                }
                finally {
                    JdbcUtils.closeStatement((Statement)stmt);
                }
            }
            finally {
                if (!oldAutoCommit) {
                    con.setAutoCommit(false);
                }
            }
        }
    }

    protected void executeSchemaStatement(Statement stmt, String sql) throws SQLException {
        block3: {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Executing schema statement: " + sql));
            }
            try {
                stmt.executeUpdate(sql);
            }
            catch (SQLException ex) {
                if (!this.logger.isWarnEnabled()) break block3;
                this.logger.warn((Object)("Unsuccessful schema statement: " + sql), (Throwable)ex);
            }
        }
    }


}
