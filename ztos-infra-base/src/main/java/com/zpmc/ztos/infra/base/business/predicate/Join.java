package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.enums.framework.JoinTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.utils.FieldUtils;
import org.apache.commons.lang.StringUtils;

public class Join {

    private final JoinTypeEnum _joinType;
    private final IMetafieldId _metafield;
    private final String _alias;

    public Join(JoinTypeEnum inJoinType, IMetafieldId inFieldId, String inAlias) {
        this._joinType = inJoinType;
        this._metafield = inFieldId;
        this._alias = inAlias;
    }

    public String getHql(String inAlias) {
        String joinAliasStr = "";
        if (StringUtils.isNotEmpty((String)this._alias)) {
            joinAliasStr = " as " + this._alias + " ";
        }
        return " " + this._joinType.getName() + " " + FieldUtils.getAliasedField(inAlias, this._metafield) + joinAliasStr;
    }

    public JoinTypeEnum getJoinType() {
        return this._joinType;
    }

    public IMetafieldId getJoinField() {
        return this._metafield;
    }
    
}
