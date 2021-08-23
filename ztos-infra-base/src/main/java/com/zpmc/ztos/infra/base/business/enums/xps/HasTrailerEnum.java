package com.zpmc.ztos.infra.base.business.enums.xps;

import com.sun.istack.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public enum HasTrailerEnum {
    UNKNOWN(0, "Unknown"),
    DOES_NOT_HAVE_TRAILER(1, "Does not have Trailer"),
    HAS_A_TRAILER(2, "Has Trailer"),
    HAS_SPECIFIC_TRAILER(3, "Has Specific Trailer"),
    HAS_NOMINATED_TRAILER(4, "Has a Nominated Trailer (Is not attached yet)"),
    HAS_SPECIFIC_INVALID_TRAILER(5, "Has Specific Invalid Trailer");

    private int _serialValue;
    private String _display;

    @NotNull
    public static HasTrailerEnum fromSerialValue(int inSerialValue) {
        switch (inSerialValue) {
            case 0: {
                return UNKNOWN;
            }
            case 1: {
                return DOES_NOT_HAVE_TRAILER;
            }
            case 2: {
                return HAS_A_TRAILER;
            }
            case 3: {
                return HAS_SPECIFIC_TRAILER;
            }
            case 4: {
                return HAS_NOMINATED_TRAILER;
            }
            case 5: {
                return HAS_SPECIFIC_INVALID_TRAILER;
            }
        }
        return UNKNOWN;
    }

    public int toSerialValue() {
        return this._serialValue;
    }

    @NotNull
    public String toDisplayString() {
        return this._display;
    }

    public void writeToBinaryStream(@NotNull DataOutputStream inOutputStream) throws IOException {
        inOutputStream.writeByte(this.toSerialValue());
    }

    public static HasTrailerEnum readFromBinaryStream(@NotNull DataInputStream inStream) throws IOException {
        return HasTrailerEnum.fromSerialValue(inStream.readByte());
    }

    private HasTrailerEnum(int inSerialValue, String inDisplayString) {
        this._serialValue = inSerialValue;
        this._display = inDisplayString;
    }
}
