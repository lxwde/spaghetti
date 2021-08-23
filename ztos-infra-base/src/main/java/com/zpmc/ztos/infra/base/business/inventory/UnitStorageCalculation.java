package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.framework.AppCalendarIntervalEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.StorageStartEndDayRuleTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.business.model.Guarantee;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;
import com.zpmc.ztos.infra.base.business.model.VisitDetails;
import com.zpmc.ztos.infra.base.common.configs.InventoryConfig;
import com.zpmc.ztos.infra.base.common.contexts.ArgoExtensionContext;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.events.ArgoCalendarEvent;
import com.zpmc.ztos.infra.base.common.events.Event;
import com.zpmc.ztos.infra.base.common.events.EventManager;
import com.zpmc.ztos.infra.base.common.events.EventType;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.ArgoCalendarEventType;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.zpmc.ztos.infra.base.utils.DateUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class UnitStorageCalculation {
    private static final Logger LOGGER = Logger.getLogger(UnitStorageCalculation.class);
    private double _ufvFlexStorageDouble01;
    private double _ufvFlexStorageDouble02;
    private double _ufvFlexStorageDouble03;
    private String _ufvFlexStorageString01;
    private String _ufvFlexStorageString02;
    private String _ufvFlexStorageString03;
    public static String AFTER_UNIT_STORAGE_CALCULATION_METHOD = "unitStorageCalculationExtension";
    public static String BEFORE_UNIT_STORAGE_CALCULATION_METHOD = "beforeUnitStorageCalculationExtension";
    private String _inChargeFor;
    private int _waivedFreeDays;
    private Date _inProposedPaidThruDay;
    private int _freeDaysAllowed;
    private Long _ruleGkey;
    private Date _calculationStartTime;
    private Date _calculationEndTime;
    private Date _lastFreeDay;
    private Date _paidThruDay;
    private Date _guaranteeThruDay;
    private Date _lineGuaranteeThruDay;
    private ScopedBizUnit _guaranteeParty;
    private ScopedBizUnit _lineGuaranteeParty;
    private Date _powerGuaranteeThruDay;
    private ScopedBizUnit _powerGuaranteeParty;
    private int _daysTotal;
    private int _daysOwed;
    private int _daysPaid;
    private int _waivedFreeDaysBeforeUfvPtd;
    private String _lineOperatorId;
    private StorageRule _srule;
    private boolean _isGratisIncluded;
    private boolean _isFreedaysIncluded;
    private boolean _isEndDayIncluded;
    private boolean _isStartDayIncluded;
    private boolean _isFreeTimeChgedIfExceeded;
    private boolean _isRuleForPower;
    private int _startDayCuttOffHours;
    private int _endDayCuttOffHours;
    private PowerChargeEnum _powerChargeBy;
    private int _powerFirstTierRounding;
    private int _powerOtherTierRounding;
    private int _roundUpHours;
    private int _roundUpMinutes;
    static final String LINE_STORAGE = "LINE_STORAGE";
    static final String STORAGE = "STORAGE";
    static final String POWER = "POWER";
    static final String GRATIS_DAY = "GRATIS_DAY";
    private Date _configuredEndTime;
    static final String WAIVED = "WAIVED";
    static final String WAIVED_FIXED_PRICE = "WAIVED";
    static final String GUARANTEED = "GUARANTEED";
    static final String WAIVED_FREE_NOCHARGE = "WAIVED_FREE_NOCHARGE";
    static final String STORAGE_CALCULATION = "STORAGE CALCULATION";
    static final String CODE_EXTENSION = "CODE EXTENSION";
    static final String UNIT_STORAGE_CALCULATION = "UnitStorageCalculation";
    static final String UNIT_FACILITY_VISIT = "UnitFacilityVisit";
    private ArgoCalendarMatches _calendarMatches = new ArgoCalendarMatches();
    private ArgoCalendarMatches _beforeDaysOwedCalendarMatches = new ArgoCalendarMatches();

    static UnitStorageCalculation calculateStorage(UnitFacilityVisit inUfv, ArgoCalendarEventType[] inExemptTypes, ArgoCalendarEventType[] inGratisTypes, Date inProposedPaidThruDay, Date inProposedStartDate, String inChargeFor) {
        return new UnitStorageCalculation(inUfv, inExemptTypes, inGratisTypes, inProposedPaidThruDay, inProposedStartDate, inChargeFor, false);
    }

    static UnitStorageCalculation calculateStorage(UnitFacilityVisit inUfv, ArgoCalendarEventType[] inExemptTypes, ArgoCalendarEventType[] inGratisTypes, Date inProposedPaidThruDay, Date inProposedStartDate, String inChargeFor, boolean inIsGuaranteeChargeable) {
        return new UnitStorageCalculation(inUfv, inExemptTypes, inGratisTypes, inProposedPaidThruDay, inProposedStartDate, inChargeFor, inIsGuaranteeChargeable);
    }

    private UnitStorageCalculation(UnitFacilityVisit inUfv, ArgoCalendarEventType[] inExemptTypes, ArgoCalendarEventType[] inGratisTypes, Date inProposedPaidThruDay, Date inProposedStartDate, String inChargeFor, boolean inIsGuaranteeChargeable) {
        ArgoCalendar argoCal;
        CalendarTypeEnum calendarTypeEnum;
        this._inChargeFor = inChargeFor;
        this._inProposedPaidThruDay = inProposedPaidThruDay;
        if (inChargeFor.equals(POWER) && !inUfv.getUfvUnit().getUnitRequiresPower().booleanValue()) {
            return;
        }
        List exemptCalendarEvents = null;
        List gratisCalendarEvents = null;
        Boolean isComplexLevelRule = false;
        Date complexPtd = null;
        UserContext userContext = ContextHelper.getThreadUserContext();
        TimeZone timezone = inUfv.getUfvFacility().getTimeZone();
//        UnitStorageCalculationRule rule = this.determineApplicableRule(inUfv, inChargeFor);
//        this._ruleGkey = rule.getStorageRuleTableKey();
//        this._freeDaysAllowed = rule.getFreeDays();
        ScopedBizUnit lineOperator = inUfv.getUfvUnit().getUnitLineOperator();
        if (lineOperator != null) {
            this._lineOperatorId = lineOperator.getBzuName();
            if (this._lineOperatorId == null) {
                this._lineOperatorId = lineOperator.getBzuId();
            }
        }
        if (this._ruleGkey != null && this._ruleGkey > 0L) {
            this._srule = StorageRule.getStorageRule(this._ruleGkey);
            if (this._srule == null) {
                return;
            }
            isComplexLevelRule = StorageStartEndDayRuleTypeEnum.COMPLEX_IN.getKey().equals(this._srule.getSruleStartDay()) && StorageStartEndDayRuleTypeEnum.COMPLEX_OUT.getKey().equals(this._srule.getSruleEndDay());
            this._isFreedaysIncluded = this._srule.getSruleIsFreedaysIncluded();
            this._isGratisIncluded = this._srule.getSruleIsGratisIncluded();
            this._isEndDayIncluded = this._srule.getSruleIsEndDayIncluded();
            this._isStartDayIncluded = this._srule.getSruleIsStartDayIncluded();
            this._isFreeTimeChgedIfExceeded = this._srule.getSruleIsFreeTimeChgedIfExceeded() != null ? this._srule.getSruleIsFreeTimeChgedIfExceeded() : Boolean.FALSE;
            if (this._srule.getSruleStartDayCuttOffHours() != null) {
                this._startDayCuttOffHours = this._srule.getSruleStartDayCuttOffHours().intValue();
            }
            if (this._srule.getSruleEndDayCuttOffHours() != null) {
                this._endDayCuttOffHours = this._srule.getSruleEndDayCuttOffHours().intValue();
            }
            this._powerChargeBy = this._srule.getSrulePowerChargeBy();
            if (this._srule.getSrulePowerFirstTierRounding() != null) {
                this._powerFirstTierRounding = this._srule.getSrulePowerFirstTierRounding().intValue();
            }
            if (this._srule.getSrulePowerOtherTierRounding() != null) {
                this._powerOtherTierRounding = this._srule.getSrulePowerOtherTierRounding().intValue();
            }
            if (this._srule.getSruleRoundUpHours() != null) {
                this._roundUpHours = this._srule.getSruleRoundUpHours().intValue();
            }
            if (this._srule.getSruleRoundUpMinutes() != null) {
                this._roundUpMinutes = this._srule.getSruleRoundUpMinutes().intValue();
            }
            if (this._powerFirstTierRounding == 0 && this._powerOtherTierRounding > 0) {
                LOGGER.error((Object)("Storage PowerFirstTierRounding is NULL/ZERO but PowerOtherTierRounding has value " + this._powerOtherTierRounding + " for " + this._srule.getSruleId()));
                return;
            }
        }
        Date manuallyEnteredLastFreeDay = null;
        if (inChargeFor.equals(STORAGE)) {
            manuallyEnteredLastFreeDay = inUfv.getUfvLastFreeDay();
        } else if (inChargeFor.equals(POWER)) {
            manuallyEnteredLastFreeDay = inUfv.getUfvPowerLastFreeDay();
        } else if (LINE_STORAGE.equals(inChargeFor)) {
            manuallyEnteredLastFreeDay = inUfv.getUfvLineLastFreeDay();
        }
        if (manuallyEnteredLastFreeDay != null) {
            this._lastFreeDay = manuallyEnteredLastFreeDay;
        }
        IUnitStorageManager usm = (IUnitStorageManager)Roastery.getBean((String)"unitStorageManager");
        if (inChargeFor.equals(POWER)) {
            if (isComplexLevelRule.booleanValue()) {
                complexPtd = usm.findPaidThruDayForComplex(inUfv.getUfvUnit().getUnitGkey(), POWER);
            }
            this._paidThruDay = complexPtd != null ? complexPtd : inUfv.getUfvPowerPaidThruDay();
        } else if (inChargeFor.equals(LINE_STORAGE)) {
            if (isComplexLevelRule.booleanValue()) {
                complexPtd = usm.findPaidThruDayForComplex(inUfv.getUfvUnit().getUnitGkey(), LINE_STORAGE);
            }
            this._paidThruDay = complexPtd != null ? complexPtd : inUfv.getUfvLinePaidThruDay();
        } else {
            if (isComplexLevelRule.booleanValue()) {
                complexPtd = usm.findPaidThruDayForComplex(inUfv.getUfvUnit().getUnitGkey(), STORAGE);
            }
            this._paidThruDay = complexPtd != null ? complexPtd : inUfv.getUfvPaidThruDay();
        }
        this.executeUnitStorageCalculationExtension(userContext, inUfv, BEFORE_UNIT_STORAGE_CALCULATION_METHOD);
        if (this._srule != null) {
            this._calculationStartTime = this.getStartTimeForCalculation(inUfv);
            if (inProposedStartDate != null) {
                this._paidThruDay = this.adjustTheDateWithGivenNumber(inProposedStartDate, -1, timezone);
            }
            if (this._calculationStartTime != null && this._lastFreeDay == null) {
                if (this.ruleIncludesExemptTypesInCalculation()) {
                    calendarTypeEnum = LINE_STORAGE.equals(inChargeFor) ? CalendarTypeEnum.STORAGE : CalendarTypeEnum.getEnum((String)inChargeFor);
                    argoCal = this._srule.getSruleCalendar() != null ? this._srule.getSruleCalendar() : ArgoCalendar.findDefaultCalendar((CalendarTypeEnum)calendarTypeEnum);
  //                  exemptCalendarEvents = ArgoCalendarUtil.getEvents((ArgoCalendarEventType[])inExemptTypes, (ArgoCalendar)argoCal);
                }
  //              Date firstPayDay = ArgoCalendarUtil.getEndDate((Date)this._calculationStartTime, (TimeZone)timezone, (int)this._freeDaysAllowed, exemptCalendarEvents, null);
  //              this._lastFreeDay = this.adjustTheDateWithGivenNumber(firstPayDay, -1, timezone);
            }
        }
        this._guaranteeThruDay = inUfv.getUfvGuaranteeThruDay();
        this._guaranteeParty = inUfv.getUfvGuaranteeParty();
        this._powerGuaranteeThruDay = inUfv.getUfvPowerGuaranteeThruDay();
        this._powerGuaranteeParty = inUfv.getUfvPowerGuaranteeParty();
        this._lineGuaranteeThruDay = inUfv.getUfvLineGuaranteeThruDay();
        this._lineGuaranteeParty = inUfv.getUfvLineGuaranteeParty();
        this._configuredEndTime = this.getConfiguredEndTimeForCalculation(inUfv);
        this._calculationEndTime = this.determineCaculationEndTime(this._configuredEndTime, inProposedPaidThruDay);
        if (this._calculationStartTime != null && DateUtil.differenceInCalendarDays((Date)this._calculationStartTime, (Date)this._calculationEndTime, (TimeZone)timezone) < 0L) {
            return;
        }
        if (this._lastFreeDay != null) {
            Date entryStartDate;
            if (inChargeFor.equals(STORAGE) || LINE_STORAGE.equals(inChargeFor) || inChargeFor.equals(POWER) && PowerChargeEnum.DAY.equals((Object)this._powerChargeBy)) {
                if (this.ruleIncludesGratisTypesInCalculation()) {
                    calendarTypeEnum = LINE_STORAGE.equals(inChargeFor) ? CalendarTypeEnum.STORAGE : CalendarTypeEnum.getEnum((String)inChargeFor);
                    argoCal = this._srule.getSruleCalendar() != null ? this._srule.getSruleCalendar() : ArgoCalendar.findDefaultCalendar((CalendarTypeEnum)calendarTypeEnum);
  //                  gratisCalendarEvents = ArgoCalendarUtil.getEvents((ArgoCalendarEventType[])inGratisTypes, (ArgoCalendar)argoCal);
                }
                if (gratisCalendarEvents == null) {
                    gratisCalendarEvents = new ArrayList();
                }
                if (InventoryConfig.BILLING_EXCLUDE_WAIVED_DAYS_FOR_TIER_CALCULATION.isOn(userContext)) {
                    this.addToCalendarEntriesForWaviedAndGuaranteed(inUfv, inChargeFor, gratisCalendarEvents);
                }
                this._daysPaid = this.calculateStorageDaysPaid(this._paidThruDay, this._lastFreeDay, gratisCalendarEvents, timezone, inIsGuaranteeChargeable);
                if (inProposedStartDate != null) {
                    this._waivedFreeDays = this.getWaivedFreeDaysCount(gratisCalendarEvents, timezone);
                    if (this._daysPaid >= this._waivedFreeDays) {
                        this._daysPaid -= this._waivedFreeDays;
                    }
                }
                if (inUfv.getUfvPaidThruDay() == null && manuallyEnteredLastFreeDay == null || inUfv.getUfvLinePaidThruDay() == null && manuallyEnteredLastFreeDay == null) {
                    long diffInDays = DateUtil.differenceInCalendarDays((Date)this._lastFreeDay, (Date)this._calculationEndTime, (TimeZone)timezone);
                    if (this._isFreeTimeChgedIfExceeded && diffInDays > 0L) {
                        this._freeDaysAllowed = 0;
                        this._lastFreeDay = this.adjustTheDateWithGivenNumber(this._calculationStartTime, -1, timezone);
                    }
                }
                this._daysOwed = UnitStorageCalculation.calculateStorageDaysOwed(this._paidThruDay, this._lastFreeDay, this._calculationEndTime, gratisCalendarEvents, timezone, this._calendarMatches);
                this._daysTotal = this.calculateStorageDaysTotal(this._lastFreeDay, this._calculationEndTime, gratisCalendarEvents, timezone);
            } else if (PowerChargeEnum.HOUR.equals((Object)this._powerChargeBy)) {
                if (this._paidThruDay != null) {
                    this._daysPaid = this.getHoursOwed(this._calculationStartTime, this._paidThruDay, timezone);
                }
                this._lastFreeDay = this.adjustTheTimeWithGivenNumber(this._calculationStartTime, this._freeDaysAllowed, timezone);
                this._daysTotal = this.getHoursOwed(this._lastFreeDay, this._calculationEndTime, timezone);
                this._daysOwed = this._daysTotal - this._daysPaid;
                entryStartDate = this._paidThruDay != null ? this._paidThruDay : this._calculationStartTime;
                this.setCalendarEntriesManually(entryStartDate, this._calculationEndTime, this._daysOwed, "POWER CHARGE BY HOUR");
            } else if (PowerChargeEnum.DAY_24HOUR.equals((Object)this._powerChargeBy)) {
                this.getDay24HourOwed(timezone);
                entryStartDate = this._paidThruDay != null ? this._paidThruDay : this._calculationStartTime;
                this.setCalendarEntriesManually(entryStartDate, this._calculationEndTime, this._daysOwed, "POWER CHARGE DAY_24HOUR");
            }
        }
        this.executeUnitStorageCalculationExtension(userContext, inUfv, AFTER_UNIT_STORAGE_CALCULATION_METHOD);
    }

    private void addExemptDayToCalendarEntriesForFreeTimeCharge(List inOutCalendarMatches, List inExemptCalendarEvents, TimeZone inTimezone) {
        if (this._lastFreeDay != null && this._calculationStartTime != null && ArgoUtils.removeTime((Date)this._lastFreeDay, (TimeZone)inTimezone).after(ArgoUtils.removeTime((Date)this._calculationStartTime, (TimeZone)inTimezone)) && inExemptCalendarEvents != null && !inExemptCalendarEvents.isEmpty()) {
            inOutCalendarMatches.addAll(inExemptCalendarEvents);
        }
    }

    private Boolean isDaysOwedExceededFreePeriod(String inChargeFor, UnitFacilityVisit inUfv, List inEvents, TimeZone inTimeZone) {
        Boolean retValue = Boolean.FALSE;
        if (STORAGE.equals(inChargeFor) && inUfv.getUfvPaidThruDay() == null || LINE_STORAGE.equals(inChargeFor) && inUfv.getUfvLinePaidThruDay() == null || POWER.equals(inChargeFor) && inUfv.getUfvPowerPaidThruDay() == null) {
            Date startDay = this.adjustTheDateWithGivenNumber(this._calculationStartTime, -1, inUfv.getUfvFacility().getTimeZone());
            int daysOwed = UnitStorageCalculation.calculateStorageDaysOwed(null, startDay, this._calculationEndTime, inEvents, inTimeZone, this._beforeDaysOwedCalendarMatches);
            if (this._daysPaid == 0 && daysOwed > this._freeDaysAllowed) {
                retValue = Boolean.TRUE;
            }
        }
        return retValue;
    }

    private int getHoursOwed(Date inStartTime, Date inEndTime, TimeZone inTimezone) {
        int retHours = this.roundMinuteAndDayToHours(inStartTime, inEndTime, inTimezone);
        if (this._powerOtherTierRounding > 0 && retHours > this._powerFirstTierRounding && this._powerFirstTierRounding > 0) {
            int retHoursMinusFirstTierRounding = retHours - this._powerFirstTierRounding;
            if (retHoursMinusFirstTierRounding > 0) {
                int quotient = retHoursMinusFirstTierRounding / this._powerOtherTierRounding;
                int reminder = retHoursMinusFirstTierRounding % this._powerOtherTierRounding;
                if (reminder > 0) {
                    retHours = quotient * this._powerOtherTierRounding + this._powerOtherTierRounding + this._powerFirstTierRounding;
                }
            }
        } else if (retHours < this._powerFirstTierRounding) {
            retHours = this._powerFirstTierRounding;
        }
        return retHours;
    }

    private void getDay24HourOwed(TimeZone inTimezone) {
        int paidDays = 0;
        if (this._paidThruDay != null) {
            paidDays = this.roundItToDays(this._calculationStartTime, this._paidThruDay, inTimezone);
            if (this._powerFirstTierRounding > 0 && paidDays < this._powerFirstTierRounding) {
                paidDays = this._powerFirstTierRounding;
            }
        }
        this._daysPaid = paidDays;
        this._daysTotal = this.roundItToDays(this._calculationStartTime, this._calculationEndTime, inTimezone);
        if (this._daysTotal == 0) {
            this._daysTotal = this._powerFirstTierRounding;
        }
        this._daysOwed = this._daysTotal - this._daysPaid;
        if ((this._daysTotal <= 0 || this._daysPaid <= 0 || this._daysOwed != 0) && this._daysOwed < this._powerFirstTierRounding) {
            this._daysOwed = this._powerFirstTierRounding;
        }
    }

    private int roundItToDays(Date inStartDate, Date inEndDate, TimeZone inTimezone) {
        int roundedDays = 0;
        int tempDays = (int) DateUtil.differenceInDays((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone);
        int tempHours = (int)DateUtil.differenceInHoursMinusDays((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone);
        int tempMinutes = (int)DateUtil.differenceInMinutesMinusDaysAndHours((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone);
        if (this._roundUpMinutes > 0 && tempMinutes >= this._roundUpMinutes) {
            ++tempHours;
            tempMinutes = 0;
        }
        if (tempHours >= 24) {
            ++tempDays;
            tempHours = 0;
        }
        if (this._roundUpHours > 0 && tempHours >= this._roundUpHours) {
            ++tempDays;
            tempHours = 0;
        }
        roundedDays = tempDays;
        return roundedDays;
    }

    private int roundMinuteAndDayToHours(Date inStartDate, Date inEndDate, TimeZone inTimezone) {
        int roundedHours = 0;
        inStartDate = DateUtils.truncate((Date)inStartDate, (int)12);
        inEndDate = DateUtils.truncate((Date)inEndDate, (int)12);
        int tempDays = (int)DateUtil.differenceInDays((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone);
        int tempHours = (int)DateUtil.differenceInHoursMinusDays((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone);
        int tempMinutes = (int) Math.round(DateUtil.differenceInMinutesMinusDaysAndHours((Date)inStartDate, (Date)inEndDate, (TimeZone)inTimezone));
        if (this._roundUpMinutes > 0 && tempMinutes >= this._roundUpMinutes) {
            ++tempHours;
            tempMinutes = 0;
        }
        if (tempDays > 0) {
            int daysToHours = tempDays * 24;
            tempHours += daysToHours;
        }
        if (this._roundUpHours > 0 && tempHours < this._roundUpHours) {
            tempHours = this._roundUpHours;
        }
        roundedHours = tempHours;
        return roundedHours;
    }

    private void setCalendarEntriesManually(Date inStartDate, Date inEndDate, int inDuration, String inEventName) {
        ArgoCalendarEventType eventType = new ArgoCalendarEventType(inEventName);
        ArgoCalendarEvent calEvent = this.createCalendarEvent(inEventName, eventType, inStartDate, AppCalendarIntervalEnum.ONCE, inEndDate);
        this._calendarMatches.logCalendarEntriesManually(calEvent, inStartDate, inEndDate, inDuration);
    }

    private boolean ruleIncludesExemptTypesInCalculation() {
        return this._isFreedaysIncluded;
    }

    private boolean ruleIncludesGratisTypesInCalculation() {
        return this._isGratisIncluded;
    }

//    private UnitStorageCalculationRule determineApplicableRule(UnitFacilityVisit inUfv, String inChargeFor) {
//        UnitStorageCalculationRule rule = null;
//        LineOperator lineOp = LineOperator.resolveLineOprFromScopedBizUnit((ScopedBizUnit)inUfv.getUfvUnit().getUnitLineOperator());
//        if (lineOp != null) {
//            FieldValue[] fieldValues;
//            EntityMappingPredicate entityMapper = null;
//            if (inChargeFor.equals(STORAGE)) {
//                entityMapper = lineOp.getLineopDemurrageRules();
//            } else if (inChargeFor.equals(POWER)) {
//                entityMapper = lineOp.getLineopPowerRules();
//            } else if (inChargeFor.equals(LINE_STORAGE)) {
//                entityMapper = lineOp.getLineopLineDemurrageRules();
//            }
//            if (entityMapper != null && (fieldValues = entityMapper.mapEntity((IValueSource)inUfv)) != null) {
////                rule = new UnitStorageCalculationRule(fieldValues, inChargeFor);
//            }
//        }
//        if (rule == null) {
//            rule = new UnitStorageCalculationRule(0, new Long(0L));
//        }
//        return rule;
//    }

    private static int calculateStorageDaysOwed(Date inPaidThruDay, Date inLastFreeDay, Date inEndTime, List inEvents, TimeZone inTimeZone, ArgoCalendarMatches inCalendarMatches) {
        int daysOwed = 0;
        Date outTime;
        Date solDay = inPaidThruDay != null && inPaidThruDay.after(inLastFreeDay) ? inPaidThruDay : inLastFreeDay;
        Date date = outTime = inEndTime != null ? inEndTime : ArgoUtils.timeNow();
        if (outTime.before(solDay) || DateUtil.isSameDay((Date)solDay, (Date)outTime, (TimeZone)inTimeZone)) {
            daysOwed = 0;
        } else {
            Calendar calendar = Calendar.getInstance(inTimeZone);
            calendar.setTimeInMillis(solDay.getTime());
            calendar.add(6, 1);
            Date firstPayDay = calendar.getTime();
    //        daysOwed = ArgoCalendarUtil.getEventFreeDays((Date)firstPayDay, (Date)outTime, (TimeZone)inTimeZone, (List)inEvents, null, (ArgoCalendarMatches)inCalendarMatches);
        }
        return daysOwed;
    }

    private int calculateStorageDaysTotal(Date inLastFreeDay, Date inEndTime, List inEvents, TimeZone inTimeZone) {
        int daysOwed = 0;
        Date outTime;
        Date solDay = inLastFreeDay;
        Date date = outTime = inEndTime != null ? inEndTime : ArgoUtils.timeNow();
        if (outTime.before(solDay) || DateUtil.isSameDay((Date)solDay, (Date)outTime, (TimeZone)inTimeZone)) {
            daysOwed = 0;
        } else {
            Calendar calendar = Calendar.getInstance(inTimeZone);
            calendar.setTimeInMillis(solDay.getTime());
            calendar.add(6, 1);
            Date firstPayDay = calendar.getTime();
//            ArgoCalendarMatches dummyCalendarMatches = new ArgoCalendarMatches();
//            daysOwed = ArgoCalendarUtil.getEventFreeDays((Date)firstPayDay, (Date)outTime, (TimeZone)inTimeZone, (List)inEvents, null, (ArgoCalendarMatches)dummyCalendarMatches);
        }
        return daysOwed;
    }

    private int calculateStorageDaysPaid(Date inPaidThruDay, Date inLastFreeDay, List inEvents, TimeZone inTimeZone) {
        return this.calculateStorageDaysPaid(inPaidThruDay, inLastFreeDay, inEvents, inTimeZone, false);
    }

    private int calculateStorageDaysPaid(Date inPaidThruDay, Date inLastFreeDay, List inEvents, TimeZone inTimeZone, boolean inIsGuaranteeChargeable) {
        int daysPaid = 0;
        if (inPaidThruDay != null) {
            Calendar calendar = Calendar.getInstance(inTimeZone);
            calendar.setTimeInMillis(inLastFreeDay.getTime());
            calendar.add(6, 1);
            Date firstPayDay = calendar.getTime();
//            daysPaid = ArgoCalendarUtil.getEventFreeDays((Date)firstPayDay, (Date)inPaidThruDay, (TimeZone)inTimeZone, (List)inEvents, null, (ArgoCalendarMatches)new ArgoCalendarMatches());
        }
        return daysPaid;
    }

    private Date determineCaculationEndTime(Date inConfiguredEndTime, Date inProposedPaidThruDay) {
        Date endTime = inConfiguredEndTime == null && inProposedPaidThruDay == null ? ArgoUtils.timeNow() : (inConfiguredEndTime != null && inProposedPaidThruDay == null ? inConfiguredEndTime : (inConfiguredEndTime == null ? inProposedPaidThruDay : (inConfiguredEndTime.after(inProposedPaidThruDay) ? inProposedPaidThruDay : inConfiguredEndTime)));
        return endTime;
    }

    @Nullable
    private Date getStartTimeForCalculation(UnitFacilityVisit inUfv) {
        Date startTime = null;
        Unit unit = inUfv.getUfvUnit();
        CarrierVisit cv = null;
        cv = UnitCategoryEnum.EXPORT.equals((Object)inUfv.getUfvUnit().getUnitCategory()) ? (inUfv.getUfvActualObCv() != null ? inUfv.getUfvActualObCv() : inUfv.getUfvIntendedObCv()) : unit.getUnitDeclaredIbCv();
        VisitDetails cvd = cv == null ? null : cv.getCvCvd();
        String startDateValue = this._srule.getSruleStartDay();
        if (StorageStartEndDayRuleTypeEnum.YARD_IN.getKey().equals(startDateValue)) {
            startTime = inUfv.getUfvTimeIn();
        } else if (StorageStartEndDayRuleTypeEnum.DIS_DONE.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCvdTimeDischargeComplete();
            }
        } else if (StorageStartEndDayRuleTypeEnum.FFD.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCvdInboundFirstFreeDay();
            }
        } else if (StorageStartEndDayRuleTypeEnum.FAD.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCvdTimeFirstAvailability();
            }
        } else if (StorageStartEndDayRuleTypeEnum.ATA.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cv.getCvATA();
            }
        } else if (StorageStartEndDayRuleTypeEnum.ATD.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cv.getCvATD();
            }
        } else if (StorageStartEndDayRuleTypeEnum.ETA.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCvdETA();
            }
        } else if (StorageStartEndDayRuleTypeEnum.START_WORK.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCarrierStartWorkTime();
            }
        } else if (StorageStartEndDayRuleTypeEnum.END_WORK.getKey().equals(startDateValue)) {
            if (cvd != null) {
                startTime = cvd.getCarrierEndWorkTime();
            }
        } else if (StorageStartEndDayRuleTypeEnum.COMPLEX_IN.getKey().equals(startDateValue)) {
            startTime = inUfv.getUfvUnit().getUnitComplexInTime();
        } else if (StorageStartEndDayRuleTypeEnum.POWER_CONNECT.getKey().equals(startDateValue)) {
            EventManager em = (EventManager) Roastery.getBean((String)"eventManager");
            Event event = null;
            if (inUfv.getUfvTimeIn() != null) {
     //           event = em.findEventsLaterThanAppliedDate(inUfv.getUfvTimeIn(), (IEventType) EventEnum.UNIT_POWER_CONNECT, (IServiceable)unit);
            }
            startTime = event != null ? event.getEvntAppliedDate() : inUfv.getUfvTimeIn();
        } else if (StorageStartEndDayRuleTypeEnum.CUSTOM.getKey().equals(startDateValue)) {
            String startDateExtension = this._srule.getSruleStartDayExtension().getExtensionName();
            FieldChanges changes = new FieldChanges();
            changes.setFieldChange(IInventoryBizMetafield.SRULE_START_DAY_EXTENSION, (Object)startDateExtension);
            changes.setFieldChange(IInventoryField.UFV_UNIT, (Object)inUfv.getUfvUnit());
            changes.setFieldChange(IInventoryField.UNIT_ACTIVE_UFV, (Object)inUfv);
            startTime = this.getExtensionStartDate(startDateExtension, changes);
        } else {
            startTime = inUfv.getUfvTimeIn();
        }
        if (!this._isStartDayIncluded && startTime != null) {
            long datePlusOne = startTime.getTime() + 86400000L;
            startTime = new Date(datePlusOne);
        }
        if (startTime == null) {
            LOGGER.warn((Object)("Storage Start Time is null! For rule start day " + startDateValue + " on unit " + unit.getUnitId()));
        }
        return startTime;
    }

    @Nullable
    private Date getConfiguredEndTimeForCalculation(UnitFacilityVisit inUfv) {
        Date calculationEndDate = null;
        Object recentPowerConnectDate = null;
        if (this._srule == null) {
            calculationEndDate = inUfv.getUfvTimeOut();
        } else {
            String endDateValue = this._srule.getSruleEndDay();
            CarrierVisit actualObCv = inUfv.getUfvActualObCv();
            VisitDetails cvd = null;
            if (actualObCv != null) {
                cvd = actualObCv.getCvCvd();
            }
            if (StorageStartEndDayRuleTypeEnum.OB_CARRIER_WORK_START.getKey().equals(endDateValue)) {
                if (actualObCv != null) {
                    calculationEndDate = actualObCv.getCarrierStartWorkTime();
                }
            } else if (StorageStartEndDayRuleTypeEnum.YARD_OUT.getKey().equals(endDateValue)) {
                calculationEndDate = inUfv.getUfvTimeOut();
            } else if (StorageStartEndDayRuleTypeEnum.COMPLEX_OUT.getKey().equals(endDateValue)) {
                calculationEndDate = inUfv.getUfvUnit().isActive() ? null : inUfv.getUfvUnit().getUnitComplexOutTime();
            } else if (StorageStartEndDayRuleTypeEnum.TIME_OF_LOADING.getKey().equals(endDateValue)) {
                calculationEndDate = inUfv.getUfvTimeOfLoading();
            } else if (StorageStartEndDayRuleTypeEnum.ETA.getKey().equals(endDateValue)) {
                if (cvd != null) {
                    calculationEndDate = cvd.getCvdETA();
                }
            } else if (StorageStartEndDayRuleTypeEnum.ATA.getKey().equals(endDateValue)) {
                if (actualObCv != null) {
                    calculationEndDate = actualObCv.getCvATA();
                }
            } else if (StorageStartEndDayRuleTypeEnum.ATD.getKey().equals(endDateValue)) {
                if (actualObCv != null) {
                    calculationEndDate = actualObCv.getCvATD();
                }
            } else if (StorageStartEndDayRuleTypeEnum.END_WORK.getKey().equals(endDateValue)) {
                if (cvd != null) {
                    calculationEndDate = cvd.getCarrierEndWorkTime();
                }
            } else if (StorageStartEndDayRuleTypeEnum.POWER_DISCONNECT.getKey().equals(endDateValue)) {
                calculationEndDate = this.getPowerCalculationEndTime(inUfv);
            } else if (StorageStartEndDayRuleTypeEnum.TIME_LABOR_ON_BOARD.getKey().equals(endDateValue)) {
                if (actualObCv != null && cvd != null) {
                    calculationEndDate = cvd.getCarrierTimeLaborOnBoard();
                }
            } else if (StorageStartEndDayRuleTypeEnum.CUSTOM.getKey().equals(endDateValue)) {
                String endDateExtension = this._srule.getSruleEndDayExtension().getExtensionName();
                FieldChanges changes = new FieldChanges();
                changes.setFieldChange(IInventoryBizMetafield.SRULE_END_DAY_EXTENSION, (Object)endDateExtension);
                changes.setFieldChange(IInventoryField.UFV_UNIT, (Object)inUfv.getUfvUnit());
                changes.setFieldChange(IInventoryField.UNIT_ACTIVE_UFV, (Object)inUfv);
                calculationEndDate = this.getExtensionEndDate(endDateExtension, changes);
            } else {
                calculationEndDate = inUfv.getUfvTimeOut();
            }
        }
        if (!this._isEndDayIncluded && calculationEndDate != null) {
            long datePlusOne = calculationEndDate.getTime() - 86400000L;
            calculationEndDate = new Date(datePlusOne);
        }
        return calculationEndDate;
    }

    private Date getExtensionStartDate(String inStartDateExtension, FieldChanges inChanges) {
        ArgoExtensionContext extensionContext = ArgoExtensionContext.createExtensionContext((UserContext)ContextHelper.getThreadUserContext(), (String)inStartDateExtension, null);
        IStorageRuleExtensionHandler handler = (IStorageRuleExtensionHandler) PortalApplicationContext.getBean((String)"storageRuleExtensionHandler");
        extensionContext.setParameter((Object)"EFieldChanges", (Object)inChanges);
//        StorageRuleExtensionResponse response = (StorageRuleExtensionResponse)handler.invoke((IExtensionContext)extensionContext);
//        return response.getCustomizedStartDate();
        return null;
    }

    private Date getExtensionEndDate(String inEndDateExtension, FieldChanges inChanges) {
        ArgoExtensionContext extensionContext = ArgoExtensionContext.createExtensionContext((UserContext)ContextHelper.getThreadUserContext(), (String)inEndDateExtension, null);
        IStorageRuleExtensionHandler handler = (IStorageRuleExtensionHandler)PortalApplicationContext.getBean((String)"storageRuleExtensionHandler");
        extensionContext.setParameter((Object)"EFieldChanges", (Object)inChanges);
//        StorageRuleExtensionResponse response = (StorageRuleExtensionResponse)handler.invoke((IExtensionContext)extensionContext);
//        return response.getCustomizedEndDate();
        return null;
    }

    private Date getPowerCalculationEndTime(UnitFacilityVisit inUfv) {
        Date calculationEndDate = null;
        Date lastPowerConnectDate = null;
        Date lastPowerDisConnectDate = null;
        EventManager em = (EventManager)Roastery.getBean((String)"eventManager");
        EventType powerDisconnectEventType = EventType.resolveIEventType((IEventType) EventEnum.UNIT_POWER_DISCONNECT);
//        Event lastPowerDisconnectevent = em.getMostRecentEventByType(powerDisconnectEventType, (IServiceable)inUfv.getUfvUnit());
//        EventType powreConnectEventType = EventType.resolveIEventType((IEventType)EventEnum.UNIT_POWER_CONNECT);
//        Event lastPowerConnectEvent = em.getMostRecentEventByType(powreConnectEventType, (IServiceable)inUfv.getUfvUnit());
//        if (lastPowerConnectEvent != null) {
//            lastPowerConnectDate = lastPowerConnectEvent.getEvntAppliedDate();
//        }
//        if (lastPowerDisconnectevent != null) {
//            lastPowerDisConnectDate = lastPowerDisconnectevent.getEvntAppliedDate();
//        }
        if (lastPowerConnectDate != null && lastPowerDisConnectDate != null && lastPowerConnectDate.after(lastPowerDisConnectDate)) {
            lastPowerDisConnectDate = null;
        }
        calculationEndDate = inUfv.getUfvTimeOfLoading() != null ? (lastPowerDisConnectDate != null ? lastPowerDisConnectDate : inUfv.getUfvTimeOfLoading()) : lastPowerDisConnectDate;
        return calculationEndDate;
    }

    private void addToCalendarEntriesForWaviedAndGuaranteed(UnitFacilityVisit inUfv, String inChargeFor, List inCalendarEvents) {
        List waiverList;
        ChargeableUnitEvent cue = null;
        ChargeableUnitEventTypeEnum evenTypeEnum = null;
        if (inChargeFor.equals(POWER)) {
            evenTypeEnum = ChargeableUnitEventTypeEnum.REEFER;
        } else if (inChargeFor.equals(STORAGE)) {
            evenTypeEnum = ChargeableUnitEventTypeEnum.STORAGE;
        } else if (inChargeFor.equals(LINE_STORAGE)) {
            evenTypeEnum = ChargeableUnitEventTypeEnum.LINE_STORAGE;
        }
        cue = UnitEventExtractManager.findExistingChargeableUnitStorageEvent(inUfv.getUfvFacility(), inUfv, evenTypeEnum);
        if (cue != null && !(waiverList = Guarantee.getListOfGuarantees((BillingExtractEntityEnum) BillingExtractEntityEnum.INV, (Serializable)cue.getPrimaryKey())).isEmpty()) {
            for (Object gte : waiverList) {
                ArgoCalendarEventType eventType = new ArgoCalendarEventType(GUARANTEED);
                String calDesc = null;
                if (GuaranteeTypeEnum.PAID.equals((Object)((Guarantee)gte).getGnteGuaranteeType())) continue;
                if (GuaranteeTypeEnum.WAIVER.equals((Object)((Guarantee)gte).getGnteGuaranteeType())) {
                    if (GuaranteeOverrideTypeEnum.FREE_NOCHARGE.equals((Object)((Guarantee)gte).getGnteOverrideValueType())) {
                        calDesc = "WAIVED_" + GuaranteeOverrideTypeEnum.FREE_NOCHARGE.getKey();
                    } else if (!UnitStorageCalculation.isFixedPriceWaiverAlreadyGuaranteed((Guarantee)gte)) {
                        calDesc = "WAIVED_" + GuaranteeOverrideTypeEnum.FIXED_PRICE.getKey();
                    }
                } else {
                    calDesc = GUARANTEED;
                }
                ArgoCalendarEvent calEvent = this.createCalendarEvent(calDesc, eventType, ((Guarantee)gte).getGnteGuaranteeStartDay(), AppCalendarIntervalEnum.DAILY, ((Guarantee)gte).getGnteGuaranteeEndDay());
                inCalendarEvents.add(calEvent);
            }
        }
    }

    private int getWaivedFreeDaysCount(List inGratisCalendarEvents) {
        int waivedFreeNoChargeDaysBeforePTD = 0;
        for (int i = 0; i < inGratisCalendarEvents.size(); ++i) {
            ArgoCalendarEvent event = (ArgoCalendarEvent)inGratisCalendarEvents.get(i);
            if (!WAIVED_FREE_NOCHARGE.equals(event.getArgocalevtName())) continue;
            ++waivedFreeNoChargeDaysBeforePTD;
        }
        return waivedFreeNoChargeDaysBeforePTD;
    }

    private int getWaivedFreeDaysCount(List inGratisCalendarEvents, TimeZone inTimeZone) {
        int waivedFreeNoChargeDaysBeforePTD = 0;
        for (int i = 0; i < inGratisCalendarEvents.size(); ++i) {
            ArgoCalendarEvent event = (ArgoCalendarEvent)inGratisCalendarEvents.get(i);
            if (!WAIVED_FREE_NOCHARGE.equals(event.getArgocalevtName())) continue;
            Date waiveStartDate = event.getArgocalevtOccurrDateStart();
            Date waiveEndDate = event.getArgocalevtRecurrDateEnd();
            if (waiveStartDate != null && waiveEndDate != null) {
                Calendar startCalendar = Calendar.getInstance(inTimeZone);
                startCalendar.setTime(waiveStartDate);
                while (startCalendar.getTimeInMillis() <= waiveEndDate.getTime()) {
                    ++waivedFreeNoChargeDaysBeforePTD;
                    startCalendar.add(6, 1);
                }
                continue;
            }
            ++waivedFreeNoChargeDaysBeforePTD;
        }
        return waivedFreeNoChargeDaysBeforePTD;
    }

    protected ArgoCalendarEvent createCalendarEvent(String inName, ArgoCalendarEventType inType, Date inOccurrStartDate, AppCalendarIntervalEnum inInterval, Date inRecurEndDate) {
        ArgoCalendarEvent calEvent = new ArgoCalendarEvent();
        calEvent.setArgocalevtName(inName);
        calEvent.setArgocalevtEventType(inType);
        calEvent.setArgocalevtInterval(inInterval);
        calEvent.setArgocalevtOccurrDateStart(inOccurrStartDate);
        calEvent.setArgocalevtRecurrDateEnd(inRecurEndDate);
        return calEvent;
    }

    public long getStorageRuleTableKey() {
        return this._ruleGkey != null ? this._ruleGkey : 0L;
    }

    private Date adjustTheDateWithGivenNumber(Date inDate, int inAdjustDay, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone);
        calendar.setTime(inDate);
        calendar.add(5, inAdjustDay);
        return calendar.getTime();
    }

    private static boolean isFixedPriceWaiverAlreadyGuaranteed(Guarantee inGte) {
        return Guarantee.getListOfGuaranteesForMatchedRelatedGuaranteeGkey((Guarantee)inGte);
    }

    private Date adjustTheTimeWithGivenNumber(Date inDate, int inAdjustDay, TimeZone inTimeZone) {
        Calendar calendar = Calendar.getInstance(inTimeZone);
        calendar.setTime(inDate);
        calendar.add(10, inAdjustDay);
        return calendar.getTime();
    }

    @Nullable
    public String getRuleType() {
        String sruleId = this._srule != null ? (String)this._srule.getFieldValue(IInventoryField.SRULE_ID) : "NO_RULE";
        return sruleId;
    }

    @Nullable
    public String getRuleStartDayType() {
        String sruleStartDayType = "";
        if (this._srule != null && (sruleStartDayType = (String)this._srule.getFieldValue(IInventoryField.SRULE_START_DAY)) != null && StorageStartEndDayRuleTypeEnum.CUSTOM.getName().equals(sruleStartDayType)) {
            sruleStartDayType = this._srule.getSruleStartDayExtension() != null ? this._srule.getSruleStartDayExtension().getExtensionName() : "";
        }
        return sruleStartDayType;
    }

    @Nullable
    public String getRuleEndDayType() {
        String sruleEndDayType = "";
        if (this._srule != null && (sruleEndDayType = (String)this._srule.getFieldValue(IInventoryField.SRULE_END_DAY)) != null && StorageStartEndDayRuleTypeEnum.CUSTOM.getName().equals(sruleEndDayType)) {
            sruleEndDayType = this._srule.getSruleEndDayExtension() != null ? this._srule.getSruleEndDayExtension().getExtensionName() : "";
        }
        return sruleEndDayType;
    }

    public boolean isStartDayIncluded() {
        return this._isStartDayIncluded;
    }

    public boolean isEndDayIncluded() {
        return this._isEndDayIncluded;
    }

    public boolean isFreeTimeChgedIfExceeded() {
        return this._isFreeTimeChgedIfExceeded;
    }

    public String getRuleCalendarId() {
        String ruleCalendarId = "";
        if (this._srule != null) {
            ruleCalendarId = this._srule.getSruleCalendar() != null ? this._srule.getSruleCalendar().getArgocalId() : "";
        }
        return ruleCalendarId;
    }

    public int getFreeDaysAllowed() {
        return this._freeDaysAllowed;
    }

    public Date getCalculationStartTime() {
        return this._calculationStartTime;
    }

    public Date getCalculationEndTime() {
        return this._calculationEndTime;
    }

    public Date getConfiguredEndTime() {
        return this._configuredEndTime;
    }

    public boolean isRuleUndefined() {
        return this._srule == null;
    }

    public boolean isStartDateNull() {
        return this._calculationStartTime == null;
    }

    public Date getLastFreeDay() {
        return this._lastFreeDay;
    }

    public Date getPaidThruDay() {
        return this._paidThruDay;
    }

    public Date getGuaranteeThruDay() {
        return this._guaranteeThruDay;
    }

    public Date getLineGuaranteeThruDay() {
        return this._lineGuaranteeThruDay;
    }

    public ScopedBizUnit getLineGuaranteeParty() {
        return this._lineGuaranteeParty;
    }

    public ScopedBizUnit getGuaranteeParty() {
        return this._guaranteeParty;
    }

    public Date getPowerGuaranteeThruDay() {
        return this._powerGuaranteeThruDay;
    }

    public ScopedBizUnit getPowerGuaranteeParty() {
        return this._powerGuaranteeParty;
    }

    public int getDaysOwed() {
        return this._daysOwed;
    }

    public int getStorageDaysTotal() {
        return this._daysTotal;
    }

    public int getDaysPaid() {
        return this._daysPaid;
    }

    public String getLineOperatorId() {
        return this._lineOperatorId;
    }

