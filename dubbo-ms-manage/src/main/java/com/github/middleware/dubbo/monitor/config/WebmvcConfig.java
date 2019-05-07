package com.github.middleware.dubbo.monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
@EnableWebMvc
public class WebmvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfig.class);
    private static final String VIEWS = "/WEB-INF/views/";
    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    private static final String RESOURCES_HANDLER = "/static/**";
    private static final String RESOURCES_LOCATION = "/WEB-INF/static/";

    /**
     * 注册试图处理器
     *
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        LOGGER.info("viewResolver");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEWS);
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * messageSource
     *
     * @return
     */
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        LOGGER.info("messageSource");
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info("addResourceHandlers");
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
    }

    /**
     * <mvc:default-servlet-handler/>
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        LOGGER.info("configureDefaultServletHandling");
        configurer.enable();
    }


    /**
     * 注册servlet适配器
     *
     * @return
     */
//    @Bean
//    public HandlerAdapter servletHandlerAdapter() {
//        LOGGER.info("servletHandlerAdapter");
//        return new SimpleServletHandlerAdapter();
//    }

//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LOGGER.info("localeChangeInterceptor");
//        return new LocaleChangeInterceptor();
//    }
//
//    @Bean(name = "localeResolver")
//    public CookieLocaleResolver cookieLocaleResolver() {
//        LOGGER.info("cookieLocaleResolver");
//        return new CookieLocaleResolver();
//    }
//    @Bean
//    public HandlerMapping resourceHandlerMapping() {
//        LOGGER.info("resourceHandlerMapping");
//        return super.resourceHandlerMapping();
//    }
}
