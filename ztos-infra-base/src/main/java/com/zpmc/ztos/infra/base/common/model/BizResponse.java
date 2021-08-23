package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

public class BizResponse implements IMessageCollector, Serializable {
    private Serializable _returnValue;
    private Map _crudResults;
    private IQueryResult _queryResult;
    private final IMessageCollector _mc;
    private static final String INDENT_0 = "\n";
    private static final String INDENT_1 = "\n   ";
    private static final String INDENT_2 = "\n      ";
    private static final String INDENT_3 = "\n         ";
    private static final Logger LOGGER = Logger.getLogger(BizResponse.class);

    public BizResponse() {
        this._mc = MessageCollectorFactory.createMessageCollector();
    }

    public BizResponse(BizRequest inRequest) {
        this._mc = MessageCollectorFactory.createMessageCollector(inRequest.getErrorOverrides());
    }

    public void addCrudResult(CrudResult inResult) {
        if (this._crudResults == null) {
            this._crudResults = new HashMap();
        }
        this._crudResults.put(inResult.getCrudOperationKey(), inResult);
    }

    public CrudResult getCrudResult(Serializable inTupleKey) {
        CrudResult cr;
        if (this._crudResults == null) {
            this._crudResults = new HashMap();
        }
        if ((cr = (CrudResult)this._crudResults.get(inTupleKey)) == null) {
            cr = new CrudResult(inTupleKey);
            this.addCrudResult(cr);
        }
        return cr;
    }

    @Deprecated
    public Element getClassElement(String inEntityClass) {
        List vaoList = null;
        IQueryResult qr = this.getQueryResult();
        if (qr != null) {
            vaoList = qr.getRetrievedResults();
        }
        return this.formDataTableElement(inEntityClass, vaoList);
    }

    public int getRowCount(String inEntityClass) {
        List vaoList = this.getValueObjects(inEntityClass);
        return vaoList.size();
    }

    @Nullable
    public Serializable getCreatedPrimaryKey() {
        CrudResult cr = this.getCrudResult(null);
        if (cr == null) {
            return null;
        }
        return (Serializable)cr.getNewInstanceKey();
    }

    public IQueryResult getQueryResult() {
        return this._queryResult;
    }

