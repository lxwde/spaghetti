package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.DiagnosticEventKey;

import java.io.Serializable;
import java.util.List;

public interface IDiagnosticRequestManager extends IDetailedDiagnostics {

    public boolean isRecordingEnabled();

    public void setEnabled(boolean var1);

    public void recordEvent(IDiagnosticEvent var1);

    public boolean hasReachedMaximumRecords();

    public void setMaxEvents(Long var1);

    public Long getMaxEventsAllowed();

    public void clearEvents();

    public List<IDiagnosticEvent> getAllEvents();

    public List<IDiagnosticEvent> getAllEventsIncludingSubEvents();

    public List<IDiagnosticEvent> getAllEventsIncludingSubEvents(String var1);

    public List<IDiagnosticEvent> findTopEventsByKey(Serializable[] var1);

    public IDiagnosticEvent getLastRecordedEvent();

    public IDiagnosticEvent findEvent(DiagnosticEventKey var1);

    public void closeLastEvent();
}
