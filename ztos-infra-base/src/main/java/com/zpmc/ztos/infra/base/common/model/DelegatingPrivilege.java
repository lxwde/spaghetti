package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IFeatureId;
import com.zpmc.ztos.infra.base.business.interfaces.IPrivilege;
import com.zpmc.ztos.infra.base.business.interfaces.IPrivilegeId;
import com.zpmc.ztos.infra.base.business.interfaces.IPrivilegeManager;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.security.Privilege;
import com.zpmc.ztos.infra.base.common.security.PrivilegeIdFactory;

import java.util.List;

public class DelegatingPrivilege extends Privilege {

    private IPrivilege _backingPrivilege;

    public DelegatingPrivilege(String inPrivilegeName) {
        super(inPrivilegeName);
    }

    @Override
    public String getModuleId() {
        return this.getBackingPrivilege().getModuleId();
    }

    @Override
    public String getInitialVersion() {
        return this.getBackingPrivilege().getInitialVersion();
    }

    @Override
    public List<IFeatureId> getFeatureIds() {
        return this.getBackingPrivilege().getFeatureIds();
    }

    @Override
    public boolean hasFeature(IFeatureId inFeatureId) {
        return this.getBackingPrivilege().hasFeature(inFeatureId);
    }

    @Override
    public boolean hasNoFeatures() {
        return this.getBackingPrivilege().hasNoFeatures();
    }

    private IPrivilege getBackingPrivilege() {
        if (this._backingPrivilege == null) {
            IPrivilegeManager manager = (IPrivilegeManager) Roastery.getBean("privilegeManager");
            IPrivilege privilege = manager.findPrivilege(this.getPrivilegeId());
            if (privilege == null) {
                throw BizFailure.createProgrammingFailure("Privs file not registered with IPrivilegeManager");
            }
            this._backingPrivilege = privilege;
        }
        return this._backingPrivilege;
    }

    @Override
    public IPrivilegeId getPrivilegeId() {
        return PrivilegeIdFactory.valueOf(this.getAttribute());
    }

    public boolean equals(Object inObj) {
        if (inObj == this.getBackingPrivilege()) {
            return true;
        }
        return super.equals(inObj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getDetailedDiagnostics() {
        return this.getBackingPrivilege().getDetailedDiagnostics();
    }
}
