package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.CommodityDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.lang.ObjectUtils;

import java.io.Serializable;
import java.util.logging.Logger;


public class Commodity extends CommodityDO {

    private static final CharSequence NEWLINE = "\n";
    private static final Logger LOGGER = Logger.getLogger(Commodity.class.getName());

    public Commodity(EntitySet inCmdyRefSet, String inCmdyId) {
        this();
        this.setCmdyScope(inCmdyRefSet);
        this.setCmdyId(inCmdyId);
        this.setCmdyLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public Commodity() {
        this.setCmdyIsArchetype(Boolean.FALSE);
        this.setCmdyIsFood(Boolean.FALSE);
        this.setCmdyIsFilm(Boolean.FALSE);
        this.setCmdyIsOils(Boolean.FALSE);
        this.setCmdyIsExclusiveReeferTower(Boolean.FALSE);
        this.setCmdyIsHeatSensitive(Boolean.FALSE);
        this.setCmdyIsTaintable(Boolean.FALSE);
        this.setCmdyIsTainting(Boolean.FALSE);
        this.setCmdyIsValuables(Boolean.FALSE);
        this.setCmdyIcon1(new Long(0L));
        this.setCmdyIcon2(new Long(0L));
        this.setCmdyVcgRatio(new Double(0.0));
        this.setCmdyHazValidated(Boolean.FALSE);
        this.setCmdyIsNonReefer(Boolean.FALSE);
        this.setCmdyTempValidated(Boolean.FALSE);
        this.setCmdyTempRangeValidated(Boolean.FALSE);
        this.setCmdyLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setCmdyLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getCmdyLifeCycleState();
    }

    public static Commodity findCommodity(String inCmdyId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Commodity").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.CMDY_ID, (Object)inCmdyId));
        return (Commodity)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);

    }

    public static Commodity findCommodityProxy(String inCmdyId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Commodity").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.CMDY_ID, (Object)inCmdyId));
        Serializable[] cmdyGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (cmdyGkey == null || cmdyGkey.length == 0) {
            return null;
        }
        if (cmdyGkey.length == 1) {
            return (Commodity)HibernateApi.getInstance().load(Commodity.class, cmdyGkey[0]);
        }
        throw BizFailure.create((IPropertyKey)IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(cmdyGkey.length), (Object)dq);
    }

    public static Commodity createCommodity(String inCmdyId) {
        Commodity commodity = new Commodity();
        commodity.setCmdyId(inCmdyId);
        commodity.setCmdyDescription(inCmdyId);
        commodity.setCmdyOneCharCode("" + inCmdyId.charAt(0));
        commodity.setCmdyShortName(inCmdyId);
        commodity.setCmdyVcgRatio(new Double(0.5));
        HibernateApi.getInstance().save((Object)commodity);
        return commodity;
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        super.preProcessInsert(inOutMoreChanges);
        if (this.getCmdyId() != null) {
            inOutMoreChanges.setFieldChange(IArgoRefField.CMDY_ID, (Object)this.getCmdyId().trim());
        }
    }

    public BizViolation validateDeletion() {
        BizViolation bv = this.checkIfArcheTypeReferred();
        if (bv != null) {
            return bv;
        }
        if (this.equals(this.getCmdyArchetype())) {
            this.setCmdyArchetype(null);
            this.setCmdyIsArchetype(Boolean.FALSE);
            HibernateApi.getInstance().update((Object)this);
            HibernateApi.getInstance().flush();
        }
        return null;
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
        super.applyFieldChanges(inFieldChanges);
        if (inFieldChanges != null && inFieldChanges.hasFieldChange(IArgoRefField.CMDY_IS_ARCHETYPE)) {
            Boolean isArchetype;
            Boolean bl = isArchetype = this.getCmdyIsArchetype() != null ? this.getCmdyIsArchetype() : Boolean.FALSE;
            if (isArchetype.booleanValue() && !this.equals(this.getCmdyArchetype())) {
                this.setCmdyArchetype(this);
            } else if (this.equals(this.getCmdyArchetype()) && !isArchetype.booleanValue()) {
                this.setCmdyIsArchetype(Boolean.TRUE);
            }
        }
    }

