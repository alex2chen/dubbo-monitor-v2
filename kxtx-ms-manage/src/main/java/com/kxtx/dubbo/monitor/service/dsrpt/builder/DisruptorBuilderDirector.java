package com.kxtx.dubbo.monitor.service.dsrpt.builder;

import com.kxtx.dubbo.monitor.service.dsrpt.api.DisruptorBuilder;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class DisruptorBuilderDirector {
    public <T> Disruptor<T> builder(DisruptorBuilder<T> builder) {
        builder.builderEventFactory();
        builder.builderSetting();
        builder.builderThreadFactory();
        return builder.getDisruptor();
    }
}
