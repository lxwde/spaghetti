package com.zpmc.ztos.infra.base.business.equipments;

import com.zpmc.ztos.infra.base.business.dataobject.ChassisDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EquipNominalLengthEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Chassis extends ChassisDO
{
    private static final Logger LOGGER = LogManager.getLogger(Chassis.class);


    public Chassis() {
        this.setEqClass(EquipClassEnum.CHASSIS);
        this.setEqFits20(Boolean.FALSE);
        this.setEqFits24(Boolean.FALSE);
        this.setEqFits30(Boolean.FALSE);
        this.setEqFits40(Boolean.FALSE);
        this.setEqFits45(Boolean.FALSE);
        this.setEqFits48(Boolean.FALSE);
        this.setEqFits53(Boolean.FALSE);
        this.setEqIsChassisTriaxle(Boolean.FALSE);
    }

    public static Chassis findChassis(String inChsId) {
        String lookupId = Chassis.getEqLookupKey(inChsId);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Chassis").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.EQ_ID_NO_CHECK_DIGIT, (Object)lookupId));
        try {
            return (Chassis) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        }
        catch (Exception e) {
            return null;
        }

    }

    public static Chassis findOrCreateChassis(String inEqId, String inEquipTypeId, DataSourceEnum inDataSource) throws BizViolation {
        Chassis chs = Chassis.findChassis(inEqId);
        if (chs != null) {
            if (!chs.getEqEquipType().isBasic20Allowed() && !chs.getEqEquipType().isBasic40Allowed()) {
                LOGGER.warn((Object)("Found chassis " + chs.getEqIdFull() + " of EquipType " + chs.getEqEquipType() + "that does not allow 20 or 40 footers. This is invalid configuration. Forcibly enabling 20 and 40 footers."));
                chs.getEqEquipType().setEqtypFits20(true);
                chs.getEqEquipType().setEqtypFits40(true);
            }
            return chs;
        }
        chs = Chassis.createChassis(inEqId, inEquipTypeId, inDataSource);
        if (!chs.getEqEquipType().isBasic20Allowed() && !chs.getEqEquipType().isBasic40Allowed()) {
            LOGGER.warn((Object)("Found chassis " + chs.getEqIdFull() + " of EquipType " + chs.getEqEquipType() + "that does not allow 20 or 40 footers. This is invalid configuration. Forcibly enabling 20 and 40 footers."));
            chs.getEqEquipType().setEqtypFits20(true);
            chs.getEqEquipType().setEqtypFits40(true);
        }
        return chs;
    }

    public static Chassis createChassis(String inChassisId, String inChsType, DataSourceEnum inDataSource) throws BizViolation {
        Chassis chs = new Chassis();
        chs.setEquipmentIdFields(inChassisId);
        EquipType equipType = EquipType.findEquipType(inChsType);
        if (equipType == null && inChsType != null) {
            equipType = EquipType.createEquipType(inChsType, EquipClassEnum.CHASSIS);
        }
        if (inChsType == null) {
            throw BizViolation.createFieldViolation((IPropertyKey) IFrameworkPropertyKeys.VALIDATION__REQUIRED_FIELD, null, (IMetafieldId) IArgoRefField.EQTYP_ID);
        }
        if (equipType == null) {
            throw BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.UNKNOWN_CHASSIS_TYPE, null, (IMetafieldId) IArgoRefField.EQTYP_ID, (Object)inChsType);
        }
        if (equipType.getEqtypClass() != EquipClassEnum.CHASSIS) {
            throw BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.INVALID_CHASSIS_TYPE, null, (IMetafieldId) IArgoRefField.EQTYP_ID, (Object)inChsType);
        }
        chs.setEqEquipType(equipType);
        chs.updateEquipTypeProperties(equipType);
        chs.setEqDataSource(inDataSource);
        HibernateApi.getInstance().save((Object)chs);
        return chs;
    }

    public static String getDefaultChsTypeIdForEq(Equipment inEq) {
        EquipNominalLengthEnum eqLength = inEq.getEqEquipType().getEqtypNominalLength();
        String chsType = eqLength == EquipNominalLengthEnum.NOM20 ? "CH20" : (eqLength == EquipNominalLengthEnum.NOM45 ? "CH45" : "CH40");
        return chsType;
    }

    public static String findFullIdOrPadCheckDigit(String inChsId) {
        String chsIdFull = inChsId;
        Chassis chs = Chassis.findChassis(inChsId);
        if (chs == null) {
            if (inChsId.length() == 10) {
//                DomainQuery dq = QueryUtils.createDomainQuery((String)"Chassis").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQ_LIFE_CYCLE_STATE, (Object)LifeCycleStateEnum.ACTIVE)).addDqPredicate(PredicateFactory.like((IMetafieldId)IArgoRefField.EQ_ID_FULL, (String)(inChsId + "%")));
//                List eqs = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
//                if (eqs != null && eqs.size() == 1) {
//                    chs = (Chassis)eqs.get(0);
//                    chsIdFull = chs.getEqIdFull();
//                }
            }
        } else {
            chsIdFull = chs.getEqIdFull();
        }
        return chsIdFull;
    }

    @Override
    protected FieldChanges updateEquipTypeProperties(FieldChanges inChanges, EquipType inEquipType) {
        FieldChanges moreChanges = super.updateEquipTypeProperties(inChanges, inEquipType);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS20, inEquipType.getEqtypFits20(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS24, inEquipType.getEqtypFits24(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS30, inEquipType.getEqtypFits30(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS40, inEquipType.getEqtypFits40(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS45, inEquipType.getEqtypFits45(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS48, inEquipType.getEqtypFits48(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_FITS53, inEquipType.getEqtypFits53(), moreChanges);
        this.setDenormalizedEqtypPropValue(inChanges, IArgoRefField.EQ_IS_CHASSIS_TRIAXLE, inEquipType.getEqtypIsChassisTriaxle(), moreChanges);
        return moreChanges;
    }

    @Override
    public void setFieldValue(IMetafieldId inFieldId, Object inFieldValue) {
//        if (ArgoBizMetafield.CHS_EQ_TYPE.equals((Object)inFieldId)) {
//            super.setFieldValue(IArgoRefField.EQ_EQUIP_TYPE, inFieldValue);
//        } else {
//            super.setFieldValue(inFieldId, inFieldValue);
//        }
    }

    public Object getChsEqType() {
        return this.getEqEquipType().getPrimaryKey();
    }

    public static Chassis resolveChsFromEq(Equipment inEq) {
        Chassis chs = null;
        if (inEq != null && EquipClassEnum.CHASSIS.equals((Object)inEq.getEqClass())) {
//            chs = (Chassis)HibernateApi.getInstance().downcast((HibernatingEntity)inEq, Chassis.class);
        }
        return chs;
    }

    public String toString() {
        String iso = this.getEqEquipType() != null ? this.getEqEquipType().getEqtypId() : "????";
        return "Chs[" + this.getEqIdFull() + ':' + iso + ']';
    }


}
