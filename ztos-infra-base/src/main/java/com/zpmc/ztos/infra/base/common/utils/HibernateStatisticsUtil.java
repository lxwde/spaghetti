package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HibernateStatisticsUtil {
    private static final Logger LOGGER = Logger.getLogger(HibernateStatisticsUtil.class);
    public static final String SESSION_COUNT_THRESHOLD = "com.navis.framework.hibernate.statistics.session_count_limit";
    public static final String SESSION_COUNT_THRESHOLD_DEFAULT_STR = "50000";
    public static final int SESSION_COUNT_THRESHOLD_DEFAULT = Integer.valueOf("50000");

    private HibernateStatisticsUtil() {
    }

    public static void enableStatistics(boolean inEnable) {
        HibernateStatisticsUtil.getSessionFactory().getStatistics().setStatisticsEnabled(inEnable);
    }

    public static void clearStatistics() {
        HibernateStatisticsUtil.getSessionFactory().getStatistics().clear();
    }

    private static SessionFactory getSessionFactory() {
        return PersistenceUtils.getSessionFactory();
    }

    public static Statistics getStatistics() {
        return HibernateStatisticsUtil.getSessionFactory().getStatistics();
    }

    public static void logComprehensiveStatistics() {
        HibernateStatisticsUtil.logStatisticsSummary();
        HibernateStatisticsUtil.logEntityStatistics();
        HibernateStatisticsUtil.logCollectionStatistics();
    }

    public static void logStatisticsSummary() {
        Statistics stats = HibernateStatisticsUtil.getStatistics();
        Logger statlogger = Logger.getLogger((String)Statistics.class.getPackage().getName());
        Level oldLevel = statlogger.getLevel();
        statlogger.setLevel(Level.INFO);
        stats.logSummary();
        statlogger.setLevel(oldLevel);
    }

    public static String getEntityStatisticsLog() {
        Statistics stats = HibernateStatisticsUtil.getStatistics();
        StringBuilder statsBuf = new StringBuilder("Hibernate Statistics");
        try {
            statsBuf.append(" -- capture enabled = ").append(stats.isStatisticsEnabled());
            statsBuf.append("\nTaken from ").append(new Date(stats.getStartTime()));
            statsBuf.append(" to  ").append(new Date());
            statsBuf.append("\n\nTOTALS \n").append("[ loads=").append(stats.getEntityLoadCount()).append(",fetches=").append(stats.getEntityFetchCount()).append(",inserts=").append(stats.getEntityInsertCount()).append(",updates=").append(stats.getEntityUpdateCount()).append(",deletes=").append(stats.getEntityDeleteCount()).append("]\n").append("[ query execution=").append(stats.getQueryExecutionCount()).append(", cache put=").append(stats.getSecondLevelCachePutCount()).append(",cache hit=").append(stats.getSecondLevelCacheHitCount()).append(",cache miss=").append(stats.getSecondLevelCacheMissCount()).append(" ]\n").append("[ trans cnt=").append(stats.getTransactionCount()).append(",successful tx count=").append(stats.getSuccessfulTransactionCount()).append(",sess open=").append(stats.getSessionOpenCount()).append(",sess close=").append(stats.getSessionCloseCount()).append(",query exe=").append(stats.getQueryExecutionCount()).append(",stmts prepared=").append(stats.getPrepareStatementCount()).append(",stmts closed=").append(stats.getCloseStatementCount()).append(",flush count=").append(stats.getFlushCount()).append(" ]\n").append("[ collections loaded=").append(stats.getCollectionLoadCount()).append(",colls updated=").append(stats.getCollectionUpdateCount()).append(",colls removed=").append(stats.getCollectionRemoveCount()).append(",colls recreated=").append(stats.getCollectionRecreateCount()).append(",colls fetched=").append(stats.getCollectionFetchCount()).append(" ]\n\n");
            String[] entityNames = stats.getEntityNames();
            ArrayList<ClassAwareEntityStat> entities = new ArrayList<ClassAwareEntityStat>();
            for (String entityName : entityNames) {
                EntityStatistics entityStatistics = stats.getEntityStatistics(entityName);
                SecondLevelCacheStatistics cacheStatistics = stats.getSecondLevelCacheStatistics(entityName);
                if (entityStatistics.getLoadCount() <= 0L && entityStatistics.getFetchCount() <= 0L && (cacheStatistics == null || cacheStatistics.getHitCount() <= 0L && cacheStatistics.getMissCount() <= 0L && cacheStatistics.getPutCount() <= 0L)) continue;
                entities.add(new ClassAwareEntityStat(entityName, entityStatistics));
            }
            Collections.sort(entities, new Comparator<ClassAwareEntityStat>(){

                @Override
                public int compare(ClassAwareEntityStat inO1, ClassAwareEntityStat inO2) {
                    return new Long(inO2.getStats().getFetchCount()).compareTo(inO1.getStats().getFetchCount());
                }
            });
            if (!entities.isEmpty()) {
                statsBuf.append("\n\nEntity Acccess Sorted by Load Counts\n");
                for (ClassAwareEntityStat entity : entities) {
                    SecondLevelCacheStatistics cacheStatistics;
                    EntityStatistics estats = entity.getStats();
                    statsBuf.append(entity.getEntityClassName()).append("  [ fetches=").append(estats.getFetchCount()).append(" loads=").append(estats.getLoadCount());
                    if (estats.getUpdateCount() > 0L) {
                        statsBuf.append(",updates=").append(estats.getUpdateCount());
                    }
                    if (estats.getInsertCount() > 0L) {
                        statsBuf.append(",inserts=").append(estats.getInsertCount());
                    }
                    if (estats.getDeleteCount() > 0L) {
                        statsBuf.append(",deletes=").append(estats.getDeleteCount());
                    }
                    if (estats.getOptimisticFailureCount() > 0L) {
                        statsBuf.append(",optimisticLockFailures=").append(estats.getOptimisticFailureCount());
                    }
                    if ((cacheStatistics = stats.getSecondLevelCacheStatistics(entity.getEntityClassName())) != null) {
                        statsBuf.append("    [2nd Level Cache: elements=").append(cacheStatistics.getElementCountInMemory());
                        if (cacheStatistics.getHitCount() > 0L) {
                            statsBuf.append(", hits=").append(cacheStatistics.getHitCount());
                        }
                        if (cacheStatistics.getPutCount() > 0L) {
                            statsBuf.append(", puts=").append(cacheStatistics.getPutCount());
                        }
                        if (cacheStatistics.getMissCount() > 0L) {
                            statsBuf.append(", misses=").append(cacheStatistics.getMissCount());
                        }
                    }
                    statsBuf.append("] \n");
                }
            }
            statsBuf.append(HibernateStatisticsUtil.getQueryStatSummary());
        }
        catch (Exception e) {
            statsBuf.append("\n\nError encountered with : ").append(e);
        }
        return statsBuf.toString();
    }

    public static String getQueryStatSummary() {
        Statistics stats = HibernateStatisticsUtil.getStatistics();
        ArrayList<SortableQueryStat> queries = new ArrayList<SortableQueryStat>();
        StringBuilder statsBuf = new StringBuilder();
        String[] queryStrs = stats.getQueries();
        if (queryStrs.length > 0) {
            statsBuf.append("\n\nQuery Execution Stats Ordered by Max Times\n\n");
        }
        for (String queryStr : queryStrs) {
            QueryStatistics queryStats = stats.getQueryStatistics(queryStr);
            StringBuilder queryBuf = new StringBuilder();
            queryBuf.append("[executionCount=").append(queryStats.getExecutionCount()).append(",rowCount=").append(queryStats.getExecutionRowCount()).append(",avgTime=").append(queryStats.getExecutionAvgTime()).append(",minTime=").append(queryStats.getExecutionMinTime()).append(",maxTime=").append(queryStats.getExecutionMaxTime()).append("]");
            queryBuf.append("\n").append(queryStr).append("\n\n");
            queries.add(new SortableQueryStat(queryBuf.toString(), queryStats));
        }
        Collections.sort(queries, new Comparator<SortableQueryStat>(){

            @Override
            public int compare(SortableQueryStat inS1, SortableQueryStat inS2) {
                return new Long(inS2.getStats().getExecutionMaxTime()).compareTo(inS1.getStats().getExecutionMaxTime());
            }
        });
        for (SortableQueryStat query : queries) {
            statsBuf.append(query.getQueryDesc());
        }
        return statsBuf.toString();
    }

    public static void logEntityStatistics() {
        LOGGER.setLevel(Level.INFO);
        LOGGER.info((Object) HibernateStatisticsUtil.getEntityStatisticsLog());
    }

    public static void logCollectionStatistics() {
        String[] collectionRoleNames;
        LOGGER.setLevel(Level.INFO);
        Statistics stats = HibernateStatisticsUtil.getStatistics();
        StringBuffer statsBuf = new StringBuffer("\nCollection Acccess\n");
        statsBuf.append("TOTALS ").append("[ loads=").append(stats.getCollectionLoadCount()).append(",fetches=").append(stats.getCollectionFetchCount()).append(",recreated=").append(stats.getCollectionRecreateCount()).append(",removes=").append(stats.getCollectionRemoveCount()).append("]\n");
        for (String entityName : collectionRoleNames = stats.getCollectionRoleNames()) {
            CollectionStatistics collectionStats = stats.getCollectionStatistics(entityName);
            if (collectionStats.getLoadCount() <= 0L) continue;
            statsBuf.append(entityName).append(" ").append("[fetches=").append(collectionStats.getFetchCount()).append(",loads=").append(collectionStats.getLoadCount()).append(",removes=").append(collectionStats.getRemoveCount()).append(",updates=").append(collectionStats.getUpdateCount()).append("]\n");
        }
        LOGGER.info((Object)statsBuf);
    }

    public static void writeStatsForBigSessions() {
        try {
            int countThreshold = SESSION_COUNT_THRESHOLD_DEFAULT;
            String countThresholdProp = "";
            try {
                countThresholdProp = System.getProperty(SESSION_COUNT_THRESHOLD, SESSION_COUNT_THRESHOLD_DEFAULT_STR);
                countThreshold = Integer.parseInt(countThresholdProp);
            }
            catch (Exception e) {
                LOGGER.debug((Object)("Invalid limit specified in the system property (com.navis.framework.hibernate.statistics.session_count_limit) :" + countThresholdProp), (Throwable)e);
                countThreshold = SESSION_COUNT_THRESHOLD_DEFAULT;
            }
            if (countThreshold <= 0) {
                return;
            }
            Session currentSession = HibernateApi.getInstance().getCurrentSession();
            if (currentSession == null) {
                return;
            }
            SessionStatistics statistics = currentSession.getStatistics();
            if (statistics == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)"Hibernate session is without Statistics, returning");
                }
                return;
            }
            int entityCount = statistics.getEntityCount();
            if (entityCount > countThreshold) {
                TransactionParms parms = TransactionParms.getBoundParms();
                UserContext uc = parms == null ? null : parms.getUserContext();
                StringBuilder statsBuf = new StringBuilder("\nLarge Hibernate Session Detected: ");
                if (uc != null) {
                    statsBuf.append(uc.getBriefDetails());
                }
                statsBuf.append(", logging threshold=").append(countThreshold).append(", total entity count=").append(entityCount).append(", collection count total=").append(statistics.getCollectionCount()).append("]\n");
                String msg = statsBuf.toString();
                BizFailure bf = BizFailure.create(msg);
                LOGGER.error((Object)msg, (Throwable)bf);
            }
        }
        catch (Throwable e) {
            LOGGER.debug((Object)"Error while attempting to write stats for big hibernate session", e);
        }
    }

    private static class SortableQueryStat {
        private final QueryStatistics _stats;
        private final String _query;

        SortableQueryStat(String inQuery, QueryStatistics inStats) {
            this._query = inQuery;
            this._stats = inStats;
        }

        public String getQueryDesc() {
            return this._query;
        }

        public QueryStatistics getStats() {
            return this._stats;
        }
    }

    private static class ClassAwareEntityStat {
        private final EntityStatistics _stats;
        private final String _className;

        ClassAwareEntityStat(String inClassName, EntityStatistics inStats) {
            this._className = inClassName;
            this._stats = inStats;
        }

        public String getEntityClassName() {
            return this._className;
        }

        public EntityStatistics getStats() {
            return this._stats;
        }
    }
}
