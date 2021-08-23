package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.interfaces.IEdiAnsiMessageFormat;

import java.util.List;

public class EdiMessageAnsi4010 extends SegmentsAnsi004010
        implements IEdiAnsiMessageFormat {
    @Override
    public List createMsgHeaderRules() {
        return null;
    }

    @Override
    public List createMsgDetailRules(String inMsgName) {
        return null;
    }

    @Override
    public List createMsgSummaryRules() {
        return null;
    }

    @Override
    public List createMessageCommonRules() {
        return null;
    }

    @Override
    public List createAnsi322DetailRules() {
        return null;
    }

    @Override
    public List createAnsi323DetailRules() {
        return null;
    }
}

