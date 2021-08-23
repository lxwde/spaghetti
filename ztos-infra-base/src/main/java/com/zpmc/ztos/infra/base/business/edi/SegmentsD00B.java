package com.zpmc.ztos.infra.base.business.edi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentsD00B {
    Map _segmentErrors = new HashMap();

    protected List createUNBRules() {
        ArrayList<EdiRule> unbRules = new ArrayList<EdiRule>();
        EdiRule ruleUNB1 = new EdiRule("UNB_1", "UNB", new Integer(1), new Integer(1), new Integer(0), new Integer(1), new Integer(4), new Integer(1), "UNOA");
        unbRules.add(ruleUNB1);
        EdiRule ruleUNB2 = new EdiRule("UNB_2", "UNB", new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), null);
        unbRules.add(ruleUNB2);
        EdiRule ruleUNB4 = new EdiRule("UNB_4", "UNB", new Integer(1), new Integer(3), new Integer(0), new Integer(1), new Integer(14), new Integer(1), null);
        unbRules.add(ruleUNB4);
        EdiRule ruleUNB5 = new EdiRule("UNB_5", "UNB", new Integer(1), new Integer(4), new Integer(0), new Integer(1), new Integer(14), new Integer(1), null);
        unbRules.add(ruleUNB5);
        EdiRule ruleUNB6 = new EdiRule("UNB_6", "UNB", new Integer(1), new Integer(4), new Integer(1), new Integer(1), new Integer(14), new Integer(1), null);
        unbRules.add(ruleUNB6);
        EdiRule ruleUNB7 = new EdiRule("UNB_7", "UNB", new Integer(1), new Integer(5), new Integer(0), new Integer(1), new Integer(14), new Integer(1), null);
        unbRules.add(ruleUNB7);
        return unbRules;
    }

    protected List createUNHRules() {
        ArrayList<EdiRule> unhRules = new ArrayList<EdiRule>();
        EdiRule ruleUNH1 = new EdiRule("UNH_1", "UNH", new Integer(1), new Integer(1), null, new Integer(1), new Integer(14), new Integer(1), null);
        unhRules.add(ruleUNH1);
        EdiRule ruleUNH2 = new EdiRule("UNH_2", "UNH", new Integer(1), new Integer(2), new Integer(0), new Integer(1), new Integer(6), new Integer(1), null);
        unhRules.add(ruleUNH2);
        EdiRule ruleUNH3 = new EdiRule("UNH_3", "UNH", new Integer(1), new Integer(2), new Integer(1), new Integer(1), new Integer(3), new Integer(1), null);
        unhRules.add(ruleUNH3);
        EdiRule ruleUNH4 = new EdiRule("UNH_4", "UNH", new Integer(1), new Integer(2), new Integer(2), new Integer(1), new Integer(3), new Integer(1), null);
        unhRules.add(ruleUNH4);
        EdiRule ruleUNH5 = new EdiRule("UNH_5", "UNH", new Integer(1), new Integer(2), new Integer(3), new Integer(1), new Integer(3), new Integer(1), null);
        unhRules.add(ruleUNH5);
        EdiRule ruleUNH6 = new EdiRule("UNH_6", "UNH", new Integer(1), new Integer(2), new Integer(4), new Integer(1), new Integer(6), new Integer(1), null);
        unhRules.add(ruleUNH6);
        return unhRules;
    }

    protected List createBGMRules() {
        ArrayList<EdiRule> bgmRules = new ArrayList<EdiRule>();
        EdiRule ruleBGM1 = new EdiRule("BGM_1", "BGM", new Integer(1), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(1), null);
        bgmRules.add(ruleBGM1);
        EdiRule ruleBGM3 = new EdiRule("BGM_3", "BGM", new Integer(1), new Integer(3), null, new Integer(1), new Integer(3), new Integer(1), null);
        bgmRules.add(ruleBGM3);
        return bgmRules;
    }

    protected List createCNTRules() {
        ArrayList cntRules = new ArrayList();
        return cntRules;
    }

    protected List createUNTRules() {
        ArrayList<EdiRule> untRules = new ArrayList<EdiRule>();
        EdiRule ruleUNT1 = new EdiRule("UNT_1", "UNT", new Integer(1), new Integer(1), new Integer(0), new Integer(1), new Integer(6), new Integer(1), null);
        untRules.add(ruleUNT1);
        EdiRule ruleUNT2 = new EdiRule("UNT_2", "UNT", new Integer(1), new Integer(2), new Integer(0), new Integer(1), new Integer(14), new Integer(1), null);
        untRules.add(ruleUNT2);
        return untRules;
    }

    protected List createTDTRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> tdtRules = new ArrayList<EdiRule>();
        EdiRule ruleTDT1 = new EdiRule("TDT_1", "TDT", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tdtRules.add(ruleTDT1);
        EdiRule ruleTDT3 = new EdiRule("TDT_3", "TDT", new Integer(inSegmentPosition), new Integer(3), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tdtRules.add(ruleTDT3);
        return tdtRules;
    }

    protected List createLOCRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> locRules = new ArrayList<EdiRule>();
        EdiRule ruleLOC1 = new EdiRule("LOC_1", "LOC", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        locRules.add(ruleLOC1);
        EdiRule ruleLOC2 = new EdiRule("LOC_2", "LOC", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(25), new Integer(inSegmentOccurences), null);
        locRules.add(ruleLOC2);
        return locRules;
    }

    protected List createNADRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> nadRules = new ArrayList<EdiRule>();
        EdiRule ruleNAD1 = new EdiRule("NAD_1", "NAD", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        nadRules.add(ruleNAD1);
        EdiRule ruleNAD2 = new EdiRule("NAD_2", "NAD", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(35), new Integer(inSegmentOccurences), null);
        nadRules.add(ruleNAD2);
        EdiRule ruleNAD3 = new EdiRule("NAD_3", "NAD", new Integer(inSegmentPosition), new Integer(2), new Integer(1), new Integer(0), new Integer(3), new Integer(inSegmentOccurences), null);
        nadRules.add(ruleNAD3);
        EdiRule ruleNAD4 = new EdiRule("NAD_4", "NAD", new Integer(inSegmentPosition), new Integer(2), new Integer(2), new Integer(0), new Integer(3), new Integer(inSegmentOccurences), null);
        nadRules.add(ruleNAD4);
        return nadRules;
    }

    protected List createEQDRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> eqdRules = new ArrayList<EdiRule>();
        EdiRule ruleEQD1 = new EdiRule("EQD_1", "EQD", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD1);
        EdiRule ruleEQD2 = new EdiRule("EQD_2", "EQD", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(17), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD2);
        EdiRule ruleEQD3 = new EdiRule("EQD_3", "EQD", new Integer(inSegmentPosition), new Integer(3), new Integer(0), new Integer(1), new Integer(10), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD3);
        EdiRule ruleEQD4 = new EdiRule("EQD_4", "EQD", new Integer(inSegmentPosition), new Integer(3), new Integer(1), new Integer(1), new Integer(17), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD4);
        EdiRule ruleEQD5 = new EdiRule("EQD_5", "EQD", new Integer(inSegmentPosition), new Integer(3), new Integer(2), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD5);
        EdiRule ruleEQD8 = new EdiRule("EQD_8", "EQD", new Integer(inSegmentPosition), new Integer(6), null, new Integer(0), new Integer(3), new Integer(inSegmentOccurences), null);
        eqdRules.add(ruleEQD8);
        return eqdRules;
    }

    protected List createTMDRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> tmdRules = new ArrayList<EdiRule>();
        EdiRule ruleTMD1 = new EdiRule("TMD_1", "TMD", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tmdRules.add(ruleTMD1);
        return tmdRules;
    }

    protected List createDTMRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> dtmRules = new ArrayList<EdiRule>();
        EdiRule ruleDTM1 = new EdiRule("DTM_1", "DTM", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        dtmRules.add(ruleDTM1);
        EdiRule ruleDTM2 = new EdiRule("DTM_2", "DTM", new Integer(inSegmentPosition), new Integer(1), new Integer(1), new Integer(1), new Integer(35), new Integer(inSegmentOccurences), null);
        dtmRules.add(ruleDTM2);
        EdiRule ruleDTM3 = new EdiRule("DTM_3", "DTM", new Integer(inSegmentPosition), new Integer(1), new Integer(2), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        dtmRules.add(ruleDTM3);
        return dtmRules;
    }

    protected List createMEARules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> meaRules = new ArrayList<EdiRule>();
        EdiRule ruleMEA1 = new EdiRule("MEA_1", "MEA", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        meaRules.add(ruleMEA1);
        EdiRule ruleMEA2 = new EdiRule("MEA_2", "MEA", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        meaRules.add(ruleMEA2);
        EdiRule ruleMEA3 = new EdiRule("MEA_3", "MEA", new Integer(inSegmentPosition), new Integer(3), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        meaRules.add(ruleMEA3);
        EdiRule ruleMEA4 = new EdiRule("MEA_4", "MEA", new Integer(inSegmentPosition), new Integer(3), new Integer(1), new Integer(1), new Integer(18), new Integer(inSegmentOccurences), null);
        meaRules.add(ruleMEA4);
        return meaRules;
    }

    protected List createRFFRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> rffRules = new ArrayList<EdiRule>();
        EdiRule ruleRFF1 = new EdiRule("RFF_1", "RFF", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        rffRules.add(ruleRFF1);
        return rffRules;
    }

    protected List createSELRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> selRules = new ArrayList<EdiRule>();
        EdiRule ruleSEL1 = new EdiRule("SEL_1", "SEL", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(10), new Integer(inSegmentOccurences), null);
        selRules.add(ruleSEL1);
        EdiRule ruleSEL2 = new EdiRule("SEL_2", "SEL", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        selRules.add(ruleSEL2);
        EdiRule ruleSEL3 = new EdiRule("SEL_3", "SEL", new Integer(inSegmentPosition), new Integer(3), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        selRules.add(ruleSEL3);
        return selRules;
    }

    protected List createTMPRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> tmpRules = new ArrayList<EdiRule>();
        EdiRule ruleTMP1 = new EdiRule("TMPL_1", "TMP", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tmpRules.add(ruleTMP1);
        EdiRule ruleTMP2 = new EdiRule("TMPL_2", "TMP", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tmpRules.add(ruleTMP2);
        EdiRule ruleTMP3 = new EdiRule("TMPL_3", "TMP", new Integer(inSegmentPosition), new Integer(2), new Integer(1), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        tmpRules.add(ruleTMP3);
        return tmpRules;
    }

    protected List createRNGRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> rngRules = new ArrayList<EdiRule>();
        EdiRule ruleRNG1 = new EdiRule("RNG_1", "RNG", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        rngRules.add(ruleRNG1);
        EdiRule ruleRNG2 = new EdiRule("RNG_2", "RNG", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        rngRules.add(ruleRNG2);
        EdiRule ruleRNG3 = new EdiRule("RNG_3", "RNG", new Integer(inSegmentPosition), new Integer(2), new Integer(1), new Integer(1), new Integer(18), new Integer(inSegmentOccurences), null);
        rngRules.add(ruleRNG3);
        EdiRule ruleRNG4 = new EdiRule("RNG_4", "RNG", new Integer(inSegmentPosition), new Integer(2), new Integer(2), new Integer(1), new Integer(18), new Integer(inSegmentOccurences), null);
        rngRules.add(ruleRNG4);
        return rngRules;
    }

    protected List createFTXRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> ftxRules = new ArrayList<EdiRule>();
        EdiRule ruleFTX1 = new EdiRule("FTX_1", "FTX", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        ftxRules.add(ruleFTX1);
        return ftxRules;
    }

    protected List createDGSRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> dgsRules = new ArrayList<EdiRule>();
        EdiRule ruleDGS1 = new EdiRule("DGS_1", "DGS", new Integer(inSegmentPosition), new Integer(1), null, new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        dgsRules.add(ruleDGS1);
        EdiRule ruleDGS2 = new EdiRule("DGS_2", "DGS", new Integer(inSegmentPosition), new Integer(2), new Integer(0), new Integer(1), new Integer(7), new Integer(inSegmentOccurences), null);
        dgsRules.add(ruleDGS2);
        EdiRule ruleDGS5 = new EdiRule("DGS_5", "DGS", new Integer(inSegmentPosition), new Integer(3), new Integer(0), new Integer(1), new Integer(4), new Integer(inSegmentOccurences), null);
        dgsRules.add(ruleDGS5);
        return dgsRules;
    }

    protected List createDIMRules(int inSegmentPosition, int inSegmentOccurences) {
        ArrayList<EdiRule> dimRules = new ArrayList<EdiRule>();
        EdiRule ruleDIM1 = new EdiRule("DIM_1", "DIM", new Integer(inSegmentPosition), new Integer(1), new Integer(0), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        dimRules.add(ruleDIM1);
        EdiRule ruleDIM2 = new EdiRule("DIM_1", "DIM", new Integer(inSegmentPosition), new Integer(2), new Integer(1), new Integer(1), new Integer(3), new Integer(inSegmentOccurences), null);
        dimRules.add(ruleDIM2);
        EdiRule ruleDIM3 = new EdiRule("DIM_1", "DIM", new Integer(inSegmentPosition), new Integer(2), new Integer(2), new Integer(0), new Integer(15), new Integer(inSegmentOccurences), null);
        dimRules.add(ruleDIM3);
        EdiRule ruleDIM4 = new EdiRule("DIM_1", "DIM", new Integer(inSegmentPosition), new Integer(2), new Integer(3), new Integer(0), new Integer(15), new Integer(inSegmentOccurences), null);
        dimRules.add(ruleDIM4);
        EdiRule ruleDIM5 = new EdiRule("DIM_1", "DIM", new Integer(inSegmentPosition), new Integer(2), new Integer(4), new Integer(0), new Integer(15), new Integer(inSegmentOccurences), null);
        dimRules.add(ruleDIM5);
        return dimRules;
    }

    public void addErrorMessage(String inMsgKey, String inMsgValue) {
        this._segmentErrors.put(inMsgKey, inMsgValue);
    }

}