    public void setFieldValue(IMetafieldId inMetafieldId, Object inFieldValue) {
        DataSourceEnum curSrc = ContextHelper.getThreadDataSource();
        if (IArgoRefField.CMDY_ARCHETYPE.equals((Object)inMetafieldId) && !DataSourceEnum.SNX.equals((Object)curSrc)) {
            Boolean isArchetype;
            Boolean bl = isArchetype = this.getCmdyIsArchetype() != null ? this.getCmdyIsArchetype() : Boolean.FALSE;
            if (!isArchetype.booleanValue()) {
                super.setFieldValue(inMetafieldId, inFieldValue);
            }
        } else {
            super.setFieldValue(inMetafieldId, inFieldValue);
        }
    }

    public static Commodity findOrCreateCommodity(String inCmdyId) {
        Commodity commodity;
        if (inCmdyId != null) {
            inCmdyId = inCmdyId.trim();
        }
        if ((commodity = Commodity.findCommodity(inCmdyId)) == null) {
            commodity = Commodity.createCommodity(inCmdyId);
        }
        return commodity;
    }

    public static Commodity findOrCreateCommodityProxy(String inCmdyId) {
        Commodity commodity;
        if (inCmdyId != null) {
            inCmdyId = inCmdyId.trim();
        }
        if ((commodity = Commodity.findCommodityProxy(inCmdyId)) == null) {
            commodity = Commodity.createCommodity(inCmdyId);
        }
        return commodity;
    }

    public static Commodity findOrCreateCommodity(String inCmdyId, String inCmdyName, String inCmdyDescription) {
        Commodity commodity;
        if (inCmdyId != null) {
            inCmdyId = inCmdyId.trim();
        }
        if ((commodity = Commodity.findCommodity(inCmdyId)) == null) {
            commodity = Commodity.createCommodity(inCmdyId);
            commodity.setCmdyShortName(inCmdyName != null ? inCmdyName : commodity.getCmdyId());
            commodity.setCmdyDescription(inCmdyDescription != null ? inCmdyDescription : commodity.getCmdyShortName());
        }
        return commodity;
    }

    public static Commodity findOrCreateCommodityProxy(String inCmdyId, String inCmdyName, String inCmdyDescription) {
        Commodity commodity;
        if (inCmdyId != null) {
            inCmdyId = inCmdyId.trim();
        }
        if ((commodity = Commodity.findCommodityProxy(inCmdyId)) == null) {
            commodity = Commodity.createCommodity(inCmdyId);
            String shortName = inCmdyName != null ? inCmdyName : inCmdyId;
            commodity.setCmdyShortName(shortName);
            commodity.setCmdyDescription(inCmdyDescription != null ? inCmdyDescription : shortName);
        }
        return commodity;
    }

    public String toString() {
        return "CommodityId:" + this.getCmdyId();
    }

