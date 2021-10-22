package com.zpmc.ztos.infra.base.helper;

import com.zpmc.ztos.infra.base.event.*;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentHashSet;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@EventSubscriber
public class TaskEventDispatcher implements EventListener {
    @Autowired
    private ZpmcEventBus zpmcEventBus;
    // TODO: replace with concurrent list
    private List<ZpmcAbstractRunnable> tasks = new ArrayList<>();

    public void addTask(ZpmcAbstractRunnable task) {
        tasks.add(task);
    }

    public void removeTask(ZpmcAbstractRunnable task) {
        tasks.remove(task);
    }

    @Override
    public void handleEvent(EventBase event) {
        if (event instanceof TaskEvent) {
            TaskEvent taskEvent = (TaskEvent) event;
            for (ZpmcAbstractRunnable task : tasks) {
                if (task.getTaskId().equals(taskEvent.getTaskId())) {
                    if (taskEvent instanceof TaskTriggerEvent) {
                        task.setTaskTriggerEvent((TaskTriggerEvent) taskEvent);
                    } else if (taskEvent instanceof TaskStopEvent){
                        task.setTaskCompleteEvent((TaskStopEvent) taskEvent);
                    }
                }
            }
        }
    }

    public void sendTaskCompleteEvent(TaskCompleteEvent event) {
        zpmcEventBus.postMQ(event);
    }
}
