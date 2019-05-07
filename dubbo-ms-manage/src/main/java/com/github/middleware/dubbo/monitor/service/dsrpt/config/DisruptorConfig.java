package com.github.middleware.dubbo.monitor.service.dsrpt.config;

import com.github.middleware.dubbo.monitor.domain.DisruptorSetting;
import com.github.middleware.dubbo.monitor.domain.EventHandlerChain;

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
