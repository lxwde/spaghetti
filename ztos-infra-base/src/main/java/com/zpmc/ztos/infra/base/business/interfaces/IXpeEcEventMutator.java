package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.Date;

public interface IXpeEcEventMutator {
    public void setEceventGkey(Long var1);

    public void setEceventPkey(Long var1);

    public void setEceventTimestamp(Date var1);

    public void setEceventType(Long var1);

    public void setEceventCheId(Long var1);

    public void setEceventCheName(String var1);

    public void setEceventOperatorName(String var1);

    public void setEceventSubType(Long var1);

    public void setEceventTypeDescription(String var1);

    public void setEceventFromCheIdName(Long var1);

    public void setEceventToCheIdName(Long var1);

    public void setEceventUnitIdName(String var1);

    public void setEceventPowName(String var1);

    public void setEceventPoolName(String var1);

    public void setEceventWorkQueue(String var1);

    public void setEceventTravelDistance(Long var1);

    public void setEceventMoveKind(String var1);

    public void setEceventIsTwinMove(Boolean var1);

    public void setEceventStartDistance(Long var1);

    public void setEceventWorkAssignmentGkey(Long var1);

    public void setEceventWorkAssignmentId(String var1);

    public void setEceventUnitReference(String var1);

    public void setEceventTransactionId(String var1);

    public void setEceventXpsLocType(Long var1);

    public void setEceventLocId(String var1);

    public void setEceventLocSlot(String var1);

    public void setEceventXpsUnladenLocType(Long var1);

    public void setEceventUnladenLocId(String var1);

    public void setEceventUnladenLocSlot(String var1);

    public void setEceventXpsLadenLocType(Long var1);

    public void setEceventLadenLocId(String var1);

    public void setEceventLadenLocSlot(String var1);
}
