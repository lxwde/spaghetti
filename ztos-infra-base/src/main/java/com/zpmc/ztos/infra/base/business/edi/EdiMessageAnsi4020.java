package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.interfaces.IEdiAnsiMessageFormat;

import java.util.ArrayList;
import java.util.List;

public class EdiMessageAnsi4020 extends SegmentsAnsi004020
        implements IEdiAnsiMessageFormat {

    @Override
    public List createMessageCommonRules() {
        ArrayList messageRules = new ArrayList();
        messageRules.addAll(this.createMsgHeaderRules());
        messageRules.addAll(this.createMsgSummaryRules());
        return messageRules;
    }

    @Override
    public List createMsgHeaderRules() {
        ArrayList headerRules = new ArrayList();
        headerRules.addAll(this.createISARules());
        headerRules.addAll(this.createGSRules());
        return headerRules;
    }

    @Override
    public List createMsgDetailRules(String inMsgName) {
        ArrayList messageDetailRules = new ArrayList();
        return messageDetailRules;
    }

    @Override
    public List createMsgSummaryRules() {
        ArrayList summaryRules = new ArrayList();
        summaryRules.addAll(this.createGERules());
        summaryRules.addAll(this.createIEARules());
        return summaryRules;
    }

    @Override
    public List createAnsi322DetailRules() {
        ArrayList detailRules = new ArrayList();
        detailRules.addAll(this.createDTMRules());
        detailRules.addAll(this.createN7Rules());
        detailRules.addAll(this.createQ5Rules());
        detailRules.addAll(this.createR4Rules());
        detailRules.addAll(this.createSERules());
        detailRules.addAll(this.createSTRules());
        return detailRules;
    }

    @Override
    public List createAnsi323DetailRules() {
        ArrayList detailRules = new ArrayList();
        detailRules.addAll(this.createR4Rules());
        detailRules.addAll(this.createV1Rules());
        detailRules.addAll(this.createV9Rules());
        return detailRules;
    }
}
