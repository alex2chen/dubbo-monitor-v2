package com.github.middleware.dubbo.monitor.service.dsrpt.builder;

import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public abstract class DisruptorDecorator<T> {
    protected Disruptor<T> disruptor;

    public DisruptorDecorator(Disruptor<T> disruptor) {
        this.disruptor = disruptor;
    }

    public void addDecorator() {
        Assert.notNull(disruptor, "disruptor is required.");
    }
}
