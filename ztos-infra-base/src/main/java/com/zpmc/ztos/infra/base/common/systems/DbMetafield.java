package com.zpmc.ztos.infra.base.common.systems;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.DbMetafieldDO;
import com.zpmc.ztos.infra.base.business.enums.framework.*;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.utils.ScopedQueryUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
//import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang3.EnumUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DbMetafield extends DbMetafieldDO implements IFlexibleScope,
        IDataModelNotExtensible,
        IEntityUniquenessAware,
        IPlatformComponent {

    public static final IMetafieldId[] UNIQUE_FIELD_IDS = new IMetafieldId[]{IConfigSettingField.MFDO_ID, IConfigSettingField.MFDO_SCOPE, IConfigSettingField.MFDO_SCOPE_GKEY};

    @Nullable
    public IMetafieldId getMetafieldId() {
        return MetafieldIdFactory.valueOf(super.getMfdoId());
    }

    @Override
    public int getScopeLevel() {
        return this.getMfdoScope().intValue();
    }

    @Override
    public Serializable getScopeKey() {
        return this.getMfdoScopeGkey();
    }

    @Override
    @NotNull
    public IMetafieldId getScopeKeyFieldId() {
        return IConfigSettingField.MFDO_SCOPE_GKEY;
    }

    @Override
    @NotNull
    public IMetafieldId getScopeLevelFieldId() {
        return IConfigSettingField.MFDO_SCOPE;
    }

    @Override
    protected boolean shouldCheckDirty() {
        return false;
    }

    public IValueHolder getMfdoSelectionEntry() {
        ValueObject vo = new ValueObject(IFrameworkBizMetafield.MFDO_SELECTION_ENTRY.getFieldId());
        vo.setFieldValue(IConfigSettingField.MFDO_WIDGET_TYPE, (Object)this.getMfdoWidgetType());
        Set mfdoChoiceList = this.getMfdoChoiceList();
        if (mfdoChoiceList != null && !mfdoChoiceList.isEmpty()) {
            int i = 0;
            String[] choices = new String[mfdoChoiceList.size()];
            for (Object choice : mfdoChoiceList) {
                choices[i++] = ((DbMetafieldChoice)choice).getMfdchValue();
            }
            vo.setFieldValue(IConfigSettingField.MFDO_CHOICE_LIST, (Object)choices);
        }
        return vo;
    }

    public void setMfdoSelectionEntry(IValueHolder inVo) {
        ConfigurableWidgetTypeEnum fieldValue = (ConfigurableWidgetTypeEnum)inVo.getFieldValue(IConfigSettingField.MFDO_WIDGET_TYPE);
        this.setMfdoWidgetType(fieldValue);
        Object choiceObj = inVo.getFieldValue(IConfigSettingField.MFDO_CHOICE_LIST);
        if (choiceObj instanceof String[]) {
            this.setMfdoChoices((String[])choiceObj);
        }
    }

    private void setMfdoChoices(String[] inChoices) {
        HashSet<DbMetafieldChoice> choiceList = (HashSet<DbMetafieldChoice>) this.getMfdoChoiceList();
        if (choiceList == null) {
            choiceList = new HashSet<DbMetafieldChoice>();
        } else {
            choiceList.clear();
            HibernateApi.getInstance().flush();
        }
        for (int i = 0; i < inChoices.length; ++i) {
            String choice = inChoices[i];
            DbMetafieldChoice dbChoice = new DbMetafieldChoice();
            dbChoice.setMfdchValue(choice);
            dbChoice.setMfdchMetafield(this);
            choiceList.add(dbChoice);
        }
    }

    @Override
    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
        if (inFieldId == IFrameworkBizMetafield.MFDO_SELECTION_ENTRY) {
            this.setMfdoSelectionEntry((IValueHolder)inFieldValue);
        } else {
            super.setFieldValue(inFieldId, inFieldValue);
        }
    }

    @Override
    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        if (this.getMfdoImportance() == null) {
            IMetafield metafield = Roastery.getMetafieldDictionary().findMetafield(this.getMetafieldId());
            if (metafield != null) {
                this.setMfdoImportance(metafield.getImportance());
            } else {
                throw BizFailure.create("Creating a field override which does not exist in the business tier. You must make sure metafield definition is registered in both tiers. ");
            }
        }
    }

    public LetterCaseEnum getLetterCase() {
        LetterCaseValidationEnum persistentCase = this.getMfdoCaseValidation();
        LetterCaseEnum letterCase = null;
        if (persistentCase != null) {
            letterCase = (LetterCaseEnum) EnumUtils.getEnum(LetterCaseEnum.class, (String)persistentCase.getName());
        }
        return letterCase;
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        bizViolation = this.validateUniqueKeys(bizViolation);
        if (inChanges.hasFieldChange(IConfigSettingField.MFDO_MAX_CHARS)) {
            Long maxChars = (Long)inChanges.getFieldChange(IConfigSettingField.MFDO_MAX_CHARS).getNewValue();
            IMetafield metafield = Roastery.getMetafieldDictionary().findMetafield(this.getMetafieldId());
            int dbDize = metafield.getDbSize();
            if (dbDize != -1 && (maxChars.intValue() > dbDize || maxChars.intValue() < 1)) {
                bizViolation = BizViolation.createFieldViolation(IFrameworkPropertyKeys.VALIDATION__NOT_IN_RANGE, bizViolation, IConfigSettingField.MFDO_MAX_CHARS, maxChars, 1L, dbDize);
            }
        }
        return bizViolation;
    }

    public Long getMfdoOptionTableKey() {
        return this.getMfdoGkey();
    }

    @Override
    public IMetafieldId[] getUniqueKeyFieldIds() {
        return UNIQUE_FIELD_IDS;
    }

    @Override
    public IMetafieldId getPrimaryBusinessKey() {
        return IConfigSettingField.MFDO_ID;
    }

    @Override
    public String getTypeId() {
        return FrameworkPlatformComponentTypeEnum.METAFIELD.name();
    }

    @Override
    public String getComponentBusinessName() {
        return this.getMfdoId();
    }

    @Override
    public String getComponentDesc() {
        if (StringUtils.isNotEmpty((String)this.getMfdoLongName())) {
            return this.getMfdoLongName();
        }
        return this.getMfdoComment();
    }

    @Override
    public String getComponentContent() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return this.getMfdoImportance() != null && FieldImportanceEnum.NOT_USED != this.getMfdoImportance();
    }

    @Override
    public CustomizationComponentContentTypeEnum getComponentContentType() {
        return CustomizationComponentContentTypeEnum.CONFIGURATION;
    }

    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public String getCreator() {
        return null;
    }

    @Override
    public Date getChanged() {
        return null;
    }

    @Override
    public String getChanger() {
        return null;
    }

    @Override
    public String getComponentSubType() {
        IMetafieldDictionary mfd = Roastery.getMetafieldDictionary();
        IMetafield field = mfd.findMetafield(this.getMetafieldId());
        if (field != null) {
            return field.getFieldCreationType().name();
        }
        return null;
    }

    @Override
    public String getScopeId() {
        return ScopedQueryUtils.getScopeName(this.getScopeLevel(), this.getScopeKey());
    }

    @Override
    public String toString() {
        return super.toString() + "  " + this.getMfdoId();
    }
}
