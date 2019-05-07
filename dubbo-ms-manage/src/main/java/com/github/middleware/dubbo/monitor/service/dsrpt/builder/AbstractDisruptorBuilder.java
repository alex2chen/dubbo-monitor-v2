package com.github.middleware.dubbo.monitor.service.dsrpt.builder;

import com.github.middleware.dubbo.monitor.domain.DisruptorSetting;
import com.github.middleware.dubbo.monitor.service.dsrpt.api.DisruptorBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.util.Assert;

import java.util.concurrent.ThreadFactory;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */

public abstract class AbstractDisruptorBuilder<T> implements DisruptorBuilder<T> {
    protected EventFactory<T> eventFactory;
    protected DisruptorSetting disruptorSetting;
    protected ThreadFactory threadFactory;

    @Override
    public Disruptor<T> getDisruptor() {
        Assert.notNull(disruptorSetting, "disruptorSetting is required.");
        return new Disruptor<T>(
                eventFactory,
                disruptorSetting.getRingBufferSize(),
                threadFactory,
                disruptorSetting.getProducerType(),
                disruptorSetting.getWaitStrategyType().instance()
        );
    }
}
