package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.enums.argo.BizRoleEnum;
import com.zpmc.ztos.infra.base.business.model.ScopedBizUnit;

import java.io.Serializable;
import java.util.*;

public class BizGkeysByRole implements Serializable {
    Map _roleList = new HashMap();

    public boolean isEmpty() {
        return this._roleList.isEmpty();
    }

    public boolean isNotEmpty() {
        return !this._roleList.isEmpty();
    }

    public Object[] getGkeyArrayForRole(BizRoleEnum inRole) {
        Object[] result = null;
        HashSet roleGkeys = (HashSet)this._roleList.get((Object)inRole);
        if (roleGkeys != null) {
            result = roleGkeys.toArray();
        }
        return result;
    }

    public Object[] getGkeyArrayForAllRoles() {
        Collection allGkeys = this._roleList.values();
        return allGkeys.toArray();
    }

    public boolean hasAffiliations(BizRoleEnum inRole) {
        boolean hasSome = false;
        HashSet roleGkeys = (HashSet)this._roleList.get((Object)inRole);
        if (roleGkeys != null && roleGkeys.size() > 0) {
            hasSome = true;
        }
        return hasSome;
    }

    public void addBizUnit(ScopedBizUnit inBizUnit) {
        HashSet<Long> roleGkeys = (HashSet<Long>)this._roleList.get((Object)inBizUnit.getBzuRole());
        if (roleGkeys == null) {
            roleGkeys = new HashSet<Long>();
        }
        roleGkeys.add(inBizUnit.getBzuGkey());
        this._roleList.put(inBizUnit.getBzuRole(), roleGkeys);
    }

    public void addBizRoleGkey(BizRoleEnum inBizRoleENum, Long inGkey) {
        HashSet<Long> roleGkeys = (HashSet<Long>)this._roleList.get((Object)inBizRoleENum);
        if (roleGkeys == null) {
            roleGkeys = new HashSet<Long>();
        }
        roleGkeys.add(inGkey);
        this._roleList.put(inBizRoleENum, roleGkeys);
    }

    public boolean containsBizUnit(ScopedBizUnit inBizUnit) {
        Long bizGkey = inBizUnit.getBzuGkey();
        return this.containsBizUnit(bizGkey);
    }

    public boolean containsBizUnit(Serializable inBizUnitGkey) {
        for (Object roleGkeys : this._roleList.values()) {
            for (Object gkey : (HashSet)roleGkeys) {
                if (!gkey.equals(inBizUnitGkey)) continue;
                return true;
            }
        }
        return false;
    }

    public Set getAllBizRoles() {
        return this._roleList.keySet();
    }
}
