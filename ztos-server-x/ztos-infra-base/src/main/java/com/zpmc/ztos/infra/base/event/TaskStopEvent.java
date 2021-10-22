package com.zpmc.ztos.infra.base.event;

public class TaskStopEvent extends TaskEvent {
    public TaskStopEvent() {}
    public TaskStopEvent(String taskId) {
        super(taskId);
    }

    @Override
    public String toString() {
        return "TaskStopEvent{} " + super.toString();
    }
}
