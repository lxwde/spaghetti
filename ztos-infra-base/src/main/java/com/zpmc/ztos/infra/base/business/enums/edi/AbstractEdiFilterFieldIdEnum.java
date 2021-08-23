package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class AbstractEdiFilterFieldIdEnum extends AtomizedEnum {
    private String[] _xmlFields;

    public AbstractEdiFilterFieldIdEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inXMLFields) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._xmlFields = StringUtils.split((String)inXMLFields, (char)',');
    }

    @Nullable
    public String[] getXmlFields() {
        return this._xmlFields;
    }

}
