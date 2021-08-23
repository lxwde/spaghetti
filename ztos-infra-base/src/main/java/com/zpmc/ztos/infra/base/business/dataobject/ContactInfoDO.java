package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.RefCountry;
import com.zpmc.ztos.infra.base.business.model.RefState;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 联系人信息
 *
 * @author yejun
 */
@Data
public class ContactInfoDO extends DatabaseEntity
implements Serializable {

    private String ctctName;
    private String ctctAddressLine1;
    private String ctctAddressLine2;
    private String ctctAddressLine3;
    private String ctctCity;
    private String ctctMailCode;
    private String ctctTel;
    private String ctctFax;
    private String ctctEmailAddress;
    private String ctctSms;
    private String ctctWebSiteURL;
    private String ctctFtpAddress;
    private RefState ctctState;
    private RefCountry ctctCountry;


    @Override
    public void setPrimaryKey(Serializable var) {

    }

    @Override
    public Serializable getPrimaryKey() {
        return null;
    }
}
