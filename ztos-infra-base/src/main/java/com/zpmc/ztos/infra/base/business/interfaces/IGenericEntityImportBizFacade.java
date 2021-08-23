package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface IGenericEntityImportBizFacade {
    public static final String BEAN_ID = "genericEntityImportBizFacade";

    public IEntityImportResponse importSingleEntityXml(UserContext var1, String var2);

    public IMultiEntityImportResponse importMultipleEntityXml(UserContext var1, String var2);
}
