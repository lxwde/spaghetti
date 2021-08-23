package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface ISimpleSavedQueryField {

    public static final IMetafieldId QUERY_GKEY = MetafieldIdFactory.valueOf("queryGkey");
    public static final IMetafieldId QUERY_BUSER_GKEY = MetafieldIdFactory.valueOf("queryBuserGkey");
    public static final IMetafieldId QUERY_ROLE_GKEY = MetafieldIdFactory.valueOf("queryRoleGkey");
    public static final IMetafieldId QUERY_NAME = MetafieldIdFactory.valueOf("queryName");
    public static final IMetafieldId QUERY_DESCRIPTION = MetafieldIdFactory.valueOf("queryDescription");
    public static final IMetafieldId QUERY_ENTITY_NAME = MetafieldIdFactory.valueOf("queryEntityName");
    public static final IMetafieldId QUERY_CATEGORY = MetafieldIdFactory.valueOf("queryCategory");
    public static final IMetafieldId QUERY_WHERE_FORMAT = MetafieldIdFactory.valueOf("queryWhereFormat");
    public static final IMetafieldId QUERY_WHERE = MetafieldIdFactory.valueOf("queryWhere");
    public static final IMetafieldId QUERY_DISPLAY_FIELDS = MetafieldIdFactory.valueOf("queryDisplayFields");
    public static final IMetafieldId QUERY_ORDERING = MetafieldIdFactory.valueOf("queryOrdering");
    public static final IMetafieldId QUERY_PREDICATE = MetafieldIdFactory.valueOf("queryPredicate");
    public static final IMetafieldId QUERY_CREATED = MetafieldIdFactory.valueOf("queryCreated");
    public static final IMetafieldId QUERY_CREATOR = MetafieldIdFactory.valueOf("queryCreator");
    public static final IMetafieldId QUERY_CHANGED = MetafieldIdFactory.valueOf("queryChanged");
    public static final IMetafieldId QUERY_CHANGER = MetafieldIdFactory.valueOf("queryChanger");
    public static final IMetafieldId QUERY_USER_FORM_FILTERS = MetafieldIdFactory.valueOf("queryUserFormFilters");
    public static final IMetafieldId USRFILT_GKEY = MetafieldIdFactory.valueOf("usrfiltGkey");
    public static final IMetafieldId USRFILT_USER = MetafieldIdFactory.valueOf("usrfiltUser");
    public static final IMetafieldId USRFILT_FORM_ID = MetafieldIdFactory.valueOf("usrfiltFormId");
    public static final IMetafieldId USRFILT_LAST_USED_FILTER = MetafieldIdFactory.valueOf("usrfiltLastUsedFilter");
    public static final IMetafieldId PRDCT_GKEY = MetafieldIdFactory.valueOf("prdctGkey");
    public static final IMetafieldId PRDCT_VERB = MetafieldIdFactory.valueOf("prdctVerb");
    public static final IMetafieldId PRDCT_METAFIELD = MetafieldIdFactory.valueOf("prdctMetafield");
    public static final IMetafieldId PRDCT_VALUE = MetafieldIdFactory.valueOf("prdctValue");
    public static final IMetafieldId PRDCT_UI_VALUE = MetafieldIdFactory.valueOf("prdctUiValue");
    public static final IMetafieldId PRDCT_ORDER = MetafieldIdFactory.valueOf("prdctOrder");
    public static final IMetafieldId PRDCT_NEGATED = MetafieldIdFactory.valueOf("prdctNegated");
    public static final IMetafieldId PRDCT_PARAMETER_TYPE = MetafieldIdFactory.valueOf("prdctParameterType");
    public static final IMetafieldId PRDCT_PARAMETER_LABEL = MetafieldIdFactory.valueOf("prdctParameterLabel");
    public static final IMetafieldId PRDCT_PARAMETER_INTERNAL_NAME = MetafieldIdFactory.valueOf("prdctParameterInternalName");
    public static final IMetafieldId PRDCT_PARENT_PREDICATE = MetafieldIdFactory.valueOf("prdctParentPredicate");
    public static final IMetafieldId PRDCT_CHILD_PRDCT_LIST = MetafieldIdFactory.valueOf("prdctChildPrdctList");
    public static final IMetafieldId RECAP_GKEY = MetafieldIdFactory.valueOf("recapGkey");
    public static final IMetafieldId RECAP_BUSER_GKEY = MetafieldIdFactory.valueOf("recapBuserGkey");
    public static final IMetafieldId RECAP_ROLE_GKEY = MetafieldIdFactory.valueOf("recapRoleGkey");
    public static final IMetafieldId RECAP_ENTITY_NAME = MetafieldIdFactory.valueOf("recapEntityName");
    public static final IMetafieldId RECAP_NAME = MetafieldIdFactory.valueOf("recapName");
    public static final IMetafieldId RECAP_DESCRIPTION = MetafieldIdFactory.valueOf("recapDescription");
    public static final IMetafieldId RECAP_X_METAFIELD_ID = MetafieldIdFactory.valueOf("recapXMetafieldId");
    public static final IMetafieldId RECAP_Y_METAFIELD_ID = MetafieldIdFactory.valueOf("recapYMetafieldId");
    public static final IMetafieldId RECAP_FORMULAS = MetafieldIdFactory.valueOf("recapFormulas");
    public static final IMetafieldId RECAP_CREATED = MetafieldIdFactory.valueOf("recapCreated");
    public static final IMetafieldId RECAP_CREATOR = MetafieldIdFactory.valueOf("recapCreator");
    public static final IMetafieldId RECAP_CHANGED = MetafieldIdFactory.valueOf("recapChanged");
    public static final IMetafieldId RECAP_CHANGER = MetafieldIdFactory.valueOf("recapChanger");
    public static final IMetafieldId FORMULA_GKEY = MetafieldIdFactory.valueOf("formulaGkey");
    public static final IMetafieldId FORMULA_RECAP = MetafieldIdFactory.valueOf("formulaRecap");
    public static final IMetafieldId FORMULA_NAME = MetafieldIdFactory.valueOf("formulaName");
}
