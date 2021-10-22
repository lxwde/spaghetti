package com.zpmc.ztos.infra.base.event;

public class TaskTriggerEvent extends TaskEvent {
    public TaskTriggerEvent() {}
    public TaskTriggerEvent(String taskId) {
        super(taskId);
    }

    @Override
    public String toString() {
        return "TaskTriggerEvent{} " + super.toString();
    }
}
