package com.github.middleware.dubbo.monitor.domain;

import com.github.middleware.dubbo.monitor.service.dsrpt.engine.WaitStrategyType;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.Util;

import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
@Deprecated
public class StartArgs<T> {
    private DisruptorSetting setting;
    private List<EventHandlerChain<T>> handlerChain;
    private EventFactory<T> eventFactory;

    public StartArgs() {
        this.setting = new DisruptorSetting();
    }

    public StartArgs(DisruptorSetting setting, List<EventHandlerChain<T>> handlerChain, EventFactory<T> eventFactory) {
        this.setting = setting;
        this.handlerChain = handlerChain;
        this.eventFactory = eventFactory;
    }

    public StartArgs setRingBufferSize(int ringBufferSize) {
        this.setting.setRingBufferSize(Util.ceilingNextPowerOfTwo(ringBufferSize));
        return this;
    }

    public StartArgs setProducerType(ProducerType producerType) {
        this.setting.setProducerType(producerType);
        return this;
    }

    public StartArgs setWaitStrategyType(WaitStrategyType waitStrategyType) {
        this.setting.setWaitStrategyType(waitStrategyType);
        return this;
    }

    public StartArgs setEventFactory(EventFactory<T> eventFactory) {
        this.eventFactory = eventFactory;
        return this;
    }

    public DisruptorSetting getSetting() {
        return setting;
    }

    public void setSetting(DisruptorSetting setting) {
        this.setting = setting;
    }

    public List<EventHandlerChain<T>> getHandlerChain() {
        return handlerChain;
    }

    public void setHandlerChain(List<EventHandlerChain<T>> handlerChain) {
        this.handlerChain = handlerChain;
    }

    public EventFactory<T> getEventFactory() {
        return eventFactory;
    }
}
