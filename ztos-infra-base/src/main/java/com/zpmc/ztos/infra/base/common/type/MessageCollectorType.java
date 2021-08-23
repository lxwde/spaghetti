package com.zpmc.ztos.infra.base.common.type;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MessageCollectorType implements Serializable {
    private MessageType[] messages;
    private Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public MessageCollectorType() {
    }

    public MessageCollectorType(MessageType[] messages) {
        this.messages = messages;
    }

    public MessageType[] getMessages() {
        return this.messages;
    }

    public void setMessages(MessageType[] messages) {
        this.messages = messages;
    }

    public MessageType getMessages(int i) {
        return this.messages[i];
    }

    public void setMessages(int i, MessageType _value) {
        this.messages[i] = _value;
    }

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MessageCollectorType)) {
            return false;
        }
        MessageCollectorType other = (MessageCollectorType)obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
        }
        this.__equalsCalc = obj;
        boolean _equals = this.messages == null && other.getMessages() == null || this.messages != null && Arrays.equals(this.messages, other.getMessages());
        this.__equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getMessages() != null) {
            for (int i = 0; i < Array.getLength(this.getMessages()); ++i) {
                Object obj = Array.get(this.getMessages(), i);
                if (obj == null || obj.getClass().isArray()) continue;
                _hashCode += obj.hashCode();
            }
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }
}
