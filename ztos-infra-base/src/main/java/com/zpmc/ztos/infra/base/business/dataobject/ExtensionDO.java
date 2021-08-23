package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.framework.ExtensionLanguageEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ExtensionDO extends DatabaseEntity implements Serializable {
    private Long extGkey;
    private String extName;
    private ExtensionLanguageEnum extLang;
    private Long extVersion;
    private Boolean extSysSeeded;
    private Boolean extEnabled;
    private String extType;
    private Long extScopeLevel;
    private String extScopeGkey;
    private String extClassName;
    private String extFileName;
    private String extSearchableContents;
    private String extDescription;
    private Date extCreated;
    private String extCreator;
    private Date extChanged;
    private String extChanger;
    private byte[] extContents;

    @Override
    public Serializable getPrimaryKey() {
        return this.getExtGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getExtGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ExtensionDO)) {
            return false;
        }
        ExtensionDO that = (ExtensionDO)other;
        return ((Object)id).equals(that.getExtGkey());
    }

    public int hashCode() {
        Long id = this.getExtGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getExtGkey() {
        return this.extGkey;
    }

    public void setExtGkey(Long extGkey) {
        this.extGkey = extGkey;
    }

    public String getExtName() {
        return this.extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public ExtensionLanguageEnum getExtLang() {
        return this.extLang;
    }

    public void setExtLang(ExtensionLanguageEnum extLang) {
        this.extLang = extLang;
    }

    public Long getExtVersion() {
        return this.extVersion;
    }

    public void setExtVersion(Long extVersion) {
        this.extVersion = extVersion;
    }

    public Boolean getExtSysSeeded() {
        return this.extSysSeeded;
    }

    public void setExtSysSeeded(Boolean extSysSeeded) {
        this.extSysSeeded = extSysSeeded;
    }

    public Boolean getExtEnabled() {
        return this.extEnabled;
    }

    public void setExtEnabled(Boolean extEnabled) {
        this.extEnabled = extEnabled;
    }

    public String getExtType() {
        return this.extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public Long getExtScopeLevel() {
        return this.extScopeLevel;
    }

    public void setExtScopeLevel(Long extScopeLevel) {
        this.extScopeLevel = extScopeLevel;
    }

    public String getExtScopeGkey() {
        return this.extScopeGkey;
    }

    public void setExtScopeGkey(String extScopeGkey) {
        this.extScopeGkey = extScopeGkey;
    }

    public String getExtClassName() {
        return this.extClassName;
    }

    public void setExtClassName(String extClassName) {
        this.extClassName = extClassName;
    }

    public String getExtFileName() {
        return this.extFileName;
    }

    public void setExtFileName(String extFileName) {
        this.extFileName = extFileName;
    }

    public String getExtSearchableContents() {
        return this.extSearchableContents;
    }

    public void setExtSearchableContents(String extSearchableContents) {
        this.extSearchableContents = extSearchableContents;
    }

    public String getExtDescription() {
        return this.extDescription;
    }

    public void setExtDescription(String extDescription) {
        this.extDescription = extDescription;
    }

    public Date getExtCreated() {
        return this.extCreated;
    }

    public void setExtCreated(Date extCreated) {
        this.extCreated = extCreated;
    }

    public String getExtCreator() {
        return this.extCreator;
    }

    public void setExtCreator(String extCreator) {
        this.extCreator = extCreator;
    }

    public Date getExtChanged() {
        return this.extChanged;
    }

    public void setExtChanged(Date extChanged) {
        this.extChanged = extChanged;
    }

    public String getExtChanger() {
        return this.extChanger;
    }

    public void setExtChanger(String extChanger) {
        this.extChanger = extChanger;
    }

    public byte[] getExtContents() {
        return this.extContents;
    }

    public void setExtContents(byte[] extContents) {
        this.extContents = extContents;
    }
}
