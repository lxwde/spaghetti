package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.enums.argo.JobFrequencyEnum;
import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * 工作定义
 * @author yejun
 */
@Data
public class JobDefinitionDO extends DatabaseEntity implements Serializable {

    private Long jobdefGkey;
    private String jobdefId;
    private String jobdefInternalName;
    private String jobdefDescription;
    private String jobdefMessageSubject;
    private String jobdefErrorMessageReceiverAddress;
    private String jobdefErrorMessagePrefix;
    private String jobdefMessageReceiverAddress;
    private String jobdefMessagePrefix;
    private JobFrequencyEnum jobdefFrequency;
    private Date jobdefLifeStartDate;
    private Date jobdefLifeEndDate;
    private String jobdefHours;
    private String jobdefMinutes;
    private Long jobdefRepeatIntervalMin;
    private Long jobdefRepeatIntervalHour;
    private Long jobdefRepeatIntervalDay;
    private Long jobdefRepeatCount;
    private Boolean jobdefIsOnLastDefinedWeekdayOfMonth;
    private Boolean jobdefIsAtLastDayOfMonth;
    private Boolean jobdefIsOnClosestWorkday;
    private Long jobdefIsOnDefinedWeekdayOccuranceOfMonth;
    private Boolean jobdefIsClustered;
    private String jobdefExecutionNode;
    private String jobdefExpression;
    private Date jobdefCreated;
    private String jobdefCreator;
    private Date jobdefChanged;
    private String jobdefChanger;
 //   private JobGroup jobdefJobGroup;
    private Set jobdefWeekdays;
    private Set jobdefDaysOfMonth;
    private Set jobdefLogs;

