package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.SortOrderEnum;

public interface IFieldSort {

    public IMetafieldId getFieldId();

    public SortOrderEnum getSortOrder();

    public boolean isAscending();
}
