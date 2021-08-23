package com.zpmc.ztos.infra.base.business.enums.xps;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.List;

public class AtomizedEnumXpsConverter {
    private AtomizedEnumXpsConverter() {
    }

    @Nullable
    public static <T extends AtomizedEnum> T findEnumInstance(@Nullable Long inXpsOrdinal, @NotNull List<T> inXpsOrdinal2EnumInstanceList, @NotNull T inDefaultValue) {
        if (inXpsOrdinal == null) {
            return inDefaultValue;
        }
        int listIndex = inXpsOrdinal.intValue();
        int xpsListIndex = 0;
        if (inDefaultValue instanceof CheOperatingModeEnum) {
            xpsListIndex = inXpsOrdinal == 0L ? 0 : (inXpsOrdinal == 1L ? 1 : (inXpsOrdinal == 2L ? 2 : (inXpsOrdinal == 3L ? 3 : (inXpsOrdinal == 4L ? 4 : (inXpsOrdinal == 5L ? 5 : (inXpsOrdinal == 6L ? 6 : 1))))));
        }
        if (inDefaultValue instanceof WiMoveKindEnum) {
            xpsListIndex = inXpsOrdinal == 0L ? 0 : (inXpsOrdinal == 1L ? 8 : (inXpsOrdinal == 2L ? 1 : (inXpsOrdinal == 3L ? 7 : (inXpsOrdinal == 4L ? 2 : (inXpsOrdinal == 5L ? 3 : (inXpsOrdinal == 6L ? 6 : (inXpsOrdinal == 7L ? 4 : (inXpsOrdinal == 8L ? 5 : (inXpsOrdinal == 9L ? 9 : 9)))))))));
        } else if (inDefaultValue instanceof CheStatusEnum) {
            xpsListIndex = inXpsOrdinal == 0L ? 0 : (inXpsOrdinal == 1L ? 1 : (inXpsOrdinal == 2L ? 2 : (inXpsOrdinal == 3L ? 3 : (inXpsOrdinal == 4L ? 4 : (inXpsOrdinal == 5L ? 5 : (inXpsOrdinal == 6L ? 6 : (inXpsOrdinal == 7L ? 7 : (inXpsOrdinal == 8L ? 8 : (inXpsOrdinal == 9L ? 9 : (inXpsOrdinal == 10L ? 10 : (inXpsOrdinal == 11L ? 11 : 2)))))))))));
        } else if (inDefaultValue instanceof CheTalkStatusEnum) {
            xpsListIndex = inXpsOrdinal == 1L ? 0 : (inXpsOrdinal == 2L ? 1 : (inXpsOrdinal == 3L ? 2 : 2));
        } else if (inDefaultValue instanceof CheMessageStatusEnum) {
            xpsListIndex = inXpsOrdinal == 1L ? 0 : (inXpsOrdinal == 2L ? 1 : (inXpsOrdinal == 3L ? 2 : 2));
        } else if (inDefaultValue instanceof PowDispatchModeEnum) {
            xpsListIndex = inXpsOrdinal == 0L ? 0 : (inXpsOrdinal == 1L ? 1 : (inXpsOrdinal == 2L || inXpsOrdinal == 32L ? 2 : (inXpsOrdinal == 4L ? 3 : (inXpsOrdinal == 5L ? 4 : (inXpsOrdinal == 6L ? 5 : (inXpsOrdinal == 7L ? 6 : (inXpsOrdinal == 8L ? 7 : 0)))))));
        } else {
            if (listIndex < 0 || listIndex >= inXpsOrdinal2EnumInstanceList.size()) {
                return inDefaultValue;
            }
            xpsListIndex = listIndex;
        }
        AtomizedEnum result = (AtomizedEnum)inXpsOrdinal2EnumInstanceList.get(xpsListIndex);
        if (result == null) {
            return inDefaultValue;
        }
        return (T)result;
    }

