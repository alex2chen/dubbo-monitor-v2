package com.kxtx.dubbo.monitor.service.dsrpt.builder;

import com.kxtx.dubbo.monitor.service.dsrpt.filter.DisruptorExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class ExceptionHandlerDecorator<T> extends DisruptorDecorator<T> {
    public ExceptionHandlerDecorator(Disruptor<T> disruptor) {
        super(disruptor);
    }

    @Override
    public void addDecorator() {
        super.addDecorator();
        disruptor.setDefaultExceptionHandler(new DisruptorExceptionHandler<T>());
    }
}
