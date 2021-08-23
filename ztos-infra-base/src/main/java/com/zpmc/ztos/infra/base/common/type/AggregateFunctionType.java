package com.zpmc.ztos.infra.base.common.type;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class AggregateFunctionType implements Serializable {

    private static final String MIN_FUNCTION = "min";
    private static final String MAX_FUNCTION = "max";
    private static final String SUM_FUNCTION = "sum";
    public static final AggregateFunctionType MIN = new AggregateFunctionType("min");
    public static final AggregateFunctionType MAX = new AggregateFunctionType("max");
    public static final AggregateFunctionType SUM = new AggregateFunctionType("sum");
    private final String _functionName;

    private AggregateFunctionType(String inName) {
        this._functionName = inName;
    }

    public String toString() {
        return this._functionName;
    }

    @Nullable
    public static AggregateFunctionType lookup(String inFunctionKey) {
        if (MIN_FUNCTION.equals(inFunctionKey)) {
            return MIN;
        }
        if (MAX_FUNCTION.equals(inFunctionKey)) {
            return MAX;
        }
        if (SUM_FUNCTION.equals(inFunctionKey)) {
            return SUM;
        }
        return null;
    }

    @Nullable
    public Object readResolve() throws ObjectStreamException {
        return AggregateFunctionType.lookup(this._functionName);
    }
}
