package com.github.middleware.dubbo.monitor.service.dsrpt.builder;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.github.middleware.dubbo.monitor.domain.EventHandlerChain;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class EventHandlerChainDecorator<T> extends DisruptorDecorator<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerChainDecorator.class);
    private List<EventHandlerChain<T>> eventHandlerChains;

    public EventHandlerChainDecorator(Disruptor<T> disruptor) {
        super(disruptor);
    }

    public EventHandlerChainDecorator(Disruptor<T> disruptor, List<EventHandlerChain<T>> eventHandlerChains) {
        super(disruptor);
        this.eventHandlerChains = eventHandlerChains;
    }

    @Override
    public void addDecorator() {
        super.addDecorator();
        Assert.notNull(eventHandlerChains, "StartArgs.handlerChain is required.");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < eventHandlerChains.size(); i++) {
            EventHandlerChain<T> eventHandlersChain = eventHandlerChains.get(i);
            str.append(eventHandlersChain.printDependencyGraph());
            EventHandlerGroup<T> eventHandlerGroup = null;
            if (ArrayUtils.isEmpty(eventHandlersChain.getCurrentHandles())) continue;
            if (i == 0) {
                eventHandlerGroup = disruptor.handleEventsWith(eventHandlersChain.getCurrentHandles());
            } else {
                eventHandlerGroup = disruptor.after(eventHandlersChain.getCurrentHandles());
            }
            if (!ArrayUtils.isEmpty(eventHandlersChain.getNextHandles())) {
                eventHandlerGroup.then(eventHandlersChain.getNextHandles());
            }
        }
        LOGGER.info("Disruptor config EventHandler:" + str.toString());
    }
}
