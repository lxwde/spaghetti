package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.ValidationDataTypeEnum;

public interface IValidationModel {

    public static final int LENGTH_UNDEFINED = -1;

    public ValidationDataTypeEnum getDataType();

    public String getPattern();

    public String getAlternatePatternErrorMsg();

    public String getMinValue();

    public String getMaxValue();

    public int getMinLength();

    public int getMaxLength();

    public LetterCaseEnum getCase();
}
