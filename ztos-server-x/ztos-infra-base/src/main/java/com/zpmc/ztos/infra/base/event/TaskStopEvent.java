package com.zpmc.ztos.infra.base.event;

public class TaskStopEvent extends TaskEvent {

    public TaskStopEvent(String taskId) {
        super(taskId);
    }
}
