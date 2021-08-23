package com.zpmc.ztos.infra.base.common.database;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IMessageCollector;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.TransactionParms;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public class PersistenceTemplatePropagationRequired extends PersistenceTemplate {
    public PersistenceTemplatePropagationRequired(UserContext inUserContext) {
        super(inUserContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @NotNull
    public IMessageCollector invoke(CarinaPersistenceCallback inCallback) {
        if (HibernateApi.getInstance().getCurrentSession() != null) {
            IMessageCollector collector = null;
            try {
                collector = TransactionParms.getBoundParms().getMessageCollector();
                inCallback.doInTransaction();
            }
            catch (Exception e) {
                if (collector == null) {
                    collector = MessageCollectorFactory.createMessageCollector();
                    collector.registerExceptions(BizFailure.createProgrammingFailure("Programming Error! Session exists without transaction parms which could only happen if you are rolling your own sessions."));
                }
                collector.registerExceptions(e);
            }
            finally {
                if (inCallback.getResult() != null && this.getResult() == null) {
                    this.setResult(inCallback.getResult());
                }
            }
            return collector;
        }
        return super.invoke(inCallback);
    }
}
