package com.zpmc.ztos.infra.base.common.enums;

import com.zpmc.ztos.infra.base.business.interfaces.IAtom;
import com.zpmc.ztos.infra.base.business.interfaces.IIconKey;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.enums.Enum;

import java.lang.reflect.Method;

public class AtomizedEnum extends Enum implements IAtom {

    private String _key;
    private String _descriptionResKey;
    private String _codeResKey;
    private String _backgroundColor;
    private String _foregroundColor;
    private String _iconIdPath;
    private static final Logger LOGGER = Logger.getLogger(AtomizedEnum.class);

    protected AtomizedEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor) {
        this(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, null);
    }

    protected AtomizedEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, @Nullable String inIconIdPath) {
        super(inKey);
        this._key = inKey;
        this._descriptionResKey = inDescriptionResKey;
        this._codeResKey = inCodeResKey;
        this._backgroundColor = inBackgroundColor;
        this._foregroundColor = inForegroundColor;
        this._iconIdPath = inIconIdPath;
    }

    @Override
    public String getKey() {
        return this._key;
    }

    @Override
    @Deprecated
    public String getDescriptionResKey() {
        return this._descriptionResKey;
    }

    @Deprecated
    public IPropertyKey getDescriptionResourceKey() {
        return this.getDescriptionPropertyKey();
    }

    @Override
    public IPropertyKey getDescriptionPropertyKey() {
        return PropertyKeyFactory.valueOf(this._descriptionResKey);
    }

    @Override
    @Deprecated
    public String getCodeResKey() {
        return this._codeResKey;
    }

    @Deprecated
    public IPropertyKey getCodeResourceKey() {
        return this.getCodePropertyKey();
    }

    @Override
    public IPropertyKey getCodePropertyKey() {
        return PropertyKeyFactory.valueOf(this._codeResKey);
    }

    public String getBackgroundColor() {
        return this._backgroundColor;
    }

    public String getForegroundColor() {
        return this._foregroundColor;
    }

    @Override
    @Nullable
    public IIconKey getIconKey() {
//        if (StringUtils.isNotBlank((String)this._iconIdPath)) {
//            return IconKeyFactory.valueOf(this._iconIdPath);
//        }
        return null;
    }

    public String getMappingClassName() {
        String thisClassName = this.getClass().getName();
        String mappingClassName = thisClassName.replaceFirst("business.atoms.", "persistence.atoms.UserType");
        return mappingClassName;
    }

    @Nullable
    public static AtomizedEnum resolve(Class inAtomizedEnumClass, String inKey) {
        AtomizedEnum enumInstance;
        try {
            Method m = inAtomizedEnumClass.getDeclaredMethod("getEnum", String.class);
            enumInstance = (AtomizedEnum)m.invoke(null, inKey);
        }
        catch (Exception e) {
            LOGGER.error((Object)("resolve: could not resove enum with key " + inKey + " of class " + inAtomizedEnumClass));
            return null;
        }
        return enumInstance;
    }
}
