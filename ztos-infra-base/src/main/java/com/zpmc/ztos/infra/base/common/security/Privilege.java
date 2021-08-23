package com.zpmc.ztos.infra.base.common.security;

import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.FeatureIdFactory;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;
import com.zpmc.ztos.infra.base.common.model.ScopeCoordinates;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Privilege implements IMutablePrivilege {

    private ScopeCoordinates _scopeCoordinates;
    private final String _privilegeName;
    private String _moduleId;
    private String _initialVersion;
    private List<IFeatureId> _features;
    private IPropertyKey _descriptionKey;
    private List<IDetailedDiagnostics> _source = new ArrayList<IDetailedDiagnostics>();
    private IPropertyKey _nameKey;
    private IPrivilegeId _privilegeId;
    private String _debugHistory;

    public Privilege(String inPrivilegeName) {
        this._privilegeName = inPrivilegeName;
        this._privilegeId = PrivilegeIdFactory.valueOf(inPrivilegeName);
        this._descriptionKey = PropertyKeyFactory.valueOf("privs.description." + this._privilegeName);
        this._nameKey = PropertyKeyFactory.valueOf("privs.name." + this._privilegeName);
    }

    @Deprecated
    protected Privilege(String inPrivilegeName, String inModuleId, String inInitialVersion, String inFeatureId) {
        this(inPrivilegeName);
        this._moduleId = inModuleId;
        this._initialVersion = inInitialVersion;
        this._features = new ArrayList<IFeatureId>(1);
        if (!StringUtils.isBlank((String)inFeatureId)) {
            this._features.add(FeatureIdFactory.valueOf(inFeatureId));
        }
    }

    protected Privilege(String inPrivilegeName, String inModuleId, String inInitialVersion, List<IFeatureId> inFeatureIds) {
        this(inPrivilegeName);
        this._moduleId = inModuleId;
        this._initialVersion = inInitialVersion;
        this._features = inFeatureIds;
    }

    @Override
    public List<IFeatureId> getFeatureIds() {
        return this._features;
    }

    @Override
    public boolean hasFeature(IFeatureId inFeatureId) {
        return this._features.contains(inFeatureId);
    }

    @Override
    public boolean hasNoFeatures() {
        return this._features == null || this._features.isEmpty();
    }

    @Override
    public String getModuleId() {
        return this._moduleId;
    }

    @Override
    public IPropertyKey getDescriptionKey() {
        return this._descriptionKey;
    }

    @Override
    public IPropertyKey getNameKey() {
        return this._nameKey;
    }

    @Override
    public String getInitialVersion() {
        return this._initialVersion;
    }

    @Override
    public boolean isAllowed(UserContext inUserContext) {
        IDecisionManager accessDecisionManager = (IDecisionManager) PortalApplicationContext.getBean("roleDecisionManager");
//        ConfigAttributeDefinition configAttribute = new ConfigAttributeDefinition((ConfigAttribute)this);
//        return accessDecisionManager.isAllowed(inUserContext, (Object)configAttribute, configAttribute);
        return false;
    }

    public String getAttribute() {
        return this._privilegeName;
    }

    @Override
    public IPrivilegeId getPrivilegeId() {
        return this._privilegeId;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Privilege");
        buf.append("{_privilegeName=").append(this._privilegeName);
        buf.append(",_moduleId=").append(this._moduleId);
        buf.append(",_initialVersion=").append(this._initialVersion);
        buf.append(",_descriptionKey=").append(this._descriptionKey);
        buf.append(",_features=").append(this.featuresToString());
        buf.append('}');
        return buf.toString();
    }

    private String featuresToString() {
        if (this._features == null || this._features.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (IFeatureId feature : this._features) {
            sb.append(feature.toString()).append(',');
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Nullable
    public static IPrivilege returnPrivilege(String inPrivilegeName) throws IllegalArgumentException {
        if (inPrivilegeName == null) {
            return null;
        }
        return new Privilege(inPrivilegeName);
    }

    @Override
    public void setDescriptionKey(IPropertyKey inDescriptionKey) {
        this._descriptionKey = inDescriptionKey;
    }

    @Override
    public void setNameKey(IPropertyKey inNameKey) {
        this._nameKey = inNameKey;
    }

    @Override
    public ScopeCoordinates getScopeCoordinates() {
        return this._scopeCoordinates;
    }

    @Override
    public void setScopeCoordinates(ScopeCoordinates inScopeCoordinates) {
        this._scopeCoordinates = inScopeCoordinates;
    }

    @Override
    public void setFeatureIds(List<IFeatureId> inFeatureIds) {
        this._features = inFeatureIds;
    }

    @Override
    public String getDebugHistory() {
        return this._debugHistory;
    }

    @Override
    public void setDebugHistory(String inDebugHistory) {
        this._debugHistory = inDebugHistory;
    }

    @Override
    public String getDetailedDiagnostics() {
        StringBuilder buf = new StringBuilder();
        if (!this._source.isEmpty()) {
            for (IDetailedDiagnostics obj : this._source) {
                buf.append("\n" + obj.getDetailedDiagnostics());
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    @Override
    public void appendSource(IDetailedDiagnostics inSource) {
        this._source.add(inSource);
    }
}
