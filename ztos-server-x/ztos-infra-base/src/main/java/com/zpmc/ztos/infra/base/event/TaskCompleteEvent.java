package com.zpmc.ztos.infra.base.event;

public class TaskCompleteEvent extends TaskEvent{
    public TaskCompleteEvent(String taskId) {
        super(taskId);
    }
}
