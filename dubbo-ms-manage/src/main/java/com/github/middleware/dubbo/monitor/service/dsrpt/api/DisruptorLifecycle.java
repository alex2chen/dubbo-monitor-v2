package com.github.middleware.dubbo.monitor.service.dsrpt.api;

/**
 * Created by YT on 2017/6/30.
 */
public interface DisruptorLifecycle<T> {
    void init();

    void shutdown();

    void awaitShutdown(long time);

    void halt();

}
