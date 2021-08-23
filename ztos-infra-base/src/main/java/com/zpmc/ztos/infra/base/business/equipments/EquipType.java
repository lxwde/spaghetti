package com.zpmc.ztos.infra.base.business.equipments;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import com.zpmc.ztos.infra.base.business.dataobject.EquipTypeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.core.LengthUnitEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoRefField;
import com.zpmc.ztos.infra.base.business.interfaces.IMeasurementUnit;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;


import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;

public class EquipType extends EquipTypeDO {
    public static final String UNKNOWN_TYPE = "UNKN";
    private static final Logger LOGGER = Logger.getLogger((String) EquipType.class.getName());
    private static final String ARGO_TYPE_GENERAL = "general";
    private static final String ARGO_TYPE_VENTILATED = "general, ventilated";
    private static final String ARGO_TYPE_BULK = "bulk";
    private static final String ARGO_TYPE_SPECIAL = "special";
    private static final String ARGO_TYPE_REEFER = "reefer";
    private static final String ARGO_TYPE_PORTHOLE_REEFER = "porthole reefer";
    private static final String ARGO_TYPE_OPEN_TOP = "open top";
    private static final String ARGO_TYPE_HARD_TOP = "hard top";
    private static final String ARGO_TYPE_PLATFORM = "platform";
    private static final String ARGO_TYPE_FLATRACK_FIXED = "flatrack (fixed)";
    private static final String ARGO_TYPE_FLATRACK_COLLAPSIBLE = "flatrack (collapsible)";
    private static final String ARGO_TYPE_FRAME = "frame";
    private static final String ARGO_TYPE_FLATRACK = "flatrack";
    private static final String ARGO_TYPE_TANK = "tank";
    private static final String ARGO_TYPE_SEA_AIR = "sea-air";
    private static final String ARGO_TYPE_CHASSIS = "chassis";
    private static final String ARGO_TYPE_MG = "motor generator";
    private static final int MM_20FT = 6068;
    private static final int MM_24FT = 7328;
    private static final int MM_30FT = 9144;
    private static final int MM_40FT = 12192;
    private static final int MM_44FT = 13411;
    private static final int MM_45FT = 13716;
    private static final int MM_48FT = 14630;
    private static final int MM_53FT = 16154;
    private static final int MM_4FT0 = 1260;
    private static final int MM_4FT3 = 1295;
    private static final int MM_8FT0 = 2438;
    private static final int MM_8FT6 = 2591;
    private static final int MM_9FT0 = 2743;
    private static final int MM_9FT6 = 2896;
    private static final int MM_13FT0 = 3962;


    public EquipType() {
        this.setEqtypDataSource(DataSourceEnum.UNKNOWN);
        this.setEqtypClass(EquipClassEnum.CONTAINER);
        this.setEqtypBasicLength(EquipBasicLengthEnum.BASIC40);
        this.setEqtypNominalLength(EquipNominalLengthEnum.NOM40);
        this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM86);
        double lengthMm = this.getEqtypNominalLength().getValueInUnits((IMeasurementUnit)LengthUnitEnum.MILLIMETERS);
        this.setEqtypLengthMm(new Long(new Double(lengthMm).longValue()));
        double heightMm = this.getEqtypNominalHeight().getValueInUnits((IMeasurementUnit)LengthUnitEnum.MILLIMETERS);
        this.setEqtypHeightMm(new Long(new Double(heightMm).longValue()));
        this.setEqtypIsoGroup(EquipIsoGroupEnum.GP);
        this.setEqtypRfrType(EquipRfrTypeEnum.NON_RFR);
        this.setEqtypIsDeprecated(Boolean.FALSE);
        this.setEqtypIsArchetype(Boolean.FALSE);
        this.setEqtypUsesAccessories(Boolean.FALSE);
        this.setEqtypIsTemperatureControlled(Boolean.FALSE);
        this.setEqtypOogOk(Boolean.FALSE);
        this.setEqtypIsUnsealable(Boolean.FALSE);
        this.setEqtypHasWheels(Boolean.FALSE);
        this.setEqtypIsOpen(Boolean.FALSE);
        this.setEqtypFits20(Boolean.FALSE);
        this.setEqtypFits24(Boolean.FALSE);
        this.setEqtypFits30(Boolean.FALSE);
        this.setEqtypFits40(Boolean.FALSE);
        this.setEqtypFits45(Boolean.FALSE);
        this.setEqtypFits48(Boolean.FALSE);
        this.setEqtypFits53(Boolean.FALSE);
        this.setEqtypIsChassisNoPick(Boolean.FALSE);
        this.setEqtypIsChassisTriaxle(Boolean.FALSE);
        this.setEqtypIsChassisBombCart(Boolean.FALSE);
        this.setEqtypIsChassisCassette(Boolean.FALSE);
        this.setEqtypIsSuperFreezeReefer(Boolean.FALSE);
        this.setEqtypIsControlledAtmosphereReefer(Boolean.FALSE);
        this.setEqtypNoStowOnTopIfEmpty(Boolean.FALSE);
        this.setEqtypNoStowOnTopIfLaden(Boolean.FALSE);
        this.setEqtypMustStowBelowDeck(Boolean.FALSE);
        this.setEqtypMustStowAboveDeck(Boolean.FALSE);
        this.setEqtypWidthMm(new Long(0L));
        this.setEqtypTareWeightKg(new Double(0.0));
        this.setEqtypSafeWeightKg(new Double(0.0));
        this.setEqtypPictId(new Long(0L));
        this.setEqtypMilliTeus(new Long(0L));
        this.setEqtypeIs2x20NotAllowed(Boolean.FALSE);
        this.setEqtypLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public boolean isUnknownType() {
        return UNKNOWN_TYPE.equals(this.getEqtypId());
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setEqtypLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getEqtypLifeCycleState();
    }

    public static EquipType findEquipType(String inEquipTypeId) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ID, (Object)inEquipTypeId));
//        return (EquipType)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
        return null;
    }

    public static EquipType findEquipTypeProxy(String inEquipTypeId) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ID, (Object)inEquipTypeId));
//        Serializable[] eqTypGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
//        if (eqTypGkey == null || eqTypGkey.length == 0) {
//            return null;
//        }
//        if (eqTypGkey.length == 1) {
//            return (EquipType)HibernateApi.getInstance().load(EquipType.class, eqTypGkey[0]);
//        }
//        throw BizFailure.create((PropertyKey)FrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(eqTypGkey.length), (Object)dq);
        return null;
    }

    public static Collection findEquipTypesByArchType(EquipType inArchType) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ARCHETYPE, (Object)inArchType.getEqtypGkey()));
//        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        return null;
    }

    public static EquipType hydrate(Serializable inPrimaryKey) {
//        return (EquipType)HibernateApi.getInstance().load(EquipType.class, inPrimaryKey);
        return null;
    }

    @Nullable
    public static EquipType findEquipType(EquipNominalHeightEnum inHeight, EquipIsoGroupEnum inGroup, EquipNominalLengthEnum inLength, EquipClassEnum inClass) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_HEIGHT, (Object)((Object)inHeight))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ISO_GROUP, (Object)((Object)inGroup))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_LENGTH, (Object)((Object)inLength))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_CLASS, (Object)((Object)inClass)));
