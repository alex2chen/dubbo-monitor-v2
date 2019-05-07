package com.github.middleware.dubbo.monitor.service.dsrpt.api;

import com.lmax.disruptor.dsl.Disruptor;

/**
 * Created by YT on 2017/7/4.
 */
public interface DisruptorBuilder<T> {
    void builderEventFactory();

    void builderSetting();

    void builderThreadFactory();

    Disruptor<T> getDisruptor();
}
