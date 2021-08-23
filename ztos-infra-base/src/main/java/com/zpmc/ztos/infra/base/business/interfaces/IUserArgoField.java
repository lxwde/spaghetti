package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IUserArgoField {
    public static final IMetafieldId ARGOUSER_BIZ_GROUP = MetafieldIdFactory.valueOf((String)"argouserBizGroup");
    public static final IMetafieldId ARGOUSER_OPERATOR = MetafieldIdFactory.valueOf((String)"argouserOperator");
    public static final IMetafieldId ARGOUSER_COMPLEX = MetafieldIdFactory.valueOf((String)"argouserComplex");
    public static final IMetafieldId ARGOUSER_FACILITY = MetafieldIdFactory.valueOf((String)"argouserFacility");
    public static final IMetafieldId ARGOUSER_YARD = MetafieldIdFactory.valueOf((String)"argouserYard");
    public static final IMetafieldId ARGOUSER_HORIZON_DAYS = MetafieldIdFactory.valueOf((String)"argouserHorizonDays");
    public static final IMetafieldId ARGOUSER_MY_LIST_CHOICE = MetafieldIdFactory.valueOf((String)"argouserMyListChoice");
    public static final IMetafieldId ARGOUSER_COMPANY_BIZ_UNIT = MetafieldIdFactory.valueOf((String)"argouserCompanyBizUnit");
    public static final IMetafieldId BIZGRP_GKEY = MetafieldIdFactory.valueOf((String)"bizgrpGkey");
    public static final IMetafieldId BIZGRP_ID = MetafieldIdFactory.valueOf((String)"bizgrpId");
    public static final IMetafieldId BIZGRP_SCOPE = MetafieldIdFactory.valueOf((String)"bizgrpScope");
    public static final IMetafieldId BIZGRP_NAME = MetafieldIdFactory.valueOf((String)"bizgrpName");
    public static final IMetafieldId BIZGRP_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"bizgrpLifeCycleState");
    public static final IMetafieldId BIZGRP_BUSINESS_UNITS = MetafieldIdFactory.valueOf((String)"bizgrpBusinessUnits");
    public static final IMetafieldId BIZGRPBZU_GKEY = MetafieldIdFactory.valueOf((String)"bizgrpbzuGkey");
    public static final IMetafieldId BIZGRPBZU_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"bizgrpbzuLifeCycleState");
    public static final IMetafieldId BIZGRPBZU_BIZ_GROUP = MetafieldIdFactory.valueOf((String)"bizgrpbzuBizGroup");
    public static final IMetafieldId BIZGRPBZU_BIZ_UNIT = MetafieldIdFactory.valueOf((String)"bizgrpbzuBizUnit");
    public static final IMetafieldId ARGROLE_OPERATOR = MetafieldIdFactory.valueOf((String)"argroleOperator");
    public static final IMetafieldId KYSET_GKEY = MetafieldIdFactory.valueOf((String)"kysetGkey");
    public static final IMetafieldId KYSET_OWNER_TYPE = MetafieldIdFactory.valueOf((String)"kysetOwnerType");
    public static final IMetafieldId KYSET_OWNER_GKEY = MetafieldIdFactory.valueOf((String)"kysetOwnerGkey");
    public static final IMetafieldId KYSET_NAME = MetafieldIdFactory.valueOf((String)"kysetName");
    public static final IMetafieldId KYSET_ENTITY_NAME = MetafieldIdFactory.valueOf((String)"kysetEntityName");
    public static final IMetafieldId KYSET_KEYS = MetafieldIdFactory.valueOf((String)"kysetKeys");
    public static final IMetafieldId KYSET_TIME_LAST_PREEN = MetafieldIdFactory.valueOf((String)"kysetTimeLastPreen");
}
