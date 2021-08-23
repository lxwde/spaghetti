package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.ReeferRecordDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.VentUnitEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.BizFieldList;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChange;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;
import java.util.HashSet;

public class ReeferRecord extends ReeferRecordDO {
    private static final double MAX_REEFER_HOURS = 1000000.0;
    private BizFieldList _bizFields;
    private static final Logger LOGGER = Logger.getLogger(ReeferRecord.class);

    public ReeferRecord() {
        this.setRfrecDataSource(DataSourceEnum.UNKNOWN);
        this.setRfrecHasMalfunction(Boolean.FALSE);
        this.setRfrecIsBulbOn(Boolean.FALSE);
        this.setRfrecAreDrainsOpen(Boolean.FALSE);
        this.setRfrecTime(new Date());
    }

    public synchronized BizFieldList getBizFieldList() {
        if (this._bizFields == null) {
            this._bizFields = new BizFieldList();
            IMetafieldId unitLine = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IInventoryField.RFREC_UNIT, (IMetafieldId)IInventoryField.UNIT_LINE_OPERATOR);
            this._bizFields.add(unitLine, BizRoleEnum.LINEOP);
        }
        return this._bizFields;
    }

    public static ReeferRecord createReeferRecord(Unit inUnit) {
        ReeferRecord rfRec = new ReeferRecord();
        HashSet<ReeferRecord> rfrecSet = (HashSet<ReeferRecord>) inUnit.getUnitReeferRecordSet();
        if (rfrecSet == null) {
            rfrecSet = new HashSet<ReeferRecord>();
            inUnit.setUnitReeferRecordSet(rfrecSet);
        }
        rfRec.setRfrecUnit(inUnit);
        rfrecSet.add(rfRec);
        rfRec.setRfrecIsPowered(inUnit.getUnitIsPowered());
        rfRec.setRfrecIsAlarmOn(inUnit.getUnitIsAlarmOn());
        return rfRec;
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    public String getDescription() {
        return "RFR:" + this.getRfrecReturnTmp();
    }

    public int compareTo(Object inReeferRecord) {
        if (!(inReeferRecord instanceof ReeferRecord)) {
            return 1;
        }
        Date thisDate = this.getRfrecTime();
        Date inDate = ((ReeferRecord)inReeferRecord).getRfrecTime();
        if (thisDate == null) {
            if (inDate == null) {
                return 0;
            }
            return -1;
        }
        if (inDate == null) {
            return 1;
        }
        return thisDate.compareTo(inDate);
    }

    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        String prevFaultCode;
        String faultCode;
        Unit unit;
        super.preProcessInsert(inOutMoreChanges);
        String rfrecId = this.getRfrecCreationRefId();
//        String uniqueIdString = this.getRfrecUnit().getReeferCreationRefId();
        if (rfrecId != null && !rfrecId.isEmpty()) {
//            uniqueIdString = rfrecId;
            LOGGER.info((Object)("ReeferRecord: using the pre-=populate value in the newly reefer record: " + rfrecId));
        }
//        this.setSelfAndFieldChange(UnitField.RFREC_CREATION_REF_ID, uniqueIdString, inOutMoreChanges);
        if (this.getRfrecIsPowered() == null && (unit = this.getRfrecUnit()) != null) {
            Boolean unitIsPowered = unit.getUnitIsPowered();
            if (unitIsPowered == null) {
                unitIsPowered = Boolean.FALSE;
            }
            this.setSelfAndFieldChange(UnitField.RFREC_IS_POWERED, unitIsPowered, inOutMoreChanges);
        }
        if (this.getRfrecIsAlarmOn() == null && (unit = this.getRfrecUnit()) != null) {
            Boolean unitIsAlarmOn = unit.getUnitIsAlarmOn();
            if (unitIsAlarmOn == null) {
                unitIsAlarmOn = Boolean.FALSE;
            }
            this.setSelfAndFieldChange(UnitField.RFREC_IS_ALARM_ON, unitIsAlarmOn, inOutMoreChanges);
        }
        String string = faultCode = this.getRfrecFaultCode() == null ? null : this.getRfrecFaultCode().trim();
        if (faultCode != null && !faultCode.isEmpty() && !faultCode.equals(prevFaultCode = this.getPreviousFaultCode())) {
            try {
                IServicesManager srvcMgr = (IServicesManager) Roastery.getBean((String)"servicesManager");
                srvcMgr.recordEvent((IEventType) EventEnum.UNIT_REEFER_FAULT, faultCode, null, null, (IServiceable)this.getRfrecUnit(), inOutMoreChanges);
            }
            catch (BizViolation bv) {
                LOGGER.error((Object)("recordReeferFault: error recording service event: " + (Object)((Object)bv)));
            }
        }
        if (this.getRfrecDataSource() == null || DataSourceEnum.UNKNOWN.equals((Object)this.getRfrecDataSource())) {
            this.setSelfAndFieldChange(IInventoryField.RFREC_DATA_SOURCE, (Object) ContextHelper.getThreadDataSource(), inOutMoreChanges);
        }
        FieldChanges reeferRecChanges = this.getReeferRecordChanges(inOutMoreChanges);
        if (faultCode != null) {
            this.setSelfAndFieldChange(IInventoryField.RFREC_FAULT_CODE, faultCode, inOutMoreChanges);
        }
        if (!(reeferRecChanges.hasFieldChange(IInventoryField.RFREC_DATA_SOURCE) && reeferRecChanges.getFieldChangeCount() == 1 || DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource()))) {
            this.getRfrecUnit().recordUnitEvent((IEventType)EventEnum.REEFER_SETTINGS_RECORDED, reeferRecChanges, "Reefer changes are recorded");
        }
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        super.preProcessUpdate(inChanges, inOutMoreChanges);
        FieldChanges reeferRecChanges = this.getReeferRecordChanges(inChanges);
        if (!(reeferRecChanges.hasFieldChange(IInventoryField.RFREC_DATA_SOURCE) && reeferRecChanges.getFieldChangeCount() == 1 || DataSourceEnum.SNX.equals((Object)ContextHelper.getThreadDataSource()))) {
            this.getRfrecUnit().recordUnitEvent((IEventType)EventEnum.REEFER_SETTINGS_RECORDED, reeferRecChanges, "Reefer changes are recorded");
        }
    }

    public FieldChanges getReeferRecordChanges(FieldChanges inChanges) {
        FieldChanges reeferRecChanges = new FieldChanges();
        for (IMetafieldId reeferFields : UnitField.REEFER_FIELDS) {
            if (!inChanges.hasFieldChange(reeferFields)) continue;
            FieldChange change = inChanges.getFieldChange(reeferFields);
            reeferRecChanges.setFieldChange(reeferFields, change.getPriorValue(), change.getNewValue());
        }
        return reeferRecChanges;
    }

    public void updateReturnTmp(Double inReturnTmp) {
        this.setRfrecReturnTmp(inReturnTmp);
    }

    public void updateSetPointTmp(Double inTempSetting) {
        this.setRfrecSetPointTmp(inTempSetting);
    }

    public void updateReeferHours(Double inReeferHours) {
        this.setRfrecReeferHours(inReeferHours);
    }

    public void setVentSettingAndUnit(Double inVentSetting, VentUnitEnum inVentUnit) {
        this.setRfrecVentSetting(inVentSetting);
        this.setRfrecVentUnit(inVentUnit);
    }

    public void setHumidityRequired(Double inHumidityRequired) {
        this.setRfrecHmdty(inHumidityRequired);
    }

    public void setUpdateRfrecCO2(Double inCO2Required) {
        this.setRfrecCO2(inCO2Required);
    }

    public void setUpdateRfrecO2(Double inO2Required) {
        this.setRfrecO2(inO2Required);
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        Double reeferHours;
        BizViolation bv = super.validateChanges(inChanges);
        if (!this.getRfrecUnit().isReefer()) {
            bv = BizViolation.create((IPropertyKey) IArgoPropertyKeys.NON_REEFER_RECORDING, (BizViolation)bv);
        }
        if ((reeferHours = this.getRfrecReeferHours()) != null) {
            if (reeferHours < 0.0) {
                bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.REEFER_HOURS_MUST_BE_POSITIVE, (BizViolation)bv);
            }
            if (reeferHours > 1000000.0) {
                bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.REEFER_HOURS_MAXIMUM_EXCEEDED, (BizViolation)bv);
            }
        }
        return bv;
    }

    @Nullable
    private String getPreviousFaultCode() {
        IQueryResult result;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ReeferRecord").addDqField(IInventoryField.RFREC_FAULT_CODE).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.RFREC_UNIT, (Object)this.getRfrecUnit().getUnitGkey())).addDqOrdering(Ordering.desc((IMetafieldId)IInventoryField.RFREC_TIME));
        if (this.getRfrecGkey() != null) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId)IInventoryField.RFREC_GKEY, (Object)this.getRfrecGkey()));
        }
        if ((result = HibernateApi.getInstance().findValuesByDomainQuery(dq)) == null || result.getTotalResultCount() == 0) {
            return null;
        }
        return (String)result.getValue(0, IInventoryField.RFREC_FAULT_CODE);
    }

    public Class getArchiveClass() {
        //return ArchiveReeferRecord.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }
}
