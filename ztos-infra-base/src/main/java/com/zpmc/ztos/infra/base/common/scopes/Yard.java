package com.zpmc.ztos.infra.base.common.scopes;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.YardDO;
import com.zpmc.ztos.infra.base.business.enums.argo.LocTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.ScopeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.XpsImportStateEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.BinModelHelper;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Yard extends YardDO implements IScopeNodeEntity, ILocation, IServiceable {
//    private transient FieldHandler _fieldHandler;
    private static final Logger LOGGER = Logger.getLogger(Yard.class);
    public static final String CONTAINER_STOWAGE_BIN_CONTEXT = "STOWAGE_CONTAINERS";
    public static final String RAILCAR_STOWAGE_BIN_CONTEXT = "STOWAGE_RAILCARS";
    public static final String DUMMY_WORK_QUEUE_EXTENSION = "-Purgatory";

    public Yard() {
        this.setYrdLifeCycleState(LifeCycleStateEnum.ACTIVE);
        this.setYrdXpsImportState(XpsImportStateEnum.AWAIT_MANUAL_IMPORT);
    }

    @Override
    public LocTypeEnum getLocType() {
        return LocTypeEnum.YARD;
    }

    @Override
    public String getLocId() {
        return this.getYrdId();
    }

    @Override
    public String getCarrierVehicleId() {
        return this.getYrdId();
    }

    @Override
    public Long getLocGkey() {
        return this.getYrdGkey();
    }

    @Override
    public Facility getLocFacility() {
        return this.getYrdFacility();
    }

    @Override
    @Nullable
    public CarrierVisit getInboundCv() {
        return null;
    }

    @Override
    @Nullable
    public CarrierVisit getOutboundCv() {
        return null;
    }

    @Override
    public void verifyMoveToAllowed(IEntity inMovingEntity, String inSlot) throws BizViolation {
    }

    @Override
    public void verifyMoveFromAllowed(IEntity inMovingEntity, String inSlot) throws BizViolation {
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setYrdLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getYrdLifeCycleState();
    }

//    @Override
//    public FieldHandler getFieldHandler() {
//        return this._fieldHandler;
//    }

//    @Override
//    public void setFieldHandler(FieldHandler inFieldHandler) {
//        this._fieldHandler = inFieldHandler;
//    }

    public String toString() {
        return "yrd-" + this.getYrdId();
    }

    @Nullable
    public static Yard findYard(String inYrdId, Facility inFacility) {
        IDomainQuery dqYard = QueryUtils.createDomainQuery((String)"Yard");
        IDomainQuery dqYardID = dqYard.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_ID, (Object)inYrdId));
        IDomainQuery dq = dqYardID.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_FACILITY, (Object)inFacility.getPrimaryKey()));

