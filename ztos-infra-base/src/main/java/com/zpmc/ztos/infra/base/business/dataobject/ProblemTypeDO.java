package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class ProblemTypeDO extends DatabaseEntity implements Serializable {

    private Long probtypeGkey;
    private String probtypeId;
    private String probtypeSolution;
    private String probtypeContext;
    private String probtypeDescription;
    private Boolean probtypeSystemProvided;
    private String probtypeCreator;
    private Date probtypeCreated;
    private Date probtypeChanged;
    private String probtypeChanger;

    public Serializable getPrimaryKey() {
        return this.getProbtypeGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getProbtypeGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ProblemTypeDO)) {
            return false;
        }
        ProblemTypeDO that = (ProblemTypeDO)other;
        return ((Object)id).equals(that.getProbtypeGkey());
    }

    public int hashCode() {
        Long id = this.getProbtypeGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getProbtypeGkey() {
        return this.probtypeGkey;
    }

    public void setProbtypeGkey(Long probtypeGkey) {
        this.probtypeGkey = probtypeGkey;
    }

    public String getProbtypeId() {
        return this.probtypeId;
    }

    public void setProbtypeId(String probtypeId) {
        this.probtypeId = probtypeId;
    }

    public String getProbtypeSolution() {
        return this.probtypeSolution;
    }

    public void setProbtypeSolution(String probtypeSolution) {
        this.probtypeSolution = probtypeSolution;
    }

    public String getProbtypeContext() {
        return this.probtypeContext;
    }

    public void setProbtypeContext(String probtypeContext) {
        this.probtypeContext = probtypeContext;
    }

    public String getProbtypeDescription() {
        return this.probtypeDescription;
    }

    public void setProbtypeDescription(String probtypeDescription) {
        this.probtypeDescription = probtypeDescription;
    }

    public Boolean getProbtypeSystemProvided() {
        return this.probtypeSystemProvided;
    }

    public void setProbtypeSystemProvided(Boolean probtypeSystemProvided) {
        this.probtypeSystemProvided = probtypeSystemProvided;
    }

    public String getProbtypeCreator() {
        return this.probtypeCreator;
    }

    public void setProbtypeCreator(String probtypeCreator) {
        this.probtypeCreator = probtypeCreator;
    }

    public Date getProbtypeCreated() {
        return this.probtypeCreated;
    }

    public void setProbtypeCreated(Date probtypeCreated) {
        this.probtypeCreated = probtypeCreated;
    }

    public Date getProbtypeChanged() {
        return this.probtypeChanged;
    }

    public void setProbtypeChanged(Date probtypeChanged) {
        this.probtypeChanged = probtypeChanged;
    }

    public String getProbtypeChanger() {
        return this.probtypeChanger;
    }

    public void setProbtypeChanger(String probtypeChanger) {
        this.probtypeChanger = probtypeChanger;
    }
}
