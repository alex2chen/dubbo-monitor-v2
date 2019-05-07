package com.github.middleware.dubbo.monitor.service.dsrpt.filter;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.lmax.disruptor.ExceptionHandler;

import java.util.StringJoiner;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class DisruptorExceptionHandler<T> implements ExceptionHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisruptorExceptionHandler.class);
    private String errorPrefix = "Disruptor failed for thread: ";

    @Override
    public void handleEventException(Throwable ex, long sequence, T event) {
        StringJoiner str = new StringJoiner(" | ");
        str.add(errorPrefix);
        str.add("Sequence: ");
        str.add(sequence + "");
        str.add("Event: ");
        str.add(event.toString());
        str.add("Exception message: ");
        str.add(ex.getMessage());
        LOGGER.error(str.toString(), ex);

        throw new RuntimeException(ex);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        LOGGER.error(errorPrefix + throwable.getMessage(), throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        LOGGER.error(errorPrefix + throwable.getMessage(), throwable);
    }
}
