package com.zpmc.ztos.infra.base.common.math;

import com.zpmc.ztos.infra.base.business.enums.argo.CheckDigitAlgorithmEnum;

public class CheckDigitAlgorithm {

    static int intValueV = Character.getNumericValue('V');
    static int intValueL = Character.getNumericValue('L');
    static int intValueB = Character.getNumericValue('B');

    public String getMappingClassName() {
        return "com.navis.argo.business.reference.UserTypeCheckDigitAlgoEnum";
    }

    public static String calc(CheckDigitAlgorithmEnum inType, String inEqId) {
        String checkDigit = null;
        if (inType == CheckDigitAlgorithmEnum.STANDARD) {
            checkDigit = CheckDigitAlgorithm.calcStandard(inEqId);
        } else if (inType == CheckDigitAlgorithmEnum.HAPPAGLLOYD) {
            checkDigit = CheckDigitAlgorithm.calcHappagLloyd(inEqId);
        } else if (CheckDigitAlgorithmEnum.PARTOFID.equals((Object)inType)) {
            checkDigit = null;
        }
        return checkDigit;
    }

    public static String calcStandard(String inEqId) {
        String checkDigit = null;
        if (inEqId == null) {
            checkDigit = null;
        } else if (inEqId.length() < 10) {
            checkDigit = null;
        } else {
            char[] chars = inEqId.substring(0, 10).toCharArray();
            int totalWeight = 0;
            try {
                for (int i = 0; i < 10; ++i) {
                    int charWeight = CheckDigitAlgorithm.weightedValue(i, chars[i]);
                    totalWeight += charWeight;
                }
                checkDigit = totalWeight % 11 == 10 ? new String("0") : new Integer(totalWeight % 11).toString();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return checkDigit;
    }

    private static int weightedValue(int inIdx, char inChar) throws Exception {
        int baseValue = 0;
        int charInt = Character.getNumericValue(inChar);
        if (charInt < 0 || charInt > 35) {
            throw new Exception("Check Digit not computable");
        }
        baseValue = charInt >= intValueV ? 3 + charInt : (charInt >= intValueL ? 2 + charInt : (charInt >= intValueB ? 1 + charInt : 0 + charInt));
        Double result = new Double((double)baseValue * Math.pow(2.0, inIdx));
        return result.intValue();
    }

    private static String calcHappagLloyd(String inEqId) {
        String checkDigit = null;
        if (inEqId == null) {
            checkDigit = null;
        } else if (inEqId.length() < 10) {
            checkDigit = null;
        } else if (inEqId.substring(0, 4).equals("HLCU")) {
            int totalWeight = 84;
            int multiplier = 8;
            try {
                String digits = inEqId.substring(4, 10);
                for (int i = 0; i < 6; ++i) {
                    int nthDigit = Integer.parseInt(digits.substring(i, i + 1));
                    int nthDigitWeight = nthDigit * (multiplier *= 2);
                    totalWeight += nthDigitWeight;
                }
                totalWeight *= 10;
                totalWeight /= 11;
                String temp = Integer.toString(++totalWeight);
                checkDigit = temp.substring(temp.length() - 1);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return checkDigit;
    }
}
