package com.zpmc.ztos.infra.base.common.database;

import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticRequestManager;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticRequestManagerAware;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticRequestSubEventManager;
import com.zpmc.ztos.infra.base.common.events.AbstractDiagnosticEvent;

import java.util.Date;

public class HierarchicalDiagnosticEvent extends AbstractDiagnosticEvent implements IDiagnosticRequestManagerAware {

    IDiagnosticRequestSubEventManager _diagnosticRequestManager;

    public static HierarchicalDiagnosticEvent createEvent(IDiagnosticRequestManager inParent, String inEventType) {
//        DiagnosticSubEventHolder diagnosticRequestManager = new DiagnosticSubEventHolder();
//        HierarchicalDiagnosticEvent diagnosticEvent = new HierarchicalDiagnosticEvent(inEventType, new Date());
//        diagnosticRequestManager.setEnabled(inParent.isRecordingEnabled());
//        diagnosticEvent.setDiagnosticRequestManager(diagnosticRequestManager);
//        diagnosticRequestManager.setRootEvent(diagnosticEvent);
//        return diagnosticEvent;
        return null;
    }

    private HierarchicalDiagnosticEvent(String inInEventType, Date inEventTime) {
        super(inInEventType, inEventTime);
    }

    @Override
    public IDiagnosticRequestSubEventManager getDiagnosticRequestManager() {
        return this._diagnosticRequestManager;
    }

    public void setDiagnosticRequestManager(IDiagnosticRequestSubEventManager inDiagnosticRequestManager) {
        this._diagnosticRequestManager = inDiagnosticRequestManager;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getDetailedDiagnostics() {
        return super.getDetailedDiagnostics().replace("\n", "\n             ") + this._diagnosticRequestManager.getDetailedDiagnostics().replace("\n", "\n             ");
    }

}
