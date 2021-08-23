package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.argo.MetafieldCreationTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.DbTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FieldImportanceEnum;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.common.model.GuiTableEntry;
import com.zpmc.ztos.infra.base.common.model.GuiWidgetEntry;
import com.zpmc.ztos.infra.base.common.model.ValidationEntry;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import com.sun.istack.Nullable;

public interface IMetafield {
    public static final int DB_SIZE_UNSPECIFIED = -1;

    public IMetafieldId getMetafieldId();

    public String getFieldId();

    public MetafieldCreationTypeEnum getFieldCreationType();

    public Class getValueClass();

    public boolean isPrimaryKey();

    public boolean isForeignKey();

    public String getEntityName();

    @Deprecated
    public DbTypeEnum getDbType();

    public int getDbSize();

    public FuzzyBoolean getDbNullable();

    public FuzzyBoolean getValidationRequired();

    public boolean isValidationRequired();

    public ValidationEntry getValidationEntry();

    public GuiWidgetEntry getGuiWidget();

    public GuiTableEntry getGuiTable();

    public String getDefaultValue();

    public IPropertyKey getLongLabelKey();

    public IPropertyKey getShortLabelKey();

    public IPropertyKey getHelpKey();

    public IPropertyKey getDescriptionKey();

    public IMeasured getMeasured();

    @Nullable
//    public IMetafieldGroups getGroups();

    public boolean isGroupMember(@NotNull String var1);

    @Deprecated
    @Nullable
    public String getGroupId();

    public String getFlexGroupCategory();

    public boolean isSyntheticField();

    public boolean isChartField();

    public boolean isImageField();

    public boolean isDisplayedAsImageInTable();

    public String getSourceBean();

    public MetafieldIdList getDepends();

    public boolean isComponentType();

    @Nullable
    public IMetafield getParentComponent();

    public FieldImportanceEnum getImportance();

    public boolean isHidden();

    public boolean isVisibilityClientConfigurable();

    public boolean isCustomField();

    public IMetafield getAliases();

    public IMetafield getMappedEntry();

    public MetafieldIdList getMappedByFields();

    public String getDebugMergeHistory();

    public boolean isSecured();

}
