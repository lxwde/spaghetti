package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IPoolField extends IPoolsField {
    public static final IMetafieldId POOL_MEMBER_EQTYPE_POOL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)POOLEQTYPE_POOL_MEMBER, (IMetafieldId)POOLMBR_POOL);
    public static final IMetafieldId POOL_MEMBER_POOL_COMPLEX = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)POOLMBR_POOL, (IMetafieldId)POOL_COMPLEX);
    public static final IMetafieldId POOL_MEMBER_EQCLASS_POOL = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)POOLEQCLASS_POOL_MEMBER, (IMetafieldId)POOLMBR_POOL);

}
