package com.zpmc.ztos.infra.base.common.model;


import com.zpmc.ztos.infra.base.business.enums.framework.SortOrderEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IFieldSort;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueHolder;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Ordering implements Serializable,
        IFieldSort {

    private final IMetafieldId _fieldId;
    private SortOrderEnum _sortOrder;
    private static final Logger LOGGER = Logger.getLogger(Ordering.class);

    public static Ordering asc(IMetafieldId inFieldId) {
        return new Ordering(inFieldId, SortOrderEnum.ASCENDING);
    }

    @Deprecated
    public static Ordering asc(String inFieldId) {
        return new Ordering(MetafieldIdFactory.valueOf(inFieldId), SortOrderEnum.ASCENDING);
    }

    public static Ordering desc(IMetafieldId inFieldId) {
        return new Ordering(inFieldId, SortOrderEnum.DESCENDING);
    }

    @Deprecated
    public static Ordering desc(String inFieldId) {
        return new Ordering(MetafieldIdFactory.valueOf(inFieldId), SortOrderEnum.DESCENDING);
    }

    public static Ordering invert(Ordering inOrdering) {
        return new Ordering(inOrdering.getFieldId(), SortOrderEnum.invert(inOrdering.getSortOrder()));
    }

    @Deprecated
    private Ordering(IMetafieldId inFieldId, SortOrderEnum inSortOrder) {
        this._fieldId = inFieldId;
        this._sortOrder = inSortOrder;
    }

    public Ordering(Ordering inOrdering) {
        this._fieldId = inOrdering._fieldId;
        this._sortOrder = inOrdering._sortOrder;
    }

    public Comparator<IValueHolder> asComparator() {
        Comparator baseComparator = this.isAscending() ? Comparator.naturalOrder() : Comparator.reverseOrder();
//        return Comparator.comparing(inVh -> (Comparable)inVh.getFieldValue(this.getFieldId()), Comparator.nullsLast(baseComparator));
        return null;
    }

    public static Optional<Comparator<IValueHolder>> chain(Ordering... inOrderings) {
        return Arrays.stream(inOrderings).map(Ordering::asComparator).reduce(Comparator::thenComparing);
    }

    @Override
    public IMetafieldId getFieldId() {
        return this._fieldId;
    }

    @Override
    public SortOrderEnum getSortOrder() {
        return this._sortOrder;
    }

    @Override
    public boolean isAscending() {
        return this._sortOrder.equals((Object) SortOrderEnum.ASCENDING);
    }

    @Deprecated
    public void setAscending(boolean inAscending) {
        this._sortOrder = inAscending ? SortOrderEnum.ASCENDING : SortOrderEnum.DESCENDING;
    }

    public boolean equals(Object inOther) {
        if (this == inOther) {
            return true;
        }
        if (!(inOther instanceof Ordering)) {
            return false;
        }
        Ordering ordering = (Ordering)inOther;
        if (!this._fieldId.equals(ordering._fieldId)) {
            return false;
        }
        return this._sortOrder.equals((Object)ordering._sortOrder);
    }

    public int hashCode() {
        int result = this._fieldId.hashCode();
        result = 29 * result + this._sortOrder.hashCode();
        return result;
    }

    public String toString() {
        return this._fieldId.getFieldId() + " " + (Object)((Object)this._sortOrder);
    }

    public String toHqlString(String inEntityAlias) {
        String fieldStr = FieldUtils.getAliasedField(inEntityAlias, this._fieldId);
        return fieldStr + " " + this._sortOrder.toHqlString();
    }

}
