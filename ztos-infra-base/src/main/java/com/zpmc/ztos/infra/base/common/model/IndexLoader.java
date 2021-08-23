package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ICarinaIndex;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.type.DatabaseType;
import com.zpmc.ztos.infra.base.common.utils.DatabaseIndexUtils;
import com.zpmc.ztos.infra.base.common.utils.DatabaseUtils;
import com.zpmc.ztos.infra.base.common.utils.PersistenceUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.*;
import org.hibernate.metadata.ClassMetadata;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.List;
import java.util.Map;

import static com.zpmc.ztos.infra.base.common.utils.DatabaseIndexUtils.getAllMetaDefinedIndexes;

public class IndexLoader {
    public static final String TYPE_GEOMETRY = "org.hibernatespatial.GeometryUserType";
    private DatabaseHelper _dbHelper;
    private static final Logger LOGGER = Logger.getLogger(IndexLoader.class);

    public IndexLoader() {
        DatabaseIndexUtils.setIndexNameSuffix(1);
    }

    public void setDbHelper(DatabaseHelper inDbHelper) {
        this._dbHelper = inDbHelper;
    }

    public List<ForeignKey> getDeclaredFKConstraints() {
        ArrayList<ForeignKey> fks = new ArrayList<ForeignKey>();
        Configuration configuration = PersistenceUtils.getMappingConfiguration();
        List<String> tablesToExcludeFormIndexView = DatabaseUtils.getTablesToExcludeFormIndexView();
        try {
//            Iterator iterator = configuration.getTableMappings();
//            while (iterator.hasNext()) {
//                Table parentTable = (Table)iterator.next();
//                if (tablesToExcludeFormIndexView.contains(parentTable.getName().toUpperCase())) continue;
//                Iterator foreignKeyIterator = parentTable.getForeignKeyIterator();
//                while (foreignKeyIterator.hasNext()) {
//                    ForeignKey foreignKey = (ForeignKey)foreignKeyIterator.next();
//                    fks.add(foreignKey);
//                }
//            }
        }
        catch (HibernateException e) {
            LOGGER.error((Object)("IndexLoader: misconfigured HBM file, exception:  " + (Object)((Object)e)));
        }
        fks.removeAll(Arrays.asList(new Object[]{null}));
        return fks;
    }

    public List<ICarinaIndex> getHbmDeclaredFKIndexes() {
        List<ICarinaIndex> hbmFKIndexes = this.getFKIndexes();
        List<ICarinaIndex> hbmMetaDefIndexesDisabled = getAllMetaDefinedIndexes("carina-fk-index-disable");
        hbmFKIndexes.removeAll(hbmMetaDefIndexesDisabled);
        return hbmFKIndexes;
    }

    public List<ICarinaIndex> getHbmDeclaredFKIndexes(Table inTable) {
        ArrayList<ICarinaIndex> fkIndexes = new ArrayList<ICarinaIndex>();
        List<ForeignKey> hbmFKIndexes = this.getDeclaredFKConstraints(inTable);
        for (ForeignKey fk : hbmFKIndexes) {
            fkIndexes.add(DatabaseIndexUtils.buildCarinaIndex(fk.getColumns().iterator(), fk.getTable().getName(), null));
        }
        fkIndexes.removeAll(Arrays.asList(new Object[]{null}));
        List<ICarinaIndex> hbmMetaDefIndexesDisabled = getAllMetaDefinedIndexes("carina-fk-index-disable");
        fkIndexes.removeAll(hbmMetaDefIndexesDisabled);
        return fkIndexes;
    }

    public List<ICarinaIndex> getHbmDeclaredIndexes() {
        List<ICarinaIndex> hbmIndexes = this.getIndexes();
        List<ICarinaIndex> hbmMetaDefIndexes = getAllMetaDefinedIndexes("carina-index");
        hbmIndexes.addAll(hbmMetaDefIndexes);
        hbmIndexes.addAll(this.getHbmDatabaseSpecificIndexes());
        return hbmIndexes;
    }

    public List<ICarinaIndex> getHbmDatabaseSpecificIndexes() {
        String metaDefinition = DatabaseIndexUtils.getSpecificDatabaseMetaDefinition();
        return getAllMetaDefinedIndexes(metaDefinition);
    }

    @Nullable
    public ICarinaIndex getDeclaredIndexByName(String inIndexName) {
        List<ICarinaIndex> declaredIndexes = this.getHbmDeclaredIndexes();
        for (ICarinaIndex index : declaredIndexes) {
            if (!inIndexName.equalsIgnoreCase(index.getIndexName())) continue;
            return index;
        }
        return null;
    }

    public List<ICarinaIndex> getMandatorySpatialIndexes() {
        if (!this._dbHelper.isDatabaseType(DatabaseType.ORACLE)) {
            return Collections.emptyList();
        }
        return this.getSpatialIndexes();
    }

    @Nullable
    public ICarinaIndex getPrimaryKeyIndex(Table inTable) {
        return DatabaseIndexUtils.buildCarinaIndex(inTable.getPrimaryKey().getColumnIterator(), inTable.getName(), null);
    }

