package com.github.middleware.dubbo.monitor.service.dsrpt.engine;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.github.middleware.dubbo.monitor.service.dsrpt.api.DisruptorLifecycle;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.TimeUnit;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public abstract class AbstractDisruptorLifecycle<T> implements DisruptorLifecycle<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDisruptorLifecycle.class);
    private Disruptor<T> disruptor;

    public abstract void init();

    @Override
    public void shutdown() {
        LOGGER.info("Disruptor has shutdown.");
        disruptor.shutdown();
    }

    @Override
    public void halt() {
        LOGGER.info("Disruptor has halt.");
        disruptor.halt();
    }

    @Override
    public void awaitShutdown(long time) {
        try {
            LOGGER.info("Disruptor has shutdown after " + time + " " + TimeUnit.SECONDS + ".");
            disruptor.shutdown(time, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            LOGGER.error("Disruptor awaitShutdown error:", e);
        }
    }

    public Disruptor<T> getDisruptor() {
        return disruptor;
    }

    public void setDisruptor(Disruptor<T> disruptor) {
        this.disruptor = disruptor;
    }

    public long getCurrentLocation() {
        return getDisruptor().getCursor();
    }
}
