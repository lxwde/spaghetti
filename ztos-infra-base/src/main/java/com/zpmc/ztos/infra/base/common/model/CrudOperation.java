package com.zpmc.ztos.infra.base.common.model;

import java.io.Serializable;
import java.util.Iterator;

public class CrudOperation implements Serializable {

    public static final int TASK_INSERT = 1;
    public static final int TASK_UPDATE = 2;
    public static final int TASK_DELETE = 3;
    public static final int TASK_INSERT_OR_UPDATE = 4;
    public static final int TASK_POPULATE = 5;
    public static final int TASK_BATCH_UPDATE = 6;
    public static final int TASK_BATCH_DELETE = 7;
    private Object _key;
    private int _task;
    private String _className;
    private FieldChanges _fieldChanges;
    private Object[] _primaryKeys;
    private static final String INDENT1 = "\n   ";
    private static final String INDENT2 = "\n      ";
    private static final String INDENT3 = "\n         ";

    public CrudOperation() {
    }

    public CrudOperation(Object inKey, int inTask, String inClassName, FieldChanges inFieldChanges, Object[] inPrimaryKeys) {
        this._key = inKey;
        this._task = inTask;
        this._className = inClassName;
        this._fieldChanges = inFieldChanges;
        this._primaryKeys = inPrimaryKeys;
    }

    public String toString() {
        StringBuilder strBuf = new StringBuilder(INDENT1 + this.task2LogString(this._task) + " class = '" + this._className + "'");
        if (this._primaryKeys != null) {
            strBuf.append("\n      keys:");
            for (int i = 0; i < this._primaryKeys.length; ++i) {
                strBuf.append("\n         '").append(this._primaryKeys[i]).append("'");
            }
        }
        if (this._fieldChanges != null) {
            strBuf.append("\n      fields:");
            Iterator<FieldChange> iterator = this._fieldChanges.getIterator();
            while (iterator.hasNext()) {
                FieldChange fc = iterator.next();
                strBuf.append(INDENT3).append(fc);
            }
        }
        return strBuf.toString();
    }

    public Object getKey() {
        return this._key;
    }

    public void setKey(Object inKey) {
        this._key = inKey;
    }

    public int getTask() {
        return this._task;
    }

    public void setTask(int inTask) {
        this._task = inTask;
    }

    public String getClassName() {
        return this._className;
    }

    public void setClassName(String inClassName) {
        this._className = inClassName;
    }

    public FieldChanges getFieldChanges() {
        return this._fieldChanges;
    }

    public void setFieldChanges(FieldChanges inFieldChanges) {
        this._fieldChanges = inFieldChanges;
    }

    public Object[] getPrimaryKeys() {
        return this._primaryKeys;
    }

    public void setPrimaryKeys(Object[] inPrimaryKeys) {
        this._primaryKeys = inPrimaryKeys;
    }

    private String task2LogString(int inTask) {
        switch (inTask) {
            case 3: {
                return "DELETE";
            }
            case 2: {
                return "UPDATE";
            }
            case 1: {
                return "INSERT";
            }
            case 4: {
                return "INSERT/UPDATE";
            }
            case 5: {
                return "POPULATE";
            }
            case 6: {
                return "BATCH UPDATE";
            }
            case 7: {
                return "BATCH DELETE";
            }
        }
        return "??????";
    }
}
