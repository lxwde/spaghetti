package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Date;
import java.util.List;

public interface IDiagnosticEvent extends IDetailedDiagnostics {

    public String getEventType();

    public Date getEventStartTime();

    public Long getDuration();

    public void setDuration(Long var1);

    public void endDuration();

    public String getDescription();

    public void setDescription(String var1);

    public void appendMessage(String var1);

    public void appendStackTrace(String var1);

    public List<String> getMessages();

    public int getEventSequence();

    public void setEventSequence(int var1);

    public IDiagnosticEvent getParent();

    public void setParent(IDiagnosticEvent var1);
}
