package com.kxtx.dubbo.monitor.domain;

import com.lmax.disruptor.EventHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class EventHandlerChain<T> {
    private EventHandler<T>[] currentHandles;
    private EventHandler<T>[] nextHandles;

    public EventHandlerChain(EventHandler<T>[] currentHandles) {
        this(currentHandles, null);
    }

    public EventHandlerChain(EventHandler<T>[] currentHandles, EventHandler<T>[] nextHandles) {
        Assert.notNull(currentHandles);
        this.currentHandles = currentHandles;
        this.nextHandles = nextHandles;
    }

    public EventHandler<T>[] getCurrentHandles() {
        return currentHandles;
    }

    public EventHandler<T>[] getNextHandles() {
        return nextHandles;
    }

    public String printDependencyGraph() {
        StringJoiner str = new StringJoiner(" | ", "{", "}");
        printEventHandlers(str, getCurrentHandles());
        if (!ArrayUtils.isEmpty(getNextHandles())) {
            str.add(" -> ");
            printEventHandlers(str, getNextHandles());
        }
        return str.toString();
    }

    private void printEventHandlers(StringJoiner str, EventHandler<T>[] eventHandlers) {
        for (int j = 0; j < eventHandlers.length; j++) {
            str.add(eventHandlers[j].getClass().getSimpleName());
        }
    }
}
