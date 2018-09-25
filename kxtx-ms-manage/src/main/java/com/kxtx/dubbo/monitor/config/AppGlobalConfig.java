package com.kxtx.dubbo.monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
@ComponentScan(basePackages = {"com.kxtx.dubbo.monitor"}, includeFilters = {@ComponentScan.Filter(value = Service.class)})
@Import({WebmvcConfig.class, SecurityConfig.class, DubboConfig.class, MongoConfig.class})
//@Import({WebmvcConfig.class, DubboConfig.class, MongoConfig.class})
@ImportResource("classpath*:spring-disruptor.xml")
@PropertySource("classpath:/application.properties")
public class AppGlobalConfig {
}
