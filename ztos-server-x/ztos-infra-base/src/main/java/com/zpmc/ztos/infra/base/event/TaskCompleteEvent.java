package com.zpmc.ztos.infra.base.event;

public class TaskCompleteEvent extends TaskEvent{
    public TaskCompleteEvent() {}
    public TaskCompleteEvent(String taskId) {
        super(taskId);
    }

    @Override
    public String toString() {
        return "TaskCompleteEvent{} " + super.toString();
    }
}
