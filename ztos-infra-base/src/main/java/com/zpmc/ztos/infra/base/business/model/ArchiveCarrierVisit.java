package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.dataobject.ArchiveCarrierVisitDO;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.util.Date;

public class ArchiveCarrierVisit extends ArchiveCarrierVisitDO {
    public void setArCvOperator(ScopedBizUnit inCvOperator) {
        if (inCvOperator != null) {
            this.setArCvOperatorId(inCvOperator.getBzuId());
            this.setArCvOperatorGkey(inCvOperator.getBzuGkey());
        }
    }

    public void onArchive(DatabaseEntity inEntity) {
        this.setArCvArchiveDate(new Date());
    }
}
