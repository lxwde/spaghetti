package com.zpmc.ztos.infra.base.common.events;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IEventListener;
import com.zpmc.ztos.infra.base.business.interfaces.IEventManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventManager implements IEventManager {

    private static final Logger LOGGER = Logger.getLogger(EventManager.class);
  //  @GuardedBy(value="this")
    @NotNull
    private List<IEventListener> _synchronousListeners = new LinkedList<IEventListener>();
  //  @GuardedBy(value="this")
    @NotNull
    private List<IEventListener> _asynchronousListeners = new LinkedList<IEventListener>();
    @NotNull
    final ExecutorService _asynchronousEventExecutor = Executors.newSingleThreadExecutor();

    @Override
    public synchronized void addEventListener(@NotNull IEventListener inListener) {
        if (this._asynchronousListeners.contains(inListener) || this._synchronousListeners.contains(inListener)) {
            throw new IllegalStateException("cannot register a listener more than once: " + inListener);
        }
        if (inListener.isAsynchronous()) {
            this._asynchronousListeners = EventManager.copyList(this._asynchronousListeners);
            this._asynchronousListeners.add(inListener);
        } else {
            this._synchronousListeners = EventManager.copyList(this._synchronousListeners);
            this._synchronousListeners.add(inListener);
        }
    }

    @Override
    public synchronized void removeEventListener(IEventListener inListener) {
        if (inListener.isAsynchronous()) {
            this._asynchronousListeners = EventManager.copyList(this._asynchronousListeners);
            this._asynchronousListeners.remove(inListener);
        } else {
            this._synchronousListeners = EventManager.copyList(this._synchronousListeners);
            this._synchronousListeners.remove(inListener);
        }
    }

    @Override
    public synchronized void postEvent(final @NotNull IEvent inEvent) {
        final List<IEventListener> myAsyncList = this._asynchronousListeners;
        List<IEventListener> mySyncList = this._synchronousListeners;
        if (!mySyncList.isEmpty()) {
            EventManager.dispatchEvent(mySyncList, inEvent);
        }
        if (!myAsyncList.isEmpty()) {
            this._asynchronousEventExecutor.submit(new Runnable(){

                @Override
                public void run() {
                    EventManager.dispatchEvent(myAsyncList, inEvent);
                }
            });
        }
    }

    private static void dispatchEvent(List<IEventListener> inListeners, IEvent inEvent) {
        for (IEventListener listener : inListeners) {
            try {
                if (!listener.getEventClass().isAssignableFrom(inEvent.getClass())) continue;
                listener.onEvent(inEvent);
            }
            catch (Throwable t) {
                LOGGER.error((Object)("exception occurred while dispatching event: " + inEvent + " to listener: " + listener), t);
            }
        }
    }

    private static List<IEventListener> copyList(List<IEventListener> inListeners) {
        return new LinkedList<IEventListener>(inListeners);
    }
}
