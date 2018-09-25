package com.kxtx.dubbo.monitor.service.dsrpt.engine;

import com.kxtx.dubbo.monitor.domain.UrlEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class UrlEventFactory implements EventFactory<UrlEvent> {
    @Override
    public UrlEvent newInstance() {
        return new UrlEvent();
    }
}
