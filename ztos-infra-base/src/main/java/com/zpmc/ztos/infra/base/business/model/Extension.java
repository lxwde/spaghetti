package com.zpmc.ztos.infra.base.business.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.ExtensionDO;
import com.zpmc.ztos.infra.base.business.enums.framework.CustomizationComponentContentTypeEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.ExtensionLanguageEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FrameworkPlatformComponentTypeEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.type.FrameworkExtensionTypes;
import com.zpmc.ztos.infra.base.common.type.FuzzyBoolean;
import com.zpmc.ztos.infra.base.common.utils.ExtensionBeanUtils;
import com.zpmc.ztos.infra.base.common.utils.ScopedQueryUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Extension extends ExtensionDO implements IExtension, IFlexibleScope, IEntityUniquenessAware, ISystemSeedable, IPlatformComponent {
    public static final IMetafieldId[] UNIQUE_FIELD_IDS = new IMetafieldId[]{IExtensionField.EXT_NAME, IExtensionField.EXT_SCOPE_LEVEL, IExtensionField.EXT_SCOPE_GKEY};
    private static final Logger LOGGER = Logger.getLogger(Extension.class);

    public Extension() {
        this.setExtLang(ExtensionLanguageEnum.GROOVY);
        this.setExtSysSeeded(Boolean.FALSE);
        this.setExtEnabled(Boolean.TRUE);
    }

    @Override
    public IExtensionType getExtensionType() {
        IExtensionTypeManager typeManager = ExtensionBeanUtils.getExtensionTypeManager();
        return typeManager.findExtension(ExtensionTypeIdFactory.valueOf(this.getExtType()));
    }

    @Override
    public String getExtensionName() {
        return this.getExtName();
    }

    @Override
    public boolean isOfLanguage(ExtensionLanguageEnum inEnum) {
        return this.getExtLang().equals(inEnum);
    }

    @Override
    public boolean isEnabled() {
        return this.getExtEnabled();
    }

    @Override
    @NotNull
    public ExtensionLanguageEnum getLanguage() {
        return this.getExtLang();
    }

    @Override
    @NotNull
    public Long getInternalVersion() {
        return this.getExtVersion();
    }

    @Override
    public byte[] getContents() {
        return this.getExtContents();
    }

    @Override
    public Long getUniqueKey() {
        return this.getExtGkey();
    }

    public Object getExtScopeLevelName() {
        IEntityScoper scoper = (IEntityScoper) Roastery.getBean("entityScoper");
        return scoper.getScopeEnum(this.getScopeLevel());
    }

    @Nullable
    public String getExtScopeName() {
        return ScopedQueryUtils.getScopeName(this.getScopeLevel(), this.getScopeKey());
    }

    @Override
    public int getScopeLevel() {
        return this.getExtScopeLevel().intValue();
    }

    @Override
    public Serializable getScopeKey() {
        return this.getExtScopeGkey();
    }

    @Override
    @NotNull
    public IMetafieldId getScopeKeyFieldId() {
        return IExtensionField.EXT_SCOPE_GKEY;
    }

    @Override
    @NotNull
    public IMetafieldId getScopeLevelFieldId() {
        return IExtensionField.EXT_SCOPE_LEVEL;
    }

    public boolean isExtensionInUse() {
        IDomainQuery dq = QueryUtils.createDomainQuery("ExtensionInjection").addDqPredicate(PredicateFactory.eq(IExtensionField.EXTINJ_EXTENSION, this.getExtGkey()));
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().existsByDomainQuery(dq);
    }

    @Override
    public void preProcessInsert(FieldChanges inOutMoreChanges) {
        inOutMoreChanges.setFieldChange(IExtensionField.EXT_VERSION, 1L);
    }

    @Override
    public void preProcessUpdate(FieldChanges inChanges, FieldChanges inOutMoreChanges) {
        inOutMoreChanges.setFieldChange(IExtensionField.EXT_VERSION, this.getExtVersion() + 1L);
    }

    @Override
    public void preProcessInsertOrUpdate(FieldChanges inOutMoreChanges) {
        if (this.getExtContents() != null) {
            String contentString = new String(this.getExtContents());
            if (contentString.length() > 4000) {
                contentString = contentString.substring(0, 4000);
            }
            inOutMoreChanges.setFieldChange(IExtensionField.EXT_SEARCHABLE_CONTENTS, contentString);
        }
    }

    @Override
    public BizViolation validateChanges(FieldChanges inChanges) {
        boolean isBadScope;
        BizViolation violation = super.validateChanges(inChanges);
        Long scopeLevel = this.getExtScopeLevel();
        if (ScopeCoordinates.GLOBAL_LEVEL.equals(scopeLevel)) {
            isBadScope = this.getExtScopeGkey() != null;
        } else {
            boolean bl = isBadScope = this.getExtScopeGkey() == null || scopeLevel == null;
        }
        if (isBadScope) {
            violation = BizViolation.createFieldViolation(IFrameworkPropertyKeys.CRUD__FIELD_REQUIRED, violation, IExtensionField.EXT_SCOPE_GKEY);
        } else if (!ScopeCoordinates.GLOBAL_LEVEL.equals(scopeLevel)) {
            boolean isIllegalScope = false;
            ApplicationModuleSettings appSettings = (ApplicationModuleSettings) PortalApplicationContext.getBean("appModuleSettings");
            if (appSettings.isGloballyScoped() == FuzzyBoolean.TRUE) {
                isIllegalScope = true;
            } else {
                IEntityScoper scoper = (IEntityScoper)Roastery.getBean("entityScoper");
                if (scopeLevel < ScopeCoordinates.GLOBAL_LEVEL || scopeLevel > scoper.getMostSpecificSupportedScopeEnum().getScopeLevel()) {
                    isIllegalScope = true;
                }
            }
            if (isIllegalScope) {
                violation = BizViolation.createFieldViolation(IFrameworkPropertyKeys.FAILURE__INVALID_SCOPE_LEVEL, violation, IExtensionField.EXT_SCOPE_LEVEL, scopeLevel);
            }
        }
        violation = this.validateUniqueKeys(violation);
        ICodeExtensionValidator validator = ExtensionBeanUtils.getCodeValidator();
        try {
            Class clazz = validator.validate(this);
            if (clazz != null) {
                String className = clazz.getSimpleName();
                inChanges.setFieldChange(IExtensionField.EXT_CLASS_NAME, className);
                if (!className.equals(this.getExtName())) {
                    if (this.getExtSysSeeded().booleanValue()) {
                        violation = BizViolation.create(IExtensionPropertyKeys.ERROR__EXTENSION_LIBRARY_NAME, violation, this.getExtName(), className);
                    } else {
                        LOGGER.info((Object)("Extension with name '" + this.getExtName() + "' has a class name that is different, '" + className + "'. Good practice is that both should be identical."));
                    }
                }
            }
        }
        catch (BizViolation newViolation) {
            if (violation != null) {
                violation.appendToChain(newViolation);
            }
            violation = newViolation;
        }
        return violation;
    }

    @Override
    public BizViolation validateDeletion() {
        if (this.isExtensionInUse()) {
            return BizViolation.create(IExtensionPropertyKeys.ERROR__EXTENSION_IN_USE, null, this.getExtName());
        }
        return super.validateDeletion();
    }

    @Nullable
    public static Extension findExtension(String inExtensionName, ScopeCoordinates inCoordinates) {
        int scopeLevel = inCoordinates.getMostSpecificScopeLevel();
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension").addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_NAME, inExtensionName)).addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_SCOPE_LEVEL, scopeLevel));
        if (!inCoordinates.isScopeGlobal()) {
            dq.addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_SCOPE_GKEY, String.valueOf(inCoordinates.getScopeLevelCoord(scopeLevel))));
        }
        dq.setScopingEnabled(false);
        return (Extension)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static IExtension findLibrary(@NotNull UserContext inUserContext, String inLibraryName) throws BizViolation {
        Extension extension = null;
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension").addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_NAME, inLibraryName)).addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_TYPE, FrameworkExtensionTypes.LIBRARY.getTypeId())).addDqPredicate(ScopedQueryUtils.getEntityPredicateForEffectiveScope(inUserContext.getScopeCoordinate(), IExtensionField.EXT_SCOPE_LEVEL, IExtensionField.EXT_SCOPE_GKEY));
        dq.addDqOrdering(Ordering.desc(IExtensionField.EXT_SCOPE_LEVEL));
        dq.setScopingEnabled(false);
        List matches = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (!matches.isEmpty()) {
            for (Object match : matches) {
                if (!((Extension)match).isEnabled()) continue;
                extension = (Extension)match;
                break;
            }
            if (extension == null) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info((Object)("All LIBRARY extensions '" + inLibraryName + "' within the user effective path are disabled."));
                }
                throw BizViolation.create(IExtensionPropertyKeys.ERROR__EXTENSION_LIBRARY_DISABLED, null, inLibraryName);
            }
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("LIBRARY extension " + inLibraryName + " does not exist."));
            }
            throw BizViolation.create(IExtensionPropertyKeys.ERROR__EXTENSION_LIBRARY_NOT_FOUND, null, inLibraryName);
        }
        return extension;
    }

    @Nullable
    public static List<Extension> findExtensions(String inExtensionName) {
        IDomainQuery dq = QueryUtils.createDomainQuery("Extension").addDqPredicate(PredicateFactory.eq(IExtensionField.EXT_NAME, inExtensionName));
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    public static List<Extension> getScopedExtensionsForType(UserContext inUserContext, String inExtensionType) {
        IDomainQuery dq = ScopedQueryUtils.getEntityQueryForBroadestAllowedScope(inUserContext, "Extension", IExtensionField.EXT_SCOPE_LEVEL, IExtensionField.EXT_SCOPE_GKEY);
        dq.addDqPredicate(PredicateFactory.conjunction().add(PredicateFactory.eq(IExtensionField.EXT_TYPE, inExtensionType)));
        dq.setScopingEnabled(false);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }

    @Deprecated
    @NotNull
    public static Extension createOrUpdate(String inExtensionName, ExtensionLanguageEnum inLanguage, String inExtensionType, byte[] inBody, ScopeCoordinates inCoordinates) {
        return Extension.createOrUpdateAtHighLevel(inExtensionName, inLanguage, inExtensionType, inBody, inCoordinates);
    }

    @NotNull
    public static Extension createOrUpdateAtHighLevel(String inExtensionName, ExtensionLanguageEnum inLanguage, String inExtensionType, byte[] inBody, ScopeCoordinates inCoordinates) {
        int scope = inCoordinates.getMinScopeLevel();
        return Extension.createOrUpdateAtScope(inExtensionName, inLanguage, inExtensionType, inBody, inCoordinates, scope);
    }

    @NotNull
    public static Extension createOrUpdateAtGivenScope(String inExtensionName, ExtensionLanguageEnum inLanguage, String inExtensionType, byte[] inBody, ScopeCoordinates inCoordinates) {
        int scope = inCoordinates.getMaxScopeLevel();
        return Extension.createOrUpdateAtScope(inExtensionName, inLanguage, inExtensionType, inBody, inCoordinates, scope);
    }

    private static Extension createOrUpdateAtScope(String inExtensionName, ExtensionLanguageEnum inLanguage, String inExtensionType, byte[] inBody, ScopeCoordinates inCoordinates, int inScope) {
        Extension extension = Extension.findExtension(inExtensionName, inCoordinates);
        boolean isNewExtension = false;
        if (extension == null) {
            isNewExtension = true;
            extension = new Extension();
            extension.setExtName(inExtensionName);
            extension.setExtType(inExtensionType);
            extension.setExtLang(inLanguage);
        }
        if (inCoordinates.isScopeGlobal()) {
            extension.setExtScopeLevel(ScopeCoordinates.GLOBAL_LEVEL);
        } else {
            extension.setExtScopeLevel(Long.valueOf(inScope));
            extension.setExtScopeGkey(String.valueOf(inCoordinates.getScopeLevelCoord(inScope)));
        }
        extension.setExtContents(inBody);
        if (isNewExtension) {
            HibernateApi.getInstance().save(extension);
        }
        return extension;
    }

    @Override
    public IMetafieldId[] getUniqueKeyFieldIds() {
        return UNIQUE_FIELD_IDS;
    }

    @Override
    public String getHumanReadableKey() {
        return this.getExtensionName();
    }

    @Override
    public IMetafieldId getPrimaryBusinessKey() {
        return IExtensionField.EXT_NAME;
    }

    @Override
    public boolean isSystemSeeded() {
        return this.getExtSysSeeded();
    }

    @Override
    public String getTypeId() {
        return FrameworkPlatformComponentTypeEnum.CODE_EXTENSION.name();
    }

    @Override
    public String getComponentBusinessName() {
        return this.getExtName();
    }

    @Override
    public String getComponentDesc() {
        return this.getExtDescription();
    }

    @Override
    public String getComponentContent() {
        return this.getExtSearchableContents();
    }

    @Override
    public CustomizationComponentContentTypeEnum getComponentContentType() {
        return CustomizationComponentContentTypeEnum.GROOVY;
    }

    @Override
    public Date getCreated() {
        return this.getExtCreated();
    }

    @Override
    public String getCreator() {
        return this.getExtCreator();
    }

    @Override
    public Date getChanged() {
        return this.getExtChanged();
    }

    @Override
    public String getChanger() {
        return this.getExtChanger();
    }

    @Override
    public String getComponentSubType() {
        return this.getExtType();
    }

    @Override
    @Nullable
    public String getScopeId() {
        return this.getExtScopeName();
    }
}
