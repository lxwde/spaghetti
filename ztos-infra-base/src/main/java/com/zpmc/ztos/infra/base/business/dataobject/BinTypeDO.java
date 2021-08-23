package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.contexts.BinContext;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * bin类型
 * @author yejun
 */
@Data
public class BinTypeDO extends DatabaseEntity
implements Serializable {

    private Long btpGkey;
    private String btpId;
    private String btpDescription;
    private Boolean btpSystemDefined;
    private Long btpLevelRestriction;
    private BinContext btpContext;

    public Serializable getPrimaryKey() {
        return this.getBtpGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getBtpGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof BinTypeDO)) {
            return false;
        }
        BinTypeDO that = (BinTypeDO)other;
        return ((Object)id).equals(that.getBtpGkey());
    }

    public int hashCode() {
        Long id = this.getBtpGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getBtpGkey() {
        return this.btpGkey;
    }

    protected void setBtpGkey(Long btpGkey) {
        this.btpGkey = btpGkey;
    }

    public String getBtpId() {
        return this.btpId;
    }

    protected void setBtpId(String btpId) {
        this.btpId = btpId;
    }

    public String getBtpDescription() {
        return this.btpDescription;
    }

    protected void setBtpDescription(String btpDescription) {
        this.btpDescription = btpDescription;
    }

    public Boolean getBtpSystemDefined() {
        return this.btpSystemDefined;
    }

    protected void setBtpSystemDefined(Boolean btpSystemDefined) {
        this.btpSystemDefined = btpSystemDefined;
    }

    public Long getBtpLevelRestriction() {
        return this.btpLevelRestriction;
    }

    protected void setBtpLevelRestriction(Long btpLevelRestriction) {
        this.btpLevelRestriction = btpLevelRestriction;
    }

    public BinContext getBtpContext() {
        return this.btpContext;
    }

    protected void setBtpContext(BinContext btpContext) {
        this.btpContext = btpContext;
    }

}
