package com.kxtx.dubbo.monitor.service.dsrpt.config;

import com.kxtx.dubbo.monitor.domain.DisruptorSetting;
import com.kxtx.dubbo.monitor.domain.EventHandlerChain;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
@Deprecated
public interface DisruptorConfig<T> {
    DisruptorSetting getDefaultConfig();

    EventHandlerChain getHandleChain();
}
