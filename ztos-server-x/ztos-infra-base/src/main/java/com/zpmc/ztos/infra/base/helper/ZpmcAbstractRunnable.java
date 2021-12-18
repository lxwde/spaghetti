package com.zpmc.ztos.infra.base.helper;

import com.zpmc.ztos.infra.base.config.ApplicationContextProvider;
import com.zpmc.ztos.infra.base.event.TaskCompleteEvent;
import com.zpmc.ztos.infra.base.event.TaskStopEvent;
import com.zpmc.ztos.infra.base.event.TaskTriggerEvent;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class ZpmcAbstractRunnable implements ZpmcRunnable{
    public static final String NO_SUCH_TASK = "-1";
    private TaskEventDispatcher taskEventDispatcher;
    private TaskTriggerEvent taskTriggerEvent = new TaskTriggerEvent(NO_SUCH_TASK);
    private TaskStopEvent taskStopEvent = new TaskStopEvent(NO_SUCH_TASK);
    private String taskId;
    @Override
    public void beforeExecute() {

    }

    public ZpmcAbstractRunnable(String taskId) {
        this.taskId = taskId;
        taskEventDispatcher = ApplicationContextProvider.APPLICATION_CONTEXT.getBean(TaskEventDispatcher.class);
        taskEventDispatcher.addTask(this);
    }

    @Override
    public void execute() throws InterruptedException {
        while (!taskStopEvent.getTaskId().equals(this.taskId)) {
            Thread.sleep(300);
            if (taskTriggerEvent.getTaskId().equals(this.taskId)) {
                process();
                taskTriggerEvent.setTaskId(NO_SUCH_TASK);
            }
        }
    }

    public abstract void process();
    public abstract void processScheduled();

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public synchronized void setTaskTriggerEvent(TaskTriggerEvent taskTriggerEvent) {
        this.taskTriggerEvent = taskTriggerEvent;
    }

    public synchronized void setTaskCompleteEvent(TaskStopEvent taskStopEvent) {
        this.taskStopEvent = taskStopEvent;
    }

    @Override
    public void afterExecute() {
        taskEventDispatcher.sendTaskCompleteEvent(new TaskCompleteEvent(this.taskId));
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void timer() {
        processScheduled();
    }

}
