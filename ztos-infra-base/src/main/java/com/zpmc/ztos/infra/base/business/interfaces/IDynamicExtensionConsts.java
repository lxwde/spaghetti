package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IDynamicExtensionConsts {
    public static final String DYNAMIC_ENTITY = "DynamicHibernatingEntity";
    public static final IMetafieldId CUSTOM_ENTITY_GKEY = MetafieldIdFactory.valueOf("customEntityGkey");
    public static final IMetafieldId CUSTOM_ENTITY_FIELDS = MetafieldIdFactory.valueOf("customEntityFields");
    public static final IMetafieldId CUSTOM_FLEX_FIELDS = MetafieldIdFactory.valueOf("customFlexFields");
    public static final IMetafieldId MODIFICATION_FIELD_LENGTH = MetafieldIdFactory.getCompoundMetafieldId(IExtensionDataModelField.MEXTMF_MODIFICATION_CURRENT_VERSION, IExtensionDataModelField.MEXTMFV_COLUMN_LENGTH);

}
