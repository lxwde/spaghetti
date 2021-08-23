package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueHolder;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public class EntityCollectionUtils {
    private EntityCollectionUtils() {
    }

    @Deprecated
    public static Collection convertEntityCollectionToVOCollection(Collection inCollection, String[] inFields) {
        MetafieldIdList fieldsToExtract = inFields == null || inFields.length == 0 ? new MetafieldIdList() : new MetafieldIdList(inFields);
        return EntityCollectionUtils.convertEntityCollectionToVOCollection(inCollection, fieldsToExtract);
    }

    public static List convertEntityCollectionToVOCollection(Collection inCollection, MetafieldIdList inMetafieldIds) {
        if (inCollection == null || inCollection.isEmpty()) {
            return new ArrayList();
        }
        ArrayList<ValueObject> fieldList = new ArrayList<ValueObject>(inCollection.size());
        for (Object item : inCollection) {
            if (!(item instanceof IEntity)) continue;
            IEntity entity = (IEntity)item;
            fieldList.add(entity.getValueObject(inMetafieldIds));
        }
        return fieldList;
    }

    public static String[] convertToIds(@NotNull Collection<IMetafieldId> inMetafieldIds) {
        String[] fieldIds = new String[inMetafieldIds.size()];
        int i = 0;
        for (IMetafieldId id : inMetafieldIds) {
            fieldIds[i++] = id.getQualifiedId();
        }
        return fieldIds;
    }

    public static Serializable[] convertEntityCollection2GkeyArray(Collection inEntities) {
        return EntityCollectionUtils.convertPersistentCollection2GkeyArray(inEntities);
    }

    public static List convertPersistentCollection2List(Collection inEntities) {
        ArrayList<Serializable> gKeyList = new ArrayList<Serializable>(inEntities.size());
        for (Object item : inEntities) {
            if (!(item instanceof IEntity)) continue;
            gKeyList.add(((IEntity)item).getPrimaryKey());
        }
        return gKeyList;
    }

    public static Serializable[] convertPersistentCollection2Array(Collection inEntities, boolean inDoFormatForUI) {
        Serializable[] array = new Serializable[inEntities.size()];
        int i = 0;
        for (Object item : inEntities) {
            if (item instanceof IEntity) {
                if (inDoFormatForUI) {
                    array[i++] = ((IEntity)item).getHumanReadableKey();
                    continue;
                }
                array[i++] = ((IEntity)item).getPrimaryKey();
                continue;
            }
            if (!(item instanceof Serializable)) continue;
            array[i++] = (Serializable)item;
        }
        return array;
    }

    public static Serializable[] convertPersistentCollection2GkeyArray(Collection inEntities) {
        return EntityCollectionUtils.convertPersistentCollection2Array(inEntities, false);
    }

//    public static void addEntityCollectionToBizResponse(Collection inCollection, BizResponse inOutResponse) {
//        ArrayList<ValueObject> vaoList = new ArrayList<ValueObject>(inCollection.size());
//        for (Object item : inCollection) {
//            if (!(item instanceof IEntity)) continue;
//            vaoList.add(((IEntity)item).getValueObject());
//        }
//        inOutResponse.setQueryResult(new QueryResultImpl(vaoList));
//    }
//
//    public static void addVOCollectionToBizResponse(Collection inCollection, BizResponse inOutResponse) {
//        ArrayList vaoList = new ArrayList(inCollection.size());
//        for (Object item : inCollection) {
//            if (!(item instanceof IValueHolder)) continue;
//            vaoList.add(item);
//        }
//        inOutResponse.setQueryResult(new QueryResultImpl(vaoList));
//    }

    @NotNull
    public static Map<Serializable, ValueObject> convertValueObjectsToMap(@NotNull Collection<ValueObject> inValueObjects) {
        HashMap<Serializable, ValueObject> entityMap = new HashMap<Serializable, ValueObject>(inValueObjects.size());
        for (ValueObject vo : inValueObjects) {
            entityMap.put(vo.getEntityPrimaryKey(), vo);
        }
        return entityMap;
    }

    @NotNull
    public static Serializable[] convertValueHolderToGkeyArray(@NotNull Collection<IValueHolder> inValueObjects) {
        Serializable[] array = new Serializable[inValueObjects.size()];
        int i = 0;
        for (IValueHolder vo : inValueObjects) {
            array[i++] = vo.getEntityPrimaryKey();
        }
        return array;
    }

    @NotNull
    public static Map<Serializable, IValueHolder> convertValueHoldersToMap(@NotNull Collection<IValueHolder> inValueObjects) {
        HashMap<Serializable, IValueHolder> entityMap = new HashMap<Serializable, IValueHolder>(inValueObjects.size());
        for (IValueHolder vo : inValueObjects) {
            entityMap.put(vo.getEntityPrimaryKey(), vo);
        }
        return entityMap;
    }

    public static void dumpLogOnEntityCollection(Collection inCollection, Logger inLogger) {
        for (Object item : inCollection) {
            if (!(item instanceof IEntity)) continue;
            ValueObject vo = ((IEntity)item).getValueObject();
            inLogger.info((Object)vo.getDetailsForLog());
        }
    }

    public static void dumpLogOnVOCollection(Collection inCollection, Logger inLogger) {
        for (Object item : inCollection) {
            if (!(item instanceof ValueObject)) continue;
            inLogger.info((Object)((ValueObject)item).getDetailsForLog());
        }
    }
}
