package com.kxtx.dubbo.monitor.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
public class DubboConfig {
    private static final String REGISTRY_ADDRESS = "registry.address";
    private static final String APP_NAME = "app.name";
    private static final String APP_OWNER = "app.owner";
    private static final String PROTOCOL_PORT = "protocol.port";
    private static final String REGISTRY_GROUP = "registry.group";
    @Autowired
    private Environment env;

    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.kxtx.dubbo.monitor");
        return annotationBean;
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(env.getProperty(APP_NAME, "kxtx-dubbo-monitor"));
        applicationConfig.setOwner(env.getProperty(APP_OWNER));
        applicationConfig.setLogger("slf4j");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(env.getProperty(REGISTRY_ADDRESS));
        registryConfig.setGroup(env.getProperty(REGISTRY_GROUP));
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo");
        protocolConfig.setPort(Integer.parseInt(env.getProperty(PROTOCOL_PORT, "20880")));
        return protocolConfig;
    }

}