//        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
//        return primaryKeys == null || primaryKeys.length == 0 ? null : EquipType.hydrate(primaryKeys[0]);
        return null;
    }

    @Nullable
    public static EquipType findEquipType(Long inHeightMM, Long inWidthMM, EquipNominalLengthEnum inNominalLengthEnum, EquipIsoGroupEnum inGroup, EquipClassEnum inClass) {
        Serializable[] primaryKeys;
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ISO_GROUP, (Object)((Object)inGroup))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_CLASS, (Object)((Object)inClass))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_LENGTH, (Object)((Object)inNominalLengthEnum)));
//        if (inHeightMM != null) {
//            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_HEIGHT_MM, (Object)inHeightMM));
//        }
//        if (inWidthMM != null) {
//            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_WIDTH_MM, (Object)inWidthMM));
//        }
//        return (primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq)) == null || primaryKeys.length == 0 ? null : EquipType.hydrate(primaryKeys[0]);
        return null;
    }

    @Nullable
    public static EquipType findEquipArcheType(EquipNominalHeightEnum inHeight, EquipIsoGroupEnum inGroup, EquipNominalLengthEnum inLength, EquipClassEnum inClass) {
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_HEIGHT, (Object)((Object)inHeight))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ISO_GROUP, (Object)((Object)inGroup))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_LENGTH, (Object)((Object)inLength))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_IS_ARCHETYPE, (Object)Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_CLASS, (Object)((Object)inClass)));
//        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
//        return primaryKeys == null || primaryKeys.length == 0 ? null : EquipType.hydrate(primaryKeys[0]);
        return null;
    }

    public static EquipType createEquipType(String inEquipTypeId) {
        EquipType et = new EquipType();
        et.setEqtypId(inEquipTypeId);
        et.setEqtypDataSource(DataSourceEnum.AUTO_GEN);
        et.calculatePropertiesFromIsoCode(inEquipTypeId);
//        HibernateApi.getInstance().save((Object)et);
//        if (et.getEqtypArchetype() == null) {
//            et.setEqtypArchetype(et);
//        }
//        HibernateApi.getInstance().saveOrUpdate((Object)et);
        return et;
    }

    public static EquipType getUnknownEquipType() {
        EquipType eqType = EquipType.findEquipType(UNKNOWN_TYPE);
        if (eqType == null) {
            eqType = EquipType.createEquipType(UNKNOWN_TYPE);
        }
        return eqType;
    }

    public static void createReferenceDataForTests() {
        EquipType et0 = EquipType.findOrCreateEquipType("4200");
        EquipType et1 = EquipType.findOrCreateEquipType("4210");
        et1.setEqtypArchetype(et0);
        et1.setEqtypIsArchetype(Boolean.FALSE);
        et0 = EquipType.findOrCreateEquipType("2200");
        et1 = EquipType.findOrCreateEquipType("2210");
        et1.setEqtypArchetype(et0);
        et1.setEqtypIsArchetype(Boolean.FALSE);
    }

    public static EquipType createEquipType(String inEquipTypeId, EquipClassEnum inEquipTypeClass) {
        EquipType et = new EquipType();
        et.setEqtypId(inEquipTypeId);
        et.setEqtypDataSource(DataSourceEnum.AUTO_GEN);
        et.calculatePropertiesFromIsoCode(inEquipTypeId);
        et.setEqtypClass(inEquipTypeClass);
        if (et.getEqtypArchetype() == null) {
            et.setEqtypArchetype(et);
        }
//        HibernateApi.getInstance().save((Object)et);
        return et;
    }

    public static EquipType findOrCreateEquipType(String inEquipTypeId) {
        EquipType et = EquipType.findEquipType(inEquipTypeId);
        if (et == null) {
            et = EquipType.createEquipType(inEquipTypeId);
  //          HibernateApi.getInstance().save((Object)et);
            //LOGGER.info((Object)("findOrCreateEquipType: Created EquipType <" + et.getEqtypId() + ">"));
            return et;
        }
        return et;
    }

    public static EquipType findOrCreateEquipTypeProxy(String inEquipTypeId) {
        EquipType et = EquipType.findEquipTypeProxy(inEquipTypeId);
        if (et == null) {
            et = EquipType.createEquipType(inEquipTypeId);
//            HibernateApi.getInstance().save((Object)et);
            //LOGGER.info((Object)("findOrCreateEquipTypeProxy: Created EquipType <" + et.getEqtypId() + ">"));
            return et;
        }
        return et;
    }

    public static EquipType updateOrCreateNonStandardType(String inEquipTypeId, String inIsoEquivalent) {
        EquipType et = EquipType.findEquipType(inEquipTypeId);
        if (et == null) {
            et = EquipType.createEquipType(inEquipTypeId);
            et.calculatePropertiesFromIsoCode(inIsoEquivalent);
  //          HibernateApi.getInstance().save((Object)et);
            //LOGGER.info((Object)("findOrCreateEquipType: Created EquipType <" + et.getEqtypId() + ">"));
        } else {
            et.calculatePropertiesFromIsoCode(inIsoEquivalent);
   //         HibernateApi.getInstance().update((Object)et);
            //LOGGER.info((Object)("findOrCreateEquipType: Updated EquipType <" + et.getEqtypId() + ">"));
        }
        return et;
    }

    public static EquipType createOrUpdateIsoCntrType(String inEquipTypeId) {
        boolean newEquipType = false;
        EquipType et = EquipType.findEquipType(inEquipTypeId);
        if (et == null) {
            et = new EquipType();
            et.setEqtypId(inEquipTypeId);
            et.setEqtypDataSource(DataSourceEnum.AUTO_GEN);
            newEquipType = true;
        }
        et.calculatePropertiesFromIsoCode(inEquipTypeId);
//        HibernateApi.getInstance().saveOrUpdate((Object)et);
//        if (LOGGER.isInfoEnabled()) {
//            if (newEquipType) {
//                //LOGGER.info((Object)("createOrUpdateIsoCntrType: Created EquipType <" + et.getEqtypId() + ">"));
//            } else {
//                //LOGGER.info((Object)("createOrUpdateIsoCntrType: Updated EquipType <" + et.getEqtypId() + ">"));
//            }
//        }
        return et;
    }

    private void calculatePropertiesFromIsoCode(String inIsoCode) {
        String argotype;
        String argoheight;
        long height;
        long mteu;
        long tare;
        long length;
        String argosize;
        boolean errorSuspected = false;
        String isoCode = inIsoCode;
        while (isoCode.length() < 4) {
            isoCode = isoCode + '0';
        }
        this.setEqtypClass(EquipClassEnum.CONTAINER);
        this.setEqtypWidthMm(new Long(2438L));
        switch (isoCode.charAt(0)) {
            case '2': {
                argosize = "20";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM20);
                length = 6068L;
                tare = 1900L;
                mteu = 1000L;
                break;
            }
            case '4': {
                argosize = "40";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM40);
                length = 12192L;
                tare = 3084L;
                mteu = 2000L;
                break;
            }
            case '9':
            case 'L': {
                argosize = "45";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM45);
                length = 13716L;
                tare = 3800L;
                mteu = 2250L;
                break;
            }
            case 'M': {
                argosize = "48";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM48);
                length = 14630L;
                tare = 4000L;
                mteu = 2400L;
                break;
            }
            case 'Q': {
                argosize = "24";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM24);
                length = 7328L;
                tare = 2000L;
                mteu = 1200L;
                break;
            }
            default: {
                argosize = "40";
                this.setEqtypNominalLength(EquipNominalLengthEnum.NOM40);
                length = 12192L;
                tare = 3084L;
                mteu = 2000L;
                errorSuspected = true;
            }
        }
        this.setEqtypLengthMm(new Long(length));
        switch (isoCode.charAt(1)) {
            case '0':
            case '1': {
                height = 2438L;
                argoheight = "80";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM80);
                break;
            }
            case '2':
            case '3': {
                height = 2591L;
                argoheight = "86";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM86);
                break;
            }
            case '4': {
                height = 2743L;
                argoheight = "90";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM90);
                break;
            }
            case '5':
            case '6': {
                height = 2896L;
                argoheight = "96";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM96);
                break;
            }
            case '7':
            case '8': {
                height = 1260L;
                argoheight = "40";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM40);
                break;
            }
            case '9': {
                height = 2591L;
                argoheight = "86";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM86);
                errorSuspected = true;
                break;
            }
            default: {
                height = 2591L;
                argoheight = "86";
                this.setEqtypNominalHeight(EquipNominalHeightEnum.NOM86);
                errorSuspected = true;
            }
        }
        this.setEqtypHeightMm(new Long(height));
        char subtype = isoCode.charAt(3);
        block15 : switch (isoCode.charAt(2)) {
            case '0':
            case 'G': {
                argotype = ARGO_TYPE_GENERAL;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.GP);
                break;
            }
            case '1':
            case 'V': {
                argotype = ARGO_TYPE_VENTILATED;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.VH);
                break;
            }
            case '2':
            case '8':
            case 'B': {
                argotype = ARGO_TYPE_BULK;
                switch (subtype) {
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.BK);
                        break block15;
                    }
                }
                this.setEqtypIsoGroup(EquipIsoGroupEnum.BU);
                break;
            }
            case 'S': {
                argotype = ARGO_TYPE_SPECIAL;
                switch (subtype) {
                    case '0': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.SN);
                        break block15;
                    }
                    case '1': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.SN);
                        break block15;
                    }
                    case '2': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.SN);
                        break block15;
                    }
                }
                this.setEqtypIsoGroup(EquipIsoGroupEnum.SN);
                break;
            }
            case '3':
            case 'R': {
                argotype = ARGO_TYPE_REEFER;
                this.setEqtypRfrType(EquipRfrTypeEnum.INTEG_AIR);
                this.setEqtypIsTemperatureControlled(Boolean.TRUE);
                switch (subtype) {
                    case '0': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.RE);
                        break;
                    }
                    case '1': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.RT);
                        break;
                    }
                    case '9': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.RC);
                        break;
                    }
                    default: {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.RS);
                    }
                }
                if (length == 6068L) {
                    tare = 2750L;
                    break;
                }
                if (length != 12192L) break;
                if (height == 2896L) {
                    tare = 4150L;
                    break;
                }
                tare = 3950L;
                break;
            }
            case '4':
            case 'H': {
                argotype = ARGO_TYPE_PORTHOLE_REEFER;
                this.setEqtypRfrType(EquipRfrTypeEnum.PORTHOLE);
                this.setEqtypIsTemperatureControlled(Boolean.TRUE);
                this.setEqtypIsoGroup(EquipIsoGroupEnum.HR);
                break;
            }
            case '5':
            case 'U': {
                this.setEqtypIsOpen(Boolean.TRUE);
                this.setEqtypOogOk(Boolean.TRUE);
                switch (subtype) {
                    case '6': {
                        argotype = ARGO_TYPE_HARD_TOP;
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.UP);
                        this.setEqtypIsUnsealable(Boolean.FALSE);
                        break block15;
                    }
                }
                argotype = ARGO_TYPE_OPEN_TOP;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.UT);
                this.setEqtypIsUnsealable(Boolean.TRUE);
                break;
            }
            case '6':
            case 'P': {
                this.setEqtypOogOk(Boolean.TRUE);
                this.setEqtypIsUnsealable(Boolean.TRUE);
                this.setEqtypIsOpen(Boolean.TRUE);
                switch (subtype) {
                    case '0': {
                        argotype = ARGO_TYPE_PLATFORM;
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.PL);
                        this.setEqtypNoStowOnTopIfLaden(Boolean.TRUE);
                        break block15;
                    }
                    case '1':
                    case '2': {
                        argotype = ARGO_TYPE_FLATRACK_FIXED;
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.PF);
                        break block15;
                    }
                    case '3':
                    case '4': {
                        argotype = ARGO_TYPE_FLATRACK_COLLAPSIBLE;
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.PC);
                        break block15;
                    }
                    case '5': {
                        argotype = ARGO_TYPE_FRAME;
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.PS);
                        break block15;
                    }
                }
                argotype = ARGO_TYPE_FLATRACK;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.PF);
                break;
            }
            case '7':
            case 'T': {
                argotype = ARGO_TYPE_TANK;
                switch (subtype) {
                    case '0':
                    case '1':
                    case '2': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.TN);
                        break block15;
                    }
                    case '3':
                    case '4':
                    case '5':
                    case '6': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.TD);
                        break block15;
                    }
                    case '7':
                    case '8':
                    case '9': {
                        this.setEqtypIsoGroup(EquipIsoGroupEnum.TG);
                        break block15;
                    }
                }
                this.setEqtypIsoGroup(EquipIsoGroupEnum.TN);
                break;
            }
            case '9':
            case 'A': {
                argotype = ARGO_TYPE_SEA_AIR;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.AS);
                break;
            }
            case 'C': {
                argotype = ARGO_TYPE_CHASSIS;
                this.setEqtypClass(EquipClassEnum.CHASSIS);
                this.setEqtypIsoGroup(EquipIsoGroupEnum.CH);
                this.setEqtypFits20(EquipNominalLengthEnum.NOM20.equals((Object)this.getEqtypNominalLength()));
                this.setEqtypFits24(EquipNominalLengthEnum.NOM24.equals((Object)this.getEqtypNominalLength()));
                this.setEqtypFits40(EquipNominalLengthEnum.NOM40.equals((Object)this.getEqtypNominalLength()));
                this.setEqtypFits45(EquipNominalLengthEnum.NOM45.equals((Object)this.getEqtypNominalLength()));
                this.setEqtypHasWheels(Boolean.TRUE);
                this.setEqtypIsUnsealable(Boolean.TRUE);
                this.setEqtypIsOpen(Boolean.TRUE);
                this.setEqtypUsesAccessories(Boolean.TRUE);
                switch (subtype) {
                    case 'B': {
                        this.setEqtypIsChassisBombCart(Boolean.TRUE);
                        this.setEqtypIsChassisCassette(Boolean.FALSE);
                        break block15;
                    }
                    case 'T': {
                        this.setEqtypIsChassisBombCart(Boolean.FALSE);
                        this.setEqtypIsChassisCassette(Boolean.TRUE);
                        break block15;
                    }
                }
                this.setEqtypIsChassisBombCart(Boolean.FALSE);
                this.setEqtypIsChassisCassette(Boolean.FALSE);
                break;
            }
            case 'M': {
                argotype = ARGO_TYPE_MG;
                this.setEqtypClass(EquipClassEnum.ACCESSORY);
                this.setEqtypIsoGroup(EquipIsoGroupEnum.NA);
                break;
            }
            default: {
                argotype = ARGO_TYPE_GENERAL;
                this.setEqtypIsoGroup(EquipIsoGroupEnum.GP);
                errorSuspected = true;
            }
        }
        this.setEqtypTareWeightKg(new Double(tare));
        this.setEqtypMilliTeus(new Long(mteu));
        StringBuilder description = new StringBuilder(argosize);
        description.append("ft");
        if (argoheight.charAt(0) == '9') {
            description.append(" hi-cube");
        }
        description.append(' ');
        description.append(argotype);
        this.setEqtypDescription(description.toString());
        EquipType archetype = EquipType.findArchetype(this.getEqtypNominalLength(), this.getEqtypIsoGroup(), this.getEqtypNominalHeight());
        if (archetype == null) {
            errorSuspected = true;
        }
        this.setEqtypArchetype(archetype);
        this.setEqtypPictId(this.getPictId(argotype, subtype));
    }

    private Long getPictId(String inArgoType, char inSubType) {
        Long result = new Long(0L);
        StringBuilder pictIdString = new StringBuilder("0000");
        if (this.getEqtypNominalLength().equals((Object)EquipNominalLengthEnum.NOM20)) {
            pictIdString.setCharAt(0, '2');
        } else if (this.getEqtypNominalLength().equals((Object)EquipNominalLengthEnum.NOM40)) {
            pictIdString.setCharAt(0, '4');
        } else if (this.getEqtypNominalLength().equals((Object)EquipNominalLengthEnum.NOM45)) {
            pictIdString.setCharAt(0, '9');
            pictIdString.setCharAt(1, '5');
        }
        if (inArgoType.equals(ARGO_TYPE_GENERAL)) {
            pictIdString.setCharAt(2, '0');
        } else if (inArgoType.equals(ARGO_TYPE_VENTILATED) || inArgoType.equals(ARGO_TYPE_BULK) || inArgoType.equals(ARGO_TYPE_SPECIAL)) {
            pictIdString.setCharAt(2, '1');
        } else if (inArgoType.equals(ARGO_TYPE_REEFER) || inArgoType.equals(ARGO_TYPE_PORTHOLE_REEFER)) {
            pictIdString.setCharAt(2, '3');
        } else if (inArgoType.equals(ARGO_TYPE_OPEN_TOP)) {
            pictIdString.setCharAt(2, '5');
        } else if (inArgoType.equals(ARGO_TYPE_PLATFORM)) {
            pictIdString.setCharAt(2, '6');
            pictIdString.setCharAt(1, '9');
        } else if (inArgoType.equals(ARGO_TYPE_FLATRACK_FIXED)) {
            pictIdString.setCharAt(2, '6');
            pictIdString.setCharAt(3, '1');
        } else if (inArgoType.equals(ARGO_TYPE_FLATRACK_COLLAPSIBLE)) {
            pictIdString.setCharAt(2, '6');
            pictIdString.setCharAt(3, '3');
        } else if (inArgoType.equals(ARGO_TYPE_FRAME)) {
            pictIdString.setCharAt(2, '7');
        } else if (inArgoType.equals(ARGO_TYPE_FLATRACK)) {
            pictIdString.setCharAt(2, '6');
        } else if (inArgoType.equals(ARGO_TYPE_TANK)) {
            pictIdString.setCharAt(2, '7');
        } else if (inArgoType.equals(ARGO_TYPE_SEA_AIR)) {
            pictIdString.setCharAt(2, '0');
        }
        result = new Long(pictIdString.toString());
        return result;
    }

    private static EquipType findArchetype(EquipNominalLengthEnum inSize, EquipIsoGroupEnum inType, EquipNominalHeightEnum inHeight) {
//        EquipIsoGroupEnum genericIsoGroup = EquipType.getGenericIsoGroup(inType);
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_CLASS, (Object)((Object)EquipClassEnum.CONTAINER))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_IS_ARCHETYPE, (Object)Boolean.TRUE)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_LENGTH, (Object)((Object)inSize))).addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ISO_GROUP, (Object)((Object)genericIsoGroup))).addDqOrdering(Ordering.desc((IMetafieldId)IArgoRefField.EQTYP_NOMINAL_HEIGHT));
//        List result = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        EquipType equipType = null;
//        Iterator iterator = result.iterator();
//        while (iterator.hasNext() && inHeight.compareTo((Object)(equipType = (EquipType)iterator.next()).getEqtypNominalHeight()) < 0) {
//        }
        return equipType;
    }

    public String getEqtypFeatures() {
        StringBuilder s = new StringBuilder();
        if (this.isTemperatureControlled()) {
            EquipRfrTypeEnum rfrType = this.getEqtypRfrType();
            if (this.isPortHoleReefer()) {
                s.append("/rfr-ph");
            } else if (this.isIntegralReefer()) {
                if (this.isControlledAtmosphereReefer()) {
                    s.append("/rfr-cta");
                } else if (EquipRfrTypeEnum.INTEG_AIR.equals((Object)rfrType)) {
                    s.append("/rfr-air-dual");
                } else if (EquipRfrTypeEnum.INTEG_AIR_SINGLE.equals((Object)rfrType)) {
                    s.append("/rfr-H2O-sngl");
                } else if (EquipRfrTypeEnum.INTEG_H20.equals((Object)rfrType)) {
                    s.append("/rfr-H2O-dual");
                } else if (EquipRfrTypeEnum.INTEG_H20_SINGLE.equals((Object)rfrType)) {
                    s.append("/rfr-H2O-sngl");
                } else {
                    s.append("/rfr-int");
                }
            }
        }
        if (this.isTank()) {
            s.append("/tank");
        }
        if (this.isFlat()) {
            s.append("/flatrack");
        }
        if (this.isChassis()) {
            s.append("/chss");
            if (this.fits20()) {
                s.append(".20");
            } else if (this.fits24()) {
                s.append(".24");
            } else if (this.fits30()) {
                s.append(".30");
            } else if (this.fits40()) {
                s.append(".40");
            } else if (this.fits45()) {
                s.append(".45");
            } else if (this.fits48()) {
                s.append(".48");
            } else if (this.fits53()) {
                s.append(".53");
            }
        }
        if (this.isOogOk()) {
            s.append("/oog-ok");
        }
        return s.toString();
    }

    public boolean usesAccessories() {
        return this.getEqtypUsesAccessories();
    }

    public boolean isTemperatureControlled() {
        return this.getEqtypIsTemperatureControlled();
    }

    public boolean isOogOk() {
        return this.getEqtypOogOk();
    }

    public boolean isUnsealable() {
        return this.getEqtypIsUnsealable();
    }

    public boolean hasWheels() {
        return this.getEqtypHasWheels();
    }

    public boolean isOpen() {
        return this.getEqtypIsOpen();
    }

    public boolean isChassis() {
        return this.getEqtypClass().equals((Object) EquipClassEnum.CHASSIS);
    }

    public boolean fits20() {
        return this.getEqtypFits20();
    }

    public boolean fits24() {
        return this.getEqtypFits24();
    }

    public boolean fits30() {
        return this.getEqtypFits30();
    }

    public boolean fits40() {
        return this.getEqtypFits40();
    }

    public boolean fits45() {
        return this.getEqtypFits45();
    }

    public boolean fits48() {
        return this.getEqtypFits48();
    }

    public boolean fits53() {
        return this.getEqtypFits53();
    }

    public boolean isChassisNoPick() {
        return this.getEqtypIsChassisNoPick();
    }

    public boolean is2x20() {
        return this.getEqtypeIs2x20NotAllowed() == null ? false : this.getEqtypeIs2x20NotAllowed();
    }

    public boolean isBombCart() {
        return this.getEqtypIsChassisBombCart() == null ? false : this.getEqtypIsChassisBombCart();
    }

    public boolean isCassette() {
        return this.getEqtypIsChassisCassette() == null ? false : this.getEqtypIsChassisCassette();
    }

    public boolean isChassisTriaxle() {
        return this.getEqtypIsChassisTriaxle();
    }

    public boolean isIntegralReefer() {
        EquipRfrTypeEnum eqtypRfrType = this.getEqtypRfrType();
        boolean isIntegral = EquipRfrTypeEnum.INTEG_AIR.equals((Object)eqtypRfrType) || EquipRfrTypeEnum.INTEG_H20.equals((Object)eqtypRfrType) || EquipRfrTypeEnum.INTEG_UNK.equals((Object)eqtypRfrType);
        return isIntegral;
    }

    public boolean isAirCooledIntegral() {
        EquipRfrTypeEnum eqtypRfrType = this.getEqtypRfrType();
        return EquipRfrTypeEnum.INTEG_AIR.equals((Object)eqtypRfrType);
    }

    public boolean isWaterCooledIntegral() {
        EquipRfrTypeEnum eqtypRfrType = this.getEqtypRfrType();
        return EquipRfrTypeEnum.INTEG_H20.equals((Object)eqtypRfrType);
    }

    public boolean isPortHoleReefer() {
        EquipRfrTypeEnum eqtypRfrType = this.getEqtypRfrType();
        return EquipRfrTypeEnum.PORTHOLE.equals((Object)eqtypRfrType);
    }

    public boolean isFantainer() {
        EquipRfrTypeEnum eqtypRfrType = this.getEqtypRfrType();
        return EquipRfrTypeEnum.FANTAINER.equals((Object)eqtypRfrType);
    }

    public boolean isSuperFreezeReefer() {
        return this.getEqtypIsSuperFreezeReefer();
    }

    public boolean isControlledAtmosphereReefer() {
        return this.getEqtypIsControlledAtmosphereReefer();
    }

    public boolean canStowOnTopIfEmpty() {
        return this.getEqtypNoStowOnTopIfEmpty();
    }

    public boolean canStowOnTopIfLaden() {
        return this.getEqtypNoStowOnTopIfLaden();
    }

    public boolean mustStowBelowDeck() {
        return this.getEqtypMustStowBelowDeck();
    }

    public boolean mustStowAboveDeck() {
        return this.getEqtypMustStowAboveDeck();
    }

    public boolean isTank() {
        EquipIsoGroupEnum isoType = this.getEqtypIsoGroup();
        boolean isTank = EquipIsoGroupEnum.TD.equals((Object)isoType) || EquipIsoGroupEnum.TN.equals((Object)isoType) || EquipIsoGroupEnum.TG.equals((Object)isoType);
        return isTank;
    }

    public boolean isFlat() {
        EquipIsoGroupEnum isoType = this.getEqtypIsoGroup();
        boolean isFlat = EquipIsoGroupEnum.PF.equals((Object)isoType) || EquipIsoGroupEnum.PC.equals((Object)isoType);
        return isFlat;
    }

    public boolean isActive() {
        return LifeCycleStateEnum.ACTIVE.equals((Object)this.getLifeCycleState());
    }

    public boolean isBundleable() {
        EquipIsoGroupEnum isoType = this.getEqtypIsoGroup();
        boolean isBundleable = this.getEqtypClass() == EquipClassEnum.CHASSIS || EquipIsoGroupEnum.PL.equals((Object)isoType) || EquipIsoGroupEnum.PC.equals((Object)isoType) || EquipIsoGroupEnum.PS.equals((Object)isoType);
        return isBundleable;
    }

    public boolean isEqualSizeTypeHeight(EquipType inEquipType) {
        return this.getEqtypNominalLength().equals((Object)inEquipType.getEqtypNominalLength()) && this.getEqtypIsoGroup().equals((Object)inEquipType.getEqtypIsoGroup()) && this.getEqtypNominalHeight().equals((Object)inEquipType.getEqtypNominalHeight());
    }

    public String toString() {
        return "EquipType: " + this.getEqtypId();
    }

    public void updateChsSlotLabel(String inChsSlotLabel) {
        this.setEqtypSlotLabels(inChsSlotLabel);
    }

    public String getChsSlotLabel(int inSlotNbr) {
        String slotLabels = this.getEqtypSlotLabels();
        if (slotLabels != null) {
            String[] labels = slotLabels.split(",");
            if (inSlotNbr > 0 && inSlotNbr <= labels.length) {
                return labels[inSlotNbr - 1];
            }
        }
        return null;
    }

    @Nullable
    public String getChsSlotIndex(@Nullable String inSlotLabel) {
        String slotLabels;
//        if (!Strings.isNullOrEmpty((String)inSlotLabel) && (slotLabels = this.getEqtypSlotLabels()) != null) {
//            int i;
//            String[] labels = slotLabels.split(",");
//            for (i = 0; i < labels.length; ++i) {
//                if (!inSlotLabel.equalsIgnoreCase(labels[i])) continue;
//                return Integer.toString(i + 1);
//            }
//            for (i = 0; i < labels.length; ++i) {
//                String index = Integer.toString(i + 1);
//                if (Strings.isNullOrEmpty((String)labels[i]) || !inSlotLabel.equals(index)) continue;
//                return index;
//            }
//        }
        return null;
    }

    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
