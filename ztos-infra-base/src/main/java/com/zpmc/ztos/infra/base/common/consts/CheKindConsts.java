package com.zpmc.ztos.infra.base.common.consts;

public class CheKindConsts {
    public static final Long UNKNOWN = new Long(0L);
    public static final Long STRAD = new Long(1L);
    public static final Long RTG = new Long(2L);
    public static final Long TRUCK = new Long(3L);
    public static final Long CRANE = new Long(4L);
    public static final Long FORK = new Long(5L);
    public static final Long CLERK = new Long(6L);
    public static final Long RMG = new Long(7L);
    public static final Long MTT = new Long(8L);
    public static final Long MTS = new Long(9L);
    public static final Long REACHSTACKER = new Long(10L);
    public static final Long AGV = new Long(11L);
    public static final Long TT = new Long(12L);
    public static final Long ASC = new Long(13L);
    public static final Long WSV = new Long(14L);
    private static final String[] CODES = new String[]{"?", "STR", "RTG", "TRK", "CRN", "FORK", "MAN", "RMG", "MTT", "MTS", "RS", "AGV", "TT", "ASC", "WSV"};

    public static String getCode(Long inCheKind) {
        if (inCheKind == null || inCheKind.intValue() >= CODES.length) {
            return "?";
        }
        return CODES[inCheKind.intValue()];
    }
}
