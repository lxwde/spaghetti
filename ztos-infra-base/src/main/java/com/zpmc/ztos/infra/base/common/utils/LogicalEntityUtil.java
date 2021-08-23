package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LogicalEntityUtil {
    private static final String UNIT_EQUIP_STATES_FIELD_ID = "unitEquipmentStates";
    private static final String UNIT_ACC_EQUIP_STATES_FIELD_ID = "unitAccEquipmentStates";
    private static final String UNIT_CTR_EQUIP_STATES_FIELD_ID = "unitCtrEquipmentStates";
    private static final String UNIT_CHS_EQUIP_STATES_FIELD_ID = "unitChsEquipmentStates";
    private static final String GDS_BILLS_OF_LADING_FIELD_ID = "gdsblBillsOfLading";
    private static final String UNIT_SERVICE_ORDERS_FIELD_ID = "unitServiceOrders";

    public static Class getPathToGuardianClass(IMetafieldId inPathToGuardian) {
        String fieldId = inPathToGuardian.getFieldId();
        return HiberCache.getFieldClass((String)fieldId);
    }

    public static LogicalEntityEnum[] getPathToGuardianCompatibleLogEntityTypes(IMetafieldId inPathToGuardian) {
        if (inPathToGuardian != null) {
            String fieldId = inPathToGuardian.getFieldId();
            return LogicalEntityUtil.getPossibleLogEntityTypesForClass(fieldId);
        }
        return null;
    }

    public static boolean isLogicalEntityCompatibleWithPathToGuardian(IMetafieldId inPathToGuardian, LogicalEntityEnum inLogicalEntity) {
        if (inLogicalEntity != null) {
            LogicalEntityEnum[] logicalEntities = LogicalEntityUtil.getPathToGuardianCompatibleLogEntityTypes(inPathToGuardian);
            for (int i = 0; i < logicalEntities.length; ++i) {
                LogicalEntityEnum logicalEntity = logicalEntities[i];
                if (!inLogicalEntity.equals((Object)logicalEntity)) continue;
                return true;
            }
        }
        return false;
    }

    public static LogicalEntityEnum[] getPossibleLogEntityTypesForClass(String inFieldId) {
        Class fieldClass = HiberCache.getFieldClass((String)inFieldId);
        ArrayList<LogicalEntityEnum> validLogicalEntities = new ArrayList<LogicalEntityEnum>();
        if (fieldClass != null) {
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.BKG, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.DO, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.LO, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.RO, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.ERO, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.RCARV, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.RV, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.VV, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.EQ, validLogicalEntities);
            LogicalEntityUtil.addIfBelongs(fieldClass, LogicalEntityEnum.UNIT, validLogicalEntities);
            if (Collection.class.isAssignableFrom(fieldClass)) {
                if (UNIT_EQUIP_STATES_FIELD_ID.equals(inFieldId) || UNIT_ACC_EQUIP_STATES_FIELD_ID.equals(inFieldId) || UNIT_CTR_EQUIP_STATES_FIELD_ID.equals(inFieldId) || UNIT_CHS_EQUIP_STATES_FIELD_ID.equals(inFieldId)) {
                    validLogicalEntities.add(LogicalEntityEnum.EQ);
                } else if (GDS_BILLS_OF_LADING_FIELD_ID.equals(inFieldId)) {
                    validLogicalEntities.add(LogicalEntityEnum.BL);
                } else if (UNIT_SERVICE_ORDERS_FIELD_ID.equals(inFieldId)) {
                    validLogicalEntities.add(LogicalEntityEnum.SRVO);
                } else {
                    throw BizFailure.create((String)("Cannot map from entered field " + inFieldId + " of type colleciton to correct logical entity!"));
                }
            }
        }
        return validLogicalEntities.toArray(new LogicalEntityEnum[0]);
    }

    private static void addIfBelongs(Class inFieldClass, LogicalEntityEnum inLogicalEntityEnum, List inOutValidLogicalEntities) {
        Class clazz = inLogicalEntityEnum.getDomainClass();
        if (inFieldClass.isAssignableFrom(clazz)) {
            inOutValidLogicalEntities.add(inLogicalEntityEnum);
        }
    }
}
