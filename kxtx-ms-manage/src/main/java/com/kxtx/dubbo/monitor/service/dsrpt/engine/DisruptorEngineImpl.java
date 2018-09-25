package com.kxtx.dubbo.monitor.service.dsrpt.engine;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.kxtx.dubbo.monitor.core.NamedThreadFactory;
import com.kxtx.dubbo.monitor.domain.EventHandlerChain;
import com.kxtx.dubbo.monitor.domain.StartArgs;
import com.kxtx.dubbo.monitor.service.dsrpt.api.DisruptorBuilder;
import com.kxtx.dubbo.monitor.service.dsrpt.api.DisruptorEngine;
import com.kxtx.dubbo.monitor.service.dsrpt.builder.*;
import com.kxtx.dubbo.monitor.service.dsrpt.filter.DisruptorExceptionHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class DisruptorEngineImpl<T> extends AbstractDisruptorLifecycle<T> implements DisruptorEngine<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisruptorEngineImpl.class);
    private DisruptorBuilderDirector builderDirector = new DisruptorBuilderDirector();
    private DisruptorBuilder<T> disruptorBuilder;
    private List<EventHandlerChain<T>> eventHandlerChains;

    public DisruptorEngineImpl() {
        this(null);
    }

    public DisruptorEngineImpl(DisruptorBuilder<T> disruptorBuilder) {
        this(disruptorBuilder, null);
    }

    public DisruptorEngineImpl(DisruptorBuilder<T> disruptorBuilder, List<EventHandlerChain<T>> eventHandlerChains) {
        this.disruptorBuilder = disruptorBuilder;
        this.eventHandlerChains = eventHandlerChains;
    }

    @Override
    public void init() {
        setDisruptor(builderDirector.builder(disruptorBuilder));
        new EventHandlerChainDecorator(getDisruptor(), eventHandlerChains).addDecorator();
        new ExceptionHandlerDecorator(getDisruptor()).addDecorator();
        LOGGER.info("Created disruptor " + getDisruptorConfiguration());
        getDisruptor().start();
    }

    public String getDisruptorConfiguration() {
        StringJoiner str = new StringJoiner(" | ", "{", "}");
        str.add("Ringbuffer slot size: " + getDisruptor().getBufferSize());
        return str.toString();
    }

    @Override
    public void publish(EventTranslator eventTranslator) {
        Assert.notNull(getDisruptor(), "Disruptor instance is not started.");
        Assert.notNull(eventTranslator, "arg[eventTranslator] is required.");
        LOGGER.info("Published " + eventTranslator.getClass().getSimpleName() + " event to sequence: " + getCurrentLocation());
        getDisruptor().getRingBuffer().publishEvent(eventTranslator);
    }

    public void setDisruptorBuilder(DisruptorBuilder<T> disruptorBuilder) {
        this.disruptorBuilder = disruptorBuilder;
    }

    public void setEventHandlerChains(List<EventHandlerChain<T>> eventHandlerChains) {
        this.eventHandlerChains = eventHandlerChains;
    }
}
