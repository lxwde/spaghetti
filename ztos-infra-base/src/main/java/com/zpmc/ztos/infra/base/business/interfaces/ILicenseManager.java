package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.model.FeatureIdFactory;
import com.zpmc.ztos.infra.base.common.model.UserContext;

public interface ILicenseManager {
    public static final String BEAN_ID = "licenseManager";
    public static final IFeatureId ALL_FEATURES_ID = FeatureIdFactory.valueOf("ALL_FEATURES");
    public static final IFeatureId CORE_FEATURES_ID = FeatureIdFactory.valueOf("CORE_FEAT");
    public static final IFeatureId SYSTEM_FOUNDATION_ID = FeatureIdFactory.valueOf("SYSTEM_FOUNDATION");

//    public IActivatedLicense getEffectiveLicense(@NotNull ScopeCoordinates var1);

    public boolean hasFeature(@NotNull UserContext var1, @NotNull IFeatureId var2);

    public void filterLicensedPrivileges(@NotNull UserContext var1);

//    public ConfigAttributeDefinition filterByFeature(Neo4jProperties.Authentication var1, ConfigAttributeDefinition var2);

//    public boolean isPrivilegeLicensed(@NotNull ScopeCoordinates var1, @NotNull IPrivilegeId var2, boolean var3);

    public void refresh();

//    public Collection<IPrivilege> getLicensedPrivileges(ScopeCoordinates var1);

}
