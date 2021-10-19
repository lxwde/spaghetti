package com.zpmc.ztos.infra.base.helper;

public interface ZpmcRunnable extends Runnable {

    default void run() {
        beforeExecute();
        execute();
        afterExecute();
    }

    void beforeExecute();
    void execute();
    void afterExecute();
}
