package com.zpmc.ztos.infra.base.common.events;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

public class AuditEvent {

    public static final int TASK_CREATE = 1;
    public static final int TASK_UPDATE = 2;
    public static final int TASK_DELETE = 3;
    public static final int TASK_QUERY = 4;
    public static final int TASK_EVENT = 5;
    public static final int ALERT_NONE = 0;
    public static final int ALERT_DEBUG = 1;
    public static final int ALERT_WARN = 2;
    public static final int ALERT_ERROR = 3;
    private String _userId;
    private final Date _eventTime;
    private int _task;
    private String _action;
    private int _alert;
    private String _message;
    private String _state;
    private IEntity _entity;
    private Serializable _entityPKey;
    private String _entityHKey;
    private final FieldChanges _fieldChanges;

    public AuditEvent() {
        TransactionParms parms = (TransactionParms) TransactionSynchronizationManager.getResource(TransactionParms.class);
        String userId = parms != null ? parms.getUserId() : "*unknown*";
        String businessActionId = parms != null ? parms.getBusinessActionId() : "*unknown*";
        this._eventTime = new Date();
        this._userId = userId;
        this._task = 5;
        this._action = businessActionId;
        this._fieldChanges = null;
        this._alert = 0;
    }

    public AuditEvent(Date inEventTime, String inUserId, int inTask, String inAction, IEntity inEntity, FieldChanges inFieldChanges) {
        this._eventTime = inEventTime;
        this._userId = inUserId;
        this._task = inTask;
        this._action = inAction;
        this._entity = inEntity;
        this._entityPKey = inEntity.getPrimaryKey();
        this._entityHKey = inEntity.getHumanReadableKey();
        this._fieldChanges = inFieldChanges;
        this._alert = 0;
        this._message = null;
        this.formStateString();
    }

    private void formStateString() {
        if (this._fieldChanges == null) {
            return;
        }
        StringBuilder buf = new StringBuilder();
        Iterator<FieldChange> iterator = this._fieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fieldChange = iterator.next();
            String fieldId = fieldChange.getFieldId();
            Object fieldValue = fieldChange.getNewValue();
            Object priorFieldValue = fieldChange.getPriorValue();
            buf.append(fieldId).append(":(").append(priorFieldValue).append("=>").append(fieldValue).append("); ");
        }
        this.setState(buf.toString());
    }

    public String getUserId() {
        return this._userId;
    }

    public void setUserId(String inUserId) {
        this._userId = inUserId;
    }

    public int getTask() {
        return this._task;
    }

    public void setTask(int inTask) {
        this._task = inTask;
    }

    public String getAction() {
        return this._action;
    }

    public void setAction(String inAction) {
        this._action = inAction;
    }

    public String getMessage() {
        return this._message;
    }

    public void setMessage(String inMessage) {
        this._message = inMessage;
    }

    public IEntity getEntity() {
        return this._entity;
    }

    public void setEntity(IEntity inEntity) {
        this._entity = inEntity;
        this._entityPKey = inEntity.getPrimaryKey();
        this._entityHKey = inEntity.getHumanReadableKey();
    }

    public Serializable getEntityPKey() {
        return this._entityPKey;
    }

    public int getAlert() {
        return this._alert;
    }

    public void setAlert(int inAlert) {
        this._alert = inAlert;
    }

    public String getState() {
        return this._state;
    }

    public void setState(String inState) {
        this._state = inState;
    }

    public String getEntityHKey() {
        return this._entityHKey;
    }

    public FieldChanges getFieldChanges() {
        return this._fieldChanges;
    }

    @Nullable
    public String getEntityPKeyString() {
        return this._entityPKey == null ? null : this._entityPKey.toString();
    }

    public Date getEventTime() {
        return this._eventTime;
    }

    public String getTaskString() {
        switch (this.getTask()) {
            case 1: {
                return "CREATE";
            }
            case 2: {
                return "UPDATE";
            }
            case 3: {
                return "DELETE";
            }
            case 4: {
                return "QUERY";
            }
            case 5: {
                return "EVENT";
            }
        }
        return "OTHER";
    }

    @NotNull
    public static String getTaskString(int inTask) {
        switch (inTask) {
            case 1: {
                return "CREATE";
            }
            case 2: {
                return "UPDATE";
            }
            case 3: {
                return "DELETE";
            }
            case 4: {
                return "QUERY";
            }
            case 5: {
                return "EVENT";
            }
        }
        return "UNKNOWN";
    }

    @Nullable
    public String getAlertString() {
        switch (this.getAlert()) {
            case 1: {
                return "DEBUG";
            }
            case 2: {
                return "WARN";
            }
            case 3: {
                return "ERROR";
            }
        }
        return null;
    }

    @Deprecated
    public String[] getFieldIds() {
        int i = 0;
        String[] ids = new String[this._fieldChanges.getFieldChangeCount()];
        Iterator<FieldChange> iterator = this._fieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            ids[i++] = fc.getFieldId();
        }
        return ids;
    }

    @Deprecated
    public Object[] getPriorFieldValues() {
        int i = 0;
        Object[] pvs = new Object[this._fieldChanges.getFieldChangeCount()];
        Iterator<FieldChange> iterator = this._fieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            pvs[i++] = fc.getPriorValue();
        }
        return pvs;
    }

    @Deprecated
    public Object[] getFieldValues() {
        int i = 0;
        Object[] nvs = new Object[this._fieldChanges.getFieldChangeCount()];
        Iterator<FieldChange> iterator = this._fieldChanges.getIterator();
        while (iterator.hasNext()) {
            FieldChange fc = iterator.next();
            nvs[i++] = fc.getNewValue();
        }
        return nvs;
    }
}
