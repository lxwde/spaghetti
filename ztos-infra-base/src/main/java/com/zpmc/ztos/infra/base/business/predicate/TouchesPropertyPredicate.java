package com.zpmc.ztos.infra.base.business.predicate;

import com.alibaba.fastjson.support.geo.Geometry;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public class TouchesPropertyPredicate extends GeometryPropertyPredicate {
    public TouchesPropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId, inOtherFieldId);
    }

    @Override
    protected String getOp() {
        return " touches ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Geometry fieldValue = (Geometry)inValueSource.getFieldValue(this.getFieldId());
        Geometry compareValue = (Geometry)inValueSource.getFieldValue(this.getOtherFieldId());
        if (fieldValue == null || compareValue == null) {
            return false;
        }
 //       return fieldValue.touches(compareValue);
        return false;
    }

}
