package com.zpmc.ztos.infra.base.business.enums.xps;

import com.sun.istack.NotNull;

import java.util.Collection;
import java.util.EnumSet;

public enum TransferZoneKindEnum {
    NOT_A_TRANSFERZONE("Not a"),
    LANDSIDE("Landside"),
    WATERSIDE("Waterside"),
    RAIL("Rail"),
    VESSEL("Vessel"),
    CARMG("CARMG"),
    OTHER("Other");

    private final String _description;
    public static final Collection<TransferZoneKindEnum> IS_ASC_TRANSFER_ZONE;
    public static final Collection<TransferZoneKindEnum> IS_END_OR_SIDE_LOADING;

    private TransferZoneKindEnum(String inDescription) {
        this._description = inDescription + " transfer zone";
    }

    @NotNull
    public String toLogString() {
        return this._description;
    }

    static {
        IS_ASC_TRANSFER_ZONE = EnumSet.of(LANDSIDE, WATERSIDE);
        IS_END_OR_SIDE_LOADING = EnumSet.of(LANDSIDE, WATERSIDE, CARMG);
    }
}
