package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ProblemSolutionDO extends DatabaseEntity implements Serializable {

    private Long solutionGkey;
    private String solutionId;
    private Long solutionScopeLevel;
    private String solutionScopeGkey;
    private Boolean solutionIsActive;
    private Boolean solutionSystemProvided;
    private String solutionDescription;
    private String solutionEntity;
    private String solutionCreator;
    private Date solutionCreated;
    private Date solutionChanged;
    private String solutionChanger;
    private ProblemType solutionProblemType;
    private JobConfiguration solutionJobConfiguration;
    private Extension solutionDataProvider;
    private Extension solutionSolveStrategy;
    private Extension solutionConfiguration;
    private Extension solutionStatusHandler;
    private JobGroup solutionJobGroup;
    private SavedPredicate solutionPredicate;

    public Serializable getPrimaryKey() {
        return this.getSolutionGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getSolutionGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof ProblemSolutionDO)) {
            return false;
        }
        ProblemSolutionDO that = (ProblemSolutionDO)other;
        return ((Object)id).equals(that.getSolutionGkey());
    }

    public int hashCode() {
        Long id = this.getSolutionGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getSolutionGkey() {
        return this.solutionGkey;
    }

    public void setSolutionGkey(Long solutionGkey) {
        this.solutionGkey = solutionGkey;
    }

    public String getSolutionId() {
        return this.solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public Long getSolutionScopeLevel() {
        return this.solutionScopeLevel;
    }

    public void setSolutionScopeLevel(Long solutionScopeLevel) {
        this.solutionScopeLevel = solutionScopeLevel;
    }

    public String getSolutionScopeGkey() {
        return this.solutionScopeGkey;
    }

    public void setSolutionScopeGkey(String solutionScopeGkey) {
        this.solutionScopeGkey = solutionScopeGkey;
    }

    public Boolean getSolutionIsActive() {
        return this.solutionIsActive;
    }

    public void setSolutionIsActive(Boolean solutionIsActive) {
        this.solutionIsActive = solutionIsActive;
    }

    public Boolean getSolutionSystemProvided() {
        return this.solutionSystemProvided;
    }

    public void setSolutionSystemProvided(Boolean solutionSystemProvided) {
        this.solutionSystemProvided = solutionSystemProvided;
    }

    public String getSolutionDescription() {
        return this.solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public String getSolutionEntity() {
        return this.solutionEntity;
    }

    public void setSolutionEntity(String solutionEntity) {
        this.solutionEntity = solutionEntity;
    }

    public String getSolutionCreator() {
        return this.solutionCreator;
    }

    public void setSolutionCreator(String solutionCreator) {
        this.solutionCreator = solutionCreator;
    }

    public Date getSolutionCreated() {
        return this.solutionCreated;
    }

    public void setSolutionCreated(Date solutionCreated) {
        this.solutionCreated = solutionCreated;
    }

    public Date getSolutionChanged() {
        return this.solutionChanged;
    }

    public void setSolutionChanged(Date solutionChanged) {
        this.solutionChanged = solutionChanged;
    }

    public String getSolutionChanger() {
        return this.solutionChanger;
    }

    public void setSolutionChanger(String solutionChanger) {
        this.solutionChanger = solutionChanger;
    }

    public ProblemType getSolutionProblemType() {
        return this.solutionProblemType;
    }

    public void setSolutionProblemType(ProblemType solutionProblemType) {
        this.solutionProblemType = solutionProblemType;
    }

    public JobConfiguration getSolutionJobConfiguration() {
        return this.solutionJobConfiguration;
    }

    public void setSolutionJobConfiguration(JobConfiguration solutionJobConfiguration) {
        this.solutionJobConfiguration = solutionJobConfiguration;
    }

    public Extension getSolutionDataProvider() {
        return this.solutionDataProvider;
    }

    public void setSolutionDataProvider(Extension solutionDataProvider) {
        this.solutionDataProvider = solutionDataProvider;
    }

    public Extension getSolutionSolveStrategy() {
        return this.solutionSolveStrategy;
    }

    public void setSolutionSolveStrategy(Extension solutionSolveStrategy) {
        this.solutionSolveStrategy = solutionSolveStrategy;
    }

    public Extension getSolutionConfiguration() {
        return this.solutionConfiguration;
    }

    public void setSolutionConfiguration(Extension solutionConfiguration) {
        this.solutionConfiguration = solutionConfiguration;
    }

    public Extension getSolutionStatusHandler() {
        return this.solutionStatusHandler;
    }

    public void setSolutionStatusHandler(Extension solutionStatusHandler) {
        this.solutionStatusHandler = solutionStatusHandler;
    }

    public JobGroup getSolutionJobGroup() {
        return this.solutionJobGroup;
    }

    public void setSolutionJobGroup(JobGroup solutionJobGroup) {
        this.solutionJobGroup = solutionJobGroup;
    }

    public SavedPredicate getSolutionPredicate() {
        return this.solutionPredicate;
    }

    public void setSolutionPredicate(SavedPredicate solutionPredicate) {
        this.solutionPredicate = solutionPredicate;
    }

}
