package com.zpmc.ztos.infra.base.business.edi;

import java.util.ArrayList;
import java.util.List;

public class SegmentsAnsi004020 {

    protected List createISARules() {
        ArrayList<EdiRule> isaRules = new ArrayList<EdiRule>();
        EdiRule ruleISA1 = new EdiRule("ISA_1", "ISA", new Integer(1), new Integer(1), null, new Integer(2), new Integer(2), new Integer(1), null);
        isaRules.add(ruleISA1);
        EdiRule ruleISA3 = new EdiRule("ISA_3", "ISA", new Integer(1), new Integer(3), null, new Integer(2), new Integer(2), new Integer(1), null);
        isaRules.add(ruleISA3);
        EdiRule ruleISA5 = new EdiRule("ISA_5", "ISA", new Integer(1), new Integer(5), null, new Integer(2), new Integer(2), new Integer(1), null);
        isaRules.add(ruleISA5);
        EdiRule ruleISA7 = new EdiRule("ISA_7", "ISA", new Integer(1), new Integer(7), null, new Integer(2), new Integer(2), new Integer(1), null);
        isaRules.add(ruleISA7);
        EdiRule ruleISA9 = new EdiRule("ISA_9", "ISA", new Integer(1), new Integer(9), null, new Integer(6), new Integer(6), new Integer(1), null);
        isaRules.add(ruleISA9);
        EdiRule ruleISA10 = new EdiRule("ISA_10", "ISA", new Integer(1), new Integer(10), null, new Integer(4), new Integer(4), new Integer(1), null);
        isaRules.add(ruleISA10);
        EdiRule ruleISA11 = new EdiRule("ISA_11", "ISA", new Integer(1), new Integer(11), null, new Integer(1), new Integer(1), new Integer(1), null);
        isaRules.add(ruleISA11);
        EdiRule ruleISA12 = new EdiRule("ISA_12", "ISA", new Integer(1), new Integer(12), null, new Integer(5), new Integer(5), new Integer(1), null);
        isaRules.add(ruleISA12);
        EdiRule ruleISA13 = new EdiRule("ISA_13", "ISA", new Integer(1), new Integer(13), null, new Integer(9), new Integer(9), new Integer(1), null);
        isaRules.add(ruleISA13);
        EdiRule ruleISA14 = new EdiRule("ISA_14", "ISA", new Integer(1), new Integer(14), null, new Integer(1), new Integer(1), new Integer(1), null);
        isaRules.add(ruleISA14);
        EdiRule ruleISA15 = new EdiRule("ISA_15", "ISA", new Integer(1), new Integer(15), null, new Integer(1), new Integer(1), new Integer(1), null);
        isaRules.add(ruleISA15);
        EdiRule ruleISA16 = new EdiRule("ISA_16", "ISA", new Integer(1), new Integer(16), null, new Integer(1), new Integer(1), new Integer(1), null);
        isaRules.add(ruleISA16);
        return isaRules;
    }

    protected List createGSRules() {
        ArrayList<EdiRule> isaRules = new ArrayList<EdiRule>();
        EdiRule ruleGS1 = new EdiRule("GS_1", "GS", new Integer(1), new Integer(1), null, new Integer(2), new Integer(2), new Integer(1), null);
        isaRules.add(ruleGS1);
        EdiRule ruleGS4 = new EdiRule("GS_4", "GS", new Integer(1), new Integer(4), null, new Integer(8), new Integer(8), new Integer(1), null);
        isaRules.add(ruleGS4);
        EdiRule ruleGS5 = new EdiRule("GS_5", "GS", new Integer(1), new Integer(5), null, new Integer(4), new Integer(8), new Integer(1), null);
        isaRules.add(ruleGS5);
        EdiRule ruleGS6 = new EdiRule("GS_6", "GS", new Integer(1), new Integer(6), null, new Integer(1), new Integer(9), new Integer(1), null);
        isaRules.add(ruleGS6);
        EdiRule ruleGS7 = new EdiRule("GS_7", "GS", new Integer(1), new Integer(7), null, new Integer(1), new Integer(2), new Integer(1), null);
        isaRules.add(ruleGS7);
        EdiRule ruleGS8 = new EdiRule("GS_8", "GS", new Integer(1), new Integer(8), null, new Integer(1), new Integer(12), new Integer(1), null);
        isaRules.add(ruleGS8);
        return isaRules;
    }

    protected List createSTRules() {
        ArrayList<EdiRule> isaRules = new ArrayList<EdiRule>();
        EdiRule ruleST1 = new EdiRule("ST_1", "ST", new Integer(1), new Integer(1), null, new Integer(3), new Integer(3), new Integer(1), null);
        isaRules.add(ruleST1);
        EdiRule ruleST2 = new EdiRule("ST_2", "ST", new Integer(1), new Integer(2), null, new Integer(4), new Integer(9), new Integer(1), null);
        isaRules.add(ruleST2);
        return isaRules;
    }

