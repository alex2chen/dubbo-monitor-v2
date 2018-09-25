package com.kxtx.dubbo.monitor.service.dsrpt.api;

import com.kxtx.dubbo.monitor.domain.StartArgs;

/**
 * Created by YT on 2017/6/30.
 */
public interface DisruptorLifecycle<T> {
    void init();

    void shutdown();

    void awaitShutdown(long time);

    void halt();

}
