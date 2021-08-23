package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetSubtypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.GuiWidgetTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.ValidationDataTypeEnum;
import com.zpmc.ztos.infra.base.common.model.GuiWidgetEntry;
import com.zpmc.ztos.infra.base.common.model.LovExtraEntry;

public interface IMetafieldDictionaryLegacy {
    @Deprecated
    public GuiWidgetEntry findGuiWidgetEntry(IMetafieldId var1);

    @Deprecated
    public LovExtraEntry findFieldLovExtraEntry(IMetafieldId var1);

    @Deprecated
    public boolean isFieldValidationRequired(IMetafieldId var1);

    @Deprecated
    public int findFieldGuiWidgetWidthInChars(IMetafieldId var1);

    @Deprecated
    public int findFieldValidationMaxLength(IMetafieldId var1);

    public int findFieldDbSize(IMetafieldId var1);

    @Deprecated
    public ValidationDataTypeEnum findFieldValidationDataType(IMetafieldId var1);

    @Deprecated
    public GuiWidgetTypeEnum findFieldGuiWidgetType(IMetafieldId var1);

    @Deprecated
    public GuiWidgetSubtypeEnum findFieldGuiWidgetSubType(IMetafieldId var1);

    @Deprecated
    public String findFieldGuiWidgetLovCollectionKey(IMetafieldId var1);

    @Deprecated
    public IPropertyKey findFieldShortLabelKey(IMetafieldId var1);

    @Deprecated
    public IPropertyKey findFieldLongLabelKey(IMetafieldId var1);

    @Deprecated
    public ValidationDataTypeEnum guessFieldValidationDataType(IMetafieldId var1);

    @Deprecated
    public IValidationModel findValidationEntry(IMetafieldId var1);
}
