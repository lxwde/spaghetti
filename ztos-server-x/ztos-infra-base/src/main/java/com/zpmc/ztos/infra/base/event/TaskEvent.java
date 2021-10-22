package com.zpmc.ztos.infra.base.event;

import com.google.common.base.Objects;

public abstract class TaskEvent extends EventBase {
    private String taskId;
    public TaskEvent() {}
    public TaskEvent(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEvent taskEvent = (TaskEvent) o;
        return Objects.equal(taskId, taskEvent.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }
}
