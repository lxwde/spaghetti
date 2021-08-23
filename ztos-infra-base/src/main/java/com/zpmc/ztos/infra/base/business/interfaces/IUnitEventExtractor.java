package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

import java.io.Serializable;

public interface IUnitEventExtractor {
    public static final String BEAN_ID = "unitEventExtractor";

    public int extractUnitEvents(Serializable[] var1) throws BizViolation;

  //  public void extractAllBillableEvent(JobExecutionContext var1);
}
