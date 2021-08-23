package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Date;

public interface IXpeEcEventAccessor {
    public Long getEceventGkey();

    public Long getEceventPkey();

    public Date getEceventTimestamp();

    public Long getEceventType();

    public Long getEceventCheId();

    public String getEceventCheName();

    public String getEceventOperatorName();

    public Long getEceventSubType();

    public String getEceventTypeDescription();

    public Long getEceventFromCheIdName();

    public Long getEceventToCheIdName();

    public String getEceventUnitIdName();

    public String getEceventPowName();

    public String getEceventPoolName();

    public String getEceventWorkQueue();

    public Long getEceventTravelDistance();

    public String getEceventMoveKind();

    public Boolean getEceventIsTwinMove();

    public Long getEceventStartDistance();

    public Long getEceventWorkAssignmentGkey();

    public String getEceventWorkAssignmentId();

    public String getEceventUnitReference();

    public String getEceventTransactionId();

    public Long getEceventXpsLocType();

    public String getEceventLocId();

    public String getEceventLocSlot();

    public Long getEceventXpsUnladenLocType();

    public String getEceventUnladenLocId();

    public String getEceventUnladenLocSlot();

    public Long getEceventXpsLadenLocType();

    public String getEceventLadenLocId();

    public String getEceventLadenLocSlot();
}