//        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").
//                addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.YRD_ID, (Object)inYrdId)).
//                addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.YRD_FACILITY, (Object)inFacility.getPrimaryKey()));
        dq.setScopingEnabled(false);
        return (Yard) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Yard loadByGkey(Serializable inGkey) {
        return (Yard) Roastery.getHibernateApi().load(Yard.class, inGkey);
    }

    public static Yard findYardInComplex(String inLocId, Complex inComplex) {
        //IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoField.YRD_ID, (Object)inLocId)).addDqPredicate(PredicateFactory.eq((IMetafieldId)ArgoCompoundField.YRD_COMPLEX, (Object)inComplex.getPrimaryKey()));
        IDomainQuery dqYard = QueryUtils.createDomainQuery((String)"Yard");
        IDomainQuery dqYardID = dqYard.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_ID, (Object)inLocId));
        IDomainQuery dq    = dqYardID.addDqPredicate(PredicateFactory.eq((IMetafieldId) ArgoCompoundField.YRD_COMPLEX, (Object)inComplex.getPrimaryKey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        dq.setScopingEnabled(false);
        dq.setRequireTotalCount(false);
        List yardList = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (yardList != null && !yardList.isEmpty()) {
            Yard yard = (Yard)yardList.get(0);
            if (yardList.size() > 1) {
                LOGGER.warn((Object)("More than one yard <" + inLocId + "> found in the complex, the first one from facility <" + yard.getYrdFacility().getFcyId() + "> is returned"));
            }
            return (Yard)yardList.get(0);
        }
        return null;
    }

    public static Yard findOrCreateYard(String inYardId, String inYrdName, Facility inFacility) {
        Yard yard = Yard.findYard(inYardId, inFacility);
        if (yard == null) {
            yard = new Yard();
            yard.setYrdId(inYardId);
        }
        yard.setYrdFacility(inFacility);
        yard.setYrdName(inYrdName);
        HibernateApi.getInstance().saveOrUpdate((Object)yard);
        return yard;
    }

    public static List<Yard> findActiveYardsForFacility(Facility inFacility) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_FACILITY, (Object)inFacility.getPrimaryKey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public BizViolation validateChanges(FieldChanges inFieldChanges) {
        int entityLevel;
        ScopeCoordinates scope;
        int maxLevel;
        BizViolation bizViol = null;
        super.validateChanges(inFieldChanges);
        if (this.getPrimaryKey() == null && (maxLevel = (scope = ContextHelper.getThreadUserContext().getScopeCoordinate()).getMaxScopeLevel()) >= (entityLevel = this.getScopeEnum().getScopeLevel().intValue())) {
            IPropertyKey currentScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(maxLevel - 1))).getDescriptionPropertyKey();
            IPropertyKey requiredScope = ((ScopeEnum)((Object) ScopeEnum.getEnumList().get(entityLevel - 2))).getDescriptionPropertyKey();
            bizViol = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NO_PRIVILEGE_TO_CHANGE_TOPOLOGY, null, (Object)currentScope, (Object)requiredScope);
        }
        if (inFieldChanges.hasFieldChange(IArgoField.YRD_FACILITY) || inFieldChanges.hasFieldChange(IArgoField.YRD_ID)) {
            bizViol = this.checkIfYrdAlreadyExistsForFcy(bizViol);
        }
        return bizViol;
    }

    private BizViolation checkIfYrdAlreadyExistsForFcy(BizViolation inBv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_FACILITY, (Object)this.getYrdFacility().getFcyGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_ID, (Object)this.getYrdId()));
        Serializable pkValue = this.getPrimaryKey();
        if (pkValue != null) {
            dq.addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.pkEq((Object)pkValue)));
        }
        if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
            inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.DUPLICATE_YARD_FOR_THIS_FACILITY, (BizViolation)inBv, (IMetafieldId) IArgoField.YRD_ID, (Object)this.getYrdId(), (Object)this.getYrdFacility().getFcyId());
        }
        return inBv;
    }

    public IScopeEnum getScopeEnum() {
        return ScopeEnum.YARD;
    }

    public IScopeNodeEntity getParent()
    {
//        return this.getYrdFacility();
        return null;
    }

    public Collection getChildren() {
        return Collections.emptySet();
    }

    public String getId() {
        return this.getYrdId();
    }

    public String getPathName() {
        Facility facility = this.getYrdFacility();
        Complex complex = facility.getFcyComplex();
        Operator operator = complex.getCpxOperator();
        return operator.getOprId() + "/" + complex.getCpxId() + "/" + facility.getFcyId() + "/" + this.getYrdId();
    }

    public String getYrdPathName() {
        return this.getPathName();
    }

    @Override
    public List getGuardians() {
        return Collections.emptyList();
    }

    @Override
    @Nullable
    public String getLovKeyNameForPathToGuardian(String inPathToGuardian) {
        return null;
    }

    @Override
    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum inLogicalEntityType) {
        return null;
    }

    @Override
    public Boolean skipEventRecording() {
        return false;
    }

    @Override
    public void postEventCreation(IEvent inEventCreated) {
    }

    @Override
    public LogicalEntityEnum getLogicalEntityType() {
        return LogicalEntityEnum.YARD;
    }

    @Override
    public String getLogEntityId() {
        return this.getYrdId();
    }

    @Override
    public String getLogEntityParentId() {
        return this.getYrdFacility().getFcyId();
    }

    @Override
    public void calculateFlags() {
    }

    @Override
    @Nullable
    public BizViolation verifyApplyFlagToEntityAllowed() {
        return null;
    }

    @Override
    public Complex getLogEntityComplex() {
        return this.getYrdFacility().getFcyComplex();
    }

    public boolean isValidSlotName(String inYardSlot) {
        boolean isValid = false;
        AbstractBin yardModel = this.getYrdBinModel();
        if (yardModel == null || inYardSlot == null) {
            isValid = true;
        } else {
            AbstractBlock block;
            String tierName;
            String binName;
            int lastDot = inYardSlot.lastIndexOf(46);
            if (lastDot > 0) {
                binName = inYardSlot.substring(0, lastDot);
                tierName = inYardSlot.substring(lastDot + 1, inYardSlot.length());
            } else {
                binName = inYardSlot;
                tierName = "";
            }
            AbstractBin bin = yardModel.findDescendantBinFromInternalSlotString(binName, null);
            if (bin != null && !tierName.isEmpty() && (block = BinModelHelper.getBlockFromModelBin((AbstractBin)bin)) != null) {
                isValid = block.isValidTierName(tierName);
            }
        }
        return isValid;
    }

    public AuditEvent vetAuditEvent(@NotNull AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public void clearYardModel() {
        this.setYrdCompiledYard(null);
    }

    @Nullable
    public List<YrdBlkSupplement> getYrdBlkSupplements() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"YrdBlkSupplement").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YBS_YARD, (Object)this.getYrdGkey()));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    public YrdBlkSupplement findYrdBlkSupplement(@NotNull AbstractBlock inForBlock) {
        String blockId = inForBlock.getBlockName();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"YrdBlkSupplement").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YBS_YARD, (Object)this.getYrdGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YBS_BLOCK_ID, (Object)blockId));
        dq.setScopingEnabled(false);
        return (YrdBlkSupplement)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static Yard hydrate(Serializable inPrimaryKey) {
        return (Yard)HibernateApi.getInstance().load(Yard.class, inPrimaryKey);
    }

    public void setBinModel(IBinModel inBinModel) {
        this.setYrdBinModel((AbstractBin)inBinModel);
    }

    public IMetafieldId getCompileYardField() {
        return IArgoField.YRD_COMPILED_YARD;
    }

    public IMetafieldId getSparcsSettingsField() {
        return IArgoField.YRD_SPARCS_SETTINGS;
    }

    public IMetafieldId getBerthTextFileField() {
        return IArgoField.YRD_BERTH_TEXT_FILE;
    }

    public byte[] getCompiledYard() {
        return this.getYrdCompiledYard();
    }

    public String getSparcsSettings() {
        return this.getYrdSparcsSettings();
    }

    public byte[] getSparcsSettingsBytes() {
        byte[] settingBytes = null;
        String settings = this.getSparcsSettings();
        try {
            settingBytes = settings != null ? settings.getBytes("ASCII") : null;
        }
        catch (UnsupportedEncodingException ex) {
            LOGGER.error((Object)ex.getMessage(), (Throwable)ex);
        }
        return settingBytes;
    }

    public boolean getConfigFromSettingsFile(String inSettingCode) {
        boolean configValue = false;
        byte[] settingBytes = this.getSparcsSettingsBytes();
        if (settingBytes != null) {
//            SparcsSettings sparcsSettings = SparcsSettings.createFromStream((InputStream)new ByteArrayInputStream(settingBytes));
//            configValue = sparcsSettings.getBooleanConfigSetting(inSettingCode);
        }
        return configValue;
    }

    public byte[] getBerthTextFile() {
        return this.getYrdBerthTextFile();
    }

    public boolean isActive() {
        return LifeCycleStateEnum.ACTIVE.equals((Object)this.getYrdLifeCycleState());
    }

    public boolean isReady() {
        return XpsImportStateEnum.READY.equals((Object)this.getYrdXpsImportState());
    }

    public String getScopeName() {
        Facility fcy = this.getYrdFacility();
        Complex cpx = fcy.getFcyComplex();
        Operator opr = cpx.getCpxOperator();
        return String.format("%s/%s/%s/%s", opr.getOprId(), cpx.getCpxId(), fcy.getFcyId(), this.getYrdId());
    }

    public static List<Yard> findActiveImportReadyYards() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Yard").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.YRD_XPS_IMPORT_STATE, (Object)((Object) XpsImportStateEnum.READY)));
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public String getDummyWQName() {
        return this.getId() + DUMMY_WORK_QUEUE_EXTENSION;
    }

}
