package com.kxtx.dubbo.monitor.service.dsrpt.api;

import com.lmax.disruptor.EventTranslator;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public interface DisruptorEngine<T> extends DisruptorLifecycle<T> {
    void publish(EventTranslator<T> eventTranslator);
}
