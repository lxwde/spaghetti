package com.zpmc.ztos.infra.base.business.interfaces;

import java.io.Serializable;
import java.util.Date;

public interface IBatch {
    public Serializable getGkey();

    public Long getNbr();

    public Long getSegmentCount();

    public Long getTransactionCount();

    public String getInterchangeNbr();

    public String getEdiSessName();

    public String getEdibatchCreator();

    public String getEdibatchChanger();

    public Date getEdibatchCreated();

    public Date getEdibatchChanged();
}
