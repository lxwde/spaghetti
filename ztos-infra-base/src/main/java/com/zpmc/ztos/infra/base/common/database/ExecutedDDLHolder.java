package com.zpmc.ztos.infra.base.common.database;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IExecutedDDL;
import org.springframework.util.StringUtils;

import java.util.*;

public class ExecutedDDLHolder implements IExecutedDDL {
    List<String> _lastExecutedSql = new ArrayList<String>();
    Map<String, Throwable> _ddlExceptions = new HashMap<String, Throwable>();

    @Override
    public String getExecutedDDL() {
        return StringUtils.collectionToDelimitedString(this._lastExecutedSql, (String)"\n");
    }

    public void addDDL(String inDDL) {
        this._lastExecutedSql.add(inDDL);
    }

    public void addDDL(String[] inDDLs) {
        Collections.addAll(this._lastExecutedSql, inDDLs);
    }

    public void addException(String inDDL, Throwable inT) {
        this._ddlExceptions.put(inDDL, inT);
    }

    public void clear() {
        this._lastExecutedSql.clear();
        this._ddlExceptions.clear();
    }

    @Override
    @NotNull
    public Map<String, Throwable> getExceptions() {
        return this._ddlExceptions;
    }

    @Override
    public boolean hasExceptions() {
        return !this._ddlExceptions.isEmpty();
    }
}
