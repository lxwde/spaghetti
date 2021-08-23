package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.ScopedBizUnitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.LifeCycleStateEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.ObsoletableFilterFactory;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScopedBizUnit extends ScopedBizUnitDO {
    public static final String UNKNOWN_BIZ_UNIT = "UNK";
    private static final String COMMA = ",";
    public static final String AGENCY_BIC = "BIC";
    public static final String AGENCY_SCAC = "SCAC";
    private static final Logger LOGGER = Logger.getLogger(ScopedBizUnit.class);

    public static ScopedBizUnit hydrate(Serializable inPrimaryKey) {
        return (ScopedBizUnit) HibernateApi.getInstance().load(ScopedBizUnit.class, inPrimaryKey);
    }

    public ScopedBizUnit() {
        this.setBzuIsEqOperator(Boolean.FALSE);
        this.setBzuIsEqOwner(Boolean.FALSE);
        this.setBzuLifeCycleState(LifeCycleStateEnum.ACTIVE);
    }

    public static ScopedBizUnit findOrCreateScopedBizUnit(String inId, BizRoleEnum inRole) {
        ScopedBizUnit sbu = ScopedBizUnit.findScopedBizUnit(inId, inRole);
        if (sbu == null) {
            sbu = ScopedBizUnit.createScopedBizUnit(inId, inRole, null);
        }
        return sbu;
    }

    public static ScopedBizUnit findOrCreateLeasingCompany(String inId) {
        ScopedBizUnit sbu = ScopedBizUnit.findScopedBizUnit(inId, BizRoleEnum.LEASINGCO);
        if (sbu == null) {
            sbu = ScopedBizUnit.createScopedBizUnit(inId, BizRoleEnum.LEASINGCO, null);
            sbu.setBzuIsEqOwner(Boolean.TRUE);
        } else {
            sbu.setBzuLifeCycleState(LifeCycleStateEnum.ACTIVE);
        }
        return sbu;
    }

    public static ScopedBizUnit findOrCreateLeasingCompanyProxy(String inId) {
        ScopedBizUnit sbu = ScopedBizUnit.findScopedBizUnitProxy(inId, BizRoleEnum.LEASINGCO);
        if (sbu == null) {
            sbu = ScopedBizUnit.createScopedBizUnit(inId, BizRoleEnum.LEASINGCO, null);
            sbu.setBzuIsEqOwner(Boolean.TRUE);
        } else {
            sbu.setBzuLifeCycleState(LifeCycleStateEnum.ACTIVE);
        }
        return sbu;
    }

    public void setLifeCycleState(LifeCycleStateEnum inLifeCycleState) {
        this.setBzuLifeCycleState(inLifeCycleState);
    }

    public LifeCycleStateEnum getLifeCycleState() {
        return this.getBzuLifeCycleState();
    }

    public static ScopedBizUnit findScopedBizUnit(String inId, BizRoleEnum inRole) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object)inRole)));
        return (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public static ScopedBizUnit findScopedBizUnitProxy(String inId, BizRoleEnum inRole) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object)inRole)));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        if (bzuGkey.length == 1) {
            return (ScopedBizUnit)HibernateApi.getInstance().load(ScopedBizUnit.class, bzuGkey[0]);
        }
        throw BizFailure.create((IPropertyKey) IFrameworkPropertyKeys.FRAMEWORK__NON_UNIQUE_RESULT, null, (Object)new Long(bzuGkey.length), (Object)dq);
    }

    public static ScopedBizUnit resolveScopedBizUnit(String inScopeBizId, String inScopeBizIdAgency, BizRoleEnum inRole) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object)inRole)));
        if (AGENCY_BIC.equals(inScopeBizIdAgency)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_BIC, (Object)inScopeBizId));
        } else if (AGENCY_SCAC.equals(inScopeBizIdAgency)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_SCAC, (Object)inScopeBizId));
        } else if (ArgoConfig.SHIPPER_CONSIGNEE_USING_NAME.isOn(ContextHelper.getThreadUserContext()) && BizRoleEnum.SHIPPER.equals((Object)inRole)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_NAME, (Object)inScopeBizId));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inScopeBizId));
        }
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    protected void initializeDefaultProperties() {
        MasterBizUnit master = this.getBzuBizu();
        if (master != null) {
//            this.setBzuBic(master.getBizuBic());
//            this.setBzuCtct(master.getBizuCtct());
//            this.setBzuName(master.getBizuName());
//            this.setBzuScac(master.getBizuScac());
        } else {
            this.setBzuCtct(new ContactInfo());
        }
    }

    public static ScopedBizUnit loadByPrimaryKey(Serializable inGkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_GKEY, (Object)inGkey));
        return (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    protected static ScopedBizUnit createScopedBizUnit(String inId, BizRoleEnum inRole, MasterBizUnit inMaster) {
        ScopedBizUnit bud = new ScopedBizUnit();
        bud.setBzuId(inId);
        bud.setBzuBizu(inMaster);
        bud.setBzuRole(inRole);
        bud.initializeDefaultProperties();
        HibernateApi.getInstance().saveOrUpdate((Object)bud);
        return bud;
    }

    @Nullable
    public static IMetafieldId getContactInfoCompoundMetafieldId(IMetafieldId inMetafieldId) {
        IMetafieldId cmpmetafldid = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.BZU_CTCT, (IMetafieldId)inMetafieldId);
        return cmpmetafldid;
    }

    public IMetafieldId getScopeFieldId() {
        return IArgoRefField.BZU_SCOPE;
    }

    public IMetafieldId getRoleFieldId() {
        return IArgoRefField.BZU_ROLE;
    }

    public IMetafieldId getNaturalKeyField() {
        return IArgoRefField.BZU_ID;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        BizViolation bv = super.validateChanges(inChanges);
        bv = this.checkMandatoryRole(bv);
        return bv;
    }

    protected BizViolation validateUniqueness(FieldChanges inChanges, BizViolation inBizViolation) {
        return this.checkScopeBzuIdRoleUniquness(inBizViolation);
    }

    private BizViolation checkScopeBzuIdRoleUniquness(BizViolation inBv) {
        IMetafieldId roleFieldId = this.getRoleFieldId();
        IMetafieldId naturalKeyId = this.getNaturalKeyField();
        IMetafieldId scopeEntityKeyId = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.BZU_SCOPE, (IMetafieldId)IArgoField.ENSET_NAME);
        BizRoleEnum role = (BizRoleEnum)((Object)this.getField(roleFieldId));
        if (role != null) {
            String natkey = this.getFieldString(naturalKeyId);
            String scopeId = this.getBzuScope().getEnsetName();
            IDomainQuery dq = QueryUtils.createDomainQuery((String)this.getEntityName()).addDqPredicate(PredicateFactory.eq((IMetafieldId)roleFieldId, (Object)role.getKey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)naturalKeyId, (Object)natkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)scopeEntityKeyId, (Object)scopeId));
            Serializable pkValue = this.getPrimaryKey();
            if (pkValue != null) {
                dq.addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.pkEq((Object)pkValue)));
            }
            if (HibernateApi.getInstance().existsByDomainQuery(dq)) {
                inBv = inBv != null ? BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.NON_UNIQUE_SCOPED_ID_AND_ROLE, (BizViolation)inBv, null, (Object)natkey, (Object)role.getKey()) : BizViolation.create((IPropertyKey) IArgoPropertyKeys.NON_UNIQUE_SCOPED_ID_AND_ROLE, null, (Object)natkey, (Object)role.getKey());
            }
        }
        return inBv;
    }

    public Object getBzuBizRoleMisc() {
        return this.getBzuRole();
    }

    public void setBzuBizRoleMisc(Object inObj) {
  //      BizRoleEnum roleEnum = BizRoleEnum.getEnum((String)inObj);
   //    super.setBzuRole(roleEnum);
    }

    public Object getRepAgentTableKey() {
        return this.getBzuGkey();
    }

    private BizViolation checkMandatoryRole(BizViolation inBv) {
        BizRoleEnum role = (BizRoleEnum)((Object)this.getField(IArgoRefField.BZU_ROLE));
        if (role == null) {
            inBv = BizViolation.createFieldViolation((IPropertyKey) IArgoPropertyKeys.MANDATORY_ROLE, (BizViolation)inBv, null);
        }
        return inBv;
    }

    public Object clone() throws CloneNotSupportedException {
        ScopedBizUnit clone = (ScopedBizUnit)super.clone();
        clone.setBzuGkey(null);
        clone.setBzuRepresentions(null);
        clone.setBzuBizgrpBizUnit(null);
        return clone;
    }

    @Nullable
    public static ScopedBizUnit findEquipmentOwner(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_IS_EQ_OWNER, (Object) Boolean.TRUE));
        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        return primaryKeys == null || primaryKeys.length == 0 ? null : ScopedBizUnit.hydrate(primaryKeys[0]);
    }

    @Nullable
    public static ScopedBizUnit findEquipmentOwnerProxy(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_IS_EQ_OWNER, (Object) Boolean.TRUE));
        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        return primaryKeys == null || primaryKeys.length == 0 ? null : ScopedBizUnit.hydrate(primaryKeys[0]);
    }

    @Nullable
    public static ScopedBizUnit findEquipmentOperator(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_IS_EQ_OPERATOR, (Object) Boolean.TRUE));
        Serializable[] primaryKeys = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        return primaryKeys == null || primaryKeys.length == 0 ? null : ScopedBizUnit.hydrate(primaryKeys[0]);
    }

    public static ScopedBizUnit findEquipmentOperatorProxy(String inBizUnitId) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inBizUnitId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_IS_EQ_OPERATOR, (Object) Boolean.TRUE));
        Serializable[] bzuGkey = HibernateApi.getInstance().findPrimaryKeysByDomainQuery(dq);
        if (bzuGkey == null || bzuGkey.length == 0) {
            return null;
        }
        return (ScopedBizUnit)HibernateApi.getInstance().load(ScopedBizUnit.class, bzuGkey[0]);
    }

    public static ScopedBizUnit resolveScopedBizUnit(String inScopeBizId, String inScopeBizName, String inScopeBizCode, String inScopeBizCodeAgency, BizRoleEnum inRole) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"ScopedBizUnit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ROLE, (Object)((Object)inRole)));
        if (AGENCY_BIC.equals(inScopeBizCodeAgency)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_BIC, (Object)inScopeBizCode));
        } else if (AGENCY_SCAC.equals(inScopeBizCodeAgency)) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_SCAC, (Object)inScopeBizCode));
        } else if (inScopeBizId != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_ID, (Object)inScopeBizId));
        } else if (inScopeBizName != null) {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoRefField.BZU_NAME, (Object)inScopeBizName));
        }
        dq.setFilter(ObsoletableFilterFactory.createShowActiveFilter());
        return (ScopedBizUnit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    public ReferenceEntity findByNaturalKey(String inNaturalKey) {
        String[] s = inNaturalKey.split("/");
        String id = s[0];
        if (s.length > 1) {
   //         BizRoleEnum role = BizRoleEnum.getEnum(s[1]);
   //         return ScopedBizUnit.findScopedBizUnit(id, role);
        }
        return super.findByNaturalKey(inNaturalKey);
    }

    public String getNaturalKey() {
        StringBuilder s = new StringBuilder().append(this.getBzuId()).append("/").append(this.getBzuRole().getKey());
        return s.toString();
    }

    @Nullable
    public String getBzuNameAndRole() {
        BizRoleEnum role;
        String roleDesc;
        ArrayList<String> values = new ArrayList<String>();
        String name = this.getBzuName();
        if (name != null) {
            values.add(name);
        }
//        String string = roleDesc = (role = this.getBzuRole()) != null ? TranslationUtils.getTranslationContext((UserContext)ContextHelper.getThreadUserContext()).getMessageTranslator().getMessage(role.getDescriptionPropertyKey()) : null;
//        if (roleDesc != null) {
//            values.add(roleDesc);
//        }
        return ScopedBizUnit.formatValues(values, COMMA);
    }

    @Nullable
    private static String formatValues(List<String> inValues, String inSeperator) {
        StringBuilder label = new StringBuilder();
        for (String value : inValues) {
            if (label.length() == 0) {
                label.append(value);
                continue;
            }
            label.append(inSeperator);
            label.append(value);
        }
        return label.toString().length() > 0 ? label.toString() : null;
    }

    public boolean isBondedTruckingCompany() {
        return false;
    }

}
