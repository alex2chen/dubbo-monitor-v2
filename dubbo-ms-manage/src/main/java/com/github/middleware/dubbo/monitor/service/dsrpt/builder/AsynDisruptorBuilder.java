package com.github.middleware.dubbo.monitor.service.dsrpt.builder;

import com.github.middleware.dubbo.monitor.core.NamedThreadFactory;
import com.github.middleware.dubbo.monitor.domain.DisruptorSetting;
import com.github.middleware.dubbo.monitor.service.dsrpt.engine.DefaultEventFactory;
import com.github.middleware.dubbo.monitor.service.dsrpt.engine.WaitStrategyType;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.Util;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class AsynDisruptorBuilder<T> extends AbstractDisruptorBuilder<T> {
    public AsynDisruptorBuilder() {
    }

    public AsynDisruptorBuilder(EventFactory<T> eventFactory) {
        this.eventFactory = eventFactory;
    }

    @Override
    public void builderEventFactory() {
        if (this.eventFactory == null) this.eventFactory = new DefaultEventFactory<T>();
    }

    @Override
    public void builderSetting() {
        this.disruptorSetting = new DisruptorSetting() {{
            setProducerType(ProducerType.SINGLE);
            setRingBufferSize(Util.ceilingNextPowerOfTwo(1024 * 1024));
            setWaitStrategyType(WaitStrategyType.YIELDING);
        }};
    }

    @Override
    public void builderThreadFactory() {
        this.threadFactory = new NamedThreadFactory();
    }
}
