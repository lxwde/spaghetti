package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IReferenceField {

    public static final IMetafieldId CNTRY_CODE = MetafieldIdFactory.valueOf("cntryCode");
    public static final IMetafieldId CNTRY_ALPHA3_CODE = MetafieldIdFactory.valueOf("cntryAlpha3Code");
    public static final IMetafieldId CNTRY_NUM3_CODE = MetafieldIdFactory.valueOf("cntryNum3Code");
    public static final IMetafieldId CNTRY_NAME = MetafieldIdFactory.valueOf("cntryName");
    public static final IMetafieldId CNTRY_OFFICIAL_NAME = MetafieldIdFactory.valueOf("cntryOfficialName");
    public static final IMetafieldId STATE_LIST = MetafieldIdFactory.valueOf("stateList");
    public static final IMetafieldId CNTRY_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf("cntryLifeCycleState");
    public static final IMetafieldId STATE_GKEY = MetafieldIdFactory.valueOf("stateGkey");
    public static final IMetafieldId STATE_CODE = MetafieldIdFactory.valueOf("stateCode");
    public static final IMetafieldId STATE_NAME = MetafieldIdFactory.valueOf("stateName");
    public static final IMetafieldId STATE_CNTRY = MetafieldIdFactory.valueOf("stateCntry");

}
