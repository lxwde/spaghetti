package com.zpmc.ztos.infra.base.business.predicate;

import com.alibaba.fastjson.support.geo.Geometry;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;

public class CrossesPropertyPredicate extends GeometryPropertyPredicate{
    public CrossesPropertyPredicate(IMetafieldId inFieldId, IMetafieldId inOtherFieldId) {
        super(inFieldId, inOtherFieldId);
    }

    @Override
    protected String getOp() {
        return " crosses ";
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Geometry fieldValue = (Geometry)inValueSource.getFieldValue(this.getFieldId());
        Geometry compareValue = (Geometry)inValueSource.getFieldValue(this.getOtherFieldId());
        if (fieldValue == null || compareValue == null) {
            return false;
        }
  //      return fieldValue.crosses(compareValue);
        return false;
    }
}
