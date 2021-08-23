package com.zpmc.ztos.infra.base.common.beans;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.extension.PersistenceEventStatusEnum;
import com.zpmc.ztos.infra.base.business.enums.extension.PersistenceEventTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.CacheProviderLibraryEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.configs.CarinaDataSourceConnectionProvider;
import com.zpmc.ztos.infra.base.common.configs.CustomizableConfiguration;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.ExecutedDDLHolder;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DefaultSessionFactory extends LocalSessionFactoryBean implements ISecondLevelCacheConfigurationAware,
        IReloadableSessionFactory{
    ExecutedDDLHolder _ddlHolder = new ExecutedDDLHolder();
    private String _sessionScopedEntityInterceptorBeanId;
    private ListableBeanFactory _listableBeanFactory;
    private HbmFileSource _frameworkMappings;
    private Properties _hibernateProperties;
    private FuzzyBoolean _secondLevelCacheConfigured = FuzzyBoolean.UNSPECIFIED;
    private boolean _forceSchemaUpdate;
    private DatabaseHelper _dbHelper;
    private IUpgradeManager _upgradeManager;
    private Long _jvmExecutionSequence;
    private static int RELOAD_SEQUENCE_PER_JVM = 0;
    private Date _initialCreationTime = new Date();
    private static final String BEAN_FACTORY_LOCATION = "beanFactoryLocation";
    private static final String CONFIG_VALUE_COLUMN = "cnfig_value";
    private static final String BUILD_NUMBER_COLUMN = "modver_number";
    private static final String CONFIG_TABLE = "config_settings";
    private static final String MODULE_VERSION_TABLE = "frm_module_versions";
    public static final String BUILD_NUM_CONF_ID = "FRMCARINA011";
    private static final String EHCACHE_SERVER_CONFIG_PROP = "net.sf.ehcache.configurationResourceName";
    private static final Logger LOGGER = Logger.getLogger(DefaultSessionFactory.class);
    private static final String CONFIG_SETTINGS_QUERY = "select cnfig_value from config_settings where cfg_id='";
    private static final String MODULE_VERSION_QUERY = "select modver_number from frm_module_versions where modver_name ='";
    public static final String PERSISTENCE = "com.navis.framework.persistence";
    private final UserContext _systemUserContext = new ArgoUserContext(123, "123", ScopeCoordinates.GLOBAL_SCOPE);//UserContextUtils.getSystemUserContext();
    private Level _prevHibernateLogLevel;
    private Level _prevSpringLogLevel;

    public DefaultSessionFactory() {
        LOGGER.setLevel(Level.INFO);
    }

    @Override
    protected void postProcessMappings(Configuration inConfig) throws HibernateException {
        super.postProcessMappings(inConfig);
//        ISchemaExtensionLoader loader = ExtensionModelUtils.getSchemaExtensionLoader();
//        loader.applySchemaExtensionToConfigurationIfRequired(inConfig);
    }

    @Override
    protected void afterSessionFactoryCreation() throws Exception {
        super.afterSessionFactoryCreation();
    }

    public void setAutoSpringMappingLocation(BeanFactoryLocation inLocation) {
        this._listableBeanFactory = inLocation.getBeanFactory();
        this.setAutoSpringMappingResources();
    }

    private ListableBeanFactory getBeanFactory() {
        if (this._listableBeanFactory == null) {
            BeanFactoryLocation location = (BeanFactoryLocation)Roastery.getBean(BEAN_FACTORY_LOCATION);
            this._listableBeanFactory = location.getBeanFactory();
        }
        return this._listableBeanFactory;
    }

    private List<String> getAutoSpringMappingResources() {
        ListableBeanFactory beanFactory = this.getBeanFactory();
        String[] beanNames = BeanFactoryUtil.beanNamesIncludingAncestors(beanFactory, HbmFileSource.class);
        LOGGER.info((Object)"hibernateSessionFactory will be built with: ");
        ArrayList<String> fileNames = new ArrayList<String>();
        for (String beanName : beanNames) {
            HbmFileSource sources = (HbmFileSource)beanFactory.getBean(beanName);
            Object[] mappings = sources.getMappings();
            LOGGER.info((Object)("--" + beanName + ":" + org.springframework.util.StringUtils.arrayToCommaDelimitedString((Object[])mappings)));
 //           fileNames.addAll(Arrays.asList(mappings));
        }
        return fileNames;
    }

    private void setAutoSpringMappingResources() {
        List<String> fileNames = this.getAutoSpringMappingResources();
        super.setMappingResources(fileNames.toArray(new String[fileNames.size()]));
    }

    @Deprecated
    public void setFrameworkMappings(HbmFileSource inFrameworkMappings) {
        this._frameworkMappings = inFrameworkMappings;
    }

    @Override
    public void setMappingResources(String[] inMappingResources) {
        if (this._frameworkMappings != null) {
            String[] frameworkMappings = this._frameworkMappings.getMappings();
            String[] allMappings = new String[frameworkMappings.length + inMappingResources.length];
            System.arraycopy(frameworkMappings, 0, allMappings, 0, frameworkMappings.length);
            System.arraycopy(inMappingResources, 0, allMappings, frameworkMappings.length, inMappingResources.length);
            super.setMappingResources(allMappings);
        } else {
            super.setMappingResources(inMappingResources);
        }
    }

    @Override
    public void setEntityInterceptor(Interceptor inEntityInterceptor) {
        if (inEntityInterceptor instanceof BaseInterceptor) {
            this._sessionScopedEntityInterceptorBeanId = "auditInterceptor";
        } else {
            super.setEntityInterceptor(inEntityInterceptor);
        }
    }

    @Override
    public void setHibernateProperties(Properties inHibernateProperties) {
        String systemPredefinedProvider = System.getProperty("hibernate.bytecode.provider");
        if (systemPredefinedProvider == null) {
            String bytecodeProvider = inHibernateProperties.getProperty("hibernate.bytecode.provider");
            if (bytecodeProvider != null) {
                System.setProperty("hibernate.bytecode.provider", bytecodeProvider);
            } else {
                System.setProperty("hibernate.bytecode.provider", "javassist");
            }
        }
        LogUtils.forceLogAtInfo(LOGGER, "Hibernate will be started with BYTECODE_PROVIDER=" + System.getProperty("hibernate.bytecode.provider"));
        // this.checkContextConfig(inHibernateProperties);
        super.setHibernateProperties(inHibernateProperties);
        this._hibernateProperties = inHibernateProperties;
    }

    public void setUpgradeManager(IUpgradeManager inIUpgradeManager) {
        this._upgradeManager = inIUpgradeManager;
    }

    public void setDbHelper(DatabaseHelper inDbHelper) {
        this._dbHelper = inDbHelper;
    }

    public void afterPropertiesSet() throws Exception {
//        String dialect = this.findDialect();
//        if (dialect != null) {
//            this._hibernateProperties.setProperty("hibernate.dialect", dialect);
//        }
        if (!(this._hibernateProperties == null || CarinaUtils.isBlank(this._hibernateProperties.getProperty("hibernate.cache.provider_class")) && CarinaUtils.isBlank(this._hibernateProperties.getProperty("hibernate.cache.region.factory_class")))) {
            this.initializeSecondLevelCache();
        }
        super.afterPropertiesSet();
        this.assignJvmExecutionSequence();
        if (this._sessionScopedEntityInterceptorBeanId != null) {
            HibernateApi.getInstance().setSessionScopedInterceptorId(this._sessionScopedEntityInterceptorBeanId);
        }
        boolean isUpgraded = this.processSchemaCreationAndUpgrade();
        //this.applySchemaExtensions(isUpgraded);
        //this.postProcessAfterInitialization();
    }

    private void applySchemaExtensions(boolean inSystemSchemaUpgrade) throws Exception {
        ISchemaExtensionLoader loader = ExtensionModelUtils.getSchemaExtensionLoader();
        boolean applied = loader.processSchemaExtensions(UserContextUtils.getSystemUserContext(), this, inSystemSchemaUpgrade);
        if (!applied) {
            this.recordEvent(PersistenceEventTypeEnum.START_SYSTEM_ONLY, this._initialCreationTime, PersistenceEventStatusEnum.SUCCESS);
        } else {
            HiberCache.reload();
        }
    }

    @Override
    public SessionFactory rebuildSessionFactory() throws Exception {
        String oldFactory = this.getSessionFactory().toString();
        super.afterPropertiesSet();
        SessionFactory newFactory = this.getSessionFactory();
        LogUtils.forceLogAtInfo(LOGGER, "New session factory replaced " + (Object)newFactory + " old factory " + oldFactory);
        this.incrementFactoryReloadSequence();
        return newFactory;
    }

    public boolean isSingleton() {
        return false;
    }

    @Override
    public IExecutedDDL executeSchemaExtensionDDL() throws Exception {
        this.logger.info((Object)"Updating database schema for Hibernate SessionFactory");
        JdbcTemplate template = new JdbcTemplate(this.getDataSource());
        template.execute(new ConnectionCallback(){

            public Object doInConnection(Connection inConnection) throws SQLException, DataAccessException {
                Dialect dialect = Dialect.getDialect((Properties)DefaultSessionFactory.this.getConfiguration().getProperties());
                DatabaseMetadata metadata = new DatabaseMetadata(inConnection, dialect);
                String[] sql = DefaultSessionFactory.this.getConfiguration().generateSchemaUpdateScript(dialect, metadata);
                DefaultSessionFactory.this.executeSchemaScript(inConnection, sql);
                return sql;
            }
        });
        ExecutedDDLHolder executedDDLHolded = this._ddlHolder;
        this._ddlHolder = new ExecutedDDLHolder();
        return executedDDLHolded;
    }

    @Override
    public Date getInitialCreationTime() {
        return this._initialCreationTime;
    }

    protected boolean isSchemaChangeAllowed() {
        boolean isEnabled = false;
        if (null != this._upgradeManager) {
            if (this._upgradeManager.isEnabled()) {
                isEnabled = DatabaseUtils.isConfigSettingOn(this._dbHelper, FrameworkConfig.AUTO_UPGRADE);
            }
            this._upgradeManager.setEnabled(isEnabled);
        }
        return isEnabled;
    }

    private boolean processSchemaCreationAndUpgrade() {
        boolean schemaChangeEnabled = this.isSchemaChangeAllowed();
        boolean isUpgraded = false;
        if (schemaChangeEnabled) {
            this._prevHibernateLogLevel = LogUtils.pushLogLevel("org.hibernate", Level.DEBUG);
            this._prevSpringLogLevel = LogUtils.pushLogLevel("org.springframework.orm", Level.DEBUG);
            LogUtils.pushLogLevel(PERSISTENCE, Level.INFO);
            boolean createDb = this.createDatabaseSchemaIfEmpty();
            isUpgraded = this.processSystemDeliveredSchemaUpgrade(isUpgraded, createDb);
            this.restoreLogLevels();
        } else {
            LOGGER.warn((Object)"FRMCARINA012 setting is 'false'. This prevents the database from upgrading. If your database is not upgraded, you may experience problems. If you need to upgrade the database, please set it 'true'");
        }
        return isUpgraded;
    }

    private boolean processSystemDeliveredSchemaUpgrade(boolean inUpgraded, boolean inCreateDb) {
        Date startUpgrade = new Date();
        Integer buildNumber = this.findBuildNumber();
        this._upgradeManager.setDatabaseVersionNumber(buildNumber);
        this.initializeModuleVersionManagers();
        LOGGER.info((Object)("database buildNumber is: " + buildNumber));
        if (buildNumber == 0 && !inCreateDb) {
            LOGGER.error((Object)"afterPropertiesSet: after rebuild-tables. Don't run UpgradeActions");
            this._upgradeManager.setAfterRebuild(true);
        } else if (this._upgradeManager.isSchemaUpgradeRequired() || this._forceSchemaUpdate) {
            LOGGER.info((Object)"afterPropertiesSet: old database number requires update to: 9999");
            inUpgraded = true;
            try {
                this.updateDatabaseSchema();
                this.recordEvent(PersistenceEventTypeEnum.SYSTEM_SCHEMA_UPGRADE, startUpgrade, PersistenceEventStatusEnum.SUCCESS);
            }
            catch (Throwable t) {
                LOGGER.warn((Object)("afterPropertiesSet: database update failed with exception: " + t));
                this._upgradeManager.setEnabled(false);
                this.restoreLogLevels();
                this.recordEvent(PersistenceEventTypeEnum.SYSTEM_SCHEMA_UPGRADE, startUpgrade, PersistenceEventStatusEnum.FAILURE);
                throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__UPGRADE, t, "");
            }
            LOGGER.info((Object) DateUtil.formElapsedTimeMsg(startUpgrade.getTime(), "afterPropertiesSet: database schema upgrade time"));
        }
        return inUpgraded;
    }

    protected void recordEvent(PersistenceEventTypeEnum inType, Date inStartTime, PersistenceEventStatusEnum inStatus) {
        IPersistenceEventRecorder recorder = PersistenceUtils.getPersistenceEventRecorder();
        recorder.record(inType, this._ddlHolder, inStartTime, inStatus);
        this._ddlHolder.clear();
    }

    private boolean createDatabaseSchemaIfEmpty() {
        boolean createDb = false;
        if (!this.isDatabaseInitialized()) {
            Date startCreate = new Date();
            LOGGER.info((Object)"Database schema will be seeded from scratch.");
            try {
                this.createDatabaseSchema();
                this._upgradeManager.setAfterRebuild(false);
                this._upgradeManager.setInitial(true);
                createDb = true;
                this.recordEvent(PersistenceEventTypeEnum.SYSTEM_SCHEMA_CREATION, startCreate, PersistenceEventStatusEnum.SUCCESS);
            }
            catch (Throwable t) {
                LOGGER.fatal((Object)("afterPropertiesSet: database create failed with exception: " + t));
                this._upgradeManager.setEnabled(false);
                this.restoreLogLevels();
                this.recordEvent(PersistenceEventTypeEnum.SYSTEM_SCHEMA_UPGRADE, startCreate, PersistenceEventStatusEnum.FAILURE);
                throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__UPGRADE, t);
            }
        }
        return createDb;
    }

    private void restoreLogLevels() {
        LogUtils.setLogLevel("org.hibernate", this._prevHibernateLogLevel);
        LogUtils.setLogLevel("org.springframework.orm", this._prevSpringLogLevel);
        LogUtils.setLogLevel(PERSISTENCE, Level.WARN);
    }

    private void logSchemaUpgrade() {
        IUpgradeLogBizFacade upgradeLog = (IUpgradeLogBizFacade)PortalApplicationContext.getBean("upgradeLogBizFacade");
        IMessageCollector msgCollector = MessageCollectorFactory.createMessageCollector();
        upgradeLog.logSchemaUpgrade(this._systemUserContext, msgCollector, this._upgradeManager);
        if (msgCollector.hasError()) {
            LOGGER.error((Object)("Failed logging schema upgrade" + msgCollector));
        }
    }

    protected void checkContextConfig(@NotNull Properties inHibernateProperties) {
        ApplicationModuleSettings applModuleSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean("appModuleSettings");
        if (ServerUtils.getServerMode() == null && CacheProviderLibraryEnum.EHCACHE == applModuleSettings.getCacheProviderLibrary() && inHibernateProperties.containsKey(EHCACHE_SERVER_CONFIG_PROP)) {
            LOGGER.info((Object)"For a clustered container-based application, changing setting of ehcache config file for standalone clients with no lifecycle services.");
            inHibernateProperties.setProperty(EHCACHE_SERVER_CONFIG_PROP, "ehcache-config-standalone.xml");
        }
    }

    private void initializeModuleVersionManagers() {
        String[] versionBeanNames;
        for (String beanName : versionBeanNames = BeanFactoryUtil.beanNamesIncludingAncestors(Roastery.getBeanFactory(), IModuleVersionManager.class)) {
            IModuleVersionManager moduleVersionManager = (IModuleVersionManager)Roastery.getBeanFactory().getBean(beanName);
            Integer dbNumber = this.findModuleBuildNumber(moduleVersionManager.getModuleName());
            moduleVersionManager.setDbVersionNumber(dbNumber);
            this._upgradeManager.addModuleVersionManager(moduleVersionManager);
        }
    }

    private void initializeSecondLevelCache() {
        LOGGER.info((Object)"Initializing Hibernate second level cache.");
        ListableBeanFactory lbf = Roastery.getBeanFactory();
        Object[] beanNames = BeanFactoryUtil.beanNamesIncludingAncestors(lbf, ISecondLevelCacheProperties.class);
        if (beanNames == null || beanNames.length == 0) {
            return;
        }
        Properties cachedEntitiesProperties = new Properties();
        Properties cachedCollectionProperties = new Properties();
        for (Object string : beanNames) {
            SecondLevelCacheProperties mergedProperties = (SecondLevelCacheProperties)lbf.getBean((String)string);
            if (mergedProperties.getEntityProperties() != null) {
                cachedEntitiesProperties = PropertiesUtil.merge(cachedEntitiesProperties, mergedProperties.getEntityProperties(), mergedProperties.getOverrideProperty());
            }
            if (mergedProperties.getCollectionProperties() == null) continue;
            cachedCollectionProperties = PropertiesUtil.merge(cachedCollectionProperties, mergedProperties.getCollectionProperties(), mergedProperties.getOverrideProperty());
        }
        LOGGER.debug((Object)("Initializing Hibernate second level cache with the following Entity properties: " + cachedEntitiesProperties + " and the following Collection properties: " + cachedCollectionProperties + " from the following beans: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString((Object[])beanNames)));
        this.setEntityCacheStrategies(cachedEntitiesProperties);
        this.setCollectionCacheStrategies(cachedCollectionProperties);
        this._secondLevelCacheConfigured = FuzzyBoolean.TRUE;
    }

    private boolean isDatabaseInitialized() {
        return this._dbHelper.tableExists(CONFIG_TABLE);
    }

    protected Integer findBuildNumber() {
        Integer retVal = this._dbHelper.queryForObject("select cnfig_value from config_settings where cfg_id='FRMCARINA011'", Integer.class);
        if (null == retVal) {
            return 0;
        }
        return retVal;
    }

    protected Integer findModuleBuildNumber(String inModuleName) {
        try {
            Integer retVal = this._dbHelper.queryForObject(MODULE_VERSION_QUERY + inModuleName + "'", Integer.class);
            if (null == retVal) {
                return 0;
            }
            return retVal;
        }
        catch (Exception e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("Lookup of module version number for " + inModuleName + " failed. A row will be inserted for it next time. " + e.getMessage()));
            }
            return 0;
        }
    }

    @Nullable
    private String findDialect() {
        String retVal = System.getProperty("hibernate.spatial.dialect");
        if (StringUtils.isEmpty((String)retVal)) {
            retVal = this._dbHelper.getDbHibernateDialect();
        }
        return retVal;
    }

    @Override
    protected void executeSchemaStatement(Statement inStmt, String inSql) throws SQLException {
        Dialect dialect = Dialect.getDialect((Properties)this.getConfiguration().getProperties());
//        if (dialect instanceof PostgisDialect) {
//            inSql = inSql.replaceAll("bool\\s+default\\s+1", "bool default '1'");
//            inSql = inSql.replaceAll("bool\\s+default\\s+0", "bool default '0'");
//        }
        LOGGER.info((Object)("Executing DDL: " + inSql));
        try {
            this._ddlHolder.addDDL(inSql);
            inStmt.executeUpdate(inSql);
        }
        catch (SQLException ex) {
            LOGGER.fatal((Object)("Unsuccessful schema statement, please talk to a Navis representative. Your schema might be in an inconsistent state." + inSql), (Throwable)ex);
            this._ddlHolder.addException(inSql, ex);
        }
    }

    @Override
    protected void postProcessConfiguration(Configuration inConfig) throws HibernateException {
        super.postProcessConfiguration(inConfig);
        inConfig.setProperty("hibernate.connection.provider_class", CarinaDataSourceConnectionProvider.class.getName());
    }

    @Override
    protected Configuration newConfiguration() throws HibernateException {
        return new CustomizableConfiguration();
    }

    private void postProcessAfterInitialization() {
        String[] beanNames;
        ListableBeanFactory beanFactory = this.getBeanFactory();
        for (String beanName : beanNames = BeanFactoryUtil.beanNamesIncludingAncestors(beanFactory, ISessionFactoryPostInitializer.class)) {
            ISessionFactoryPostInitializer initializer = (ISessionFactoryPostInitializer)beanFactory.getBean(beanName);
            initializer.postInitialize(beanFactory);
        }
    }

    protected DatabaseHelper getDbHelper() {
        return this._dbHelper;
    }

    public void setForceSchemaUpdate(boolean inForceSchemaUpdate) {
        this._forceSchemaUpdate = inForceSchemaUpdate;
    }

    @Override
    public FuzzyBoolean isSecondLevelCacheEnabled() {
        return this._secondLevelCacheConfigured;
    }

    @Override
    public int getSessionFactoryReloadSequenceForJvm() {
        return RELOAD_SEQUENCE_PER_JVM;
    }

    private void incrementFactoryReloadSequence() {
        ++RELOAD_SEQUENCE_PER_JVM;
    }

    @Override
    public Long getJvmExecutionSequence() {
        return this._jvmExecutionSequence;
    }

    public void assignJvmExecutionSequence() {
        this._jvmExecutionSequence = System.currentTimeMillis();
    }

    @Override
    public Configuration createSystemLoadedConfiguration() {
        Configuration config = new Configuration();
        List<String> fileNames = this.getAutoSpringMappingResources();
        for (String file : fileNames) {
            try {
                String xml = ResourceUtils.loadResourceAsString(file);
                ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                config.addInputStream((InputStream)stream);
            }
            catch (Throwable e) {
                throw BizFailure.createProgrammingFailure(file + " file invalid hbm due to " + e);
            }
        }
        return config;
    }

}
