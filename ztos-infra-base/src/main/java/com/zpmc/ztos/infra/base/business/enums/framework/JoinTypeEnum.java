package com.zpmc.ztos.infra.base.business.enums.framework;
import org.apache.commons.lang.enums.Enum;

public class JoinTypeEnum extends Enum {
    private static final String INNER_JOIN_HQL = "inner join";
    private static final String LEFT_OUTER_JOIN_HQL = "left outer join";
    private static final String RIGHT_OUTER_JOIN_HQL = "right outer join";
    private static final String LEFT_OUTER_JOIN_FETCH_HQL = "left outer join fetch";
    public static final JoinTypeEnum INNER_JOIN = new JoinTypeEnum("inner join");
    public static final JoinTypeEnum LEFT_OUTER_JOIN = new JoinTypeEnum("left outer join");
    public static final JoinTypeEnum LEFT_OUTER_JOIN_FETCH = new JoinTypeEnum("left outer join fetch");
    public static final JoinTypeEnum RIGHT_OUTER_JOIN = new JoinTypeEnum("right outer join");

    private JoinTypeEnum(String inName) {
        super(inName);
    }
}
