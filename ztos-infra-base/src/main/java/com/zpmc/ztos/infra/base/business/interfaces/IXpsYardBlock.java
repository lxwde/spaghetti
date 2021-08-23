package com.zpmc.ztos.infra.base.business.interfaces;

public interface IXpsYardBlock extends IXpsYardBin {
    public static final int BLOCK_TYPE_UNKNOWN = 0;
    public static final int BLOCK_TYPE_FORK = 1;
    public static final int BLOCK_TYPE_BUILDING = 2;
    public static final int BLOCK_TYPE_WHEELED = 3;
    public static final int BLOCK_TYPE_LOGICAL = 4;
    public static final int BLOCK_TYPE_STRADDLE = 5;
    public static final int BLOCK_TYPE_RTG = 6;
    public static final int BLOCK_TYPE_ASC = 7;
    public static final int BLOCK_TYPE_RAIL = 8;
    public static final int BLOCK_TYPE_STACKED_HEAP = 9;
    public static final int BLOCK_TYPE_BUFFER_HEAP = 10;
    public static final int BLOCK_TYPE_WHEELED_HEAP = 11;
    public static final int BLOCK_TYPE_WAREHOUSE_STACKED_INSIDE = 12;
    public static final int BLOCK_TYPE_WAREHOUSE_STACKED_OUTSIDE = 13;
    public static final int BLOCK_TYPE_WAREHOUSE_HEAP_INSIDE = 14;
    public static final int BLOCK_TYPE_WAREHOUSE_HEAP_OUTSIDE = 15;
    public static final int BLOCK_TYPE_BUFFER_PARALLEL = 16;
    public static final int BLOCK_TYPE_BUFFER_SERIAL = 17;
    public static final int BLOCK_TYPE_STRADDLE_GRID = 18;
    public static final int BLOCK_TYPE_CARMGTZ = 19;
    public static final Character BLOCK_TRAFFIC_FLOW_LOGICAL = Character.valueOf('A');
    public static final Character BLOCK_TRAFFIC_FLOW_ANTI_LOGICAL = Character.valueOf('D');
    public static final Character BLOCK_TRAFFIC_FLOW_BOTH = Character.valueOf('B');

    public String getConvertedTierName(int var1);

    public int getTierConvTableId();

    public boolean isValidTierName(String var1);

    public int convertTierNameToTier(String var1);

    public int getBlockType();

    public String getBlockName();

    public boolean isWheeled();

    public boolean isWheeledOrYardTransTZ();

    public boolean isGrounded();

    public Character getTrafficFlow();

    public boolean isTransferZone();

    public boolean isBlockTypeAscOrCarmg();

    public boolean isBlockTypeStraddle();
}
