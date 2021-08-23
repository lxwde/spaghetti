package com.zpmc.ztos.infra.base.common.events;

import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;

import java.util.Date;

public class SimpleDiagnosticEvent extends AbstractDiagnosticEvent {
    public static IDiagnosticEvent createEvent(String inEventType) {
        return new SimpleDiagnosticEvent(inEventType, new Date());
    }

    private SimpleDiagnosticEvent(String inInEventType, Date inEventTime) {
        super(inInEventType, inEventTime);
    }


}
