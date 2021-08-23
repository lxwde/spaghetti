package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.ExchangeArea;
import com.zpmc.ztos.infra.base.business.model.Lane;
import lombok.Data;

import java.util.Date;

/**
 *
 * 交换车道
 * @author yejun
 */
@Data
public class ExchangeLaneDO extends Lane {

    /**
     * 最后一次动作时间
     */
    private Date xchlnLastMoveTime;

    private Long xchlnTimeSinceLastMove;
    private Long xchlnTimeWaitingForMove;
    private Integer xchlnMoveCount;
    private Integer xchlnMovesToGo;
    private ExchangeArea xchlnXcharea;



}
