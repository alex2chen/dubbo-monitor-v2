package com.github.middleware.dubbo.monitor.service.dsrpt.api;

import com.github.middleware.dubbo.monitor.service.dsrpt.config.DisruptorConfig;

/**
 * Created by YT on 2017/6/30.
 */
@Deprecated
public interface EventPublisher<T> {
    void publish(T t);
    void setDisruptorConfig(DisruptorConfig<T> disruptorConfig);
}
