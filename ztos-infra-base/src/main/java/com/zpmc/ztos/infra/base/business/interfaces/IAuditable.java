package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.events.AuditEvent;

public interface IAuditable {

    public AuditEvent vetAuditEvent(AuditEvent var1);
}
