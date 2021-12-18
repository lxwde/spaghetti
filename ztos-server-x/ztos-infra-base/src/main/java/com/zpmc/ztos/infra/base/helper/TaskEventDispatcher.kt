package com.zpmc.ztos.infra.base.helper

import com.zpmc.ztos.infra.base.event.*
import org.springframework.beans.factory.annotation.Autowired
import com.zpmc.ztos.infra.base.helper.ZpmcAbstractRunnable
import org.springframework.stereotype.Component
import java.util.ArrayList

@Component
@EventSubscriber
class TaskEventDispatcher : EventListener {
    @Autowired
    private val zpmcEventBus: ZpmcEventBus? = null

    // TODO: replace with concurrent list
    private val tasks: MutableList<ZpmcAbstractRunnable> = ArrayList()
    fun addTask(task: ZpmcAbstractRunnable) {
        tasks.add(task)
    }

    fun removeTask(task: ZpmcAbstractRunnable) {
        tasks.remove(task)
    }

    override fun handleEvent(event: EventBase) {
        if (event is TaskEvent) {
            val taskEvent = event
            for (task in tasks) {
                if (task.taskId == taskEvent.taskId) {
                    if (taskEvent is TaskTriggerEvent) {
                        if (task.taskId == taskEvent.getTaskId()) {
                            task.setTaskTriggerEvent(taskEvent)
                        }
                    } else if (taskEvent is TaskStopEvent) {
                        if (task.taskId == taskEvent.getTaskId()) {
                            task.setTaskCompleteEvent(taskEvent)
                        }
                    }
                }
            }
        }
    }

    fun sendTaskCompleteEvent(event: TaskCompleteEvent?) {
        zpmcEventBus!!.postMQ(event)
    }
}