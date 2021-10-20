package com.zpmc.ztos.infra.base.helper;

public interface ZpmcRunnable extends Runnable {

    default void run() {
        beforeExecute();
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        afterExecute();
    }

    void beforeExecute();
    void execute() throws InterruptedException;
    void afterExecute();
}
