package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IEdiMessageFormat {
    public List createMsgHeaderRules();

    public List createMsgDetailRules(String var1);

    public List createMsgSummaryRules();

    public List createMessageCommonRules();
}
