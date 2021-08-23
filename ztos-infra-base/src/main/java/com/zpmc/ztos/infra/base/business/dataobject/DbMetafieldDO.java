package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.AbstractMeasurementUnitEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.ConfigurableWidgetTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FieldImportanceEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LetterCaseValidationEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Set;

public class DbMetafieldDO extends DatabaseEntity implements Serializable {
    private Long mfdoGkey;
    private Long mfdoScope;
    private String mfdoScopeGkey;
    private String mfdoId;
    private FieldImportanceEnum mfdoImportance;
    private String mfdoShortName;
    private String mfdoLongName;
    private String mfdoHelpLabel;
    private String mfdoGroupId;
    private AbstractMeasurementUnitEnum mfdoMeasuredUserUnit;
    private Long mfdoMaxChars;
    private LetterCaseValidationEnum mfdoCaseValidation;
    private String mfdoExtraXMLDefinition;
    private ConfigurableWidgetTypeEnum mfdoWidgetType;
    private String mfdoComment;
    private Set mfdoChoiceList;

    @Override
    public Serializable getPrimaryKey() {
        return this.getMfdoGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getMfdoGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof DbMetafieldDO)) {
            return false;
        }
        DbMetafieldDO that = (DbMetafieldDO)other;
        return ((Object)id).equals(that.getMfdoGkey());
    }

    public int hashCode() {
        Long id = this.getMfdoGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getMfdoGkey() {
        return this.mfdoGkey;
    }

    public void setMfdoGkey(Long mfdoGkey) {
        this.mfdoGkey = mfdoGkey;
    }

    public Long getMfdoScope() {
        return this.mfdoScope;
    }

    public void setMfdoScope(Long mfdoScope) {
        this.mfdoScope = mfdoScope;
    }

    public String getMfdoScopeGkey() {
        return this.mfdoScopeGkey;
    }

    public void setMfdoScopeGkey(String mfdoScopeGkey) {
        this.mfdoScopeGkey = mfdoScopeGkey;
    }

    public String getMfdoId() {
        return this.mfdoId;
    }

    public void setMfdoId(String mfdoId) {
        this.mfdoId = mfdoId;
    }

    public FieldImportanceEnum getMfdoImportance() {
        return this.mfdoImportance;
    }

    public void setMfdoImportance(FieldImportanceEnum mfdoImportance) {
        this.mfdoImportance = mfdoImportance;
    }

    public String getMfdoShortName() {
        return this.mfdoShortName;
    }

    public void setMfdoShortName(String mfdoShortName) {
        this.mfdoShortName = mfdoShortName;
    }

    public String getMfdoLongName() {
        return this.mfdoLongName;
    }

    public void setMfdoLongName(String mfdoLongName) {
        this.mfdoLongName = mfdoLongName;
    }

    public String getMfdoHelpLabel() {
        return this.mfdoHelpLabel;
    }

    public void setMfdoHelpLabel(String mfdoHelpLabel) {
        this.mfdoHelpLabel = mfdoHelpLabel;
    }

    public String getMfdoGroupId() {
        return this.mfdoGroupId;
    }

    public void setMfdoGroupId(String mfdoGroupId) {
        this.mfdoGroupId = mfdoGroupId;
    }

    public AbstractMeasurementUnitEnum getMfdoMeasuredUserUnit() {
        return this.mfdoMeasuredUserUnit;
    }

    public void setMfdoMeasuredUserUnit(AbstractMeasurementUnitEnum mfdoMeasuredUserUnit) {
        this.mfdoMeasuredUserUnit = mfdoMeasuredUserUnit;
    }

    public Long getMfdoMaxChars() {
        return this.mfdoMaxChars;
    }

    public void setMfdoMaxChars(Long mfdoMaxChars) {
        this.mfdoMaxChars = mfdoMaxChars;
    }

    public LetterCaseValidationEnum getMfdoCaseValidation() {
        return this.mfdoCaseValidation;
    }

    public void setMfdoCaseValidation(LetterCaseValidationEnum mfdoCaseValidation) {
        this.mfdoCaseValidation = mfdoCaseValidation;
    }

    public String getMfdoExtraXMLDefinition() {
        return this.mfdoExtraXMLDefinition;
    }

    public void setMfdoExtraXMLDefinition(String mfdoExtraXMLDefinition) {
        this.mfdoExtraXMLDefinition = mfdoExtraXMLDefinition;
    }

    public ConfigurableWidgetTypeEnum getMfdoWidgetType() {
        return this.mfdoWidgetType;
    }

    public void setMfdoWidgetType(ConfigurableWidgetTypeEnum mfdoWidgetType) {
        this.mfdoWidgetType = mfdoWidgetType;
    }

    public String getMfdoComment() {
        return this.mfdoComment;
    }

    public void setMfdoComment(String mfdoComment) {
        this.mfdoComment = mfdoComment;
    }

    public Set getMfdoChoiceList() {
        return this.mfdoChoiceList;
    }

    protected void setMfdoChoiceList(Set mfdoChoiceList) {
        this.mfdoChoiceList = mfdoChoiceList;
    }
}
