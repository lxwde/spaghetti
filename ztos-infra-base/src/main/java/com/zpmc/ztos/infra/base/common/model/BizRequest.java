package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IDataQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IErrorOverrides;
import com.zpmc.ztos.infra.base.business.interfaces.IExecutableQuery;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;

import java.io.Serializable;
import java.util.*;

public class BizRequest implements Serializable {
    private UserContext _userContext;
    private String _origin;
    private Map _requestParms;
    private String _updateTarget;
    private String _updateAction;
    private List _crudOperations;
    private String _queryTarget;
    private String _queryAction;
    private boolean _requiresDatabase = true;
    private IExecutableQuery _executableQuery;
    private IErrorOverrides _errorOverrides;
    private static final Logger LOGGER = Logger.getLogger(BizRequest.class);

    @Deprecated
    public static IDomainQuery createDomainQuery(String inEntityName) {
        return QueryUtils.createDomainQuery(inEntityName);
    }

    public BizRequest(UserContext inUserContext) {
        this._userContext = inUserContext;
        this._origin = "presentation tier";
    }

    @Deprecated
    public BizRequest(String inOrigin) {
        this._origin = inOrigin;
    }

    public void setQueryTarget(String inSessionBean, String inBusinessMethod) {
        this._queryTarget = inSessionBean;
        this._queryAction = inBusinessMethod;
    }

    public void setApiTarget(String inApiName) {
        ApiMapping mapping = ApiMappings.getApiMapping(inApiName);
        if (mapping == null) {
            throw BizFailure.create("Attempt to execute BizRequest with an unknown API: " + inApiName);
        }
        if (mapping.isReadOnly()) {
            this.setQueryTarget(mapping.getHandlerClass(), mapping.getHandlerMethod());
        } else {
            this.setUpdateTarget(mapping.getHandlerClass(), mapping.getHandlerMethod());
        }
        if (!mapping.requiresDatabase()) {
            this.setRequiresDatabase(false);
        }
    }

    public void setUpdateTarget(String inSessionBean, String inBusinessMethod) {
        this._updateTarget = inSessionBean;
        this._updateAction = inBusinessMethod;
    }

    public String getQueryTarget() {
        return this._queryTarget;
    }

    public String getQueryAction() {
        return this._queryAction;
    }

    public String getUpdateTarget() {
        return this._updateTarget;
    }

    public String getUpdateAction() {
        return this._updateAction;
    }

    public String getOrigin() {
        return this._origin;
    }

    public void setOrigin(String inOrigin) {
        this._origin = inOrigin;
    }

    public Iterator getCrudOperationIterator() {
        if (this._crudOperations == null) {
            this._crudOperations = new ArrayList();
        }
        return this._crudOperations.iterator();
    }

    public void addCrudOperation(CrudOperation inCrudOperation) {
        if (this._crudOperations == null) {
            this._crudOperations = new ArrayList(1);
        }
        this._crudOperations.add(inCrudOperation);
    }

    public Map getParameters() {
        return this._requestParms;
    }

    public void addParameters(Map inParameters) {
        if (this._requestParms == null) {
            this._requestParms = new HashMap(inParameters.size());
        }
        this._requestParms.putAll(inParameters);
    }

    @Nullable
    public Serializable getParameterValue(String inParamName) {
        if (this._requestParms != null) {
            return (Serializable)this._requestParms.get(inParamName);
        }
        return null;
    }

    @Nullable
    public String getParameterStringValue(String inParamName) {
        Serializable parm = this.getParameterValue(inParamName);
        if (parm != null) {
            return parm.toString();
        }
        return null;
    }

    public void setParameter(String inParamName, Serializable inParamValue) {
        if (this._requestParms == null) {
            this._requestParms = new HashMap(5);
        }
        this._requestParms.put(inParamName, inParamValue);
    }

    private void ensureFinderQuery() {
        if (this._executableQuery == null) {
            this._executableQuery = new FinderQuery();
        }
    }

    public void setQueryFields(String[] inQueryFields) {
        this.ensureFinderQuery();
        if (this._executableQuery instanceof IDataQuery) {
            ((IDataQuery)this._executableQuery).addFields(inQueryFields);
        } else {
            LOGGER.error((Object)"Cannot set fields on non-IDataQuery.");
        }
    }

    @Deprecated
    @Nullable
    public String[] getQueryFields() {
        if (this._executableQuery == null) {
            return null;
        }
        MetafieldIdList ids = this._executableQuery.getMetafieldIds();
        return ids.asQualifiedStringArray();
    }

