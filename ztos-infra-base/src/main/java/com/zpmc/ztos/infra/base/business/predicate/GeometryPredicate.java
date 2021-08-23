package com.zpmc.ztos.infra.base.business.predicate;

import com.alibaba.fastjson.support.geo.Geometry;
import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;

public abstract class GeometryPredicate extends SimplePredicate {

    protected final int _spatialRelationType;

    GeometryPredicate(int inSpatialRelationType, IMetafieldId inFieldId, Object inFieldValue) {
        super(inFieldId, inFieldValue);
        this._spatialRelationType = inSpatialRelationType;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object fieldValue = inValueSource.getFieldValue(this.getFieldId());
        if (fieldValue != null) {
            Object requiredValue = this.getFieldValue();
            if (fieldValue instanceof Geometry) {
                return this.isGeometrySatisfiedBy((Geometry)fieldValue, requiredValue);
            }
        }
        return false;
    }

    @Override
    @NotNull
    public String toHqlString(String inEntityAlias) {
        String aliasedField = FieldUtils.getAliasedField(inEntityAlias, this.getFieldId());
        return "(" + this.getOp() + '(' + aliasedField + ", ?)  = true )";
    }

    private boolean isGeometrySatisfiedBy(Geometry inFieldValue, Object inRequiredValue) {
        boolean success = false;
        if (inRequiredValue instanceof String) {
//            WKTReader fromText = new WKTReader();
//            try {
//                Geometry filter = fromText.read((String)inRequiredValue);
//                return inFieldValue.equals(filter);
//            }
//            catch (ParseException parseException) {}
        } else if (inRequiredValue instanceof Geometry) {
            return inFieldValue.equals((Geometry)inRequiredValue);
        }
        return success;
    }

}