    protected List createQ5Rules() {
        ArrayList<EdiRule> q5Rules = new ArrayList<EdiRule>();
        EdiRule ruleQ51 = new EdiRule("Q5_1", "Q5", new Integer(1), new Integer(1), null, new Integer(1), new Integer(2), new Integer(1), null);
        q5Rules.add(ruleQ51);
        EdiRule ruleQ52 = new EdiRule("Q5_2", "Q5", new Integer(1), new Integer(2), null, new Integer(8), new Integer(8), new Integer(1), null);
        q5Rules.add(ruleQ52);
        EdiRule ruleQ53 = new EdiRule("Q5_3", "Q5", new Integer(1), new Integer(3), null, new Integer(4), new Integer(8), new Integer(1), null);
        q5Rules.add(ruleQ53);
        EdiRule ruleQ54 = new EdiRule("Q5_4", "Q5", new Integer(1), new Integer(4), null, new Integer(2), new Integer(2), new Integer(1), null);
        q5Rules.add(ruleQ54);
        return q5Rules;
    }

    protected List createN7Rules() {
        ArrayList<EdiRule> n7Rules = new ArrayList<EdiRule>();
        EdiRule ruleN71 = new EdiRule("N7_1", "N7", new Integer(1), new Integer(1), null, new Integer(1), new Integer(4), new Integer(1), null);
        n7Rules.add(ruleN71);
        EdiRule ruleN72 = new EdiRule("N7_2", "N7", new Integer(1), new Integer(2), null, new Integer(1), new Integer(10), new Integer(1), null);
        n7Rules.add(ruleN72);
        EdiRule ruleN73 = new EdiRule("N7_3", "N7", new Integer(1), new Integer(3), null, new Integer(1), new Integer(10), new Integer(1), null);
        n7Rules.add(ruleN73);
        EdiRule ruleN74 = new EdiRule("N7_4", "N7", new Integer(1), new Integer(4), null, new Integer(1), new Integer(2), new Integer(1), null);
        n7Rules.add(ruleN74);
        return n7Rules;
    }

    protected List createDTMRules() {
        ArrayList<EdiRule> dtmRules = new ArrayList<EdiRule>();
        EdiRule ruleDTM1 = new EdiRule("DTM_1", "DTM", new Integer(1), new Integer(1), null, new Integer(3), new Integer(3), new Integer(1), null);
        dtmRules.add(ruleDTM1);
        EdiRule ruleDTM2 = new EdiRule("DTM_2", "DTM", new Integer(1), new Integer(2), null, new Integer(8), new Integer(8), new Integer(1), null);
        dtmRules.add(ruleDTM2);
        EdiRule ruleDTM3 = new EdiRule("DTM_3", "DTM", new Integer(1), new Integer(3), null, new Integer(4), new Integer(8), new Integer(1), null);
        dtmRules.add(ruleDTM3);
        return dtmRules;
    }

    protected List createW2Rules() {
        ArrayList<EdiRule> w2Rules = new ArrayList<EdiRule>();
        EdiRule ruleW21 = new EdiRule("W2_1", "W2", new Integer(1), new Integer(1), null, new Integer(1), new Integer(4), new Integer(1), null);
        w2Rules.add(ruleW21);
        EdiRule ruleW22 = new EdiRule("W2_2", "W2", new Integer(1), new Integer(2), null, new Integer(1), new Integer(10), new Integer(1), null);
        w2Rules.add(ruleW22);
        EdiRule ruleW23 = new EdiRule("W2_3", "W2", new Integer(1), new Integer(3), null, new Integer(1), new Integer(30), new Integer(1), null);
        w2Rules.add(ruleW23);
        EdiRule ruleW24 = new EdiRule("W2_4", "W2", new Integer(1), new Integer(4), null, new Integer(2), new Integer(2), new Integer(1), null);
        w2Rules.add(ruleW24);
        EdiRule ruleW25 = new EdiRule("W2_4", "W2", new Integer(1), new Integer(5), null, new Integer(1), new Integer(2), new Integer(1), null);
        w2Rules.add(ruleW25);
        return w2Rules;
    }

    protected List createR4Rules() {
        ArrayList<EdiRule> r4Rules = new ArrayList<EdiRule>();
        EdiRule ruleR41 = new EdiRule("R4_1", "R4", new Integer(1), new Integer(1), null, new Integer(1), new Integer(1), null, null);
        r4Rules.add(ruleR41);
        EdiRule ruleR42 = new EdiRule("R4_2", "R4", new Integer(1), new Integer(2), null, new Integer(1), new Integer(2), null, null);
        r4Rules.add(ruleR42);
        EdiRule ruleR43 = new EdiRule("R4_3", "R4", new Integer(1), new Integer(3), null, new Integer(1), new Integer(30), null, null);
        r4Rules.add(ruleR43);
        EdiRule ruleR44 = new EdiRule("R4_4", "R4", new Integer(1), new Integer(4), null, new Integer(2), new Integer(24), null, null);
        r4Rules.add(ruleR44);
        return r4Rules;
    }

