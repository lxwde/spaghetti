package com.zpmc.ztos.infra.base.common.impls;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.AbstractDataQuery;
import com.zpmc.ztos.infra.base.business.model.Conjunction;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.predicate.*;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.model.KeyValuePair;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.type.Type;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class DomainQueryImpl extends AbstractDataQuery implements IDomainQuery {
    private List _predicates = new ArrayList();
    private List _aggregatedFields = new ArrayList();
    private List _joins = new ArrayList();
    private boolean _isDistinct;
    private int _selectTimeOutSecs;
    private boolean _isSelectForUpdate;
    private boolean _isFullLeftOuterJoin;
    private boolean _isFormatResultsForUI;
    private IQueryFilter _filter;
    private ISecurityFilter _securityFilter;
    private boolean _scopingEnabled = true;
    private boolean _isBypassInstanceSecurity;
    private boolean _isEnforceHorizonScoping;
    private IDomainQueryTuner _tuner;
    private boolean _isDistinctReplacement;
    private String _devComment;
    private static final Logger LOGGER = Logger.getLogger(DomainQueryImpl.class);

    public DomainQueryImpl(String inEntityName) {
        if (StringUtils.isEmpty((String)inEntityName)) {
            throw new IllegalArgumentException("DomainQueryImpl constructor called with null entityName");
        }
        this.setQueryEntityName(inEntityName);
    }

    @Override
    @Deprecated
    @Nullable
    public List getMetafields() {
        return this.getDefaultDisplayFields();
    }

    @Override
    @Deprecated
    public void replaceFields(List inFields) {
        this._metafieldIds = new MetafieldIdList(inFields);
    }

    @Override
    @Deprecated
    public void replaceSelectedFields(MetafieldIdList inFields) {
        this.setMetafieldIds(inFields);
    }

    @Override
    public String toHqlObjectQueryString(String inEntityAlias) {
        StringBuilder buffer = new StringBuilder();
        this.appendHqlFrom(buffer, inEntityAlias);
        this.appendHqlWhere(buffer, inEntityAlias);
        this.appendHqlOrderBy(buffer, inEntityAlias);
        return buffer.toString();
    }

    @Override
    public String toHqlCountString(String inEntityAlias) {
        StringBuilder buffer = new StringBuilder();
        if (this._isDistinct) {
            buffer.append("select count(distinct ");
            buffer.append(inEntityAlias);
            buffer.append('.');
            buffer.append(this._metafieldIds.get(0));
            buffer.append(')');
        } else {
            buffer.append("select count(*)");
        }
        buffer.append(' ');
        this.appendHqlFrom(buffer, inEntityAlias);
        this.appendHqlWhere(buffer, inEntityAlias);
        return buffer.toString();
    }

    @Override
    public String toHqlExistsString(String inEntityAlias) {
        StringBuilder builder = new StringBuilder();
        String selectCol = null;
        selectCol = this.getFieldValues().length != 0 ? inEntityAlias + "." + this.getFieldValues()[0].getKey() : "*";
        builder.append("select cd.dummy from CarinaDual as cd where exists (select ").append(selectCol);
        builder.append(' ');
        this.appendHqlFrom(builder, inEntityAlias);
        this.appendHqlWhere(builder, inEntityAlias);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String toHqlRecapString(String inEntityAlias) {
        StringBuilder builder = new StringBuilder();
        this.appendHqlSelect(builder, inEntityAlias);
        builder.append(", count(*) ");
        this.appendHqlFrom(builder, inEntityAlias);
        this.appendHqlWhere(builder, inEntityAlias);
        builder.append(" group by ");
        Iterator<IMetafieldId> iterator = this._metafieldIds.iterator();
        while (iterator.hasNext()) {
            IMetafieldId fieldId = iterator.next();
            builder.append(FieldUtils.getAliasedField(inEntityAlias, fieldId));
            if (!iterator.hasNext()) continue;
            builder.append(", ");
        }
        this.appendHqlOrderBy(builder, inEntityAlias);
        return builder.toString();
    }

    @Override
    public String toHqlRecapSumString(String inEntityAlias) {
        if (this._metafieldIds.getSize() < 3) {
            LOGGER.error((Object)"Not enough metafields for recap sum");
            return "";
        }
        IMetafieldId sumField = this._metafieldIds.get(this._metafieldIds.getSize() - 1);
        this._metafieldIds.remove(sumField);
        StringBuilder builder = new StringBuilder();
        this.appendHqlSelect(builder, inEntityAlias);
        this._metafieldIds.add(sumField);
        builder.append(", sum(" + FieldUtils.getAliasedField(inEntityAlias, sumField) + ") ");
        this.appendHqlFrom(builder, inEntityAlias);
        this.appendHqlWhere(builder, inEntityAlias);
        builder.append(" group by ");
        for (int i = 0; i < this._metafieldIds.getSize() - 1; ++i) {
            IMetafieldId fieldId = this._metafieldIds.get(i);
            builder.append(FieldUtils.getAliasedField(inEntityAlias, fieldId));
            if (i >= this._metafieldIds.getSize() - 2) continue;
            builder.append(", ");
        }
        return builder.toString();
    }

    @Override
    public String toHqlSelectString(String inEntityAlias) {
        StringBuilder buffer = new StringBuilder();
        this.appendHqlSelect(buffer, inEntityAlias);
        return buffer.toString();
    }

    @Override
    public boolean hasAggregateField() {
        return !this._aggregatedFields.isEmpty();
    }

    private void appendAggregatedHqlSelect(StringBuilder inOutBuffer, String inEntityAlias) {
        Iterator iterator = this._aggregatedFields.iterator();
        while (iterator.hasNext()) {
            AggregateField agfield = (AggregateField)iterator.next();
            inOutBuffer.append(agfield._function.toString()).append('(');
            inOutBuffer.append(inEntityAlias);
            inOutBuffer.append('.');
            inOutBuffer.append(agfield._fieldId);
            inOutBuffer.append(") ");
            if (!iterator.hasNext()) continue;
            inOutBuffer.append(", ");
        }
    }

    private void appendHqlSelect(StringBuilder inOutBuffer, String inEntityAlias) {
        if (this._metafieldIds.getSize() > 0) {
            inOutBuffer.append("select ");
            if (this._isDistinct) {
                inOutBuffer.append("distinct ");
            }
            if (this.hasAggregateField()) {
                this.appendAggregatedHqlSelect(inOutBuffer, inEntityAlias);
            } else {
                Iterator<IMetafieldId> iterator = this._metafieldIds.iterator();
                while (iterator.hasNext()) {
                    IMetafieldId fieldId = iterator.next();
                    inOutBuffer.append(FieldUtils.getAliasedField(inEntityAlias, fieldId));
                    if (!iterator.hasNext()) continue;
                    inOutBuffer.append(", ");
                }
            }
            inOutBuffer.append(" ");
        }
    }

    private void appendHqlFrom(StringBuilder inOutBuffer, String inEntityAlias) {
        inOutBuffer.append("from ").append(this.getQueryEntityName()).append(" as ").append(inEntityAlias).append(" ");
        this.appendJoins(inOutBuffer, inEntityAlias);
    }

    private void appendJoins(StringBuilder inOutBuffer, String inEntityAlias) {
        HashSet joins = new HashSet();
        if (this._isFullLeftOuterJoin) {
            for (IMetafieldId fieldId : this._metafieldIds) {
                this.formJoinClauseForField(inEntityAlias, fieldId, joins, inOutBuffer);
            }
            for (Object predicate : this._predicates) {
                this.formJoinClauseForPredicate((IPredicate)predicate, inEntityAlias, joins, inOutBuffer);
            }
            for (Object ordering : this._orderings) {
                if (this._metafieldIds.contains(((Ordering)ordering).getFieldId())) continue;
                this.formJoinClauseForField(inEntityAlias, ((Ordering)ordering).getFieldId(), joins, inOutBuffer);
            }
            inOutBuffer.append(" ");
        }
//        if (!this._joins.isEmpty()) {
//            for (Join join : this._joins) {
//                JoinType joinType;
//                if (this._isFullLeftOuterJoin && (joinType = join.getJoinType()) == JoinType.LEFT_OUTER_JOIN && joins.contains(join.getJoinField().getQualifiedId())) continue;
//                inOutBuffer.append(" ").append(join.getHql(inEntityAlias)).append(" ");
//            }
//        }
    }

    private void formJoinClauseForPredicate(IPredicate inPredicate, String inEntityAlias, Set inOutJoins, StringBuilder inOutBuffer) {
        if (inPredicate instanceof AbstractFieldPredicate) {
            AbstractFieldPredicate fp = (AbstractFieldPredicate)inPredicate;
            this.formJoinClauseForField(inEntityAlias, fp.getFieldId(), inOutJoins, inOutBuffer);
        } else if (inPredicate instanceof Junction) {
            Junction junction = (Junction)inPredicate;
            Iterator iterator = junction.getPredicateIterator();
            while (iterator.hasNext()) {
                IPredicate predicate = (IPredicate)iterator.next();
                this.formJoinClauseForPredicate(predicate, inEntityAlias, inOutJoins, inOutBuffer);
            }
        } else if (inPredicate instanceof NotPredicate) {
            this.formJoinClauseForPredicate(((NotPredicate)inPredicate).getUnnegatedPredicate(), inEntityAlias, inOutJoins, inOutBuffer);
        }
    }

    private void formJoinClauseForField(String inEntityAlias, IMetafieldId inFieldId, Set inOutJoins, StringBuilder inOutBuffer) {
        IMetafieldId entityMfid;
        StringBuilder pathToEntityBuilder = new StringBuilder(inEntityAlias);
        if (inFieldId.isEntityAware()) {
            return;
        }
        while ((entityMfid = inFieldId.getQualifyingMetafieldId()) != null) {
            pathToEntityBuilder.append(".").append(entityMfid.getFieldId());
            Type fieldType = HiberCache.getFieldType(entityMfid.getFieldId());
            if (fieldType != null && fieldType.isEntityType() && !inOutJoins.contains(pathToEntityBuilder.toString())) {
                inOutBuffer.append(" left join ").append(pathToEntityBuilder.toString());
                inOutJoins.add(pathToEntityBuilder.toString());
            }
            inFieldId = inFieldId.getQualifiedMetafieldId();
        }
    }

    private void appendHqlWhere(StringBuilder inOutBuffer, String inEntityAlias) {
        StringBuilder whereBuf = new StringBuilder();
        Iterator iterator = this._predicates.iterator();
        while (iterator.hasNext()) {
            whereBuf.append(((IPredicate)iterator.next()).toHqlString(inEntityAlias));
            if (!iterator.hasNext()) continue;
            whereBuf.append(" and ");
        }
        if (whereBuf.length() != 0) {
            inOutBuffer.append("where ").append(whereBuf.toString()).append(" ");
        }
    }

    private void appendHqlOrderBy(StringBuilder inOutBuffer, String inEntityAlias) {
        if (!this._orderings.isEmpty()) {
            inOutBuffer.append(" order by ");
            Iterator iterator = this._orderings.iterator();
            while (iterator.hasNext()) {
                Ordering ordering = (Ordering)iterator.next();
                inOutBuffer.append(ordering.toHqlString(inEntityAlias));
                if (!iterator.hasNext()) continue;
                inOutBuffer.append(", ");
            }
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder().append("from ").append(this.getQueryEntityName());
        try {
            StringBuilder whereBuf = new StringBuilder();
            Iterator iterator = this._predicates.iterator();
            while (iterator.hasNext()) {
                whereBuf.append(iterator.next().toString());
                if (!iterator.hasNext()) continue;
                whereBuf.append(" and ");
            }
            if (whereBuf.length() != 0) {
                buffer.append(" where ");
                buffer.append(whereBuf.toString());
            }
            if (!this._orderings.isEmpty()) {
                buffer.append(" order by ");
                iterator = this._orderings.iterator();
                while (iterator.hasNext()) {
                    Ordering ordering = (Ordering)iterator.next();
                    buffer.append(ordering.toString());
                    if (!iterator.hasNext()) continue;
                    buffer.append(", ");
                }
            }
        }
        catch (Exception e) {
            buffer.append(e);
        }
        return buffer.toString();
    }

    @Override
    public IDomainQuery setDqMaxResults(int inMaxResults) {
        this.setMaxResults(inMaxResults);
        return this;
    }

    @Override
    public IDomainQuery setDqFirstResult(int inFirstResult) {
        this.setFirstResult(inFirstResult);
        return this;
    }

  
    @Override
    public IDomainQuery addDqOrdering(Ordering inOrdering) {
        this._orderings.add(inOrdering);
        return this;
    }

    @Override
    public IDomainQuery addDqOrderings(Ordering[] inOrderings) {
        if (inOrderings != null) {
            for (int i = 0; i < inOrderings.length; ++i) {
                this.addDqOrdering(inOrderings[i]);
            }
        }
        return this;
    }

    public IDomainQuery removeOrderings() {
        this._orderings.clear();
        return this;
    }

    @Override
    public IDomainQuery addDqPredicate(IPredicate inCriterion) {
        this._predicates.add(0, inCriterion);
        return this;
    }

    @Override
    public IDomainQuery addDqField(String inFieldId) {
        IMetafieldId metafieldId = MetafieldIdFactory.valueOf(inFieldId);
        if (!this._metafieldIds.contains(metafieldId)) {
            this._metafieldIds.add(metafieldId);
        }
        return this;
    }

    @Override
    public IDomainQuery addDqField(IMetafieldId inFieldId) {
        if (!this._metafieldIds.contains(inFieldId)) {
            this._metafieldIds.add(inFieldId);
        }
        return this;
    }

    @Override
    public IDomainQuery addDqFields(String[] inFieldIds) {
        if (inFieldIds != null) {
            for (int i = 0; i < inFieldIds.length; ++i) {
                this.addDqField(inFieldIds[i]);
            }
        }
        return this;
    }

    @Override
    public IDomainQuery addDqFields(List inFieldIds) {
        if (inFieldIds != null) {
            Iterator iterator = inFieldIds.iterator();
            while (iterator.hasNext()) {
                this.addDqField((IMetafieldId)iterator.next());
            }
        }
        return this;
    }

    @Override
    public IDomainQuery addDqFields(MetafieldIdList inFieldIds) {
        if (inFieldIds != null) {
            for (int i = 0; i < inFieldIds.getSize(); ++i) {
                this.addDqField(inFieldIds.get(i));
            }
        }
        return this;
    }

    @Override
    public IDomainQuery setDqFields(MetafieldIdList inFieldIds) {
        this._metafieldIds = new MetafieldIdList();
        this.addDqFields(inFieldIds);
        return this;
    }

    @Override
    public IDomainQuery clearDqFields() {
        this._metafieldIds = new MetafieldIdList();
        return this;
    }

    @Override
    public IDomainQuery addDqAggregateField(AggregateFunctionType inFunctionType, String inFieldId) {
        IMetafieldId metafieldId = MetafieldIdFactory.valueOf(inFieldId);
        if (this._metafieldIds.contains(metafieldId)) {
            LOGGER.error((Object)"Carina currently does not support multiple aggregate operations on the same field");
        } else {
            this.addDqField(inFieldId);
            this._aggregatedFields.add(new AggregateField(inFunctionType, inFieldId));
        }
        return this;
    }

    @Override
    public IDomainQuery addDqAggregateField(AggregateFunctionType inFunctionType, IMetafieldId inFieldId) {
        return this.addDqAggregateField(inFunctionType, inFieldId.getQualifiedId());
    }


    private boolean isFieldInDq(String inFieldId) {
        IMetafieldId metafieldId = MetafieldIdFactory.valueOf(inFieldId);
        return this._metafieldIds.contains(metafieldId);
    }

    @Override
    public IDomainQuery addDqJoin(Join inJoin) {
        this._joins.add(inJoin);
        return this;
    }

    @Override
    public IDomainQuery addDqLeftOuterJoin(IMetafieldId inFieldId) {
        if (!this.isFieldInDq(inFieldId.getQualifiedId())) {
            this.addDqField(inFieldId);
        }
        this._joins.add(PredicateFactory.createJoin(JoinTypeEnum.LEFT_OUTER_JOIN, inFieldId, null));
        return this;
    }

    @Override
    @Deprecated
    public IDomainQuery addDqLeftOuterJoin(String inFieldId) {
        return this.addDqLeftOuterJoin(MetafieldIdFactory.valueOf(inFieldId));
    }

    @Override
    public IDomainQuery setDqFieldsDistinct() {
        return this.setDqFieldsDistinct(true);
    }

    @Override
    public IDomainQuery setDqFieldsDistinct(boolean inIsDqFieldsDistinct) {
        this._isDistinct = inIsDqFieldsDistinct;
        return this;
    }

    @Override
    public boolean areFieldsDistinct() {
        return this._isDistinct;
    }

    @Override
    public IDomainQuery setFullLeftOuterJoin(boolean inFullLeftOuterJoin) {
        this._isFullLeftOuterJoin = inFullLeftOuterJoin;
        return this;
    }

    @Override
    public IDomainQuery setFormatResultsForUI(boolean inFormatResultsForUI) {
        this._isFormatResultsForUI = inFormatResultsForUI;
        return this;
    }

    @Override
    public boolean isFormatResultsForUI() {
        return this._isFormatResultsForUI;
    }

    @Override
    public KeyValuePair[] getFieldValues() {
        ArrayList<KeyValuePair> typedValues = new ArrayList<KeyValuePair>();
        Iterator iter = this._predicates.iterator();
        while (iter.hasNext()) {
            KeyValuePair[] subvalues = ((IPredicate)iter.next()).getFieldValues();
            typedValues.addAll(Arrays.asList(subvalues));
        }
        return typedValues.toArray(new KeyValuePair[typedValues.size()]);
    }

    public int getTotalResultCount() {
        return -1;
    }

    @Override
    public void setOrderings(Ordering[] inOrderings) {
        this.removeOrderings();
        this.addDqOrderings(inOrderings);
    }

    @Override
    public void addFields(String[] inFieldIds) {
        this.addDqFields(inFieldIds);
    }

    @Override
    public IDataQuery createEnhanceableClone() {
        DomainQueryImpl clone = (DomainQueryImpl)this.createEnhanceableCloneWithoutPredicates();
        clone._predicates = new ArrayList(this._predicates);
        return clone;
    }

    @Override
    public IDataQuery createEnhanceableCloneWithoutPredicates() {
        DomainQueryImpl clone = new DomainQueryImpl(this.getQueryEntityName());
        this.copyDataQuery(clone);
        clone._aggregatedFields = new ArrayList(this._aggregatedFields);
        clone._joins = new ArrayList(this._joins);
        clone._isDistinct = this._isDistinct;
        clone._isSelectForUpdate = this._isSelectForUpdate;
        clone._selectTimeOutSecs = this._selectTimeOutSecs;
        clone._isFullLeftOuterJoin = this._isFullLeftOuterJoin;
        clone._isFormatResultsForUI = this._isFormatResultsForUI;
        clone._filter = this._filter;
 //       clone._tuner = this._tuner;
        clone._devComment = this._devComment;
        clone._isBypassInstanceSecurity = this._isBypassInstanceSecurity;
        clone._isEnforceHorizonScoping = this._isEnforceHorizonScoping;
        clone._scopingEnabled = this._scopingEnabled;
        clone._isDistinctReplacement = this._isDistinctReplacement;
        return clone;
    }

//    @Override
//    @Nullable
//    public QueryCriteria getQueryCriteria() {
//        return null;
//    }

    @Override
    public boolean isSelectForUpdate() {
        return this._isSelectForUpdate;
    }

    @Override
    public IDomainQuery setSelectForUpdate(boolean inSelectForUpdate) {
        return this.setSelectForUpdate(inSelectForUpdate, 0);
    }

    @Override
    public IDomainQuery setSelectForUpdate(boolean inSelectForUpdate, int inSelectTimeOutSecs) {
        if (!inSelectForUpdate && inSelectTimeOutSecs != 0 || inSelectForUpdate && inSelectTimeOutSecs < 0) {
            throw new IllegalArgumentException("Values for 'inSelectForUpdate:' " + inSelectForUpdate + " and 'inSelectTimeOutSecs: " + inSelectTimeOutSecs + " are not compatible.");
        }
        this._isSelectForUpdate = inSelectForUpdate;
        this._selectTimeOutSecs = inSelectTimeOutSecs;
        return this;
    }

    @Override
    public int getSelectTimeOutSecs() {
        return this._selectTimeOutSecs;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        for (Object predicate : this._predicates) {
            if (((IMetaFilter)predicate).isSatisfiedBy(inValueSource)) continue;
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public IPredicate getPredicatesAsSinglePredicate() {
        if (this._predicates.isEmpty()) {
            return null;
        }
        if (this._predicates.size() == 1) {
            return (IPredicate)this._predicates.get(0);
        }
        Conjunction conjunction = PredicateFactoryBase.conjunction();
        for (Object predicate : this._predicates) {
            conjunction.add((IPredicate)predicate);
        }
        return conjunction;
    }

    @Override
    public IQueryFilter getFilter() {
        return this._filter;
    }

    @Override
    public void setFilter(IQueryFilter inFilter) {
        this._filter = inFilter;
    }

    @Override
    public boolean getScopingEnabled() {
        return this._scopingEnabled;
    }

    @Override
    public void setScopingEnabled(boolean inEnabled) {
        this._scopingEnabled = inEnabled;
    }

    @Override
    public ISecurityFilter getSecurityFilter() {
        return this._securityFilter;
    }

    @Override
    public void setSecurityFilter(ISecurityFilter inSecurityFilter) {
        this._securityFilter = inSecurityFilter;
    }

    @Override
    public boolean isBypassInstanceSecurity() {
        return this._isBypassInstanceSecurity;
    }

    @Override
    public void setBypassInstanceSecurity(boolean inBypassInstanceSecurity) {
        this._isBypassInstanceSecurity = inBypassInstanceSecurity;
    }

    @Override
    public boolean isEnforceHorizonScoping() {
        return this._isEnforceHorizonScoping;
    }

    @Override
    public void setEnforceHorizonScoping(boolean inEnforceHorizonScoping) {
        this._isEnforceHorizonScoping = inEnforceHorizonScoping;
    }

    @Override
    public int getDqPredicatesSize() {
        return this._predicates.size();
    }

    @Override
    public void setDomainQueryTuner(IDomainQueryTuner inTuner) {
        this._tuner = inTuner;
    }

    @Override
    public IDomainQueryTuner getDomainQueryTuner() {
        return this._tuner;
    }

    @Override
    public boolean isDistinctReplacement() {
        return this._isDistinctReplacement;
    }

    @Override
    public void setDistinctReplacement(boolean inIsDistinctReplacement) {
        this._isDistinctReplacement = inIsDistinctReplacement;
    }

    @Override
    @Nullable
    public String getDevComment() {
        if (this._devComment == null) {
            return null;
        }
        if (this._devComment.length() > 100) {
            this._devComment = this._devComment.substring(0, 100);
        }
        return this._devComment + " -Dev";
    }

    @Override
    public void appendDevComment(@NotNull String inComment) {
        this._devComment = this._devComment == null ? " Dev- " + inComment : this._devComment + ", " + inComment;
    }

    @Override
    public IQueryCriteria getQueryCriteria() {
        return null;
    }

    private class AggregateField
            implements Serializable {
        private final AggregateFunctionType _function;
        private final String _fieldId;

        AggregateField(AggregateFunctionType inFunction, String inInFieldId) {
            this._function = inFunction;
            this._fieldId = inInFieldId;
        }
    }
}