//        super.preProcessInsertOrUpdate(inOutMoreChanges);
        this.preProcessUpdate(null, inOutMoreChanges);
    }

    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
//        super.preProcessUpdate(inChanges, inOutMoreChanges);
        if (this.getPrimaryKey() == null) {
            this.syncNominalLengthToPhysicalLength(inOutMoreChanges);
            this.syncNominalHeightToPhysicalHeight(inOutMoreChanges);
        } else if (inChanges != null) {
            if (inChanges.hasFieldChange(IArgoRefField.EQTYP_NOMINAL_LENGTH)) {
                this.syncPhysicalLengthToNominalLength(inOutMoreChanges);
            } else if (inChanges.hasFieldChange(IArgoRefField.EQTYP_LENGTH_MM)) {
                this.syncNominalLengthToPhysicalLength(inOutMoreChanges);
            }
            if (inChanges.hasFieldChange(IArgoRefField.EQTYP_NOMINAL_HEIGHT)) {
                this.syncPhysicalHeightToNominalHeight(inOutMoreChanges);
            } else if (inChanges.hasFieldChange(IArgoRefField.EQTYP_HEIGHT_MM)) {
                this.syncNominalHeightToPhysicalHeight(inOutMoreChanges);
            }
        }
    }

    private void syncPhysicalLengthToNominalLength(FieldChanges inOutMoreChanges) {
        Long lengthMm;
        EquipBasicLengthEnum basicLength;
        EquipNominalLengthEnum nomLength = this.getEqtypNominalLength();
        Long widthMm = new Long(2438L);
        Long heightMm = this.getEqtypHeightMm();
        if (nomLength == EquipNominalLengthEnum.NOM10) {
            basicLength = EquipBasicLengthEnum.BASIC20;
            lengthMm = this.lengthMm(EquipNominalLengthEnum.NOM10);
        } else if (nomLength == EquipNominalLengthEnum.NOM20) {
            basicLength = EquipBasicLengthEnum.BASIC20;
            lengthMm = new Long(6068L);
        } else if (nomLength == EquipNominalLengthEnum.NOM22) {
            basicLength = EquipBasicLengthEnum.BASIC20;
            lengthMm = this.lengthMm(EquipNominalLengthEnum.NOM22);
        } else if (nomLength == EquipNominalLengthEnum.NOM23) {
            basicLength = EquipBasicLengthEnum.BASIC20;
            lengthMm = this.lengthMm(EquipNominalLengthEnum.NOM23);
        } else if (nomLength == EquipNominalLengthEnum.NOM24) {
            basicLength = EquipBasicLengthEnum.BASIC20;
            lengthMm = new Long(7328L);
        } else if (nomLength == EquipNominalLengthEnum.NOM30) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(9144L);
        } else if (nomLength == EquipNominalLengthEnum.NOM40) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(12192L);
        } else if (nomLength == EquipNominalLengthEnum.NOM44) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(13411L);
        } else if (nomLength == EquipNominalLengthEnum.NOM45) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(13716L);
            heightMm = new Long(2896L);
        } else if (nomLength == EquipNominalLengthEnum.NOM48) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(14630L);
            widthMm = new Long(2591L);
            heightMm = new Long(2896L);
        } else if (nomLength == EquipNominalLengthEnum.NOM53) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(16154L);
            widthMm = new Long(2591L);
            heightMm = new Long(2896L);
        } else if (nomLength == EquipNominalLengthEnum.NOM32 || nomLength == EquipNominalLengthEnum.NOM42 || nomLength == EquipNominalLengthEnum.NOM51 || nomLength == EquipNominalLengthEnum.NOM546 || nomLength == EquipNominalLengthEnum.NOM60) {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = this.lengthMm(nomLength);
        } else {
            basicLength = EquipBasicLengthEnum.BASIC40;
            lengthMm = new Long(12192L);
        }
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_BASIC_LENGTH, (Object)basicLength, inOutMoreChanges);
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_LENGTH_MM, lengthMm, inOutMoreChanges);
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_WIDTH_MM, widthMm, inOutMoreChanges);
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_HEIGHT_MM, heightMm, inOutMoreChanges);
    }

    private void syncPhysicalHeightToNominalHeight(FieldChanges inOutMoreChanges) {
        EquipNominalHeightEnum nomHeight = this.getEqtypNominalHeight();
        Long heightMm = this.getEqtypHeightMm();
        if (nomHeight == EquipNominalHeightEnum.NOM40) {
            heightMm = new Long(1260L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM43) {
            heightMm = new Long(1295L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM80) {
            heightMm = new Long(2438L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM86) {
            heightMm = new Long(2591L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM90) {
            heightMm = new Long(2743L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM96) {
            heightMm = new Long(2896L);
        } else if (nomHeight == EquipNominalHeightEnum.NOM130) {
            heightMm = new Long(3962L);
        }
    //    this.setSelfAndFieldChange(IArgoRefField.EQTYP_HEIGHT_MM, heightMm, inOutMoreChanges);
    }

    private void syncNominalHeightToPhysicalHeight(FieldChanges inOutMoreChanges) {
        Long height = this.getEqtypHeightMm();
        int margin = 100;
        EquipNominalHeightEnum nominalHeight = null;
        if (height <= 1260L) {
            nominalHeight = EquipNominalHeightEnum.NOM40;
        } else if (height <= 1295L) {
            nominalHeight = EquipNominalHeightEnum.NOM43;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM49)) {
            nominalHeight = EquipNominalHeightEnum.NOM49;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM76)) {
            nominalHeight = EquipNominalHeightEnum.NOM76;
        } else if (height <= 2438L) {
            nominalHeight = EquipNominalHeightEnum.NOM80;
        } else if (height <= 2591L) {
            nominalHeight = EquipNominalHeightEnum.NOM86;
        } else if (height <= 2743L) {
            nominalHeight = EquipNominalHeightEnum.NOM90;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM91)) {
            nominalHeight = EquipNominalHeightEnum.NOM91;
        } else if (height <= 2896L) {
            nominalHeight = EquipNominalHeightEnum.NOM96;
//        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM100)) {
//            nominalHeight = EquipNominalHeightEnum.NOM100;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM102)) {
            nominalHeight = EquipNominalHeightEnum.NOM102;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM106)) {
            nominalHeight = EquipNominalHeightEnum.NOM106;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM114)) {
            nominalHeight = EquipNominalHeightEnum.NOM114;
        } else if (height <= this.heightMm(EquipNominalHeightEnum.NOM116)) {
            nominalHeight = EquipNominalHeightEnum.NOM116;
        } else if (height <= 3962L) {
            nominalHeight = EquipNominalHeightEnum.NOM130;
        }
//        if (nominalHeight == null) {
//            nominalHeight = height <= 1360L ? EquipNominalHeightEnum.NOM40 : (height <= 1395L ? EquipNominalHeightEnum.NOM43 : (height <= this.heightMm(EquipNominalHeightEnum.NOM49) + 100L ? EquipNominalHeightEnum.NOM49 : (height <= this.heightMm(EquipNominalHeightEnum.NOM76) + 100L ? EquipNominalHeightEnum.NOM76 : (height <= 2538L ? EquipNominalHeightEnum.NOM80 : (height <= 2691L ? EquipNominalHeightEnum.NOM86 : (height <= 2843L ? EquipNominalHeightEnum.NOM90 : (height <= this.heightMm(EquipNominalHeightEnum.NOM91) + 100L ? EquipNominalHeightEnum.NOM91 : (height <= 2996L ? EquipNominalHeightEnum.NOM96 : (height <= this.heightMm(EquipNominalHeightEnum.NOM10) + 100L ? EquipNominalHeightEnum.NOM10 : (height <= this.heightMm(EquipNominalHeightEnum.NOM102) + 100L ? EquipNominalHeightEnum.NOM102 : (height <= this.heightMm(EquipNominalHeightEnum.NOM106) + 100L ? EquipNominalHeightEnum.NOM106 : (height <= this.heightMm(EquipNominalHeightEnum.NOM114) + 100L ? EquipNominalHeightEnum.NOM114 : (height <= this.heightMm(EquipNominalHeightEnum.NOM116) + 100L ? EquipNominalHeightEnum.NOM116 : (height <= 4062L ? EquipNominalHeightEnum.NOM130 : EquipNominalHeightEnum.NOMNA))))))))))))));
//        }
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_NOMINAL_HEIGHT, (Object)nominalHeight, inOutMoreChanges);
    }

    private void syncNominalLengthToPhysicalLength(FieldChanges inOutMoreChanges) {
        Long lengthMm = this.getEqtypLengthMm();
        int margin = 1000;
        int halfMargin = 500;
        int smallMargin = 300;
        EquipNominalLengthEnum nominalLength = null;
        EquipBasicLengthEnum basicLength = null;
        if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM10)) {
            nominalLength = EquipNominalLengthEnum.NOM10;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (lengthMm == 6068L) {
            nominalLength = EquipNominalLengthEnum.NOM20;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM22)) {
            nominalLength = EquipNominalLengthEnum.NOM22;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM23)) {
            nominalLength = EquipNominalLengthEnum.NOM23;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (lengthMm == 7328L) {
            nominalLength = EquipNominalLengthEnum.NOM24;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (lengthMm == 9144L) {
            nominalLength = EquipNominalLengthEnum.NOM30;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM32)) {
            nominalLength = EquipNominalLengthEnum.NOM32;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm == 12192L) {
            nominalLength = EquipNominalLengthEnum.NOM40;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM42)) {
            nominalLength = EquipNominalLengthEnum.NOM42;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM44)) {
            nominalLength = EquipNominalLengthEnum.NOM44;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm == 13716L) {
            nominalLength = EquipNominalLengthEnum.NOM45;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm == 14630L) {
            nominalLength = EquipNominalLengthEnum.NOM48;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM51)) {
            nominalLength = EquipNominalLengthEnum.NOM51;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM53)) {
            nominalLength = EquipNominalLengthEnum.NOM53;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM546)) {
            nominalLength = EquipNominalLengthEnum.NOM546;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (lengthMm.longValue() == this.lengthMm(EquipNominalLengthEnum.NOM60)) {
            nominalLength = EquipNominalLengthEnum.NOM60;
            basicLength = EquipBasicLengthEnum.BASIC40;
        }
        if (nominalLength == null) {
            if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM10) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM10;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (lengthMm <= 6568L) {
                nominalLength = EquipNominalLengthEnum.NOM20;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM22) + 300L) {
                nominalLength = EquipNominalLengthEnum.NOM22;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM23) + 300L) {
                nominalLength = EquipNominalLengthEnum.NOM23;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (lengthMm <= 8328L) {
                nominalLength = EquipNominalLengthEnum.NOM24;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (lengthMm <= 9644L) {
                nominalLength = EquipNominalLengthEnum.NOM30;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM32) + 1219L) {
                nominalLength = EquipNominalLengthEnum.NOM32;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= 12692L) {
                nominalLength = EquipNominalLengthEnum.NOM40;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM42) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM42;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= 13911L) {
                nominalLength = EquipNominalLengthEnum.NOM44;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= 14216L) {
                nominalLength = EquipNominalLengthEnum.NOM45;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= 15130L) {
                nominalLength = EquipNominalLengthEnum.NOM48;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM51) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM51;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM53) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM53;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM546) + 1000L) {
                nominalLength = EquipNominalLengthEnum.NOM546;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (lengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM60) + 1000L) {
                nominalLength = EquipNominalLengthEnum.NOM60;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else {
                nominalLength = EquipNominalLengthEnum.NOMXX;
                basicLength = EquipBasicLengthEnum.BASIC20;
            }
        }
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_NOMINAL_LENGTH, (Object)nominalLength, inOutMoreChanges);
//        this.setSelfAndFieldChange(IArgoRefField.EQTYP_BASIC_LENGTH, (Object)basicLength, inOutMoreChanges);
    }

    public void applyFieldChanges(FieldChanges inFieldChanges) {
//        super.applyFieldChanges(inFieldChanges);
        Boolean v = this.getEqtypIsArchetype();
        boolean isArchetype = v instanceof Boolean && v != false;
        EquipType currentArchetype = this.getEqtypArchetype();
        if (isArchetype) {
            this.setEqtypArchetype(this);
        } else if (currentArchetype == null) {
            this.setEqtypIsArchetype(Boolean.TRUE);
            this.setEqtypArchetype(this);
        } else if (this.equals(currentArchetype)) {
            this.setEqtypIsArchetype(Boolean.TRUE);
        }
        if (this.getEqtypRfrType() == null) {
            this.setEqtypRfrType(EquipRfrTypeEnum.NON_RFR);
        }
        if (this.getEqtypDataSource() == null) {
            this.setEqtypDataSource(DataSourceEnum.USER_LCL);
        }
    }

    public BizViolation validateDeletion() {
        BizViolation bv = this.checkIfArcheTypeReferred();
        if (bv != null) {
            return bv;
        }
//        if (this.equals(this.getEqtypArchetype())) {
//            this.setEqtypArchetype(null);
//            HibernateApi.getInstance().update((Object)this);
//            HibernateApi.getInstance().flush();
//        }
        return null;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
//        EquipType prevArcheType;
//        BizViolation bv = super.validateChanges(inChanges);
//        if (inChanges.hasFieldChange(IArgoRefField.EQTYP_ARCHETYPE) && ObjectUtils.equals((Object)(prevArcheType = (EquipType)inChanges.getFieldChange(IArgoRefField.EQTYP_ARCHETYPE).getPriorValue()), (Object)this)) {
//            bv = this.checkIfArcheTypeReferred();
//        }
//        if (inChanges.hasFieldChange(IArgoRefField.EQTYP_ID) && this.getEqtypId() != null && this.getEqtypId().contains(" ")) {
//            throw BizFailure.wrap((Throwable)BizViolation.create((PropertyKey)ArgoPropertyKeys.INVALID_EQTYPE_CONTAINS_BLANK_SPACES, null, (Object)this.getEqtypId()));
//        }
//        bv = this.checkRequiredField(bv, IArgoRefField.EQTYP_ID);
//        bv = this.checkRequiredField(bv, IArgoRefField.EQTYP_NOMINAL_LENGTH);
//        bv = this.checkRequiredField(bv, IArgoRefField.EQTYP_CLASS);
//        bv = this.checkRequiredField(bv, IArgoRefField.EQTYP_TARE_WEIGHT_KG);
//        return bv;
        return null;
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.EQTYP_SCOPE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.EQTYP_ID;
    }

    public ScopeEnum getMinimumScope() {
        return ScopeEnum.COMPLEX;
    }

    public static EquipIsoGroupEnum getGenericIsoGroup(EquipIsoGroupEnum inIsoGroup) {
        if (EquipIsoGroupEnum.RE.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.RE;
        }
        if (EquipIsoGroupEnum.RT.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.RE;
        }
        if (EquipIsoGroupEnum.RS.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.RE;
        }
        if (EquipIsoGroupEnum.HR.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.RE;
        }
        if (EquipIsoGroupEnum.UT.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.UT;
        }
        if (EquipIsoGroupEnum.PL.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.PL;
        }
        if (EquipIsoGroupEnum.PF.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.PL;
        }
        if (EquipIsoGroupEnum.PC.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.PL;
        }
        if (EquipIsoGroupEnum.PS.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.PL;
        }
        if (EquipIsoGroupEnum.TN.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.TN;
        }
        if (EquipIsoGroupEnum.TD.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.TN;
        }
        if (EquipIsoGroupEnum.TG.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.TN;
        }
        if (EquipIsoGroupEnum.BK.equals((Object)inIsoGroup) || EquipIsoGroupEnum.BU.equals((Object)inIsoGroup)) {
            return EquipIsoGroupEnum.BK;
        }
        return EquipIsoGroupEnum.GP;
    }

    public static EquipType createTrailerType(String inTypeCode) {
        int typeCode;
        Long length;
        StringBuilder type = new StringBuilder(inTypeCode);
        while (type.length() < 3) {
            type.append('?');
        }
        if (Character.isDigit(type.charAt(0)) && Character.isDigit(type.charAt(1))) {
            length = new Long(type.substring(0, 2));
            typeCode = type.charAt(2);
        } else if (Character.isDigit(type.charAt(1)) && Character.isDigit(type.charAt(2))) {
            length = new Long(type.substring(1, 3));
            typeCode = type.charAt(1);
        } else {
            length = new Long(40L);
            typeCode = 68;
        }
        EquipType et = new EquipType();
        et.setEqtypId(inTypeCode);
        et.setEqtypClass(EquipClassEnum.SEMITRAILER);
        et.setEqtypBasicLength(EquipBasicLengthEnum.BASIC40);
        et.setEqtypNominalLength(EquipNominalLengthEnum.NOM40);
        EquipType archetype = EquipType.findArchetype(et.getEqtypNominalLength(), et.getEqtypIsoGroup(), et.getEqtypNominalHeight());
        et.setEqtypArchetype(archetype);
        et.setEqtypDataSource(DataSourceEnum.AUTO_GEN);
        et.setEqtypLengthMm(new Long(length * 305L));
        et.setEqtypHeightMm(new Long(2896L));
        et.setEqtypWidthMm(new Long(2438L));
        et.setEqtypTareWeightKg(new Double(3000.0));
        et.setEqtypIsTemperatureControlled(typeCode == 82);
        et.setEqtypOogOk(Boolean.FALSE);
        et.setEqtypHasWheels(Boolean.TRUE);
        return et;
    }

    private BizViolation checkIfArcheTypeReferred() {
        BizViolation bv = null;
//        DomainQuery dq = QueryUtils.createDomainQuery((String)"EquipType").addDqPredicate(PredicateFactory.eq((IMetafieldId)IArgoRefField.EQTYP_ARCHETYPE, (Object)this.getEqtypGkey()));
//        if (HibernateApi.getInstance().findCountByDomainQuery(dq) > 1) {
//            bv = BizViolation.create((IPropertyKey)ArgoPropertyKeys.EQUIPTYPE_ARCH_IS_REFERENCED, null, (Object)this.getEqtypId());
//        }
        return bv;
    }

    public double getTareWeightKg() {
        return this.getEqtypTareWeightKg() != null ? this.getEqtypTareWeightKg() : 0.0;
    }