    public void validateHazards(boolean inIsHazardous) throws BizViolation {
        if (inIsHazardous) {
            if (Boolean.FALSE.equals(this.isHazValidated()) || this.getCmdyUnNbr() == null) {
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.HAZ_NOT_REQUIRED_FOR_COMMODITY, null, (Object)this.getCmdyShortName());
            }
        } else if (Boolean.TRUE.equals(this.isHazValidated()) || this.getCmdyUnNbr() != null) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.HAZ_REQUIRED_FOR_COMMODITY, null, (Object)this.getCmdyShortName());
        }
    }

    public void validateTempOutOfRange(Double inTemp) throws BizViolation {
        if (!this.getCmdyTempRangeValidated().booleanValue()) {
            return;
        }
        if (inTemp == null) {
            return;
        }
        Double minTemp = this.getCmdyTempMin();
        Double maxTemp = this.getCmdyTempMax();
        if (minTemp != null && inTemp < minTemp) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TEMP_TOO_LOW_FOR_COMMODITY, null, (Object)inTemp, (Object)this.getCmdyShortName(), (Object)minTemp);
        }
        if (maxTemp != null && inTemp > maxTemp) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TEMP_TOO_HIGH_FOR_COMMODITY, null, (Object)inTemp, (Object)this.getCmdyShortName(), (Object)maxTemp);
        }
    }

    private void validateIdealTemp(Double inTemp) throws BizViolation {
        Double idealTemp = this.getCmdyTempIdeal();
        if (inTemp == null || idealTemp == null) {
            return;
        }
        if (idealTemp.compareTo(inTemp) != 0) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TEMP_NOT_IDEAL_TEMP_FOR_COMMODITY, null, (Object)inTemp, (Object)this.getCmdyShortName(), (Object)idealTemp);
        }
    }

    public void validateTempRequired(Double inTemp) throws BizViolation {
        if (this.getCmdyIsNonReefer().booleanValue()) {
            if (inTemp != null) {
                throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TEMP_NOT_REQUIRED_FOR_COMMODITY, null, (Object)this.getCmdyShortName());
            }
        } else if (inTemp == null && this.isTemperatureControlled() && (this.getCmdyTempRangeValidated().booleanValue() || this.getCmdyTempValidated().booleanValue())) {
            throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.TEMP_REQUIRED_FOR_COMMODITY, null, (Object)this.getCmdyShortName());
        }
        this.validateTempOutOfRange(inTemp);
        if (this.getCmdyTempValidated().booleanValue()) {
            this.validateIdealTemp(inTemp);
        }
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        Commodity prevCmdyArchType;
        String chkCmdyDesc;
        BizViolation bv = super.validateChanges(inChanges);
        bv = this.checkRequiredField(bv, IArgoRefField.CMDY_ID);
        bv = this.checkRequiredField(bv, IArgoRefField.CMDY_SHORT_NAME);
        bv = this.checkRequiredField(bv, IArgoRefField.CMDY_ONE_CHAR_CODE);
        bv = this.checkRequiredField(bv, IArgoRefField.CMDY_VCG_RATIO);
        double vcgRatio = this.getCmdyVcgRatio();
        if (vcgRatio < 0.0 || vcgRatio > 1.0) {
            bv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.INVALID_VCG_RATIO, null, (IMetafieldId) IArgoRefField.CMDY_VCG_RATIO, null);
        }
        if (this.getCmdyTempMin() != null && this.getCmdyTempMax() != null && this.getCmdyTempMin() > this.getCmdyTempMax()) {
            bv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.MIN_TEMP_GREATER, null, (IMetafieldId) IArgoRefField.CMDY_TEMP_MIN, (Object)this.getCmdyTempMin(), (Object)this.getCmdyTempMax());
        }
        if ((chkCmdyDesc = this.getCmdyDescription()) != null && chkCmdyDesc.contains(NEWLINE)) {
            bv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.INVALID_DESCRIPTION_CONTAINS_NEWLINE_CHARACTER, null, null);
        }
        if (inChanges.hasFieldChange(IArgoRefField.CMDY_ARCHETYPE) && ObjectUtils.equals((Object)(prevCmdyArchType = (Commodity)inChanges.getFieldChange(IArgoRefField.CMDY_ARCHETYPE).getPriorValue()), (Object)this)) {
            bv = this.checkIfArcheTypeReferred();
        }
        return bv;
    }

    public boolean isFood() {
        return this.getCmdyIsFood();
    }

    public boolean isFilm() {
        return this.getCmdyIsFilm();
    }

    public boolean isOils() {
        return this.getCmdyIsOils();
    }

    public boolean isTaintable() {
        return this.getCmdyIsTaintable();
    }

    public boolean isTainting() {
        return this.getCmdyIsTainting();
    }

    public boolean isExclusiveReeferTower() {
        return this.getCmdyIsExclusiveReeferTower();
    }

    public boolean isValuables() {
        return this.getCmdyIsValuables();
    }

    public boolean isHeatSensitive() {
        return this.getCmdyIsHeatSensitive();
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.CMDY_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.CMDY_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public void updateCmdyTempMax(Double inTempMax) {
        this.setCmdyTempMax(inTempMax);
    }

    public void updateCmdyTempMin(Double inTempMin) {
        this.setCmdyTempMin(inTempMin);
    }

    public void updateHazValidated(Boolean inHazValidated) {
        this.setCmdyHazValidated(inHazValidated);
    }

    public Boolean isHazValidated() {
        return this.getCmdyHazValidated();
    }

    public void updateTempValidated(Boolean inTempValidated) {
        this.setCmdyTempValidated(inTempValidated);
    }

    public void updateTempRangeValidated(Boolean inTempRangeValidated) {
        this.setCmdyTempRangeValidated(inTempRangeValidated);
    }

    public boolean isTemperatureControlled() {
        return this.getCmdyTempMin() != null || this.getCmdyTempMax() != null || this.getCmdyTempIdeal() != null;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    private BizViolation checkIfArcheTypeReferred() {
        BizViolation bv = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Commodity").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.CMDY_ARCHETYPE, (Object)this.getCmdyGkey()));
        if (HibernateApi.getInstance().findCountByDomainQuery(dq) > 1) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.CMDY_ARCH_IS_REFERENCED, null, (Object)this.getCmdyId());
        }
        return bv;
    }

}
