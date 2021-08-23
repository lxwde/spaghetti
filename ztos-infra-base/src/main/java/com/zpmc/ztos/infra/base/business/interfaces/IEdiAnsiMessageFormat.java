package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IEdiAnsiMessageFormat extends IEdiMessageFormat {
    public List createAnsi322DetailRules();

    public List createAnsi323DetailRules();
}
