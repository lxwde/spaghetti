package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.LogicalEntityEnum;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Date;
import java.util.List;

public interface IServiceable extends ILogicalEntity {

    public static final Logger DEV_LOGGER = Logger.getLogger(IServiceable.class);

    public List getGuardians();

    @Nullable
    public String getLovKeyNameForPathToGuardian(String var1);

    @Nullable
    public String getLovKeyNameForLogicalEntityType(LogicalEntityEnum var1);

    public Boolean skipEventRecording();

    public void postEventCreation(IEvent var1);

    @Nullable
    default public IEvent recordEvent(IEventType iEventType, FieldChanges fieldChanges, String string, Date date) {
        IServicesManager servicesManager = (IServicesManager) Roastery.getBean("servicesManager");
        IEvent iEvent = null;
        try {
            iEvent = servicesManager.recordEvent(iEventType, string, null, null, this, fieldChanges, date);
        }
        catch (Exception exception) {
            DEV_LOGGER.error((Object)("problem recording event of type " + iEventType.getId() + " on " + this + " : " + exception + " (ignored)"));
        }
        return iEvent;
    }
}