    public List<ICarinaIndex> getUniqueKeyIndexes(Table inTable) {
        ArrayList<ICarinaIndex> indexes = new ArrayList<ICarinaIndex>();
        Iterator uniqueKeyIterator = inTable.getUniqueKeyIterator();
        while (uniqueKeyIterator.hasNext()) {
            UniqueKey uk = (UniqueKey)uniqueKeyIterator.next();
            indexes.add(DatabaseIndexUtils.buildCarinaIndex(uk.columnIterator(), inTable.getName(), null));
        }
        Iterator columnIterator = inTable.getColumnIterator();
        while (columnIterator.hasNext()) {
            Column column = (Column)columnIterator.next();
            if (!column.isUnique()) continue;
            DatabaseType databaseType = this._dbHelper.databaseType();
            CarinaIndexFactory indexFactory = new CarinaIndexFactory(databaseType);
            ICarinaIndex index = indexFactory.getCarinaIndexObject(inTable.getName(), Arrays.asList(column.getName()), null);
            indexes.add(index);
        }
        return indexes;
    }

    public List<ICarinaIndex> getPropertyIndex(Table inTable) {
        ArrayList<ICarinaIndex> indexes = new ArrayList<ICarinaIndex>();
        Iterator indexIterator = inTable.getIndexIterator();
        while (indexIterator.hasNext()) {
            Index index = (Index)indexIterator.next();
            indexes.add(DatabaseIndexUtils.buildCarinaIndex(index.getColumnIterator(), inTable.getName(), index.getName()));
        }
        return indexes;
    }

    public List<ICarinaIndex> getSpatialIndexes() {
        ArrayList<ICarinaIndex> spatialIndexes = new ArrayList<ICarinaIndex>();
        SessionFactory sessionFactory = PersistenceUtils.getSessionFactory();
  //      String className2 = null;
        try {
            Map classMetas = sessionFactory.getAllClassMetadata();
            for (Object className2 : classMetas.keySet()) {
                ClassMetadata meta = (ClassMetadata)classMetas.get(className2);
                Configuration configuration = PersistenceUtils.getMappingConfiguration();
//                PersistentClass classMapping = configuration.getClassMapping(meta.getEntityName());
//                String tableName = classMapping.getTable().getName();
//                Table table = classMapping.getTable();
//                Iterator columnIterator = table.getColumnIterator();
//                while (columnIterator.hasNext()) {
//                    Column column = (Column)columnIterator.next();
//                    if (column == null || column.getValue() == null || column.getValue().getType() == null || !TYPE_GEOMETRY.equalsIgnoreCase(column.getValue().getType().getName())) continue;
//                    CarinaIndexFactory indexFactory = new CarinaIndexFactory(DatabaseType.ORACLE);
//                    ICarinaIndex index = indexFactory.getCarinaIndexObject(tableName, Arrays.asList(column.getName()), null);
//                    if (spatialIndexes.contains(index)) continue;
//                    spatialIndexes.add(index);
//                }
            }
        }
        catch (HibernateException e) {
//            LOGGER.error((Object)("Failure accessing class MetaData for " + className2 + " due to " + e.getMessage()));
        }
        return spatialIndexes;
    }

    public List<Table> getQualifiedTables() {
        Configuration configuration = PersistenceUtils.getMappingConfiguration();
        ArrayList<Table> result = new ArrayList<Table>();
        try {
//            Iterator iterator = configuration.getTableMappings();
//            List<String> tablesToExcludeFormIndexView = DatabaseUtils.getTablesToExcludeFormIndexView();
//            while (iterator.hasNext()) {
//                Table aTable = (Table)iterator.next();
//                if (aTable.getSubselect() != null || tablesToExcludeFormIndexView.contains(aTable.getName().toUpperCase())) continue;
//                result.add(aTable);
//            }
        }
        catch (HibernateException e) {
            LOGGER.error((Object)("IndexLoader: misconfigured HBM file, exception:  " + (Object)((Object)e)));
        }
        return result;
    }

    private List<ICarinaIndex> getIndexes() {
        ArrayList<ICarinaIndex> indexes = new ArrayList<ICarinaIndex>();
        List<Table> tables = this.getQualifiedTables();
        for (Table aTable : tables) {
            indexes.addAll(this.getPropertyIndex(aTable));
            indexes.add(this.getPrimaryKeyIndex(aTable));
            indexes.addAll(this.getUniqueKeyIndexes(aTable));
        }
        indexes.removeAll(Arrays.asList(new Object[]{null}));
        return indexes;
    }

    private List<ICarinaIndex> getFKIndexes() {
        ArrayList<ICarinaIndex> fkIndexes = new ArrayList<ICarinaIndex>();
        List<ForeignKey> fks = this.getDeclaredFKConstraints();
        for (ForeignKey fk : fks) {
            fkIndexes.add(DatabaseIndexUtils.buildCarinaIndex(fk.getColumns().iterator(), fk.getTable().getName(), null));
        }
        fkIndexes.removeAll(Arrays.asList(new Object[]{null}));
        return fkIndexes;
    }

    private List<ForeignKey> getDeclaredFKConstraints(Table inTable) {
        ArrayList<ForeignKey> fks = new ArrayList<ForeignKey>();
        Iterator foreignKeyIterator = inTable.getForeignKeyIterator();
        while (foreignKeyIterator.hasNext()) {
            ForeignKey foreignKey = (ForeignKey)foreignKeyIterator.next();
            fks.add(foreignKey);
        }
        fks.removeAll(Arrays.asList(new Object[]{null}));
        return fks;
    }
}