//    public ArgoCalendarMatches getCalendarMatches() {
//        return this._calendarMatches;
//    }

    public int getStartDayCuttOffHours() {
        return this._startDayCuttOffHours;
    }

    public int getEndDayCuttOffHours() {
        return this._endDayCuttOffHours;
    }

    public int getPowerFirstTierRounding() {
        return this._powerFirstTierRounding;
    }

    public int getPowerOtherTierRounding() {
        return this._powerOtherTierRounding;
    }

    public PowerChargeEnum getPowerChargeBy() {
        return this._powerChargeBy;
    }

    public String getChargeFor() {
        return this._inChargeFor;
    }

    public int getWaivedFreeDays() {
        return this._waivedFreeDays;
    }

    public Date getInProposedPaidThruDay() {
        return this._inProposedPaidThruDay;
    }

    public double getUfvFlexStorageDouble01() {
        return this._ufvFlexStorageDouble01;
    }

    public double getUfvFlexStorageDouble02() {
        return this._ufvFlexStorageDouble02;
    }

    public double getUfvFlexStorageDouble03() {
        return this._ufvFlexStorageDouble03;
    }

    public String getUfvFlexStorageString01() {
        return this._ufvFlexStorageString01;
    }

    public String getUfvFlexStorageString02() {
        return this._ufvFlexStorageString02;
    }

    public String getUfvFlexStorageString03() {
        return this._ufvFlexStorageString03;
    }

    public void setUfvFlexStorageDouble01(Double inUfvFlexStorageDouble01) {
        this._ufvFlexStorageDouble01 = inUfvFlexStorageDouble01;
    }

    public void setUfvFlexStorageDouble02(Double inUfvFlexStorageDouble02) {
        this._ufvFlexStorageDouble02 = inUfvFlexStorageDouble02;
    }

    public void setUfvFlexStorageDouble03(Double inUfvFlexStorageDouble03) {
        this._ufvFlexStorageDouble03 = inUfvFlexStorageDouble03;
    }

    public void setUfvFlexStorageString01(String inUfvFlexStorageString01) {
        this._ufvFlexStorageString01 = inUfvFlexStorageString01;
    }

    public void setUfvFlexStorageString02(String inUfvFlexStorageString02) {
        this._ufvFlexStorageString02 = inUfvFlexStorageString02;
    }

    public void setUfvFlexStorageString03(String inUfvFlexStorageString03) {
        this._ufvFlexStorageString03 = inUfvFlexStorageString03;
    }

    private void executeUnitStorageCalculationExtension(UserContext inUserContext, UnitFacilityVisit inUfv, String inBeforeOrAfter) {
        String calculationExtension = null;
        if (this._srule != null) {
            if (this._srule.getSruleCalculationExtension() != null) {
                calculationExtension = this._srule.getSruleCalculationExtension().getExtensionName();
            }
            if (calculationExtension != null) {
                ArgoExtensionContext extensionContext = null;
                extensionContext = ArgoExtensionContext.createExtensionContext((UserContext)inUserContext, (String)calculationExtension, (String)inBeforeOrAfter);
                if (extensionContext != null) {
                    HashMap<String, Object> inParm = new HashMap<String, Object>();
                    inParm.put(UNIT_STORAGE_CALCULATION, this);
                    inParm.put(UNIT_FACILITY_VISIT, inUfv);
                    extensionContext.setParameters(inParm);
//                    IStorageCalculationExtensionHandler handler = (IStorageCalculationExtensionHandler)Roastery.getBean((String)"storageCalculationExtensionHandler");
//                    ArgoExtensionResponse response = (ArgoExtensionResponse)handler.invoke((IExtensionContext)extensionContext);
                    inParm.clear();
                }
            }
        }
    }
}
