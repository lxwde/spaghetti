package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EdiMessageClassEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PostingActionEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.PropertyGroupEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.configs.AbstractConfig;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.exceptions.BizWarning;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.utils.MessageTranslatorUtils;
import com.zpmc.ztos.infra.base.common.utils.TranslationUtils;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class EdiPostingContext {
    private static Map PARENTCHILDPOSTINGRULEMAP = null;
    private static List<String> UNIT_NON_ROUTING_GROUP_FIELDS = null;
    private boolean _isFirstTransaction;
    private Double _packageWeight;
    private TimeZone _timeZone;
    private EdiMessageClassEnum _ediMessageClass;
    private DataSourceEnum _incomingDataSource;
    private DataSourceEnum _existingDataSource;
    private Serializable _tradingPartnerGkey;
    private AtomizedEnum _ufvTransitState;
    private AtomizedEnum _unitVisitState;
    private AtomizedEnum _unitCategory;
    private AtomizedEnum _unitFreightKind;
    private AtomizedEnum _unitGrossWeightSource;
    private boolean _isPostingToNewlyCreatedUfv;
    private boolean _isPostingToNewlyCreatedUnit;
    private String _xmlTranStr;
    private UserContext _userContext;
    private Map _interceptorParmMaps;
    private DataSourceEnum _dataSourceEnum;
    private int _tranNbr;
    private int _tranSize;
    private Serializable _sessionGkey;
    private List _warnings;
    private List _violations = new ArrayList();
    private Serializable _bizContextGkey;
    private IConfigValueProvider _configValues;
    private Map _postingRules;
    private Facility _facility;
    private Date _activityDate;
    private FieldChanges _postedChanges = new FieldChanges();
    private FieldChanges _ignoredChanges = new FieldChanges();
    private FieldChanges _newEntityChanges = new FieldChanges();
    private FieldChanges _tracedChanges = new FieldChanges();
    public static final String POSTING_PEA_PREFIX = "ediPoster";
    private boolean _ignoreChanges = false;
    private static IMetafieldId EDISESS_TRADING_PARTNER = null;
    private static IMetafieldId UNIT_GROSS_WEIGHT_SOURCE = null;
    private static Logger LOGGER = null;
    private List<String> _unitEntityFields = new ArrayList<String>();
    private List<String> _ufvEntityFields = new ArrayList<String>();
    private final String _get = "get";
    private final String _set = "set";
    private final String _hbr = "Hbr";
    private static final String GOODS_BASE = "GoodsBase";
    private static final String GDS_UNIT = "gdsUnit";

    public EdiPostingContext() {
        this._ufvEntityFields.add("ufvArrivePosition");
        this._ufvEntityFields.add("ufvIntendedObCv");
        this._unitEntityFields.add("HAZARDS");
        this._unitEntityFields.add("LINEOP");
        this._unitEntityFields.add("COMMODITY");
        this._unitEntityFields.add("AGENTS");
        this._unitEntityFields.add("FREIGHT_KIND");
        this._unitEntityFields.add("unitLineOperator");
        this._unitEntityFields.add("unitRouting");
        this._unitEntityFields.add("OOG");
        this._unitEntityFields.add("SEALS");
        this._unitEntityFields.add("WEIGHT");
        this._unitEntityFields.add("REEFER");
        this._unitEntityFields.add("eqsEqOperator");
        this._unitEntityFields.add("eqEquipType");
        this._ediMessageClass = EdiMessageClassEnum.UNKNOWN;
        this._incomingDataSource = DataSourceEnum.UNKNOWN;
        this._tradingPartnerGkey = null;
        this._warnings = new ArrayList();
        this._bizContextGkey = null;
        this._postingRules = new HashMap();
        this._timeZone = ContextHelper.getThreadUserTimezone();
        this._facility = ContextHelper.getThreadFacility();
    }

    public EdiPostingContext(EdiMessageClassEnum inEdiMessageClass, DataSourceEnum inIncomingDataSource, Serializable inTradingPartnerGkey, List inWarnings, Serializable inBizContextGkey, Map inPostingRules) {
        this._ufvEntityFields.add("ufvArrivePosition");
        this._ufvEntityFields.add("ufvIntendedObCv");
        this._unitEntityFields.add("HAZARDS");
        this._unitEntityFields.add("LINEOP");
        this._unitEntityFields.add("COMMODITY");
        this._unitEntityFields.add("AGENTS");
        this._unitEntityFields.add("FREIGHT_KIND");
        this._unitEntityFields.add("unitLineOperator");
        this._unitEntityFields.add("unitRouting");
        this._unitEntityFields.add("OOG");
        this._unitEntityFields.add("SEALS");
        this._unitEntityFields.add("WEIGHT");
        this._unitEntityFields.add("REEFER");
        this._unitEntityFields.add("eqsEqOperator");
        this._unitEntityFields.add("eqEquipType");
        this._ediMessageClass = inEdiMessageClass;
        this._incomingDataSource = inIncomingDataSource;
        this._tradingPartnerGkey = inTradingPartnerGkey;
        this._warnings = inWarnings;
        this._bizContextGkey = inBizContextGkey;
        this._postingRules = inPostingRules;
        this._timeZone = ContextHelper.getThreadUserTimezone();
        this._facility = ContextHelper.getThreadFacility();
    }

    public EdiPostingContext(EdiMessageClassEnum inEdiMessageClass, DataSourceEnum inIncomingDataSource, Serializable inTradingPartnerGkey, List inWarnings, Serializable inBizContextGkey, int inTranNbr, int inTranSize, Map inInterceptorParmMaps, String inXmlTranStr, UserContext inUc, DataSourceEnum inDataSourceEnum, Serializable inSessionGkey) {
        this._ufvEntityFields.add("ufvArrivePosition");
        this._ufvEntityFields.add("ufvIntendedObCv");
        this._unitEntityFields.add("HAZARDS");
        this._unitEntityFields.add("LINEOP");
        this._unitEntityFields.add("COMMODITY");
        this._unitEntityFields.add("AGENTS");
        this._unitEntityFields.add("FREIGHT_KIND");
        this._unitEntityFields.add("unitLineOperator");
        this._unitEntityFields.add("unitRouting");
        this._unitEntityFields.add("OOG");
        this._unitEntityFields.add("SEALS");
        this._unitEntityFields.add("WEIGHT");
        this._unitEntityFields.add("REEFER");
        this._unitEntityFields.add("eqsEqOperator");
        this._unitEntityFields.add("eqEquipType");
        this._ediMessageClass = inEdiMessageClass;
        this._incomingDataSource = inIncomingDataSource;
        this._tradingPartnerGkey = inTradingPartnerGkey;
        this._warnings = inWarnings;
        this._bizContextGkey = inBizContextGkey;
        this._tranNbr = inTranNbr;
        this._tranSize = inTranSize;
        this._interceptorParmMaps = inInterceptorParmMaps;
        this._xmlTranStr = inXmlTranStr;
        this._userContext = inUc;
        this._dataSourceEnum = inDataSourceEnum;
        this._sessionGkey = inSessionGkey;
    }

    public Object getFieldValue(IMetafieldId inMetafieldId) {
        if (IArgoBizMetafield.EDI_POSTING_FACT_EXISTING_DATA_SOURCE.equals((Object)inMetafieldId)) {
            return this._existingDataSource;
        }
        if (IArgoBizMetafield.EDI_POSTING_FACT_UFV_TRANSIT_STATE.equals((Object)inMetafieldId)) {
            return this._ufvTransitState;
        }
        if (IArgoBizMetafield.EDI_POSTING_FACT_UNIT_CATEGORY.equals((Object)inMetafieldId)) {
            return this._unitCategory;
        }
        if (IArgoBizMetafield.EDI_POSTING_FACT_UNIT_FREIGHT_KIND.equals((Object)inMetafieldId)) {
            return this._unitFreightKind;
        }
        if (EDISESS_TRADING_PARTNER.equals((Object)inMetafieldId)) {
            return this._tradingPartnerGkey;
        }
        if (IArgoBizMetafield.EDI_POSTING_FACT_UNIT_VISIT_STATE.equals((Object)inMetafieldId)) {
            return this._unitVisitState;
        }
        if (UNIT_GROSS_WEIGHT_SOURCE.equals((Object)inMetafieldId)) {
            return this._unitGrossWeightSource;
        }
        throw BizFailure.create((String)("request for unknown property " + (Object)inMetafieldId));
    }

    public void setUnitGrossWeightSource(AtomizedEnum inUnitGrossWeightSource) {
        this._unitGrossWeightSource = inUnitGrossWeightSource;
    }

    public void setEdiMessageClass(EdiMessageClassEnum inEdiMessageClass) {
        this._ediMessageClass = inEdiMessageClass;
    }

    public void setIncomingDataSource(DataSourceEnum inIncomingDataSource) {
        this._incomingDataSource = inIncomingDataSource;
    }

    public void setTradingPartnerGkey(Serializable inTradingPartnerGkey) {
        this._tradingPartnerGkey = inTradingPartnerGkey;
    }

    public void setUfvTransitState(AtomizedEnum inUfvTransitState) {
        this._ufvTransitState = inUfvTransitState;
    }

    public void setUnitCategory(AtomizedEnum inUnitCategory) {
        this._unitCategory = inUnitCategory;
    }

    public void setUnitVisitState(AtomizedEnum inUnitVisitState) {
        this._unitVisitState = inUnitVisitState;
    }

    public void setUnitFreightKind(AtomizedEnum inUnitFreightKind) {
        this._unitFreightKind = inUnitFreightKind;
    }

    public void setWarnings(List inWarnings) {
        this._warnings = inWarnings;
    }

    public void setBizContextGkey(Serializable inBizContextGkey) {
        this._bizContextGkey = inBizContextGkey;
    }

    public void setConfigValues(IConfigValueProvider inConfigValues) {
        this._configValues = inConfigValues;
    }

    public void setPostingRules(Map inPostingRules) {
        this._postingRules = inPostingRules;
    }

    public EdiMessageClassEnum getEdiMessageClass() {
        return this._ediMessageClass;
    }

    public DataSourceEnum getIncomingDataSource() {
        return this._incomingDataSource;
    }

    public List getWarnings() {
        return this._warnings;
    }

    public Serializable getBizContextGkey() {
        return this._bizContextGkey;
    }

    public IConfigValueProvider getConfigValues() {
        return this._configValues;
    }

    @Nullable
    public String getConfigValue(AbstractConfig inConfig) {
        if (this._configValues == null) {
            return null;
        }
        return this._configValues.getConfigValue((IConfig)inConfig);
    }

    public void addWarning(BizViolation inBv) {
        if (this._warnings != null) {
            this._warnings.add(inBv);
        }
    }

    public void addAllWarnings(List inMessages) {
        if (this._warnings != null) {
            this._warnings.addAll(inMessages);
        }
    }

    public void addViolation(BizViolation inBv) {
        if (inBv != null) {
            this._violations.add(inBv);
        }
    }

    public void addViolation(IPropertyKey inPropertyKey, Object inParm1) {
        BizViolation bv = BizViolation.create((IPropertyKey)inPropertyKey, null, (Object)inParm1);
        this._violations.add(bv);
    }

    public void addViolation(IPropertyKey inPropertyKey, Object inParm1, Object inParm2) {
        BizViolation bv = BizViolation.create((IPropertyKey)inPropertyKey, null, (Object)inParm1, (Object)inParm2);
        this._violations.add(bv);
    }

    public void addViolation(IPropertyKey inPropertyKey, Object inParm1, Object inParm2, Object inParm3) {
        BizViolation bv = BizViolation.create((IPropertyKey)inPropertyKey, null, (Object)inParm1, (Object)inParm2, (Object)inParm3);
        this._violations.add(bv);
    }

    public void addWarning(IPropertyKey inPropertyKey, Object inParm1) {
        BizViolation bv = BizWarning.create((IPropertyKey)inPropertyKey, null, (Object)inParm1);
        this._warnings.add(bv);
    }

    public void addWarning(IPropertyKey inPropertyKey, Object inParm1, Object inParm2) {
        BizViolation bv = BizWarning.create((IPropertyKey)inPropertyKey, null, (Object)inParm1, (Object)inParm2);
        this._warnings.add(bv);
    }

    public void addWarning(IPropertyKey inPropertyKey, Object inParm1, Object inParm2, Object inParm3) {
        BizViolation bv = BizWarning.create((IPropertyKey)inPropertyKey, null, (Object)inParm1, (Object)inParm2, (Object)inParm3);
        this._warnings.add(bv);
    }

    public void addViolationAsWarning(BizViolation inBv) {
        if (inBv != null) {
            this._warnings.add(inBv);
        }
    }

    public void clearWarningsAndViolations() {
        this._violations = new ArrayList();
        this._warnings = new ArrayList();
    }

    public Facility getFacility() {
        return this._facility;
    }

    public void setFacility(Facility inFacility) {
        this._facility = inFacility;
    }

    public Date getActivityDate() {
        return this._activityDate;
    }

    public void setActivityDate(Date inDate) {
        this._activityDate = inDate;
    }

    private PostingActionEnum getPostingActionForPropertyGroup(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup) {
        PostingActionEnum postingAction = null;
        IMapper rule = (IMapper)this._postingRules.get((Object)inPropertyGroup);
        if (rule != null) {
            FieldValue[] fieldValues;
//            if (PropertyGroupEnum.WEIGHT.getKey().equals(inPropertyGroup.getKey())) {
//                inPropertyGroup = PropertyGroupEnum.GROSS_WEIGHT;
//            }
//            this._existingDataSource = PropertySource.findDataSourceForProperty(inEntity, inPropertyGroup);
//            if (DataSourceEnum.UNKNOWN.getName().equals(this._existingDataSource.getName())) {
//                this._existingDataSource = PropertySource.findDataSourceForProperty(inEntity, (PropertyGroupEnum)((Object)PARENTCHILDPOSTINGRULEMAP.get((Object)inPropertyGroup)));
//            }
//            if (rule instanceof EntityMappingPredicate) {
//                EntityMappingPredicate mappingPredicate = (EntityMappingPredicate)rule;
//                rule = (EntityMappingPredicate)HibernateApi.getInstance().get(EntityMappingPredicate.class, (Serializable)mappingPredicate.getEmappGkey());
//            }
            if (rule != null && (fieldValues = rule.mapEntity((IValueSource)this)) != null && fieldValues.length == 1) {
                postingAction = (PostingActionEnum)((Object)fieldValues[0].getValue());
            }
        }
        return postingAction;
    }

    private PostingActionEnum getPostingActionForProperty(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup) {
        PostingActionEnum postingActionForPropertyGroup = this.getPostingActionForPropertyGroup(inEntity, inPropertyGroup);
        return postingActionForPropertyGroup == null ? PostingActionEnum.APPLY : postingActionForPropertyGroup;
    }

    public void setPostingToNewlyCreatedUfv(boolean inPostingToNewlyCreatedUfv) {
        this._isPostingToNewlyCreatedUfv = inPostingToNewlyCreatedUfv;
    }

    public void setPostingToNewlyCreatedUnit(boolean inPostingToNewlyCreatedUnit) {
        this._isPostingToNewlyCreatedUnit = inPostingToNewlyCreatedUnit;
    }

    public void updateField(DatabaseEntity inEntity, IMetafieldId inFieldId, Object inFieldValue) {
        if (inFieldValue != null) {
            this.updateField(inEntity, inFieldId, inFieldValue, true, null);
        }
    }

    public void updateField(DatabaseEntity inEntity, IMetafieldId inFieldId, Object inFieldValue, boolean inEvaluatePostingLogic, ICallBack inModelUpdatingCallBack) {
        Object existingValue = inEntity.getField(inFieldId);
        if (this._isPostingToNewlyCreatedUnit || this._ufvEntityFields.contains(inFieldId.getFieldId()) && this._isPostingToNewlyCreatedUfv) {
            EdiPostingContext.updateTheField(inEntity, inFieldId, inFieldValue, inModelUpdatingCallBack);
            this.registerNewEntityFieldChange(inFieldId, null, inFieldValue);
        }
        else
            {
//            N4EntityScoper scoper = (N4EntityScoper)((Object)Roastery.getBean((String)"entityScoper"));
//            PropertyGroupEnum propertyGroup = scoper.getPropertyGroupMapping(inFieldId);
//            if (propertyGroup == null) {
//                if (!ObjectUtils.equals((Object)existingValue, (Object)inFieldValue)) {
//                    this.registerPostedFieldChange(inFieldId, existingValue, inFieldValue);
//                }
//                this.registerTracedFieldChange(inFieldId, existingValue, inFieldValue);
//                EdiPostingContext.updateTheField(inEntity, inFieldId, inFieldValue, inModelUpdatingCallBack);
//            } else {
//                PostingActionEnum postingAction = null;
//                if (!PARENTCHILDPOSTINGRULEMAP.containsKey((Object)propertyGroup)) {
//                    postingAction = inEvaluatePostingLogic ? this.getPostingActionForProperty(inEntity, propertyGroup) : PostingActionEnum.APPLY;
//                    LOGGER.info((Object)("updateProperty: for property: " + (Object)inFieldId + " the posting action is: " + postingAction.getKey()));
//                } else {
//                    PostingActionEnum postingActionEnum = postingAction = inEvaluatePostingLogic ? this.getPostingActionForPropertyGroup(inEntity, propertyGroup) : PostingActionEnum.APPLY;
//                    if (postingAction != null) {
//                        LOGGER.info((Object)("updateProperty: for property: " + (Object)inFieldId + " the posting action is: " + postingAction.getKey()));
//                    }
//                }
//                if (inEvaluatePostingLogic && inFieldValue instanceof HibernatingComponent) {
//                    Field[] hbrFields;
//                    Object actualFieldValue = null;
//                    try {
//                        actualFieldValue = inFieldValue.getClass().newInstance();
//                    }
//                    catch (Exception e) {
//                        String exception = CarinaUtils.getStackTrace((Throwable)e);
//                        LOGGER.error((Object)("Instantiation of hibernating component " + inFieldValue.toString() + " failed: " + exception));
//                    }
//                    this.copyFields(inFieldValue, actualFieldValue);
//                    Class<?> hbrComponentClass = inFieldValue.getClass();
//                    for (Field hbrField : hbrFields = this.getHbrClass(hbrComponentClass).getDeclaredFields()) {
//                        IMetafieldId hbrMetafield = MetafieldIdFactory.valueOf((String)hbrField.getName());
//                        PropertyGroupEnum childSubProperty = scoper.getPropertyGroupMapping(hbrMetafield);
//                        if (childSubProperty != null) {
//                            PostingActionEnum postingActionOfSubProperty;
//                            PostingActionEnum postingActionEnum = postingActionOfSubProperty = inEvaluatePostingLogic ? this.getPostingActionForPropertyGroup(inEntity, childSubProperty) : PostingActionEnum.APPLY;
//                            if (postingActionOfSubProperty != null) {
//                                LOGGER.info((Object)("updateProperty: for property: " + (Object)hbrMetafield + " the posting action is: " + postingActionOfSubProperty.getKey()));
//                            }
//                            if (!PostingActionEnum.IGNORE.equals((Object)postingActionOfSubProperty) && (!PostingActionEnum.IGNORE.equals((Object)postingAction) || postingActionOfSubProperty != null)) continue;
//                            this.ignoreHbrComponentFields(hbrComponentClass, hbrField, inFieldId, existingValue, inFieldValue);
//                            continue;
//                        }
//                        if (!PostingActionEnum.IGNORE.equals((Object)postingAction)) continue;
//                        this.ignoreHbrComponentFields(hbrComponentClass, hbrField, inFieldId, existingValue, inFieldValue);
//                    }
//                    if (!ObjectUtils.equals(actualFieldValue, (Object)inFieldValue)) {
//                        this.registerIgnoredFieldChange(inFieldId, inFieldValue, actualFieldValue);
//                    }
//                } else {
//                    PropertyGroupEnum parentProperty = (PropertyGroupEnum)((Object)PARENTCHILDPOSTINGRULEMAP.get((Object)propertyGroup));
//                    if (inEvaluatePostingLogic && parentProperty != null) {
//                        PostingActionEnum parentPostingAction;
//                        PostingActionEnum postingActionEnum = parentPostingAction = inEvaluatePostingLogic ? this.getPostingActionForPropertyGroup(inEntity, parentProperty) : null;
//                        if (parentPostingAction == null) {
//                            if (UNIT_NON_ROUTING_GROUP_FIELDS.contains(inFieldId.getFieldId()) && inEntity.getEntityName().equals(GOODS_BASE)) {
//                                DatabaseEntity unit = (DatabaseEntity)inEntity.getField(MetafieldIdFactory.valueOf((String)GDS_UNIT));
//                                parentPostingAction = this.getPostingActionForProperty(unit, parentProperty);
//                            }
//                            if (parentPostingAction == null) {
//                                parentPostingAction = PostingActionEnum.APPLY;
//                            }
//                        }
//                        LOGGER.info((Object)("updateProperty: for property: " + (Object)inFieldId + " the posting action is: " + parentPostingAction.getKey()));
//                        if (postingAction == null && PostingActionEnum.APPLY.equals((Object)parentPostingAction)) {
//                            postingAction = parentPostingAction;
//                        }
//                        if (PostingActionEnum.IGNORE.equals((Object)postingAction) || PostingActionEnum.IGNORE.equals((Object)parentPostingAction) && postingAction == null) {
//                            this.registerIgnoredFieldChange(inFieldId, existingValue, inFieldValue);
//                            inFieldValue = existingValue;
//                        }
//                    }
//                }
//                if (ObjectUtils.equals((Object)existingValue, (Object)inFieldValue)) {
//                    this.registerTracedFieldChange(inFieldId, existingValue, inFieldValue);
//                    if (PostingActionEnum.APPLY.equals((Object)postingAction)) {
//                        PropertySource.updateDataSource(inEntity, propertyGroup, this._incomingDataSource, ContextHelper.getThreadUserId());
//                    }
//                }
//                else if (inFieldValue instanceof HibernatingComponent || PostingActionEnum.APPLY.equals((Object)postingAction)) {
//                    this.registerPostedFieldChange(inFieldId, existingValue, inFieldValue);
//                    this.registerTracedFieldChange(inFieldId, existingValue, inFieldValue);
//                    EdiPostingContext.updateTheField(inEntity, inFieldId, inFieldValue, inModelUpdatingCallBack);
//                    PropertySource.updateDataSource(inEntity, propertyGroup, this._incomingDataSource, ContextHelper.getThreadUserId());
//                }
//                else {
//                    this.registerIgnoredFieldChange(inFieldId, existingValue, inFieldValue);
//                }
            //}
        }

    }

    private void copyFields(Object inFromObject, Object inOutToObject) {
        Class<?> fromClass = inFromObject.getClass();
        Class<?> toClass = inOutToObject.getClass();
        Field[] hbrClassFields = this.getHbrClass(toClass).getDeclaredFields();
        if (!this.getHbrClass(fromClass).isInstance(inFromObject)) {
            List<Field> listOfFields = EdiPostingContext.getFieldsUpTo(inFromObject.getClass(), null);
            block4: for (Field hbrClassField : hbrClassFields) {
                for (Field toField : listOfFields) {
                    try {
                        Field fromField = this.getHbrClass(fromClass).getDeclaredField(hbrClassField.getName());
                        if (!this.isFieldNameEqual(fromField, toField)) continue;
                        String getterMethodName = "get" + EdiPostingContext.capitalizeFirstLetter(fromField.getName());
                        Method getterMethod = toClass.getMethod(getterMethodName, new Class[0]);
                        Object obj = getterMethod.invoke(inFromObject, new Object[0]);
                        String setterMethodName = "set" + EdiPostingContext.capitalizeFirstLetter(toField.getName());
                        Method setterMethod = toClass.getMethod(setterMethodName, toField.getType());
                        setterMethod.invoke(inOutToObject, obj);
                        listOfFields.remove(toField);
                        continue block4;
                    }
                    catch (Exception e) {
                        String exception = CarinaUtils.getStackTrace((Throwable)e);
                        LOGGER.error((Object)("Copying fields from " + inFromObject + " to " + inOutToObject + " failed: " + exception));
                    }
                }
            }
        } else {
            for (Field toField : hbrClassFields) {
                try {
                    Field fromField = this.getHbrClass(fromClass).getDeclaredField(toField.getName());
                    if (!fromField.getType().equals(toField.getType())) continue;
                    String getterMethodName = "get" + EdiPostingContext.capitalizeFirstLetter(fromField.getName());
                    Method getterMethod = toClass.getMethod(getterMethodName, new Class[0]);
                    Object obj = getterMethod.invoke(inFromObject, new Object[0]);
                    String setterMethodName = "set" + EdiPostingContext.capitalizeFirstLetter(toField.getName());
                    Method setterMethod = toClass.getMethod(setterMethodName, toField.getType());
                    setterMethod.invoke(inOutToObject, obj);
                }
                catch (Exception e) {
                    String exception = CarinaUtils.getStackTrace((Throwable)e);
                    LOGGER.error((Object)("Copying fields from " + inFromObject + " to " + inOutToObject + " failed: " + exception));
                }
            }
        }
    }

    private boolean isFieldNameEqual(Field inHbrField, Field inClassField) {
        String classFieldName = null;
        String hbrFieldName = inHbrField.getName();
        classFieldName = inClassField.getName().substring(0, 1).matches("[^A-Za-z]") ? inClassField.getName().substring(1) : inClassField.getName();
        return hbrFieldName.equals(classFieldName);
    }

    private Class getHbrClass(Class inToClass) {
        String className = inToClass.getName() + "Hbr";
        try {
            return Class.forName(className);
        }
        catch (Exception e) {
            String exception = CarinaUtils.getStackTrace((Throwable)e);
            LOGGER.error((Object)("The class " + className + " was not found: " + exception));
            return null;
        }
    }

    private static List<Field> getFieldsUpTo(Class<?> inStartClass, @Nullable Class<?> inParent) {
        Field[] declaredFields = inStartClass.getDeclaredFields();
        ArrayList<Field> currentClassFields = new ArrayList<Field>();
        Collections.addAll(currentClassFields, declaredFields);
        Class<?> parentClass = inStartClass.getSuperclass();
        if (!(parentClass == null || inParent != null && parentClass.equals(inParent))) {
            List<Field> parentClassFields = EdiPostingContext.getFieldsUpTo(parentClass, inParent);
            currentClassFields.addAll(parentClassFields);
        }
        return currentClassFields;
    }

    private void ignoreHbrComponentFields(Class inHbrComponentClass, Field inHbrField, IMetafieldId inFieldId, Object inExistingValue, Object inFieldValue) {
        try {
            String getterMethodName = "get" + EdiPostingContext.capitalizeFirstLetter(inHbrField.getName());
            Method getterMethod = inHbrComponentClass.getMethod(getterMethodName, new Class[0]);
            Object obj = getterMethod.invoke(inExistingValue, new Object[0]);
            String setterMethodName = "set" + EdiPostingContext.capitalizeFirstLetter(inHbrField.getName());
            Method setterMethod = inHbrComponentClass.getMethod(setterMethodName, inHbrField.getType());
            setterMethod.invoke(inFieldValue, obj);
        }
        catch (Exception e) {
            String exception = CarinaUtils.getStackTrace((Throwable)e);
            LOGGER.error((Object)("Update of " + (Object)inFieldId + " failed due to: " + exception));
        }
    }

    private void registerNewEntityFieldChange(IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        this._newEntityChanges.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
    }

    private static void updateTheField(DatabaseEntity inEntity, IMetafieldId inFieldId, Object inFieldValue, ICallBack inModelUpdatingCallBack) {
        if (inModelUpdatingCallBack == null) {
            inEntity.setFieldValue(inFieldId, inFieldValue);
        } else {
            inModelUpdatingCallBack.execute();
        }
    }

    public void updatePropertyGroup(DatabaseEntity inEntity, PropertyGroupEnum inPropertyGroup, ICallBack inCallBack) {
        if (this._isPostingToNewlyCreatedUfv && this._isPostingToNewlyCreatedUnit) {
            inCallBack.execute();
        } else {
            PostingActionEnum postingAction = this.getPostingActionForProperty(inEntity, inPropertyGroup);
            LOGGER.info((Object)("updateProperty: for property: " + inPropertyGroup.getKey() + " the posting action is: " + postingAction.getKey()));
            if (PostingActionEnum.APPLY.equals((Object)postingAction)) {
                inCallBack.execute();
                PropertySource.updateDataSource(inEntity, inPropertyGroup, this._incomingDataSource, ContextHelper.getThreadUserId());
            } else {
                this._ignoreChanges = true;
            }
        }
    }

    public boolean isOkToPostPerGeneralRule(DatabaseEntity inEntity) {
        PostingActionEnum postingAction;
        boolean result = true;
        if (!this._isPostingToNewlyCreatedUfv && inEntity != null && PostingActionEnum.IGNORE.equals((Object)(postingAction = this.getPostingActionForProperty(inEntity, PropertyGroupEnum.GENERAL)))) {
            String ediMessageClassName = MessageTranslatorUtils.translateParameter((ITranslationContext) TranslationUtils.getTranslationContext((UserContext) ContextHelper.getThreadUserContext()), (Object)((Object)this._ediMessageClass));
            BizViolation warning = BizWarning.create((IPropertyKey) IArgoPropertyKeys.EDI_GENERAL_RULE_FAILURE, null, (Object)ediMessageClassName, (Object)inEntity.getHumanReadableKey());
            this.addWarning(warning);
            result = false;
        }
        return result;
    }

    public boolean isOkToPostPerGeneralRule(DatabaseEntity inEntity, boolean inIsWarningRequired) {
        PostingActionEnum postingAction;
        boolean isOkayToPost = true;
        if (!this._isPostingToNewlyCreatedUfv && inEntity != null && PostingActionEnum.IGNORE.equals((Object)(postingAction = this.getPostingActionForProperty(inEntity, PropertyGroupEnum.GENERAL)))) {
            if (inIsWarningRequired) {
                String ediMessageClassName = MessageTranslatorUtils.translateParameter((ITranslationContext) TranslationUtils.getTranslationContext((UserContext) ContextHelper.getThreadUserContext()), (Object)((Object)this._ediMessageClass));
                BizViolation warning = BizWarning.create((IPropertyKey) IArgoPropertyKeys.EDI_GENERAL_RULE_FAILURE, null, (Object)ediMessageClassName, (Object)inEntity.getHumanReadableKey());
                this.addWarning(warning);
            }
            isOkayToPost = false;
        }
        return isOkayToPost;
    }

    public boolean isIgnorePropertyGroupChanges() {
        return this._ignoreChanges;
    }

    public boolean isUpdatingExistingUfv() {
        return !this._isPostingToNewlyCreatedUfv;
    }

    public boolean isUpdatingExistingUnit() {
        return !this._isPostingToNewlyCreatedUnit;
    }

    public void registerPostedFieldChange(IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        this._postedChanges.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
    }

    public void registerTracedFieldChange(IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        this._tracedChanges.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
    }

    public void removePostedFieldChange(IMetafieldId inFieldId) {
        this._postedChanges.removeFieldChange(inFieldId);
    }

    public void registerIgnoredFieldChange(IMetafieldId inFieldId, Object inOldValue, Object inNewValue) {
        this._ignoredChanges.setFieldChange(new FieldChange(inFieldId, inOldValue, inNewValue));
    }

    public void throwIfAnyViolations() throws BizViolation {
        if (!this._violations.isEmpty()) {
            BizViolation bv = null;
            for (Object violation : this._violations) {
                BizViolation copyViolation = BizViolation.create((IPropertyKey)((BizViolation)violation).getMessageKey(), (Throwable)((BizViolation)violation).getCause(), null, (IMetafieldId)((BizViolation)violation).getMetafieldId(), (Object[])((BizViolation)violation).getParms());
                copyViolation.setSeverity(((BizViolation)violation).getSeverity());
                bv = copyViolation.appendToChain(bv);
            }
            throw bv;
        }
    }

    public boolean isIgnoreRouting() {
        PostingActionEnum postingAction = PostingActionEnum.APPLY;
        IMapper rule = (IMapper)this._postingRules.get((Object) PropertyGroupEnum.ROUTING);
        if (rule != null) {
//            if (rule instanceof EntityMappingPredicate) {
//                EntityMappingPredicate mappingPredicate = (EntityMappingPredicate)rule;
//                rule = (EntityMappingPredicate)HibernateApi.getInstance().get(EntityMappingPredicate.class, (Serializable)mappingPredicate.getEmappGkey());
            }
            if (rule != null) {
                FieldValue[] fieldValues;
//                FieldValue[] arrfieldValue = fieldValues = rule.mapEntity((IValueSource)this) != null ? rule.mapEntity((IValueSource)this) : ((EntityMappingPredicate)rule).getMappedValues();
//                if (fieldValues != null && fieldValues.length == 1) {
//                    postingAction = (PostingActionEnum)((Object)fieldValues[0].getValue());
//                }
//            }
        }
//        return PostingActionEnum.IGNORE.equals((Object)postingAction);
        return false;
    }

    public FieldChanges getPostedFieldChanges() {
        return this._postedChanges;
    }

    public FieldChanges getNewEntityFieldChanges() {
        return this._newEntityChanges;
    }

    public FieldChanges getTracedFieldChanges() {
        return this._tracedChanges;
    }

    public FieldChanges getIgnoredFieldChanges() {
        return this._ignoredChanges;
    }

    public boolean getIsFirstTransaction() {
        return this._isFirstTransaction;
    }

    public void setIsFirstTransaction(boolean inFirstTransaction) {
        this._isFirstTransaction = inFirstTransaction;
    }

    public Double getPackageWeight() {
        return this._packageWeight;
    }

    public void setPackageWeight(Double inPackageWeight) {
        this._packageWeight = inPackageWeight;
    }

    public TimeZone getTimeZone() {
        return this._timeZone;
    }

    public void setTimeZone(TimeZone inTimeZone) {
        this._timeZone = inTimeZone;
    }

    public boolean hasViolation() {
        return !this._violations.isEmpty();
    }

    public String getXmlTranStr() {
        return this._xmlTranStr;
    }

    public UserContext getUserContext() {
        return this._userContext;
    }

    public Map getInterceptorParmMaps() {
        return this._interceptorParmMaps;
    }

    public DataSourceEnum getDataSourceEnum() {
        return this._dataSourceEnum;
    }

    public int getTranNbr() {
        return this._tranNbr;
    }

    public int getTranSize() {
        return this._tranSize;
    }

    public Serializable getSessionGkey() {
        return this._sessionGkey;
    }

    public String getPosterPeaName(IMessageCollector inMessageCollector) {
        EdiMessageClassEnum messageClass = this.getEdiMessageClass();
        try {
            String peaName = POSTING_PEA_PREFIX + messageClass.getKey();
            if (EdiMessageClassEnum.RELEASE.equals((Object)messageClass)) {
                String releaseIdentifierType = EdiPostingContext.getPeaNameforRelease(this._xmlTranStr);
//                if (ArgoEdiUtils.isEmpty(releaseIdentifierType)) {
//                    inMessageCollector.appendMessage((IUserMessage)BizFailure.create((IPropertyKey)IArgoPropertyKeys.RELEASE_IDENTIFIER_TYPE_REQUIRED, null, null));
//                }
                peaName = POSTING_PEA_PREFIX + releaseIdentifierType;
            }
            return peaName;
        }
        catch (XmlException e) {
            inMessageCollector.appendMessage((IUserMessage) BizFailure.create((IPropertyKey) IArgoPropertyKeys.PARSE_FAILED, (Throwable)e, (Object)this._xmlTranStr.substring(100)));
        }
        catch (BizFailure inEx) {
            inMessageCollector.appendMessage((IUserMessage)inEx);
        }
        return null;
    }

    public XmlObject getEdiBean(IMessageCollector inMessageCollector) {
        try {
            XmlObject ediBean = XmlObject.Factory.parse((String)this._xmlTranStr);
            XmlOptions options = new XmlOptions();
            ArrayList errorList = new ArrayList();
            options.setErrorListener(errorList);
            boolean isValid = ediBean.validate(options);
            if (!isValid) {
                String errors = EdiPostingContext.getFormattedXmlValidationErrors(errorList);
                LOGGER.error((Object)("XML Validation errors: " + errors));
                inMessageCollector.appendMessage((IUserMessage) BizViolation.create((IPropertyKey) IArgoPropertyKeys.PARSE_FAILED, null, (Object) EdiPostingContext.stripUnwantedFromXMLError(errors)));
                return null;
            }
            return ediBean;
        }
        catch (XmlException e) {
            inMessageCollector.appendMessage((IUserMessage) BizFailure.create((IPropertyKey) IArgoPropertyKeys.PARSE_FAILED, (Throwable)e, (Object)this._xmlTranStr.substring(100)));
            return null;
        }
    }

    private static String getPeaNameforRelease(String inXmlTransaction) throws XmlException {
//        ReleaseTransactionsDocument doc = ReleaseTransactionsDocument.Factory.parse(inXmlTransaction);
//        ReleaseTransactionDocument.ReleaseTransaction release = doc.getReleaseTransactions().getReleaseTransactionArray(0);
//        return release.getReleaseIdentifierType();
        return null;
    }

    private static String getFormattedXmlValidationErrors(Collection inErrorList) {
        StringBuilder errors = new StringBuilder();
        for (Object obj : inErrorList) {
            if (obj instanceof XmlError) {
                XmlError error = (XmlError)obj;
                errors.append(" Message : ");
                errors.append(error.getMessage());
                errors.append(", ");
                errors.append("Column : ");
                errors.append(error.getColumn());
                errors.append(", ");
                errors.append("Line : ");
                errors.append(error.getLine());
                errors.append(", ");
                errors.append("Error Code : ");
                errors.append(error.getErrorCode());
                errors.append(", ");
                errors.append("Source Name : ");
                errors.append(error.getSourceName());
                errors.append(", ");
                errors.append("Severity : ");
                errors.append(error.getSeverity());
                continue;
            }
            errors.append(obj);
        }
        return errors.toString();
    }

    public static String capitalizeFirstLetter(String inStr) {
        if (inStr == null || inStr.length() == 0) {
            return inStr;
        }
        if (inStr.substring(0, 1).matches("[^A-Za-z]")) {
            return inStr.substring(1, 2).toUpperCase() + inStr.substring(2);
        }
        return inStr.substring(0, 1).toUpperCase() + inStr.substring(1);
    }

    private static String stripUnwantedFromXMLError(String inErrors) {
        String nameSpace = "@http://www.navis.com/argo";
        inErrors = inErrors.replaceAll("@http://www.navis.com/argo", "");
        inErrors = inErrors.replaceAll("error:", "");
        return inErrors.replaceAll("null", "");
    }

    public static Map createParentChildPRulePropertyMapping() {
        return PARENTCHILDPOSTINGRULEMAP;
    }

    static {
        UNIT_NON_ROUTING_GROUP_FIELDS = new ArrayList<String>();
        PARENTCHILDPOSTINGRULEMAP = new HashMap();
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ACTUAL_IB_SERVICE, PropertyGroupEnum.ACTUAL_IB_CV);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ACTUAL_IB_CLASSIFICATION, PropertyGroupEnum.ACTUAL_IB_CV);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.INTENDED_OB_SERVICE, PropertyGroupEnum.INTENDED_CV);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.INTENDED_OB_CLASSIFICATION, PropertyGroupEnum.INTENDED_CV);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_POD, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_POL, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_POD2, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_ORIGIN, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_DESTINATION, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_OPL, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_OPT1, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_OPT2, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_OPT3, PropertyGroupEnum.ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.REEFER_TEMP_MAX, PropertyGroupEnum.REEFER);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.REEFER_TEMP_MIN, PropertyGroupEnum.REEFER);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.REEFER_TEMP_REQ, PropertyGroupEnum.REEFER);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.GROSS_WEIGHT, PropertyGroupEnum.WEIGHT);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.UNIT_VERIFIED_GROSS_MASS, PropertyGroupEnum.WEIGHT);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_PINNBR, PropertyGroupEnum.RELEASE_ROUTING);
        PARENTCHILDPOSTINGRULEMAP.put(PropertyGroupEnum.ROUTING_BONDED_DESTINATION, PropertyGroupEnum.RELEASE_ROUTING);
        UNIT_NON_ROUTING_GROUP_FIELDS.add("gdsOrigin");
        UNIT_NON_ROUTING_GROUP_FIELDS.add("gdsDestination");
        EDISESS_TRADING_PARTNER = MetafieldIdFactory.valueOf((String)"edisessTradingPartner");
        UNIT_GROSS_WEIGHT_SOURCE = MetafieldIdFactory.valueOf((String)"unitGrossWeightSource");
        LOGGER = Logger.getLogger(EdiPostingContext.class);
    }
}
