package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import org.dom4j.Element;

public interface ISnxImporter {
    public void parseElement(Element var1) throws BizViolation;

    public void setScopeParameters();
}
