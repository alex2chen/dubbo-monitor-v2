package com.github.middleware.dubbo.monitor.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.RegistryService;

import java.util.List;
import java.util.Set;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
public interface RegistryContainer {
    Set<String> getApplications();
    List<URL> getProvidersByApplication(String app);
    List<URL> getConsumersByApplication(String app);
    Set<String> getDependencies(String app, boolean reverse);
    Set<String> getHosts();
    List<URL> getProvidersByHost(String host);
    List<URL> getConsumersByHost(String host);
    Set<String> getServices();
    List<URL> getProvidersByService(String service);
    List<URL> getConsumersByService(String service);
    RegistryService getRegistry();
}
