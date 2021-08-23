package com.zpmc.ztos.infra.base.common.type;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class CompareOperationType implements Serializable {
    private static final String EQUALS_OP = "=";
    private static final String LESS_THAN_OP = "<";
    private static final String LESS_THAN_EQUALS_OP = "<=";
    private static final String GREATER_THAN_OP = ">";
    private static final String GREATER_THAN_EQUALS_OP = ">=";
    public static final CompareOperationType EQUALS = new CompareOperationType("=");
    public static final CompareOperationType LESS_THAN = new CompareOperationType("<");
    public static final CompareOperationType LESS_THAN_EQUALS = new CompareOperationType("<=");
    public static final CompareOperationType GREATER_THAN = new CompareOperationType(">");
    public static final CompareOperationType GREATER_THAN_EQUALS = new CompareOperationType(">=");
    private final String _operation;

    private CompareOperationType(String inName) {
        this._operation = inName;
    }

    public String toString() {
        return this._operation;
    }

    @Nullable
    public static CompareOperationType lookup(String inFunctionKey) {
        if (EQUALS_OP.equals(inFunctionKey)) {
            return EQUALS;
        }
        if (LESS_THAN_OP.equals(inFunctionKey)) {
            return LESS_THAN;
        }
        if (LESS_THAN_EQUALS_OP.equals(inFunctionKey)) {
            return LESS_THAN_EQUALS;
        }
        if (GREATER_THAN_OP.equals(inFunctionKey)) {
            return GREATER_THAN;
        }
        if (GREATER_THAN_EQUALS_OP.equals(inFunctionKey)) {
            return GREATER_THAN_EQUALS;
        }
        return null;
    }

    @Nullable
    public Object readResolve() throws ObjectStreamException {
        return CompareOperationType.lookup(this._operation);
    }

}
