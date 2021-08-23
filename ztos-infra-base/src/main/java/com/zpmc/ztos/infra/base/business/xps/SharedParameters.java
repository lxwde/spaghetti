package com.zpmc.ztos.infra.base.business.xps;

import com.zpmc.ztos.infra.base.business.dataobject.XpeSharedParametersDO;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.business.interfaces.IXpsField;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedParameters {
    public static Serializable findSharedParameters(String inParamId, Serializable inYardGkey) {
        Serializable result = null;
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"XpeSharedParametersDO").addDqPredicate(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_ID, (Object)inParamId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_YARD, (Object)inYardGkey));
        Serializable[] gKeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (gKeys.length > 0) {
            result = gKeys[0];
        }
        return result;
    }

    public static Serializable findSharedParametersForParamName(String inParamId, String inParamName, Serializable inYardGKey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"XpeSharedParametersDO").addDqPredicate(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_YARD, (Object)inYardGKey)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_ID, (Object)inParamId));
        String paramNameToUse = "";
        if (!inParamName.equals(SharedParameters.getDefaultStrategyName())) {
            paramNameToUse = inParamName;
        }
        if (paramNameToUse.isEmpty()) {
            dq.addDqPredicate((IPredicate)PredicateFactory.disjunction().add(PredicateFactory.isNull((IMetafieldId) IXpsField.SHAREDPARAMETERS_NAME)).add(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_NAME, (Object)"")));
        } else {
            dq.addDqPredicate(PredicateFactory.eq((IMetafieldId) IXpsField.SHAREDPARAMETERS_NAME, (Object)paramNameToUse));
        }
        Serializable result = null;
        Serializable[] gKeys = Roastery.getHibernateApi().findPrimaryKeysByDomainQuery(dq);
        if (gKeys.length > 0) {
            result = gKeys[0];
        }
        return result;
    }

    public static List<XpeSharedParametersParameters> findSharedParameterParms(String inParamId, Serializable inYardGkey) {
        Serializable sharedParametersGkey = SharedParameters.findSharedParametersForParamName(inParamId, SharedParameters.getDefaultStrategyName(), inYardGkey);
        if (sharedParametersGkey != null) {
            XpeSharedParametersDO params = (XpeSharedParametersDO)Roastery.getHibernateApi().load(XpeSharedParametersDO.class, sharedParametersGkey);
            return params.getSharedparametersParameters();
        }
        return null;
    }

    public static List<XpeSharedParametersParameters> findSharedParametersParmsForStrategy(String inParamId, String inEDStrategy, Serializable inYardGkey) {
        Serializable sharedParametersGkey = SharedParameters.findSharedParametersForParamName(inParamId, inEDStrategy, inYardGkey);
        if (sharedParametersGkey != null) {
            XpeSharedParametersDO params = (XpeSharedParametersDO)Roastery.getHibernateApi().load(XpeSharedParametersDO.class, sharedParametersGkey);
            return params.getSharedparametersParameters();
        }
        return null;
    }

    @Nullable
    public static Map<String, String> getSharedParameterParmsAsMap(String inParamId, String inEDStrategy, Serializable inYardGkey) {
        List<XpeSharedParametersParameters> parameterParms;
        HashMap<String, String> paramMap = null;
        String strategyName = "";
        if (!inEDStrategy.equals(SharedParameters.getDefaultStrategyName())) {
            strategyName = inEDStrategy;
        }
        if ((parameterParms = SharedParameters.findSharedParametersParmsForStrategy(inParamId, strategyName, inYardGkey)) != null) {
            paramMap = new HashMap<String, String>();
            for (int i = 0; i < parameterParms.size(); i += 2) {
                XpeSharedParametersParameters keyChildParam = parameterParms.get(i);
                XpeSharedParametersParameters valueChildParam = parameterParms.get(i + 1);
                if (valueChildParam != null) {
                    paramMap.put(keyChildParam.getValue(), valueChildParam.getValue());
                    continue;
                }
                paramMap.put(keyChildParam.getValue(), null);
            }
        }
        return paramMap;
    }

    public static String getDefaultStrategyName() {
        return "Default";
    }

}