//    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
//        return inAuditEvent;
//    }

    public void setTareKg(double inTareKg) {
        this.setEqtypTareWeightKg(new Double(inTareKg));
    }

    public void setHeightMm(int inHeightMm) {
        this.setEqtypHeightMm(new Long(inHeightMm));
        int margin = 100;
        EquipNominalHeightEnum nominalHeight = null;
        if (inHeightMm <= 1360) {
            nominalHeight = EquipNominalHeightEnum.NOM40;
        } else if (inHeightMm <= 1395) {
            nominalHeight = EquipNominalHeightEnum.NOM43;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM49) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM49;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM76) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM76;
        } else if (inHeightMm <= 2538) {
            nominalHeight = EquipNominalHeightEnum.NOM80;
        } else if (inHeightMm <= 2691) {
            nominalHeight = EquipNominalHeightEnum.NOM86;
        } else if (inHeightMm <= 2843) {
            nominalHeight = EquipNominalHeightEnum.NOM90;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM91) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM91;
        } else if (inHeightMm <= 2996) {
            nominalHeight = EquipNominalHeightEnum.NOM96;
//        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM100) + 100L) {
//            nominalHeight = EquipNominalHeightEnum.NOM100;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM102) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM102;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM114) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM114;
        } else if ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM116) + 100L) {
            nominalHeight = EquipNominalHeightEnum.NOM116;
        } else if (inHeightMm <= 4062) {
            nominalHeight = EquipNominalHeightEnum.NOM130;
        }
