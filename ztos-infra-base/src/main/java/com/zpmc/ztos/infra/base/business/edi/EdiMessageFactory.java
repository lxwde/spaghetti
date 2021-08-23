package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.enums.edi.EdiMessageStandardEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IEdiAnsiMessageFormat;
import com.zpmc.ztos.infra.base.business.interfaces.IEdiEdifactMessageFormat;

import java.util.ArrayList;
import java.util.List;

public class EdiMessageFactory {

    private static final String D_95B = "D95B";
    private static final String D_00B = "D00B";
    private static final String V30_00B = "3000B";
    private static final String CODECO = "CODECO";
    private static final String COPARN = "COPARN";
    private static final String COPINO = "COPINO";
    private static final String COARRI = "COARRI";
    private static final String COPRAR = "COPRAR";
    private static final String BAPLIE = "BAPLIE";
    private static final String APERAK = "APERAK";
    private static final String TPFREP = "TPFREP";
    private static final String ANSI322 = "322";
    private static final String ANSI323 = "323";
    private static final String V00_4020 = "004020";
    private static final String V00_4010 = "004010";

    public List createEdiMsgRules(String inMsgName, String inMsgVersion, EdiMessageStandardEnum inMsgStandard) {
        List msgRules = new ArrayList();
        if (EdiMessageStandardEnum.EDIFACT.equals((Object)inMsgStandard)) {
            EdiMessageD00B msgD00B;
            if (D_95B.equalsIgnoreCase(inMsgVersion)) {
                EdiMessageD95B msgD95B = new EdiMessageD95B();
                msgRules = msgD95B.createMessageCommonRules();
                if (msgRules != null && !msgRules.isEmpty()) {
                    msgRules.addAll(this.createEdifactMsgDetailRules(msgD95B, inMsgName));
                }
            } else if ((D_00B.equalsIgnoreCase(inMsgVersion) || V30_00B.equalsIgnoreCase(inMsgVersion)) && (msgRules = (msgD00B = new EdiMessageD00B()).createMessageCommonRules()) != null && !msgRules.isEmpty()) {
                msgRules.addAll(this.createEdifactMsgDetailRules(msgD00B, inMsgName));
            }
        } else if (EdiMessageStandardEnum.X12.equals((Object)inMsgStandard)) {
            EdiMessageAnsi4010 msgAnsi4010;
            if (V00_4020.equalsIgnoreCase(inMsgVersion)) {
                EdiMessageAnsi4020 msgAnsi4020 = new EdiMessageAnsi4020();
                msgRules = msgAnsi4020.createMessageCommonRules();
                if (msgRules != null && !msgRules.isEmpty()) {
                    msgRules.addAll(this.createAnsiMsgDetailRules(msgAnsi4020, inMsgName));
                }
            } else if (V00_4010.equalsIgnoreCase(inMsgVersion) && (msgRules = (msgAnsi4010 = new EdiMessageAnsi4010()).createMessageCommonRules()) != null && !msgRules.isEmpty()) {
                msgRules.addAll(this.createAnsiMsgDetailRules(msgAnsi4010, inMsgName));
            }
        }
        return msgRules;
    }

    private List createEdifactMsgDetailRules(IEdiEdifactMessageFormat inMsgFormat, String inMsgName) {
        if (CODECO.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createCodecoDetailRules();
        }
        if (COPARN.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createCoparnDetailRules();
        }
        if (COPINO.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createCopinoDetailRules();
        }
        if (COARRI.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createCoarriDetailRules();
        }
        if (COPRAR.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createCoprarDetailRules();
        }
        if (BAPLIE.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createBaplieDetailRules();
        }
        if (APERAK.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createAperakDetailRules();
        }
        if (TPFREP.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createTpfrepDetailRules();
        }
        return null;
    }

    private List createAnsiMsgDetailRules(IEdiAnsiMessageFormat inMsgFormat, String inMsgName) {
        if (ANSI322.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createAnsi322DetailRules();
        }
        if (ANSI323.equalsIgnoreCase(inMsgName)) {
            return inMsgFormat.createAnsi323DetailRules();
        }
        return null;
    }
}