    @Nullable
    public static <T extends AtomizedEnum> Long findXpsOrdinal(@Nullable T inEnumInstance, @NotNull List<T> inXpsOrdinal2EnumInstanceList, @Nullable Long inDefaultValue) {
        if (inEnumInstance == null) {
            return inDefaultValue;
        }
        if (inEnumInstance instanceof CheOperatingModeEnum) {
            if (inEnumInstance.getKey().equals("UNKNOWN")) {
                return 0L;
            }
            if (inEnumInstance.getKey().equals("REQUIREHANDLER")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("SELFCOMPLETE")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("TRUCK")) {
                return 3L;
            }
            if (inEnumInstance.getKey().equals("CRANE")) {
                return 4L;
            }
            if (inEnumInstance.getKey().equals("TRUCKSHIFTCHASSIS")) {
                return 5L;
            }
            if (inEnumInstance.getKey().equals("TRUCKCOUPLED")) {
                return 6L;
            }
            return 1L;
        }
        if (inEnumInstance instanceof WiMoveKindEnum) {
            if (inEnumInstance.getKey().equals("RECV")) {
                return 0L;
            }
            if (inEnumInstance.getKey().equals("SHFT")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("DLVR")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("YARD")) {
                return 3L;
            }
            if (inEnumInstance.getKey().equals("DSCH")) {
                return 4L;
            }
            if (inEnumInstance.getKey().equals("LOAD")) {
                return 5L;
            }
            if (inEnumInstance.getKey().equals("SHOB")) {
                return 6L;
            }
            if (inEnumInstance.getKey().equals("RDSC")) {
                return 7L;
            }
            if (inEnumInstance.getKey().equals("RLOD")) {
                return 8L;
            }
            if (inEnumInstance.getKey().equals("OTHR")) {
                return 9L;
            }
            return 9L;
        }
        if (inEnumInstance instanceof CheStatusEnum) {
            if (inEnumInstance.getKey().equals("UNKNOWN")) {
                return 0L;
            }
            if (inEnumInstance.getKey().equals("WORKING")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("UNAVAIL")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("WANTTOQUIT")) {
                return 3L;
            }
            if (inEnumInstance.getKey().equals("OUTOFSERVICE")) {
                return 4L;
            }
            if (inEnumInstance.getKey().equals("ONBREAK")) {
                return 5L;
            }
            if (inEnumInstance.getKey().equals("OFFLINEBREAKDOWN")) {
                return 6L;
            }
            if (inEnumInstance.getKey().equals("OFFLINEMAINTENANCE")) {
                return 7L;
            }
            if (inEnumInstance.getKey().equals("OFFLINESAFETY")) {
                return 8L;
            }
            if (inEnumInstance.getKey().equals("ONLINEREPAIR")) {
                return 9L;
            }
            if (inEnumInstance.getKey().equals("ONLINEINIT")) {
                return 10L;
            }
            if (inEnumInstance.getKey().equals("REHANDLE")) {
                return 11L;
            }
            if (inEnumInstance.getKey().equals("MANUAL")) {
                return 12L;
            }
            return 2L;
        }
        if (inEnumInstance instanceof CheTalkStatusEnum) {
            if (inEnumInstance.getKey().equals("WANTTALK")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("NOTALK")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("NEEDSCONFIRM")) {
                return 3L;
            }
            return 3L;
        }
        if (inEnumInstance instanceof CheMessageStatusEnum) {
            if (inEnumInstance.getKey().equals("NOMSG")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("MSGSENT")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("MSGACKED")) {
                return 3L;
            }
            return 3L;
        }
        if (inEnumInstance instanceof PowDispatchModeEnum) {
            if (inEnumInstance.getKey().equals("STOP")) {
                return 0L;
            }
            if (inEnumInstance.getKey().equals("MANUAL")) {
                return 1L;
            }
            if (inEnumInstance.getKey().equals("AUTO")) {
                return 2L;
            }
            if (inEnumInstance.getKey().equals("CUSTOM")) {
                return 4L;
            }
            if (inEnumInstance.getKey().equals("TRUCKSONLY")) {
                return 5L;
            }
            if (inEnumInstance.getKey().equals("A4")) {
                return 6L;
            }
            if (inEnumInstance.getKey().equals("OPTIMIZE")) {
                return 7L;
            }
            if (inEnumInstance.getKey().equals("OPTIMIZETRUCKS")) {
                return 8L;
            }
            return 0L;
        }
        int arrayIndex = inXpsOrdinal2EnumInstanceList.indexOf(inEnumInstance);
        if (arrayIndex < 0) {
            return inDefaultValue;
        }
        return (long)arrayIndex;
    }

