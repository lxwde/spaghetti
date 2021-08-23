package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IValueSource;
import com.zpmc.ztos.infra.base.common.utils.SearchUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LikePredicate extends SimplePredicate {
    private Pattern _pattern;

    LikePredicate(IMetafieldId inFieldId, Object inValue) {
        super(inFieldId, inValue);
    }

    @Override
    protected String getOp() {
        return " like ";
    }

    private Pattern getPattern() {
        if (this._pattern == null) {
            String sqlPattern = (String)this.getFieldValue();
            String regex = SearchUtils.convertSQLToRegExPattern(sqlPattern);
            this._pattern = Pattern.compile(regex);
        }
        return this._pattern;
    }

    @Override
    public boolean isSatisfiedBy(IValueSource inValueSource) {
        Object fieldValue = inValueSource.getFieldValue(this.getFieldId());
        if (fieldValue == null) {
            return false;
        }
        if (!(fieldValue instanceof String)) {
            fieldValue = fieldValue.toString();
        }
        Matcher m = this.getPattern().matcher((String)fieldValue);
        return m.find();
    }

}
