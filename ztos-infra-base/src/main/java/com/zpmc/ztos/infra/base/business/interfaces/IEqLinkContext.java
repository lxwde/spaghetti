package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.enums.argo.EventEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;

import java.util.Collection;

public interface IEqLinkContext<Node extends INodeLinked<Node, Value, Key>, Value, Key> extends ILinkContext<Node, Value, Key>{
    public EqUnitRoleEnum getRole();

    public DataSourceEnum getDataSourceEnum();

    public boolean isEventRecordAllowed();

    public boolean isMatchActiveUnit();

    public EventEnum getNode1Event();

    public EventEnum getNode2Event();

 //   public NodeSwipeRule getRoleInUseValidationRule();

    public Node getReturnNode();

    public Collection<Node> getNodesInSameRole();

    public Collection<Node> getNodesInUse();

 //   public NodeSwipeRule getNodeInUseValidationRule();

    public boolean isSwipeAllowed();

    public boolean isMultipleNodeAllowed();

    public Node getMasterNode();

    public Value getMasterUnit();

    public boolean isHatchClerkValidation();

    public boolean validateLocation();

    public String getSlotOnCarriage();


}
