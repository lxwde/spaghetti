package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.SavedPredicateDO;
import com.zpmc.ztos.infra.base.business.enums.core.PredicateParmEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.Junction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.utils.SavedPredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Namespace;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SavedPredicate extends SavedPredicateDO implements IEntityUniquenessAware {
    private static final Logger LOGGER = Logger.getLogger(SavedPredicate.class);
    private IMetafieldId _metafieldId;
    private static final String PREDICATE_PARAMETER = "PredicateParameter";
    private static final String E_PREDICATE = "predicate";
    private static final String A_PRED_METAFIELD = "metafield";
    private static final String A_PRED_VERB = "verb";
    private static final String A_PRED_VALUE = "value";
    private static final String A_PRED_UI_VALUE = "ui-value";
    private static final String A_PRED_ORDER = "order";
    private static final String A_PRED_NEGATED = "negated";
    private static final String A_PRED_PARM_TYPE = "parm-type";
    private static final String A_PRED_PARM_LABEL = "parm-label";
    private static final String A_PRED_PARM_INTERNAL_NAME = "parm-internal-name";
    private static final char NATURAL_KEY_FLAG = '^';

    public SavedPredicate() {
        this.setPrdctParameterType(PredicateParmEnum.NO_PARM);
    }

    public SavedPredicate(IValueHolder inValueHolder) {
        IValueHolder[] childPredicates;
        IMetafieldId mfid;
        this._metafieldId = mfid = (IMetafieldId)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_METAFIELD);
        String mfidName = mfid == null ? null : mfid.getQualifiedId();
        Object fieldValue = inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_VALUE);
        Object fieldUiValue = inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_UI_VALUE);
        String fieldUiString = fieldUiValue == null ? null : fieldUiValue.toString();
        this.setPrdctMetafield(mfidName);
        this.setPrdctVerb((PredicateVerbEnum)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_VERB));
        this.setPrdctValue(SavedPredicateUtils.formValueString(mfid, fieldValue));
        this.setPrdctUiValue(fieldUiString);
        this.setPrdctOrder((Long)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_ORDER));
        this.setPrdctNegated((Boolean)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_NEGATED));
        PredicateParmEnum parmType = (PredicateParmEnum)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_TYPE);
        this.setPrdctParameterType(parmType);
        if (!PredicateParmEnum.NO_PARM.equals(parmType)) {
            this.setPrdctParameterLabel((String)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_LABEL));
            this.setPrdctParameterInternalName((String)inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_INTERNAL_NAME));
            if (this.getPrdctParameterInternalName() == null || this.getPrdctParameterInternalName().isEmpty()) {
                String parmInternalName = this.createUniqueParmName();
                this.setPrdctParameterInternalName(parmInternalName);
            }
        }
        if ((childPredicates = (IValueHolder[])inValueHolder.getFieldValue(ISimpleSavedQueryField.PRDCT_CHILD_PRDCT_LIST)) != null && childPredicates.length > 0) {
            ArrayList<SavedPredicate> childList = new ArrayList<SavedPredicate>();
            this.setPrdctChildPrdctList(childList);
            for (IValueHolder childPredicate : childPredicates) {
                SavedPredicate child = new SavedPredicate(childPredicate);
                child.setPrdctParentPredicate(this);
                childList.add(child);
            }
        }
    }

    public SavedPredicate(Element inElement) {
        Namespace ns = inElement.getNamespace();
//        this.setPrdctVerb(PredicateVerbEnum.getEnum(inElement.getAttributeValue(A_PRED_VERB, ns)));
//        this.setPrdctMetafield(inElement.getAttributeValue(A_PRED_METAFIELD, ns));
//        String externalKey = inElement.getAttributeValue(A_PRED_VALUE, ns);
//        this.setPrdctValue(this.externalKey2internalKey(this.getPrdctMetafield(), externalKey));
//        this.setPrdctUiValue(inElement.getAttributeValue(A_PRED_UI_VALUE, ns));
//        this.setPrdctOrder(Long.valueOf(inElement.getAttributeValue(A_PRED_ORDER, ns)));
//        this.setPrdctNegated(Boolean.valueOf(inElement.getAttributeValue(A_PRED_NEGATED, ns)));
//        this.setPrdctParameterType(PredicateParmEnum.getEnum(inElement.getAttributeValue(A_PRED_PARM_TYPE, ns)));
//        this.setPrdctParameterLabel(inElement.getAttributeValue(A_PRED_PARM_LABEL, ns));
//        this.setPrdctParameterInternalName(inElement.getAttributeValue(A_PRED_PARM_INTERNAL_NAME, ns));
//        List childElements = inElement.getChildren(E_PREDICATE, ns);
//        if (childElements != null) {
//            ArrayList<SavedPredicate> childPredicates = new ArrayList<SavedPredicate>();
//            this.setPrdctChildPrdctList(childPredicates);
//            for (Object childElement : childElements) {
//                SavedPredicate child = new SavedPredicate((Element)childElement);
//                child.setPrdctParentPredicate(this);
//                childPredicates.add(child);
//            }
//        }
    }

    @Nullable
    public IMetafieldId getMetafieldId() {
        return this._metafieldId != null ? this._metafieldId : MetafieldIdFactory.valueOf(this.getPrdctMetafield());
    }

    public void getPredicateParameterFieldVaos(List inOutParameterListToFill) {
        List children;
        PredicateParmEnum parmType = this.getPrdctParameterType();
        if (!PredicateParmEnum.NO_PARM.equals(parmType)) {
            IValueHolder parameterVao = this.createParameterVao();
            inOutParameterListToFill.add(parameterVao);
        }
        if ((children = this.getPrdctChildPrdctList()) != null && !children.isEmpty()) {
            for (Object aChildren : children) {
                SavedPredicate child = (SavedPredicate)aChildren;
                child.getPredicateParameterFieldVaos(inOutParameterListToFill);
            }
        }
    }

    private IValueHolder createParameterVao() {
        IMetafieldId mfid = MetafieldIdFactory.valueOf(this.getPrdctMetafield());
        Object fieldDefaultValue = SavedPredicateUtils.parseValueString(mfid, this.getPrdctValue());
        PredicateParmEnum parmType = this.getPrdctParameterType();
        String parmLabel = this.getPrdctParameterLabel();
        String parmInternalName = this.getPrdctParameterInternalName();
        PredicateVerbEnum paramVerb = this.getPrdctVerb();
        boolean isParmRequired = false;
        if (PredicateParmEnum.REQUIRED_PARM.equals(parmType)) {
            isParmRequired = true;
        }
        ValueObject vao = new ValueObject(PREDICATE_PARAMETER);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_METAFIELD_ID, (Object)mfid);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_VALUE, fieldDefaultValue);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_IS_MULTI_VALUED, (Object)(PredicateVerbEnum.IN == paramVerb ? 1 : 0));
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_LABEL, (Object)parmLabel);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_INTERNAL_NAME, (Object)parmInternalName);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_IS_REQUIRED, (Object)isParmRequired);
        vao.setFieldValue(IFrameworkBizMetafield.PREDICATE_PARM_VERB, (Object)paramVerb);
        return vao;
    }

    public boolean requiresParameterEntry() {
        PredicateParmEnum parmType = this.getPrdctParameterType();
        if (PredicateParmEnum.REQUIRED_PARM.equals(parmType) && this.getPrdctValue() == null) {
            return true;
        }
        List children = this.getPrdctChildPrdctList();
        if (children != null && !children.isEmpty()) {
            for (Object aChildren : children) {
                SavedPredicate child = (SavedPredicate)aChildren;
                if (!child.requiresParameterEntry()) continue;
                return true;
            }
        }
        return false;
    }

    public IValueHolder getPredicateVao() {
        IValueHolder[] childPredicates;
        IMetafieldId mfid = MetafieldIdFactory.valueOf(this.getPrdctMetafield());
        Object fieldValue = SavedPredicateUtils.parseValueString(mfid, this.getPrdctValue());
        ValueObject vao = new ValueObject("SavedPredicate");
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_METAFIELD, (Object)mfid);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_VERB, (Object)this.getPrdctVerb());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_VALUE, fieldValue);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_UI_VALUE, (Object)this.getPrdctUiValue());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_ORDER, (Object)this.getPrdctOrder());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_NEGATED, (Object)this.getPrdctNegated());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_TYPE, (Object)this.getPrdctParameterType());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_LABEL, (Object)this.getPrdctParameterLabel());
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_INTERNAL_NAME, (Object)this.getPrdctParameterInternalName());
        List children = this.getPrdctChildPrdctList();
        if (children == null || children.isEmpty()) {
            childPredicates = new IValueHolder[]{};
        } else {
            childPredicates = new IValueHolder[children.size()];
            int i = 0;
            for (Object aChildren : children) {
                SavedPredicate child = (SavedPredicate)aChildren;
                childPredicates[i++] = child.getPredicateVao();
            }
        }
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_CHILD_PRDCT_LIST, (Object)childPredicates);
        return vao;
    }

    @Nullable
    public IPredicate getExecutablePredicate() {
        TransactionParms tp = TransactionParms.getBoundParms();
        UserContext userContext = tp.getUserContext();
        return this.getExecutablePredicate(userContext, Collections.emptyMap());
    }

    @Nullable
    public IPredicate getExecutablePredicate(UserContext inUserContext, Map inPredicateParms) {
        boolean hasChildren;
        Object operand;
        IMetafieldId mfid = this.getMetafieldId();
        PredicateParmEnum parameterType = this.getPrdctParameterType();
        if (parameterType == null) {
            throw BizFailure.create("getPrdctParameterType is NULL.  Should be defined.");
        }
        if (PredicateParmEnum.NO_PARM.equals(parameterType)) {
            operand = SavedPredicateUtils.parseValueString(mfid, this.getPrdctValue());
        } else {
            String key = this.getPrdctParameterInternalName();
            if (inPredicateParms.containsKey(key)) {
                operand = inPredicateParms.get(key);
                if (operand == null && PredicateParmEnum.OPTIONAL_PARM.equals(parameterType)) {
                    return null;
                }
                if (operand == null && PredicateParmEnum.REQUIRED_PARM.equals(parameterType)) {
                    throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__PREDICATE_PARM_REQUIRED_PARAM_MISSING, null, mfid, this.getPrdctParameterLabel());
                }
            } else {
                if (PredicateParmEnum.OPTIONAL_PARM.equals(parameterType)) {
                    return null;
                }
                throw BizFailure.create(IFrameworkPropertyKeys.FAILURE__PREDICATE_PARM_REQUIRED_PARAM_MISSING, null, mfid, this.getPrdctParameterLabel());
            }
        }
        PredicateVerbEnum verb = this.getPrdctVerb();
        boolean isNegated = this.getPrdctNegated();
        if (!PredicateParmEnum.NO_PARM.equals(parameterType) && operand == Boolean.FALSE && PredicateVerbEnum.TRUE.equals(verb)) {
            verb = PredicateVerbEnum.FALSE;
        }
        if (verb.equals(PredicateVerbEnum.EQ) && isNegated) {
            isNegated = false;
            verb = PredicateVerbEnum.NE;
        }
        IPredicate predicate = this.createPredicate(inUserContext, mfid, verb, operand);
        List children = this.getPrdctChildPrdctList();
        if (children != null) {
            if (predicate instanceof Junction) {
                Junction junction = (Junction)predicate;
                for (Object aChildren : children) {
                    SavedPredicate child = (SavedPredicate)aChildren;
                    IPredicate executablePredicate = child.getExecutablePredicate(inUserContext, inPredicateParms);
                    if (executablePredicate == null) continue;
                    boolean hasChildren2 = true;
                    if (executablePredicate instanceof Junction) {
                        hasChildren2 = ((Junction)executablePredicate).getPredicateIterator().hasNext();
                    }
                    if (!hasChildren2) continue;
                    junction.add(executablePredicate);
                }
            } else if (!children.isEmpty()) {
                throw BizFailure.create("invalid SavedPredicate: a predicate with children must be a Junction or a Negation.");
            }
        }
        if (isNegated) {
            predicate = PredicateFactory.not(predicate);
        }
        if (predicate instanceof Junction && !(hasChildren = ((Junction)predicate).getPredicateIterator().hasNext())) {
            predicate = null;
        }
        return predicate;
    }

    public void explainMisMatch(@NotNull UserContext inUserContext, IValueSource inValueSource, StringBuffer inOutBuffer) {
        List children;
        ScopeCoordinates scopeCoordinates = inUserContext.getScopeCoordinate();
//        IMetafieldDictionaryScopedProvider scopedProvider = (IMetafieldDictionaryScopedProvider) Roastery.getBean("MetafieldDictionaryProvider");
//        IMetafieldDictionary metafieldDictionary = scopedProvider.getMetafieldDictionary(scopeCoordinates);
//        IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider)PortalApplicationContext.getBean("messageTranslatorProvider");
//        IMessageTranslator messageTranslator = translatorProvider.getMessageTranslator(Locale.ENGLISH);
//        IMetafieldId mfid = this.getMetafieldId();
//        Object operand = SavedPredicateUtils.parseValueString(mfid, this.getPrdctValue());
//        PredicateVerbEnum verb = this.getPrdctVerb();
//        if ((PredicateVerbEnum.AND.equals(verb) || PredicateVerbEnum.OR.equals(verb)) && (children = this.getPrdctChildPrdctList()) != null) {
//            for (Object aChildren : children) {
//                SavedPredicate child = (SavedPredicate)aChildren;
//                if (this.getPrdctNegated().booleanValue()) {
//                    child.setPrdctNegated(child.getPrdctNegated() == false);
//                    if (child != children.get(0)) {
//                        if (PredicateVerbEnum.AND.equals(verb)) {
//                            inOutBuffer.append(" AND ");
//                        } else {
//                            inOutBuffer.append(" OR ");
//                        }
//                    }
//                }
//                child.explainMisMatch(inUserContext, inValueSource, inOutBuffer);
//            }
//        }
//        boolean isNegated = this.getPrdctNegated();
//        if (verb.equals(PredicateVerbEnum.EQ) && isNegated) {
//            isNegated = false;
//            verb = PredicateVerbEnum.NE;
//        }
//        IPredicate predicate = this.createPredicate(inUserContext, mfid, verb, operand);
//        if (isNegated) {
//            predicate = PredicateFactory.not(predicate);
//        }
//        if (mfid != null && !predicate.isSatisfiedBy(inValueSource)) {
//            String fieldLabel = null;
//            IMetafield mfd = metafieldDictionary.findMetafield(mfid);
//            if (mfd != null) {
//                fieldLabel = messageTranslator.getMessage(mfd.getLongLabelKey());
//            }
//            String verbString = messageTranslator.getMessage(verb.getDescriptionPropertyKey());
//            if (isNegated) {
//                inOutBuffer.append("NOT! (");
//            }
//            inOutBuffer.append(fieldLabel);
//            inOutBuffer.append(' ');
//            inOutBuffer.append(verbString);
//            inOutBuffer.append(' ');
//            inOutBuffer.append(this.getPrdctUiValue());
//            if (isNegated) {
//                inOutBuffer.append(')');
//            }
//            inOutBuffer.append('\n');
//        }
    }

    private IPredicate createPredicate(@NotNull UserContext inUserContext, IMetafieldId inMfid, PredicateVerbEnum inVerb, Object inOperand) {
        IPredicate predicate = null;
        if (PredicateVerbEnum.NE.equals(inVerb) && inOperand != null && HiberCache.isDbNullable(inMfid)) {
            predicate = PredicateFactory.disjunction().add(PredicateFactory.isNull(inMfid)).add(PredicateFactory.createPredicate(inUserContext, inMfid, inVerb, inOperand));
        } else if (PredicateVerbEnum.FALSE.equals(inVerb) && HiberCache.isDbNullable(inMfid)) {
            predicate = PredicateFactory.disjunction().add(PredicateFactory.isNull(inMfid)).add(PredicateFactory.createPredicate(inUserContext, inMfid, inVerb, inOperand));
        }
        if (predicate == null) {
            predicate = PredicateFactory.createPredicate(inUserContext, inMfid, inVerb, inOperand);
        }
        return predicate;
    }

    public static IValueHolder createPredicateValueHolder(IMetafieldId inMfid, PredicateVerbEnum inVerb, Object inValue, Object inUiValue, int inOrder, boolean inIsNegated, PredicateParmEnum inParmType, String inParmLabel, IValueHolder[] inChildrenValueHolders) {
        if (inVerb == null || inParmType == null) {
            throw BizFailure.create("Invalid usage of method createPredicateValueHolder: parameter inVerb or inParm can't be null");
        }
        if (PredicateParmEnum.NO_PARM.equals(inParmType) && inParmLabel != null) {
            LOGGER.warn((Object)"cretaePredicateValueHolder called with invalid parameters: if inParm is NON_PARM then inParmName should be null. Setting inParmName to null...");
            inParmLabel = null;
        } else if (!PredicateParmEnum.NO_PARM.equals(inParmType) && inParmLabel == null) {
            throw BizFailure.create("Invalid usage of method createPredicateValueHolder: If Criteria is of REQUIRED_PARM or OPTIONAL_PARM then a non-null inParmLabel has to be entered");
        }
        ValueObject vao = new ValueObject("SavedPredicate");
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_METAFIELD, (Object)inMfid);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_VERB, (Object)inVerb);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_VALUE, inValue);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_UI_VALUE, inUiValue);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_NEGATED, (Object)inIsNegated);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_ORDER, (Object)inOrder);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_TYPE, (Object)inParmType);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_LABEL, (Object)inParmLabel);
        vao.setFieldValue(ISimpleSavedQueryField.PRDCT_CHILD_PRDCT_LIST, (Object)inChildrenValueHolders);
        return vao;
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bizViolation = super.validateChanges(inChanges);
        PredicateParmEnum parmType = this.getPrdctParameterType();
        String parmLabel = this.getPrdctParameterLabel();
        String parmIntName = this.getPrdctParameterInternalName();
        if (PredicateParmEnum.NO_PARM.equals(parmType) && parmLabel != null && parmIntName != null) {
            LOGGER.warn((Object)"Entered ParmType is NON_PARM -- the ParmName and ParmLabel has to be null. Setting parmName and parmLabel to null...");
            this.setPrdctParameterLabel(null);
            this.setPrdctParameterInternalName(null);
        }
        if (!PredicateParmEnum.NO_PARM.equals(parmType)) {
            PredicateVerbEnum verb;
            if (parmLabel == null) {
                bizViolation = BizViolation.create(IFrameworkPropertyKeys.FAILURE__PREDICATE_PARM_LABEL_MISSING, bizViolation);
            }
//            if (!SavedQueryFactory.isPredicateVerbSupportedForParam(verb = this.getPrdctVerb())) {
//                bizViolation = BizViolation.create(IFrameworkPropertyKeys.FAILURE__PREDICATE_PARM_UNSUPPORTED_VERB, null, bizViolation);
//            }
//            if (this.getPrdctParameterInternalName() == null) {
//                String parmInternalName = this.createUniqueParmName();
//                this.setPrdctParameterInternalName(parmInternalName);
//            }
        }
        return bizViolation;
    }

    private String createUniqueParmName() {
        String uniqueId = new UniqueID(){}.toString();
        return uniqueId;
    }

    public Element toSnxElement(Element inParentElement) {
//        Element thisElement = new Element(E_PREDICATE);
//        this.setAttribute(thisElement, A_PRED_VERB, this.getPrdctVerb().getKey());
//        this.setAttribute(thisElement, A_PRED_METAFIELD, this.getPrdctMetafield());
//        this.setAttribute(thisElement, A_PRED_VALUE, this.internalKey2ExternalKey(this.getPrdctMetafield(), this.getPrdctValue()));
//        this.setAttribute(thisElement, A_PRED_UI_VALUE, this.getPrdctUiValue());
//        this.setAttribute(thisElement, A_PRED_ORDER, String.valueOf(this.getPrdctOrder().intValue()));
//        this.setAttribute(thisElement, A_PRED_NEGATED, String.valueOf(this.getPrdctNegated()));
//        this.setAttribute(thisElement, A_PRED_PARM_TYPE, this.getPrdctParameterType().getKey());
//        this.setAttribute(thisElement, A_PRED_PARM_LABEL, this.getPrdctParameterLabel());
//        this.setAttribute(thisElement, A_PRED_PARM_INTERNAL_NAME, this.getPrdctParameterInternalName());
//        List children = this.getPrdctChildPrdctList();
//        if (children != null) {
//            for (Object aChildren : children) {
//                SavedPredicate child = (SavedPredicate)aChildren;
//                child.toSnxElement(thisElement);
//            }
//        }
//        if (inParentElement != null) {
//            inParentElement.addContent((Content)thisElement);
//        }
//        return thisElement;
        return null;
    }

    @Override
    public IMetafieldId[] getUniqueKeyFieldIds() {
        return new IMetafieldId[]{ISimpleSavedQueryField.PRDCT_VERB, ISimpleSavedQueryField.PRDCT_METAFIELD, ISimpleSavedQueryField.PRDCT_VALUE, ISimpleSavedQueryField.PRDCT_ORDER, ISimpleSavedQueryField.PRDCT_PARENT_PREDICATE};
    }

    @Override
    public IMetafieldId getPrimaryBusinessKey() {
        return null;
    }

    private String internalKey2ExternalKey(String inFieldId, String inInternalKey) {
        IMetafieldId inMetafieldId = MetafieldIdFactory.valueOf(inFieldId);
        if (StringUtils.isEmpty((String)inInternalKey)) {
            return inInternalKey;
        }
        Class propertyClass = HiberCache.getFieldClass(inMetafieldId.getFieldId());
        if (INaturallyKeyedEntity.class.isAssignableFrom(propertyClass)) {
            try {
                Object primaryKey;
                try {
                    primaryKey = Long.parseLong(inInternalKey);
                }
                catch (NumberFormatException e) {
                    primaryKey = inInternalKey;
                }
                INaturallyKeyedEntity entity = (INaturallyKeyedEntity) HibernateApi.getInstance().load(propertyClass, (Serializable)primaryKey);
                return '^' + entity.getNaturalKey();
            }
            catch (Exception e) {
                LOGGER.error((Object)("internalKey2ExternalKey: could not load: " + e));
                return inInternalKey;
            }
        }
        return inInternalKey;
    }

    private String externalKey2internalKey(String inFieldId, String inExternalKey) {
        IMetafieldId inMetafieldId = MetafieldIdFactory.valueOf(inFieldId);
        if (StringUtils.isEmpty((String)inExternalKey)) {
            return inExternalKey;
        }
        Class propertyClass = HiberCache.getFieldClass(inMetafieldId.getFieldId());
        if (INaturallyKeyedEntity.class.isAssignableFrom(propertyClass) && inExternalKey.length() > 1 && inExternalKey.charAt(0) == '^') {
            try {
                String convertedExternalKey = inExternalKey.substring(1);
                INaturallyKeyedEntity sample = (INaturallyKeyedEntity)propertyClass.newInstance();
                INaturallyKeyedEntity entity = sample.findByNaturalKey(convertedExternalKey);
                Serializable primaryKey = entity == null ? null : entity.getPrimaryKey();
                return primaryKey == null ? null : primaryKey.toString();
            }
            catch (Exception e) {
                LOGGER.error((Object)("externalKey2internalKey: could not resolve: " + e));
                return inExternalKey;
            }
        }
        return inExternalKey;
    }

    private void setAttribute(Element inElement, String inAttributeName, String inAttributeValue) {
        if (inAttributeValue != null) {
 //           inElement.setAttribute(inAttributeName, inAttributeValue);
        }
    }
}
