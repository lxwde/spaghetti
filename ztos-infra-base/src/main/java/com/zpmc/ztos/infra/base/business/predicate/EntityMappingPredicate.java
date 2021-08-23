package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.dataobject.EntityMappingPredicateDO;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.EntityMappingValue;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldValue;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.model.ValueObject;
import com.zpmc.ztos.infra.base.common.utils.SavedPredicateUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EntityMappingPredicate extends EntityMappingPredicateDO {
    private IMetafieldId _metafieldId;
    private IPredicate _predicate;
    private static final String E_PREDICATE = "predicate";
    private static final String E_NEXT = "next-predicate";
    private static final String E_SUB = "sub-predicate";
    private static final String E_MAPPED_VALUE = "mapped-value";
    private static final String E_VALUE = "value";
    private static final String A_PRED_METAFIELD = "metafield";
    private static final String A_PRED_VERB = "verb";
    private static final String A_PRED_VALUE = "value";
    private static final String A_PRED_UI_VALUE = "ui-value";
    private static final String A_PRED_NEGATED = "negated";
    private static final Logger LOGGER = Logger.getLogger(EntityMappingPredicate.class);

    public EntityMappingPredicate() {
    }

    public EntityMappingPredicate(IValueHolder inValueHolder) {
        this(null, null, inValueHolder);
    }

    public EntityMappingPredicate(Element inElement) {
        EntityMappingPredicate emp;
//        String mfid = inElement.getAttributeValue(A_PRED_METAFIELD, inElement.getNamespace());
//        this._metafieldId = MetafieldIdFactory.valueOf((String)mfid);
//        this.setEmappMetafield(mfid);
//        this.setEmappVerb(PredicateVerbEnum.getEnum((String)inElement.getAttributeValue(A_PRED_VERB, inElement.getNamespace())));
//        this.setEmappValue(SnxUtil.externalKey2internalKey(mfid, inElement.getAttributeValue("value", inElement.getNamespace())));
//        this.setEmappUiValue(inElement.getAttributeValue(A_PRED_UI_VALUE, inElement.getNamespace()));
//        this.setEmappNegated(Boolean.valueOf(inElement.getAttributeValue(A_PRED_NEGATED, inElement.getNamespace())));
//        Element childElement = inElement.getChild(E_SUB, inElement.getNamespace());
//        if (childElement != null) {
//            emp = new EntityMappingPredicate(childElement);
//            HibernateApi.getInstance().save((Object)emp);
//            this.setEmappSubPredicate(emp);
//            HibernateApi.getInstance().flush();
//        }
//        if ((childElement = inElement.getChild(E_NEXT, inElement.getNamespace())) != null) {
//            emp = new EntityMappingPredicate(childElement);
//            HibernateApi.getInstance().save((Object)emp);
//            this.setEmappNextPredicate(emp);
//            HibernateApi.getInstance().flush();
//        }
//        ArrayList<EntityMappingValue> valueList = new ArrayList<EntityMappingValue>();
//        List valueElements = inElement.getChildren(E_MAPPED_VALUE, inElement.getNamespace());
//        if (valueElements == null || valueElements.isEmpty()) {
//            valueElements = inElement.getChildren("value", inElement.getNamespace());
//        }
//        for (Element valueElement : valueElements) {
//            EntityMappingValue value = new EntityMappingValue();
//            value.setEmapvEmapp(this);
//            String fieldId = valueElement.getAttributeValue(A_PRED_METAFIELD, valueElement.getNamespace());
//            value.setEmapvMetafield(fieldId);
//            value.setEmapvValue(SnxUtil.externalKey2internalKey(fieldId, valueElement.getAttributeValue("value", valueElement.getNamespace())));
//            value.setEmapvUiValue(valueElement.getAttributeValue(A_PRED_UI_VALUE, valueElement.getNamespace()));
//            valueList.add(value);
//        }
//        this.setEmappValues(valueList);
    }

    private EntityMappingPredicate(EntityMappingPredicate inParent, EntityMappingPredicate inSibling, IValueHolder inValueHolder) {
        IMetafieldId mfid;
        this._metafieldId = mfid = (IMetafieldId)inValueHolder.getFieldValue(IArgoField.EMAPP_METAFIELD);
        String mfidName = mfid == null ? null : mfid.getQualifiedId();
        Object fieldValue = inValueHolder.getFieldValue(IArgoField.EMAPP_VALUE);
        Object fieldUiValue = inValueHolder.getFieldValue(IArgoField.EMAPP_UI_VALUE);
        String fieldUiString = fieldUiValue == null ? null : fieldUiValue.toString();
        this.setEmappMetafield(mfidName);
        this.setEmappVerb((PredicateVerbEnum)inValueHolder.getFieldValue(IArgoField.EMAPP_VERB));
        this.setEmappValue(SavedPredicateUtils.formValueString((IMetafieldId)mfid, (Object)fieldValue));
        this.setEmappUiValue(fieldUiString);
        this.setEmappNegated((Boolean)inValueHolder.getFieldValue(IArgoField.EMAPP_NEGATED));
        IValueHolder nextVao = (IValueHolder)inValueHolder.getFieldValue(IArgoField.EMAPP_NEXT_PREDICATE);
        IValueHolder subVao = (IValueHolder)inValueHolder.getFieldValue(IArgoField.EMAPP_SUB_PREDICATE);
        this.setMappedValues((FieldValue[])inValueHolder.getFieldValue(IArgoBizMetafield.EMAPP_MAPPED_VALUES));
        if (inParent != null) {
            inParent.setEmappSubPredicate(this);
        } else if (inSibling != null) {
            inSibling.setEmappNextPredicate(this);
        }
        if (subVao != null) {
            new EntityMappingPredicate(this, null, subVao);
        }
        if (nextVao != null) {
            new EntityMappingPredicate(null, this, nextVao);
        }
    }

    public IValueHolder getEntityMappingVao() {
        return EntityMappingPredicate.buildEntityMappingVao(this, null, null);
    }

    private static IValueHolder buildEntityMappingVao(EntityMappingPredicate inRoot, ValueObject inParentVao, ValueObject inSiblingVao) {
        EntityMappingPredicate sibling;
        EntityMappingPredicate child;
        IMetafieldId mfid = MetafieldIdFactory.valueOf((String)inRoot.getEmappMetafield());
        Object fieldValue = SavedPredicateUtils.parseValueString((IMetafieldId)mfid, (String)inRoot.getEmappValue());
        FieldValue[] mappedValues = inRoot.getMappedValues();
        ValueObject vao = new ValueObject("EntityMappingPredicate");
        vao.setFieldValue(IArgoField.EMAPP_METAFIELD, (Object)inRoot.getMetafieldId());
        vao.setFieldValue(IArgoField.EMAPP_VERB, (Object)inRoot.getEmappVerb());
        vao.setFieldValue(IArgoField.EMAPP_VALUE, fieldValue);
        vao.setFieldValue(IArgoField.EMAPP_NEGATED, (Object)inRoot.getEmappNegated());
        vao.setFieldValue(IArgoField.EMAPP_UI_VALUE, (Object)inRoot.getEmappUiValue());
        vao.setFieldValue(IArgoField.EMAPP_NEXT_PREDICATE, null);
        vao.setFieldValue(IArgoField.EMAPP_SUB_PREDICATE, null);
        vao.setFieldValue(IArgoBizMetafield.EMAPP_MAPPED_VALUES, (Object)mappedValues);
        if (inParentVao != null) {
            inParentVao.setFieldValue(IArgoField.EMAPP_SUB_PREDICATE, (Object)vao);
        }
        if (inSiblingVao != null) {
            inSiblingVao.setFieldValue(IArgoField.EMAPP_NEXT_PREDICATE, (Object)vao);
        }
        if ((child = inRoot.getEmappSubPredicate()) != null) {
            EntityMappingPredicate.buildEntityMappingVao(child, vao, null);
        }
        if ((sibling = inRoot.getEmappNextPredicate()) != null) {
            EntityMappingPredicate.buildEntityMappingVao(sibling, null, vao);
        }
        return vao;
    }

    public IMetafieldId getMetafieldId() {
        return this._metafieldId != null ? this._metafieldId : MetafieldIdFactory.valueOf((String)this.getEmappMetafield());
    }

    @Nullable
    public FieldValue[] mapEntity(IValueSource inEntity) {
        if (this.satisfiesThis(inEntity)) {
            EntityMappingPredicate child = this.getEmappSubPredicate();
            if (child != null) {
                return child.mapEntity(inEntity);
            }
            return this.getMappedValues();
        }
        EntityMappingPredicate sibling = this.getEmappNextPredicate();
        if (sibling != null) {
            return sibling.mapEntity(inEntity);
        }
        return null;
    }

    @Nullable
    protected FieldValue[] getMappedValues() {
        FieldValue[] fieldValues = null;
        List values = this.getEmappValues();
        if (values != null && !values.isEmpty()) {
            fieldValues = new FieldValue[values.size()];
            int i = 0;
            for (Object emapv : values) {
                IMetafieldId metafieldId = MetafieldIdFactory.valueOf((String)((EntityMappingValue)emapv).getEmapvMetafield());
                Object fieldValue = SavedPredicateUtils.parseValueString((IMetafieldId)metafieldId, (String)((EntityMappingValue)emapv).getEmapvValue());
                fieldValues[i++] = new FieldValue(metafieldId, fieldValue, ((EntityMappingValue)emapv).getEmapvUiValue());
            }
        }
        return fieldValues;
    }

    private void setMappedValues(FieldValue[] inMappedValues) {
        if (inMappedValues != null) {
            ArrayList<EntityMappingValue> valueList = new ArrayList<EntityMappingValue>();
            this.setEmappValues(valueList);
            for (int i = 0; i < inMappedValues.length; ++i) {
                FieldValue fv = inMappedValues[i];
                Object value = fv.getValue();
                String valueAsString = SavedPredicateUtils.formValueString((IMetafieldId)fv.getFieldId(), (Object)value);
                String uiValue = fv.getUiValue();
                EntityMappingValue emapv = new EntityMappingValue();
                emapv.setEmapvEmapp(this);
                valueList.add(emapv);
                emapv.setEmapvMetafield(fv.getFieldId().getQualifiedId());
                emapv.setEmapvValue(valueAsString);
                emapv.setEmapvUiValue(uiValue);
            }
        }
    }

    private boolean satisfiesThis(IValueSource inEntity) {
        IPredicate predicate = this.getExecutablePredicate();
        return predicate.isSatisfiedBy(inEntity);
    }

    public IPredicate getExecutablePredicate() {
        if (this._predicate != null) {
            return this._predicate;
        }
        IMetafieldId mfid = this.getMetafieldId();
        Object operand = SavedPredicateUtils.parseValueString((IMetafieldId)mfid, (String)this.getEmappValue());
        PredicateVerbEnum verb = this.getEmappVerb();
        UserContext uc = ContextHelper.getThreadUserContext();
        this._predicate = PredicateFactory.createPredicate((UserContext)uc, (IMetafieldId)mfid, (PredicateVerbEnum)verb, (Object)operand);
        if (this.getEmappNegated().booleanValue()) {
            this._predicate = PredicateFactory.not((IPredicate)this._predicate);
        }
        return this._predicate;
    }

    public Element toSnxElement(String inElementName) {
        Element element;
        if (inElementName == null) {
            inElementName = E_PREDICATE;
        }
//        Element thisElement = new Element(inElementName);
//        this.setAttribute(thisElement, A_PRED_VERB, this.getEmappVerb().getKey());
//        this.setAttribute(thisElement, A_PRED_METAFIELD, this.getEmappMetafield());
//        this.setAttribute(thisElement, "value", SnxUtil.internalKey2ExternalKey(this.getEmappMetafield(), this.getEmappValue()));
//        this.setAttribute(thisElement, A_PRED_UI_VALUE, this.getEmappUiValue());
//        this.setAttribute(thisElement, A_PRED_NEGATED, String.valueOf(this.getEmappNegated()));
//        List mappedValues = this.getEmappValues();
//        for (Object mappedValue : mappedValues) {
//            Element valueElement = new Element(E_MAPPED_VALUE);
//            this.setAttribute(valueElement, A_PRED_METAFIELD, ((EntityMappingValue)mappedValue).getEmapvMetafield());
//            this.setAttribute(valueElement, "value", SnxUtil.internalKey2ExternalKey(((EntityMappingValue)mappedValue).getEmapvMetafield(), ((EntityMappingValue)mappedValue).getEmapvValue()));
//            this.setAttribute(valueElement, A_PRED_UI_VALUE, ((EntityMappingValue)mappedValue).getEmapvUiValue());
//            thisElement.addContent((AbstractDocument.Content)valueElement);
//        }
//        EntityMappingPredicate child = this.getEmappSubPredicate();
//        if (child != null) {
//            element = child.toSnxElement(E_SUB);
//            thisElement.addContent((AbstractDocument.Content)element);
//        }
//        if ((child = this.getEmappNextPredicate()) != null) {
//            element = child.toSnxElement(E_NEXT);
//            thisElement.addContent((AbstractDocument.Content)element);
//        }
//        return thisElement;
        return null;
    }

    private void setAttribute(Element inElement, String inAttributeName, String inAttributeValue) {
        if (inAttributeValue != null) {
    //        inElement.setAttribute(inAttributeName, inAttributeValue);
        }
    }

    public boolean hasMappedValue(IMetafieldId inMetafieldId, Object inMappedValue) {
        boolean hasMapped = false;
        FieldValue[] fieldValues = this.getMappedValues();
        if (fieldValues != null) {
            for (FieldValue value : fieldValues) {
                IMetafieldId fieldId = value.getFieldId();
                Object mappedValues = value.getValue();
                if (!inMetafieldId.equals((Object)fieldId)) continue;
                Object[] valueList = mappedValues instanceof Object[] ? (Object[])mappedValues : new Object[]{mappedValues};
                for (Object mappedValue : valueList) {
                    if (!inMappedValue.equals(mappedValue)) continue;
                    return true;
                }
            }
        }
        if (this.getEmappSubPredicate() != null) {
            EntityMappingPredicate sibling = this.getEmappSubPredicate();
            hasMapped = sibling.hasMappedValue(inMetafieldId, inMappedValue);
        }
        if (!hasMapped && this.getEmappNextPredicate() != null) {
            EntityMappingPredicate nextPredicate = this.getEmappNextPredicate();
            hasMapped = nextPredicate.hasMappedValue(inMetafieldId, inMappedValue);
        }
        return hasMapped;
    }
}
