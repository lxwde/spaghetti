package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.interfaces.IEdiEdifactMessageFormat;

import java.util.ArrayList;
import java.util.List;

public class EdiMessageD00B extends SegmentsD00B
        implements IEdiEdifactMessageFormat {

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
        headerRules.addAll(this.createUNBRules());
        headerRules.addAll(this.createUNHRules());
        headerRules.addAll(this.createBGMRules());
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
        summaryRules.addAll(this.createCNTRules());
        summaryRules.addAll(this.createUNTRules());
        return summaryRules;
    }

    @Override
    public List createCodecoDetailRules() {
        ArrayList detailRules = new ArrayList();
        detailRules.addAll(this.createTDTRules(1, 2));
        detailRules.addAll(this.createNADRules(1, 1));
        detailRules.addAll(this.createDGSRules(1, 1));
        detailRules.addAll(this.createEQDRules(1, 1));
        detailRules.addAll(this.createTMDRules(1, 1));
        detailRules.addAll(this.createDTMRules(1, 1));
        detailRules.addAll(this.createMEARules(1, 1));
        detailRules.addAll(this.createFTXRules(1, 1));
        return detailRules;
    }

    @Override
    public List createCoparnDetailRules() {
        ArrayList detailRules = new ArrayList();
        return detailRules;
    }

    @Override
    public List createCoarriDetailRules() {
        ArrayList detailRules = new ArrayList();
        detailRules.addAll(this.createTDTRules(1, 2));
        detailRules.addAll(this.createLOCRules(1, 1));
        detailRules.addAll(this.createTMDRules(1, 1));
        detailRules.addAll(this.createMEARules(1, 1));
        detailRules.addAll(this.createRFFRules(1, 1));
        detailRules.addAll(this.createNADRules(1, 1));
        detailRules.addAll(this.createEQDRules(1, 1));
        detailRules.addAll(this.createDGSRules(1, 1));
        return detailRules;
    }

    @Override
    public List createCoprarDetailRules() {
        ArrayList detailRules = new ArrayList();
        return detailRules;
    }

    @Override
    public List createCopinoDetailRules() {
        ArrayList detailRules = new ArrayList();
        return detailRules;
    }

    @Override
    public List createBaplieDetailRules() {
        ArrayList detailRules = new ArrayList();
        return detailRules;
    }

    @Override
    public List createAperakDetailRules() {
        ArrayList detailRules = new ArrayList();
        return detailRules;
    }

    @Override
    public List createTpfrepDetailRules() {
        ArrayList detailRules = new ArrayList();
        detailRules.addAll(this.createDTMRules(1, 5));
        detailRules.addAll(this.createTDTRules(1, 1));
        detailRules.addAll(this.createLOCRules(1, 1));
        return detailRules;
    }
}
