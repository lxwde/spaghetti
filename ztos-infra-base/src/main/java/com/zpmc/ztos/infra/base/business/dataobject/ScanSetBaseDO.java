package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;


/**
 *
 * 扫描设备
 * @author yejun
 */
@Data
public abstract class ScanSetBaseDO extends DatabaseEntity implements Serializable {
    private Long scanGkey;
    private Long scanSetId;

//    @Override
//    public Serializable getPrimaryKey() {
//        return this.getScanGkey();
//    }
}