    protected List createN1Rules() {
        ArrayList<EdiRule> n1Rules = new ArrayList<EdiRule>();
        EdiRule ruleN11 = new EdiRule("R1_1", "N1", new Integer(1), new Integer(1), null, new Integer(2), new Integer(3), new Integer(1), null);
        n1Rules.add(ruleN11);
        EdiRule ruleN12 = new EdiRule("N1_2", "N1", new Integer(1), new Integer(3), null, new Integer(1), new Integer(2), new Integer(1), null);
        n1Rules.add(ruleN12);
        EdiRule ruleN13 = new EdiRule("N1_3", "N1", new Integer(1), new Integer(4), null, new Integer(2), new Integer(80), new Integer(1), null);
        n1Rules.add(ruleN13);
        return n1Rules;
    }

    protected List createL0Rules() {
        ArrayList<EdiRule> l0Rules = new ArrayList<EdiRule>();
        EdiRule ruleL01 = new EdiRule("L0_1", "L0", new Integer(1), new Integer(1), null, new Integer(1), new Integer(3), new Integer(1), null);
        l0Rules.add(ruleL01);
        return l0Rules;
    }

    protected List createSERules() {
        ArrayList<EdiRule> seRules = new ArrayList<EdiRule>();
        EdiRule ruleSE1 = new EdiRule("SE_1", "SE", new Integer(1), new Integer(1), null, new Integer(1), new Integer(10), new Integer(1), null);
        seRules.add(ruleSE1);
        EdiRule ruleSE2 = new EdiRule("SE_2", "SE", new Integer(1), new Integer(2), null, new Integer(4), new Integer(9), new Integer(1), null);
        seRules.add(ruleSE2);
        return seRules;
    }

    protected List createM7Rules() {
        ArrayList m7Rules = new ArrayList();
        return m7Rules;
    }

    protected List createH3Rules() {
        ArrayList isaRules = new ArrayList();
        return isaRules;
    }

    protected List createH1Rules() {
        ArrayList isaRules = new ArrayList();
        return isaRules;
    }

    protected List createL3Rules() {
        ArrayList isaRules = new ArrayList();
        return isaRules;
    }

    protected List createV1Rules() {
        ArrayList<EdiRule> v1Rules = new ArrayList<EdiRule>();
        EdiRule ruleV11 = new EdiRule("V1_1", "V1", 1, 1, null, 1, 8, null, null);
        v1Rules.add(ruleV11);
        EdiRule ruleV12 = new EdiRule("V1_2", "V1", 1, 2, null, 2, 28, null, null);
        v1Rules.add(ruleV12);
        EdiRule ruleV13 = new EdiRule("V1_3", "V1", 1, 4, null, 2, 10, null, null);
        v1Rules.add(ruleV13);
        EdiRule ruleV14 = new EdiRule("V1_4", "V1", 1, 5, null, 2, 4, null, null);
        v1Rules.add(ruleV14);
        EdiRule ruleV15 = new EdiRule("V1_5", "V1", 1, 7, null, 2, 2, null, null);
        v1Rules.add(ruleV15);
        EdiRule ruleV16 = new EdiRule("V1_6", "V1", 1, 8, null, 1, 1, null, null);
        v1Rules.add(ruleV16);
        return v1Rules;
    }

    protected List createV9Rules() {
        ArrayList<EdiRule> v9Rules = new ArrayList<EdiRule>();
        EdiRule ruleV91 = new EdiRule("V9_1", "V9", 1, 1, null, 3, 3, null, null);
        v9Rules.add(ruleV91);
        EdiRule ruleV92 = new EdiRule("V9_2", "V9", 1, 3, null, 8, 8, null, null);
        v9Rules.add(ruleV92);
        EdiRule ruleV93 = new EdiRule("V9_3", "V9", 1, 4, null, 4, 8, null, null);
        v9Rules.add(ruleV93);
        return v9Rules;
    }

    protected List createGERules() {
        ArrayList<EdiRule> geRules = new ArrayList<EdiRule>();
        EdiRule ruleGE1 = new EdiRule("GE_1", "GE", new Integer(1), new Integer(1), null, new Integer(1), new Integer(6), new Integer(1), null);
        geRules.add(ruleGE1);
        EdiRule ruleGE2 = new EdiRule("GE_2", "GE", new Integer(1), new Integer(2), null, new Integer(1), new Integer(9), new Integer(1), null);
        geRules.add(ruleGE2);
        return geRules;
    }

    protected List createIEARules() {
        ArrayList<EdiRule> ieaRules = new ArrayList<EdiRule>();
        EdiRule ruleIEA1 = new EdiRule("IEA_1", "IEA", new Integer(1), new Integer(1), null, new Integer(1), new Integer(5), new Integer(1), null);
        ieaRules.add(ruleIEA1);
        EdiRule ruleIEA2 = new EdiRule("IEA_2", "IEA", new Integer(1), new Integer(2), null, new Integer(9), new Integer(9), new Integer(1), null);
        ieaRules.add(ruleIEA2);
        return ieaRules;
    }
}
