package com.github.middleware.dubbo.monitor.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
@ComponentScan(basePackages = {"com.github.middleware.dubbo.monitor"}, includeFilters = {@ComponentScan.Filter(value = Service.class)})
@Import({WebmvcConfig.class, SecurityConfig.class, DubboConfig.class, MongoConfig.class})
//@Import({WebmvcConfig.class, DubboConfig.class, MongoConfig.class})
@ImportResource("classpath*:spring-disruptor.xml")
@PropertySource("classpath:/application.properties")
public class AppGlobalConfig {
}
