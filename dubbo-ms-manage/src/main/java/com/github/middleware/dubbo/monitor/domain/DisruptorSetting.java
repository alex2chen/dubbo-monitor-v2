package com.github.middleware.dubbo.monitor.domain;

import com.github.middleware.dubbo.monitor.service.dsrpt.engine.WaitStrategyType;
import com.lmax.disruptor.dsl.ProducerType;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class DisruptorSetting implements Serializable {
    private int ringBufferSize;
    private ProducerType producerType;
    private WaitStrategyType waitStrategyType;

    public int getRingBufferSize() {
        return ringBufferSize;
    }

    public void setRingBufferSize(int ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
    }

    public ProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(ProducerType producerType) {
        this.producerType = producerType;
    }

    public WaitStrategyType getWaitStrategyType() {
        return waitStrategyType;
    }

    public void setWaitStrategyType(WaitStrategyType waitStrategyType) {
        this.waitStrategyType = waitStrategyType;
    }
}