//        if (nominalHeight == null) {
//            nominalHeight = inHeightMm <= 1360 ? EquipNominalHeightEnum.NOM40 : (inHeightMm <= 1395 ? EquipNominalHeightEnum.NOM43 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM49) + 100L ? EquipNominalHeightEnum.NOM49 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM76) + 100L ? EquipNominalHeightEnum.NOM76 : (inHeightMm <= 2538 ? EquipNominalHeightEnum.NOM80 : (inHeightMm <= 2691 ? EquipNominalHeightEnum.NOM86 : (inHeightMm <= 2843 ? EquipNominalHeightEnum.NOM90 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM91) + 100L ? EquipNominalHeightEnum.NOM91 : (inHeightMm <= 2996 ? EquipNominalHeightEnum.NOM96 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM10) + 100L ? EquipNominalHeightEnum.NOM10 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM102) + 100L ? EquipNominalHeightEnum.NOM102 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM114) + 100L ? EquipNominalHeightEnum.NOM114 : ((long)inHeightMm <= this.heightMm(EquipNominalHeightEnum.NOM116) + 100L ? EquipNominalHeightEnum.NOM116 : (inHeightMm <= 4062 ? EquipNominalHeightEnum.NOM130 : EquipNominalHeightEnum.NOMNA)))))))))))));
//        }
        this.setEqtypNominalHeight(nominalHeight);
    }

    public void setLengthMm(int inLengthMm) {
        this.setEqtypLengthMm(new Long(inLengthMm));
        int margin = 1000;
        int halfMargin = 500;
        EquipNominalLengthEnum nominalLength = null;
        EquipBasicLengthEnum basicLength = null;
        if (inLengthMm == 6068) {
            nominalLength = EquipNominalLengthEnum.NOM20;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM22)) {
            nominalLength = EquipNominalLengthEnum.NOM22;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (inLengthMm == 7328) {
            nominalLength = EquipNominalLengthEnum.NOM24;
            basicLength = EquipBasicLengthEnum.BASIC20;
        } else if (inLengthMm == 9144) {
            nominalLength = EquipNominalLengthEnum.NOM30;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM32)) {
            nominalLength = EquipNominalLengthEnum.NOM32;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (inLengthMm == 12192) {
            nominalLength = EquipNominalLengthEnum.NOM40;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM42)) {
            nominalLength = EquipNominalLengthEnum.NOM42;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (inLengthMm == 13716) {
            nominalLength = EquipNominalLengthEnum.NOM45;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if (inLengthMm == 14630) {
            nominalLength = EquipNominalLengthEnum.NOM48;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM51)) {
            nominalLength = EquipNominalLengthEnum.NOM51;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM53)) {
            nominalLength = EquipNominalLengthEnum.NOM53;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM546)) {
            nominalLength = EquipNominalLengthEnum.NOM546;
            basicLength = EquipBasicLengthEnum.BASIC40;
        } else if ((long)inLengthMm == this.lengthMm(EquipNominalLengthEnum.NOM60)) {
            nominalLength = EquipNominalLengthEnum.NOM60;
            basicLength = EquipBasicLengthEnum.BASIC40;
        }
        if (nominalLength == null) {
            if (inLengthMm <= 6568) {
                nominalLength = EquipNominalLengthEnum.NOM20;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM22) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM22;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (inLengthMm <= 8328) {
                nominalLength = EquipNominalLengthEnum.NOM24;
                basicLength = EquipBasicLengthEnum.BASIC20;
            } else if (inLengthMm <= 9644) {
                nominalLength = EquipNominalLengthEnum.NOM30;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM32) + 1219L) {
                nominalLength = EquipNominalLengthEnum.NOM32;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (inLengthMm <= 12692) {
                nominalLength = EquipNominalLengthEnum.NOM40;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM42) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM42;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (inLengthMm <= 13911) {
                nominalLength = EquipNominalLengthEnum.NOM44;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (inLengthMm <= 14216) {
                nominalLength = EquipNominalLengthEnum.NOM45;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if (inLengthMm <= 15130) {
                nominalLength = EquipNominalLengthEnum.NOM48;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM51) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM51;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM53) + 500L) {
                nominalLength = EquipNominalLengthEnum.NOM53;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM546) + 1000L) {
                nominalLength = EquipNominalLengthEnum.NOM546;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else if ((long)inLengthMm <= this.lengthMm(EquipNominalLengthEnum.NOM60) + 1000L) {
                nominalLength = EquipNominalLengthEnum.NOM60;
                basicLength = EquipBasicLengthEnum.BASIC40;
            } else {
                nominalLength = EquipNominalLengthEnum.NOMXX;
                basicLength = EquipBasicLengthEnum.BASIC20;
            }
        }
        this.setEqtypNominalLength(nominalLength);
        this.setEqtypBasicLength(basicLength);
    }

    public void setDescription(String inDescription) {
        this.setEqtypDescription(inDescription);
    }

    public void setEventsTypeToRecordEnum(EquipEventsTypeToRecordEnum inEventsTypeToRecord) {
        this.setEqtypeEventsTypeToRecordEnum(inEventsTypeToRecord);
    }

    private long lengthMm(EquipNominalLengthEnum inLength) {
        return (long)inLength.getValueInUnits((IMeasurementUnit)LengthUnitEnum.MILLIMETERS);
    }

    private long heightMm(EquipNominalHeightEnum inHeight) {
        return (long)inHeight.getValueInUnits((IMeasurementUnit)LengthUnitEnum.MILLIMETERS);
    }

    @Override
    public Integer getEqtypTeuCapacity() {
        return super.getEqtypTeuCapacity();
    }

    public boolean isMultiEqAttachAllowed() {
        return this.getEqtypeIs2x20NotAllowed() != null && this.getEqtypeIs2x20NotAllowed() == false;
    }

    public boolean isBasic20Allowed() {
        return this.fits20() || this.fits24();
    }

    public boolean isBasic40Allowed() {
        return this.fits30() || this.fits40() || this.fits45() || this.fits48() || this.fits53();
    }
}
