package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

public interface IXpscachePropertyKeys {
    public static final IPropertyKey YARD_FILE_VERSION = PropertyKeyFactory.valueOf((String)"YARD_FILE_VERSION");
    public static final IPropertyKey LABEL_XPSCACHEDOBJECT_AUDIT_JOB_STATUS = PropertyKeyFactory.valueOf((String)"LABEL_XPSCACHEDOBJECT_AUDIT_JOB_STATUS");
    public static final IPropertyKey FAILURE_GENERATE_UNIQUE_PKEY = PropertyKeyFactory.valueOf((String)"FAILURE_GENERATE_UNIQUE_PKEY");
    public static final IPropertyKey FAILED_GEN_UNIQUE_PKEY_ATTEMPT = PropertyKeyFactory.valueOf((String)"FAILED_GEN_UNIQUE_PKEY_ATTEMPT");

}
