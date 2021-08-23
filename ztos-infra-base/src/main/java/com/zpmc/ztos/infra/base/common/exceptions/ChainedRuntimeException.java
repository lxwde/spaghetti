package com.zpmc.ztos.infra.base.common.exceptions;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class ChainedRuntimeException extends RuntimeException {

    private Throwable _cause = this;

    public ChainedRuntimeException(String inMessage, Throwable inCause) {
        super(inMessage);
        this._cause = inCause;
    }

    @Override
    @Nullable
    public Throwable getCause() {
        return this._cause == this ? null : this._cause;
    }

    @Override
    public synchronized Throwable initCause(Throwable inCause) {
        if (this._cause != this) {
            throw new IllegalStateException("Can't overwrite cause");
        }
        if (inCause == this) {
            throw new IllegalArgumentException("Self-causation not permitted");
        }
        this._cause = inCause;
        return this;
    }
}