    public static <T extends AtomizedEnum> void synchEntityEnumXpsFieldChanges(@NotNull IEntity inOutEntity, @NotNull IMetafieldId inMetafieldIdEnum, @NotNull IMetafieldId inMetafieldIdXps, @NotNull Class<T> inAtomizedEnumClass, @NotNull T inDefaultEnum, @Nullable Long inDefaultXps, @NotNull List<T> inXpsOrdinal2EnumInstanceList, @NotNull FieldChanges inOutMoreChanges, @Nullable T inNewEnum, @Nullable Long inNewXps) {
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(inOutEntity, inMetafieldIdEnum, inMetafieldIdXps, inAtomizedEnumClass, inDefaultEnum, inDefaultXps, inXpsOrdinal2EnumInstanceList, inOutMoreChanges, AtomizedEnumXpsConverter.isEqualEvenIfNull(inDefaultEnum, inNewEnum), AtomizedEnumXpsConverter.isEqualEvenIfNull(inDefaultXps, inNewXps), inNewEnum, inNewXps);
    }

    public static <T extends AtomizedEnum> void synchEntityEnumXpsFieldChanges(@NotNull IEntity inOutEntity, @NotNull IMetafieldId inMetafieldIdEnum, @NotNull IMetafieldId inMetafieldIdXps, @NotNull Class inAtomizedEnumClass, @Nullable T inDefaultEnum, @Nullable Long inDefaultXps, @NotNull List inXpsOrdinal2EnumInstanceList, @NotNull FieldChanges inOutMoreChanges, @NotNull FieldChanges inChanges) {
        AtomizedEnumXpsConverter.synchEntityEnumXpsFieldChanges(inOutEntity, inMetafieldIdEnum, inMetafieldIdXps, inAtomizedEnumClass, inDefaultEnum, inDefaultXps, inXpsOrdinal2EnumInstanceList, inOutMoreChanges, !inChanges.hasFieldChange(inMetafieldIdEnum), !inChanges.hasFieldChange(inMetafieldIdXps), (AtomizedEnum) AtomizedEnumXpsConverter.getTypedFieldChangeValue(inAtomizedEnumClass, inChanges.getFieldChange(inMetafieldIdEnum)), AtomizedEnumXpsConverter.getTypedFieldChangeValue(Long.class, inChanges.getFieldChange(inMetafieldIdXps)));
    }

    private static <T extends AtomizedEnum> void synchEntityEnumXpsFieldChanges(@NotNull IEntity inOutEntity, @NotNull IMetafieldId inMetafieldIdEnum, @NotNull IMetafieldId inMetafieldIdXps, @NotNull Class<T> inAtomizedEnumClass, @NotNull T inDefaultEnum, @Nullable Long inDefaultXps, @NotNull List<T> inXpsOrdinal2EnumInstanceList, @NotNull FieldChanges inOutMoreChanges, boolean inIsEnumUnchanged, boolean inIsXpsUnchanged, @Nullable T inNewEnum, @Nullable Long inNewXps) {
        if (inIsEnumUnchanged == inIsXpsUnchanged) {
            return;
        }
        if (inIsEnumUnchanged) {
            T newXpsEnum = AtomizedEnumXpsConverter.findEnumInstance(inNewXps, inXpsOrdinal2EnumInstanceList, inDefaultEnum);
            inOutEntity.setSelfAndFieldChange(inMetafieldIdEnum, newXpsEnum, inOutMoreChanges);
            return;
        }
        Long newEnumXps = AtomizedEnumXpsConverter.findXpsOrdinal(inNewEnum, inXpsOrdinal2EnumInstanceList, inDefaultXps);
        inOutEntity.setSelfAndFieldChange(inMetafieldIdXps, (Object)newEnumXps, inOutMoreChanges);
    }

    @Nullable
    private static <T> T getTypedFieldChangeValue(@NotNull Class<T> inFieldTypeClass, @Nullable FieldChange inFieldChange) {
        if (inFieldChange == null) {
            return null;
        }
        Object fieldValueObject = inFieldChange.getNewValue();
        if (!inFieldTypeClass.isAssignableFrom(fieldValueObject.getClass())) {
            return null;
        }
        return (T)fieldValueObject;
    }

    private static boolean isEqualEvenIfNull(@Nullable Object inLhsObject, @Nullable Object inRhsObject) {
        if (inLhsObject == null) {
            return inRhsObject == null;
        }
        return inLhsObject.equals(inRhsObject);
    }

}