    public Serializable getPrimaryKey() {
        return this.getJobdefGkey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getJobdefGkey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof JobDefinitionDO)) {
            return false;
        }
        JobDefinitionDO that = (JobDefinitionDO)other;
        return ((Object)id).equals(that.getJobdefGkey());
    }

    public int hashCode() {
        Long id = this.getJobdefGkey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getJobdefGkey() {
        return this.jobdefGkey;
    }

    protected void setJobdefGkey(Long jobdefGkey) {
        this.jobdefGkey = jobdefGkey;
    }

    public String getJobdefId() {
        return this.jobdefId;
    }

    protected void setJobdefId(String jobdefId) {
        this.jobdefId = jobdefId;
    }

    public String getJobdefInternalName() {
        return this.jobdefInternalName;
    }

    protected void setJobdefInternalName(String jobdefInternalName) {
        this.jobdefInternalName = jobdefInternalName;
    }

    public String getJobdefDescription() {
        return this.jobdefDescription;
    }

    protected void setJobdefDescription(String jobdefDescription) {
        this.jobdefDescription = jobdefDescription;
    }

    public String getJobdefMessageSubject() {
        return this.jobdefMessageSubject;
    }

    protected void setJobdefMessageSubject(String jobdefMessageSubject) {
        this.jobdefMessageSubject = jobdefMessageSubject;
    }

    public String getJobdefErrorMessageReceiverAddress() {
        return this.jobdefErrorMessageReceiverAddress;
    }

    protected void setJobdefErrorMessageReceiverAddress(String jobdefErrorMessageReceiverAddress) {
        this.jobdefErrorMessageReceiverAddress = jobdefErrorMessageReceiverAddress;
    }

    public String getJobdefErrorMessagePrefix() {
        return this.jobdefErrorMessagePrefix;
    }

    protected void setJobdefErrorMessagePrefix(String jobdefErrorMessagePrefix) {
        this.jobdefErrorMessagePrefix = jobdefErrorMessagePrefix;
    }

    public String getJobdefMessageReceiverAddress() {
        return this.jobdefMessageReceiverAddress;
    }

    protected void setJobdefMessageReceiverAddress(String jobdefMessageReceiverAddress) {
        this.jobdefMessageReceiverAddress = jobdefMessageReceiverAddress;
    }

    public String getJobdefMessagePrefix() {
        return this.jobdefMessagePrefix;
    }

    protected void setJobdefMessagePrefix(String jobdefMessagePrefix) {
        this.jobdefMessagePrefix = jobdefMessagePrefix;
    }

    public JobFrequencyEnum getJobdefFrequency() {
        return this.jobdefFrequency;
    }

    protected void setJobdefFrequency(JobFrequencyEnum jobdefFrequency) {
        this.jobdefFrequency = jobdefFrequency;
    }

    public Date getJobdefLifeStartDate() {
        return this.jobdefLifeStartDate;
    }

    protected void setJobdefLifeStartDate(Date jobdefLifeStartDate) {
        this.jobdefLifeStartDate = jobdefLifeStartDate;
    }

    public Date getJobdefLifeEndDate() {
        return this.jobdefLifeEndDate;
    }

    protected void setJobdefLifeEndDate(Date jobdefLifeEndDate) {
        this.jobdefLifeEndDate = jobdefLifeEndDate;
    }

    public String getJobdefHours() {
        return this.jobdefHours;
    }

    protected void setJobdefHours(String jobdefHours) {
        this.jobdefHours = jobdefHours;
    }

    public String getJobdefMinutes() {
        return this.jobdefMinutes;
    }

    protected void setJobdefMinutes(String jobdefMinutes) {
        this.jobdefMinutes = jobdefMinutes;
    }

    public Long getJobdefRepeatIntervalMin() {
        return this.jobdefRepeatIntervalMin;
    }

    protected void setJobdefRepeatIntervalMin(Long jobdefRepeatIntervalMin) {
        this.jobdefRepeatIntervalMin = jobdefRepeatIntervalMin;
    }

    public Long getJobdefRepeatIntervalHour() {
        return this.jobdefRepeatIntervalHour;
    }

    protected void setJobdefRepeatIntervalHour(Long jobdefRepeatIntervalHour) {
        this.jobdefRepeatIntervalHour = jobdefRepeatIntervalHour;
    }

    public Long getJobdefRepeatIntervalDay() {
        return this.jobdefRepeatIntervalDay;
    }

    protected void setJobdefRepeatIntervalDay(Long jobdefRepeatIntervalDay) {
        this.jobdefRepeatIntervalDay = jobdefRepeatIntervalDay;
    }

    public Long getJobdefRepeatCount() {
        return this.jobdefRepeatCount;
    }

    protected void setJobdefRepeatCount(Long jobdefRepeatCount) {
        this.jobdefRepeatCount = jobdefRepeatCount;
    }

    public Boolean getJobdefIsOnLastDefinedWeekdayOfMonth() {
        return this.jobdefIsOnLastDefinedWeekdayOfMonth;
    }

    protected void setJobdefIsOnLastDefinedWeekdayOfMonth(Boolean jobdefIsOnLastDefinedWeekdayOfMonth) {
        this.jobdefIsOnLastDefinedWeekdayOfMonth = jobdefIsOnLastDefinedWeekdayOfMonth;
    }

    public Boolean getJobdefIsAtLastDayOfMonth() {
        return this.jobdefIsAtLastDayOfMonth;
    }

    protected void setJobdefIsAtLastDayOfMonth(Boolean jobdefIsAtLastDayOfMonth) {
        this.jobdefIsAtLastDayOfMonth = jobdefIsAtLastDayOfMonth;
    }

    public Boolean getJobdefIsOnClosestWorkday() {
        return this.jobdefIsOnClosestWorkday;
    }

    protected void setJobdefIsOnClosestWorkday(Boolean jobdefIsOnClosestWorkday) {
        this.jobdefIsOnClosestWorkday = jobdefIsOnClosestWorkday;
    }

    public Long getJobdefIsOnDefinedWeekdayOccuranceOfMonth() {
        return this.jobdefIsOnDefinedWeekdayOccuranceOfMonth;
    }

    protected void setJobdefIsOnDefinedWeekdayOccuranceOfMonth(Long jobdefIsOnDefinedWeekdayOccuranceOfMonth) {
        this.jobdefIsOnDefinedWeekdayOccuranceOfMonth = jobdefIsOnDefinedWeekdayOccuranceOfMonth;
    }

    public Boolean getJobdefIsClustered() {
        return this.jobdefIsClustered;
    }

    protected void setJobdefIsClustered(Boolean jobdefIsClustered) {
        this.jobdefIsClustered = jobdefIsClustered;
    }

    public String getJobdefExecutionNode() {
        return this.jobdefExecutionNode;
    }

    protected void setJobdefExecutionNode(String jobdefExecutionNode) {
        this.jobdefExecutionNode = jobdefExecutionNode;
    }

    public String getJobdefExpression() {
        return this.jobdefExpression;
    }

    protected void setJobdefExpression(String jobdefExpression) {
        this.jobdefExpression = jobdefExpression;
    }

    public Date getJobdefCreated() {
        return this.jobdefCreated;
    }

    protected void setJobdefCreated(Date jobdefCreated) {
        this.jobdefCreated = jobdefCreated;
    }

    public String getJobdefCreator() {
        return this.jobdefCreator;
    }

    protected void setJobdefCreator(String jobdefCreator) {
        this.jobdefCreator = jobdefCreator;
    }

    public Date getJobdefChanged() {
        return this.jobdefChanged;
    }

    protected void setJobdefChanged(Date jobdefChanged) {
        this.jobdefChanged = jobdefChanged;
    }

    public String getJobdefChanger() {
        return this.jobdefChanger;
    }

    protected void setJobdefChanger(String jobdefChanger) {
        this.jobdefChanger = jobdefChanger;
    }

//    public JobGroup getJobdefJobGroup() {
//        return this.jobdefJobGroup;
//    }
//
//    protected void setJobdefJobGroup(JobGroup jobdefJobGroup) {
//        this.jobdefJobGroup = jobdefJobGroup;
//    }

    public Set getJobdefWeekdays() {
        return this.jobdefWeekdays;
    }

    protected void setJobdefWeekdays(Set jobdefWeekdays) {
        this.jobdefWeekdays = jobdefWeekdays;
    }

    public Set getJobdefDaysOfMonth() {
        return this.jobdefDaysOfMonth;
    }

    protected void setJobdefDaysOfMonth(Set jobdefDaysOfMonth) {
        this.jobdefDaysOfMonth = jobdefDaysOfMonth;
    }

    public Set getJobdefLogs() {
        return this.jobdefLogs;
    }

    protected void setJobdefLogs(Set jobdefLogs) {
        this.jobdefLogs = jobdefLogs;
    }
}