    public MetafieldIdList getQueryFieldIds() {
        if (this._executableQuery == null) {
            return new MetafieldIdList();
        }
        MetafieldIdList ids = this._executableQuery.getMetafieldIds();
        return ids;
    }

    @Deprecated
    public void setQueryPrimaryKey(Serializable inQueryPrimaryKey) {
        this.ensureFinderQuery();
        if (this._executableQuery instanceof FinderQuery) {
            ((FinderQuery)this._executableQuery).setQueryPrimaryKey(inQueryPrimaryKey);
        }
    }

    @Deprecated
    @Nullable
    public Serializable getQueryPrimaryKey() {
        Serializable result = null;
        result = this._executableQuery == null || !(this._executableQuery instanceof FinderQuery) ? null : ((FinderQuery)this._executableQuery).getQueryPrimaryKey();
        return result;
    }

    public IExecutableQuery getDataQuery() {
        return this._executableQuery;
    }

    public void setDataQuery(IExecutableQuery inDataQuery) {
        this._executableQuery = inDataQuery;
    }

    public UserContext getUserContext() {
        return this._userContext;
    }

    public void setUserContext(UserContext inUserContext) {
        this._userContext = inUserContext;
    }

    @Deprecated
    public UserContext getUserCredentials() {
        return this._userContext;
    }

    @Deprecated
    public void setUserCredentials(UserContext inUserContext) {
        this._userContext = inUserContext;
    }

    public boolean hasCrudOperations() {
        return this._updateTarget != null;
    }

    public boolean hasQueryOperations() {
        return this._queryTarget != null;
    }

    public String summaryString() {
        StringBuffer strBuf = new StringBuffer("action: ");
        if (this.hasCrudOperations()) {
            strBuf.append(this._updateTarget + "/" + this._updateAction);
        }
        if (this.hasQueryOperations()) {
            if (this.hasCrudOperations()) {
                strBuf.append("  &  ");
            }
            strBuf.append(this._queryTarget + "/" + this._queryAction);
        }
        if (this._executableQuery != null) {
            strBuf.append(" for " + this._executableQuery.getQueryEntityName());
        }
        if (this._userContext != null) {
            strBuf.append(", requestor = " + this._userContext.getUserId());
        }
        return strBuf.toString();
    }

    public boolean requiresDatabase() {
        return this._requiresDatabase;
    }

    public void setRequiresDatabase(boolean inRequiresDatabase) {
        this._requiresDatabase = inRequiresDatabase;
    }

    public String toString() {
        String indent1 = "\n   ";
        String indent2 = "\n      ";
        String indent3 = "\n         ";
        StringBuilder strBuf = new StringBuilder("\nREQUEST  target = ");
        try {
            String[] queryFields;
            if (this.hasCrudOperations()) {
                strBuf.append(this._updateTarget + "/" + this._updateAction);
            }
            if (this.hasQueryOperations()) {
                if (this.hasCrudOperations()) {
                    strBuf.append("  &  ");
                }
                strBuf.append(this._queryTarget + "/" + this._queryAction);
            }
            if (this._userContext != null) {
                strBuf.append(", requestor = " + this._userContext.getUserId());
            }
            if (this._requestParms != null) {
                strBuf.append("\n   parameters:");
                for (Object entry : this._requestParms.entrySet()) {
                    strBuf.append("\n         " + ((Map.Entry)entry).getKey() + " '" + ((Map.Entry)entry).getValue() + "'");
                }
            }
            if (this.getDataQuery() != null) {
                strBuf.append("\n   query:");
                strBuf.append("\n      " + this.getDataQuery().toString());
            }
            if ((queryFields = this.getQueryFields()) != null && queryFields.length > 0) {
                strBuf.append("\n   fields:");
                for (int i = 0; i < queryFields.length; ++i) {
                    strBuf.append("\n      " + queryFields[i]);
                }
            }
            if (this._crudOperations != null) {
                Iterator iterator = this._crudOperations.iterator();
                while (iterator.hasNext()) {
                    strBuf.append(iterator.next());
                }
            }
            strBuf.append("\n/REQUEST\n");
        }
        catch (Throwable t) {
            strBuf.append("\n toString failed with: " + t + "\n" + CarinaUtils.getStackTrace(t));
            LOGGER.error((Object)("toString in this BizRequest failed. \n" + strBuf));
        }
        return strBuf.toString();
    }

    @Nullable
    public IErrorOverrides getErrorOverrides() {
        return this._errorOverrides;
    }

    public void setErrorOverrides(IErrorOverrides inErrorOverrides) {
        this._errorOverrides = inErrorOverrides;
    }

}
