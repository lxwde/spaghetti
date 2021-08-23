package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IPoolsField {
    public static final IMetafieldId POOL_GKEY = MetafieldIdFactory.valueOf((String)"poolGkey");
    public static final IMetafieldId POOL_ID = MetafieldIdFactory.valueOf((String)"poolId");
    public static final IMetafieldId POOL_COMPLEX = MetafieldIdFactory.valueOf((String)"poolComplex");
    public static final IMetafieldId POOL_NAME = MetafieldIdFactory.valueOf((String)"poolName");
    public static final IMetafieldId POOL_TAG = MetafieldIdFactory.valueOf((String)"poolTag");
    public static final IMetafieldId POOL_ADMIN_NAME = MetafieldIdFactory.valueOf((String)"poolAdminName");
    public static final IMetafieldId POOL_ADMIN_PHONE = MetafieldIdFactory.valueOf((String)"poolAdminPhone");
    public static final IMetafieldId POOL_ADMIN_MOBILE = MetafieldIdFactory.valueOf((String)"poolAdminMobile");
    public static final IMetafieldId POOL_ADMIN_FAX = MetafieldIdFactory.valueOf((String)"poolAdminFax");
    public static final IMetafieldId POOL_ADMIN_EMAIL = MetafieldIdFactory.valueOf((String)"poolAdminEmail");
    public static final IMetafieldId POOL_ADMIN_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"poolAdminLifeCycleState");
    public static final IMetafieldId POOL_CREATED = MetafieldIdFactory.valueOf((String)"poolCreated");
    public static final IMetafieldId POOL_CREATOR = MetafieldIdFactory.valueOf((String)"poolCreator");
    public static final IMetafieldId POOL_CHANGED = MetafieldIdFactory.valueOf((String)"poolChanged");
    public static final IMetafieldId POOL_CHANGER = MetafieldIdFactory.valueOf((String)"poolChanger");
    public static final IMetafieldId POOL_MEMBERS = MetafieldIdFactory.valueOf((String)"poolMembers");
    public static final IMetafieldId POOL_EQUIPMENT = MetafieldIdFactory.valueOf((String)"poolEquipment");
    public static final IMetafieldId POOL_TRUCK_COS = MetafieldIdFactory.valueOf((String)"poolTruckCos");
    public static final IMetafieldId POOLMBR_GKEY = MetafieldIdFactory.valueOf((String)"poolmbrGkey");
    public static final IMetafieldId POOLMBR_POOL = MetafieldIdFactory.valueOf((String)"poolmbrPool");
    public static final IMetafieldId POOLMBR_EQ_OPER = MetafieldIdFactory.valueOf((String)"poolmbrEqOper");
    public static final IMetafieldId POOLMBR_LIFE_CYCLE_STATE = MetafieldIdFactory.valueOf((String)"poolmbrLifeCycleState");
    public static final IMetafieldId POOLMBR_CREATED = MetafieldIdFactory.valueOf((String)"poolmbrCreated");
    public static final IMetafieldId POOLMBR_CREATOR = MetafieldIdFactory.valueOf((String)"poolmbrCreator");
    public static final IMetafieldId POOLMBR_CHANGED = MetafieldIdFactory.valueOf((String)"poolmbrChanged");
    public static final IMetafieldId POOLMBR_CHANGER = MetafieldIdFactory.valueOf((String)"poolmbrChanger");
    public static final IMetafieldId POOLMBR_EQUIP_TYPES = MetafieldIdFactory.valueOf((String)"poolmbrEquipTypes");
    public static final IMetafieldId POOLMBR_EQ_CLASSES = MetafieldIdFactory.valueOf((String)"poolmbrEqClasses");
    public static final IMetafieldId POOLEQ_GKEY = MetafieldIdFactory.valueOf((String)"pooleqGkey");
    public static final IMetafieldId POOLEQ_EQUIPMENT = MetafieldIdFactory.valueOf((String)"pooleqEquipment");
    public static final IMetafieldId POOLEQ_POOL = MetafieldIdFactory.valueOf((String)"pooleqPool");
    public static final IMetafieldId POOLEQ_JOINED = MetafieldIdFactory.valueOf((String)"pooleqJoined");
    public static final IMetafieldId POOLEQ_REMOVED = MetafieldIdFactory.valueOf((String)"pooleqRemoved");
    public static final IMetafieldId POOLEQ_CREATED = MetafieldIdFactory.valueOf((String)"pooleqCreated");
    public static final IMetafieldId POOLEQ_CREATOR = MetafieldIdFactory.valueOf((String)"pooleqCreator");
    public static final IMetafieldId POOLEQ_CHANGED = MetafieldIdFactory.valueOf((String)"pooleqChanged");
    public static final IMetafieldId POOLEQ_CHANGER = MetafieldIdFactory.valueOf((String)"pooleqChanger");
    public static final IMetafieldId POOLEQTYPE_GKEY = MetafieldIdFactory.valueOf((String)"pooleqtypeGkey");
    public static final IMetafieldId POOLEQTYPE_POOL_MEMBER = MetafieldIdFactory.valueOf((String)"pooleqtypePoolMember");
    public static final IMetafieldId POOLEQTYPE_LENGTH = MetafieldIdFactory.valueOf((String)"pooleqtypeLength");
    public static final IMetafieldId POOLEQTYPE_ISO_GROUP = MetafieldIdFactory.valueOf((String)"pooleqtypeIsoGroup");
    public static final IMetafieldId POOLEQTYPE_HEIGHT = MetafieldIdFactory.valueOf((String)"pooleqtypeHeight");
    public static final IMetafieldId POOLEQTYPE_JOINED = MetafieldIdFactory.valueOf((String)"pooleqtypeJoined");
    public static final IMetafieldId POOLEQTYPE_REMOVED = MetafieldIdFactory.valueOf((String)"pooleqtypeRemoved");
    public static final IMetafieldId POOLEQTYPE_CREATED = MetafieldIdFactory.valueOf((String)"pooleqtypeCreated");
    public static final IMetafieldId POOLEQTYPE_CREATOR = MetafieldIdFactory.valueOf((String)"pooleqtypeCreator");
    public static final IMetafieldId POOLEQTYPE_CHANGED = MetafieldIdFactory.valueOf((String)"pooleqtypeChanged");
    public static final IMetafieldId POOLEQTYPE_CHANGER = MetafieldIdFactory.valueOf((String)"pooleqtypeChanger");
    public static final IMetafieldId POOLTRKCO_GKEY = MetafieldIdFactory.valueOf((String)"pooltrkcoGkey");
    public static final IMetafieldId POOLTRKCO_TRUCKING_COMPANY = MetafieldIdFactory.valueOf((String)"pooltrkcoTruckingCompany");
    public static final IMetafieldId POOLTRKCO_POOL = MetafieldIdFactory.valueOf((String)"pooltrkcoPool");
    public static final IMetafieldId POOLTRKCO_BANNED = MetafieldIdFactory.valueOf((String)"pooltrkcoBanned");
    public static final IMetafieldId POOLTRKCO_CREATED = MetafieldIdFactory.valueOf((String)"pooltrkcoCreated");
    public static final IMetafieldId POOLTRKCO_CREATOR = MetafieldIdFactory.valueOf((String)"pooltrkcoCreator");
    public static final IMetafieldId POOLTRKCO_CHANGED = MetafieldIdFactory.valueOf((String)"pooltrkcoChanged");
    public static final IMetafieldId POOLTRKCO_CHANGER = MetafieldIdFactory.valueOf((String)"pooltrkcoChanger");
    public static final IMetafieldId POOLEQCLASS_GKEY = MetafieldIdFactory.valueOf((String)"pooleqclassGkey");
    public static final IMetafieldId POOLEQCLASS_POOL_MEMBER = MetafieldIdFactory.valueOf((String)"pooleqclassPoolMember");
    public static final IMetafieldId POOLEQCLASS_EQ_CLASS_ENUM = MetafieldIdFactory.valueOf((String)"pooleqclassEqClassEnum");
    public static final IMetafieldId POOLEQCLASS_CREATED = MetafieldIdFactory.valueOf((String)"pooleqclassCreated");
    public static final IMetafieldId POOLEQCLASS_CREATOR = MetafieldIdFactory.valueOf((String)"pooleqclassCreator");
    public static final IMetafieldId POOLEQCLASS_CHANGED = MetafieldIdFactory.valueOf((String)"pooleqclassChanged");
    public static final IMetafieldId POOLEQCLASS_CHANGER = MetafieldIdFactory.valueOf((String)"pooleqclassChanger");
}
