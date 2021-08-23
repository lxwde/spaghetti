package com.zpmc.ztos.infra.base.business.predicate;

import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.interfaces.IPredicate;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class PredicateSynthesizer {
    private PredicateSynthesizer() {
    }

    @Nullable
    public static IPredicate createPredicate(UserContext inUserContext, final IMetafieldId inMetafieldId, final PredicateVerbEnum inVerb, final Object inValue) {
        if (inMetafieldId != null && HiberCache.isSyntheticField(inMetafieldId.getFieldId())) {
//            PersistenceTemplatePropagationRequired template = new PersistenceTemplatePropagationRequired(inUserContext);
//            IMessageCollector mc = template.invoke(new CarinaPersistenceCallback(){
//
//                @Override
//                protected void doInTransaction() {
//                    String methodName = inMetafieldId.getFieldId();
//                    methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
//                    methodName = "form" + methodName + "Predicate";
//                    Class[] parameters = new Class[]{IMetafieldId.class, PredicateVerbEnum.class, Object.class};
//                    try {
//                        Class c = HiberCache.getEntityClassForField(inMetafieldId.getFieldId());
//                        Method m = c.getMethod(methodName, parameters);
//                        Object[] arguments = new Object[]{inMetafieldId, inVerb, inValue};
//                        try {
//                            Object result = m.invoke(null, arguments);
//                            this.setResult(result);
//                        }
//                        catch (Exception e) {
//                            throw BizFailure.wrap(e);
//                        }
//                    }
//                    catch (Throwable throwable) {
//                        // empty catch block
//                    }
//                }
//            });
//            return (IPredicate)template.getResult();
        }
        return null;
    }
}