    public void setQueryResult(IQueryResult inQueryResult) {
        if (this._queryResult != null) {
            throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__RESPONSE_OVERWRITE, null, inQueryResult);
        }
        this._queryResult = inQueryResult;
    }

    public void setResult(IValueHolder inValueObject) {
        ArrayList<IValueHolder> vaoList = new ArrayList<IValueHolder>(1);
        vaoList.add(inValueObject);
//        this.setQueryResult(new QueryResultImpl(vaoList));
    }

    @Nullable
    public ValueObject getValueObject(String inEntityClass) {
        int resultCount = 0;
        ValueObject result = null;
        IQueryResult qr = this.getQueryResult();
        if (qr != null) {
            List vaoList = qr.getRetrievedResults();
            for (Object vao : vaoList) {
                if (!org.apache.commons.lang.StringUtils.equals((String)((ValueObject)vao).getEntityClassName(), (String)inEntityClass)) continue;
                result = (ValueObject)vao;
                ++resultCount;
            }
        }
        switch (resultCount) {
            case 0: {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info((Object)("getValueObject: no VAO found for " + inEntityClass + ", returning null"));
                }
                return null;
            }
            case 1: {
                return result;
            }
        }
        LOGGER.error((Object)("getValueObject: more than one VAO exists for " + inEntityClass));
        return result;
    }

    @Deprecated
    public List getValueObjects(String inEntityClass) {
        IQueryResult qr = this.getQueryResult();
        if (qr == null || qr.getRetrievedResults() == null) {
            throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__NO_QUERY_RESULTS, null);
        }
        ArrayList<ValueObject> result = new ArrayList<ValueObject>();
        List allVaos = qr.getRetrievedResults();
        for (Object vao : allVaos) {
            if (!org.apache.commons.lang.StringUtils.equals((String)((ValueObject)vao).getEntityClassName(), (String)inEntityClass)) continue;
            result.add((ValueObject)vao);
        }
        return result;
    }

    @Deprecated
    public List getValueObjects() {
        IQueryResult qr = this.getQueryResult();
        if (qr == null || qr.getRetrievedResults() == null) {
            throw BizFailure.create(IFrameworkPropertyKeys.FRAMEWORK__NO_QUERY_RESULTS, null);
        }
        return new ArrayList(qr.getRetrievedResults());
    }

    public String getStatus() {
        if (this._mc.containsMessageLevel(MessageLevelEnum.SEVERE)) {
            return "SEVERE";
        }
        if (this._mc.containsMessageLevel(MessageLevelEnum.WARNING)) {
            return "WARNING";
        }
        if (this._mc.containsMessageLevel(MessageLevelEnum.INFO)) {
            return "INFO";
        }
        return "OK";
    }

    public String toString() {
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(INDENT_0).append("RESPONSE");
        try {
            strBuf.append(INDENT_1).append("status: ").append(this.getStatus());
            strBuf.append("  - ").append(this._mc);
            if (this._queryResult != null) {
                strBuf.append(INDENT_1).append("IQueryResult set #0");
                for (int i = this._queryResult.getFirstResult(); i <= this._queryResult.getLastResult(); ++i) {
                    IValueHolder vh = this._queryResult.getValueHolder(i);
                    strBuf.append(this.getDetailsForLog(vh).toString());
                    if (i != 10) continue;
                    strBuf.append(INDENT_2).append("   and ").append(this._queryResult.getLastResult() - 10).append(" more like this.");
                    break;
                }
                strBuf.append(INDENT_1).append("/IQueryResult");
            }
        }
        catch (Throwable t) {
            strBuf.append("\n toString failed with: " + t + INDENT_0 + CarinaUtils.getStackTrace(t));
            LOGGER.error((Object)("toString for BizResponse failed. \n" + strBuf));
        }
        strBuf.append(INDENT_0).append("RESPONSE");
        return strBuf.toString();
    }

    private StringBuffer getDetailsForLog(IValueHolder inValueHolder) {
        StringBuffer strBuf = new StringBuffer("\n      Values for key " + inValueHolder.getEntityPrimaryKey());
        TreeSet<IMetafieldId> set = new TreeSet<IMetafieldId>();
        MetafieldIdList fields = inValueHolder.getFields();
        Iterator<IMetafieldId> iterator = fields.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        for (IMetafieldId fieldId : set) {
            String listedValue;
            Object fieldValue = inValueHolder.getFieldValue(fieldId);
            strBuf.append("\n         <").append(fieldId).append("> = ");
            if (fieldValue instanceof Object[]) {
                String fieldStr = StringUtils.arrayToDelimitedString((Object[])((Object[])fieldValue), (String)",");
                listedValue = "[" + fieldStr + "]";
            } else {
                listedValue = fieldValue == null ? null : fieldValue.toString();
            }
            strBuf.append("'").append(listedValue).append("'");
        }
        return strBuf;
    }

    private Element formDataTableElement(String inEntityClass, List inTable) {
//        Element dataTableElem = new Element("data-table");
//        dataTableElem.setAttribute("class", inEntityClass);
//        if (inTable != null) {
//            for (Object vao : inTable) {
//                Element rowElem = this.valueObject2RowElem((ValueObject)vao);
//                dataTableElem.addContent((Content)rowElem);
//            }
//        }
//        return dataTableElem;
        return null;
    }

    private Element valueObject2RowElem(ValueObject inValueObject) {
//        Element rowElem = new Element("row");
//        Serializable entityPk = inValueObject.getEntityPrimaryKey();
//        if (entityPk != null) {
//            rowElem.setAttribute("primary-key", entityPk.toString());
//        }
//        Iterator<String> iterator = inValueObject.getFieldIterator();
//        while (iterator.hasNext()) {
//            String fieldId = iterator.next();
//            Object fieldValue = inValueObject.getFieldValue(fieldId);
//            Element fieldElem = new Element("field");
//            fieldElem.setAttribute("id", fieldId);
//            if (fieldValue != null) {
//                if (fieldValue instanceof Date) {
//                    String formattedDate = DateFormat.getDateTimeInstance().format((Date)fieldValue);
//                    fieldElem.setText(formattedDate);
//                } else {
//                    fieldElem.setText(fieldValue.toString());
//                }
//            }
//            rowElem.addContent((Content)fieldElem);
//        }
//        return rowElem;
          return null;
    }

    public boolean hasRequestFailed() {
        return this._mc.hasError();
    }

    @Override
    @NotNull
    public Collection<IUserMessage> getOverriddenErrors() {
        return this._mc.getOverriddenErrors();
    }

    @Override
    public Collection<IUserMessage> getOverrideableErrors() {
        return this._mc.getOverriddenErrors();
    }

    @Override
    @Nullable
    public IErrorOverrides getErrorOverrides() {
        return this._mc.getErrorOverrides();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this._mc.getAttributes();
    }

    @Override
    public void setAttributes(Map<String, Object> inMap) {
        this._mc.setAttributes(inMap);
    }

    @Override
    public Object getAttribute(String inKey) {
        return this._mc.getAttribute(inKey);
    }

    @Override
    public void setAttribute(String inKey, Object inValue) {
        this._mc.setAttribute(inKey, inValue);
    }

    @Override
    public void registerExceptions(Throwable inError) {
        this._mc.registerExceptions(inError);
    }

    @Override
    public void appendMessage(IUserMessage inUserMessage) {
        this._mc.appendMessage(inUserMessage);
    }

    @Override
    public void appendMessage(MessageLevelEnum inSeverity, IPropertyKey inKey, String inDefaultMessage, Object[] inParms) {
        this._mc.appendMessage(inSeverity, inKey, inDefaultMessage, inParms);
    }

    @Override
    public List getMessages(MessageLevelEnum inSeverity) {
        return this._mc.getMessages(inSeverity);
    }

    @Override
    public List getMessages() {
        return this._mc.getMessages();
    }

    @Override
    public int getMessageCount() {
        return this._mc.getMessageCount();
    }

    @Override
    public int getMessageCount(MessageLevelEnum inSeverity) {
        return this._mc.getMessageCount(inSeverity);
    }

    @Override
    public boolean containsMessage() {
        return this._mc.containsMessage();
    }

    @Override
    public boolean containsMessage(IPropertyKey inMessageKey) {
        return this._mc.containsMessage(inMessageKey);
    }

    @Override
    public boolean containsMessageLevel(MessageLevelEnum inSeverity) {
        return this._mc.containsMessageLevel(inSeverity);
    }

    @Override
    public boolean hasError() {
        return this._mc.hasError();
    }

    public IMessageCollector getMessageCollector() {
        return this._mc;
    }

    @Override
    public String toLoggableString(UserContext inUc) {
        return this._mc.toLoggableString(inUc);
    }

    @Override
    public String toCompactString() {
        return null;
    }

    public Serializable getReturnValue() {
        return this._returnValue;
    }

    public void setReturnValue(Serializable inReturnValue) {
        this._returnValue = inReturnValue;
    }

}
