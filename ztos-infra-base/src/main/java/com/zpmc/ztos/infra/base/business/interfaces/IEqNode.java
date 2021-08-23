package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.enums.argo.EquipClassEnum;
import com.zpmc.ztos.infra.base.business.enums.inventory.EqUnitRoleEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;

import java.util.Collection;

public interface IEqNode<Node extends IEqNode<Node, Value, Key>, Value, Key>
extends INodeLinked<Node, Value, Key> {
    public EqUnitRoleEnum getRole();

    public EquipClassEnum getEqClass();

    public Node attach(Node var1, EqUnitRoleEnum var2) throws BizViolation;

    public Node attach(Node var1, EqUnitRoleEnum var2, boolean var3, boolean var4, boolean var5, boolean var6) throws BizViolation;

    public Node attach(Node var1, EqUnitRoleEnum var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, String var10) throws BizViolation;

    public Collection<Node> getNodesInRole(EqUnitRoleEnum... var1);

    public Collection<Node> getActiveNodesInRole(EqUnitRoleEnum... var1);

    public void detach() throws BizViolation;

    public void detach(IUnlinkContext<Node, Value, Key> var1) throws BizViolation;

    public void detach(String var1, boolean var2, boolean var3) throws BizViolation;
}
