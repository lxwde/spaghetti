package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.GuaranteeDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BillingExtractEntityEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.GuaranteeOverrideTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.GuaranteeTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.inventory.ChargeableUnitEvent;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.type.AggregateFunctionType;
import com.zpmc.ztos.infra.base.common.utils.ArgoUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Namespace;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Guarantee extends GuaranteeDO {
    private static String APPLIED_TO_CLASS = "appliedToClass";
    private static String APPLIED_TO_PRIMARY_KEY = "appliedToPrimaryKey";
    private static String APPLIED_TO_NATURAL_KEY = "appliedToNaturalKey";
    private static String GUARANTEE_ID = "guaranteeId";
    private static final String EVENT_TYPE_ID = "eventTypeId";
    private static final String EVENT_GKEY = "eventGkey";
    private static String N4_USERID = "n4UserId";
    private static String EXTERNAL_USERID = "externalUserId";
    private static String EXTERNAL_CONTACT_NAME = "externalContactName";
    private static String EXTERNAL_ADDRESS1 = "externalAddress1";
    private static String EXTERNAL_ADDRESS2 = "externalAddress2";
    private static String EXTERNAL_ADDRESS3 = "externalAddress3";
    private static String EXTERNAL_CITY = "externalCity";
    private static String EXTERNAL_STATE = "externalState";
    private static String EXTERNAL_COUNTRY = "externalCountry";
    private static String EXTERNAL_TELEPHONE = "externalTelephone";
    private static String EXTERNAL_FAX = "externalFax";
    private static String EXTERNAL_EMAIL_ADDRESS = "externalEmailAddress";
    private static String GUARANTEE_TYPE = "guaranteeType";
    private static String GUARANTEE_START_DAY = "guaranteeStartDay";
    private static String GUARANTEE_END_DAY = "guaranteeEndDay";
    private static String QUANTITY = "quantity";
    private static String GUARANTEE_AMOUNT = "guaranteeAmount";
    private static String VOIDED_OR_EXPIRED_DATE = "voidedOrExpiredDate";
    private static String NOTES = "notes";
    private static String PAYMENT_TYPE = "paymentType";
    private static String GUARANTEE_CUSTOMER = "guaranteeCustomer";
    private static String AUTHORIZATION_NBR = "authorizationNbr";
    private static String AUTHORIZATION_EXPIRATION = "authorizationExpiration";
    private static String TRANSACTION_REFERENCE = "transactionReference";
    private static String CUSTOMER_REFERENCE_ID = "customerReferenceId";
    private static String GUARANTEE_CUSTOMER_BIZROLE = "guaranteeCustomerBizRole";
    private static final Logger LOGGER = Logger.getLogger(Guarantee.class);
    private static final SimpleDateFormat XML_DATE_TIME_ZONE_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
    static final String REEFER = "REEFER";
    static final String STORAGE = "STORAGE";
    public static final String PEA_NAME = "updateUfvGuaranteeThruDayAndPartyINV";
    public static String QUEUED = "QUEUED";
    public static String GUARANTEED = "GUARANTEED";
    private String _gnteActionForGroovyValidation;

    public String getGuaranteeIdFromSequenceProvide() {
//        BillingSequenceProvider seqProvider = new BillingSequenceProvider();
//        String gntId = seqProvider.getGuaranteeIdNextSeqValue().toString();
//        return gntId;
        return null;
    }

    public void createGuaranteeXML(IMessageCollector inOutMessageCollector, Guarantee inGuarantee, Element inOutRootElement) {
//        Element eResponse = new Element("guarantee");
        Element eResponse = null;
        try {
            Namespace ns = inOutRootElement.getNamespace();
            this.addChildElement(eResponse, inGuarantee.getGnteAppliedToClass().getKey(), APPLIED_TO_CLASS, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteAppliedToPrimaryKey(), APPLIED_TO_PRIMARY_KEY, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteAppliedToNaturalKey(), APPLIED_TO_NATURAL_KEY, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteAppliedToEventId(), EVENT_TYPE_ID, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteAppliedToEventGkey(), EVENT_GKEY, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteGuaranteeId(), GUARANTEE_ID, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteN4UserId(), N4_USERID, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalUserId(), EXTERNAL_USERID, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalContactName(), EXTERNAL_CONTACT_NAME, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalAddress1(), EXTERNAL_ADDRESS1, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalAddress2(), EXTERNAL_ADDRESS2, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalAddress3(), EXTERNAL_ADDRESS3, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalCity(), EXTERNAL_CITY, ns);
            if (inGuarantee.getGnteExternalState() != null) {
                this.addChildElement(eResponse, inGuarantee.getGnteExternalState().getId(), EXTERNAL_STATE, ns);
            }
            if (inGuarantee.getGnteExternalCountry() != null) {
                this.addChildElement(eResponse, inGuarantee.getGnteExternalCountry().getId(), EXTERNAL_COUNTRY, ns);
            }
            this.addChildElement(eResponse, inGuarantee.getGnteExternalTelephone(), EXTERNAL_TELEPHONE, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalFax(), EXTERNAL_FAX, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteExternalEmailAddress(), EXTERNAL_EMAIL_ADDRESS, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteGuaranteeType().getName(), GUARANTEE_TYPE, ns);
            this.addChildElement(eResponse, this.convertDateInStringFromat(inGuarantee.getGnteGuaranteeStartDay()), GUARANTEE_START_DAY, ns);
            this.addChildElement(eResponse, this.convertDateInStringFromat(inGuarantee.getGnteGuaranteeEndDay()), GUARANTEE_END_DAY, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteQuantity(), QUANTITY, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteGuaranteeAmount(), GUARANTEE_AMOUNT, ns);
            this.addChildElement(eResponse, this.convertDateInStringFromat(inGuarantee.getGnteVoidedOrExpiredDate()), VOIDED_OR_EXPIRED_DATE, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteNotes(), NOTES, ns);
            this.addChildElement(eResponse, inGuarantee.getGntePaymentType().getKey(), PAYMENT_TYPE, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteGuaranteeCustomer().getBzuId(), GUARANTEE_CUSTOMER, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteAuthorizationNbr(), AUTHORIZATION_NBR, ns);
            this.addChildElement(eResponse, this.convertDateInStringFromat(inGuarantee.getGnteAuthorizationExpiration()), AUTHORIZATION_EXPIRATION, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteTransactionReference(), TRANSACTION_REFERENCE, ns);
            this.addChildElement(eResponse, inGuarantee.getGnteCustomerReferenceId(), CUSTOMER_REFERENCE_ID, ns);
            if (inGuarantee.getGnteGuaranteeCustomer() != null) {
                this.addChildElement(eResponse, inGuarantee.getGnteGuaranteeCustomer().getBzuRole().getId(), GUARANTEE_CUSTOMER_BIZROLE, ns);
            }
        }
        catch (Exception e) {
            inOutMessageCollector.registerExceptions((Throwable)e);
        }
//        inOutRootElement.addContent((Content)eResponse);
    }

    public Class getArchiveClass() {
        //return ArchiveGuarantee.class;
        return null;
    }

    public boolean doArchive() {
        return ArgoConfig.ARCHIVE_GUARANTEES_PRIOR_TO_PURGE.isOn(ContextHelper.getThreadUserContext());
    }

    public static List getGuaranteeListForCUEs(List inAppliedToPrimaryKeyList, BillingExtractEntityEnum inExtractEntityEnum) {
        if (inAppliedToPrimaryKeyList == null || inAppliedToPrimaryKeyList.isEmpty() || inAppliedToPrimaryKeyList.toArray().length == 0) {
            return Collections.emptyList();
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Collection)inAppliedToPrimaryKeyList)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inExtractEntityEnum)));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static List getGuaranteeListForCUEs(Serializable[] inCueGkeys, BillingExtractEntityEnum inExtractEntityEnum) {
        if (inCueGkeys.length == 0) {
            return Collections.emptyList();
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object[])inCueGkeys)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inExtractEntityEnum)));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    @Nullable
    public static Guarantee findGuaranteeForCueAndGuaranteeId(Long inAppliedToPrimaryKey, BillingExtractEntityEnum inExtractEntityEnum, String inGuaranteeId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inExtractEntityEnum))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_ID, (Object)inGuaranteeId));
        dq.setDqMaxResults(1);
        Serializable[] gnteGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        return gnteGkeys == null || gnteGkeys.length == 0 ? null : Guarantee.hydrate(gnteGkeys[0]);
    }

    public static List<Guarantee> findGuaranteesForCue(ChargeableUnitEvent inCue) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inCue.getBexuGkey()));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static Guarantee findLastGuaranteeForCue(ChargeableUnitEvent inCue, Guarantee inFreeWaiver) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inCue.getBexuGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.GNTE_GKEY, (Object)inFreeWaiver.getGnteGkey())).addDqPredicate(PredicateFactory.isNotNull((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_END_DAY)).addDqOrdering(Ordering.desc((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_END_DAY)).setDqMaxResults(1);
        Serializable[] gnteGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        return gnteGkeys == null || gnteGkeys.length == 0 ? null : Guarantee.hydrate(gnteGkeys[0]);
    }

    public static boolean isGuaranteeExistsOnSameDayAsAsWavierStartDate(Guarantee inWavier, ChargeableUnitEvent inCue) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inCue.getBexuGkey())).addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.GNTE_GKEY, (Object)inWavier.getGnteGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_START_DAY, (Object)inWavier.getGnteGuaranteeStartDay()));
        return Roastery.getHibernateApi().existsByDomainQuery(dq);
    }

    public static Guarantee hydrate(Serializable inPrimaryKey) {
        return (Guarantee) HibernateApi.getInstance().load(Guarantee.class, inPrimaryKey);
    }

    @Nullable
    public static Guarantee findGuaranteeByGuaranteeId(String inGuaranteeId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_ID, (Object)inGuaranteeId));
        dq.setDqMaxResults(1);
        Serializable[] gnteGkeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        return gnteGkeys == null || gnteGkeys.length == 0 ? null : Guarantee.hydrate(gnteGkeys[0]);
    }

    @Nullable
    public static Double getSumOfGuaranteeAmountForCUEs(List inCueList, BillingExtractEntityEnum inExtractEntityEnum, ScopedBizUnit inScopedBizUnit, Serializable inGnteGkey) {
        IQueryResult result;
        Double gnteAmountSum = null;
        assert (inCueList != null);
        ArrayList<Long> extractGkeys = new ArrayList<Long>();
        for (Object cueObject : inCueList) {
            ChargeableUnitEvent cue = (ChargeableUnitEvent)cueObject;
            if (cue == null) continue;
            extractGkeys.add(cue.getBexuGkey());
        }
        if (extractGkeys.isEmpty()) {
            return null;
        }
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqAggregateField(AggregateFunctionType.SUM, IArgoExtractField.GNTE_GUARANTEE_AMOUNT).addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object[])extractGkeys.toArray())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inExtractEntityEnum))).addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object[])new GuaranteeTypeEnum[]{GuaranteeTypeEnum.OAC, GuaranteeTypeEnum.PRE_PAY})).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_CUSTOMER, (Object)inScopedBizUnit.getBzuGkey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        if (inGnteGkey != null) {
            dq.addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.GNTE_GKEY, (Object)inGnteGkey));
        }
        if ((result = Roastery.getHibernateApi().findValuesByDomainQuery(dq)).getTotalResultCount() > 0) {
            gnteAmountSum = (Double)result.getValue(0, 0);
        }
        return gnteAmountSum == null ? 0.0 : gnteAmountSum;
    }

    private void addChildElement(Element inEResponse, Object inData, String inNodeName, Namespace inNs) {
//        Element e = new Element(inNodeName, inNs);
//        Text textContent = new Text(inData != null ? inData.toString() : "");
//        e.addContent((Content)textContent);
//        inEResponse.addContent((Content)e);
    }

    @Nullable
    private String convertDateInStringFromat(Date inDate) {
        String datestring = null;
        if (inDate != null) {
            datestring = XML_DATE_TIME_ZONE_SIMPLE_DATE_FORMAT.format(inDate);
        }
        return datestring;
    }

    public static List getCreditPreAuthorizedGurantees(BillingExtractEntityEnum inAppliedToClass, boolean inVoidedExpired) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedToClass))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.CREDIT_PREAUTHORIZE)));
        Conjunction match = PredicateFactory.conjunction();
        if (inVoidedExpired) {
            match.add(PredicateFactory.isNotNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        } else {
            match.add(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        }
        dq.addDqPredicate((IPredicate)match);
        IDomainQuery subQuery = null;
        if (inAppliedToClass.equals((Object) BillingExtractEntityEnum.INV)) {
            subQuery = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object[])new Object[]{GUARANTEED, QUEUED}));
        } else if (inAppliedToClass.equals((Object) BillingExtractEntityEnum.MARINE)) {
            subQuery = QueryUtils.createDomainQuery((String)"ChargeableMarineEvent").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXM_STATUS, (Object[])new Object[]{GUARANTEED, QUEUED}));
        }
        if (subQuery != null) {
            dq.addDqPredicate(PredicateFactory.subQueryIn(subQuery, (IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY));
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static List getListOfExpiredFixedWaiverAndNotGuaranteed(Date inVoidedOrExpiredDate, BillingExtractEntityEnum inAppliedToClass) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedToClass))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.WAIVER))).addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object)((Object) GuaranteeOverrideTypeEnum.FREE_NOCHARGE)));
        Conjunction notNullAndEqualMatch = PredicateFactory.conjunction();
        notNullAndEqualMatch.add(PredicateFactory.le((IMetafieldId) IArgoExtractField.GNTE_WAIVER_EXPIRATION_DATE, (Object)inVoidedOrExpiredDate));
        notNullAndEqualMatch.add(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        dq.addDqPredicate((IPredicate)notNullAndEqualMatch);
        IDomainQuery subQuery = null;
        if (inAppliedToClass.equals((Object) BillingExtractEntityEnum.INV)) {
            subQuery = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXU_STATUS, (Object[])new Object[]{GUARANTEED, QUEUED}));
        } else if (inAppliedToClass.equals((Object) BillingExtractEntityEnum.MARINE)) {
            subQuery = QueryUtils.createDomainQuery((String)"ChargeableMarineEvent").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.BEXM_STATUS, (Object[])new Object[]{GUARANTEED, QUEUED}));
        }
        if (subQuery != null) {
            dq.addDqPredicate(PredicateFactory.subQueryIn(subQuery, (IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY));
        }
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static List getListOfGuarantees(BillingExtractEntityEnum inAppliedToClass, Serializable inAppliedToPrimaryKey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedToClass))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static Set<String> getListOfGuaranteesOnly(BillingExtractEntityEnum inAppliedToClass, Serializable inAppliedToPrimaryKey) {
        return Guarantee.getListOfGuaranteesOnly(inAppliedToClass, inAppliedToPrimaryKey, null);
    }

    public static Set<String> getListOfGuaranteesOnly(BillingExtractEntityEnum inAppliedToClass, Serializable inAppliedToPrimaryKey, Guarantee inWavier) {
        HashSet<String> gnteIds = new HashSet<String>();
        ArrayList<GuaranteeTypeEnum> guaranteeList = new ArrayList<GuaranteeTypeEnum>();
        guaranteeList.add(GuaranteeTypeEnum.WAIVER);
        guaranteeList.add(GuaranteeTypeEnum.PAID);
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqField(IArgoExtractField.GNTE_GUARANTEE_ID).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedToClass))).addDqPredicate(PredicateFactory.not((IPredicate) PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, guaranteeList))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        if (inWavier != null) {
            dq.addDqPredicate(PredicateFactory.between((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_START_DAY, (Comparable) ArgoUtils.getLocalTimeStartOfDay(inWavier.getGnteGuaranteeStartDay()), (Comparable)ArgoUtils.getLocalTimeEndOfDay(inWavier.getGnteGuaranteeEndDay())));
        }
        IQueryResult result = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
        for (int i = 0; i < result.getTotalResultCount(); ++i) {
            String gnteGuaranteteId = (String)result.getValue(i, IArgoExtractField.GNTE_GUARANTEE_ID);
            gnteIds.add(gnteGuaranteteId);
        }
        return gnteIds;
    }

    public static Set<String> getListOfWaiversOnly(BillingExtractEntityEnum inAppliedToClass, Serializable inAppliedToPrimaryKey) {
        HashSet<String> waiverIds = new HashSet<String>();
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqField(IArgoExtractField.GNTE_GUARANTEE_ID).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.WAIVER))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToPrimaryKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedToClass))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object)((Object) GuaranteeOverrideTypeEnum.FREE_NOCHARGE))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        IQueryResult result = Roastery.getHibernateApi().findValuesByDomainQuery(dq);
        for (int i = 0; i < result.getTotalResultCount(); ++i) {
            String waiverId = (String)result.getValue(i, IArgoExtractField.GNTE_GUARANTEE_ID);
            waiverIds.add(waiverId);
        }
        return waiverIds;
    }

    public static boolean getListOfGuaranteesForMatchedRelatedGuaranteeGkey(Guarantee inGuarantee) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_RELATED_GUARANTEE, (Object)inGuarantee.getPrimaryKey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        return Roastery.getHibernateApi().existsByDomainQuery(dq);
    }

    public String getGnteAppliedToEventId() {
        String eventId = null;
        Long extractGkey = this.getGnteAppliedToPrimaryKey();
        ChargeableUnitEvent cue = (ChargeableUnitEvent)Roastery.getHibernateApi().get(ChargeableUnitEvent.class, (Serializable)extractGkey);
        if (cue != null) {
            eventId = cue.getBexuEventType();
        }
        return eventId;
    }

    public Long getGnteAppliedToEventGkey() {
        Long eventGkey = null;
        Long extractGkey = this.getGnteAppliedToPrimaryKey();
        ChargeableUnitEvent cue = (ChargeableUnitEvent)Roastery.getHibernateApi().get(ChargeableUnitEvent.class, (Serializable)extractGkey);
        if (cue != null) {
            eventGkey = cue.getBexuSourceGkey();
        }
        return eventGkey;
    }

    public static IPredicate formGnteAppliedToEventIdPredicate(IMetafieldId inMetafieldId, PredicateVerbEnum inVerb, Object inValue) {
        IPredicate bexuEventTypePredicate = PredicateVerbEnum.EQ.equals((Object)inVerb) ? PredicateFactory.eq((IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (Object)inValue) : PredicateFactory.createPredicate((UserContext)ContextHelper.getThreadUserContext(), (IMetafieldId) IArgoExtractField.BEXU_EVENT_TYPE, (PredicateVerbEnum)inVerb, (Object)inValue);
        IDomainQuery cueForEventTypeQuery = QueryUtils.createDomainQuery((String)"ChargeableUnitEvent").addDqField(IArgoExtractField.BEXU_GKEY).addDqPredicate(bexuEventTypePredicate);
        return PredicateFactory.subQueryIn((IDomainQuery)cueForEventTypeQuery, (IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY);
    }

    @Nullable
    public String getGnteExternalCountryName() {
        if (this.getGnteExternalCountry() != null) {
            return this.getGnteExternalCountry().getCntryName();
        }
        return null;
    }

    @Nullable
    public String getGnteExternalStateName() {
        if (this.getGnteExternalState() != null) {
            return this.getGnteExternalState().getStateName();
        }
        return null;
    }

    public boolean isGuaranteeVoided() {
        return this.getGnteVoidedOrExpiredDate() != null;
    }

    public boolean isWavier() {
        return GuaranteeTypeEnum.WAIVER.equals((Object)this.getGnteGuaranteeType());
    }

    public static Guarantee load(Serializable inPrimaryKey) {
        return (Guarantee)Roastery.getHibernateApi().load(Guarantee.class, inPrimaryKey);
    }

    @Nullable
    public static Guarantee findFixedPriceWaiverFor(Long inGnteAppliedToPrimaryKey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.WAIVER))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inGnteAppliedToPrimaryKey)).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object)((Object) GuaranteeOverrideTypeEnum.FIXED_PRICE)));
        return (Guarantee)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static Guarantee getFreeNoChargeWaiverForCUE(Long inAppliedToGkey, BillingExtractEntityEnum inInv) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inInv))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.WAIVER))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object)((Object) GuaranteeOverrideTypeEnum.FREE_NOCHARGE))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        return (Guarantee)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static Guarantee getRelatedGuaranteeForWaiver(Guarantee inWaiver) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.in((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object[])new GuaranteeTypeEnum[]{GuaranteeTypeEnum.OAC, GuaranteeTypeEnum.PRE_PAY, GuaranteeTypeEnum.CREDIT_PREAUTHORIZE})).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inWaiver.getGnteAppliedToPrimaryKey())).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_RELATED_GUARANTEE, (Object)inWaiver.getGnteGkey()));
        return (Guarantee)Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public static List getFreeNoChargeWaiverRecordsForCUE(Long inEventGkey, BillingExtractEntityEnum inAppliedTo) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_GUARANTEE_TYPE, (Object)((Object) GuaranteeTypeEnum.WAIVER))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inEventGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object)inAppliedTo))).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_OVERRIDE_VALUE_TYPE, (Object)((Object) GuaranteeOverrideTypeEnum.FREE_NOCHARGE)));
        return Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
    }

    public static boolean isNonVoidedGuaranteeExistsForCue(Long inAppliedToGkey, Guarantee inGuarantee) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"Guarantee").addDqPredicate(PredicateFactory.ne((IMetafieldId) IArgoExtractField.GNTE_GKEY, (Object)inGuarantee.getGnteGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_PRIMARY_KEY, (Object)inAppliedToGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoExtractField.GNTE_APPLIED_TO_CLASS, (Object)((Object) BillingExtractEntityEnum.INV))).addDqPredicate(PredicateFactory.isNull((IMetafieldId) IArgoExtractField.GNTE_VOIDED_OR_EXPIRED_DATE));
        return Roastery.getHibernateApi().existsByDomainQuery(dq);
    }

    public String getGnteActionForGroovyValidation() {
        return this._gnteActionForGroovyValidation;
    }

    public void setGnteActionForGroovyValidation(String inGnteActionForGroovyValidation) {
        this._gnteActionForGroovyValidation = inGnteActionForGroovyValidation;
    }

    public String toString() {
        StringBuilder gnte = new StringBuilder("Guarantee[");
        if (this.getGnteGkey() != null) {
            gnte.append(" Guarantee Gkey:").append(this.getGnteGkey().toString());
        }
        gnte.append(" Guarantee Id:").append(this.getGnteGuaranteeId());
        if (this.getGnteGuaranteeType() != null) {
            gnte.append(" Type:").append(this.getGnteGuaranteeType().getKey());
        }
        gnte.append(" EventType Id:").append(this.getGnteAppliedToEventId()).append(" Natural Key:").append(this.getGnteAppliedToNaturalKey());
        if (this.getGnteGuaranteeCustomer() != null) {
            gnte.append(" Customer Id:").append(this.getGnteGuaranteeCustomer().getBzuId());
        }
        gnte.append(" Start Day:" + this.getGnteGuaranteeStartDay());
        gnte.append(" End Day:" + this.getGnteGuaranteeEndDay());
        if (this.getGnteWaiverExpirationDate() != null) {
            gnte.append(" Expiration Time:" + this.getGnteWaiverExpirationDate());
        }
        gnte.append("]");
        return gnte.toString();
    }
}
